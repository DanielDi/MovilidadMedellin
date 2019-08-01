package main

import punto.Interseccion
import punto.Via
import movil._
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.jfree.data.general.DatasetGroup

object Grafico extends KeyListener{
  var estadoThread = true                //auxiliar para verificar si el hilo esta corriendo
  
  var dataset = new XYSeriesCollection()
    
  var grafica = ChartFactory.createScatterPlot("", "", "", dataset,PlotOrientation.VERTICAL,false,false,false)
  
  var graficaPlot = grafica.getXYPlot() 
  
  var render = new XYLineAndShapeRenderer()      // Para el diseño de las lineas
  
  var n = 0                                      //Variable auxiliar para asignar ID a los dataset
  
  var numVias = 0                         //auxuliar que guarda la posición el primer vehiculo en el dataset. El numero de vias.
    
  def graficarVias(vias:ArrayBuffer[Via], intersecciones:ArrayBuffer[Interseccion]){
    numVias = vias.length                
    render.setStroke(new BasicStroke(4))           //Asigna grosor
    render.setBaseShapesVisible(true)              //Poner visibilidad de los puntos
    render.setBaseSeriesVisible(true)              //Hacer visibles las lineas
                                                  //Creación de las lineas de las vías
    vias.foreach({x => val via = new XYSeries(n)
      via.add(x.interO.xI,x.interO.yI)            //Añadir punto origen de la via 
      via.add(x.interF.xI, x.interF.yI)            //añadir punto fin de la via
      dataset.addSeries(via)                        // Añadir via al dataset
      render.setSeriesShapesVisible(n, false)        //No mostrar forma de los puntos
      render.setSeriesPaint(n,Color.lightGray)      //Asignar color a las vias
      n += 1                                        //Aumentar al ID 
    })
                                                    //Añadir intersecciones a la gráfica
    intersecciones.foreach({
      x => val interseccion = new XYTextAnnotation(x.nombre,x.xI,x.yI+0.1)
      graficaPlot.addAnnotation(interseccion)
    })
    graficaPlot.getRangeAxis().setVisible(false)   // ocultar eje x
    graficaPlot.getDomainAxis().setVisible(false)   // ocultar eje y
    graficaPlot.setDomainGridlinesVisible(false)   //Quitar la grilla
    graficaPlot.setBackgroundPaint(Color.white)   //Color del fondo
    graficaPlot.setRenderer(render)               //Set de las modificaciones graficas
    
    var panel = new ChartPanel(grafica)         //donde se dibuja la grafica
    
    var ventana = new JFrame("Proyecto") 
    
    ventana.setVisible(true)
    
    ventana.setSize(800, 600)
    
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
     
    ventana.add(panel)
    
    ventana.addKeyListener(this)
  }
    
  def graficarVehiculos(vehiculos: ArrayBuffer[Vehiculo]){
    if(dataset.getSeriesCount  == numVias){    	                //Si no hay carros 
			vehiculos.foreach({v => val vehiculo = new XYSeries(n)    // Añadir Data set 
  			vehiculo.add(v.posInicial.xI,v.posInicial.yI)
  			dataset.addSeries(vehiculo)
  			render.setSeriesPaint(n, colorVehiculo(v))
  			render.setSeriesShapesVisible(n, true)
  			n += 1
			})
    }
    else{                                                       // Si ya hay vehiculos (desde la segunda iteracion)
      var x = numVias
      vehiculos.foreach({v =>
        dataset.getSeries(x).remove(0)                          // Quitar punto anterior
        dataset.getSeries(x).add(v.posInicial.xI, v.posInicial.yI)  //Asignar punto nuevo
        x += 1
      })
    } 
  }
//  
  def colorVehiculo(v: Vehiculo): Color = v match {
    case v: Bus => Color.BLUE
    case v: Camion => Color.RED
    case v: Carro => Color.GREEN
    case v: Moto => Color.MAGENTA
    case v: MotoTaxi => Color.YELLOW
  }
  
  def removerVehiculos{
    Simulacion.arrayDeVehiculos = ArrayBuffer[Vehiculo]()
    for(i <- numVias until dataset.getSeriesCount-1) dataset.removeSeries(numVias)
    println(Simulacion.arrayDeVehiculos.mkString(","))
  }
  
  def keyTyped(x: KeyEvent) = {}
  def keyReleased(x : KeyEvent) = {}
  def keyPressed(e: KeyEvent) {
    var key = e.getKeyCode();
    if(key == KeyEvent.VK_F5){
//      Simulacion.hilo.
//      GrafoVia.g.clear()
      if(dataset.getSeriesCount != numVias) removerVehiculos
      Main.iniciar()
    }
    else if(key == KeyEvent.VK_F6){
      if (estadoThread) {
        Simulacion.hilo.suspend()
        estadoThread = false
      }
      else {
        Simulacion.hilo.resume()
        estadoThread = true
      }
    }
  }
}

