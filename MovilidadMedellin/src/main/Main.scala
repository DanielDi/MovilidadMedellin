package main

import movil.Vehiculo

object Main extends App {
  var simul = Simulacion
  Simulacion.arrayDeVehiculos.foreach(x => println(x.placa))
}