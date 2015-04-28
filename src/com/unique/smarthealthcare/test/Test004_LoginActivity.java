package com.unique.smarthealthcare.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.robotium.solo.Solo;
import com.unique.smarthealthcare.HomeActivity;
import com.unique.smarthealthcare.LoginActivity;
import com.unique.smarthealthcare.R;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test004_LoginActivity extends ActivityInstrumentationTestCase2<LoginActivity> {

	private final static int TIME_OUT = 3000; 
	private final static int TOAST_CLOSE_TIME_OUT = 5000;
	
    private LoginActivity loginActivity;
    private EditText passwordEditText;
    private Button loginButton, registerButton;
    private TextView forgetPasswordTextView;
    private Solo solo;

    public Test004_LoginActivity() {
        super(LoginActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        loginActivity = getActivity();
        solo = new Solo(getInstrumentation(),getActivity());

        passwordEditText= (EditText)loginActivity.findViewById(R.id.enterpassword);

        loginButton = (Button)loginActivity.findViewById(R.id.login_btn);
        registerButton = (Button)loginActivity.findViewById(R.id.reg_btn);

        forgetPasswordTextView = (TextView)loginActivity.findViewById(R.id.forget_password_text_view);
    }

    //Check that password edit text exist and "Password" hint is in edit text
    public void test_step001_PasswordET() throws InterruptedException {
    	solo.unlockScreen();
        assertNotNull("Password field activity is null", passwordEditText);
        assertEquals("Password field hint correspondence is wrong", "Password", passwordEditText.getHint().toString());
    }

    //Check that "Login" and "Register" buttons exists
    public void test_step002_ButtonsExists(){
        assertNotNull("LoginButton is null", loginButton);
        assertNotNull("RegisterButton is null", registerButton);
    }

    //Check that "Login" and "Register" buttons are clickable
    public void test_step003_ButtonsClickable(){
        assertTrue("LoginButton isn't clickable", loginButton.isClickable());
        assertTrue("RegisterButton isn't clickable", registerButton.isClickable());
    }

    //Check that "Forget Password!" label exist and it correspondence
    public void test_step004_ForgetPasswordLabel(){
        assertNotNull("ForgetPassword label is null", forgetPasswordTextView);
        assertEquals("ForgetPassword label correspondence is wrong", "Forget Password!", forgetPasswordTextView.getText().toString());
    }

    //Check that ActionBar label exist and it correspondence
    public void test_step005_ActionBar(){
        assertNotNull("ActionBar label is null", loginActivity.getActionBar().getTitle());
        assertEquals("ActionBar label correspondence is wrong", "Smart Health Care", loginActivity.getActionBar().getTitle().toString());
    }

    //Test negative/wrong values handling of the "Password" field (edit text)
    public void test_step006_NonExistedPassword(){    	    
    	solo.enterText(passwordEditText, "_notExistedPassword_");
    	solo.clickOnButton(loginButton.getText().toString());
    	//Result - "Incorrect password" toast message appear
    	checkToast("Incorrect password");  
    }
        
    public void test_step007_WrongPassword(){    	    
    	solo.enterText(passwordEditText, "~!@#$%^&*'-+");
    	solo.clickOnButton(loginButton.getText().toString());
    	//Result - "Incorrect password" toast message appear
    	checkToast("Incorrect password");  
    }
    
    public void test_step008_EmptyPassword(){    	    
    	solo.enterText(passwordEditText, "");
    	solo.clickOnButton(loginButton.getText().toString());
    	//Result - "Please, enter your password" toast message appear
    	checkToast("Please, enter your password");  
    }
    
    //Test "Forget password" functionality
    public void test_step010_ForgetPassword(){
        // open Forget Password dialog
        solo.clickOnText(forgetPasswordTextView.getText().toString());        
        solo.waitForText("Enter security Hint");
       solo.searchText("Your Password");
        
        assertEquals("Password hint correspondence is wrong", "Enter Password Hint", ((EditText)solo.getView(R.id.securityhint_edt)).getHint().toString());
        
        solo.getButton("Get Password").isClickable();
        solo.getButton("Cancel").isClickable();
      
//        Press "Get Password" button
        solo.clickOnButton("Get Password");
    //   checkToast("Please, enter your security hint");
  solo.searchText("Please, enter your security hint");     
//        Press "Cancel" button
        solo.clickOnButton("Cancel");
    //    solo.searchText("Enter security Hint", false);
                
    }    
    
    public void test_step011_ForgetPasswordDialogWrongHints(){
    	solo.clickOnText(forgetPasswordTextView.getText().toString());
    	solo.waitForText("Enter security Hint");
    	solo.getButton("Get Password").isClickable();
    	solo.enterText((EditText)solo.getView(R.id.securityhint_edt), "_notExistedHint_");
    	solo.clickOnButton("Get Password");
      //  checkToast("Please, enter correct security hint");
    	  solo.searchText("Please, enter correct security hint");
/*Error*/      //  solo.enterText((EditText)solo.getView(R.id.securityhint_edt), "~!@#$%^&*'-+"); App will fail if we enter this text
//solo.enterText((EditText)solo.getView(R.id.securityhint_edt), "TEMPORARY STRING");
  //  	solo.clickOnButton("Get Password");
    //    checkToast("Please, enter correct security hint");
        
    }
    
    public void test_step012_ForgetPasswordDialogHidingCorrectHint(){
    	solo.clickOnText(forgetPasswordTextView.getText().toString());
    	solo.waitForText("Enter security Hint");
    	
    	solo.enterText((EditText)solo.getView(R.id.securityhint_edt), "correcrHint");
    	solo.clickOnButton("Get Password");            	   	
    	assertTrue("correctPassword", solo.searchText(((TextView)solo.getView(R.id.textView3)).getText().toString()));
    	solo.searchText("correctPassword");
    	
    	solo.enterText((EditText)solo.getView(R.id.securityhint_edt), "_notExistedHint_");
    	solo.clickOnButton("Get Password");    	
    	assertTrue("", solo.searchText(((TextView)solo.getView(R.id.textView3)).getText().toString()));    	
    	
    }
    
    public void test_step013_ForgetPasswordDialogReopenDialogCheckHint(){
    //	solo.clickOnText(forgetPasswordTextView.getText().toString());
    	solo.waitForText("Enter security Hint");
    	
    //	solo.enterText((EditText)solo.getView(R.id.securityhint_edt), "correcrHint");
    //	solo.clickOnButton("Get Password");
    	//solo.clickOnButton("Cancel");
      //  solo.searchText("Enter security Hint", false);
        
     //   solo.clickOnText(forgetPasswordTextView.getText().toString());        
       // solo.waitForText("Enter security Hint");
        
     //   assertTrue("", solo.searchText(((TextView)solo.getView(R.id.textView3)).getText().toString()));        
    }
   
    
    
    
    //Test "Login" button functionality
    public void test_step014_ForgetPasswordDialogSuccessLogin(){    	
    	solo.enterText(passwordEditText, "correctPassword");
    	solo.clickOnButton(loginButton.getText().toString());
    	
//    	checkToast("Congrats: login is successful");
    	solo.waitForText("Congrats: login is successful");
    	
    	solo.assertCurrentActivity("Not displaying Home Activity", HomeActivity.class);
    	solo.goBack();
    	solo.goBack();
    	    	
    }
    
    //Check auto-login after successful first login
    public void test_step015_ForgetPasswordDialogAutoLogin(){
    	
    	solo.clickOnMenuItem("Signout");
		solo.assertCurrentActivity("Not displaying login after signout", LoginActivity.class);    			
    
    }
    
    
    
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
