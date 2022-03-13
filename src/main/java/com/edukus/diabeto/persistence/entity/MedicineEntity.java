package com.edukus.diabeto.persistence.entity;


import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "MedicineEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicine")
public class MedicineEntity {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String label;
    private Double qte;
    private String unit;
    private String prandial;
    private String email;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private List<MedicineTimeEntity> times;


}
