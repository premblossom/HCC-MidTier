package com.hcc.hccservice;

import com.hcc.hccservice.service.CommunityRelativeFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HccServiceApplication {

//	@Autowired
//	CommunityRelativeFactorService communityRelativeFactorService;

	public static void main(String[] args) {

		SpringApplication.run(HccServiceApplication.class, args);

//		 = new CommunityRelativeFactorService();


	}

}
