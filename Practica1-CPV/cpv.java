import java.io.FileReader;
import java.io.IOException;

public class cpv {
    protected static int A = 0;
    protected static int B = 0;
    protected static int C = 0;
    protected static int D = 0;
    
    public static void main(String arg[]) {
    
        if (arg.length>0) {
          try {
            Yylex lex = new Yylex(new FileReader(arg[0]));
            Yytoken token = null;
            while (  (token = lex.yylex()) != null  ) {
              switch (token.getToken()) {
                case 'A':
                  cpv.A+=1;
                  break;
                case 'B':
                  cpv.B+=1;
                  break;
                case 'C':
                  cpv.C+=1;
                  break;
                case 'D':
                  cpv.D+=1;
              }
            }
            System.out.println("A "+cpv.A);
            System.out.println("B "+cpv.B);
            System.out.println("C "+cpv.C);
            System.out.println("D "+cpv.D);
          } catch (IOException e) { }
        }
    }

}
