package com.hcc.hccservice.service;

import com.hcc.hccservice.entity.ListingV24;
import com.hcc.hccservice.entity.SortByIcd10Code;
import com.hcc.hccservice.repo.ListingV24Repo;
import com.hcc.hccservice.repo.SortByIcd10CodeRepo;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class SortByIcd10CodeService {

    @Autowired
    private SortByIcd10CodeRepo sortByIcd10CodeRepo;

    @Autowired
    private ListingV24Repo listingV24Repo;

    public void addAllData() throws IOException {

        File file = new File("");
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        HashSet<String > temp = new HashSet<>();

        List<SortByIcd10Code> sortByIcd10Codes = new ArrayList();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cellsInRow = currentRow.iterator();
            SortByIcd10Code sortByIcd10Code = new SortByIcd10Code();


            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
//                if(cellIdx == 0 && currentCell.getNumericCellValue() == 0){
//
//                }
                switch (cellIdx) {
                    case 0:
                        temp.add(currentCell.getStringCellValue());
                        sortByIcd10Code.setDiagnosisCode(currentCell.getStringCellValue());
                        break;

                    case 1:
                        sortByIcd10Code.setDescription(currentCell.getStringCellValue());
                        break;

                    case 2:
                        if(listingV24Repo.findById((long) currentCell.getNumericCellValue()).isPresent())
                            sortByIcd10Code.setListingV24(listingV24Repo.findById((long) currentCell.getNumericCellValue()).get());
                        else
                            System.out.println("Wrong id "+ currentCell.getNumericCellValue());
                        break;

                    default:
                        break;
                }

                cellIdx++;
            }
            if(! (sortByIcd10Code.getDiagnosisCode().equals("")))
            {
                Optional<SortByIcd10Code> o = sortByIcd10CodeRepo.findById(sortByIcd10Code.getDiagnosisCode());
                SortByIcd10Code sortByIcd10CodeOld = o.isPresent() ? o.get() : null;
                if(sortByIcd10CodeOld != null){
                    if(sortByIcd10CodeOld.getListingV24().getWeight() < sortByIcd10Code.getListingV24().getWeight()){
                        sortByIcd10Codes.add(sortByIcd10Code);
                sortByIcd10CodeRepo.save(sortByIcd10Code);
                    }
                }
                else{
                    sortByIcd10Codes.add(sortByIcd10Code);
                sortByIcd10CodeRepo.save(sortByIcd10Code);
                }
            }
        }
        workbook.close();
//        for(SortByIcd10Code a : sortByIcd10Codes){
            System.out.println(sortByIcd10Codes.size()+"     "+temp.size());
//        }


    }

    public double findValueByDiagnosisCode(String diagnosisCode) {
        Optional<SortByIcd10Code> option = sortByIcd10CodeRepo.findById(diagnosisCode);
        if(option.isPresent()){
            SortByIcd10Code sortByIcd10Code = option.get();
            return sortByIcd10Code.getListingV24().getWeight();
        }
        return -1;
    }
}
