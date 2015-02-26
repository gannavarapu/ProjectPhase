package com.example.hp.phase1;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.provider.Settings;
import com.example.hp.phase1.DB;

public class helpactivity extends ActionBarActivity implements LocationListener {
    private TextView latituteField;
    private TextView longitudeField;
    private LocationManager locationManager;
    private String provider;
    boolean isGPSEnabled = false;
    Button greenBtn;
    // flag for network status
    boolean isNetworkEnabled = false;
    String[] numberlist={"","","","","",""};
    DB db= new DB(this);

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpactivity);
       greenBtn=(Button)findViewById(R.id.green);
        final Location location1= getLocation();
        greenBtn.setOnClickListener(new View.OnClickListener()
        {

                                        public  void  onClick(View view)
                                        {
                                       sendSMSMessage(location1);
                                        }
                                    }


        );


    }

    public Location getLocation ()
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        // locationManager.getLastKnownLocation();
        provider = LocationManager.GPS_PROVIDER;
        Location location = locationManager.getLastKnownLocation(provider);


        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
        if (!enabled) {
            System.out.println("Provider " + provider + " not been selected.");
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        // Initialize the location fields
        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            return location ;
            // onLocationChanged(location);
        } else {


            return location;
        }

    }

    protected void sendSMSMessage(Location currentLocation) {
        int i;
        Log.i("Send SMS", "");
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String mobilenum=prefs.getString("userid","5187635916");
        mobilenumbersObj mobilenumbersObj= db.getMobilenum(mobilenum);
        numberlist[0]=mobilenumbersObj.mobilenum1;
        numberlist[1]=mobilenumbersObj.mobilenum2;
        numberlist[2]=mobilenumbersObj.mobilenum3;
        numberlist[3]=mobilenumbersObj.mobilenum4;
        for(i=0;i<=3;i++)
        {


            String phoneNo = numberlist[i];
            String message = "Help!!!";
            if(phoneNo!= null&&phoneNo!="") {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    StringBuffer smsBody = new StringBuffer();
                    smsBody.append("Help!!!!!!!!!!!");
                    smsBody.append("http://maps.google.com?q=");
                    smsBody.append(23);
                    smsBody.append(",");
                    smsBody.append(39);

                    smsManager.sendTextMessage(phoneNo, null, smsBody.toString(), null, null);
                    Toast.makeText(getApplicationContext(), "SMS SENT",
                            Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.",
                            Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
    }
    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 400, 1, this);
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        int lat = (int) (location.getLatitude());
        int lng = (int) (location.getLongitude());
        latituteField.setText(String.valueOf(lat));
        longitudeField.setText(String.valueOf(lng));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enabled new provider " + provider,
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disabled provider " + provider,
                Toast.LENGTH_SHORT).show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        setTitle("Emergency Help");


        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

}
