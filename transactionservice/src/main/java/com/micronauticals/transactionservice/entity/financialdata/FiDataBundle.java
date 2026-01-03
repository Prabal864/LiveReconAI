package com.micronauticals.transactionservice.entity.financialdata;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "fi_data_bundle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiDataBundle {

    @Id
    private String id;

    @Column
    private String status;

    @Column
    private String consentId;

    @Column
    private String format;

    @Embedded
    private DataRange dataRange;

    @OneToMany(mappedBy = "fiDataBundle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fip> fips;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataRange {
        @Column(name = "data_range_from")
        private String from;
        @Column(name = "data_range_to")
        private String to;
    }
}
