package pdk.chart.fx.overlay;

import pdk.chart.Chart;
import pdk.chart.api.RectangleEdge;
import pdk.chart.axis.ValueAxis;
import pdk.chart.fx.ChartCanvas;
import pdk.chart.fx.ChartViewer;
import pdk.chart.plot.Crosshair;
import pdk.chart.plot.PlotOrientation;
import pdk.chart.plot.XYPlot;
import pdk.chart.swing.CrosshairOverlay;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * An overlay for a {@link ChartViewer} that draws crosshairs on a plot.
 */
public class CrosshairOverlayFX extends CrosshairOverlay implements OverlayFX {

    /**
     * Creates a new instance.
     */
    public CrosshairOverlayFX() {
        super();
    }

    @Override
    public void paintOverlay(Graphics2D g2, ChartCanvas chartCanvas) {
        if (chartCanvas.getRenderingInfo() == null) {
            return;
        }
        Shape savedClip = g2.getClip();
        Rectangle2D dataArea = chartCanvas.getRenderingInfo().getPlotInfo().getDataArea();
        g2.clip(dataArea);
        Chart chart = chartCanvas.getChart();
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis xAxis = plot.getDomainAxis();
        RectangleEdge xAxisEdge = plot.getDomainAxisEdge();
        for (Crosshair ch : getDomainCrosshairs()) {
            if (ch.isVisible()) {
                double x = ch.getValue();
                double xx = xAxis.valueToJava2D(x, dataArea, xAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawVerticalCrosshair(g2, dataArea, xx, ch);
                } else {
                    drawHorizontalCrosshair(g2, dataArea, xx, ch);
                }
            }
        }
        ValueAxis yAxis = plot.getRangeAxis();
        RectangleEdge yAxisEdge = plot.getRangeAxisEdge();
        for (Crosshair ch : getRangeCrosshairs()) {
            if (ch.isVisible()) {
                double y = ch.getValue();
                double yy = yAxis.valueToJava2D(y, dataArea, yAxisEdge);
                if (plot.getOrientation() == PlotOrientation.VERTICAL) {
                    drawHorizontalCrosshair(g2, dataArea, yy, ch);
                } else {
                    drawVerticalCrosshair(g2, dataArea, yy, ch);
                }
            }
        }
        g2.setClip(savedClip);
    }
}
