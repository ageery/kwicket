package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.tabs

import de.agilecoders.wicket.core.markup.html.bootstrap.tabs.AjaxBootstrapTabbedPanel
import org.apache.wicket.extensions.markup.html.tabs.ITab
import org.apache.wicket.model.IModel

open class KAjaxBootstrapTabbedPanel<T: ITab>(id: String,
                                              tabs: List<T>,
                                              model: IModel<Int>? = null)
    : AjaxBootstrapTabbedPanel<T>(id, tabs, model)
