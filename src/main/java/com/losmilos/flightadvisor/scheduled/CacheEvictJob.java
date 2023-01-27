package com.losmilos.flightadvisor.scheduled;

import com.losmilos.flightadvisor.utility.Constants.CacheNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CacheEvictJob {

    @Scheduled(fixedRate = 60 * 60 * 1000, initialDelay = 60 * 60 * 1000)
    @CacheEvict(value = CacheNames.CITIES_WITH_COMMENTS, allEntries = true)
    public void citiesWithCommentsCacheEvict() {
      log.info("Cache {} cleared.", CacheNames.CITIES_WITH_COMMENTS);
    }

}
