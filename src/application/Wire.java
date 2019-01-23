package application;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Wire extends Element{
//	private Line line=new Line();

	Wire(){
	}
	
	Wire(double X, double Y){
		setX(X);
		setY(Y);
		
		circles=new ArrayList<Circle>(2);
		circles.add(0, new Circle(0, 0, 5));
		circles.get(0).setFill(Color.ALICEBLUE);
		circles.add(1, new Circle(0, 30, 5));
		circles.get(1).setFill(Color.ALICEBLUE);
		
		wires=new ArrayList<Line>(1);
		wires.add(0, new Line());
		wires.get(0).setStrokeWidth(4);
		wires.get(0).startXProperty().bind(circles.get(0).layoutXProperty());
		wires.get(0).startYProperty().bind(circles.get(0).layoutYProperty());
		wires.get(0).endXProperty().bind(circles.get(1).layoutXProperty());
		wires.get(0).endYProperty().bind(circles.get(1).layoutYProperty());
		
		group.setOnMouseClicked(me->{
			if(me.getClickCount()==2) {
				circles.get(1).setLayoutX(300);
			}
		});
		
		group.getChildren().addAll(wires.get(0), circles.get(0), circles.get(1));
	}

	Wire(Element prev, Element next){
//		
//		//https://teratail.com/questions/97911
//		for(int i=0; i<2; i++) {
//			wires.get(i).startXProperty()
//		}
//		line.startXProperty().bind(prev.circles.get(1).layoutXProperty());
//		line.startYProperty().bind(prev.circles.get(1).layoutYProperty());
//		line.endXProperty().bind(next.circles.get(0).layoutXProperty());
//		line.endYProperty().bind(next.circles.get(0).layoutYProperty());
//		line.setStrokeWidth(3.0);
//		
//		line.setOnMouseEntered(e->{
//			Glow glow= new Glow();
//			glow.setLevel(0.5);
//			line.setStroke(Color.BLUE);
//			line.setEffect(glow);
//		});
//		line.setOnMouseExited(e->{
//			line.setStroke(Color.BLACK);
//			line.setEffect(null);
//		});
//		setDraggable();
//		circles=new ArrayList<Circle>(2);
//		for(int i=0; i<2; i++) {
//			circles.add(i, new Circle(0, 0, 5));
//			circles.get(i).layoutXProperty().bind(wires.get(i).endXProperty());
//			circles.get(i).layoutYProperty().bind(wires.get(i).endYProperty());
//			circles.get(i).setFill(Color.ALICEBLUE);
//		}
//		
//		group.getChildren().addAll(wires.get(0), wires.get(1), circles.get(0), circles.get(1));
	}


	@Override
	public void editValue() {

	}

	@Override
	public Complex getImpedance() {
		return new Complex(0.0, 0.0);
	}

}
