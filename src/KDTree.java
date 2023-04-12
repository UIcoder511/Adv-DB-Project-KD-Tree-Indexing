import java.util.*;

public class KDTree {
    private Node root;
    private int size=0;

    public int getSize() {
        return size;
    }

    private static class Node {
        private double[] point;
        private Node left, right;

        public Node(double[] point) {
            this.point = point;
        }
    }

    public void insert(double[] point){
        size++;
        root = insertHelper(root, point, 0);
    }

    private Node insertHelper(Node node, double[] point, int depth) {
        if (node == null) {
            return new Node(point);
        }

        int currDepth = depth % 3;
        if (point.equals(node.point)) {
            return node;
        }

        if (point[currDepth] < node.point[currDepth]) {
            node.left = insertHelper(node.left, point, depth + 1);
        } else {
            node.right = insertHelper(node.right, point, depth + 1);
        }

        return node;
    }

    public void build(double[][] points) {
        root = build(points, 0, points.length - 1, 0);
    }

    private Node build(double[][] points, int lo, int hi, int depth) {
        if (lo > hi) return null;
        Arrays.sort(points,(o1, o2) -> {
            if (o1[depth] < o2[depth]) return -1;
            if (o1[depth] > o2[depth]) return 1;
            return 0;
        });
        int mid = lo + (hi - lo) / 2;
        double[] point = points[mid];
        Node node = new Node(point);

        int nextDepth = (depth + 1) % 3;
        node.left = build(Arrays.copyOfRange(points, lo, mid-1) , 0, mid-1, nextDepth);
        node.right = build(Arrays.copyOfRange(points, mid+1, hi), 0, hi-mid-1, nextDepth);

        return node;
    }

    public List<double[]> rangeSearch(double[] min, double[] max) {
        List<double[]> result = new ArrayList<>();
        rangeSearch(root, min, max, result, 0);
        return result;
    }

    private void rangeSearch(Node node, double[] min, double[] max, List<double[]> result, int depth) {
        if (node == null) return;

        double[] point = node.point;
        if (point[0] >= min[0] && point[0] <= max[0] &&
                point[1] >= min[1] && point[1] <= max[1] &&
                point[2] >= min[2] && point[2] <= max[2]) {
            result.add(point);
        }

        int nextDepth = (depth + 1) % 3;
        if (node.left != null && node.left.point[nextDepth] >= min[nextDepth]) {
            rangeSearch(node.left, min, max, result, nextDepth);
        }
        if (node.right != null && node.right.point[nextDepth] <= max[nextDepth]) {
            rangeSearch(node.right, min, max, result, nextDepth);
        }
    }

//    public List<double[]> closerPoints(double[] point) {
//        List<double[]> result = new ArrayList<>();
//        closerPoints(root, point, result, Double.MAX_VALUE, 0);
//        return result;
//    }
//
//    private void closerPoints(Node node, double[] point, List<double[]> result, double minDistance, int depth) {
//        if (node == null) return;
//
//        double distance = distance(node.point, point);
//        if (distance < minDistance) {
//            result.clear();
//            result.add(node.point);
//            minDistance = distance;
//        } else if (distance == minDistance) {
//            result.add(node.point);
//        }
//
//        int nextDepth = (depth + 1) % 3;
//        double diff = point[depth] - node.point[depth];
//        Node first = diff < 0 ? node.left : node.right;
//        Node second = diff < 0 ? node.right : node.left;
//        closerPoints(first, point, result, minDistance, nextDepth);
//        if (diff < minDistance) {
//            closerPoints(second, point, result, minDistance, nextDepth);
//        }
//    }

    public List<double[]> closerPoints(double[] point) {
//        List<double[]> result = new ArrayList<>();
        return closerPoints(root, point,  0,new ArrayList<double[]>());
//        return result;
    }

    private List<double[]> closerPoints(Node node, double[] point, int depth,List<double[]> res) {
        if (node == null) return res;
//        System.out.println(Arrays.toString(node.point));
        double diff = point[depth] - node.point[depth];
        Node first = diff < 0 ? node.left : node.right;
        Node second = diff < 0 ? node.right : node.left;

//        System.out.println(depth);
//        double distance = distance(node.point, point);
//        System.out.println(distance+ " min : "+minDistance);
//        if (distance < minDistance) {
//
//            result.clear();
//            result.add(node.point);
//            minDistance = distance;
//        } else if (distance == minDistance) {
//            result.add(node.point);
//        }

//        int nextDepth = (depth + 1) % 3;

//        Node first = diff < 0 ? node.left : node.right;
//        Node second = diff < 0 ? node.right : node.left;
        List<double[]> best=res;
        if(res.size()==0 || first!=null ){
            List<double[]> temp= closerPoints(first, point,(depth + 1) % 3,res);
            best= closest(temp,	node.point, point);
//        System.out.println("min:  : d - ");

        }
        double radiusSqrt = distance(point,best.get(best.size()-1));
        double dist = point[depth] - node.point[depth];
//        System.out.println(Math.abs(dist)+" : "+radiusSqrt);
        if (radiusSqrt > Math.abs(dist)) {
            if(res.size()==0 || second!=null){
                List<double[]> temp=closerPoints(second, point,  (depth + 1) % 3,best);
                best = closest(temp, best.get(best.size()-1), point);
            }

        }

        return best;
    }

