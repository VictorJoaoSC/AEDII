#include <stdio.h>

int main(){
	char teste[100];

	gets(teste);


	int i = 0;
	while(teste !="FIM"){
		gets(teste);
		fflush(stdin);

		i++;
	}


	printf("TAMANHO :%d",i);

	return 0;
}
