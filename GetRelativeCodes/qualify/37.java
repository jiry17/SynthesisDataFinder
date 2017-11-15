package mm;
	
import java.io.*;


public class Main {


	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num1 = Integer.parseInt(br.readLine());
		int num2 = Integer.parseInt(br.readLine());
		int num3 = Integer.parseInt(br.readLine()),count=0;
		char[] result = String.valueOf(num1*num2*num3).toCharArray();

		
		for( int i=0;i<10;i++){
			for(int j=0;j<result.length;j++){
				if(result[j] == Character.forDigit(i,10)) count++;
				}
			System.out.println(count);
			count=0;
		}
		
	}
}