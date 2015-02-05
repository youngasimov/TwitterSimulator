package usm.simulacion.twitter.io;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author herbyn
 */
public class GraphOut {

    public GraphOut() {

    }

    public  void pastel(DefaultPieDataset dataset1, String nombre ) throws IOException{
        JFreeChart grafica = ChartFactory.createPieChart(nombre, dataset1,true,true,false);
        //ChartFrame frame = new ChartFrame("saltos", grafica);
        //frame.pack();
        //frame.setVisible(true);
        ChartUtilities.saveChartAsJPEG(new File(nombre+".jpg"), grafica , 500,300);
    }

   public void barras(DefaultCategoryDataset dataset, String nombre, String ejeX, String ejeY) throws IOException {

        JFreeChart chart = ChartFactory.createBarChart(nombre,ejeX,ejeY,dataset,PlotOrientation.VERTICAL,true,true,false);
        //ChartFrame frame = new ChartFrame(nombre, chart);
        //frame.pack();
        //frame.setVisible(true);
        ChartUtilities.saveChartAsJPEG(new File(nombre+".jpg"), chart, 500, 300);
    }
}
