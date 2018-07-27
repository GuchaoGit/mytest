package com.example.hardcattle.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcB;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.otg.idcard.OTGReadCardAPI;

import java.util.ArrayList;

/**
 * Created by guc on 2018/7/10.
 * 描述：
 */
public class NFCTest extends Activity {
    public static final int MESSAGE_VALID_OTGBUTTON = 15;
    public static final int MESSAGE_VALID_NFCBUTTON = 16;
    public static final int MESSAGE_VALID_BTBUTTON = 17;
    public static final int MESSAGE_VALID_PROCESS = 1001;
    private static final int DB_VERSION = 2;
    private static final int SETTING_SERVER_IP = 11;
    private static final int SETTING_BT = 22;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    public static String remoteIPA = "";
    public static String remoteIPB = "";
    public static String remoteIPC = "";
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    public static String addressmac = "";
    public static String DB_CREATE_TABLE_IPCONFIG = "CREATE TABLE IF NOT EXISTS [setipconfig] " +
            "([ID] INTEGER PRIMARY KEY,[IP] VARCHAR)";
    private static Button onredo, setNFC;
    private static String DB_NAME;
    private static DatabaseHelper mOpenHelper;
    private static SQLiteDatabase db;
    private TextView title;
    private TextView name;
    private TextView nametext;
    private TextView sex;
    private TextView sextext;
    private TextView mingzu;
    private TextView mingzutext;
    private TextView birthday;
    private TextView birthdaytext;
    private TextView address;
    private TextView addresstext;
    private TextView number;
    private TextView numbertext;
    private TextView qianfa;
    private TextView qianfatext;
    private TextView start;
    private TextView starttext;
    private TextView end;
    private TextView endtext;
    private TextView dncodetext;
    private TextView dncode;
    private int portin;
    private TextView Readingtext;
    private ImageView idimg;
    private Context tcontext;
    private ArrayList<String> IPArray = null;
    private int mode = 2;//1,OTG; 2, NFC; //3, Bluetooth;
    private int logflag = 0;
    private NfcAdapter mAdapter = null;
    //	private ReadCardAPI NFCReadCardAPI;
    private OTGReadCardAPI ReadCardAPI;
    private PendingIntent pi = null;
    //滤掉组件无法响应和处理的Intent
    private IntentFilter tagDetected = null;
    private String[][] mTechLists;
    private Intent inintent = null;
    private int readflag = 0;
    private BluetoothAdapter btAdapt;
    private final Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int tt;
            ReadCardAPI.setport(portin);
            switch (msg.what) {
                case MESSAGE_VALID_BTBUTTON:
                    ReadCardAPI.setmac(addressmac);
                    tt = ReadCardAPI.BtReadCard(btAdapt);
                    Log.e("For Test", " ReadCard TT=" + tt);
                    if (tt == 2) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("接收数据超时！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   接收数据超时！");
                        Readingtext.setTextColor(Color.BLUE);
//						onredo.setEnabled(true);
//						onredo.setFocusable(true);
//						onredo.setBackgroundResource(R.drawable.sfz_dq);
                    }
//			        if (tt==51)
//			        {
//			        	return;
//			        }
                    if (tt == 41) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("读卡失败！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   读卡失败！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    if (tt == 42) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("没有找到服务器！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   没有找到服务器！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    if (tt == 43) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("服务器忙！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   服务器忙！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    if (tt == 90) {
                        nametext.setText(ReadCardAPI.Name());
                        sextext.setText(ReadCardAPI.SexL());
                        mingzutext.setText(ReadCardAPI.NationL());
                        birthdaytext.setText(ReadCardAPI.BornL());
                        addresstext.setText(ReadCardAPI.Address());
                        numbertext.setText(ReadCardAPI.CardNo());
                        qianfatext.setText(ReadCardAPI.Police());
                        starttext.setText(ReadCardAPI.Activity());
                        endtext.setText(ReadCardAPI.Activity());
                        dncodetext.setText(ReadCardAPI.DNcode());

                        idimg.setImageBitmap(Bytes2Bimap(ReadCardAPI.GetImage()));

                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        ReadCardAPI.release();
                        Readingtext.setText("   读卡成功！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    onredo.setEnabled(true);
                    onredo.setFocusable(true);
                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    readflag = 0;
//					Readingtext.setVisibility(View.GONE);
                    break;
                case MESSAGE_VALID_NFCBUTTON:
                    ReadCardAPI.writeFile("come into MESSAGE_CLEAR_ITEMS 1");
                    tt = ReadCardAPI.NfcReadCard(inintent);
//					tt=testReadCardAPI.ReadCard();
                    ReadCardAPI.writeFile("come into MESSAGE_CLEAR_ITEMS 2");
                    Log.e("For Test", " ReadCard TT=" + tt);
                    if (tt == 2) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("接收数据超时！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   接收数据超时！");
                        Readingtext.setTextColor(Color.BLUE);
//						onredo.setEnabled(true);
//						onredo.setFocusable(true);
//						onredo.setBackgroundResource(R.drawable.sfz_dq);
                    }
//			        if (tt==51)
//			        {
//			        	return;
//			        }
                    if (tt == 41) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("读卡失败！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   读卡失败！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    if (tt == 42) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("没有找到服务器！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   没有找到服务器！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    if (tt == 43) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("服务器忙！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        Readingtext.setText("   服务器忙！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    if (tt == 90) {
                        nametext.setText(ReadCardAPI.Name());
                        sextext.setText(ReadCardAPI.SexL());
                        mingzutext.setText(ReadCardAPI.NationL());
                        birthdaytext.setText(ReadCardAPI.BornL());
                        addresstext.setText(ReadCardAPI.Address());
                        numbertext.setText(ReadCardAPI.CardNo());
                        qianfatext.setText(ReadCardAPI.Police());
                        starttext.setText(ReadCardAPI.Activity());
                        endtext.setText(ReadCardAPI.ActivityL());
                        dncodetext.setText(ReadCardAPI.DNcode());

                        idimg.setImageBitmap(Bytes2Bimap(ReadCardAPI.GetImage()));

                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        ReadCardAPI.release();
                        Readingtext.setText("   读卡成功！");
                        Readingtext.setTextColor(Color.BLUE);
                    }
                    readflag = 0;
//					Readingtext.setVisibility(View.GONE);
                    break;
                case MESSAGE_VALID_OTGBUTTON:
//					ReadCardAPI=new OTGReadCardAPI(tcontext,IPArray);
                    tt = ReadCardAPI.ConnectStatus();
                    Log.e("For Test", " ConnectStatus TT=" + tt);
                    if (tt == 0) {
//				      	new AlertDialog.Builder(NFCTest.this)
//				       			.setTitle("提示" ).setMessage("设备未连接！" )
//				       			.setPositiveButton("确定" ,  null ).show();
                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        Readingtext.setText("   设备未连接！");
                        Readingtext.setTextColor(Color.BLUE);
//						Readingtext.setVisibility(View.GONE);
                        break;
                    }
                    if (tt == 2) {
//				      	new AlertDialog.Builder(NFCTest.this)
//		       			.setTitle("提示" ).setMessage("请再次读卡！" )
//		       			.setPositiveButton("确定" ,  null ).show();
//						onredo.setEnabled(true);
//						onredo.setFocusable(true);
//						onredo.setBackgroundResource(R.drawable.sfz_dq);
//						Readingtext.setVisibility(View.GONE);
                        break;
                    }
                    tt = ReadCardAPI.OTGReadCard();
                    Log.e("For Test", " ReadCard TT=" + tt);
                    if (tt == 2) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("接收数据超时！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        Readingtext.setText("   接收数据超时！");
                        Readingtext.setTextColor(Color.BLUE);
//						Readingtext.setVisibility(View.GONE);
                        break;
                    }
                    if (tt == 41) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("读卡失败！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        Readingtext.setText("   读卡失败！");
                        Readingtext.setTextColor(Color.BLUE);
//						Readingtext.setVisibility(View.GONE);
                        break;
                    }
                    if (tt == 42) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("没有找到服务器！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        Readingtext.setText("   没有找到服务器！");
                        Readingtext.setTextColor(Color.BLUE);
//						Readingtext.setVisibility(View.GONE);
                        break;
                    }
                    if (tt == 43) {
//			        	new AlertDialog.Builder(NFCTest.this)
//			        			.setTitle("提示" ).setMessage("服务器忙！" )
//			        			.setPositiveButton("确定" ,  null ).show();
                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        Readingtext.setText("   服务器忙！");
                        Readingtext.setTextColor(Color.BLUE);
//						Readingtext.setVisibility(View.GONE);
                        break;
                    }
                    if (tt == 90) {
                        nametext.setText(ReadCardAPI.Name());
                        sextext.setText(ReadCardAPI.SexL());
                        mingzutext.setText(ReadCardAPI.NationL());
                        birthdaytext.setText(ReadCardAPI.BornL());
                        addresstext.setText(ReadCardAPI.Address());
                        numbertext.setText(ReadCardAPI.CardNo());
                        qianfatext.setText(ReadCardAPI.Police());
                        starttext.setText(ReadCardAPI.Activity().subSequence(0, 8));
                        endtext.setText(ReadCardAPI.Activity().subSequence(8, 16));
                        dncodetext.setText(ReadCardAPI.DNcode());

                        idimg.setImageBitmap(Bytes2Bimap(ReadCardAPI.GetImage()));

                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                        ReadCardAPI.release();
                        Readingtext.setText("   读卡成功！");
                        Readingtext.setTextColor(Color.BLUE);
//						Readingtext.setVisibility(View.GONE);
                        break;
                    }
                    break;
            }
        }
    };
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_OTGBUTTON, 0);
                        }
                    } else {
                        onredo.setEnabled(true);
                        onredo.setFocusable(true);
                        onredo.setBackgroundResource(R.drawable.sfz_dq);
                    }
                }
            }
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainyishu);
        tcontext = this;//this.getApplicationContext();
        IPArray = new ArrayList<String>();
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        this.registerReceiver(mUsbReceiver, filter);
        onredo = (Button) findViewById(R.id.scale);
        setNFC = findViewById(R.id.set_nfc);
        setNFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null) {
                    title.setText("身份证识别NFC模式");
                    mode = 2;
                    if (mAdapter != null) startNFC_Listener();
                } else {
                    new AlertDialog.Builder(NFCTest.this)
                            .setTitle("提示").setMessage("本机不支持功能！")
                            .setPositiveButton("确定", null).show();
                }
            }
        });
