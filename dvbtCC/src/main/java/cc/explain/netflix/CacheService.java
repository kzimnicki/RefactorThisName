package cc.explain.netflix;

import cc.explain.netflix.redis.Language;

import java.util.Optional;

public interface CacheService {

    void put(Language from, Language to, String key, String value);

    Optional<String> get(Language from, Language to, String key);
}