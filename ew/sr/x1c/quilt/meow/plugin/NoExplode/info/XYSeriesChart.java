package ew.sr.x1c.quilt.meow.plugin.NoExplode.info;

import java.awt.Color;
import java.awt.Font;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.data.xy.XYSeriesCollection;

public class XYSeriesChart {

    // 處理中文字體
    public static void initTheme() {
        StandardChartTheme theme = new StandardChartTheme("TW");
        theme.setExtraLargeFont(new Font("微軟正黑體", Font.BOLD, 20));
        theme.setRegularFont(new Font("微軟正黑體", Font.PLAIN, 14));
        theme.setLargeFont(new Font("微軟正黑體", Font.PLAIN, 14));
        theme.setSmallFont(new Font("微軟正黑體", Font.PLAIN, 12));
        ChartFactory.setChartTheme(theme);
    }

    public static JFreeChart createChart(XYSeriesCollection dataSet, String title) {
        JFreeChart chart = ChartFactory.createXYAreaChart(title, null, null, dataSet, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot) chart.getPlot();

        // 透明度
        plot.setBackgroundAlpha(0.5F);
        plot.setForegroundAlpha(0.5F);

        ValueAxis value = plot.getDomainAxis();
        value.setUpperMargin(0);
        value.setVisible(false);

        XYAreaRenderer renderer = (XYAreaRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.GREEN);
        return chart;
    }

    public static JFreeChart createChart(XYSeriesCollection dataSet, String title, int minRange, int maxRange) {
        JFreeChart chart = createChart(dataSet, title);
        XYPlot plot = (XYPlot) chart.getPlot();

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setRange(minRange, maxRange);
        return chart;
    }
}
