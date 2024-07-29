package com.gabbar925.photoeditios.editor.featuresfoto.puzzle.layout.slant;

import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.Line;
import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.PuzzleLayout;
import com.gabbar925.photoeditios.editor.featuresfoto.puzzle.slant.SlantPuzzleLayout;

public class SixSlantLayout extends NumberSlantLayout {
    public int getThemeCount() {
        return 2;
    }

    public SixSlantLayout() {
    }

    public SixSlantLayout(SlantPuzzleLayout slantPuzzleLayout, boolean z) {
        super(slantPuzzleLayout, z);
    }

    public SixSlantLayout(int i) {
        super(i);
    }

    public void layout() {
        switch (this.theme) {
            case 0:
                addLine(0, Line.Direction.VERTICAL, 0.33333334f);
                addLine(1, Line.Direction.VERTICAL, 0.5f);
                addLine(0, Line.Direction.HORIZONTAL, 0.7f, 0.7f);
                addLine(1, Line.Direction.HORIZONTAL, 0.5f, 0.5f);
                addLine(2, Line.Direction.HORIZONTAL, 0.3f, 0.3f);
                return;
            case 1:
                addLine(0, Line.Direction.HORIZONTAL, 0.33333334f);
                addLine(1, Line.Direction.HORIZONTAL, 0.5f);
                addLine(0, Line.Direction.VERTICAL, 0.3f, 0.3f);
                addLine(2, Line.Direction.VERTICAL, 0.5f, 0.5f);
                addLine(4, Line.Direction.VERTICAL, 0.7f, 0.7f);
                return;
            default:
                return;
        }
    }

    public PuzzleLayout clone(PuzzleLayout puzzleLayout) {
        return new SixSlantLayout((SlantPuzzleLayout) puzzleLayout, true);
    }
}
