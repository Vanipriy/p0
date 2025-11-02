package com.payroll.util;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.FileOutputStream;


public class PdfUtil {
    public static void generatePaySlip(String path, String content) throws Exception {
        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream(path));
        doc.open();
        doc.add(new Paragraph(content));
        doc.close();
    }
}