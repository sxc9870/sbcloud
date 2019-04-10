package com.sbcloud.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程变量工具
 *
 */
public class ThreadLocalUtil {

    private static class MapThreadLocal extends ThreadLocal<Map<String, Object>> {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>() {

                private static final long serialVersionUID = 3637958959138295593L;

                @Override
                public Object put(String key, Object value) {
                    if (LOGGER.isDebugEnabled()) {
                        if (containsKey(key)) {
                            LOGGER.debug("Overwritten attribute to thread context: " + key + " = " + value);
                        } else {
                            LOGGER.debug("Added attribute to thread context: " + key + " = " + value);
                        }
                    }

                    return super.put(key, value);
                }
            };
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadLocalUtil.class);

    protected final static ThreadLocal<Map<String, Object>> threadContext = new MapThreadLocal();;

    public static Object get(String key) {
        return getContextMap().get(key);
    }

    /**
     * 取得thread context Map的实例。
     * 
     * @return thread context Map的实例
     */
    protected static Map<String, Object> getContextMap() {
        return threadContext.get();
    }

    public static void put(String key, Object value) {
        getContextMap().put(key, value);
    }

    public static Object remove(String key) {
        return getContextMap().remove(key);
    }

    /**
     * 清理线程所有被hold住的对象。以便重用！
     */

    public static void reset() {
        getContextMap().clear();
    }

    private ThreadLocalUtil() {
    }
}
