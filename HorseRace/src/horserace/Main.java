package horserace;

public class Main {

    public static void main(String[] args) {

        HorseRace hr1 = new HorseRace();
        HorseRace hr2 = new HorseRace();

        Radio radio = new Radio("RADIO PLUS");
        Teletext t = new Teletext("TELETEXT REALITY");
        BettingWebsite bw = new BettingWebsite("SUPER SPORT");

        IHorseRaceListener annonymous = new IHorseRaceListener() {
            @Override
            public void raceStart(HorseRace hr) {
                System.out.println("Annonymous listener race START");
            }

            @Override
            public void raceEnd(HorseRace hr) {
                System.out.println("Annonymous listener race END");
            }
        };

        hr1.addListeners(radio, t, bw, annonymous);
        hr2.addListeners(radio, t, bw, annonymous);

        hr1.start();
        hr2.start();

    }
}
