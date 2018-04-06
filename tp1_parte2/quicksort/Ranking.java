import java.io.*;


class Time{

    String pais;

    private int pontos;
    private int jogos;
    private int vitoria;
    private int empates;
    private int derrotas;
    private int golsPro;
    private int golsContra;
    private int saldoGols;
    private int doidao;

    public Time(){
        this.pontos = 0;
        this.jogos = 1;
        this.vitoria = 0;
        this.empates = 0;
        this.derrotas = 0;
        this.golsPro = 0;
        this.golsContra = 0;
        this.saldoGols = 0;

    }

    public Time( String pais, int placar1, int placar2){

        this();
        
        this.pais = pais;
        
        this.golsContra = this.getGolsContra() + placar2;

        this.golsPro=  this.getGolsPro() + placar1 ;

        if(placar1 > placar2)
            this.setVitorias();
        else
            if(placar1 == placar2)
                this.setEmpates();
            else
                this.setDerrotas();
    }

    public int getDoidao(){
        return this.pontos * 1000 + golsPro;
    }

    public String getPais(){
        return this.pais;
    }
    public int getPontos(){
        return this.pontos;
    }
    public int getJogos(){
        return this.jogos;
    }
    public int getVitoria(){
        return this.vitoria;
    }
    public int getEmpates(){
        return this.empates;
    }
    public int getDerrotas(){
        return this.derrotas;
    }
    public int getGolsPro(){
        return this.golsPro;
    }
    public int getGolsContra(){
        return this.golsContra;
    }
    public int getSaldoGols(){
        return  this.saldoGols = this.golsPro - this.golsContra ;
    }
    
    public void setPais(String pais){
        this.pais = pais;
    }
    public void setJogos(){
        this.jogos++;
    }
    public void setVitorias(){
        this.vitoria++;
        this.setPontos(3);
    }
    public void setEmpates(){
        this.empates++;
        this.setPontos(1);
    }
    public void setDerrotas(){
        this.derrotas++;
    }
    public void setPontos(int pontos){
        this.pontos +=pontos;
    }
    public void setGolsPro(int golsPro){
        this.golsPro = golsPro;
    }
    public void setGolsContra(int golsContra){
        this.golsContra = golsContra;
    }
    public void setSaldoGols(int saldoGols){
        this.saldoGols = this.golsPro - this.golsContra;
    }

    public void imprimirDadosTime(){
        MyIO.println(this.pais+" pg("+this.pontos+") j("+this.jogos+") v("+this.vitoria+") e("+this.empates+") d("+this.derrotas+") gp("+this.golsPro+") gc("+this.golsContra+") sg("+this.getSaldoGols()+") d("+getDoidao()+")");
    }

}

class Ranking{

    static Time[] listaTime = new Time[2000];
    static int n=0;

    public static void main(String[] args){
        lerPartidas();
        insertionSort();
        imprimirElementosLista();
    

    }

    public static void insertionSort(){

        for(int i = 1; i < n; i++){

            Time temp = listaTime[i];
            int j = i -1;

            while( (j>=0) && (listaTime[j].getVitoria() > temp.getVitoria() ) ){
                listaTime[j+1] = listaTime[j];
                j--;
            }

            listaTime[j+1] = temp;

        }
    }


    private static void lerPartidas() {
        String[] infos;
        String file;
                
        file = MyIO.readLine();
        
        while(file.equals("0")==false){
            String dir = "/home/joaoSC/Downloads/copa/"+file+".html";
            // String dir = "/tmp/copa/"+file+".html";
            				
            infos = LerArquivo(dir);

            int copa = Integer.parseInt(file);

            setPartidaRanking(infos,copa);
           
            file = MyIO.readLine();
        }
    }

