package MVC.View;

import MVC.Model.GameManager;
import MVC.View.NetworkingPopUps.HostGamePopUp;
import MVC.View.View2D.PieceView2D;
import MVC.View.View3D.ChessPiece3D;
import MVC.View.View3D.SquareView3D;
import MVC.View.View2D.BoardView2D;
import MVC.View.View2D.SquareView2D;
import MVC.View.View3D.BoardView3D;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.net.UnknownHostException;


public class GameView {

    //TODO - change this
    private SimpleBooleanProperty is3D = new SimpleBooleanProperty(false);

    private HBox gameHBox;
    private VBox root;
    VBox rightSideContainer;
    private HBox sideCoordAndBoardContainer;
    private int windowHeight = 750;
    private int windowWidth = 900;
    BoardView board;
    private FlowPane deadPieceHolderWhite;
    private FlowPane deadPieceHolderBlack;
    GameManager gm;
    Text inCheckTextBlack;
    Text inCheckTextWhite;

    private PerspectiveCamera camera;
    private String CAMERA = "Cam2";
    private final GameMenuBar gameMenuBar;

    public GameView(GameManager model) throws UnknownHostException {

        this.gm = model;
        //make gameHBox which is a set of horizontal boxes
        gameHBox = new HBox();

        //root.setAlignment(Pos.CENTER);
        gameHBox.setMinSize(windowWidth,windowHeight);


        //***************************************************************
        //Background stuff
        //TODO - find a different background texture
        String imageLink = "https://images.freecreatives.com/wp-content/uploads/2016/01/Free-Photoshop-Purity-Wood-Texture.jpg";//"https://images.freecreatives.com/wp-content/uploads/2016/01/High-Quality-Oak-Seamless-Wood-Texture.jpg";//"https://tr.rbxcdn.com/7324f5e7134f93c9c9e41e30c4d5bb0a/420/420/Decal/Png";
        BackgroundImage bgImage = new BackgroundImage(new Image(imageLink), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(bgImage);
        gameHBox.setBackground(background);
        //***************************************************************

        //add side coords to view
        VBox boardCoordContainer = new VBox();
        sideCoordAndBoardContainer = new HBox();

        reloadGameView();

        boardCoordContainer.getChildren().add(sideCoordAndBoardContainer);
        //put this whole shabang in the root, an HBox
        gameHBox.getChildren().add(boardCoordContainer);


        rightSideContainer = new VBox();
        createDeadPieceHolders();
        makeInCheckText();
        gameHBox.getChildren().add(rightSideContainer);

        //make the menu bar and add the menu bar and the gameHBox to the root
        gameMenuBar = new GameMenuBar();
        root = new VBox();
        root.getChildren().addAll(gameMenuBar,gameHBox);

    }

    public void reloadGameView(){
        sideCoordAndBoardContainer.getChildren().clear();

        sideCoordAndBoardContainer.getChildren().add(makeSideBoardCoords());

        //TODO - dont reset game model
        //this.gm.resetGame();

        if(is3D.get()) {
            board = new BoardView3D(gm.getBoard());
            //***********************************
            Group miniRoot = new Group();

            //initialize the camera
            camera = new PerspectiveCamera(true);
            //camera.setVerticalFieldOfView(false);
            camera.setNearClip(1.0);
            camera.setFarClip(10000.0);

            SubScene boardScene = new SubScene(miniRoot, 640, 640);
            //SubScene boardScene = new SubScene(board, 640,640);
            boardScene.setCamera(camera);
            boardScene.setFill(Color.GRAY);
            miniRoot.getChildren().add(board);
            changeCameraOnClick(boardScene);

            sideCoordAndBoardContainer.getChildren().add(boardScene);
            //***********************************
        }else {
            board = new BoardView2D(640, gm.getBoard());
            //add the actual board in
            sideCoordAndBoardContainer.getChildren().add(board);
        }

    }

    /**Creates 2 flowpanes
     * Each flowpane contains the dead/captured pieces for each team
     * Adds each flowpane to the rightside Container
     */
    private void createDeadPieceHolders() {

        //make the flowpanes for dead pieces
        deadPieceHolderWhite = new FlowPane();
        deadPieceHolderBlack = new FlowPane();
        Text deadPieceHolderWhiteName = new Text();
        Text deadPieceHolderBlackName = new Text();
        deadPieceHolderBlackName.setFont(new Font(20));
        deadPieceHolderWhiteName.setFont(new Font(20));
        deadPieceHolderBlackName.setText("Captured Player Two Pieces:");
        deadPieceHolderWhiteName.setText("Captured Player One Pieces:");

        rightSideContainer.getChildren().add(deadPieceHolderWhiteName);
        rightSideContainer.getChildren().add(deadPieceHolderWhite);
        rightSideContainer.getChildren().add(deadPieceHolderBlackName);
        rightSideContainer.getChildren().add(deadPieceHolderBlack);
    }

    /**
     * makes the side board coords
     * @return a Vbox containing the coords
     */
    public VBox makeSideBoardCoords(){
        VBox sideBoardCoords = new VBox(56);
        sideBoardCoords.setPadding(new Insets(10));
        for (int i = 0; i < 8; i++) {
            Text coord = new Text();
            coord.setFont(new Font(20));
            coord.setText(String.valueOf(i));
            sideBoardCoords.getChildren().add(coord);
        }
        return sideBoardCoords;
    }


    /**
     * makes the top board coords
     * @return a Hbox containing the coords
     */
    public HBox makeTopBoardCoords(){
        HBox coords = new HBox(67);
        coords.setPadding(new Insets(10));
        Region spacer = new Region();
        spacer.setPrefWidth(0);
        coords.getChildren().add(spacer);
        for (int i = 0; i < 8; i++) {
            Text coord = new Text();
            coord.setFont(new Font(20));
            coord.setText(String.valueOf(i));
            coords.getChildren().add(coord);
        }
        return coords;
    }

    //TODO change this up probably
    /**
     * grabs the piece image at the specified spot and puts it in its respective deadpiece holder depending on team
     * @param row the row of the square
     * @param col the column of the square
     * @param deadPieceHolder the correct team's dead piece holder
     */
    public void killPiece(int row, int col, FlowPane deadPieceHolder){
        if(is3D.get()){
            SquareView3D oldLocationSquare = (SquareView3D)board.getSquareAt(row,col);
            ChessPiece3D pieceKilled = oldLocationSquare.removePieceFromSquare();

            deadPieceHolder.getChildren().add(new PieceView2D(pieceKilled.getPieceType(), pieceKilled.getPieceColor()).getView());
        } else {
            SquareView2D oldLocationSquare = (SquareView2D)board.getSquareAt(row,col);
            deadPieceHolder.getChildren().add(oldLocationSquare.getPiece());
            oldLocationSquare.getChildren().clear();
        }
    }

    /**
     * Makes text object for showing if a player is in check
     */
    private void makeInCheckText() {
        inCheckTextBlack = new Text();
        inCheckTextWhite = new Text();
        inCheckTextBlack.setFont(new Font(40));
        inCheckTextWhite.setFont(new Font(40));
        inCheckTextBlack.setFill(Color.RED);
        inCheckTextWhite.setFill(Color.RED);
        inCheckTextBlack.setText("");
        inCheckTextWhite.setText("");
        rightSideContainer.getChildren().add(inCheckTextBlack);
        rightSideContainer.getChildren().add(inCheckTextWhite);
    }

    private void changeCameraOnClick(SubScene subScene) {

        Transform cam1A = new Translate(400,1600,-1400);
        Transform cam1B = new Rotate(40,Rotate.X_AXIS);

        Transform cam2A = new Translate(0,SquareView3D.SQUARE_SIZE*8,0);
        Transform cam2B = new Rotate(180,Rotate.X_AXIS);

        Transform cam2C = new Translate(SquareView3D.SQUARE_SIZE*8,0,0);
        Transform cam2D = new Rotate(180,Rotate.Y_AXIS);

        camera.getTransforms().addAll(cam1A,cam1B); //set camera angle for view 1

        subScene.setOnKeyPressed(event -> {
            System.out.println(CAMERA);
            if (event.getCode() == KeyCode.C) {

                camera.getTransforms().addAll(cam1A,cam1B);
                if (CAMERA == "Cam1") {
                    try {
                        camera.getTransforms().addAll(cam1B.createInverse(),cam1A.createInverse());
                        board.getTransforms().addAll(cam2D.createInverse(),cam2C.createInverse(),cam2B.createInverse(),cam2A.createInverse());
                        CAMERA = "Cam2";
                    } catch (NonInvertibleTransformException e) {
                        e.printStackTrace();
                    }
                }
                else if (CAMERA == "Cam2")  {
                    try {
                        camera.getTransforms().addAll(cam1B.createInverse(),cam1A.createInverse());
                        board.getTransforms().addAll(cam2A,cam2B,cam2C,cam2D);
                    } catch (NonInvertibleTransformException e) {
                        e.printStackTrace();
                    }
                    CAMERA = "Cam1";

                }

                System.out.println(CAMERA);
            }
        });
    }

    public Text getInCheckTextBlack() {
        return inCheckTextBlack;
    }

    public Text getInCheckTextWhite() {
        return inCheckTextWhite;
    }

    public BoardView getBoard() {
        return board;
    }

    public VBox getRoot() {
        return root;
    }

    public FlowPane getDeadPieceHolderWhite() {
        return deadPieceHolderWhite;
    }

    public FlowPane getDeadPieceHolderBlack() {
        return deadPieceHolderBlack;
    }

    public boolean is3D() {
        return is3D.get();
    }

    public SimpleBooleanProperty is3DProperty() {
        return is3D;
    }

//    public void setIs3D(boolean is3D) {
//        this.is3D.setValue(is3D);
//    }

    public GameMenuBar getGameMenuBar() {
        return gameMenuBar;
    }
}
