import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

class MainViewModel : ViewModel() {

    private val _equationText = MutableLiveData<String>("0")
    val equationText: LiveData<String> = _equationText

    private val _resultText = MutableLiveData<String>()

    fun addDigit(btn: String) {
        val currentEquation = _equationText.value ?: return

        when (btn) {
            "C" -> {
                // If equation is not empty, remove the last character
                _equationText.value = if (currentEquation.length > 1) {
                    currentEquation.dropLast(1)
                } else {
                    // If equation is empty after removing, reset to "0"
                    "0"
                }
            }

            "=" -> {
                // Assign the calculated result to the equation
                _equationText.value = _resultText.value ?: "0"
            }

            else -> {
                updateEquation(btn, currentEquation)
            }
        }

        try {
            _resultText.value = calculateResult(_equationText.value!!)
        } catch (_: Exception) {
            // Handle invalid expressions safely
        }
    }

    private fun updateEquation(btn: String, currentEquation: String) {
        if (btn in listOf("+", "-", "*", "/", "%") && currentEquation.last() in listOf('+', '-', '*', '/', '%')) {
            return
        } else {
            _equationText.value = if (currentEquation == "0") btn else currentEquation + btn
        }
    }

    private fun calculateResult(equation: String): String {
        val context: Context = Context.enter()
        context.optimizationLevel = -1
        val scriptable: Scriptable = context.initStandardObjects()
        var result = context.evaluateString(scriptable, equation, "Javascript", 1, null).toString()
        return if (result.endsWith(".0")) result.replace(".0", "") else result
    }
}
