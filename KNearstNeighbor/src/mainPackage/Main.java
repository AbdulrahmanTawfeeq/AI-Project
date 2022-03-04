package mainPackage;

import java.util.ArrayList;

import mainPackage.KNN.Header;

/**
 * Main
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Process start...");
        ArrayList<String[]> dataset = Utilites.readCSV("data/dataset.csv", ";");
        KNN knn = new KNN(dataset, Header.WITH);
        System.out.println(knn.classify(new String[] { "3", "7" }, (short) 3));
    }
}