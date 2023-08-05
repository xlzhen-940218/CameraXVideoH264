//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package androidx.camera.video;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.video.internal.encoder.EncodedData;
import androidx.core.util.Consumer;

import java.util.concurrent.Executor;

final class H264_AutoValue_Recorder_RecordingRecord extends H264Recorder.RecordingRecord {
    private final OutputOptions getOutputOptions;
    private final Executor getCallbackExecutor;
    private final Consumer<VideoRecordEvent> getEventListener;
    private final Consumer<EncodedData> getVideoListener;
    private final Consumer<EncodedData> getAudioListener;
    private final boolean hasAudioEnabled;
    private final boolean isPersistent;
    private final long getRecordingId;

    H264_AutoValue_Recorder_RecordingRecord(OutputOptions getOutputOptions, @Nullable Executor getCallbackExecutor
            , @Nullable Consumer<VideoRecordEvent> getEventListener
            , @Nullable Consumer<EncodedData> getVideoListener
            , @Nullable Consumer<EncodedData> getAudioListener
            , boolean hasAudioEnabled, boolean isPersistent, long getRecordingId) {
        if (getOutputOptions == null) {
            throw new NullPointerException("Null getOutputOptions");
        } else {
            this.getOutputOptions = getOutputOptions;
            this.getCallbackExecutor = getCallbackExecutor;
            this.getEventListener = getEventListener;
            this.getVideoListener = getVideoListener;
            this.getAudioListener = getAudioListener;
            this.hasAudioEnabled = hasAudioEnabled;
            this.isPersistent = isPersistent;
            this.getRecordingId = getRecordingId;
        }
    }

    @NonNull
    OutputOptions getOutputOptions() {
        return this.getOutputOptions;
    }

    @Nullable
    Executor getCallbackExecutor() {
        return this.getCallbackExecutor;
    }

    @Nullable
    Consumer<VideoRecordEvent> getEventListener() {
        return this.getEventListener;
    }

    @Override
    Consumer<EncodedData> getAudioEncodedDataListener() {
        return this.getAudioListener;
    }

    @Override
    Consumer<EncodedData> getVideoEncodedDataListener() {
        return this.getVideoListener;
    }

    boolean hasAudioEnabled() {
        return this.hasAudioEnabled;
    }

    boolean isPersistent() {
        return this.isPersistent;
    }

    long getRecordingId() {
        return this.getRecordingId;
    }

    public String toString() {
        return "RecordingRecord{getOutputOptions=" + this.getOutputOptions + ", getCallbackExecutor=" + this.getCallbackExecutor + ", getEventListener=" + this.getEventListener + ", hasAudioEnabled=" + this.hasAudioEnabled + ", isPersistent=" + this.isPersistent + ", getRecordingId=" + this.getRecordingId + "}";
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Recorder.RecordingRecord)) {
            return false;
        } else {
            Recorder.RecordingRecord that = (Recorder.RecordingRecord) o;
            boolean var10000;
            if (this.getOutputOptions.equals(that.getOutputOptions())) {
                label45:
                {
                    if (this.getCallbackExecutor == null) {
                        if (that.getCallbackExecutor() != null) {
                            break label45;
                        }
                    } else if (!this.getCallbackExecutor.equals(that.getCallbackExecutor())) {
                        break label45;
                    }

                    if (this.getEventListener == null) {
                        if (that.getEventListener() != null) {
                            break label45;
                        }
                    } else if (!this.getEventListener.equals(that.getEventListener())) {
                        break label45;
                    }

                    if (this.hasAudioEnabled == that.hasAudioEnabled() && this.isPersistent == that.isPersistent() && this.getRecordingId == that.getRecordingId()) {
                        var10000 = true;
                        return var10000;
                    }
                }
            }

            var10000 = false;
            return var10000;
        }
    }

    public int hashCode() {
        int h$ = 1;
        h$ *= 1000003;
        h$ ^= this.getOutputOptions.hashCode();
        h$ *= 1000003;
        h$ ^= this.getCallbackExecutor == null ? 0 : this.getCallbackExecutor.hashCode();
        h$ *= 1000003;
        h$ ^= this.getEventListener == null ? 0 : this.getEventListener.hashCode();
        h$ *= 1000003;
        h$ ^= this.hasAudioEnabled ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= this.isPersistent ? 1231 : 1237;
        h$ *= 1000003;
        h$ ^= (int) (this.getRecordingId >>> 32 ^ this.getRecordingId);
        return h$;
    }
}
