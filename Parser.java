
public class Parser {
	
	private static String input = "a = b;  if{a>=c}(b+c)else(a*b/3)"; //should replace with input file
	private static int index = -1;

	public static void main(String[] args) {
		Token t = new Token();
		
		while(index < input.length() -1) {
			t = nextToken();
			
			System.out.printf("%6d %6d\n", t.getToken(), t.getAttribute());
		}

	}

	private static Token nextToken() {
		
		return null;
	}

}
