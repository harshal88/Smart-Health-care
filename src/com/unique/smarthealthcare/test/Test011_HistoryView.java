package com.unique.smarthealthcare.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ListView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.robotium.solo.Solo;
import com.unique.smarthealthcare.*;
import com.unique.smarthealthcare.R;

public class Test011_HistoryView  extends ActivityInstrumentationTestCase2<HomeActivity> {

	private ListView tripList;
    private Activity activity;
    private Solo solo;
    private final static int TOAST_CLOSE_TIME_OUT = 5000;
    public Test011_HistoryView() {
        super(HomeActivity.class);
    }

    //Setting Up tests
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(),getActivity());

        solo.unlockScreen();
        solo.clickOnText("History");
        solo.waitForFragmentById(R.layout.fragment_list_tracks, 2000);
        activity = solo.getCurrentActivity();
        tripList = (ListView)activity.findViewById(android.R.id.list);
    }

    //Check that saved trip can be opened
    public void test_step001_ObjectsExist() throws Exception {
        solo.clickInList(1);
        solo.waitForActivity(ShowMapActivity.class,2000);
        assertEquals("ShowMap activity is not displayed", "ShowMapActivity", solo.getCurrentActivity().getLocalClassName());

        Activity activityShowMap = solo.getCurrentActivity();
        MapFragment mapFragment = (MapFragment)activityShowMap.getFragmentManager().findFragmentById(
                R.id.map);
        GoogleMap map = mapFragment.getMap();
        assertNotNull("Map is not exist",map!=null);
    }

    //Check that saved trip can be deleted
    public void test_step002_Deleting() throws Exception {
        solo.clickLongInList(1);
        int beforeCount = tripList.getCount();
        assertTrue("Context Menu is not opened", solo.searchText("Delete the record"));

        solo.clickOnText("Delete the record");
        assertTrue("Delete Dialog is not opened", solo.searchText("Are you sure"));
        solo.clickOnText("NO");
        int afterCount = tripList.getCount();
        assertEquals("Track was deleted",beforeCount,afterCount);
        
        solo.clickLongInList(1);
        solo.clickOnText("Delete the record");
        solo.clickOnText("YES");
        checkToast("Record is deleted");

        afterCount = tripList.getCount();
        assertEquals("Track was not deleted",beforeCount,afterCount+1);
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
