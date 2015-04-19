package afpcsoft.com.br.bestplaces.controller;

import android.app.ActionBar;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import java.security.acl.Owner;
import java.util.HashMap;
import java.util.Map;

import afpcsoft.com.br.bestplaces.R;

/**
 * Created by AndréFelipe on 18/04/2015.
 */
public class ButtonConfigurattionActivity extends BaseActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private ViewGroup viewGroup;


    Map<View, LinearLayout> viewMap;

    private LinearLayout gridVertical1;
    private LinearLayout gridVertical2;
    private LinearLayout gridVertical3;
    private LinearLayout gridVertical4;
    private LinearLayout gridVertical5;
    private LinearLayout gridVertical6;
    private LinearLayout gridVertical7;
    private LinearLayout gridVertical8;
    private LinearLayout gridVertical9;
    private LinearLayout gridVertical10;
    private LinearLayout gridVertical11;
    private LinearLayout gridVertical12;
    private LinearLayout gridVertical13;
    private LinearLayout gridVertical14;

    private View search;
    private View refresh;
    private View zoomIn;
    private View zoomOut;
    private View myLocal;

    private int x_cord;
    private int y_cord;

    private LinearLayout layoutStart1;
    private LinearLayout layoutStart2;
    private LinearLayout layoutStart3;
    private LinearLayout layoutStart4;
    private LinearLayout layoutStart5;
    private LinearLayout layoutStart6;
    private LinearLayout layoutStart7;
    private LinearLayout layoutStart8;

    private LinearLayout layoutCenter1;
    private LinearLayout layoutCenter2;
    private LinearLayout layoutCenter3;
    private LinearLayout layoutCenter4;
    private LinearLayout layoutCenter5;
    private LinearLayout layoutCenter6;
    private LinearLayout layoutCenter7;
    private LinearLayout layoutCenter8;

    private LinearLayout layoutCenter21;
    private LinearLayout layoutCenter22;
    private LinearLayout layoutCenter23;
    private LinearLayout layoutCenter24;
    private LinearLayout layoutCenter25;
