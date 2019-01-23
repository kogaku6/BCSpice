package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	public static Stage stage;
	public static AnchorPane root;
	private static int px=640,py=480;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		String path_file="default.ini";
		List<String> list=new ArrayList<String>();
		//default.iniのロード
		try {
			//jarにコンパイルした場合、相対パスをInputStreamに変換する必要がある
			InputStream is=getClass().getResourceAsStream(path_file);
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			BufferedReader br=new BufferedReader(isr);

			//ファイル読み込み
			String str;
			while((str=br.readLine())!=null){
				list.add(str);
			}
			px=Integer.parseInt(list.get(0));//prefWidth
			py=Integer.parseInt(list.get(1));//prefHeight

			br.close();//ファイルを閉じる
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			stage=primaryStage;
			//setUserAgentStylesheet(STYLESHEET_CASPIAN);//Windowのスタイルを変更
			stage.sizeToScene();//謎の余白を消す
			stage.setResizable(true);//ウィンドウの拡大・縮小を許可
			Image img = readImage("resources/icon.png");//アプリケーションのアイコンの設定
			stage.getIcons().add(img);
			stage.setOnCloseRequest(we->{//ウィンドウを閉じる際のイベントを追加
				abortClosing(we);
			});
			sendTitleController("たいとる");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void sendTitleController(String labelText){
		try {
			root=(AnchorPane)FXMLLoader.load(getClass().getResource("Title.fxml"));
			root.setStyle("-fx-font-family:'Meiryo'");
			Scene top=new Scene(root, px, py);
			top.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle(labelText);
			stage.setScene(top);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//jarから画像を読み込むクラス
	private Image readImage(String path) {
		Image img=null;
		img = new Image(getClass().getResourceAsStream(path));
		return img;
	}

	//ウィンドウを閉じる際にメッセージ表示
	private void abortClosing(WindowEvent we) {
		Alert al=new Alert(AlertType.CONFIRMATION);
		al.setHeaderText(null);
		al.setContentText("おわるよ");
		al.setTitle("めっせーじ");
		Optional<ButtonType> result=al.showAndWait();
		if(result.get()==ButtonType.OK) {
			System.out.print("終わったよ");
		}
		else {
			we.consume();
		}
	}

}
