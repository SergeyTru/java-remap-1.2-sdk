package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.entities.*;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class PaymentOutTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        PaymentOut paymentOut = new PaymentOut();
        paymentOut.setName("paymentout_" + randomString(3) + "_" + new Date().getTime());
        paymentOut.setMoment(LocalDateTime.now());
        paymentOut.setSum(randomLong(10, 10000));
        paymentOut.setOrganization(simpleEntityManager.getOwnOrganization());
        paymentOut.setAgent(simpleEntityManager.createSimpleCounterparty());
        paymentOut.setExpenseItem(simpleEntityManager.createSimpleExpenseItem());

        api.entity().paymentout().create(paymentOut);

        ListEntity<PaymentOut> updatedEntitiesList = api.entity().paymentout().get(filterEq("name", paymentOut.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        PaymentOut retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(paymentOut.getName(), retrievedEntity.getName());
        assertEquals(paymentOut.getMoment(), retrievedEntity.getMoment());
        assertEquals(paymentOut.getSum(), retrievedEntity.getSum());
        assertEquals(paymentOut.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(paymentOut.getAgent().getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(paymentOut.getExpenseItem().getMeta().getHref(), retrievedEntity.getExpenseItem().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().paymentout().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void newTest() throws IOException, LognexApiException {
        PaymentOut paymentOut = api.entity().paymentout().templateDocument();
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(Long.valueOf(0), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);

        assertEquals(paymentOut.getOrganization().getMeta().getHref(), simpleEntityManager.getOwnOrganization().getMeta().getHref());
        assertEquals(paymentOut.getGroup().getMeta().getHref(), simpleEntityManager.getMainGroup().getMeta().getHref());
    }

    @Test
    public void newByPurchaseOrdersTest() throws IOException, LognexApiException {
        PurchaseOrder purchaseOrder = simpleEntityManager.createSimple(PurchaseOrder.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(purchaseOrder));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(purchaseOrder.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(purchaseOrder.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(purchaseOrder.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(purchaseOrder.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(purchaseOrder.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySalesReturnsTest() throws IOException, LognexApiException {
        SalesReturn salesReturn = simpleEntityManager.createSimple(SalesReturn.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(salesReturn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(salesReturn.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(salesReturn.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(salesReturn.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(salesReturn.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(salesReturn.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newBySuppliesTest() throws IOException, LognexApiException {
        Supply supply = simpleEntityManager.createSimple(Supply.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(supply));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(supply.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(supply.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(supply.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(supply.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(supply.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByInvoicesInTest() throws IOException, LognexApiException {
        InvoiceIn invoiceIn = simpleEntityManager.createSimple(InvoiceIn.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(invoiceIn));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(invoiceIn.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(invoiceIn.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(invoiceIn.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(invoiceIn.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(invoiceIn.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Test
    public void newByCommissionReportsOutTest() throws IOException, LognexApiException {
        CommissionReportOut commissionReportOut = simpleEntityManager.createSimple(CommissionReportOut.class);

        PaymentOut paymentOut = api.entity().paymentout().templateDocument("operations", Collections.singletonList(commissionReportOut));
        LocalDateTime time = LocalDateTime.now();

        assertEquals("", paymentOut.getName());
        assertEquals(commissionReportOut.getSum(), paymentOut.getSum());
        assertFalse(paymentOut.getShared());
        assertTrue(paymentOut.getApplicable());
        assertTrue(ChronoUnit.MILLIS.between(time, paymentOut.getMoment()) < 1000);
        assertEquals(1, paymentOut.getOperations().size());
        assertEquals(commissionReportOut.getMeta().getHref(), paymentOut.getOperations().get(0).getMeta().getHref());
        assertEquals(commissionReportOut.getGroup().getMeta().getHref(), paymentOut.getGroup().getMeta().getHref());
        assertEquals(commissionReportOut.getContract().getMeta().getHref(), paymentOut.getContract().getMeta().getHref());
        assertEquals(commissionReportOut.getAgent().getMeta().getHref(), paymentOut.getAgent().getMeta().getHref());
        assertEquals(commissionReportOut.getOrganization().getMeta().getHref(), paymentOut.getOrganization().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        PaymentOut originalPaymentOut = (PaymentOut) originalEntity;
        PaymentOut retrievedPaymentOut = (PaymentOut) retrievedEntity;

        assertEquals(originalPaymentOut.getName(), retrievedPaymentOut.getName());
        assertEquals(originalPaymentOut.getOrganization().getMeta().getHref(), retrievedPaymentOut.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentOut.getAgent().getMeta().getHref(), retrievedPaymentOut.getAgent().getMeta().getHref());
        assertEquals(originalPaymentOut.getExpenseItem().getMeta().getHref(), retrievedPaymentOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        PaymentOut originalPaymentOut = (PaymentOut) originalEntity;
        PaymentOut updatedPaymentOut = (PaymentOut) updatedEntity;

        assertNotEquals(originalPaymentOut.getName(), updatedPaymentOut.getName());
        assertEquals(changedField, updatedPaymentOut.getName());
        assertEquals(originalPaymentOut.getOrganization().getMeta().getHref(), updatedPaymentOut.getOrganization().getMeta().getHref());
        assertEquals(originalPaymentOut.getAgent().getMeta().getHref(), updatedPaymentOut.getAgent().getMeta().getHref());
        assertEquals(originalPaymentOut.getExpenseItem().getMeta().getHref(), updatedPaymentOut.getExpenseItem().getMeta().getHref());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().paymentout();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return PaymentOut.class;
    }
}