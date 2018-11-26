package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Resistor extends Element{
	private double resistance=100;
	Rectangle ra=new Rectangle(100,30,100,30);
	
	Resistor(){
		ra.setFill(Color.GREEN);
	}

	public double getResistance() {
		return resistance;
	}

	public void setResistance(double resistance) {
		this.resistance = resistance;
	}
	
	
}
