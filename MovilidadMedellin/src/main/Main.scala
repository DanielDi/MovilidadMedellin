package main

import manejoJSON.ManejoJSON
import movil.Vehiculo

object Main extends App {
<<<<<<< HEAD
  
    val ruta = "C:\\Users\\nclsc\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
=======
  val ruta = "C:\\Users\\nclsc\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
//  val ruta = "C:\\Users\\MSI-PC\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
>>>>>>> refs/heads/master
//  val ruta = "C:\\Users\\sebas\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
<<<<<<< HEAD
//  val ruta = "C:\\Users\\MSI-PC\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
  //val ruta = "C:\\Users\\DELL\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
=======
//  val ruta = "C:\\Users\\DELL\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
>>>>>>> refs/heads/master
  val archivoParametros = "parametros.json"
  val archivoResultados = "resultados.json"
  
  var manejoJSON = new ManejoJSON()
  var parametrosSimulacion = manejoJSON.leerArchivo(ruta + archivoParametros)
  
  Simulacion.crearGrafico
  Simulacion.hilo = new Thread(Simulacion)
  
  def iniciar(){
    Simulacion.t = 0
    Simulacion.iniciarVias
	  Simulacion.crearGrafo
	  Simulacion.hilo = new Thread(Simulacion)
    Vehiculo.crearVehiculos()
    Simulacion.iniciar
    println("tiempo : " + Simulacion.t)
  }
    
    def llamar = manejoJSON.escribirArchivoResultados(ruta + archivoResultados)
    
}