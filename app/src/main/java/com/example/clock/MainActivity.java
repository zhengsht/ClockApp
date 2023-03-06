package com.example.clock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private clock myclock;
    private final int INIT = 1;
    private final int MSG_TIME = 2;
    int flag = 0;

    LunarDate mLunar = new LunarDate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//继续之前app的状态
        setContentView(R.layout.cover);//加载布局文件，作为初始界面显示,启动界面
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置窗体全屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//阻止休眠

        mHandler.sendEmptyMessageDelayed(INIT,1500);//显示启动界面1.5s后开始app主页面初始化
    }

    //日期更新函数
    private void updatedate(){
        ImageView bg = findViewById(R.id.bg);
        bg.setImageAlpha(60);
        int yyyy = Calendar.getInstance().get(Calendar.YEAR);
        int mm = Calendar.getInstance().get(Calendar.MONTH)+1;
        int dd = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        int week = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        TextView Time_year = findViewById(R.id.Time_year);
        TextView Time_month = findViewById(R.id.Time_month);
        TextView Time_day = findViewById(R.id.Time_day);
        TextView Time_week = findViewById(R.id.Time_week);
        TextView Time_week_E = findViewById(R.id.Time_week_E);
        Time_year.setText(""+yyyy);


        if(mm<10){
            Time_month.setText("0"+mm);
        }else{
            Time_month.setText(""+mm);
        }

        if(dd<10){
            Time_day.setText("0"+dd);
        }else{
            Time_day.setText(""+dd);
        }
        switch (week){
            case 0:Time_week.setText("星期六");Time_week_E.setText("Saturday");break;
            case 1:Time_week.setText("星期日");Time_week_E.setText("Sunday");break;
            case 2:Time_week.setText("星期一");Time_week_E.setText("Monday");break;
            case 3:Time_week.setText("星期二");Time_week_E.setText("Tuesday");break;
            case 4:Time_week.setText("星期三");Time_week_E.setText("Wednesday");break;
            case 5:Time_week.setText("星期四");Time_week_E.setText("Thursday");break;
            case 6:Time_week.setText("星期五");Time_week_E.setText("Friday");break;
        }

        mLunar.setCurrentDate(yyyy,mm,dd);
        TextView lunardate = findViewById(R.id.lunardate);
        TextView lunaryear = findViewById(R.id.tbyear);
        lunardate.setText(mLunar.getLunarDate());
        lunaryear.setText(mLunar.getLunarYear());
    }

    //1s为周期，更新时间的消息请求
    private void initview(){
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                //更新时间
                int h= Calendar.getInstance().get(Calendar.HOUR_OF_DAY); //获取当前时间，Calendar.hour_of_day24小时制
                int m= Calendar.getInstance().get(Calendar.MINUTE);
                int s= Calendar.getInstance().get(Calendar.SECOND);
                int t=h*3600+m*60+s;
                if (t<0) t+=24*3600;
                Message msg_time=new Message();
                msg_time.what=MSG_TIME;
                msg_time.arg1=(int)(t);
                mHandler.sendMessage(msg_time);
            }
        },0,1000);
    }

    //消息处理函数
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case INIT://初始化更新主界面
                    setContentView(R.layout.activity_main);
                    updatedate();
                    initview();
                case MSG_TIME: //更新时间显示
                    TextView clock1=findViewById(R.id.Time_Hour_H);//载入时间显示的组件
                    TextView clock2=findViewById(R.id.Time_Hour_L);//载入时间显示的组件
                    TextView clock3=findViewById(R.id.Time_Min_H);//载入时间显示的组件
                    TextView clock4=findViewById(R.id.Time_Min_L);//载入时间显示的组件
                    int sum,hour,min,sec;
                    sum=msg.arg1;
                    if(sum>24*3600) sum-=24*3600;
                    hour=(int)Math.floor(sum/3600);//Math.floor() 返回小于或等于一个给定数字的最大整数。向下取整
                    min=(int)Math.floor((sum-hour*3600)/60);
                    sec=(int)Math.floor(sum-hour*3600-min*60);
                    clock1.setText(""+(int)hour/10);
                    clock2.setText(""+(int)hour%10);

                    clock3.setText(""+(int)min/10);
                    clock4.setText(""+(int)min%10);

                    myclock = findViewById(R.id.clock);
                    myclock.hour = hour;
                    myclock.min = min;
                    myclock.sec = sec;
                    if(hour>12) myclock.hour = hour -12;
                    myclock.invalidate();
                    //判断是否需要更新日期
                    if(hour==0 && min==0 && sec==0) updatedate();
                    break;
            }
            return false;
        }
    });

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0,0);

    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}