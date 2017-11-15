package com.sam.demo.utility;

public class FindDigitInString {

	public static void main(String[] args) {
		String str = "sachin123mutkure45";
		char ch[] = str.toCharArray();
		String digit="";
		int sum = 0;
		
		for(int i=0;i<ch.length;i++){
			if(Character.isDigit(ch[i])){
				digit = digit + ch[i];
				sum = sum + Character.getNumericValue(ch[i]);
				//sum = sum + Integer.parseInt(String.valueOf(ch[i]));
			}
		}
		System.out.println("Digits: "+digit +" : Sum Of Digits"+sum);
	}
}
