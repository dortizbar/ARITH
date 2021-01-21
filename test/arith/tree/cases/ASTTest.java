package arith.tree.cases;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;

import arith.core.Interpreter;
import arith.parser.Expression;
import arith.parser.Parser;
import arith.tree.AST;
import arith.tree.ASTNode;

public class ASTTest {

	private AST tree;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		tree = new AST();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetNodeRoot() {
		assertNull(tree.getNodeRoot());
		tree.AddRoot("10");
		assertEquals(tree.getNodeRoot().getSymbol(), "10");
		assertNull(tree.getNodeRoot().getRight());
		assertNull(tree.getNodeRoot().getLeft());
	}

	@Test
	public void testSetNodeRoot() {
		ASTNode n  = new ASTNode("20");
		tree.setNodeRoot(n);
		assertNull(tree.getNodeRoot().getLeft());
		assertNull(tree.getNodeRoot().getRight());
		assertEquals(tree.getNodeRoot().getSymbol(), "20");
		 
	}

	@Test
	public void testAssembleTree() {
		ArrayList<String> tArray = new ArrayList<String>();
		String exam = "0+-2^2*8+-5";
		for(char ch: exam.toCharArray()) {			
			tArray.add(String.valueOf(ch));
		}
		tree.assembleTree(tArray);
		assertEquals(tree.getNodeRoot().getSymbol(),"+" );
		assertEquals(tree.getNodeRoot().getRight().getSymbol(),"-5" );
		assertEquals(tree.getNodeRoot().getLeft().getSymbol(),"+" );
		assertEquals(tree.getNodeRoot().getLeft().getRight().getSymbol(),"*" );
		assertEquals(tree.getNodeRoot().getLeft().getRight().getLeft().getSymbol(),"^" );
	
		tArray = new ArrayList<String>();
		 exam = "5^2+-2*8*5";
		for(char ch: exam.toCharArray()) {			
			tArray.add(String.valueOf(ch));
		}
		tree = new AST();
		tree.assembleTree(tArray);
		tree.printTree(tree.getNodeRoot(), 0);
		
		assertEquals(tree.getNodeRoot().getSymbol(),"+" );
		assertEquals(tree.getNodeRoot().getRight().getSymbol(),"*" );
		assertEquals(tree.getNodeRoot().getLeft().getSymbol(),"^" );
		assertEquals(tree.getNodeRoot().getLeft().getRight().getSymbol(),"2" );
		
	}


	@Test
	public void testPrintTree() {
		ArrayList<String> tArray = new ArrayList<String>();
		String exam = "0+2^2*8+-5";
		for(char ch: exam.toCharArray()) {			
			tArray.add(String.valueOf(ch));
		}
		tree.assembleTree(tArray);
		tree.printTree(tree.getNodeRoot(), 0);
		assertEquals(tree.getPrintTree().size(), 9);
		assertEquals(tree.getPrintTree().get(4), "^ Level: 3");
	}
	@Test
	public void shouldParseBasicTree() {
		String toSave = "1+2";
		char[] arr = toSave.toCharArray();
		for(char ch: arr) {
			tree.AddRoot(String.valueOf(ch));
		}
		assertEquals(tree.getNodeRoot().getSymbol(), "+");
		assertEquals(tree.getNodeRoot().getSymbol(), "+");
		assertEquals(tree.getNodeRoot().getLeft().getSymbol(), "1");
		assertEquals(tree.getNodeRoot().getRight().getSymbol(), "2");
		
	}
	
	@Test
	public void shouldParse2LevelTree() {
		String toSave = "5+10*4";
		char[] arr = toSave.toCharArray();
		for(char ch: arr) {
			tree.AddRoot(String.valueOf(ch));
		}
		
		assertEquals(tree.getNodeRoot().getSymbol(), "+");
		assertEquals(tree.getNodeRoot().getLeft().getSymbol(), "5");
		assertNull(tree.getNodeRoot().getLeft().getLeft());
		assertEquals(tree.getNodeRoot().getLeft().getSymbol(), "5");
		assertEquals(tree.getNodeRoot().getRight().getSymbol(), "*");
		
	}

