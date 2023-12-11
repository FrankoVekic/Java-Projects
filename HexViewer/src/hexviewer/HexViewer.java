package hexviewer;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Properties;

public class HexViewer {

    public static void main(String[] args) {

        //Path path = Paths.get(args[args.length - 1]);
        Properties configuration = ArgumentProperties.readProperties();
        processFile(configuration);
    }

    private static void processFile(Properties configuration) {

        String fileToOpen = configuration.getProperty("file");
        Path path = Paths.get(fileToOpen);

        try {

            String s = Files.readString(path, Charset.forName("ISO-8859-1"));

            boolean showString = configuration.getProperty("s").equals("true");
            boolean showHex = configuration.getProperty("h").equals("true");
            boolean showBinary = configuration.getProperty("b").equals("true");
            boolean showAll = configuration.getProperty("a").equals("true");

            if (showString) {
                writeAsString(s);
            }

            if (showHex) {
                writeAsHex(s);
            }

            if (showBinary) {
                writeAsBinary(s);
            }

            if (showAll) {
                writeAsString(s);
                writeAsHex(s);
                writeAsBinary(s);
            }
        } catch (Exception e) {
            writeUsage();
            e.printStackTrace();
        }

//        for (int i = 0; i < args.length - 1; i++) {
//            switch (args[i]) {
//                case "-s":
//                    writeAsString(s);
//                    break;
//                case "-h":
//                    writeAsHex(s);
//                    break;
//                case "-b":
//                    writeAsBinary(s);
//                    break;
//                case "-a":
//                    writeAsString(s);
//                    writeAsHex(s);
//                    writeAsBinary(s);
//                    break;
//                default:
//                    writeUsage();
//            }
//        }
    }

    private static void writeUsage() {
        System.out.println("Usage:");
        System.out.println("java HexViewer.java -s -b -h -a <file>");
    }

    private static int[][] get2DimensionalArray(String s) {
        if (s.length() < 1) {
            return new int[0][0];
        }

        int rows = s.length() / 16;
        int lastRow = s.length() % 16;

        int[][] temp;

        // what is there is less then 16 characters in string
        if (rows == 0) {
            temp = new int[1][lastRow];
            int counter = 0;
            while (counter < s.length()) {
                temp[0][counter] = s.charAt(counter);
                counter++;
            }
            return temp;
        }

        // the case when there are more then 16 char
        if (lastRow > 0) {
            temp = new int[rows + 1][];
            for (int i = 0; i < rows; i++) {
                temp[i] = new int[16];
            }
            temp[rows] = new int[lastRow];
        } else {
            temp = new int[rows][];
            for (int i = 0; i < rows; i++) {
                temp[i] = new int[16];
            }
        }

        int counter = 0;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length; j++) {
                temp[i][j] = s.charAt(counter);
                counter++;
            }
        }

        return temp;

    }

    private static void writeAsString(String s) {
        System.out.println(s);
    }

    private static void writeAsHex(String s) {
        int[][] array = get2DimensionalArray(s);
        System.out.println("\n\nAddress  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e   f\tDump");

        String temp = "";
        int rowCounter = 0;

        for (int i = 0; i < array.length; i++) {
            temp += "\n";
            temp += "%1$08x ".formatted(rowCounter);

            for (int j = 0; j < array[i].length; j++) {
                temp += "%1$02x ".formatted(array[i][j]);
            }

            if (array[i].length < 16) {

                for (int x = array[i].length; x < 16; x++) {
                    temp += "   ";
                }

            }

            temp += "\t";

            for (int j = 0; j < array[i].length; j++) {
                char c = array[i][j] <= 32 || array[i][j] > 127 ? '.' : (char) array[i][j];
                temp += c;
            }

            rowCounter += 16;
        }
        System.out.println(temp);
    }

    private static void writeAsBinary(String s) {
        String temp = "\n\nAddress         0        1        2        3        4        5        6        7        8        9        a        b        c        d        e        f";

        for (int counter = 0; counter < s.length(); counter++) {
            if (counter % 16 == 0) {
                temp += "\n";
                temp += "%1$08x ".formatted(counter);
            }
            String t = Integer.toBinaryString(s.charAt(counter));

            temp += getFixedSize(t) + " ";

        }
        System.out.println(temp);
    }

    private static String getFixedSize(String t) {

        int missing = 8 - t.length();
        String temp = "";

        for (int i = missing; i > 0; i--) {
            temp += "0";
        }
        temp += t;
        return temp;
    }

}
