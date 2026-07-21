package java04;


class Task  extends Thread  {

    private String task;

    Task(String task){
        this.task = task;
    }

    @Override
    public void run(){
        System.out.println(task + " is being executed: " + Thread.currentThread().getName());
    }
    
}

public class multithreading {
    public static void main(String[] args) {
        Thread th1= new Task("Learning CloudFullstack");
        th1.start();

        Thread th2= new Task("Learning AWS");
        th2.start();
    }
}
