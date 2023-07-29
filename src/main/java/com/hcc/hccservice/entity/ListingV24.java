package com.hcc.hccservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListingV24 {

    @Id
    private Long id;
    private String hccDescription;
    private double weight;
//    @OneToMany(mappedBy="listingV24")
//    private Set<SortByIcd10Code> sortByIcd10CodeSet;

}