    private static String[] LerArquivo(String dir){
        
		String inicio = "<table cellspacing=\"1\">";
		String fim  = "<tr bgcolor=\"#000000\"><td colspan=\"5\">&nbsp;</td></tr>"; 	
        String str="";
		String dados[] = new String[2000];

		String charset = " content=\"text/html; charset";

		boolean toBegin = false;		
		
		int i = 0;

        Arq.openRead(dir,"ISO-8859-1");		
		
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

    private static int contarPartidas(String[] infos){
		int partidas = 0;

		for(int i =0; i < infos.length && infos[i]!=null; i++){
			if(infos[i].contains("<tr>"))
				partidas++;
		}
		return partidas;
	}

    private static void setPartidaRanking( String[] vetor, int copa){

        String time1 = null;
        String time2 = null;

        int placar1 = 0;
        int placar2 = 0;
		
		boolean controlCenter = false;
        boolean posRight = false;

		for(int i = 0; i < vetor.length && vetor[i]!=null;i++){
            
            if(vetor[i].contains("right")){
				// partidas[pos].setTime1(filtro(vetor[i]));
                time1 =  paisCorrespondentes( filtro(vetor[i]) );
                posRight = true;
			}
			else if(posRight && vetor[i].contains("x")){
				String placar[] = getHtmlPlacar(vetor[i]);
			 	placar1 = (Integer.parseInt(placar[0]));
			 	placar2 = (Integer.parseInt(placar[1]));

				controlCenter = true;
                posRight = false;
			}
			else if (controlCenter){
				time2 = paisCorrespondentes( filtro(vetor[i]) );
                verificarLista( time1, time2, placar1, placar2);
                controlCenter = false;	
			}

           	
		}
    }

    private static String paisCorrespondentes( String pais){

        if(pais.equals("Alemanha Ocidental"))
            pais = "Alemanha";
        else if(pais.equals("União Soviética"))
            pais = "Rússia";
        else if(pais.equals("Tchecoslováquia"))
            pais = "República Tcheca";
        else if(pais.equals("Iugoslávia") || pais.equals("Sérvia e Montenegro"))
            pais = "Sérvia";
        
        return pais;
            
    }

    private static void verificarLista( String time1, String time2, int placar1, int placar2 ){

        int i = 0;

        boolean equalTime1 = false;
        boolean equalTime2 = false;

        while( i < listaTime.length && listaTime[i] != null && ( !equalTime1 || !equalTime2 ) ){

            if(listaTime[i].getPais().equals(time1)){
                equalTime1 = true;
                refreshRanking(i,placar1,placar2);
            }
            else if(listaTime[i].getPais().equals(time2)){
                equalTime2 = true;
                refreshRanking(i,placar2,placar1);
            }
            i++;
        }
        try{
            if(!equalTime1){
            Time time = new Time( time1, placar1, placar2);
            inserirFim(time);
        }
            if(!equalTime2){
                Time time = new Time( time2,placar2, placar1);
                inserirFim(time);
               
            }
        }
        catch(Exception e){
            MyIO.println("aaaf");
        }
        

    }

    private static void refreshRanking(int pos, int placar1, int placar2){

        Time reference = listaTime[pos];

        reference.setGolsContra( reference.getGolsContra() + placar2 );

        reference.setGolsPro( reference.getGolsPro() + placar1 );

        if(placar1 > placar2)
            reference.setVitorias();
        else
            if(placar1 == placar2)
                reference.setEmpates();
            else
                reference.setDerrotas();
        
        reference.setJogos();

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

    private static void inserirFim(Time time) throws Exception{

        if(n>= listaTime.length)
            throw new Exception("Lista Cheia - inserirFim");
        
        listaTime[n] = time;
        n++;
    }

     private static void inserirInicio(Time time) throws Exception{
        
        if(n>=listaTime.length)
            throw new Exception("Lista cheia - inserirInicio");
        for(int i = n; i > 0; i--){
            listaTime[i] = listaTime[i-1];
        }

        listaTime[0] = time;
        n++;
    }

    private static void imprimirElementosLista(){
        for(int i = 0; i < n;i++)
            listaTime[i].imprimirDadosTime();
    }
}