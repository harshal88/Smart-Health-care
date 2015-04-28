package com.unique.smarthealthcare.test;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;
import com.unique.smarthealthcare.HomeActivity;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test012_ChangePassActivity extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public Test012_ChangePassActivity() {
        super(HomeActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void test_step001_ChangePassword() throws InterruptedException {
    	solo.unlockScreen();    	
    	solo.clickOnMenuItem("Change Password");
    	
    	solo.searchText("Change Password");
    	
    	solo.enterText(0, "correctPassword");
    	solo.enterText(1, "correctPassword2");
    	solo.enterText(2, "correctPassword2");
    	solo.clickOnButton("Cancel");
    	
    	solo.clickOnMenuItem("Change Password");
    	solo.enterText(0, "correctPassword");
    	solo.enterText(1, "correctPassword");
    	solo.enterText(2, "correctPassword");
    	solo.clickOnButton("Change Password");
    }
    
}
