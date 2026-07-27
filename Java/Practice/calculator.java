import java.util.Scanner;

public class calculator {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        int number1;
        int number2;

        char operator;
        double result;

        System.out.println("Enter the first number :");
        number1=sc.nextInt();

        System.out.println("Enter operator (+, - , * , /) :");
        operator=sc.next().charAt(0);


        System.out.println("Enter the second number :");
        number2= sc.nextInt();


        switch (operator) {
            case '+':
                result=number1+number2;
                System.out.println(" Addition  is : " + result);
                break;
            
            case '-':
                result=number1-number2;
                System.out.println("Subtraction  is : " + result);
                break;

            case '*':
                result=number1*number2;
                System.out.println("Multiplication  is : " + result);
                break;

            case '/':
                if(number2==0){
                    System.out.println("Cannot divide by zero");
                    break;
                }
                result=number1/number2;
                System.out.println("Division  is : " + result);
                break;
        
            default:
                break;
        }





    }
}
