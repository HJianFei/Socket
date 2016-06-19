package com.hjf.admin.socket;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hjf.admin.socket.adapter.MyAdapter;
import com.hjf.admin.socket.constants.Constants;
import com.hjf.admin.socket.entity.Info;
import com.hjf.admin.socket.receiver.SocketReceiver;
import com.hjf.admin.socket.service.SocketService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SocketReceiver socketReceiver;
    private Button btn_start, btn_stop;
    private ListView lv_info;
    private Intent mSocketIntent;
    private List<Info> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                mList.add((Info) msg.obj);
                initData(mList);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册监听器
        socketReceiver = new SocketReceiver(mHandler);
        IntentFilter socketIntentFilter = new IntentFilter();
        socketIntentFilter.addAction(Constants.SOCKER_RCV);
        registerReceiver(socketReceiver, socketIntentFilter);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        lv_info = (ListView) findViewById(R.id.lv_info);


        //启动Socket
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSocketIntent = new Intent();
                mSocketIntent.setClass(MainActivity.this, SocketService.class);
                // 启动  Socket 服务
                startService(mSocketIntent)
                ;
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSocketIntent != null) {
                    stopService(mSocketIntent);
                }
            }
        });
    }

    private void initData(List<Info> list) {
        mAdapter = new MyAdapter(this, list);
        mAdapter.notifyDataSetChanged();
        lv_info.setAdapter(mAdapter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(socketReceiver);
    }
}
