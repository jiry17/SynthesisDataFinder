import java.io.*;
import java.util.*;
public class count
{
public static void main(String args[])
{
int i,j;
String s="a1b6";
int n=s.length();
char c[]=s.toCharArray();
for(i=0;i<n;i++)
	{
	if(Character.isDigit(c[i]))
	{
	//int v=c[i];	
String ss=String.valueOf(c[i]);
	int v=Integer.parseInt(ss);
	System.out.print(v);				
	for(j=1;j<=v;j++)
	{
		
		System.out.print(c[i-1]);
	}
	}
	}
}
}


