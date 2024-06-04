/*
 La grammatica di Python si trova a
 https://docs.python.org/3/reference/grammar.html

 Questa e` stata elaborata da  Bart Kiers, bart@big-o.nl
 e si trova a https://github.com/bkiers/python3-parser

 Semplificata ai fini del corso di CLP -- Marco Bertoni, Cosimo Laneve
*/

// $antlr-format alignTrailingComments true, columnLimit 150, minEmptyLines 1, maxEmptyLinesToKeep 1, reflowComments false, useTab false
// $antlr-format allowShortRulesOnASingleLine false, allowShortBlocksOnASingleLine true, alignSemicolons hanging, alignColons hanging

parser grammar Python3Parser;

options {
    superClass = Python3ParserBase;
    tokenVocab = Python3Lexer;
}

root
    : NEWLINE* (simple_stmts | compound_stmt)* EOF
    ;

simple_stmts
    : simple_stmt (';' simple_stmt)* ';'? NEWLINE
    ;

compound_stmt
    : if_stmt
    | while_stmt
    | for_stmt
    | funcdef
    ;

simple_stmt
    : assignment
    | expr
    | return_stmt
    | import_stm
    ;

assignment
    : exprlist augassign exprlist
    ;

return_stmt
    : 'return' exprlist?
    ;

import_stm
    : 'import' dotted_name ('as' NAME)?
    | 'from' dotted_name 'import' (NAME  (',' NAME)*  | '*')
    ;

dotted_name
    : NAME ('.' NAME)*
    ;

funcdef
    : 'def' NAME '(' paramlist? ')' ':' block
    ;

paramlist
    : paramdef ('=' expr)? (',' paramdef ('=' expr)?)*
    ;

paramdef
    : NAME (':' expr)?
    ;

augassign
    : '='
    | '+='
    | '-='
    | '*='
    | '@='
    | '/='
    | '%='
    | '&='
    | '|='
    | '^='
    | '<<='
    | '>>='
    | '**='
    | '//='
    ;

if_stmt
    : 'if' expr ':' block ('elif' expr ':' block)* ('else' ':' block)?
    ;

while_stmt
    : 'while' expr ':' block ('else' ':' block)?
    ;

for_stmt
    : 'for' exprlist ':' block ('else' ':' block)?
    ;

block
    : simple_stmts
    | NEWLINE INDENT (simple_stmts | compound_stmt)+ DEDENT
    ;

comp_op
    : '<'
    | '>'
    | '=='
    | '>='
    | '<='
    | '<>'
    | '!='
    | 'in'
    | 'not' 'in'
    | 'is'
    | 'is' 'not'
    ;

expr
    : atom trailer*
    | expr '**' expr
    | ('+' | '-' | '~')+ expr
    | expr ('*' | '@' | '/' | '%' | '//') expr
    | expr ('+' | '-') expr
    | expr ('<<' | '>>') expr
    | expr '&' expr
    | expr '^' expr
    | expr '|' expr
    | 'not' expr
    | expr comp_op expr
    | expr 'and' expr
    | expr 'or' expr
    | expr 'if' expr 'else' expr
    ;
atom
    : '(' testlist_comp? ')'
    | '[' testlist_comp? ']'
    | '{' testlist_comp? '}'
    | NAME
    | NUMBER
    | STRING+
    | '...'
    | 'None'
    | 'True'
    | 'False'
    ;

testlist_comp    : expr (comp_for |  (',' expr)* ','?)
       ;
trailer
    : '(' arglist? ')'
    | '[' expr (',' expr)* ','? ']'
    | '.' NAME
    | '[' expr? ':' expr? (':' expr? )? ']'
    ;

exprlist
    : expr (',' expr )* ','?
    ;

arglist
    : argument (',' argument)* ','?
    ;

argument
    : expr comp_for? | expr '=' expr
    ;

comp_iter
    : comp_for
    | comp_if
    ;

comp_for
    : 'for' exprlist 'in' expr comp_iter?
    ;

comp_if
    : 'if' expr comp_iter?
    ;