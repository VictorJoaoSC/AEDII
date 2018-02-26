import java.util.Random;

class AleatoriaRecursiva {

    private static Random aleatorio = new Random();

   public static void main (String[] args){
      aleatorio.setSeed(4);
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
         MyIO.println(AleatoriaRecursiva(entrada[i]));
      }
   }

   private static String AleatoriaRecursiva(String str){
        char escolhida = RandomChar();
        char substituir = RandomChar();
    
        return AleatoriaRecursiva(str,0,escolhida,substituir);
   }

    private static String AleatoriaRecursiva(String str,int count,char escolhida, char substituir){

        String novaString = "";

        if(count < str.length()){
            if(str.charAt(count) == escolhida)
                novaString = substituir + AleatoriaRecursiva(str,count+1,escolhida,substituir);
            else
                novaString = str.charAt(count) + AleatoriaRecursiva(str,count+1,escolhida,substituir);
        }

        return novaString;

    }

    private static char RandomChar(){
	   return ((char) ('a'+(Math.abs(aleatorio.nextInt())%26)));
   }
}
