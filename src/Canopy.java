import java.util.ArrayList;

/**
 * Created by dgf on 2016/5/31.
 */
public class Canopy {

    private ArrayList<float[]> points = (ArrayList<float[]>) new ArrayList<float[]>(); // 进行聚类的点

    private ArrayList<ArrayList<float[]>> clusters = (ArrayList<ArrayList<float[]>>) new ArrayList<ArrayList<float[]>>(); // 存储簇

    private double T2 = -1; // 阈值
    int clnm;

    /*构造函数*/
    public Canopy(ArrayList<float[]> dataSet) {
        this.points= (ArrayList<float[]>) dataSet.clone();
    }
    public Canopy() {

    }
     // 进行聚类，按照Canopy算法进行计算，将所有点进行聚类

    public int cluster() {
//        T2 = 100;
        T2 = getAverageDistance((ArrayList<float[]>) points);
        System.out.println(T2);
        while (points.size() != 0) {/*point不为空*/

            ArrayList<float[]> cluster = (ArrayList<float[]>) new ArrayList<float[]>();

            float[] basePoint = points.get(0); // 基准点
            cluster.add(basePoint);
            points.remove(0);
            int index = 0;
            while (index < points.size()) {
                float[] anotherPoint = points.get(index);

              /* double distance = Math.sqrt((basePoint.x – anotherPoint.x)

                        * (basePoint.x – anotherPoint.x)

                + (basePoint.y – anotherPoint.y)

                * (basePoint.y – anotherPoint.y));*/
                float sum=0;
                for (int i=1;i<points.get(0).length;i++){
                    float temp=0;
                    temp=basePoint[i]-anotherPoint[i];
                    sum+=temp*temp;

                }
                float distance= (float) Math.sqrt((double)sum);

                if (distance <= T2) {
                    cluster.add(anotherPoint);
                    points.remove(index);
                } else {
                    index++;
                }
            }
            clusters.add(cluster);
            clnm=cluster.size();
        }
        return clnm ;
    }

    //获取Cluster对应的中心点(各点相加求平均)
    public ArrayList<float[]> getClusterCenterPoints() {

        ArrayList<float[]> centerPoints = new ArrayList<float[]>();
      /* for (i=0;i<centerPoints.get(0).length;i++) {

            centerPoints.add(getCenterPoint((ArrayList<float[]>) cluster));

        }*/
        return centerPoints;

    }

     //得到平均距离

    private double getAverageDistance(ArrayList<float[]> points) {

        double sum = 0;
        int pointSize = points.size();
        for (int i = 0; i < pointSize; i++) {
            for (int j = 0; j < pointSize; j++) {
                if (i == j)
                    continue;
                float[] pointA = points.get(i);
                float[] pointB = points.get(j);
                for (int k = 1; k < points.get(0).length; k++) {
                    float temp;
                    temp =pointA[k]-pointB[k];
                    sum += temp * temp;
                }
               /*sum += Math.sqrt((pointA.x – pointB.x) * (pointA.x – pointB.x)
                + (pointA.y – pointB.y) * (pointA.y – pointB.y));*/
            }
        }

        int distanceNumber = pointSize * (pointSize + 1) / 2;

        double T2 = sum / distanceNumber / 32; // 平均距离的1/8

        return T2;

    }


    //得到的中心点(各点相加求平均)



    private float[] getCenterPoint(ArrayList<float[]> points) {

       /*double sumX = 0;

        double sumY = 0;

        for (flout[] point : points) {

            sumX += point.x;

            sumY += point.y;

        }

        int clusterSize = points.size();

        Point centerPoint = new Point(sumX / clusterSize, sumY / clusterSize);*/
        int pointlengths=points.get(0).length;
        float[] centerPoint=new float[ pointlengths];
        for (int i=0;i<points.size();i++) {
            float sum[]= new float[pointlengths];
            for (int j = 1; j < pointlengths; j++) {
                sum[j]+=points.get(i)[j];

            }
            while(i== pointlengths){
                for (int k = 1; k< pointlengths; k++) {
                    centerPoint[k]=sum[k]/points.size();

                }

            }
        }
        return centerPoint;
    }

     // 获取阈值T2
    public double getThreshold() {

        return T2;

    }
    public ArrayList<float[]> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<float[]> points) {
        this.points = points;
    }

}
