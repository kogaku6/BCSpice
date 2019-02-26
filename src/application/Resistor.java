package application;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Resistor extends Element{
	private DoubleProperty resistance=new SimpleDoubleProperty(30.0);
	private Rectangle rectangle=new Rectangle(0,0,100,30);
	private Label label=new Label();

	private Current current=new Current(0.0, 0.0, Current.DIRECT);

	Resistor(double X, double Y){
		setX(X);
		setY(Y);

		setName("Resistor");

		label.textProperty().bind(resistance.asString());
		label.layoutXProperty().bind(label.widthProperty().divide(2));
		label.setTextFill(Color.WHITE);

		rectangle.setFill(null);
		rectangle.setStrokeWidth(4);
		rectangle.setStroke(Color.BLACK);

		wires=new ArrayList<Line>(2);
		wires.add(0, new Line(0, rectangle.getHeight()/2, -20, rectangle.getHeight()/2));
		wires.get(0).setStrokeWidth(4);
		wires.add(1, new Line(rectangle.getWidth(), rectangle.getHeight()/2, rectangle.getWidth()+20, rectangle.getHeight()/2));
		wires.get(1).setStrokeWidth(4);

		circles=new ArrayList<CircuitNode>(2);
		for(int i=0; i<2; i++) {
			circles.add(i, new CircuitNode(0, 0, 5));
			circles.get(i).setNodalID(i);
			if(i==0) {
				circles.get(i).setLayoutX(-20);
			}
			else {
				circles.get(i).setLayoutX(rectangle.getWidth()+20);
			}
			circles.get(i).setLayoutY(rectangle.getHeight()/2);
			circles.get(i).setFill(Color.ALICEBLUE);
			wires.get(i).endXProperty().bind(circles.get(i).layoutXProperty());
			wires.get(i).endYProperty().bind(circles.get(i).layoutYProperty());
		}
		group.getChildren().addAll(wires.get(0), wires.get(1), rectangle, label, circles.get(0), circles.get(1));
		group.setOnMouseClicked(me->{
			if(me.getButton().equals(MouseButton.PRIMARY)) {//左クリックされた場合
				if(me.getClickCount()==2) {//ダブルクリックされた場合
					me.consume();//ウィンドウを閉じた後にポップアップが表示されないようにする
					editValue();
				}
			}
			else if(me.getButton().equals(MouseButton.SECONDARY)) {//右クリックされた場合
				if(Circuit.isSimulating) {//シミュレーション実行中である場合
					me.consume();
					if(Circuit.isCirculation(getID(), 0)) {
						Stage stage=new Stage();
						stage.initOwner(Main.stage);
						stage.initModality(Modality.APPLICATION_MODAL);//閉じるまで他の操作を禁止
						stage.setTitle("ぐらふ");
						stage.setResizable(true);
						VBox pane=new VBox();
						pane.setAlignment(Pos.CENTER);

						Scene scene=new Scene(pane, 400, 300);

						NumberAxis xAxis=new NumberAxis();
						NumberAxis yAxis=new NumberAxis();
						LineChart<Number, Number> chart=new LineChart<>(xAxis,yAxis);
	//					chart.setLegendVisible(false);
						chart.setAnimated(false);
						xAxis.setLabel("time");
						yAxis.setLabel("voltage");
						Series<Number, Number> series=new Series<Number, Number>();
						series.setName("voltage");

						DoubleProperty resolution=new SimpleDoubleProperty(0.01);
						TextField resolutionForm=new TextField();
						resolutionForm.setPromptText("set a resolution time");
						resolutionForm.setText(String.valueOf(resolution.get()));
						resolutionForm.textProperty().addListener((o, old,newly) ->{
							if(resolutionForm.getLength()>0) {
								String s="";
								for(char c : newly.toCharArray()){
									if(((int)c >= 48 && (int)c <= 57 || (int)c == 46)){
										s+=c;
									}
								}
								resolution.set(Double.parseDouble(s));
								resolutionForm.setText(resolution.getValue().toString());
							}
						});
						DoubleProperty finishTime=new SimpleDoubleProperty(1.0);
						TextField finishForm=new TextField();
						finishForm.setPromptText("set a finishtime");
						finishForm.setText(String.valueOf(finishTime.get()));
						finishForm.textProperty().addListener((o, old,newly) ->{
							if(finishForm.getLength()>0) {
								String s="";
								for(char c : newly.toCharArray()){
									if(((int)c >= 48 && (int)c <= 57 || (int)c == 46)){
										s+=c;
									}
								}
								finishTime.set(Double.parseDouble(s));
								finishForm.setText(finishTime.getValue().toString());
							}
						});

						Button button=new Button();
						button.setText("OK");
						button.setOnMouseClicked(event->{//ボタンクリック時のイベント
	//						if(Circuit.groupIDs)
							chart.getData().clear();
							List<Series<Number, Number>> list=new ArrayList<Series<Number, Number>>();
							current=new Current(5.0, 20.0, Current.ALTERNATIVE);
							double dx=resolution.get();
							for(double i=0.0; i<finishTime.get(); i+=dx) {
								series.getData().add(new Data<Number, Number>(i, resistance.get()*current.getValue(i)));
							}
							list.add(series);
							chart.getData().addAll(list);//グラフの生成
						});
						pane.getChildren().addAll(resolutionForm, finishForm, button, chart);
						stage.setScene(scene);
						stage.sizeToScene();
						stage.showAndWait();
					}
					else {
						System.out.println("回路じゃないっぽいからシミュレーションできないよ");
					}
				}
				else {
					me.consume();
					editValue();
				}
			}
		});
		setDraggable();
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
		VBox pane=new VBox();
		pane.setAlignment(Pos.CENTER);
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
	public Complex getImpedance() {
		return new Complex(resistance.get(), 0.0);
	}
}
