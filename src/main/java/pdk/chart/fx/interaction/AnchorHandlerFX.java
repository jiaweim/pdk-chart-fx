package pdk.chart.fx.interaction;

import javafx.scene.input.MouseEvent;
import pdk.chart.fx.ChartCanvas;

import java.awt.geom.Point2D;

/**
 * Handles mouse clicks on the {@link ChartCanvas} by updating the anchor and
 * redrawing the chart.
 */
public class AnchorHandlerFX extends AbstractMouseHandlerFX {

    /**
     * Records the mouse down location.
     */
    private Point2D mousePressedPoint;

    /**
     * Creates a new instance.
     *
     * @param id the id ({@code null} not permitted).
     */
    public AnchorHandlerFX(String id) {
        super(id, false, false, false, false);
    }

    /**
     * Handles a mouse pressed event by recording the location of the mouse
     * pointer (so that later we can check that the click isn't part of a
     * drag).
     *
     * @param canvas the chart canvas.
     * @param e      the mouse event.
     */
    @Override
    public void handleMousePressed(ChartCanvas canvas, MouseEvent e) {
        this.mousePressedPoint = new Point2D.Double(e.getX(), e.getY());
    }

    /**
     * Handles a mouse clicked event by setting the anchor point for the
     * canvas and redrawing the chart (the anchor point is a reference point
     * used by the chart to determine crosshair lines).
     *
     * @param canvas the chart canvas ({@code null} not permitted).
     * @param e      the mouse event ({@code null} not permitted).
     */
    @Override
    public void handleMouseClicked(ChartCanvas canvas, MouseEvent e) {
        if (this.mousePressedPoint == null) {
            return;
        }
        Point2D currPt = new Point2D.Double(e.getX(), e.getY());
        if (this.mousePressedPoint.distance(currPt) < 2) {
            canvas.setAnchor(currPt);
        }
        this.mousePressedPoint = null;
    }

}

