package afpcsoft.com.br.bestplaces.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.DialogUtils;
import afpcsoft.com.br.bestplaces.Utils.StaticValues;
import afpcsoft.com.br.bestplaces.model.ItemPrices;
import afpcsoft.com.br.bestplaces.model.Place;
import afpcsoft.com.br.bestplaces.model.PlaceApiEdited;
import afpcsoft.com.br.bestplaces.model.Price;
import afpcsoft.com.br.bestplaces.model.detailsPlacesApi.DetailsApiResult;
import afpcsoft.com.br.bestplaces.model.placesApi.ResultPlaces;
import afpcsoft.com.br.bestplaces.service.AddPriceTask;
import afpcsoft.com.br.bestplaces.service.DetailsApiEditedTask;
import afpcsoft.com.br.bestplaces.service.DetailsApiTask;
import afpcsoft.com.br.bestplaces.service.DetailsNativeTask;
import afpcsoft.com.br.bestplaces.service.ListPricesTask;
import afpcsoft.com.br.bestplaces.service.UpdatePlaceTask;

public class InfosActivity extends ActionBarActivity implements ActionBar.TabListener, UpdatePlaceTask.OnPostExecuteListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    public int tag;
    public ResultPlaces resultPlaces;
    public Place place;
    public PlaceApiEdited placeApiEdited;

    public static int ADD_PHONE = 1;
    public static int ADD_SITE = 2;
    public static int ADD_FACE = 3;
    public static int ADD_PLUS = 4;

    protected DrawerLayout drawerLayoutLeft;
    protected LinearLayout drawerLeft;
    protected boolean openDrawableLeft;

    protected DrawerLayout drawerLayoutRight;
    protected LinearLayout drawerRight;
    protected boolean openDrawableRight;

    private NumberPicker np1;
    private NumberPicker np2;
    private NumberPicker np3;
    private NumberPicker np4;
    private NumberPicker np5;
    private EditText title;
    private EditText desciption;

    private LinearLayout layoutPhone;
    private LinearLayout layoutSite;
    private LinearLayout layoutFace;
    private LinearLayout layoutPlus;
    private EditText phoneEditText;
    private EditText siteEditText;
    private EditText faceEditText;
    private EditText plusEditText;
    private Button saveData;

    public Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infos);

        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", 0);
        if (tag == StaticValues.GoogleAPIPlaces) {
            resultPlaces = (ResultPlaces) intent.getSerializableExtra("resultPlaces");
            placeApiEdited = new PlaceApiEdited(resultPlaces.getPlaceId());
        }else if(tag == StaticValues.NativePlaces ){
            place = (Place) intent.getSerializableExtra("place");
        }

        getOverflowMenu();
        rightMenu();
        leftMenu();

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        np1 = (NumberPicker) findViewById(R.id.numberPicker1);
        np1.setMinValue(0);
        np1.setMaxValue(9);
        np1.setWrapSelectorWheel(true);

        np2 = (NumberPicker) findViewById(R.id.numberPicker2);
        np2.setMinValue(0);
        np2.setMaxValue(9);
        np2.setWrapSelectorWheel(true);

        np3 = (NumberPicker) findViewById(R.id.numberPicker3);
        np3.setMinValue(0);
        np3.setMaxValue(9);
        np3.setWrapSelectorWheel(true);

        np4 = (NumberPicker) findViewById(R.id.numberPicker4);
        np4.setMinValue(0);
        np4.setMaxValue(9);
        np4.setWrapSelectorWheel(true);


        np5 = (NumberPicker) findViewById(R.id.numberPicker5);
        np5.setMinValue(0);
        np5.setMaxValue(9);
        np5.setWrapSelectorWheel(true);

        title = (EditText) findViewById(R.id.titleEditText);
        desciption = (EditText) findViewById(R.id.descriptionEditText);

        layoutPhone = (LinearLayout) findViewById(R.id.layoutPhone);
        layoutSite = (LinearLayout) findViewById(R.id.layoutSite);
        layoutPlus = (LinearLayout) findViewById(R.id.layoutPlus);
        layoutFace = (LinearLayout) findViewById(R.id.layoutFace);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        siteEditText = (EditText) findViewById(R.id.siteEditText);
        faceEditText = (EditText) findViewById(R.id.faceEditText);
        plusEditText = (EditText) findViewById(R.id.plusEditText);
        saveData = (Button) findViewById(R.id.saveData);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int item = 0;
                int type = 0;
                int placeId = 0;
                String placeApiId = "";
                String itemStr = "";

                if(!phoneEditText.getText().toString().equals("")){
                    item = StaticValues.PHONE;
                    itemStr = phoneEditText.getText().toString();
                }else if(!siteEditText.getText().toString().equals("")){
                    item = StaticValues.SITE;
                    itemStr = siteEditText.getText().toString();
                }else if(!faceEditText.getText().toString().equals("")){
                    item = StaticValues.FACE;
                    itemStr = faceEditText.getText().toString();
                }else if(!plusEditText.getText().toString().equals("")){
                    item = StaticValues.PLUS;
                    itemStr = plusEditText.getText().toString();
                }

                if (tag == StaticValues.GoogleAPIPlaces) {
                    type = StaticValues.GoogleAPIPlaces;
                    placeApiId = resultPlaces.getPlaceId();
                }else if(tag == StaticValues.NativePlaces ){
                    type = StaticValues.NativePlaces;
                    placeId = place.getId();
                }

                new UpdatePlaceTask(InfosActivity.this, type, item, placeId, placeApiId, itemStr, InfosActivity.this).execute();
                closeLeftDrawer();
            }
        });



        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    public Price getItemSubmited(){
        Price price = new Price();
        price.setName(title.getText().toString());
        price.setDescription(desciption.getText().toString());
        if(np1.getValue() > 0 && np2.getValue() > 0) {
            price.setPrice("R$ " + np1.getValue() + np2.getValue() + np3.getValue() + "," + np4.getValue() + np5.getValue());
        }else if(np1.getValue() == 0 && np2.getValue() > 0) {
            price.setPrice("R$ " + np2.getValue() + np3.getValue() + "," + np4.getValue() + np5.getValue());
        }else if(np1.getValue() == 0 && np2.getValue() == 0) {
            price.setPrice("R$ " + np3.getValue() + "," + np4.getValue() + np5.getValue());
        }
        price.setType(tag);
        if (tag == StaticValues.GoogleAPIPlaces) {
            price.setPlaceApiIdString(placeApiEdited.getPlaceApiId());
        }else if(tag == StaticValues.NativePlaces ){
            price.setNativePlaceId(place.getId());
        }
        return price;
    }

    public void resetView(){
        title.setText("");
        desciption.setText("");
        np1.setValue(0);
        np2.setValue(0);
        np3.setValue(0);
        np4.setValue(0);
        np5.setValue(0);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void onBackPressed(){

        if(drawerLayoutRight.isDrawerOpen(drawerRight)){
            drawerLayoutRight.closeDrawer(drawerRight);
        }else if(drawerLayoutLeft.isDrawerOpen(drawerLeft)){
            drawerLayoutLeft.closeDrawer(drawerLeft);
        }else{
            super.onBackPressed();
        }
    }

    protected void leftMenu() {
        try {
            drawerLayoutLeft = (DrawerLayout) findViewById(R.id.drawer_layout_left);
            drawerLeft = (LinearLayout) findViewById(R.id.drawer_left);

            drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            drawerLayoutLeft.setDrawerListener(new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View view, float v) {

                }

                @Override
                public void onDrawerOpened(View view) {
                    drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }

                @Override
                public void onDrawerClosed(View view) {
                    drawerLayoutLeft.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }

                @Override
                public void onDrawerStateChanged(int i) {

                }
            });

            Field dragger = null;

            try {
                dragger = drawerLayoutLeft.getClass().getDeclaredField("mRightDragger");
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

    public void openRightDrawer(){
        drawerLayoutRight.openDrawer(drawerRight);
    }

    public void closeRightDrawer(){
        drawerLayoutRight.closeDrawer(drawerRight);
    }

    public void openLeftDrawer(int type){
        drawerLayoutLeft.openDrawer(drawerLeft);
        if(type == ADD_PHONE){
            layoutPhone.setVisibility(View.VISIBLE);
            layoutSite.setVisibility(View.GONE);
            layoutFace.setVisibility(View.GONE);
            layoutPlus.setVisibility(View.GONE);
        }else if(type == ADD_SITE){
            layoutSite.setVisibility(View.VISIBLE);
            layoutPhone.setVisibility(View.GONE);
            layoutFace.setVisibility(View.GONE);
            layoutPlus.setVisibility(View.GONE);
        }else if(type == ADD_FACE){
            layoutFace.setVisibility(View.VISIBLE);
            layoutSite.setVisibility(View.GONE);
            layoutPhone.setVisibility(View.GONE);
            layoutPlus.setVisibility(View.GONE);
        }else if(type == ADD_PLUS){
            layoutPlus.setVisibility(View.VISIBLE);
            layoutSite.setVisibility(View.GONE);
            layoutPhone.setVisibility(View.GONE);
            layoutFace.setVisibility(View.GONE);
        }
    }

    public void closeLeftDrawer(){
        drawerLayoutLeft.closeDrawer(drawerLeft);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onPostExecute(String result) {
        startActivity(getIntent());
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0) {
                return GeneralFragment.newInstance(position + 1, resultPlaces, place);
            }else{
                return PricesFragment.newInstance(position + 1, resultPlaces, place);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PricesFragment extends Fragment implements AddPriceTask.OnPostExecuteListener, ListPricesTask.OnPostExecuteListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static ResultPlaces resultPlaces;
        public static Place place;
        private List <Price> itemPricesList;
        private LayoutInflater inflater;
        private View rootView;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PricesFragment newInstance(int sectionNumber, ResultPlaces resultPlacesNew, Place newPlace) {
            resultPlaces = resultPlacesNew;
            place = newPlace;
            PricesFragment fragment = new PricesFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PricesFragment() {
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final InfosActivity infosActivity = ((InfosActivity) getActivity());

            Price price = infosActivity.getItemSubmited();
            new ListPricesTask(infosActivity, price, PricesFragment.this).execute();

            itemPricesList = new ArrayList<Price>();

            this.inflater = inflater;

            rootView = inflater.inflate(R.layout.fragment_prices, container, false);
            final LinearLayout containerPrices = (LinearLayout) rootView.findViewById(R.id.containerPrices);

            LinearLayout addItem = (LinearLayout) rootView.findViewById(R.id.addPrice);
            addItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infosActivity.openRightDrawer();
                }
            });

            generateList(inflater, containerPrices, itemPricesList);

            Button button = (Button) infosActivity.findViewById(R.id.savePrice);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Price price = infosActivity.getItemSubmited();
                    new AddPriceTask(infosActivity, price, PricesFragment.this).execute();

                    infosActivity.closeRightDrawer();
                    infosActivity.resetView();
                }
            });

            return rootView;
        }

        private void generateList(LayoutInflater inflater, LinearLayout containerPrices, List<Price> itemPricesList) {
            if(itemPricesList != null) {
                containerPrices.removeAllViews();
                for (Price itemPrices : itemPricesList) {

                    View itemLista = inflater.inflate(R.layout.adapter_template_list_prices, null);
                    TextView item = (TextView) itemLista.findViewById(R.id.item);
                    item.setText(itemPrices.getName());
                    TextView description = (TextView) itemLista.findViewById(R.id.description);
                    description.setText(itemPrices.getDescription());
                    TextView price = (TextView) itemLista.findViewById(R.id.price);
                    price.setText(itemPrices.getPrice());

                    containerPrices.addView(itemLista);
                }
            }
        }

        @Override
        public void onPostExecute(List<Price> result) {
            final LinearLayout containerPrices = (LinearLayout) rootView.findViewById(R.id.containerPrices);
            generateList(inflater, containerPrices, result);
        }
    }

    public static class GeneralFragment extends Fragment implements DetailsApiTask.OnPostExecuteListener, DetailsNativeTask.OnPostExecuteListener, DetailsApiEditedTask.OnPostExecuteListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static ResultPlaces resultPlaces;
        private static Place place;
        private ImageView addPhone;
        private ImageView addSite;
        private ImageView facebookIcon;
        private ImageView plusIcon;
        private String plusUrl;
        private String facebookUrl;

        private TextView textViewName;
        private TextView textViewPhone;
        private TextView textViewAddress;
        private TextView textViewSite;
        private TextView textViewTags;

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static GeneralFragment newInstance(int sectionNumber, ResultPlaces newResultPlaces, Place newPlace) {
            resultPlaces = newResultPlaces;
            place = newPlace;
            GeneralFragment fragment = new GeneralFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public GeneralFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final InfosActivity infosActivity = ((InfosActivity) getActivity());

            final View rootView = inflater.inflate(R.layout.fragment_infos, container, false);
            textViewName = (TextView) rootView.findViewById(R.id.name);
            textViewPhone = (TextView) rootView.findViewById(R.id.phone);
            textViewAddress = (TextView) rootView.findViewById(R.id.address);
            textViewSite = (TextView) rootView.findViewById(R.id.site);
            textViewTags = (TextView) rootView.findViewById(R.id.type);

            addPhone = (ImageView) rootView.findViewById(R.id.addPhone);
            addSite = (ImageView) rootView.findViewById(R.id.addSite);

            facebookIcon = (ImageView) rootView.findViewById(R.id.facebook_icon);
            plusIcon = (ImageView) rootView.findViewById(R.id.plus_icon);

            facebookIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(facebookUrl != null) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
                        }catch (Exception e){
                            Toast.makeText(infosActivity, "url mal formada", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        infosActivity.openLeftDrawer(ADD_FACE);
                    }
                }
            });

            plusIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(plusUrl != null) {
                        try{
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(plusUrl)));
                        }catch (Exception e){
                            Toast.makeText(infosActivity, "url mal formada", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        infosActivity.openLeftDrawer(ADD_PLUS);
                    }
                }
            });

            addPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infosActivity.openLeftDrawer(ADD_PHONE);
                }
            });

            addSite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    infosActivity.openLeftDrawer(ADD_SITE);
                }
            });

            ImageView imageView = (ImageView) rootView.findViewById(R.id.photo);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ScrollView scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
                    scrollView.setVisibility(View.INVISIBLE);
                    ImageView expandImageVisibility = (ImageView) rootView.findViewById(R.id.img_expand);
                    expandImageVisibility.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }
                    });
                    expandImageVisibility.setVisibility(View.VISIBLE);
                    ImageView expandImage = (ImageView) rootView.findViewById(v.getId());
                    Drawable d = expandImage.getDrawable();
                    Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    d.draw(canvas);
                    expandImageVisibility.setImageBitmap(bitmap);

                    DisplayMetrics metrics = new DisplayMetrics();
