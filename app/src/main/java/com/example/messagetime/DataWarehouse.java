package com.example.messagetime;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DataWarehouse {
    private Context context;
    private HashSet<String> savedPhoneNumbers;

    DataWarehouse(Context context){
        this.context = context;
        savedPhoneNumbers = new HashSet<>();
    }

    public List<ContactsInformation> getNamePhoneIDFromContacts(){
        List<ContactsInformation> contactInformationList = new ArrayList<>();

        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));



                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String phoneName = phoneNo.replaceAll("[\\s\\+\\(\\)-]", "");
                        if (!savedPhoneNumbers.contains(phoneName)) {
                            ContactsInformation person = new ContactsInformation(id, name, phoneName);
                            contactInformationList.add(person);
                            savedPhoneNumbers.add(phoneName);
                        }
                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
        return contactInformationList;
    }

    //final List<String> contactsBookNumbers = new ArrayList<>();
        /*
        Cursor phones = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()){
            String phoneName = phones.getString(phones.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            //phoneName = phoneNumber.replaceAll("[\\s\\+\\(\\)-]", "");
            contactsBookNumbers.add(phoneName);
        }
        phones.close();
        */

}