//    private LinearLayout layoutCenter26;
    private LinearLayout layoutCenter27;
    private LinearLayout layoutCenter28;

    private LinearLayout layoutEnd1;
    private LinearLayout layoutEnd2;
    private LinearLayout layoutEnd3;
    private LinearLayout layoutEnd4;
    private LinearLayout layoutEnd5;
    private LinearLayout layoutEnd6;
    private LinearLayout layoutEnd7;
    private LinearLayout layoutEnd8;

    GridLayout gridLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_configuration);

        viewMap = new HashMap<View, LinearLayout>();

        setUpMapIfNeeded();
        initialize();
        generateLayouts();


    }

    private void generateLayouts() {

        gridLayout = (GridLayout) findViewById(R.id.grid);
        gridLayout.setRowCount(8);
        gridLayout.setColumnCount(4);

        layoutStart1 = new LinearLayout(this);
        layoutStart1.setBackgroundResource(R.drawable.field_shape);
        layoutStart1.setGravity(Gravity.CENTER);
        layoutStart2 = new LinearLayout(this);
        layoutStart2.setBackgroundResource(R.drawable.field_shape);
        layoutStart2.setGravity(Gravity.CENTER);
        layoutStart3 = new LinearLayout(this);
        layoutStart3.setBackgroundResource(R.drawable.field_shape);
        layoutStart3.setGravity(Gravity.CENTER);
        layoutStart4 = new LinearLayout(this);
        layoutStart4.setBackgroundResource(R.drawable.field_shape);
        layoutStart4.setGravity(Gravity.CENTER);
        layoutStart5 = new LinearLayout(this);
        layoutStart5.setBackgroundResource(R.drawable.field_shape);
        layoutStart5.setGravity(Gravity.CENTER);
        layoutStart6 = new LinearLayout(this);
        layoutStart6.setBackgroundResource(R.drawable.field_shape);
        layoutStart6.setGravity(Gravity.CENTER);
        layoutStart7 = new LinearLayout(this);
        layoutStart7.setBackgroundResource(R.drawable.field_shape);
        layoutStart7.setGravity(Gravity.CENTER);
        layoutStart8 = new LinearLayout(this);
        layoutStart8.setBackgroundResource(R.drawable.field_shape);
        layoutStart8.setGravity(Gravity.CENTER);

        layoutCenter1 = new LinearLayout(this);
        layoutCenter1.setBackgroundResource(R.drawable.field_shape);
        layoutCenter1.setGravity(Gravity.CENTER);
        layoutCenter2 = new LinearLayout(this);
//        layoutCenter2.setBackgroundResource(R.drawable.field_shape);
        layoutCenter2.setGravity(Gravity.CENTER);
        layoutCenter3 = new LinearLayout(this);
//        layoutCenter3.setBackgroundResource(R.drawable.field_shape);
        layoutCenter3.setGravity(Gravity.CENTER);
        layoutCenter4 = new LinearLayout(this);
//        layoutCenter4.setBackgroundResource(R.drawable.field_shape);
        layoutCenter4.setGravity(Gravity.CENTER);
        layoutCenter5 = new LinearLayout(this);
//        layoutCenter5.setBackgroundResource(R.drawable.field_shape);
        layoutCenter5.setGravity(Gravity.CENTER);
        layoutCenter6 = new LinearLayout(this);
        layoutCenter6.setBackgroundResource(R.drawable.field_shape);
        layoutCenter6.setGravity(Gravity.CENTER);
        layoutCenter7 = new LinearLayout(this);
        layoutCenter7.setBackgroundResource(R.drawable.field_shape);
        layoutCenter7.setGravity(Gravity.CENTER);
        layoutCenter8 = new LinearLayout(this);
        layoutCenter8.setBackgroundResource(R.drawable.field_shape);
        layoutCenter8.setGravity(Gravity.CENTER);

        layoutCenter21 = new LinearLayout(this);
        layoutCenter21.setBackgroundResource(R.drawable.field_shape);
        layoutCenter21.setGravity(Gravity.CENTER);
        layoutCenter22 = new LinearLayout(this);
//        layoutCenter22.setBackgroundResource(R.drawable.field_shape);
        layoutCenter22.setGravity(Gravity.CENTER);
        layoutCenter23 = new LinearLayout(this);
//        layoutCenter23.setBackgroundResource(R.drawable.field_shape);
        layoutCenter23.setGravity(Gravity.CENTER);
        layoutCenter24 = new LinearLayout(this);
//        layoutCenter24.setBackgroundResource(R.drawable.field_shape);
        layoutCenter24.setGravity(Gravity.CENTER);
        layoutCenter25 = new LinearLayout(this);
//        layoutCenter25.setBackgroundResource(R.drawable.field_shape);
        layoutCenter25.setGravity(Gravity.CENTER);
//        layoutCenter26 = new LinearLayout(this);
//        layoutCenter26.setBackgroundResource(R.drawable.field_shape);
//        layoutCenter26.setGravity(Gravity.CENTER);
        layoutCenter27 = new LinearLayout(this);
        layoutCenter27.setBackgroundResource(R.drawable.field_shape);
        layoutCenter27.setGravity(Gravity.CENTER);
        layoutCenter28 = new LinearLayout(this);
        layoutCenter28.setBackgroundResource(R.drawable.field_shape);
        layoutCenter28.setGravity(Gravity.CENTER);

        layoutEnd1 = new LinearLayout(this);
        layoutEnd1.setBackgroundResource(R.drawable.field_shape);
        layoutEnd1.setGravity(Gravity.CENTER);
        layoutEnd2 = new LinearLayout(this);
        layoutEnd2.setBackgroundResource(R.drawable.field_shape);
        layoutEnd2.setGravity(Gravity.CENTER);
        layoutEnd3 = new LinearLayout(this);
        layoutEnd3.setBackgroundResource(R.drawable.field_shape);
        layoutEnd3.setGravity(Gravity.CENTER);
        layoutEnd4 = new LinearLayout(this);
        layoutEnd4.setBackgroundResource(R.drawable.field_shape);
        layoutEnd4.setGravity(Gravity.CENTER);
        layoutEnd5 = new LinearLayout(this);
        layoutEnd5.setBackgroundResource(R.drawable.field_shape);
        layoutEnd5.setGravity(Gravity.CENTER);
        layoutEnd6 = new LinearLayout(this);
        layoutEnd6.setBackgroundResource(R.drawable.field_shape);
        layoutEnd6.setGravity(Gravity.CENTER);
        layoutEnd7 = new LinearLayout(this);
        layoutEnd7.setBackgroundResource(R.drawable.field_shape);
        layoutEnd7.setGravity(Gravity.CENTER);
        layoutEnd8 = new LinearLayout(this);
        layoutEnd8.setBackgroundResource(R.drawable.field_shape);
        layoutEnd8.setGravity(Gravity.CENTER);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
        int fourthPartScreenWidth = (int)(screenWidth/4);
        int octPartScreenHeight = (int)(screenHeight/8);

        GridLayout.Spec col0 = GridLayout.spec(0);
        GridLayout.Spec col1 = GridLayout.spec(1);
        GridLayout.Spec col2 = GridLayout.spec(2);
        GridLayout.Spec col3 = GridLayout.spec(3);
        GridLayout.Spec colButton = GridLayout.spec(1,2);

        GridLayout.Spec row0 = GridLayout.spec(0);
        GridLayout.Spec row1 = GridLayout.spec(1);
        GridLayout.Spec row2 = GridLayout.spec(2);
        GridLayout.Spec row3 = GridLayout.spec(3);
        GridLayout.Spec row4 = GridLayout.spec(4);
        GridLayout.Spec row5 = GridLayout.spec(5);
        GridLayout.Spec row6 = GridLayout.spec(6);
        GridLayout.Spec row7 = GridLayout.spec(7);


        //Column 1
        GridLayout.LayoutParams start1 = new GridLayout.LayoutParams(row0, col0);
        start1.setGravity(Gravity.CENTER);
        start1.width = fourthPartScreenWidth;
        start1.height = octPartScreenHeight;
        layoutStart1.setLayoutParams(start1);
        layoutStart1.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutStart1);

        GridLayout.LayoutParams start2 = new GridLayout.LayoutParams(row1, col0);
        start2.setGravity(Gravity.CENTER);
        start2.width = fourthPartScreenWidth;
        start2.height = octPartScreenHeight;
        layoutStart2.setLayoutParams(start2);
        layoutStart2.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutStart2);

        GridLayout.LayoutParams start3 = new GridLayout.LayoutParams(row2, col0);
        start3.setGravity(Gravity.CENTER);
        start3.width = fourthPartScreenWidth;
        start3.height = octPartScreenHeight;
        layoutStart3.setLayoutParams(start3);
        layoutStart3.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutStart3);

        GridLayout.LayoutParams start4 = new GridLayout.LayoutParams(row3, col0);
        start4.setGravity(Gravity.CENTER);
        start4.width = fourthPartScreenWidth;
        start4.height = octPartScreenHeight;
        layoutStart4.setLayoutParams(start4);
        layoutStart4.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutStart4);

        GridLayout.LayoutParams start5 = new GridLayout.LayoutParams(row4, col0);
        start5.setGravity(Gravity.CENTER);
        start5.width = fourthPartScreenWidth;
        start5.height = octPartScreenHeight;
        layoutStart5.setLayoutParams(start5);
        layoutStart5.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutStart5);

        GridLayout.LayoutParams start6 = new GridLayout.LayoutParams(row5, col0);
        start6.setGravity(Gravity.CENTER);
        start6.width = fourthPartScreenWidth;
        start6.height = octPartScreenHeight;
        layoutStart6.setLayoutParams(start6);
        layoutStart6.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutStart6);

        GridLayout.LayoutParams start7 = new GridLayout.LayoutParams(row6, col0);
        start7.setGravity(Gravity.CENTER);
        start7.width = fourthPartScreenWidth;
        start7.height = octPartScreenHeight;
        layoutStart7.setLayoutParams(start7);
        layoutStart7.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutStart7);

        GridLayout.LayoutParams start8 = new GridLayout.LayoutParams(row7, col0);
        start8.setGravity(Gravity.CENTER);
        start8.width = fourthPartScreenWidth;
        start8.height = octPartScreenHeight;
        layoutStart8.setLayoutParams(start8);
        layoutStart8.setOnDragListener(new MyDragListener());
