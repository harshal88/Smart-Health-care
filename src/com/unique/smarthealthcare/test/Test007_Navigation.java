package com.unique.smarthealthcare.test;

import android.graphics.Point;
import android.support.v4.widget.DrawerLayout;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.plus.model.people.Person;
import com.robotium.solo.Solo;
import com.unique.smarthealthcare.*;
import com.unique.smarthealthcare.R;

/**
 * Created by sshcherbanenko on 05.01.15.
 */
public class Test007_Navigation extends ActivityInstrumentationTestCase2<HomeActivity> {

    private final static int TOAST_CLOSE_TIME_OUT = 5000;
    private Solo solo;

    public Test007_Navigation() {
        super(HomeActivity.class);
    }

    //Setting Up tests
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());
    }

    //Opening Navigation Drawer by clicking on Action Bar left edge
    public void test1_NavigationDrawerActionBar() throws Exception {
    	
        solo.unlockScreen();
        if (solo.getCurrentActivity().getClass().equals(LoginActivity.class)) {
        	solo.enterText(0, "correctPassword");
    		solo.clickOnButton("Login");
		}
        solo.assertCurrentActivity("Couldn't login", HomeActivity.class);
        
        solo.setNavigationDrawer(Solo.OPENED);
        clickOnNavigationDrawerListItem(5);
        assertTrue("Navigation Drawer not opened", solo.searchText("Body Mass Index"));
    }

    //Opening Navigation Drawer by sliding from "left to right"
    public void test2_NavigationDrawerSlide() throws Exception {
        solo.unlockScreen();
        Point deviceSize = new Point();
        solo.getCurrentActivity().getWindowManager().getDefaultDisplay().getSize(deviceSize);
        int screenWidth = deviceSize.x;
        int screenHeight = deviceSize.y;
        int fromX = 0;
        int toX = screenWidth / 2;
        int fromY = screenHeight / 2;
        int toY = fromY;

        solo.drag(fromX, toX, fromY, toY, 1);

        clickOnNavigationDrawerListItem(5);
        assertTrue("Navigation Drawer not opened", solo.searchText("Body Mass Index"));
    }

    //Check, that Home item of Navigation Drawer is working correctly
    public void test3_ND_Home_Item() throws Exception {
        solo.unlockScreen();
        solo.clickOnText("BMI");
        assertTrue("BMI fragment not opened", solo.searchText("Body Mass Index"));

        solo.setNavigationDrawer(Solo.OPENED);
        clickOnNavigationDrawerListItem(0);

        assertTrue("Can't get to BMI fragment",
                solo.searchText("Home"));
    }

    //Check, that ListTrack item of Navigation Drawer is working correctly
    public void test4_ND_ListTrack_Item() throws Exception {
        solo.unlockScreen();

        solo.setNavigationDrawer(Solo.OPENED);
        clickOnNavigationDrawerListItem(1);
        boolean listTripsFound = solo.searchText("saved trips");
        if (!listTripsFound){
            assertTrue("Can't get to ListTrips fragment",
                    solo.searchText("Welcome"));
        } else {
            solo.goBack();
            assertTrue("Cen't get back to Home activity",
                    solo.searchText("Home"));
        }
    }

    //Check, that Graphs item of Navigation Drawer is working correctly
    public void test5_ND_Graphs_Item() throws Exception {
        solo.unlockScreen();

        solo.setNavigationDrawer(Solo.OPENED);
        clickOnNavigationDrawerListItem(2);
        assertTrue("You haven't get to Graphs fragment",
                solo.searchText("Distance v/s Time"));
        solo.goBack();

        assertTrue("You haven't get to BMI fragment",
                solo.searchText("Home"));
    }

    //Check, that Graphs item of Navigation Drawer is working correctly
    public void test6_ND_Compass_Item() throws Exception {
        solo.unlockScreen();

        solo.setNavigationDrawer(Solo.OPENED);
        clickOnNavigationDrawerListItem(3);
        assertTrue("You haven't get to Compass fragment",
                solo.searchText("Heading"));
        solo.goBack();

        assertTrue("You haven't get back to Home activity",
                solo.searchText("Home"));
    }

    //Check, that Graphs item of Navigation Drawer is working correctly
    public void test7_ND_StopWatch_Item() throws Exception {
        solo.unlockScreen();

        solo.setNavigationDrawer(Solo.OPENED);
        clickOnNavigationDrawerListItem(4);

        ImageButton startBtn = solo.getImageButton(0);
        assertEquals("You haven't get to StopWatch fragment",startBtn.getId(),getActivity().findViewById(R.id.bStart).getId());        ;
        solo.goBack();

        assertTrue("You haven't get back to Home activity",
                solo.searchText("Home"));
    }

    //Check, that BMI item of Navigation Drawer is working correctly
    public void test8_ND_BMI_Item() throws Exception {
        solo.unlockScreen();

        solo.setNavigationDrawer(Solo.OPENED);
        clickOnNavigationDrawerListItem(5);
        assertTrue("You haven't get to BMI fragment",
                solo.searchText("Ideal Weight"));
        solo.goBack();

        assertTrue("You haven't get back to Home activity",
                solo.searchText("Home"));
    }

    //Check, that toast appearing, when user press "Back" on HomeActivity
    public void test9_HomeActivityBack() throws Exception {
        solo.unlockScreen();
        solo.goBack();
        checkToast(getActivity().getString(R.string.please_click_back_again_to_exit));
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

    /**
     * Perform click on navigation drawer
     * @param position - an item to be pressed
     */
    private void clickOnNavigationDrawerListItem(int position){
        ListView drawerList = (ListView) solo.getView(com.unique.smarthealthcare.R.id.left_drawer);
        View listElement = drawerList.getChildAt(position);
        solo.clickOnView(listElement);
    }
    
}
