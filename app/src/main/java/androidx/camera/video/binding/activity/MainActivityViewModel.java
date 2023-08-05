package androidx.camera.video.binding.activity;

import android.view.View;

import androidx.camera.video.MainActivity;

import com.xlzhen.mvvm.binding.base.BaseActivityViewModel;

public class MainActivityViewModel extends BaseActivityViewModel<MainActivity> {
    public MainActivityViewModel(MainActivity activity) {
        super(activity);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void backPage(View view) {
        activity.finish();
    }
}
