

%%

Nombre = [a-zA-Z][a-zA-Z0-9]*

%int

%String variable, valor 

%state VARINIT, CLASS, VAR, TEXT, PRINT

%%

<YYNITIAL>{

  "class " { yybegin(CLASS); }

  "public static void main(String argv[]) {" { }

  "String " { yybegin(VARINIT); }

  ^"System.out.println(" { yybegin(VAR); }

  "System.out.println(" { yybegin(PRINT); }

}

<CLASS>{

  ${Nombre} {TablaSimbolos.put("$Class_name", yytext(); ); yybegin(YYNITIAL); } 
}

<VARINIT>{ 

  ${Nombre} { variable=yytext(); valor="" ; yybegin(YYNITIAL); } 

  " = " {}

  \" { yybegin(TEXT); }
  
  \; { TablaSimbolos.put(variable, valor); yybegin(YYNITIAL); }

}

<TEXT>{

  \\\" { valor+= "\""; }

  \\\; { valor+= ";"; }

  [^\"]               { valor+= yytext(); }                 

  \"                  { yybegin(VARINIT); }                 

}

<VAR>{
  
             { valor += TablaSimbolos.get(yytext()); }

  /* valor entre comillas */
  \"                     { yybegin(TEXTO); }

  /* valor */
  [^\n\s\;]              { valor += yytext(); } 

  /* final de linea o punto y coma*/
  [\n\;]                 { TablaSimbolos.put(variable, valor); yybegin(YYINITIAL); }

}
