package arith.tree;

import java.util.ArrayList;

public class AST {
	
	private ASTNode nodeRoot;
	private ArrayList<String> printTree;
	
	public AST() {
		nodeRoot = null;
		setPrintTree(new ArrayList<String>());
	}
	
	public ASTNode getNodeRoot() {
		return nodeRoot;
	}
	
	public ArrayList<String> getPrintTree() {
		return printTree;
	}

	public void setPrintTree(ArrayList<String> printTree) {
		this.printTree = printTree;
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
				
		}
		
		
		if(value.equals("^")){
			if(node.getSymbol().contains("-")) {
				ASTNode expNode = new ASTNode(value);
				expNode.setLeft(node);
				return expNode;
			}
			if(node.getSymbol().chars().allMatch(Character::isDigit)) {
				ASTNode expNode = new ASTNode(value);
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
				sumNode.setLeft(node);
				return sumNode;
			}
			
			if(node.getSymbol().contains("^")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				return sumNode;
			}
			
			if(node.getSymbol().chars().allMatch(Character::isDigit)) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				return sumNode;
			}
			if(node.getSymbol().equals("+")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				return sumNode;
			}
			if(node.getSymbol().equals("*")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
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
				return sumNode;
			}
			if(node.getSymbol().equals("*")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				return sumNode;
			}
			if(node.getSymbol().contains("-")) {
				ASTNode sumNode = new ASTNode(value);
				sumNode.setLeft(node);
				return sumNode;
			}
			if(node.getSymbol().contains("^")) {
				ASTNode sumNode = new ASTNode(value);
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
			printTree.add(node.getSymbol()+" Level: "+ count);
			count ++;
			printTree(node.getLeft(), count);
			
			printTree(node.getRight(), count);
		}
	}

	
	



}
