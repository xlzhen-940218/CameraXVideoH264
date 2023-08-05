package androidx.camera.video.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.BR;
import androidx.camera.video.H264PendingRecording;
import androidx.camera.video.H264Recorder;
import androidx.camera.video.H264Recording;
import androidx.camera.video.MediaStoreOutputOptions;
import androidx.camera.video.Quality;
import androidx.camera.video.QualitySelector;
import androidx.camera.video.VideoCapture;
import androidx.camera.video.VideoRecordEvent;
import androidx.camera.video.binding.activity.DemoCameraActivityViewModel;
import androidx.camera.video.databinding.ActivityDemoCameraBinding;
import androidx.camera.video.internal.encoder.EncodedData;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;

import com.google.common.util.concurrent.ListenableFuture;
import com.xlzhen.mvvm.activity.BaseActivity;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DemoCameraActivity extends BaseActivity<ActivityDemoCameraBinding, DemoCameraActivityViewModel> {
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private VideoCapture<H264Recorder> videoCapture;
    private H264Recording h264Recording;
    private ExecutorService cameraExecutor;

    @Override
    protected int getVariableId() {
        return BR.DemoCameraActivity;
    }

    @Override
    protected DemoCameraActivityViewModel bindingModel() {
        return new DemoCameraActivityViewModel(this);
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void initData() {
        cameraExecutor = Executors.newSingleThreadExecutor();
        binding.viewFinder.post(() -> {
            startCamera();
        });

    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderListenableFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderListenableFuture.addListener(new Runnable() {
            @OptIn(markerClass = ExperimentalGetImage.class)
            @SuppressLint("RestrictedApi")
            @Override
            public void run() {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderListenableFuture.get();

                    Preview preview = new Preview.Builder().build();
                    preview.setSurfaceProvider(binding.viewFinder.getSurfaceProvider());

                    H264Recorder recorder = new H264Recorder.Builder()
                            .setQualitySelector(QualitySelector.from(Quality.HIGHEST))
                            .build();
                    videoCapture = VideoCapture.withOutput(recorder);

                    cameraProvider.unbindAll();
                    cameraProvider.bindToLifecycle(DemoCameraActivity.this
                            , CameraSelector.DEFAULT_BACK_CAMERA, preview, videoCapture);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @Override
    protected ActivityDemoCameraBinding bindingInflate() {
        return ActivityDemoCameraBinding.inflate(getLayoutInflater());
    }

    public void getYUV(View view) {

    }

    @SuppressLint("MissingPermission")
    public void captureVideo(View view) {
        if (videoCapture == null)
            return;

        model.videoCaptureEnabled.setValue(false);

        H264Recording curAnyCameraRecording = h264Recording;
        if (curAnyCameraRecording != null) {
            // Stop the current recording session.
            curAnyCameraRecording.stop();
            h264Recording = null;
            return;
        }

        String name = new SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                .format(System.currentTimeMillis());
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            contentValues.put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/CameraX-Video");
        }

        MediaStoreOutputOptions mediaStoreOutputOptions = new MediaStoreOutputOptions
                .Builder(getContentResolver(), MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
                .setContentValues(contentValues)
                .build();
        H264PendingRecording anyCameraPendingRecording = videoCapture.getOutput()
                .prepareRecording(this, mediaStoreOutputOptions);
        h264Recording = anyCameraPendingRecording
                .start(ContextCompat.getMainExecutor(this), new Consumer<VideoRecordEvent>() {
                    @Override
                    public void accept(VideoRecordEvent videoRecordEvent) {
                        if (videoRecordEvent.getClass() == VideoRecordEvent.Start.class) {
                            model.videoCaptureEnabled.setValue(true);
                            model.startOrStop.setValue(false);
                        } else if (videoRecordEvent.getClass() == VideoRecordEvent.Finalize.class) {
                            if (!((VideoRecordEvent.Finalize) videoRecordEvent).hasError()) {
                                Toast.makeText(DemoCameraActivity.this
                                                , String.format("Video capture succeeded: %s", ((VideoRecordEvent.Finalize) videoRecordEvent).getOutputResults().getOutputUri())
                                                , Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                h264Recording.close();
                                h264Recording = null;
                            }
                            model.startOrStop.setValue(true);
                            model.videoCaptureEnabled.setValue(true);
                        }
                    }
                }, new Consumer<EncodedData>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void accept(EncodedData encodedData) {
                        byte[] data = new byte[encodedData.getBufferInfo().size];
                        encodedData.getByteBuffer().remaining();
                        encodedData.getByteBuffer().get(data);
                        String hexData = Arrays.toString(data);
                        //Log.v("video", hexData);
                        System.out.printf("video = %s%n", hexData);
                        encodedData.close();
                    }
                }, new Consumer<EncodedData>() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void accept(EncodedData encodedData) {
                        byte[] data = new byte[encodedData.getBufferInfo().size];
                        encodedData.getByteBuffer().remaining();
                        encodedData.getByteBuffer().get(data);
                        String hexData = Arrays.toString(data);
                        //Log.v("audio", hexData);
                        System.out.printf("audio = %s%n", hexData);
                        encodedData.close();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }
}
