import androidx.compose.runtime.*
import eu.koders.data.Sponsor
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img


@Composable
fun SponsorPane() {
    Div({
        style {
            textAlign("center")
            paddingTop(12.vh)
            paddingBottom(6.vh)
        }
    }) {
        Img(src = "imgs/sponsors/${Sponsor.Acinq.id}.png") {
            style {
                height(10.vh)
            }
        }
    }

    SponsorLine(Sponsor.Type.GOLD, 8.vh, 10000)
    SponsorLine(Sponsor.Type.SILVER, 6.vh, 7000)
}

@Composable
private fun SponsorLine(type: Sponsor.Type, height: CSSNumeric, changeDelay: Long) {
    val sponsors = remember { Sponsor.all.filter { it.data.type == type } }

    var displays by remember { mutableStateOf(sponsors.take(2).map { Pair(1.0, it.id) }) }

    LaunchedEffect(null) {
        fun setAlpha(index: Int, alpha: Double) {
            val m = displays.toMutableList()
            m[index] = alpha to m[index].second
            displays = m.toList()
        }

        fun setSponsor(index: Int, sponsor: String) {
            val m = displays.toMutableList()
            m[index] = 1.0 to sponsor
            displays = m.toList()
        }

        var nextSponsor = 2 % sponsors.size
        var nextDisplay = 0
        while (true) {
            delay(changeDelay)
            setAlpha(nextDisplay, 0.0)
            delay(550)
            setSponsor(nextDisplay, sponsors[nextSponsor].id)
            delay(550)
            nextSponsor = (nextSponsor + 1) % sponsors.size
            nextDisplay = (nextDisplay + 1) % displays.size
        }
    }

    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
            flexWrap(FlexWrap.Wrap)
            justifyContent(JustifyContent.Center)
            paddingTop(12.vh)
        }
    }) {
        repeat(2) {
            val (alpha, sponsor) = displays[it]
            Div({
                style {
                    width(50.percent)
                    paddingBottom(6.vh)
                    textAlign("center")
                    opacity(alpha)
                    property("transition", "opacity 500ms")
                }
            }) {
                key("img-$it") {
                    Img(src = "imgs/sponsors/$sponsor.png") {
                        style {
                            height(height)
                        }
                    }
                }
            }
        }
    }
}
