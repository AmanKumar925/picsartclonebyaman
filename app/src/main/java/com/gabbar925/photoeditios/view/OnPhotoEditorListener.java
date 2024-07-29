package com.gabbar925.photoeditios.view;

import android.view.View;

public interface OnPhotoEditorListener {
    void onAddViewListener(ViewType viewType, int i);

    void onEditTextChangeListener(View view, String str, int i);

    @Deprecated
    void onRemoveViewListener(int i);

    void onRemoveViewListener(ViewType viewType, int i);

    void onStartViewChangeListener(ViewType viewType);

    void onStopViewChangeListener(ViewType viewType);
}
