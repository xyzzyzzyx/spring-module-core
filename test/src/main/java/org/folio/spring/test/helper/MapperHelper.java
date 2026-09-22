package org.folio.spring.test.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.StreamReadFeature;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Unit test helper for constructing the JsonMapper without going through Spring.
 */
public class MapperHelper {
  
  /**
   * Prohibit construction on this helper utility.
   */
  private MapperHelper() {
    // Should not do anything.
  }

  /**
   * Build the JsonMapper.
   *
   * @return The built mapper.
   */
  public static JsonMapper build() {
    return construct().build();
  }

  /**
   * Construct the JsonMapper without building it.
   *
   * Use this to customize the defaults before changing.
   *
   * @return The mapper builder.
   */
  public static JsonMapper.Builder construct() {
    return JsonMapper
    .builderWithJackson2Defaults()
    .configure(DeserializationFeature.FAIL_ON_MISSING_EXTERNAL_TYPE_ID_PROPERTY, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .configure(MapperFeature.REQUIRE_TYPE_ID_FOR_SUBTYPES, true)
    .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true)
    .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    .configure(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION, true)
    .changeDefaultPropertyInclusion(incl -> incl
      .withValueInclusion(JsonInclude.Include.NON_NULL)
      .withContentInclusion(JsonInclude.Include.NON_NULL)
    )
    .findAndAddModules();
  }

}
