package arith.core;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import arith.parser.Expression;
import arith.parser.Parser;
import arith.tree.AST;

public class Main {

	Parser parser = Parboiled.createParser(Parser.class);
	
	
	public static int result(String input) {
		Parser parser = Parboiled.createParser(Parser.class);
		
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		Expression exp = (Expression) result.resultValue;
		
		if (result.hasErrors()) {
            System.out.println("\nParse Errors:\n" + printParseErrors(result));
        }
		
		AST tree = new AST();
		tree.assembleTree(exp.symbols);
		tree.printTree(tree.getNodeRoot(), 0);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		//System.out.println("Operation ans: "+res);
		return res;
		
	}
	public static void main(String[] args) {
	
		if(args.length > 0) {
			String inputString = args[0];
			int operationTotal = result(inputString);
			System.out.println(operationTotal);
		}
		
		/*
		if(args.length > 0) {
            File file = new File(args[0]);
            BufferedReader reader;
    		try {
    			reader = new BufferedReader(new FileReader(file));
    			String line = reader.readLine();
    			while (line != null) {
    				if(line.contains("check")) {
    					System.out.println(line);
    				String toProcess = line.charAt(0)
    				line = reader.readLine();
    				}
    				
    			}
    			reader.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

         
        }
        */
		
		
	}
	
	
}
