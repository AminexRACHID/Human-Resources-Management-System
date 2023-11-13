package miaad.rh.stagiaire.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.sql.Date;

public class PDFGeneratorService {
    public byte[] generateAttestation(String stagaireName, Date startDate, Date endDate) throws DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        document.add(new Paragraph("Attestation de Stage"));
        document.add(new Paragraph("Je soussigné, responsable du département informatique, certifie que M/Mme "
                + stagaireName + " a effectué son stage à partir du " + startDate +" au "+endDate));
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
