import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.content.Context;

public class YourActivity extends Activity {

    private CallStateListener callStateListener;
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_layout);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        callStateListener = new CallStateListener(this);
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (telephonyManager != null) {
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
        }
    }
}
