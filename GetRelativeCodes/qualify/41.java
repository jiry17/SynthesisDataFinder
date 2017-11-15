package com.misc;

public class AddNumbers {
	public static void main(String[] args) {
		String s = "7 13";
		char[] arr = s.toCharArray();
		int total = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			if (Character.isDigit(arr[i])) {
				sb.append(arr[i]);
			} else {
				if (!sb.toString().isEmpty())
					total = total + Integer.parseInt(sb.toString());
				sb = new StringBuilder();
			}
		}
		if (!sb.toString().isEmpty())
			total = total + Integer.parseInt(sb.toString());
		System.out.println(total);
	}
}
