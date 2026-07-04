/**
 * <b>pdk-chart-fx</b> is an extension for pdk-chart
 * to provide JavaFX controls for displaying and interacting with charts created pdk-chart.
 */
module pdk.chart.fx {
    requires java.desktop;

    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.swing;
    requires pdk.chart;
    requires com.orsonpdf.orsonpdf;

    exports pdk.chart.fx;
    exports pdk.chart.fx.graphics;
    exports pdk.chart.fx.interaction;
    exports pdk.chart.fx.overlay;
}