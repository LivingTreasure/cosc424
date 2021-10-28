/*
 * Token.java
 * Project 1 COSC 424
 * @author Neil Yoder, Jacob Neulight, Elliot Cole
 * 
 * Holds the token class methods
 * 
 */
public class Token {
	private int token;
	private int attribute;
	
	public Token() {
		super();
	}
	
	public Token(int token, int attribute) {
		super();
		this.token = token;
		this.attribute = attribute;
		
	}
	
	public int getToken() {
		return token;
	}
	
	public int getAttribute() {
		return attribute;
	}

}
