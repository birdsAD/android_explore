package com.ggd543.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ControlActivity extends Activity {
    private static final String TAG = "ControlActivity";
    private ICountService iCountService;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.v(TAG, "---- " + Thread.currentThread() + " ----");

        setStartButtonListener();
        setStopButtonListener();
        setBindButtonListener();
        setUnbindButtonListener();
    }

    final private ServiceConnection serviceConnection = new ServiceConnection() {
        private String TAG = "ServiceConnection";

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.v(TAG, "onServiceDisconnected - componentName: " + componentName);
            iCountService = (ICountService) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.v(TAG, "onServiceDisconnected - componentName: " + componentName);
        }
    };

    private AsyncTask<Void, Integer, Integer> asyncTask;

    class AsynchronousTask extends AsyncTask<Void, Integer, Integer> {
        final private String TAG = "AsyncTask";
        private boolean isStop;
        final private ICountService iCountService;

        public AsynchronousTask(ICountService iCountService) {
            this.iCountService = iCountService;
        }

        @Override
        protected Integer doInBackground(Void... v) {
            Log.v(TAG, "doInBackground");
            if (iCountService == null) {
                Log.v(TAG, "return immediately");
                return 0;
            }
            while (!isStop) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                Log.v(TAG, "call publishProgress ");
                publishProgress(iCountService.getCounter());
            }
            return iCountService.getCounter();
        }

        @Override
        protected void onPreExecute() {
            Log.v(TAG, "PreExecute");
            isStop = false;
            super.onPreExecute();    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.v(TAG, "PostExecute");
            isStop = true;
            super.onPostExecute(integer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int counter = values[0];
            ((TextView) findViewById(R.id.textInfo)).setText("" + counter);
            Log.v(TAG, "onProgressUpdate");
            super.onProgressUpdate(values);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        protected void onCancelled() {
            Log.v(TAG, "onCancelled");
            isStop = true;
            super.onCancelled();    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

    private void setStartButtonListener() {
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(ControlActivity.this, CountService.class));
                asyncTask = new AsynchronousTask(iCountService);
                asyncTask.execute();
            }
        }

        );
    }

    private void setStopButtonListener() {
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(ControlActivity.this, CountService.class));
                if (asyncTask != null) {
                    asyncTask.cancel(true);
                    asyncTask = null;
                }
            }
        });
    }

    private void setBindButtonListener() {
        findViewById(R.id.bind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindService();
            }
        });
    }


    private void setUnbindButtonListener() {
        findViewById(R.id.unbind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unbindService();
            }
        });
    }

    private void bindService() {
        bindService(new Intent(ControlActivity.this, CountService.class), serviceConnection, BIND_AUTO_CREATE);
    }

    private void unbindService() {
        try {
            unbindService(serviceConnection);
            iCountService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            unbindService();
            stopService(new Intent(ControlActivity.this, CountService.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.v(TAG, "onDestroy");
        super.onDestroy();
    }
}
