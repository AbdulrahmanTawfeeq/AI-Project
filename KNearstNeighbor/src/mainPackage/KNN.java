package mainPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class KNN {
    private ArrayList<String[]> dataSet;
    private Header headerState;
    private String[] dataSetHeader;
    private ArrayList<Double> distances;

    public enum Header {
        WITH, WITHOUT
    }

    public KNN(ArrayList<String[]> dataSet, Header headerState) {
        this.headerState = headerState;
        setDataSet(dataSet);
    }

    public String classify(String[] newData, short k) {
        calcDistances(newData, k);
        return voting(minDistances(distances, k));
    }

    private void calcDistances(String[] newData, short k) {
        distances = new ArrayList<Double>();
        for (int i = 0; i < dataSet.size(); i++) {
            double distance = 0;
            for (int j = 0; j < dataSet.get(i).length - 1; j++) {
                try {
                    if (newData[j] != null && dataSet.get(i)[j] != null) {
                        distance += Math
                                .abs(Double.parseDouble(newData[j]) - Double.parseDouble(dataSet.get(i)[j]));
                    }
                } catch (NumberFormatException e) {
                    System.out.println(e);
                }
            }
            distances.add(distance);
        }
    }

    private double[] minDistances(ArrayList<Double> arrayList, short k) {
        double[] distances = new double[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            distances[i] = arrayList.get(i);
        }
        Arrays.sort(distances);
        double[] minKDistances = new double[k];
        for (int i = 0; i < k; i++) {
            minKDistances[i] = distances[i];
        }
        return minKDistances;
    }

    private String voting(double[] minDistances) {
        ArrayList<String> nearestLabels = new ArrayList<String>();
        for (int i = 0; i < minDistances.length; i++) {
            String[] record = dataSet.get(distances.indexOf(minDistances[i]));
            nearestLabels.add(record[record.length - 1]);
        }
        ArrayList<String[]> countedLabels = new ArrayList<String[]>();
        Set<String> set = new HashSet<String>(nearestLabels);
        set.forEach((classLabel) -> {
            int counter = 0;
            for (int i = 0; i < nearestLabels.size(); i++) {
                if (classLabel.equals(nearestLabels.get(i))) {
                    counter++;
                }
            }
            countedLabels.add(new String[] { String.valueOf(counter), classLabel });
        });
        int max = 0;
        int indexOfMax = -1;
        boolean can = true;
        for (int i = 0; i < countedLabels.size(); i++) {
            if (Integer.parseInt(countedLabels.get(i)[0]) > max) {
                max = Integer.parseInt(countedLabels.get(i)[0]);
                indexOfMax = i;
            } else if (Integer.parseInt(countedLabels.get(i)[0]) == max && i == countedLabels.size() - 1) {
                can = false;
                break;
            }
        }
        if (can) {
            return countedLabels.get(indexOfMax)[1];
        } else {
            return "Cannot vote, change the number of k.";
        }
    }

    public void setDataSet(ArrayList<String[]> dataSet) {
        if (headerState == Header.WITHOUT) {
            this.dataSet = dataSet;
        } else if (headerState == Header.WITH) {
            dataSetHeader = dataSet.remove(0);
            this.dataSet = dataSet;
        }
    }

    public void setHeaderState(Header headerState) {
        this.headerState = headerState;
    }

    public String[] getDataSetHeader() {
        return dataSetHeader;
    }

    public ArrayList<Double> getDistances() {
        return distances;
    }

    @Override
    public String toString() {
        return "Dataset: " + dataSet.toString() + "\n";
    }
}
