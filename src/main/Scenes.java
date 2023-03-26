package main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Scenes {
	
	
	
	
	public static void main(String[] args) {

		Main.main(args);
	}
	
	public static void setMainMenuScene(Stage primaryStage)
	{
		primaryStage.setScene(new Scene(Scenes.getMainMenu(primaryStage), Main.screenWidth, Main.screenHeight));
	}
	
	public static VBox getMainMenu(Stage primaryStage)
	{
		primaryStage.setTitle("PacMan - Main Menu");
		final Font buttonFont = Font.font("Courier New",FontWeight.BOLD,25);
    	final Font headlineFont = Font.font("Courier New",FontWeight.BLACK,30);
		Button btnSettings = new Button();
        Button btnPlay = new Button();
        Button btnMapEdit = new Button();
        Button btnClose = new Button();
        Label headline = new Label("Main Menu");
        btnPlay.setText("Start Game");
        btnMapEdit.setText("Map Editor(DEV)");
        btnMapEdit.setStyle("-fx-background-color: red; "); 
        btnSettings.setText("Settings");
        btnClose.setText("Exit Game");
        headline.setFont(headlineFont);
        btnPlay.setFont(buttonFont);
        btnMapEdit.setFont(buttonFont);
        btnSettings.setFont(buttonFont);
        btnClose.setFont(buttonFont);
        btnSettings.setDisable(true);
        btnSettings.setTooltip(new Tooltip("Hello"));
        
        
        btnClose.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	Platform.exit();
            }
        });
        
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
        	 
            @Override
            public void handle(ActionEvent event) {
            	setGameScene(primaryStage);
            		Game game = new Game();	
            		Main.currentGame = game;
            }
        }); 
        btnMapEdit.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
            	setEditorScene(primaryStage);
            }
        });
        
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(20);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.getChildren().addAll(headline,btnPlay,btnSettings,btnClose);
        mainMenu.getChildren().addAll(btnMapEdit);
        
        return mainMenu;
	}
	
	
	public static void setGameScene(Stage primaryStage)
	{
		
		primaryStage.setTitle("PacMan - Game");
		Pane gameScene = new Pane();
		Game.gameScene = gameScene;
		int stageHeight = (int)Main.screenHeight;
		int stageWidth = (int)Main.screenWidth;
		int numberTileCols = (int)(stageWidth / Game.tileSize);
		int numberTileRows = (int)(stageHeight / Game.tileSize);
		Map.tiles = new Tile[numberTileCols][numberTileRows];
		Map.tiles1d = new Tile[numberTileCols*numberTileRows];
		//Game.loadMap(gameScene,numberTileCols,numberTileRows);
		
		
		Map.getMap(numberTileCols, numberTileRows);
		Map.loadMap(gameScene);
		int index = 0;
		for(int i = 0; i < numberTileCols;i++)
		{
			for(int j = 0; j < numberTileRows;j++)
			{

				if(Map.tiles[i][j].type == 1)
				{
					Map.numberPathTiles++;
					
					
				}
				Debug.tileDebugEventHandler(Map.tiles[i][j]);
					Map.tiles1d[index++] = Map.tiles[i][j];
			}
		}
		primaryStage.setScene(new Scene(gameScene, Main.screenWidth, Main.screenHeight));
		
	}
	
	public static void setEditorScene(Stage primaryStage)
	{
		primaryStage.setTitle("PacMan - Map Editor");
		Pane gameScene = new Pane();

		
		int stageHeight = (int)Main.screenHeight;
		int stageWidth = (int)Main.screenWidth;
		int numberTileCols = (int)(stageWidth / Game.tileSize);
		int numberTileRows = (int)(stageHeight / Game.tileSize);
		Map.tiles = new Tile[numberTileCols][numberTileRows];
		//Game.loadMap(gameScene,numberTileCols,numberTileRows);
		
		for(int i = 0; i < numberTileRows;i++)
		{
			for(int j = 0; j < numberTileCols;j++)
			{
				Tile tile;
				
				if(j == 0 || i==0 || j==(numberTileCols - 1) || i == (numberTileRows - 1))
				{
					tile = new Tile(Game.tileSize*j,Game.tileSize*i,Game.tileSize,Game.tileSize,0);
				} else
				{
					tile = new Tile(Game.tileSize*j,Game.tileSize*i,Game.tileSize,Game.tileSize,1);
				}
				Map.tiles[j][i] = tile;
				tile.col = j;
				tile.row = i;
				MapEditor.makeEditable(tile);
				
				
				
				gameScene.getChildren().addAll(tile);	
			}
		}

		primaryStage.setScene(new Scene(gameScene, Main.screenWidth, Main.screenHeight));
		MapEditor.start(primaryStage.getScene());
	}

}
