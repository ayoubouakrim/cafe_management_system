package com.cafe.service.impl;

import com.cafe.bean.Bill;
import com.cafe.bean.BillDetails;
import com.cafe.dao.BillDao;
import com.cafe.service.facade.BillDetailsService;
import com.cafe.service.facade.BillService;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillDao billDao;
    @Autowired
    private BillDetailsService billDetailsService;


    @Override
    public List<Bill> findByCreatedBy(String username) {
        return billDao.findByCreatedBy(username);
    }

    @Override
    public List<Bill> findAll() {
        return billDao.findAll();
    }

    @Override
    public String generateReport(Bill bill) throws Exception {
        String fileName;
        if (validate(bill)) {
            if (bill.getUuid() != null) {
                fileName = "BILL-" + bill.getUuid();
            } else {
                fileName = "BILL-" + System.currentTimeMillis();
                bill.setUuid(fileName);
                bill = save(bill);

            }
            String data = "Name: " + bill.getName() + "\n" +
                    "Email: " + bill.getEmail() + "\n" +
                    "Phone: " + bill.getPhone() + "\n" +
                    "Method: " + bill.getMethod();
            Document document = new Document();
            final String store_location = "C:\\Users\\ADMIN\\Desktop\\ayoub\\popoCafe";
            PdfWriter.getInstance(document, new FileOutputStream(store_location + "\\" + fileName + ".pdf"));

            document.open();
            setRectangleInPdf(document);

            Paragraph chunk = new Paragraph("Cafe Management Bill", getFont("Header"));
            chunk.setAlignment(Element.ALIGN_CENTER);
            document.add(chunk);
            document.add(new Paragraph("\n"));
            Paragraph paragraph = new Paragraph(data, getFont("data"));
            document.add(paragraph);
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            addTableHeader(table);


            List<BillDetails> products = bill.getProductDetails();

            for (BillDetails product : products) {
                addRows(table, product);
            }
            document.add(table);

            Paragraph footer = new Paragraph("Total: " + bill.getTotal(), getFont("data"));
            document.add(footer);
            document.close();
            return fileName;

        }
        return null;

    }

    private void addRows(PdfPTable table, BillDetails bill) {
        table.addCell(bill.getProduct().getName());
        table.addCell(String.valueOf(bill.getProduct().getCategory()));
        table.addCell(String.valueOf(bill.getQuantity()));
        table.addCell(String.valueOf(bill.getProduct().getPrice()));
        table.addCell(String.valueOf(bill.getQuantity() * bill.getProduct().getPrice()));
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Name", "Category", "Quantity", "Price", "Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });
    }


    private Font getFont(String header) {
        switch (header) {
            case "Header":
                return FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, Font.BOLD, BaseColor.BLACK);
            case "data":
                return FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, BaseColor.BLACK);
            default:
                return FontFactory.getFont(FontFactory.TIMES, 12, Font.NORMAL, BaseColor.BLACK);
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        Rectangle rec = new Rectangle(577, 825, 18, 15);
        rec.enableBorderSide(1);
        rec.enableBorderSide(2);
        rec.enableBorderSide(4);
        rec.enableBorderSide(8);
        rec.setBorderColor(BaseColor.BLACK);
        rec.setBorderWidth(1);
        document.add(rec);


    }

    public Bill save(Bill bill) {
        if (validate(bill)) {
            Bill savedBill = billDao.save(bill);
            if (bill.getProductDetails() != null) {
                bill.getProductDetails().forEach(product -> {
                    product.setBill(savedBill);
                    billDetailsService.save(product);
                });
            }
            return savedBill;
        }
        return null;

    }

    private boolean validate(Bill bill) {
        return bill.getUuid() != null &&
                bill.getName() != null;
    }
}
