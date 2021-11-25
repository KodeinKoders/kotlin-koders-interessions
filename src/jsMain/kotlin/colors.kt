import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import kotlin.reflect.KProperty

object KodersColors {
    private val byName = HashMap<String, CSSColorValue>()
    @PublishedApi internal val byValue = HashMap<CSSColorValue, String>()

    private operator fun CSSColorValue.getValue(thisRef: Any?, property: KProperty<*>): CSSColorValue {
        byName[property.name] = this
        byValue[this] = property.name
        return this
    }

    operator fun get(name: String) = byName.getValue(name)

    inline fun nameOf(color: KodersColors.() -> CSSColorValue) = byValue.getValue(color.invoke(this))

    /*
                    Dark
         kyzantium       krouille
         kinzolin        kuivre
      purple                orange
         kamethiste      korail
         klycine         kaumon
                    Cute
     */

    // Primarykyzantium
    val orange by Color("#E8441F")
    val purple by Color("#921F81")

    // Secondary
    val cute by Color("#F7E1DE")
    val dark by Color("#240821")

    val darker by Color("#120411")

    // Tertiary
    val kyzantium by Color("#480F40")
    val kinzolin by Color("#6D1761")
    val kamethiste by Color("#B35C9D")
    val klycine by Color("#D39AB8")
    val kaumon by Color("#F0A698")
    val korail by Color("#EC755B")
    val kuivre by Color("#A6301F")
    val krouille by Color("#651B20")
}

val Color.koders get() = KodersColors
