package manejoJSON

import scala.io.Source
import java.io._
import scala.collection.mutable.ArrayBuffer
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
    
//    var promedioOrigen: Double = s.arrayDeIntersecciones
    
    var tiempoReal = s.tRefresh * s.dt
    
//    var velocidadMaxima = s.arrayDeVehiculos.max
    
    var vehiculos = new VehiculosR(v.totalVehiculos, v.cCarros, v.cMotos, v.cBuses, v.cCamiones, v.cMotoTaxis)
    var vehiculosInterseccion = new VehiculosInterseccion(50, 46, 5, 3)
    var mallaVial = new MallaVial(s.arrayDeVias.length, s.arrayDeIntersecciones.length, viasUnSentido, viasDobleSentido, 
        s.vehiculosMin, s.vehiculosMax, longitudPromedio, vehiculosInterseccion)
    var tiempos = new Tiempos(s.t, tiempoReal)
    var velocidades = new Velocidades(40, 80, 63)
    var distancias = new Distancias(523, 1540, 1250)
    var resultadosSimulaciones = new ResultadosSimulacion(vehiculos, mallaVial, tiempos, velocidades, distancias)
    
    //Transformamos la instancia ResultadosSimulacion en un String
    var jsonString = write(resultadosSimulaciones)
    
    //Lo copiamos en el archivo
    pw.write(jsonString)
    pw.close
  }
}