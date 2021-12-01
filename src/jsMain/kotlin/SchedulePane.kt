import androidx.compose.runtime.*
import eu.koders.data.Data
import eu.koders.data.Schedule
import kotlinx.browser.document
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.url.URLSearchParams
import kotlin.js.Date
import kotlin.math.abs


@Composable
fun SchedulePane() {

    val loc = remember { URLSearchParams(document.location!!.search).get("loc") }

    Div({
        style {
            flex(1)
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            padding(3.cssRem)
        }
    }) {
        H1({
            style {
                fontFamily("Picon-Extended", "sans-serif")
                fontWeight(300)
                fontSize(5.cssRem)
                color(Color.koders.kaumon)
            }
        }) {
            Text("kotlin")
            Span({
                style {
                    fontWeight(700)
                    color(Color.koders.korail)
                }
            }) {
                Text("KODERS")
            }
            Text("2021")
        }

        Div({
            style {
                flex(1)
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                justifyContent(JustifyContent.Center)
                paddingBottom(if (loc == null) 8.em else 16.em)
            }
        }) {
            if (loc == null || loc == "auditorium") {
                Div({
                }) {
                    nextTalk(Schedule.auditorium)
                }
            }

            if (loc == null || loc == "loft") {
                Div({
                }) {
                    nextTalk(Schedule.loft)
                }
            }
        }
    }
}

@Composable
fun nextTalk(schedule: Data<Schedule>) {
    Div({
        style {
            fontFamily("Picon", "sans-serif")
            fontSize(2.em)
            color(Color.koders.cute)
            paddingTop(2.em)
            marginRight(3.em)
        }
    }) {
        var slot by remember { mutableStateOf<Schedule.Slot?>(null) }

        LaunchedEffect(null) {
            val talks = schedule.data.sessions.map { (it.hour * 60 + it.minute) to it }

            while (true) {
                val now = Date().let { it.getHours() * 60 + it.getMinutes() }
                slot = talks.sortedBy { abs(it.first - now) } .first().second
                delay(10_000)
            }
        }

        val s = slot ?: return@Div

        H2({
            style {
                fontFamily("Picon-Extended", "sans-serif")
                fontSize(1.5.em)
                fontWeight(100)
                paddingBottom(0.5.em)
            }
        }) {
            Text("${schedule.id.replaceFirstChar { it.titlecase() }}, ${s.hour.toString().padStart(2, '0')}h${s.minute.toString().padStart(2, '0')}:")
        }

        Div({
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Row)
            }
        }) {
            Div({
                style {
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Column)
                }
            }) {
                s.talk.data.speakers.forEach { speaker ->
                    Img(src = "imgs/speakers/${speaker.id}.jpeg") {
                        style {
                            width(4.em)
                            borderRadius(0.25.em)
                            marginRight(0.5.em)
                            marginBottom(0.5.em)
                        }
                    }
                }
            }

            Div {
                H3({
                    style {
                        fontSize(1.em)
                        fontWeight(300)
                    }
                }) {
                    Text(s.talk.data.speakers.joinToString(" & ") { it.data.name })
                }

                H2({
                    style {
                        fontSize(1.5.em)
                        fontWeight(500)
                    }
                }) {
                    Text(s.talk.data.title)
                }
            }

        }
    }
}
