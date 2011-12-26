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
        //�õ�ContentResolver����
        ContentResolver cr = getContentResolver();
        //ȡ�õ绰���п�ʼһ��Ĺ��
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //�����ƶ�һ�¹��
        while (cursor.moveToNext()) {
            // �Ȼ�ȡĿ�������˵� _ID,
            String contractId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //ȡ����ϵ������
            String name = getContractName(cursor);
            String phoneNumber = getFirstPhoneNumber(cursor, contractId);
            String email = getFirstEmail(cursor, contractId);
            string += (name + ":" + phoneNumber + ":"+email+"\n");
        }
        cursor.close();
        //����TextView��ʾ������
        tv.setText(string);
        //��ʾ����Ļ
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

        // ����͸��_ID���ҵ绰���룬�绰������ܲ�ֹһ��,�������ж����޵绰����
        String isPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
        String number = null;
        // ����е绰���룬������ȥquery�绰����
        if (Integer.parseInt(isPhone) > 0) {
            Cursor phoneNumberCursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contractId, null, null);
            while (phoneNumberCursor.moveToNext()) {
                number = phoneNumberCursor.getString(phoneNumberCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                // ֻȡ��һ���绰����
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
