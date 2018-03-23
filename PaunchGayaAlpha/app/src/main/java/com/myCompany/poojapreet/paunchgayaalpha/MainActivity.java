package com.myCompany.poojapreet.paunchgayaalpha;

import android.Manifest;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button b1,b2,b3,oksubdialog,deletesubdialog,savedialogmain,chosendialogmain;
    EditText et;
    Dialog dialog,dialogmain,subdialogchosenmain;
    RadioButton five,ten,fifteen;
    String address,city,country,fnadd;


    //ArrayList<String> contacts=new ArrayList<String>();
    ListView list,list2;
    ArrayAdapter<String> tmp,adaptersubdialogchosenmain,to_be_del;
    ArrayList<String> rlist=new ArrayList<>();
    ArrayList<String> chosencontacts=new ArrayList<String>();
    ArrayList<String> to_be_deleted=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Choose_Recipients chosen = new Choose_Recipients();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        subdialogchosenmain=new Dialog(this);
        subdialogchosenmain.setTitle("Chosen Contacts");
        subdialogchosenmain.setContentView(R.layout.list_of_dialog_contactschosen);
        oksubdialog=(Button)subdialogchosenmain.findViewById(R.id.OK);
        deletesubdialog=(Button)subdialogchosenmain.findViewById(R.id.del);
        list2=(ListView)subdialogchosenmain.findViewById(R.id.listView2);
        dialogmain = new Dialog(this);
        dialogmain.setTitle("Choose Recipients");
        dialogmain.setContentView(R.layout.activity_choose__recipients);
        list = (ListView) dialogmain.findViewById(R.id.listView);
        et=(EditText)dialogmain.findViewById(R.id.et);

        dialogmain.setCancelable(true);

        savedialogmain = (Button) dialogmain.findViewById(R.id.donebutton);
        //chosendialogmain = (Button) dialogmain.findViewById(R.id.temp_chos);
        final Choose_Recipients choose_recipients = new Choose_Recipients();

        /*dialog=new Dialog(this);
        dialog.setContentView(R.layout.choose_time);
        dialog.setTitle("Reaching..");
        five=(RadioButton)dialog.findViewById(R.id.rd_);
        ten=(RadioButton)dialog.findViewById(R.id.rd_2);
        fifteen=(RadioButton)dialog.findViewById(R.id.radioButton);*/
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.emergency);
        b3 = (Button) findViewById(R.id.button3);
        final int requestcodeforcontacts = 0;
        final int requestcodeforlocation=0;
        int requestcodeforsms=0;
        final int requestcodeforinternet=0;
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_CONTACTS)){

            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_CONTACTS},requestcodeforcontacts);
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){

            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},requestcodeforcontacts);
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)){

            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET},requestcodeforinternet);
            }
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.SEND_SMS)){

            }
            else{
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS},requestcodeforsms);
            }
        }

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


                if (chosencontacts.contains(temp)) {
                    Toast.makeText(MainActivity.this,""+name+" is already selected",Toast.LENGTH_SHORT).show();
                } else {
                    chosencontacts.add(temp);

                    Toast.makeText(MainActivity.this, "" + name + " is " + "selected", Toast.LENGTH_SHORT).show();}

                adaptersubdialogchosenmain= new ArrayAdapter<String>(getApplicationContext(), R.layout.checkedtextview_for_dialog, chosencontacts);
                list2.setAdapter(adaptersubdialogchosenmain);
            }
        });
        savedialogmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,""+"Contacts saved",Toast.LENGTH_SHORT).show();
                dialogmain.dismiss();
            }
        });

        et.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changes the Text
                MainActivity.this.tmp.getFilter().filter(cs);
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
        /*chosendialogmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subdialogchosenmain.show();
            }
        });*/
        oksubdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subdialogchosenmain.dismiss();
            }
        });
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView ck = (CheckedTextView) list2.getAdapter().getView(position, null, null);
                String str = (String) list2.getItemAtPosition(position);
                // Toast.makeText(Choose_Recipients.this,""+str,Toast.LENGTH_SHORT).show();

                if (to_be_deleted.contains(str)) {
                    to_be_deleted.remove(str);
                } else {
                    to_be_deleted.add(str);
                }
                //Toast.makeText(MainActivity.this,""+to_be_deleted.size(),Toast.LENGTH_SHORT).show();
            }
        });
        deletesubdialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int t, x;

                for (t = 0; t < to_be_deleted.size(); t++) {
                    String r = to_be_deleted.get(t);
                    for (x = 0; x < chosencontacts.size(); x++) {
                        if (chosencontacts.contains(r)) {
                            chosencontacts.remove(r);
                        }
                    }
                }
                //Toast.makeText(Choose_Recipients.this,""+chosenContacts.size(),Toast.LENGTH_SHORT).show();
                adaptersubdialogchosenmain = new ArrayAdapter<String>(getApplicationContext(), R.layout.checkedtextview_for_dialog, chosencontacts);
                list2.setAdapter(adaptersubdialogchosenmain);

            }
        });

        final GPSTracker gps = new GPSTracker(this);
        final double lat, lng;
        //lat = gps.getLatitude();
        //lng = gps.getLongitude();
        final Geocoder geocoder;

        geocoder = new Geocoder(this, Locale.getDefault());
       b3.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                   if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){

                   }
                   else{
                       ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},requestcodeforlocation);
                   }
               }
               if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
                   if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)){

                   }
                   else{
                       ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET},requestcodeforinternet);
                   }
               }


               try {
                   GPSTracker gps = new GPSTracker(MainActivity.this);

                   double lat = gps.getLatitude();
                   double lng = gps.getLongitude();
                   List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
                  // if (addresses != null) {
                       address = addresses.get(0).getAddressLine(0);
                       city = addresses.get(0).getAddressLine(1);
                       country = addresses.get(0).getAddressLine(2);
                       fnadd=address+","+city+","+country;
                   if (chosencontacts.size()==0) {
                       Toast.makeText(MainActivity.this, "" + "Please choose some contacts", Toast.LENGTH_SHORT).show();
                   }

                   else{
                       choose_recipients.SendMap(chosencontacts,fnadd);
                       Toast.makeText(MainActivity.this,""+"Current Location sent to chosen contacts",Toast.LENGTH_SHORT).show();
                   }
                  /* } else {
                       Toast.makeText(MainActivity.this, "" + "No address found. Please restart your location service.", Toast.LENGTH_SHORT).show();
                   }*/
               } catch (IOException e) {
                   e.printStackTrace();
               } catch (NullPointerException e) {
                   Toast.makeText(MainActivity.this, "" + "No address found. Please restart your location service and try after a few seconds.", Toast.LENGTH_SHORT).show();
                   e.printStackTrace();
               } catch (IndexOutOfBoundsException e){
                   Toast.makeText(MainActivity.this, "" + "No address found. Please restart your location service and try after a few seconds.", Toast.LENGTH_SHORT).show();
                   e.printStackTrace();
               }
           }
       });
       /* b5=(Button)dialog.findViewById(R.id.ok);
        b6=(Button)dialogmain.findViewById(R.id.cancel);*/
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chosencontacts.size()==0){
                    Toast.makeText(MainActivity.this, "" + "Please choose some contacts.", Toast.LENGTH_SHORT).show();

                }else {
                    choose_recipients.Send(chosencontacts);
                    Toast.makeText(MainActivity.this, "" + chosencontacts.size() + " Message(s) sent successfully", Toast.LENGTH_SHORT).show();
                }



        /*String h=getIntent().getExtras().getString("address");
        String p=getIntent().getExtras().getString("city");
        String l=getIntent().getExtras().getString("country");*/


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){

                    }
                    else{
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},requestcodeforcontacts);
                    }
                }
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)!= PackageManager.PERMISSION_GRANTED){
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.INTERNET)){

                    }
                    else{
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET},requestcodeforinternet);
                    }
                }
                try {
                    final GPSTracker gps = new GPSTracker(MainActivity.this);

                    double lati=gps.getLatitude();
                    double lngi=gps.getLongitude();
                    List<Address> addresses = geocoder.getFromLocation(lati, lngi, 1);
                    // if (addresses != null) {
                    address = addresses.get(0).getAddressLine(0);
                    city = addresses.get(0).getAddressLine(1);
                    country = addresses.get(0).getAddressLine(2);

                    fnadd=address+","+city+","+country;
                    if (chosencontacts.size()==0) {
                        Toast.makeText(MainActivity.this, "" + "Please choose some contacts", Toast.LENGTH_SHORT).show();
                    }else{
                        choose_recipients.Emergency(chosencontacts,fnadd);
                        Toast.makeText(MainActivity.this,""+"Emergency alert sent to chosen contacts",Toast.LENGTH_SHORT).show();}

                  /* } else {
                       Toast.makeText(MainActivity.this, "" + "No address found. Please restart your location service.", Toast.LENGTH_SHORT).show();
                   }*/
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    Toast.makeText(MainActivity.this, "" + "No address found. Please restart your location service and wait a few seconds.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e){
                    Toast.makeText(MainActivity.this, "" + "No address found. Please restart your location service and wait a few seconds.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }




        }});

       /* b1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dialog.show();

                return true;
            }
        });*/
    }


    @Override
    protected void onPause(){
        super.onPause();
        Log.d("snehil","onPause()");
        if (adaptersubdialogchosenmain!=null){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();
// This will remove all entries of the specific SharedPreferences
            editor.clear();
            for (int i = 0; i < adaptersubdialogchosenmain.getCount(); ++i){
                // This assumes you only have the list items in the SharedPreferences.
                editor.putString(String.valueOf(i), adaptersubdialogchosenmain.getItem(i));
            }
            editor.commit();}
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.d("sn","onStop()");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("sn","onResume()");
        chosencontacts.clear();
        ArrayAdapter<String> po=new ArrayAdapter<String>(MainActivity.this,R.layout.checkedtextview_for_dialog,chosencontacts);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        for (int i = 0;; i++) {
            final String str = prefs.getString(String.valueOf(i), "");
            if (!str.equals("")) {
                po.add(str);
            } else {
                break; // Empty String means the default value was returned.
            }
        }
        list2.setAdapter(po);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.choices) {
            LoadContactsAyscn loadContactsAyscn=new LoadContactsAyscn();
            loadContactsAyscn.execute();


            dialogmain.show();
        }
        if (id==R.id.viewchosen){
            subdialogchosenmain.show();
        }
       /*if(id==R.id.settings){
            Intent intent=new Intent(this,App_settings.class);
            startActivity(intent);}*/

        return super.onOptionsItemSelected(item);
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

             ArrayList<String> contacts=new ArrayList<>();
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

             ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    getApplicationContext(), R.layout.simple_list_item_single_choice, contacts);
            tmp=adapter;
            list.setAdapter(adapter);



        }


    }
}


