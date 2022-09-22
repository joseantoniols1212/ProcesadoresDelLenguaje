

%%


%%

[a-zA-Z]*[aeiou]([aeiou][a-zA-Z]*[aeiou]|[aeiou]) { return new Yytoken('A', yytext()); }

[a-zA-Z]*[aeiou]                                  { return new Yytoken('B', yytext()); }

[a-zA-Z]*[aeiou][aeiou][a-zA-Z]+                  { return new Yytoken('C', yytext()); }

[a-zA-Z]+                                         { return new Yytoken('D', yytext()); }

.|\n                                              { }
