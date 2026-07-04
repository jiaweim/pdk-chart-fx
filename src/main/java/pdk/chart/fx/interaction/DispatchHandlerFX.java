package pdk.chart.fx.interaction;

import javafx.scene.input.MouseEvent;
import pdk.chart.entity.ChartEntity;
import pdk.chart.fx.ChartCanvas;
import pdk.chart.fx.ChartViewer;

import java.awt.geom.Point2D;

/**
 * Handles mouse move and click events on the {@link ChartCanvas} by
 * dispatching {@link ChartMouseEventFX} events to listeners that are
 * registered with the {@code ChartCanvas} (listeners can also be registered
 * with a {@link ChartViewer} control).
 */
public class DispatchHandlerFX extends AbstractMouseHandlerFX {

    /**
     * Records the mouse down location.
     */
    private Point2D mousePressedPoint;

    /**
     * Creates a new instance.
     *
     * @param id the id ({@code null} not permitted).
     */
    public DispatchHandlerFX(String id) {
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
     * Handles a mouse moved event by passing on an event to all registered
     * listeners.
     *
     * @param canvas the chart canvas ({@code null} not permitted).
     * @param e      the mouse event.
     */
    @Override
    public void handleMouseMoved(ChartCanvas canvas, MouseEvent e) {
        if (canvas.getChart() == null) {
            return; // do nothing
        }
        double x = e.getX();
        double y = e.getY();
        ChartEntity entity = canvas.getRenderingInfo().getEntityCollection().getEntity(x, y);
        ChartMouseEventFX event = new ChartMouseEventFX(canvas.getChart(), e, entity);
        for (ChartMouseListenerFX listener : canvas.getChartMouseListeners()) {
            listener.chartMouseMoved(event);
        }
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
        if (this.mousePressedPoint == null || canvas.getChart() == null) {
            return;
        }
        double x = e.getX();
        double y = e.getY();
        ChartEntity entity = canvas.getRenderingInfo().getEntityCollection().getEntity(x, y);
        ChartMouseEventFX event = new ChartMouseEventFX(canvas.getChart(), e, entity);
        for (ChartMouseListenerFX listener : canvas.getChartMouseListeners()) {
            listener.chartMouseClicked(event);
        }
    }

}
