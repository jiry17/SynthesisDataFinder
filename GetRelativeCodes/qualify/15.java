package concepts.src.arraysexpert;
/**
 * string. 
Ex:I/P="asdf12bgt3bh5j" 
O/P=20 

I/P="iuy2hjg4jhg8" 
O/P=14 

I/P="7 13" 
O/P=20
 *
 */
public class AddNumberFromString {

	public static void main(String[] args) {
		String s = "asdf12bgt3bh5j";
		char[] arr = s.toCharArray();
		int sum = 0;
		String localDigit="";
		for (int i = 0; i < arr.length; i++) {

			if (Character.isDigit(arr[i])) {
				localDigit=localDigit+arr[i];

			}
			else
			{
				if(localDigit!="")
				{
					sum = sum + Integer.parseInt(localDigit);
				}
				localDigit="";
			}
		}
		System.out.println(sum);

	}

}
