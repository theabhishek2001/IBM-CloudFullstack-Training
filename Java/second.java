import java.util.Scanner; // Step 1: Import Scanner
public class second {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); // Step 2: Create Scanner
        // System.out.print("Enter your name: ");
        // String name = sc.nextLine(); // Read String
        // System.out.print("Enter your age: ");
        // int age = sc.nextInt(); // Read integer
        // System.out.print("Enter your GPA: ");
        // double gpa = sc.nextDouble(); // Read decimal
        // System.out.println("Welcome, " + name + "!");
        // System.out.println("Age: " + age + ", GPA: " + gpa);

       
        // String sports=sc.nextLine();

        // System.out.println("Enter your favorite sport: " + sports);

        // int color= sc.nextInt();

        // switch(color) {
        //     case 1:
        //         System.out.println("Red");
        //         break;
        //     case 2:
        //         System.out.println("Blue");
        //         break;
        //     case 3:
        //         System.out.println("Green");
        //         break;
        //     default:
        //         System.out.println("Invalid color choice");
        // }

//         Temperature Converter
// Take Celsius as input from the user. Convert to Fahrenheit using: 
// (C × 9/5) + 32. Print the result
        // double celcius= sc.nextDouble();
        // double feh =(celcius * 9/5) + 32;

        // System.out.print("Temperature is "+ feh);

        // ⭐⭐
//  Challenge 2
// Write a program that takes 5 numbers as 
// input into an array, then prints the 
// maximum, minimum, sum, and average.

        // int[] numbers = new int[5];

        // for(int i=0;i<5;i++){
        //     numbers[i] =  sc.nextInt();
        // }

        // int maxm= numbers[0];
        // int minm = numbers[0];
        // int sum =0;
        // int avg = 0;

        // for(int i=0;i<5;i++){
        //     if(numbers[i]>maxm){
        //         maxm=numbers[i];
        //     }
        //     if(numbers[i]<minm){
        //         minm=numbers[i];
        //     }
        //     sum+=numbers[i];
        //     avg=sum/5;
        // }

        // System.out.println("Maximum: " + maxm);
        // System.out.println("Minimum: " + minm);
        // System.out.println("Sum: " + sum);
        // System.out.println("Average: " + avg);

//         Challenge 1
// Print a right-angle star triangle pattern

        // int n= sc.nextInt();

    //     for(int i=0;i<5;i++){
    //         for(int j=0;j<=i;j++){
    //             System.out.print("* ");
    //         }
    //         System.out.println("\n");
    //     }


    // }


       
//  Challenge 3
// Print all prime numbers between 1 and 50. 
// Count how many there are and display the 
// count at the end.


        // int count=0;
        
        // for(int i=2;i<=50;i++){
        //     boolean isPrime=true;

        //     for(int j=2;j<i;j++){
        //         if(i%j==0){
        //             isPrime=false;
        //             break;
        //         }

        //     }
        //     if(isPrime){
        //         System.out.println(i + " ");
        //         count++;
        //     }
        // }

        // System.out.println("Count of the number : " + count);

        // Predict the output of each program without running it. Show your working

        int x=5;
        System.out.println(x++  + " " + ++x);

        for(int i=10;i>0;i-=3){
            System.out.print(i + "\n");
        }
    }
}