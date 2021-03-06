package com.gsd.sapphyxapi;

/**
 * Created by ry on 9/3/17.
 * SharedPreferences used in Sapphyx Launcher to allow users some Color Options in Pages.
 * That is if the dev adds color options...
 * This is only necessary if the dev does not add a settings page to their page or project.
 * We can add more at anytime!
 */

interface SettingsKeys {

    static String COLOR_PAGE_PRIMARY = "pref_staticPrimary";
    static String COLOR_PAGE_PRIMARYDARK = "pref_staticPrimaryDark";
    static String COLOR_PAGE_ACCENT = "pref_staticAccent";
    static String COLOR_PAGE_BACKGROUND = "pref_staticBackground";

}
