package squaredigitchains;

public class SquareDigitChains {

	public SquareDigitChains() {
		
		int counter = 0;
		for (int i = 1; i < 10000000; i++) {
			
			char[] letters = Integer.toString(i).toCharArray();
			
			if(i % 1000000 == 0) {
				System.out.println("i: " + i);
				System.out.println("counter: " + counter);
			}
			
			int sum = 0;
			
			do {
				sum = 0;
				
				for (int j = 0; j < letters.length; j++) {
					sum += Math.pow(Integer.parseInt(Character.toString(letters[j])), 2) ;
				}
				
				letters = Integer.toString(sum).toCharArray();
				
				if(sum == 89)
					counter++;
				
			} while (!(sum == 1 || sum == 89));
			
		}
		
		System.out.println(counter);
	}

	public static void main(String[] args) {
		new SquareDigitChains();

	}

}
