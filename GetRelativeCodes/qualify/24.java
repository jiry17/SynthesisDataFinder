package sasi1;
import java.util.Scanner;
public class hunter43 {
public static void main(String[] args) {
	System.out.println("Enter the String:");
	Scanner s=new Scanner(System.in);
	String a=s.nextLine();
	char b[]=a.toCharArray();
	int d=0,e=0,l=0,g=0;
	char f='\0';
	String h1="";
	for(int i=0;i<a.length();i++){
	if(Character.isDigit(b[i])){ 
       	String z=Character.toString(a.charAt(i));
       	d=Integer.parseInt(z);
       	e=d;
       	if(i!=a.length()-1){
       	if(Character.isDigit(b[i+1])){
       		String z1=Character.toString(a.charAt(i+1));
           	d=Integer.parseInt(z1);
       		e=e*10+d;
       		++i;
       		if(i!=a.length()-1){
       		if(Character.isDigit(b[i+1])){
       			System.out.println("Enter the number from 1 to 99");
       			++g;
       			break;
       		}}}}
        ++l;
	}
	else{
		f=b[i];
	}
	
	if(l==1&&g==0){
		for(int j=1;j<=e;j++){
			h1=h1.concat(Character.toString(f));
			//System.out.print(f);
		}
		l=0;
		f='\0';
		d=0;
	}	}
	if(g==0)
	System.out.println(h1);
}
}
