package com.sivakumaraketi.parser_json;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.Toast;

import com.sivakumaraketi.parser_json.Model.Results;

import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView lv;

   ArrayList<HashMap<String, String>> contactList;
   //ArrayList<Results> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetContacts().execute();

       // ListView lv = getListView();
        // listening to single list item on click
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // selected item
               // String team = ((TextView) view).getText().toString();
               // ArrayList<contactList> fileList = new ArrayList<>();
                // Launching new Activity on selecting single List Item
               // Results data = contactList.get(position);
              //  Intent i = new Intent(getApplicationContext(), NameResult.class);
                // sending data to new activity

             //   i.putExtra("data", data.name);

              //  startActivity(i);

            }
        });




    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

        }


        @Override
        protected Void doInBackground(Void... arg0) {
            JSONParser sh = new JSONParser();

            String url = "https://swapi.co/api/people/";

            // Making a request to url and getting response
           // String url = "https://swapi.co/api/people/?page="+counter+" ";

            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, " url: " + url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String nexturl = jsonObj.getString("next");
                    Log.e(TAG, "next url: " + nexturl);
                    if (nexturl != null){
                        url = nexturl;
                    }
                    // Getting JSON Array node
                    JSONArray results = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject c = results.getJSONObject(i);

                        String name = c.getString("name");
                        String height = c.getString("height");
                        String mass = c.getString("mass");
                        String birth_year = c.getString("birth_year");


                          // tmp hash map for single contact
                       HashMap<String, String> alldetails = new HashMap<>();


                       // Results alldetails = new Results();

                        // adding each child node to HashMap key => value
                       alldetails.put("height", height);
                        alldetails.put("name", name);
                        alldetails.put("mass", mass);
                        alldetails.put("birth_year", birth_year);

                        contactList.add(alldetails);

/*                        alldetails.name = name;
                        alldetails.height = height;
                        alldetails.mass = mass;
                        alldetails.birth_year = birth_year;
                        contactList.add(alldetails);
*/

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactList,
                    R.layout.list_items, new String[]{ "name"},
                   new int[]{R.id.name});

            lv.setAdapter(adapter);

        }





    }


}