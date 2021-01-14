package arith.core;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

import arith.parser.Expression;
import arith.parser.Parser;

public class Main {

	Parser parser = Parboiled.createParser(Parser.class);
	
	
	public static void main(String[] args) {
		
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		
		String input = "-2 + 3 * -4 + 6 * 2 * 0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		System.out.println(exp.symbols.size());
		
		if (result.hasErrors()) {
            System.out.println("\nParse Errors:\n" + printParseErrors(result));
        }
		System.out.println("symbol "+ exp.symbols.toArray().length);
		for(String num:exp.symbols) {
			System.out.println(num +"position");
		}
		
		System.out.println("numb "+ exp.numbs.toArray().length);
		for(String num:exp.numbs) {
			System.out.println(num +"position");
		}
	}
}
