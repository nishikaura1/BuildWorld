package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Engine {
    TERenderer ter = new TERenderer();
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        ter.initialize(WIDTH, HEIGHT);
        InputHandler p = new InputHandler(finalWorldFrame, ter);
        p.handleInput("");
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, running both of these:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
        InputHandler p = new InputHandler(finalWorldFrame, ter);
        p.handleInput(input);
        return finalWorldFrame;
    }
    public static void main(String[] args) {
        Engine e = new Engine();
        e.interactWithKeyboard();
        TETile[][] frame = e.interactWithInputString("RDDD:Q");
        System.out.println(TETile.toString(frame));
        e.ter.initialize(WIDTH, HEIGHT);
        e.ter.renderFrame(frame);
    }

}
