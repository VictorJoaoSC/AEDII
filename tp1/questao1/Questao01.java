class Questao01 {

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
         MyIO.println(Palindromo(entrada[i])==true?"SIM":"NAO");
      }
   }

   private static boolean Palindromo(String str){

	   boolean palindromo = true;

	   for(int i = 0; i < str.length() & palindromo; i++){
	   	if(str.charAt(i) != str.charAt(str.length()-1-i))
			palindromo = false;
	   }

	   return palindromo;
   }
}
