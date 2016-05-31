import java.util.Random;

/**
 * Created by dgf on 2016/5/31.
 */
public class Test {
    public void test(){
        Random rand = new Random();
        int[] dataSet = new int[5];
        dataSet[0] = rand.nextInt(100);
        dataSet[1] = rand.nextInt(100);
        System.out.println(dataSet[0]);
        System.out.println(dataSet[1]);

    }
}
