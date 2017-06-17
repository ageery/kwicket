Kotlin Wicket Parent
====================

[![Build Status](https://travis-ci.org/ageery/kwicket.svg?branch=master)](https://travis-ci.org/ageery/kwicket.svg?branch=master)

This project provides a set of Kotlin (mostly extension) methods for more fluently working with Wicket in Kotlin.

This includes:

* Setting component visibility based on a lambda
* Setting when a component should be enabled based on a lambda
* Creating behaviors that set the onConfigure handler to a lambda
* Creating AjaxEventBehavior instances that specify the onEvent handler with lambdas
* Adding lambda event handlers to components
* Specifying whether a component can be updated via ajax
* Creating AjaxLink, Link, AjaxButton and Button components with lambdas for their event handlers
* Model-related helpers
  * Create IModel instance from any Serialiable object
  * Creating a LoadableDetachableModel from a lambda

