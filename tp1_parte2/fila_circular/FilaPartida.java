import java.io.*;

class FilaPartida{

    private static Partida[] fila = new Partida[6];
    private static int primeiro = 0;
    private static int ultimo = 0;

    public static void main(String[] args){

		String[] infos;
        String line;

		Partida[] partidas;
        

        line = MyIO.readLine();
                
        try{
            while(line.equals("0")==false){

                // String dir = "/home/joaoSC/Downloads/copa/"+line+".html";
                String dir = "/tmp/copa/"+line+".html";        
                
                infos = LerArquivo(dir);

                int numPartidas = contarPartidas(infos);

                partidas = new Partida[numPartidas];

                partidas = setInfosPartida(infos,partidas);

                for(int i = 0; i < partidas.length; i ++){
                    partidas[i].setCopa(Integer.parseInt(line));    
                    inserir(partidas[i]);
                }
                line = MyIO.readLine();
            }

            line = MyIO.readLine();
            int count = Integer.parseInt(line);

            MyIO.println("AAAAA:"+count);

            for( int i = 0; i < count; i++){
                line = MyIO.readLine();
                leituraMovimentacao(line);
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    //ESSE METODO REALIZA A LEITURA DAS MOVIMENTOS DA FILA E FAZ A CHAMADA DAS FUNÇÔES DE MOVIMENTAÇÕES
    private static void leituraMovimentacao(String line) throws Exception{

        String infosLine[];
        
        infosLine = line.split(" ");

                //if(line.contains("R")){
                  //  infoRemovido(remover());
                //}
                //else 
                
                if(line.contains("I")){
                    
                    String[] infos;
                    // String dir = "/home/joaoSC/Downloads/copa/"+infosLine[1]+".html";

                    String dir = "/tmp/copa/"+infosLine[1]+".html";
                    
                    infos = LerArquivo(dir);

                    int numPartidas = contarPartidas(infos);

                    Partida[] partidas = new Partida[numPartidas];

                    partidas = setInfosPartida(infos,partidas);

                    int pos = Integer.parseInt(infosLine[2]) -1;

                    partidas[pos].setCopa(Integer.parseInt(infosLine[1]));
                    
                
                    partidas[pos].imprimir();
                    
                    inserir(partidas[pos]);

                }
    }

    private static void calcularMediaGols(){

        float gools = 0;


        int aux = 0;
        int i;
        try{
            for(i = primeiro; i != ultimo ; i = (i+1)%fila.length ){
                gools+= fila[i].getPlacar1() + fila[i].getPlacar2();
                aux++;
            }
        }
        catch(Exception e){
            MyIO.println("erro aqui");
        }
        
        MyIO.println("Média: " + Math.round(gools/ aux)); 

    }

    //ESSE METODO CRIA OBJETOS DO TIPO PARTIDA É INICIALIZA SEUS ATRIBUTOS APARTI DAS INFORMAÇÕES
    //CONTIDAS EM UM TABELA HTML
    private static Partida[] setInfosPartida(String[] vetor,Partida[] partidas){
		String grupo = null;
		int pos = -1;

		boolean control = false;
		boolean posCenter = false;
        boolean posRight = false;
    
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
                posRight = true;
			}
			else if(posRight && vetor[i].contains("x")){
				String placar[] = getHtmlPlacar(vetor[i]);
				partidas[pos].setPlacar1(Integer.parseInt(placar[0]));
				partidas[pos].setPlacar2(Integer.parseInt(placar[1]));
                posRight = false;
				posCenter = true;
			}
			else if (posCenter){
				partidas[pos].setTime2(filtro(vetor[i]));
				posCenter = false;
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
        
        int pos = 1;

        if(array.length == 4){

            if(array[2].contains("&nbsp;")){
                pos = 1;

                array[pos] = array[pos].replace("<br","");
            }
            else{
                pos = 2;
            }
        }
 
		return array[pos].replace("</td","");
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
    private static String[] LerArquivo(String arq){
        
		String inicio = "<table cellspacing=\"1\">";
		String fim  = "<tr bgcolor=\"#000000\"><td colspan=\"5\">&nbsp;</td></tr>"; 	
        String str="";
		String dados[] = new String[2000];

		String charset = " content=\"text/html; charset";

		boolean toBegin = false;		
		
		int i = 0;	

        Arq.openRead(arq,"ISO-8859-1");	
		
		try{
			str = Arq.readLine();

			while(str.equals(fim) == false){
			
				if(str.equals(inicio))
					toBegin = true;
			
				if(toBegin && str.trim().equals("")==false){
					dados[i] = str;
					i++;		
				}
				
				str = Arq.readLine();
			}		
		}
		catch(Exception e ){
			System.out.println("Error");		
		}  
		return dados;
    }

    //METODOS DA LISTA

    //ESSE METODO RECEBE UM OBJETO PARTIDA QUE FOI REMOVIDA DA LISTA E IMPRIMI OS VALORES DO SEUS ATRIBUTOS
    private static void infoRemovido(Partida partida){
        MyIO.println("(R) "+partida.getCopa()+" - "+partida.getEtapa()+" - "+partida.getTime1()+" x "+partida.getTime2());
    }

    ////ESSE METODO RECEBE UM OBJETO DO TIPO PARTIDA E UM INTEIRO INDICANDO UM ÍNDICE DA LISTA E 
    //INSERI ESSE OBEJTO NA POSIÇÃO INDICADA PELO ÍNDICE 
    
    private static void inserir(Partida partida) {
        if( ( (ultimo+1) % fila.length  ) ==  primeiro )
                remover();
            //infoRemovido(remover());
        fila[ultimo] = partida;
        ultimo = (ultimo + 1 ) % fila.length;
        //calcularMediaGols();
    }


    //ESSE METODO RECEBE UM INTEIRO INDIC       ANDO UM ÍNDICE DA LISTA E REMOVE UM OBJETO DA POSIÇÃO INDICA PELO
    //ÍNDICE E REALOCA OS OBJETOS RESTANTES
    private static Partida remover(){
        if(primeiro == ultimo)
            MyIO.println("RUIM");

        Partida removida = fila[primeiro];

        primeiro = (primeiro+1) % fila.length;

        return removida;
    }

}


class Partida{

    private static Partida[] fila = new Partida[6];
    private static int n = 0;
    
    private static int primeiro = 0;
    private static int ultimo = primeiro;


    private int copa;
    private int placar1;
    private int placar2;
    private int dia;
    private int mes;

    private String etapa;
    private String time1;
    private String time2;
    private String local;

    
    public Partida(){
    }

    public  Partida(String etapa){
        setEtapa(etapa);
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

    public void imprimir(){

        MyIO.println("[COPA "+this.getCopa()+"] - "+this.getEtapa()+" - "+this.getDia()+
			"/"+this.getMes()+" - "+this.getTime1()+" ("+this.getPlacar1()+") x ("+
			this.getPlacar2()+") "+this.getTime2()+"  - "+this.getLocal());

    }

}
