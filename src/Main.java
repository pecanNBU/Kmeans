import java.util.ArrayList;

public class Main {
    private static ArrayList<float[]> users;
    public static void main(String[] args) {
        User user = new User();
        users =(ArrayList<float[]>) user.CreateUser();

        Canopy canopy = new Canopy(users);
        int m = canopy.cluster();
        System.out.println(m);

        Kmeans cu = new Kmeans(users);
        cu.execute(m);


//        for (int j=0;j<user.k;j++){
//            float[] element = users.get(j);
//            System.out.println("("+element[0]+","+element[1]+")");
//        }
    }
}
