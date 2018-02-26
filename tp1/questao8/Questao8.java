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
		 
		
		String html = getHtml(endereco);
		
		int[] dadosSites = InfoDados(html);

		MyIO.println("a("+dadosSites[0]+") e("+dadosSites[1]+") i("+dadosSites[2]+") o("+dadosSites[3]+") u("+dadosSites[4]+") á("+dadosSites[5]+") é("+dadosSites[6]+") í("+dadosSites[7]+") ó("+dadosSites[8]+") ú("+dadosSites[9]+") à("+dadosSites[10]+") è("+dadosSites[11]+") ì("+dadosSites[12]+") ò("+dadosSites[13]+") ù("+dadosSites[14]+") ã("+dadosSites[15]+") õ("+dadosSites[16]+") â("+dadosSites[17]+") ê("+dadosSites[18]+") î("+dadosSites[19]+") ô("+dadosSites[20]+") û("+dadosSites[21]+") consoante("+dadosSites[22]+") <br>("+dadosSites[23]+") <table>("+dadosSites[24]+") "+nomeSite); 

	  }
   }

	public static int[] InfoDados(String html){
		
		int[] dadosSites = new int[25];		
		

		for(int i =0 ; i < html.length(); i++){
				if(html.charAt(i)=='<'){
					int resp =Infotags(html,i);
						if(resp == 0){
							dadosSites[23]++;
							i+=3;
						}	
						else
							if(resp == 1){
								dadosSites[24]++;
								i+=6;
							}
				}
					dadosSites=countDados(html.charAt(i),dadosSites);
		}
		
		return dadosSites;
	}

	private static int Infotags(String html,int pos){

		String padrao1 = "<br>";
		String padrao2 = "<table>";
		
		int savePos = pos;

		int resp = 0;

		boolean conti =true;

		for(int i = 0; i < 4 && conti; i++){
			if(html.charAt(pos)!= padrao1.charAt(i))
				conti = false;
			pos++;
		}

		if(!conti){
			conti = true;
			pos = savePos;

			for(int i = 0; i < 7 && conti; i++){
				if(html.charAt(pos)!= padrao2.charAt(i))
				   conti = false;
			    pos++;
			}

			if(conti)
				resp = 1;
			else
				resp = -1;
		}
		
		return resp;

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
				dados[5]++;
			break;
			case 'é':
				dados[6]++;
			break;
			case 'í':
				dados[7]++;
			break;
			case 'ó':
				dados[8]++;
			break;
			case 'ú':
				dados[9]++;
			break;
			case 'à':
				dados[10]++;
			break;
			case 'è':
				dados[11]++;
			break;
			case 'ì':
				dados[12]++;
			break;
			case 'ò':
				dados[13]++;
			break;
			case 'ù':
				dados[14]++;
				break;
			case 'ã':
				dados[15]++;
			break;
			case 'õ':
				dados[16]++;
			break;
			case 'â':
				dados[17]++;
			break;
			case 'ê':
				dados[18]++;
			break;
			case 'î':
				dados[19]++;
			break;
			case 'ô':
				dados[20]++;
			break;
			case 'û':
				dados[21]++;
			break;
			case '<':
			default:
				if(l >= 97 && l<=122){
					dados[22]++;
				}
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
