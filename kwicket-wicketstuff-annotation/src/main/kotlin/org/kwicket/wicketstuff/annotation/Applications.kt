package org.kwicket.wicketstuff.annotation

import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication
import org.wicketstuff.annotation.scan.AnnotatedMountScanner

fun <A : WebApplication> A.enableMountAnnotations(scanPackages: List<String>? = null,
                                                  scanClasses: List<Class<out Page>>? = null,
                                                  scanPatterns: List<String>? = null): A {
    val amc = AnnotatedMountScanner()
    scanPackages?.let { it.forEach { amc.scanPackage(it).mount(this) } }
    scanPatterns?.let { it.forEach { amc.scanPattern(it).mount(this) } }
    scanClasses?.let { it.forEach { amc.scanClass(it).mount(this) } }
    return this
}
