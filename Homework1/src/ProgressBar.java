
public class ProgressBar {

    public static void main(String[] args) {

        int userInput = 0;
        String equals = "";

        try {
            userInput = Integer.parseInt(args[0]);
        } catch (Exception e) {
            System.out.println("Invalid input, must be number.");

        }

        if (userInput < 1 || userInput > 100) {
            System.out.println("Number has to be from 1 - 100");
            return;
        }

        for (int i = 0; i <= userInput; i++) {

            System.out.print("\r");
            System.out.printf("%s%d%%", equals, i);

            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            equals += "=";

        }

    }

}
