package main

import movil.Vehiculo

object Main extends App {
  Simulacion.iniciarVias
  var simul = Simulacion
  Simulacion.arrayDeVehiculos.foreach(x => println(x.placa))
}