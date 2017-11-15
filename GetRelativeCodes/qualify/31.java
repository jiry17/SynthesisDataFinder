package SharedByBhanuSir;

public class sumofnumberinagivenstring 
{
	public static void main(String[] args) 
	{
		String name = "page 14 of 99";
		char a[]=name.toCharArray();
		int sum =0;
		for(char b : a)
		{
		if(Character.isDigit(b))
			{
				
				int v=Integer.parseInt(""+b);
				//int v=Character.getNumericValue(b);
				sum=sum+v;
			}
		}System.out.println(sum);
		
	}

}
