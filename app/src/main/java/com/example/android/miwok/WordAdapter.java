package com.example.android.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by xiaoyuer on 2017/2/21.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    //定义背景颜色变量
    private int mColorResourceID;

    //构造函数
    public WordAdapter(Activity context, ArrayList<Word> androidWord, int colorResourceID) {
        super(context, 0, androidWord);
        mColorResourceID = colorResourceID;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Word currentWord = getItem(position);
        //设置miwok文字格式
        TextView miwokTextView = (TextView) listView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getmMiwokTranslation());
        //设置翻译文字格式
        TextView defaultTextView = (TextView) listView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getmDefaultTranslation());
        //设置图片显示
        ImageView iconView = (ImageView) listView.findViewById(R.id.image_view);
        if (currentWord.hasImage()) {
            iconView.setImageResource(currentWord.getImageResourceID());
            iconView.setVisibility(View.VISIBLE);
        }
        else {
            iconView.setVisibility(View.GONE);
        }
        //设置背景颜色
        View textContainer = listView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        textContainer.setBackgroundColor(color);

        return listView;
    }
}
