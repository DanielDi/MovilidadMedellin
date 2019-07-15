package punto

class Sentido(private var unavia: Boolean, private var doblevia: Boolean){

}

object Sentido {
  
  def dobleVia() = new Sentido(false,true)

  
  def unaVia() = new Sentido(true,false)
  
}