import java.io.*;
import java.util.*;
class alphanumber
{
public static void main(String args[])
{
String s;
Scanner scr=new Scanner(System.in);
s=scr.next();
int l=s.length();
char c[]=s.toCharArray();
for(int i=0;i<l;i++)
{
int j=i+1;
int k=j+1;
if((j<l)&&(k<l))
{
if((Character.isDigit(c[j]))&&(Character.isDigit(c[k])))
{
String st=String.valueOf(c[j])+String.valueOf(c[k]);
int r=Integer.parseInt(st);
for(int m=0;m<r;m++)
{
System.out.print(c[i]);
}
}
else if(Character.isDigit(c[j]))
{
String str=String.valueOf(c[j]);
int rr=Integer.parseInt(str);
for(int m=0;m<rr;m++)
{
System.out.print(c[i]);
}
}
}
}
}
}
