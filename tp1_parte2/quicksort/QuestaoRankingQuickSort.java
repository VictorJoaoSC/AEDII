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
        return pais;
    }
    public int getPontos(){
        return pontos;
    }
    public int getJogos(){
        return jogos;
    }
    public int getVitoria(){
        return vitoria;
    }
    public int getEmpates(){
        return empates;
    }
    public int getDerrotas(){
        return derrotas;
    }
    public int getGolsPro(){
        return golsPro;
    }
    public int getGolsContra(){
        return golsContra;
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

    private Time[] listaTime;
    private int n;

    Ranking(){
        this(2000);
    }

    Ranking(int tam){
        listaTime = new Time[2000];
        n = 0;
    }
    
    public  void inserirFim(Time time) throws Exception{

        if(n>= listaTime.length)
            throw new Exception("Lista Cheia - inserirFim");
        
        listaTime[n] = time;
        n++;
    }

    public Time[] getLista(){
        return listaTime;
    }

    public void imprimirElementosLista(){

        for(int i = 0; i < n;i++)
            listaTime[i].imprimirDadosTime();
    }

    public int getQtdElementosLista(){
        return n;
    }
}

class QuestaoRankingQuickSort{

    private static Ranking ranking = new Ranking();

    public static void main(String[] args){

        lerCopas();

        // insertionSortP();
        quickSortSort();

        ranking.imprimirElementosLista();
        
    }

     private static void lerCopas() {
        String[] infos;
        String file;
                
        file = MyIO.readLine();
        
        while(file.equals("0")==false){
            String dir = "/home/joaoSC/Downloads/copa/"+file+".html";

            // String dir = "/tmp/copa/"+file+".html";
                        
            infos = LerArquivo(dir);

            int copa = Integer.parseInt(file);

            buscarPartidas(infos,copa);
            
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

    private static void buscarPartidas( String[] vetor, int copa){

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
                inserirElementosLista(time1,time2,placar1,placar2);
                controlCenter = false;	
			}
		}
    }

    private static void inserirElementosLista(String time1,String time2, int placar1, int placar2){
        
        try{
            if(buscarTimeExistente(time1,placar1,placar2) == false)
                ranking.inserirFim( new Time(time1,placar1,placar2) );
            
            if(buscarTimeExistente(time2,placar2,placar1) == false)
                ranking.inserirFim( new Time (time2,placar2,placar1) );
        }
        catch(Exception erro){

        }
        
    }

    private static boolean buscarTimeExistente( String time , int placar1, int placar2){

        Time[] reference = ranking.getLista();

        boolean existe = false;

        for( int i = 0; i < ranking.getQtdElementosLista() && !existe ;i++){

            if(reference[i].getPais().equals(time)){
                existe = true;
                refreshRanking(i,placar1,placar2);
            }

        }

        return existe;
        
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

    private static void refreshRanking(int pos, int placar1, int placar2){

        Time reference = ranking.getLista()[pos];

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

    private static void quickSortSort(){
        int e = 0;
        int d = ranking.getQtdElementosLista() -1;
        Time[] lista = ranking.getLista();
        quickSortSortP(e,d,lista);
        // quickSortSort(e,d,lista);
    }

    private static void quickSortSort(int e, int d,Time[] lista){
        int i = e;
        int j = d;

        Time pivo = lista[(d + e)/2];
        while( i <=j ){
            while(lista[i].getGolsPro() < pivo.getGolsPro()){
                // if( lista[i+1]!=null && lista[i].getGolsPro() == lista[i+1].getGolsPro())
                //     if(lista[i].getPais().compareToIgnoreCase(lista[i+1].getPais()) > 0)
                //         swap(i,i+1,lista);
                i++;
                
            }

            if(lista[i].getGolsPro() == pivo.getGolsPro()){
                if(lista[i].getPais().compareToIgnoreCase(pivo.getPais()) > 0){
                    swap(i,(d+e)/2,lista);
                    // pivo = lista[i];
                }
                    
            }


            
            while(lista[j].getGolsPro() > pivo.getGolsPro()){
                // if( lista[j].getGolsPro() == lista[j-1].getGolsPro())
                //     if(lista[j].getPais().compareToIgnoreCase(lista[j-1].getPais()) < 0)
                //         swap(j-1,j,lista);
                  j--;
             }

            if(lista[j].getGolsPro() == pivo.getGolsPro()){
                if(lista[j].getPais().compareToIgnoreCase(pivo.getPais()) < 0){
                    swap((d+e)/2,j,lista);
                    // pivo = lista[j];
                }
                    
            }


        
            // else{
            //     if(lista[j].getDerrotas() == pivo.getDerrotas())
            //     if(lista[j].getPais().compareToIgnoreCase(pivo.getPais()) > 0)
            //         swap(j,(d+e)/2,lista);

            // }
            
            // if(i == j){
            //     if(lista[i].getGolsPro() == lista[j].getGolsPro()){
            //     if(lista[i].getPais().compareToIgnoreCase(lista[j].getPais()) > 0){
            //         swap(i,j,lista);
            //     }
            //     }
            // }
            


            if( i <= j ){
                swap(i,j,lista);
                i++;
                j--;
            }
                
        }
        if(e < j)
            quickSortSort(e,j,lista);
        if(i < d)
            quickSortSort(i,d,lista);
        
        
    }

    private static void quickSortSortP(int e, int d,Time[] lista){
        int i = e;
        int j = d;

        String pivo = lista[(d + e)/2].getPais();
        while( i <=j ){
            while(lista[i].getPais().compareToIgnoreCase(pivo) < 0){
                i++;
            }
            while(lista[j].getPais().compareToIgnoreCase(pivo) > 0)
                j--;
            if( i <= j ){
                swap(i,j,lista);
                i++;
                j--;
            }
                
        }
        if(e < j)
            quickSortSortP(e,j,lista);
        if(i < d)
            quickSortSortP(i,d,lista);
    }

    //  public static void insertionSortP(){

    //     Time[] lista = ranking.getLista();

    //     int n = ranking.getQtdElementosLista();

    //     for(int i = 1; i < n; i++){

    //         Time temp = lista[i];
    //         int j = i -1;
            
    //         while( (j>=0) && palavra( lista[j].getPais(), temp.getPais()) ){
    //             lista[j+1] = lista[j];
    //             j--;
    //         }            

    //         lista[j+1] = temp;

    //     }
    // }


    // private static boolean palavra(String str1, String str2){

    //     boolean maior = false;
    //     boolean iguais = false;

    //     int i = 0;

    //     do{
    //         if( str1.charAt(i) == str2.charAt(i) )
    //             iguais = true;
    //         else{
    //             if(str1.charAt(i) > str2.charAt(i))
    //                 maior = true;
    //             iguais = false;
    //         } 
    //         i++;
    //     }while( !maior && iguais && i < str1.length() && i < str2.length());

    //     return maior;
    // }


    private static void swap(int menor, int i, Time[] lista){

        Time tmp = lista[menor];

        lista[menor] = lista[i];

        lista[i] = tmp;

    }
}