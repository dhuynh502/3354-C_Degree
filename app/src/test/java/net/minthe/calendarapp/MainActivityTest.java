package net.minthe.calendarapp;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;
/*
  Class test the buttons for functionanility
  so far we are testing the CustomMonthView Button
 */
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(CustomMonthView.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }
    @Test
    public void testLaunchOfCustomMonthView(){
        assertNotNull(mActivity.findViewById(R.id.customMonthButton));
        onView(withId.(R.id.customMonthButton)).perform(click());

        Activity customMonthView = getInstrumentation().waitForMonitorWithTimeout(monitor,4000);
        asserNotNull(customMonthView);
    }
    @After
    public void tearDown() throws Exception {
    }
}