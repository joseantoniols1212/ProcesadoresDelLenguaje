

%%

%int

%xstate TIEMPO1 TIEMPO2 SUBTITULOS NBLOQUE

%{
  int tiempo1, tiempo2;
  int subs = 0;
  int tiempo_total = 0;
  int lineas = 0;
  int palabras = 0;

  public void sout(){
    System.out.println("NUM_SUBTITULOS "+subs);
    System.out.println("TIEMPO_TOTAL "+tiempo_total);
    System.out.println("NUM_LINEAS "+lineas);
    System.out.println("NUM_PALABRAS "+palabras);
  }

%}

%%

<YYINITIAL> {
  
  \s|\n {}

  [1-9][0-9]* { subs += 1; yybegin(TIEMPO1); }

  . { yybegin(NBLOQUE); }
  
}

<TIEMPO1> {

  [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3} { tiempo1 = Integer.parseInt(yytext().substring(0,2))*3600 + Integer.parseInt(yytext().substring(3,5))*60 + Integer.parseInt(yytext().substring(6,8)); }

  \s|\t {}

  "-->" { yybegin(TIEMPO2);}

  . { subs--; yybegin(YYINITIAL); }
}

<TIEMPO2> {

  [0-9]{2}:[0-9]{2}:[0-9]{2},[0-9]{3} { tiempo2 = Integer.parseInt(yytext().substring(0,2))*3600 + Integer.parseInt(yytext().substring(3,5))*60 + Integer.parseInt(yytext().substring(6,8)); }

  " " {}

  \n { tiempo_total+= tiempo2-tiempo1; yybegin(SUBTITULOS);}

  . { subs--; yybegin(YYINITIAL); }

}

<SUBTITULOS> {

  [^\s\n]+ { palabras++; }

  . { }

  \n { lineas++; yybegin(NBLOQUE); }

}

<NBLOQUE> {
  
  \n { yybegin(YYINITIAL); }

  . { yybegin(SUBTITULOS); }

}
