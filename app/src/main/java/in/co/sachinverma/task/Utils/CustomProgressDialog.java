package in.co.sachinverma.task.Utils;

import android.app.ProgressDialog;
import android.content.Context;

import in.co.sachinverma.task.R;

public class CustomProgressDialog extends ProgressDialog {

    private static CustomProgressDialog customProgressDialog = null;
    private static Context mContext = null;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public static CustomProgressDialog getInstance(Context context){
        mContext = context;
        if(customProgressDialog == null){
            customProgressDialog = new CustomProgressDialog(context);
            customProgressDialog.setIcon(R.mipmap.ic_launcher);
            customProgressDialog.setTitle("Fetching Data");
            customProgressDialog.setCanceledOnTouchOutside(false);
            customProgressDialog.setCancelable(false);
        }
        return customProgressDialog;
    }

    public void mProgessDismiss(){
        this.dismiss();
        customProgressDialog = null;
    }
}
