import java.io.FileReader;
import java.io.IOException;

public class wc {
    protected static int lineas = 0;
    protected static int caracteres = 0;
    protected static int palabras = 0;
    
    public static void main(String arg[]) {
    
        if (arg.length>0) {
          try {
            Yylex lex = new Yylex(new FileReader(arg[0]));
            Yytoken token = null;
            while (  (token = lex.yylex()) != null  ) {
              if(token.getToken() == token.PALABRA) {
                wc.palabras++;
                wc.caracteres+=token.getValor().length();
              }
              else if(token.getToken() == token.CARACTER) wc.caracteres++;
              else if(token.getToken() == token.LINEA) {
                wc.lineas++;
                wc.caracteres++;
              }
            }
            System.out.println(" "+wc.lineas+"  "+wc.palabras+" "+wc.caracteres+" "+arg[0]);
          } catch (IOException e) { }
        }
    }

}
