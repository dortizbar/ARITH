package arith.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;

@BuildParseTree
public class Parser extends BaseParser<Expression>{

	Expression input = new Expression();
	
	public Rule Expression() {
		return Sequence(OneOrMore(symbolNum()), AnyOf("+*^"));
	}
	
	public Rule InputLine() {
		return Sequence(Expression(), EOI);
	}
	
	public Rule Sum() {
		return Ch('+');
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
		return Sequence(ZeroOrMore('-'), OneOrMore(num()), OneOrMore(' ') );
	}
	
	
	
	
	
}
