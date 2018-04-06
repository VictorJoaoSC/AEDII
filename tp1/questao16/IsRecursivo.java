class IsRecursivo {

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
	      MyIO.println(infos(entrada[i]));
      }
   }

   private static String infos(String str){
   	
	   str = toLowerCase(str);

	   String out = "";

	   out += (isVogal(str)?"SIM":"NAO")+" ";
	   out += (isConsoante(str)?"SIM":"NAO")+" ";
	   out += (isInteger(str)?"SIM":"NAO")+" ";
	   out += (isReal(str)?"SIM":"NAO");

	   return out;
   }

	private static boolean isVogal(String str){
		return isVogal(str,0);
	}
	//ESSE METODO VERIFICA SE UMA STRING É COMPOSTO POR APENAS VOGAIS
   private static boolean isVogal(String str,int i){
   	   boolean vogal = true;

		if(i < str.length()){
			if(isVogal(str.charAt(i)))
				vogal = isVogal(str,i+1);
			else
				vogal = false;
		}

		return vogal;	
   }
	//ESSE METODO VERIFICA SE UM CHAR É UM VOGAL
    private static boolean isVogal(char c){
	   boolean vogal = true;
		if(c!='a' && c!='e' && c!='i' && c!='o' && c!='u')
           		vogal = false;
		return vogal;
   	}

   private static boolean isConsoante(String str){
	   return isConsoante(str,0);
   }

	 //ESSE METODO VERIFCA SE UMA STRING É COMPOSTA POR APENAS CONSOANTES
   private static boolean isConsoante(String str,int i){
   	   boolean consoante = true;
		
		if(i < str.length()){
			if(isLetra(str.charAt(i))==false || !isVogal(str.charAt(i))==false)
           		consoante = false;
			else
				consoante = isConsoante(str,i+1);
		}

       return consoante;
   }
 //ESSE METODO VERIFCA SE UM STRING É COMPOSTA APENAS POR NUMEROS INTEIROS
   private static boolean isInteger(String str,int i){
   	
	boolean integer = true;

	if(i < str.length()){
		if(isInteger(str.charAt(i))){
			integer = isInteger(str,i+1);
		}
		else
			integer = false;
			
	}
	return integer;	
   }
	//ESSE METODO VERIFICA SE UM CHAR É COMPOSTO POR APENAS LETRAS
    private static boolean isLetra(char c){
	   boolean letra = true;

	   if(c < 'a' || c > 'z')
	   		letra = false;
		return letra;
   }

   private static boolean isInteger(String str){
	   return isInteger(str,0);
   }
	//ESSE MÉTODO VERIFICA SE UM CHAR É COMPOSTO POR UM NUMERO ATRAVES DO CODIGO ASCII
    private static boolean isInteger(char c){
	   boolean integer = true;
	   if(c<47 || c>58){
			integer = false;
		}
		return integer;
   }
    //ESSE METODO VERIFIA A STRING É COMPOSTA APENAS POR UM NÚMERO REAL
   private static boolean isReal(String str,int i,int count){
	   boolean resp = true;
	   int real = 0;

	   if(i < str.length()){
		    if(str.charAt(i)==',' || str.charAt(i)=='.'){
				 real++;
			}
		else{
			 if(isInteger(str.charAt(i)) && real<2){
				 resp = isReal(str,i+1,real);
			 }
			 else
			 	resp = false;
	    }
   	   }
		return (resp) && real==1;
   }

   private static boolean isReal(String str){
	   return isReal(str,0,0);
   }
   //ESSE METODO CONVERTE N CARACETERES IGUAIS A MAISCULO DA STRING PARA MINUSCULO ATRAVES DE CÓDIGO ASCII DE FORMA RECURSIVA
   private static String toLowerCase(String str,int i){
	   String resp="";

       if(i<str.length()){
           if(str.charAt(i)>=65 && str.charAt(i)<=90)
                resp += (char)(str.charAt(i)+32) + toLowerCase(str,i+1);
            else
				resp += str.charAt(i) + toLowerCase(str,i+1);
       }
       return resp;
  	
   }
   private static String toLowerCase(String str){
       return toLowerCase(str,0);
   }

}
