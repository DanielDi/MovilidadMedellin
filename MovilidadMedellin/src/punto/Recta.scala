package punto

trait Recta {
  type I = Interseccion
  
  val origen: I
  val fin: I
}