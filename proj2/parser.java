import java.util.Stack;

public class parser {
	
	//action table
	static int[][] action = {
		          // id, while, ( , ), relop, addop, assignop, multop, num, $
		/** 1 **/ {   4, 5,     9,   0, 0,     0,     0,        0,      0,   0},
		/** 2 **/ {   0, 0,     0,   0, 0,     0,     0,        0,      0,   999},
		/** 3 **/ {   0, 0,     0,   0, 0,     0,     0,        0,      0,   999},
		/** 4 **/ {   0, 0,     0, -10, -10,  -10,    10,       0,      0,   -10},
		/** 5 **/ {   0, 0,     11,  0, 0,     0,     0,        0,      0,   0},
		/** 6 **/ {   0, 0,     0,   0, 12,    16,     0,       0,      0,   0},
		/** 7 **/ {   0, 0,     0,  -7,  0,    0,     0,        13,      0,   -7},
		/** 8 **/ {   0, 0,     0,   -11,  -11,    -11,     0,        -11,      0,   -11},
		/** 9 **/ {   19, 0,     9,   0, 0,     0,     0,        0,      8,   0},
		/** 10 **/ {   19, 0,     9,   0, 0,     0,     0,        0,      8,   0},
		/** 11 **/ {   19, 0,     9,   0, 0,     0,     0,        0,      8,   0},
		/** 12 **/ {   19, 0,     9,   0, 0,     0,     0,        0,      8,   0},
		/** 13 **/ {   19, 0,     9,   0, 0,     0,     0,        0,      8,   0},
		/** 14 **/ {   0, 0,     0,   15, 0,     16,     0,        0,      0,   0},
		/** 15 **/ {   0, 0,     0,   -12, -12,     -12,     0,        -12,      0,   -12},
		/** 16 **/ {   19, 0,     9,   0, 0,     0,     0,        0,      8,   0},
		/** 17 **/ {   0, 0,     0,   0, 0,     6,     0,        0,      0,   -3},
		/** 18 **/ {   0, 0,     0,   -8, -8,     -8,     0,        -8,      0,   -8},
		/** 19 **/ {   0, 0,     0,   -10, -10,     -10,     0,        -10,      0,   -10},
		/** 20 **/ {   0, 0,     0,   21, 0,     0,     0,        0,      0,   0},
		/** 21 **/ {   0, 0,     0,   0, 0,     0,     0,        0,      0,   0},
		/** 22 **/ {   0, 0,     0,   0, 0,     0,     0,        0,      0,   -4},
		/** 23 **/ {   0, 0,     0,   -5, 0,     16,     0,        0,      0,   -5},
		/** 24 **/ {   0, 0,     0,   -6, 0,     0,     0,        12,      0,   -6},
		/** 25 **/ {   0, 0,     0,   -9, -9,     -9,     0,        -9,      0,   -9},
	};
	
	//GOTO table
	static int[][] gotoState = {
					// S	A		W		R		E		T		F
		/** 1 */	{  -1, 	2, 		3,	 	-1,		6,	 	7,	 	18},
		/** 2 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 3 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 4 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 5 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 6 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 7 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 8 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 9 */	{  -1, 	-1, 	-1, 	-1,		14, 	7,	 	18},
		/** 10 */	{  -1, 	-1, 	-1, 	-1,		17, 	7,	 	18},
		/** 11 */	{  -1, 	-1, 	-1, 	20,		6,	 	7,	 	18},
		/** 12 */	{  -1, 	-1, 	-1, 	-1,		23, 	7,	 	18},
		/** 13 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	25},
		/** 14 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 15 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 16 */	{  -1, 	-1, 	-1, 	-1,		-1, 	24, 	18},
		/** 17 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 18 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 19 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 20 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 21 */	{  22, 	2,	 	3,	 	-1,		-1, 	-1, 	-1},
		/** 22 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 23 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 24 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
		/** 25 */	{  -1, 	-1, 	-1, 	-1,		-1, 	-1, 	-1},
	};
	
	//length of beta
	static int[] lenBeta = {
		/** 1 */ 0,
		/** 2 */ 1,
		/** 3 */ 1,
		/** 4 */ 2,
		/** 5 */ 0,
		/** 6 */ 0,
		/** 7 */ 7,
		/** 8 */ 11,
		/** 9 */ 0,
		/** 10 */ 0,
		/** 11 */ 0,
		/** 12 */ 0,
		/** 13 */ 0,
		/** 14 */ 0,
		/** 15 */ 3,
		/** 16 */ 0,
		/** 17 */ 11,
		/** 18 */ 8,
		/** 19 */ 2,
		/** 20 */ 0,
		/** 21 */ 0,
		/** 22 */ 9,
		/** 23 */ 7,
		/** 24 */ 7,
		/** 25 */ 8,
	};
	
