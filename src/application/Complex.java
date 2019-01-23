package application;

public class Complex {
	private double realPart=0;//実部
	private double imaginaryPart=0;//虚部
	final static int Rectangular=0;
	final static int Polar=1;
	
	//直交座標系で初期化
	Complex(double realPart, double imaginaryPart){//http://networkprogramming.blog18.fc2.com/blog-entry-78.html
		this.realPart=realPart;
		this.imaginaryPart=imaginaryPart;
	}
	//オプション付きの初期化
	Complex(double x, double y, int option) {
		if(option==0) {//直交座標系
			setRealPart(x);
			setImaginaryPart(y);
		}
		else if(option==1) {//極座標系
			setRealPart(x*Math.cos(y));
			setImaginaryPart(x*Math.sin(y));
		}
	}

	//printlnで表示される文字列
	public String toString() {
		return "{"+realPart+","+imaginaryPart+"}\n"+getAbsolute()+"∠"+getArgument()+"}";
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
}