    List<double[]> closest(List<double[]> n0list, double[] n1,  double[] target) {
        if (n0list==null || n0list.size()==0) return (new ArrayList<double[]>(){{
            add(n1);
        }});

        if (n1 == null) return n0list;

        double d1 = distance(n0list.get(n0list.size()-1), target);
        double d2 = distance(n1, target);

        if (d1 < d2)
        {
//            System.out.println("d1 "+d1);
            return n0list;
        }

        else if(d1==d2){
//            System.out.println("d1 "+d1);
            //            System.out.println("d1 "+d2);
            if(!n0list.contains(n1))

            n0list.add(n1);
            //            System.out.println("d1 "+d2);
            return n0list;
        }else{
//            System.out.println("d1 "+d1);
            return (new ArrayList<double[]>(){{
                add(n1);
            }});
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<Node> q = new LinkedList<>();
        q.add(this.root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node n = q.poll();
                if (n != null) {
                    sb.append(Arrays.toString(n.point)).append(", ");
                    q.add(n.left);
                    q.add(n.right);
                } else {
                    sb.append("null, ");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }



//    public List<double[]> closerPoints(double[] point) {
//        List<double[]> result = new ArrayList<>();
//        closerPoints(root, point, result, Double.MAX_VALUE, 0);
//        return result;
//    }
//
//    private void closerPoints(Node node, double[] point, List<double[]> result, double minDistance, int depth) {
//        if (node == null) return;
////        System.out.println(Arrays.toString(node.point));
//        double distance = distance(node.point, point);
//        System.out.println(distance+ " min : "+minDistance);
//        if (distance < minDistance) {
//
//            result.clear();
//            result.add(node.point);
//            minDistance = distance;
//        } else if (distance == minDistance) {
//            result.add(node.point);
//        }
//
//        int nextDepth = (depth + 1) % 3;
//        double diff = point[depth] - node.point[depth];
//        Node first = diff < 0 ? node.left : node.right;
//        Node second = diff < 0 ? node.right : node.left;
//        closerPoints(first, point, result, minDistance, nextDepth);
//        System.out.println("min:  : d - ");
//        double radiusSqrt = distance(point,result.get(result.size()-1));
//        double dist = point[depth] - node.point[depth];
//        if (radiusSqrt >= dist) {
//            closerPoints(second, point, result, minDistance, nextDepth);
//        }
//    }
//

    private double distance(double[] a, double[] b) {
        double dx = a[0] - b[0];
        double dy = a[1] - b[1];
        double dz = a[2] - b[2];
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


//    java






//    public List<double[]> findNearbyPoints(double[] targetPoint, double distanceThreshold) {
//        List<double[]> nearbyPoints = new ArrayList<>();
//        PriorityQueue<NodeDistance> pq = new PriorityQueue<>();
//
//        findNearbyPoints(root, targetPoint, distanceThreshold, pq, 0);
//
//        while (!pq.isEmpty()) {
//            nearbyPoints.add(pq.poll().node.point);
//        }
//
//        return nearbyPoints;
//    }
//
//    private void findNearbyPoints(Node node, double[] targetPoint, double distanceThreshold,
//                                  PriorityQueue<NodeDistance> pq, int depth) {
//        if (node == null) return;
//
//        double distance = distance(node.point, targetPoint);
//        if (distance <= distanceThreshold) {
//            pq.offer(new NodeDistance(node, distance));
//        }
//
//        int nextDepth = (depth + 1) % 3;
//        if (node.left != null && targetPoint[depth] < node.point[depth] + distanceThreshold) {
//            findNearbyPoints(node.left, targetPoint, distanceThreshold, pq, nextDepth);
//        }
//        if (node.right != null && targetPoint[depth] > node.point[depth] - distanceThreshold) {
//            findNearbyPoints(node.right, targetPoint, distanceThreshold, pq, nextDepth);
//        }
//    }
//
//    private static class NodeDistance implements Comparable<NodeDistance> {
//        private final Node node;
//        private final double distance;
//
//        public NodeDistance(Node node, double distance) {
//            this.node = node;
//            this.distance = distance;
//        }
//
//        @Override
//        public int compareTo(NodeDistance other) {
//            return Double.compare(distance, other.distance);
//        }
//    }



}
