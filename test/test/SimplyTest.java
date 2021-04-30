package test;

public class SimplyTest {

	public static void main(String[] args) {
		String input = "0 test prova";
		String daTrasformare ="";
		for(int i=0; i<input.length();i++) {
			char c =input.charAt(i);
			if(c=='0') {
				daTrasformare = daTrasformare + c;
				System.out.println("-" + daTrasformare);
			}
		}
	}

}