//        onredo.setOnTouchListener(new OnTouchListener(){
//
//            //			@Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        if (mode==2)
//                        {
//                            return false;
//                        }
//                        if (mode==3)
//                        {
//                            if (readflag==1) break;
//                            readflag=1;
//                        }
//                        onredo.setBackgroundResource(R.drawable.sfz_dq_c);
//                        nametext.setText("");
//                        sextext.setText("");
//                        mingzutext.setText("");
//                        birthdaytext.setText("");
//                        addresstext.setText("");
//                        numbertext.setText("");
//                        qianfatext.setText("");
//                        starttext.setText("");
//                        endtext.setText("");
//                        dncodetext.setText("");
//
//                        Readingtext.setVisibility(View.VISIBLE);
//                        idimg.setImageBitmap(null);
//                        Log.e("For Test"," ACTION_DOWN");
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        if (mode==1)
//                        {
//                            onredo.setBackgroundResource(R.drawable.sfz_dq_c);
//                            onredo.setEnabled(false);
//                            onredo.setFocusable(false);
//                            mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_OTGBUTTON, 0);
//                        }else if (mode==3)
//                        {
//                            if (!btAdapt.isEnabled()) {
//                                Intent enableIntent = new Intent(
//                                        BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//                                // Otherwise, setup the chat session
//                            }
//                            if (readflag==0)
//                            {
//                                Readingtext.setVisibility(View.GONE);
//                                break;
//                            }
//                            if (addressmac.length()<10)
//                            {
//                                new AlertDialog.Builder(NFCTest.this)
//                                        .setTitle("提示" ).setMessage("未设置蓝牙读卡设备！" )
//                                        .setPositiveButton("确定" ,  null ).show();
//                                onredo.setEnabled(true);
//                                onredo.setFocusable(true);
//                                onredo.setBackgroundResource(R.drawable.sfz_dq);
//                                readflag=0;
//                                Readingtext.setVisibility(View.GONE);
//                                break;
//
//                            }
//                            onredo.setBackgroundResource(R.drawable.sfz_dq_c);
//                            onredo.setEnabled(false);
//                            onredo.setFocusable(false);
//                            mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_BTBUTTON, 0);
//                        }else
//                        {
//                            onredo.setBackgroundResource(R.drawable.sfz_dq);
//                        }
//                        break;
//                }
//                return true;
//            }
//
//        });

        title = (TextView) findViewById(R.id.title);
        name = (TextView) findViewById(R.id.name);
        sex = (TextView) findViewById(R.id.sex);
        nametext = (TextView) findViewById(R.id.nametext);
        sextext = (TextView) findViewById(R.id.sextext);
        mingzu = (TextView) findViewById(R.id.mingzu);
        mingzutext = (TextView) findViewById(R.id.mingzutext);
        birthday = (TextView) findViewById(R.id.birthday);
        birthdaytext = (TextView) findViewById(R.id.birthdaytext);
        address = (TextView) findViewById(R.id.address);
        addresstext = (TextView) findViewById(R.id.addresstext);
        number = (TextView) findViewById(R.id.number);
        numbertext = (TextView) findViewById(R.id.numbertext);
        qianfa = (TextView) findViewById(R.id.qianfa);
        qianfatext = (TextView) findViewById(R.id.qianfatext);
        start = (TextView) findViewById(R.id.start);
        starttext = (TextView) findViewById(R.id.starttext);
        end = (TextView) findViewById(R.id.end);
        endtext = (TextView) findViewById(R.id.endtext);
        Readingtext = (TextView) findViewById(R.id.Readingtext);
        dncodetext = (TextView) findViewById(R.id.dncodetext);
        dncode = (TextView) findViewById(R.id.dncode);

        if (mode == 1) title.setText("身份证识别OTG模式");
        if (mode == 2) title.setText("身份证识别NFC模式");
        if (mode == 3) title.setText("身份证识别蓝牙模式");
        name.setText("姓名：");
        sex.setText("性别：");
        mingzu.setText("民族：");
        birthday.setText("出生年月：");
        address.setText("地址：");
        number.setText("身份证号码：");
        qianfa.setText("签发机关：");
        start.setText("生效时间：");
        end.setText("生效时间：");
        dncode.setText("DN码：");
        idimg = (ImageView) findViewById(R.id.idimg);
