package main

import manejoJSON.ManejoJSON
import movil.Vehiculo

object Main extends App {
  
  val ruta = "C:\\Users\\nclsc\\workspace\\MovilidadMedellin\\src\\"
  val archivoParametros = "parametros.json"
  val archivoResultados = "resultados.json"
  
  var manejoJSON = new ManejoJSON()
  var parametrosSimulacion = manejoJSON.leerArchivo(ruta + archivoParametros)
  println(parametrosSimulacion)
  println(parametrosSimulacion.vehiculos)
  println(parametrosSimulacion.velocidad)
  manejoJSON.escribirArchivoResultados(ruta + archivoResultados)

  Simulacion.iniciarVias
  var simul = Simulacion
  Simulacion.arrayDeVehiculos.foreach(x => println(x.placa))

}