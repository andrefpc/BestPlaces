package afpcsoft.com.br.bestplaces.controller;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import afpcsoft.com.br.bestplaces.R;

public class BaseActivity extends ActionBarActivity {

    protected DrawerLayout drawerLayoutLeft;
    protected LinearLayout drawerLeft;
    protected boolean openDrawableLeft;

    protected DrawerLayout drawerLayoutRight;
    protected LinearLayout drawerRight;
    protected boolean openDrawableRight;
    protected Menu menu;

    protected ActionBarDrawerToggle drawerToggle;
//    protected ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getOverflowMenu();
        leftMenu();
        rightMenu();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        this.menu = menu;
        return true;
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void initialize(){

    }

    protected void getOverflowMenu() {

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void leftMenu() {
        try {
            drawerLayoutLeft = (DrawerLayout) findViewById(R.id.drawer_layout_left);
            drawerLeft = (LinearLayout) findViewById(R.id.drawer_left);

            drawerToggle = new ActionBarDrawerToggle((Activity) this, drawerLayoutLeft, 0, 0){
                public void onDrawerClosed(View view) {
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                public void onDrawerOpened(View drawerView) {
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };

            drawerLayoutLeft.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) {

                }

                @Override
                public void onDrawerOpened(View view) {
//                    drawerLayoutRight.closeDrawer(drawerRight);
                }

                @Override
                public void onDrawerClosed(View view) {
                }

                @Override
                public void onDrawerStateChanged(int i) {
                }
            });

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);

            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerLayoutLeft.setDrawerListener(drawerToggle);

            Field dragger = null;

            try {
                dragger = drawerLayoutLeft.getClass().getDeclaredField("mLeftDragger");
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dragger.setAccessible(true);
            ViewDragHelper draggerObj = null;
            try {
                draggerObj = (ViewDragHelper) dragger.get(drawerLayoutLeft);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Field mEdgeSize = null;
            try {
                mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mEdgeSize.setAccessible(true);
            int edge = 0;
            try {
                edge = mEdgeSize.getInt(draggerObj);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                mEdgeSize.setInt(draggerObj, edge);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    protected void rightMenu() {
        try {
            drawerLayoutRight = (DrawerLayout) findViewById(R.id.drawer_layout_right);
            drawerRight = (LinearLayout) findViewById(R.id.drawer_right);
//
            drawerLayoutRight.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            drawerLayoutRight.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) {

                }

                @Override
                public void onDrawerOpened(View view) {
                    drawerLayoutRight.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }

                @Override
                public void onDrawerClosed(View view) {
                    drawerLayoutRight.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }

                @Override
                public void onDrawerStateChanged(int i) {
                }
            });

            Field dragger = null;

            try {
                dragger = drawerLayoutRight.getClass().getDeclaredField("mRightDragger");
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            dragger.setAccessible(true);
            ViewDragHelper draggerObj = null;
            try {
                draggerObj = (ViewDragHelper) dragger.get(drawerLayoutRight);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Field mEdgeSize = null;
            try {
                mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
            } catch (NoSuchFieldException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mEdgeSize.setAccessible(true);
            int edge = 0;
            try {
                edge = mEdgeSize.getInt(draggerObj);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                mEdgeSize.setInt(draggerObj, edge);
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
