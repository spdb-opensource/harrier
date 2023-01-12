package cn.spdb.harrier.common.test;

import org.junit.Test;

import cn.spdb.harrier.common.model.DAG;

public class DagTest {

	private static DAG<String, String, Integer> dag=new DAG<String, String, Integer>();
	
	@Test
	public void load() {
		dag.addNode("A", "A1");
		dag.addNode("B", "B2");
		dag.addNode("C", "C3");
		dag.addNode("D", "D4");
		dag.addNode("E", "E5");
		dag.addNode("F", "F5");
		
		dag.addEdge("A", "B", 1, false);
		dag.addEdge("A", "C", 2, false);
		dag.addEdge("B", "D", 3, false);
		dag.addEdge("C", "D", 4, false);
		dag.addEdge("D", "E", 5, false);
		
		System.out.println(dag.getBeginNode());
		System.out.println(dag.getEndNode());
		System.out.println(dag.getInDegree("D"));
		System.out.println(dag.getPreviousNodes("D"));
		System.out.println(dag.getSubsequentNodes("D"));
		
	}

}
