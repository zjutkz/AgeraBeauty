package com.zjutkz.app.model.eventbus;

import java.io.Serializable;

/**
 * Created by kangzhe on 16/9/9.
 */
public class RouteEvent implements Serializable{

    public String protocol;

    public RouteEvent(String protocol){
        this.protocol = protocol;
    }
}
