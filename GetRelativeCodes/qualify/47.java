import java.util.Scanner;

public class Problem01_Enigma {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            int middle = line.replaceAll("[0-9 ]","").length()/2;

            for (char character : line.toCharArray()){
                if(Character.isDigit(character) || character == ' '){
                    System.out.print((char)character);
                }
                else{
                    System.out.print((char)(character+middle));
                }
            }
            System.out.println();
        }
    }
}
