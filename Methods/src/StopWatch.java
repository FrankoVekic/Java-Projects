import java.util.logging.Level;
import java.util.logging.Logger;

public class StopWatch {

    public static void main(String[] args) {

        int sec = 0;
        int counter = 0;

        if (args.length < 1) {
            showHelp(null);
        }

        try {
            sec = Integer.parseInt(args[0]);
        } catch (Exception e) {
            showHelp( e);
        }

        for (int hours = 0; hours < 24; hours++) {
            for (int minutes = 0; minutes < 60; minutes++) {
                for (int seconds = 0; seconds < 60; seconds++) {
                    System.out.printf("%s:%s:%s", getNormalized(hours), getNormalized(minutes), getNormalized(seconds));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StopWatch.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.print("\b\b\b\b\b\b\b\b");
                    if (counter == sec) {
                        System.exit(0);
                    }
                    counter++;
                }
            }
        }

    }

    public static String getNormalized(int x) {
        return (x > 9) ? "" + x : "0" + x;
    }

    private static void showHelp(Exception ex) {
        if(ex != null){
            ex.printStackTrace();
        }
        System.out.println("Please use program like this:");
        System.out.println("\t java StopWatch.java <seconds>");
        System.exit(0);
    }

}