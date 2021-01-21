package arith.core;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import arith.parser.Expression;
import arith.parser.Parser;
import arith.tree.AST;

@SuppressWarnings(value = { "all" })
public class Main {

	Parser parser = Parboiled.createParser(Parser.class);
	
	public static void disableWarning() {
	    System.err.close();
	}
	public int result(String input) {
		
		Parser parser = Parboiled.createParser(Parser.class);
		
		ParsingResult<Expression> result = new ReportingParseRunner<Expression>(parser.InputLine()).run(input);
		
		Expression exp = (Expression) result.resultValue;
		
		AST tree = new AST();
		
		tree.assembleTree(exp.symbols);
		
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		
		return res;
		
	}
	
	public void testIf(String test) {
		Parser parser = Parboiled.createParser(Parser.class);
		
		ParsingResult<Expression> result = new ReportingParseRunner<Expression>(parser.InputLine()).run(test);
		
		Expression exp = (Expression) result.resultValue;
		
		System.out.print(exp.symbols.size());
		for(int i = 0; i< exp.symbols.size(); i++) {
			System.out.print(exp.symbols.get(i));
			
		}
	}
	public static void main(String[] args) {
		
			String test= "x := ( a + 1 )";
			Main m = new Main();
			m.testIf(test);
			
/*
			Main m = new Main();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String inputString = new String();
			
			try { 
				inputString = br.readLine();
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			int operationTotal = m.result(inputString);
			System.out.println(operationTotal);
	*/	
	}
	
	
}
