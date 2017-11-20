package present;

import base.Basepresent;
import Moudle.LognModle;
import MyInterface.Lognview;

/**
 * Created by 地地 on 2017/11/12.
 * 邮箱：461211527@qq.com.
 */

public class   LognP  extends Basepresent {
    private LognModle lognModle;
    public LognP(Lognview viewmodel) {
        super(viewmodel);
        if(lognModle==null){
            lognModle=new LognModle();
        }
    }

}
