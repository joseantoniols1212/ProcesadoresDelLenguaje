

%%

Variable      = [a-zA-Z_][a-zA-Z0-9_]*
Comando       = [a-zA-Z][a-zA-Z0-9_]*

%{
  String variable, valor;
%}

%int

%xstate EQUAL, VAR, ARGS, TEXTO

%%

<YYINITIAL> {
  
  /* comando */
  {Comando}/\s           { System.out.print(yytext()); yybegin(ARGS); }

  /* variable */
  {Variable}/\=          { variable = yytext(); valor=""; yybegin(EQUAL); }

  [\s\n]              { }

}

<EQUAL>\= { yybegin(VAR); }

<VAR> {

  /* otra variable */
  \${Variable}           { valor += TablaSimbolos.get(yytext()); }

  /* valor entre comillas */
  \"                     { yybegin(TEXTO); }

  /* valor */
  [^\n\s\;]              { valor += yytext(); } 

  /* final de linea o punto y coma*/
  [\n\;]                 { TablaSimbolos.put(variable, valor); yybegin(YYINITIAL); }

}

<ARGS> {

  /* variable */
  \${Variable}          { System.out.print(TablaSimbolos.get(yytext())); }

  /* texto */
  [^\n$]                { System.out.print(yytext()); }

  /* final de linea */
  \n                    { yybegin(YYINITIAL); }

}

<TEXTO> {

  /* variable */
  \${Variable}          { valor += TablaSimbolos.get(yytext()); }

  /* texto */
  [^\"]               { valor+= yytext(); }                 

  /* termina entrecomillado */
  \"                  { yybegin(VAR); }                 

}
