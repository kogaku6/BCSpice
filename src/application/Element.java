package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public abstract class Element {//素子クラス
	private Integer ID=null;
	private String name=null;
	private List<Integer> nodes=new ArrayList<Integer>();
	protected double offsetX=0.0;
	protected double offsetY=0.0;
	protected Group group=new Group();
	protected List<Line> wires;
	protected List<Circle> circles;

	Element(){//暗黙的スーパーコンストラクタの定義 子クラスのコンストラクタの呼び出しの前に呼ばれる
		this(0.0, 0.0);
	}

	Element(double X, double Y){
		this.ID=Circuit.getMinID();
		Circuit.putElement(this.ID, this);
		this.setX(X);
		this.setY(Y);
		setDraggable();
	}

	public boolean isConnected(int num) {
		if(nodes.get(num)!=null) {
			System.out.println("つながってるよ");
			return true;
		}
		else {
			System.out.println("つながってないよ");
			return false;
		}
	}

	protected void setDraggable() {
		group.setOnMouseEntered(e->{
			Glow glow=new Glow();
			glow.setLevel(0.5);
			group.setEffect(glow);
			Main.root.setCursor(Cursor.HAND);
		});
		group.setOnMouseExited(e->{
			group.setEffect(null);
			Main.root.setCursor(Cursor.DEFAULT);
		});
		
		group.setOnMousePressed(e->{
			offsetX = e.getX();
			offsetY = e.getY();
		});
		group.addEventHandler(MouseDragEvent.MOUSE_DRAGGED,e->{
			group.layoutXProperty().set(e.getSceneX()-offsetX);
			group.layoutYProperty().set(e.getSceneY()-offsetY);
			e.consume();
		});
	}

	public double getX() {
		return group.layoutXProperty().get();
	}

	public void setX(double X) {
		group.layoutXProperty().set(X);
	}

	public double getY() {
		return group.layoutYProperty().get();
	}

	public void setY(double Y) {
		group.layoutYProperty().set(Y);
	}

	public DoubleProperty getPropertyX() {
		return group.layoutXProperty();
	}

	public DoubleProperty getPropertyY() {
		return group.layoutYProperty();
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer ID) {
		this.ID = ID;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	protected abstract void editValue();
	public abstract Complex getImpedance();

}