//		idimg.setImageResource(R.drawable.ic_launcher);
        Readingtext.setVisibility(View.GONE);
        Readingtext.setText("      正在读卡，请稍候...");
        Readingtext.setTextColor(Color.RED);
        portin = 9000;

        DB_NAME = "/sdcard/yishu/usingservice.db";
        mOpenHelper = new DatabaseHelper(this);
        db = mOpenHelper.getWritableDatabase();
        String sql = "select IP from setipconfig where ID=1;";
        Cursor cur;
        cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) {
            while (!cur.isAfterLast()) {
                remoteIPA = cur.getString(0).trim();
                break;
            }
        }
        cur.close();
        sql = "select IP from setipconfig where ID=2;";
        cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) {
            while (!cur.isAfterLast()) {
                remoteIPB = cur.getString(0).trim();
                break;
            }
        }
        cur.close();
        sql = "select IP from setipconfig where ID=3;";
        cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) {
            while (!cur.isAfterLast()) {
                remoteIPC = cur.getString(0).trim();
                break;
            }
        }
        cur.close();
        sql = "select IP from setipconfig where ID=4;";
        cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) {
            while (!cur.isAfterLast()) {
                addressmac = cur.getString(0).trim();
                break;
            }
        }
        cur.close();
        sql = "select IP from setipconfig where ID=5;";
        cur = db.rawQuery(sql, null);
        if (cur.moveToFirst() == true) {
            while (!cur.isAfterLast()) {
                String pp = cur.getString(0).trim();
                if (pp.length() < 2) {
                    portin = 9000;
                } else {
                    portin = (int) Integer.parseInt(cur.getString(0).trim());
                }
                break;
            }
        }
        cur.close();
        db.close();
        mOpenHelper.close();
        IPArray.add(remoteIPA);
        IPArray.add(remoteIPB);
        IPArray.add(remoteIPC);
        btAdapt = BluetoothAdapter.getDefaultAdapter();// 初始化本机蓝牙功能

        ReadCardAPI = new OTGReadCardAPI(getApplicationContext() /* tcontext*/, IPArray);

