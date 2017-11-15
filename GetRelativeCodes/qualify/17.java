import java.util.Scanner;

public class Numerology {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String[] input = sc.nextLine().split("\\W+");
		long sum = 0;
		int charPosition = 0;
		int day = Integer.parseInt(input[0]);
		int month = Integer.parseInt(input[1]);
		int year = Integer.parseInt(input[2]);
		String userName = input[3];
		sum = day * month * year;
		if (month % 2 != 0) {
			sum = sum * sum;
		}
		char[] name = userName.toCharArray();
		for (char c : name) {
			charPosition = (Character.toLowerCase(c) - 'a') + 1;
			if (Character.isUpperCase(c) && !Character.isDigit(c)) {
				sum += charPosition * 2;
			} else if (Character.isDigit(c)) {
				sum += Integer.parseInt(Character.toString(c));
			} else if (!Character.isDigit(c)) {
				sum += charPosition;
			}
		}
		do {
			char[] numbers = String.valueOf(sum).toCharArray();
			if (sum >= 13) {
				sum = 0;
				for (char c : numbers) {
					sum += Integer.parseInt(Character.toString(c));
				}
			}
			numbers = null;
		} while (sum >= 13);
		System.out.println(sum);
		sc.close();
	}
}
