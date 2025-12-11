package org.trex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import org.duckdb.DuckDBDriver;

/**
 * TrexSQL JDBC Driver - a wrapper around DuckDBDriver that supports
 * the jdbc:trex: URL scheme.
 *
 * <p>Example usage:</p>
 * <pre>
 * Connection conn = DriverManager.getConnection("jdbc:trex:");
 * Connection conn = DriverManager.getConnection("jdbc:trex:/path/to/database.db");
 * </pre>
 */
public class TrexSQLDriver implements java.sql.Driver {

    public static final String TREX_URL_PREFIX = "jdbc:trex:";

    private static final DuckDBDriver delegate = new DuckDBDriver();

    static {
        try {
            DriverManager.registerDriver(new TrexSQLDriver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!acceptsURL(url)) {
            return null;
        }
        // Convert jdbc:trex: to jdbc:duckdb: and delegate
        String duckdbUrl = convertUrl(url);
        Connection conn = delegate.connect(duckdbUrl, info);
        if (conn instanceof org.duckdb.DuckDBConnection) {
            return new TrexSQLConnection((org.duckdb.DuckDBConnection) conn);
        }
        return conn;
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        return url != null && url.startsWith(TREX_URL_PREFIX);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        String duckdbUrl = convertUrl(url);
        return delegate.getPropertyInfo(duckdbUrl, info);
    }

    @Override
    public int getMajorVersion() {
        return delegate.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        return delegate.getMinorVersion();
    }

    @Override
    public boolean jdbcCompliant() {
        return delegate.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return delegate.getParentLogger();
    }

    /**
     * Convert a jdbc:trex: URL to jdbc:duckdb: URL.
     */
    private static String convertUrl(String url) {
        if (url == null) {
            return null;
        }
        if (url.startsWith(TREX_URL_PREFIX)) {
            return DuckDBDriver.DUCKDB_URL_PREFIX + url.substring(TREX_URL_PREFIX.length());
        }
        return url;
    }
}
