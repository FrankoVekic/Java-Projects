package filereaderhomework;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileReader {

    public static int fileCounter = 0;
    public static int dirCounter = 0;
    public static long memoryUsage = 0;

    public static void main(String[] args) throws IOException {


        
        try {
            
            // get specific file, SET YOUR PATH
            Path path1 = Paths.get("");
            BasicFileAttributes attr = Files.readAttributes(path1, BasicFileAttributes.class);
            FileTime date = attr.creationTime();

            String type = Files.probeContentType(path1);

            // path to any dir, SET YOUR PATH
            File dir = new File("");

            if (dir.exists() && dir.isDirectory()) {
                System.out.println("DIR:" + dir.getAbsolutePath());
                reader(dir,"");
                System.out.println("\n---------------------------------------------------------------");
                System.out.println("Type: \t\t" + (type == null ? "File folder" : type));
                System.out.println("Location:\t" + dir.getParent());
                System.out.println("Size:\t\t" + memoryUsage / 1024 + " KB " + "(" + memoryUsage + " bytes)");
                System.out.println("Contains:\t" + fileCounter + " Files, " + dirCounter + " Folders");
                System.out.println("---------------------------------------------------------------");
                System.out.println("Created:\t" + formatDateTime(date));
            } else {
                System.out.println("This dir does not exist.");
            }
        } catch (Exception e) {
            System.out.println("This file does not exist.");
            e.printStackTrace();
        }

    }

   public static void reader(File dir, String s) {
       
    File[] files = dir.listFiles();

    try {
        for (int i = 0; i < files.length; i++) {
            
            File file = files[i];
            if (file.isDirectory()) {
                
                dirCounter++;
                System.out.println(s + "dir-- " + file.getName());               
                reader(file, s + "    ");
                
            } else {
                
                fileCounter++;
                memoryUsage += file.length();   
                System.out.println(s + "file-- " + file.getName());
            }

            if (i < files.length - 1) {
                System.out.println(s + "|");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public static String formatDateTime(FileTime fileTime) {

        LocalDateTime localDateTime = fileTime
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return localDateTime.format(
                DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy, h:mm:ss a"));
    }

}
