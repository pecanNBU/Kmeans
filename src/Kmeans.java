import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dgf on 2016/5/31.
 */
public class Kmeans {
    private int k;// 簇数目
    private int m;// 迭代次数
    private int dataSetLength;// 数据及元素个数

    private ArrayList<float[]> dataSet;// 数据集链表
    private ArrayList<float[]> center;// 中心链表
    private ArrayList<ArrayList<float[]>> cluster; //簇
    private ArrayList<Float> jc;// 误差平方和，k越接近dataSetLength，误差和越小
    private Random random;

    /**
     * 设置分组原始数据集
     getter and setter
     */
    public ArrayList<float[]> getDataSet() {
        return dataSet;
    }
    public void setDataSet(ArrayList<float[]> dataSetim) {
        this.dataSet = dataSetim;
    }

    /**
     * 获取结果 分组
     *
     * @return cluster
     */

    public ArrayList<ArrayList<float[]>> getCluster() {
        return cluster;
    }


    /**
     * 获取簇中心 分组
     *
     * @return center
     */

    public ArrayList<float[]> getClustercenter() {
        return center;
    }


    /**
     * 构造函数 ，传入需要分成的簇数目
     *
     * @param k
     *
     */
    public void initclusternumber(int k) {
        if (k <= 0) {
            k = 1;
        }
        this.k = k;
    }

    /**
     * 初始化
     */
    private void init(int  clusternumber) {
        initclusternumber(clusternumber);
        m = 0;
        random = new Random();
//        if (dataSet == null || dataSet.size() == 0) {
//            initDataSet();
//        }
        dataSetLength = dataSet.size();
        if (k > dataSetLength) {
            k = dataSetLength;
        }
        center = initCenters();
        cluster = initCluster();
        jc = new ArrayList<Float>();
    }

    /**
     *如果调用者未初始化数据集，则采用内部测试数据集
     */
    public Kmeans(ArrayList<float[]> user){
        this.dataSet=(ArrayList<float[]>) user.clone();
    }
//    private void initDataSet() {
//        dataSet = new ArrayList<float[]>();
//        float[][] dataSetArray = new float[][] { { 8, 2 }, { 3, 4 }, { 2, 5 },
//                { 4, 2 }, { 7, 3 }, { 6, 2 }, { 4, 7 }, { 6, 3 }, { 5, 3 },
//                { 6, 3 }, { 6, 9 }, { 1, 6 }, { 3, 9 }, { 4, 1 }, { 8, 6 } };
//
//        for (int i = 0; i < dataSetArray.length; i++) {
//            dataSet.add(dataSetArray[i]);
//        }
//    }

    /**
     *初始化中心数据链表，分成多少簇就有多少中心点
     * 值随机
     *
     * @return 中心点集
     */
    private ArrayList<float[]> initCenters() {
        ArrayList<float[]> center = new ArrayList<float[]>();
        int[] randoms = new int[k];
        boolean flag;

        for (int i = 1; i < k; i++) {
            flag = true;
            int temp = 0;
            while (flag) {
                temp = random.nextInt(dataSetLength);
                /*返回一个伪随机数，它是取自此随机数生成器序列的、在 0（包括）和指定值（不包括）之间均匀分布的 int 值。*/
                int j = 0;
                while (j < i) {
                    if (temp == randoms[j]) {
                        break;
                    }
                    j++;
                }
                if (j == i) {
                    flag = false;
                }
            }
            randoms[i] = temp;
        }

        /*测试伪随机数生成情况*/
        for(int i=0;i<k;i++)
        {
            System.out.println("test1:randoms["+i+"]="+randoms[i]);
        }

        System.out.println();
        for (int i = 0; i < k; i++) {
            center.add(dataSet.get(randoms[i]));
            // 生成初始中心链表
        }
        return center;
    }

    /**
     *初始化簇集合
     *
     * @return 一个分为k簇的空数据集合
     */
    private ArrayList<ArrayList<float[]>> initCluster() {
        ArrayList<ArrayList<float[]>> cluster = new ArrayList<ArrayList<float[]>>();
        for (int i = 0; i < k; i++) {
            cluster.add(new ArrayList<float[]>());
        }

        return cluster;
    }

    /**
     *计算两个点之间的距离
     *
     * @param element
     *            点1
     * @param center
     *            点2
     * @return 距离
     */
    private float distance(float[] element, float[] center) {
        float distance = 0.0f;
        float temp=0.0f,sum=0.0f;

        for (int i=1;i<element.length;i++){
            temp=element[i]-center[i];
            sum+=temp*temp;
        }
        distance = (float) Math.sqrt(sum);


        /*float x = element[0] - center[0];
        float y = element[1] - center[1];
        float z = x * x + y * y;
        distance = (float) Math.sqrt(z);*/

        return distance;
    }

