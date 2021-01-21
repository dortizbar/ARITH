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
		//return Sequence(symbolNum(),ZeroOrMore(Operation(),symbolNum()));
		return assignmentStep();
	}
	
	public Rule InputLine() {
		return Sequence(Expression(), EOI, push(input));
	}
	
	public Rule Operation() {
		return Sequence(ZeroOrMore(' '),AnyOf("+*^"),push(input.symbol(match())),ZeroOrMore(' '));
	}
	
	public Rule num() {
		return Sequence(ZeroOrMore('-'),push(input.symbol(match())), OneOrMore(CharRange('0', '9'),push(input.symbol(match()))));
	}
	
	public Rule symbolNum() {
		return Sequence(ZeroOrMore(' '),ZeroOrMore('-'),push(input.symbol(match())), OneOrMore(num()),push(input.symbol(match())));
	}
	public Rule statementIf() {
		return String("if");
	}
	public Rule statementElse() {
		return String("else");
	}
	public Rule statementThen() {
		return String("then");
	}
	public Rule statementEquals() {
		return String(":=");
	}
	public Rule statementWhile() {
		return String("while");
	}
	public Rule LeftPar() {
		return ZeroOrMore(Sequence(String("("),push(input.symbol(match()))));
	}
	public Rule RightPar() {
		return ZeroOrMore(Sequence(String(")"),push(input.symbol(match()))));
	}
	public Rule symbol() {
		
		return OneOrMore( ZeroOrMore("∧"), ZeroOrMore("="), ZeroOrMore("<"), ZeroOrMore(">"), ZeroOrMore("∨"),ZeroOrMore(":"));
	}
	
	public Rule variables() {
		return OneOrMore(Sequence(CharRange('a', 'z'),push(input.symbol(match()))));
	}
	public Rule assign() {
		return Sequence(String(":="),push(input.symbol(match())));
	}
	public Rule operationSymbols() {
		return Sequence(AnyOf("+*,-"),push(input.symbol(match())));
	}
	
	// VALUE ASSIGNATIONS
	public Rule assignmentStep() {
		return Sequence(variables(), ZeroOrMore(num()),ZeroOrMore(' '),assign(),ZeroOrMore(' '),LeftPar(),ZeroOrMore(' '),
				ZeroOrMore(variables()),ZeroOrMore(num()),
				ZeroOrMore(' '),
				ZeroOrMore(Sequence(operationSymbols(),ZeroOrMore(' '), ZeroOrMore(variables()),ZeroOrMore(num())),
						ZeroOrMore(' '),RightPar()));
	}
	

	
	public Rule Sentence() {
		return OneOrMore(ZeroOrMore(CharRange('a', 'z')),ZeroOrMore(num()), ZeroOrMore(String("true")), ZeroOrMore(String("false")),ZeroOrMore(' '));
	}
	
	public Rule ifRule() {
		return Sequence(String("if"),ZeroOrMore(' '), LeftPar(), ZeroOrMore(' '),
			Sentence(),ZeroOrMore(' '), ZeroOrMore(Sequence(symbol(),ZeroOrMore(' '), ZeroOrMore(Sentence()))),ZeroOrMore(' '), RightPar(), 
			String("else"));
		
	}
	
	public void elseRule() {
		
	}
	public Rule thenRule() {
		return Sequence(ZeroOrMore("while"), Sentence(), symbol() );
	}
	
	public Rule whileRule() {
		return null;
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
