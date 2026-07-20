package java03;

import java.util.Date;
import java.time.LocalDate;
import java.util.Arrays;

public class arr {
    public static void main(String[] args) {
        int[] arr=new int[5];
        
        arr[0]=1;
        arr[1]=43;
        arr[3]=23;
        arr[2]=-13;
        arr[4]=55;

        Arrays.sort(arr);

        for (int i : arr) {
            System.out.println(i);
        }

        // System.out.print(arr.toString());

        Date d= new Date();
        // 
        LocalDate t= LocalDate.now();

        LocalDate futuDate= t.plusDays(5);

        System.out.println(futuDate);
        System.out.println(d);
    }
}
