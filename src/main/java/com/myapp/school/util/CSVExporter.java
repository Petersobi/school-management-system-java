package com.myapp.school.util;

import com.myapp.school.model.Attendance;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVExporter {
    public static void exportAttendance(List<Attendance> records,String path){
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.append("Student,Date,Status\n");
            for (Attendance attendance : records) {
                fileWriter.append(attendance.getStudentName()).append(",");
                fileWriter.append(attendance.getDate().toString()).append(",");
                fileWriter.append(attendance.getStatus().name()).append("\n");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to export CSV" + e);
        }
    }
}
