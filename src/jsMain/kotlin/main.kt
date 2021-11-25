import androidx.compose.runtime.*
import eu.koders.data.Sponsor
import eu.koders.data.Sponsor.Companion.Acinq
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.renderComposableInBody



fun main() {
    renderComposableInBody {
        Div({
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Row)
                width(100.vw)
                height(100.vh)
                backgroundColor(Color.koders.cute)
            }
        }) {
            Div({
                style {
                    flex(5)
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Column)
                    backgroundColor(Color.koders.kyzantium)
                    property("clip-path", "polygon(0% 0%, 100% 0%, 90% 100%, 0% 100%)")
                }
            }) {
                SchedulePane()
            }
            Div({
                style {
                    flex(3)
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Column)
                }
            }) {
                SponsorPane()
            }
        }
    }
}

