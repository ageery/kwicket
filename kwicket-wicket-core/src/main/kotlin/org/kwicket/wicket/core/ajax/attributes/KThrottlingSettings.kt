package org.kwicket.wicket.core.ajax.attributes

import org.apache.wicket.ajax.attributes.ThrottlingSettings
import org.apache.wicket.util.time.Duration

/**
 * [ThrottlingSettings] with named and default constructor arguments.
 */
open class KThrottlingSettings(id: String? = null,
                               delay: Duration? = null,
                               postponeTimerOnUpdate: Boolean = false)
    : ThrottlingSettings(id, delay, postponeTimerOnUpdate)