package com.yarin.android.Examples_03_02;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.widget.TextView;

public class Activity01 extends Activity
{
//    public void setPhoneNumber(Intent data){
//
//        Cursor cursor =  managedQuery(data.getData(), null, null, null, null);
//        while (cursor.moveToNext())
//        {
//            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
//
//            displayName.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)));
//
//            String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//            if ( hasPhone.equalsIgnoreCase("1"))
//                hasPhone = "true";
//            else
//                hasPhone = "false" ;
//
//            if (Boolean.parseBoolean(hasPhone))
//            {
//                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
//                while (phones.moveToNext())
//                {
//                    contactNumberEdit.setText(phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
//                }
//                phones.close();
//            }
//        }
//        cursor.close();
//
//
//
//
//
//    }

	public void onCreate(Bundle savedInstanceState)
	{
		TextView tv = new TextView(this);
		String string = "";
		super.onCreate(savedInstanceState);
		//�õ�ContentResolver����
        ContentResolver cr = getContentResolver();
        //ȡ�õ绰���п�ʼһ��Ĺ��
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        //�����ƶ�һ�¹��
        while(cursor.moveToNext())
        {
        	//ȡ����ϵ������
        	int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        	String contact = cursor.getString(nameFieldColumnIndex);
        	//ȡ�õ绰����
        	int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
        	String number = cursor.getString(numberFieldColumnIndex);

        	string += (contact+":"+number+"\n");
        }
        cursor.close();
		//����TextView��ʾ������
		tv.setText(string);
		//��ʾ����Ļ
		setContentView(tv);
	}
}
