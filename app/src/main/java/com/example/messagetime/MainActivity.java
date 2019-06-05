package com.example.messagetime;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    final private int REQUEST_MULTIPLE_PERMISSIONS = 124;
    List<ContactsInformation> myContacts = new ArrayList<>();
    RecyclerView contactsList;
    ContactsListAdapter contactsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "begin");
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {
            Log.d(TAG, "First if");
            AccessContactsPermission();

        } else {
            Log.d(TAG, "second if");
            contactsList = findViewById(R.id.contactsList);
            DataWarehouse dataWarehouse = new DataWarehouse(this);
            myContacts = dataWarehouse.getNamePhoneIDFromContacts();

            contactsListAdapter = new ContactsListAdapter(this, new ContactItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent callIntent = new Intent(MainActivity.this, CallActivity.class);
                    //TODO pass in argument extras depending on which item was clicked
                    startActivity(callIntent);
                    Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                }
            });


            contactsListAdapter.setPeopleList(myContacts);
            contactsList.setAdapter(contactsListAdapter);
            contactsList.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    private void AccessContactsPermission(){
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");
        if (!addPermission(permissionsList, Manifest.permission.CALL_PHONE))
            permissionsNeeded.add("Make Calls");

        if (permissionsList.size() > 0){
            if (permissionsNeeded.size() > 0){
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 0; i < permissionsNeeded.size(); i++){
                    message += ", " + permissionsNeeded.get(i);
                }
                showMessageOkCancel(message, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_MULTIPLE_PERMISSIONS);
                    }
                });
                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_MULTIPLE_PERMISSIONS);
            return;
        }
    }

    private void showMessageOkCancel(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);

            if (!shouldShowRequestPermissionRationale(permission))

                return false;
        }

        return true;
    }
}
