package com.example.android.miwok;

/**
 * Created by xiaoyuer on 2017/2/21.
 */

public class Word {

    private String mDefaultTranslation;  //翻译
    private String mMiwokTranslation;  //miwok文字
    private int mImageResourceID = NO_IMAGE_PROVIDED;  //图片显示
    private int mAudioResourceID;

    private static final int NO_IMAGE_PROVIDED = -1;  //常量，判断是否添加了颜色
    //构造函数
    public Word(String DefaultTranslation, String MiwokTranslation, int AudioResourceID) {
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mAudioResourceID = AudioResourceID;
    }
    //构造函数
    public Word(String DefaultTranslation, String MiwokTranslation, int ImageResourceID, int AudioResourceID) {
        mDefaultTranslation = DefaultTranslation;
        mMiwokTranslation = MiwokTranslation;
        mImageResourceID = ImageResourceID;
        mAudioResourceID = AudioResourceID;
    }

    //获取函数
    public String getmDefaultTranslation() {
        return mDefaultTranslation;
    }
    public String getmMiwokTranslation() {
        return mMiwokTranslation;
    }
    public int getImageResourceID() { return mImageResourceID; }
    public int getmAudioResourceID() {
        return mAudioResourceID;
    }

    //判断是否存在图片
    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED;
    }
}
