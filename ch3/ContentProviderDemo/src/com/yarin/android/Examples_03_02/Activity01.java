package com.yarin.android.Examples_03_02;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class Activity01 extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        TextView tv = new TextView(this);
        String string = "";
        super.onCreate(savedInstanceState);
        //得到ContentResolver对象
        ContentResolver cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //向下移动一下光标
        while (cursor.moveToNext()) {
            // 先获取目标联络人的 _ID,
            String contractId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //取得联系人名字
            String name = getContractName(cursor);
            String phoneNumber = getFirstPhoneNumber(cursor, contractId);
            String email = getFirstEmail(cursor, contractId);
            string += (name + ":" + phoneNumber + ":"+email+"\n");
        }
        cursor.close();
        //设置TextView显示的内容
        tv.setText(string);
        //显示到屏幕
        setContentView(tv);
    }

    private String getFirstEmail(Cursor cursor, String contractId) {
        String email = null;
        Cursor emailCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contractId, null, null);
        if (emailCursor.moveToFirst()) {
            email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
        }
        return email;
    }

    private String getFirstPhoneNumber(Cursor cursor, String contractId) {
        ContentResolver cr = getContentResolver();
        String contact = getContractName(cursor);

        // 接着透过_ID来找电话号码，电话号码可能不止一个,所以先判断有无电话号码
        String isPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;
        // 如果有电话号码，接着再去query电话号码
        if (Integer.parseInt(isPhone) > 0) {
            Cursor phoneNumberCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contractId, null, null);
            while (phoneNumberCursor.moveToNext()) {
                number = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // 只取第一个电话号码
                break;
            }
            phoneNumberCursor.close();
        }
        return number;
    }

    private String getContractName(Cursor cursor) {
        int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        return cursor.getString(nameFieldColumnIndex);
    }
}
