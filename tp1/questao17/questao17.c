#include <stdio.h>


int Isfinalizar(char str[],int i){
    char para[]="FIM\n";

	int finalizar = 1;

    if(str[i] == para[i] && str[i]!='\0' && para[i]!='\0')
        finalizar = Isfinalizar(str,i+1);
    
    if(str[i]==para[i] && str[i]=='\0' && para[i]=='\0')
        finalizar = 0;
    return finalizar;
}

int tamanhoString(char str[], int i){
	int tam = 0;

    if(str[i]!='\n')
        tam = 1 + tamanhoString(str,i+1);
    return tam;

}

int IsPalindromo(char str[], int i , int tam){
    int palindromo = 1;

    if(i < tam){
        if(str[i]==str[tam-1-i]){
            palindromo = IsPalindromo(str,i+1,tam);
        }
        else{
            palindromo = 0;
        }
    }

    return palindromo;
}


int main(){

    char str[400];
	
    fgets(str,400,stdin);
    // int aux = tamanhoString(str,0);
    // printf("%d",IsPalindromo(str,0,tam));

    while(Isfinalizar(str,0)){
        int tam = tamanhoString(str,0);
        IsPalindromo(str,0,tam)==1?printf("SIM\n"):printf("NAO\n");
        fgets(str,400,stdin);    
    }



    return 1;
}