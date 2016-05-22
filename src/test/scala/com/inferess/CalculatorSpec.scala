import org.scalatest._
import com.inferess.Calculator;

class CalculatorSpec extends WordSpec {

  "A Calculator" should {

    "return 0 when freq of word is 0" in {
      var cal = new Calculator()
      var result = cal.tfIdf(0,100,5,4)      
      assert(result == 0)      
    }
    
  }
}