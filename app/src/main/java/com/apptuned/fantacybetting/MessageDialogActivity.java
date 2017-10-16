package com.apptuned.fantacybetting;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by davies on 10/16/17.
 */
public class MessageDialogActivity extends Dialog implements android.view.View.OnClickListener {

    private Activity activity;
    public Dialog d;
    private String title, message;
    private int status;

    private TextView mdTitle, mdMessage, mdDismiss;

    public MessageDialogActivity(Activity activity) {
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
    }

    public MessageDialogActivity(Activity activity, String title, String message, int status) {
        // status -1 Error, 0 Warning, 1 Success
        super(activity);
        // TODO Auto-generated constructor stub
        this.activity = activity;
        this.title = title;
        this.message = message;
        this.status = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_message_dialog);

        mdTitle = (TextView)findViewById(R.id.mdTitle);
        mdTitle.setText(this.title == null ? "" : this.title);

        mdMessage = (TextView)findViewById(R.id.mdMessage);
        mdMessage.setText(this.message == null ? "" : this.message);

        mdDismiss = (TextView) findViewById(R.id.mdDismiss);
        mdDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Setting home team icon
        String statusIconUrl = "";
        switch (status){
            case -1:
                mdTitle.setTextColor(Color.rgb(169,68,66));
                break;
            case 0:
                mdTitle.setTextColor(Color.rgb(108,81,44));
                break;
            case 1:
                mdTitle.setTextColor(Color.rgb(43,84,44));
                break;
            default:
                mdTitle.setTextColor(Color.rgb(36,82,105));
                break;
        }

    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

}
