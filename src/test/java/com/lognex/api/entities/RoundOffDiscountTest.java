package com.lognex.api.entities;

import com.lognex.api.entities.discounts.Discount;
import com.lognex.api.entities.discounts.RoundOffDiscount;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class RoundOffDiscountTest extends EntityTestBase {

    @Test
    public void RoundOffDiscountCrudTest() throws IOException, ApiClientException {
        //get all discount
        ListEntity<Discount> discountList = api.entity().discount().get();
        assertEquals(1, discountList.getRows().size());
        //get one
        Discount discount = discountList.getRows().get(0);
        RoundOffDiscount roundOffDiscount = api.entity().roundoffdiscount().get(discount.getId());
        assertNotNull(roundOffDiscount.getName());
        assertNotEquals("new", roundOffDiscount);
        String defaultName = roundOffDiscount.getName();
        //update one
        roundOffDiscount.setName("new");
        api.entity().roundoffdiscount().update(roundOffDiscount.getId(), roundOffDiscount);
        roundOffDiscount = api.entity().roundoffdiscount().get(roundOffDiscount.getId());
        assertEquals("new", roundOffDiscount.getName());
        roundOffDiscount.setName(defaultName);
        api.entity().roundoffdiscount().update(roundOffDiscount.getId(), roundOffDiscount);
    }
}
