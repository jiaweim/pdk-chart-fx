package pdk.chart.fx.interaction;

import javafx.scene.input.ScrollEvent;
import pdk.chart.Chart;
import pdk.chart.fx.ChartCanvas;
import pdk.chart.model.ChartRenderingInfo;
import pdk.chart.plot.Plot;
import pdk.chart.plot.PlotRenderingInfo;
import pdk.chart.plot.Zoomable;
import pdk.chart.plot.pie.PiePlot;

import java.awt.geom.Point2D;

/**
 * Handles scroll events (mouse wheel etc) on a {@link ChartCanvas}.
 */
public class ScrollHandlerFX extends AbstractMouseHandlerFX
        implements MouseHandlerFX {

    /**
     * The zoom factor.
     */
    private double zoomFactor = 0.1;

    /**
     * Creates a new instance with the specified ID.
     *
     * @param id the handler ID ({@code null} not permitted).
     */
    public ScrollHandlerFX(String id) {
        super(id, false, false, false, false);
        this.zoomFactor = 0.1;
    }

    /**
     * Returns the zoom factor.  The default value is 0.10 (ten percent).
     *
     * @return The zoom factor.
     */
    public double getZoomFactor() {
        return this.zoomFactor;
    }

    /**
     * Sets the zoom factor (a percentage amount by which the mouse wheel
     * movement will change the chart size).
     *
     * @param zoomFactor the zoom factor.
     */
    public void setZoomFactor(double zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    @Override
    public void handleScroll(ChartCanvas canvas, ScrollEvent e) {
        Chart chart = canvas.getChart();
        if (chart == null) {
            return;
        }
        Plot plot = chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable zoomable = (Zoomable) plot;
            handleZoomable(canvas, zoomable, e);
        } else if (plot instanceof PiePlot) {
            PiePlot<?> pp = (PiePlot<?>) plot;
            pp.handleMouseWheelRotation((int) e.getDeltaY());
        }
    }

    /**
     * Handle the case where a plot implements the {@link Zoomable} interface.
     *
     * @param canvas   the chart canvas.
     * @param zoomable the zoomable plot.
     * @param e        the mouse wheel event.
     */
    private void handleZoomable(ChartCanvas canvas, Zoomable zoomable,
            ScrollEvent e) {
        if (canvas.getChart() == null) {
            return;
        }
        // don't zoom unless the mouse pointer is in the plot's data area
        ChartRenderingInfo info = canvas.getRenderingInfo();
        PlotRenderingInfo pinfo = info.getPlotInfo();
        Point2D p = new Point2D.Double(e.getX(), e.getY());
        if (pinfo.getDataArea().contains(p)) {
            Plot plot = (Plot) zoomable;
            // do not notify while zooming each axis
            boolean notifyState = plot.isNotify();
            plot.setNotify(false);
            int clicks = (int) e.getDeltaY();
            double zf = 1.0 + this.zoomFactor;
            if (clicks < 0) {
                zf = 1.0 / zf;
            }
            if (canvas.isDomainZoomable()) {
                zoomable.zoomDomainAxes(zf, pinfo, p, true);
            }
            if (canvas.isRangeZoomable()) {
                zoomable.zoomRangeAxes(zf, pinfo, p, true);
            }
            plot.setNotify(notifyState);  // this generates the change event too
        }
    }

}
