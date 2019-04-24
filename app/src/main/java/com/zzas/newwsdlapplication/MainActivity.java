package com.zzas.newwsdlapplication;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button mMYbt;
    // 在webService服务端中可以找到NAMESPAC的具体内容
    private static final String SERVICE_NAMESPACE = "http://tobacco/ind/cyft/etm";
    // URL
    private static final String SERVICE_URL = "http://39.106.158.240:85/cyft/services/ETM_BusinessOrder?wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMYbt = findViewById(R.id.mybt);
        mMYbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        zasa zasa = new zasa();
                        String FunName="sendUserinfo";
                        String reqXml="<?xml version=\"1.0\" encoding=\"GBK\"?><dataset><datalist><params><ieme>1804306</ieme></params></datalist></dataset>";
                        String str = zasa.dfs(FunName,reqXml);
                        Log.i("zzas",str);
                        zasa.XmlPull(str);
                    }
                }).start();



            }
        });
    }
}
