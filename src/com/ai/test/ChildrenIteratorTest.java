package com.ai.test;

import java.util.Iterator;

import com.ai.ChildrenIterator;
import com.ai.Node;

public class ChildrenIteratorTest implements ChildrenIterator{
	private Iterator<Node> iter;
	
	public ChildrenIteratorTest(Iterator<Node> iter) {
		super();
		this.iter = iter;
	}

	@Override
	public boolean hasNext() {
		return iter.hasNext();
	}

	@Override
	public Node getNext() {
		return iter.next();
	}

	@Override
	public void clearLast() {
	}

}
