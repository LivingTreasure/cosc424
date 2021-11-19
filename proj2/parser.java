import java.util.Stack;

public class parser {
	
	//action table
	static int[][] action = {
			//fill in here
	};
	
	//GOTO table
	static int[][] gotoState = {
		//fill in here	
	};
	
	//length of beta
	static int[] lenBeta = { }; //fill in
	
	//index of LHS
	//number representations of nonterminal: 
	static int[] lhs = { }; //fill in
	
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
	private static final int ADDOP  	= 1;
	private static final int MULOP  	= 2;
	private static final int ASSIGNOP  	= 3;
	private static final int LPAREN 	= 4;
	private static final int RPAREN 	= 5;
	private static final int SEMICOLON 	= 6;
	private static final int NUM 		= 7;
	private static final int DOLLAR 	= 8;
		
	// input symbols – represents terminals
	private static final String[] symbols = { "id", "+", "*", "(", ")", "$" };

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

