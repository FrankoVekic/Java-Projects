
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class GuessMyNumber {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int userNumber = 0;
        int wrongAnswerCount = 0;
        int random = ThreadLocalRandom.current().nextInt(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        do {
            System.out.println("Guess the number I imagined");
            userNumber = scanner.nextInt();

            System.out.println("You think it's: " + userNumber);

            if (userNumber == random) {
                System.out.println("You guessed right! The number was: " + random + "\nNumber of wrong guesses: " + wrongAnswerCount);
            } else {

                System.out.println("Wrong, guess again.");
                wrongAnswerCount++;

                if (userNumber > random) {
                    System.out.println("Your number was higher then the number i imagined.");

                } else {
                    System.out.println("Your number is less then the one i imagined");
                }
            }

        } while (userNumber != random);
    }

}
