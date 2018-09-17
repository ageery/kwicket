package org.kwicket.wicket.core.request.resource

import org.apache.wicket.request.resource.PackageResourceReference
import org.apache.wicket.request.resource.ResourceReference
import kotlin.reflect.KClass

/**
 * Creates a resource reference from a [KClass] and a name.
 *
 * @receiver [KClass] that is the anchor for the resource
 * @param name relative path to the resource
 * @return [ResourceReference] constructed from the @receiver and the [name]
 */
fun KClass<*>.resRef(name: String): PackageResourceReference = KPackageResourceReference(this, name)

/**
 * [PackageResourceReference] with named parameters.
 *
 * @param scope the [KClass] that anchors the reference
 * @param name the path to the resource, relative to the scope
 */
class KPackageResourceReference(scope: KClass<*>, name: String) : PackageResourceReference(scope.java, name)