#include <stdio.h>
#include <locale.h> 
int toFinalizar(char str[]){

	char para[]="FIM\n";

	int i =0;

	int finalizar = 1;

	while(str[i] == para[i] && str[i]!='\0' && para[i]!='\0'){
		i++;
	}

	if(str[i]==para[i] && str[i]=='\0' && para[i]=='\0')
		finalizar = 0;

	return finalizar;
}

int tamanhoString(char str[]){
	int tam = 0;

	while(str[tam] !='\n'){
		tam++;
	}
	return tam;


}

int isPalindromo(char str[]){
	int tam = tamanhoString(str);
	int i =0;
	int j = 0;
	int palindromo = 1;
	
	char invertido[1000];

	for(i; i< tam && palindromo;i++){
		if(str[i]!=str[tam-1-i]){
			palindromo = 0;
			
		}
			
	}

	return palindromo;

}

int main(){
	
	setlocale(LC_ALL,"");
	char str[400];
	
	
	fgets(str,400,stdin);

	while(toFinalizar(str)){
		isPalindromo(str)==1?printf("SIM\n"):printf("NAO\n");
		fgets(str,400,stdin);	
	}

	return 1;

}

