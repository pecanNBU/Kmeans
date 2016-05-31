import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dgf on 2016/5/31.
 */
public class User {
    /*用户信息
    *年龄（1-100） /10
    * 性别（10表示男，0表示女）
    * 籍贯（1-8）表示不同省份
    * 职业（1-10）表示不同职业
    * 利用随机数模拟生成400个用户
     */
    public int k = 5000;
    private ArrayList<float[]> user;
    public ArrayList<float[]> CreateUser(){
        Random rand = new Random();
        float dataSet[][] = new float[k][4];
        user = new ArrayList<float[]>();
        for(int i = 0 ; i < k ; i++){
            dataSet[i][0] = rand.nextInt(100);
            dataSet[i][1] = rand.nextInt(100);
            dataSet[i][2] = rand.nextInt(100);
            dataSet[i][3] = rand.nextInt(100);
            user.add(dataSet[i]);
        }
//        for (int j=0;j<k;j++){
//            int[] element = user.get(j);
//            System.out.println("("+element[0]+","+element[1]+")");
//        }
    return user;
    }
}