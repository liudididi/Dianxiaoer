package com.liu.asus.dianxiaoer;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.List;

import base.BaseActivity;
import base.Basepresent;
import utils.SPUtils;

public class StartActivity extends BaseActivity {



    @Override
    public List<Basepresent> initp() {
        return null;
    }

    @Override
    public int getid() {
        return R.layout.activity_start;
    }

    @Override
    public void init() {
        Boolean logfist = (Boolean) SPUtils.get(this, "logfirst", false);
        if(logfist==true){
            intent(StartActivity.this,MainActivity.class);
        }
       ViewPager vp =findViewById(R.id.vp);
       vp.setAdapter(new Myvpadapter());

    }

    class  Myvpadapter extends PagerAdapter{
        private  int[] imgid={R.mipmap.pageyi,R.mipmap.pageer,R.mipmap.pagesan,};
        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(StartActivity.this, R.layout.vpitem, null);
            ImageView img_dongtu= view.findViewById(R.id.img_dongtu);
            ImageView img_jiesuo= view.findViewById(R.id.img_jiesuo);
            ImageView img_tu = view.findViewById(R.id.img_tu);
            Glide.with(StartActivity.this).load(R.mipmap.spdt).into(
                    new GlideDrawableImageViewTarget(img_dongtu,1));
            if(position==3){
                img_tu.setVisibility(View.GONE);
                img_dongtu.setVisibility(View.VISIBLE);
                img_jiesuo.setVisibility(View.VISIBLE);
                img_jiesuo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent(StartActivity.this,MainActivity.class);
                        SPUtils.put(StartActivity.this,"logfirst",true);
                    }
                });
            }else {
                img_dongtu.setVisibility(View.GONE);
                img_jiesuo.setVisibility(View.GONE);
                img_tu.setImageResource(imgid[position]);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
