package eurojackpot;

import java.util.Scanner;

public class EuroJackpot {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int[] fiveNumbers = new int[5];
        int[] twoNumbers = new int[2];
        int[] jackpotFiveNumbers = new int[5];
        int[] jackpotTwoNumbers = new int[2];

        StaticUtils.getNumbers(scanner, fiveNumbers, twoNumbers);

        jackpotFiveNumbers = StaticUtils.get5From50();
        jackpotTwoNumbers = StaticUtils.get2From12();
        System.out.println("-------------- YOUR NUMBERS --------------");
        StaticUtils.print(fiveNumbers);
        StaticUtils.print(twoNumbers);
        System.out.println("-------------- EUROJACKPOT NUMBERS --------------");
        StaticUtils.print(jackpotFiveNumbers);
        StaticUtils.print(jackpotTwoNumbers);

        int twoHits = StaticUtils.getHits(twoNumbers, jackpotTwoNumbers);
        int fiveHits = StaticUtils.getHits(fiveNumbers, jackpotFiveNumbers);

        System.out.println("You got: " + fiveHits + " of the five, and " + twoHits + " of two.");
        
        scanner.close();

    }

}
