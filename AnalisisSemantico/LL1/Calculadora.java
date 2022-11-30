import java.io.FileReader;
import java.io.IOException;
import java.io.FileReader;

class Calculadora {

private static Symbol simbolo;
private static Yylex lex;

private static Symbol leer() {
	int token = 0;
	try {
		simbolo = lex.yylex();
		// System.out.println(simbolo.lexema());
		if (simbolo == null) {
			simbolo = new Symbol(Symbol.EOF, null);
		}
	} catch (IOException e) {
		System.out.println("IOException");
		System.exit(0);
	}
	return simbolo;
}

private static void error() {
	System.out.println("ERROR: Ultimo token leido: "+ simbolo.lexema());
	System.exit(1);
}


public static void main(String[] arg) {
    if (arg.length>0) {
        try {
            lex = new Yylex(new FileReader(arg[0]));
            /// .... A completar .....   
            simbolo = leer();
            axioma();
        } catch (IOException e) {
        } 
    }
}


/*************************************************************************/
/************************ A implementar por el alumno ********************/
/*************************************************************************/

// Gramatica
//
//  Reglas                                            | Simbolos directores
// ---------------------------------------------------+--------------------------------
// axioma -> lineas EOF                               |  num, (, -, EOF
// lineas -> exp EOLN lineas | epsilon| EOLN exp      |  num, (, - | EOF | EOLN
// exp    -> TE                                       |  num, (, -
// E      -> epsilon | +TE | -TE                      |  EOLN,) | + | -
// T      -> FQ                                       |  num, (, -
// Q      -> epsilon | *FQ | /FQ                      |  +, -, EOLN, ) | * | /
// F      -> num | (exp) | -F                         |  num | ( | -

  private static void axioma(){
    switch (simbolo.token()){
      case Symbol.AP:
      case Symbol.MENOS:
      case Symbol.EOF:
      case Symbol.NUMERO:
        lineas();
        // Simbolo deberia ser EOF aqui
        break;
      default:
        error();
    }
  }

  private static void lineas(){
    switch (simbolo.token()){
      case Symbol.AP:
      case Symbol.MENOS:
      case Symbol.NUMERO:
        System.out.println((int)linea());
        simbolo = leer(); // quitamos EOLN
        lineas();
        break;
      case Symbol.EOLN:
        System.out.println();
        simbolo = leer(); // quitamos EOLN
        lineas();
        break;
      case Symbol.EOF:
        break;
      default:
        error();
    }
  }

  private static float linea(){
    switch (simbolo.token()){
      case Symbol.AP:
      case Symbol.MENOS:
      case Symbol.NUMERO:
        return t()+e();
      default:
        error();
    }
    return 0;
  }

  private static float f() {
    float res = 0;
    switch (simbolo.token()){
      case Symbol.NUMERO:
        res = Float.parseFloat(simbolo.lexema());
        simbolo = leer();
        break;
      case Symbol.AP:
        simbolo = leer();
        res = linea();
        simbolo = leer();
        break;
      case Symbol.MENOS:
        simbolo = leer(); // quitamos el menos
        return -f();
      default:
        error();
    }
    return res;
  }

  private static float e() {
    switch (simbolo.token()) {
      case Symbol.MAS:
        simbolo=leer();
        return t()+e();
      case Symbol.MENOS:
        simbolo=leer();
        return -t()+e();
      case Symbol.EOLN:
      case Symbol.CP:
        return 0;
      default: error();
    }
    return 0;
  }

  private static float t() {
    switch (simbolo.token()){
      case Symbol.AP:
      case Symbol.NUMERO:
      case Symbol.MENOS:
        return f()*q();
      default:
        error();
    }
    return 0;
  }

  private static float q() {
    switch (simbolo.token()){
      case Symbol.POR:
        simbolo = leer();
        return f()*q();
      case Symbol.DIV:
        simbolo = leer();
        return 1/f()*q();
      case Symbol.MAS:
      case Symbol.MENOS:
      case Symbol.EOLN:
      case Symbol.CP:
        return 1;
      default:
        error();
    }
    return 0;
  }

}
