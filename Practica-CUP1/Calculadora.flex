import java_cup.runtime.*;

%%

%cup

%%

[0-9]|[1-9][0-9]* { return new Symbol(sym.NUM, new Integer(yytext())); }
\- { return new Symbol(sym.MINUS); }
\+ { return new Symbol(sym.PLUS); }
\* { return new Symbol(sym.MUL); }
\/ { return new Symbol(sym.DIV); }
\( { return new Symbol(sym.LP); }
\) { return new Symbol(sym.RP); }
\n { return new Symbol(sym.NL); }
[^] { }
