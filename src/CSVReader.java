import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static   double[][]   getPointsList(){
        List<double[]> pointsList = new ArrayList<>();
        String csvFile = System.getProperty("user.dir")+"\\src\\data\\coordinates.csv";
        String line;
        String csvSplitBy = ",";
        System.out.println(csvFile);

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values =line.trim().split(",");
                double[] point = new double[3];
                for (int i = 0; i < 3; i++) {
                    point[i] = Double.parseDouble(values[i]);
                }
                pointsList.add(point);
//                // Do something with the values, such as print them to the console
//                for (String value : values) {
//                    System.out.print(value + " ");
//                }
//                System.out.println();
            }

            double[][] points = pointsList.toArray(new double[pointsList.size()][]);
            return points;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}