package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TitleController extends Application implements Initializable{
	private static int imageNum=0;
	private static List<Image> images=new ArrayList<Image>();
	@FXML
	ImageView back;
	@FXML
	AnchorPane top;
	@FXML
	AnchorPane ap;
	@FXML
	MenuBar mb;
	@FXML
	MenuItem importItem, exportItem, closeItem, runItem, specialItem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//背景画像の読み込み
		images.add(readImage("resources/default.png"));
		images.add(readImage("resources/title.png"));
		images.add(readImage("resources/silhouette.png"));
		//背景の初期設定
		back.fitWidthProperty().bind(Main.stage.widthProperty());
		back.fitHeightProperty().bind(Main.stage.heightProperty());
		back.setImage(images.get(0));
		back.setOpacity(0.5);
		//メニューバーの初期設定
		mb.prefWidthProperty().bind(Main.stage.widthProperty());
		//メニューアイテムの初期設定
		exportItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));//Cmd+S
		closeItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));//ESCでウィンドウを閉じる
		//contextmenuの設定
		ContextMenu contextMenu=new ContextMenu();
		MenuItem editWire=new MenuItem("Wire");
		MenuItem editResistor=new MenuItem("Resistor");
		MenuItem editCapacitor=new MenuItem("Capacitor");
		MenuItem editInductor=new MenuItem("Inductor");
		MenuItem editVoltage=new MenuItem("Voltage");
		MenuItem editSpecial=new MenuItem("Preset");
		MenuItem eraseItem=new MenuItem("Erase All");
		editWire.setOnAction(e->{//ワイヤーの生成
			ap.getChildren().add(new Wire(contextMenu.getX()-Main.stage.getX(), contextMenu.getY()-Main.stage.getY()).getGroup());
		});
		editResistor.setOnAction(e->{//抵抗の生成
			ap.getChildren().add(new Resistor(contextMenu.getX()-Main.stage.getX(), contextMenu.getY()-Main.stage.getY()).getGroup());
		});
		editCapacitor.setOnAction(e->{//キャパシタの生成
			ap.getChildren().add(new Capacitor(contextMenu.getX()-Main.stage.getX(), contextMenu.getY()-Main.stage.getY()).getGroup());
		});
		editInductor.setOnAction(e->{//インダクタの生成
			ap.getChildren().add(new Inductor(contextMenu.getX()-Main.stage.getX(), contextMenu.getY()-Main.stage.getY()).getGroup());
		});
		editVoltage.setOnAction(e->{//電源の生成
			ap.getChildren().add(new Voltage(contextMenu.getX()-Main.stage.getX(), contextMenu.getY()-Main.stage.getY()).getGroup());
		});
		editSpecial.setOnAction(e->{
			//フィールドに素子を生成(テスト用)
			Voltage vt=new Voltage(150, 150);//電源の生成
			Resistor rs1=new Resistor(170+Math.random()*50, 70+Math.random()*50);//抵抗の生成
			Resistor rs2=new Resistor(50+Math.random()*50, 50+Math.random()*50);
			ap.getChildren().addAll(vt.getGroup(),rs1.getGroup(),rs2.getGroup());//小ウィンドウに抵抗を置く
		});
		eraseItem.setOnAction(e->{
			Circuit.elementIDs.forEach((a,b)->{
				b.remove();
				System.out.println("groupをけすよ");
				ap.getChildren().remove(b.getGroup());
			});
		});
		Menu edit=new Menu("Edit", null, editWire, editResistor, editCapacitor, editInductor, editVoltage, new SeparatorMenuItem(), editSpecial);
		Menu erase=new Menu("Erase", null, eraseItem);
		contextMenu.getItems().addAll(edit, erase);
		top.setOnMouseDragged(e->{

		});
		top.setOnMouseClicked(e->{
			contextMenu.hide();
			Circuit.isSimulating=false;
			ap.setCursor(Cursor.DEFAULT);
			if(e.getButton().equals(MouseButton.SECONDARY)) {
				contextMenu.show(ap, e.getScreenX(), e.getScreenY());
			}
		});
		top.setOnScroll(e->{
			double zoomFactor=1.00;
			double deltaY=e.getDeltaY();

			if(deltaY<0) {
				if(ap.getScaleY()>0.5) {
					zoomFactor=0.95;
				}
			}
			else {
				if(ap.getScaleY()<2.0) {
					zoomFactor=1.05;
				}
			}
			//System.out.println(ap.getScaleY());
			ap.setScaleX(ap.getScaleX()*zoomFactor);
			ap.setScaleY(ap.getScaleY()*zoomFactor);
		});



		//ワイヤーの初期色
