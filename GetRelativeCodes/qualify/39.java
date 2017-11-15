
public class lastDigFinder {

	public void findLast(String s){
		
		try {
			char[] numArray = s.toCharArray();
			int total = 0;
			for(int i = 0; i < numArray.length;i++){
				if(i % 2 == 1){
					total += Integer.parseInt(Character.toString(numArray[i]));
				}else if(i%2 == 0 || i == 0){
					int doubled = Integer.parseInt(Character.toString(numArray[i])) * 2;
					if(doubled >= 10){
						char[] array = Integer.toString(doubled).toCharArray();
						total += doubled - 9;
					}else if (doubled < 10){
						total += doubled;
					}
				}
			}
			int lastDig = 10 - (total%10);
			System.out.println("Last digit is: "+lastDig);
		} catch (Exception ex) {
			System.out.println("Unknown Error occured. Please try again");
		}
	}
	
}
