# InstaMonitor
InstaMonitor is an Android library that can provide the following for any application:
1. Period of time for which the application was open 
2. Period of time spent on each Activity 
3. Period of time spent on each Fragment 
4. Include reset function to delete all saved stats 
5. Include ignore(Activity) to ignore speciﬁc activities from tracking 
6. Use local storage to store the results across application sessions


 Let's get started:
 
 # Usage
   just one line of code to add to your Application class:
        //init InstaMonitor
        InstaMonitor.getInstance().init(this);
        
        and now feel free to get the data anytime and  anywhere.
                    ArrayList<Session> sessionsList;
                    sessionsList = InstaMonitor.getInstance().getMonitorData();



# Setup
  Including in your project via :

  gradle :
  compile 'com.brandedme.hossamhassan.InstaMonitor:InstaMonitor:1.0.2'
  
Or
   Maven :
<dependency>
  <groupId>com.brandedme.hossamhassan.InstaMonitor</groupId>
  <artifactId>InstaMonitor</artifactId>
  <version>1.0.2</version>
  <type>pom</type>
</dependency>
