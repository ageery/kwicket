package org.kwicket.sample

import kotlinx.html.span
import org.apache.wicket.model.IModel
import org.kwicket.behavior.refreshEvery
import org.kwicket.kotlinx.html.RegionInfoPanel
import org.kwicket.kotlinx.html.div
import org.kwicket.kotlinx.html.panel
import org.kwicket.kotlinx.html.region
import org.kwicket.kotlinx.html.span
import org.kwicket.component.q
import org.kwicket.model.ldm
import org.kwicket.secs
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.basic.KLabel
import java.time.LocalDateTime
import java.util.*

private val timeId = "time"

private fun time(model: IModel<String>) = region().panel {
    div(builder = { KWebMarkupContainer(id = it) }) {
        span { +"The time in " }
        span(model = model)
        span { +" (" }
        span(builder = { KLabel(id = it, model = { TimeZone.getDefault().displayName }.ldm()) })
        span { +") is " }
        span(id = timeId)
    }
}

class TimePanel(id: String, model: IModel<String>) : RegionInfoPanel<String>(id = id, model = model, region = ::time) {

    init {
        q(
            KLabel(
                id = timeId,
                model = { LocalDateTime.now().toLocalTime() }.ldm(),
                outputMarkupId = true,
                behaviors = *arrayOf(refreshEvery(5.1.secs()))
            )
        )
    }

}