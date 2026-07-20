package java03;


enum Day{
    Monday,
    Tuesday,
    Wednesday
}

public class e {
    public static void printDay(Day day){

        switch (day) {
            case Monday:
                System.out.println("Öption 1");
                
                break;

            case Tuesday:
                System.out.println("Öption 2");
                
                break;
            
                case Wednesday:
                System.out.println("Öption 3");
                
                break;
        
            default:
                System.out.println("Ïnvalid choice");
                break;
        }

    }
}
