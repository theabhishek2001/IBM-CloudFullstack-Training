package java03;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Date {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Enter date (dd/MM/yyyy): ");
        String date = sc.nextLine();

        LocalDate dt = LocalDate.parse(date,formatter);

        LocalDate futureDate = dt.plusDays(30);
        String fdate = futureDate.format(formatter);

        System.out.println("30 days later: " + futureDate);


        DateTimeFormatter nformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate  newdate = LocalDate.parse(fdate,nformatter);

        System.out.println("30 days later: " + newdate);


       
    }
}