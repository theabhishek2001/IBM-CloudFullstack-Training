package java04;


import java.util.ArrayList;
import java.util.List;

class User {
    private final String name;
    private final int age;

    public User(String name, int age) {
        if (age < 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.age = age;
    }

    public String name() {
        return name;
    }

    public int age() {
        return age;
    }
}

public class java14 {
    public static void main(String[] args) {
        User u = new User("Aaryan", 20);
        // System.out.println(u.name());
        // System.out.println(u.age());
        // int choice = 1;
        // switch (choice) {
        //     case 1:
        //         System.out.println("you entered 1");
        //         break;
        
        //     default:
        //         break;
        // }
        String day = "Sunday";
        boolean isWeekend;
        switch (day) {
            case "Monday":
            case "Tuesday":
            case "Wednesday":
            case "Thursday":
            case "Friday":
                isWeekend = false;
                break;
            case "Saturday":
            case "Sunday":
                isWeekend = true;
                break;
            default:
                throw new IllegalArgumentException("Not a Day of the Week");
        }
        // System.out.println(isWeekend);

        // String multiLine = "Hello my name is Aaryan\nAnd i am a corporate trainer";
        String mLine = "Aaryan is a corporate trainer\n"
                + "And \"he\" is currently delivering batch \n"
                + "in IBM. % \n";
        System.out.println(mLine);

        // if(mLine instanceof String str) {
        //     // System.out.println(str.length());
        // }

        // var name = "Aaryan";
        // name = "Rohan";
        // // System.out.println(name);
        // var count = 3.14;
        // // System.out.println(count);

        // var list = new ArrayList<Integer>();
        // list.add(34);
        // // System.out.println(list);
        // for (var i : list) {
        //     System.out.println(i);
        // }



    }
    
}