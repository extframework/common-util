package net.yakclient.common.util

public interface ServiceCollector<T : Any> {
    public fun add(impl: T)
}

public open class ServiceListCollector<T: Any>(
    protected val services: MutableList<T> = ArrayList()
) : ServiceCollector<T>, List<T> by services {
    override fun add(impl: T) {
        services.add(impl)
    }
}

public open class ServiceMapCollector<K, V: Any>(
    private val keyProvider: (V) -> K,
    protected val services: MutableMap<K, V> = HashMap()
) : ServiceCollector<V>, Map<K, V> by services {
    override fun add(impl: V) {
        services[keyProvider(impl)] = impl
    }
}