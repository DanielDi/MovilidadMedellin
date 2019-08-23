package punto

trait Recta {
  type I = Interseccion
  
  var origen: I
  var fin: I

  var angulo =() => {
      var Y = (fin.yI - origen.yI)
      var X = (fin.xI - origen.xI)
      var pendiente = Y/X
      if(X == 0) math.Pi/2
      else math.atan(pendiente)
  }
}