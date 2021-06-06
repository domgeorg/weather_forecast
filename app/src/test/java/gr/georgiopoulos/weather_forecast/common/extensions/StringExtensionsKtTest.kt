package gr.georgiopoulos.weather_forecast.common.extensions

import org.junit.Assert
import org.junit.Test

class StringExtensionsKtTest {
    //region toHour
    @Test
    fun `given null toHour then should return empty string`() {
        val actual = null.toHour()
        val expected = ""

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given empty sting toHour then should return empty string`() {
        val actual = "".toHour()
        val expected = ""

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given 0 toHour then should return valid hour`() {
        val actual = "0".toHour()
        val expected = "00:00"

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given 3 chars toHour then should return valid hour`() {
        val actual = "200".toHour()
        val expected = "02:00"

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given 4 chars toHour then should return valid hour`() {
        val actual = "1400".toHour()
        val expected = "14:00"

        Assert.assertEquals(expected, actual)
    }
    //endregion

    //region formatDate
    @Test
    fun `given Time with valid format when formatDate then should return dd MMMM yyyy`() {
        val time = "2021-06-06"

        val actual = time.formatDate()
        val expected = "06 June 2021"

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given time with valid format when formatDate then should return dd MMMM yyyy`() {
        val time = "2021-06-06T12:24:36"

        val actual = time.formatDate()
        val expected = "06 June 2021"

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given time with invalid format when formatDate then should return empty string`() {
        val time = "2021-06T12:24:36"

        val actual = time.formatDate()
        val expected = ""

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given null time when formatDate then should return empty string`() {
        val time = null

        val actual = time.formatDate()
        val expected = ""

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `given empty time when formatDate then should return empty string`() {
        val time = ""

        val actual = time.formatDate()
        val expected = ""

        Assert.assertEquals(expected, actual)
    }
    //endregion
}