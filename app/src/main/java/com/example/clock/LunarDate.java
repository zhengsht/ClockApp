package com.example.clock;

import android.widget.TextView;

import androidx.versionedparcelable.VersionedParcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class LunarDate {
    final static long[] lunarInfo = new long[]{
            0x06AA4C, 0x0AD541, 0x24DAB6, 0x04B64A, 0x69573D, 0x0A4E51, 0x0D2646, 0x5E933A,
            0x0D534D, 0x05AA43,/*2021-2030*/
            0x36B537, 0x096D4B, 0xB4AEBF, 0x04AD53, 0x0A4D48, 0x6D25BC, 0x0D254F, 0x0D5244,
            0x5DAA38, 0x0B5A4C,/*2031-2040*/
            0x056D41, 0x24ADB6, 0x049B4A, 0x7A4BBE, 0x0A4B51, 0x0AA546, 0x5B52BA, 0x06D24E,
            0x0ADA42, 0x355B37,/*2041-2050*/
            0x09374B, 0x8497C1, 0x049753, 0x064B48, 0x66A53C, 0x0EA54F, 0x06B244, 0x4AB638,
            0x0AAE4C, 0x092E42,/*2051-2060*/
            0x3C9735, 0x0C9649, 0x7D4ABD, 0x0D4A51, 0x0DA545, 0x55AABA, 0x056A4E, 0x0A6D43,
            0x452EB7, 0x052D4B,/*2061-2070*/
            0x8A95BF, 0x0A9553, 0x0B4A47, 0x6B553B, 0x0AD54F, 0x055A45, 0x4A5D38, 0x0A5B4C,
            0x052B42, 0x3A93B6,/*2071-2080*/
            0x069349, 0x7729BD, 0x06AA51, 0x0AD546, 0x54DABA, 0x04B64E, 0x0A5743, 0x452738,
            0x0D264A, 0x8E933E,/*2081-2090*/
            0x0D5252, 0x0DAA47, 0x66B53B, 0x056D4F, 0x04AE45, 0x4A4EB9, 0x0A4D4C, 0x0D1541,
            0x2D92B5          /*2091-2099*/
    };

    final static String[] lunarMonthList = new String[]{
            "正月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","腊月"};

    final static String[] lunarDayList = new String[]{
            "初一","初二","初三","初四","初五","初六","初七","初八","初九","初十",
            "十一","十二","十三","十四","十五","十六","十七","十八","十九","二十",
            "廿一","廿二","廿三","廿四","廿五","廿六","廿七","廿八","廿九","三十"};

    final static String[] HeavenlyStems = new String[]{
            "庚","辛","壬","癸","甲","乙","丙","丁","戊","己"};

    final static String[] EarthlyStems = new String[]{
            "申","酉","戌","亥","子","丑","寅","卯","辰","巳","午","未"};

    private int year;
    private int month;
    private int day;
    int yearNumber;

    public void LunarDate(int year,int month,int day){
        this.year = year;
        this.month = month;
        this.day = day;
        yearNumber = this.year - 2021;
    }

    public void setCurrentDate(int year,int month,int day){
        this.year = year;
        this.month = month;
        this.day = day;
        yearNumber = this.year - 2021;
    }

    public void setYearNumber(int year){
        yearNumber = year - 2021;
    }

    public long getLeapMonth(){
        return lunarInfo[yearNumber]>>20;
    }

    public long monthSF(){
        return (lunarInfo[yearNumber]&0x000060)>>5;
    }

    public long daySF(){
        return (lunarInfo[yearNumber]&0x00001F);
    }

    public String getLunarDate(){
        int monthNumber = 1;
        boolean a = true;
        int daycount =
                (int) getIntervalDays(this.year,(int) monthSF(),(int) daySF(),
                        this.year,this.month,this.day);
        while(a){
            if(daycount>=0){
                if((lunarInfo[yearNumber]&((0x080000)>>(monthNumber-1)))==0){
                    if(daycount<=29) a = false;else {daycount = daycount-29;monthNumber++;}
                }else {
                    if(daycount<=30) a = false;else {daycount = daycount-30;monthNumber++;}
                }
            }else {
                setYearNumber(this.year-1);
                daycount =
                        (int) getIntervalDays(
                                this.year-1,
                                (int) monthSF(),
                                (int) daySF(),
                                this.year,this.month,this.day);
            }
        }
        int leapMonth = (int) getLeapMonth();
        int lunarMonth;
        String isleapmonth = "";
        if(monthNumber>leapMonth && leapMonth!=0) {
            lunarMonth = (int) monthNumber-1;
            if(lunarMonth==leapMonth) isleapmonth = "闰";else isleapmonth = "";
        }else {
            lunarMonth = (int) monthNumber;
        }
        return isleapmonth+lunarMonthList[lunarMonth-1]+lunarDayList[daycount];
    }

    public String getLunarYear(){
        return HeavenlyStems[this.year%10]+EarthlyStems[this.year%12]+"年";
    }

    public double getIntervalDays(int y1,int m1,int d1,int y2,int m2,int d2){
        String date1 = ""+y1;
        if(m1<10) date1 = date1+"0"+m1;else date1 = date1+m1;
        if(d1<10) date1 = date1+"0"+d1;else date1 = date1+d1;

        String date2 = ""+y2;
        if(m2<10) date2 = date2+"0"+m2;else date2 = date2+m2;
        if(d2<10) date2 = date2+"0"+d2;else date2 = date2+d2;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date data_1 = null;
        try{
            data_1 = simpleDateFormat.parse(date1);
        }catch (ParseException e){
            e.printStackTrace();
        }
        Date data_2 = null;
        try{
            data_2 = simpleDateFormat.parse(date2);
        }catch (ParseException e){
            e.printStackTrace();
        }
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(data_1);
        cal2.setTime(data_2);
        double dayCount = (cal2.getTimeInMillis() - cal1.getTimeInMillis())/(1000*3600*24);
        return dayCount;
    }//2-1

}
