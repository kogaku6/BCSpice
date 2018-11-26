package application;

import java.util.ArrayList;
import java.util.List;

public class CircuitNode {//接点クラス
	private Integer ID=null;
	private double current=0;
	private double voltage=0;
	private List<Element> elements=new ArrayList<Element>();

	//http://www.ecircuitcenter.com/SpiceTopics/Overview/Overview.htm
	//http://ednjapan.com/edn/articles/1306/04/news005.html

	CircuitNode(){

	}

	public void add(Element element) {
		elements.add(element);
	}

	public Element get(int num) {
		return elements.get(num);
	}

	public double getNodalCurrent() {//TODO 接点に印加される電流を計算
		return 0;
	}

	public double getNodalVoltage() {//TODO 接点に印加される電圧を計算
		return 0;
	}

//	public double getNodalImpedance() {//TODO 接点と接点の間の抵抗値を計算(Circuitクラスでやったほうがいいかも)
//		//return Matrix2D.mult(new Matrix2D(), b);
//		return 0;
//	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}




}