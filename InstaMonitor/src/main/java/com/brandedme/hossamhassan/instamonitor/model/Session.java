package com.brandedme.hossamhassan.instamonitor.model;

/**
 * Session model store session data name ,duration and shortName
 * Created by Hossam on 5/11/2016.
 */
public class Session {
    private String name,time,shortName;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * for setting the short name like simple name
     * example com.brandedme.hossamhassan.instamonitor.MainActivity will be MainActivity
     * @param name full class name as string
     */
    private void setShortName(String name)
    {
        //if name null return
        if (name==null)
            return;
        //if not null
        int pos = name.lastIndexOf ('.') + 1;
        shortName= name.substring(pos);
    }

    /**
     * Gets short name.
     *
     * @return the short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
        setShortName(name);
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }
}

