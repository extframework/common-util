module yakclient.common.util {
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core.jvm;
    requires java.logging;
    requires kotlin.reflect;

    exports net.yakclient.common.util;
    exports net.yakclient.common.util.resource;
}