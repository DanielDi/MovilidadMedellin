package main

import manejoJSON.ManejoJSON
import movil.Vehiculo
import org.neo4j.driver.v1._
import scala.collection.mutable.ArrayBuffer

import punto.Interseccion
import punto.Via

object Main extends App {

//  val ruta = "C:\\Users\\nclsc\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
//   val ruta = "C:\\Users\\Sebastian\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
//  val ruta = "C:\\Users\\MSI-PC\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
  val ruta = "C:\\Users\\DELL\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
  
  val archivoParametros = "parametros.json"
  val archivoResultados = "resultados.json"
  
  var manejoJSON = new ManejoJSON()
  var parametrosSimulacion = manejoJSON.leerArchivo(ruta + archivoParametros)
  
  Simulacion.crearGrafico
  Simulacion.hilo = new Thread(Simulacion)
  Simulacion.iniciarVias
  Simulacion.crearGrafo
  Simulacion.crearSemaforo()
  
  def iniciar(){
    Simulacion.t = 0
	  Simulacion.hilo = new Thread(Simulacion)
    Simulacion.crearVehiculos()
    Simulacion.iniciar
  }
   
  def iniciarCargado(){
    Simulacion.t = 0
	  Simulacion.crearGrafo
	  Simulacion.hilo = new Thread(Simulacion)
    Simulacion.iniciar
  }
    
  def llamar = manejoJSON.escribirArchivoResultados(ruta + archivoResultados)
    
}









