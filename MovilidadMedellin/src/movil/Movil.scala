package movil

import punto.Velocidad
import punto.Interseccion
import main.Simulacion
import scala.collection.mutable.Queue

abstract class Movil {
   
  var posInicial: Interseccion
  var posFinal: Interseccion
  var vel: Velocidad
  var path: Queue[Interseccion]
  def aumentarPosc(dt: Int)  
  
  def direccionAngulo(posO: Interseccion, camino: Queue[Interseccion]):Double = {
//    println("Puntos cambio de direccion: "+posO+" , "+ camino.front)
    var cambio: Double = 0
    var a = Simulacion.arrayDeVias.filter(via =>
      ((via.interO == posO) && (via.interF == camino.front)))
    if (a.size != 0) cambio = a(0).anguloVia
    else if (a.size == 0){
      a = Simulacion.arrayDeVias.filter(via =>
      ((via.interF == posO) && (via.interO == camino.front)))
      //sebas
//      cambio = math.Pi
//      println("Angulo: " + a(0).anguloVia +cambio)
      cambio = a(0).anguloVia2
    }
//    if(a(0).X > 0 && a(0).Y >= 0) cambio = math.Pi
//    else if(a(0).X < 0 && a(0).Y >= 0) cambio = math.Pi
//    if(a(0).Y > 0 && a(0).X >= 0) cambio = math.Pi
//    else if(a(0).Y < 0 && a(0).X >= 0) cambio = math.Pi
//    println("Angulo: " + a(0).anguloVia +cambio)
    
//    if(cambio==0 && a(0).anguloVia == 0){
//      return this.vel.direccion.grado
//    }else{
//      return a(0).anguloVia +cambio
//    }
    return cambio
  }
    
}