package com.lvdiscordian.nsquare;

import java.util.ArrayList;

/**
 * Created by matt on 3/22/16.
 */
public class Node implements Comparable<Node>{
    private State currentState;
    private ArrayList<Integer> pathFromStart;

    public Node(State start){
        this.currentState = start;
        this.pathFromStart = new ArrayList<>();
    }

    public Node(Node parent, Integer op){
        this.currentState = parent.getCurrentState().operation(op);
        this.pathFromStart = new ArrayList<>(parent.getPathFromStart());
        this.pathFromStart.add(op);
    }

    public ArrayList<Integer> getPathFromStart() {
        return pathFromStart;
    }

    public State getCurrentState() {
        return currentState;
    }

    /**
     * Basically compare f(n) = g(n) + h(n). g(n) is the pathFromStart.size() and h(n) is the currentState.heuristic().
     *
     * @param rhs The right hand side that is to be compared
     * @return
     */
    @Override
    public int compareTo(Node rhs) {
        return (this.pathFromStart.size() + this.currentState.heuristic()) -
                (rhs.pathFromStart.size() + rhs.currentState.heuristic());
    }

}
