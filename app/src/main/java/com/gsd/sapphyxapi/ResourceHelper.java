package com.gsd.sapphyxapi;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Originally created by Luke Klinker for Blur API
 * We may not need to modify anything here but if we do, we have the power to do so.
 * Let's see how we use this api first before we make any modifications...
 */

public class ResourceHelper {

    private static final String TAG = "ResourceHelper";
    private static boolean DEBUG = false;

    private Context context;
    private String packageName;
    private Resources resources;

    public ResourceHelper(Context context, String packageName) {
        this.context = context;
        this.packageName = packageName;

        if (DEBUG) {
            this.resources = context.getResources();
        } else {
            try {
                this.resources = context.getPackageManager().getResourcesForApplication(packageName);
            } catch (Exception e) {
                Log.e(TAG, "error getting resources");
            }
        }
    }

    public Context getResourceContext() throws PackageManager.NameNotFoundException {
        return context.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
    }

    public int getIdentifier(String name, String type) {
        if (resources != null) {
            return resources.getIdentifier(name, type, packageName);
        } else {
            return 0;
        }
    }

    public int getId(String name) {
        if (resources != null) {
            return getIdentifier(name, "id");
        } else {
            return 0;
        }
    }

    public int getColor(String name) {
        if (resources != null) {
            return resources.getColor(getIdentifier(name, "color"));
        } else {
            return 0;
        }
    }

    public String getString(String name) {
        if (resources != null) {
            return resources.getString(getIdentifier(name, "string"));
        } else {
            return "";
        }
    }

    public Animation getAnimation(String name) {
        if (resources != null) {
            try {
                return createAnimationFromXml(context, resources.getAnimation(getIdentifier(name, "anim")));
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public Drawable getDrawable(String name) {
        if (resources != null) {
            return resources.getDrawable(getIdentifier(name, "drawable"));
        } else {
            return null;
        }
    }

    public Drawable getDrawable(int id) {
        if (resources != null) {
            return resources.getDrawable(id);
        } else {
            return null;
        }
    }

    public Drawable getMipmap(String name) {
        if (resources != null) {
            return resources.getDrawable(getIdentifier(name, "mipmap"));
        } else {
            return null;
        }
    }

    public String[] getStringArray(String name) {
        if (resources != null) {
            return resources.getStringArray(getIdentifier(name, "array"));
        } else {
            return null;
        }
    }

    public int[] getIntArray(String name) {
        if (resources != null) {
            return resources.getIntArray(getIdentifier(name, "array"));
        } else {
            return null;
        }
    }

    public int getDimension(String name) {
        if (resources != null) {
            return resources.getDimensionPixelSize(getIdentifier(name, "dimen"));
        } else {
            return 0;
        }
    }

    public int getInteger(String name) {
        if (resources != null) {
            return resources.getInteger(getIdentifier(name, "integer"));
        } else {
            return 0;
        }
    }

    public boolean getBoolean(String name) {
        if (resources != null) {
            return resources.getBoolean(getIdentifier(name, "bool"));
        } else {
            return false;
        }
    }

    public View getLayout(String name) {
        return getLayout(name, null);
    }

    public View getLayout(String name, ViewGroup parent) {
        return getLayout(name, parent, false);
    }

    public View getLayout(String name, ViewGroup parent, boolean attachToParent) {
        try {
            Context viewContext = getResourceContext();

            if (resources != null && viewContext != null) {
                int id = getIdentifier(name, "layout");
                try {
                    View v = LayoutInflater.from(viewContext).inflate(resources.getLayout(id), parent, attachToParent);
                    return v;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Couldn't create context for inflating the view");
            e.printStackTrace();
        }

        return null;
    }

    private static Animation createAnimationFromXml(Context c, XmlPullParser parser)
            throws XmlPullParserException, IOException {

        return createAnimationFromXml(c, parser, null, Xml.asAttributeSet(parser));
    }

    private static Animation createAnimationFromXml(Context c, XmlPullParser parser,
                                                    AnimationSet parent, AttributeSet attrs) throws XmlPullParserException, IOException {

        Animation anim = null;

        // Make sure we are on a start tag.
        int type;
        int depth = parser.getDepth();

        while (((type=parser.next()) != XmlPullParser.END_TAG || parser.getDepth() > depth)
                && type != XmlPullParser.END_DOCUMENT) {

            if (type != XmlPullParser.START_TAG) {
                continue;
            }

            String  name = parser.getName();

            if (name.equals("set")) {
                anim = new AnimationSet(c, attrs);
                createAnimationFromXml(c, parser, (AnimationSet)anim, attrs);
            } else if (name.equals("alpha")) {
                anim = new AlphaAnimation(c, attrs);
            } else if (name.equals("scale")) {
                anim = new ScaleAnimation(c, attrs);
            }  else if (name.equals("rotate")) {
                anim = new RotateAnimation(c, attrs);
            }  else if (name.equals("translate")) {
                anim = new TranslateAnimation(c, attrs);
            } else {
                throw new RuntimeException("Unknown animation name: " + parser.getName());
            }

            if (parent != null) {
                parent.addAnimation(anim);
            }
        }

        return anim;

    }
}
