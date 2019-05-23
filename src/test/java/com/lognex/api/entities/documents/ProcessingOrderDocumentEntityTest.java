package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProcessingOrderDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity e = new ProcessingOrderDocumentEntity();
        e.setName("processingorder_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription(randomString());
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = new ProductEntity();
        material.setName(randomString());
        api.entity().product().post(material);
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);
        e.setProcessingPlan(processingPlan);

        e.setPositions(new ListEntity<>());
        e.getPositions().setRows(new ArrayList<>());
        DocumentPosition position = new DocumentPosition();
        position.setQuantity(3.1234);
        position.setAssortment(material);
        e.getPositions().getRows().add(position);

        api.entity().processingorder().post(e);

        ListEntity<ProcessingOrderDocumentEntity> updatedEntitiesList = api.entity().processingorder().
                get(limit(50), filterEq("name", e.getName()), expand("positions"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingOrderDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
        assertEquals(position.getQuantity(), retrievedEntity.getPositions().getRows().get(0).getQuantity());
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity.getPositions().getRows().get(0).getAssortment();
        assertEquals(material.getMeta().getHref(), retrievedProduct.getMeta().getHref());
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity e = createSimpleDocumentProcessingOrder();

        ProcessingOrderDocumentEntity retrievedEntity = api.entity().processingorder().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().processingorder().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ProcessingOrderDocumentEntity e = createSimpleDocumentProcessingOrder();

        ProcessingOrderDocumentEntity retrievedOriginalEntity = api.entity().processingorder().get(e.getId());
        String name = "processingorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        //Хак для того, чтобы при методе put не было попытки удалить материалы/продукты (должно быть исправлено)
        e.setPositions(null);
        api.entity().processingorder().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "processingorder_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(1500);
        e.setPositions(null);
        api.entity().processingorder().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity e = createSimpleDocumentProcessingOrder();

        ListEntity<ProcessingOrderDocumentEntity> entitiesList = api.entity().processingorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().processingorder().delete(e.getId());

        entitiesList = api.entity().processingorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity e = createSimpleDocumentProcessingOrder();

        ListEntity<ProcessingOrderDocumentEntity> entitiesList = api.entity().processingorder().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().processingorder().delete(e);

        entitiesList = api.entity().processingorder().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().processingorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private ProcessingOrderDocumentEntity createSimpleDocumentProcessingOrder() throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity e = new ProcessingOrderDocumentEntity();
        e.setName("processingorder_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = new ProductEntity();
        material.setName(randomString());
        api.entity().product().post(material);
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);
        e.setProcessingPlan(processingPlan);

        e.setPositions(new ListEntity<>());
        e.getPositions().setRows(new ArrayList<>());
        DocumentPosition position = new DocumentPosition();
        position.setQuantity(3.1234);
        position.setAssortment(material);
        e.getPositions().getRows().add(position);

        api.entity().processingorder().post(e);

        return e;
    }

    private void getAsserts(ProcessingOrderDocumentEntity e, ProcessingOrderDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
        assertEquals(e.getPositions().getMeta().getSize(), retrievedEntity.getPositions().getMeta().getSize());
        assertEquals(e.getUpdated().withNano(0), retrievedEntity.getUpdated().withNano(0));
    }

    private void putAsserts(ProcessingOrderDocumentEntity e, ProcessingOrderDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProcessingOrderDocumentEntity retrievedUpdatedEntity = api.entity().processingorder().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getProcessingPlan().getMeta().getHref(), retrievedUpdatedEntity.getProcessingPlan().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getPositions().getMeta().getSize(), retrievedUpdatedEntity.getPositions().getMeta().getSize());
        assertNotEquals(retrievedOriginalEntity.getUpdated().withNano(0), retrievedUpdatedEntity.getUpdated().withNano(0));
    }
}