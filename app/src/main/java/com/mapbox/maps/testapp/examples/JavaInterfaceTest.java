package com.mapbox.maps.testapp.examples;

import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.expressions.generated.Expression;
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;
import com.mapbox.maps.extension.style.types.Formatted;
import com.mapbox.maps.extension.style.types.FormattedSection;

import java.util.ArrayList;

import static com.mapbox.maps.extension.style.expressions.generated.Expression.*;

public class JavaInterfaceTest {

    private void addSymbolLayer(Style style) {
        Formatted formatted = new Formatted();
        formatted.add(new FormattedSection("test", 0.7, new ArrayList<String>(), "#1000"));
        SymbolLayer symbolLayer = new SymbolLayer("test", "test")
                .iconAnchor(IconAnchor.BOTTOM)
                .textField(formatted)
                .iconOpacity(literal(0.9));
        symbolLayer.bindTo(style);

        Expression expression = division(
                literal(2.0),
                pi());
        symbolLayer.iconOpacity(expression);
        symbolLayer.iconColor(rgba(255.0, 0.0, 0.0, 1.0));
    }
}