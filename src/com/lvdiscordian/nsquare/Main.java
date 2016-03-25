package com.lvdiscordian.nsquare;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        State test = new State(4);
//        System.out.println(test);
        test = Main.randomize(test, 50);

        System.out.println(test);

        AStar search = new AStar(test);

        while(!search.step()){
            System.out.println("Looking at State: " + search.getNodes().peek().getCurrentState() +" next.");
        }

        System.out.println("Found solution: " + search.getNodes().peek().getPathFromStart() );

    }

    private static State randomize(State state, int steps){
        State randState = state;
        Random rand = new Random();
        for(int i = 0; i < steps; i++){
            switch ( rand.nextInt(4) ){
                case 0: //Up case
                    randState = randState.up();
                    break;
                case 1: //right case
                    randState = randState.right();
                    break;
                case 2:
                    randState = randState.down();
                    break;
                case 3:
                    randState = randState.left();
                    break;
            }
        }
        return randState;
    }

}
