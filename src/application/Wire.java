package application;

import javafx.scene.shape.Line;

public class Wire extends Element{
	//private Polyline line = new Polyline();
	private Line line=new Line();

	Wire(){
	}

	Wire(Element prev, Element next){
		//https://teratail.com/questions/97911
		line.startXProperty().bind(prev.group.layoutXProperty());
		line.startYProperty().bind(prev.group.layoutYProperty());
		line.endXProperty().bind(next.group.layoutXProperty());
		line.endYProperty().bind(next.group.layoutYProperty());
		line.strokeProperty().bind(Circuit.color);
		line.effectProperty().bind(Circuit.effect);
		line.setStrokeWidth(3.0);
		group.getChildren().add(line);
	}

	@Override
	public void editValue() {

	}

	@Override
	public Double getImpedance() {
		return null;
	}

}
