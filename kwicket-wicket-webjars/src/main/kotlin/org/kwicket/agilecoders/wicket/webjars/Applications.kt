package org.kwicket.agilecoders.wicket.webjars

import de.agilecoders.wicket.webjars.WicketWebjars
import de.agilecoders.wicket.webjars.collectors.AssetPathCollector
import de.agilecoders.wicket.webjars.settings.ResourceStreamProvider
import de.agilecoders.wicket.webjars.settings.WebjarsSettings
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.util.time.Duration
import java.util.regex.Pattern

/**
 * Extension method for enabling webjars in a [WebApplication].
 *
 * @receiver the [WebApplication] to add the webjars functionality to
 * @param A type of [WebApplication] the webjar functionality is being added to
 * @param resourceStreamProvider [ResourceStreamProvider] to use to load resources
 * @param assetPathCollectors [AssetPathCollector instances to use to find resources
 * @param webjarsPackage webjars package path (e.g. "META-INF.resources.webjars")
 * @param webjarsPath the path where all webjars are stored (e.g. "META-INF/resources/webjars")
 * @param resourcePattern the pattern to filter accepted webjars resources
 * @param recentVersionPlaceHolder placeholder for recent version (e.g. current)
 * @param readFromCacheTimeout timeout which is used when reading from cache (Future.get(timeout))
 * @param useCdnResources whether the resources for the webjars should be loaded from a CDN network
 * @param cdnUrl base URL of the webjars CDN
 * @return the [WebApplication] the webjar functionality was added to
 */
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