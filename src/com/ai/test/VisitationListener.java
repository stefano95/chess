package com.ai.test;


import java.util.LinkedList;
import java.util.List;

public class VisitationListener {

	private List<TestNode> visitedNodes = new LinkedList<TestNode>();
	
	public void onVisitation(TestNode visitedNode) {
		visitedNodes.add(visitedNode);
	}
	
	public List<TestNode> getVisitedNodes() {
		return visitedNodes;
	}
}
