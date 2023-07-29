package com.hcc.hccservice.controller;

import com.hcc.hccservice.service.CommunityRelativeFactorService;
import com.hcc.hccservice.service.InputProcessorService;
import com.hcc.hccservice.service.ListingV24Service;
import com.hcc.hccservice.service.SortByIcd10CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BaseController {

    @Autowired
    private ListingV24Service listingV24Service;

    @Autowired
    private SortByIcd10CodeService sortByIcd10CodeService;

    @Autowired
    private CommunityRelativeFactorService communityRelativeFactorService;

    @Autowired
    private InputProcessorService inputProcessorService;

    @GetMapping("/")
    public String executorService() throws IOException {

        sortByIcd10CodeService.addAllData();
//        System.out.println(communityRelativeFactorService.getValueBasedOnCommunityAgeGender("Community, NonDual, Aged",69,"male"));


        return "Success";
    }

    @PostMapping("/parse-input-file")
    public ResponseEntity<?> parseInputFile(@RequestParam("file") MultipartFile file) throws IOException {
//        System.out.println(file.getNamfie());


        return ResponseEntity.ok(inputProcessorService.parseInputFile(file));
    }


}
