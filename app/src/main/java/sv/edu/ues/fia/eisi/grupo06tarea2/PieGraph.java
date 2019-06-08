package sv.edu.ues.fia.eisi.grupo06tarea2;

import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

public class PieGraph
{
    public GraphicalView getGraphicalView(Context context, int largeSlide, int mediumSlide,
                                          int tinySlide)
    {
        CategorySeries series = new CategorySeries("NUTRICIÓN");

        int [] portions={largeSlide,mediumSlide,tinySlide};
        String [] seriesNames= new String[] {"CARBOHIDRATOS 53%", "GRASAS 35%","PROTEÍNAS 12%"};

        int numSlide=3;
        for (int i=0; i<numSlide; i++)
        {
            series.add(seriesNames[i],portions[i]);

        }

        DefaultRenderer defaultRenderer=new DefaultRenderer();
        SimpleSeriesRenderer simpleSeriesRenderer=null;
        int [] colors = {Color.LTGRAY,Color.MAGENTA,Color.GREEN};


        for (int i=0; i<numSlide; i++)
        {
            simpleSeriesRenderer = new SimpleSeriesRenderer();
            simpleSeriesRenderer.setColor(colors[i]);
            defaultRenderer.addSeriesRenderer(simpleSeriesRenderer);
        }
        return ChartFactory.getPieChartView(context, series,defaultRenderer);
    }
}
