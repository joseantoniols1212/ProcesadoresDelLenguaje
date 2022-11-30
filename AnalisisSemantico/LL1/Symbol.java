public class Symbol {
	public final static int MAS = 1;
  public final static int MENOS = 2;
	public final static int POR= 3;
	public final static int DIV= 4;
	public final static int AP= 5;
	public final static int CP= 6;
	public final static int PYC=7;
	public final static int NUMERO=8;
	public final static int EOLN=9;
	public final static int EOF=-1;
	
	private int token;
	private String lexema;
	
	public Symbol(int token, String lexema) {
		this.token = token;
		this.lexema = lexema;
	}
	
	public Symbol(int token) {
		this(token,null);
	}
	
	public int token() {
		return token;
	}
	
	public String lexema() {
		return lexema;
	}
}
