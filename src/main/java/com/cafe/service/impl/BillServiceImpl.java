package com.cafe.service.impl;

import com.cafe.bean.Bill;
import com.cafe.dao.BillDao;
import com.cafe.service.facade.BillService;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class BillServiceImpl implements BillService {
    @Autowired
    private BillDao billDao;


    @Override
    public String generateReport(Bill bill) throws Exception{
        String fileName;
        if (validate(bill)) {
            if (bill.getUuid() != null) {
                fileName = bill.getUuid();
            } else {
                fileName = "BILL-" + System.currentTimeMillis();
                bill.setUuid(fileName);
                save(bill);

            }
            String data = "Name: " + bill.getName() + "\n" +
                    "Email: " + bill.getEmail() + "\n" +
                    "Phone: " + bill.getPhone() + "\n" +
                    "Method: " + bill.getMethod() ;
            Document document = new Document();
            final String store_location = "C:\\Users\\ADMIN\\Desktop\\ayoub\\popoCafe";
            PdfWriter.getInstance(document, new FileOutputStream(store_location + "\\" + fileName + ".pdf"));

            document.open();
            setRectangleInPdf(document);

            Paragraph chunk = new Paragraph("Cafe Management Bill", FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, BaseColor.BLACK));
        }
        return null;

    }

    private void setRectangleInPdf(Document document) throws DocumentException {
        Rectangle rec = new Rectangle(577, 825, 18, 15);
        rec.enableBorderSide(1);
        rec.enableBorderSide(2);
        rec.enableBorderSide(4);
        rec.enableBorderSide(8);
        rec.setBackgroundColor(BaseColor.BLACK);
        rec.setBorderWidth(1);
        document.add(rec);


    }

    private void save(Bill bill) {
        if (validate(bill)) {
            billDao.save(bill);
        }

    }

    private boolean validate(Bill bill) {
        return bill.getUuid() != null &&
                bill.getName() != null;
    }
}
