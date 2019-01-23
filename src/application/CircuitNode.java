package application;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Circle;

public class CircuitNode {//接点クラス
	private double X=0.0;
	private double Y=0.0;
	private Integer ID=null;//節点のID
	//private double current=0;//節点電流
	//private double voltage=0;//節点電圧
	private List<Element> elements=new ArrayList<Element>();//節点につながっている素子のIDのリスト
	private Circle en=new Circle();

	//http://www.ecircuitcenter.com/SpiceTopics/Overview/Overview.htm
	//http://ednjapan.com/edn/articles/1306/04/news005.html

	CircuitNode(double X, double Y){
		en.setLayoutX(X);
		en.setLayoutX(Y);
	}
	CircuitNode(){
		this(0.0, 0.0);
	}

	//節点に素子をつなげる
	public void add(Element element) {
		elements.add(element.getID(), element);
	}

	//節点につながっている素子を返す
	public Element get(int num) {
		return elements.get(num);
	}

	//節点から素子を取り除く
	public void remove(Integer ID) {
		elements.remove(ID.intValue());
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

	public void setID(Integer iD) {
		ID = iD;
	}

	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

}