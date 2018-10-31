import java.util.Scanner;

public class Test {

    private long timeStamp;
    private long time;


    public static void main(String[] args) {
        Test test = new Test();
        test.start();
    }

    void start() {
        ToTime toTime = new ToTime(timeStamp);
        ToTimeStamp toTimeStamp = new ToTimeStamp(time);
        TimeDifference timeDifference = new TimeDifference();

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("输入序号以执行一个操作:1.时间戳转日期; 2.日期转时间戳; 3.计算两个日期的时差;4.结束");
            String choice = in.nextLine();
            switch (choice) {
                case "1":
                    toTime.inputTimeStamp();//调用此方法
                    start();
                case "2":
                    toTimeStamp.inputTime();
                    start();
                case "3":
                    timeDifference.timeDifference();
                    start();
                case "4":
                    System.exit(0);
            }
            continue;//如输出123以外的数据,重新执行此循环;
        }
    }
}

class ToTime {
    private long timeStamp;
    private long year = 1970;
    private long month;
    private long day;
    private long hour;
    private long minute;
    private long second;
    private long i = 0;
    private int j = 0;
    private long[] dayOfMonth = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    private long[] dayOfMonthInLeapYear = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};


    public ToTime(long ts) {
        timeStamp = ts;
    }

    void inputTimeStamp() {
        Scanner in = new Scanner(System.in);
        System.out.print("输入一个long类型的时间戳:");
        timeStamp = in.nextLong();
        timeStamp = timeStamp + 28800;
        ToTime toTime = new ToTime(timeStamp);
        toTime.timeStampToTime();
    }

    private void timeStampToTime() {
        ToTime toTime = new ToTime(timeStamp);
        toTime.calculateYear();
        toTime.calculateMonth();
        toTime.calculateDay();
        toTime.calculateSecond();
        toTime.calculateMinute();
        toTime.calculateHour();
        toTime.outputDate();
        i = 0;
        j = 0;
    }


    private void calculateYear() {
        i = timeStamp;
        if (i % 86400 == 0)
            i = i / 86400 + 1;
        else
            i = i / 86400 + 1;
        while (i >= 365) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                if (i >= 366) {
                    year = year + 1;
                    i = i - 366;
                }
            } else {
                i = i - 365;
                year = year + 1;
            }
        }
        if (i == 0)
            i = 1;
    }

    private void calculateMonth() {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            while (j <= 11) {
                if (i - dayOfMonthInLeapYear[j] <= 0)
                    break;
                j = j + 1;
            }
            month = j;
        } else {
            while (j <= 11) {
                if (i - dayOfMonth[j] <= 0)
                    break;
                j = j + 1;
            }
            month = j;
        }
    }

    private void calculateDay() {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            day = i - dayOfMonthInLeapYear[j - 1];
        } else {
            day = i - dayOfMonth[j - 1];
        }
    }

    private void calculateHour() {
        hour = (timeStamp % 86400) / 3600;
    }

    private void calculateMinute() {
        minute = (timeStamp % 3600) / 60;
    }

    private void calculateSecond() {
        second = timeStamp % 60;
    }

    private void outputDate() {
        System.out.println("转换所得的时间为：" + year + "年" + month + "月" + day + "日" + hour + "点" + minute + "分" + second + "秒");
    }

}

class ToTimeStamp {
    private long time;
    private long year;
    private long month;
    private long day;
    private long hour;
    private long minute;
    private long second;
    private long yearTS = 0;//time stamp;
    private long monthTS;
    private long dayTS;
    private long hourTS;
    private long minuteTS;
    private long secondTS;
    private int m;
    private long timeStamp;
    private String displayTime;
    private long[] dayOfMonth = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    private long[] dayOfMonthInLeapYear = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

    public ToTimeStamp(long t) {
        time = t;
    }

