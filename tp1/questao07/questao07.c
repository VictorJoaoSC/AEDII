#include <stdio.h>
#include <locale.h> 
int toFinalizar(char str[],char str2[]){
	
	int i = 0;

	while(str[i] == str2[i] && str[i] != '\0' && str2[i] != '\0'){
		i++;
	}

	if(str[i] == '\0' && str2[i] == '\0'){
		return 1;
	}
	else{
		return 0;
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

	for(i=tam;i>=0;i--){
			invertido[j]=str[i];
			j++;
	}
	i =0 ;
	for(i; i< tam && palindromo;i++){
		if(str[i]!=invertido[i])
			palindromo = 0;
	}
	  
	

	return palindromo;

}

int main(){
	
	setlocale(LC_ALL,"");
	char str[1000];
	
	
	scanf("%[^\n]s",str);
	int numLinhas = 0;
	
	while(!toFinalizar(str,"FIM")){
		setbuf(stdin, NULL);
		isPalindromo(str)==1?printf("SIM\n"):printf("NAO\n");
		scanf("%[^\n]s",str);

	}
	return 0;

}

