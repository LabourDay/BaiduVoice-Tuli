package com.example.baiduspeech.utils;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizerListener;

/**
 * Created by WZH on 2018/3/30.
 */

//åæçå¬å?
class MyListnener implements SpeechSynthesizerListener {
    @Override
    public void onSynthesizeStart(String s) {
        //åæåå¤å·¥ä½
    }
    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        //åææ°æ®åè¿åº¦çåè°æ¥å£ï¼åå¤æ¬¡åè°
    }
    @Override
    public void onSynthesizeFinish(String s) {
        //åææ­£å¸¸ç»æï¼æ¯å¥åææ­£å¸¸ç»æé½ä¼åè°ï¼å¦æè¿ç¨ä¸­åºéï¼ååè°onErrorï¼ä¸ååè°æ­¤æ¥å£
    }
    @Override
    public void onSpeechStart(String s) {
        //æ­æ¾å¼?å§ï¼æ¯å¥æ­æ¾å¼?å§é½ä¼åè°?
    }
    @Override
    public void onSpeechProgressChanged(String s, int i) {
        //æ­æ¾è¿åº¦åè°æ¥å£ï¼åå¤æ¬¡åè°
    }
    @Override
    public void onSpeechFinish(String s) {
        // æ­æ¾æ­£å¸¸ç»æï¼æ¯å¥æ­æ¾æ­£å¸¸ç»æé½ä¼åè°ï¼å¦æè¿ç¨ä¸­åºéï¼ååè°onError,ä¸ååè°æ­¤æ¥å?
    }
    @Override
    public void onError(String s, SpeechError speechError) {
        //å½åææèæ­æ¾è¿ç¨ä¸­åºéæ¶åè°æ­¤æ¥å£
    }
}

