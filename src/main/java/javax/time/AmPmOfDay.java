/*
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
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
package javax.time;

import java.util.Calendar;

import javax.time.calendrical.CalendricalAdjuster;
import javax.time.calendrical.CalendricalObject;
import javax.time.calendrical.DateTimeBuilder;
import javax.time.calendrical.LocalTimeField;
import javax.time.calendrical.TimeAdjuster;

/**
 * A half-day before or after midday, with the values 'AM' and 'PM'.
 * <p>
 * {@code AmPmOfDay} is an enum representing the half-day concepts of AM and PM.
 * AM is defined as from 00:00 to 11:59, while PM is defined from 12:00 to 23:59.
 * <p>
 * All date-time fields have an {@code int} value.
 * The {@code int} value follows {@link Calendar}, assigning 0 to AM and 1 to PM.
 * It is recommended that applications use the enum rather than the {@code int} value
 * to ensure code clarity.
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code AmPmOfDay}.
 * Use {@code getValue()} instead.</b>
 * <p>
 * This enum represents a common concept that is found in many calendar systems.
 * As such, this enum may be used by any calendar system that has the AM/PM
 * concept defined exactly equivalent to the ISO calendar system.
 * 
 * <h4>Implementation notes</h4>
 * This is an immutable and thread-safe enum.
 */
public enum AmPmOfDay implements CalendricalObject, TimeAdjuster {

    /**
     * The singleton instance for the morning, AM - ante meridiem.
     * This has the numeric value of {@code 0}.
     */
    AM,
    /**
     * The singleton instance for the afternoon, PM - post meridiem.
     * This has the numeric value of {@code 1}.
     */
    PM;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code AmPmOfDay} from an {@code int} value.
     * <p>
     * {@code AmPmOfDay} is an enum representing before and after midday.
     * This factory allows the enum to be obtained from the {@code int} value.
     * The {@code int} value follows {@link Calendar}, assigning 0 to AM and 1 to PM.
     *
     * @param amPmOfDay  the AM/PM value to represent, from 0 (AM) to 1 (PM)
     * @return the AM/PM, not null
     * @throws CalendricalException if the value is invalid
     */
    public static AmPmOfDay of(int amPmOfDay) {
        switch (amPmOfDay) {
            case 0: return AM;
            case 1: return PM;
            default: throw new CalendricalException("Invalid value for AM/PM: " + amPmOfDay);
        }
    }

    /**
     * Obtains an instance of {@code AmPmOfDay} from an hour-of-day.
     * <p>
     * {@code AmPmOfDay} is an enum representing before and after midday.
     * This factory allows the enum to be obtained from the hour-of-day value, from 0 to 23.
     *
     * @param hourOfDay  the hour-of-day to extract from, from 0 to 23
     * @return the AM/PM, not null
     * @throws CalendricalException if the hour-of-day is invalid
     */
    public static AmPmOfDay ofHourOfDay(int hourOfDay) {
        LocalTimeField.HOUR_OF_DAY.checkValidValue(hourOfDay);
        return hourOfDay < 12 ? AM : PM;
    }

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code AmPmOfDay} from a calendrical.
     * <p>
     * A calendrical represents some form of date and time information.
     * This factory converts the arbitrary calendrical to an instance of {@code AmPmOfDay}.
     * 
     * @param calendrical  the calendrical to convert, not null
     * @return the AM/PM, not null
     * @throws CalendricalException if unable to convert to a {@code AmPmOfDay}
     */
    public static AmPmOfDay from(CalendricalObject calendrical) {
        if (calendrical instanceof AmPmOfDay) {
            return (AmPmOfDay) calendrical;
        }
        return of((int) LocalTimeField.AMPM_OF_DAY.getValueFrom(calendrical));
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the AM/PM {@code int} value.
     * <p>
     * The values are numbered following {@link Calendar}, assigning 0 to AM and 1 to PM.
     *
     * @return the AM/PM value, from 0 (AM) to 1 (PM)
     */
    public int getValue() {
        return ordinal();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the textual representation, such as 'AM' or 'PM'.
     * <p>
     * This method is notionally specific to {@link ISOChronology} as it uses
     * the AM/PM rule to obtain the text. However, it is expected that
     * the text will be equivalent for all AM/PM rules, thus this aspect
     * of the implementation should be irrelevant to applications.
     * <p>
     * If no textual mapping is found then the {@link #getValue() numeric value} is returned.
     *
     * @param locale  the locale to use, not null
     * @return the short text value of the AM/PM, not null
     */
//    public String getText(TextStyle style, Locale locale) {
//        return AMPM_OF_DAY.getText(getValue(), style, locale);
//    }

    //-----------------------------------------------------------------------
    /**
     * Extracts date-time information in a generic way.
     * <p>
     * This method exists to fulfill the {@link CalendricalObject} interface.
     * This implementation returns the following types:
     * <ul>
     * <li>AmPmOfDay
     * <li>DateTimeBuilder, using {@link LocalTimeField#AMPM_OF_DAY}
     * <li>Class, returning {@code AmPmOfDay}
     * </ul>
     * 
     * @param <R> the type to extract
     * @param type  the type to extract, null returns null
     * @return the extracted object, null if unable to extract
     */
    @SuppressWarnings("unchecked")
    @Override
    public <R> R extract(Class<R> type) {
        if (type == DateTimeBuilder.class) {
            return (R) new DateTimeBuilder(LocalTimeField.AMPM_OF_DAY, getValue());
        } else if (type == Class.class) {
            return (R) AmPmOfDay.class;
        } else if (type == AmPmOfDay.class) {
            return (R) this;
        }
        return null;
    }

    @Override
    public AmPmOfDay with(CalendricalAdjuster adjuster) {
        if (adjuster instanceof AmPmOfDay) {
            return ((AmPmOfDay) adjuster);
        }
        DateTimes.checkNotNull(adjuster, "Adjuster must not be null");
        throw new CalendricalException("Unable to adjust AmPmOfDay with " + adjuster.getClass().getSimpleName());
    }

    @Override
    public LocalTime adjustTime(LocalTime time) {
        return time.plusHours(12 * (ofHourOfDay(time.getHourOfDay()).getValue() - getValue()));
    }

}
