package org.kwicket.sample

import kotlinx.html.span
import org.apache.wicket.model.IModel
import org.kwicket.builder.RegionInfoPanel
import org.kwicket.builder.div
import org.kwicket.builder.panel
import org.kwicket.builder.region
import org.kwicket.builder.span
import org.kwicket.component.q
import org.kwicket.model.ldm
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.basic.KLabel
import java.time.LocalDateTime
import java.util.*

private val timeId = "time"

private fun time(model: IModel<String>) = region().panel {
    div(builder = { KWebMarkupContainer(id = it) }) {
        span { +"The time in " }
        span(builder = { KLabel(id = it, model = model) })
        span { +" (" }
        span(builder = { KLabel(id = it, model = { TimeZone.getDefault().displayName }.ldm()) })
        span { +") is " }
        span(id = timeId)
    }
}

// FIXME: add a behavior to update the time label periodically
class TimePanel(id: String, model: IModel<String>) : RegionInfoPanel<String>(id = id, model = model, region = ::time) {

    init {
        q(KLabel(id = timeId, model = { LocalDateTime.now().toLocalTime() }.ldm()))
    }

}