package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuit {
	public static Map<Integer, CircuitNode> circuitIDs=new HashMap<Integer, CircuitNode>();
	public static Map<Integer, Element> elementIDs =new HashMap<Integer, Element>();
	public static Map<Integer, List<Element>> groupIDs =new HashMap<Integer, List<Element>>();
	
	public static int[][] field= new int[30][30];
	
	public static boolean isSimulating=false;
//	public static Integer chosingNode=null;
//	public static SimpleObjectProperty<Color> color=new SimpleObjectProperty<Color>();
//	public static SimpleObjectProperty<Effect> effect=new SimpleObjectProperty<Effect>();

	public static int getMinID() {
		int num=0;
		if(!elementIDs.isEmpty()) {
			for(num=1;num<elementIDs.size();num++) {
				if(elementIDs.get(num)==null) {
					break;
				}
			}
		}
		return num;
	}

	public static void putElement(Integer key, Element element) {
		if(groupIDs.get(key)!=null) {

		}
		else {
			List<Element> list=new ArrayList<Element>();
			list.add(element);
			groupIDs.put(key, list);
		}
	}

	public static boolean isCirculation(Integer id) {
//		System.out.println("今の素子idは"+elementIDs.get(id).getID());
//		System.out.println("次の素子idは"+elementIDs.get(id).getNextIDs());
//
//		if(elementIDs.get(id).getNextIDs().size()>0) {
//			if(elementIDs.get(id).getNextIDs()==id) {
//				return true;
//			}
//			else {
//				return Circuit.isCirculation(id);
//			}
//		}
//		else {
//			return false;
//		}
		return false;
	}
}
