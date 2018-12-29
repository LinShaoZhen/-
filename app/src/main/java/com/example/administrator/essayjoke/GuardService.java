package com.example.administrator.essayjoke;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by nana on 2018/9/7.
 * 双进程守护
 */

public class GuardService extends Service {

    private static final int GuardId = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        //提高进程优先级
        startForeground(GuardId, new Notification());
        //绑定建立链接
        bindService(new Intent(this, MessageService.class), mServiceConnection, Context.BIND_IMPORTANT);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ProcessConnection.Stub() {
        };
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接上
            Toast.makeText(GuardService.this, "建立链接", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //断开连接，重新启动，重新绑定
            startService(new Intent(GuardService.this,MessageService.class));

            bindService(new Intent(GuardService.this,MessageService.class),mServiceConnection,Context.BIND_IMPORTANT);

        }
    };
}
