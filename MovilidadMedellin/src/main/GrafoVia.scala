package main


import scalax.collection.edge.WLUnDiEdge
import scala.collection.mutable.ArrayBuffer
import punto.Via
import punto.Interseccion
import scalax.collection.mutable.Graph

object GrafoVia {
  
  def construir(vias :ArrayBuffer[Via]){
    var g= Graph[Interseccion,WLUnDiEdge]()
    vias.foreach(x => g.add(WLUnDiEdge.apply(x.origen,x.fin)(x.distancia, x.nom)))
  }
}