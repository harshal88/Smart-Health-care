package com.unique.smarthealthcare.test;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.InstrumentationTestCase;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.robotium.solo.Condition;
import com.robotium.solo.Solo;
import com.unique.smarthealthcare.R;
import com.unique.smarthealthcare.Splashscreen;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test002_Splashscreen extends ActivityUnitTestCase<Splashscreen> {

	private Instrumentation mInstrumentation;
    private Splashscreen splashScreenActivity;
    private ImageView animationContainer;
    private TextView bottomTextView;
    private TextView topTextView; 
    private Solo solo;
    
    public Test002_Splashscreen() {
        super(Splashscreen.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        
        Intent intent = new Intent(getInstrumentation().getTargetContext(), Splashscreen.class);
        startActivity(intent, null, null);
        
        mInstrumentation = getInstrumentation();
        splashScreenActivity = getActivity();
        
        animationContainer = (ImageView)splashScreenActivity.findViewById(R.id.running_athlete);
        bottomTextView = (TextView)splashScreenActivity.findViewById(R.id.textView2);
        topTextView = (TextView)splashScreenActivity.findViewById(R.id.textView1);
        
        solo = new Solo(getInstrumentation(), getActivity());                
    }

    //Check that activity appear
    public void test_step1_ActivityAppear() throws InterruptedException {
    	solo.unlockScreen();
        assertNotNull("Splashscreen activity is null", splashScreenActivity);
    }

    //Check that animation is showed
    public void test_step2_AnimationShowed(){        
        assertNotNull("Animation is null", animationContainer);                
    }
    
    //Bottom text appear and it correspondence
    public void test_step3_BottomText(){   	
        assertNotNull("Bottom text is null", bottomTextView);
        assertEquals("Bottom text correspondence is wrong",splashScreenActivity.getString(R.string.dby),bottomTextView.getText().toString());
    }
    
    //Top text appear and it correspondence
    public void test_step4_TopText(){
        assertNotNull("Top text is null", topTextView);
        assertEquals("Top text correspondence is wrong",splashScreenActivity.getString(R.string.intro),topTextView.getText().toString());
    }
    
}
