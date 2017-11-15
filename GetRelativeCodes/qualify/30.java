import java.util.*;
import java.io.*;

public class p2 {

	public static void main(String[] args) {
		
		try {
			Scanner in = new Scanner(new FileReader("C:/Users/THMKG/Downloads/ECOOCS_2013/Round 1/data/DATA21.txt"));
			for (int i =0; i< 5; i++) {
				for (int b =0; b< 5; b++) {
					int finalTotal  = 0;
					String baseString = in.next();
					char[] base = baseString.toCharArray();
					for (int d = 0; d < base.length; d++) {
						//System.out.print(base[d]);
						int digit = Integer.parseInt(Character.toString(base[d]));
						//System.out.println(digit);
						if (base.length %2 == 0)  {
							if ((d+1) %2 == 0)  {
								digit *= 2;
								finalTotal += (digit%10) + (digit-digit%10)/10;
								//System.out.println((digit%10) + (digit-digit%10)/10 + " s");
							} else {
								finalTotal += digit;
								//System.out.println(digit + " d");
							}
						} else {
							if ((d+1) %2 == 0)  {
								finalTotal += digit;
								//System.out.println(digit + " d");
							} else {
								digit *= 2;
								finalTotal += (digit%10) + (digit-digit%10)/10;
								//System.out.println((digit%10) + (digit-digit%10)/10 + " s");
							}
						}
					}
					System.out.print(((10-finalTotal%10)==10)?"0":(10-finalTotal%10));
					//System.out.println();
				}
				System.out.println();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}