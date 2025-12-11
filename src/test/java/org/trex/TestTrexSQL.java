package org.trex;

import static org.duckdb.test.Assertions.*;
import static org.duckdb.test.Runner.runTests;

import java.sql.*;
import org.duckdb.DuckDBAppender;
import org.duckdb.DuckDBConnection;

public class TestTrexSQL {

    static {
        try {
            Class.forName("org.trex.TrexSQLDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String TREX_JDBC_URL = "jdbc:trex:";

    public static void main(String[] args) throws Exception {
        System.exit(runTests(args, TestTrexSQL.class));
    }

    public static void test_trex_driver_connection() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL)) {
            assertTrue(conn != null);
            assertTrue(conn instanceof TrexSQLConnection);
            assertFalse(conn.isClosed());
        }
    }

    public static void test_trex_driver_accepts_url() throws Exception {
        TrexSQLDriver driver = new TrexSQLDriver();
        assertTrue(driver.acceptsURL("jdbc:trex:"));
        assertTrue(driver.acceptsURL("jdbc:trex:/path/to/db.db"));
        assertFalse(driver.acceptsURL("jdbc:duckdb:"));
        assertFalse(driver.acceptsURL("jdbc:mysql:"));
    }

    public static void test_trex_connection_query() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL); Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 42 AS answer")) {
            assertTrue(rs.next());
            assertEquals(42, rs.getInt("answer"));
            assertFalse(rs.next());
        }
    }

    public static void test_trex_connection_unwrap() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL)) {
            TrexSQLConnection trexConn = (TrexSQLConnection) conn;
            DuckDBConnection duckdbConn = trexConn.unwrapDuckDB();
            assertTrue(duckdbConn != null);
            assertFalse(duckdbConn.isClosed());
        }
    }

    public static void test_trex_connection_duplicate() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL)) {
            TrexSQLConnection trexConn = (TrexSQLConnection) conn;
            try (TrexSQLConnection duplicate = trexConn.duplicate()) {
                assertTrue(duplicate != null);
                assertFalse(duplicate.isClosed());
            }
        }
    }

    public static void test_trex_appender() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL); Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE test_table (id INTEGER, name VARCHAR)");

            TrexSQLConnection trexConn = (TrexSQLConnection) conn;
            try (TrexSQLAppender appender = trexConn.createAppender("test_table")) {
                assertTrue(appender != null);
                assertFalse(appender.isClosed());

                appender.beginRow().append(1).append("Alice").endRow();

                appender.beginRow().append(2).append("Bob").endRow();

                appender.flush();
            }

            try (ResultSet rs = stmt.executeQuery("SELECT * FROM test_table ORDER BY id")) {
                assertTrue(rs.next());
                assertEquals(1, rs.getInt("id"));
                assertEquals("Alice", rs.getString("name"));

                assertTrue(rs.next());
                assertEquals(2, rs.getInt("id"));
                assertEquals("Bob", rs.getString("name"));

                assertFalse(rs.next());
            }
        }
    }

    public static void test_trex_appender_unwrap() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL); Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE test_unwrap (value INTEGER)");

            TrexSQLConnection trexConn = (TrexSQLConnection) conn;
            try (TrexSQLAppender appender = trexConn.createAppender("test_unwrap")) {
                DuckDBAppender duckdbAppender = appender.unwrapDuckDB();
                assertTrue(duckdbAppender != null);
            }
        }
    }

    public static void test_trex_prepared_statement() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement("SELECT ? + 1 AS result")) {
            pstmt.setInt(1, 41);
            try (ResultSet rs = pstmt.executeQuery()) {
                assertTrue(rs.next());
                assertEquals(42, rs.getInt("result"));
            }
        }
    }

    public static void test_trex_transaction() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL); Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false);

            stmt.execute("CREATE TABLE test_tx (value INTEGER)");
            stmt.execute("INSERT INTO test_tx VALUES (1)");

            conn.commit();

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_tx")) {
                assertTrue(rs.next());
                assertEquals(1L, rs.getLong(1));
            }

            stmt.execute("INSERT INTO test_tx VALUES (2)");
            conn.rollback();

            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM test_tx")) {
                assertTrue(rs.next());
                assertEquals(1L, rs.getLong(1));
            }
        }
    }

    public static void test_trex_metadata() throws Exception {
        try (Connection conn = DriverManager.getConnection(TREX_JDBC_URL)) {
            DatabaseMetaData meta = conn.getMetaData();
            assertTrue(meta != null);
            // Metadata still shows DuckDB since we're wrapping it
            assertTrue(meta.getDatabaseProductName().contains("DuckDB"));
        }
    }
}
