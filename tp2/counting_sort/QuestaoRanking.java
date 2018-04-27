import java.io.*;
import java.util.Arrays;


class Time implements Comparable<Time>{

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

    //BEGIN - GETTERS

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

    //GETTERS - END


    
    //SETTERS - BEGIN
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


    @Override
    public int compareTo(Time time){
        return this.pais.compareToIgnoreCase(time.getPais());
    }


    //SETTERS - END

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
    //METODO RETORNA UM VETOR DO TIPO TIME
    public Time[] getLista(){
        return listaTime;
    }

    //METODO QUE IMPRIMI OS ATRIBUTOS DE CADA OBJETO DO VETOR DO TIPO TIME
    public void imprimirElementosLista(){

        for(int i = 0; i < n;i++)
            listaTime[i].imprimirDadosTime();
    }

    //METODO QUE RETORNA A QUANTIDADE DE ELEMENTOS NO VETOR DO TIPO TIME
    public int getQtdElementosLista(){
        return n;
    }
}

class QuestaoRanking{

    private static Ranking ranking = new Ranking();
    private static int comparacoes  = 0;

    public static void main(String[] args){

        lerCopas();
        
        // Arrays.sort(ranking.getLista(),0,ranking.getQtdElementosLista());
        
        long inicio = System.currentTimeMillis();
        countsort();
        long tempo = System.currentTimeMillis() - inicio;

        criarTxt(tempo);

        ranking.imprimirElementosLista();

    }

    //METODO RESPONSAVEL PELA LEITURA DO ARQUIVO DE ENTRA TXT
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
    
    //METODO RESPONSAVEL PELA LEITURA DO ARQUIVO HTML E ARMAZENAR SUAS INFORMAÇÕES EM UM VETOR
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

    //ESSE METODO CRIA OBJETOS DO TIPO PARTIDA É INICIALIZA SEUS ATRIBUTOS APARTI DAS INFORMAÇÕES
    //CONTIDAS EM UM TABELA HTML
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
    //METODO QUE INSERI OS OBEJTOS DO TIPO TIME NA LISTA DE RANKING CASO ESTE OBJETO NAO EXITA NA LISTA
    //SE NAO SEUS ATRIBUTOS SAO ATUALIZADOS
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

     //METOD RESPONSAVEL POR IDENTIFIAR OBJETOS QUE POSSUI O MESMO VALOR PARA ATRIBUTO PAIS E SE EXISTIR
    //O SEUS OUTROS ATRIBUTOS SÃO ATUALIZADOS
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
    
    //METODO QUE IDENTIFIA PAIS CORRESPONDENTES
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

    //METODO RESPONSAVEL POR ATULIZAR OS ATRIBUTOS DE OBJETOS QUE NÃO FORAM INSERIDOS NA LISTA
    //POR JÁ POSSUIR OBEJTO COM O MESMO VALOR ATRIBUTO PAÍS
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


    private static void countsort(){
        int n = ranking.getQtdElementosLista();

        Time[] lista = ranking.getLista();

        countingSort(lista,n);
    }

    private static int getMin (Time[] lista, int n) {
        
        int min = lista[0].getGolsContra();

        for (int i = 1 ; i < n; i++) {
            if (lista[i].getGolsContra() < min) { 
                min = lista[i].getGolsContra();
                comparacoes++;
            }
            else
                comparacoes++;
         }
        
        return min;
     }
 
    public static void countingSort(Time[] lista, int n){  
         
         int menor = getMin(lista,n);  
         
         int maior = getMax(lista,n);  
         
         
         int[] aux = new int[maior - menor +1];

         for(int x=0;x<n;x++){  
             aux[lista[x].getGolsContra() -1]++;  
         }

         for(int x=1;x<aux.length;x++){   
             aux[x] += aux[x -1];  
         }

         Time[] resposta = new Time[n];  
         
         for(int x=0;x<n;x++){  
             resposta[aux[lista[x].getGolsContra()-1]-1]=lista[x];  
             aux[lista[x].getGolsContra() -1]-=1;  
         }  
         
         lista = resposta;

     } 



    // private static void countsort(Time[] lista, int n){

    //     Time output[] = new Time[n];
    //     int count[] = new int[getMax(lista,n)+1];
    //     // Arrays.fill(count,0);
    //     int i;

    //     for( i = 0; i < count.length;count[i]=0,i++);

    //     for( i = 0; i < n;count[lista[i].getGolsContra()]++,i++);

    //     for( i = 1; i < count.length;count[i]+=count[i-1],i++);

    //     for( i = n-1; i>=0; output[count[lista[i].getGolsContra()]-1] = lista[i], count[lista[i].getGolsContra()]--,i--);
    // }
 

    private static int getMax(Time[] lista, int n)
    {
        int max = lista[0].getDoidao();
        for (int i = 1; i < n; i++)
            if (lista[i].getDoidao() > max){
                comparacoes++;
                max = lista[i].getDoidao();
            }
            else
                comparacoes++;
                
        return max;
    }


     private static void criarTxt(long tempo){
        String str = "581203\t"+tempo+"\t"+comparacoes;

        FileWriter arq;

        try{
            arq = new FileWriter( new File("581203_radixsort.txt"));
            arq.write(str);
            arq.close();
        }
        catch(Exception e){
            MyIO.println("merda");
        }
    }

}