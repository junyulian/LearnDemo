package com.jun.learndemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jun.bean.EventMessage;
import com.jun.bean.PersonInfo;
import com.jun.utils.CameraUtil;
import com.jun.utils.PermissionsUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecondActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.iv_pic)
    ImageView iv_pic;

    private CameraUtil cameraUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
    }

    /**
     * 首先检查相机权限
     * 没有相机权限申请相机权限
     */
    private void handleOpenCamera(){
        PermissionsUtil util = PermissionsUtil.getInstance().setContext(SecondActivity.this);
        if(util.hasCameraPermission()){
            openCamera();
        }else {
            util.handleCameraPermissions(new PermissionsUtil.onPermissionListener() {
                @Override
                public void agreeCameraPermission() {
                    openCamera();
                }
            });
        }
    }



    @OnClick({R.id.btn_camera,R.id.btn_phone,R.id.btn_2_glide})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_camera:
                handleOpenCamera();
                break;

            case R.id.btn_phone:
                callPhone();
                break;
            case R.id.btn_2_glide:
                Intent intent = new Intent(this,GlideActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void openCamera(){
        cameraUtil = new CameraUtil(this);
        cameraUtil.openCamera();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraUtil.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                cameraUtil.setImgShow(iv_pic);
            } else {
                Toast.makeText(this,"取消",Toast.LENGTH_LONG).show();
            }
        }
    }



    public void callPhone(){

    }


    @Subscribe(sticky = true)
    public void onEventMessage(EventMessage msg){
        Log.e(TAG,"获取到消息");
        if(msg.getType() == 1){

            String name = msg.getString("name");
            int age = msg.getInt("age");

            String perName = msg.getObject("person", PersonInfo.class).getName();
            String perName2 = msg.getList("personList",PersonInfo.class).get(1).getName();

            Log.e(TAG,"name="+name+"-age:"+age+"-perName:"+perName+"-perName2:"+perName2);

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
