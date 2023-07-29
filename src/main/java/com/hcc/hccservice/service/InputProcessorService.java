package com.hcc.hccservice.service;

import com.hcc.hccservice.entity.CommunityRelativeFactor;
import com.hcc.hccservice.model.CodeValuePair;
import com.hcc.hccservice.model.CommunityNameValuePair;
import com.hcc.hccservice.model.ParsedFileResult;
import com.hcc.hccservice.model.PatientResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class InputProcessorService {

    @Autowired
    private CommunityRelativeFactorService communityRelativeFactorService;

    @Autowired
    private SortByIcd10CodeService sortByIcd10CodeService;

    public ParsedFileResult parseInputFile(MultipartFile file) throws IOException {

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheet("Sheet1");
        Iterator<Row> rows = sheet.iterator();
        List<PatientResult> patientResults = new ArrayList<>();
        int rowNumber = 0;
        while (rows.hasNext()) {
            Row currentRow = rows.next();

            // skip header
            if (rowNumber == 0) {
                rowNumber++;
                continue;
            }
            Iterator<Cell> cellsInRow = currentRow.iterator();
            PatientResult patientResult = new PatientResult();

            List<String > caseList = new ArrayList<>();
            int cellIdx = 0;
            while (cellsInRow.hasNext()) {
                Cell currentCell = cellsInRow.next();
//                if(cellIdx == 0 && currentCell.getNumericCellValue() == 0){
//
//                }
                switch (cellIdx) {
                    case 0:
                        patientResult.setName(currentCell.getStringCellValue());
                        break;

                    case 1:
                        patientResult.setAge((int)currentCell.getNumericCellValue());
                        break;

                    case 2:
                        patientResult.setGender(currentCell.getStringCellValue());
                        break;

                    case 3:
                        patientResult.setCommunityNameValuePair(CommunityNameValuePair.builder()
                                        .community(currentCell.getStringCellValue())
                                        .value(communityRelativeFactorService.getValueBasedOnCommunityAgeGender(currentCell.getStringCellValue(),patientResult.getAge(),patientResult.getGender()))
                                .build());
                        break;

                    default:
                        if(!currentCell.getStringCellValue().trim().isEmpty())
                            caseList.add(currentCell.getStringCellValue());
                        break;
                }

                cellIdx++;
            }
            double total = patientResult.getCommunityNameValuePair().getValue();
            List<CodeValuePair> codeValuePairs = new ArrayList<>();
            for(String a : caseList){
                double res = sortByIcd10CodeService.findValueByDiagnosisCode(a);
                total += res;
                codeValuePairs.add(CodeValuePair.builder()
                                .code(a)
                                .value(res)
                        .build());
            }
            patientResult.setCodeValuePairs(codeValuePairs);
            patientResult.setTotal(total);
            patientResults.add(patientResult);
        }
        return ParsedFileResult.builder()
                .patientResults(patientResults)
                .build();
    }
}
