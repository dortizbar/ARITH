package arith.core;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

import arith.parser.Expression;
import arith.parser.Parser;
import arith.tree.AST;

public class Main {

	Parser parser = Parboiled.createParser(Parser.class);
	
	
	public static void main(String[] args) {
		
		Parser parser = Parboiled.createParser(Parser.class);
		
		
						
		String input = "3 * 8 + 9 * 10";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		System.out.println(exp.symbols.size());
		
		if (result.hasErrors()) {
            System.out.println("\nParse Errors:\n" + printParseErrors(result));
        }
		
		
	/*	System.out.println("numb "+ exp.numbs.toArray().length);
		for(String num:exp.numbs) {
			System.out.print("position");
		}
	**/	
		AST tree = new AST();
		tree.assembleTree(exp.symbols);
		tree.printTree(tree.getNodeRoot(), 0);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		System.out.println(res);
	}
	
	
}
