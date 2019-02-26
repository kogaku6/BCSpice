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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Capacitor extends Element{
	private DoubleProperty capacitance=new SimpleDoubleProperty(0.00001);
	private Label label=new Label();

	private Current current=new Current(0.0, 0.0, Current.DIRECT);

	Capacitor(double X, double Y){
		setX(X);
		setY(Y);

		setName("Capacitor"+Circuit.elementIDs.get(this.getID()));

		Line line1=new Line(0, 0, 0, 40);
		line1.setStrokeWidth(4);
		Line line2=new Line(10, 0, 10, 40);
		line2.setStrokeWidth(4);


		label.textProperty().bind(capacitance.asString());
		label.setTextFill(Color.WHITE);

		wires=new ArrayList<Line>(2);
		wires.add(0, new Line(0, 20, -20, 20));
		wires.get(0).setStrokeWidth(4);
		wires.add(1, new Line(line2.getStartX(), 20, line2.getStartX()+20, 20));
		wires.get(1).setStrokeWidth(4);

		circles=new ArrayList<CircuitNode>(2);
		for(int i=0; i<2; i++) {
			CircuitNode node=new CircuitNode(0, 0, 5);
			if(i==0) {
				node.setLayoutX(-20);
			}
			else {
				node.setLayoutX(line2.getLayoutX()+20);
			}
			node.setLayoutY(20);
			node.setFill(Color.ALICEBLUE);
			wires.get(i).endXProperty().bind(node.layoutXProperty());
			wires.get(i).endYProperty().bind(node.layoutYProperty());
			circles.add(i, node);
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
						Series<Number, Number> sekibun=new Series<Number, Number>();
						sekibun.setName("voltage");

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
							int num=0;
							for(double i=0.0; i<finishTime.get(); i+=dx) {
								double d;
								if(num>0) {
									d=(double)sekibun.getData().get(num-1).getYValue();
								}
								else {
									d=0.0;
								}
								System.out.println(d+", "+(1/capacitance.get())*(d+(current.getValue(i)+current.getValue(i+dx))*dx/2));
								sekibun.getData().add(new Data<Number, Number>(i, (1/capacitance.get())*(d+(current.getValue(i)+current.getValue(i+dx))*dx/2)));
								num++;
							}
							list.add(sekibun);
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

	@Override
	protected void editValue() {
		//新しいウィンドウの生成
		Stage stage=new Stage();
		stage.initOwner(Main.stage);
		stage.initModality(Modality.APPLICATION_MODAL);//閉じるまで他の操作を禁止
		stage.setTitle("キャパシタンスを入力してね");
		stage.setResizable(false);
		VBox pane=new VBox();
		pane.setAlignment(Pos.CENTER);
		Scene scene=new Scene(pane, 400, 300);
		//https://stackoverflow.com/questions/7555564/what-is-the-recommended-way-to-make-a-numeric-textfield-in-javafx
		TextField form=new TextField();
		form.setText(String.valueOf(capacitance.get()));
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
				capacitance.set(Double.parseDouble(form.getText()));
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
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
