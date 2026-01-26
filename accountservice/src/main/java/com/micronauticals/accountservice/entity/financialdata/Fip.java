package com.micronauticals.accountservice.entity.financialdata;


import lombok.*;
import jakarta.persistence.*;
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

    @Override
    public String toString() {
        return "Fip{" +
                "id=" + id +
                ", fipID='" + fipID + '\'' +
                ", accounts=" + (accounts != null ? accounts.stream().map(a -> a.getId()).toList() : null) +
                '}';
    }
}