package horserace;

import java.util.ArrayList;

public class BettingWebsite implements IHorseRaceListener {

    private String name;
    private boolean isBetting = true;
    ArrayList<Integer> bets = new ArrayList<>();

    public BettingWebsite(String name) {
        this.name = name;
    }

    @Override
    public void raceStart(HorseRace hr) {
        System.out.println(name + " was informed that race started");
    }

    @Override
    public void raceEnd(HorseRace hr) {
        System.out.println(name + " was informed that race started");
    }

}
