# InstaMonitor [![Release](https://jitpack.io/v/DevHossamHassan/InstaMonitor.svg)](https://jitpack.io/#DevHossamHassan/InstaMonitor)
InstaMonitor is an Android library that can provide the following for any application:

1. Period of time for which the application was open 
2. Period of time spent on each Activity 
3. Period of time spent on each Fragment 
4. Include reset function to delete all saved stats 
5. Include ignore(Activity) to ignore speciﬁc activities from tracking 
6. Use local storage to store the results across application sessions


# Let's get started:
 
# Usage

  just one line of code to add to your Application class:
   ```java
                 InstaMonitor.getInstance().init(this);
   ```

        
  and now feel free to get the data anytime and  anywhere.
   ```java
                    ArrayList<Session> sessionsList;
                    sessionsList = InstaMonitor.getInstance().getMonitorData();
   ```
  for tracking your fragments usage 
  
      just extend InstaFragment    if you plan to extend Fragment from android.app.Fragment
      or          InstaFragmentS4  for Fragment from android.support.v4.app.Fragment
      


# Setup
  Including in your project via :

   gradle :
   ```gradle
                   compile 'com.github.DevHossamHassan:InstaMonitor:1.3.0'
   ```
  
Add this to your root `build.gradle` file (**not** your module `build.gradle` file) :
    
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }

#Full Example 
   you can take a look at  [InstaMonitorSample2] (https://github.com/DevHossamHassan/InstaMonitorSample2) application 
   
   
   step by step tutorials :
   
  1- Configure InstaMonitor,
      your application class should look like:
  ```java
      public class YourApplication extends Application {
           @Override
           public void onCreate() {
                          super.onCreate();
        
                          //Init InstaMonitor
                          InstaMonitor.getInstance().init(this);
        
                          //Enable debug mode, false by default.
                          InstaMonitor.setEnableDebugMode(true);
        
                          //Ignore tracking for a particular Activity
                          InstaMonitor.ignoreActivity(SecondActivity.class);
           }
     }
   ```

  2- Get The Sessions Data anywhere and anytime,
   using one line of code 
   ```java
                        ArrayList<Session> sessionsList = InstaMonitor.getInstance().getMonitorData();
   ```
   and you can reset all sessions states using resetSessionsState method 
   ```java
                        InstaMonitor.getInstance().resetSessionsState();
   ```
 see the full example you would create something like this,
        in your Activity onCreate 
        
 ```java
         public class YourActivity extends AppCompatActivity {
            //sample textView 
            TextView tvData;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_second);
                      //bind the textView 
                      tvData=(TextView)findViewById(R.id.tvDataMap);
                      //get Sessions And set the data to Text view
                      setDataToView();


              }
       void setDataToViews() {
            ArrayList<Session> sessionsList = InstaMonitor.getInstance().getMonitorData();
                 if (sessionsList != null) {
                       for (Session session : sessionsList) {
                       String data = tvData.getText() + session.getShortName() + "\t" + session.getTime()+"\n";
                       tvData.setText(data);
                       }
                  } else
                        tvData.setText("Null");
                        }
             }
      
```