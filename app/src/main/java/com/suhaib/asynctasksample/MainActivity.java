package com.suhaib.asynctasksample;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.ExecutorCompletionService;

public class MainActivity extends AppCompatActivity {

    TextView btnClick ;
    ListView itemsList ;
    ListViewAdapter adapter;
    Context context;
    boolean isGettingData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            btnClick = (TextView) findViewById(R.id.click_tv);
            itemsList = (ListView) findViewById(R.id.lv_items);
            context = this;
            btnClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isGettingData) {
                        isGettingData = true;
                        new GetItemsAsync().execute();
                    }
                }
            });
        } catch (Exception ex) {

        }
    }


    private class GetItemsAsync extends AsyncTask<Void, Void, HashMap<String, BigDecimal>> {


        @Override
        protected void onPreExecute() {
            /*
            * on pre execute, the background execution is not started yet
            * so we still can communicate with the UI main thread
            * */
            btnClick.setText("Please wait ...");
            super.onPreExecute();
        }

        @Override
        protected HashMap<String, BigDecimal> doInBackground(Void... voids) {
            try {
            /*
            * now the background execution is started, so we CANNOT communicate
            * with UI thread because the execution is performed in another thread.
            * In this sample we are simulating a call to web api that will take
            * one second then return a set of items and their prices to be
            * shown in the list view
            * */
                Thread.sleep(1000);
            }
            catch (Exception ex) {

            }
            return GetRandomItems();
        }

        @Override
        protected void onPostExecute(HashMap<String, BigDecimal> itemsHash) {
            /*
            * on post execute, the background execution is finished
            * so we can communicate with the UI main thread again
            * */
            btnClick.setText("Click me");
            adapter = new ListViewAdapter(itemsHash, context);
            itemsList.setAdapter(adapter);
            isGettingData = false;
            super.onPostExecute(itemsHash);
        }
    }

    private HashMap<String, BigDecimal> GetRandomItems() {
        /*
        * This method simulates a call to a web api
        * that returns a hash map of item name as a key
        * and the price as the value
        * */
        HashMap<String, BigDecimal> result = new HashMap<>();
        try {
            result.put("Item 1", BigDecimal.valueOf(10));
            result.put("Item 2", BigDecimal.valueOf(20));
            result.put("Item 3", BigDecimal.valueOf(30));
        } catch (Exception ex) {

        }
        return  result;
    }
}
