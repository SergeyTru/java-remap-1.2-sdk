package com.lognex.api.entities.documents.positions;

import com.lognex.api.entities.documents.DocumentPosition;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InvoiceDocumentPosition extends DocumentPosition {
    private Double discount;
    private Integer vat;
}
