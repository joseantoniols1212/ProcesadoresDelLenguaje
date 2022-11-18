
%%
%int

%%
while					  { return Yytoken.WHILE;  }
do					      { return Yytoken.DO;  }
[_a-zA-Z][_a-zA-Z0-9]*    { return Yytoken.IDENT;  }
0|[1-9][0-9]*		      { return Yytoken.NUMERO; }
\{                        { return Yytoken.ALL; }
\}                        { return Yytoken.CLL; }
\(                        { return Yytoken.AP; }
\)                        { return Yytoken.CP; }
\;                        { return Yytoken.PYC; }
\=                        { return Yytoken.IGUAL; }
\<                        { return Yytoken.MENOR; }
[^]                       {}

