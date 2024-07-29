package com.gabbar925.photoeditios.editor.sticker.event;

import android.view.MotionEvent;

import com.gabbar925.photoeditios.editor.sticker.StickerView;

public interface StickerIconEvent {
    void onActionDown(StickerView stickerView, MotionEvent motionEvent);

    void onActionMove(StickerView stickerView, MotionEvent motionEvent);

    void onActionUp(StickerView stickerView, MotionEvent motionEvent);
}
