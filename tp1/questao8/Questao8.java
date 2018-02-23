import java.io.*;
import java.net.*;

class Questao8 {

   public static void main (String[] args){
      String[] entrada = new String[1000];
      String linha;
      int numEntrada = 0;
      //Leitura da entrada padrao
      do {
         entrada[numEntrada] = MyIO.readLine();
      } while (entrada[numEntrada++].equals("FIM") == false);
      numEntrada--;   //Desconsiderar ultima linha contendo a palavra FIM

      //Para cada linha de entrada, gerando uma de saida contendo o numero de letras maiusculas da entrada
      for(int i = 0; i < numEntrada -1; i+=2){
         String nomeSite  = entrada[i];
		 String endereco = entrada[i+1];
		 System.out.println(nomeSite + " : "+endereco);
		
		String html = getHtml(endereco);
		
		String teste[] = InfoDados(html);

    	 
      }
   }

	public static String[] InfoDados(String html){
		
		int[] dadosSites = new int[6];		
		
		for(int i =0 ; i < html.length(); i++){
				dadosSites=countDados(html.charAt(i),dadosSites);
		}
		
		MyIO.println(dadosSites[5]);
		
		return null;
	}

	public static int[] countDados(char l,int[] dados){
		
		switch(l){
			case 'a':
				dados[0]++;
			break;
			case 'e':
				dados[1]++;
			break;
			case 'i':
				dados[2]++;
			break;
			case 'o':
				dados[3]++;
			break;
			case 'u':
				dados[4]++;
			break;
			case 'á':
				MyIO.println(l);
				dados[5]++;
			break;		
		}

	   return dados;
	}

	public static String getHtml(String endereco){
      URL url;
      InputStream is = null;
      BufferedReader br;
      String resp = "", line;
 
      try {
         url = new URL(endereco);
         is = url.openStream();  // throws an IOException
         br = new BufferedReader(new InputStreamReader(is));
 
         while ((line = br.readLine()) != null) {
            resp += line + "\n";
         }
      } catch (MalformedURLException mue) {
         mue.printStackTrace();
      } catch (IOException ioe) {
         ioe.printStackTrace();
      }
 
      try {
         is.close();
      } catch (IOException ioe) {
          //tratar erro
      }
 
      return resp;
   }
}
