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
   //METODO RESPONSAVEL POR CHAMAR OS METODOS DE VERIFICAÇÃO DE INFORMAÇÕES DA STRING E RETORNA OS RESULTADOS
   private static String Infos(String str){
   	
	   str = ToLowerCase(str);

	   String out = "";

	   out += (IsVogal(str)?"SIM":"NAO")+" ";
	   out += (IsConsoante(str)?"SIM":"NAO")+" ";
	   out += (IsInteger(str)?"SIM":"NAO")+" ";
	   out += (IsReal(str)?"SIM":"NAO");

	   return out;
   }

	//ESSE METODO VERIFICA SE UMA STRING É COMPOSTO POR APENAS VOGAIS
   private static boolean IsVogal(String str){
   	   boolean isVogal = true;
		

	   for(int i = 0; i < str.length() && isVogal;i++){
		   isVogal = IsVogal(str.charAt(i));
	   }

       return isVogal;
   
   }
  //ESSE METODO VERIFICA SE UM CHAR É UM VOGAL
   private static boolean IsVogal(char c){
	   boolean isVogal = true;
		if(c!='a' && c!='e' && c!='i' && c!='o' && c!='u')
           		isVogal = false;
		return isVogal;
   }
   //ESSE METODO VERIFCA SE UMA STRING É COMPOSTA POR APENAS CONSOANTES
   private static boolean IsConsoante(String str){
   	   boolean isConsoante = true;
		

	   for(int i = 0; i < str.length() && isConsoante;i++){
		   if(!IsLetra(str.charAt(i)) || !IsVogal(str.charAt(i)))
		   		isConsoante = false;
	   }

       return isConsoante;
   }
  //ESSE METODO VERIFICA SE UM CHAR É COMPOSTO POR APENAS LETRAS
   private static boolean IsLetra(char c){
	   boolean isLetra = true;

	   if(c < 'a' || c > 'z')
	   		isLetra = false;
		return isLetra;
   }
  //ESSE METODO VERIFCA SE UM STRING É COMPOSTA APENAS POR NUMEROS INTEIROS
   private static boolean IsInteger(String str){
   	
	boolean integer = true;

	for(int i = 0; i < str.length() && integer ; i++){
		integer = IsInteger(str.charAt(i));
			
	}
	
	return integer;	
   }

   //ESSE MÉTODO VERIFICA SE UM CHAR É COMPOSTO POR UM NUMERO ATRAVES DO CODIGO ASCII
   private static boolean IsInteger(char c){
	   boolean integer = true;
	   if(c<47 || c>58){
			integer = false;
		}
		return integer;
   }
   //ESSE METODO VERIFIA A STRING É COMPOSTA APENAS POR UM NÚMERO REAL
   private static boolean IsReal(String str){
   	
	   boolean numero = true;
	   int real = 0;

	   for(int i = 0; i < str.length() && numero && real < 2; i++){
		 if(str.charAt(i)==',' || str.charAt(i)=='.'){
				 real++;
			}
		else{
			 if(!IsInteger(str.charAt(i))){
				 numero = false;
			 }				
		}
	   }

   	return conti && (real==1);
   }
  //ESSE METODO CONVERTE N CARACETERES IGUAIS A MAISCULO DA STRING PARA MINUSCULO ATRAVES DE CÓDIGO ASCII
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
