package arith.parser;


import java.util.ArrayList;
import java.util.HashSet;

import com.sun.tools.javac.util.List;

public class Expression {

	
	public final ArrayList<String> symbols  = new ArrayList<String>() ;
	public final ArrayList<String> numbs  = new ArrayList<String>();

	
	public ArrayList<String> getSymbols() {
		return symbols;
	}
	
	
	public Expression symbol(final String sym) {
		symbols.add(sym);
		return this;
	}

	public ArrayList<String> getNumbs() {
		return numbs;
	}
	
	
	public Expression numb(final String num) {
		numbs.add(num);
		return this;
	}
	

	
	
	
}
