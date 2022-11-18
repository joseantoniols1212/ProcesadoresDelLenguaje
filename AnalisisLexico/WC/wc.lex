%%


%%

[^\s]+     { return new Yytoken(Yytoken.PALABRA, yytext()); }

.             { return new Yytoken(Yytoken.CARACTER, yytext()); }

\n            { return new Yytoken(Yytoken.LINEA, yytext()); }
