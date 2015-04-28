package com.unique.smarthealthcare.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.RadioButton;

import com.robotium.solo.Solo;
import com.unique.smarthealthcare.HomeActivity;
import com.unique.smarthealthcare.R;
import com.unique.smarthealthcare.UserPrefActivity;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test009_UserPrefActivity extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Activity uprefActivity;
    private EditText age;
    private EditText hZip, wZip, sZip;
    private EditText email;        
    private Solo solo;    

    public Test009_UserPrefActivity() {
        super(HomeActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
        solo.clickOnMenuItem("Edit User Info");
        solo.assertCurrentActivity("Not displaying Home Activity", UserPrefActivity.class);
        uprefActivity = solo.getCurrentActivity();
        
        age = (EditText)uprefActivity.findViewById(R.id.TextAge);
        hZip = (EditText)uprefActivity.findViewById(R.id.TextZipHome);
        wZip = (EditText)uprefActivity.findViewById(R.id.TextZipWork);
        sZip = (EditText)uprefActivity.findViewById(R.id.TextZipSchool);
        email = (EditText)uprefActivity.findViewById(R.id.TextEmail);
    }

    public void test_step001_UserDataEnter() throws InterruptedException {
    	solo.clearEditText(age);
    	solo.clearEditText(hZip);
    	solo.clearEditText(wZip);
    	solo.clearEditText(sZip);
    	solo.clearEditText(email);
    	solo.enterText(age, "27");
    	solo.enterText(hZip, "145");
    	solo.enterText(wZip, "123");
    	solo.enterText(sZip, "213");
    	solo.enterText(email, "mail@gmail.com");
    	solo.clickOnRadioButton(1);
        
    	solo.clickOnMenuItem("Save");
    }
    
    public void test_step002_UserDataCheck() throws InterruptedException {
    	solo.searchEditText("27");
    	solo.searchEditText("145");
    	solo.searchEditText("123");
    	solo.searchEditText("213");
    	solo.searchEditText("mail@gmail.com");

    	solo.clickOnMenuItem("Save");
    }
        
}
