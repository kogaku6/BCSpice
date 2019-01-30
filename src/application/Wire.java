package application;

import java.util.ArrayList;

import javafx.scene.Cursor;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Wire extends Element{
	Wire(){
		this(50, 0);
	}
	
	Wire(double X, double Y){
		setX(X);
		setY(Y);
		
		setName("Wire");
		
		circles=new ArrayList<CircuitNode>(2);
		circles.add(0,new CircuitNode(0, 0, 5));
		circles.get(0).setFill(Color.ALICEBLUE);
		circles.add(1, new CircuitNode(0, 0, 5));
		circles.get(1).setFill(Color.ALICEBLUE);
		circles.get(1).setLayoutX(X);
		circles.get(1).setLayoutX(Y);
		circles.forEach(a->{
			a.setOnMouseEntered(e->{
				a.setFill(Color.INDIANRED);
				Glow glow=new Glow();
				glow.setLevel(0.5);
				a.setEffect(glow);
				Main.root.setCursor(Cursor.HAND);
			});
			a.setOnMouseReleased(e->{
				a.setEffect(null);
				a.setFill(Color.ALICEBLUE);
				Main.root.setCursor(Cursor.DEFAULT);
			});
			a.setOnMousePressed(e->{
				offsetX=e.getX()+group.getLayoutX();
				offsetY=e.getY()+group.getLayoutY();
			});
			a.addEventHandler(MouseDragEvent.MOUSE_DRAGGED, e->{
				a.layoutXProperty().set(e.getSceneX()-offsetX);
				a.layoutYProperty().set(e.getSceneY()-offsetY);
				Circuit.isIntersectNodes(a.getID());
//				e.consume();	
			});
		});
		
		wires=new ArrayList<Line>(1);
		Line line=new Line();
		line.setStrokeWidth(4);
		line.startXProperty().bind(circles.get(0).layoutXProperty());
		line.startYProperty().bind(circles.get(0).layoutYProperty());
		line.endXProperty().bind(circles.get(1).layoutXProperty());
		line.endYProperty().bind(circles.get(1).layoutYProperty());
		wires.add(line);
		
		group.getChildren().addAll(wires.get(0), circles.get(0), circles.get(1));
		setDraggable();
	}

//		//https://teratail.com/questions/97911
	//使う予定なし
	Wire(Element prev, Element next){
		
	}


	@Override
	public void editValue() {//このメソッドは空でOK
	}

	@Override
	public Complex getImpedance() {
		return new Complex(0.0, 0.0);
	}

}
