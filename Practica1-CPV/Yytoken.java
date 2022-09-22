public class Yytoken {
    public final static int NUMERO = 127;
    public final static int MAS = 128;
    public final static int EOLN = 10;

    private int token;
    private String valor;

    public Yytoken(char token, String valor) {
         this.token = token;
         this.valor = valor;
    }

    public int getToken()  {
         return token;
    }

    public String toString() {
         return "<"+token+","+valor+">";
    }
}
