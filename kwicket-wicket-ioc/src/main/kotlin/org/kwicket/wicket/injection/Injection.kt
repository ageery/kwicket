package org.kwicket.wicket.injection

import org.apache.wicket.injection.Injector

/**
 * Populates properties in the @receiver with values via Wicket injection.
 *
 * @receiver any type of object
 */
fun Any.inject() = Injector.get().inject(this)