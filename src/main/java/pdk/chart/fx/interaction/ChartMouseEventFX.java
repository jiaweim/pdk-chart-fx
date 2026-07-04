package pdk.chart.fx.interaction;

import javafx.scene.input.MouseEvent;
import pdk.chart.Chart;
import pdk.chart.entity.ChartEntity;
import pdk.chart.fx.ChartViewer;

import java.io.Serializable;
import java.util.EventObject;

/**
 * A mouse event for a chart that is displayed in a (JavaFX)
 * {@link ChartViewer}.
 *
 * @see ChartMouseListenerFX
 */
public class ChartMouseEventFX extends EventObject implements Serializable {

    /**
     * For serialization.
     */
    private static final long serialVersionUID = -682393837314562149L;

    /**
     * The chart that the mouse event relates to.
     */
    private final Chart chart;

    /**
     * The Java mouse event that triggered this event.
     */
    private final MouseEvent trigger;

    /**
     * The chart entity (if any).
     */
    private final ChartEntity entity;

    /**
     * Constructs a new event.
     *
     * @param chart   the source chart ({@code null} not permitted).
     * @param trigger the mouse event that triggered this event
     *                ({@code null} not permitted).
     * @param entity  the chart entity (if any) under the mouse point
     *                ({@code null} permitted).
     */
    public ChartMouseEventFX(Chart chart, MouseEvent trigger,
                             ChartEntity entity) {
        super(chart);
        this.chart = chart;
        this.trigger = trigger;
        this.entity = entity;
    }

    /**
     * Returns the chart that the mouse event relates to.
     *
     * @return The chart (never {@code null}).
     */
    public Chart getChart() {
        return this.chart;
    }

    /**
     * Returns the mouse event that triggered this event.
     *
     * @return The event (never {@code null}).
     */
    public MouseEvent getTrigger() {
        return this.trigger;
    }

    /**
     * Returns the chart entity (if any) under the mouse point.
     *
     * @return The chart entity (possibly {@code null}).
     */
    public ChartEntity getEntity() {
        return this.entity;
    }

}

