package application;

public abstract class Element {
	private Element prev=null;
	private Element next=null;
	private double x=0.0;
	private double y=0.0;
	
	Element(Element prev, Element next){
		this.prev=prev;
		this.next=next;
	}
	
	Element(){
	}
	
	public Element getPrev() {
		return this.prev;
	}
	
	public void setPrev(Element prev){
		this.prev=prev;
	}
	
	public Element getNext() {
		return this.next;
	}
	
	public void setNext(Element next) {
		this.next=next;
	}
	
	public boolean isConnected() {
		if(this.prev!=null&&this.next!=null) {
			return true;
		}
		else {
			return false;
		}
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
