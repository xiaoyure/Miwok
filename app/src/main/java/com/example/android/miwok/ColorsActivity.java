package com.example.android.miwok;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer; //定义音频
    private AudioManager mAudioManager;  //神奇的应用啊


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisə", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭə", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        //音频播放的触摸接口控制
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(NumbersActivity.this, "click item list", Toast.LENGTH_SHORT).show();
                releaseMediaPlayer();
                Word word = words.get(position);

                //请求AudioFocus服务，请求结果存入result中
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //AUDIOFOCUS_REQUEST_GRANTED申请成功
                //AUDIOFOCUS_REQUEST_FAILED申请失败
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getmAudioResourceID());
                    mMediaPlayer.start();
                    //释放无用音频占用的空间
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

    }

    //不用时，释放掉音频占用的资源
    private MediaPlayer.OnCompletionListener mCompletionListener = new  MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer){
            releaseMediaPlayer();
        }
    };

    //定义AudioFocus被强占、再次获得通知
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        //方法
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //AUDIOFOCUS_LOSS_TRANSIENT暂时失去Audio Focus
                //AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK暂时失去AudioFocus，但是可以继续播放(需降低音量)
                //在该APP中，由于音频很短，则二者可归一
                mMediaPlayer.pause(); //暂停播放
                mMediaPlayer.seekTo(0); //从头开始播放
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //获得了Audio Focus
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //失去了Audio Focus，此时可以直接释放占用的资源
                releaseMediaPlayer();
            }
        }
    };

    @Override //退出该界面时，停止播放音频
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    //释放音频占用的空间
    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
