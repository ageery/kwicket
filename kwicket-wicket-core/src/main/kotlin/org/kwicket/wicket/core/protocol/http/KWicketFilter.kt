package org.kwicket.wicket.core.protocol.http

import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.http.WicketFilter

/**
 * [WicketFilter] with named parameters.
 *
 * @param webApp Wicket [WebApplication] connected to the filter
 * @param filterPath URL path to the application
 */
class KWicketFilter(webApp: WebApplication, filterPath: String = "/") : WicketFilter(webApp) {

    init {
        this.filterPath = filterPath
    }

}