package eurojackpot;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class StaticUtils {
    
    

    public static void print(int[] array) {

        System.out.print("[ ");
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println("]");
    }

    public static void sortArray(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }

            }
        }
    }

    public static boolean exists(int number, int[] array) {
        for (int x : array) {
            if (x == number) {
                return true;
            }
        }
        return false;
    }

    public static int[] get6From45() {
        int[] temp = new int[6];
        for (int i = 0; i < temp.length; i++) {
            int number = ThreadLocalRandom.current().nextInt(1, 46);
            if (!exists(number, temp)) {
                temp[i] = number;
            } else {
                i--;
            }

        }
        StaticUtils.sortArray(temp);
        return temp;
    }

    public static int[] get5From50() {
        int[] temp = new int[5];
        for (int i = 0; i < temp.length; i++) {
            int number = ThreadLocalRandom.current().nextInt(1, 51);
            if (!exists(number, temp)) {
                temp[i] = number;
            } else {
                i--;
            }

        }
        StaticUtils.sortArray(temp);
        return temp;
    }

    public static int[] get2From12() {
        int[] temp = new int[2];
        for (int i = 0; i < temp.length; i++) {
            int number = ThreadLocalRandom.current().nextInt(1, 13);
            if (!exists(number, temp)) {
                temp[i] = number;
            } else {
                i--;
            }

        }
        StaticUtils.sortArray(temp);
        return temp;
    }

    public static int getHits(int[] myArray, int[] lottoArray) {

        int hits = 0;

        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < lottoArray.length; j++) {
                if (myArray[i] == lottoArray[j]) {
                    hits++;
                }
            }
        }

        return hits;
    }

    public static void print(char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println("");
        }
    }

    public static void createDiagonalArray(String input, char[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if (j == i) {
                    array[i][j] = input.charAt(i);
                } else {
                    array[i][j] = '-';
                }
            }
        }
    }

    public static void getNumbers(Scanner scanner, int[] fiveNumbers, int[] twoNumbers) {
        
        int number;

        while (true) {
            System.out.println("First enter 5 numbers from 1-50");

            for (int i = 0; i < 5; i++) {
                System.out.println("Enter number " + (i + 1) + ":");

                try {
                    number = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                    i--;
                    continue;
                }

                if (number < 1 || number > 50) {
                    System.out.println("Number has to be from 1-50");
                    i--;
                    continue;
                }

                if (StaticUtils.exists(number, fiveNumbers)) {
                    System.out.println("Number: " + number + " is already selected");
                    i--;
                    continue;
                }

                fiveNumbers[i] = number;
            }

            sortArray(fiveNumbers);

            System.out.println("Now enter 2 numbers from 1-12");

            for (int i = 0; i < 2; i++) {
                System.out.println("Enter number " + (i + 1) + ":");

                try {
                    number = scanner.nextInt();
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next();
                    i--;
                    continue;
                }

                if (StaticUtils.exists(number, twoNumbers)) {
                    System.out.println("Number: " + number + " is already selected");
                    i--;
                    continue;
                }

                if (number < 1 || number > 12) {
                    System.out.println("Number has to be from 1-12");
                    i--;
                    continue;
                }

                twoNumbers[i] = number;
            }

            sortArray(twoNumbers);

            break;
        }
    }

}
