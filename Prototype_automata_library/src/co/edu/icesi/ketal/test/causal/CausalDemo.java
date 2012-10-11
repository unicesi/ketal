package co.edu.icesi.ketal.test.causal;

import org.jgroups.conf.ClassConfigurator;
import org.jgroups.protocols.Causal;
import org.jgroups.protocols.Causal.CausalHeader;
import org.jgroups.protocols.Causal.CausalNewViewHeader;

public class CausalDemo {

	public static void main(String[] args) {
		testTC02();
	}
	
	public static void testTC01(){
		NodeCausal nodeA = new NodeCausal("NodeA");
		NodeCausal nodeB = new NodeCausal("NodeB");
		NodeCausal nodeC = new NodeCausal("NodeC");
		
		
		try {
			
		nodeA.send("A", nodeC.getAddressNode());
		nodeB.send("B", nodeC.getAddressNode());
		nodeC.send("C", null);
		nodeB.send("B1", nodeC.getAddressNode());
			
			Thread.sleep(5000);
			
			System.out.println("NodeA: "+nodeA.linkedStrings());
			System.out.println("NodeB: "+nodeB.linkedStrings());
			System.out.println("NodeC: "+nodeC.linkedStrings());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testTC02()
	{
		NodeCausal nodeA = new NodeCausal("NodeA");
		NodeCausal nodeB = new NodeCausal("NodeB");
		NodeCausal nodeC = new NodeCausal("NodeC");
		
		
		try {
			
			nodeA.send("A", null);
			nodeA.send("A", null);
			nodeB.send("B", null);
			nodeB.send("B", null);
			nodeC.send("C", null);
			nodeC.send("C", null);
			
			Thread.sleep(5000);
			
			System.out.println("NodeA: "+nodeA.linkedStrings());
			System.out.println("NodeB: "+nodeB.linkedStrings());
			System.out.println("NodeC: "+nodeC.linkedStrings());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testTC03()
	{
		NodeCausal nodeA = new NodeCausal("NodeA");
		
		try {
			
		nodeA.send("A", null);
			
			Thread.sleep(2000);
			
			System.out.println("NodeA: "+nodeA.linkedStrings());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
