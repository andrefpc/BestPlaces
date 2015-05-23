package afpcsoft.com.br.bestplaces.controller;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
//    protected ActionBarDrawerToggle drawerToggle;
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
        getMenuInflater().inflate(R.menu.menu_infos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        if (drawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_search){
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }else if(id == R.id.action_filter){
            if(drawerLayoutRight.isDrawerOpen(drawerRight)) {
                drawerLayoutRight.closeDrawer(drawerRight);
            }else{
                drawerLayoutRight.openDrawer(drawerRight);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void initialize(){


//        menu = (ImageView) findViewById(R.id.menu);
//        menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Animation rotation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.menu_rotation);
//                //RotateAnimation rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//                //rotation.setDuration(300);
//                //menu.startAnimation(rotation);
//
//                if(drawerLayoutLeft.isDrawerOpen(drawerLeft)){
//                    drawerLayoutLeft.closeDrawer(drawerLeft);
//                    openDrawableLeft = false;
//                }else{
//                    if(drawerLayoutRight != null && drawerLayoutRight.isDrawerOpen(drawerRight)) {
//                        drawerLayoutRight.closeDrawer(drawerRight);
//                    }
//                    drawerLayoutLeft.openDrawer(drawerLeft);
//                    openDrawableLeft = true;
//                }
//            }
//        });
//        final ImageView more = (ImageView) findViewById(R.id.more);
//        more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RotateAnimation rotation= new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
//                rotation.setDuration(300);
//                more.startAnimation(rotation);
//            }
//        });
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

    @Override
    public boolean onKeyDown(int keycode, KeyEvent e) {
        switch(keycode) {
            case KeyEvent.KEYCODE_MENU:
                if(drawerLayoutLeft.isDrawerOpen(drawerLeft)){
                    drawerLayoutLeft.closeDrawer(drawerLeft);
                    openDrawableLeft = false;
                }else{
                    drawerLayoutRight.closeDrawer(drawerRight);
                    drawerLayoutLeft.openDrawer(drawerLeft);
                    openDrawableLeft = true;
                }
        }

        return super.onKeyDown(keycode, e);
    }

    protected void leftMenu() {
        try {
            drawerLayoutLeft = (DrawerLayout) findViewById(R.id.drawer_layout_left);
            drawerLeft = (LinearLayout) findViewById(R.id.drawer_left);

//            drawerToggle = new ActionBarDrawerToggle(BaseActivity.this, drawerLayoutLeft,
//                    R.drawable.ic_action_navigation_menu, 0, 0) ;
            
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);

//            drawerLayoutLeft.setDrawerListener(drawerToggle);

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
//            drawerLayoutRight.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

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
