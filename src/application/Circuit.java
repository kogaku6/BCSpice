package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Circuit {
	public static Map<Integer, CircuitNode> nodeIDs=new HashMap<Integer, CircuitNode>();
	public static Map<Integer, Element> elementIDs =new HashMap<Integer, Element>();
	public static Map<Integer, List<Element>> groupIDs =new HashMap<Integer, List<Element>>();

//	public static int[][] field= new int[30][30];

	public static boolean isSimulating=false;
//	public static Integer chosingNode=null;
//	public static SimpleObjectProperty<Color> color=new SimpleObjectProperty<Color>();
//	public static SimpleObjectProperty<Effect> effect=new SimpleObjectProperty<Effect>();

	//elementIDsの空いている番号で最小のものを取得
	public static int getMinElementID() {
		int num=0;
		if(!elementIDs.isEmpty()) {
			for(; num<elementIDs.size(); num++) {
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
		elementIDs.put(getMinElementID(), el);
	}

	//elementIDsのnum番目の要素を消去する
	public static void removeElement(int num) {
		System.out.println(num+"番目のelementを消すよ");
		elementIDs.remove(num);
		System.out.println("elementIDsのsize:"+elementIDs.size());
	}

	//nodeIDsの空いている最小の番号にCircuitNodeを割り振る
	public static void addNode(CircuitNode cn, Element element) {
		System.out.println("minID:"+getMinNodeID());
		cn.setID(getMinNodeID());
		cn.setElementID(element.getID());
		nodeIDs.put(getMinNodeID(), cn);
		cn.setElementtype(element.getName());
	}

	//nodeIDsのnum番目の要素を消去する
	public static void removeNode(int num) {
		System.out.println(num+"番目のnodeを消すよ");
		nodeIDs.remove(num);
		System.out.println("nodeIDsのsize:"+nodeIDs.size());
	}

	//接点が重なっているか判定
	public static void isIntersectNodes(Integer ID) {
		CircuitNode node1=nodeIDs.get(ID);

		for (Entry<Integer, CircuitNode> entry : nodeIDs.entrySet()) {
			if(ID!=entry.getKey()) {
				CircuitNode node2=entry.getValue();
				double x1=elementIDs.get(node1.getElementID()).getGroup().getLayoutX()+node1.getLayoutX();
				double y1=elementIDs.get(node1.getElementID()).getGroup().getLayoutY()+node1.getLayoutY();
				double x2=elementIDs.get(node2.getElementID()).getGroup().getLayoutX()+node2.getLayoutX();
				double y2=elementIDs.get(node2.getElementID()).getGroup().getLayoutY()+node2.getLayoutY();
				double distance=Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2));
	//			System.out.println(ID+":"+node1.getLayoutX()+", "+node1.getLayoutY()+i+":"+node2.getLayoutX()+", "+node2.getLayoutY());
	//			System.out.println(ID+"と"+i+":"+distance);
				if(distance<20) {
					if(node1.getIntersectNode()!=entry.getKey()) {
						System.out.println(node1.getID()+"("+node1.getElementID()+")×"+node2.getID()+"("+node2.getElementID()+")");
					}
						node1.add(node2);
						node2.add(node1);
				}
				else {
					if(node1.getIntersectNode()==entry.getKey()) {
						System.out.println(node1.getID()+"("+node1.getElementID()+")_"+node2.getID()+"("+node2.getElementID()+")");
					}
						node1.remove(node2.getID());
						node2.remove(node1.getID());
				}
			}
		}
	}

	//keyとElementを設定
	public static void putElement(Integer key, Element element) {
		if(elementIDs.get(key)!=null) {

		}
		else {
			List<Element> list=new ArrayList<Element>();
			list.add(element);
			groupIDs.put(key, list);
		}
	}

	//使う予定なし
	public static boolean isCirculation(Integer elementID, Integer nodeID) {
		System.out.println("判定かいすぃ");
		for (Entry<Integer, CircuitNode> entry : nodeIDs.entrySet()) {
			System.out.println(entry.getKey()+"×"+entry.getValue().getIntersectNode());
		}
		CircuitNode node1=nodeIDs.get(nodeID);//nodeIDのnode
		Element el1=elementIDs.get(node1.getElementID());
		System.out.println("元の素子IDは"+elementID);
		System.out.println("今の素子IDは"+el1.getID()+", 今のnodeIDは"+node1.getID());
		if(node1.getIntersectNode()!=null) {
			CircuitNode node2=nodeIDs.get(node1.getIntersectNode());//node1と重なっているnode
			Element el2=elementIDs.get(node2.getElementID());
			if(el2.getID()==elementID) {
				System.out.println("かいろだよ!!!!!");
				return true;
			}
			else {
				CircuitNode node3=nodeIDs.get(el2.circles.get((node2.getNodalID()+1)%2).getID());//node2の反対側のnode
				System.out.println("次の素子IDは"+el2.getID()+", 次のnodeIDは"+node3.getID());
				return Circuit.isCirculation(elementID, node3.getID());
			}
		}
		else {
			System.out.println("次の素子はないよ");
			System.out.println("かいろじゃないよ");
			return false;
		}
	}
}
