package byow.Core;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.Random;

public class Interactor {
    private TETile[][] finalWorldFrame;
    private InputSource inputSource;
    private TERenderer ter;
    private Random random;
    private Position avatarPos;
    private String HUDDesc;
    private boolean gameOver;
    private String movementTracker;
    private long seed;

    public Interactor(TETile[][] finalWorldFrame, InputSource inputSource, TERenderer ter, Random random, long seed) {
        this.finalWorldFrame = finalWorldFrame;
        this.inputSource = inputSource;
        this.ter = ter;
        this.random = random;
        this.seed = seed;
        this.movementTracker = "";
        placeAvatar();
    }

    private void placeAvatar() {
        avatarPos =  new Position(random.nextInt(0, finalWorldFrame.length),
                random.nextInt(0, finalWorldFrame[0].length));
        while (getTile(avatarPos)!=Tileset.FLOOR) {
            avatarPos = new Position(random.nextInt(0, finalWorldFrame.length),
                    random.nextInt(0, finalWorldFrame[0].length));
        }
        updateTile(avatarPos, Tileset.AVATAR);
    }

    private void updateTile(Position p, TETile tile) {
        finalWorldFrame[p.getX()][p.getY()] = tile;
    }

    private TETile getTile(Position p) {
        return finalWorldFrame[p.getX()][p.getY()];
    }

    public void interact(String prevMovement, String replayMovement, boolean display, boolean replayDisplay) {
        handlePriorMovement(prevMovement, false);
        handlePriorMovement(replayMovement, replayDisplay);
        movementTracker += " ";

        char previous = Character.MIN_VALUE;
        while (!gameOver) {
            if(inputSource.hasNext()) {
                char c = inputSource.next();
                handleMovement(c, previous);
                previous = c;
            }
            else if (!display) {
                gameOver = true;
            }
            if(display) {
                handleMousePress();
                drawFrame(10);
            }
        }
    }
    private void handlePriorMovement(String priorMovement, boolean display) {
        for (char key: priorMovement.toCharArray()) {
            if(display) {
                handleMousePress();
                drawFrame(250);
            }
            handleMovement(key, Character.MIN_VALUE);
        }
    }

    private void handleMovement(char key, char previous) {
        if (key == 'W') {
            moveFrom(avatarPos, avatarPos.add(0, 1), key);
        } else if (key == 'A') {
            moveFrom(avatarPos, avatarPos.add(-1, 0), key);
        } else if (key == 'S') {
            moveFrom(avatarPos, avatarPos.add(0, -1), key);
        } else if (key == 'D') {
            moveFrom(avatarPos, avatarPos.add(1, 0), key);
        } else if (previous == ':' && key == 'Q') {
            gameOver = true;
            saveWorld("world.txt");
        }
    }

    private void saveWorld(String filename) {
        try {
            File myFile = new File(filename);
            FileWriter writer = new FileWriter(myFile);
            writer.write(movementTracker + " " + seed);
            writer.close();
        }
        catch (Exception e) {}
    }
    private void handleMousePress() {
        Position mouseP = new Position((int) StdDraw.mouseX(), (int) StdDraw.mouseY());
        try {
            this.HUDDesc = getTile(mouseP).description();
        }
        catch(Exception e) { }
    }

    private void drawFrame(int time) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 14));
        ter.renderFrame(finalWorldFrame);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(4, finalWorldFrame[0].length - 1, HUDDesc);
        StdDraw.text(70, finalWorldFrame[0].length - 1, new Date().toString());
        StdDraw.show();
        StdDraw.pause(time);
    }

    private void moveFrom(Position oldP, Position newP, char key) {
        movementTracker += key;
        if (getTile(newP)!=Tileset.WALL) {
            updateTile(oldP, Tileset.FLOOR);
            updateTile(newP, Tileset.AVATAR);
            avatarPos = newP;
        }
    }

}
