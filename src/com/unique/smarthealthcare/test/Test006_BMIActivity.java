package com.unique.smarthealthcare.test;


import android.app.Activity;
import android.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.robotium.solo.Solo;
import com.unique.smarthealthcare.BMIFragmentActivity;
import com.unique.smarthealthcare.HomeActivity;
import com.unique.smarthealthcare.LoginActivity;
import com.unique.smarthealthcare.R;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test006_BMIActivity extends ActivityInstrumentationTestCase2<HomeActivity> {
    
    private final static int TOAST_CLOSE_TIME_OUT = 5000;
    private Activity fragmentActivity;
    private Solo solo;
    private EditText weightEditText;
    private EditText heightEditText;
    private EditText resultEditText;
    private Button calculateBMIButton;
    private Button idealWeightButton;
    private Button clearButton;
    private Spinner weightSpinner;
    private Spinner heightSpinner;

    public Test006_BMIActivity() {
        super(HomeActivity.class);
    }

    //Setting Up tests
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
            
        solo.unlockScreen();
        solo.clickOnText("BMI");        
        solo.waitForFragmentById(R.layout.fragment_bmi, 2000);
        fragmentActivity = solo.getCurrentActivity();
        
        weightEditText = (EditText)fragmentActivity.findViewById(R.id.txtbWeight);
        heightEditText = (EditText)fragmentActivity.findViewById(R.id.txtHeight);
        resultEditText = (EditText)fragmentActivity.findViewById(R.id.bmi);
        calculateBMIButton = (Button)fragmentActivity.findViewById(R.id.btnCalculate);
        idealWeightButton = (Button)fragmentActivity.findViewById(R.id.btnCalcIdeal);
        clearButton = (Button)fragmentActivity.findViewById(R.id.btnClear);
        weightSpinner = (Spinner)fragmentActivity.findViewById(R.id.weight_unit);
		heightSpinner = (Spinner)fragmentActivity.findViewById(R.id.height_unit);        
    }

    //Check that all objects in BMI fragment are exist
    public void test_step001_ObjectsExists() throws Exception {
 
        assertEquals("", weightEditText.getText().toString());
        assertEquals("", heightEditText.getText().toString());
        assertEquals("", resultEditText.getText().toString());
        
        assertTrue("BMI text label not displayed", solo.searchText("Body Mass Index"));
        assertTrue("Weight text label not displayed", solo.searchText("Weight"));
        assertTrue("Height text label not displayed", solo.searchText("Height"));

        assertEquals("Kg", weightSpinner.getItemAtPosition(weightSpinner.getSelectedItemPosition()).toString());
        assertEquals("M", heightSpinner.getItemAtPosition(heightSpinner.getSelectedItemPosition()).toString());
    }

    //Check that all BMI fragment buttons exists and clickable
    public void test_step002_ButtonsClickable(){
    	assertNotNull("RegisterButton is null", calculateBMIButton);
        assertTrue("Calculate button not clickable", calculateBMIButton.isClickable());
        assertNotNull("RegisterButton is null", idealWeightButton);
        assertTrue("Ideal Weight button not clickable", idealWeightButton.isClickable());
        assertNotNull("RegisterButton is null", clearButton);
        assertTrue("Clear button not clickable", clearButton.isClickable());
    }

    //Check that "Calculate BMI" functionality is working correctly
    public void test_step003_CalculateBMI() throws Exception {
        solo.enterText(0, "90");
        solo.enterText(1, "1.8");
        solo.clickOnButton(calculateBMIButton.getText().toString());
        assertTrue("BMI calculations is wrong", solo.searchText("27.78"));
    }

    //Check that "Calculate BMI" functionality is working correctly with different settings
    public void test_step004_WorkingWithSpinners() throws Exception {
    	solo.enterText(weightEditText, "90");
    	solo.enterText(heightEditText, "1.8");
        solo.pressSpinnerItem(0, 2);
        solo.pressSpinnerItem(1, 2);

        solo.clickOnButton(calculateBMIButton.getText().toString());
        assertTrue("BMI calculation is wrong", solo.searchText("135.62"));
    }

    //Check that "Ideal Weight" functionality is working correctly
    public void test_step005_IdealWeight() throws Exception {
        solo.enterText(weightEditText, "65");
    	solo.enterText(heightEditText, "1.8");
        
        solo.clickOnButton(idealWeightButton.getText().toString());
        assertTrue("BMI calculation is wrong", solo.searchText("59.62kg"));
    }

    //Check that "Clear" functionality is working correctly
    public void test_step006_Clear() throws Exception {
        solo.enterText(weightEditText, "90");
    	solo.enterText(heightEditText, "1.8");
        
        solo.clickOnButton(clearButton.getText().toString());
        EditText txtWeight = solo.getEditText(0);
        EditText txtHeight = solo.getEditText(1);
        assertTrue("Clear Button is not working correctly", txtWeight.getText().toString().equals(""));
        assertTrue("Clear Button is not working correctly", txtHeight.getText().toString().equals(""));
    }

    //Check that empty fields toasts are showing correctly
    public void test_step007_EmptyFields() throws Exception {
    	solo.enterText(weightEditText, "65");
    	solo.clickOnButton(calculateBMIButton.getText().toString());
        checkToast("You have not entered a value for the Height field");
        solo.clickOnButton(clearButton.getText().toString());
        
        solo.enterText(heightEditText, "1.8");
        solo.clickOnButton(idealWeightButton.getText().toString());
        checkToast("You have not entered a value for the Weight field");
    }

    //Check that "0" fields toasts are showing correctly
    public void test_step008_FieldsWith0() throws Exception {
    	solo.enterText(weightEditText, "0");
    	solo.enterText(heightEditText, "1.5");
        solo.clickOnButton(calculateBMIButton.getText().toString());
        checkToast("You have specified an incorrect weight. Weight must be more that 1kg");
        solo.pressSpinnerItem(0, 2);
        solo.clickOnButton(idealWeightButton.getText().toString());
        checkToast("You have specified an incorrect weight. Weight must be more that 1lbs");
        solo.clickOnButton("Clear");

        solo.enterText(weightEditText, "70");
    	solo.enterText(heightEditText, "0");
        solo.clickOnButton(calculateBMIButton.getText().toString());
        checkToast("You have specified an incorrect height. Height must be more that 1m");
        solo.pressSpinnerItem(1, 2);
        solo.clickOnButton(idealWeightButton.getText().toString());
        checkToast("You have specified an incorrect height. Height must be more that 1ft");
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
    
}
