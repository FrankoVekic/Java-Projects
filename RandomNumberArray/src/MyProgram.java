
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class MyProgram {

    public static void main(String[] args) {

        // loop that fills the array with random numbers from 1 - 100 v1
        int[] newArray = new int[10];
        for (int j = 0; j < newArray.length; j++) {

            newArray[j] = ThreadLocalRandom.current().nextInt(1, 101);
        }

        for (int k = 0; k < newArray.length; k++) {

            System.out.println("Value at index:  " + k + " is: " + newArray[k]);
        }

        // loop that fills the array with random numbers from 1 - 100 v2
        int[] myArray = new int[10];

        for (int i = 0; i < myArray.length; i++) {

            myArray[i] = ThreadLocalRandom.current().nextInt(1, 101);
        }
        System.out.println(Arrays.toString(myArray));

    }
}
