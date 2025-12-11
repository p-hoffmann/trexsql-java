package org.trex;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.*;
import java.util.*;
import org.duckdb.DuckDBAppender;

/**
 * TrexSQL Appender wrapper around DuckDBAppender.
 * Provides bulk data insertion capabilities for TrexSQL.
 */
public class TrexSQLAppender implements AutoCloseable {

    private final DuckDBAppender delegate;

    public TrexSQLAppender(DuckDBAppender delegate) {
        this.delegate = delegate;
    }

    /**
     * Get the underlying DuckDBAppender.
     */
    public DuckDBAppender unwrapDuckDB() {
        return delegate;
    }

    public TrexSQLAppender beginRow() throws SQLException {
        delegate.beginRow();
        return this;
    }

    public TrexSQLAppender endRow() throws SQLException {
        delegate.endRow();
        return this;
    }

    public TrexSQLAppender beginStruct() throws SQLException {
        delegate.beginStruct();
        return this;
    }

    public TrexSQLAppender endStruct() throws SQLException {
        delegate.endStruct();
        return this;
    }

    public TrexSQLAppender beginUnion(String tag) throws SQLException {
        delegate.beginUnion(tag);
        return this;
    }

    public TrexSQLAppender endUnion() throws SQLException {
        delegate.endUnion();
        return this;
    }

    public long flush() throws SQLException {
        return delegate.flush();
    }

    @Override
    public void close() throws SQLException {
        delegate.close();
    }

    public boolean isClosed() throws SQLException {
        return delegate.isClosed();
    }

    // Primitive append methods

    public TrexSQLAppender append(boolean value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(char value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(byte value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(short value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(int value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(long value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(float value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(double value) throws SQLException {
        delegate.append(value);
        return this;
    }

    // Boxed type append methods

    public TrexSQLAppender append(Boolean value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(Character value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(Byte value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(Short value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(Integer value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(Long value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(Float value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(Double value) throws SQLException {
        delegate.append(value);
        return this;
    }

    // BigInteger and BigDecimal

    public TrexSQLAppender appendHugeInt(long lower, long upper) throws SQLException {
        delegate.appendHugeInt(lower, upper);
        return this;
    }

    public TrexSQLAppender append(BigInteger value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender appendDecimal(short value) throws SQLException {
        delegate.appendDecimal(value);
        return this;
    }

    public TrexSQLAppender appendDecimal(int value) throws SQLException {
        delegate.appendDecimal(value);
        return this;
    }

    public TrexSQLAppender appendDecimal(long value) throws SQLException {
        delegate.appendDecimal(value);
        return this;
    }

    public TrexSQLAppender appendDecimal(long lower, long upper) throws SQLException {
        delegate.appendDecimal(lower, upper);
        return this;
    }

    public TrexSQLAppender append(BigDecimal value) throws SQLException {
        delegate.append(value);
        return this;
    }

    // String and UUID

    public TrexSQLAppender append(String value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender appendUUID(long mostSigBits, long leastSigBits) throws SQLException {
        delegate.appendUUID(mostSigBits, leastSigBits);
        return this;
    }

    public TrexSQLAppender append(UUID value) throws SQLException {
        delegate.append(value);
        return this;
    }

    // Date and Time

    public TrexSQLAppender appendEpochDays(int days) throws SQLException {
        delegate.appendEpochDays(days);
        return this;
    }

    public TrexSQLAppender append(LocalDate value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender appendDayMicros(long micros) throws SQLException {
        delegate.appendDayMicros(micros);
        return this;
    }

    public TrexSQLAppender append(LocalTime value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender appendDayMicros(long micros, int offset) throws SQLException {
        delegate.appendDayMicros(micros, offset);
        return this;
    }

    public TrexSQLAppender append(OffsetTime value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender appendEpochSeconds(long seconds) throws SQLException {
        delegate.appendEpochSeconds(seconds);
        return this;
    }

    public TrexSQLAppender appendEpochMillis(long millis) throws SQLException {
        delegate.appendEpochMillis(millis);
        return this;
    }

    public TrexSQLAppender appendEpochMicros(long micros) throws SQLException {
        delegate.appendEpochMicros(micros);
        return this;
    }

    public TrexSQLAppender appendEpochNanos(long nanos) throws SQLException {
        delegate.appendEpochNanos(nanos);
        return this;
    }

    public TrexSQLAppender append(LocalDateTime value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(java.util.Date value) throws SQLException {
        delegate.append(value);
        return this;
    }

    public TrexSQLAppender append(OffsetDateTime value) throws SQLException {
        delegate.append(value);
        return this;
    }

    // Collections and Maps

    public TrexSQLAppender append(Collection<?> collection) throws SQLException {
        delegate.append(collection);
        return this;
    }

    public TrexSQLAppender append(Iterable<?> iter, int count) throws SQLException {
        delegate.append(iter, count);
        return this;
    }

    public TrexSQLAppender append(Iterator<?> iter, int count) throws SQLException {
        delegate.append(iter, count);
        return this;
    }

    public TrexSQLAppender append(Map<?, ?> map) throws SQLException {
        delegate.append(map);
        return this;
    }

    // Null and Default

    public TrexSQLAppender appendNull() throws SQLException {
        delegate.appendNull();
        return this;
    }

    public TrexSQLAppender appendDefault() throws SQLException {
        delegate.appendDefault();
        return this;
    }

    // Array append methods

    public TrexSQLAppender append(boolean[] values) throws SQLException {
        delegate.append(values);
        return this;
    }

    public TrexSQLAppender append(boolean[] values, boolean[] nullMask) throws SQLException {
        delegate.append(values, nullMask);
        return this;
    }

    public TrexSQLAppender append(byte[] values) throws SQLException {
        delegate.append(values);
        return this;
    }

    public TrexSQLAppender append(char[] characters) throws SQLException {
        delegate.append(characters);
        return this;
    }

    public TrexSQLAppender append(short[] values) throws SQLException {
        delegate.append(values);
        return this;
    }

    public TrexSQLAppender append(short[] values, boolean[] nullMask) throws SQLException {
        delegate.append(values, nullMask);
        return this;
    }

    public TrexSQLAppender append(int[] values) throws SQLException {
        delegate.append(values);
        return this;
    }

    public TrexSQLAppender append(int[] values, boolean[] nullMask) throws SQLException {
        delegate.append(values, nullMask);
        return this;
    }

    public TrexSQLAppender append(long[] values) throws SQLException {
        delegate.append(values);
        return this;
    }

    public TrexSQLAppender append(long[] values, boolean[] nullMask) throws SQLException {
        delegate.append(values, nullMask);
        return this;
    }

    public TrexSQLAppender append(float[] values) throws SQLException {
        delegate.append(values);
        return this;
    }

    public TrexSQLAppender append(float[] values, boolean[] nullMask) throws SQLException {
        delegate.append(values, nullMask);
        return this;
    }

    public TrexSQLAppender append(double[] values) throws SQLException {
        delegate.append(values);
        return this;
    }

    public TrexSQLAppender append(double[] values, boolean[] nullMask) throws SQLException {
        delegate.append(values, nullMask);
        return this;
    }

    // Settings

    public boolean getWriteInlinedStrings() {
        return delegate.getWriteInlinedStrings();
    }

    public TrexSQLAppender setWriteInlinedStrings(boolean writeInlinedStrings) {
        delegate.setWriteInlinedStrings(writeInlinedStrings);
        return this;
    }
}
