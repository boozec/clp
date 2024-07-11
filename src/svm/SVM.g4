grammar SVM;

@header {
import java.util.HashMap;
}

@lexer::members {
public int lexicalErrors=0;
}

/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/
  
assembly: (instruction)* ;

instruction:
    ( 	LOAD REG NUMBER '(' REG ')' 	// = memory[NUMBER + REGright] <- REGleft  ## NEVER USED! WHY?
    	| STORE REG NUMBER '(' REG ')'	// = REGleft <- memory[NUMBER + REGright]
    	| STOREI REG NUMBER				// = REG <- NUMBER 
    	| MOVE REG REG					// = REGleft <- REGright
    	| ADD REG REG					// = top <- REGleft + REGright
    	| ADDI REG NUMBER				// = top <- REGleft + NUMBER
    	| SUB REG REG 					// = top <- REGleft - REGright
    	| SUBI REG NUMBER    			// = top <- REGleft - NUMBER
    	| MUL REG REG					// = top <- REGleft * REGright
    	| MULI REG NUMBER				// = top <- REGleft * NUMBER
    	| DIV REG REG					// = top <- REGleft / REGright
    	| DIVI REG NUMBER				// = top <- REGleft / NUMBER
    	| PUSH (n=NUMBER | l=LABEL)		// = memory[sp] = number|label , sp = sp-1
    	| PUSHR REG						// = memory[sp] = REG , sp = sp-1
    	| POP							// = sp = sp+1
    	| POPR REG 						// = REG <- memory[sp+1] == STORE REG 0($sp)
    	| BRANCH LABEL					// = ip = LABEL
    	| BRANCHEQ REG REG LABEL		// = if REGleft == REGright => ip = LABEL
    	| BRANCHLESSEQ REG REG LABEL 	// = if REGleft <= REGright => ip = LABEL
    	| JUMPSUB LABEL
    	| RETURNSUB REG
    	| l=LABEL ':'
 	  	| HALT
	  ) ;
 	 
/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/
 
LOAD  	 : 'load' 	; 	
STORE	 : 'store' 	; 	
STOREI	 : 'storei' ;  	
MOVE	 : 'move' 	;
ADD  	 : 'add'  	;
ADDI  	 : 'addi'  	;
SUB	 	 : 'sub' 	;	
SUBI	 : 'subi' 	;	
MUL	 	 : 'mul' 	;  	
MULI	 : 'muli' 	;
DIV	 	 : 'div' 	;
DIVI 	 : 'divi' 	;
PUSH	 : 'push' 	;
PUSHR	 : 'pushr'	;
POP	 	 : 'pop' 	;
POPR	 : 'popr' 	;
BRANCH 	 : 'b' 		;
BRANCHEQ :'beq' 	;
BRANCHLESSEQ:'bleq' ;
JUMPSUB	 : 'jsub' 	;
RETURNSUB: 'rsub'	;
HALT	 : 'halt' ;	

REG 	 : 'A0' | 'RA' | 'FP' | 'SP' | 'AL' | 'T1' | 'T2' ;
LABEL	 : ('a'..'z'|'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9')* ;
NUMBER	 : '0' | ('-')?(('1'..'9')('0'..'9')*) ;


WHITESP  : ( '\t' | ' ' | '\r' | '\n' )+   -> channel(HIDDEN);
LINECOMENTS    : '//' (~('\n'|'\r'))* -> skip;

ERR   	 : . { System.err.println("Invalid char: "+ getText()); lexicalErrors++;  } -> channel(HIDDEN); 

