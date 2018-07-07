package com.autokartz.autokartz.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.autokartz.autokartz.R;
import com.autokartz.autokartz.dialoges.SignOutDialog;
import com.autokartz.autokartz.fragments.AutoCashFragment;
import com.autokartz.autokartz.fragments.CompanyURLFragment;
import com.autokartz.autokartz.fragments.ContactUsFragment;
import com.autokartz.autokartz.fragments.EnquiryFormFragment;
import com.autokartz.autokartz.fragments.EnquiryFormsFragment;
import com.autokartz.autokartz.fragments.HomeFragment;
import com.autokartz.autokartz.fragments.MyAccountFragment;
import com.autokartz.autokartz.fragments.OrdersFragment;
import com.autokartz.autokartz.interfaces.FcmTokenResponseListsner;
import com.autokartz.autokartz.interfaces.GetImageListener;
import com.autokartz.autokartz.interfaces.SignOutResponseListener;
import com.autokartz.autokartz.services.databases.LocalDatabase.DatabaseCURDOperations;
import com.autokartz.autokartz.services.databases.preferences.AccountDetailHolder;
import com.autokartz.autokartz.services.webServices.apiRequests.FcmTokenApi;
import com.autokartz.autokartz.utils.apiResponses.UserDetailBean;
import com.autokartz.autokartz.utils.pojoClasses.CarInformation;
import com.autokartz.autokartz.utils.pojoClasses.CategoryInformation;
import com.autokartz.autokartz.utils.pojoClasses.UserNotificationCount;
import com.autokartz.autokartz.utils.util.AppToast;
import com.autokartz.autokartz.utils.util.CheckPermission;
import com.autokartz.autokartz.utils.util.Logger;
import com.autokartz.autokartz.utils.util.converter.CSVFile;
import com.autokartz.autokartz.utils.util.dialogs.DismissDialog;
import com.autokartz.autokartz.utils.util.dialogs.ShowDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainDashboardActivity extends AppCompatActivity implements GetImageListener, SignOutResponseListener, FcmTokenResponseListsner {
    @BindView(R.id.main_nav_drawer_layout)
    DrawerLayout mNavDrawerLayout;
    @BindView(R.id.nav_toolbar)
    Toolbar mToolbar;
    TextView mEmailNavbar;
    TextView mDisplayNameNavbar;
    TextView mLetterNameNavbar;
    NavigationView mNavView;
    private static final String LOG_TAG = MainDashboardActivity.class.getName();
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DatabaseCURDOperations databaseCURDOperations;
    private static int NAV_ITEM_INDEX = 0;
    private Fragment fragment;
    private Context mContext;
    private List carInfoList, catInfoList;
    private ActionBar mActionBar;
    private AccountDetailHolder mAccountDetailHolder;
    private String mCurrentPhotoPath;
    private UserDetailBean mUserDetailBean;
    private ProgressDialog mProgressDialog;
    private static final String HOME_TAG = HomeFragment.class.getName();
    String encoded;
    InterstitialAd mInterstitialAd;
    ViewPager viewPager;
    TabLayout indicator;
    List<Integer> imageSlider;
    BottomNavigationView navigation;
    TextView notifiaction_count;
    int notification_count;
    int countTotal = 0;
    int totalCount;
    ArrayList<UserNotificationCount> mUserNotificationCount = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard2);
        ButterKnife.bind(this);
        this.mContext = MainDashboardActivity.this;
        init();
    }

    private void init() {
        CheckPermission.checkAndRequestPermissions(mContext);
        setNavigationBar();
        setToolBar();
        setActionBarDrawer();
        // initMobileAds();
        initViews();
        initVariables();
        handleNavigationSelectedItem();
        openFragment(new HomeFragment());
        setViews();
    }

    private void setNavigationBar() {
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    NAV_ITEM_INDEX = 0;
                    break;
                case R.id.navigation_enquiry:
                    fragment = new EnquiryFormFragment();
                    NAV_ITEM_INDEX = 7;
                    break;
                case R.id.navigation_orders:
                    fragment = new OrdersFragment();
                    NAV_ITEM_INDEX = 8;
                    break;
                case R.id.navigation_myaccount:
                    fragment = new MyAccountFragment();
                    NAV_ITEM_INDEX = 9;
                    break;
                case R.id.navigation_autocash:
                    fragment = new AutoCashFragment();
                    NAV_ITEM_INDEX = 10;
                    break;
            }
            return openFragment(fragment);
        }
    };

    private void initMobileAds() {
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mInterstitialAd = new InterstitialAd(mContext);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void setToolBar() {
        mToolbar.setTitleTextColor(getResources().getColor(R.color.orange));
        setSupportActionBar(mToolbar);
    }

    private void setActionBarDrawer() {
        setActionBarDrawerToggle();
        mActionBar = getSupportActionBar();
        mActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.White)));
    }

    private void setActionBarDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mNavDrawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };
        mNavDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initViews() {

        //imageSlideTimer();
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        View header = mNavView.getHeaderView(0);
        mEmailNavbar = (TextView) header.findViewById(R.id.nav_email_tv);
        mLetterNameNavbar = (TextView) header.findViewById(R.id.nav_firstletter_tv);
        mDisplayNameNavbar = (TextView) header.findViewById(R.id.nav_displayname_tv);
        notifiaction_count = (TextView) MenuItemCompat.getActionView(mNavView.getMenu().
                findItem(R.id.nav_enquiry_form));
    }

    private void initVariables() {
        mContext = getApplicationContext();
        mAccountDetailHolder = new AccountDetailHolder(mContext);
        mUserDetailBean = mAccountDetailHolder.getUserDetailBean();
        fcmToken();
        notificationintent();
        sendNotification();
        carInfoList = new ArrayList<>();
        catInfoList = new ArrayList();
        databaseCURDOperations = new DatabaseCURDOperations(mContext);
        if (!mAccountDetailHolder.getIsCarInfoLoaded()) {
            mProgressDialog = ShowDialog.show(MainDashboardActivity.this, "", "Loading Data", true, false);
            ReadExcelFile readExcelFile = new ReadExcelFile();
            readExcelFile.execute();
        }
    }

    private void sendNotification() {
        String fragment_name = getIntent().getStringExtra("fragment_name");
        String enquiry_id = getIntent().getStringExtra("enquiry_id");
        if (fragment_name != null && fragment_name.matches("PartSuggestionFragment")) {
            Fragment fragment1 = new EnquiryFormsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("enquiryId", enquiry_id);
            fragment1.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_frame, fragment1).commit();

        }
    }


    private void notificationintent() {
        mUserNotificationCount = mAccountDetailHolder.getNotificationCount();
        for (int i = 0; i < mUserNotificationCount.size(); i++) {
            notification_count = Integer.parseInt(mUserNotificationCount.get(i).getUserNotificationCount());
            totalCount = countTotal += notification_count;
            notifiaction_count.setGravity(Gravity.CENTER_VERTICAL);
            notifiaction_count.setTypeface(null, Typeface.BOLD);
            notifiaction_count.setTextColor(getResources().getColor(R.color.appcolorornage));
            notifiaction_count.setText(String.valueOf(totalCount));
            String textNotification = String.valueOf(notifiaction_count.getText());
            if (textNotification.matches("0")) {
                notifiaction_count.setVisibility(View.GONE);
            }
        }
    }

    private void fcmToken() {
        String token = FirebaseInstanceId.getInstance().getToken();
        String user_id = mUserDetailBean.getUserId();
        if (token != null && user_id != null) {
            token.length();
            FcmTokenApi fcmTokenAPI = new FcmTokenApi(mContext, this);
            fcmTokenAPI.callFcmTokenApi(token, user_id);
        }
    }

    private void handleNavigationSelectedItem() {
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        MenuItem itemHome = navigation.getMenu().getItem(0);//set bottom navigation wrt navigation drawer
                        navigation.setSelectedItemId(itemHome.getItemId());
                        NAV_ITEM_INDEX = 0;
                        break;
                    case R.id.nav_enquiry_form:
                        fragment = new EnquiryFormsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("enquiryId", "");
                        notifiaction_count.setVisibility(View.GONE);
                        fragment.setArguments(bundle);
                        NAV_ITEM_INDEX = 1;
                        break;
                    case R.id.nav_orders:
                        fragment = new OrdersFragment();
                        MenuItem itemOrder = navigation.getMenu().getItem(2);//set bottom navigation wrt navigation drawer
                        navigation.setSelectedItemId(itemOrder.getItemId());
                        NAV_ITEM_INDEX = 2;
                        break;
                    case R.id.nav_myaccount:
                        fragment = new MyAccountFragment();
                        MenuItem itemAccount = navigation.getMenu().getItem(3);//set bottom navigation wrt navigation drawer
                        navigation.setSelectedItemId(itemAccount.getItemId());
                        NAV_ITEM_INDEX = 3;
                        break;
                    case R.id.nav_myautocash:
                        fragment = new AutoCashFragment();
                        MenuItem itemCash = navigation.getMenu().getItem(4);//set bottom navigation wrt navigation drawer
                        navigation.setSelectedItemId(itemCash.getItemId());
                        NAV_ITEM_INDEX = 4;
                        break;
                    case R.id.nav_companyurl:
                        fragment = new CompanyURLFragment();
                        NAV_ITEM_INDEX = 5;
                        break;
                    case R.id.nav_contact_us:
                        fragment = new ContactUsFragment();
                        NAV_ITEM_INDEX = 6;
                        break;
                    case R.id.nav_signout:
                        openSignOutDialog();
                        return false;
                }
                openFragment(fragment);
                mNavDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void openSignOutDialog() {
        SignOutDialog signOutDialog = new SignOutDialog(MainDashboardActivity.this, this);
        signOutDialog.show();
    }

    private boolean openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commitNowAllowingStateLoss();
        return true;
    }

    private void setViews() {
        String email = mUserDetailBean.getEmailId();
        String name = mUserDetailBean.getGarageOwnerName();
        mEmailNavbar.setText(email);
        char firstLetter = 0;
        if (!name.isEmpty()) {
            firstLetter = name.charAt(0);
        }
        mLetterNameNavbar.setText(String.valueOf(firstLetter));
        mDisplayNameNavbar.setText(name);
    }

    private void readCarInfoExcelFile() {
        try {
            CarInformation carInformation;
            String[] raw;
            InputStream inputStream = getResources().getAssets().open("carinfo_exp.csv");
            CSVFile csvFile = new CSVFile(inputStream);
            carInfoList = csvFile.read();
            Logger.LogDebug(LOG_TAG, carInfoList.size() + "");
            databaseCURDOperations.deleteCarInfo();
            for (int i = 1; i < carInfoList.size(); i++) {
                raw = (String[]) carInfoList.get(i);
                carInformation = new CarInformation(raw[0], raw[1], raw[2], raw[3], raw[4]);
                Logger.LogDebug(LOG_TAG, raw[0] + " " + raw[1] + " " + raw[2] + " " + raw[3] + " " + raw[4] + " ");
                databaseCURDOperations.insertCarInfo(carInformation);
            }
            mAccountDetailHolder.setKeyIsCarInfoLoaded(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 103) {
            if (data != null && data.getData() != null) {
                Uri uri = data.getData();
                mCurrentPhotoPath = getRealPathFromURI(uri);

            } else {
                if (data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    Uri uri = getImageUri(this, bitmap);
                    File finalFile = new File(getRealPathFromURI(uri));
                    mCurrentPhotoPath = finalFile.getPath();
                }
            }
            Intent i = new Intent("com.autokartz.custombroadcast");
            sendBroadcast(i);
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public String getImage(int pos) {
        if (mCurrentPhotoPath != null && !mCurrentPhotoPath.isEmpty()) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            ArrayList<CategoryInformation> selectedPartList = mAccountDetailHolder.getSelectedCarParts();
            CategoryInformation categoryInformation = selectedPartList.get(pos);
            ArrayList<String> imageList = categoryInformation.getmImagePathList();
            imageList.add(encoded);
            categoryInformation.setmImagePathList(imageList);
            selectedPartList.set(pos, categoryInformation);
            mAccountDetailHolder.setSelectedCarParts(selectedPartList);
        }
        return encoded;
    }

    @Override
    public void signoutResponse(boolean status) {
        if (status) {
            AppToast.showToast(mContext, "User LoggedOut");
            mAccountDetailHolder.clearAllSharedPreferences();
            mAccountDetailHolder.setUserLoggedIn(false);
            Intent loginIntent = new Intent(this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
        }
    }

    @Override
    public void fcmTokenResponse(boolean success) {
        if (success) {
        }
    }

    class ReadExcelFile extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            readCarInfoExcelFile();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            DismissDialog.dismissWithCheck(mProgressDialog);
        }

    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = navigation.getMenu().getItem(0);
        if (NAV_ITEM_INDEX == 0) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 0 && NAV_ITEM_INDEX != 0) {
            NAV_ITEM_INDEX = 0;
            getSupportActionBar().setTitle("Home");
            openFragment(new HomeFragment());
            navigation.setSelectedItemId(homeItem.getItemId());
        } else {
            super.onBackPressed();
        }
    }

}