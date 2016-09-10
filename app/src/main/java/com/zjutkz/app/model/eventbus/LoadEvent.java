package com.zjutkz.app.model.eventbus;

import com.zjutkz.app.model.Beauty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class LoadEvent implements Serializable{

    public List<Beauty.BeautyEntity> beauties;

    public LoadEvent(List<Beauty.BeautyEntity> beauties){
        this.beauties = beauties;
    }
}
