package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
	public static Stage stage;
	public static AnchorPane root;
	public static int px=640,py=480;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		String path_file="default.ini";
		//File file = new File(getClass().getResource(path_file).getFile());//jarから読み込み
		List<String> list=new ArrayList<String>();
		try {
			//FileReader fr = new FileReader(file);//ファイルを読み込むクラス
			InputStream is=getClass().getResourceAsStream(path_file);
            InputStreamReader isr = new InputStreamReader(is,"UTF-8");
			//BufferedReader br=new BufferedReader(fr);//ファイルを読み込んで，できるだけバッファ領域に保存するクラス
			BufferedReader br=new BufferedReader(isr);//ファイルを読み込んで，できるだけバッファ領域に保存するクラス

			String str;

			while((str=br.readLine())!=null){//読み込んだ行に処理をする
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
			//setUserAgentStylesheet(STYLESHEET_CASPIAN);
			stage.sizeToScene();//謎の余白を消す
			stage.setResizable(true);
			Image img = readImage("resources/icon.png");
			stage.getIcons().add(img);

			sendTitleController("たいとる");

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void sendTitleController(String labelText){
			try {
				root = (AnchorPane)FXMLLoader.load(getClass().getResource("Title.fxml"));
				Scene top=new Scene(root,px,py);
				KeyCombination kc=new KeyCodeCombination(KeyCode.S,KeyCombination.SHORTCUT_DOWN);
				top.addEventHandler(KeyEvent.KEY_RELEASED,new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if(kc.match((KeyEvent) ke)) {//Ctrl+Sでイベント発生
							exportScreenshot();
						}
					}
				});
				top.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				stage.setTitle(labelText);
				stage.setOnCloseRequest(we->{//ウィンドウを閉じる際のイベントを追加
					abortClosing(we);
				});
				stage.setScene(top);
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public Image readImage(String path) {
		Image img=null;
		//jarから読み込み
		img = new Image(getClass().getResourceAsStream(path));
		return img;
	}

	public static void exportScreenshot() {
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
				ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png",saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void abortClosing(WindowEvent we) {
		//ウィンドウを閉じる際にメッセージ表示
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