    /**
     *获取距离集合最小距离的位置
     *
     * @param distance
     *          距离组数
     * @return 最小距离在距离数组中位置
     */
    private int minDistance(float[] distance) {
        float minDistance = distance[0];
        int minLocation = 0;
        for (int i = 1; i < distance.length; i++) {
            if (distance[i] < minDistance) {
                minDistance = distance[i];
                minLocation = i;
            } else if (distance[i] == minDistance) // 如果相等随即返回一个位置
            {
                if (random.nextInt(10) < 5) {
                    minLocation = i;
                }
            }
        }

        return minLocation;
    }

    /**
     *核心，将当前元素放置最小距离中心相关的簇中
     */
    private void clusterSet() {
        float[] distance = new float[k];
        for (int i = 0; i < dataSetLength; i++) {
            for (int j = 0; j < k; j++) {
                distance[j] = distance(dataSet.get(i), center.get(j));
                // System.out.println("test2:"+"dataSet["+i+"],center["+j+"],distance="+distance[j]);

            }
            int minLocation = minDistance(distance);
            // System.out.println("test3:"+"dataSet["+i+"],minLocation="+minLocation);
            // System.out.println();

            cluster.get(minLocation).add(dataSet.get(i));// 核心当前元素放置最小距离相关簇中

        }
    }

    /**
     *求两点误差平方的方法
     *
     * @param element
     *          点1
     * @param center
     *          点2
     * @return 误差平方
     */
    private float errorSquare(float[] element, float[] center) {
        float sum = 0;

        for (int i=1;i<element.length;i++){
            float temp=element[i]-center[i];
            sum+=temp*temp;
        }

        float errSquare = (float) Math.sqrt(sum);

        return errSquare;
        /*float x = element[0] - center[0];
        float y = element[1] - center[1];

        float errSquare = x * x + y * y;

        return errSquare;*/
    }

    /**
     *计算误差平方和准则函数的方法
     */
    private void countRule() {
        float jcF = 0;
        for (int i = 0; i < cluster.size(); i++) {
            for (int j = 0; j < cluster.get(i).size(); j++) {
                jcF += errorSquare(cluster.get(i).get(j), center.get(i));

            }
        }
        jc.add(jcF);
    }

    /**
     *设置新的簇中心方法
     */
    private void setNewCenter() {
        for (int i = 0; i < k; i++) {
            int n = cluster.get(i).size();
            if (n != 0) {
                float[] newCenter=new float[dataSet.get(0).length];
                for (int j=1;j<dataSet.get(0).length;j++){
                    newCenter[j]=0;
                }
               /* float[] newCenter = { 0, 0 };*/
                for (int m = 0; m < n; m++) {
                    for (int q=1;q<dataSet.get(0).length;q++){
                        newCenter[q] += cluster.get(i).get(m)[q];
                    }
                }
                // 设置一个平均值
                for (int j=1;j<dataSet.get(0).length;j++){
                    newCenter[j] = newCenter[j] / n;
                }
               /* newCenter[0] = newCenter[0] / n;
                newCenter[1] = newCenter[1] / n;*/
                center.set(i, newCenter);
            }
        }
    }

    /**
     *打印数据，测试用
     *
     * @param dataArray
     *            数据集
     * @param dataArrayName
     *            数据集名称
     */
    public void printDataArray(ArrayList<float[]> dataArray,
                               String dataArrayName) {
        for (int i = 0; i < dataArray.size(); i++) {
            System.out.print("print:" + dataArrayName + "[" + i + "]={");
            for (int k=0;k<dataSet.get(0).length;k++){
                System.out.print(dataArray.get(i)[k]+",");
            }
            System.out.print("}");
            System.out.println();
        }
        System.out.println("===================================");
    }

    /**
     * Kmeans算法核心过程
     * @param clusternumber
     */
    private void kmeans(int clusternumber) {
        init(clusternumber);
        // printDataArray(dataSet,"initDataSet");
        // printDataArray(center,"initCenter");

        // 循环分组，直到误差不变为止
        while (true) {
            clusterSet();
            // for(int i=0;i<cluster.size();i++)
            // {
            // printDataArray(cluster.get(i),"cluster["+i+"]");
            // }

            countRule();

            // System.out.println("count:"+"jc["+m+"]="+jc.get(m));

            // System.out.println();
            // 误差不变，分组完成
            if (m != 0) {
                if (jc.get(m) - jc.get(m - 1) == 0) {
                    break;
                }
            }

            setNewCenter();
            // printDataArray(center,"newCenter");
            m++;
            cluster.clear();
            cluster = initCluster();
        }

        // System.out.println("note:the times of repeat:m="+m);//�����������
    }

    /**
     * 执行算法
     */
    public void execute(int clusternumber) {
        long startTime = System.currentTimeMillis();
        System.out.println("kmeans begins");
        kmeans(clusternumber);
        long endTime = System.currentTimeMillis();
        System.out.println("kmeans running time=" + (endTime - startTime)
                + "ms");
        System.out.println("kmeans ends");
        System.out.println();
    }
}
