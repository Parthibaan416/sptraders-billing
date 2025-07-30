package com.sptraders.sptraders_billing.service;


import com.sptraders.sptraders_billing.entity.CustomerEntry;
import com.sptraders.sptraders_billing.repository.CustomerEntryRepository;
import com.sptraders.sptraders_billing.util.ApplicationUtillity;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class PDFService {

    private CustomerEntryRepository customerEntryRepository;

    @Autowired
    public PDFService(CustomerEntryRepository theCustomerEntryRepository)
    {
        this.customerEntryRepository = theCustomerEntryRepository;
    }

    private static final float MARGIN = 40;
    private static final float ROW_HEIGHT = 20;
    private static final float[] COL_WIDTHS = {30, 70, 120, 120, 70, 50};

    PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    PDType1Font fontBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

    public void generatePdf(OutputStream out, String customerName, Date from, Date to) {
        List<CustomerEntry> entries = customerEntryRepository.findByCustomerAndDateBetween(customerName, from, to);

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDPageContentStream cs = new PDPageContentStream(doc, page);
            float y = PDRectangle.A4.getHeight() - MARGIN;

            y = drawCenteredText(cs, "SP TRADERS", 18, y);
            y = drawCenteredText(cs, "(DOMESTIC & INTERNATIONAL COURIER PACKERS & MOVERS)", 11, y - 5);
            y = drawCenteredText(cs, "(MR. S.YOGESHWARAN (PROPRIETOR) MOB NO : 8807187119 / 8682008073)", 10, y - 5);
            y = drawCenteredText(cs, "CUSTOMER NAME : " + customerName.toUpperCase(), 12, y - 10);
            y -= 30;

            drawTableHeader(cs, y);
            y -= ROW_HEIGHT;

            int serial = 1;
            for (CustomerEntry entry : entries) {
                if (y < MARGIN + ROW_HEIGHT * 3) {
                    cs.close();
                    page = new PDPage(PDRectangle.A4);
                    doc.addPage(page);
                    cs = new PDPageContentStream(doc, page);
                    y = PDRectangle.A4.getHeight() - MARGIN;
                    y = drawCenteredText(cs, "SP TRADERS", 18, y);
                    y = drawCenteredText(cs, "CUSTOMER NAME : " + customerName.toUpperCase(), 12, y - 20);
                    y -= 30;
                    drawTableHeader(cs, y);
                    y -= ROW_HEIGHT;
                }

                drawTableRow(cs, y, String.valueOf(serial++), ApplicationUtillity.dateFormat(entry.getDate()), entry.getAwbNo(),
                        entry.getDestination(), String.valueOf(entry.getWeight()), String.valueOf(entry.getTotal()));
                y -= ROW_HEIGHT;
            }

            cs.close();
            doc.save(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void drawTableHeader(PDPageContentStream cs, float y) throws IOException {
        String[] headers = {"S.NO", "DATE", "AWB NO", "DESTINATION", "WEIGHT", "PRICE"};
        drawRow(cs, y, headers, true);
    }

    private void drawTableRow(PDPageContentStream cs, float y, String... cols) throws IOException {
        drawRow(cs, y, cols, false);
    }

    private void drawRow(PDPageContentStream cs, float y, String[] cols, boolean bold) throws IOException {
        float x = MARGIN;
        cs.setFont(bold ? fontBold : font, 10);
        cs.setStrokingColor(Color.BLACK);
        cs.setLineWidth(0.5f);

        for (int i = 0; i < cols.length; i++) {
            cs.addRect(x, y, COL_WIDTHS[i], ROW_HEIGHT);
            cs.beginText();
            cs.newLineAtOffset(x + 2, y + 5);
            cs.showText(cols[i]);
            cs.endText();
            x += COL_WIDTHS[i];
        }
        cs.stroke();
    }

    private float drawCenteredText(PDPageContentStream cs, String text, int size, float y) throws IOException {
        cs.beginText();
        cs.setFont(fontBold, size);
        float textWidth = fontBold.getStringWidth(text) / 1000 * size;
        float centerX = (PDRectangle.A4.getWidth() - textWidth) / 2;
        cs.newLineAtOffset(centerX, y);
        cs.showText(text);
        cs.endText();
        return y - (size + 2);
    }

}
