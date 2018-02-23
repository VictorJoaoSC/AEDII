import java.util.Random;

class Questao03 {
   
   private static Random gerador = new Random();	


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
      for(int i = 0; i < numEntrada; i++){
         MyIO.println(Aleatorio(entrada[i]));
      }
   }

   private static String Aleatorio(String str){
   	char paraSubstituir = RandomChar();
	char  substituir = RandomChar();
	String novaStr = "";
	gerador.setSeed(4);

	for(int i = 0 ; i < str.length(); i++){
		if(str.charAt(i) == paraSubstituir)
			novaStr+= substituir;
		else
			novaStr+=str.charAt(i);
	}

	return novaStr;

   }

   private static char RandomChar(){
	   return ((char) ('a'+(Math.abs(gerador.nextInt())%26)));
   }	   
}
