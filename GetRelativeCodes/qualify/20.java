
public class demopoo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String str="WK17H019";
//		 
		 
//		 //System.out.println(s2);
//		 int i=Integer.parseInt(str);
//		 i++;
//		 System.out.println(str);
//		 
//		 char arr[]=str.toCharArray();
		
//		int p=Integer.parseInt(str.substring(3,4));
//		 System.out.println(p);
		String s = "Adnadb1123adslknkn*()(";
		for(int i=0;i<s.length();i++)
		{
		   if(Character.isDigit(s.charAt(i)))
		   System.out.print(s.charAt(i)+"  ");
		}
	}

}
