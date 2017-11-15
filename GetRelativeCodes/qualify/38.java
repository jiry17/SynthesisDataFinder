package examples;

public class CharArrayExample {

	public static void main(String[] args) {

		String s = "a";
		try {
			int value = Integer.parseInt(s);
			System.out.println(value);
		} catch(NumberFormatException nfe) {
			
			
			System.out.println("cannot be parsed into integer");
		}

		String digits = "123456";
		digits.charAt(3);

		//		char ch = 'Z';
		//		int value = Character.getNumericValue(ch);
		//		System.out.println(value);

		char[] digitArray = digits.toCharArray();
		//		System.out.println(digitArray[3]);

		//		for(char c: digitArray) {
		//			System.out.println(c);
		//		}

	}

}
