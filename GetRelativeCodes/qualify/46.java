package ru.geekbrains.practise.task17;

public class RleDesarchiver {
    public static void main(String[] args) {
        String s = "Т28У7Т7 2М4Н4О3Г2О3 1П1О6В5Т5О3Р2О2В2 2Т1";
        System.out.println(desarchivate(s));
    }


    public static String desarchivate(String s) {
        StringBuilder s1 = new StringBuilder();
        char[] chars = s.toCharArray();
        char temp = ' ';
        String counter = "0";
        for (int i = 0; i < chars.length; i++) {
            if (!Character.isDigit(chars[i])) {
                for (int j = 0; j < Integer.parseInt(counter); j++) {
                    s1.append(temp);
            }
                counter = "0";
                temp = chars[i];
        } else {
                counter += chars[i];
            }
        }
        for (int j = 0; j < Integer.parseInt(counter); j++) {
            s1.append(temp);
        }
        return s1.toString();
    }


}
