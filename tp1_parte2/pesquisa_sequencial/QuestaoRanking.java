//OK

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

    //SETTERS - END

    //METODO QUE IMPRIMI OS ATRIBUTOS DO OBJETO TIME
    public void imprimirDadosTime(){
        MyIO.println(this.pais+" pg("+this.pontos+") j("+this.jogos+") v("+this.vitoria+") e("+this.empates+") d("+this.derrotas+") gp("+this.golsPro+") gc("+this.golsContra+") sg("+this.getSaldoGols()+")");
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

    private static int comparacoes = 0;

    public static void main(String[] args){

        lerCopas();
    }

    //METODO RESPONSAVEL PELA LEITURA DO ARQUIVO DE ENTRA TXT
     private static void lerCopas() {
        String[] infos;
        String file;
                
        file = MyIO.readLine();
        
        while(file.equals("0")==false){
            // String dir = "/home/joaoSC/Downloads/copa/"+file+".html";

            String dir = "/tmp/copa/"+file+".html";
                        
            infos = LerArquivo(dir);

            int copa = Integer.parseInt(file);

            buscarPartidas(infos,copa);
            
            file = MyIO.readLine();
        }

        String pais = MyIO.readLine();
        
        long tempo = 0;

        while(pais.equals("FIM") == false){
            
            long inicio = System.currentTimeMillis();

            MyIO.println(pais+" -- "+(buscaSequencial(pais)?"SIM":"NÃO"));

            tempo+=System.currentTimeMillis() - inicio;

            pais = MyIO.readLine();
        }

        criarTxt(tempo);
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

    //METODO QUE REALIZA A TROCA ENTRE SI DE DOIS OBJETOS EM UM VETOR DO TIPO TIME
    private static void swap(int menor, int i, Time[] lista){

        Time tmp = lista[menor];

        lista[menor] = lista[i];

        lista[i] = tmp;

    }

    //METODO RESPONSAVEL POR CRIAR ARQUIVO TXT NO DIRETORIA CORESSPONDENTE CONTENDO INFORMAÇÃO DE
    //QUANTIDA DE COMPARACOES REALIZADAS NA ORDENÇÃO DA LISTA DO TIPO TIME E O TEMPO DE EXECUÇÃO DA 
    //ORDENAÇÃO
    private static void criarTxt(long time){
        String arq_name = "581203_sequencial.txt";

        String save = "581203\t"+time+"\t"+"\t"+comparacoes;

        FileWriter arq;

        try{
            arq = new FileWriter( new File(arq_name));
            arq.write(save);
            arq.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

     private static boolean buscaSequencial(String key){

        boolean isKey = false;

        int tam = ranking.getQtdElementosLista();

        Time[] lista = ranking.getLista();

        for( int i = 0; i < tam  && !isKey; i++ ){
            if(lista[i].getPais().equals(key)){
                comparacoes++;
                isKey = true;
            }else
                comparacoes++;
                
        }

        return isKey;

    }
}
