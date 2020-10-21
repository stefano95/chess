package com.ai;

public class MinMax {

	public static final int GAME_WINNING_VALUE = 500000;
	private int depth;
	
	public MinMax(int depth) {
		super();
		this.depth = depth;
	}
	
	public Node getBestChild (Node current) {
		return getMax (current, 0, Integer.MIN_VALUE, 0).maxChild;
	}
	
	private Result getMax (Node current, int lvl, int alpha, int parentValue) {
		Result result = new Result();
		result.value = Integer.MIN_VALUE;
		
		ChildrenIterator iter = current.getChildrenIterator();     // iterate through the all possible moves
		while (iter.hasNext()) {
			Node child = iter.getNext();
			int currentValue = child.getValue();
			
			if (currentValue >= GAME_WINNING_VALUE) {
				result.maxChild = child;
				result.value = currentValue;
				iter.clearLast();
				break;
			}
			//checks if the level of prediction is reached
			if (lvl + 1 < depth) {
				Result tmp = getMax(child, lvl + 1, result.value, currentValue);
				currentValue -= tmp != null ? tmp.value : 0; //it predicts the opponent's best move and subtracts it
			}
			//check if the currentValue is better than the best one so far
			if (result.value < currentValue) {
				result.maxChild = child;
				result.value = currentValue;
			}
			//reinstate the state of the board to the state where the iteration has started.
			iter.clearLast();
			
			//alpha is parent's siblings best value. If a worse value than the alpha is found the algorithm stops. 
			if (alpha > (parentValue - currentValue)) {
				break;
			}
		}	
		return result;
	}
	// 2 parameters are taken as a result of the recursion - the best move and the value of that move
	private class Result {
		Node maxChild;
		int value;
	}
}
