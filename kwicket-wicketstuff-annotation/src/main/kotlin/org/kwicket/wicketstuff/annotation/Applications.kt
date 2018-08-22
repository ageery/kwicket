package org.kwicket.wicketstuff.annotation

import org.apache.wicket.Page
import org.apache.wicket.protocol.http.WebApplication
import org.wicketstuff.annotation.scan.AnnotatedMountScanner

/**
 * Extension method to add mount annotation scanning to a [WebApplication].
 *
 * @receiver the [WebApplication] to add the annotation scanning functionality to
 * @param A type of [WebApplication] the mount scanning is being added to
 * @param scanPackages list of packages to scan for annotations
 * @param scanClasses list of classes to scan for annotations
 * @param scanPatterns list of Spring search paths to scan for annotations
 * @return the [WebApplication] the mount scanning was added to
 */
fun <A : WebApplication> A.enableMountAnnotations(
    scanPackages: List<String>? = null,
    scanClasses: List<Class<out Page>>? = null,
    scanPatterns: List<String>? = null
): A {
    val amc = AnnotatedMountScanner()
    scanPackages?.let { it.forEach { amc.scanPackage(it).mount(this) } }
    scanPatterns?.let { it.forEach { amc.scanPattern(it).mount(this) } }
    scanClasses?.let { it.forEach { amc.scanClass(it).mount(this) } }
    return this
}
