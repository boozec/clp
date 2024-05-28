/*
 La grammatica di Python si trova a
 https://docs.python.org/3/reference/grammar.html

 Questa e` stata elaborata da  Bart Kiers, bart@big-o.nl
 e si trova a https://github.com/bkiers/python3-parser

 Semplificata ai fini del corso di CLP -- Marco Bertoni, Cosimo Laneve
*/

// $antlr-format alignTrailingComments true, columnLimit 150, maxEmptyLinesToKeep 1, reflowComments false, useTab false
// $antlr-format allowShortRulesOnASingleLine true, allowShortBlocksOnASingleLine true, minEmptyLines 0, alignSemicolons ownLine
// $antlr-format alignColons trailing, singleLineOverrulesHangingColon true, alignLexerCommands true, alignLabels true, alignTrailers true

lexer grammar Python3Lexer;

tokens {
    INDENT,
    DEDENT
}

options {
    superClass = Python3LexerBase;
}

STRING  : '\'' (STRING_ESCAPE_SEQ | ~[\\\r\n\f'])* '\''
        | '"' ( STRING_ESCAPE_SEQ | ~[\\\r\n\f"])* '"'
        ;

NUMBER : DECIMAL_INTEGER | FLOAT_NUMBER ;

AND        : 'and';
AS         : 'as';
DEF        : 'def';
ELIF       : 'elif';
ELSE       : 'else';
FALSE      : 'False';
FOR        : 'for';
FROM       : 'from';
IF         : 'if';
IMPORT     : 'import';
IN         : 'in';
IS         : 'is';
NONE       : 'None';
NOT        : 'not';
OR         : 'or';
RETURN     : 'return';
TRUE       : 'True';
UNDERSCORE : '_';
WHILE      : 'while';

NEWLINE: ({this.atStartOfInput()}? SPACES | ( '\r'? '\n' | '\r' | '\f') SPACES?) {this.onNewLine();};

NAME: ('_' | 'a'..'z' | 'A'..'Z') ('_' | 'a'..'z' | 'A'..'Z' | '0'..'9')* 
     ;

DECIMAL_INTEGER: NON_ZERO_DIGIT DIGIT* | '0'+;

FLOAT_NUMBER: POINT_FLOAT | EXPONENT_FLOAT;

DOT                : '.';
ELLIPSIS           : '...';
STAR               : '*';
OPEN_PAREN         : '(' {this.openBrace();};
CLOSE_PAREN        : ')' {this.closeBrace();};
COMMA              : ',';
COLON              : ':';
SEMI_COLON         : ';';
POWER              : '**';
ASSIGN             : '=';
OPEN_BRACK         : '[' {this.openBrace();};
CLOSE_BRACK        : ']' {this.closeBrace();};
OR_OP              : '|';
XOR                : '^';
AND_OP             : '&';
LEFT_SHIFT         : '<<';
RIGHT_SHIFT        : '>>';
ADD                : '+';
MINUS              : '-';
DIV                : '/';
MOD                : '%';
IDIV               : '//';
NOT_OP             : '~';
OPEN_BRACE         : '{' {this.openBrace();};
CLOSE_BRACE        : '}' {this.closeBrace();};
LESS_THAN          : '<';
GREATER_THAN       : '>';
EQUALS             : '==';
GT_EQ              : '>=';
LT_EQ              : '<=';
NOT_EQ_1           : '<>';
NOT_EQ_2           : '!=';
AT                 : '@';
ARROW              : '->';
ADD_ASSIGN         : '+=';
SUB_ASSIGN         : '-=';
MULT_ASSIGN        : '*=';
AT_ASSIGN          : '@=';
DIV_ASSIGN         : '/=';
MOD_ASSIGN         : '%=';
AND_ASSIGN         : '&=';
OR_ASSIGN          : '|=';
XOR_ASSIGN         : '^=';
LEFT_SHIFT_ASSIGN  : '<<=';
RIGHT_SHIFT_ASSIGN : '>>=';
POWER_ASSIGN       : '**=';
IDIV_ASSIGN        : '//=';

SKIP_: ( SPACES | COMMENT | LINE_JOINING) -> skip;

UNKNOWN_CHAR: .;

fragment STRING_ESCAPE_SEQ: '\\' . | '\\' NEWLINE;

fragment NON_ZERO_DIGIT: [1-9];

fragment DIGIT: [0-9];

fragment POINT_FLOAT: INT_PART? '.' DIGIT+ | INT_PART '.';

fragment EXPONENT_FLOAT: ( INT_PART | POINT_FLOAT) EXPONENT;

fragment INT_PART: DIGIT+;

fragment EXPONENT: [eE] [+-]? DIGIT+;

fragment SPACES: [ \t]+;

fragment COMMENT: '#' ~[\r\n\f]*;

fragment LINE_JOINING: '\\' SPACES? ( '\r'? '\n' | '\r' | '\f');