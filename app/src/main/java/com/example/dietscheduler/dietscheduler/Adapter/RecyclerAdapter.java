package com.example.dietscheduler.dietscheduler.Adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dietscheduler.dietscheduler.AlertReciever;
import com.example.dietscheduler.dietscheduler.MainActivity;
import com.example.dietscheduler.dietscheduler.Model.Week_diet_data;
import com.example.dietscheduler.dietscheduler.R;

import java.util.Calendar;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Week_diet_data> dataList;
    Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, body, time;
        public ImageView user_pic;

        public MyViewHolder(View view) {
            super(view);
            title=(TextView)view.findViewById(R.id.textView);
            body=(TextView)view.findViewById(R.id.textView2);
            time=(TextView)view.findViewById(R.id.textView3);
        }
    }

    public RecyclerAdapter(List<Week_diet_data> dataList, Context context) {
        this.dataList = dataList;
        this.mcontext=context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview=LayoutInflater.from(parent.getContext()).inflate(R.layout.data_content,parent,false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final Week_diet_data data = dataList.get(position);
        holder.title.setText(data.getFood());
        holder.body.setText(data.getTime());
        holder.time.setText(data.getDay());
        Handler handler = new Handler();
        int delay = 600; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                Calendar cc = Calendar.getInstance();
                int mHour = cc.get(Calendar.HOUR_OF_DAY);
                int mMinute = cc.get(Calendar.MINUTE);
                String hour= String.valueOf(mHour);
                String min=String.valueOf(mMinute);
                String timer=hour+":"+min;
                if (timer.equals(timer)){
                    startAlarm(cc);
                }
            }
        }, delay);


    }
    public void startAlarm(Calendar calendar){
        Intent intent = new Intent(mcontext, AlertReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mcontext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager=(AlarmManager) mcontext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000, pendingIntent);
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
