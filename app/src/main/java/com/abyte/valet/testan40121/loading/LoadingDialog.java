package com.abyte.valet.testan40121.loading;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.abyte.valet.testan40121.R;

public class LoadingDialog {

    private final Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity myActivity){
        activity = myActivity;
    }

    @SuppressLint("InflateParams")
    public synchronized void startDialog(){

        if (dialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            LayoutInflater inflater = activity.getLayoutInflater();
            builder.setView(inflater.inflate(R.layout.custom_dialog, null));

            builder.setCancelable(true);

            dialog = builder.create();
            dialog.show();
        } else if (!dialog.isShowing()){
            dialog.show();
        }
    }

    public synchronized void stopDialog(){
        if (dialog != null)
        dialog.dismiss();
    }

}