	@Test
	public void shouldParse3LevelTree() {
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		
		String input = "0 * 0 + 10 + -0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		assertEquals(tree.getNodeRoot().getSymbol(), "+");
		assertEquals(tree.getNodeRoot().getLeft().getSymbol(), "+");
		assertEquals(tree.getNodeRoot().getLeft().getLeft().getSymbol(), "*");
		assertEquals(tree.getNodeRoot().getLeft().getLeft().getLeft().getSymbol(), "0");
		assertEquals(tree.getNodeRoot().getLeft().getLeft().getRight().getSymbol(), "0");
		assertEquals(tree.getNodeRoot().getLeft().getRight().getSymbol(),"10");
		assertNull(tree.getNodeRoot().getLeft().getLeft().getLeft().getLeft());
		assertEquals(tree.getNodeRoot().getRight().getSymbol(), "-0");
		
	}
	
	@Test
	public void easy_1(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "2 + 3";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 5);
	}
	@Test
	public void easy_2(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "3 + 92";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 95);
	}
	@Test
	public void easy_3(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "100 + 0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 100);
	}
	@Test
	public void easy_4(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-1 + -3";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, -4);
	}
	@Test
	public void easy_5(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "10 + -3";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 7);
	}
	@Test
	public void easy_6(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-1 + 0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, -1);
	}
	@Test
	public void easy_7(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "99 + 3 + 12 + 2";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 116);
	}
	@Test
	public void easy_8(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "2 + 3 + 4 + -1";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 8);
	}
	@Test
	public void easy_9(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-1 + -2 + 3";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 0);
	}
	@Test
	public void easy_10(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-1 + -5 + -1";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, -7);
	}
	@Test
	public void easy_11(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "9 * 3";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 27);
	}
	@Test
	public void easy_12(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-3 * 4";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, -12);
	}
	@Test
	public void easy_13(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "0 * 2";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 0);
	}
	@Test
	public void easy_14(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "20 * 5";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 100);
	}
	@Test
	public void easy_15(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "0 * 2";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 0);
	}
	@Test
	public void easy_16(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-2 * -0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 0);
	}
	@Test
	public void easy_17(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "2 * 3 * 4 * 1000";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 24000);
	}
	@Test
	public void easy_18(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "1 * -2 * 3 * -4";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 24);
	}
	@Test
	public void easy_19(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "9 * 2 * 99 * 999";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 1780218);
	}
	@Test
	public void easy_20(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "1 * 1 * -1 * 1";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, -1);
	}
	@Test
	public void medium_1(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "2 + 3 * 4";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 14);
	}
	@Test
	public void medium_2(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-2 + 3 * -4 + 6 * 2 * 0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, -14);
	}
	@Test
	public void medium_3(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "3 * 8 + 9 * 10";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 114);
	}
	@Test
	public void medium_4(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "5 * 6 + 9";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 39);
	}
	@Test
	public void medium_5(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "5 * 8 + 6 * 4 + -2";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 62);
	}
	@Test
	public void medium_6(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "-10 * 4 + 3 * 6 + 8";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, -14);
	}
	@Test
	public void medium_7(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "100 + -100 * 0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 100);
	}
	@Test
	public void medium_8(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "0 * 0 + 0 + -0";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 0);
	}
	
	@Test
	public void medium_9(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "2 * 4 * -2 + 3 * 8";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 8);
	}
	@Test
	public void medium_10(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "2 + 3";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(5, 5);
	}
	@Test
	public void hard_1(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "2^2 + 5 * 2 + -1";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 13);
	}
	@Test
	public void hard_2(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "1 + 2 + 3 + 4^3";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 70);
	}
	@Test
	public void hard_3(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "5 * 1 * 16^2 + 30";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 1310);
	}
	@Test
	public void hard_4(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "95 + -0 * 86 * 2";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 95);
	}
	@Test
	public void hard_5(){
		Parser parser = Parboiled.createParser(Parser.class);
		
		
		String input = "5 + 20 * 7 + 3 * 5 + 10^2";
		ParsingResult<?> result = new RecoveringParseRunner<Expression>(parser.InputLine()).run(input);
		
		
		Expression exp = (Expression) result.resultValue;
		
		tree.assembleTree(exp.symbols);
		
		Interpreter inter = new Interpreter();
		int res = inter.eval(tree);
		assertEquals(res, 260);
	}
}
