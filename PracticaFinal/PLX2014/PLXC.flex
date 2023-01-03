import java_cup.runtime.*;

%%

%cup

%%


// Delimitadores

\{                                                    { return new Symbol(sym.ALL); }
\}                                                    { return new Symbol(sym.CLL); }
\(                                                    { return new Symbol(sym.AP); }
\)                                                    { return new Symbol(sym.CP); }
\;                                                    { return new Symbol(sym.PYC); }
\,                                                    { return new Symbol(sym.COMA); }

// Operaciones aritmeticas, logicas y asignacion

\=                                                    { return new Symbol(sym.ASIG); }
\+                                                    { return new Symbol(sym.MAS, yytext()); }
\-                                                    { return new Symbol(sym.MENOS, yytext()); }
\*                                                    { return new Symbol(sym.POR, yytext()); }
\/                                                    { return new Symbol(sym.DIV, yytext()); }
\!                                                    { return new Symbol(sym.NOT); }
"||"                                                  { return new Symbol(sym.OR); }
"&&"                                                  { return new Symbol(sym.AND); }
\<                                                    { return new Symbol(sym.MENOR, yytext()); }
\>                                                    { return new Symbol(sym.MAYOR, yytext()); }
"<="                                                  { return new Symbol(sym.MENORIGUAL, yytext()); }
">="                                                  { return new Symbol(sym.MAYORIGUAL, yytext()); }
"=="                                                  { return new Symbol(sym.IGUAL, yytext()); }
"!="                                                  { return new Symbol(sym.DISTINTO, yytext()); }
\%                                                    { return new Symbol(sym.MODULO, yytext()); }

\+\+		                                    					{ return new Symbol(sym.INC, yytext()); }
\-\-		                                    					{ return new Symbol(sym.DEC, yytext()); }

// Flujo de ejecucion y funciones

print                                                 { return new Symbol(sym.PRINT); }
if                                                    { return new Symbol(sym.IF); }
else                                                  { return new Symbol(sym.ELSE); }
while                                                 { return new Symbol(sym.WHILE); }
for                                                   { return new Symbol(sym.FOR); }
do                                                    { return new Symbol(sym.DO); }
step                                                  { return new Symbol(sym.STEP); }
to                                                    { return new Symbol(sym.TO); }
downto                                                { return new Symbol(sym.DOWNTO); }

// Declaradores de tipos

int                                                   { return new Symbol(sym.INT); }
float                                                 { return new Symbol(sym.FLOAT); }
char                                                  { return new Symbol(sym.CHAR); }

// Tipos primitivos

[a-zA-Z][a-zA-Z0-9]*                                  { return new Symbol(sym.IDENT, yytext()); }                                                // Identificador
\'\\?.\'|\'\u[0-9A-Fa-f]{4}\'                         { String aux = yytext(); return new Symbol(sym.CARACTER, aux.substring(1,aux.length())); } // Char
0|[1-9][0-9]*                                         { return new Symbol(sym.ENTERO, Integer.parseInt(yytext())); }                             // Int
0x[0-9a-fA-F]+                                        { return new Symbol(sym.ENTERO, Integer.parseInt(yytext().substring(2),16)); }             // Int(16)
0[0-7]+                                               { return new Symbol(sym.ENTERO, Integer.parseInt(yytext().substring(1),8)); }              // Int(8)

// Ignoramos espacios en blanco

\/\/.*											                          {  }
\r|\n                                                 {  }
\ |\t|\f                                              {  }
[^]                                                   { throw new Error("Illegal character <"+yytext()+">"); }

