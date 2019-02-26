package application;

public class Current {
	public final static int DIRECT=0;
	public final static int ALTERNATIVE=1;

	private int currentType=DIRECT;
	private Double maxValue=0.0;
	private Double frequency=0.0;

	Current(double maxValue, double frequency, int currentType){
		this.maxValue=maxValue;
		this.frequency=frequency;
		this.currentType=currentType;
	}

	public double getValue(double time) {
		Double value=null;
		switch(currentType) {
			case DIRECT:
				value=maxValue;
				break;
			case ALTERNATIVE:
				value=maxValue*Math.sin(frequency*time);
				break;
		}
		return value;
	}

}
