package com.fil.workerappz.pinedittext;

import android.support.annotation.ColorRes;

/**
 *
 *
 */

public interface VerificationAction {

  /**
   */
  void setFigures(int figures);

  /**
   */
  void setVerCodeMargin(int margin);

  /**
   */
  void setBottomSelectedColor(@ColorRes int bottomSelectedColor);

  /**
   */
  void setBottomNormalColor(@ColorRes int bottomNormalColor);

  /**
   */
  void setSelectedBackgroundColor(@ColorRes int selectedBackground);

  /**
   */
  void setBottomLineHeight(int bottomLineHeight);

  /**
   */
  void setOnVerificationCodeChangedListener(OnVerificationCodeChangedListener listener);

  /***
   *
   */
  interface OnVerificationCodeChangedListener {

    /**
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    void onVerCodeChanged(CharSequence s, int start, int before, int count);

    /**
     * @param s
     */
    void onInputCompleted(CharSequence s);
  }
}