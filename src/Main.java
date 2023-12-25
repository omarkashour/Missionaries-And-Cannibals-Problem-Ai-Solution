import atlantafx.base.theme.PrimerLight;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	Font customFontRegular = Font.loadFont(Main.class.getResourceAsStream("/Product Sans Regular.ttf"), 22);
	static Font customFontBold = Font.loadFont(Main.class.getResourceAsStream("/Product Sans Bold.ttf"), 22);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		TabPane tp = new TabPane();
		Tab bfsTab = new Tab("BFS");
		Tab dfsTab = new Tab("DFS");
		bfsTab.setClosable(false);
		dfsTab.setClosable(false);
		tp.getTabs().addAll(bfsTab, dfsTab);
		Scene scene = new Scene(tp, 1600, 800);

		BFSscreen bfsScreen = new BFSscreen(primaryStage, scene);
		bfsTab.setContent(bfsScreen);

		DFSscreen dfsScreen = new DFSscreen(primaryStage, scene);
		dfsTab.setContent(dfsScreen);

		Application.setUserAgentStylesheet(new PrimerLight().getUserAgentStylesheet());
		scene.getStylesheets().add("style.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Ai Project 1 - Omar Kashour - 1210082");
		primaryStage.setResizable(false);
		primaryStage.show();
//		primaryStage.setFullScreen(true);

	}
}
