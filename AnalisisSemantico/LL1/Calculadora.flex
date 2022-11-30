%%

%type Symbol
// %debug

%%   

/* Expresiones y reglas */
   
    "+"                { return new Symbol(Symbol.MAS, yytext()); }
    "-"                { return new Symbol(Symbol.MENOS, yytext()); }
    "*"                { return new Symbol(Symbol.POR, yytext()); }
    "/"                { return new Symbol(Symbol.DIV, yytext()); }
    "("                { return new Symbol(Symbol.AP, yytext()); }
    ")"                { return new Symbol(Symbol.CP, yytext()); }
    ";"                { return new Symbol(Symbol.PYC, yytext()); }
    0|[1-9][0-9]*      { return new Symbol(Symbol.NUMERO, yytext()); }
    \r|\n              { return new Symbol(Symbol.EOLN, "EOLN"); }   
    \ |\t\f            {  }  
    [^]                { throw new Error("Illegal character <"+yytext()+">"); }
