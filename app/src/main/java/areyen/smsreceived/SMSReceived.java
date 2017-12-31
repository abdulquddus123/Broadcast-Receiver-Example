package areyen.smsreceived;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by a_qud on 12/28/2017.
 */

public class SMSReceived extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       // Toast.makeText(context, "INtent Receive", Toast.LENGTH_SHORT).show();

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle=intent.getExtras();
            SmsMessage[] chucks=null;
            String message="";
            String number="";
            if(bundle!=null){
                Object[] pdus=(Object[])bundle.get("pdus");
                String smsMessageStr="";
                chucks=new SmsMessage[pdus.length];
                for(int i=0;i<pdus.length;i++){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        String format=bundle.getString("format");
                        chucks[i]=SmsMessage.createFromPdu((byte[])pdus[i],format);

                    }else{
                        chucks[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                    }
                    number=chucks[i].getOriginatingAddress();
                    message+=chucks[i].getMessageBody();
                    smsMessageStr +=" SMS From" + number + "\n";
                smsMessageStr+=message+ "\n";
                }
                Toast.makeText(context, "SMS Received From"+ number+"Text"+message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