	//index of LHS
	//number representations of nonterminal: 
	static int[] lhs = { 
		/** 1 */ 0,
		/** 2 */ 0,
		/** 3 */ 0,
		/** 4 */ 6,
		/** 5 */ 0,
		/** 6 */ 0,
		/** 7 */ 4,
		/** 8 */ 6,
		/** 9 */ 0,
		/** 10 */ 0,
		/** 11 */ 0,
		/** 12 */ 0,
		/** 13 */ 0,
		/** 14 */ 0,
		/** 15 */ 6,
		/** 16 */ 0,
		/** 17 */ 1,
		/** 18 */ 5,
		/** 19 */ 6,
		/** 20 */ 0,
		/** 21 */ 0,
		/** 22 */ 0,
		/** 23 */ 3,
		/** 24 */ 4,
		/** 25 */ 5,
	};
	
	// grammar production rules
	static String[] rules = {
		"",					// ignore for index starting at 1
		"S -> A",
		"S -> W",
		"A -> id assignop E",
		"W -> while ( R ) S",
		"R -> E relop E",
		"E -> E addop T",
		"E -> T",
		"T -> F",
		"T -> T multop F",
		"F -> id",
		"F -> num",
		"F -> ( E )",
	};
	
	// constant declarations
	private static final int ID     	= 0;
	private static final int WHILE     	= 1;
	private static final int RELOP     	= 2;
	private static final int ASSIGNOP  	= 3;
	private static final int NUM 		= 4;
	private static final int ADDOP  	= 5;
	private static final int MULOP  	= 6;
	private static final int LPAREN 	= 7;
	private static final int RPAREN 	= 8;
	private static final int DOLLAR 	= 9;
		
	// input symbols � represents terminals
	private static final String[] symbols = { "id", "while", "==", "=", "num", "+", "*", "(", ")", "$" };

	// input string       for example:   x     *    y     +    z     $
	private static int[] inputString = { ID, MULOP, ID, ADDOP, ID, DOLLAR };

	
	
	public static void main(String[] args) {
		
		Stack<Integer> stack = new Stack<Integer>();
		int a;				// current input symbol
		int s;				// state on top of stack
		int t;				// temporary state variable
		int ip = 0;		// input buffer pointer
		int i;				// loop variable
		int len;			// length of beta (RHS of production)
		int pass = 0;		// pass number
		
		// initialize stack
		stack.push(0);			// push initial state
		
		// set a to the first symbol of input string
		a = inputString[ip];
		
		System.out.printf( "%4s    %30s      %6s\n", "Pass", "Input", "Action" );
		while(true)
		{
			pass++;
			System.out.printf("%4d", pass);
			printInput( ip );
			s = stack.peek();
			//------------------------------------------------------------
			// shift
			if( action[s][a] > 0 && action[s][a] < 999 )
			{
				stack.push(action[s][a]);		// push action on stack
				System.out.printf("     shift %d\n", action[s][a]);
				a = inputString[++ip];			// move to next input symbol
			}
			
			//------------------------------------------------------------
			// reduce
			else if( action[s][a] < 0 )
			{
				// pop |beta| symbol off stack
				len = lenBeta[s];
				for (i=0; i<len; i++)
				{
					t = stack.pop();
				}
				
				System.out.printf("     reduce by %s\n", 
							      rules[ Math.abs(action[s][a]) ] );
				
				// set state t to top of stack
				t = stack.peek();
				
				// push GOTO[t, A] onto stack
				stack.push(gotoState[t][lhs[s]]);
				
				// output production A --> beta
			}
			
			//------------------------------------------------------------
			// accept
			else if( action[s][a] == 999 )
			{
				System.out.println("     Accept");
				return;
			}
			
			//------------------------------------------------------------
			// error
			else
			{
				System.out.println("     Syntax Error");
				return;
			}
		}  // end of while loop 	
		
    }  // end of main
	

	private static void printInput( int ip )
	{
		String str = "";
		
		for( int j = ip; j < inputString.length; j ++ )
		{
			str = str + symbols[ inputString[j] ] + " ";
		}
		
		System.out.printf( "     %30s", str);
	}
}

