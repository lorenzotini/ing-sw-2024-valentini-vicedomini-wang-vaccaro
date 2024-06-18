package it.polimi.ingsw.gc27.View.GUI;

public class Point {
    int x;
    int y;
    int count;
    public Point(int x, int y){
        this.x = x;
        this.y = y;
        this.count = 0;
    }
    public int getx(){
        return this.x;
    }
    public int gety(){
        return this.y;
    }
    public void incrementCount(){
        this.count++;
    }
    public void decreaseCount(){
        this.count--;
    }
    public int getCount(){
        return this.count;
    }
}