//                    InfosActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    expandImageVisibility.setMinimumHeight(metrics.heightPixels);
                    expandImageVisibility.setMinimumWidth(metrics.widthPixels);
                }
            });
            ProgressBar loading = (ProgressBar) rootView.findViewById(R.id.loading);

            ImageView imageStreetView = (ImageView) rootView.findViewById(R.id.street_view);

            ImageView imageNavigation = (ImageView) rootView.findViewById(R.id.navigation);

            if(resultPlaces != null && place == null) {
                textViewName.setText(resultPlaces.getName());
                textViewTags.setText(resultPlaces.getTypes().toString());

                imageStreetView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), StreetViewActivity.class);
                        intent.putExtra("lat", resultPlaces.getGeometry().getLocation().getLat());
                        intent.putExtra("lng", resultPlaces.getGeometry().getLocation().getLng());
                        startActivity(intent);
                    }
                });

                imageNavigation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double lat = resultPlaces.getGeometry().getLocation().getLat();
                        Double lng = resultPlaces.getGeometry().getLocation().getLng();
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng + ""));
                        startActivity(intent);
                    }
                });
                new DetailsApiTask(resultPlaces.getPlaceId(), textViewPhone, textViewAddress, textViewSite, imageView, loading, getActivity(), this).execute();

            }else if(resultPlaces == null && place != null) {
                textViewName.setText(place.getName());
                if(place.getType() == 0){
                    textViewTags.setText("Restaurante");
                }else if(place.getType() == 1){
                    textViewTags.setText("Estacionamento");
                }else{
                    textViewTags.setText("Posto");
                }

                imageStreetView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), StreetViewActivity.class);
                        intent.putExtra("lat", place.getLat());
                        intent.putExtra("lng", place.getLng());
                        startActivity(intent);
                    }
                });

                imageNavigation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double lat = place.getLat();
                        Double lng = place.getLng();
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng + ""));
                        startActivity(intent);
                    }
                });
                new DetailsNativeTask(place.getId(), textViewPhone, textViewAddress, textViewSite, imageView, loading, getActivity(), this).execute();

            }else{
            }

            return rootView;
        }

        @Override
        public void onPostExecute(DetailsApiResult detailsApiResult) {

            plusIcon.setImageResource(R.drawable.plus_off);
            facebookIcon.setImageResource(R.drawable.facebook_off);

            final InfosActivity infosActivity = ((InfosActivity) getActivity());
            new DetailsApiEditedTask(infosActivity, new PlaceApiEdited(resultPlaces.getPlaceId()), this).execute();

            if(detailsApiResult != null) {

                if (detailsApiResult.getResult().getFormattedPhoneNumber() == null) {
                    addPhone.setVisibility(View.VISIBLE);
                    textViewPhone.setText(getString(R.string.phoneUnavaible));
                }

                if (detailsApiResult.getResult().getWebsite() == null) {
                    addSite.setVisibility(View.VISIBLE);
                    textViewSite.setText(getString(R.string.site_indisponivel));
                }

                if (detailsApiResult.getResult().getUrl() != null) {
                    plusIcon.setImageResource(R.drawable.plus_on);
                    plusUrl = detailsApiResult.getResult().getUrl();
                }
            }

        }

        @Override
        public void onPostExecute(Place place) {
            plusIcon.setImageResource(R.drawable.plus_off);
            facebookIcon.setImageResource(R.drawable.facebook_off);
            if(place != null) {
                if (place.getPhone() == null || place.getPhone().equals("")) {
                    addPhone.setVisibility(View.VISIBLE);
                    textViewPhone.setText(getString(R.string.phoneUnavaible));
                }
                if (place.getSite() == null || place.getSite().equals("")) {
                    addSite.setVisibility(View.VISIBLE);
                    textViewSite.setText(getString(R.string.site_indisponivel));
                }
                if (place.getPlusPage() != null && !place.getPlusPage().equals("")) {
                    plusIcon.setImageResource(R.drawable.plus_on);
                    plusUrl = place.getPlusPage();
                }
                if (place.getFacebookPage() != null && !place.getFacebookPage().equals("")) {
                    facebookIcon.setImageResource(R.drawable.facebook_on);
                    facebookUrl = place.getFacebookPage();
                }
            }
        }

        @Override
        public void onPostExecute(PlaceApiEdited placeApiEdited) {
            if(placeApiEdited != null) {
                if (placeApiEdited.getPhone() != null) {
                    textViewPhone.setText(placeApiEdited.getPhone());
                    addPhone.setVisibility(View.GONE);
                }
                if (placeApiEdited.getSite() != null) {
                    addSite.setVisibility(View.GONE);
                    textViewSite.setText(placeApiEdited.getSite());
                }

                if (placeApiEdited.getFacebookPage() != null) {
                    facebookIcon.setImageResource(R.drawable.facebook_on);
                    facebookUrl = placeApiEdited.getFacebookPage();
                }
            }
        }
    }
}
