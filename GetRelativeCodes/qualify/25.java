/**
 * Created by admin on 06.01.2017.
 */
public abstract class FromFileGetter {
    public static Integer extractInt(String line) {
        StringBuilder result=new StringBuilder();
        for (Character c : line.toCharArray())
            if(Character.isDigit(c)) result.append(c);
        return Integer.parseInt(result.toString());
    }
}
