package movil

import punto.Velocidad

trait MovimientoUniforme {
  
  def formaAumentoPosicion(velocidad: Velocidad, dt: Int) = {
    var x = velocidad.magnitud*Math.cos(velocidad.direccion.grado)*dt
    var y = velocidad.magnitud*Math.sin(velocidad.direccion.grado)*dt
    (x,y)
  }
}