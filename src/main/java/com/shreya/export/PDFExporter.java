package com.shreya.export;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.shreya.model.Note;

import java.util.List;

public class PDFExporter {

    public static void export(List<Note> notes, String filePath) {

        try {

            PdfWriter writer = new PdfWriter(filePath);

            PdfDocument pdf = new PdfDocument(writer);

            Document document = new Document(pdf);

            document.add(new Paragraph("NoteSphere Notes"));
            document.add(new Paragraph("----------------------------"));

            for (Note note : notes) {

                document.add(new Paragraph("Title : " + note.getTitle()));
                document.add(new Paragraph("Category : " + note.getCategory()));
                document.add(new Paragraph("Date : " + note.getDateTime()));
                document.add(new Paragraph("Pinned : " + (note.isPinned() ? "Yes" : "No")));
                document.add(new Paragraph("Content :"));
                document.add(new Paragraph(note.getContent()));
                document.add(new Paragraph("---------------------------------------"));

            }

            document.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}