//        gridLayout.addView(layoutStart8);

        //Column 2
        GridLayout.LayoutParams center1 = new GridLayout.LayoutParams(row0, col1);
        center1.setGravity(Gravity.CENTER);
        center1.width = fourthPartScreenWidth;
        center1.height = octPartScreenHeight;
        layoutCenter1.setLayoutParams(center1);
        layoutCenter1.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter1);

        GridLayout.LayoutParams center2 = new GridLayout.LayoutParams(row1, col1);
        center2.setGravity(Gravity.CENTER);
        center2.width = fourthPartScreenWidth;
        center2.height = octPartScreenHeight;
        layoutCenter2.setLayoutParams(center2);
        layoutCenter2.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter2);

        GridLayout.LayoutParams center3 = new GridLayout.LayoutParams(row2, col1);
        center3.setGravity(Gravity.CENTER);
        center3.width = fourthPartScreenWidth;
        center3.height = octPartScreenHeight;
        layoutCenter3.setLayoutParams(center3);
        layoutCenter3.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter3);

        GridLayout.LayoutParams center4 = new GridLayout.LayoutParams(row3, col1);
        center4.setGravity(Gravity.CENTER);
        center4.width = fourthPartScreenWidth;
        center4.height = octPartScreenHeight;
        layoutCenter4.setLayoutParams(center4);
        layoutCenter4.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter4);

        GridLayout.LayoutParams center5 = new GridLayout.LayoutParams(row4, col1);
        center5.setGravity(Gravity.CENTER);
        center5.width = fourthPartScreenWidth;
        center5.height = octPartScreenHeight;
        layoutCenter5.setLayoutParams(center5);
        layoutCenter5.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter5);

        GridLayout.LayoutParams center6 = new GridLayout.LayoutParams(row5, colButton);
        center6.setGravity(Gravity.CENTER);
        center6.width = fourthPartScreenWidth*2;
        center6.height = octPartScreenHeight;
        layoutCenter6.setLayoutParams(center6);
        layoutCenter6.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter6);

        GridLayout.LayoutParams center7 = new GridLayout.LayoutParams(row6, col1);
        center7.setGravity(Gravity.CENTER);
        center7.width = fourthPartScreenWidth;
        center7.height = octPartScreenHeight;
        layoutCenter7.setLayoutParams(center7);
        layoutCenter7.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter7);

        GridLayout.LayoutParams center8 = new GridLayout.LayoutParams(row7, col1);
        center8.setGravity(Gravity.CENTER);
        center8.width = fourthPartScreenWidth;
        center8.height = octPartScreenHeight;
        layoutCenter8.setLayoutParams(center8);
        layoutCenter8.setOnDragListener(new MyDragListener());
