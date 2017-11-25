package in.htec.visitrec;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class VisitCheck extends Service {
    public VisitCheck() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
