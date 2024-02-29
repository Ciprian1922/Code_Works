import java.util.Random;

public class GenerateArray {
    public static int[] generateArray(int length) {
        int[] a = new int[length];
        Random r=new Random();
        for (int i = 0; i < length; i++)
            a[i] = abs(r.nextInt());
        return a;
    }

    static int abs(int n)
    {
        //return n*sgn(n);
        return n>=0?n:-n;
    }

//    static int sgn(int n)
//    {
//        if(n<0)
//            return -1;
//        if(n>0)
//            return 1;
//        return 0;
//    }
}
