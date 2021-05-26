
package com.mapbox.maps.testapp.style.layers;

import androidx.test.annotation.UiThreadTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.generated.*;
import com.mapbox.maps.extension.style.layers.properties.generated.*;
import com.mapbox.maps.testapp.style.BaseStyleJavaTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.get;
import static com.mapbox.maps.extension.style.expressions.dsl.generated.ExpressionDslKt.literal;
import static junit.framework.TestCase.*;

/**
 * Basic smoke tests for CircleLayer
 */
@RunWith(AndroidJUnit4.class)
public class CircleLayerJavaTest extends BaseStyleJavaTest {

    @Test
    @UiThreadTest
    public void sourceLayerTest() {
        Expression expression = Expression.eq(
                get("undefined"),
                literal(1.0)
        );
        CircleLayer layer = CircleLayerKt.circleLayer("id", "source", CircleLayerDsl -> {
            CircleLayerDsl.sourceLayer("test");
            CircleLayerDsl.minZoom(10.0);
            CircleLayerDsl.filter(expression);
            CircleLayerDsl.visibility(Visibility.VISIBLE);
            CircleLayerDsl.circleOpacity(0.5);
            return null;
        });
        setupLayer(layer);
        assertEquals("test", layer.getSourceLayer());
        assertEquals(10.0, layer.getMinZoom());
        assertEquals(Visibility.VISIBLE, layer.getVisibility());
        assertEquals(0.5, layer.getCircleOpacity());
        assertEquals(expression.toString(), layer.getFilter().toString());

        Expression newExpression = Expression.eq(
                get("undefined"),
                literal(2.0)
        );
        layer.minZoom(20.0);
        layer.visibility(Visibility.NONE);
        layer.circleOpacity(1.0);
        layer.filter(newExpression);
        assertEquals(20.0, layer.getMinZoom());
        assertEquals(Visibility.NONE, layer.getVisibility());
        assertEquals(1.0, layer.getCircleOpacity());
        assertEquals(newExpression.toString(), layer.getFilter().toString());
    }
}