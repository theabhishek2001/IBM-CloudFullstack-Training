package java03;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

class Stackx{
    Stackx(){
        Stack<Integer> stack = new Stack<>();
        stack.push(23);
        stack.push(34);
        stack.push(56);

        stack.pop();

        System.out.println(stack.size());
        System.out.println(stack.get(0));
        stack.clear();

        System.out.println(stack);
    }
}

class Que{
    Que(){
        Queue<Integer> q =new ArrayDeque();

        q.offer(5);
        q.offer(69);
        q.offer(96);

        q.poll();

        System.out.println(q.size());
        System.out.println(q.peek());
        System.out.println(q.contains(34));
    }
}

public class ds {
    public static void main(String[] args) {
        // Stackx s= new Stackx();
        Que q= new Que();


    }
}
