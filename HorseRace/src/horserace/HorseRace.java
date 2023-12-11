package horserace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HorseRace extends Thread {

    ArrayList<Horse> horses = new ArrayList<>();
    ArrayList<IHorseRaceListener> listeners = new ArrayList<>();

    @Override
    public void run(){
        myStart();
    }
    
    public void myStart() {

        System.out.println("PROGRAM STARTS HERE.");
        notifyListenersRaceStarted();
        System.out.println("---------------------");

        for (int i = 0; i < 10; i++) {
            horses.add(new Horse("Horse " + (i + 1)));
        }

        for (int i = 0; i < 10; i++) {
            sleep(1000);
            shuffleHorses(horses);
            printTopFive(horses);

        }
        notifyListenersRaceEnded();
        printWinners(horses);
        
    }

    public static void shuffleHorses(ArrayList<Horse> horses) {
        Random random = new Random();

        for (int i = horses.size() - 1; i > 0; i--) {

            int randomIndex = random.nextInt(i + 1);

            Horse temp = horses.get(i);
            horses.set(i, horses.get(randomIndex));
            horses.set(randomIndex, temp);
        }

    }
    
    public void printWinners(ArrayList<Horse> horses){
        System.out.println("WINNER: "+ horses.get(0) + "\nRUNNER UP: " + horses.get(1) + "\nTHRID PLACE: " + horses.get(2));        
    }
    

    public void printTopFive(ArrayList<Horse> horses) {
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0 -> System.out.println("FIRST PLACE: " + horses.get(i));
                case 1 -> System.out.println("SECOND PLACE: " + horses.get(i));
                case 2 -> System.out.println("THIRD PLACE: " + horses.get(i));
                case 3 -> System.out.println("FOURTH PLACE: " + horses.get(i));
                case 4 -> System.out.println("FIFTH PLACE: " + horses.get(i));
            }
        }
        System.out.println("---------------------");
    }

    public void sleep(int milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException ex) {
            Logger.getLogger(HorseRace.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addListener(IHorseRaceListener listener){
        listeners.add(listener);
    }
    
    public void addListeners(IHorseRaceListener... list){
        listeners.addAll(Arrays.asList(list));
   }

    private void notifyListenersRaceStarted() {
        for(IHorseRaceListener temp : listeners) temp.raceStart(this);
    }

    private void notifyListenersRaceEnded() {
        for(IHorseRaceListener temp : listeners) temp.raceEnd(this);
    }

}
