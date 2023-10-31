package com.games;

public class Game {

    private String title;
    private int releaseYear;
    private GameType type;
    private GameConsole console;
    private static int gameCounter;

    public Game(GameType type, GameConsole console) {
        this.type = type;
        this.console = console;
        gameCounter++;
    }

    public GameType getType() {
        return type;
    }

    public void setType(GameType type) {
        this.type = type;
    }

    public GameConsole getConsole() {
        return console;
    }

    public void setConsole(GameConsole console) {
        this.console = console;
    }

    public static int getGameCounter() {
        return gameCounter;
    }

    public static void setGameCounter(int gameCounter) {
        Game.gameCounter = gameCounter;
    }

    @Override
    public String toString() {
        return "Game[" + "type=" + type + ", console=" + console + ']';
    }

}