//		ReadCardAPI.setpathflag(2);
        ReadCardAPI.setport(portin);
//		ReadCardAPI.setlogflag(1);
//		mAdapter = NfcAdapter.getDefaultAdapter(this);
        mAdapter = NfcAdapter.getDefaultAdapter(getApplicationContext());
        if (mAdapter == null) {
//			ReadCardAPI.writeFile("pass new test 2.1");
        } else {
            init_NFC();
//			ReadCardAPI.writeFile("pass new test 2.2");
        }
//		if (mAdapter.equals(null)) ReadCardAPI.writeFile("mAdapter is null");
//		else ReadCardAPI.writeFile("mAdapter is not null");
//		ReadCardAPI.writeFile("pass new test 3");
//		ReadCardAPI.writeFile("pass new test 4");

    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mode == 1) {
            new AlertDialog.Builder(NFCTest.this)
                    .setTitle("提示").setMessage("目前处于OTG模式！")
                    .setPositiveButton("确定", null).show();
            return;
        }
        if (mode == 3) {
            new AlertDialog.Builder(NFCTest.this)
                    .setTitle("提示").setMessage("目前处于蓝牙模式！")
                    .setPositiveButton("确定", null).show();
            return;
        }
//		mHandler.sendEmptyMessageDelayed(MESSAGE_CLEAR_ITEMS, 0);
        if (readflag == 1) {
            return;
        }
        inintent = intent;
        readflag = 1;
        nametext.setText("");
        sextext.setText("");
        mingzutext.setText("");
        birthdaytext.setText("");
        addresstext.setText("");
        numbertext.setText("");
        qianfatext.setText("");
        starttext.setText("");
        endtext.setText("");
        dncodetext.setText("");

