package main

import manejoJSON.ManejoJSON
import movil.Vehiculo

object Main extends App {
  val ruta = "C:\\Users\\sebas\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
//  val ruta = "C:\\Users\\MSI-PC\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
//  val ruta = "C:\\Users\\sebas\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
  //val ruta = "C:\\Users\\DELL\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
  val archivoParametros = "parametros.json"
  val archivoResultados = "resultados.json"
  
  var manejoJSON = new ManejoJSON()
  var parametrosSimulacion = manejoJSON.leerArchivo(ruta + archivoParametros)
  manejoJSON.escribirArchivoResultados(ruta + archivoResultados)
  
  Simulacion.crearGrafico
  Simulacion.hilo = new Thread(Simulacion)
//  println(Simulacion.arrayDeVehiculos.mkString(", "))
  
  def iniciar(){
    Simulacion.t = 0
    Simulacion.iniciarVias
    println("**********ACA ESTOY HP: "+ Simulacion.arrayDeIntersecciones)
	  Simulacion.crearGrafo
	  Simulacion.hilo = new Thread(Simulacion)
    Vehiculo.crearVehiculos()
    Simulacion.iniciar
  }
}