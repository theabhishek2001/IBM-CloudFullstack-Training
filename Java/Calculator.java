public class Calculator {


        int x;
        int y;
        
        private Calculator() {
        }

        public int diff(){
            return x-y;
        }

        private static Calculator obj= new Calculator();


        public static Calculator getInstance(){
            return obj;
        }
    
    
}
