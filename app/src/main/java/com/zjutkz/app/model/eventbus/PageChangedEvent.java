package com.zjutkz.app.model.eventbus;

/**
 * Created by kangzhe on 16/9/10.
 */
public class PageChangedEvent {

    public int currentPosition = 0;

    public PageChangedEvent(int currentPosition){
        this.currentPosition = currentPosition;
    }
}
