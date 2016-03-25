package com.lvdiscordian.nsquare;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by matt on 3/23/16.
 */
public class AStar {
    PriorityQueue<Node> nodes;
    State goal;
    HashSet<State> previousStates;
//    static String[] opMap = {"up", "down", "left", "right"};

    public AStar(State start) {
        Node startNode = new Node(start);
        nodes = new PriorityQueue<>();
        nodes.add(startNode);
        goal = new State(start.getSize());
        previousStates = new HashSet<>();
        previousStates.add(start);
    }

    /**
     * Allows us to step through the searching process
     * @return will return false unless the answer is on the top of the queue.
     */
    public boolean step(){
        boolean[] validOps;
        Node temp;
        Node top = nodes.peek();

        if(top.getCurrentState().equals(goal)){
            return true;
        }

        top = nodes.poll();
        validOps = top.getCurrentState().getValidOperations();
        for(int i = 0; i <validOps.length; i++){
            if(validOps[i]){
                temp = new Node(top, i);
                if(!previousStates.contains(temp.getCurrentState())) {
                    previousStates.add(temp.getCurrentState());
                    nodes.add(temp);
                }
            }
        }

        return false;
    }

    public PriorityQueue<Node> getNodes() {
        return nodes;
    }

    public State getGoal() {
        return goal;
    }

    public HashSet<State> getPreviousStates() {
        return previousStates;
    }
}
