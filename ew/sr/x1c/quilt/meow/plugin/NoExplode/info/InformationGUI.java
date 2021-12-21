package ew.sr.x1c.quilt.meow.plugin.NoExplode.info;

import ew.sr.x1c.quilt.meow.plugin.NoExplode.Main;
import ew.sr.x1c.quilt.meow.plugin.NoExplode.base.UptimeCommand;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class InformationGUI extends JFrame {

    private static final int SIZE = 25;
    private static final int PANEL_WIDTH = 838, PANEL_HEIGHT = 125;

    private static final List<Double> CPU = new LinkedList<>();
    private static final List<Double> MEMORY = new LinkedList<>();
    private static final List<Double> TPS = new LinkedList<>();

    private final Image icon;

    private int current;
    private int maxMemory;

    private JPanel cpuChartPanel, memoryChartPanel, tpsChartPanel;

    public InformationGUI() {
        icon = new ImageIcon(getClass().getClassLoader().getResource("ew/sr/x1c/image/Icon.png")).getImage();
        initComponents();
        XYSeriesChart.initTheme();

        setPanelSize(panelCPU, PANEL_WIDTH, PANEL_HEIGHT);
        setPanelSize(panelMemory, PANEL_WIDTH, PANEL_HEIGHT);
        setPanelSize(panelTPS, PANEL_WIDTH, PANEL_HEIGHT);

        setBorderLayout(panelCPU);
        setBorderLayout(panelMemory);
        setBorderLayout(panelTPS);
    }

    private static void setBorderLayout(JPanel panel) {
        panel.setLayout(new BorderLayout());
    }

    private static void setPanelSize(JPanel panel, int width, int height) {
        panel.setPreferredSize(new Dimension(width, height));
    }

    public void registerUpdateTask() {
        new BukkitRunnable() {

            @Override
            public void run() {
                if (maxMemory == 0) {
                    maxMemory = Integer.parseInt(PlaceholderAPI.setPlaceholders(null, "%server_ram_max%"));
                }

                double tps = Bukkit.getTPS()[0];
                String rawCPU = ChatColor.stripColor(PlaceholderAPI.setPlaceholders(null, "%spark_cpu_process_10s%"));
                double cpu = Double.parseDouble(rawCPU.substring(0, rawCPU.length() - 1));
                double memory = Double.parseDouble(PlaceholderAPI.setPlaceholders(null, "%server_ram_used%"));

                StringBuilder sb = new StringBuilder().append("<html>");
                sb.append(PlaceholderAPI.setPlaceholders(null, "線上人數 : %server_online% / %server_max_players%")).append("<br />");
                sb.append(ChatColor.stripColor(PlaceholderAPI.setPlaceholders(null, "TPS : %spark_tps%"))).append("<br />");
                sb.append(ChatColor.stripColor(PlaceholderAPI.setPlaceholders(null, "CPU 使用率 系統 : %spark_cpu_system% 伺服器 : %spark_cpu_process%"))).append("<br />");
                sb.append(PlaceholderAPI.setPlaceholders(null, "記憶體 已使用 : %server_ram_used% MB 剩餘 : %server_ram_free% MB 總計 : %server_ram_total% MB 最大 : %server_ram_max% MB")).append("<br />");
                sb.append(PlaceholderAPI.setPlaceholders(null, "載入區塊數 : %server_total_chunks% 生物實體數 : %server_total_living_entities% 總實體數 : %server_total_entities%")).append("<br />");
                sb.append("伺服器運行時間 : ").append(UptimeCommand.getUpTime()).append("</html>");

                if (++current > SIZE) {
                    CPU.remove(0);
                    MEMORY.remove(0);
                    TPS.remove(0);
                }

                CPU.add(cpu);
                MEMORY.add(memory);
                TPS.add(tps);

                SwingUtilities.invokeLater(() -> {
                    labelInformation.setText(sb.toString());
                    generateChart();
                });
            }
        }.runTaskTimerAsynchronously(Main.getPlugin(), 20 * 5, 20 * 5);
    }

    private static XYSeriesCollection toXYSeriesDataSet(String title, List<Double> data) {
        XYSeries series = new XYSeries(title);
        int count = 0;
        for (Double value : data) {
            series.add(count++, value);
        }
        XYSeriesCollection ret = new XYSeriesCollection();
        ret.addSeries(series);
        return ret;
    }

    // 產生折線圖
    private void generateChart() {
        JFreeChart chartCPU = XYSeriesChart.createChart(toXYSeriesDataSet("CPU", CPU), "CPU 使用率", 0, 100);
        JFreeChart chartMemory = XYSeriesChart.createChart(toXYSeriesDataSet("記憶體", MEMORY), "記憶體使用量", 0, maxMemory);
        JFreeChart chartTPS = XYSeriesChart.createChart(toXYSeriesDataSet("TPS", TPS), "伺服器 TPS", 0, 20);

        cpuChartPanel = updateChart(chartCPU, panelCPU, cpuChartPanel);
        memoryChartPanel = updateChart(chartMemory, panelMemory, memoryChartPanel);
        tpsChartPanel = updateChart(chartTPS, panelTPS, tpsChartPanel);
    }

    public static JPanel updateChart(JFreeChart chart, JPanel panel, JPanel last) {
        ChartPanel chartPanel = new ChartPanel(chart) {

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
            }
        };

        if (last != null) {
            panel.remove(last);
        }
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.validate();
        return chartPanel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelInformation = new javax.swing.JLabel();
        panelGraph = new javax.swing.JPanel();
        panelTPS = new javax.swing.JPanel();
        panelCPU = new javax.swing.JPanel();
        panelMemory = new javax.swing.JPanel();

        setTitle("伺服器狀態");
        setIconImage(icon);
        setName("InformationFrame"); // NOI18N
        setResizable(false);

        labelInformation.setText("系統資訊");

        javax.swing.GroupLayout panelTPSLayout = new javax.swing.GroupLayout(panelTPS);
        panelTPS.setLayout(panelTPSLayout);
        panelTPSLayout.setHorizontalGroup(
            panelTPSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        panelTPSLayout.setVerticalGroup(
            panelTPSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelCPULayout = new javax.swing.GroupLayout(panelCPU);
        panelCPU.setLayout(panelCPULayout);
        panelCPULayout.setHorizontalGroup(
            panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 350, Short.MAX_VALUE)
        );
        panelCPULayout.setVerticalGroup(
            panelCPULayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelMemoryLayout = new javax.swing.GroupLayout(panelMemory);
        panelMemory.setLayout(panelMemoryLayout);
        panelMemoryLayout.setHorizontalGroup(
            panelMemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelMemoryLayout.setVerticalGroup(
            panelMemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 125, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelGraphLayout = new javax.swing.GroupLayout(panelGraph);
        panelGraph.setLayout(panelGraphLayout);
        panelGraphLayout.setHorizontalGroup(
            panelGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGraphLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCPU, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTPS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMemory, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelGraphLayout.setVerticalGroup(
            panelGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelGraphLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCPU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMemory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTPS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(labelInformation)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void initLookAndFeel() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelInformation;
    private javax.swing.JPanel panelCPU;
    private javax.swing.JPanel panelGraph;
    private javax.swing.JPanel panelMemory;
    private javax.swing.JPanel panelTPS;
    // End of variables declaration//GEN-END:variables
}
