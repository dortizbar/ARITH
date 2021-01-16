package arith.core;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import static org.parboiled.errors.ErrorUtils.printParseErrors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
		//System.out.println("Punto Despues Parser");
		Expression exp = (Expression) result.resultValue;
		//System.out.println("Punto expr");
	
	//	if (result.hasErrors()) {
    //        System.out.println("\nParse Errors:\n" + printParseErrors(result));
    //    }
	
		AST tree = new AST();
		
		tree.assembleTree(exp.symbols);
		//System.out.println("Ensamblado");
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		//System.out.println("Evaluado");
		
		return res;
		
	}
	public static void main(String[] args) {
		
			Main m = new Main();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String inputString = new String();
			//inputString = "afuera try";
			try { //inputString = "en try";
				//System.out.println(br.readLine() + inputString);
				inputString = br.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

			
			int operationTotal = m.result(inputString);
			System.out.println(operationTotal);
		
				
		
	}
	
	
}
