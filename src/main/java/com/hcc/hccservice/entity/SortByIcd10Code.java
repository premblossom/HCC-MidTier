package com.hcc.hccservice.entity;

import com.hcc.hccservice.entity.ListingV24;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortByIcd10Code {

    @Id
    private String diagnosisCode;
    private String description;

    @ManyToOne()
    @JoinColumn(name = "listingV24_id", nullable = false)
    private ListingV24 listingV24;

}
