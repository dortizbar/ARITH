package arith.interpreter;


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

	public ASTNode insert(ASTNode node, String value) {
		
		if(node == null) {
			return new ASTNode(value);
		}
		if(value.chars().allMatch(Character::isDigit)) {
		
			node.setRight(insert(node.getRight(), value));
			System.out.println("NUMBER: "+value);
				
		}		
		
		if(value.equals("+")) {
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
		}
		
		return node;
		
	}
	
	public void AddRoot(String value) {
		nodeRoot = insert(nodeRoot, value);
	}
	
	public void printTree(ASTNode node) {
		if(node!=null) {
			printTree(node.getLeft());
			System.out.println(node.getSymbol());
			printTree(node.getRight());
		}
	}

	
	public static void main(String[] args) {
		
		String n = "3*8+9*1";
		char[] m = n.toCharArray();
		
		AST tree = new AST();
		for(char ch: m) {
			System.out.print(ch);
			tree.AddRoot(String.valueOf(ch));
		}
		
		System.out.println("----------------------------------------------");
		System.out.println(tree.nodeRoot.getSymbol());
		System.out.println(tree.nodeRoot.getLeft().getSymbol());
		System.out.println(tree.nodeRoot.getRight().getSymbol());
		System.out.println("----------------------------------------------");
		tree.printTree(tree.nodeRoot);
		
		
	}

}
