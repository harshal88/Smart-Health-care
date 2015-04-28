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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.robotium.solo.Solo;
import com.unique.smarthealthcare.Chronometer;
import com.unique.smarthealthcare.HomeActivity;
import com.unique.smarthealthcare.R;
import com.unique.smarthealthcare.RecordingActivity;
import com.unique.smarthealthcare.SaveTripActivity;
import com.unique.smarthealthcare.ShowMapActivity;
import com.unique.smarthealthcare.StartTripActivity;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test010_NewTripView extends ActivityInstrumentationTestCase2<HomeActivity> {

	private Solo solo;
    private final static int TOAST_CLOSE_TIME_OUT = 5000;
    private Activity activity;
    private TextView txtDistance;
    private TextView txtDuration;
    private TextView txtCurSpeed;
    private TextView txtMaxSpeed;
    private TextView txtAvgSpeed;
    private GoogleMap map = null;
    private MapFragment mapFragment;
    private ToggleButton showMap;
    private ToggleButton showStats;
    private Button btnPause, btnFinish;

    public Test010_NewTripView() {
        super(HomeActivity.class);
    }

    //Setting Up tests
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());

        solo.unlockScreen();
        activity = solo.getCurrentActivity();
        TextView tvNewTrip = (TextView)activity.findViewById(R.id.ButtonNew);

        solo.clickOnView(tvNewTrip);
        solo.waitForActivity(StartTripActivity.class,5000);

        activity = solo.getCurrentActivity();
        Button startBtn = (Button)activity.findViewById(R.id.ButtonStart);

     
        
        solo.clickOnView(startBtn);
        solo.waitForActivity(RecordingActivity.class,5000);

        activity = solo.getCurrentActivity();

        mapFragment = (MapFragment)activity.getFragmentManager().findFragmentById(R.id.map);
        if(mapFragment!=null){
        	map = mapFragment.getMap();
        }

        txtDuration = (TextView)activity.findViewById(R.id.TextDuration);
        txtDistance = (TextView)activity.findViewById(R.id.TextDistance);
        txtCurSpeed = (TextView)activity.findViewById(R.id.TextSpeed);
        txtMaxSpeed = (TextView)activity.findViewById(R.id.TextMaxSpeed);
        txtAvgSpeed = (TextView)activity.findViewById(R.id.TextAvgSpeed);

        showMap = (ToggleButton) activity.findViewById(R.id.show_map);
        showStats = (ToggleButton) activity.findViewById(R.id.show_stat);

        btnPause = (Button)activity.findViewById(R.id.btn_pause);
        btnFinish = (Button)activity.findViewById(R.id.btn_finished);
    }

    //Check that all objects in Recording Activity are exist
    public void test_step001_ObjectExists() throws Exception {
    	

        TextView labelDuration = (TextView)activity.findViewById(R.id.TextView012);
        TextView labelDistance = (TextView)activity.findViewById(R.id.TextView011);
        TextView labelSpeed = (TextView)activity.findViewById(R.id.TextView013);
        TextView labelMaxSpeed = (TextView)activity.findViewById(R.id.TextView014);
        TextView labelAvgSpeed = (TextView)activity.findViewById(R.id.TextView016);

        if(labelDuration!=null){
        	assertNotNull("Duration label is not exist", labelDuration.getText()!=null);
        }else{
        	assertTrue(false);
        }
        assertNotNull("Distance text is not exist", txtDistance.getText()!=null);
        if(labelDistance!=null){
        assertNotNull("Distance label is not exist", labelDistance.getText()!=null);
	    }else{
	    	assertTrue(false);
	    }
        assertNotNull("CurSpeed text is not exist", txtCurSpeed.getText()!=null);
        if(labelSpeed!=null){
        assertNotNull("CurSpeed label is exist", labelSpeed.getText()!=null);
		}else{
			assertTrue(false);
		}
        assertNotNull("MaxSpeed text is not exist", txtMaxSpeed.getText()!=null);
        if(labelMaxSpeed!=null){
        assertNotNull("MaxSpeed label is not exist", labelMaxSpeed.getText()!=null);
	    }else{
	    	assertTrue(false);
	    }
        assertNotNull("AvgSpeed text is not exist", txtAvgSpeed.getText()!=null);
        if(labelAvgSpeed!=null){
        assertNotNull("AvgSpeed label is not exist", labelAvgSpeed.getText()!=null);
	    }else{
	    	assertTrue(false);
	    }

        assertNotNull("Map is not exist",map!=null);

        assertNotNull("ShowMap ToggleButton is not exist",showMap!=null);
        assertNotNull("ShowStat ToggleButton label is not exist",showStats!=null);

        assertNotNull("Pause Button is not exist",btnPause!=null);
        assertNotNull("Finish Button is not exist", btnFinish!=null);


    }

  //Check that all objects in Recording Activity are working correctly
    public void test_step002_ObjectWork() throws Exception {
  
    	
    	
        solo.clickOnView(showMap);
        Thread.sleep(1000);
        assertEquals("Show (Hide) Map functionality is not working",mapFragment.getView().getVisibility(),View.GONE);

        solo.clickOnView(showMap);
        Thread.sleep(1000);
        assertEquals("Show (Hide) Map functionality is not working", mapFragment.getView().getVisibility(), View.VISIBLE);

        solo.clickOnView(btnPause);
        checkToast("Recording paused; GPS now offline");

        Thread.sleep(2000);
        solo.clickOnView(btnPause);
        checkToast("GPS restarted. It may take a moment to resync.");
        Thread.sleep(20000);

        solo.clickOnView(btnFinish);
        
        assertTrue("Distance text is empty", !txtDistance.getText().toString().isEmpty());
        assertTrue("CurSpeed text is empty", !txtCurSpeed.getText().toString().isEmpty());
        assertTrue("MaxSpeed text is empty", !txtMaxSpeed.getText().toString().isEmpty());
        assertTrue("AvgSpeed text is empty", !txtAvgSpeed.getText().toString().isEmpty());

        solo.waitForActivity(SaveTripActivity.class,2000);
        Activity activity = solo.getCurrentActivity();
        ToggleButton commuteBtn = (ToggleButton)activity.findViewById(R.id.ToggleCommute);
        ToggleButton schoolBtn = (ToggleButton)activity.findViewById(R.id.ToggleSchool);
        ToggleButton workBtn = (ToggleButton)activity.findViewById(R.id.ToggleWorkRel);
        ToggleButton exerciseBtn = (ToggleButton)activity.findViewById(R.id.ToggleExercise);
        ToggleButton socialBtn = (ToggleButton)activity.findViewById(R.id.ToggleSocial);
        ToggleButton shoppingBtn = (ToggleButton)activity.findViewById(R.id.ToggleShopping);
        ToggleButton errandBtn = (ToggleButton)activity.findViewById(R.id.ToggleErrand);
        ToggleButton otherBtn = (ToggleButton)activity.findViewById(R.id.ToggleOther);

        final EditText commentEt = (EditText)activity.findViewById(R.id.NotesField);

        Button discardBtn = (Button)activity.findViewById(R.id.ButtonDiscard);
        Button submitBtn = (Button)activity.findViewById(R.id.ButtonSubmit);

        if(commuteBtn!=null && schoolBtn!=null && workBtn!=null && exerciseBtn!=null && socialBtn!=null && shoppingBtn!=null && errandBtn!=null && otherBtn!= null){
	        pressAndCheckToogleButtonDescription(commuteBtn, "Commute", "Commute: this Running trip was primarily to get between home and your main workplace.");
	        pressAndCheckToogleButtonDescription(schoolBtn, "School", "School: this Running trip was primarily to go to or from school or college.");
	        pressAndCheckToogleButtonDescription(workBtn, "Work-Related", "Work-Related: this Running trip was primarily to go to or from a business related meeting, function, or work-related errand for your job.");
	        pressAndCheckToogleButtonDescription(exerciseBtn, "Exercise", "Exercise: this Running trip was primarily for exercise, or biking for the sake of biking.");
	        pressAndCheckToogleButtonDescription(socialBtn, "Social", "Social: this Running trip was primarily for going to or from a social activity, e.g. at a friend's house, the park, a restaurant, the movies.");
	        pressAndCheckToogleButtonDescription(shoppingBtn, "Shopping", "Shopping: this Running trip was primarily to purchase or bring home goods or groceries.");
	        pressAndCheckToogleButtonDescription(errandBtn, "Errand", "Errand: this Running trip was primarily to attend to personal business such as banking, a doctor visit, going to the gym, etc.");
	        pressAndCheckToogleButtonDescription(otherBtn, "Other", "Other: if none of the other reasons applied to this trip, you can enter comments below to tell us more.");
        }else{
        	assertTrue(false);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                commentEt.setText("New trip");
            }
        });
        Thread.sleep(2000);
        if(submitBtn!=null){
        	solo.clickOnView(submitBtn);
        }else{
        	assertTrue(false);
        }
        solo.waitForActivity(ShowMapActivity.class,2000);
        assertEquals("ShowMap activity is not displayed", "ShowMapActivity", solo.getCurrentActivity().getLocalClassName());
    }
    

  //Check that Discard Saving Trip functionality are working correctly
    public void test_step003_Discard() throws Exception {

        Thread.sleep(20000);
        solo.clickOnView(btnFinish);

        solo.waitForActivity(SaveTripActivity.class,2000);
        Activity activity = solo.getCurrentActivity();
        Thread.sleep(1000);
        ToggleButton socialBtn = (ToggleButton)activity.findViewById(R.id.ToggleSocial);

        Button discardBtn = (Button)activity.findViewById(R.id.ButtonDiscard);

        if(discardBtn!=null){
	        solo.clickOnView(discardBtn);
	        checkToast("Trip discarded");
        }else{
        	assertTrue(false);
        }
        solo.waitForActivity(HomeActivity.class, 2000);
        assertEquals("Home activity is not displayed", "HomeActivity", solo.getCurrentActivity().getLocalClassName());

    }

    //Check usual mode lifecycle
    public void test_step004_Usual() throws Exception {

        Thread.sleep(20000);
        solo.clickOnView(btnFinish);

        solo.waitForActivity(SaveTripActivity.class,2000);
        Activity activity = solo.getCurrentActivity();
        Button submitButton = null;
        ToggleButton socialButton = null;
        
        if(activity!=null){
	        submitButton = (Button)activity.findViewById(R.id.ButtonSubmit);
	        socialButton = (ToggleButton)activity.findViewById(R.id.ToggleSocial);
        }
        if(submitButton!=null && socialButton!=null){
	        pressAndCheckToogleButtonDescription(socialButton, "Social", "Social: this Running trip was primarily for going to or from a social activity, e.g. at a friend's house, the park, a restaurant, the movies.");
	
	        final EditText commentEt = (EditText)activity.findViewById(R.id.NotesField);
	        getActivity().runOnUiThread(new Runnable() {
	            @Override
	            public void run() {
	                commentEt.setText("Social trip");
	            }
	        });
	        
	        Thread.sleep(2000);
	        solo.clickOnView(submitButton);
	        solo.waitForActivity(ShowMapActivity.class, 2000);
	        assertEquals("ShowMap activity is not displayed", "ShowMapActivity", solo.getCurrentActivity().getLocalClassName());
        }

    }
    /**
     * Check Toast message appearing
     * @param toastMessage - text of the Toast message to find
     */
    private void checkToast(String toastMessage){
        assertTrue("Could not find the Toast message", solo.searchText(toastMessage));

        //wait for Toast to close
        solo.waitForDialogToClose(TOAST_CLOSE_TIME_OUT);
    }

    private void pressAndCheckToogleButtonDescription (ToggleButton button, String buttonName, String description) throws InterruptedException{
        assertNotNull(buttonName+" Toggle Button is not exist",button!=null);
        assertEquals(buttonName+" Toggle Button text is wrong",buttonName,button.getText().toString());
        solo.clickOnView(button);
        Thread.sleep(1000);
        TextView textPurpDescription = (TextView)solo.getCurrentActivity().findViewById(R.id.TextPurpDescription);
        assertEquals(buttonName+" Toggle Button description is wrong", description, textPurpDescription.getText().toString());
    }
    
}
