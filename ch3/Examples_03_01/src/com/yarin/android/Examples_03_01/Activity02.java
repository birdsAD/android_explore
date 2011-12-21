package com.yarin.android.Examples_03_01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Activity02 extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		Button button = (Button) findViewById(R.id.button2);
		button.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(Activity02.this, Activity01.class);
				startActivity(intent);
				Activity02.this.finish();
			}
		});
	}
}

