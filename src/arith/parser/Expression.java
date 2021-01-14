package arith.parser;


import java.util.HashSet;

public class Expression {

	private final HashSet<String> numbers = new HashSet<>();
	
	private final HashSet<String> symbols  = new HashSet<>();;

	public HashSet<String> getNumbers() {
		return numbers;
	}

	
	public HashSet<String> getSymbols() {
		return symbols;
	}
	
	public Expression number(final String num) {
		numbers.add(num);
		return this;
	}
	
	public Expression symbol(final String sym) {
		symbols.add(sym);
		return this;
	}

	

	
	
	
}
