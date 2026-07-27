class parent {


    public void getName(){
        System.out.println("This is parent class");
    }
    
}

class child extends parent {
    public void getName(){
        System.out.println("This is child class");
    }
    
}


public class methodoverriding {

    public static void main(String[] args) {
        
        child c= new child();
        c.getName();

    }
}