import Controller.Controller;
import Model.GameManager;
import View.GameMenu;
import View.GameView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class ChessGameMain extends Application {


    private GameManager theModel;
    private GameView theView;
    private Controller theController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        theModel = new GameManager();
        theView  = new GameView(theModel);
    }

    @Override
    public void start(Stage primaryStage) {
        theController = new Controller(theView,theModel);

        //MenuBar menuBar = new MenuBar();
        //GameMenu gameMenuBar = new GameMenu();

        Scene scene = new Scene(theView.getRoot());
        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
