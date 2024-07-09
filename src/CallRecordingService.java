import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CallRecordingService extends Service {
    private MediaRecorder recorder;
    private String recordingFileName;
    private String logFileName;

    @Override
    public void onCreate() {
        super.onCreate();
        log("Service onCreate");

        // Determine the directory for storing recordings and logs
        File appDirectory = new File(getExternalFilesDir(null), "CallRecordings");
        if (!appDirectory.exists()) {
            appDirectory.mkdirs();
        }

        // Create file names with timestamp
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        recordingFileName = new File(appDirectory, "Recording_" + timestamp + ".3gp").getAbsolutePath();
        logFileName = new File(appDirectory, "ServiceLog_" + timestamp + ".txt").getAbsolutePath();

        // Initialize the MediaRecorder
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(recordingFileName);

        try {
            recorder.prepare();
        } catch (IOException e) {
            logError("Error in preparing recorder: " + e.getMessage());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        log("Service onStartCommand");
        try {
            recorder.start();
        } catch (IllegalStateException e) {
            logError("Error in starting recorder: " + e.getMessage());
            // Handle alternative actions or notify users here
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        log("Service onDestroy");
        if (recorder != null) {
            try {
                recorder.stop();
                recorder.release();
            } catch (IllegalStateException e) {
                logError("Error in stopping recorder: " + e.getMessage());
                // Handle alternative actions or cleanup here
            }
            recorder = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        log("Service onBind");
        // This is a started service, not a bound service, so return null
        return null;
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

    private void logError(String errorMessage) {
        log("ERROR: " + errorMessage);
    }
}



//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//import android.media.MediaRecorder;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Locale;
//
//public class CallRecordingService extends Service {
//    private MediaRecorder recorder;
//    private String recordingFileName;
//    private String logFileName;
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        log("Service onCreate");
//
//        // Determine the directory for storing recordings and logs
//        File appDirectory = new File(getExternalFilesDir(null), "CallRecordings");
//        if (!appDirectory.exists()) {
//            appDirectory.mkdirs();
//        }
//
//        // Create file names with timestamp
//        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
//        recordingFileName = new File(appDirectory, "Recording_" + timestamp + ".3gp").getAbsolutePath();
//        logFileName = new File(appDirectory, "ServiceLog_" + timestamp + ".txt").getAbsolutePath();
//
//        // Initialize the MediaRecorder
//        recorder = new MediaRecorder();
//        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
//        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//        recorder.setOutputFile(recordingFileName);
//
//        try {
//            recorder.prepare();
//        } catch (IOException e) {
//            logError("Error in preparing recorder: " + e.getMessage());
//        }
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        log("Service onStartCommand");
//        try {
//            recorder.start();
//        } catch (IllegalStateException e) {
//            logError("Error in starting recorder: " + e.getMessage());
//            // Handle alternative actions or notify users here
//        }
//        return START_STICKY;
//    }
//
//    @Override
//    public void onDestroy() {
//        log("Service onDestroy");
//        if (recorder != null) {
//            try {
//                recorder.stop();
//                recorder.release();
//            } catch (IllegalStateException e) {
//                logError("Error in stopping recorder: " + e.getMessage());
//                // Handle alternative actions or cleanup here
//            }
//            recorder = null;
//        }
//    }
//
//    @Override
//    public IBinder onBind(Intent intent) {
//        log("Service onBind");
//        // This is a started service, not a bound service, so return null
//        return null;
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
//
//    private void logError(String errorMessage) {
//        log("ERROR: " + errorMessage);
//    }
//}
