

%%


%%


// Entero largo
// -Hexadecimal
0(x|X)[0-9a-fA-F]+(l|L)  { return new Yytoken(Yytoken.TOKEN_CTE_ENTERO_LARGO, yytext());}

// -Octal
0[0-7]+(l|L)       { return new Yytoken(Yytoken.TOKEN_CTE_ENTERO_LARGO, yytext());}
// -Decimal
[0-9]+(l|L)              { return new Yytoken(Yytoken.TOKEN_CTE_ENTERO_LARGO, yytext());}

// Entero corto
// -Hexadecimal
0(x|X)[0-9a-fA-F]+       { return new Yytoken(Yytoken.TOKEN_CTE_ENTERO, yytext());}

// -Octal
0[0-7]+            { return new Yytoken(Yytoken.TOKEN_CTE_ENTERO, yytext());}

// -Decimal
0|[1-9][0-9]*      { return new Yytoken(Yytoken.TOKEN_CTE_ENTERO, yytext());}

// Real corto
[0-9]+\.[0-9]+(E(\+|-)?[0-9]+)?(f|F)       { return new Yytoken(Yytoken.TOKEN_CTE_REAL_CORTO, yytext());}
\.[0-9]+(E(\+|-)?[0-9]+)?(f|F)             { return new Yytoken(Yytoken.TOKEN_CTE_REAL_CORTO, yytext());}
[0-9]+\.(E(\+|-)?[0-9]+)?(f|F)            { return new Yytoken(Yytoken.TOKEN_CTE_REAL_CORTO, yytext());}
[0-9]+(E(\+|-)?[0-9]+)?(f|F)            { return new Yytoken(Yytoken.TOKEN_CTE_REAL_CORTO, yytext());}

// Real largo
[0-9]+(E(\+|-)?[0-9]+)?(d|D)            { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}
[0-9]+(E(\+|-)?[0-9]+)            { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}
[0-9]+\.[0-9]+(E(\+|-)?[0-9]+)?(d|D)       { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}
[0-9]+\.(E(\+|-)?[0-9]+)?(d|D)       { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}
\.[0-9]+(E(\+|-)?[0-9]+)?(d|D)      { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}
[0-9]+\.[0-9]+(E(\+|-)?[0-9]+)?           { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}
[0-9]+\.(E(\+|-)?[0-9]+)?           { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}
\.[0-9]+(E(\+|-)?[0-9]+)?           { return new Yytoken(Yytoken.TOKEN_CTE_REAL_LARGO, yytext());}


\s|\n {}

// Error

[^\s]+                           { return new Yytoken(Yytoken.TOKEN_ERROR, yytext());}
