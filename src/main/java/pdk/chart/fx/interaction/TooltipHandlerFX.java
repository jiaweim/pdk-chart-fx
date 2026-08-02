package pdk.chart.fx.interaction;

import javafx.scene.input.MouseEvent;
import pdk.chart.entity.ChartEntity;
import pdk.chart.entity.EntityCollection;
import pdk.chart.fx.ChartCanvas;
import pdk.chart.model.ChartRenderingInfo;

/**
 * Handles the updating of tooltips on a {@link ChartCanvas}.
 */
public class TooltipHandlerFX extends AbstractMouseHandlerFX
        implements MouseHandlerFX {

    /**
     * Creates a new instance with the specified ID.
     *
     * @param id the handler id ({@code null} not permitted).
     */
    public TooltipHandlerFX(String id) {
        super(id, false, false, false, false);
    }

    /**
     * Handles a mouse moved event by updating the tooltip.
     *
     * @param canvas the chart canvas ({@code null} not permitted).
     * @param e      the mouse event.
     */
    @Override
    public void handleMouseMoved(ChartCanvas canvas, MouseEvent e) {
        if (canvas.getChart() == null || !canvas.isTooltipEnabled()) {
            return;
        }
        String text = getTooltipText(canvas, e.getX(), e.getY());
        canvas.setTooltip(text, e.getScreenX(), e.getScreenY());
    }

    /**
     * Returns the tooltip text.
     *
     * @param canvas the canvas that is displaying the chart.
     * @param x      the x-coordinate of the mouse pointer.
     * @param y      the y-coordinate of the mouse pointer.
     * @return String The tooltip text (possibly {@code null}).
     */
    private String getTooltipText(ChartCanvas canvas, double x, double y) {
        ChartRenderingInfo info = canvas.getRenderingInfo();
        if (info == null) {
            return null;
        }
        EntityCollection entities = info.getEntityCollection();
        if (entities == null) {
            return null;
        }
        ChartEntity entity = entities.getEntity(x, y);
        if (entity == null) {
            return null;
        }
        return entity.getToolTipText();
    }

}
