import java.util.Scanner;


public class finddigits {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		 int count = Integer.parseInt(in.next());
	        for (int x=0;x<count;x++) {
	            System.out.println(getDigits(in.next()));
	        }

	}

	private static int getDigits(String string) {
		int givenNum = Integer.parseInt(string);
		int count = 0;
		
		for (Character digit : string.toCharArray()) {
			if (digit!='0' && givenNum % Character.getNumericValue(digit) ==0 ) {
				count++;
			}
		}
		
		return count;
	}

}
