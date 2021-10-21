
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
		int state = 0;
		char c = ' ';
		while(true) {
			switch(state) {
			case 0:
				c = nextChar();
				if(Character.isLetter(c))		state = 1;
				else if(Character.isDigit(c))	state = 3;
				else if( c == '{')				state = 5;
				else if( c == '}')				state = 6;
				else if( c == '(')				state = 7;
				else if( c == ')')				state = 8;
				else if( c == ';')				state = 9;
				else if( c == '=')				state = 10;
				else if( c == '!')				state = 13;
				else if( c == '<')				state = 15;
				else if( c == '>')				state = 18;
				else if( c == '*')				state = 22;
				else if( c == '/')				state = 23;
				else if( c == '%')				state = 24;
				else if( c == '+')				state = 25;
				else if( c == '1')				state = 26;
				break;
			
			//ID's
			case 1:
				c = nextChar();
				if(Character.isLetterOrDigit(c)) 	state = 1;
				else								state = 2;
				break;
			case 2:
				retract();
				return new Token(getToken(), installID());
			//Numbers
			case 3:
				c = nextChar();
				if(Character.isDigit(c))	state = 3;
				else						state = 4;
				break;
				
			case 4:
				retract();
				return new Token(CONSTANT.NUM, numValue(c));
				
			case 5:
				return new Token(CONSTANT.LBRACKET, 0);
				
			case 6:
				return new Token(CONSTANT.RBRACKET, 0);
				
			case 7:
				return new Token(CONSTANT.LPAREN, 0);
				
			case 8:
				return new Token(CONSTANT.RPAREN, 0);
				
			case 9:
				return new Token(CONSTANT.SEMICOL, 0);
			}
		}
		
	}

	private static char nextChar() {
		index++;
		return input.charAt(index);
	}
	
	private static void retract() {
		index--;
	}
	
	private static int numValue(char c) {
		return Character.getNumericValue(c);
	}
	
	private static int getToken() {
		//TODO
		//should check if lexeme exists in symbol table, will add if it does not exist
		//return attribute
		return CONSTANT.ID;
	}
	
	private static int installID() {
		//TODO
		//finds a given lexeme and returns index of it in the symbol table
		return 0;
	}

}
