package com.karan.banduukbaaz;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//import android.support.annotation.NonNull;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity {

//    private CameraCaptureSession myCameraCaptureSession;
//    private String myCameraID;
//    private CameraManager myCameraManager;
//    private CameraDevice myCameraDevice;
//    private TextureView myTextrureView;
//    private CaptureRequest.Builder myCaptureRequestBuilder;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        myTextrureView = findViewById(R.id.tt);
//        myCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
//        openCamera();
//    }
//    private CameraDevice.StateCallback myStateCallBack = new CameraDevice.StateCallback() {
//        @Override
//        public void onOpened(@NonNull CameraDevice camera) {
//            myCameraDevice = camera;
//        }
//        @Override
//        public void onDisconnected(@NonNull CameraDevice camera) {
//            myCameraDevice.close();
//        }
//
//        @Override
//        public void onError(@NonNull CameraDevice camera, int error) {
//            myCameraDevice.close();
//            myCameraDevice = null;
//        }
//    };
//
//    private void openCamera() {
//        try {
//            myCameraID = myCameraManager.getCameraIdList()[0];
//
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
//
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            myCameraManager.openCamera(myCameraID, myStateCallBack, null);
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    public void cameraPreview(View view){
//
//        SurfaceTexture mySurfaceTexture = myTextrureView.getSurfaceTexture();
//        Surface mySurface = new Surface(mySurfaceTexture);
//
//        try {
//            myCaptureRequestBuilder = myCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
//            myCaptureRequestBuilder.addTarget(mySurface);
//
//            myCameraDevice.createCaptureSession(Arrays.asList(mySurface), new CameraCaptureSession.StateCallback() {
//                        @Override
//                        public void onConfigured(@NonNull CameraCaptureSession session) {
//                            myCameraCaptureSession = session;
//                            myCaptureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//                            try {
//                                myCameraCaptureSession.setRepeatingRequest(myCaptureRequestBuilder.build(), null, null);
//                            } catch (CameraAccessException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onConfigureFailed(@NonNull CameraCaptureSession session) {
//
//                        }
//                    }, null
//            );
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//
//    }

    private static final int REQ_CAMERA_IMAGE = 123;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String message = "Click the button below to start";
        if(cameraNotDetected()){
            message = "No camera detected, clicking the button below will have unexpected behaviour.";
        }
        TextView cameraDescriptionTextView = (TextView) findViewById(R.id.text_view_camera_description);
        cameraDescriptionTextView.setText(message);
    }

    private boolean cameraNotDetected() {
        return !getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public void onUseCameraClick(View button){
        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, REQ_CAMERA_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_OK) {
            String imgPath = data.getStringExtra(CameraActivity.EXTRA_IMAGE_PATH);
            Log.i("Got image path: " + imgPath);
            displayImage(imgPath);
        } else if (requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_CANCELED) {
            Log.i("User didn't take an image");
        }
    }

    private void displayImage(String path) {
        ImageView imageView = (ImageView) findViewById(R.id.image_view_captured_image);
        imageView.setImageBitmap(BitmapHelper.decodeSampledBitmap(path, 300, 250));
    }
}
