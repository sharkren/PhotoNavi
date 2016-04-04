package com.example.thomas.photonavi.service;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;

import com.example.thomas.photonavi.model.ContactItem;
import com.example.thomas.photonavi.model.ContactItemList;

/**
 * Created by thomas on 2016-01-25.
 */
public class MyPhoneNumList implements Runnable {

    private Context mContext;
    private Cursor mCursor;

    public MyPhoneNumList(Context context) {
        mContext = context;
    }

    @Override
    public void run() {
        mCursor = mContext.getContentResolver()
                .query(Phone.CONTENT_URI, null, null, null, null);

        String name;
        String number;

        ContactItemList contactItemList = ContactItemList.getInstance();
        if (mCursor.moveToFirst() && mCursor.getCount() > 0) {
            do {
                name = mCursor.getString(mCursor.getColumnIndex(Phone.DISPLAY_NAME));
                number = mCursor.getString(mCursor.getColumnIndex(Phone.NUMBER));
                ContactItem contactItem = new ContactItem();
                contactItem.setName(name);
                contactItem.setPhoneNumber(number);

                contactItemList.setContactItemList(contactItem);
                //Log.d("MyContactList", "Name : " + name + " / Number : " + number);
                //Log.d("MyContactList", "setName : " + contactItem.getName() + " / setPhoneNumber : " + contactItem.getPhoneNumber());

            } while(mCursor.moveToNext());
        }

        mCursor.close();
    }

}
