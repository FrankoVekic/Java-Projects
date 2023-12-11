package diagonalarray;

import java.util.Scanner;

public class DiagonalArray {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a random string: ");
        String input = scanner.nextLine();

        char[][] array = new char[input.length()][input.length()];

        StaticUtils.createDiagonalArray(input, array);

        StaticUtils.print(array);

        scanner.close();

    }

}
