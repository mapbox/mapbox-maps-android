package com.mapbox.maps.attribution

import android.graphics.Bitmap
import android.graphics.PointF
import android.widget.TextView

/**
 * Class used to measure all attributions for placing them on the screen.
 */
class AttributionMeasure internal constructor(
  private val snapshot: Bitmap,
  private val logo: Bitmap,
  private val logoSmall: Bitmap,
  private val textView: TextView,
  private val textViewShort: TextView,
  private val margin: Float
) {
  private var shorterText = false

  /**
   * Measure all attributions.
   *
   * @return [AttributionLayout] with all attributions.
   */
  fun measure(): AttributionLayout? {
    val chain = Chain(
      FullLogoLongTextCommand(),
      FullLogoShortTextCommand(),
      SmallLogoLongTextCommand(),
      SmallLogoShortTextCommand(),
      LongTextCommand(),
      ShortTextCommand(),
      NoTextCommand()
    )
    val attributionLayout = chain.start(this)
    attributionLayout?.let {
      shorterText = it.isShortText
    }
    return attributionLayout
  }

  private class FullLogoLongTextCommand : Command {
    override fun execute(measure: AttributionMeasure): AttributionLayout? {
      val width = measure.logoContainerWidth + measure.textViewContainerWidth
      return execute(measure, width, measure.maxSize, measure.logo)
    }
  }

  private class FullLogoShortTextCommand : Command {
    override fun execute(measure: AttributionMeasure): AttributionLayout? {
      val width = measure.logoContainerWidth + measure.textViewShortContainerWidth
      return execute(measure, width, measure.maxSizeShort, measure.logo, true)
    }
  }

  private class SmallLogoLongTextCommand : Command {
    override fun execute(measure: AttributionMeasure): AttributionLayout? {
      val width = measure.logoSmallContainerWidth + measure.textViewContainerWidth
      return execute(measure, width, measure.maxSize, measure.logoSmall)
    }
  }

  private class SmallLogoShortTextCommand : Command {
    override fun execute(measure: AttributionMeasure): AttributionLayout? {
      val width = measure.logoContainerWidth + measure.textViewShortContainerWidth
      return execute(measure, width, measure.maxSizeShort, measure.logoSmall, true)
    }
  }

  private class LongTextCommand : Command {
    override fun execute(measure: AttributionMeasure): AttributionLayout? {
      val width = measure.textViewContainerWidth + measure.margin
      return execute(measure, width, measure.maxSize, null)
    }
  }

  private class ShortTextCommand : Command {
    override fun execute(measure: AttributionMeasure): AttributionLayout? {
      val width = measure.textViewShortContainerWidth + measure.margin
      return execute(measure, width, measure.maxSizeShort, null, true)
    }
  }

  private class NoTextCommand : Command {
    override fun execute(measure: AttributionMeasure): AttributionLayout? {
      return AttributionLayout(null, null, false)
    }
  }

  /**
   * @return short or full version of [TextView] depending on [AttributionLayout.isShortText] flag
   */
  fun getTextView(): TextView {
    return if (shorterText) textViewShort else textView
  }

  private inner class Chain(vararg commands: Command) {
    var commands: List<Command> = listOf(*commands)

    fun start(measure: AttributionMeasure): AttributionLayout? {
      var attributionLayout: AttributionLayout? = null
      for (command in commands) {
        attributionLayout = command.execute(measure)
        if (attributionLayout != null) {
          break
        }
      }
      return attributionLayout
    }
  }

  private fun interface Command {
    fun execute(
      measure: AttributionMeasure,
      width: Float,
      maxSize: Float,
      logo: Bitmap?,
      isShortText: Boolean = false
    ): AttributionLayout? {
      return if (width <= maxSize)
        AttributionLayout(
          logo,
          calculateAnchor(measure.snapshot, measure.textView, measure.margin),
          isShortText
        )
      else
        null
    }

    fun execute(measure: AttributionMeasure): AttributionLayout?
  }

  private val textViewContainerWidth: Float
    get() = textView.measuredWidth + margin

  private val logoContainerWidth: Float
    get() = logo.width + 2 * margin

  private val textViewShortContainerWidth: Float
    get() = textViewShort.measuredWidth + margin

  private val logoSmallContainerWidth: Float
    get() = logoSmall.width + 2 * margin

  private val maxSize: Float
    get() = (snapshot.width * 8 / 10).toFloat()

  private val maxSizeShort: Float
    get() = snapshot.width.toFloat()

  /**
   * Static variables and methods.
   */
  companion object {
    private fun calculateAnchor(
      snapshot: Bitmap,
      textView: TextView,
      margin: Float
    ): PointF {
      return PointF(
        snapshot.width - textView.measuredWidth - margin,
        snapshot.height - textView.measuredHeight - margin
      )
    }
  }
}