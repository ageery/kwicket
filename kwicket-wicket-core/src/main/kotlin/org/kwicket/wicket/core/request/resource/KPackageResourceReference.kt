package org.kwicket.wicket.core.request.resource

import org.apache.wicket.request.resource.PackageResourceReference
import kotlin.reflect.KClass

fun KClass<*>.resRef(name: String) = KPackageResourceReference(this, name)

class KPackageResourceReference(scope: KClass<*>, name: String) : PackageResourceReference(scope.java, name)