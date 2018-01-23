package org.kwicket.agilecoders.wicket.webjars

import de.agilecoders.wicket.webjars.WicketWebjars
import de.agilecoders.wicket.webjars.collectors.AssetPathCollector
import de.agilecoders.wicket.webjars.settings.ResourceStreamProvider
import de.agilecoders.wicket.webjars.settings.WebjarsSettings
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.util.time.Duration
import java.util.regex.Pattern

fun <A : WebApplication> A.enableWebjars(
    resourceStreamProvider: ResourceStreamProvider? = null,
    assetPathCollectors: Array<AssetPathCollector>? = null,
    webjarsPackage: String? = null,
    webjarsPath: String? = null,
    resourcePattern: Pattern? = null,
    recentVersionPlaceHolder: String? = null,
    readFromCacheTimeout: Duration? = null,
    useCdnResources: Boolean? = null,
    cdnUrl: String? = null
): A {
    val settings = WebjarsSettings()

    resourceStreamProvider?.let { settings.resourceStreamProvider(it) }
    assetPathCollectors?.let { settings.assetPathCollectors(*it) }
    webjarsPackage?.let { settings.webjarsPackage(it) }
    webjarsPath?.let { settings.webjarsPath(it) }
    resourcePattern?.let { settings.resourcePattern(it) }
    webjarsPath?.let { settings.webjarsPath(it) }
    recentVersionPlaceHolder?.let { settings.recentVersionPlaceHolder(it) }
    readFromCacheTimeout?.let { settings.readFromCacheTimeout(it) }
    useCdnResources?.let { settings.useCdnResources(it) }
    cdnUrl?.let { settings.cdnUrl(it) }

    WicketWebjars.install(this, settings)
    return this
}