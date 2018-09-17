package org.kwicket.wicket.core

import org.apache.wicket.SharedResources
import org.apache.wicket.request.resource.IResource

operator fun SharedResources.set(name: String, resource: IResource) = this.add(name, resource)