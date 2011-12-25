package com.ggd543.android;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class FileActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        loadText();
    }

    private void loadText() {
//        InputStream is = Resources.getSystem().openRawResource(R.raw.text);
        InputStream is = getResources().openRawResource(R.raw.text);
        try {
            byte[] buf = new byte[is.available()];
            is.read(buf, 0, buf.length);
            ((TextView) findViewById(R.layout.main)).setText(new String(buf, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
