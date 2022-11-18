public class Yytoken {

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
