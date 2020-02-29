package com.jun.learndemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

public class GlideActivity extends AppCompatActivity {


    @BindView(R.id.iv_test1)ImageView iv_test1;
    @BindView(R.id.iv_test2)ImageView iv_test2;
    @BindView(R.id.iv_test3)ImageView iv_test3;


    private static final String uri = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1576400299068&di=87f32e05586e1b6410db0ef71cb25d02&imgtype=0&src=http%3A%2F%2Fp2.ssl.cdn.btime.com%2Ft01fe033300f22c00fb.jpg%3Fsize%3D690x388%3Fsize%3D690x388";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_test1,R.id.btn_test2})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_test1:
                loadImg1();
                break;
            case R.id.btn_test2:
                loadImg2();
                break;
        }
    }

    private void loadImg1(){

        Glide.with(this)
                .load(uri)
                .override(300, 300)
                .into(iv_test1);
    }

    private void loadImg2(){

        RequestOptions options = new RequestOptions()//圆形图片
                .circleCrop();

        Glide.with(this)
                .load(uri)
                .apply(options)
                .into(iv_test2);

        RequestOptions options2 = new RequestOptions()//圆形图片
                .transform(new BlurTransformation(),new GrayscaleTransformation())//模糊化处理，黑白处理
                .circleCrop();
        Glide.with(this)
                .load(uri)
                .apply(options2)
                .into(iv_test3);
    }
}
