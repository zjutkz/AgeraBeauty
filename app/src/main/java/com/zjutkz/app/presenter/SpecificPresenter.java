package com.zjutkz.app.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;
import com.kennyc.bottomsheet.BottomSheet;
import com.kennyc.bottomsheet.BottomSheetListener;
import com.zjutkz.app.R;
import com.zjutkz.app.utils.AppUtils;
import com.zjutkz.app.utils.PhotoProcessor;
import com.zjutkz.app.view.callback.SpecificView;

/**
 * Created by kangzhe on 16/9/10.
 */
public class SpecificPresenter extends MvpBasePresenter<SpecificView> implements View.OnLongClickListener,BottomSheetListener{

    private String data;

    private Context context;

    public SpecificPresenter(String data, Context context){
        this.data = data;
        this.context = context;
    }

    @Override
    public boolean onLongClick(View v) {
        AppUtils.showBottomSheet(context,this);
        return true;
    }

    @Override
    public void onSheetShown(@NonNull BottomSheet bottomSheet) {
        
    }

    @Override
    public void onSheetItemSelected(@NonNull BottomSheet bottomSheet, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.share:
                PhotoProcessor.getInstance().sharePic(data);
                break;
            case R.id.save:
                try {
                    PhotoProcessor.getInstance().savePic(data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.wall_paper:
                PhotoProcessor.getInstance().setWallPaper(data);
                break;
        }
    }

    @Override
    public void onSheetDismissed(@NonNull BottomSheet bottomSheet, @DismissEvent int i) {

    }
}
