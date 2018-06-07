package org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.dialog

import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal
import org.apache.wicket.MarkupContainer
import org.apache.wicket.ajax.AjaxRequestTarget
import org.apache.wicket.markup.html.list.ListView
import org.kwicket.component.q
import org.kwicket.component.target
import org.kwicket.model.value
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer

open class PanelModal(id: String) : Modal<Any>(id) {

    companion object {
        private val contentId: String = "content"
    }

    private var content: MarkupContainer
    private lateinit var footer: MarkupContainer

    init {
        content = q(KWebMarkupContainer(id = contentId, outputMarkupId = true))
    }

    override fun createFooter(id: String): MarkupContainer {
        footer = super.createFooter(id)
        return footer
    }

    fun show(panel: (String) -> MarkupContainer, target: AjaxRequestTarget? = null, info: ModalInfo? = null) {
        val newContent = panel(contentId).setOutputMarkupId(true)
        content = content.replaceWith(newContent) as MarkupContainer
        (footer.get("buttons") as ListView<*>).model.value.clear()
        val modalInfo = info ?: if (newContent is HasModalInfo) newContent.modalInfo else null
        modalInfo?.let {
            with(it) {
                title?.let { if (escapeHeaderString != null) header(it, escapeHeaderString) else header(it) }
                size?.let { size(it) }
                footerButtons?.let { it.invoke(Modal.BUTTON_MARKUP_ID, form).forEach { addButton(it) } }
            }
        }
        val refreshTarget = target(target)
        refreshTarget.add(this)
        show(refreshTarget)
    }

}