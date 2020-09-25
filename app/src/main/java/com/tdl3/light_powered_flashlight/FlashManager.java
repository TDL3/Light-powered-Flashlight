package com.tdl3.light_powered_flashlight;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import android.widget.TextView;


public class FlashManager {
    private Context context;
    private TextView status;
    FlashManager (Context context, TextView status) {
        this.context = context;
        this.status = status;
    }

    public void toggleFlashLight(boolean on) {
            try {
                CameraManager mCameraManager = context.getSystemService(CameraManager.class);
                String[] ids  = mCameraManager.getCameraIdList();
                for (String id : ids) {
                    CameraCharacteristics c = mCameraManager.getCameraCharacteristics(id);
                    Boolean flashAvailable = c.get(CameraCharacteristics.FLASH_INFO_AVAILABLE);
                    Integer lensFacing = c.get(CameraCharacteristics.LENS_FACING);
                    if (flashAvailable != null
                            && flashAvailable
                            && lensFacing != null
                            && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                        mCameraManager.setTorchMode(id, on);
                    } else {
                        status.setText(R.string.no_flashlight);
                    }
                }
            } catch (CameraAccessException e) {
                Log.e("CameraAccessException","CameraAccessException Captured, details show as bellow:");
                e.printStackTrace();
            }
        }

}
