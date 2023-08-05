package androidx.camera.video.binding.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.camera.video.activity.DemoCameraActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.xlzhen.mvvm.binding.base.BaseActivityViewModel;


public class DemoCameraActivityViewModel extends BaseActivityViewModel<DemoCameraActivity> {
    public MutableLiveData<Boolean> videoCaptureEnabled = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> startOrStop = new MutableLiveData<>(true);
    public DemoCameraActivityViewModel(DemoCameraActivity activity) {
        super(activity);
    }

    @Override
    public void onResume() {
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
        }
    }

    @Override
    public void backPage(View view) {
        activity.finish();
    }

    public void getYUV(View view){
        activity.getYUV(view);
    }

    public void captureVideo(View view){
        activity.captureVideo(view);
    }
}
