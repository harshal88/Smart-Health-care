package com.unique.smarthealthcare.test;

import junit.framework.Assert;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Instrumentation.ActivityMonitor;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotium.solo.Solo;
import com.unique.smarthealthcare.HomeActivity;
import com.unique.smarthealthcare.LoginActivity;
import com.unique.smarthealthcare.R;
import com.unique.smarthealthcare.RegisterActivity;
import com.unique.smarthealthcare.Splashscreen;
import com.unique.smarthealthcare.UserPrefActivity;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test013_HelpActivity extends ActivityInstrumentationTestCase2<HomeActivity> {

    private Solo solo;

    public Test013_HelpActivity() {
        super(HomeActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
    }

    public void test_step001_CheckHelpAndFAQ() throws InterruptedException {
    	solo.unlockScreen();    	        
    	solo.clickOnMenuItem("Help and FAQ");     
    }
    
    public void test_step001_SignOut() throws InterruptedException {
    	solo.unlockScreen();    	        
    	solo.clickOnMenuItem("Signout");
    }
           
}
