package mainPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Utilites {
    /**
     * @author Abdulrahman Tawffeq
     * @param path      the name of the file to read
     * @param seperator like [:] [,] [;] [$] and etc.
     * @throws FileNotFoundException if the named file does not exist, is a
     *                               directory rather than a regular file, or for
     *                               some other reason cannot be opened for reading
     * @throws {@link                IOException} If an I/O error occurs
     * @Note UTF-8 csv file is not accepted
     * @return 2D {@link ArrayList}
     */
    public static ArrayList<String[]> readCSV(String path, String seperator) {
        String line = "";
        FileReader file = null;
        BufferedReader input = null;
        ArrayList<String[]> dataSet = null;
        try {
            file = new FileReader(path);
            input = new BufferedReader(file);
            dataSet = new ArrayList<String[]>();
            while ((line = input.readLine()) != null) {
                if (line.startsWith("ï»¿")) {
                    System.err.println("Only comma delimited csv file is accepted!");
                    break;
                }
                dataSet.add(line.split(seperator));
            }

            input.close();
            file.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return dataSet;
    }
}
