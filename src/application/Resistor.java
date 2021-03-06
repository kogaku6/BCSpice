package application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Resistor extends Element{
	private DoubleProperty resistance=new SimpleDoubleProperty(100.0);
	private Rectangle rectangle=new Rectangle(0,0,100,30);
	private Label label=new Label();

	Resistor(double X, double Y){
		setX(X);
		setY(Y);
		label.textProperty().bind(resistance.asString());
//		label.layoutXProperty().bind(XProperty.divide(2));
//		label.layoutYProperty().bind(YProperty.divide(2));
		label.setTextFill(Color.BLACK);
		rectangle.setFill(Color.GREEN);
		rectangle.setOnMouseClicked(me->{
			if(me.getButton().equals(MouseButton.PRIMARY)) {//左クリックされた場合
				if(me.getClickCount()==2) {//ダブルクリックされた場合
					editValue();
				}
			}
			else if(me.getButton().equals(MouseButton.SECONDARY)) {//右クリックされた場合
				editValue();
			}
		});
		group.getChildren().addAll(rectangle, label);
	}

	public void setResistance(double resistance) {
		this.resistance.set(resistance);
	}

	@Override
	public void editValue() {
		//新しいウィンドウの生成
		Stage stage=new Stage();
		stage.initOwner(Main.stage);
		stage.initModality(Modality.APPLICATION_MODAL);//閉じるまで他の操作を禁止
		stage.setTitle("抵抗を入力してね");
		stage.setResizable(false);
		StackPane pane=new StackPane();
		Scene scene=new Scene(pane, 400, 300);
		//https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
		TextField form=new TextField();
		form.setText(String.valueOf(resistance.get()));
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
				resistance.set(Double.parseDouble(form.getText()));
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

	@Override
	public Double getImpedance() {
		return resistance.get();
	}
}
