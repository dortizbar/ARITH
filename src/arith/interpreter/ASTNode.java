package arith.interpreter;

public class ASTNode {
	
	private String symbol;
	
	private ASTNode right;
	
	private ASTNode left;

	public ASTNode(String symbol) {
		
		this.symbol=symbol;
		right= null;
		left = null;
		
	}
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public ASTNode getRight() {
		return right;
	}

	public void setRight(ASTNode right) {
		this.right = right;
	}

	public ASTNode getLeft() {
		return left;
	}

	public void setLeft(ASTNode left) {
		this.left = left;
	}
	
	

}
