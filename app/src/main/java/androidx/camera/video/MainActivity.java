package androidx.camera.video;

import androidx.camera.video.binding.activity.MainActivityViewModel;
import androidx.camera.video.databinding.ActivityMainBinding;

import com.xlzhen.mvvm.activity.BaseActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainActivityViewModel> {


    @Override
    protected int getVariableId() {
        return BR.MainActivity;
    }

    @Override
    protected MainActivityViewModel bindingModel() {
        return new MainActivityViewModel(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected ActivityMainBinding bindingInflate() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }
}