# SapphyxAPI
The API that is used to build, run, and maintain the extended homescreens of Sapphyx Launcher. 
Sapphyx Launcher is one of the few launchers that has its own extended homescreen service or swipe to left screens. 
With the API, Developers can use this library to create pages for Sapphyx launcher that can acts a Interactable Widgets, Utilities, Exclusive Add-Ons, or a mirrored view of a certain activity or feature withen their application. 
Pages can be built by themselves without a main activity needed. 
No ads are to be put into any pages.

This repository is not done yet and is currently just a place holder.

# Installation
# Setup
# How It Works

# SharedPreferences
Aside from tapping into the api to create pages, Sapphyx Launcher offers some SharedPreferences to customize the page with color options if your page does not have a settings activity. After you have set SharedPrefences to your views, the user can than customize the color options via the Static Pages preferences.

-Primary Color = android:key="pref_staticPrimary"

-PrimaryDark Color = android:key="pref_staticPrimaryDark"

-Accent Color = android:key="pref_staticAccent"

-Page Background Color = android:key="pref_staticBackground"

In your class add SharedPreferences...

SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

Tap into the SharedPreferences key...

background.setBackgroundColor(sp.getInt("pref_drawerBackground", Color.WHITE));

