package com.bochao;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.content.res.Configuration;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import android.view.Window;
import android.widget.FrameLayout;

import com.bochao.keyboards.KeyboardUtil;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;


public class KeyboardActivity extends UnityPlayerActivity {

    Context context;
    private Activity mActivity;
    protected UnityPlayer mUnityPlayer;
    Handler h = null;

    public boolean IsShowKeyBoard;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        context = this;
        mActivity = this;

        mUnityPlayer = super.mUnityPlayer;

        setContentView(R.layout.keymap);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.unity_player_layout);
        frameLayout.addView(mUnityPlayer.getView(), FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);

        mUnityPlayer.requestFocus();
        Log.i("error", "error");

        h = new Handler() {

            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    new KeyboardUtil(mActivity, context).showKeyboard();
                }
                if (msg.what == 2) {
                    new KeyboardUtil(mActivity, context).hideKeyboard();
                }
            }
        };

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (IsShowKeyBoard) {
            h.sendEmptyMessage(1);
        } else {
            h.sendEmptyMessage(2);
        }

    }

    public void OpenKeyBoard() {
        h.sendEmptyMessage(1);
        IsShowKeyBoard = true;
    }

    public void CloseKeyBoard() {
        h.sendEmptyMessage(2);
        IsShowKeyBoard = false;
    }
}
