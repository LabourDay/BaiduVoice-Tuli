package com.example.baiduspeech.utils;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;

/**
 * Created by WZH on 2018/3/30.
 */

//合成监听�?
class MyListnener implements SpeechSynthesizerListener {
    @Override
    public void onSynthesizeStart(String s) {
        //合成准备工作
    }
    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        //合成数据和进度的回调接口，分多次回调
    }
    @Override
    public void onSynthesizeFinish(String s) {
        //合成正常结束，每句合成正常结束都会回调，如果过程中出错，则回调onError，不再回调此接口
    }
    @Override
    public void onSpeechStart(String s) {
        //播放�?始，每句播放�?始都会回�?
    }
    @Override
    public void onSpeechProgressChanged(String s, int i) {
        //播放进度回调接口，分多次回调
    }
    @Override
    public void onSpeechFinish(String s) {
        // 播放正常结束，每句播放正常结束都会回调，如果过程中出错，则回调onError,不再回调此接�?
    }
    @Override
    public void onError(String s, SpeechError speechError) {
        //当合成或者播放过程中出错时回调此接口
    }
}

