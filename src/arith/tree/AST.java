package arith.tree;

import java.util.ArrayList;

public class AST {
	
	private ASTNode nodeRoot;
	
	public AST() {
		nodeRoot = null;
	}
	
	public ASTNode getNodeRoot() {
		return nodeRoot;
	}

	public void setNodeRoot(ASTNode nodeRoot) {
		this.nodeRoot = nodeRoot;
	}
	
	public void assembleTree(ArrayList<String> symbols) {
		
		for(String sym: symbols) {
			AddRoot(sym);
		}
	}

	public ASTNode insert(ASTNode node, String value) {
		
		if(node == null) {
			return new ASTNode(value);
		}
		
		if(node.getSymbol().equals("-")){
			node.setSymbol("-"+value);
			return node;
		}
		
		
		if(value.equals("-")) {
			node.setRight(insert(node.getRight(), value));
			return node;
		}
		if(value.chars().allMatch(Character::isDigit)) {
		
			node.setRight(insert(node.getRight(), value));
			System.out.println("NUMBER: "+value);
				
		}
		
		
		if(value.equals("^")){
			if(node.getSymbol().contains("-")) {
				ASTNode expNode = new ASTNode(value);
				System.out.println("SUM SYMBOL neg: "+value);
				expNode.setLeft(node);
				return expNode;
			}
			if(node.getSymbol().chars().allMatch(Character::isDigit)) {
				ASTNode expNode = new ASTNode(value);
				System.out.println("SUM SYMBOL neg: "+value);
				expNode.setLeft(node);
				return expNode;
			}
			else {
			node.setRight(insert(node.getRight(), value));
			return node;
			}
			
		}
		
		
		if(value.equals("+")) {
			if(node.getSymbol().contains("-")) {
				ASTNode sumNode = new ASTNode(value);
				System.out.println("SUM SYMBOL neg: "+value);
				sumNode.setLeft(node);
				return sumNode;
			}
			
			if(node.getSymbol().contains("^")) {
				ASTNode sumNode = new ASTNode(value);
				System.out.println("SUM SYMBOL exp: "+value);
				sumNode.setLeft(node);
				return sumNode;
			}
			
			if(node.getSymbol().chars().allMatch(Character::isDigit)) {
				ASTNode sumNode = new ASTNode(value);
				System.out.println("SUM SYMBOL1: "+value);
				sumNode.setLeft(node);
				return sumNode;
			}
			if(node.getSymbol().equals("+")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				System.out.println("SUM SYMBOL2: "+value);
				return sumNode;
			}
			if(node.getSymbol().equals("*")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				System.out.println("SUM SYMBOL3: "+value);
				return sumNode;
			}
		}
		
		if(value.equals("*")) {
			if(node.getSymbol().equals("+")) {
				node.setRight(insert(node.getRight(), value));
			}
			if(node.getSymbol().chars().allMatch(Character::isDigit)) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				System.out.println("MULT SYMBOL1: "+value);
				return sumNode;
			}
			if(node.getSymbol().equals("*")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				System.out.println("MULT SYMBOL2: "+value);
				return sumNode;
			}
			if(node.getSymbol().contains("-")) {
				ASTNode sumNode = new ASTNode(value);
				System.out.println("SUM SYMBOL neg: "+value);
				sumNode.setLeft(node);
				return sumNode;
			}
			if(node.getSymbol().contains("^")) {
				ASTNode sumNode = new ASTNode(value);
				System.out.println("SUM SYMBOL exp: "+value);
				sumNode.setLeft(node);
				return sumNode;
			}
		}
		
		return node;
		
	}
	
	public void AddRoot(String value) {
		nodeRoot = insert(nodeRoot, value);
	}
	
	public void printTree(ASTNode node, int count) {
		if(node!=null) {
			System.out.println(node.getSymbol()+" Level: "+ count);
			count ++;
			printTree(node.getLeft(), count);
			
			printTree(node.getRight(), count);
		}
	}

	
	public static void main(String[] args) {
		
		String n = "3*8+-9^2*1";
		char[] m = n.toCharArray();
		
		AST tree = new AST();
		for(char ch: m) {
			System.out.print(ch);
			tree.AddRoot(String.valueOf(ch));
		}
		
		int count = 0;
		
		System.out.println("----------------------------------------------");
		System.out.println(tree.nodeRoot.getSymbol());
		System.out.println(tree.nodeRoot.getLeft().getSymbol());
		System.out.println(tree.nodeRoot.getRight().getRight().getSymbol());
		System.out.println("----------------------------------------------");
		System.out.println("----------------------------------------------Prueba 1------------------");
		tree.printTree(tree.nodeRoot, count);
		System.out.println("----------------------------------------------Prueba 2------------------");
		n = "2+3*4";
		m = n.toCharArray();
		tree = new AST();
		for(char ch: m) {
			System.out.print(ch);
			tree.AddRoot(String.valueOf(ch));
		}
		count=0;
		tree.printTree(tree.nodeRoot, count);
		System.out.println("----------------------------------------------Prueba 3------------------");
		n = "-2+3*-4+6*2*0";
		m = n.toCharArray();
		tree = new AST();
		for(char ch: m) {
			System.out.print(ch);
			tree.AddRoot(String.valueOf(ch));
		}
		count=0;
		tree.printTree(tree.nodeRoot, count);
		System.out.println("----------------------------------------------Prueba 4------------------");
		n = "3*8+9*10";
		m = n.toCharArray();
		tree = new AST();
		for(char ch: m) {
			System.out.print(ch);
			tree.AddRoot(String.valueOf(ch));
		}
		count=0;
		tree.printTree(tree.nodeRoot, count);
	}

}
