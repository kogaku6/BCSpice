package application;

public class Complex {
	private double realPart;//実部
	private double imaginaryPart;//虚部
	
	Complex(double realPart, double imaginary){
	}

	//実部を返す
	public Double getRealPart() {
		return realPart;
	}
	
	//実部を設定
	public void setRealPart(Double realPart) {
		this.realPart = realPart;
	}
	
	//虚部を返す
	public Double getImaginaryPart() {
		return imaginaryPart;
	}
	
	//虚部を設定
	public void setImaginaryPart(Double imaginaryPart) {
		this.imaginaryPart = imaginaryPart;
	}	
	
	//絶対値を返す
	public double getAbsolute() {
		return Math.sqrt(realPart*realPart + imaginaryPart*imaginaryPart);
	}	
	
	//偏角を返す
	public double getArgument() {
		return Math.atan(imaginaryPart/realPart);
	}
	
	//極座標表示を返す
	public String getPolar() {
		return String.valueOf(getAbsolute())+"∠"+String.valueOf(getArgument());
	}
}
