package com.hcc.hccservice.service;

import com.hcc.hccservice.entity.ListingV24;
import com.hcc.hccservice.repo.ListingV24Repo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ListingV24Service {

    @Autowired
    private ListingV24Repo listingV24Repo;

    public void addAllData() throws IOException {

        File file = new File("");
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();

        List<ListingV24> listingV24s = new ArrayList();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cellsInRow = currentRow.iterator();
            ListingV24 listingV24 = new ListingV24();


            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
//                if(cellIdx == 0 && currentCell.getNumericCellValue() == 0){
//
//                }
                switch (cellIdx) {
                    case 0:
                        listingV24.setId((long) currentCell.getNumericCellValue());
                        break;

                    case 1:
                        listingV24.setHccDescription(currentCell.getStringCellValue());
                        break;

                    case 2:
                        listingV24.setWeight(currentCell.getNumericCellValue());
                        break;

                    default:
                        break;
                }

                cellIdx++;
            }
            if(! (listingV24.getId() == 0))
            {
                listingV24s.add(listingV24);
//                listingV24Repo.save(listingV24);
            }
        }
        workbook.close();
        for(ListingV24 a : listingV24s){
            System.out.println(a.toString());
        }


    }
}
