package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.bootstrap.behavior.BootstrapBaseBehavior
import de.agilecoders.wicket.core.markup.html.bootstrap.html.HtmlTag
import de.agilecoders.wicket.core.markup.html.bootstrap.html.MetaTag
import de.agilecoders.wicket.core.markup.html.bootstrap.html.MobileViewportMetaTag
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents
import org.apache.wicket.markup.head.CssHeaderItem
import org.apache.wicket.markup.head.IHeaderResponse
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.CssResourceReference
import org.kwicket.model.model
import java.util.Locale

abstract class BasePage(parameters: PageParameters) : WebPage(parameters) {

    constructor() : this(PageParameters())

    override fun onInitialize() {
        super.onInitialize()
        add(HtmlTag("html", Locale.ENGLISH))
        add(BootstrapBaseBehavior())
        add(MobileViewportMetaTag("viewport"))
        add(MetaTag("description", "description".model(), "My description".model()))
        add(MetaTag("author", "author".model(), "My author".model()))
        add(Label("title", "Sample".model()))
        add(Navbar("navbar").apply {
            position = Navbar.Position.STATIC_TOP
            setBrandName("KWicket Sample".model())
            addComponents(NavbarComponents.transform(Navbar.ComponentPosition.RIGHT,
                    NavbarButton<AjaxPage, AjaxPage>(AjaxPage::class.java, "Ajax Example".model()),
                    NavbarButton<NonAjaxPage, NonAjaxPage>(NonAjaxPage::class.java, "Non-ajax Example".model())))
        })
    }

    override fun renderHead(response: IHeaderResponse) {
        super.renderHead(response)
        response.render(CssHeaderItem.forReference(CssResourceReference(BasePage::class.java, "theme.css")))
    }

}
