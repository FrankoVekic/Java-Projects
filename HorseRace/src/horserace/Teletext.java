package horserace;

public class Teletext implements IHorseRaceListener {

    private String name;

    public Teletext(String name) {
        this.name = name;
    }

    @Override
    public void raceStart(HorseRace hr) {
        System.out.println(name + " was informed that race started");
    }

    @Override
    public void raceEnd(HorseRace hr) {
        System.out.println(name + " was informed that race ended");
    }

}
