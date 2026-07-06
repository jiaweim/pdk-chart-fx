package pdk.chart.fx.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pdk.chart.Chart;
import pdk.chart.JChart;
import pdk.chart.axis.NumberAxis;
import pdk.chart.block.BlockBorder;
import pdk.chart.data.category.CategoryDataset;
import pdk.chart.data.category.DefaultCategoryDataset;
import pdk.chart.fx.ChartViewer;
import pdk.chart.fx.interaction.ChartMouseEventFX;
import pdk.chart.fx.interaction.ChartMouseListenerFX;
import pdk.chart.plot.CategoryPlot;
import pdk.chart.renderer.category.BarRenderer;
import pdk.chart.title.TextTitle;

import java.awt.*;

/**
 * A demo showing the display of JFreeChart within a JavaFX application.
 */
public class BarChartFXDemo1 extends Application implements ChartMouseListenerFX {

    /**
     * Returns a sample dataset.
     *
     * @return The dataset.
     */
    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(7445, "JFreeSVG", "Warm-up");
        dataset.addValue(24448, "Batik", "Warm-up");
        dataset.addValue(4297, "JFreeSVG", "Test");
        dataset.addValue(21022, "Batik", "Test");
        return dataset;
    }

    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     * @return The chart.
     */
    private static Chart createChart(CategoryDataset dataset) {
        Chart chart = JChart.bar(dataset, null, "Milliseconds", "Performance: JFreeSVG vs Batik");
        chart.addSubtitle(new TextTitle("Time to generate 1000 charts in SVG "
                + "format (lower bars = better performance)"));
        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();

        // set transparent background paint, so the background color of the parent is used
//        java.awt.Color transparent = new java.awt.Color(1.0f, 1.0f, 1.0f, 0f);
//        chart.setBackgroundPaint(transparent);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        chart.getLegend().setFrame(BlockBorder.NONE);
        return chart;
    }

    /**
     * Adds a chart viewer to the stage and displays it.
     *
     * @param stage the stage.
     */
    @Override
    public void start(Stage stage) {
        CategoryDataset dataset = createDataset();
        Chart chart = createChart(dataset);
        ChartViewer viewer = new ChartViewer(chart);
        viewer.addChartMouseListener(this);
        stage.setScene(new Scene(viewer));
        stage.setTitle("BarChartFXDemo1.java");
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

    /**
     * Write the event to the console, just to illustrate.
     *
     * @param event event info.
     */
    @Override
    public void chartMouseClicked(ChartMouseEventFX event) {
        System.out.println(event);
    }

    /**
     * Write the event to the console, just to illustrate.
     *
     * @param event event info.
     */
    @Override
    public void chartMouseMoved(ChartMouseEventFX event) {
        System.out.println(event);
    }

}