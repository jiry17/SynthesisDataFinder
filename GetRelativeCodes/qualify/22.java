
public class SquareDigit {
	public static void main(String[] args) {
		SquareDigit sd = new SquareDigit();
		
		System.out.print(sd.squareDigits(5));
	}

	public int squareDigits(int n) {
		String m = String.valueOf(n);

		char[] digs = m.toCharArray();
		m="";
		int dig[] = new int[digs.length];

		for(int x = 0; x < digs.length; x++) {
			dig[x] = Integer.parseInt(Character.toString(digs[x]));
			dig[x] *= dig[x];
			m+= String.valueOf(dig[x]);
		}

		return Integer.parseInt(m);

		/*while(n > 9)
			n /= 10;

		return n;*/
	}
}