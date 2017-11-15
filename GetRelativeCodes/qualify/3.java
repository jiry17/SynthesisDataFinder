package com.naga.algorithms;

public class SumofNumbersInString
{

	public static void main(String[] args)
	{
		String s  = "1ab23c4d67";
		String temp = "";
		int sum = 0;
		
		for(char ch: s.toCharArray())
		{
			if(Character.isDigit(ch))
			{
				temp = temp+ch;
				//System.out.println(temp);
			}
			else{
				if(!temp.equals(""))
				sum  = sum + Integer.parseInt(temp);
				temp = "";
			}
		}
		if(!temp.equals(""))
			sum  = sum + Integer.parseInt(temp);
		
		System.out.println(sum);

	}

}
