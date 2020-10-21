package com.ai.test;

import com.ai.MinMax;

public class MinMaxTest {

	public static void main(String[] args) {
		System.out.println("Decision Making 0: " + testDecisionMaking0());
		System.out.println("Decision Making 1: " + testDecisionMaking1());
		System.out.println("Alpha Prunning 0: " + testPrunning0());
	}
	
	public static boolean testDecisionMaking0() {
		TestNode root = new TestNode(0);
		TestNode child0 = new TestNode(0);
		TestNode child1 = new TestNode(3);
		root.addChild(child0);
		root.addChild(child1);
		TestNode child00 = new TestNode(0);
		TestNode child01 = new TestNode(1);
		TestNode child10 = new TestNode(5);
		TestNode child11 = new TestNode(10);
		TestNode child12 = new TestNode(20);
		child0.addChild(child00);
		child0.addChild(child01);
		child1.addChild(child10);
		child1.addChild(child11);
		child1.addChild(child12);
		
		MinMax minmax = new MinMax(2);
		TestNode bestChild = (TestNode) minmax.getBestChild(root);
		
		return bestChild == child0;
	}
	
	public static boolean testPrunning0() {
		TestNode root = new TestNode(0);
		TestNode child0 = new TestNode(0);
		TestNode child1 = new TestNode(3);
		root.addChild(child0);
		root.addChild(child1);
		TestNode child00 = new TestNode(0);
		TestNode child01 = new TestNode(1);
		TestNode child10 = new TestNode(5);
		TestNode child11 = new TestNode(10);
		TestNode child12 = new TestNode(20);
		child0.addChild(child00);
		child0.addChild(child01);
		child1.addChild(child10);
		child1.addChild(child11);
		child1.addChild(child12);
		
		VisitationListener vListener = new VisitationListener();
		child11.setVisitationListener(vListener);
		child12.setVisitationListener(vListener);
		
		MinMax minmax = new MinMax(2);
		minmax.getBestChild(root);
		
		return vListener.getVisitedNodes().isEmpty();
	}
	
	public static boolean testDecisionMaking1() {
		TestNode root = new TestNode(0);
		TestNode child0 = new TestNode(0);
		TestNode child1 = new TestNode(3);
		root.addChild(child0);
		root.addChild(child1);
		
		TestNode child00 = new TestNode(0);
		child0.addChild(child00);
		TestNode child01 = new TestNode(1);
		child0.addChild(child01);
		
		TestNode child000 = new TestNode(0);
		child00.addChild(child000);
		TestNode child0000 = new TestNode(3);
		child000.addChild(child0000);
		
		TestNode child010 = new TestNode(0);
		child01.addChild(child010);
		TestNode child0100 = new TestNode(0);
		child010.addChild(child0100);
		
		TestNode child10 = new TestNode(2);
		child1.addChild(child10);
		
		TestNode child100 = new TestNode(0);
		child10.addChild(child100);
		
		TestNode child1000 = new TestNode(10);
		child100.addChild(child1000);
		
		MinMax minmax = new MinMax(4);
		TestNode bestChild = (TestNode) minmax.getBestChild(root);
		
		return bestChild == child0;
	}
}


