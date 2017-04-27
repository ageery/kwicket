package org.kwicket.sample

import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormType
import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior
import de.agilecoders.wicket.core.markup.html.bootstrap.image.GlyphIconType
import de.agilecoders.wicket.core.markup.html.bootstrap.table.TableBehavior
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.event.Broadcast
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder
import org.apache.wicket.model.IModel
import org.apache.wicket.spring.injection.annot.SpringBean
import org.apache.wicket.util.time.Duration
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.KBootstrapAjaxLink
import org.kwicket.agilecoders.wicket.core.ajax.markup.html.bootstrap.form.KBootstrapForm
import org.kwicket.behavior.EnabledWhen
import org.kwicket.component.KPropertyColumn
import org.kwicket.component.onEvent
import org.kwicket.component.q
import org.kwicket.model.IdentityModel
import org.kwicket.model.PropChain
import org.kwicket.model.model
import org.kwicket.model.value
import org.kwicket.wicket.core.ajax.attributes.KThrottlingSettings
import org.kwicket.wicket.core.ajax.form.KAjaxFormComponentUpdatingBehavior
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.extensions.ajax.markup.html.repeater.data.table.KAjaxFallbackDefaultDataTable
import org.kwicket.wicket.extensions.markup.html.repeater.util.KSortableDataProvider
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.wicketstuff.annotation.mount.MountPath
import org.wicketstuff.event.annotation.AbstractPayloadTypedEvent

class CustomerDataProvider(val repo: CustomerRepository, val lastNameFilter: IModel<String?>, var pageSize: Int, sort: CustomerSort, order: SortOrder) : KSortableDataProvider<Customer, CustomerSort>(sort, order) {

    override fun iterator(first: Long, count: Long): Iterator<Customer> {
        val pageable = PageRequest((first / pageSize).toInt(), pageSize,
                if (sort.isAscending) Sort.Direction.ASC else Sort.Direction.DESC,
                *sort.property.props)
        val list = if (lastNameFilter.value == null)
            repo.findAll(pageable)
        else
            repo.findByLastNameStartsWithIgnoreCase(lastNameFilter.value!!, pageable)
        return list.iterator()
    }

    override fun size(): Long = if (lastNameFilter.value == null) repo.count() else repo.countByLastNameStartsWithIgnoreCase(lastNameFilter.value!!)
    override fun model(customer: Customer): IModel<Customer> = IdentityModel(customer, idToValue = { id -> repo.findOne(id) }, valueToId = { it.id })
}

val customerCheck: (AbstractPayloadTypedEvent<*>) -> Boolean = { it.types == listOf(Customer::class.java) }

@MountPath("customers")
class ManageCustomersPage : BasePage() {

    @SpringBean lateinit var repo: CustomerRepository

    val newCustomerModel = null.model<Customer?>()
    val notEditing = { newCustomerModel.value == null }

    init {
        val pageSize = 10
        val searchModel = null.model<String?>()

        val searchForm = q(KBootstrapForm(id = "searchForm",
                model = searchModel,
                type = FormType.Inline,
                behaviors = EnabledWhen(notEditing))
                .onEvent({ c, e: CancelEvent -> e.target.add(c) }))
        val table = q(KAjaxFallbackDefaultDataTable("table",
                columns = listOf(
                        KPropertyColumn<Customer, CustomerSort, Int?>(label = "Id".model(), props = PropChain { +Customer::id }, sort = CustomerSort.ID),
                        KPropertyColumn<Customer, CustomerSort, String?>(label = "First Name".model(), props = PropChain { +Customer::firstName }, sort = CustomerSort.FIRST_NAME),
                        KPropertyColumn<Customer, CustomerSort, String?>(label = "Last Name".model(), props = PropChain { +Customer::lastName }, sort = CustomerSort.LAST_NAME),
                        LinkColumn<Customer, CustomerSort>(label = "Actions".model(), generator = { id, model ->
                            listOf(
                                    KBootstrapAjaxLink(id = id, model = model, label = "".model(), icon = GlyphIconType.edit,
                                            onClick = { target, customer -> send(this, Broadcast.BUBBLE, EditEvent(target, customer!!)) },
                                            behaviors = EnabledWhen(notEditing)),
                                    KBootstrapAjaxLink(id = id, model = model, label = "".model(), icon = GlyphIconType.trash,
                                            onClick = { target, customer -> send(this, Broadcast.BUBBLE, DeleteEvent(target, customer!!)) },
                                            behaviors = EnabledWhen(notEditing))
                            )
                        })),
                dataProvider = CustomerDataProvider(repo = repo,
                        lastNameFilter = searchModel,
                        pageSize = pageSize,
                        sort = CustomerSort.ID,
                        order = SortOrder.ASCENDING),
                rowsPerPage = pageSize,
                outputMarkupId = true,
                behaviors = *arrayOf(TableBehavior().bordered().condensed().striped(), EnabledWhen(notEditing)))
                .onEvent({ c, e: SaveEvent<Customer> -> e.target.add(c) }, guard = customerCheck)
                .onEvent({ c, e: EditEvent<Customer> -> e.target.add(c) }, guard = customerCheck)
                .onEvent({ c, e: DeleteEvent<Customer> -> e.target.add(c) }, guard = customerCheck))
        q(KTextField(id = "lastNameSearch", model = searchModel,
                behaviors = *arrayOf(
                        KAjaxFormComponentUpdatingBehavior("keyup",
                                onUpdate = { it.add(table) },
                                updateAjaxAttributes = {
                                    it.throttlingSettings = KThrottlingSettings(
                                            delay = Duration.milliseconds(250),
                                            postponeTimerOnUpdate = true)
                                }),
                        InputBehavior(),
                        AttributeAppender("placeholder", "Filter By Last Name".model()))))

        val editPanel = q(CustomerEditPanel(id = "newCustomerPanel",
                model = newCustomerModel,
                visibleWhen = { newCustomerModel.value != null })
                .onEvent(handler = { c, e: SaveEvent<Customer> -> e.target.add(c) }, guard = customerCheck)
                .onEvent(handler = { c, e: EditEvent<Customer> -> e.target.add(c) }, guard = customerCheck)
                .onEvent({ c, e: CancelEvent -> e.target.add(c) }))

        q(KBootstrapAjaxLink<Customer>("newCustomerLink",
                type = Buttons.Type.Default,
                label = "New Customer".model(),
                icon = GlyphIconType.plus,
                onClick = { target, _ ->
                    newCustomerModel.value = Customer()
                    send(page, Broadcast.BREADTH, EditEvent(target, newCustomerModel.value))
                }))

        onEvent(guard = customerCheck, handler = { _, e: SaveEvent<Customer> ->
            repo.save(e.payload)
            newCustomerModel.value = null
            e.target.add(editPanel, searchForm, table)
        })
        onEvent(guard = customerCheck, handler = { _, e: DeleteEvent<Customer> ->
            repo.delete(e.payload)
            e.target.add(table)
        })
        onEvent(guard = customerCheck, handler = { _, e: EditEvent<Customer> ->
            newCustomerModel.value = e.payload
            e.target.add(editPanel, searchForm, table)
        })
        onEvent(handler = { _, e: CancelEvent ->
            newCustomerModel.value = null
            e.target.add(editPanel, searchForm, table)
        })
    }

}

