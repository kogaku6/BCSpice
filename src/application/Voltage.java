package application;

import java.util.ArrayList;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Voltage extends Element{
	//0:DC 1:AC
	private int waveType=0;
	private DoubleProperty voltage=new SimpleDoubleProperty(5.0);
	private Label label=new Label();

	Voltage(){
		this(0,0);
	}

	Voltage(double X, double Y){
		setX(X);
		setY(Y);
		
		setName("Voltage");
		
//		circle.setFill(Color.SANDYBROWN);
		Line line1=new Line(0, 0, 0, 40);
		line1.setStrokeWidth(4);
		Line line2=new Line(10, 5, 10, 30);
		line2.setStrokeWidth(4);
		
		label.textProperty().bind(voltage.asString());
		label.setTextFill(Color.WHITE);
		
		wires=new ArrayList<Line>(2);
		wires.add(0, new Line(0, 20, -20, 20));
		wires.get(0).setStrokeWidth(4);
		wires.add(1, new Line(line2.getStartX(), 20, line2.getStartX()+20, 20));
		wires.get(1).setStrokeWidth(4);
		
		circles=new ArrayList<CircuitNode>(2);
		for(int i=0; i<2; i++) {
			circles.add(i, new CircuitNode(0, 0, 5));
			circles.get(i).layoutXProperty().bind(wires.get(i).endXProperty());
			circles.get(i).layoutYProperty().bind(wires.get(i).endYProperty());
			circles.get(i).setFill(Color.ALICEBLUE);
		}
		
		group.getChildren().addAll(wires.get(0), wires.get(1), line1, line2, label, circles.get(0), circles.get(1));
		setDraggable();
	}

	@Override
	public void editValue() {
		// TODO 自動生成されたメソッド・スタブ

	}

	public int getVoltageType() {
		return waveType;
	}

	public void setVoltageType(int voltageType) {
		this.waveType = voltageType;
	}

	@Override
	public Complex getImpedance() {
		return new Complex(0.0, 0.0);
	}

}
