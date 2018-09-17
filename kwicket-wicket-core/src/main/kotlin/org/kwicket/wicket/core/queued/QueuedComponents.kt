package org.kwicket.wicket.core.queued

import org.apache.wicket.MarkupContainer
import org.apache.wicket.Page
import org.apache.wicket.behavior.Behavior
import org.apache.wicket.feedback.IFeedbackMessageFilter
import org.apache.wicket.markup.html.form.upload.FileUpload
import org.apache.wicket.markup.html.form.validation.IFormValidator
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.model.IModel
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.request.resource.IResource
import org.apache.wicket.request.resource.PackageResourceReference
import org.apache.wicket.request.resource.ResourceReference
import org.kwicket.component.q
import org.kwicket.wicket.core.markup.html.KWebMarkupContainer
import org.kwicket.wicket.core.markup.html.basic.KLabel
import org.kwicket.wicket.core.markup.html.basic.KMultiLineLabel
import org.kwicket.wicket.core.markup.html.form.KForm
import org.kwicket.wicket.core.markup.html.form.KTextArea
import org.kwicket.wicket.core.markup.html.form.KTextField
import org.kwicket.wicket.core.markup.html.form.upload.KFileUploadField
import org.kwicket.wicket.core.markup.html.image.KImage
import org.kwicket.wicket.core.markup.html.image.KInlineImage
import org.kwicket.wicket.core.markup.html.image.KPicture
import org.kwicket.wicket.core.markup.html.image.KSource
import org.kwicket.wicket.core.markup.html.link.KBookmarkablePageLink
import org.kwicket.wicket.core.markup.html.list.KListView
import org.kwicket.wicket.core.markup.html.panel.KFeedbackPanel
import kotlin.reflect.KClass

fun <T> MarkupContainer.form(
    id: String,
    model: IModel<T>? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    outputMarkupId: Boolean? = null,
    behaviors: List<Behavior>? = null,
    validators: List<IFormValidator>? = null
) = q(
    KForm(
        id = id,
        model = model,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        outputMarkupId = outputMarkupId,
        behaviors = behaviors,
        validators = validators
    )
)

fun MarkupContainer.webMarkupContainer(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KWebMarkupContainer(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors
    )
)

fun MarkupContainer.webMarkupContainer(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behavior: Behavior
) = q(
    KWebMarkupContainer(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        visible = visible,
        enabled = enabled,
        behavior = behavior
    )
)

fun <T, C : List<T>> MarkupContainer.listView(
    id: String,
    model: IModel<C>? = null,
    reuseItems: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null,
    populate: ListItem<T>.() -> Unit
) = q(
    KListView(
        id = id,
        model = model,
        reuseItems = reuseItems,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = behaviors,
        populate = { populate(it) }
    )
)

fun MarkupContainer.label(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KLabel(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors
    )
)

fun MarkupContainer.label(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behavior: Behavior
) = q(
    KLabel(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behavior = behavior
    )
)

fun MarkupContainer.multiLineLabel(
    id: String,
    model: IModel<*>,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KMultiLineLabel(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        escapeModelStrings = escapeModelStrings,
        renderBodyOnly = renderBodyOnly,
        behaviors = behaviors
    )
)

fun MarkupContainer.fileUploadField(
    id: String,
    model: IModel<MutableList<FileUpload>>,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
) = KFileUploadField(
    id = id,
    model = model,
    required = required,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    label = label,
    visible = visible,
    enabled = enabled,
    behaviors = behaviors
)

fun MarkupContainer.fileUploadField(
    id: String,
    model: IModel<MutableList<FileUpload>>,
    required: Boolean? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    label: IModel<String>? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behavior: Behavior
) = KFileUploadField(
    id = id,
    model = model,
    required = required,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    label = label,
    visible = visible,
    enabled = enabled,
    behaviors = listOf(behavior)
)

fun MarkupContainer.feedbackPanel(
    id: String,
    filter: IFeedbackMessageFilter? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KFeedbackPanel(
        id = id,
        filter = filter,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = behaviors,
        visible = visible,
        enabled = enabled
    )
)

fun MarkupContainer.picture(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KPicture(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors
    )
)

fun MarkupContainer.picture(
    id: String,
    model: IModel<*>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behavior: Behavior
) = q(
    KPicture(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        visible = visible,
        enabled = enabled,
        behavior = behavior
    )
)

fun MarkupContainer.image(
    id: String,
    model: IModel<*>? = null,
    resRef: ResourceReference? = null,
    resParams: PageParameters? = null,
    resRefs: List<ResourceReference>? = null,
    imageResources: List<IResource>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    xValues: List<String>? = null,
    sizes: List<String>? = null,
    behaviors: List<Behavior>? = null
) = q(
    KImage(
        id = id,
        model = model,
        resRef = resRef,
        resParams = resParams,
        resRefs = resRefs,
        imageResources = imageResources,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        xValues = xValues,
        sizes = sizes,
        behaviors = behaviors
    )
)

