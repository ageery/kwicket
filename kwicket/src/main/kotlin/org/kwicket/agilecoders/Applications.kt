package org.kwicket.agilecoders

import de.agilecoders.wicket.core.Bootstrap
import de.agilecoders.wicket.core.settings.ActiveThemeProvider
import de.agilecoders.wicket.core.settings.BootstrapSettings
import de.agilecoders.wicket.core.settings.ThemeProvider
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.request.resource.ResourceReference

fun <A : WebApplication> A.enableBootstrap(version: String? = null,
                                           modernizrVersion: String? = null,
                                           deferJavascript: Boolean? = null,
                                           autoAppendResources: Boolean? = null,
                                           cssResourceReference: ResourceReference? = null,
                                           jsResourceReference: ResourceReference? = null,
                                           modernizrResourceReference: ResourceReference? = null,
                                           jsResourceFilterName: String? = null,
                                           updateSecurityManager: Boolean? = null,
                                           activeThemeProvider: ActiveThemeProvider? = null,
                                           themeProvider: ThemeProvider? = null,
                                           useCdnResources: Boolean? = null): A {
    val settings = BootstrapSettings()
    version?.let { settings.version = it }
    modernizrVersion?.let { settings.modernizrVersion = it }
    deferJavascript?.let { settings.setDeferJavascript(it) }
    autoAppendResources?.let { settings.setAutoAppendResources(it) }
    cssResourceReference?.let { settings.cssResourceReference = it }
    jsResourceReference?.let { settings.jsResourceReference = it }
    modernizrResourceReference?.let { settings.modernizrResourceReference = it }
    jsResourceFilterName?.let { settings.jsResourceFilterName = it }
    activeThemeProvider?.let { settings.activeThemeProvider = it }
    themeProvider?.let { settings.themeProvider = it }
    useCdnResources?.let { settings.useCdnResources(it) }
    Bootstrap.install(this, settings)
    return this
}

