package arith.core;

import arith.tree.AST;
import arith.tree.ASTNode;

public class Interpreter {

	public final int eval(AST tree) {
		
		ASTNode nodeType = tree.getNodeRoot();
		
		if(nodeType.getSymbol().contains("-")) {
			return Integer.parseInt(nodeType.getSymbol());
		}
		
		if(nodeType.getSymbol().chars().allMatch(Character::isDigit)) {
			return Integer.parseInt(nodeType.getSymbol());
		}
		
		if(nodeType.getSymbol().equals("+")) {
			ASTNode n1 = nodeType.getLeft();
			tree.setNodeRoot(n1);
			int left = eval(tree);
			ASTNode n2 = nodeType.getRight();
			tree.setNodeRoot(n2);
			int right = eval(tree);
			return (left+right);
		}
		if(nodeType.getSymbol().equals("*")) {
			ASTNode n1 = nodeType.getLeft();
			tree.setNodeRoot(n1);
			int left = eval(tree);
			ASTNode n2 = nodeType.getRight();
			tree.setNodeRoot(n2);
			int right = eval(tree);
			return (left*right);
		}
		if(nodeType.getSymbol().equals("^")) {
			ASTNode n1 = nodeType.getLeft();
			tree.setNodeRoot(n1);
			int left = eval(tree);
			ASTNode n2 = nodeType.getRight();
			tree.setNodeRoot(n2);
			int right = eval(tree);
			return (left*right);
		}
		
	
		return 0;
		
	}
}
