package miaad.rh.administration.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import miaad.rh.administration.dto.AttestationInfoDto;

import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.util.Calendar;

public class AttestationGeneratorService {
    public byte[] generateAttestation(AttestationInfoDto attestationInfoDto) throws DocumentException {
        String nomPrenom = attestationInfoDto.getNomPrenom();
        String cin = attestationInfoDto.getCin();
        String poste = attestationInfoDto.getPoste();
        Date dateOccupation = attestationInfoDto.getDateOccupation();
        String nomEtablissement = attestationInfoDto.getNomEtablissement();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);

        // Ouverture du document
        document.open();

        // Création d'une table pour placer les logos sur la même ligne
        PdfPTable table = new PdfPTable(2); // Deux colonnes pour les deux logos
        table.setWidthPercentage(100); // Pour occuper la largeur complète du document

        // Ajout du logo à gauche
        try {
            Image imgLeft = Image.getInstance("src/main/resources/asset/umi.jpg");
            imgLeft.scaleToFit(150, 50);
            PdfPCell cellLeft = new PdfPCell(imgLeft);
            cellLeft.setBorder(Rectangle.NO_BORDER);
            table.addCell(cellLeft);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Ajout du logo à droite
        try {
            Image imgRight = Image.getInstance("src/main/resources/asset/fsm.png");
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
        Paragraph title = new Paragraph("Attestation de Travail", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Trois tabulations entre le titre et le contenu
        document.add(new Paragraph("\n\n\n"));

        // Contenu de l'attestation
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Paragraph content = new Paragraph();
        content.add(new Phrase("Je soussigné, responsable du département informatique au sein de "));
        content.add(new Phrase(nomEtablissement, boldFont));
        content.add(new Phrase(" atteste par la présente que Monsieur/Madame "));
        content.add(new Phrase(nomPrenom, boldFont));
        content.add(new Phrase(" titulaire de la carte d'identité nationale numéro "));
        content.add(new Phrase(cin, boldFont));
        content.add(new Phrase(" est actuellement employé(e) au sein de notre département de l'informatique depuis le "));
        content.add(new Phrase(dateOccupation.toString(), boldFont));
        content.add(new Phrase(" en qualité de "));
        content.add(new Phrase(poste, boldFont));
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
            Image imgRightbtm = Image.getInstance("src/main/resources/asset/signature.png");
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
