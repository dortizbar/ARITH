package arith.tree;

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

import arith.parser.Expression;
import arith.parser.Parser;

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
		System.out.print(tree.getPrintTree().toString());
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
}