//        gridLayout.addView(layoutCenter8);

        //Column 3
        GridLayout.LayoutParams center21 = new GridLayout.LayoutParams(row0, col2);
        center21.setGravity(Gravity.CENTER);
        center21.width = fourthPartScreenWidth;
        center21.height = octPartScreenHeight;
        layoutCenter21.setLayoutParams(center21);
        layoutCenter21.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter21);

        GridLayout.LayoutParams center22 = new GridLayout.LayoutParams(row1, col2);
        center22.setGravity(Gravity.CENTER);
        center22.width = fourthPartScreenWidth;
        center22.height = octPartScreenHeight;
        layoutCenter22.setLayoutParams(center22);
        layoutCenter22.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter22);

        GridLayout.LayoutParams center23 = new GridLayout.LayoutParams(row2, col2);
        center23.setGravity(Gravity.CENTER);
        center23.width = fourthPartScreenWidth;
        center23.height = octPartScreenHeight;
        layoutCenter23.setLayoutParams(center23);
        layoutCenter23.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter23);

        GridLayout.LayoutParams center24 = new GridLayout.LayoutParams(row3, col2);
        center24.setGravity(Gravity.CENTER);
        center24.width = fourthPartScreenWidth;
        center24.height = octPartScreenHeight;
        layoutCenter24.setLayoutParams(center24);
        layoutCenter24.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter24);

        GridLayout.LayoutParams center25 = new GridLayout.LayoutParams(row4, col2);
        center25.setGravity(Gravity.CENTER);
        center25.width = fourthPartScreenWidth;
        center25.height = octPartScreenHeight;
        layoutCenter25.setLayoutParams(center25);
        layoutCenter25.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter25);

