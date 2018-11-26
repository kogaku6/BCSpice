package application;

import javafx.scene.shape.Polyline;

public class Wire extends Element{
	Polyline line = new Polyline();
	
	Wire(Element prev, double Element){
		line.getPoints().addAll(new Double[] {
				
		});
	}
	
	Wire(){
		line.getPoints().addAll(new Double[]{
			    0.0, 0.0,
			    20.0, 10.0,
			    10.0, 20.0 });
	}
	
}
