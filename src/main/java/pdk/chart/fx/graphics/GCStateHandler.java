package pdk.chart.fx.graphics;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Clip / save / restore is global to a graphic context
 */
final class GCStateHandler {

    private final static boolean TRACE = false;

    /**
     * The graphics context for the JavaFX canvas.
     */
    final GraphicsContext gc;

    private int saveCount = 0;

    /**
     * stack of saved states
     */
    private final ArrayList<GCState> states = new ArrayList<>(8);

    GCStateHandler(final GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public String toString() {
        return "GCState{" + "gc=" + gc + ", stackPos= " + saveCount + '}';
    }

    boolean isStateSavedSince(final int saveCount) {
        return (getSaveCount() > saveCount);
    }

    int getSaveCount() {
        return this.saveCount;
    }

    int save(final GCState state) {
        if (TRACE) {
            System.out.println("gc.save:  in: " + this);
        }

        // save current clip:
        this.gc.save();

        // save state in stack at current restorePoint:
        states.add(state);

        final int restore = this.saveCount++;

        if (TRACE) {
            System.out.println("gc.save: out: " + this);
        }
        return restore;
    }

    private GCState restore() {
        if (TRACE) {
            System.out.println("gc.restore:  in: " + this);
        }
        GCState state = null;

        if (isStateSavedSince(0)) {
            final int pos = --this.saveCount;
            this.gc.restore(); // get back original clip
            if (pos >= 0) {
                state = states.remove(pos);
            }
        }
        if (TRACE) {
            System.out.println("gc.restore: out: " + this);
        }
        return state;
    }

    GCState restoreToCount(final int saveCount) {
        if (TRACE) {
            System.out.println("gc.restoreToCount(" + saveCount + "):  in: " + this);
        }
        GCState state = null;

        while (isStateSavedSince(saveCount)) {
            state = restore();
        }
        if (TRACE) {
            System.out.println("gc.restoreToCount(" + saveCount + "): out: " + this);
        }
        return state;
    }

    public final static class GCState {

        final Color savedColor;
        final Paint savedPaint;
        final Stroke savedStroke;
        final Font savedFont;
        final AffineTransform savedTransform;

        public GCState(Color color, Paint paint, Stroke stroke, Font font, AffineTransform transform) {
            this.savedColor = color;
            this.savedPaint = paint;
            this.savedStroke = stroke;
            this.savedFont = font;
            this.savedTransform = new AffineTransform(transform);
        }

        @Override
        public String toString() {
            return "GCState{"
                    + "savedColor=" + savedColor
                    + ", savedPaint=" + savedPaint
                    + ", savedStroke=" + savedStroke
                    + ", savedFont=" + savedFont
                    + ", savedTransform=" + savedTransform + '}';
        }
    }
}
