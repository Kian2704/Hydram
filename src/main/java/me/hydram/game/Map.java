package me.hydram.game;

import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/*Läd die Map aud der .pacmanmap Datei in ein zweidimensionales Tilearray*/

public class Map {
    public static Tile[][] tiles; //cols - rows
    public static Tile[] tiles1d;
    public static Vec2 pacmanSpawn = null;
    public static Vec2 pacmanSpawn2 = null;
    public static int numberPathTiles;
    public static Vec2 ghostSpawn;

    public static Tile[][] getMap(int level, int cols, int rows) {
        numberPathTiles = 0;
        try {
            InputStream is;
            if (Main.enableMultiplayer == false)
                is = Map.class.getResourceAsStream("maps/map_" + level + ".pacmanmap");
            else
                is = Map.class.getResourceAsStream("maps/map_localmultiplayer_" + level + ".pacmanmap");
            //File mapFile = new File("maps/map_" + level + ".pacmanmap");
            BufferedReader mapFile = new BufferedReader(new InputStreamReader(is));

            //öffnet Map Datei
            Scanner myReader = new Scanner(mapFile);
            if (tiles == null) {
                System.out.println("Error Tile Array not initialized!");
                myReader.close();
                return null;
            }
            int count = 0;
            while (myReader.hasNextLine()) {
                if (count >= rows) {
                    System.out.println("Map size doesn't match Screen size!");
                }
                String line = myReader.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (i > cols) {
                        System.out.println("Map size doesn't match Screen size!");
                        myReader.close();
                        return null;
                    }
                    //0 = Wall, 1 = Path,2 = Ghost Spawn, 3 Pacman Spawn, 4 empty tile, 5 Spawn Blocker, 6 Powerup, 7 Breakable wall tile
                    switch (line.charAt(i)) {
                        case 'X': { //wenn Map Datei an aktueller Stelle "X" setze tiles an gegebener Stelle auf der richtigen Wert für Weg
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 1);
                            tiles[i][count].setEnt(new Point());
                            break;
                        }
                        case 'N': { //wenn Map Datei an aktueller Stelle "N" setze tiles an gegebener Stelle auf der richtigen Wert für leeres Feld
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 4);
                            break;
                        }
                        case 'W':  //wenn Map Datei an aktueller Stelle "W" setze tiles an gegebener Stelle auf der richtigen Wert für Wand
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 0);
                            break;
                        case 'P': { //wenn Map Datei an aktueller Stelle "P" setze tiles an gegebener Stelle auf der richtigen Wert für Pacman
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 3);
                            if (pacmanSpawn == null) {
                                pacmanSpawn = new Vec2(tiles[i][count].getX(), tiles[i][count].getY());
                            } else if (pacmanSpawn != null && Main.enableMultiplayer == true) {
                                pacmanSpawn2 = new Vec2(tiles[i][count].getX(), tiles[i][count].getY());
                            }
                            break;
                        }
                        case 'G': {  //wenn Map Datei an aktueller Stelle "" setze tiles an gegebener Stelle auf der richtigen Wert für
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 2);
                            ghostSpawn = new Vec2(tiles[i][count].getX(), tiles[i][count].getY());
                            break;
                        }
                        case 'B': {  //wenn Map Datei an aktueller Stelle "B" setze tiles an gegebener Stelle auf der richtigen Wert für SpawnBlocker
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 5);
                            tiles[i][count].setEnt(new SpawnBlocker());
                            break;
                        }
                        case 'U': {  //wenn Map Datei an aktueller Stelle "U" setze tiles an gegebener Stelle auf der richtigen Wert für Wand
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 1);
                            tiles[i][count].setEnt(new Powerup());
                            break;
                        }
                        case 'D': {  //wenn Map Datei an aktueller Stelle "D" setze tiles an gegebener Stelle auf der richtigen Wert für Zerstörbare Wand
                            tiles[i][count] = new Tile(Game.tileSize * i, Game.tileSize * count, Game.tileSize, Game.tileSize, 7);
                            break;
                        }
                    }
                    tiles[i][count].col = i;
                    tiles[i][count].row = count;
                }
                count++;

            }
            myReader.close(); //schließe Map Datei
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        if (pacmanSpawn == null || (Main.enableMultiplayer == true && pacmanSpawn2 == null)) {
            Scenes.setMainMenuScene();
        }
        return tiles;
    }


    //läd Map
    public static void loadMap(Pane gameScene) {
        for (int i = 0; i < tiles[0].length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                gameScene.getChildren().addAll(tiles[j][i]);
                if (tiles[j][i].getEnt() != null) {
                    gameScene.getChildren().addAll(tiles[j][i].getEnt());
                }
            }
        }

    }
}
