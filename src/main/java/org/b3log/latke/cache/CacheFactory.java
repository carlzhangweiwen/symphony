/*
 * Copyright (c) 2009-2016, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.b3log.latke.cache;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.b3log.latke.Latkes;
import org.b3log.latke.RuntimeEnv;
import org.b3log.latke.logging.Level;
import org.b3log.latke.logging.Logger;

/**
 * Cache factory.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 2.0.1.1, Jan 8, 2016
 */
public final class CacheFactory {

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(CacheFactory.class.getName());

    /**
     * Caches.
     */
    private static final Map<String, Cache<String, ?>> CACHES = Collections.synchronizedMap(new HashMap<String, Cache<String, ?>>());

    /**
     * Removes all caches.
     */
    public static synchronized void removeAll() {
        RuntimeEnv runtime = Latkes.getRuntime("cache");

        if (RuntimeEnv.LOCAL == Latkes.getRuntimeEnv()) { // Always LOCAL cache if runs on a standard servlet container
            runtime = RuntimeEnv.LOCAL;
        }

        switch (runtime) {
            case LOCAL:
                // Clears cache one by one
                for (final Map.Entry<String, Cache<String, ?>> entry : CACHES.entrySet()) {
                    final Cache<String, ?> cache = entry.getValue();

                    cache.removeAll();
                    LOGGER.log(Level.TRACE, "Clears cache[name={0}]", entry.getKey());
                }

                break;

            default:
                throw new RuntimeException("Latke runs in the hell.... Please set the enviornment correctly");
        }
    }

    /**
     * Gets a cache specified by the given cache name.
     *
     * @param cacheName the given cache name
     * @return a cache specified by the given cache name
     */
    @SuppressWarnings("unchecked")
    public static synchronized Cache<String, ? extends Serializable> getCache(final String cacheName) {
        LOGGER.log(Level.INFO, "Constructing Cache[name={0}]....", cacheName);

        Cache<String, ?> ret = CACHES.get(cacheName);

        try {
            if (null == ret) {
                switch (Latkes.getRuntime("cache")) {
                    case LOCAL:
                        final Class<Cache<String, ?>> localLruCache = (Class<Cache<String, ?>>) Class.forName(
                                "org.b3log.latke.cache.local.memory.LruMemoryCache");

                        ret = localLruCache.newInstance();
                        break;
                    default:
                        throw new RuntimeException("Latke runs in the hell.... Please set the enviornment correctly");
                }

                CACHES.put(cacheName, ret);
            }
        } catch (final Exception e) {
            throw new RuntimeException("Can not get cache: " + e.getMessage(), e);
        }

        LOGGER.log(Level.INFO, "Constructed Cache[name={0}]", cacheName);

        return (Cache<String, Serializable>) ret;
    }

    /**
     * Private default constructor.
     */
    private CacheFactory() {
    }
}
