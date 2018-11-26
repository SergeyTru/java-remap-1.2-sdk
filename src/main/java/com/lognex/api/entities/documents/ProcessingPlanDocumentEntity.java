package com.lognex.api.entities.documents;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.markers.ProductMarker;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProcessingPlanDocumentEntity extends DocumentEntity {
    private Long cost;
    private String externalCode;
    private ListEntity<DocumentPosition> materials;
    private String pathName;
    private ListEntity<DocumentPosition> products;

    @Getter
    @Setter
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class PlanItem extends MetaEntity {
        private ProductMarker product;
        private Double quantity;
    }
}
