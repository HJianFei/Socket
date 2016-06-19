package com.hjf.admin.socket.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.hjf.admin.socket.constants.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class SocketService extends Service {

    private Socket clientSocket = null;

    private SocketReceiveThread socketReceiveThread = null;

    private boolean stop = true;

    public SocketService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        socketReceiveThread = new SocketReceiveThread();
        stop = false;
        // 开启接收线程
        socketReceiveThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        Log.d("service", "socket service destroy!");
        stop = true;
        if (socketReceiveThread.isAlive()) {
            socketReceiveThread.interrupt();
            socketReceiveThread = null;
        }
        if (clientSocket.isConnected()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SocketReceiveThread extends Thread {
        private StringBuilder str = new StringBuilder();
        private Set<String> set = new HashSet<>();
        private InputStream is = null;
        private int flag = 0;

        @Override
        public void run() {
            Log.d("service", "socket service - SocketReceiveThread::run");
            try {
                clientSocket = new Socket(Constants.IntetADDRESS, Constants.IntetPORT);
                clientSocket.setSoTimeout(5 * 1000);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("SSSSS");
            }
            while (!stop) {
                // 读取输入的数据(阻塞读)
                try {
                    int rc = 0;
                    is = clientSocket.getInputStream();
                    while ((rc = is.read()) != -1) {
                        if (rc < 16) {
                            str.append("0" + Integer.toHexString(rc));
                        } else {
                            str.append(Integer.toHexString(rc));
                        }
                        if ("7e".equals(Integer.toHexString(rc))) {
                            flag++;
                        }
                        if (flag % 2 == 0) {
                            flag = 0;
                            set.add(str.toString());
                            System.out.println(str.toString() + "  " + str.toString().length());
                            Intent sendIntent = new Intent(Constants.SOCKER_RCV);
                            sendIntent.putExtra("action", Constants.RECEIVER_STR);
                            sendIntent.putExtra("content", str.toString());
                            // 发送广播，将被Activity组件中的BroadcastReceiver接收到
                            sendBroadcast(sendIntent);
                            str.delete(0, str.length());

                        }
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
