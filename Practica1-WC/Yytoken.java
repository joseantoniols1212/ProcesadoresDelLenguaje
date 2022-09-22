public class Yytoken {
    public final static int PALABRA = 1;
    public final static int LINEA = 2;
    public final static int CARACTER = 3;

    private int token;
    private String valor;

    public Yytoken(int token, String valor) {
         this.token = token;
         this.valor = valor;
    }

    public int getToken()  {
         return token;
    }

    public String getValor()  {
         return valor;
    }

    public String toString() {
         return "<"+token+","+valor+">";
    }
}
