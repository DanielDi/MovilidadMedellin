package main

import movil.Vehiculo

object Main extends App {
  println("hola mi perrito x3")
  var simul = Simulacion
  Simulacion.arrayDeVehiculos.foreach(x => println(x.placa))
}