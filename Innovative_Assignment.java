import java.util.Scanner;
import java.util.HashMap;
public class Innovative_Assignment {
    public static void main(String [] args){
        // Scan the data
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter String : ");
        String text = sc.nextLine();
        System.out.print("Enter Pattern to be Searched : ");
        String pattern = sc.next();

        /*Asking for user preference */
        System.out.println("How you want to customize search? \n1.First Occurence \n2.All Occurence");
        System.out.print("Enter your choice: ");
        String choice = sc.nextLine();
        choice = sc.nextLine();
        /*Loop runs until correct value is entered!*/
        while(true){
            if(choice.equalsIgnoreCase("first occurence")){
                int n = text.length();
                int m = pattern.length();
                int pie_table[] = new int[pattern.length()];
                compute_pie_table(n,m, pie_table,pattern);

                /*Printing pie table */
                for(int i=0;i<pattern.length();i++){
                    System.out.print(pie_table[i]);
                }
                int index = find_first_occurence(n,m,text,pattern,pie_table);
                if(index!=-1){
                    System.out.println("\nPattern Located at "+(index+1)+" Position!");
                }
                else{
                    System.out.println("\nPattern Not Found!");
                }
                break;
            }
            else if(choice.equalsIgnoreCase("all occurence")){
                int q = 13; // any prime number
                int d = 10; // unique possible characters in string
                search_pattern(pattern, text, q, d);
                break;
            }
            else{
                System.out.println("Check Spelling Error & Continue!");
                System.out.println("How you want to customize search? \n1.First Occurence \n2.All Occurence");
                System.out.print("Enter your choice: ");
                choice = sc.nextLine();
            }
        }
        sc.close();
    }
    public static void search_pattern(String pattern, String text, int q, int d){
        int n = text.length();
        int m = pattern.length();
        int p = 0; // hash value for pattern
        int t = 0; // hash value for text
        int h = 1; // base hash value for 256 characters

        // Value of base hash function will be Math.pow(d, m-1)%q for each character in pattern
        for(int i=0;i<m-1;i++){
            h = (h*d)%q;
        }

        /*Calculate rolling hash function for pattern */
        for(int i=0;i<m;i++){
            p = (d*p + pattern.charAt(i))%q;
            t = (d*t + text.charAt(i))%q;
        }
        boolean done = false;

        /*Finding pattern in text using sliding window to pattern length */
        for(int i=0;i<=n-m;i++){
            if(p==t){
                int j;
                //check if characters are equal
                for(j=0;j<m;j++){
                    if(text.charAt(i+j)!=pattern.charAt(j)){
                        break;
                    }
                }
                if(j==m){
                    done = true;
                    System.out.println("Pattern located at "+(i+1)+" position!");
                    // break;
                }

            }
            //calculate the hash value for next window
            if(i<n-m){
                t = (d*(t-text.charAt(i)*h)+text.charAt(i+m))%q;
                if(t<0){
                    t = (t+q);
                }
            }
        }
        if(!done){
            System.out.println("Pattern doesn't exist!");
        }
    }
    public static void compute_pie_table(int n, int m, int pie_table[], String pattern){
        /*Creating pie table using mapping initial occurences using hashmap */
        pie_table[0] = 0;
        int len = 0; //length of previous longest prefix that is also a suffix
        int i=1;
        while(i<m){ 
            if(pattern.charAt(i)==pattern.charAt(len)){
                len++;
                pie_table[i] = len;
                i++;
            }
            else{
                if(len!=0){
                    len = pie_table[len-1];
                }
                else{
                    pie_table[i] = 0;
                    i++;
                }
            }
        }
    }
    public static int find_first_occurence(int n, int m, String text, String pattern, int pie_table[]){
        int i=0,j=0;
        
        while(i<n){
            if(pattern.charAt(j)==text.charAt(i)){
                i++;
                j++;
                if(j==m){
                    return i-j;
                }
            }
            else if(i<n && pattern.charAt(j)!=text.charAt(i)){
                if(j>0){
                    j = pie_table[j-1];
                }
                else{
                    i++;
                }
            }
        }
        return -1;
    }
}