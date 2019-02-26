package application;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.shape.Circle;

public class CircuitNode extends Circle{//接点クラス
	private Integer ID=null;//節点のID
	private Integer elementID=null;
	private Integer nodalID=null;
	//private double current=0;//節点電流
	//private double voltage=0;//節点電圧
	private Integer intersectNode=null;
	private Map<Integer, Element> elements=new HashMap<Integer, Element>();//節点につながっている素子のIDのリスト
	private String elementtype=null;

	//http://www.ecircuitcenter.com/SpiceTopics/Overview/Overview.htm
	//http://ednjapan.com/edn/articles/1306/04/news005.html

//	CircuitNode(double X, double Y){
//		en.setLayoutX(X);
//		en.setLayoutX(Y);
//	}
//	CircuitNode(){
//		this(0.0, 0.0);
//	}

	public CircuitNode(int i, int j, int k) {
		super(i, j, k);
	}

	//節点に素子をつなげる
	public void add(CircuitNode node) {
		Element element=Circuit.elementIDs.get(node.getElementID());
		elements.put(element.getID(), element);
		setIntersectNode(node.getID());
	}

	//節点につながっている素子を返す
	public Element getElement(int num) {
		if(elements.size()>num) {
			return elements.get(num);
		}
		else {
			return null;
		}
	}

	//節点から素子を取り除く
	public void remove(Integer ID) {
//		if(elements.size()>ID) {
			if(elements.get(ID)!=null) {
				elements.remove(ID.intValue());
				setIntersectNode(null);
			}
//		}
	}

	//TODO 接点に印加される電流を計算
	public double getNodalCurrent() {
		return 0;
	}

	//TODO 接点に印加される電圧を計算
	public double getNodalVoltage() {
		return 0;
	}

	//TODO 接点と接点の間の抵抗値を計算(Circuitクラスでやったほうがいいかも)
//	public double getNodalImpedance() {
//		//return Matrix2D.mult(new Matrix2D(), b);
//		return 0;
//	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public String getElementtype() {
		return elementtype;
	}

	public void setElementtype(String elementtype) {
		this.elementtype = elementtype;
	}

	public Integer getElementID() {
		return elementID;
	}

	public void setElementID(Integer elementID) {
		this.elementID = elementID;
	}

	public Integer getIntersectNode() {
		return intersectNode;
	}

	public void setIntersectNode(Integer intersectNode) {
		this.intersectNode = intersectNode;
//		if(elementtype=="Wire") {
			if(intersectNode!=null) {
				CircuitNode node=Circuit.nodeIDs.get(intersectNode);
				double x1=Circuit.elementIDs.get(node.getElementID()).getGroup().getLayoutX();
				double y1=Circuit.elementIDs.get(node.getElementID()).getGroup().getLayoutY();
				double x2=Circuit.elementIDs.get(this.getElementID()).getGroup().getLayoutX();
				double y2=Circuit.elementIDs.get(this.getElementID()).getGroup().getLayoutY();
				this.layoutXProperty().set(x1-x2+node.getLayoutX());
				this.layoutYProperty().set(y1-y2+node.getLayoutY());
			}
//		}
	}

	public Integer getNodalID() {
		return nodalID;
	}

	public void setNodalID(Integer nodalID) {
		this.nodalID = nodalID;
	}


}