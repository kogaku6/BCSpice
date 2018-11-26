package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Voltage extends Element{
	//0:DC 1:AC
	private int voltageType=0;
	private Circle circle=new Circle(0 ,0, 40);

	Voltage(){
		this(0,0);
	}

	Voltage(double X, double Y){
		circle.setFill(Color.SANDYBROWN);
		group.getChildren().add(circle);
		setX(X);
		setY(Y);
	}

	@Override
	public void editValue() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public int getVoltageType() {
		return voltageType;
	}

	public void setVoltageType(int voltageType) {
		this.voltageType = voltageType;
	}

	@Override
	public Double getImpedance() {
		return null;
	}

}
