package afpcsoft.com.br.bestplaces.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import afpcsoft.com.br.bestplaces.R;
import afpcsoft.com.br.bestplaces.Utils.CameraPreview;
import afpcsoft.com.br.bestplaces.Utils.CameraUtils;
import afpcsoft.com.br.bestplaces.Utils.ImageUtils;

public class CameraActivity extends Activity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private File pictureFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Toast.makeText(CameraActivity.this, "Clique na tela para capturar a foto (a foto deve ser capturada deitada", Toast.LENGTH_LONG).show();

        // Create an instance of Camera
        mCamera = CameraUtils.getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        final LinearLayout layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        final LinearLayout layoutCapture = (LinearLayout) findViewById(R.id.layoutCapture);

        Button back = (Button) findViewById(R.id.backButton);
        Button save = (Button) findViewById(R.id.saveButton);
        Button capture = (Button) findViewById(R.id.captureButton);


        SetCameraParams();
        setCameraDisplayOrientation();

        // Add a listener to the Capture button
        capture.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        Toast.makeText(CameraActivity.this, "Imagem Capturada", Toast.LENGTH_LONG).show();
                        layoutCapture.setVisibility(View.GONE);
                        layoutButtons.setVisibility(View.VISIBLE);
                        mCamera.takePicture(null, null, mPicture);
                        mCamera.stopPreview();
                    }
                }
        );

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void SetCameraParams() {
        Camera.Parameters params = mCamera.getParameters();

        List<String> focusModes = params.getSupportedFocusModes();
        if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            // Autofocus mode is supported
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }

        mCamera.setParameters(params);
    }

    public void setCameraDisplayOrientation()
    {
        if (mCamera == null)
        {
            Log.d("CameraError","setCameraDisplayOrientation - camera null");
            return;
        }

        Camera.CameraInfo info = new Camera.CameraInfo();
//        Camera.getCameraInfo(CAM_ID, info);

        WindowManager winManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int rotation = winManager.getDefaultDisplay().getRotation();

        int degrees = 0;

        switch (rotation)
        {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
        {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        mCamera.setDisplayOrientation(result);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            pictureFile = ImageUtils.getOutputMediaFile(ImageUtils.MEDIA_TYPE_IMAGE);
            if (pictureFile == null){
                Log.d("CameraError", "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d("CameraError", "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d("CameraError", "Error accessing file: " + e.getMessage());
            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        releaseCamera();
        finish();
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
