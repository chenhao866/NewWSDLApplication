package com.zzas.newwsdlapplication;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class zasa {


    public static final String WEB_SERVER_URL = "http://39.106.158.240:85/cyft/services/ETM_BusinessOrder?wsdl";
    private static final String NAMESPACE = "http://tobacco/ind/cyft/etm";// 命名空间
    private static final ExecutorService executorService = Executors.newFixedThreadPool(3);//限制线程池大小为3的线程池

    public String dfs(String FunName,String reqXml){
        String action=NAMESPACE + FunName;// 调用的方法名称
        SoapObject rpc = new SoapObject(NAMESPACE, FunName);// 指定WebService的命名空间和调用的方法名
        rpc.addProperty("reqXml", reqXml);// 设置需调用WebService接口需要传入的参数
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        envelope.bodyOut = rpc;
        envelope.dotNet = true;  //true是.net false是java // 设置是否调用的是dotNet开发的WebService
        envelope.setOutputSoapObject(rpc);
        HttpTransportSE transport = new HttpTransportSE(WEB_SERVER_URL);
        transport.debug = true;
        try {
            transport.call(action, envelope);// 调用WebService
            if (envelope.getResponse()!=null){
                Object object = (Object)envelope.getResponse();
                return object.toString();// 获取返回的数据
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void XmlPull(String xmlData) {
        XmlPullParserFactory factory = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
            int eventType = parser.getEventType();
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {//不是结束节点
                String nodeName = parser.getName();//获得节点名称
                switch (eventType) {//循环节点
                    case XmlPullParser.START_TAG: { // 如果是开始节点
                        if ("sex".equals(nodeName)) {
                            name = parser.nextText();
                        } else if ("wince_psw".equals(nodeName)) {
                            version = parser.nextText();
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG: {// 如果是结束节点
                        if ("data".equals(nodeName)) {
                            Log.d("MainActivity", "name is " + name);
                            Log.d("MainActivity", "version is " + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }















}
