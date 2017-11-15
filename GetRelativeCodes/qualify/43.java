package com.mcs.easysocial;
/**
 * Created by kaushal on 10/12/16.
 */
public class EasyStringMod {

    private static final String EMPTY = "";
    private static final String NULL = "null";

    public static String intToString(int number){
        return Integer.toString(number);
    }
    public static int stringToInt(String string){
        return Integer.parseInt(string);
    }

    public static boolean isEmpty(String s) {
        return null == s || EMPTY.equals(s.trim());
    }
    public static boolean isNotEmpty(String s) {
        return !isEmpty(s);
    }

    public static boolean containsDigit(String s) {
        boolean containsDigit = false;

        if (s != null && !s.isEmpty()) {
            for (char c : s.toCharArray()) {
                if (containsDigit = Character.isDigit(c)) {
                    break;
                }
            }
        }

        return containsDigit;
    }
}