    void inputTime() {
        Scanner in = new Scanner(System.in);
        System.out.println("输入一个十一至十四位数的时间值(如:20181014094530，注意月份等数值的大小）：");
        time = in.nextLong();
        if ((time >= (Math.pow(10, 14))) || (time <= (1111111111 * 9))) {
            System.out.println("你输入了一个不符合要求的数值！请重新输入！数据不符合要求");
            inputTime();
        }
        second = time % 100;
        minute = time % 10000 / 100;
        hour = time % 1000000 / 10000;
        day = time / 1000000 % 100;
        month = time / 100000000 % 100;
        year = time / 100000000 / 100;
        m = (int) month;
        if (month > 12 || minute > 59 || second > 59 || day == 0 || month == 0 || (day - dayOfMonthInLeapYear[m - 1] > dayOfMonthInLeapYear[m]) || hour >= 24) {
            System.out.println("你输入了一个不符合要求的数值！请重新输入！部分数据不符合要求");
            inputTime();
        }
        displayTime = "输入的时间为：" + year + "年" + month + "月" + day + "日" + hour + "点" + minute + "分" + second + "秒";
        System.out.println(displayTime);
        timeToTimeStamp();
    }

    private void timeToTimeStamp() {
        calculateYear();
        calculateMonth();
        calculateDay();
        calculateHour();
        calculateMinute();
        calculateSecond();
        outputTimeStamp();
    }

    private void calculateYear() {
        yearTS = (year - 1970) * 31536000;
        long i;
        if (year > 1972)
            i = (year - 1972) / 4 + 1;
        else
            i = (year - 1972) / 4;
        yearTS = yearTS + i * 86400;
    }

    private void calculateMonth() {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            monthTS = 86400 * dayOfMonthInLeapYear[m - 1];
        else
            monthTS = 86400 * dayOfMonth[m - 1];
    }

    private void calculateDay() {
        dayTS = (day - 1) * 86400;
    }

    private void calculateHour() {
        hourTS = hour * 3600 - 28800;
    }

    private void calculateMinute() {
        minuteTS = minute * 60;
    }

    private void calculateSecond() {
        secondTS = second;
    }

    private void outputTimeStamp() {
        timeStamp = yearTS + monthTS + dayTS + hourTS + minuteTS + secondTS;
        System.out.println("转化后的时间戳为：" + timeStamp);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getDisplayTime() {
        return displayTime;
    }
}

class TimeDifference {
    private long time;
    private long timeStamp1;
    private long timeStamp2;
    private long timeStampDifference;
    private String displayTime1;
    private String displayTime2;
    private long day;
    private long hour;
    private long minute;
    private long second;
    ToTimeStamp toTimeStamp = new ToTimeStamp(time);

    public void timeDifference() {
        time1ToTS();
        getTimeStamp1();
        time2ToTS();
        getTimeStamp2();
        calculate();
    }

    private void time1ToTS() {
        toTimeStamp.inputTime();
    }

    private void getTimeStamp1() {
        toTimeStamp.getTimeStamp();
        timeStamp1 = toTimeStamp.getTimeStamp();
        toTimeStamp.getDisplayTime();
        displayTime1 = toTimeStamp.getDisplayTime();
    }

    private void time2ToTS() {
        toTimeStamp.inputTime();
    }

    private void getTimeStamp2() {
        toTimeStamp.getTimeStamp();
        timeStamp2 = toTimeStamp.getTimeStamp();
        toTimeStamp.getDisplayTime();
        displayTime2 = toTimeStamp.getDisplayTime();
    }

    private void calculate() {
        timeStampDifference = timeStamp1 - timeStamp2;
        if (timeStampDifference < 0)
            timeStampDifference = -timeStampDifference;
        day = timeStampDifference / 86400;
        hour = (timeStampDifference % 86400) / 3600;
        minute = (timeStampDifference % 3600) / 60;
        second = timeStampDifference % 60;
        System.out.println(displayTime1);
        System.out.println(displayTime2);
        System.out.println("两者相差" + day + "天" + hour + "小时" + minute + "分" + second + "秒");
    }
}