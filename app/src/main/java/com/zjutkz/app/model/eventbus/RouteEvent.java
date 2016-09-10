package com.zjutkz.app.model.eventbus;

import java.io.Serializable;

/**
 * Created by kangzhe on 16/9/9.
 */
public class RouteEvent implements Serializable{

    public String protocol;
    public Object bundle;

    public RouteEvent(String protocol,Object bundle){
        this.protocol = protocol;
        this.bundle = bundle;
    }
}
