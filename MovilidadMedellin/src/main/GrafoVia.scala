package main


import scalax.collection.edge.WLDiEdge
import scala.collection.mutable.ArrayBuffer
import punto.Via
import punto.Interseccion
import scalax.collection.mutable.Graph
import scala.collection.Traversable
import scalax.collection.GraphTraversal

object GrafoVia {
  val g= Graph[Interseccion,WLDiEdge]()
  
  def construir(vias :ArrayBuffer[Via]){
    vias.foreach(x => if(x.sentido.doblevia) {
                      g.add(WLDiEdge(x.origen.copy(),x.fin.copy())(x.distancia, x.nom))
                      g.add(WLDiEdge(x.fin.copy(),x.origen.copy())(x.distancia, x.nom))
                      }else{
                         g.add(WLDiEdge(x.origen.copy(),x.fin.copy())(x.distancia, x.nom))
                      })
  }
 
  
  def menorCamino(n1: Interseccion, n2: Interseccion)={
    println("n1: "+n1+" n2: "+ n2)
    println("Grafo: "+ g.nodes.foreach(x => print(x.toOuter+": X-"+x.toOuter.xI+", Y-"+x.toOuter.yI+" ")))
    val nodo1= g.get(n1)
    val nodo2= g.get(n2)
    val path = nodo1.shortestPathTo(nodo2).get
    path.nodes
  }
}