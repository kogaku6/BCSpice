package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;

public class TitleController implements Initializable{
	private static int imageNum=0;
	private static List<Image> images=new ArrayList<Image>();
	private double offsetX,offsetY;
	@FXML
	ImageView back;
	@FXML
	AnchorPane ap;
	@FXML
	MenuBar mb;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		images.add(readImage("resources/default.png"));
		images.add(readImage("resources/title.png"));
		images.add(readImage("resources/silhouette.png"));

		 back.fitWidthProperty().bind(Main.stage.widthProperty());
		 back.fitHeightProperty().bind(Main.stage.heightProperty());
		 back.setImage(images.get(0));
		 mb.prefWidthProperty().bind(Main.stage.widthProperty());
		 mb.setStyle("-fx-font-family:'Meiryo'");

		 AnchorPane sp = new AnchorPane();
		 sp.setPrefWidth(400);
		 sp.setPrefHeight(200);
		 sp.setLayoutX(100);
		 sp.setLayoutY(100);
		 sp.setBorder(new Border(new BorderStroke(Color.PALETURQUOISE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(26, 4, 4, 4))));
		 sp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		 Button bt=new Button();
		 bt.setText("ぶっとん");
		 bt.setLayoutX(100);
		 bt.setLayoutY(100);
		 bt.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(sp.getChildren().size()==1) {
					Resistor rs=new Resistor();
					rs.ra.setLayoutX(75);
					rs.ra.setLayoutY(75);
					Wire wr=new Wire();
					wr.line.setLayoutX(50);
					wr.line.setLayoutY(50);
					sp.getChildren().addAll(wr.line,rs.ra);
				}
				else {
					List<Node> list=new ArrayList<Node>();
					sp.getChildren().forEach(a->{
						if(a.getClass()!=Button.class) {
							list.add(a);
						}
					});
					list.forEach(a->{
						sp.getChildren().remove(a);
					});
				}
			}
		 });
		 sp.setOnMousePressed(e->{
			 offsetX = e.getX();
			 offsetY=  e.getY();
		 });
		 sp.addEventHandler(MouseDragEvent.MOUSE_DRAGGED,e->{
				 if(e.getSceneX()-offsetX<=0) {
					 sp.setLayoutX(0);
				 }
				 else if(e.getSceneX()-offsetX>=ap.getWidth()-sp.getWidth()) {
					 sp.setLayoutX(ap.getWidth()-sp.getWidth());
				 }
				 else {
					 sp.setLayoutX(e.getSceneX()-offsetX);
				 }
			 if(e.getSceneY()-offsetY<=mb.getHeight()) {
				 sp.setLayoutY(mb.getHeight());
			 }
			 else if(e.getSceneY()-offsetY>=ap.getHeight()-sp.getHeight()) {
				 sp.setLayoutY(ap.getHeight()-sp.getHeight());
			 }
			 else{
				 sp.setLayoutY(e.getSceneY()-offsetY);
			 }

		 });
		 ap.getChildren().addAll(sp);
		 sp.getChildren().add(bt);
	}
	
	public Image readImage(String path) {
		Image img=null;
		//jarから読み込み
		img = new Image(getClass().getResourceAsStream(path));
		return img;
	}

	@FXML
	public void exportScreenShot() {
		Main.exportScreenshot();
	}

	@FXML
	public void closeWindow() {
		Main.stage.fireEvent(new WindowEvent(Main.stage,WindowEvent.WINDOW_CLOSE_REQUEST));
	}

	@FXML
	public void changeImage() {
		imageNum=(imageNum+1)%images.size();
		back.setImage(images.get(imageNum));
	}

	@FXML
	public void eraseImage() {
		if(back.isVisible()) {
			back.setVisible(false);
		}
		else {
			back.setVisible(true);
		}
	}

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
