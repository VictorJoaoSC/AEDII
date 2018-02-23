class AquecimentoRecursivo {
   public static boolean isMaiuscula (char c){
      return (c >= 'A' && c <= 'Z');
   }

   private static int contarLetrasMaiusculas(String str){
       return contarLetrasMaiusculas(str,0);
   }

   public static int contarLetrasMaiusculas (String str, int count){
      int resp = 0;
    
      if(count == str.length())
        resp = 0;
     else{
         if(isMaiuscula(str.charAt(count))==true)
            resp = contarLetrasMaiusculas(str,count+1) + 1;
            else
            resp = contarLetrasMaiusculas(str,count+1);
     }
     return resp;

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
         MyIO.println(contarLetrasMaiusculas(entrada[i]));
      }
   }
}
