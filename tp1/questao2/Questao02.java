class Questao02 {

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
         MyIO.println(CifraCesar(entrada[i]));
      }
   }

   private static String CifraCesar(String str){
   	String cript = "";

	   for(int i =0 ; i < str.length(); i++){
	   	cript+= (char)(str.charAt(i) + 3);
	   }
  	return cript; 
   }
}
