package com.puzzles;

public class GetStringDigitsSum {

	public static void main(String[] args) {

		String input = " a rahul13sumeet09sahu2";
		int sum = 0;
		int digit = 0;

		for (Character ch : input.toCharArray()) {
			if (Character.isDigit(ch)) {
				if (digit == 0) {
					digit = Integer.parseInt(Character.toString(ch));
				} else {
					digit = (digit * 10) + Integer.parseInt(Character.toString(ch));
				}
			} else {
				sum = sum + digit;
				digit = 0;
			}
		}
		sum = sum + digit;
		System.out.println("Sum is : " + sum);

		getNumbers(input);
	}

	private static void getNumbers(String input) {

		String nos[] = input.split("[a-zA-Z]+");

		int sum = 0;

		System.out.println("Length ="+nos.length);
		
		for (String number : nos) {
			System.out.println("Value = "+number);
			try {
				sum += Integer.parseInt(number);
				
			} catch (NumberFormatException e) {
			}
		}
		System.out.println("Sum =" + sum);

	}

}
