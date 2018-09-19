package org.kwicket.application

import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication
import kotlin.reflect.KClass

fun <P: Page> WebApplication.mountPage(path: String, page: KClass<P>) = mountPage(path, page.java)