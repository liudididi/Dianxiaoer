package utils;

import android.app.ProgressDialog;
import android.content.Context;


public class DialogUtils {
    private DialogUtils(){

    }
    private ProgressDialog progressDialog;
    private static class DialogUtilsHolder{
        static utils.DialogUtils DialogUtils = new DialogUtils();
    }
    public static DialogUtils getInstance(){
        return  DialogUtilsHolder.DialogUtils;
    }

    public void show(Context context, String msg){

        close();

        createProgressDialog(context,msg);

        if (progressDialog!=null&&!progressDialog.isShowing()){
            progressDialog.show();
        }



    }

    private void createProgressDialog(Context context,String msg) {

        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);

    }

    public void close(){

        if (progressDialog!=null&&progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }
}
