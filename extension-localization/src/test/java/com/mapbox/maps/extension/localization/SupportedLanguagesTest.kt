package com.mapbox.maps.extension.localization

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

private val commonLanguageList = listOf(
  arrayOf(NAME_EN, Locale.UK),
  arrayOf(NAME_AR, Locale("ar")),
  arrayOf(NAME_ES, Locale("es", "ES")),
  arrayOf(NAME_FR, Locale.FRANCE),
  arrayOf(NAME_DE, Locale.GERMAN),
  arrayOf(NAME_PT, Locale("pt", "PT")),
  arrayOf(NAME_PT, Locale("pt", "BR")),
  arrayOf(NAME_RU, Locale("ru", "RU")),
  arrayOf(NAME_JA, Locale.JAPANESE),
  arrayOf(NAME_KO, Locale.KOREA),
  arrayOf(NAME_ZH_HANS, Locale.SIMPLIFIED_CHINESE),
  arrayOf(
    NAME_ZH_HANS,
    Locale.Builder().setLanguage("zh").setRegion("CN").setScript("Hans").build()
  ),
)

@RunWith(Parameterized::class)
class LanguageNameV7Test(
  private val expected: String,
  private val inputLocale: Locale,
) {
  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "Language for locale {1} should be {0}")
    fun data() = commonLanguageList + listOf(
      arrayOf(NAME_ZH, Locale.TAIWAN),
      arrayOf(
        NAME_ZH,
        Locale.Builder().setLanguage("zh").setRegion("HK").setScript("Hant").build()
      ),
    )
  }

  @Test
  fun checkLanguageName() {
    val actual = getLanguageNameV7(inputLocale)
    assertEquals(expected, actual)
  }
}

@RunWith(Parameterized::class)
class LanguageNameV8Test(
  private val expected: String,
  private val inputLocale: Locale,
) {
  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "Language for locale {1} should be {0}")
    fun data() = commonLanguageList + listOf(
      arrayOf(NAME_IT, Locale.ITALIAN),
      arrayOf(NAME_ZH_HANT, Locale.TAIWAN),
      arrayOf(
        NAME_ZH_HANT,
        Locale.Builder().setLanguage("zh").setRegion("HK").setScript("Hant").build()
      ),
    )
  }

  @Test
  fun checkLanguageName() {
    val actual = getLanguageNameV8(inputLocale)
    assertEquals(expected, actual)
  }
}