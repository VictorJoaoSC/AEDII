class CiframentoDeCesarRecursivo {

   
   private static String CiframentoDeCesarRecursivo(String str){
       return CiframentoDeCesarRecursivo(str,0);
   }
   
   private static String CiframentoDeCesarRecursivo(String str, int count){
       
       String messagemCifrada = "";

       if(count < str.length())
            messagemCifrada = (char)(str.charAt(count)+3) + CiframentoDeCesarRecursivo(str,count+1);

       return messagemCifrada;

   }
   
   
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
         MyIO.println(CiframentoDeCesarRecursivo(entrada[i]));
      }
   }
}
