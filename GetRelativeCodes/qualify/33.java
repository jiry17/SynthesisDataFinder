package net.bemacized.haikucouch;


public class TimeUtils {

    public static int readYTTimeStamp(String timestamp) {
        int time = 0;
        String toParse = "";
        for (int i = 0; i < timestamp.toCharArray().length; i++) {
            char c = timestamp.toCharArray()[i];
            if (Character.isDigit(c)) {
                toParse += c;
                continue;
            }
            else {
                switch (c) {
                    case 'h':
                        time += Integer.parseInt(toParse) * 3600;
                        break;
                    case 'm':
                        time += Integer.parseInt(toParse) * 60;
                        break;
                    case 's':
                    default:
                        time += Integer.parseInt(toParse);
                        break;
                }
                toParse = "";
            }
        }
        if (!toParse.isEmpty()) time += Integer.parseInt(toParse);
        return time;
    }

}
