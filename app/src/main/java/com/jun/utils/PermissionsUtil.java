package com.jun.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class PermissionsUtil {

    private static PermissionsUtil defaultInstance;
    private RxPermissions rxPermissions;
    private Context context;

    private PermissionsUtil(){}


    public static PermissionsUtil getInstance(){
        if(defaultInstance == null){
            synchronized (PermissionsUtil.class){
                if(defaultInstance == null){
                    defaultInstance = new PermissionsUtil();
                }
            }
        }
        return defaultInstance;
    }

    /**
     * 必须调用
     * 设置上下文环境
     * 实例化RxPermissions
     * @param activity
     * @return
     */
    public PermissionsUtil setContext(FragmentActivity activity) {
        context = activity;
        rxPermissions = new RxPermissions(activity);
        return this;
    }

    /**
     * 统一处授权
     */
    public void setRxPermissions(){
        rxPermissions.request(Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE)
                .subscribe(granted->{
                    if(granted){

                    }else{
                        Toast.makeText(context,"禁止权限相关功能不可用",Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * 是否有相机权限
     * @return
     */
    public boolean hasCameraPermission(){
        int hasCameraPermission = ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA);
        if(hasCameraPermission == PackageManager.PERMISSION_GRANTED){
           return true;
        }
        return false;
    }

    /**
     * 处理相机权限
     * @param listener
     */
    public void handleCameraPermissions(onPermissionListener listener ){
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(granted->{
                    if(granted){
                        if(listener != null){
                            listener.agreeCameraPermission();
                        }
                    }else{
                        Toast.makeText(context,"相机不可用",Toast.LENGTH_LONG).show();
                    }
                });
    }


    /**
     * 权限监听接口
     */
    public interface onPermissionListener{
        void agreeCameraPermission();
    }


}



