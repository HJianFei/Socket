package com.hjf.admin.socket.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.hjf.admin.socket.constants.Constants;
import com.hjf.admin.socket.entity.Info;

/**
 * Created by HJianFei on 2016-6-16.
 */
public class SocketReceiver extends BroadcastReceiver {

    private Handler mHandler;
    private Info info;

    public SocketReceiver(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String action = intent.getAction();
        if (action.equals(Constants.SOCKER_RCV)) {
            String url = intent.getExtras().getString("action");
            if (Constants.RECEIVER_STR.equals(url)) {
                String contents = intent.getExtras().getString("content");
                info = new Info();

                contents = contents.replace("7d5e", "7e");
                contents = contents.replace("7d5d", "7d");
                info.setDataLength(Integer.parseInt(contents.substring(2, 4), 16) + "");
                info.setReaderAddress(contents.substring(4, 6));
                info.setActualTime(contents.substring(6, 8));
                info.setStatus(contents.substring(8, 10));
                info.setEpcCode(contents.substring(10, 34));
                info.setTemperature(Integer.parseInt((contents.substring(36, 38) + contents.substring(34, 36)), 16) / 100.0 + "");
                info.setHumidity(Integer.parseInt((contents.substring(40, 42) + contents.substring(38, 40)), 16) / 100.0 + "");
                info.setCrc(contents.substring(42, 46));
                info.setAllInfo(contents);
                Message message = Message.obtain();
                message.arg1 = 1;
                message.obj = info;
                mHandler.sendMessage(message);
//                System.out.println(contents + " " + contents.length());
//                System.out.println(Integer.parseInt(contents.substring(2, 4), 16) + "");
//                System.out.println(contents.substring(4, 6));
//                System.out.println(contents.substring(6, 8));
//                System.out.println(contents.substring(8, 10));
//                System.out.println(contents.substring(10, 34));
//                System.out.println(Integer.parseInt((contents.substring(36, 38) + contents.substring(34, 36)), 16) / 100.0);
//                System.out.println(Integer.parseInt((contents.substring(40, 42) + contents.substring(38, 40)), 16) / 100.0);
////                System.out.println(contents.substring(38, 42));
//                System.out.println(contents.substring(42, 46));
//                System.out.println(contents.substring(46, 48));
            }
        }
    }
}
