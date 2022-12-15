import java_cup.runtime.*;

%%

%cup

%%
\{                                                    { return new Symbol(sym.ALL); }
\}                                                    { return new Symbol(sym.CLL); }
\(                                                    { return new Symbol(sym.AP); }
\)                                                    { return new Symbol(sym.CP); }
\;                                                    { return new Symbol(sym.PYC); }
\=                                                    { return new Symbol(sym.ASIG); }
\+                                                    { return new Symbol(sym.MAS); }
\-                                                    { return new Symbol(sym.MENOS); }
\*                                                    { return new Symbol(sym.POR); }
\/                                                    { return new Symbol(sym.DIV); }
\!                                                    { return new Symbol(sym.NOT); }
"||"                                                  { return new Symbol(sym.OR); }
"&&"                                                  { return new Symbol(sym.AND); }
\<                                                    { return new Symbol(sym.MENOR, yytext()); }
\>                                                    { return new Symbol(sym.MAYOR, yytext()); }
"<="                                                  { return new Symbol(sym.MENORIGUAL, yytext()); }
">="                                                  { return new Symbol(sym.MAYORIGUAL, yytext()); }
"=="                                                  { return new Symbol(sym.IGUAL, yytext()); }
"!="                                                  { return new Symbol(sym.DISTINTO, yytext()); }
print                                                 { return new Symbol(sym.PRINT); }
if                                                    { return new Symbol(sym.IF); }
else                                                  { return new Symbol(sym.ELSE); }
while                                                 { return new Symbol(sym.WHILE); }
for                                                   { return new Symbol(sym.FOR); }
do                                                    { return new Symbol(sym.DO); }
[a-zA-Z][a-zA-Z0-9]*                                  { return new Symbol(sym.IDENT, yytext()); }
0|[1-9][0-9]*                                         { return new Symbol(sym.ENTERO, Integer.parseInt(yytext())); }
\/\/.*											                          {  }
\r|\n                                                 {  }
\ |\t|\f                                              {  }
[^]                                                   { throw new Error("Illegal character <"+yytext()+">"); }

