package application;

public class Current {
	public final static int DIRECT=0;
	public final static int ALTERNATIVE=1;

	private int currentType=DIRECT;
	private double maxValue=5.0;
	private double frequency=2;

	Current(int currentType){
		this.currentType=currentType;
	}

	public double getValue(double time) {
		Double value=null;
		switch(currentType) {
			case 0:
				value=maxValue;
				break;
			case 1:
				value=maxValue*Math.sin(frequency*time);
				break;
		}
		return value;
	}

}
