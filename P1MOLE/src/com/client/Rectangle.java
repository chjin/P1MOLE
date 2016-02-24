package com.client;

/**
 * Created by cjw on 2016-02-11.
 */
public class Rectangle {
    private int left,right,top,bottom;
    private int width,height;

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getWidth(){
        return right-left;
    }

    public int getHeight(){
        return bottom-top;
    }

    public void setRect(int left,int top,int right,int bottom){
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
    }

    public boolean positionInRect(int x,int y){
        boolean bCheck=false;
        if((x>=left && x<=right) && (y>=top && y<=bottom))
            bCheck=true;
        return bCheck;
    }

}