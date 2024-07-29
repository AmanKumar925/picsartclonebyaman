package com.gabbar925.photoeditios.editor.featuresfoto.picker.event;

import com.gabbar925.photoeditios.editor.featuresfoto.picker.entity.Photo;

public interface Selectable {
    void clearSelection();

    int getSelectedItemCount();

    boolean isSelected(Photo photo);

    void toggleSelection(Photo photo);
}
