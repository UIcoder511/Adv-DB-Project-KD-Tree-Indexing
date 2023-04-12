import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void Q1( KDTree tree){
        double[] low = new double[]{500,500,500};
        double[] high = new double[]{1000,1000,1000};
        long ts1 = System.currentTimeMillis();
        List<double[]> res=tree.rangeSearch(low,high);
        long ts2 = System.currentTimeMillis();
        System.out.println("\n\n_______________Q1__________\n");
        System.out.println("Query low : "+Arrays.toString(low));
        System.out.println("Query high : "+Arrays.toString(high));
//
//        res.forEach(arr->{
//            System.out.println("");
//            System.out.print("P -> ");
//            for(double i:arr){
//                System.out.print(i+" , ");
//            }
//        });

        try {
            CSVReader.writeToFile("Q1",res);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        System.out.println("\n\nip : "+tree.getSize());
        System.out.println("op : "+res.size());
        System.out.println("Q1 execution time : "+(ts2-ts1)+" miliseconds");
    }

    public static void Q2( KDTree tree) {
        double[] low = new double[] {91.7,918.4,600.4};
//        double[] low = new double[] {1,9,4};
        long ts1 = System.currentTimeMillis();
        List<double[]>  c=  tree.closerPoints(low);
        long ts2 = System.currentTimeMillis();
//        double[]  c=  tree.closerPoints(low);
        System.out.println("\n\n_______________Q2__________\n");
        System.out.println("Query point : "+Arrays.toString(low));
//        System.out.println("Query high : "+Arrays.toString(high));
//        c.forEach(arr->{
//            System.out.println("");
//            System.out.print("P -> ");
//            for(double i:arr){
//                System.out.print(i+" , ");
//            }
//        });

        try {
            CSVReader.writeToFile("Q2",c);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        System.out.println(Arrays.toString(c));

        System.out.println("\n\nip : "+tree.getSize());
        System.out.println("op : "+c.size());
        System.out.println("Q2 execution time : "+(ts2-ts1)+" miliseconds");

    }



    public static void main(String[] args) {
//        System.out.println("Hello world!");
        double[] a = new double[]{0,10,0};
        double[] b = new double[]{10,0,0};
        double[] c = new double[]{160,707,120};
//        double[] high = new double[]{900,900,900};
        KDTree tree = new KDTree();
        long ts1 = System.currentTimeMillis();


        CSVReader.getPointsList(point->{
            tree.insert(point);
//            System.out.println(point);
        });
        long ts2 = System.currentTimeMillis();
        System.out.println("No of nodes : "+tree.getSize());
        System.out.println("Tree creation execution time : "+(ts2-ts1)+" miliseconds");
//        points[points.length]=c;
//        double[][] points =  new double[][]{a,b,c};
//        System.out.println("ip : "+points.length);

//        tree.build(points);
//        System.out.println(tree);
       Q1(tree);
       Q2(tree);
    }
}