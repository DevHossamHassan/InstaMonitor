package com.brandedme.hossamhassan.instamonitor;

/**
 * Session model store session data name ,duration and shortName
 * Created by Hossam on 5/11/2016.
 */
public class Session {
    private String name,time,shortName;

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

    public String getShortName() {
        return shortName;
    }

    public void setName(String name) {
        this.name = name;
        setShortName(name);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

