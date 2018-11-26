package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.WindowEvent;

public class TitleController implements Initializable{
	private static int imageNum=0;
	private static List<Image> images=new ArrayList<Image>();
	@FXML
	ImageView back;
	@FXML
	AnchorPane ap;
	@FXML
	MenuBar mb;
	@FXML
	MenuItem saveItem, closeItem, specialItem;

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
		saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));//Cmd+S
		closeItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));//ESCでウィンドウを閉じる
		//contextmenuの設定
		ContextMenu contextMenu=new ContextMenu();
		MenuItem editResistor=new MenuItem("Resistor");
		MenuItem editWire=new MenuItem("Wire");
		MenuItem editSpecial=new MenuItem("Preset");
		MenuItem eraseItem=new MenuItem("Erase All");
		editResistor.setOnAction(e->{
			ap.getChildren().add(new Resistor(contextMenu.getX()-Main.stage.getX(), contextMenu.getY()-Main.stage.getY()).getGroup());
		});
		editSpecial.setOnAction(e->{
			//フィールドに素子を生成(テスト用)
			Voltage vt=new Voltage(150, 150);//電源の生成
			Resistor rs1=new Resistor(170+Math.random()*50, 70+Math.random()*50);//抵抗の生成
			Resistor rs2=new Resistor(50+Math.random()*50, 50+Math.random()*50);
			Wire wr1=new Wire(vt, rs1);//ワイヤーの生成
			Wire wr2=new Wire(vt, rs2);
			Wire wr3=new Wire(rs1, vt);
			Wire wr4=new Wire(rs2, vt);
			System.out.println("elementIDs");
			System.out.println(Circuit.elementIDs.values().size());
			System.out.println(Circuit.elementIDs.values());
			System.out.println("groupIDs");
			System.out.println(Circuit.groupIDs.values().size());
			System.out.println(Circuit.groupIDs.values());
			ap.getChildren().addAll(vt.getGroup(),rs1.getGroup(),rs2.getGroup(),wr1.getGroup(),wr2.getGroup(),wr3.getGroup(),wr4.getGroup());//小ウィンドウに抵抗とワイヤーを置く
			if(Circuit.isCirculation(vt.getID())) {
				System.out.println("これは紛れもなく回路だよ");
			}
			else {
				System.out.println("回路じゃないよこれは");
			}
		});
		eraseItem.setOnAction(e->{
			List<Node> list=new ArrayList<Node>();
			ap.getChildren().stream().filter(node->node.getClass()==Group.class).forEach(node->{
				list.add(node);
			});
			list.forEach(node->{
				ap.getChildren().remove(node);
			});
		});
		Menu edit=new Menu("Edit", null, editResistor, editWire, editSpecial);
		Menu erase=new Menu("Erase", null, eraseItem);
		contextMenu.getItems().addAll(edit, erase);
		ap.setOnMouseClicked(e->{
			contextMenu.hide();
			if(e.getButton().equals(MouseButton.SECONDARY)) {
				contextMenu.show(ap, e.getScreenX(), e.getScreenY());
			}
		});
		//ワイヤーの初期色
		Circuit.color.set(Color.BLACK);
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
				ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png",saveFile);
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

	//ワイヤーの見た目を変えるクラス
	@FXML
	public void changeWire() {
		if(Circuit.color.get().equals(Color.BLACK)) {
			Glow glow=new Glow();
			glow.setLevel(10);
			Circuit.color.set(Color.GOLD);
			Circuit.effect.set(glow);
		}
		else {
			Circuit.color.set(Color.BLACK);
			Circuit.effect.set(null);
		}
	}

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
		Desktop desktop=Desktop.getDesktop();
		String uriString="https://github.com/kogaku6/aiueo";
		try {
			URI uri=new URI(uriString);
			desktop.browse(uri);
		}catch(URISyntaxException e){
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
