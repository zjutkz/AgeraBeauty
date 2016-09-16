package com.zjutkz.app.model.eventbus;

import com.zjutkz.app.model.Beauty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kangzhe on 16/9/9.
 */
public class LoadEvent implements Serializable{

    public boolean isSuccess;
    public int reqType;
    public List<Beauty.BeautyEntity> beauties;

    public LoadEvent(boolean isSuccess,int reqType,List<Beauty.BeautyEntity> beauties){
        this.isSuccess = isSuccess;
        this.reqType = reqType;
        this.beauties = beauties;
    }
}
