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
    
    var longitudPromedio: Double = s.arrayDeVias.map(_.distancia).reduce(_ + _)/s.arrayDeVias.length
    
    //vehiculos en intersección
//    var promedioOrigen: Double = s.arrayDeIntersecciones.length
//    var promedioDestino: Double = s.arrayDeIntersecciones.length
//    var sinOrigen = 
//    var sinDestino =
    
    //tiempos
    var tiempoReal = s.tRefresh * s.dt
    
    //velocidades
    var arrayMagnitudes = s.arrayDeVehiculos.map(_.vel.magnitud)
    var velocidadMinima = arrayMagnitudes.min.toInt
    var velocidadMaxima = arrayMagnitudes.max.toInt
    var velocidadPromedio = arrayMagnitudes.reduce(_ + _) / arrayMagnitudes.length
    
    //distancias
    var arrayDistancias = s.arrayDeVias.map(_.distancia)
    var distanciaMin = arrayDistancias.max.toInt
    var distanciaMax = arrayDistancias.min.toInt
    var distanciaPromedio = arrayDistancias.reduce(_ + _) / arrayDistancias.length
      
    var vehiculos = new VehiculosR(v.totalVehiculos, v.cCarros, v.cMotos, v.cBuses, v.cCamiones, v.cMotoTaxis)
    var vehiculosInterseccion = new VehiculosInterseccion(50, 46, 5, 3)
    var mallaVial = new MallaVial(s.arrayDeVias.length, s.arrayDeIntersecciones.length, viasUnSentido, viasDobleSentido, 
        s.vehiculosMin, s.vehiculosMax, longitudPromedio, vehiculosInterseccion)
    var tiempos = new Tiempos(s.t, tiempoReal)
    var velocidades = new Velocidades(velocidadMinima, velocidadMaxima, velocidadPromedio)
    var distancias = new Distancias(distanciaMin, distanciaMax, distanciaPromedio.toInt)
    var resultadosSimulaciones = new ResultadosSimulacion(vehiculos, mallaVial, tiempos, velocidades, distancias)
    
    //Transformamos la instancia ResultadosSimulacion en un String
    var jsonString = write(resultadosSimulaciones)
    
    //Lo copiamos en el archivo
    pw.write(jsonString)
    pw.close
  }
}