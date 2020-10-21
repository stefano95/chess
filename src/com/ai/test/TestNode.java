package com.ai.test;

import com.ai.test.VisitationListener;
import com.gui.ChessFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import com.ai.ChildrenIterator;
import com.ai.Node;

public class TestNode implements Node {

	private int value;
	private LinkedList<Node> children = new LinkedList<Node>();
	private VisitationListener visitationListener;
	
	public TestNode(int value) {
		super();
		this.value = value;
	}
	
	public void addChild(Node child) {
		children.add(child);
	}

	@Override
	public ChildrenIterator getChildrenIterator() {
		return new ChildrenIteratorTest(children.iterator());
	}

	@Override
	public int getValue() {
		if (visitationListener != null) {
			visitationListener.onVisitation(this);
		}
		return value;
	}

	public void setVisitationListener(VisitationListener vListener) {
		this.visitationListener = vListener;
	}
}
