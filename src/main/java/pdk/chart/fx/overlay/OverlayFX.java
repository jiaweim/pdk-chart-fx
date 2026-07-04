package pdk.chart.fx.overlay;

import pdk.chart.fx.ChartCanvas;
import pdk.chart.swing.Overlay;

import java.awt.*;

/**
 * An overlay that can be added to a {@link ChartCanvas}.
 */
public interface OverlayFX extends Overlay {

    /**
     * Paints the content of the overlay onto the specified chart canvas.
     *
     * @param g2          the graphics target ({@code null} not permitted).
     * @param chartCanvas the chart canvas ({@code null} not permitted).
     */
    void paintOverlay(Graphics2D g2, ChartCanvas chartCanvas);

}
