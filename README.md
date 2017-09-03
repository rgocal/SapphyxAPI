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

In your application package, we will be creating 3 classes. A LauncherFragment, A Scale Utility and a general Utils class.

# How It Works
The concept of this API is simple, We just want to replace Google's intention of Google Now in Pixel Launcher with our own concepts in Sapphyx Launcher. We can create our own objects to view and allow full customization. The Sapphyx Page runs on our own context.

Create an object from your class and parse the view with the api. From their, the launcher reads the code as a fragment and places the code in a viewpager (err Tabs) and the user views it like another page on their homescreen. We can have up to 5 in Sapphyx Launcher, you will be creating one of those five. This api is based on Luke Klinkers Blur API and the warning he has given us is this..."this isn't running in your apps context, it is running completely on ours, which can cause a few problems for things like shared preferences, accessing resources, accessing your cache, or any of your databases. We just wanted to make it clear that this isn't quite like DashClock which is able to broadcast updates from inside your own app. These extentions have to run within our app or they just do not work. Nougat locked down some permissions, especially those around the shared preferences, which denied global accessiibility." Simply put, anything that is trying to pull data directly from an app package is not going to work well and you are going to have to find ways around this or work with me to improve the API. 

# SharedPreferences
Aside from tapping into the api to create pages, Sapphyx Launcher offers some SharedPreferences to customize the page with color options if your page does not have a settings activity. After you have set SharedPrefences to your views, the user can than customize the color options via the Static Pages preferences. 

```xml
-Primary Color = android:key="pref_staticPrimary"

-PrimaryDark Color = android:key="pref_staticPrimaryDark"

-Accent Color = android:key="pref_staticAccent"

-Page Background Color = android:key="pref_staticBackground"
```

In your class add SharedPreferences...

```xml
SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
```

Tap into the SharedPreferences key...

```xml
background.setBackgroundColor(sp.getInt("pref_drawerBackground", Color.WHITE));
```
This repository is not finished yet, please be patient.

