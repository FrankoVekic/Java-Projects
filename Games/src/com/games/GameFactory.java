package com.games;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GameFactory {

    public static Game[] getGames(int numberOfGames) {

        Game[] games = new Game[numberOfGames];

        for (int i = 0; i < games.length; i++) {
            games[i] = new Game(getRandomGameType(), getRandomGameConsole());
        }

        printOut(games);
        return new Game[numberOfGames];

    }

    private static GameType getRandomGameType() {
        GameType[] gameType = GameType.values();
        int randomType = ThreadLocalRandom.current().nextInt(0, 5);
        switch (randomType) {
            case 0:
                TypeCounter.action++;
                return GameType.ACTION;
            case 1:
                TypeCounter.adventure++;
                return GameType.ADVENTURE;
            case 2:
                TypeCounter.racing++;
                return GameType.RACING;
            case 3:
                TypeCounter.strategy++;
                return GameType.STRATEGY;
            case 4:
                TypeCounter.sport++;
                return GameType.SPORT;
        }

        return gameType[randomType];
    }

    private static GameConsole getRandomGameConsole() {
        GameConsole[] gameConsole = GameConsole.values();
        int randomConsole = ThreadLocalRandom.current().nextInt(0, 6);
        switch (randomConsole) {
            case 0:
                ConsoleCounter.nintendo++;
                return GameConsole.NINTENDO;
            case 1:
                ConsoleCounter.pc++;
                return GameConsole.PC;
            case 2:
                ConsoleCounter.ps3++;
                return GameConsole.PS3;
            case 3:
                ConsoleCounter.ps4++;
                return GameConsole.PS4;
            case 4:
                ConsoleCounter.ps5++;
                return GameConsole.PS5;
            case 5:
                ConsoleCounter.xbox++;
                return GameConsole.XBOX;
        }
        return gameConsole[randomConsole];
    }

    public static void printOut(Game[] games) {
        for (Game g : games) {
            System.out.println(g);
        }

        System.out.println("\nGAME CONSOLES: \n ");
        System.out.println("NINTENDO: " + ConsoleCounter.nintendo);
        System.out.println("PC: " + ConsoleCounter.pc);
        System.out.println("PS3: " + ConsoleCounter.ps3);
        System.out.println("PS4: " + ConsoleCounter.ps4);
        System.out.println("PS5: " + ConsoleCounter.ps5);
        System.out.println("XBOX: " + ConsoleCounter.xbox);

        System.out.println("\nGAME TYPES: \n");
        System.out.println("ACTION: " + TypeCounter.action);
        System.out.println("ADVENTURE: " + TypeCounter.adventure);
        System.out.println("STRATEGY: " + TypeCounter.strategy);
        System.out.println("SPORT: " + TypeCounter.sport);
        System.out.println("RACING: " + TypeCounter.racing);

        System.out.println("Total games: " + Game.getGameCounter());
    }


}
