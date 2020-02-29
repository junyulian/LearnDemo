package com.jun.learndemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jun.bean.EventMessage;
import com.jun.bean.PersonInfo;
import com.jun.utils.PermissionsUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.tv_hello)TextView tv_hello;
    @BindString(R.string.home_text) String home_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Log.e(TAG,"onCreate开始执行");
        tv_hello.setText(home_text);

        PermissionsUtil.getInstance().setContext(this).setRxPermissions();
    }


    @OnClick({R.id.btn_toast_info,R.id.btn_2_second})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_toast_info:
                Toast.makeText(getApplicationContext(),"HAHA",Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_2_second:
                Log.e(TAG,"开始点击");
                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(intent);
                Map map = new HashMap();

                map.put("name","lsq");
                map.put("age",21);

                PersonInfo personInfo = new PersonInfo(27,"swm");
                map.put("person",personInfo);

                PersonInfo personInfo2 = new PersonInfo(29,"zyx");
                List<PersonInfo> list = new ArrayList<PersonInfo>();
                list.add(personInfo);
                list.add(personInfo2);
                map.put("personList",list);

                EventBus.getDefault().postSticky(new EventMessage(1,map));
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true,priority = 1)
    public void onEventMessage(EventMessage msg){
        Log.e(TAG,"获取到消息");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"onStart开始执行");
        EventBus.getDefault().register(this);
    }


    /**
     * EventBus放在OnDestroy注销，APP进程进入后台，再进入前台，会执行onStart,则有两次注册错误
     */
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"onDestroy开始执行");

    }
}
