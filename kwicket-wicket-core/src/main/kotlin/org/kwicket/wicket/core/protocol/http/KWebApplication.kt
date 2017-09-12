package org.kwicket.wicket.core.protocol.http

import org.apache.wicket.RuntimeConfigurationType
import org.apache.wicket.protocol.http.WebApplication

abstract class KWebApplication(configurationType: RuntimeConfigurationType? = null) : WebApplication() {

    init {
        this.configurationType = configurationType
    }

}