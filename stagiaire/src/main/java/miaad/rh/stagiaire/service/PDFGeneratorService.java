package miaad.rh.stagiaire.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Calendar;


public class PDFGeneratorService {

    public byte[] generateAttestation(String stagiaireName, Date startDate, Date endDate) throws DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);

//        System.out.println(endDate);
//
        String[] partsstartDate = startDate.toString().split(" ");
//        System.out.println(partsstartDate);
        String[] partsendDate = endDate.toString().split(" ");
        String startDateN = partsstartDate[0] + " " + partsstartDate[2] + " " + partsstartDate[1] + " " + partsstartDate[5];
        String endDateN = partsendDate[0] + " " + partsendDate[2] + " " + partsendDate[1] + " " + partsendDate[5];


        // Ouverture du document
        document.open();

        // Création d'une table pour placer les logos sur la même ligne
        PdfPTable table = new PdfPTable(2); // Deux colonnes pour les deux logos
        table.setWidthPercentage(100); // Pour occuper la largeur complète du document

        // Ajout du logo à gauche
        try {
            Image imgLeft = Image.getInstance("stagiaire/src/main/resources/asset/umi.jpg");            imgLeft.scaleToFit(150, 50); // Ajustez la taille du logo selon vos besoins
            PdfPCell cellLeft = new PdfPCell(imgLeft);
            cellLeft.setBorder(Rectangle.NO_BORDER);
            table.addCell(cellLeft);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ajout du logo à droite
        try {
            Image imgRight = Image.getInstance("stagiaire/src/main/resources/asset/fsm.png");
            imgRight.scaleToFit(150, 50); // Ajustez la taille du logo selon vos besoins
            PdfPCell cellRight = new PdfPCell(imgRight);
            cellRight.setBorder(Rectangle.NO_BORDER);
            cellRight.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellRight);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ajout de la table au document
        document.add(table);

        // Tabulation entre la table et le titre
        document.add(new Paragraph("\n\n"));

        // Titre centré et en gras
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
        Paragraph title = new Paragraph("Attestation de Stage", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Trois tabulations entre le titre et le contenu
        document.add(new Paragraph("\n\n\n"));

        // Contenu de l'attestation
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Paragraph content = new Paragraph();
        content.add(new Phrase("Je soussigné, responsable du département informatique, certifie que M/Mme "));
        content.add(new Phrase(stagiaireName, boldFont));
        content.add(new Phrase(" a effectué son stage au département d'Informatique de la Faculté des Sciences de Meknès du "));
        content.add(new Phrase(startDateN, boldFont));
        content.add(new Phrase(" au "));
        content.add(new Phrase(endDateN, boldFont));
        content.add(new Phrase("."));
        content.add((new Phrase("\n\n")));
        content.add((new Phrase("Cette attestation est délivrée à l'intéressé(e) afin de servir et valoir ce que de droit.\n\n")));
        content.add((new Phrase("Nous vous prions d'agréer, Madame, Monsieur, nos salutations distinguées.\n\n")));
        content.add((new Phrase("Fait à MEKNES,\n")));
        Calendar currentDate = Calendar.getInstance(); // Obtention de la date actuelle
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int month = currentDate.get(Calendar.MONTH) + 1; // Les mois commencent à 0, donc on ajoute 1
        int year = currentDate.get(Calendar.YEAR);
        String currentDateText = String.format("%02d/%02d/%04d", day, month, year); // Formatage de la date
        content.add((new Phrase("Le  " + currentDateText)));
        document.add(content);

        document.add(new Paragraph("\n\n\n"));

        // Ajout du nom du responsable en bas à droite
        Font responsableFont = new Font(Font.FontFamily.TIMES_ROMAN, 12);
        Paragraph responsable = new Paragraph("Responsable: MOUL Chy", responsableFont);
        responsable.setAlignment(Element.ALIGN_RIGHT);
        document.add(responsable);

        document.add(new Paragraph("\n"));

        // Ajout de l'image contenant la signature du responsable en bas à droite
        try {
            Image imgRightbtm = Image.getInstance("stagiaire/src/main/resources/asset/signature.png");
            imgRightbtm.scaleToFit(150, 50);
            imgRightbtm.setAlignment(Element.ALIGN_RIGHT);
            document.add(imgRightbtm);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Fermeture du document
        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
