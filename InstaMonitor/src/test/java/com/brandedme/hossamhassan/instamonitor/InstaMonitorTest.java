package com.brandedme.hossamhassan.instamonitor;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class InstaMonitorTest {
    /**
     * expected equals INSTA_MONITOR
     */
    @Test
    public void getTagTestCorrect()
    {
        assertThat(InstaMonitor.getTAG(),equalTo("INSTA_MONITOR"));
    }

    /**
     * expected not equal ANY_TAG
     */
    @Test
    public void getTagTestWrong()
    {
        assertThat(InstaMonitor.getTAG(),not(equalTo("ANY_TAG")));
        //failNotEquals("Failed ",InstaMonitor.getTAG(),equalTo("ANY_TAG"));
    }


    /**
     * expected   getInstance not null
     */
    @Test
    public void instaObjectIsNotNull()
    {
        assertNotNull(InstaMonitor.getInstance());
    }

    /**
     *
     */
    @Test
    public void instaObjectSameObject()
    {
        assertThat("same instance",InstaMonitor.getInstance(),equalTo(InstaMonitor.getInstance()));
        assertThat("equalTo ",InstaMonitor.getInstance(),sameInstance(InstaMonitor.getInstance()));
        assertThat("is ",InstaMonitor.getInstance(),is(InstaMonitor.getInstance()));
        assertThat("is equalsTo  ",InstaMonitor.getInstance(),is(equalTo(InstaMonitor.getInstance())));
    }

}