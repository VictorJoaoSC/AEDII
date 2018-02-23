class PalindromoRecursivo {

   
   private static boolean IsPalindormo(String str){
       return IsPalindormo(str,0);
   }
   
   private static boolean IsPalindormo(String str, int count){
       boolean resp = true;
       
       if(str.charAt(count)!= str.charAt(str.length()-count-1)){
           resp = false;
       }
        
        else{
            if(count < str.length()-1)
                resp = IsPalindormo(str,count+1);
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
         MyIO.println(IsPalindormo(entrada[i])==true?"SIM":"NAO");
      }
   }
}
