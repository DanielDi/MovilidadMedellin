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
    
    var vehiculos = new VehiculosR(410, 120, 150, 80, 50, 10)
    var vehiculosInterseccion = new VehiculosInterseccion(50, 46, 5, 3)
    var mallaVial = new MallaVial(50, 15, 10, 40, 60, 80, 422, vehiculosInterseccion)
    var tiempos = new Tiempos(600, 50)
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