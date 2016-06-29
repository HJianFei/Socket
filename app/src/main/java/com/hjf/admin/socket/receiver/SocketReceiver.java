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
 *
 * @description 广播接收者，接收service 发送过来 socket 接收到的数据信息
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
                //字符转义，如果数据出现"7d5e",就转成"7e"
                contents = contents.replace("7d5e", "7e");
                //字符转义，如果数据出现"7d5d",就转成"7d"
                contents = contents.replace("7d5d", "7d");
                //数据长度
                info.setDataLength(Integer.parseInt(contents.substring(2, 4), 16) + "");
                //读写器地址
                info.setReaderAddress(contents.substring(4, 6));
                // 命令0xAF实时温度湿度
                info.setActualTime(contents.substring(6, 8));
                // 状态
                info.setStatus(contents.substring(8, 10));
                // EPC编码
                info.setEpcCode(contents.substring(10, 34));
                // 温度
                info.setTemperature(Integer.parseInt((contents.substring(36, 38) + contents.substring(34, 36)), 16) / 100.0 + "");
                //湿度
                info.setHumidity(Integer.parseInt((contents.substring(40, 42) + contents.substring(38, 40)), 16) / 100.0 + "");
                /// 校验码
                info.setCrc(contents.substring(42, 46));
                //全部数据信息
                info.setAllInfo(contents);

                Message message = Message.obtain();
                message.arg1 = 1;
                message.obj = info;
                mHandler.sendMessage(message);
//
            }
        }
    }
}