//        GridLayout.LayoutParams center26 = new GridLayout.LayoutParams(row5, col2);
//        center26.setGravity(Gravity.CENTER);
//        center26.width = fourthPartScreenWidth;
//        center26.height = octPartScreenHeight;
//        layoutCenter26.setLayoutParams(center26);
//        layoutCenter26.setOnDragListener(new MyDragListener());
//        gridLayout.addView(layoutCenter26);

        GridLayout.LayoutParams center27 = new GridLayout.LayoutParams(row6, col2);
        center27.setGravity(Gravity.CENTER);
        center27.width = fourthPartScreenWidth;
        center27.height = octPartScreenHeight;
        layoutCenter27.setLayoutParams(center27);
        layoutCenter27.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutCenter27);

        GridLayout.LayoutParams center28 = new GridLayout.LayoutParams(row7, col2);
        center28.setGravity(Gravity.CENTER);
        center28.width = fourthPartScreenWidth;
        center28.height = octPartScreenHeight;
        layoutCenter28.setLayoutParams(center28);
        layoutCenter28.setOnDragListener(new MyDragListener());
//        gridLayout.addView(layoutCenter28);

        //Column 3
        GridLayout.LayoutParams end1 = new GridLayout.LayoutParams(row0, col3);
        end1.setGravity(Gravity.CENTER);
        end1.width = fourthPartScreenWidth;
        end1.height = octPartScreenHeight;
        layoutEnd1.setLayoutParams(end1);
        layoutEnd1.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutEnd1);

        GridLayout.LayoutParams end2 = new GridLayout.LayoutParams(row1, col3);
        end2.setGravity(Gravity.CENTER);
        end2.width = fourthPartScreenWidth;
        end2.height = octPartScreenHeight;
        layoutEnd2.setLayoutParams(end2);
        layoutEnd2.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutEnd2);

        GridLayout.LayoutParams end3 = new GridLayout.LayoutParams(row2, col3);
        end3.setGravity(Gravity.CENTER);
        end3.width = fourthPartScreenWidth;
        end3.height = octPartScreenHeight;
        layoutEnd3.setLayoutParams(end3);
        layoutEnd3.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutEnd3);

        GridLayout.LayoutParams end4 = new GridLayout.LayoutParams(row3, col3);
        end4.setGravity(Gravity.CENTER);
        end4.width = fourthPartScreenWidth;
        end4.height = octPartScreenHeight;
        layoutEnd4.setLayoutParams(end4);
        layoutEnd4.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutEnd4);

        GridLayout.LayoutParams end5 = new GridLayout.LayoutParams(row4, col3);
        end5.setGravity(Gravity.CENTER);
        end5.width = fourthPartScreenWidth;
        end5.height = octPartScreenHeight;
        layoutEnd5.setLayoutParams(end5);
        layoutEnd5.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutEnd5);

        GridLayout.LayoutParams end6 = new GridLayout.LayoutParams(row5, col3);
        end6.setGravity(Gravity.CENTER);
        end6.width = fourthPartScreenWidth;
        end6.height = octPartScreenHeight;
        layoutEnd6.setLayoutParams(end6);
        layoutEnd6.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutEnd6);

        GridLayout.LayoutParams end7 = new GridLayout.LayoutParams(row6, col3);
        end7.setGravity(Gravity.CENTER);
        end7.width = fourthPartScreenWidth;
        end7.height = octPartScreenHeight;
        layoutEnd7.setLayoutParams(end7);
        layoutEnd7.setOnDragListener(new MyDragListener());
        gridLayout.addView(layoutEnd7);

        GridLayout.LayoutParams end8 = new GridLayout.LayoutParams(row7, col3);
        end8.setGravity(Gravity.CENTER);
        end8.width = fourthPartScreenWidth;
        end8.height = octPartScreenHeight;
        layoutEnd8.setLayoutParams(end8);
        layoutEnd8.setOnDragListener(new MyDragListener());
