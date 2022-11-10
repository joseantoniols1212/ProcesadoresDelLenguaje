import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;

class Parser {

private static int token;
private static Yylex lex;
private static int yylex() {
	int token = 0;
	try {
		token = lex.yylex();
	} catch (IOException e) {
		System.out.println("IOException");
		System.exit(0);
	}
	return token;
}

public static void main(String[] arg) {
    if (arg.length>0) {
        try {
            lex = new Yylex(new FileReader(arg[0]));
            axioma();
        } catch (IOException e) {
        } 
    }
}

private static int consume(int token, int type){
    if(token!=type){
        Yytoken.error(token);
        System.exit(0);
    }
    return yylex();
}

/// .... A completar .....
private static void axioma(){
    Yytoken.regla(0);
    token=yylex();
    if(token==Yytoken.EOF||token==Yytoken.WHILE||token==Yytoken.DO||token==Yytoken.IDENT||token==Yytoken.ALL) {
        listaSent();
        token = consume(token, Yytoken.EOF);
    } else {
        Yytoken.error(token);
    }
}

private static void listaSent(){
    if(token==Yytoken.WHILE||token==Yytoken.DO||token==Yytoken.IDENT||token==Yytoken.ALL) {
        Yytoken.regla(1);
        sent();
        listaSent();
    } else if(token==Yytoken.EOF||token==Yytoken.CLL) {
        Yytoken.regla(2);
    } else {
        Yytoken.error(token);
    }
}

private static void sent(){
    if(token==Yytoken.WHILE) {
        Yytoken.regla(3);
        token = consume(token, Yytoken.WHILE);
        token = consume(token, Yytoken.AP);
        cond();
        token = consume(token, Yytoken.CP);
        sent();
    } else if(token==Yytoken.DO){
        Yytoken.regla(4);
        token = consume(token, Yytoken.DO);
        sent();
        token = consume(token, Yytoken.WHILE);
        token = consume(token, Yytoken.AP);
        cond();
        token = consume(token, Yytoken.CP);
        token = consume(token, Yytoken.PYC);
    } else if(token==Yytoken.IDENT){
        Yytoken.regla(5);
        token = consume(token, Yytoken.IDENT);
        token = consume(token, Yytoken.IGUAL);
        var();
        token = consume(token, Yytoken.PYC);
    } else if(token==Yytoken.ALL){
        Yytoken.regla(6);
        token = consume(token, Yytoken.ALL);
        listaSent();
        token = consume(token, Yytoken.CLL);
    } else {
            Yytoken.error(token);
		        System.exit(0);
    }
}

private static void cond(){
    if(token==Yytoken.IDENT||token==Yytoken.NUMERO) {
        Yytoken.regla(7);
        var();
        token = consume(token, Yytoken.MENOR);
        var();
    } else {
            Yytoken.error(token);
		        System.exit(0);
    }
}

private static void var(){
    if(token==Yytoken.IDENT) {
        Yytoken.regla(8);
        token = consume(token, Yytoken.IDENT);
    } else if (token==Yytoken.NUMERO){
        Yytoken.regla(9);
        token = consume(token, Yytoken.NUMERO);
    } else {
            Yytoken.error(token);
		        System.exit(0);
    }
}

}
