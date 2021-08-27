package com.twitter.challenge;

import com.twitter.challenge.util.TemperatureConverter;

import org.assertj.core.data.Offset;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.within;

/**
 * Test class for TemperatureConverter
 */
public class TemperatureConverterTests {
    @Test
    public void testCelsiusToFahrenheitConversion() {
        final Offset<Float> precision = within(0.01f);

        // assert for negative temperature value
        assertThat(TemperatureConverter.celsiusToFahrenheit(-50)).isEqualTo(-58, precision);
        // assert for 0 temperature value
        assertThat(TemperatureConverter.celsiusToFahrenheit(0)).isEqualTo(32, precision);
        // assert for positive temperature value
        assertThat(TemperatureConverter.celsiusToFahrenheit(10)).isEqualTo(50, precision);
        // assert for fractional temperature value
        assertThat(TemperatureConverter.celsiusToFahrenheit(21.11f)).isEqualTo(70, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(37.78f)).isEqualTo(100, precision);
        // assert for large positive integer temperature value
        assertThat(TemperatureConverter.celsiusToFahrenheit(100)).isEqualTo(212, precision);
        assertThat(TemperatureConverter.celsiusToFahrenheit(1000)).isEqualTo(1832, precision);
    }
}
