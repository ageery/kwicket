package org.kwicket.behavior

class EnabledWhen(val isEnabled: () -> Boolean) : OnConfigureBehavior(handler = { c -> c.isEnabled = isEnabled() })