package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer; //定义音频
    private AudioManager mAudioManager;  //神奇的应用啊

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //当有更重要的声音(如电话声)需要播放，定义当前声音的状态的一些操作
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //为该数组赋值，储存内容
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
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
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceID());
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
