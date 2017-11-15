package mytec.framework.utils;

/**
 * 用来将字符串转为数字
 * @author 沈名山
 *
 */
public final class String2Integer {
	/**
	 * 输入字符串转为正整数
	 * 
	 * @param input	要转换的字符串
	 * @return	如果字符串可以转换为正整数则返回转换后的正整数,否则返回0
	 */
	public static Integer str2zzs(String input){
		for (char c : input.toCharArray()) {
			if (!Character.isDigit(c)) {
				return new Integer(0);
			}
		}
		return Integer.parseInt(input);
	}
}
