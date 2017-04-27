package org.kwicket.behavior

class VisibleWhen(val isVisible: () -> Boolean) : OnConfigureBehavior(handler = { c -> c.isVisible = isVisible() })
