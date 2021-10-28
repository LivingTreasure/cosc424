import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

public class Parser {
	private static String input = "";
	private static int index = -1;
	private static LinkedHashMap<String, Integer> symbolTable;

	public static void main(String[] args) {
		symbolTable = new LinkedHashMap<>();
		Token t = new Token();

		symbolTable.put("if", CONSTANT.IF);
		symbolTable.put("else", CONSTANT.ELSE);
		symbolTable.put("double", CONSTANT.DOUBLE);
		symbolTable.put("char", CONSTANT.CHAR);
		symbolTable.put("int", CONSTANT.INT);
		symbolTable.put("while", CONSTANT.WHILE);

		File file = new File("C:\\Users\\jrneu\\Documents\\Code\\cosc424\\input.txt");
		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()){
				input += sc.nextLine();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		while(index < input.length() -1) {
			t = nextToken();
			
			System.out.printf("%6d %6d\n", t.getToken(), t.getAttribute());
		}
	}

	private static Token nextToken() {
		int state = 0;
		char c = ' ';
		String lexeme = "";
		char x = ' ';
		while(true) {
			switch(state) {
			case 0:
				c = nextChar();
				
				lexeme += c;
				
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
				else if( c == '*')				state = 21;
				else if( c == '/')				state = 22;
				else if( c == '%')				state = 23;
				else if( c == '+')				state = 24;
				else if( c == '-')				state = 25;
				break;
			
			//ID's
			case 1:
				c = nextChar();
				
				lexeme += c;

				if(Character.isLetterOrDigit(c)) 	state = 1;
				else								state = 2;
				break;
			case 2:
				c = retract();
				lexeme = lexeme.substring(0,lexeme.length()-1);
				
				return new Token(getToken(lexeme), installID(lexeme));
			//Numbers
			case 3:
				c = nextChar();
				if(Character.isDigit(c))	state = 3;
				else						state = 4;
				break;
				
			case 4:
				c = retract();
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
			
			// if c is =
			case 10:
				c = nextChar();
				if( c == '=') 	state = 12;
				else			state = 11;
				break;
			case 11:
				c = retract();
				return new Token(CONSTANT.ASSIGNOP, CONSTANT.EQUAL);
				
			case 12:
				return new Token(CONSTANT.COMPAREOP, CONSTANT.EQUALEQ);
			
			//c = !
			case 13:
				c = nextChar();
				if( c == '=') 	state = 14;
				break;
			case 14:
				return new Token(CONSTANT.COMPAREOP, CONSTANT.NOTEQUAL);
			
			//c = <
			case 15: 
				c = nextChar();
				if( c == '=') 	state = 17;
				else			state = 16;
				break;
				
			case 16:
				c = retract();
				return new Token(CONSTANT.COMPAREOP, CONSTANT.LSTHAN);
				
			case 17:
				return new Token(CONSTANT.COMPAREOP, CONSTANT.LSTHANEQ);
				
			//c = >
			case 18: 
				c = nextChar();
				if( c == '=') 	state = 20;
				else			state = 19;
				break;
					
			case 19:
				c = retract();
				return new Token(CONSTANT.COMPAREOP, CONSTANT.GRTHAN);
					
			case 20:
				return new Token(CONSTANT.COMPAREOP, CONSTANT.GRTHANEQ);
				
			case 21:
				return new Token(CONSTANT.ARITHOP, CONSTANT.MULT);
				
			case 22:
				return new Token(CONSTANT.ARITHOP, CONSTANT.DIV);
				
			case 23:
				return new Token(CONSTANT.ARITHOP, CONSTANT.MOD);
				
			case 24:
				return new Token(CONSTANT.ARITHOP, CONSTANT.ADD);
				
			case 25:
				return new Token(CONSTANT.ARITHOP, CONSTANT.SUB);
			}
		}
		
	}

	private static char nextChar() {
		index++;
		return input.charAt(index);
	}
	
	private static char retract() {
		index--;
		return input.charAt(index);
	}
	
	private static int numValue(char c) {
		return Character.getNumericValue(c);
	}
	
	private static int getToken(String lexeme) {
		Integer attribute = symbolTable.get(lexeme.trim());
		if(attribute != null) {
			return attribute;
		}


		symbolTable.put(lexeme, CONSTANT.ID);
		return CONSTANT.ID;
	}
	
	private static int installID(String lexeme) {
		List<String> keys = new ArrayList<String>(symbolTable.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String obj = keys.get(i);
			if(obj.equals(lexeme.trim()))
				return i;
		}
		
		return -1;
	}

}
