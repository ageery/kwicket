package org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.common

import de.agilecoders.wicket.core.markup.html.bootstrap.common.INotificationMessage
import org.apache.wicket.model.IModel
import org.apache.wicket.util.time.Duration
import org.kwicket.model.model

open class KNotificationMessage(private val message: IModel<String>,
                                private val header: IModel<String> = "".model(),
                                private val hideAfter: Duration? = null,
                                private val escapeModelStrings: Boolean = true,
                                private val inlineHeader: Boolean = true) : INotificationMessage {

    override fun inlineHeader(): Boolean = inlineHeader
    override fun header(): IModel<String> = header
    override fun hideAfter(): Duration? = hideAfter
    override fun escapeModelStrings(): Boolean = escapeModelStrings
    override fun message(): IModel<String> = message

}