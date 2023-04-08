package main;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Scenes {
	
	final static private Font buttonFont = Font.font("Courier New",FontWeight.BOLD,25);
	final static private Font headlineFont = Font.font("Courier New",FontWeight.BLACK,30);
	final static private Font scoreFont = Font.font("Cascadia Code",FontWeight.BLACK,15);
	
	
	public static void setMainMenuScene()
	{
		Main.stage.setTitle("PacMan - Main Menu");
		
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
            		Game game = new Game();	
            		Main.currentGame = game;
            }
        }); 
        btnMapEdit.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
            	setEditorScene(Main.stage);
            }
        });
        
        VBox mainMenu = new VBox();
        mainMenu.setSpacing(20);
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.getChildren().addAll(headline,btnPlay,btnSettings,btnClose);
        mainMenu.getChildren().addAll(btnMapEdit);
        
        Main.stage.setScene(new Scene(mainMenu, Main.screenWidth, Main.screenHeight));
	}
	
	
	public static Node getPauseMenu()
	{
		VBox pauseBackground = new VBox();
		pauseBackground.setAlignment(Pos.CENTER);
		Rectangle btn = new Rectangle();
		btn.setWidth(Main.screenWidth);
		btn.setHeight(Main.screenHeight);
		btn.opacityProperty().set(0.5);
		btn.setFill(Color.BLACK);
		pauseBackground.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY, Insets.EMPTY)));
		pauseBackground.getChildren().add(btn);
		return btn;
	}
	
	
	public static void setGameScene(int level)
	{
		
		
		Main.stage.setTitle("PacMan - Game");
		Pane gameScene = new Pane();
		Game.gameScene = gameScene;
		int stageHeight = (int)Main.screenHeight;
		int stageWidth = (int)Main.screenWidth;
		int numberTileCols = (int)(stageWidth / Game.tileSize);
		int numberTileRows = (int)(stageHeight / Game.tileSize);
		Pane pauseBackground = new Pane();
		Rectangle rec = new Rectangle();
		rec.setFill(Color.BLACK);
		rec.setWidth(Main.screenWidth);
		rec.setHeight(Main.screenHeight);
		rec.setOpacity(0.5);
		VBox pauseMenu = new VBox();
		Button pauseResume = new Button("Resume");
		Button pauseMainMenu = new Button("Back to Main Menu");
		pauseResume.setFont(buttonFont);
		pauseResume.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
            	pauseBackground.setVisible(false);
				Main.currentGame.changePause();
            }
        });
		pauseMainMenu.setFont(buttonFont);
		pauseMainMenu.setOnAction(new EventHandler<ActionEvent>() {
	       	 
            @Override
            public void handle(ActionEvent event) {
            	Main.currentGame.stopGame();//TODO should save game
            	setMainMenuScene();
            	
            }
        });
		pauseMenu.setSpacing(20);
		pauseMenu.setAlignment(Pos.CENTER);
		pauseMenu.getChildren().addAll(pauseResume,pauseMainMenu);
		pauseBackground.getChildren().addAll(rec,pauseMenu);
		pauseBackground.setVisible(false);
		pauseMenu.setVisible(true);
		
		VBox statsBox = new VBox();
		HBox liveBox = new HBox();
		LiveCounter liveCounter = new LiveCounter();
		Label scoreBoard = new Label("Score: 0");
		scoreBoard.setFont(scoreFont);
		scoreBoard.setTextFill(Color.LIGHTGREY);
		Game.scoreLabel = scoreBoard;
		Game.liveCounter =liveCounter;
		liveBox.getChildren().add(liveCounter);
		statsBox.getChildren().addAll(scoreBoard,liveBox);
		
		Map.tiles = new Tile[numberTileCols][numberTileRows];
		Map.tiles1d = new Tile[numberTileCols*numberTileRows];
		
		Map.getMap(level,numberTileCols, numberTileRows);
		Map.loadMap(gameScene);
		gameScene.getChildren().addAll(pauseBackground);
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
			System.out.println("loaded path tiles : " + Map.numberPathTiles);
		}
		gameScene.getChildren().addAll(statsBox);
		
		Main.stage.setScene(new Scene(gameScene, Main.screenWidth, Main.screenHeight));
		pauseMenu.setLayoutX(Main.screenWidth/2-pauseMenu.getWidth()/2);
		pauseMenu.setLayoutY(Main.screenHeight/2-pauseMenu.getHeight()/2);
		Main.stage.getScene().addEventFilter(KeyEvent.KEY_PRESSED,e ->{
			if(e.getCode() == KeyCode.ESCAPE)
			{
				if(Main.currentGame != null)
				{
					pauseBackground.setVisible(!pauseBackground.isVisible());
					Main.currentGame.changePause();
				}
			}
		});
		
		
		
	}
	
	
	
	public static void setGameOverScene(int score, boolean won)
	{
		Main.currentGame = null;
		Button btnNext = new Button();
        Button btnMainMenu = new Button();
        Label headline = new Label();
		if(won == true)
		{
			headline.setTextFill(Color.GREEN);
			Main.stage.setTitle("Pacman - You Won!");
			btnNext.setText("Next Level");
			headline.setText("You Won! Score: " + score);
			btnNext.setOnAction(new EventHandler<ActionEvent>() {
	        	 
		            @Override
		            public void handle(ActionEvent event) {
		            	Game game = new Game();
	            		Main.currentGame = game;
		            }
		        });
			
		}else
		{
			headline.setTextFill(Color.RED);
			Main.stage.setTitle("Pacman - Game Over!");
			btnNext.setText("Try Again");
			headline.setText("Game Over! Score: " + score);
			
			btnNext.setOnAction(new EventHandler<ActionEvent>() {
	        	 
	            @Override
	            public void handle(ActionEvent event) {
	            	Game game = new Game();	
	            	game.getLevel();
            		Main.currentGame = game;
	            }
	        });
		}
		btnMainMenu.setText("Main Menu");
		btnMainMenu.setOnAction(new EventHandler<ActionEvent>() {
       	 
            @Override
            public void handle(ActionEvent event) {
            	setMainMenuScene();
            }
        });
		btnNext.setFont(buttonFont);
		btnMainMenu.setFont(buttonFont);
		headline.setFont(headlineFont);
		VBox menu = new VBox();
		menu.setSpacing(20);
		menu.setAlignment(Pos.CENTER);
		menu.getChildren().addAll(headline,btnNext,btnMainMenu);
		
		Main.stage.setScene(new Scene(menu,Main.screenWidth, Main.screenHeight));
        
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
				MapEditor.makeEditable(tile);
				
				
				
				gameScene.getChildren().addAll(tile);	
			}
		}

		primaryStage.setScene(new Scene(gameScene, Main.screenWidth, Main.screenHeight));
		MapEditor.start(primaryStage.getScene());
	}

}
