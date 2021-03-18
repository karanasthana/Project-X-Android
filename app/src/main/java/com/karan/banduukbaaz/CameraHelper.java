package com.karan.banduukbaaz;

import android.hardware.Camera;
import android.util.Log;

public class CameraHelper {
    public static boolean cameraAvailable(Camera camera) {
        return camera != null;
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            // Camera is not available or doesn't exist
            Log.d("getCamera failed", String.valueOf(e));
        }
        return c;
    }
}
