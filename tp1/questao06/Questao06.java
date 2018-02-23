class Questao06 {

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
	      MyIO.println(Infos(entrada[i]));
      }
   }

   private static String Infos(String str){
   	
	   str = ToLowerCase(str);

	   String out = "";

	   out += (IsVogal(str)?"SIM":"NAO")+" ";
	   out += (IsConsoante(str)?"SIM":"NAO")+" ";
	   out += (IsInteger(str)?"SIM":"NAO")+" ";
	   out += (IsReal(str)?"SIM":"NAO");

	   return out;
   }


   private static boolean IsVogal(String str){
   	   boolean isVogal = true;
		

	   for(int i = 0; i < str.length() && isVogal;i++){
	   	if(str.charAt(i)!='a' && str.charAt(i)!='e' && str.charAt(i)!='i' && str.charAt(i)!='o' && str.charAt(i)!='u')
           		isVogal = false;
	   }

       return isVogal;
   
   }

   private static boolean IsConsoante(String str){
   	   boolean isConsoante = true;
		

	   for(int i = 0; i < str.length() && isConsoante;i++){
	   	if(str.charAt(i) == 'a' || str.charAt(i) =='e' || str.charAt(i) =='i' || str.charAt(i) =='o' || str.charAt(i) =='u' || str.charAt(i)<97 || str.charAt(i) > 122)
           		isConsoante = false;
	   }

       return isConsoante;
   }

   private static boolean IsInteger(String str){
   	
	boolean integer = true;

	for(int i = 0; i < str.length() && integer ; i++){
		if(str.charAt(i) != '0' && str.charAt(i) != '1' && str.charAt(i) != '2'  && str.charAt(i) !='3' && str.charAt(i) !='4' && str.charAt(i) !='5' && str.charAt(i) !='6' && str.charAt(i) !='7' && str.charAt(i) !='8' && str.charAt(i) !='9'){
			integer = false;
		}
			
	}
	
	return integer;	
   }

   private static boolean IsReal(String str){
   	
	   boolean conti = true;
	   int count = 0;

	   for(int i = 0; i < str.length() && conti && count < 2; i++){
		 if(str.charAt(i)==',' || str.charAt(i)=='.'){
				 count++;
			}
		else{
			 if(str.charAt(i) != '0' && str.charAt(i) != '1' && str.charAt(i) != '2'  && str.charAt(i) !='3' && str.charAt(i) !='4' && str.charAt(i) !='5' && str.charAt(i) !='6' && str.charAt(i) !='7' && str.charAt(i) !='8' && str.charAt(i) !='9'){
				 conti = false;
			 }
				
		}
	   }

   	return conti && (count==1);
   }

   private static String ToLowerCase(String str){
  
	   String lowerCase = "";

	   for(int i = 0; i < str.length(); i++){
	   	if(str.charAt(i)>=65 && str.charAt(i)<=90){
			lowerCase+=( (char)(str.charAt(i)+32) );
		}
		else{
			lowerCase+=str.charAt(i);
		}
	   }


	   return lowerCase;
  	
   }

}
