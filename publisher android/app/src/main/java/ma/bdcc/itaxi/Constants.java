package ma.bdcc.itaxi;

import android.content.Context;

public class Constants {
    static final int LOCATION_SERVICE_ID=175;
    static final  String ACTION_START_LOCATION_SERVICE="startLocationService";
    static final  String ACTION_STOP_LOCATION_SERVICE="stopLocationService";
    static String userAvailability;
    static Context context;
    static Long userId= Long.valueOf(0);
    static String userType;
    static String userDestination;
}