//        gridLayout.addView(layoutEnd8);

        //Generating Buttons LayoutParams
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = (70 * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
        int heigh = (70 * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,heigh);
        layoutParams.gravity = Gravity.CENTER;

        zoomIn = new View(ButtonConfigurattionActivity.this);
        zoomIn.setLayoutParams(layoutParams);
        zoomIn.setPadding(10, 10, 10, 10);
        zoomIn.setBackgroundResource(R.drawable.plus);

        zoomOut = new View(ButtonConfigurattionActivity.this);
        zoomOut.setLayoutParams(layoutParams);
        zoomOut.setPadding(10, 10, 10, 10);
        zoomOut.setBackgroundResource(R.drawable.minus);


        myLocal = new View(ButtonConfigurattionActivity.this);
        myLocal.setLayoutParams(layoutParams);
        myLocal.setPadding(10, 10, 10, 10);
        myLocal.setBackgroundResource(R.drawable.location);

        refresh = new View(ButtonConfigurattionActivity.this);
        refresh.setLayoutParams(layoutParams);
        refresh.setPadding(10, 10, 10, 10);
        refresh.setBackgroundResource(R.drawable.refresh);

        search = new View(ButtonConfigurattionActivity.this);
        search.setLayoutParams(layoutParams);
        search.setPadding(10, 10, 10, 10);
        search.setBackgroundResource(R.drawable.search);

        search.setOnTouchListener(new MyTouchListener());
        refresh.setOnTouchListener(new MyTouchListener());
        myLocal.setOnTouchListener(new MyTouchListener());
        zoomIn.setOnTouchListener(new MyTouchListener());
        zoomOut.setOnTouchListener(new MyTouchListener());

        layoutCenter3.addView(search);
        layoutCenter4.addView(refresh);
        layoutCenter5.addView(myLocal);
        layoutCenter23.addView(zoomIn);
        layoutCenter24.addView(zoomOut);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setUpMapIfNeeded();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
        }
    }

    class MyDragListener implements View.OnDragListener {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:

                    Log.i("drag", "entrei");
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    LinearLayout container = (LinearLayout) v;
                    if(!container.equals(layoutCenter2)
                            && !container.equals(layoutCenter3)
                            && !container.equals(layoutCenter4)
                            && !container.equals(layoutCenter5)
                            && !container.equals(layoutCenter6)
                            && !container.equals(layoutCenter22)
                            && !container.equals(layoutCenter23)
                            && !container.equals(layoutCenter24)
                            && !container.equals(layoutCenter25)
                            && container.getChildCount() == 0) {
                        owner.removeView(view);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(view.getWidth(), view.getHeight());
                        layoutParams.gravity = Gravity.CENTER;
                        container.setGravity(Gravity.CENTER);
                        container.addView(view);
                    }
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                case DragEvent.ACTION_DRAG_LOCATION:
                    v.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }
            return true;
        }


        private boolean dropEventNotHandled(DragEvent dragEvent) {
            return !dragEvent.getResult();
        }
    }


    private final class MyTouchListener implements View.OnTouchListener {

        public boolean onTouch(View v, MotionEvent me) {

            if (me.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(data, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }
}