//		ReadCardAPI=new OTGReadCardAPI(tcontext,IPArray);
        ReadCardAPI.writeFile("come into onNewIntent 2");
        idimg.setImageBitmap(null);
        Readingtext.setVisibility(View.VISIBLE);
        Readingtext.setText("      正在读卡，请稍候...");
        mHandler.sendEmptyMessageDelayed(MESSAGE_VALID_NFCBUTTON, 0);
/*		int tt=ReadCardAPI.NfcReadCard(intent);
//		tt=testReadCardAPI.ReadCard();
        Log.e("For Test"," ReadCard TT="+tt);
        if (tt==2)
        {
        	new AlertDialog.Builder(tcontext)
        			.setTitle("提示" ).setMessage("接收数据超时！" )
        			.setPositiveButton("确定" ,  null ).show();
//			onredo.setEnabled(true);
//			onredo.setFocusable(true);
//			onredo.setBackgroundResource(R.drawable.sfz_dq);
        }
//        if (tt==51)
//        {
//        	return;
//        }
        if (tt==41)
        {
        	new AlertDialog.Builder(tcontext)
        			.setTitle("提示" ).setMessage("读卡失败！" )
        			.setPositiveButton("确定" ,  null ).show();
        }
        if (tt==42)
        {
        	new AlertDialog.Builder(tcontext)
        			.setTitle("提示" ).setMessage("没有找到服务器！" )
        			.setPositiveButton("确定" ,  null ).show();
        }
        if (tt==43)
        {
        	new AlertDialog.Builder(tcontext)
        			.setTitle("提示" ).setMessage("服务器忙！" )
        			.setPositiveButton("确定" ,  null ).show();
        }
        if (tt==90)
        {
        	nametext.setText(ReadCardAPI.Name());
        	sextext.setText(ReadCardAPI.SexL());
        	mingzutext.setText(ReadCardAPI.NationL());
        	birthdaytext.setText(ReadCardAPI.BornL());
        	addresstext.setText(ReadCardAPI.Address());
        	numbertext.setText(ReadCardAPI.CardNo());
        	qianfatext.setText(ReadCardAPI.Police());
        	starttext.setText(ReadCardAPI.Activity());
        	endtext.setText(ReadCardAPI.ActivityL());

			idimg.setImageBitmap(Bytes2Bimap(ReadCardAPI.GetImage()));

			onredo.setEnabled(true);
			onredo.setFocusable(true);
			onredo.setBackgroundResource(R.drawable.sfz_dq);
			ReadCardAPI.release();
        }
		readflag=0;*/
    }

    @Override
    public void onPause() {
        super.onPause();
//		mAdapter.disableForegroundDispatch(this);
        if (mode == 1) return;
        if (mode == 3) return;
        if (mAdapter != null) stopNFC_Listener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mode == 1) return;
        if (mode == 3) return;
        ReadCardAPI.writeFile("come into onResume 1");
        if (mAdapter != null) startNFC_Listener();
        ReadCardAPI.writeFile("come into onResume 2");

        ReadCardAPI.writeFile("pass onNewIntent 1.111111 action=" + getIntent().getAction());

        // Create a generic PendingIntent that will be deliver to this activity.
        // The NFC stack
        // will fill in the intent with the details of the discovered tag before
        // delivering to this activity.
