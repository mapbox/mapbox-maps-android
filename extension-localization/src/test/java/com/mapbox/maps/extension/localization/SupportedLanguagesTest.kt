package com.mapbox.maps.extension.localization

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.util.*

class SupportedLanguagesTest {
  @Test
  fun testV7() {
    assertEquals(NAME_EN, getLanguageNameV7(Locale.UK))
    assertEquals(NAME_AR, getLanguageNameV7(Locale("ar")))
    assertEquals(NAME_ES, getLanguageNameV7(Locale("es", "ES")))
    assertEquals(NAME_FR, getLanguageNameV7(Locale.FRANCE))
    assertEquals(NAME_DE, getLanguageNameV7(Locale.GERMAN))
    assertEquals(NAME_PT, getLanguageNameV7(Locale("pt", "PT")))
    assertEquals(NAME_PT, getLanguageNameV7(Locale("pt", "BR")))
    assertEquals(NAME_RU, getLanguageNameV7(Locale("ru", "RU")))
    assertEquals(NAME_JA, getLanguageNameV7(Locale.JAPANESE))
    assertEquals(NAME_KO, getLanguageNameV7(Locale.KOREA))
    assertEquals(NAME_ZH_HANS, getLanguageNameV7(Locale.SIMPLIFIED_CHINESE))
    assertEquals(NAME_ZH, getLanguageNameV7(Locale.TAIWAN))
    assertEquals(
      NAME_ZH_HANS,
      getLanguageNameV7(
        Locale.Builder().setLanguage("zh").setRegion("CN").setScript("Hans").build()
      )
    )
    assertEquals(
      NAME_ZH,
      getLanguageNameV7(
        Locale.Builder().setLanguage("zh").setRegion("HK").setScript("Hant").build()
      )
    )
  }

  @Test
  fun testV8() {
    assertEquals(NAME_EN, getLanguageNameV8(Locale.UK))
    assertEquals(NAME_AR, getLanguageNameV8(Locale("ar")))
    assertEquals(NAME_ES, getLanguageNameV8(Locale("es", "ES")))
    assertEquals(NAME_FR, getLanguageNameV8(Locale.FRANCE))
    assertEquals(NAME_DE, getLanguageNameV8(Locale.GERMAN))
    assertEquals(NAME_IT, getLanguageNameV8(Locale.ITALIAN))
    assertEquals(NAME_PT, getLanguageNameV8(Locale("pt", "PT")))
    assertEquals(NAME_PT, getLanguageNameV8(Locale("pt", "BR")))
    assertEquals(NAME_RU, getLanguageNameV8(Locale("ru", "RU")))
    assertEquals(NAME_JA, getLanguageNameV8(Locale.JAPANESE))
    assertEquals(NAME_KO, getLanguageNameV8(Locale.KOREA))
    assertEquals(NAME_VI, getLanguageNameV8(Locale("vi", "VN")))
    assertEquals(NAME_ZH_HANS, getLanguageNameV8(Locale.SIMPLIFIED_CHINESE))
    assertEquals(NAME_ZH_HANT, getLanguageNameV8(Locale.TAIWAN))
    assertEquals(
      NAME_ZH_HANS,
      getLanguageNameV8(
        Locale.Builder().setLanguage("zh").setRegion("CN").setScript("Hans").build()
      )
    )
    assertEquals(
      NAME_ZH_HANT,
      getLanguageNameV8(
        Locale.Builder().setLanguage("zh").setRegion("HK").setScript("Hant").build()
      )
    )
  }
}