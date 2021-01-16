package arith.parser;


import java.util.ArrayList;



@SuppressWarnings(value = { "all" })
public class Expression {

	
	public final ArrayList<String> symbols  = new ArrayList<String>() ;
	public final ArrayList<String> numbs  = new ArrayList<String>();

	
	public ArrayList<String> getSymbols() {
		return symbols;
	}
	
	
	public Expression symbol(final String sym) {
		if(sym.length() !=0) {
			symbols.add(sym);
		}
		
		return this;
	}

	public ArrayList<String> getNumbs() {
		return numbs;
	}
	
	
	public Expression numb(final String num) {
		if(num.length() != 0) {
			numbs.add(num);
		}
		
		return this;
	}
	

	
	
	
}
