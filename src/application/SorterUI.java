package application;
	
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;



public class SorterUI extends Application {
	
	private static String m_path;
	private List<File> m_files=new ArrayList();
	
	private boolean readDir(final String path){
		final File dir = new File(path);
		if(dir.isDirectory()) {
			
		try {
			
			for (final File fileEntry : dir.listFiles()) {
				if (!fileEntry.isDirectory()) {
					try {
						if(Files.probeContentType(fileEntry.toPath()).contains("image")) {
							m_files.add(fileEntry);
						}
					}catch(NullPointerException e) {
						
					}
					
					
				}
			}
			if(!m_files.isEmpty()) {
				return true;
			}else {
				m_path=null;
				Alert alert=new Alert(AlertType.ERROR);
				alert.setTitle("File Error");
				alert.setHeaderText("The folder you selected is empty or it contains not sopported formats!");
				alert.setContentText("Please check your path.\nSupported formats: (Jpeg,Jpg)");

				alert.showAndWait();
			}
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
			
		}
		}else {
			Alert alert=new Alert(AlertType.ERROR);
			alert.setTitle("Folder Error");
			alert.setHeaderText("The path entered is not path to a folder (directory)");
			alert.setContentText("Please check your path.");

			alert.showAndWait();
			
		}
		
		return false;
	}
	
	
	
	public void start(Stage primaryStage) {
		try {
			Pane pane = new Pane();
			primaryStage.setTitle("Sorter");
			
			Label lb_path = new Label("Enter your folder path that contains your desire pictures:");
			
			
			TextField tf_path=new TextField();
			tf_path.setPrefSize(500, 25);
			
			Button bt_next=new Button("Next"),bt_exit=new Button("Exit");
			bt_next.setPrefSize(100, 20);
			bt_exit.setPrefSize(100, 20);
			
			HBox hb_pathLabel=new HBox(),hb_pathInput=new HBox(),hb_buttonsRoot=new HBox();
			hb_pathLabel.getChildren().add(lb_path);
			hb_pathInput.getChildren().add(tf_path);
			hb_buttonsRoot.getChildren().addAll(bt_next,bt_exit);
			
			
			hb_buttonsRoot.setSpacing(30);
			
			hb_pathLabel.setPadding(new Insets(20,0,5,0));
			hb_pathInput.setPadding(new Insets(5,0,20,0));
			hb_buttonsRoot.setPadding(new Insets(20,0,20,0));
			
			hb_pathLabel.setAlignment(Pos.CENTER);
			hb_pathInput.setAlignment(Pos.CENTER);
			hb_buttonsRoot.setAlignment(Pos.CENTER);
			
			VBox VboxRoot=new VBox();
			VboxRoot.getChildren().addAll(hb_pathLabel,hb_pathInput);
			
			
			BorderPane root = new BorderPane(pane);
			
			root.setCenter(VboxRoot);
			root.setBottom(hb_buttonsRoot);
			Scene scene = new Scene(root,700,200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			bt_next.setOnAction((ActionEvent event)->{
				m_path=tf_path.getText();
				if(readDir(m_path)) {
					Label lb_options=new Label("How do you want it to be sorted?");
					final ToggleGroup group =new ToggleGroup();
					RadioButton rb_HVSort=new RadioButton("Horizontal and vertical sort"),rb_dimensionalSort=new RadioButton("Dimensional sort");
					rb_HVSort.setToggleGroup(group);
					rb_HVSort.setSelected(true);
					rb_dimensionalSort.setToggleGroup(group);
					Button bt_sort=new Button("Sort"),bt_back=new Button("Back"),bt_exit2=new Button("Exit");
					
					HBox hb_question=new HBox(),hb_option1=new HBox(),hb_option2=new HBox(),hb_buttonsOption=new HBox();
					VBox VboxOptions=new VBox();
					
					hb_question.getChildren().add(lb_options);
					hb_option1.getChildren().add(rb_HVSort);
					hb_option2.getChildren().add(rb_dimensionalSort);
					hb_buttonsOption.getChildren().addAll(bt_sort,bt_back,bt_exit2);
					
					bt_back.setPrefSize(100, 20);
					bt_sort.setPrefSize(100, 20);
					bt_exit2.setPrefSize(100, 20);
					
					hb_question.setAlignment(Pos.CENTER);
					hb_question.setPadding(new Insets(20,0,20,0));
					
					
					hb_option1.setPadding(new Insets(20,0,5,150));
					
					
					hb_option2.setPadding(new Insets(5,0,20,150));
					
					hb_buttonsOption.setAlignment(Pos.CENTER);
					hb_buttonsOption.setSpacing(20);
					hb_buttonsOption.setPadding(new Insets(20,0,20,0));
					
					VboxOptions.getChildren().addAll(hb_question,hb_option1,hb_option2);
					
					BorderPane optionPage=new BorderPane(pane);
					optionPage.setCenter(VboxOptions);
					optionPage.setBottom(hb_buttonsOption);
					
					Scene sceneOptions=new Scene(optionPage,700,200);
					primaryStage.setScene(sceneOptions);
					bt_back.setOnAction((ActionEvent event2)->{
						
						primaryStage.setScene(scene);
					});
					bt_exit2.setOnAction((ActionEvent event2)->{
						Platform.exit();
					});
					bt_sort.setOnAction((ActionEvent event2)->{
						List<List<File>> portions =new ArrayList();
						for (int i=0;i<m_files.size();i+=100) {
							portions.add(m_files.subList(i, Math.min(m_files.size(), i+100)));
						}
						
						if(group.getSelectedToggle().equals(rb_HVSort)) {
							for(List<File> portion:portions) {
								
								HVSort m_HVSort =new HVSort(portion,m_path);
								m_HVSort.start();
							}
						}else {
							
						}
					
						Task<Void> task=new Task<Void>() {

							@Override
							protected Void call() throws Exception {
								// TODO Auto-generated method stub
								return null;
							}
							
							
								
						};
						task.setOnSucceeded(e -> {
						    Stage dialog = new Stage();
						    dialog.initStyle(StageStyle.UTILITY);
						    Scene scenePopUp = new Scene(new Group(new Text(25, 25, "All is done!")));
						    dialog.setScene(scenePopUp);
						    dialog.showAndWait();
						});
						try {
							new Thread(task).join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					});
				}
				
			});
			bt_exit.setOnAction((ActionEvent event)->{
				Platform.exit();
			});
			
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
