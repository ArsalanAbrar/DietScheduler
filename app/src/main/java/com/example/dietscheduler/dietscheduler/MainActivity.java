package com.example.dietscheduler.dietscheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.dietscheduler.dietscheduler.Adapter.RecyclerAdapter;
import com.example.dietscheduler.dietscheduler.Constant.Constants;
import com.example.dietscheduler.dietscheduler.Model.Week_diet_data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Week_diet_data> week_diet_data=new ArrayList<Week_diet_data>();
    RecyclerView recyclerView;
    RecyclerAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        mAdapter = new RecyclerAdapter(week_diet_data,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);



        getDiet();

    }

//    public void startAlarm(){
//        Intent intent = new Intent(MainActivity.this, AlertReciever.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
//       // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000, pendingIntent);
//    }
    public void getDiet(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, "http://104.196.113.9/dummy/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                String res = response.toString();
                try {
            JSONObject jsonObject1=new JSONObject(res);
            String diet_time=jsonObject1.getString("diet_duration");
            JSONObject jsonObject_week=jsonObject1.getJSONObject("week_diet_data");
            JSONArray Thursday_schedule=jsonObject_week.getJSONArray("thursday");
            JSONArray  Wednesday_schedule=jsonObject_week.getJSONArray("wednesday");
            JSONArray Monday_schedule=jsonObject_week.getJSONArray("monday");
                    for (int i=0;i<Thursday_schedule.length();i++){
                        JSONObject j=(JSONObject)Thursday_schedule.get(i);
                             String food=j.getString("food");
                             String time=j.getString("meal_time");
                             Week_diet_data weekDietData=new Week_diet_data(food,time,"Thursday",4);
                             week_diet_data.add(weekDietData);
                        }
                    for (int i=0;i<Wednesday_schedule.length();i++){
                        JSONObject j=(JSONObject)Wednesday_schedule.get(i);
                        String food=j.getString("food");
                        String time=j.getString("meal_time");
                        Week_diet_data weekDietData=new Week_diet_data(food,time,"Wednesday",3);
                        week_diet_data.add(weekDietData);
                    }
                    for (int i=0;i<Monday_schedule.length();i++){
                        JSONObject j=(JSONObject)Monday_schedule.get(i);
                        String food=j.getString("food");
                        String time=j.getString("meal_time");
                        Week_diet_data weekDietData=new Week_diet_data(food,time,"Monday",1);
                        week_diet_data.add(weekDietData);
                    }
                    VolleyRequest.getInstance(getApplicationContext()).setAlarm_time(diet_time);

                    Collections.sort(week_diet_data, new Comparator<Week_diet_data>() {
                        @Override
                        public int compare(Week_diet_data lhs, Week_diet_data rhs) {
                            // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            return lhs.getId() > rhs.getId() ? 1 :  -1;
                        }
                    });



                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        VolleyRequest.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
