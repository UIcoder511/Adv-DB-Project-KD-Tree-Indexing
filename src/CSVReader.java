import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@FunctionalInterface
interface Function3<In1> { // (In1,In2,In3) -> Out
    public void apply(In1 in1);
}

public class CSVReader {
    public static   void   getPointsList(Function3<double[]> f){
//        List<double[]> pointsList = new ArrayList<>();
        String csvFile = System.getProperty("user.dir")+"\\src\\data\\coordinates.csv"; //coordinates
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
//                pointsList.add(point);
                f.apply(point);
//                // Do something with the values, such as print them to the console
//                for (String value : values) {
//                    System.out.print(value + " ");
//                }
//                System.out.println();
            }

//            double[][] points = pointsList.toArray(new double[pointsList.size()][]);
//            return points;

        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
    }

    public static void writeToFile(String query, List<double[]> list) throws IOException {
        String outputFile =System.getProperty("user.dir")+"\\src\\data\\"+query+"-output.txt";
        BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));

        for (double[] point:list) {
            out.write(Arrays.toString(point));
            out.newLine();
        }
        out.close();
    }
}