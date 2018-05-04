package hci.univie.ac.at.a3_yawa;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CityScreen extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private TextView tabCityField;
    private TextView tabMinTempField;

    public static RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_screen);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));





 /*
        // Get the selected city
        Bundle intentExtras = getIntent().getExtras();
        String selectedCity = intentExtras.getString("selectedCity");


       // Get the weather data for the selected city
        WeatherData weatherData = new WeatherData();
        String tabMinTempValue = weatherData.getMinTemp(selectedCity);
        // Append the values to the city tabs
        // City name
        tabCityField = (TextView) findViewById(R.id.tabCityField);
        tabCityField.setText(selectedCity);
        // Min Temp
        tabMinTempField = (TextView) findViewById(R.id.tabMinTempField);
        tabMinTempField.setText(tabMinTempValue);

*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city_screen, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_SELECTED_CITY = "selected_city";


        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, String selectedCity) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putString(ARG_SELECTED_CITY, selectedCity);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_city_screen, container, false);

            // Variables
            String selectedCity = getArguments().getString(ARG_SELECTED_CITY);
            final int dayNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            Calendar cal = Calendar .getInstance();
            SimpleDateFormat dateFt = new SimpleDateFormat("yyyy-MM-dd");
            int dayNumberTemp = dayNumber - 1;
            cal.add(Calendar.DATE, dayNumberTemp);
            String formattedDate = dateFt.format(cal.getTime());

            // Tab date field
            TextView textView = (TextView) rootView.findViewById(R.id.tabDateField);
            textView.setText(formattedDate);

            // City Field
            TextView tabCityField = (TextView) rootView.findViewById(R.id.tabCityField);
            tabCityField.setText(selectedCity);
            // Temp Fields
            final TextView tabMinTempField = (TextView) rootView.findViewById(R.id.tabMinTempField);
            final TextView tabAvTempField = (TextView) rootView.findViewById(R.id.tabAvTempField);
            final TextView tabMaxTempField = (TextView) rootView.findViewById(R.id.tabMaxTempField);
            final TextView tabDescriptionField = (TextView) rootView.findViewById(R.id.tabDescriptionField);

            // API URL for the selected city
            String url ="http://api.openweathermap.org/data/2.5/forecast?q="+selectedCity+"&units=metric&appid=b0658d9ad0e125e53099193d59751360";

            // Get the weather data
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            WeatherData weatherData = new WeatherData();
                            String tabMinTempValue = null;
                            String tabAvTempValue = null;
                            String tabMaxTempValue = null;
                            String tabDescription = null;
                            try {
                                tabMinTempValue = weatherData.getMinTemp(response, dayNumber);
                                tabAvTempValue = weatherData.getAvTemp(response, dayNumber);
                                tabMaxTempValue = weatherData.getMaxTemp(response, dayNumber);
                                tabDescription = weatherData.getDescription(response, dayNumber);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            tabMinTempField.setText(tabMinTempValue);
                            tabAvTempField.setText(tabAvTempValue);
                            tabMaxTempField.setText(tabMaxTempValue);
                            tabDescriptionField.setText(tabDescription);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    tabDescriptionField.setText("There is a problem with loading the data.");
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);


            //tabMinTempField.setText(tabMinTempValue);

            return rootView;
        }
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            // Get the selected city
            Bundle intentExtras = getIntent().getExtras();
            String selectedCity = intentExtras.getString("selectedCity");

            return PlaceholderFragment.newInstance(position + 1, selectedCity);
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }
    }
}
