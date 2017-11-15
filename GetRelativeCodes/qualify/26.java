package codekata.pac;

import java.util.Scanner;

public class digit 
{
	public static void main(String ar[])
	{
		Scanner sc=new Scanner(System.in);
		String s=sc.next();
		char ch[]=s.toCharArray();
		for(int i=0;i<ch.length;i++)
		{
			if(i!=ch.length-1)
			{
			if(Character.isDigit(ch[i]))
			{
				if(Character.isDigit(ch[i+1]))
				{
					int n=Integer.parseInt(String.valueOf(ch[i])+String.valueOf(ch[i+1]));
				    System.out.println(n);
					while(n>0)
					{
						System.out.print(ch[i-1]);
						n--;
					}
				}
				else
				{
					int n=Integer.parseInt(String.valueOf(ch[i]));
					while(n>0)
					{
						System.out.print(ch[i-1]);
						n--;
					}

				}
			}
			}
			if(i==ch.length-1)
			{
				if(Character.isDigit(ch[i]))
				{
					int n=Integer.parseInt(String.valueOf(ch[i]));
					while(n>0)
					{
						System.out.print(ch[i-1]);
						n--;
					}

				}
			}
		}
	}

}
