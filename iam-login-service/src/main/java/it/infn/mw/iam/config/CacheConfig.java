/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2021
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
package it.infn.mw.iam.config;



import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import it.infn.mw.iam.core.web.wellknown.IamWellKnownInfoProvider;

@Configuration
@EnableCaching
public class CacheConfig {

  private static final Logger LOG = LoggerFactory.getLogger(CacheConfig.class);
  
  @Autowired
  CacheManager cacheManager;
  
  @Scheduled(fixedDelay = 5, timeUnit = TimeUnit.MINUTES)
  public void evictWellKnownCache() {
	
	if(cacheManager.getCache(IamWellKnownInfoProvider.CACHE_KEY) != null) {
	  cacheManager.getCache(IamWellKnownInfoProvider.CACHE_KEY).clear();
	  LOG.debug("well-known config cache evicted");
	}
  }

  @Bean
  public CacheManager cacheManager() {

	return new ConcurrentMapCacheManager(IamWellKnownInfoProvider.CACHE_KEY);
  }

}
