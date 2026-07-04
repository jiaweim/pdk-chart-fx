package pdk.chart.fx.interaction;

import pdk.chart.fx.ChartViewer;
import pdk.chart.swing.ChartMouseListener;

/**
 * A mouse listener that can receive event notifications from a (JavaFX)
 * {@link ChartViewer} instance.  This interface is equivalent to the
 * {@link ChartMouseListener} interface used for charts in Swing.
 */
public interface ChartMouseListenerFX {

    /**
     * Receives notification of a mouse click on a chart.
     *
     * @param event event information (never {@code null}).
     */
    void chartMouseClicked(ChartMouseEventFX event);

    /**
     * Receives notification of a mouse move event on a chart.
     *
     * @param event event information (never {@code null}).
     */
    void chartMouseMoved(ChartMouseEventFX event);

}

