package com.suretrust.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(value = "true")
public class ProductDTO {

    private String name;
    private String brandName ;
    private long categoryId;
    private long quantity;
    private String categoryName;

}