// FIXME: there should be two methods one with a single resRef and one with a list
fun MarkupContainer.image(
    id: String,
    model: IModel<*>? = null,
    resRef: ResourceReference? = null,
    resParams: PageParameters? = null,
    resRefs: List<ResourceReference>? = null,
    imageResources: List<IResource>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    xValues: List<String>? = null,
    sizes: List<String>? = null,
    behavior: Behavior
) = q(
    KImage(
        id = id,
        model = model,
        resRef = resRef,
        resParams = resParams,
        resRefs = resRefs,
        imageResources = imageResources,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        xValues = xValues,
        sizes = sizes,
        behaviors = listOf(behavior)
    )
)

fun MarkupContainer.inlineImage(
    id: String,
    model: IModel<*>? = null,
    resRef: PackageResourceReference,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(KInlineImage(
    id = id,
    model = model,
    resRef = resRef,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    visible = visible,
    enabled = enabled,
    renderBodyOnly = renderBodyOnly,
    escapeModelStrings = escapeModelStrings,
    behaviors = behaviors
))

fun MarkupContainer.inlineImage(
    id: String,
    model: IModel<*>? = null,
    resRef: PackageResourceReference,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behavior: Behavior
) = q(KInlineImage(
    id = id,
    model = model,
    resRef = resRef,
    outputMarkupId = outputMarkupId,
    outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
    visible = visible,
    enabled = enabled,
    renderBodyOnly = renderBodyOnly,
    escapeModelStrings = escapeModelStrings,
    behavior = behavior
))


fun MarkupContainer.source(
    id: String,
    model: IModel<*>? = null,
    resRef: ResourceReference? = null,
    resParams: PageParameters? = null,
    resRefs: List<ResourceReference>? = null,
    imageResources: List<IResource>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    xValues: List<String>? = null,
    sizes: List<String>? = null,
    media: String? = null,
    behaviors: List<Behavior>? = null
) = q(
    KSource(
        id = id,
        model = model,
        resRef = resRef,
        resParams = resParams,
        resRefs = resRefs,
        imageResources = imageResources,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        xValues = xValues,
        sizes = sizes,
        media = media,
        behaviors = behaviors
    )
)

fun MarkupContainer.source(
    id: String,
    model: IModel<*>? = null,
    resRef: ResourceReference? = null,
    resParams: PageParameters? = null,
    resRefs: List<ResourceReference>? = null,
    imageResources: List<IResource>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    xValues: List<String>? = null,
    sizes: List<String>? = null,
    media: String? = null,
    behavior: Behavior
) = q(
    KSource(
        id = id,
        model = model,
        resRef = resRef,
        resParams = resParams,
        resRefs = resRefs,
        imageResources = imageResources,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        xValues = xValues,
        sizes = sizes,
        media = media,
        behavior = behavior
    )
)


fun <C : Page> MarkupContainer.bookmarkablePageLink(
    id: String,
    page: KClass<C>,
    params: PageParameters? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KBookmarkablePageLink(
        id = id,
        page = page,
        params = params,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        behaviors = behaviors
    )
)

fun <T : Any> MarkupContainer.textField(
    id: String,
    model: IModel<T?>? = null,
    type: KClass<T>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behavior: Behavior
) = q(
    KTextField(
        id = id,
        model = model,
        type = type,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = listOf(behavior)
    )
)

fun <T : Any> MarkupContainer.textField(
    id: String,
    model: IModel<T?>? = null,
    type: KClass<T>? = null,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KTextField(
        id = id,
        model = model,
        type = type,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = behaviors
    )
)

fun <T : Any> MarkupContainer.textArea(
    id: String,
    model: IModel<T?>,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behavior: Behavior
) = q(
    KTextArea(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = listOf(behavior)
    )
)

fun <T : Any> MarkupContainer.textArea(
    id: String,
    model: IModel<T?>,
    outputMarkupId: Boolean? = null,
    outputMarkupPlaceholderTag: Boolean? = null,
    visible: Boolean? = null,
    enabled: Boolean? = null,
    renderBodyOnly: Boolean? = null,
    escapeModelStrings: Boolean? = null,
    behaviors: List<Behavior>? = null
) = q(
    KTextArea(
        id = id,
        model = model,
        outputMarkupId = outputMarkupId,
        outputMarkupPlaceholderTag = outputMarkupPlaceholderTag,
        visible = visible,
        enabled = enabled,
        renderBodyOnly = renderBodyOnly,
        escapeModelStrings = escapeModelStrings,
        behaviors = behaviors
    )
)