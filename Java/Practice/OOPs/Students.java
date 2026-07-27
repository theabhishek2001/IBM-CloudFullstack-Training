package OOPs;

public class Students {
    String name;
    int age;
    double gpa;

    Students(String name,int age,double gpa){
        this.name=name;
        this.age=age;
        this.gpa=gpa;
    }

    void study(){
        System.out.println(this.name + " is studying");
    }
}
