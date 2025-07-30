package com.sptraders.sptraders_billing.controller;

import com.sptraders.sptraders_billing.dto.RequestDTO;
import com.sptraders.sptraders_billing.entity.CustomerEntry;
import com.sptraders.sptraders_billing.service.PDFService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BilllingController {

    private PDFService pdfService;

    @Autowired
    public BilllingController(PDFService thePdfService) {
        this.pdfService = thePdfService;
    }

    @PostMapping("/generatePDF")
    public void downloadPdf(@RequestBody RequestDTO requestDTO,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=sptraders-report.pdf");
        pdfService.generatePdf(response.getOutputStream(), requestDTO.getCustomerName(), requestDTO.getFromDate(), requestDTO.getToDate());
    }



}
