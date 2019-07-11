package punto

trait Recta {
  type I = Interseccion
  
  var origen: I
  var fin: I
}