/*		PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		// Setup an intent filter for all MIME based dispatches
//		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

//		try {
			ndef.addCategory(Intent.CATEGORY_DEFAULT);
//			ndef.addDataType("");
//		} catch (MalformedMimeTypeException e) {
//			throw new RuntimeException("fail", e);
//		}
		IntentFilter[] mFilters = new IntentFilter[] { ndef, };
*/        // Setup a tech list for all NfcF tags
//		String[][] mTechLists = new String[][] { new String[] { NfcB.class.getName() } };
//		mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
//		mAdapter.enableForegroundDispatch(this, pi, new IntentFilter[] { tagDetected }, mTechLists);
    }

    private void startNFC_Listener() {
        mAdapter.enableForegroundDispatch(this, pi, new IntentFilter[]{tagDetected}, mTechLists);
//		mAdapter.enableForegroundDispatch(this, pi,
//				new IntentFilter[] { tagDetected }, null);
    }

    private void stopNFC_Listener() {
        mAdapter.disableForegroundDispatch(this);
    }

    private void init_NFC() {
//		nfcIntent = new Intent(getApplicationContext(), NFCTest.class)
//				.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pi = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
//		tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);//.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mTechLists = new String[][]{new String[]{NfcB.class.getName()}};
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.option_menu, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.setlog1:
//                if (ReadCardAPI!=null)
//                {
//                    logflag=1;
//                    ReadCardAPI.setlogflag(logflag);
//                }
//                return true;
//            case R.id.setlog0:
//                if (ReadCardAPI!=null)
//                {
//                    logflag=0;
//                    ReadCardAPI.setlogflag(logflag);
//                }
//                return true;
//            case R.id.setip:
//                // Launch the DeviceListActivity to see devices and do scan
//                Intent serverIntent1 = new Intent(this, SetServerIPActivity.class);
//                startActivityForResult(serverIntent1, SETTING_SERVER_IP);
//                return true;
//            case R.id.setuseotg:
//                // Launch the DeviceListActivity to see devices and do scan
//                title.setText("身份证识别OTG模式");
//                mode=1;
//                if (mAdapter!=null)	stopNFC_Listener();
//                return true;
//            case R.id.setusenfc:
//                // Launch the DeviceListActivity to see devices and do scan
//                if (mAdapter!=null)
//                {
//                    title.setText("身份证识别NFC模式");
//                    mode=2;
//                    if (mAdapter!=null) startNFC_Listener();
//                }else
//                {
//                    new AlertDialog.Builder(NFCTest.this)
//                            .setTitle("提示" ).setMessage("本机不支持功能！" )
//                            .setPositiveButton("确定" ,  null ).show();
//                }
//                return true;
//            case R.id.setusebt:
//                // Launch the DeviceListActivity to see devices and do scan
//                title.setText("身份证识别蓝牙模式");
//                mode=3;
//                if (mAdapter!=null)	stopNFC_Listener();
//                if (!btAdapt.isEnabled()) {
//                    Intent enableIntent = new Intent(
//                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//                    // Otherwise, setup the chat session
//                }
//                ReadCardAPI.setmac(addressmac);
//                return true;
//            case R.id.setbtconfig:
//                // Launch the DeviceListActivity to see devices and do scan
////        	new AlertDialog.Builder(tcontext)
////			.setTitle("提示" ).setMessage("接收数据超时！" )
////			.setPositiveButton("确定" ,  null ).show();
//                if (!btAdapt.isEnabled()) {
//                    Intent enableIntent = new Intent(
//                            BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
//                    return true;
//                    // Otherwise, setup the chat session
//                }
//                Intent serverIntent2 = new Intent(this, DeviceListActivity.class);
//                startActivityForResult(serverIntent2, SETTING_BT);
//                return true;
//        }
//        return false;
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTING_BT) {
            if (resultCode != Activity.RESULT_OK) return;
//	    	   testReadCardAPI.writeFile("come into data action 1 requestCode="+requestCode);
//	    	   testReadCardAPI.writeFile("come into data action 1 resultCode="+resultCode);

//	    	   if (data.equals(null)) return;
//	    	   testReadCardAPI.writeFile("come into data action 2 ");
//	    	   testReadCardAPI.writeFile("come into data action = "+data.getAction());
            String address = data.getStringExtra(EXTRA_DEVICE_ADDRESS);
            mOpenHelper = new DatabaseHelper(this);
            db = mOpenHelper.getWritableDatabase();
            String sql = "select IP from setipconfig where ID=4;";
            int p = 0;
            Cursor cur;
            cur = db.rawQuery(sql, null);
            if (cur.moveToFirst() == true) {
                while (!cur.isAfterLast()) {
                    p++;
                    break;
                }
            }
            cur.close();

            if (p == 0) {
                sql = "insert into setipconfig (ID,IP) values(4,'" + address + "');";
                db.execSQL(sql);
            } else {
                sql = "update setipconfig set IP='" + address + "'" + " where ID=4" + ";";
                db.execSQL(sql);
            }
            db.close();
            mOpenHelper.close();
            addressmac = address;
            ReadCardAPI.setmac(address);

        }
        if (requestCode == SETTING_SERVER_IP) {
            IPArray.clear();
            mOpenHelper = new DatabaseHelper(this);
            db = mOpenHelper.getWritableDatabase();
            String sql = "select IP from setipconfig where ID=1;";
            Cursor cur;
            cur = db.rawQuery(sql, null);
            if (cur.moveToFirst() == true) {
                while (!cur.isAfterLast()) {
                    remoteIPA = cur.getString(0).trim();
                    break;
                }
            }
            cur.close();
            sql = "select IP from setipconfig where ID=2;";
            cur = db.rawQuery(sql, null);
            if (cur.moveToFirst() == true) {
                while (!cur.isAfterLast()) {
                    remoteIPB = cur.getString(0).trim();
                    break;
                }
            }
            cur.close();
            sql = "select IP from setipconfig where ID=3;";
            cur = db.rawQuery(sql, null);
            if (cur.moveToFirst() == true) {
                while (!cur.isAfterLast()) {
                    remoteIPC = cur.getString(0).trim();
                    break;
                }
            }
//				cur.close();
            cur.close();
            sql = "select IP from setipconfig where ID=5;";
            cur = db.rawQuery(sql, null);
            if (cur.moveToFirst() == true) {
                while (!cur.isAfterLast()) {
                    portin = (int) Integer.parseInt(cur.getString(0).trim());
                    break;
                }
            }
            cur.close();
            db.close();
            mOpenHelper.close();
            IPArray.add(remoteIPA);
            IPArray.add(remoteIPB);
            IPArray.add(remoteIPC);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(mUsbReceiver);
        super.onDestroy();
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("ERROR", "test here DBOperation this channel onCreate");
            db.execSQL(DB_CREATE_TABLE_IPCONFIG);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}
