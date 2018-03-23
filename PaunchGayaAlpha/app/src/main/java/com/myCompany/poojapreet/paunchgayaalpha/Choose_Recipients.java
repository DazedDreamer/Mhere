package com.myCompany.poojapreet.paunchgayaalpha;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class Choose_Recipients extends AppCompatActivity {
    private static final String msg = "MyActivity";
    String fnadd;

    Button b1, b2, ok, delete;
    Dialog dialog;
    ListView list, list_chosen;
    ArrayList<String> selecteditems = new ArrayList<String>();//hashmap for storing all the chosen contacts
    EditText editText;
    ArrayList<String> to_be_deleted = new ArrayList<String>();
    ArrayList<String> chosenContacts = new ArrayList<String>();
    ArrayList<String> contacts = new ArrayList<String>();
    String city, address, country;
    ArrayAdapter<String> adapter, adapter_dialog, tmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose__recipients);
        list = (ListView) findViewById(R.id.listView);
        //b2 = (Button) findViewById(R.id.temp_chos);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.list_of_dialog_contactschosen);
        dialog.setTitle("Chosen Contacts");
        dialog.setCancelable(true);
        list_chosen = (ListView) dialog.findViewById(R.id.listView2);
        ok = (Button) dialog.findViewById(R.id.OK);
        delete = (Button) dialog.findViewById(R.id.del);
        LoadContactsAyscn lca = new LoadContactsAyscn();
        lca.execute();
        editText = (EditText) findViewById(R.id.et);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView checkedTextView = (TextView) list.getAdapter().getView(position, null, null);


                // String temp_string= (String) parent.getItemAtPosition(position);
              /* Integer temp_pos=(Integer)adapter.getPosition(temp_string);
                Toast.makeText(Choose_Recipients.this,""+temp_pos+" "+adapter.getCount(),Toast.LENGTH_SHORT).show();
               /* View tempo;
                tempo=list.getChildAt(temp_pos);
                CheckedTextView checkedTextView=(CheckedTextView)tempo;*/


                String temp = (String) list.getItemAtPosition(position);
                String[] nu = temp.split(":");
                String number = nu[1];
                String name = nu[0];
                int u;


                if (chosenContacts.contains(temp)) {
                    Toast.makeText(Choose_Recipients.this,""+name+" is already selected",Toast.LENGTH_SHORT).show();
                } else {
                    chosenContacts.add(temp);

                Toast.makeText(Choose_Recipients.this, "" + name + " is " + "selected", Toast.LENGTH_SHORT).show();}

                adapter_dialog = new ArrayAdapter<String>(getApplicationContext(), R.layout.checkedtextview_for_dialog, chosenContacts);
                list_chosen.setAdapter(adapter_dialog);

               /* for (int index = 0; index < contacts.size(); index++) {
                    if (name.equals(contacts.get(index))) {
                        position = index;
                        break;
                    }
                }*/



               /* int temp_pos=list.getPositionForView(view);
                View tempo=list.getChildAt(temp_pos);
                CheckedTextView checkedTextView=(CheckedTextView)tempo;
                 if (checkedTextView.isChecked()){
                    checkedTextView.setChecked(false);
                }else{checkedTextView.setChecked(true);}*/
                //Toast.makeText(Choose_Recipients.this,""+chosenContacts.size(),Toast.LENGTH_SHORT).show();




                /*SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(Choose_Recipients.this);
                HashMap cc=sp.getAll(selecteditems));

                */

                //SaveAndSend(view);
            }
        });

        list_chosen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView ck = (CheckedTextView) list_chosen.getAdapter().getView(position, null, null);
                String str = (String) list_chosen.getItemAtPosition(position);
                // Toast.makeText(Choose_Recipients.this,""+str,Toast.LENGTH_SHORT).show();

                if (to_be_deleted.contains(str)) {
                    to_be_deleted.remove(str);
                } else {
                    to_be_deleted.add(str);
                }
                //Toast.makeText(Choose_Recipients.this,""+to_be_deleted.size(),Toast.LENGTH_SHORT).show();

            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int t, x;

                for (t = 0; t < to_be_deleted.size(); t++) {
                    String r = to_be_deleted.get(t);
                    for (x = 0; x < chosenContacts.size(); x++) {
                        if (chosenContacts.contains(r)) {
                            chosenContacts.remove(r);
                        }
                    }
                }
                //Toast.makeText(Choose_Recipients.this,""+chosenContacts.size(),Toast.LENGTH_SHORT).show();
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.checkedtextview_for_dialog, chosenContacts);
                list_chosen.setAdapter(adapter);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        GPSTracker gps = new GPSTracker(this);
        double lat, lng;
        lat = gps.getLatitude();
        lng = gps.getLongitude();
        Geocoder geocoder;

        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses != null) {
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getAddressLine(1);
                country = addresses.get(0).getAddressLine(2);
                fnadd=address+","+city+","+country;
            } else {
                Toast.makeText(Choose_Recipients.this, "" + "No address", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        b1 = (Button) findViewById(R.id.donebutton);//DONE Button in toolbar

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Choose_Recipients.this, "" + "Contacts saved", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(Choose_Recipients.this, MainActivity.class);
                myIntent.putExtra("ultimateaddress",fnadd);

                myIntent.putStringArrayListExtra("chosencontacts", chosenContacts);
                startActivity(myIntent);



            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changes the Text
                Choose_Recipients.this.tmp.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

    }














    int i;

    public void Send(ArrayList<String> a) {
        SmsManager smsManager = SmsManager.getDefault();
        for (i = 0; i < a.size(); i++) {
            String e = a.get(i);
            String[] temparray = e.split(":");

            smsManager.sendTextMessage(temparray[1], null, "Reached", null, null);

        }
        /*Iterator it=a.entrySet().iterator();
        SmsManager sms = SmsManager.getDefault();

        while(it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            String temporary=(String)pair.getValue();
            sms.sendTextMessage(temporary,null,"Reached",null,null);*/


    }
    public void SendMap(ArrayList<String> a,String t) {
        SmsManager smsManager = SmsManager.getDefault();
        for (i = 0; i < a.size(); i++) {
            String e = a.get(i);
            String[] temparray = e.split(":");

            smsManager.sendTextMessage(temparray[1], null, "I'm here at " + t, null, null);


        }
    }
    public void Emergency(ArrayList<String> a,String t) {
        SmsManager smsManager = SmsManager.getDefault();
        for (i = 0; i < a.size(); i++) {
            String e = a.get(i);
            String[] temparray = e.split(":");

            smsManager.sendTextMessage(temparray[1], null, "I am in an EMERGENCY,HELP!!!."+"I'm here at " + t, null, null);


        }
    }
    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
        //ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            //  pd = ProgressDialog.show(Choose_Recipients.this, "Loading Contacts",
            //        "Please Wait");
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            // TODO Auto-generated method stub


            Cursor c = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, null);
            while (c.moveToNext()) {

                String contactName = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = c
                        .getString(c
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                contacts.add(contactName + ":" + phNumber);
            }
            c.close();

            return contacts;
        }

        @Override
        protected void onPostExecute(ArrayList<String> contacts) {
            // TODO Auto-generated method stub
            super.onPostExecute(contacts);
            //pd.dismiss();
            adapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.simple_list_item_single_choice, contacts);
            tmp = adapter;

            list.setAdapter(adapter);

        }


    }
}











