import java.io.FileReader;
import java.io.IOException;

public class srt {

    // private static int num_subs = 0;
    // private static int tiempo_total = 0;
    // private static int num_lineas = 0;
    // private static int num_palabras = 0;
    //
    // public static void incrementarSubs(int inc){
    //   num_subs+=inc;
    // }
    //
    // public static void incrementarTiempo(int inc){
    //   tiempo_total+=inc;
    // }
    //
    // public static void incrementarLineas(int inc){
    //   num_lineas+=inc;
    // }
    //
    // public static void incrementarPalabras(int inc){
    //   num_palabras+=inc;
    // }

    public static void main(String arg[]) {
      if (arg.length>0) {
        Yylex lex = null;
        try {
          lex = new Yylex(new FileReader(arg[0]));
          while (lex.yylex() != -1);
          lex.sout();
        } catch (IOException e) {
        } 
      }
    }

}
