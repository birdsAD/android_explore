package com.yarin.android.Examples_04_11;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class Activity01 extends Activity {
    private static String TAG = Activity01.class.getName();
    TextView m_TextView;
    // 声明DatePicker对象
    DatePicker m_DatePicker;
    // 声明TimePicker对象
    TimePicker m_TimePicker;
    Button m_dpButton;
    Button m_tpButton;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // java中的日历类
        Calendar c = Calendar.getInstance();

        m_TextView = (TextView) findViewById(R.id.TextView01);
        m_dpButton = (Button) findViewById(R.id.button1);
        m_tpButton = (Button) findViewById(R.id.button2);

        // 获取DatePicker对象
        m_DatePicker = (DatePicker) findViewById(R.id.DatePicker01);
        // 将日历初始化为当前系统时间，并设置其事件监听
        m_DatePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                        // 当日期更改时，在这里处理
                        // c.set(year, monthOfYear, dayOfMonth);
                        Log.v(TAG, view + " change to " + year + "-" + monthOfYear + "-" + dayOfMonth);
                    }
                });

        // 获取TimePicker对象
        m_TimePicker = (TimePicker) findViewById(R.id.TimePicker01);
        // 设置为24小时制显示
        m_TimePicker.setIs24HourView(true);

        // 监听时间改变
        m_TimePicker
                .setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                    @Override
                    public void onTimeChanged(TimePicker view, int hourOfDay,
                                              int minute) {
                        // 时间改变时处理
                        // c.set(year, month, day, hourOfDay, minute, second);
                        Log.v(TAG, view+" chagne to "+hourOfDay+":"+minute);
                    }
                });

        m_dpButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // 弹出日期设置对话框
                new DatePickerDialog(Activity01.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // 设置日历
                                Log.v(TAG, "设置日历 "+year+"-"+monthOfYear+"-"+dayOfMonth);
                            }
                        }, m_DatePicker.getYear(), m_DatePicker.getMonth(), m_DatePicker.getDayOfMonth()).show();
            }
        });

        m_tpButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                new TimePickerDialog(Activity01.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view,
                                                  int hourOfDay, int minute) {
                                // 设置时间
                                Log.v(TAG,"设置时间: "+hourOfDay+":"+minute);
                            }
                        },  m_TimePicker.getCurrentHour(),m_TimePicker.getCurrentMinute(), true).show();
            }
        });
    }
}
