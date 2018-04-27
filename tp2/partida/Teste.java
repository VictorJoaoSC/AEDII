class Teste{
    public static void main(String[] args){

        String a = "<html lang=\"pt-BR\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=windows-1252\">";

        String vetor[];

        vetor = a.split(";");

        MyIO.println(vetor[1]);

        vetor = vetor[1].split("=");

        String charset = vetor[1].replace("\">","");

        MyIO.println(charset);
        
        // for(int i = 0; i < vetor.length; i++){
        //     String tmp="";
        //     for(int j = 0; j < vetor[i].length(); j++){
        //         if(vetor[i].charAt(j)> 47 && vetor[i].charAt(j)< 58)
        //             tmp+=vetor[i].charAt(j);
        //     }
        //     vetor[i] = tmp;
        // }

        

        // MyIO.println(getHtmlGrupo(a));


    }
    private static String getHtmlGrupo(String str){
        String grupo = "";
        String vetor[] = str.split(">");

        for(int i = 0; i < vetor[2].length() && vetor[2].charAt(i)!='<';i++){
            grupo+=vetor[2].charAt(i);
        }

        return grupo;
    } 
}