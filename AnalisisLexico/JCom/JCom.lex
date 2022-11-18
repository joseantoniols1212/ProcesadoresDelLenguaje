

%%

%int
%xstate COMMENT1 COMMENT2 COMMENT3 TEXT

%%

<YYINITIAL>{

\" {yybegin(TEXT);}
  
.|\n { }

"//" { yybegin(COMMENT1); }

"/*" {  yybegin(COMMENT2);}

"/**" { yybegin(COMMENT3);}

[^] {}

}

<COMMENT1> {

" " { }

. { return 1; }

\n { yybegin(YYINITIAL); }

}

<COMMENT2> {

"*/" { yybegin(YYINITIAL); }

" " { }

. { return 2; }

\n { }

}

<COMMENT3> {

"*/" { yybegin(YYINITIAL); }

" " { }

. { return 3; }

\n { }

}

<TEXT>{

\\\" { }
 
\" { yybegin(YYINITIAL); }

. {}

\n {}

}
