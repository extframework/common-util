package net.yakclient.common.util

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.KProperty1

public fun <T : Any> immutableLateInit(): ImmutableLateInit<T> = ImmutableLateInit()

public class ImmutableLateInit<T : Any> {
    private var value: T? = null

    public operator fun getValue(thisRef: Any, property: KProperty<*>): T =
        value ?: throw IllegalStateException("Cannot query value at this time")

    public operator fun setValue(thisRef: Any, property: KProperty<*>, value: T): Unit = if (this.value == null) this.value =
        value else throw UnsupportedOperationException("Cannot set value at this time")
}

public fun <T, I, O> transformable(delegate: KProperty1<T, I>, mapper: (I) -> O): ReadOnlyProperty<T, O> = ReadOnlyProperty { thisRef, _ ->
    mapper(delegate.get(thisRef))
}

public fun <T, I, O> transformable(delegate: KProperty0<I>, mapper: (I) -> O): ReadOnlyProperty<T, O> = ReadOnlyProperty { _, _ ->
    mapper(delegate.get())
}