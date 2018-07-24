package org.kwicket.wicket.injection

import org.apache.wicket.Component
import org.apache.wicket.injection.Injector

fun Component.inject() = Injector.get().inject(this)