package com.example.baiduspeech;

import android.content.ComponentName; 
import android.content.Context; 
import android.content.Intent; 
import android.os.Bundle; 
import android.speech.RecognitionListener; 
import android.speech.SpeechRecognizer; 
import android.support.v4.app.FragmentActivity;
import android.widget.Toast; 
  

import com.baidu.speech.VoiceRecognitionService; 
  
public class SpeechRecognizerTool implements RecognitionListener { 
  
  public interface ResultsCallback { 
    void onResults(String result); 
  } 
  
  private Context mContext; 
  
  private SpeechRecognizer mSpeechRecognizer; 
  
  private ResultsCallback mResultsCallback; 
  
  public SpeechRecognizerTool(Context context) { 
    mContext = context; 
  } 
  
  public synchronized void createTool() { 
    if (null == mSpeechRecognizer) { 
  
      // ����ʶ���� 
      mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext, 
          new ComponentName(mContext, VoiceRecognitionService.class)); 
  
      // ע������� 
      mSpeechRecognizer.setRecognitionListener(this); 
    } 
  } 
  
  public synchronized void destroyTool() { 
    mSpeechRecognizer.stopListening(); 
    mSpeechRecognizer.destroy(); 
    mSpeechRecognizer = null; 
  } 
  
  // ��ʼʶ�� 
  public void startASR(ResultsCallback callback) { 
    mResultsCallback = callback; 
  
    Intent intent = new Intent(); 
    bindParams(intent); 
    mSpeechRecognizer.startListening(intent); 
  } 
  
  //ֹͣʶ�� 
  public void stopASR() { 
    mSpeechRecognizer.stopListening(); 
  } 
  
  private void bindParams(Intent intent) { 
    // ����ʶ����� 
  } 
  
  @Override
  public void onReadyForSpeech(Bundle params) { 
    // ׼������ 
    Toast.makeText(mContext, "�뿪ʼ˵��", Toast.LENGTH_SHORT).show(); 
  } 
  
  @Override
  public void onBeginningOfSpeech() { 
    // ��ʼ˵������ 
  } 
  
  @Override
  public void onRmsChanged(float rmsdB) { 
    // �����仯���� 
  } 
  
  @Override
  public void onBufferReceived(byte[] buffer) { 
    // ¼�����ݴ������� 
  } 
  
  @Override
  public void onEndOfSpeech() { 
    // ˵���������� 
  } 
  
  @Override
  public void onError(int error) { 
  } 
  
  @Override
  public void onResults(Bundle results) { 
  
    // ���ս������ 
    if (mResultsCallback != null) { 
      String text = results.get(SpeechRecognizer.RESULTS_RECOGNITION) 
          .toString().replace("]", "").replace("[", ""); 
      mResultsCallback.onResults(text); 
    } 
  } 
  
  @Override
  public void onPartialResults(Bundle partialResults) { 
    // ��ʱ������� 
  } 
  
  @Override
  public void onEvent(int eventType, Bundle params) { 
  }

//  public void startASR(FragmentActivity activity) {
//	  	mResultsCallback = (ResultsCallback) activity; 
//	  
//	    Intent intent = new Intent(); 
//	    bindParams(intent); 
//	    mSpeechRecognizer.startListening(intent); 
//	
//  } 
} 

