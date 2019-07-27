package main

import punto.Interseccion
import punto.Via
import movil.Vehiculo
import scala.collection.mutable.ArrayBuffer
import org.jfree.chart.ChartFactory
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.XYPlot
import javax.swing.JFrame
import java.awt.BasicStroke
import java.awt.Color
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator
import java.text.NumberFormat
import org.jfree.chart.labels.XYItemLabelGenerator
import org.jfree.chart.labels.StandardXYItemLabelGenerator
import org.jfree.chart.annotations.XYTextAnnotation

class Grafico {
  
  def graficarVias(vias:ArrayBuffer[Via], intersecciones:ArrayBuffer[Interseccion]){
    
    var dataset = new XYSeriesCollection()
    
    //Creación de las lineas de las vías
    var n = 0
    vias.foreach({x => val via = new XYSeries(n)
      via.add(x.interO.xI,x.interO.yI)
      via.add(x.interF.xI, x.interF.yI)
      dataset.addSeries(via)
      n += 1
    })
    
    var grafica = ChartFactory.createScatterPlot("", "", "", dataset,PlotOrientation.VERTICAL,false,false,false)
    
    var graficaPlot = grafica.getXYPlot() 
    
    var render = new XYLineAndShapeRenderer()  // Para el diseño de las lineas
    render.setStroke(new BasicStroke(4)) //Asigna grosor  
    render.setPaint(Color.lightGray) // Color linea
    render.setBaseShapesVisible(false) //Quitar visibilidad de los puntos
    render.setBaseSeriesVisible(true) //Hacer visibles las lineas
    
    
    //Añadir intersecciones a la gráfica
    intersecciones.foreach({x => val interseccion = new XYTextAnnotation(x.nombre,x.xI,x.yI+0.1)
      graficaPlot.addAnnotation(interseccion)
    })

    graficaPlot.getRangeAxis().setVisible(false) // ocultar eje x
    graficaPlot.getDomainAxis().setVisible(false) // ocultar eje y
    graficaPlot.setDomainGridlinesVisible(false) //Quitar la grilla
    graficaPlot.setBackgroundPaint(Color.white) //Color del fondo
    graficaPlot.setRenderer(render) //Set de las modificaciones graficas
    
    var panel = new ChartPanel(grafica) //donde se dibuja la grafica
    
    var ventana = new JFrame("Proyecto") 
    
    ventana.setVisible(true)
    
    ventana.setSize(800, 600)
    
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
     
    ventana.add(panel)    
  }
  
  def graficarVehiculos(vehiculos: ArrayBuffer[Vehiculo]){
    
  }
}