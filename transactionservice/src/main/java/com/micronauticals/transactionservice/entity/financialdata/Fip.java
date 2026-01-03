package com.micronauticals.transactionservice.entity.financialdata;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "fip")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String fipID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fi_data_bundle_id")
    private FiDataBundle fiDataBundle;

    @OneToMany(mappedBy = "fip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FiAccount> accounts;
}