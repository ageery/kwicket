package org.kwicket.wicket.core.protocol.http

import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.protocol.http.WicketFilter

class KWicketFilter(webApp: WebApplication, filterPath: String) : WicketFilter(webApp) {

    init {
        this.filterPath = filterPath
    }

}