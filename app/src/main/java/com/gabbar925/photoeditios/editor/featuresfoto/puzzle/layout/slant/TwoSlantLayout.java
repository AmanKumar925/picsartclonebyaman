package com.gabbar925.photoeditios.editor.featuresfoto.puzzle.layout.slant;

import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.Line;
import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.PuzzleLayout;
import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.slant.SlantPuzzleLayout;

public class TwoSlantLayout extends NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }

    public TwoSlantLayout() {
    }

    public TwoSlantLayout(SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public TwoSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, Line.Direction.HORIZONTAL, 0.56f, 0.44f);
                return;
            case 1:
                addLine(0, Line.Direction.VERTICAL, 0.56f, 0.44f);
                return;
            default:
                return;
        }
    }

    public PuzzleLayout clone(PuzzleLayout puzzleLayout) {
        return new TwoSlantLayout((SlantPuzzleLayout) puzzleLayout, true);
    }
}
