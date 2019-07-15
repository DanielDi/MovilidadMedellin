package main

import manejoJSON.ManejoJSON
import movil.Vehiculo

object Main extends App {
  
  val ruta = "C:\\Users\\sebas\\git\\MovilidadMedellin\\MovilidadMedellin\\src\\"
  val archivoParametros = "parametros.json"
  val archivoResultados = "resultados.json"
  
  var manejoJSON = new ManejoJSON()
  var parametrosSimulacion = manejoJSON.leerArchivo(ruta + archivoParametros)
  manejoJSON.escribirArchivoResultados(ruta + archivoResultados)
<<<<<<< HEAD
=======

>>>>>>> branch 'master' of https://github.com/DanielDi/MovilidadMedellin.git
  Simulacion.crearGrafico
  Vehiculo.crearVehiculos
}