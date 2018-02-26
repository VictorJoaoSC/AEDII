#include <stdio.h>
#include <locale.h> 
int toFinalizar(char str[]){
	
	int i = 0;

	char str2[] = "FIM";

	while(str[i] == str2[i] && str[i] != '\0' && str2[i] != '\0'){
		i++;
	}

	if(str[i] == '\0' && str2[i] == '\0'){
		return 0;
	}
	else{
		return 1;
	}

}

int tamanhoString(char str[]){
	int tam = 0;

	while(str[tam] !='\0'){
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
	char str[1000];
	
	
	gets(str);
	int numLinhas = 0;

	while(toFinalizar(str)){
		isPalindromo(str)==1?printf("SIM\n"):printf("NAO\n");
		gets(str);	
	}

	return 1;

}

