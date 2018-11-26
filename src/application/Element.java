package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.input.MouseDragEvent;

public abstract class Element {//素子クラス
	private Integer ID=null;
	private List<Integer> prevIDs=new ArrayList<Integer>();
	private List<Integer> nextIDs=new ArrayList<Integer>();
	protected double offsetX=0.0;
	protected double offsetY=0.0;
	protected Group group=new Group();

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

	public boolean isConnected() {
		if(this.prevIDs.stream().allMatch(id->Circuit.elementIDs.get(id)!=null)&&this.nextIDs.stream().allMatch(id->Circuit.elementIDs.get(id)!=null)) {
			System.out.println("つながってるよ");
			return true;
		}
		else {
			System.out.println("つながってないよ");
			return false;
		}
	}

	private void setDraggable() {
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

	public List<Integer> getPrevIDs() {
		return prevIDs;
	}

	public void setPrevIDs(List<Integer> prevID) {
		this.prevIDs = prevID;
	}

	public List<Integer> getNextIDs() {
		return nextIDs;
	}

	public void setNextIDs(List<Integer> nextID) {
		this.nextIDs = nextID;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}


	protected abstract void editValue();
	public abstract Double getImpedance();

}
