import java.io.*;

public class MainPartida {

    public static void main(String[] args){
        
		String[] infos;
        String dir;
        String file;

		Partida[] partidas;
        
        BufferedReader in;

        
        file = MyIO.readLine();
        
        while(file.equals("0")==false){
            
            dir = "/home/joaoSC/Downloads/copa/"+file+".html";
            try{
                in = new BufferedReader(new FileReader(dir));
				infos = LerArquivo(in);
		
				int numPartidas = contarPartidas(infos);

				// for(int i = 0; i < infos.length; i++)
				// MyIO.println(infos[i]);

				partidas = new Partida[numPartidas];
				partidas = setInfosPartida(infos,partidas);
			
				// MyIO.println(file+"\n\n");
				for(int i = 0; i < partidas.length; i++){
					partidas[i].setCopa(Integer.parseInt(file));
					System.out.println("[COPA "+file+"] - GRUPO "+partidas[i].getEtapa()+" - "+partidas[i].getDia()+
					"/"+partidas[i].getMes()+" - "+partidas[i].getTime1()+" ("+partidas[i].getPlacar1()+") x ("+
					partidas[i].getPlacar2()+") "+partidas[i].getTime2()+"  "+partidas[i].getLocal());
				}
               
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }
            
            file = MyIO.readLine();
        }
    }

	private static Partida[] setInfosPartida(String[] vetor,Partida[] partidas){
		String grupo = null;
		int pos = -1;

		boolean control = false;
		boolean controlCenter = false;

		for(int i = 0; i < vetor.length && vetor[i]!=null;i++){
			
			if(vetor[i].contains("<td colspan=")){
				grupo = getHtmlGrupo(vetor[i]);
				// MyIO.println(grupo);
			}
			else if(vetor[i].contains("<tr>")){
				pos++;
				partidas[pos] = new Partida(grupo);
			}
			else if(vetor[i+1]!=null && vetor[i+1].contains("right")){
				String data[] = getHtmlData(vetor[i]);

				partidas[pos].setDia(Integer.parseInt(data[0]));
				partidas[pos].setMes(Integer.parseInt(data[1]));
			}
			else if(vetor[i].contains("right")){
				partidas[pos].setTime1(filtro(vetor[i]));
			}
			else if(vetor[i].contains("center")){
				String placar[] = getHtmlPlacar(vetor[i]);
				partidas[pos].setPlacar1(Integer.parseInt(placar[0]));
				partidas[pos].setPlacar2(Integer.parseInt(placar[1]));

				controlCenter = true;
			}
			else if (controlCenter){
				partidas[pos].setTime2(filtro(vetor[i]));
				controlCenter = false;
				control = true;
			}
			else if(control){
				partidas[pos].setLocal(filtro(vetor[i]));
				control = false;
			}		
		}
		return partidas;
	}

	private static String filtro(String str){
		String array[] = str.split(">");
		
		
		return array[1].replace(" ","").replace("</td","");
	}

	private static String[] getHtmlPlacar(String str){

		String placar[] = str.split("x");

		for(int i = 0; i < placar.length; i++){
            String tmp="";
            for(int j = 0; j < placar[i].length(); j++){
                if(placar[i].charAt(j)> 47 && placar[i].charAt(j)< 58)
                    tmp+=placar[i].charAt(j);
            }
            placar[i] = tmp;
        }
		return placar;
	}

	private static String[] getHtmlData(String str){
		String teste[] = str.split(">");
		String teste2[] = teste[1].split("<");

		return teste2[0].split("/");
	}
  
	private static String getHtmlGrupo(String str){
		String grupo = "";
		String vetor[] = str.split(">");

		for(int i = 0; i < vetor[2].length() && vetor[2].charAt(i)!='<';i++){
			grupo+=vetor[2].charAt(i);
		}

		return grupo.replace("GRUPO","");
	} 
	private static int contarPartidas(String[] infos){
		int partidas = 0;

		for(int i =0; i < infos.length && infos[i]!=null; i++){
			if(infos[i].contains("<tr>"))
				partidas++;
		}
		return partidas;
	}

	private static String getHtmlCharset(String str){
		
		String[] vetor;

		vetor = str.split(";");

        vetor = vetor[1].split("=");

        String charset = vetor[1].replace("\">","");
		
		return charset;
	}
	
    private static String[] LerArquivo(BufferedReader arq){
        
		String inicio = "<table cellspacing=\"1\">";
		String fim  = "<tr bgcolor=\"#000000\"><td colspan=\"5\">&nbsp;</td></tr>"; 	
        String str="";
		String dados[] = new String[2000];

		String charset = " content=\"text/html; charset";

		boolean toBegin = false;		
		
		int i = 0;		
		
		try{
			str = arq.readLine();

			while(str.equals(fim) == false){
			
				if(str.equals(inicio))
					toBegin = true;
				else if(str.contains(charset))
						MyIO.println(str);				
			
				if(toBegin && str.trim().equals("")==false){
					dados[i] = str;
					i++;		
				}
				
				str = arq.readLine();
						
			}		
		}
		catch(Exception e ){
			System.out.println("Error");		
		}  
		return dados;
    }	
}
