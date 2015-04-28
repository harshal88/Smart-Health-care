package com.unique.smarthealthcare.test;

import java.util.Arrays;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.test.ActivityUnitTestCase;
import com.unique.smarthealthcare.Splashscreen;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test001_Application extends ActivityUnitTestCase<Splashscreen> {

    private Splashscreen splashScreenActivity;
    private PackageInfo pinfo;    
    
    public Test001_Application() {
        super(Splashscreen.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(), Splashscreen.class);
            startActivity(intent, null, null);
        splashScreenActivity = getActivity();
        try {
            pinfo = splashScreenActivity.getPackageManager().getPackageInfo(splashScreenActivity.getPackageName(), PackageManager.GET_PERMISSIONS);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void test_step_1_SDKlevel(){        
    	assertEquals(true, Build.VERSION.SDK_INT == 17);//Should be 16
    }

    public void test_step_2_TargetSDKLevel(){    		    	
    	assertEquals(19, pinfo.applicationInfo.targetSdkVersion);
    }
    
    public void test_step_3_AppTitle(){
    	assertEquals("Smart Health Care", splashScreenActivity.getApplicationInfo().loadLabel(splashScreenActivity.getPackageManager()));
    }
    
    public void test_step_4_NetworkPermissions(){    	
    	assertTrue(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.INTERNET"));
    	assertTrue(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.ACCESS_NETWORK_STATE"));
    }
    
    public void test_step_5_GPSLocationPermissions(){    	    	
    	assertTrue(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.ACCESS_COARSE_LOCATION"));
    	assertTrue(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.ACCESS_FINE_LOCATION"));
    	assertTrue(Arrays.asList(pinfo.requestedPermissions).contains("com.google.android.providers.gsf.permission.READ_GSERVICES"));
    }
    
    public void test_step_6_NoOtherPermissions(){
    	assertFalse(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.ACCESS_WIFI_STATE"));
    	assertFalse(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.BATTERY_STATS"));
    	assertFalse(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.BLUETOOTH"));
    	assertFalse(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.CAMERA"));
    	assertFalse(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.BROADCAST_SMS"));
    	assertFalse(Arrays.asList(pinfo.requestedPermissions).contains("android.permission.REBOOT"));
    }
    
}
