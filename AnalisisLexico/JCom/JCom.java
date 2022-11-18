import java.io.FileReader;
import java.io.IOException;

//Prueba
public class JCom {

    protected static int comment1 = 0;
    protected static int comment2 = 0;
    protected static int comment3 = 0;
    
    public static void main(String arg[]) {
      
      if (arg.length>0) {
        try {

          Yylex lex = new Yylex(new FileReader(arg[0]));
          int token = -1;

          while ((token = lex.yylex()) != -1) {
            if (token == 1)  {
              comment1++;
            } else if (token == 2) {
              comment2++;
            } else if (token == 3) {
              comment3++;
            }
          }
        } catch (IOException e) {
        }
        System.out.println("//  "+comment1);
        System.out.println("/*  "+comment2);
        System.out.println("/** "+comment3);
      }
    }
/* prueba 12345 */
}
