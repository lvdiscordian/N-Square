package com.lvdiscordian.nsquare;

import java.util.ArrayList;

/**
 * The N-Square puzzle state object.
 */
public class State {
    private int[] tiles;
    private int size;
    private int cursor;
    private int h = -1;

    /**
     * Basic constructor that sets the puzzle in the finish state.
     * @param size The size of one dimension of the square.
     */
    public State(int size){
        this.size = size;
        this.tiles = new int[size * size];

        for(int i = 0; i < this.tiles.length ; i++) {
            this.tiles[i] = i;
        }
        this.cursor = size * size - 1; //The cursor will always be last.
    }

    /**
     * Accept preset tiles assumes appropriate a squared length.
     * @param tiles The starting position of the tiles.
     * @param cursorIndex The position of the cursor (the cell that has the value of the size^2).
     */
    public State(int[] tiles, int cursorIndex){
        this.size = (int) Math.sqrt(tiles.length);
        this.tiles = tiles;
        this.cursor = cursorIndex;
    }

    public int getSize() {
        return size;
    }

    @Override
    public boolean equals(Object obj) {
        State rhs;
        if(obj instanceof State){
            rhs = (State) obj;
            if(this.tiles != null && rhs.tiles != null &&
                    this.tiles.length == rhs.tiles.length){
                for(int i = 0; i < this.tiles.length; i++){
                    if(this.tiles[i] != rhs.tiles[i]){
                        return false;
                    }
                }
                return true;
            } else if(this.tiles == null && rhs.tiles == null){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        ArrayList<String> state = new ArrayList<>(this.tiles.length);
        for (int i : this.tiles) {
            state.add(Integer.toString(i));
        }
        return String.join(",", state);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    private int getCursorX(){
        return this.cursor % this.size;
    }

    private int getCursorY(){
        return this.cursor / this.size;
    }

    private int getIndex(int x, int y){
        return y * this.size + x;
    }

    public boolean[] getValidOperations(){
        boolean[] ops = {false, false, false, false}; //up, down, left, right

        int newLocation;
        int currentX = this.getCursorX(), currentY = this.getCursorY();

        newLocation = currentY - 1;
        if( newLocation >= 0 ){
            ops[0] = true;
        }

        newLocation = currentY + 1;
        if( newLocation < this.size ){
            ops[1] = true;
        }

        newLocation = currentX - 1;
        if( newLocation >= 0 ){
            ops[2] = true;
        }

        newLocation = currentX + 1;
        if( newLocation < this.size ){
            ops[3] = true;
        }

        return ops;
    }

    public int heuristic(){
        /**
         * Use manhattan distance as the heuristic
         */

        if(h < 0) {
            h = 0;
            int currentX, currentY, bestX, bestY, val;
            for (int index = 0; index < tiles.length; index++) {
                val = tiles[index];
                currentX = index % size;
                currentY = index / size;
                bestX = (val - 1) % size;
                bestY = (val - 1) / size;

                h += (Math.abs(currentX - bestX) + Math.abs(currentY - bestY));
            }
        }
        return h;
    }
    /*
     *  Opertations - These are the operations that can move our empty space. up, down, left, and right
     */

    private int[] swap(int newIndex){
        int[] locCopy = this.tiles.clone();
        int x = locCopy[this.cursor];
        locCopy[this.cursor] = locCopy[newIndex];
        locCopy[newIndex] = x;
        return locCopy;
    }

    public State operation(int op){
        int newLocation;
        int newIndex = -1;
        int currentX = this.getCursorX(), currentY = this.getCursorY();

        switch (op){
            case 0:
                newLocation = currentY - 1;
                if( newLocation >= 0 ){
                    newIndex = getIndex(currentX, newLocation);
                }
                break;
            case 1:
                newLocation = currentY + 1;
                if(newLocation < this.size){
                    newIndex = getIndex(currentX, newLocation);
                }
                break;
            case 2:
                newLocation = currentX - 1;
                if(newLocation >= 0){
                    newIndex = getIndex(newLocation, currentY);
                }
                break;
            case 3:
                newLocation = currentX + 1;
                if( newLocation < this.size){
                    newIndex = getIndex(newLocation, currentY);
                }
                break;
            default:
                //Invalid op
                break;
        }
        if(0 <= newIndex){
            return new State(this.swap(newIndex), newIndex);
        } else {
            return this;
        }
    }

    public State operation(String op){
        int opVal = 0;

        switch (op){
            case "up":
                break;
            case "down":
                opVal = 1;
                break;
            case "left":
                opVal = 2;
                break;
            case "right":
                opVal = 3;
                break;
            default:
                opVal = -1;
                break;
        }
        return operation(opVal);
    }

    public State up(){
        /**
         * Will move the cursor up.  If it is unable to move up then it will return itself.
         */
        return this.operation("up");
    }

    public State down(){
        /**
         * Will move the cursor down.  If it is unable to move up then it will return itself.
         */
        return this.operation("down");
    }

    public State left(){
        /**
         * Will move the cursor left.  If it is unable to move up then it will return itself.
         */
        return this.operation("left");
    }

    public State right(){
        /**
         * Will move the cursor right.  If it is unable to move up then it will return itself.
         */
        return this.operation("right");
    }

}
