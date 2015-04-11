package afpcsoft.com.br.bestplaces.controller;

import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import afpcsoft.com.br.bestplaces.R;

public class BaseActivity extends FragmentActivity {

    protected DrawerLayout drawerLayoutLeft;
    protected LinearLayout drawerLeft;
    protected boolean openDrawableLeft;

    protected DrawerLayout drawerLayoutRight;
    protected LinearLayout drawerRight;
    protected boolean openDrawableRight;
    protected ImageView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getOverflowMenu();
    }

    public void initialize(){


        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Animation rotation = AnimationUtils.loadAnimation(MapsActivity.this, R.anim.menu_rotation);
                //RotateAnimation rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                //rotation.setDuration(300);
                //menu.startAnimation(rotation);

                if(drawerLayoutLeft.isDrawerOpen(drawerLeft)){
                    drawerLayoutLeft.closeDrawer(drawerLeft);
                    openDrawableLeft = false;
                }else{
                    drawerLayoutRight.closeDrawer(drawerRight);
                    drawerLayoutLeft.openDrawer(drawerLeft);
                    openDrawableLeft = true;
                }
            }
        });
        final ImageView more = (ImageView) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotation= new RotateAnimation(0f,360f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                rotation.setDuration(300);
                more.startAnimation(rotation);
            }
        });
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

            drawerLayoutLeft.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) {

                }

                @Override
                public void onDrawerOpened(View view) {
                    RotateAnimation rotation= new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    rotation.setDuration(300);
                    menu.startAnimation(rotation);
                }

                @Override
                public void onDrawerClosed(View view) {
                    RotateAnimation rotation= new RotateAnimation(0f,-360f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                    rotation.setDuration(300);
                    menu.startAnimation(rotation);
                }

                @Override
                public void onDrawerStateChanged(int i) {
                }
            });


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
