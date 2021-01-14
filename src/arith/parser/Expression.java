package arith.parser;


import java.util.HashSet;

public class Expression {

	
	public final HashSet<String> symbols  = new HashSet<>();
	public final HashSet<String> numbs  = new HashSet<>();

	
	public HashSet<String> getSymbols() {
		return symbols;
	}
	
	
	public Expression symbol(final String sym) {
		symbols.add(sym);
		return this;
	}

	public HashSet<String> getNumbs() {
		return symbols;
	}
	
	
	public Expression numb(final String num) {
		numbs.add(num);
		return this;
	}
	

	
	
	
}
