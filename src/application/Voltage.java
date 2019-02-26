package application;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Voltage extends Element{
	public final static int DIRECT=0;
	public final static int ALTERNATIVE=1;

	private int currentType=DIRECT;
	private Double maxValue=5.0;
	private double frequency=2;
	private Label label=new Label();

	Voltage(){
		this(0,0);
	}

	Voltage(double X, double Y){
		setX(X);
		setY(Y);
		
		setName("Voltage");
		
//		circle.setFill(Color.SANDYBROWN);
		Line line1=new Line(0, 0, 0, 40);
		line1.setStrokeWidth(4);
		Line line2=new Line(10, 5, 10, 30);
		line2.setStrokeWidth(4);
		
		label.setText(maxValue.toString());
		label.setTextFill(Color.WHITE);
		
		wires=new ArrayList<Line>(2);
		wires.add(0, new Line(0, 20, -20, 20));
		wires.get(0).setStrokeWidth(4);
		wires.add(1, new Line(line2.getStartX(), 20, line2.getStartX()+20, 20));
		wires.get(1).setStrokeWidth(4);
		
		circles=new ArrayList<CircuitNode>(2);
		for(int i=0; i<2; i++) {
			circles.add(i, new CircuitNode(0, 0, 5));
			circles.get(i).layoutXProperty().bind(wires.get(i).endXProperty());
			circles.get(i).layoutYProperty().bind(wires.get(i).endYProperty());
			circles.get(i).setFill(Color.ALICEBLUE);
		}
		
		group.getChildren().addAll(wires.get(0), wires.get(1), line1, line2, label, circles.get(0), circles.get(1));
		group.setOnMouseClicked(me->{
			if(me.getButton().equals(MouseButton.PRIMARY)) {//左クリックされた場合
				if(me.getClickCount()==2) {//ダブルクリックされた場合
					me.consume();//ウィンドウを閉じた後にポップアップが表示されないようにする
					editValue();
				}
			}
			else if(me.getButton().equals(MouseButton.SECONDARY)) {//右クリックされた場合
				me.consume();
				editValue();
			}
		});
		setDraggable();
	}

	@Override
	public void editValue() {
		//新しいウィンドウの生成
		Stage stage=new Stage();
		stage.initOwner(Main.stage);
		stage.initModality(Modality.APPLICATION_MODAL);//閉じるまで他の操作を禁止
		stage.setTitle("電圧を入力してね");
		stage.setResizable(false);
		VBox pane=new VBox();
		pane.setAlignment(Pos.CENTER);
		Scene scene=new Scene(pane, 400, 300);
		//https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
		TextField form=new TextField();
		form.setText(String.valueOf(maxValue));
		form.textProperty().addListener((o, old,newly) ->{
			String s="";
			for(char c : newly.toCharArray()){
				if(((int)c >= 48 && (int)c <= 57 || (int)c == 46)){
					s+=c;
				}
			}
			form.setText(s);
		});
		Button button=new Button();
		button.setText("OK");
		button.setOnMouseClicked(event->{
			try {
				maxValue=Double.parseDouble(form.getText());
				stage.close();
			}catch(NumberFormatException e) {
				Alert al=new Alert(AlertType.ERROR);
				al.setTitle("正しい数値を入力してください");
				al.showAndWait();
			}
		});
		pane.getChildren().addAll(form,button);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.showAndWait();
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

	@Override
	public Complex getImpedance() {
		return new Complex(0.0, 0.0);
	}

}
