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

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test003_RegisterActivity extends ActivityInstrumentationTestCase2<RegisterActivity> {

	private final static int TIME_OUT = 3000; 
	private final static int TOAST_CLOSE_TIME_OUT = 5000;
	private Instrumentation mInstrumentation;
    private RegisterActivity registerActivity;
    private EditText passwordEditText;
    private EditText verifyPasswordEditText;
    private EditText securityHintEditText;
    private Button registerButton;
    private CheckBox showPassword;
    private ImageView animation;
    private Solo solo;

    public Test003_RegisterActivity() {
        super(RegisterActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        registerActivity = getActivity();
        solo = new Solo(getInstrumentation(),getActivity());
        mInstrumentation = getInstrumentation();
        registerButton = (Button)registerActivity.findViewById(R.id.reg_btn);
        
        animation = (ImageView)registerActivity.findViewById(R.id.running_athlete);
        passwordEditText = (EditText)registerActivity.findViewById(R.id.password_edt);
        verifyPasswordEditText = (EditText)registerActivity.findViewById(R.id.repassword_edt);
        securityHintEditText = (EditText)registerActivity.findViewById(R.id.security_edt);
        showPassword = (CheckBox)registerActivity.findViewById(R.id.checkBox1);
                
    }

    //Check that password edit text exist and "Password" hint is in edit text
    public void test_step001_PasswordET() throws InterruptedException {
    	solo.unlockScreen();
            	
    	assertNotNull("Password field activity is null", passwordEditText);
        assertEquals("Password field hint correspondence is wrong", "Password", passwordEditText.getHint().toString());
    }
    
    //Check that verify password edit text exist and "Verify password" hint is in edit text
    public void test_step002_VerifyPasswordET() throws InterruptedException {
    	assertNotNull("Password field activity is null", verifyPasswordEditText);
        assertEquals("Password field hint correspondence is wrong", "Verify Password", verifyPasswordEditText.getHint().toString());
    }
    
  //Check that security hint edit text exist and "Security Hint" hint is in edit text
    public void test_step003_SecyrityHintET() throws InterruptedException {
    	assertNotNull("Password field activity is null", securityHintEditText);
        assertEquals("Password field hint correspondence is wrong", "Security Hint", securityHintEditText.getHint().toString());
    }
    
  //Check that show me password check box exist and "Show me password" hint is in edit text
    public void test_step004_ShowPasswordCB() throws InterruptedException {
    	assertNotNull("Password field activity is null", showPassword);
        assertEquals("Password field hint correspondence is wrong", "Show me password", showPassword.getText().toString());
    }
    
  //Check that "Register" button exist and clickable
    public void test_step005_RegisterButtonExists(){
        assertNotNull("RegisterButton is null", registerButton);
        assertTrue("RegisterButton isn't clickable", registerButton.isClickable());
    }
    
  //Test negative/wrong values handling of the "Password" field (edit text)
    public void test_step006_EmptyAllFields(){    	        	
    	solo.clickOnButton(registerButton.getText().toString());
    	solo.waitForText("Please, fill all fields");
    }

    public void test_step007_EmptyTwoFields(){    	        	
    	solo.enterText(passwordEditText, "1");
    	solo.clickOnButton(registerButton.getText().toString());
    	solo.waitForText("Please, fill all fields");
    	
    	solo.enterText(verifyPasswordEditText, "2");
    	solo.clickOnButton(registerButton.getText().toString());
    	solo.waitForText("Please, fill all fields");
    	
    	solo.enterText(securityHintEditText, "3");
    	solo.clickOnButton(registerButton.getText().toString());
    	solo.waitForText("Passwords do not match");
    }
    
    public void test_step008_FieldsFilledByUnusualSymbols(){
    	solo.enterText(passwordEditText, "~!@#$%^&*'");
    	solo.enterText(verifyPasswordEditText, "~!@#$%^&*'");
    	solo.enterText(securityHintEditText, "~!@#$%^&*'");
    	solo.clickOnButton(registerButton.getText().toString());
    	solo.waitForText("Account successfully created");
    	
    	solo.goBack();
    }
   /*
    public void test_step009_PasswordsHidingByCheckBox(){
    	solo.enterText(passwordEditText, "correctPassword");
    	solo.enterText(verifyPasswordEditText, "correctPassword");    	    	
    	
    	solo.clickOnCheckBox(0);
    	solo.sleep(1500);
    	assertTrue(passwordEditText.getTransformationMethod().getClass().equals(HideReturnsTransformationMethod.getInstance().getClass()));
    	assertTrue(verifyPasswordEditText.getTransformationMethod().getClass().equals(HideReturnsTransformationMethod.getInstance().getClass()));
    	
    	solo.clickOnCheckBox(0);
    	solo.sleep(2000);
    	assertTrue(!passwordEditText.getTransformationMethod().getClass().equals(HideReturnsTransformationMethod.getInstance().getClass()));
    	assertTrue(!verifyPasswordEditText.getTransformationMethod().getClass().equals(HideReturnsTransformationMethod.getInstance().getClass()));
 
    	solo.goBack();
    }
   
    
  //Test "Register" button functionality
    public void test_step010_EnterValuesForFurtherTesting(){                        
    	//adding test data for further testing  
        solo.enterText(passwordEditText, "correctPassword");
    	solo.enterText(verifyPasswordEditText, "correctPassword");
    	solo.enterText(securityHintEditText, "correctHint");
		solo.clickOnButton(registerButton.getText().toString());	
		solo.goBack();
    }
    
    */
    
    /**
     * Check Toast message appearing
     * @param toastMessage - text of the Toast message to find
     */
    private void checkToast(String toastMessage){
    	assertTrue(solo.waitForDialogToOpen(TIME_OUT*2));
        if(solo.getText(0) != null){
        	assertTrue("Could not find the Toast message", solo.searchText(toastMessage));
        }
        //wait for Toast to close
        solo.waitForDialogToClose(TOAST_CLOSE_TIME_OUT);
    }
    
}
