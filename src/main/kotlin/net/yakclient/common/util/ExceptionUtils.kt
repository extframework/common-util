package net.yakclient.common.util

import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

public inline fun <T> runCatching(exception: KClass<out Exception>, crossinline block: () -> T): T? =
    runCatching(block).let {
        if (it.isFailure && !it.exceptionOrNull()!!::class.isSubclassOf(exception)) throw it.exceptionOrNull()!! else return it.getOrNull()
    }