//		Circuit.color.set(Color.BLACK);
	}

	//jarから画像を読み込むクラス
	public Image readImage(String path) {
		Image img=null;
		img = new Image(getClass().getResourceAsStream(path));
		return img;
	}

	//スクリーンショットを保存するクラス
	@FXML
	private void exportScreenShot() {
		//ファイル保存ウィンドウの生成
		final FileChooser fc=new FileChooser();
		fc.setTitle("すくりーんしょっとをほぞんできるよ");
		ExtensionFilter ef=new ExtensionFilter("Image Files (*.png)","*.png");
		fc.getExtensionFilters().addAll(
			ef,
			new ExtensionFilter("Image Filer (*.jpg)","*.jpg"),
			new ExtensionFilter("Image Files (*.bmp)","*.bmp"),
			new ExtensionFilter("All Files","*.*")
		);
		fc.setInitialFileName("NewScreenShot");
		fc.setSelectedExtensionFilter(ef);
		File saveFile=fc.showSaveDialog(Main.stage);
		if(saveFile!=null) {
			WritableImage snapshot =Main.stage.getScene().snapshot(null);
	  		try {
				ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//プログラム内からウィンドウを閉じるクラス
	@FXML
	public void closeWindow() {
		Main.stage.fireEvent(new WindowEvent(Main.stage,WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	public void startSimulate() {

		//https://algorithm.joho.info/programming/c-language/runge-kutta-method-c/
		//https://www.jstage.jst.go.jp/article/ieejeiss/124/2/124_2_404/_pdf
		//http://www.tij.co.jp/jp/lit/an/jaja464/jaja464.pdf
		//https://qiita.com/tatssuki/items/fb6b1cf14d261cc5d316
		//修正節点法 微分方程式 混合解析


//		ap.setCursor(new ImageCursor(readImage("resources/icon.png")));
//		Circuit.isSimulating=true;
		//テスト用ウィンドウの生成
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
//		chart.setLegendVisible(false);
		chart.setAnimated(false);
		xAxis.setLabel("time");
		yAxis.setLabel("current");
		XYChart.Series<Number, Number> series=new Series<Number,Number>();
		XYChart.Series<Number, Number> bibun=new Series<Number, Number>();
		series.setName("normal");
		bibun.setName("differential");

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
			chart.getData().clear();
			List<Series<Number, Number>> list=new ArrayList<Series<Number, Number>>();
			Current denryu=new Current(Current.ALTERNATIVE);
			for(double i=0.0; i<finishTime.get(); i+=resolution.get()) {
				series.getData().add(new Data<Number, Number>(i, denryu.getValue(i)));
				double dy=denryu.getValue(i+resolution.get())-denryu.getValue(i);
				bibun.getData().add(new Data<Number, Number>(i, dy/resolution.get()));
			}
			list.add(series);
			list.add(bibun);
			chart.getData().addAll(list);//グラフの生成
		});
		pane.getChildren().addAll(resolutionForm, finishForm, button, chart);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.showAndWait();
	}

	//ワイヤーの見た目を変えるクラス
//	@FXML
//	public void changeWire() {
//		if(Circuit.color.get().equals(Color.BLACK)) {
//			Glow glow=new Glow();
//			glow.setLevel(10);
//			Circuit.color.set(Color.GOLD);
//			Circuit.effect.set(glow);
//		}
//		else {
//			Circuit.color.set(Color.BLACK);
//			Circuit.effect.set(null);
//		}
//	}

	//背景画像を切り替えるクラス
	@FXML
	public void changeImage() {
		imageNum=(imageNum+1)%images.size();
		back.setImage(images.get(imageNum));
	}

	//背景画像を見えなくするクラス
	@FXML
	public void eraseImage() {
		if(back.isVisible()) {
			back.setVisible(false);
		}
		else {
			back.setVisible(true);
		}
	}

	//インターネットのURLを開くクラス
	@FXML
	public void openURL() {
		String uri="https://github.com/kogaku6/aiueo";
		getHostServices().showDocument(uri);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

}
