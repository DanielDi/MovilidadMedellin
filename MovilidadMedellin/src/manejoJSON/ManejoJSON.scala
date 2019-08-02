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
    println("INTERSECCIONES: "+s.arrayDeIntersecciones.length)
    println("VEHICULOS: "+s.arrayDeVehiculos.length)
    println(s.arrayDeVias.length/s.arrayDeIntersecciones.length)
    
    var promedioOrigen: Double = s.arrayDeVehiculos.length / s.arrayDeIntersecciones.length.toDouble
    var promedioDestino: Double = s.arrayDeVehiculos.length / s.arrayDeIntersecciones.length.toDouble
    
    def sinOrigen(): Int = {
      var intersecciones = s.arrayDeIntersecciones
      var vehiculos = s.arrayDeVehiculos
      
      var count = 0
      
      var sinOrigen = intersecciones.map(i => 
        vehiculos.filter(v => (v.origen == i)).length).filter(_ == 0).length
      sinOrigen
    }
    
    def sinDestino(): Int = {
      var intersecciones = s.arrayDeIntersecciones
      var vehiculos = s.arrayDeVehiculos
      
      var count = 0
      
      var sinDestino = intersecciones.map(i => 
        vehiculos.filter(v => (v.posFinal == i)).length).filter(_ == 0).length
    
      sinDestino
    }
    
    //tiempos
    var tiempoReal = s.tRefresh * s.t
    
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
      
    var vehiculos = new VehiculosR(v.totalVehiculos, v.cCarros, v.cMotos, v.cBuses, v.cCamiones, v.cMotoTaxis)
    var vehiculosInterseccion = new VehiculosInterseccion(promedioOrigen, promedioDestino, sinOrigen, sinDestino)
    var mallaVial = new MallaVial(s.arrayDeVias.length, s.arrayDeIntersecciones.length, viasUnSentido, viasDobleSentido, 
        s.vehiculosMin, s.vehiculosMax, longitudPromedio, vehiculosInterseccion)
    var tiempos = new Tiempos(s.t, tiempoReal)
    var velocidades = new Velocidades(velocidadMinima, velocidadMaxima, velocidadPromedio)
    var distancias = new Distancias(distanciaMin, distanciaMax, distanciaPromedio)
    var resultadosSimulaciones = new ResultadosSimulacion(vehiculos, mallaVial, tiempos, velocidades, distancias)
    
    //Transformamos la instancia ResultadosSimulacion en un String
    var jsonString = write(resultadosSimulaciones)
    
    //Lo copiamos en el archivo
    pw.write(jsonString)
    pw.close
  }
}