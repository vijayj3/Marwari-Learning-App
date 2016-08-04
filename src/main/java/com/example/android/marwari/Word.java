package com.example.android.marwari;

/**
 * Created by AV JAYARAMAN on 28-Jul-16.
 */
public class Word {

    private String mdefaultTranslation;
    private String mMiwokTranslation;
    private static final int NO_IMAGE_PROVIDED = -1;
    private int mImgResource = NO_IMAGE_PROVIDED;
    private int mAudioResource;

    public Word(String defaultTranslation, String miwokTranslation, int audioResource) {
        mdefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResource = audioResource;
    }

    public Word(String defaultTranslation, String miwokTranslation, int audioResource, int imgResource) {
        mdefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResource = audioResource;
        mImgResource = imgResource;
    }

    public boolean hasImage() {
        return mImgResource != NO_IMAGE_PROVIDED;
    }

    public String getDefaultTranslation() {
        return mdefaultTranslation;
    }

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getImgResource() {
        return mImgResource;
    }

    public int getAudioResource() {
        return mAudioResource;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mdefaultTranslation='" + mdefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImgResource=" + mImgResource +
                ", mAudioResource=" + mAudioResource +
                '}';
    }
}
