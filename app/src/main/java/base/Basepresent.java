package base;

/**
 * Created by 地地 on 2017/11/12.
 * 邮箱：461211527@qq.com.
 */

public class Basepresent<V> {
    private  V Viewmodel;
    public Basepresent(V viewmodel) {
        Viewmodel = viewmodel;
    }
    public  void  ondeach(){
      Viewmodel=null;
    }
}
