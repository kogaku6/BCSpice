package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuit {
	public static List<CircuitNode> nodeIDs=new ArrayList<CircuitNode>();
	public static List<Element> elementIDs =new ArrayList<Element>();
	public static Map<Integer, List<Element>> groupIDs =new HashMap<Integer, List<Element>>();
	
	public static int[][] field= new int[30][30];
	
	public static boolean isSimulating=false;
//	public static Integer chosingNode=null;
//	public static SimpleObjectProperty<Color> color=new SimpleObjectProperty<Color>();
//	public static SimpleObjectProperty<Effect> effect=new SimpleObjectProperty<Effect>();

	//elementIDsの空いている番号で最小のものを取得
	public static int getMinElementID() {
		int num=0;
		if(!elementIDs.isEmpty()) {
			for(num=1; num<elementIDs.size(); num++) {
				if(elementIDs.get(num)==null) {
					break;
				}
			}
		}
		return num;
	}
	
	//nodeIDsの空いている番号で最小のものを取得
	public static int getMinNodeID() {
		int num=0;
		if(!nodeIDs.isEmpty()) {
			for(; num<nodeIDs.size(); num++) {
				if(nodeIDs.get(num)==null) {
					break;
				}
			}
		}
		return num;
	}
	
	//elementIDsの空いている最小の番号にelementを割り振る
	public static void addElement(Element el) {
		el.setID(getMinElementID());
		elementIDs.add(getMinElementID(), el);
	}
	
	//nodeIDsの空いている最小の番号にCircuitNodeを割り振る
	public static void addNode(CircuitNode cn, String type) {
		System.out.println("minID:"+getMinNodeID());
		cn.setID(getMinNodeID());
		nodeIDs.add(getMinNodeID(), cn);
		cn.setElementtype(type);
	}
	
	//接点が重なっているか判定
	public static void isIntersectNodes(int ID) {
		CircuitNode node1=nodeIDs.get(ID);
		for(int i=0; i<nodeIDs.size(); i++) {
			if(ID!=i) {
				CircuitNode node2=nodeIDs.get(i);
//				double x1=element.getGroup().getLayoutX()+node1.getLayoutX();
				
				double distance=Math.sqrt(Math.pow(node1.getLayoutX()-node2.getLayoutX(), 2)+Math.pow(node1.getLayoutY()-node2.getLayoutY(), 2));
				System.out.println(ID+":"+node1.getLayoutX()+", "+node1.getLayoutY()+i+":"+node2.getLayoutX()+", "+node2.getLayoutY());
				System.out.println(ID+"と"+i+":"+distance);
				if(distance<3) {
					System.out.println(nodeIDs.get(i).getID()+"番目と重なってるよ");
				}
			}
		}
	}

	//keyに対応したElementを取得
	public static void putElement(Integer key, Element element) {
		if(groupIDs.get(key)!=null) {

		}
		else {
			List<Element> list=new ArrayList<Element>();
			list.add(element);
			groupIDs.put(key, list);
		}
	}

	//使う予定なし
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
