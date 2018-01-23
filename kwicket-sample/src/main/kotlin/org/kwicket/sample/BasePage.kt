package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag
import de.agilecoders.wicket.core.markup.html.bootstrap.html.IeEdgeMetaTag
import de.agilecoders.wicket.core.markup.html.bootstrap.html.MobileViewportMetaTag
import de.agilecoders.wicket.core.markup.html.bootstrap.image.Icon
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeCssReference
import de.agilecoders.wicket.extensions.markup.html.bootstrap.icon.FontAwesomeIconType
import org.apache.wicket.Component
import org.apache.wicket.ajax.IAjaxIndicatorAware
import org.apache.wicket.event.IEvent
import org.apache.wicket.markup.head.CssReferenceHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.model.ResourceModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.CssResourceReference
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.common.KNotificationPanel
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.navbar.KNavbar
import org.kwicket.component.q
import org.kwicket.component.refresh

abstract class BasePage(pageParameters: PageParameters? = null) : WebPage(pageParameters), IAjaxIndicatorAware {

    companion object {
        private val ajaxIndicatorId = "ajax"
    }

    private val feedback: Component

    init {
        initModel()
        q(HtmlTag("html"))
        q(Label("title", ResourceModel("page.title")))
        q(MobileViewportMetaTag("viewport"))
        q(IeEdgeMetaTag("ie-edge"))
        q(
            KNavbar(
                id = "navbar",
                renderBodyOnly = false,
                brandName = ResourceModel("app.name"),
                inverted = true,
                position = Navbar.Position.TOP
            )
        )
        q(Icon("ajax-indicator", FontAwesomeIconType.spinner).setMarkupId(ajaxIndicatorId))
        feedback = q(KNotificationPanel(id = "feedback", outputMarkupPlaceholderTag = true))
    }

    override fun renderHead(response: IHeaderResponse) {
        super.renderHead(response)
        response.render(CssReferenceHeaderItem.forReference(FontAwesomeCssReference.instance()))
        response.render(CssReferenceHeaderItem.forReference(CssResourceReference(BasePage::class.java, "theme.css")))
    }

    override fun getAjaxIndicatorMarkupId() = ajaxIndicatorId

    override fun onEvent(event: IEvent<*>) {
        val payload = event.payload
        when (payload) {
            is HasFeedbackEvent -> feedback.refresh()
        }
    }

}
