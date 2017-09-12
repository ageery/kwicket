package org.kwicket.agilecoders.wicket.core.markup.html.bootstrap.table

import de.agilecoders.wicket.core.markup.html.bootstrap.table.TableBehavior

class KTableBehavior(striped: Boolean = false,
                     condensed: Boolean = false,
                     bordered: Boolean = false,
                     hover: Boolean = false)
    : TableBehavior() {

    init {
        if (striped) striped()
        if (condensed) condensed()
        if (bordered) bordered()
        if (hover) hover()
    }

}