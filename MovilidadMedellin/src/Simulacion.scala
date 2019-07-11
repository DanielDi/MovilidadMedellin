import scala.collection.mutable.ArrayBuffer

object Simulacion extends Runnable {
  var t = 0
  var dt = 0
  var tRefresh = 0
  var vehiculosMin = 0
  var vehiculosMax = 0
  var velMin = 0
  var velMax = 0
  
  var arrayDeVehiculos = ArrayBuffer[Vehiculo]()
  var arrayDeVias = ArrayBuffer[Via]()
  
  def run() {
    while(true) {
//      listaDeVehiculos.foreach(_.mover(dt))
//      t += dt
//      Grafico.graficarVehiculos(listaDeVehiculos)
//      
//      Thread.sleep(tRefresh)
    }
  }
  
}