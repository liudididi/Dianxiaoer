package utils;

import java.util.concurrent.TimeUnit;


import MyInterface.QuestInterface;
import base.BaseApi;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 地地 on 2017/11/12.
 * 邮箱：461211527@qq.com.
 */

public class MyQusetUtils {
  public static final int TIMEOUT=1000*60;
  public static MyQusetUtils myQusetUtils;
  private QuestInterface questInterface;

  public MyQusetUtils(QuestInterface questInterface) {
    this.questInterface = questInterface;
  }

  public  QuestInterface getMyQusetUtils() {
    return questInterface;
  }

  public static class  Builder{
    OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .addInterceptor(new MyInterceptor())
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false).build();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BaseApi.Api).client(okHttpClient);
    public  Builder addConverterFactory(){
      builder.addConverterFactory(GsonConverterFactory.create());
      return   this;
    }
    public  Builder addCallAdapterFactory(){
      builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
      return  this;
    }
    public MyQusetUtils build(){
      QuestInterface questInterface=builder.build().create(QuestInterface.class);
      return   myQusetUtils=new MyQusetUtils(questInterface);
    }

  }

}
