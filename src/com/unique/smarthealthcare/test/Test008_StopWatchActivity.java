package com.unique.smarthealthcare.test;


import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.robotium.solo.Solo;
import com.unique.smarthealthcare.Chronometer;
import com.unique.smarthealthcare.HomeActivity;
import com.unique.smarthealthcare.R;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test008_StopWatchActivity extends ActivityInstrumentationTestCase2<HomeActivity> {

    private final static int TOAST_CLOSE_TIME_OUT = 5000;
    private Activity activity;
    private Solo solo;
    private Chronometer chronometer;
    private ImageButton pauseBtn, startBtn, stopBtn;

    public Test008_StopWatchActivity() {
        super(HomeActivity.class);
    }

    //Setting Up tests
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
            
        solo.unlockScreen();
        solo.setNavigationDrawer(Solo.OPENED);

        ListView drawerList = (ListView) solo.getView(com.unique.smarthealthcare.R.id.left_drawer);
        View listElement = drawerList.getChildAt(4);
        solo.clickOnView(listElement);

        solo.waitForFragmentById(R.layout.fragment_stopwatch, 2000);
        activity = solo.getCurrentActivity();

        chronometer = (Chronometer) activity.findViewById(R.id.chronometer);
        pauseBtn = (ImageButton) activity.findViewById(R.id.bPause);
        startBtn = (ImageButton) activity.findViewById(R.id.bStart);
        stopBtn = (ImageButton) activity.findViewById(R.id.bStop);
    }

    //Check that all objects in StopWatch fragment are working correctly
    public void test_step001_TimerTest() throws Exception {
        solo.clickOnView(startBtn);
        Thread.sleep(3000);
        pauseBtn = (ImageButton) activity.findViewById(R.id.bPause);
        stopBtn = (ImageButton) activity.findViewById(R.id.bStop);
        solo.clickOnView(pauseBtn);
        String chronoTime = chronometer.getText().toString();
        String[] chronoTimeSplit = chronoTime.split(":");
        assertEquals("Timer is not working correctly",chronoTimeSplit[1],"03");

        solo.clickOnView(startBtn);
        Thread.sleep(4000);
        solo.clickOnView(pauseBtn);
        chronoTime = chronometer.getText().toString();
        chronoTimeSplit = chronoTime.split(":");
        assertEquals("Timer is not working correctly",chronoTimeSplit[1],"07");

        solo.clickOnView(startBtn);
        Thread.sleep(2000);
        solo.clickOnView(startBtn);
        assertTrue("Lap functionality is not working correctly",solo.searchText("09"));

        Thread.sleep(1000);
        solo.clickOnView(stopBtn);
        Thread.sleep(1000);
        chronoTime = chronometer.getText().toString();
        chronoTimeSplit = chronoTime.split(":");
        assertEquals("Timer is not working correctly",chronoTimeSplit[1],"00");
    }
    
}
