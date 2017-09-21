package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType
import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType
import org.apache.wicket.Component
import org.apache.wicket.event.Broadcast
import org.apache.wicket.event.IEvent
import org.apache.wicket.model.IModel
import org.apache.wicket.spring.injection.annot.SpringBean
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.KBootstrapAjaxLink
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.button.KBootstrapAjaxButton
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.KBootstrapForm
import org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.dialog.PanelModal
import org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.table.KTableBehavior
import org.kwicket.component.q
import org.kwicket.component.refresh
import org.kwicket.model.model
import org.kwicket.model.value
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table.KAjaxFallbackDefaultDataTable
import org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table.KLambdaColumn
import org.kwicket.wicket.extensions.markup.html.repeater.data.table.LinkColumn
import org.kwicket.wicket.extensions.markup.html.repeater.util.KSortableDataProvider
import org.wicketstuff.annotation.mount.MountPath

@MountPath("/")
class ManageCustomersPage : BasePage() {

    @SpringBean
    private lateinit var customerService: CustomerService

    private val table: Component
    private val modal: PanelModal = q(PanelModal(id = "modal"))

    init {
        val searchModel: IModel<String?> = null.model()
        val form = q(KBootstrapForm(id = "form",
                model = searchModel,
                type = FormType.Inline,
                outputMarkupId = true))
        table = q(KAjaxFallbackDefaultDataTable<Customer, CustomerSort>(id = "table",
                outputMarkupId = true,
                columns = listOf(
                        KLambdaColumn(displayModel = "First Name".model(),
                                sort = CustomerSort.FirstName,
                                function = { it.firstName }),
                        KLambdaColumn(displayModel = "Last Name".model(),
                                sort = CustomerSort.LastName,
                                function = { it.lastName }),
                        LinkColumn(displayModel = "Action".model(),
                                links = { id, model ->
                                    listOf(
                                            KBootstrapAjaxLink(id = id, model = model,
                                                    icon = GlyphIconType.remove, onClick = { target, link ->
                                                link.send(link, Broadcast.BUBBLE, DeleteEvent(target = target, content = model.value))
                                            }),
                                            KBootstrapAjaxLink(id = id, model = model,
                                                    icon = GlyphIconType.edit, onClick = { target, _ ->
                                                modal.show(target = target,
                                                        panel = { EditCustomerPanel(it, model.value.toEdit.model()) })
                                            }))
                                })
                ),
                dataProvider = KSortableDataProvider(count = { customerService.count(term = searchModel.value).toLong() },
                        items = { first, count, sort, asc ->
                            customerService.find(term = searchModel.value, sort = sort, asc = asc,
                                    first = first.toInt(), count = count.toInt())
                        },
                        modeler = { it.model() }),
                rowsPerPage = 10,
                behaviors = KTableBehavior(hover = true, bordered = true, condensed = true, striped = true)))
        q(KTextField(id = "search",
                model = searchModel,
                behaviors = InputBehavior()))
        q(KBootstrapAjaxButton(id = "searchButton",
                icon = GlyphIconType.search,
                model = "Search".model(),
                onSubmit = { _, _ -> table.refresh() }))
        q(KBootstrapAjaxButton(id = "addButton",
                icon = GlyphIconType.plus,
                model = "Add".model(),
                onSubmit = { target, _ ->
                    modal.show(target = target,
                            panel = { EditCustomerPanel(it, EditCustomer().model()) })
                }))
        q(KBootstrapAjaxButton(id = "resetButton",
                defaultFormProcessing = false,
                icon = GlyphIconType.refresh,
                model = "Reset".model(),
                onSubmit = { _, _ ->
                    searchModel.value = null
                    form.clearInput()
                    form.refresh()
                    table.refresh()
                }))
    }

    override fun onEvent(event: IEvent<*>) {
        val payload = event.payload
        when (payload) {
            is CancelEvent -> modal.close(payload.target)
            is SaveEvent<*> ->
                when (payload.content) {
                    is EditCustomer -> {
                        modal.close(payload.target)
                        if (payload.content.id == null) customerService.insert(payload.content.fromEdit)
                        else customerService.update(payload.content.fromEdit)
                        table.refresh()
                    }
                }
            is DeleteEvent<*> ->
                when (payload.content) {
                    is Customer -> {
                        customerService.delete(payload.content)
                        table.refresh()
                    }
                }
        }
    }

}