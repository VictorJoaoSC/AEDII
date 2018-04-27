import java.io.*;


class Partida{
    
    private int copa;
    private int placar1;
    private int placar2;
    private int dia;
    private int mes;

    private String etapa;
    private String time1;
    private String time2;
    private String local;

    
    public Partida(){}

    public  Partida(String etapa , int copa){
        setEtapa(etapa);
        setCopa(copa);
    }      

    //BEGIN - SETTERS
    public void setCopa(int copa){
        this.copa = copa;
    }
    public void setPlacar1(int placar1){
        this.placar1 = placar1;
    }
    public void setPlacar2(int placar2){
        this.placar2 = placar2;
    }
    public void setDia(int dia){
        this.dia = dia;
    }
    public void setMes(int mes){
        this.mes = mes;
    }
    public void setTime1(String time1){
        this.time1 = time1;
    }
    public void setTime2(String time2){
        this.time2 = time2;
    }
    public void setLocal(String local){
        this.local = local;
    }
    public void setEtapa(String etapa){
        this.etapa = etapa;
    }

    //END - SETTERS

    //BEGIN - GETTERS
    public int getCopa(){
        return this.copa;
    }
    public int getPlacar1(){
        return this.placar1;
    }
    public int getPlacar2(){
        return this.placar2;
    }
    public int getDia(){
        return this.dia;
    }
    public int getMes(){
        return this.mes;
    }
    public String getEtapa(){
        return this.etapa;
    }
    public String getTime1(){
        return this.time1;
    }
    public String getTime2(){
        return this.time2;
    }
    public String getLocal(){
        return this.local;
    }

    //END - GETTERS

    //METHODS

    //ESSE METODO IMPRIMI MOSTRA OS VALORES CONTIDOS DE TODOS OS ATRIBUTOS DE UM OBJETO DO TIPO PARTIDA 
    public void imprimir(){

        System.out.print("[COPA "+this.getCopa()+"] - "+this.getEtapa()+" - "+this.getDia()+
			"/"+this.getMes()+" - "+this.getTime1()+" ("+this.getPlacar1()+") x ("+
			this.getPlacar2()+") "+this.getTime2()+"  - "+this.getLocal()+"\n");

    }


    public static void main(String[] args){

        //PROBLEMAS COM CHARSET(ENCODING)
        // MyIO.setCharset("UTF-8");

		String[] infos;
        String file;

		Partida[] partidas;
        
        BufferedReader in;

        System.out.print("çç");
        
        file = MyIO.readLine();
        
        while(file.equals("0")==false){
             // dir = "/home/joaoSC/Downloads/copa/"+line".html";
                String dir = "/tmp/copa/"+file+".html";
            try{
                in = new BufferedReader(new FileReader(dir));
				
                infos = LerArquivo(in);
		
				int numPartidas = contarPartidas(infos);

                int copa = Integer.parseInt(file);

				partidas = new Partida[numPartidas];
				partidas = setInfosPartida(infos,partidas, copa);

                imprimir(partidas);
            }
            catch(IOException ioe){
                ioe.printStackTrace();
            }
            
            file = MyIO.readLine();
        }
    }

    //ESSE METODO RECEBE UM VETOR DE PARTIDAS È IMPRIMI OS VALORES DOS ATRIBUTOS DE CADA OBJETO DO VETOR
    private static void imprimir(Partida[] partidas){
        
        for(int i = 0; i < partidas.length; i++){
            partidas[i].imprimir();
		}
    }

    //ESSE METODO CRIA OBJETOS DO TIPO PARTIDA É INICIALIZA SEUS ATRIBUTOS APARTI DAS INFORMAÇÕES
    //CONTIDAS EM UM TABELA HTML
    private static Partida[] setInfosPartida(String[] vetor,Partida[] partidas, int copa){
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
				partidas[pos] = new Partida(grupo , copa);
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

    //ESSE METODO RECEBE UMA STRING E RETORNA UM INFORMAÇÃO CONTIDA ENTRE A TAG HTML <td> </td> 
    private static String filtro(String str){
		String array[] = str.split(">");
		
		
		return array[1].replace("</td","");
	}

    //ESSE METODO RECEBE UM STRING E PECORRE OS CARACTERES DA STRING VERIFICANDO SE O CARACTERE ESTÁ ENTRE
    // 47 E 58 CODIGOS TABELA ASCII E RETORNA UM VETOR  
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

    //ESSE METODO RECEBE UMA STRING É RETORNA UM VETOR CONTENDO INFORMAÇÕES DE DATA (DD/MM)
	private static String[] getHtmlData(String str){
		String teste[] = str.split(">");
		String teste2[] = teste[1].split("<");

		return teste2[0].split("/");
	}
    
    //ESSE METODO RETORNA UMA STRING CONTENDO O GRUPO DA QUAL UM TIME FAZ PARTE
	private static String getHtmlGrupo(String str){
		String grupo = "";
		String vetor[] = str.split(">");

		for(int i = 0; i < vetor[2].length() && vetor[2].charAt(i)!='<';i++){
			grupo+=vetor[2].charAt(i);
		}

		return grupo;
	} 
    //ESSE MÉTODO CONTA UM NÚMERO DE PARTIDAS CONTIDO EM UM ARQUIVO HTML APARTIR DA TAG <tr>
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

    //ESSE MÉTODO FAZ A LEITURA DE UM ARQUIVO HTML É GUARDA AS INFORMAÇÕES DESSE ARQUIVO EM UM VETOR
    // QUE É RETORNADO
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
