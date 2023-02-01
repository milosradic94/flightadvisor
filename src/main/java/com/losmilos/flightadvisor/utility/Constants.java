package com.losmilos.flightadvisor.utility;

import java.util.Arrays;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public class InappropriateData {

        public static final List<String> LOWER_CASE_WORDS = Arrays.asList(new String[] {
            "word1", "word2", "word3"
        });

    }

    @UtilityClass
    public class CacheNames {

        public static final String CITIES_WITH_COMMENTS = "citiesWithComments";

    }

}
