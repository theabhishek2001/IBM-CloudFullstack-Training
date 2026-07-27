package OOPs;

public class car {
    String model;
    int topspeed= 250;
    int year=2000;
    int price;
    String engineType;

    car(String model,int year,String engineType){
        this.model=model;
        this.year=year;
        // this.engineType=new Engine(engineType);
    }



    // car(String model, int price){
    //     this.model=model;
    //     this.price=price;
    // }



    // void start(){
    //     System.out.println("You start the Engine");
    // }

    // void stop(){
    //     System.out.println("You stop the engine");
    // }

    // String getmodel(){
    //     return this.model;
    // }

    // int getprice(){
    //     return this.price;
    // }

    // void setmodel(String model){
    //     this.model=model;
    //     // this.price=price;
    // }

    // void setprice(int price){
    //     // this.model=model;
    //     this.price=price;
    // }
}
