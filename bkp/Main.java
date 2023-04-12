import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {


    public static void Q1( KDTree tree,double[][] points){
        double[] low = new double[]{500,500,500};
        double[] high = new double[]{900,900,900};
        List<double[]> res=tree.rangeSearch(low,high);
//
        res.forEach(arr->{
            System.out.println("");
            System.out.print("P -> ");
            for(double i:arr){
                System.out.print(i+" , ");
            }
        });

        System.out.println("_______________Q1__________");
        System.out.println("ip : "+points.length);
        System.out.println("op : "+res.size());
    }

    public static void Q2( KDTree tree,double[][] points) {
//        double[] low = new double[] {160.63,707.75,120.72};
        double[] low = new double[] {339.07,20.88,168.38};
//        List<double[]>  c=  tree.closerPoints(low);
        double[]  c=  tree.closerPoints(low);

//        c.forEach(arr->{
//            System.out.println("");
//            System.out.print("P -> ");
//            for(double i:arr){
//                System.out.print(i+" , ");
//            }
//        });
        System.out.println(Arrays.toString(c));
        System.out.println("_______________Q2__________");
        System.out.println("ip : "+points.length);
//        System.out.println("op : "+c.size());

    }



    public static void main(String[] args) {
//        System.out.println("Hello world!");
        double[] a = new double[]{0,10,0};
        double[] b = new double[]{10,0,0};
        double[] c = new double[]{160,707,120};
//        double[] high = new double[]{900,900,900};
        double[][] points =  CSVReader.getPointsList();
//        points[points.length]=c;
//        double[][] points =  new double[][]{a,b,c};
//        System.out.println("ip : "+points.length);
        KDTree tree = new KDTree();
        tree.build(points);
//       Q1(tree,points);
        Q2(tree,points);
    }
}