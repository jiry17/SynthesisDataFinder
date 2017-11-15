package sqrtchiselvstroke;

public class Sqrtchiselvstroke {
    public static void main(String[] args) {
        String s = "Hello, 21! 8 Buy 16!!!";
        char text[] = s.toCharArray();
        String number = "";
        int result, res = 0;
        for(int i = 0; i<text.length; i++){
            if(Character.isDigit(text[i])){
               while(i<s.length() && Character.isDigit(text[i])){
                   number += text[i];
                   i++;
               }
               result = Integer.parseInt(number);
               res = result * result;
               System.out.println(res);
               number = "";
            }
        }
    }
}
