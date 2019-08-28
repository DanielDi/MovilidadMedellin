package manejoJSON

import scala.io.Source
import java.io._
import scala.collection.mutable.ArrayBuffer
import scala.math._
import java.util.Properties
import net.liftweb.json.DefaultFormats
import net.liftweb.json._
import net.liftweb.json.Serialization.write
import parametrosSimulacion._
import resultadosSimulacion._
import movil.{Vehiculo => v}
import main.{Simulacion => s}
import punto._

class ManejoJSON {
  
  implicit val formats = DefaultFormats
  
  def leerArchivo(ruta: String): ParametrosSimulacion = {
    val archivo = parse(Source.fromFile(ruta).getLines.mkString)
        
    //json is a JValue instance
    val jsonParametros = archivo.extract[JSONParametro]
    var parametrosSimulacion = jsonParametros.parametrosSimulacion // parametrosSimulacion es una instancia JValue
    parametrosSimulacion
  }
  
  def escribirArchivoResultados(ruta: String) {
    
    //Utilizamos PrintWriter una clase propia de Java 
    var pw = new PrintWriter(new File(ruta))
    
    //obtenemos todas las variables necesarias para escribir en el archivo de resultados
    var viasUnSentido: Int = s.arrayDeVias.filter(_.sentido.unavia).length
    var viasDobleSentido: Int = s.arrayDeVias.filter(_.sentido.doblevia).length
    
    var longitudPromedio: Double = s.arrayDeVias.map(_.distancia).sum / s.arrayDeVias.length
    
    //vehiculos en intersección
    var promedioOrigen: Double = s.arrayDeVehiculos.length / s.arrayDeIntersecciones.length.toDouble
    var promedioDestino: Double = s.arrayDeVehiculos.length / s.arrayDeIntersecciones.length.toDouble
    
    def sinOrigen(): Int = {
      var intersecciones = s.arrayDeIntersecciones
      var vehiculos = s.arrayDeVehiculos
      var viajes = s.arrayDeViajes
      
      var count = 0
      
      var sinOrigen = intersecciones.map(i => 
        viajes.filter(v => (v.origen == i)).length).filter(_ == 0).length
      sinOrigen
    }
    
    def sinDestino(): Int = {
      var intersecciones = s.arrayDeIntersecciones
      var vehiculos = s.arrayDeVehiculos
      var viajes = s.arrayDeViajes
      
      var count = 0
      
      var sinDestino = intersecciones.map(i => 
        viajes.filter(v => (v.posFinal == i)).length).filter(_ == 0).length
    
      sinDestino
    }
    
    //tiempos
    var tiempoReal = (s.tRefresh.toDouble/1000) * s.t/s.dt
    
    //velocidades
    var arrayMagnitudes = s.arrayDeVehiculos.map(_.vel.magnitud)
    var velocidadMinima = arrayMagnitudes.min.toInt
    var velocidadMaxima = arrayMagnitudes.max.toInt
    var velocidadPromedio = arrayMagnitudes.reduce(_ + _) / arrayMagnitudes.length
    
    //distancias
    var arrayDistancias = s.arrayDeVias.map(_.distancia)
    var distanciaMin = arrayDistancias.min.toInt
    var distanciaMax = arrayDistancias.max.toInt
    var distanciaPromedio = arrayDistancias.reduce(_ + _) / arrayDistancias.length
      
    //Comparendos
    var cantidad = s.comparendos.length
    var promedioPorcentajeExceso = s.comparendos.map(p => (p.vel-p.vMax)*100/p.vMax).reduce(_ + _)/cantidad
    
    var vehiculos = new VehiculosR(s.totalVehiculos, s.cCarros, s.cMotos, s.cBuses, s.cCamiones, s.cMotoTaxis)
    var vehiculosInterseccion = new VehiculosInterseccion(promedioOrigen, promedioDestino, sinOrigen, sinDestino)
    var mallaVial = new MallaVial(s.arrayDeVias.length, s.arrayDeIntersecciones.length, viasUnSentido, viasDobleSentido, 
        s.vehiculosMin, s.vehiculosMax, longitudPromedio, vehiculosInterseccion)
    var tiempos = new Tiempos(s.t, tiempoReal)
    var velocidades = new Velocidades(velocidadMinima, velocidadMaxima, velocidadPromedio)
    var distancias = new Distancias(distanciaMin, distanciaMax, distanciaPromedio)
    var comparendos = new Comparendos(cantidad,promedioPorcentajeExceso)
    var resultadosSimulaciones = new ResultadosSimulacion(vehiculos, mallaVial, tiempos, velocidades, distancias,comparendos)
    
    //Transformamos la instancia ResultadosSimulacion en un String
    var jsonString = write(resultadosSimulaciones)
    
    //Lo copiamos en el archivo
    pw.write(jsonString)
    pw.close
  }
}

// promedioPorcentaje Exceso = Simulacion.comparendos.map(p => (p.vel.magnitud-p.vMax)*100/p.vMax).reduce(_+_)/Simulacion.comparendos.lenght