package com.zjutkz.app.model.eventbus;

import com.zjutkz.app.model.Beauty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class LoadEvent implements Serializable{

    public int loadType;
    public List<Beauty.BeautyEntity> beauties;

    public LoadEvent(int loadType, List<Beauty.BeautyEntity> beauties){
        this.loadType = loadType;
        this.beauties = beauties;
    }
}
