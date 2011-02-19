/*
 * Copyright (c) 2009-2011, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.calendar.format;

import static javax.time.calendar.ISODateTimeRule.DAY_OF_MONTH;
import static javax.time.calendar.ISODateTimeRule.DAY_OF_WEEK;
import static javax.time.calendar.ISODateTimeRule.MINUTE_OF_HOUR;
import static javax.time.calendar.ISODateTimeRule.MONTH_OF_YEAR;
import static javax.time.calendar.ISODateTimeRule.YEAR;
import static org.testng.Assert.assertEquals;

import javax.time.calendar.CalendricalMerger;
import javax.time.calendar.format.DateTimeFormatterBuilder.SignStyle;
import javax.time.calendar.format.DateTimeFormatterBuilder.TextStyle;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test DateTimeFormatterBuilder.
 *
 * @author Stephen Colebourne
 */
@Test
public class TestDateTimeFormatterBuilder {

    private DateTimeFormatterBuilder builder;

    @BeforeMethod
    public void setUp() {
        builder = new DateTimeFormatterBuilder();
    }

    //-----------------------------------------------------------------------
    public void test_parseCaseSensitive() throws Exception {
        builder.parseCaseSensitive();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "ParseCaseSensitive(true)");
    }

    public void test_parseCaseInsensitive() throws Exception {
        builder.parseCaseInsensitive();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "ParseCaseSensitive(false)");
    }

    //-----------------------------------------------------------------------
    public void test_parseStrict() throws Exception {
        builder.parseStrict();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "ParseStrict(true)");
    }

    public void test_parseLenient() throws Exception {
        builder.parseLenient();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "ParseStrict(false)");
    }

    //-----------------------------------------------------------------------
    public void test_appendValue_1arg() throws Exception {
        builder.appendValue(DAY_OF_MONTH);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.DayOfMonth)");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendValue_1arg_null() throws Exception {
        builder.appendValue(null);
    }

    //-----------------------------------------------------------------------
    public void test_appendValue_2arg() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 3);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.DayOfMonth,3)");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendValue_2arg_null() throws Exception {
        builder.appendValue(null, 3);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendValue_2arg_widthTooSmall() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 0);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendValue_2arg_widthTooBig() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 20);
    }

    //-----------------------------------------------------------------------
    public void test_appendValue_3arg() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 2, 3, SignStyle.NORMAL);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.DayOfMonth,2,3,NORMAL)");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendValue_3arg_nullField() throws Exception {
        builder.appendValue(null, 2, 3, SignStyle.NORMAL);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendValue_3arg_minWidthTooSmall() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 0, 2, SignStyle.NORMAL);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendValue_3arg_minWidthTooBig() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 20, 2, SignStyle.NORMAL);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendValue_3arg_maxWidthTooSmall() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 2, 0, SignStyle.NORMAL);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendValue_3arg_maxWidthTooBig() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 2, 20, SignStyle.NORMAL);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendValue_3arg_maxWidthMinWidth() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 4, 2, SignStyle.NORMAL);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendValue_3arg_nullSignStyle() throws Exception {
        builder.appendValue(DAY_OF_MONTH, 2, 3, null);
    }

    //-----------------------------------------------------------------------
    public void test_appendValue_subsequent2_parse3() throws Exception {
        builder.appendValue(MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL).appendValue(DAY_OF_MONTH, 2);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear,1,2,NORMAL)Value(ISO.DayOfMonth,2)");
        CalendricalMerger cal = f.parse("123");
        assertEquals(cal.getInputMap().get(MONTH_OF_YEAR), 1L);
        assertEquals(cal.getInputMap().get(DAY_OF_MONTH), 23L);
    }

    public void test_appendValue_subsequent2_parse4() throws Exception {
        builder.appendValue(MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL).appendValue(DAY_OF_MONTH, 2);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear,1,2,NORMAL)Value(ISO.DayOfMonth,2)");
        CalendricalMerger cal = f.parse("0123");
        assertEquals(cal.getInputMap().get(MONTH_OF_YEAR), 1L);
        assertEquals(cal.getInputMap().get(DAY_OF_MONTH), 23L);
    }

    public void test_appendValue_subsequent2_parse5() throws Exception {
        builder.appendValue(MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL).appendValue(DAY_OF_MONTH, 2).appendLiteral('4');
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear,1,2,NORMAL)Value(ISO.DayOfMonth,2)'4'");
        CalendricalMerger cal = f.parse("01234");
        assertEquals(cal.getInputMap().get(MONTH_OF_YEAR), 1L);
        assertEquals(cal.getInputMap().get(DAY_OF_MONTH), 23L);
    }

    public void test_appendValue_subsequent3_parse6() throws Exception {
        builder
            .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
            .appendValue(MONTH_OF_YEAR, 2)
            .appendValue(DAY_OF_MONTH, 2);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.Year,4,10,EXCEEDS_PAD)Value(ISO.MonthOfYear,2)Value(ISO.DayOfMonth,2)");
        CalendricalMerger cal = f.parse("20090630");
        assertEquals(cal.getInputMap().get(YEAR), 2009L);
        assertEquals(cal.getInputMap().get(MONTH_OF_YEAR), 6L);
        assertEquals(cal.getInputMap().get(DAY_OF_MONTH), 30L);
    }

    //-----------------------------------------------------------------------
    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendValueReduced_null() throws Exception {
        builder.appendValueReduced(null, 2, 2000);
    }

    public void test_appendValueReduced() throws Exception {
        builder.appendValueReduced(YEAR, 2, 2000);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "ReducedValue(ISO.Year,2,2000)");
        CalendricalMerger cal = f.parse("12");
        assertEquals(cal.getInputMap().get(YEAR), 2012L);
    }

    public void test_appendValueReduced_subsequent_parse() throws Exception {
        builder.appendValue(MONTH_OF_YEAR, 1, 2, SignStyle.NORMAL).appendValueReduced(YEAR, 2, 2000);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear,1,2,NORMAL)ReducedValue(ISO.Year,2,2000)");
        CalendricalMerger cal = f.parse("123");
        assertEquals(cal.getInputMap().get(MONTH_OF_YEAR), 1L);
        assertEquals(cal.getInputMap().get(YEAR), 2023L);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void test_appendFraction_3arg() throws Exception {
        builder.appendFraction(MINUTE_OF_HOUR, 1, 9);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Fraction(ISO.MinuteOfHour,1,9)");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendFraction_3arg_nullRule() throws Exception {
        builder.appendFraction(null, 1, 9);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendFraction_3arg_invalidRuleNotFixedSet() throws Exception {
        builder.appendFraction(DAY_OF_MONTH, 1, 9);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendFraction_3arg_invalidRuleNotMinZero() throws Exception {
        builder.appendFraction(MONTH_OF_YEAR, 1, 9);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendFraction_3arg_minTooSmall() throws Exception {
        builder.appendFraction(MINUTE_OF_HOUR, -1, 9);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendFraction_3arg_minTooBig() throws Exception {
        builder.appendFraction(MINUTE_OF_HOUR, 10, 9);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendFraction_3arg_maxTooSmall() throws Exception {
        builder.appendFraction(MINUTE_OF_HOUR, 0, -1);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendFraction_3arg_maxTooBig() throws Exception {
        builder.appendFraction(MINUTE_OF_HOUR, 1, 10);
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_appendFraction_3arg_maxWidthMinWidth() throws Exception {
        builder.appendFraction(MINUTE_OF_HOUR, 9, 3);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void test_appendText_1arg() throws Exception {
        builder.appendText(MONTH_OF_YEAR);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Text(ISO.MonthOfYear)");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendText_1arg_null() throws Exception {
        builder.appendText(null);
    }

    //-----------------------------------------------------------------------
    public void test_appendText_2arg() throws Exception {
        builder.appendText(MONTH_OF_YEAR, TextStyle.SHORT);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Text(ISO.MonthOfYear,SHORT)");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendText_2arg_nullRule() throws Exception {
        builder.appendText(null, TextStyle.SHORT);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendText_2arg_nullStyle() throws Exception {
        builder.appendText(MONTH_OF_YEAR, null);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void test_appendOffsetId() throws Exception {
        builder.appendOffsetId();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Offset('Z',+HH:MM:ss)");
    }

    @DataProvider(name="offsetPatterns")
    public Object[][] data_offsetPatterns() {
        return new Object[][] {
            {"+HH"},
            {"+HHMM"},
            {"+HH:MM"},
            {"+HHMMss"},
            {"+HH:MM:ss"},
            {"+HHMMSS"},
            {"+HH:MM:SS"},
        };
    }

    @Test(dataProvider="offsetPatterns")
    public void test_appendOffset(String pattern) throws Exception {
        builder.appendOffset("Z", pattern);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Offset('Z'," + pattern + ")");
    }

    @DataProvider(name="badOffsetPatterns")
    public Object[][] data_badOffsetPatterns() {
        return new Object[][] {
            {"HH"},
            {"HHMM"},
            {"HH:MM"},
            {"HHMMss"},
            {"HH:MM:ss"},
            {"HHMMSS"},
            {"HH:MM:SS"},
            {"+H"},
            {"+HMM"},
            {"+HHM"},
            {"+A"},
        };
    }

    @Test(dataProvider="badOffsetPatterns", expectedExceptions = IllegalArgumentException.class)
    public void test_appendOffset_badPattern(String pattern) throws Exception {
        builder.appendOffset("Z", pattern);
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendOffset_3arg_nullText() throws Exception {
        builder.appendOffset(null, "+HH:MM");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendOffset_3arg_nullPattern() throws Exception {
        builder.appendOffset("Z", null);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void test_appendZoneId() throws Exception {
        builder.appendZoneId();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "ZoneId()");
    }

    public void test_appendZoneText_1arg() throws Exception {
        builder.appendZoneText(TextStyle.FULL);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "ZoneText(FULL)");
    }

    @Test(expectedExceptions=NullPointerException.class)
    public void test_appendZoneText_1arg_nullText() throws Exception {
        builder.appendZoneText(null);
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void test_padNext_1arg() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).padNext(2).appendValue(DAY_OF_MONTH).appendValue(DAY_OF_WEEK);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)Pad(Value(ISO.DayOfMonth),2)Value(ISO.DayOfWeek)");
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_padNext_1arg_invalidWidth() throws Exception {
        builder.padNext(0);
    }

    //-----------------------------------------------------------------------
    public void test_padNext_2arg_dash() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).padNext(2, '-').appendValue(DAY_OF_MONTH).appendValue(DAY_OF_WEEK);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)Pad(Value(ISO.DayOfMonth),2,'-')Value(ISO.DayOfWeek)");
    }

    @Test(expectedExceptions=IllegalArgumentException.class)
    public void test_padNext_2arg_invalidWidth() throws Exception {
        builder.padNext(0, '-');
    }

    //-----------------------------------------------------------------------
    public void test_padOptional() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).padNext(5).optionalStart().appendValue(DAY_OF_MONTH).optionalEnd().appendValue(DAY_OF_WEEK);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)Pad([Value(ISO.DayOfMonth)],5)Value(ISO.DayOfWeek)");
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    public void test_optionalStart_noEnd() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().appendValue(DAY_OF_MONTH).appendValue(DAY_OF_WEEK);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)[Value(ISO.DayOfMonth)Value(ISO.DayOfWeek)]");
    }

    public void test_optionalStart2_noEnd() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().appendValue(DAY_OF_MONTH).optionalStart().appendValue(DAY_OF_WEEK);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)[Value(ISO.DayOfMonth)[Value(ISO.DayOfWeek)]]");
    }

    public void test_optionalStart_doubleStart() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().optionalStart().appendValue(DAY_OF_MONTH);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)[[Value(ISO.DayOfMonth)]]");
    }

    //-----------------------------------------------------------------------
    public void test_optionalEnd() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().appendValue(DAY_OF_MONTH).optionalEnd().appendValue(DAY_OF_WEEK);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)[Value(ISO.DayOfMonth)]Value(ISO.DayOfWeek)");
    }

    public void test_optionalEnd2() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().appendValue(DAY_OF_MONTH)
            .optionalStart().appendValue(DAY_OF_WEEK).optionalEnd().appendValue(DAY_OF_MONTH).optionalEnd();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)[Value(ISO.DayOfMonth)[Value(ISO.DayOfWeek)]Value(ISO.DayOfMonth)]");
    }

    public void test_optionalEnd_doubleStartSingleEnd() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().optionalStart().appendValue(DAY_OF_MONTH).optionalEnd();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)[[Value(ISO.DayOfMonth)]]");
    }

    public void test_optionalEnd_doubleStartDoubleEnd() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().optionalStart().appendValue(DAY_OF_MONTH).optionalEnd().optionalEnd();
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)[[Value(ISO.DayOfMonth)]]");
    }

    public void test_optionalStartEnd_immediateStartEnd() throws Exception {
        builder.appendValue(MONTH_OF_YEAR).optionalStart().optionalEnd().appendValue(DAY_OF_MONTH);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), "Value(ISO.MonthOfYear)Value(ISO.DayOfMonth)");
    }

    @Test(expectedExceptions=IllegalStateException.class)
    public void test_optionalEnd_noStart() throws Exception {
        builder.optionalEnd();
    }

    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    //-----------------------------------------------------------------------
    @DataProvider(name="validPatterns")
    Object[][] dataValid() {
        return new Object[][] {
            {"'a'", "'a'"},
            {"''", "''"},
            {"'!'", "'!'"},
            {"!", "'!'"},
            
            {"'hello_people,][)('", "'hello_people,][)('"},
            {"'hi'", "'hi'"},
            {"'yyyy'", "'yyyy'"},
            {"''''", "''"},
            {"'o''clock'", "'o''clock'"},
            
            {"y", "Value(ISO.Year)"},
            {"yy", "ReducedValue(ISO.Year,2,2000)"},
            {"yyy", "Value(ISO.Year,3,19,NORMAL)"},
            {"yyyy", "Value(ISO.Year,4,19,EXCEEDS_PAD)"},
            {"yyyyy", "Value(ISO.Year,5,19,EXCEEDS_PAD)"},
            
            {"Y", "Value(ISO.WeekBasedYear)"},
            {"YY", "ReducedValue(ISO.WeekBasedYear,2,2000)"},
            {"YYY", "Value(ISO.WeekBasedYear,3,19,NORMAL)"},
            {"YYYY", "Value(ISO.WeekBasedYear,4,19,EXCEEDS_PAD)"},
            {"YYYYY", "Value(ISO.WeekBasedYear,5,19,EXCEEDS_PAD)"},
            
            {"Q", "Value(ISO.QuarterOfYear)"},
            {"QQ", "Value(ISO.QuarterOfYear,2)"},
            {"QQQ", "Text(ISO.QuarterOfYear,SHORT)"},
            {"QQQQ", "Text(ISO.QuarterOfYear)"},
            {"QQQQQ", "Text(ISO.QuarterOfYear,NARROW)"},
            
            {"M", "Value(ISO.MonthOfYear)"},
            {"MM", "Value(ISO.MonthOfYear,2)"},
            {"MMM", "Text(ISO.MonthOfYear,SHORT)"},
            {"MMMM", "Text(ISO.MonthOfYear)"},
            {"MMMMM", "Text(ISO.MonthOfYear,NARROW)"},
            
            {"q", "Value(ISO.MonthOfQuarter)"},
            {"qq", "Value(ISO.MonthOfQuarter,2)"},
            {"qqq", "Value(ISO.MonthOfQuarter,3)"},
            
            {"w", "Value(ISO.WeekOfWeekBasedYear)"},
            {"ww", "Value(ISO.WeekOfWeekBasedYear,2)"},
            {"www", "Value(ISO.WeekOfWeekBasedYear,3)"},
            
            {"D", "Value(ISO.DayOfYear)"},
            {"DD", "Value(ISO.DayOfYear,2)"},
            {"DDD", "Value(ISO.DayOfYear,3)"},
            
            {"d", "Value(ISO.DayOfMonth)"},
            {"dd", "Value(ISO.DayOfMonth,2)"},
            {"ddd", "Value(ISO.DayOfMonth,3)"},
            
            {"F", "Value(ISO.WeekOfMonth)"},
            {"FF", "Value(ISO.WeekOfMonth,2)"},
            {"FFF", "Value(ISO.WeekOfMonth,3)"},
            
            {"E", "Value(ISO.DayOfWeek)"},
            {"EE", "Value(ISO.DayOfWeek,2)"},
            {"EEE", "Text(ISO.DayOfWeek,SHORT)"},
            {"EEEE", "Text(ISO.DayOfWeek)"},
            {"EEEEE", "Text(ISO.DayOfWeek,NARROW)"},
            
            {"a", "Text(ISO.AmPmOfDay,SHORT)"},
            {"aa", "Text(ISO.AmPmOfDay,SHORT)"},
            {"aaa", "Text(ISO.AmPmOfDay,SHORT)"},
            {"aaaa", "Text(ISO.AmPmOfDay)"},
            {"aaaaa", "Text(ISO.AmPmOfDay,NARROW)"},
            
            {"H", "Value(ISO.HourOfDay)"},
            {"HH", "Value(ISO.HourOfDay,2)"},
            {"HHH", "Value(ISO.HourOfDay,3)"},
            
            {"K", "Value(ISO.HourOfAmPm)"},
            {"KK", "Value(ISO.HourOfAmPm,2)"},
            {"KKK", "Value(ISO.HourOfAmPm,3)"},
            
            {"k", "Value(ISO.ClockHourOfDay)"},
            {"kk", "Value(ISO.ClockHourOfDay,2)"},
            {"kkk", "Value(ISO.ClockHourOfDay,3)"},
            
            {"h", "Value(ISO.ClockHourOfAmPm)"},
            {"hh", "Value(ISO.ClockHourOfAmPm,2)"},
            {"hhh", "Value(ISO.ClockHourOfAmPm,3)"},
            
            {"m", "Value(ISO.MinuteOfHour)"},
            {"mm", "Value(ISO.MinuteOfHour,2)"},
            {"mmm", "Value(ISO.MinuteOfHour,3)"},
            
            {"s", "Value(ISO.SecondOfMinute)"},
            {"ss", "Value(ISO.SecondOfMinute,2)"},
            {"sss", "Value(ISO.SecondOfMinute,3)"},
            
            {"S", "Value(ISO.MilliOfSecond)"},
            {"SS", "Value(ISO.MilliOfSecond,2)"},
            {"SSS", "Value(ISO.MilliOfSecond,3)"},
            
            {"A", "Value(ISO.MilliOfDay)"},
            {"AA", "Value(ISO.MilliOfDay,2)"},
            {"AAA", "Value(ISO.MilliOfDay,3)"},
            
            {"n", "Value(ISO.NanoOfSecond)"},
            {"nn", "Value(ISO.NanoOfSecond,2)"},
            {"nnn", "Value(ISO.NanoOfSecond,3)"},
            
            {"N", "Value(ISO.NanoOfDay)"},
            {"NN", "Value(ISO.NanoOfDay,2)"},
            {"NNN", "Value(ISO.NanoOfDay,3)"},
            
            {"z", "ZoneText(SHORT)"},
            {"zz", "ZoneText(SHORT)"},
            {"zzz", "ZoneText(SHORT)"},
            {"zzzz", "ZoneText(FULL)"},
            {"zzzzz", "ZoneText(FULL)"},
            
            {"I", "ZoneId()"},
            {"II", "ZoneId()"},
            {"III", "ZoneId()"},
            {"IIII", "ZoneId()"},
            {"IIIII", "ZoneId()"},
            
            {"Z", "Offset('+0000',+HHMM)"},  // SimpleDateFormat compatible
            {"ZZ", "Offset('+0000',+HHMM)"},
            {"ZZZ", "Offset('+00:00',+HH:MM)"},
            
            {"X", "Offset('Z',+HH)"},
            {"XX", "Offset('Z',+HHMM)"},
            {"XXX", "Offset('Z',+HH:MM)"},
            {"XXXX", "Offset('Z',+HHMMss)"},
            {"XXXXX", "Offset('Z',+HH:MM:ss)"},
            
            {"ppH", "Pad(Value(ISO.HourOfDay),2)"},
            {"pppDD", "Pad(Value(ISO.DayOfYear,2),3)"},
            {"pppffn", "Pad(Fraction(ISO.NanoOfSecond,1,9),3)"},
            
            {"ssfn", "Value(ISO.SecondOfMinute,2)Fraction(ISO.NanoOfSecond,1,1)"},
            {"ssfnn", "Value(ISO.SecondOfMinute,2)Fraction(ISO.NanoOfSecond,2,2)"},
            {"ssfnnn", "Value(ISO.SecondOfMinute,2)Fraction(ISO.NanoOfSecond,3,3)"},
            
            {"ssffn", "Value(ISO.SecondOfMinute,2)Fraction(ISO.NanoOfSecond,1,9)"},
            {"ssffnn", "Value(ISO.SecondOfMinute,2)Fraction(ISO.NanoOfSecond,2,9)"},
            {"ssffnnn", "Value(ISO.SecondOfMinute,2)Fraction(ISO.NanoOfSecond,3,9)"},
            
            {"mmfs", "Value(ISO.MinuteOfHour,2)Fraction(ISO.SecondOfMinute,1,1)"},
            {"mmfss", "Value(ISO.MinuteOfHour,2)Fraction(ISO.SecondOfMinute,2,2)"},
            {"mmfsss", "Value(ISO.MinuteOfHour,2)Fraction(ISO.SecondOfMinute,3,3)"},
            
            {"mmffs", "Value(ISO.MinuteOfHour,2)Fraction(ISO.SecondOfMinute,1,9)"},
            {"mmffss", "Value(ISO.MinuteOfHour,2)Fraction(ISO.SecondOfMinute,2,9)"},
            {"mmffsss", "Value(ISO.MinuteOfHour,2)Fraction(ISO.SecondOfMinute,3,9)"},
            
            {"fH", "Fraction(ISO.HourOfDay,1,1)"},
            {"fK", "Fraction(ISO.HourOfAmPm,1,1)"},
            {"fm", "Fraction(ISO.MinuteOfHour,1,1)"},
            {"fs", "Fraction(ISO.SecondOfMinute,1,1)"},
            {"fS", "Fraction(ISO.MilliOfSecond,1,1)"},
            {"fA", "Fraction(ISO.MilliOfDay,1,1)"},
            {"fn", "Fraction(ISO.NanoOfSecond,1,1)"},
            {"fN", "Fraction(ISO.NanoOfDay,1,1)"},
            
            {"yyyy[-MM[-dd", "Value(ISO.Year,4,19,EXCEEDS_PAD)['-'Value(ISO.MonthOfYear,2)['-'Value(ISO.DayOfMonth,2)]]"},
            {"yyyy[-MM[-dd]]", "Value(ISO.Year,4,19,EXCEEDS_PAD)['-'Value(ISO.MonthOfYear,2)['-'Value(ISO.DayOfMonth,2)]]"},
            {"yyyy[-MM[]-dd]", "Value(ISO.Year,4,19,EXCEEDS_PAD)['-'Value(ISO.MonthOfYear,2)'-'Value(ISO.DayOfMonth,2)]"},
            
            {"yyyy-MM-dd'T'HH:mm:ss.SSS", "Value(ISO.Year,4,19,EXCEEDS_PAD)'-'Value(ISO.MonthOfYear,2)'-'Value(ISO.DayOfMonth,2)" +
                "'T'Value(ISO.HourOfDay,2)':'Value(ISO.MinuteOfHour,2)':'Value(ISO.SecondOfMinute,2)'.'Value(ISO.MilliOfSecond,3)"},
        };
    }

    @Test(dataProvider="validPatterns")
    public void test_appendPattern_valid(String input, String expected) throws Exception {
        builder.appendPattern(input);
        DateTimeFormatter f = builder.toFormatter();
        assertEquals(f.toString(), expected);
    }

    //-----------------------------------------------------------------------
    @DataProvider(name="invalidPatterns")
    Object[][] dataInvalid() {
        return new Object[][] {
            {"'"},
            {"'hello"},
            {"'hel''lo"},
            {"'hello''"},
            {"]"},
            {"yyyy]"},
            {"yyyy]MM"},
            {"yyyy[MM]]"},
            
            {"MMMMMM"},
            {"QQQQQQ"},
            {"EEEEEE"},
            {"aaaaaa"},
            {"ZZZZ"},
            {"XXXXXX"},
            
            {"RO"},
            
            {"p"},
            {"pp"},
            {"p:"},
            
            {"f"},
            {"ff"},
            {"f:"},
            {"fy"},
            {"fa"},
            {"fM"},
        };
    }

    @Test(dataProvider="invalidPatterns", expectedExceptions=IllegalArgumentException.class)
    public void test_appendPattern_invalid(String input) throws Exception {
        try {
            builder.appendPattern(input);
        } catch (IllegalArgumentException ex) {
            throw ex;
        }
    }

}
