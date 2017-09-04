# SapphyxAPI
The API that is used to build, run, and maintain the extended homescreens of Sapphyx Launcher. 
Sapphyx Launcher is one of the few launchers that has its own extended homescreen service or swipe to left screens. 
With the API, Developers can use this library to create pages for Sapphyx launcher that can acts a Interactable Widgets, Utilities, Exclusive Add-Ons, or a mirrored view of a certain activity or feature withen their application. 
Pages can be built by themselves without a main activity needed. 
No ads are to be put into any pages.

# Installation

Add Jitpack in your root build.gradle at the end of repositories:
```xml
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Add the dependancy
```xml
dependencies {
	        compile 'com.github.rgocal:SapphyxAPI:-SNAPSHOT'
	}
```

# Setup

In this example, we are going to create  2 classes. LauncherFragment (Name of the page fragment) and Utils. This example will create a page with a digital clock on it.

```xml
public class LauncherFragment extends BaseLauncherPage {

    public Context context;
    private ResourceHelper resHelper;
    private Utils utils;

    TextView mHour;
    TextView mMin;
    TextView mDay;

    // root view of the fragment
    private RelativeLayout rootView;
    private View background;

    @Override
    public BaseLauncherPage getFragment(int position) {
        return new LauncherFragment();
    }

    @Override
    public View[] getBackground() {
        return new View[]{background};
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        utils = new Utils(activity);
        resHelper = new ResourceHelper(activity, Utils.PACKAGE_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        rootView = (RelativeLayout) resHelper.getLayout("prem_layout");

        background = rootView.findViewById(resHelper.getId("background"));

        background.setBackgroundColor(sp.getInt("pref_staticBackground", Color.TRANSPARENT));

        mHour = rootView.findViewById(resHelper.getId("hr"));
        mMin = rootView.findViewById(resHelper.getId("min"));
        mDay = rootView.findViewById(resHelper.getId("day"));

        return rootView;
    }
}
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <View
        android:id="@+id/background"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/transparent" />

    <LinearLayout
        android:id="@+id/content"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:gravity="center"
        android:orientation="vertical">

        <TextClock
            android:id="@+id/hr"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:fontFamily="sans-serif-bold"
            android:format12Hour="hh"
            android:textColor="@color/white"
            android:textSize="68sp"
            android:textStyle="bold" />

        <TextClock
            android:id="@+id/min"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:fontFamily="sans-serif-thin"
            android:format12Hour="mm"
            android:textColor="@color/white"
            android:textSize="50sp"
            android:textStyle="normal" />

        <TextClock
            android:id="@+id/day"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center_horizontal"
            android:format12Hour="a -  MMM dd"
            android:textColor="@color/white"
            android:textSize="18sp"
            android:textStyle="normal" />

    </LinearLayout>


</RelativeLayout>
```

In your code utils class, make sure the package string matches exactly where your code is placed in your packages.

```xml
public class Utils {

    public static final String PACKAGE_NAME = "your.package.name";

    private Activity context;

    public Utils(Activity context) {
        this.context = context;
    }
}
```
Add the meta code to your manifest under application. The value is what you decided to call your LauncherFragment or page fragment. I have not tried multiple pages yet so you if you tried multiple pages, you would need a meta for each fragment. This meta data is read from Sapphyx Launchers Static Page activity. If the page returns blank on your homescreen, something is wrong.

```xml
<meta-data android:name="launcher_fragment"
            android:value=".LauncherFragment" />
```

This example should return a basic time module back to your page. I will push more examples more complex as I build them as examples.

# How It Works
The concept of this API is simple, We just want to replace Google's intention of Google Now in Pixel Launcher with our own concepts in Sapphyx Launcher. We can create our own objects to view and allow full customization. The Sapphyx Page runs on our own context.

Create an object from your class and parse the view with the api. From their, the launcher reads the code as a fragment and places the code in a viewpager (err Tabs) and the user views it like another page on their homescreen. We can have up to 5 in Sapphyx Launcher, you will be creating one of those five. This api is based on Luke Klinkers Blur API and the warning he has given us is this..."this isn't running in your apps context, it is running completely on ours, which can cause a few problems for things like shared preferences, accessing resources, accessing your cache, or any of your databases. We just wanted to make it clear that this isn't quite like DashClock which is able to broadcast updates from inside your own app. These extentions have to run within our app or they just do not work. Nougat locked down some permissions, especially those around the shared preferences, which denied global accessiibility." Simply put, anything that is trying to pull data directly from an app package is not going to work well and you are going to have to find ways around this or work with me to improve the API. 

# SharedPreferences
Aside from tapping into the api to create pages, Sapphyx Launcher offers some SharedPreferences to customize the page with color options if your page does not have a settings activity. After you have set SharedPrefences to your views, the user can than customize the color options via the Static Pages preferences. Ive started working on a SettingsKey Interface but it isn't functional yet to store shared preferences accessible by Sapphyx Static Pages preferences.

```xml
-Primary Color = android:key="pref_staticPrimary"

-PrimaryDark Color = android:key="pref_staticPrimaryDark"

-Accent Color = android:key="pref_staticAccent"

-Page Background Color = android:key="pref_staticBackground"
```

In your LauncherFragment (or page) class add SharedPreferences...

```xml
SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
```

Tap into the SharedPreferences key...

```xml
background.setBackgroundColor(sp.getInt("pref_drawerBackground", Color.WHITE));
```
