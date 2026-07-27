import java.util.Scanner;

public class madlibsgame {
    public static void main(String[] args) {
        
        Scanner sc=new Scanner(System.in);


        String adj1;
        String noun1;
        String adj2;
        String verb;
        String adj3;

        System.out.println("Enter adjective first");
        adj1 = sc.nextLine();
        System.out.println("Enter noun ");
        noun1 = sc.nextLine();
        System.out.println("Enter adjective second");
        adj2 = sc.nextLine();
        System.out.println("Enter verb");
        verb = sc.nextLine();
        System.out.println("Enter adjective third");
        adj3 = sc.nextLine();



        System.out.println("\nOnce upon a time, there was a very " + adj1 + " dragon who lived near a " + noun1 +" . \n" + 
                        "Every morning, the dragon felt " + adj2 + " and decided to " + verb + " around the village.\n" + 
                        "The villagers were surprised, but they soon realized the dragon was actually very " + adj3 + ".\n" + 
                        "From that day on, everyone became good friends and lived happily ever after.");
    }
}
