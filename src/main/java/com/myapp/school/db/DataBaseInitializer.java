package com.myapp.school.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInitializer {
    public static void initialize(){
        String sql = """
                CREATE TABLE IF NOT EXISTS students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                class_name TEXT NOT NULL
                );
                """;
        try (
            Connection conn = DataBaseConnection.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.execute(sql);
            System.out.println("Students table ready");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Database",e);
        }

        String createTeacherTable = """
                CREATE TABLE IF NOT EXISTS teachers (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL,
                subject TEXT NOT NULL
                );
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                Statement stmt = conn.createStatement()){
            stmt.execute(createTeacherTable);
            System.out.println("Teachers table ready");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Database",e);
        }

        String createTeacherStudentTable = """
                CREATE TABLE IF NOT EXISTS teachers_students (
                teacher_id INTEGER NOT NULL,
                student_id INTEGER NOT NULL,
                PRIMARY KEY (teacher_id, student_id)
                FOREIGN KEY (teacher_id) REFERENCES teachers(id),
                FOREIGN KEY (student_id) REFERENCES students(id)
                );
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                Statement stmt = conn.createStatement()
                ) {
            stmt.execute(createTeacherStudentTable);
            System.out.println("Teacher Student Table ready");
        } catch (Exception e) {
            throw new RuntimeException("failed to create Teacher Student Table",e);
        }
        String createAttendanceTable = """
                CREATE TABLE IF NOT EXISTS attendance (
                id INTEGER PRIMARY KEY AUTOINCREMENT ,
                teacher_id INTEGER NOT NULL ,
                student_id INTEGER NOT NULL ,
                date TEXT NOT NULL ,
                status TEXT NOT NULL ,
                UNIQUE (teacher_id, student_id, date),
                FOREIGN KEY (teacher_id) REFERENCES teachers(id),
                FOREIGN KEY (student_id) REFERENCES students(id))
               
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                Statement stmt = conn.createStatement()){
            stmt.execute(createAttendanceTable);
            System.out.println("Attendance table ready");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Attendance Database",e);
        }
        String createUserTable = """
                CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL);
                """;
        try (Connection connection = DataBaseConnection.getConnection();
        Statement st = connection.createStatement()) {
            st.execute(createUserTable);
            System.out.println("Users Table Created");

        } catch (SQLException e) {
            throw new RuntimeException("Could not create Users Table" +e);
        }
        String createUserRolesTable = """
                CREATE TABLE IF NOT EXISTS user_roles(
                user_id INTEGER NOT NULL,
                role TEXT NOT NULL,
                PRIMARY KEY (user_id, role)
                FOREIGN KEY (user_id) REFERENCES users(id))
                """;
        try (Connection connection = DataBaseConnection.getConnection();
             Statement stm = connection.createStatement()) {
            stm.execute(createUserRolesTable);
            System.out.println("User Roles created");
        } catch (SQLException e) {
            throw new RuntimeException("Could not create User Roles table"+e);
        }
        String createAdminTable = """
                CREATE TABLE IF NOT EXISTS admins (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                first_name TEXT NOT NULL,
                last_name TEXT NOT NULL
                );
                """;
        try (
                Connection conn = DataBaseConnection.getConnection();
                Statement stmt = conn.createStatement()){
            stmt.execute(createAdminTable);
            System.out.println("Admins table ready");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Admin Database",e);
        }
    }

    }
