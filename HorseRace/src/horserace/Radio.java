package horserace;

public class Radio implements IHorseRaceListener {
    
    private String name;

    public Radio(String name) {
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
