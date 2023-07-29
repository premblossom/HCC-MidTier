package com.hcc.hccservice.service;

import com.hcc.hccservice.entity.CommunityRelativeFactor;
import com.hcc.hccservice.entity.ListingV24;
import com.hcc.hccservice.repo.CommunityRelativeFactorRepo;
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
public class CommunityRelativeFactorService {

    @Autowired
    private CommunityRelativeFactorRepo communityRelativeFactorRepo;

    public void addAllData() throws IOException {

        File file = new File("");
        InputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();

        List<CommunityRelativeFactor> communityRelativeFactors = new ArrayList();

        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cellsInRow = currentRow.iterator();
            CommunityRelativeFactor communityRelativeFactor = new CommunityRelativeFactor();


            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
//                if(cellIdx == 0 && currentCell.getNumericCellValue() == 0){
//
//                }
                switch (cellIdx) {
                    case 0:
                        communityRelativeFactor.setAge((int)currentCell.getNumericCellValue());
                        break;

                    case 1:
                        communityRelativeFactor.setGender(currentCell.getStringCellValue());
                        break;

                    case 2:
                        communityRelativeFactor.setValue(currentCell.getNumericCellValue());
                        break;

                    case 3:
                        communityRelativeFactor.setCommunity(currentCell.getStringCellValue());
                        break;

                    default:
                        break;
                }

                cellIdx++;
            }
            if(! (communityRelativeFactor.getAge() == 0))
            {
                communityRelativeFactors.add(communityRelativeFactor);
//                communityRelativeFactorRepo.save(communityRelativeFactor);
            }
        }
        workbook.close();
        for(CommunityRelativeFactor a : communityRelativeFactors){
            System.out.println(a.toString());
        }


    }

    public double getValueBasedOnCommunityAgeGender(String community, int age, String gender) {

        Optional<List<CommunityRelativeFactor>> communityRelativeFactorsOptions =  communityRelativeFactorRepo.findByCommunityAndGender(community, gender);
        if(communityRelativeFactorsOptions.isPresent()){
            List<CommunityRelativeFactor> communityRelativeFactors = communityRelativeFactorsOptions.get();
            Collections.sort(communityRelativeFactors,(a,b) -> a.getAge()-b.getAge());
            for (CommunityRelativeFactor a : communityRelativeFactors){
                if(a.getAge() < age){
                    continue;
                }
                return a.getValue();
            }



        }
        return -1;
    }
}
