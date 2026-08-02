package pdk.chart.fx.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pdk.chart.Chart;
import pdk.chart.LineChart;
import pdk.chart.api.RectangleEdge;
import pdk.chart.axis.ValueAxis;
import pdk.chart.data.general.DatasetUtils;
import pdk.chart.data.xy.XYDataset;
import pdk.chart.data.xy.XYSeries;
import pdk.chart.data.xy.XYSeriesCollection;
import pdk.chart.fx.ChartViewer;
import pdk.chart.fx.interaction.ChartMouseEventFX;
import pdk.chart.fx.interaction.ChartMouseListenerFX;
import pdk.chart.fx.overlay.CrosshairOverlayFX;
import pdk.chart.plot.Crosshair;
import pdk.chart.plot.XYPlot;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * A demo showing crosshairs that follow the data points on an XYPlot.
 */
public class CrosshairOverlayFXDemo1 extends Application {

    static class MyDemoPane extends StackPane implements ChartMouseListenerFX {

        private final ChartViewer chartViewer;

        private final Crosshair xCrosshair;

        private final Crosshair yCrosshair;

        public MyDemoPane() {
            XYDataset dataset = createDataset();
            Chart chart = createChart(dataset);
            this.chartViewer = new ChartViewer(chart);
            this.chartViewer.addChartMouseListener(this);
            getChildren().add(this.chartViewer);

            CrosshairOverlayFX crosshairOverlay = new CrosshairOverlayFX();
            this.xCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                    new BasicStroke(0f));
            this.xCrosshair.setStroke(new BasicStroke(1.5f,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
                    new float[]{2.0f, 2.0f}, 0));
            this.xCrosshair.setLabelVisible(true);
            this.yCrosshair = new Crosshair(Double.NaN, Color.GRAY,
                    new BasicStroke(0f));
            this.yCrosshair.setStroke(new BasicStroke(1.5f,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1,
                    new float[]{2.0f, 2.0f}, 0));
            this.yCrosshair.setLabelVisible(true);
            crosshairOverlay.addDomainCrosshair(xCrosshair);
            crosshairOverlay.addRangeCrosshair(yCrosshair);

            Platform.runLater(() -> this.chartViewer.getCanvas().addOverlay(crosshairOverlay));
        }

        @Override
        public void chartMouseClicked(ChartMouseEventFX event) {
            // ignore
        }

        @Override
        public void chartMouseMoved(ChartMouseEventFX event) {
            Rectangle2D dataArea = this.chartViewer.getCanvas().getRenderingInfo().getPlotInfo().getDataArea();
            Chart chart = event.getChart();
            XYPlot plot = (XYPlot) chart.getPlot();
            ValueAxis xAxis = plot.getDomainAxis();
            double x = xAxis.java2DToValue(event.getTrigger().getX(), dataArea,
                    RectangleEdge.BOTTOM);
            // make the crosshairs disappear if the mouse is out of range
            if (!xAxis.getRange().contains(x)) {
                x = Double.NaN;
            }
            double y = DatasetUtils.findYValue(plot.getDataset(), 0, x);
            this.xCrosshair.setValue(x);
            this.yCrosshair.setValue(y);
        }

    }

    private static XYDataset createDataset() {
        XYSeries series = new XYSeries("S1");
        for (int x = 0; x < 10; x++) {
            series.add(x, x + Math.random() * 4.0);
        }
        return new XYSeriesCollection(series);
    }

    private static Chart createChart(XYDataset dataset) {
        return new LineChart(dataset, "X", "Y", "CrosshairOverlayDemo1");
    }

    /**
     * Adds a chart viewer to the stage and displays it.
     *
     * @param stage the stage.
     */
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new MyDemoPane()));
        stage.setTitle("JFreeChart: CrosshairOverlayFXDemo1.java");
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show();
    }

    /**
     * Entry point.
     *
     * @param args the command line arguments
     */
    static void main(String[] args) {
        launch(args);
    }

}
