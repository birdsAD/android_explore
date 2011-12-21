package com.yarin.android.Examples_03_04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity02 extends Activity {
    private static final String TAG = "Activity02";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);    //To change body of overridden methods use File | Settings | File Templates.
        Log.v(TAG, "onSaveInstanceState ... ");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        outState.putString("name: ", df.format(new Date()));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        Log.v(TAG, this+ " onCreate");
        Button button = (Button) findViewById(R.id.button2);
        /* ����button���¼���Ϣ */
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /* �½�һ��Intent���� */
                Intent intent = new Intent();
                /* ָ��intentҪ�������� */
                intent.setClass(Activity02.this, Activity01.class);
                /* ����һ���µ�Activity */
                startActivity(intent);
                /* �رյ�ǰ��Activity */
//				Activity02.this.finish();
            }
        });
    }

    public void onStart() {
        super.onStart();

        Log.v(TAG, this+ " onStart");
    }

    public void onResume() {
        super.onResume();
        Log.v(TAG, this+  " onResume");
    }

    public void onPause() {
        super.onPause();
        Log.v(TAG,  this+ " onPause");
    }

    public void onStop() {
        super.onStop();
        Log.v(TAG, this + " onStop");
    }

    public void onDestroy() {
        super.onDestroy();
        Log.v(TAG, this +  " onDestroy");
    }

    public void onRestart() {
        super.onRestart();
        Log.v(TAG,  this+ " onReStart");
    }
}

