package pdk.chart.fx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pdk.chart.Chart;
import pdk.chart.ChartFactory;
import pdk.chart.api.HorizontalAlignment;
import pdk.chart.api.RectangleEdge;
import pdk.chart.data.general.DefaultPieDataset;
import pdk.chart.data.general.PieDataset;
import pdk.chart.plot.pie.PiePlot;
import pdk.chart.title.TextTitle;

import java.awt.*;
import java.awt.geom.Point2D;

/**
 * A pie chart demo in JavaFX.
 */
public class PieChartFXDemo1 extends Application {

    /**
     * Creates a sample dataset.
     * <p>
     * Source: http://www.bbc.co.uk/news/business-15489523
     *
     * @return A sample dataset.
     */
    private static PieDataset<String> createDataset() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        dataset.setValue("Samsung", 27.8);
        dataset.setValue("Others", 55.3);
        dataset.setValue("Nokia", 16.8);
        dataset.setValue("Apple", 17.1);
        return dataset;
    }

    /**
     * Creates a chart.
     *
     * @param dataset the dataset.
     * @return A chart.
     */
    private static Chart createChart(PieDataset<String> dataset) {

        Chart chart = ChartFactory.createPieChart(
                "Smart Phones Manufactured / Q3 2011", dataset);

        // set a custom background for the chart
        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0),
                new Color(20, 20, 20), new Point(400, 200), Color.DARK_GRAY));

        // customise the title position and font
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        // use gradients and white borders for the section colours
        plot.setSectionPaint("Others", createGradientPaint(new Color(200, 200, 255), Color.BLUE));
        plot.setSectionPaint("Samsung", createGradientPaint(new Color(255, 200, 200), Color.RED));
        plot.setSectionPaint("Apple", createGradientPaint(new Color(200, 255, 200), Color.GREEN));
        plot.setSectionPaint("Nokia", createGradientPaint(new Color(200, 255, 200), Color.YELLOW));
        plot.setDefaultSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setDefaultSectionOutlineStroke(new BasicStroke(2.0f));

        // customise the section label appearance
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);

        // add a subtitle giving the data source
        TextTitle source = new TextTitle("Source: http://www.bbc.co.uk/news/business-15489523",
                new Font("Courier New", Font.PLAIN, 12));
        source.setPaint(Color.WHITE);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);
        return chart;

    }

    /**
     * A utility method for creating gradient paints.
     *
     * @param c1 color 1.
     * @param c2 color 2.
     * @return A radial gradient paint.
     */
    private static RadialGradientPaint createGradientPaint(Color c1, Color c2) {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[]{c1, c2});
    }

    @Override
    public void start(Stage stage) {
        PieDataset<String> dataset = createDataset();
        Chart chart = createChart(dataset);
        ChartViewer viewer = new ChartViewer(chart);
        stage.setScene(new Scene(viewer));
        stage.setTitle("JFreeChart: PieChartFXDemo1.java");
        stage.setWidth(700);
        stage.setHeight(390);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
