package arith.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;


@SuppressWarnings(value = { "all" })
@BuildParseTree
public class Parser extends BaseParser<Expression>{

	Expression input = new Expression();
	
	public Rule Expression() {
		//System.out.println("Expresion parser");
		return Sequence(symbolNum(),ZeroOrMore(Operation(),symbolNum()));
		
		
		//Sequence(ZeroOrMore(' '),symbolNum(),OneOrMore(' '),OneOrMore( ZeroOrMore(' '),AnyOf("+*^"),OneOrMore(' '), symbolNum()),  push(input.symbol(match())));
	
	}
	
	public Rule InputLine() {
		return Sequence(Expression(), EOI, push(input));
	}
	
	public Rule Operation() {
		return Sequence(ZeroOrMore(' '),AnyOf("+*^"),push(input.symbol(match())),ZeroOrMore(' '));
	}
	public Rule Mult() {
		return Ch('*');
	}
	public Rule exp() {
		return Ch('^');
	}
	public Rule num() {
		return CharRange('0', '9');
	}
	//test on ending
	public Rule symbolNum() {
		return Sequence(ZeroOrMore(' '),ZeroOrMore('-'),push(input.symbol(match())), OneOrMore(num()),push(input.symbol(match())));
	}
	
	//Taken for parboiled example
	Rule WhiteSpace() {
	        return ZeroOrMore(AnyOf(" \t\f"));
	    }
	 @Override
	    protected Rule fromStringLiteral(String string) {
	        return string.endsWith(" ") ?
	                Sequence(String(string.substring(0, string.length() - 1)), WhiteSpace()) :
	                String(string);
	    }
	
	
	
	
	
}
