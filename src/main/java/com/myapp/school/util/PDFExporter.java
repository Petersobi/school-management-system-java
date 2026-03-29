package com.myapp.school.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.myapp.school.model.Attendance;

import java.io.FileOutputStream;
import java.util.List;

public class PDFExporter {
    public static void exportAttendance(List<Attendance> records,String path,String date){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream(path));
            document.open();

            Font titleFont = new Font(Font.HELVETICA);
            Paragraph title = new Paragraph("Attendance Report",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("Date: " + date));
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Student");
            table.addCell("Status");

            for (Attendance attendance: records) {
                table.addCell(attendance.getStudentName());
                table.addCell(attendance.getStatus().name());
            }
            document.add(table);
            document.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
