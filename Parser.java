/*
 * Parser.java
 * Project 1 COSC 424
 * @author Neil Yoder, Jacob Neulight, Elliot Cole
 * 
 * Parses the input data from a file and prints out the token value and index/attribute data
 * 
 */
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Parser {
	private static String input = "";
	private static int index = -1;
	//creates the symbol table
	private static LinkedHashMap<String, Integer> symbolTable;

	public static void main(String[] args) {
		symbolTable = new LinkedHashMap<>();
		Token t = new Token();

		//inserts keywords into the symbol table
		symbolTable.put("if", CONSTANT.IF);
		symbolTable.put("else", CONSTANT.ELSE);
		symbolTable.put("double", CONSTANT.DOUBLE);
		symbolTable.put("char", CONSTANT.CHAR);
		symbolTable.put("int", CONSTANT.INT);
		symbolTable.put("while", CONSTANT.WHILE);

		//cycle through a given input file, be sure to change path to your local machine!!!
		File file = new File("C:\\Users\\Neil Yoder\\eclipse-workspace\\cosc424proj1\\src\\input.txt");
		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()){
				input += sc.nextLine();
			}
			
			input = input.trim();
			
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
		char c = ' '; //this is the current character
		String lexeme = ""; //this keeps track of longer Id's, keywords, and numbers
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
				lexeme += c;
				
				if(Character.isDigit(c))	state = 3;
				else						state = 4;
				break;
				
			case 4:
				c = retract();
				lexeme = lexeme.substring(0,lexeme.length()-1);
				
				return new Token(CONSTANT.NUM, numValue(lexeme));
				
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
		//moves counter forward to next character
		index++;
		return input.charAt(index);
	}
	
	private static char retract() {
		//retracts the counter back as to not include extra chars
		index--;
		return input.charAt(index);
	}
	
	private static int numValue(String lexeme) {
		//returns integer value of the number found
		return Integer.parseInt(lexeme.trim());
	}
	
	private static int getToken(String lexeme) {
		Integer attribute = symbolTable.get(lexeme.trim());
		//checks if lexeme is in symbol table
		if(attribute != null) {
			return attribute;
		}

		//adds lexeme if not already in symbol table
		symbolTable.put(lexeme.trim(), CONSTANT.ID);
		return CONSTANT.ID;
	}
	
	private static int installID(String lexeme) {
		//This will cycle through the symbol table to find the correct index
		List<String> keys = new ArrayList<String>(symbolTable.keySet());
		for (int i = 0; i < keys.size(); i++) {
			String obj = keys.get(i);
			if(obj.equals(lexeme.trim()))
				//returns index
				return i;
		}
		
		//This indicates an error
		return -1;
	}

}
