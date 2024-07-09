import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CallStateListener extends PhoneStateListener {
    private String logFileName;
    private Context context;

    public CallStateListener(Context context) {
        this.context = context;

        // Determine the directory for storing logs
        File appDirectory = new File(context.getExternalFilesDir(null), "CallRecordings");
        if (!appDirectory.exists()) {
            boolean isDirectoryCreated = appDirectory.mkdirs();
            if (!isDirectoryCreated) {
                // Handle the case where the directory couldn't be created
            }
        }

        // Create log file name with timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        logFileName = new File(appDirectory, "PhoneStateLog_" + timestamp + ".txt").getAbsolutePath();
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                log("CALL_STATE_RINGING: " + incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                log("CALL_STATE_OFFHOOK");
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                log("CALL_STATE_IDLE");
                break;
            default:
                log("Unknown Phone State: " + state);
                break;
        }
    }

    private void log(String message) {
        try {
            FileWriter writer = new FileWriter(new File(logFileName), true);
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
            writer.append(timeStamp).append(" - ").append(message).append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Consider alternative logging mechanisms if file writing fails
        }
    }
}


//import android.content.Context;
//import android.telephony.PhoneStateListener;
//import android.telephony.TelephonyManager;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class CallStateListener extends PhoneStateListener {
//    private String logFileName;
//    private Context context;
//
//    public CallStateListener(Context context) {
//        this.context = context;
//
//        // Determine the directory for storing logs
//        File appDirectory = new File(context.getExternalFilesDir(null), "CallRecordings");
//        if (!appDirectory.exists()) {
//            boolean isDirectoryCreated = appDirectory.mkdirs();
//            if (!isDirectoryCreated) {
//                // Handle the case where the directory couldn't be created
//            }
//        }
//
//        // Create log file name with timestamp
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        logFileName = new File(appDirectory, "PhoneStateLog_" + timestamp + ".txt").getAbsolutePath();
//    }
//
//    @Override
//    public void onCallStateChanged(int state, String incomingNumber) {
//        super.onCallStateChanged(state, incomingNumber);
//
//        switch (state) {
//            case TelephonyManager.CALL_STATE_RINGING:
//                log("CALL_STATE_RINGING: " + incomingNumber);
//                break;
//            case TelephonyManager.CALL_STATE_OFFHOOK:
//                log("CALL_STATE_OFFHOOK");
//                break;
//            case TelephonyManager.CALL_STATE_IDLE:
//                log("CALL_STATE_IDLE");
//                break;
//            default:
//                log("Unknown Phone State: " + state);
//                break;
//        }
//    }
//
//    private void log(String message) {
//        try {
//            FileWriter writer = new FileWriter(new File(logFileName), true);
//            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
//            writer.append(timeStamp).append(" - ").append(message).append("\n");
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Consider alternative logging mechanisms if file writing fails
//        }
//    }
//}
