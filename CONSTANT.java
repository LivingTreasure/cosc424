/*
 * CONSTANT.java
 * Project 1 COSC 424
 * @author Neil Yoder, Jacob Neulight, Elliot Cole
 * 
 * Holds all of the constant values and keywords for a given language
 */
public final class CONSTANT {
	
	//Tokens
	public static final int ID 			= 100;
	public static final int NUM 		= 101;
	public static final int LBRACKET 	= 102;
	public static final int RBRACKET 	= 103;
	public static final int LPAREN 		= 104;
	public static final int RPAREN 		= 105;
	public static final int SEMICOL 	= 106;
	public static final int ASSIGNOP	= 107;
	public static final int COMPAREOP	= 108;
	public static final int ARITHOP		= 109;
	
	//ARITHOP attributes
	public static final int MULT = 201;
	public static final int DIV = 202;
	public static final int MOD = 203;
	public static final int ADD = 204;
	public static final int SUB = 205;
	
	//ASSIGNOP and COMPAREOP attributes
	public static final int EQUAL = 301;
	public static final int EQUALEQ= 302;
	public static final int NOTEQUAL = 303;
	public static final int LSTHAN = 304;
	public static final int LSTHANEQ = 305;
	public static final int GRTHAN = 306;
	public static final int GRTHANEQ = 307;
	
	//keywords
	public static final int INT = 401;
	public static final int DOUBLE = 402;
	public static final int CHAR = 403;
	public static final int IF = 404;
	public static final int ELSE = 405;
	public static final int WHILE = 406;

}
