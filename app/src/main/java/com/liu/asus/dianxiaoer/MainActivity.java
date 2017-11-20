package com.liu.asus.dianxiaoer;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import MyInterface.QuestInterface;
import base.BaseActivity;
import base.Basepresent;
import bean.ZhuCeBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import utils.MyQusetUtils;
import utils.SPUtils;

public class MainActivity extends BaseActivity {


    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.ed_login_tel)
    EditText edLoginTel;
    @BindView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.imageVie)
    ImageView imageVie;
    @BindView(R.id.ed_login_yz)
    EditText edLoginYz;
    @BindView(R.id.iv_login_yzm)
    ImageView ivLoginYzm;
    @BindView(R.id.relativeLayou1)
    RelativeLayout relativeLayou1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.but_log)
    Button butLog;
    @BindView(R.id.tv_login_qtdlfs)
    TextView tvLoginQtdlfs;
    @BindView(R.id.iv_login_wx)
    ImageView ivLoginWx;
    @BindView(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @BindView(R.id.relativeLayout3)
    LinearLayout relativeLayout3;
    @BindView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @BindView(R.id.iv_hidepwd)
    ImageView ivHidepwd;
    @BindView(R.id.tv_djs)
    TextView tvDjs;
    private EventHandler eventHandler;
    private Boolean dlfs;
    private Boolean djs;
    private Handler handler=new Handler();
    private   Runnable task;
    private  int tvdjs=30;
    @Override
    public List<Basepresent> initp() {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int getid() {
        initztl("#f00fff");
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        dlfs = (Boolean) SPUtils.get(this, "dlfs", false);
        if (dlfs == true) {
            edLoginYz.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivHidepwd.setVisibility(View.GONE);
            imageVie.setImageResource(R.drawable.yzm);
            ivLoginYzm.setVisibility(View.VISIBLE);
            tvDjs.setVisibility(View.GONE);
            SPUtils.put(MainActivity.this, "dlfs", false);
            edLoginYz.setHint("请输入短信验证码");
        }
        SPUtils.put(this, "dlfs", this.dlfs);
        ZhuCeBean zhuCeBean = new ZhuCeBean();
        ZhuCeBean initbean = initbean(zhuCeBean);
        Gson gson = new Gson();
        String json = gson.toJson(initbean);
        MyQusetUtils build = new MyQusetUtils.Builder().addCallAdapterFactory().addConverterFactory()
                .build();
        QuestInterface myQusetUtils = build.getMyQusetUtils();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Observable<ResponseBody> regn = myQusetUtils.regn(body);
        regn.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("=====d");
            }

            @Override
            public void onNext(ResponseBody value) {
                try {
                    System.out.println("====" + value.string());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("=====e" + e.toString());
            }

            @Override
            public void onComplete() {
                System.out.println("=====complete");
            }
        });

        ivHidepwd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //不可见
                    ivHidepwd.setImageResource(R.drawable.hidepwd);
                    edLoginYz.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                } else {
                    //可见
                    ivHidepwd.setImageResource(R.drawable.showpwd);
                    edLoginYz.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                return true;
            }
        });



        /*subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(ResponseBody requestBody) throws Exception {
                System.out.println("===="+requestBody.string());
            }
        });*/

        //短信
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码


        // 创建EventHandler对象
        // 处理你自己的逻辑
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    final String msg = throwable.getMessage();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast("msg" + msg);
                        }
                    });
                } else {

                    if (result == SMSSDK.RESULT_COMPLETE) {
                        //回调完成
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                            //提交验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast(" 登录成功");
                                }
                            });

                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            //获取验证码成功
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvDjs.setText(30+"s");
                                    ivLoginYzm.setVisibility(View.GONE);
                                    tvDjs.setVisibility(View.VISIBLE);
                                    handler.postDelayed(task,1000);
                                    Toast(" 获取验证码成功");
                                }
                            });

                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    } else {
                        ((Throwable) data).printStackTrace();
                    }


                }
            }


            // 注册监听器
        };
        SMSSDK.registerEventHandler(eventHandler);
        task=new Runnable() {
            @Override
            public void run() {
                tvdjs--;
                if(tvdjs==0){
                    tvdjs=30;
                    tvDjs.setText(30+"s");
                    handler.removeCallbacks(this);
                    ivLoginYzm.setVisibility(View.VISIBLE);
                    tvDjs.setVisibility(View.GONE);
                    SPUtils.put(MainActivity.this, "djs", false);
                }else {
                    tvDjs.setText(tvdjs+"s");
                    handler.postDelayed(this,1000);
                }
            }
        };
    }

    public ZhuCeBean initbean(ZhuCeBean zhuCeBean) {
        zhuCeBean.setH2y_app_id("15176046561");
        ZhuCeBean.PdBean pdBean = new ZhuCeBean.PdBean();
        pdBean.setAccount("15176046561");
        pdBean.setH2y_app_id("15176046561");
        pdBean.setPassword("666666");
        pdBean.setRef_one_id(0);
        pdBean.setSms_code("666666");
        zhuCeBean.setPd(pdBean);
        zhuCeBean.setToken("jinlin");

        return zhuCeBean;

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @OnClick({R.id.but_log, R.id.iv_login_yzm, R.id.tv_login_qtdlfs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.but_log:
                if (TextUtils.isEmpty(edLoginTel.getText().toString()) || TextUtils.isEmpty(edLoginYz.getText().toString())) {
                    Toast("手机号或验证码不能为空");
                    return;
                }
                SMSSDK.submitVerificationCode("86", edLoginTel.getText().toString(), edLoginYz.getText().toString());
                break;
            case R.id.iv_login_yzm:
                if (TextUtils.isEmpty(edLoginTel.getText().toString())) {
                    Toast("手机号不能为空");
                    return;
                }

                SPUtils.put(MainActivity.this, "djs", true);
                SMSSDK.getVerificationCode("86", edLoginTel.getText().toString());
                break;
            case R.id.tv_login_qtdlfs:
                dlfs = (Boolean) SPUtils.get(this, "dlfs", false);
                djs = (Boolean) SPUtils.get(this, "djs", false);
                System.out.println("dlfs==" + dlfs);
                if (dlfs == true) {
                    edLoginYz.setHint("请输入短信验证码");
                    ivHidepwd.setVisibility(View.GONE);
                    if(djs==true){
                        tvDjs.setVisibility(View.VISIBLE);
                        ivLoginYzm.setVisibility(View.GONE);

                    }else {
                        ivLoginYzm.setVisibility(View.VISIBLE);
                        tvDjs.setVisibility(View.GONE);
                    }
                    imageVie.setImageResource(R.drawable.yzm);
                    SPUtils.put(MainActivity.this, "dlfs", false);
                    edLoginYz.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    tvDjs.setVisibility(View.GONE);
                    ivHidepwd.setVisibility(View.VISIBLE);
                    edLoginYz.setHint("请输入密码");
                    ivLoginYzm.setVisibility(View.GONE);
                    imageVie.setImageResource(R.drawable.pwd);
                    edLoginYz.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    SPUtils.put(MainActivity.this, "dlfs", true);
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



}
