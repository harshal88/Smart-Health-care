package com.unique.smarthealthcare.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;
import com.robotium.solo.Solo;
import com.unique.smarthealthcare.*;
import com.unique.smarthealthcare.R;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test005_Compass extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;
    private Activity compassActivity;
    private TextView txtHeading;
    private ImageView ivCompass;

    public Test005_Compass() {
        super(HomeActivity.class);
    }

    //Setting Up tests
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
        
        solo.unlockScreen();
        if (solo.getCurrentActivity().getClass().equals(LoginActivity.class)) {
        	solo.enterText(0, "correctPassword");
    		solo.clickOnButton("Login");
		}

        solo.assertCurrentActivity("Couldn't login", HomeActivity.class);
        solo.clickOnText("Compass");        
        solo.waitForFragmentById(R.layout.fragment_compass, 2000);
        compassActivity = solo.getCurrentActivity();

        txtHeading = (TextView)compassActivity.findViewById(R.id.tvHeading);
        ivCompass = (ImageView) compassActivity.findViewById(R.id.imageViewCompass);

    }

    //Check that Heading text in Compass fragment are exist and correct
    public void test1_Heading() throws Exception {
        assertNotNull("Heading text is null", txtHeading);
        assertTrue("Heading text is incorrect",txtHeading.getText().toString().contains("Heading:"));
    }

    //Check that Compass ImageView in Compass fragment are exist
    public void test2_Compass() throws Exception {
        assertNotNull("Compass ImageView is null", ivCompass);
    }
    
}
