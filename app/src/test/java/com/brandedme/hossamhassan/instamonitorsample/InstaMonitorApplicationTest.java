package com.brandedme.hossamhassan.instamonitorsample;

import android.content.Intent;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.Button;

/**
 * Created by HossamHassan on 5/16/2016.
 */
public class InstaMonitorApplicationTest extends
        android.test.ActivityUnitTestCase<MainActivity> {
    public InstaMonitorApplicationTest(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    private MainActivity mainActivity;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        Intent intent = new Intent(getInstrumentation().getTargetContext(), MainActivity.class);
        startActivity(intent, null, null);
        mainActivity = getActivity();
    }

    @SmallTest
    public void testLayoutExists() {
        assertNotNull(mainActivity.findViewById(R.id.btnOpenSecondActivity));
        assertNotNull(mainActivity.findViewById(R.id.btnOpenFirstFragment));
        assertNotNull(mainActivity.findViewById(R.id.btnRestStates));

        Button btnOpenSecondActivity = (Button) mainActivity.findViewById(R.id.btnOpenSecondActivity);
        Button btnOpenFirstFragment = (Button) mainActivity.findViewById(R.id.btnOpenFirstFragment);
        Button btnRestStates = (Button) mainActivity.findViewById(R.id.btnRestStates);

    }

    @SmallTest
    public void testIntentTriggerViaOnClickBtnOpenSecondActivity() {
        Button btnOpenSecondActivity = (Button) mainActivity.findViewById(R.id.btnOpenSecondActivity);
        assertNotNull("Button should not be null ", btnOpenSecondActivity);
        btnOpenSecondActivity.performClick();
        Intent triggeredIntent = getStartedActivityIntent();
        assertNotNull("Intent should have triggered after btnOpen clicked ", triggeredIntent);


    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
