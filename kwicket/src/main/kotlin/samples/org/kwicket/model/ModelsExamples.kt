package samples.org.kwicket.model

import org.apache.wicket.model.IModel
import org.kwicket.model.model
import org.kwicket.model.value

fun nonNullModelExample() {
    val model: IModel<String> = "test".model()
}

fun nullableModelExample() {
    val model = "test".model<String?>()
    model.value = null
}
