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

  Simulacion.crearGrafico
  Vehiculo.crearVehiculos
  Simulacion.crearGrafo
}