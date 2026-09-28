package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.PurchaseOrderEntity;
import com.mrf.trashsorting.repository.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    // CREATE
    public PurchaseOrderEntity createPurchaseOrder(
            PurchaseOrderEntity purchaseOrder) {

        return purchaseOrderRepository.save(purchaseOrder);
    }

    // GET ALL
    public List<PurchaseOrderEntity> getAllPurchaseOrders() {

        return purchaseOrderRepository.findAll();
    }

    // GET BY ID
    public PurchaseOrderEntity getPurchaseOrderById(int id) {

        return purchaseOrderRepository
                .findById(id)
                .orElse(null);
    }

    // UPDATE
    public PurchaseOrderEntity updatePurchaseOrder(
            int id,
            PurchaseOrderEntity purchaseOrder) {

        PurchaseOrderEntity existingOrder =
                purchaseOrderRepository
                        .findById(id)
                        .orElse(null);

        if (existingOrder == null) {
            return null;
        }

        existingOrder.setVendorName(
                purchaseOrder.getVendorName());

        existingOrder.setProductName(
                purchaseOrder.getProductName());

        existingOrder.setProductType(
                purchaseOrder.getProductType());

        existingOrder.setQuantity(
                purchaseOrder.getQuantity());

        existingOrder.setUnit(
                purchaseOrder.getUnit());

        existingOrder.setUnitPrice(
                purchaseOrder.getUnitPrice());

        existingOrder.setTotalAmount(
                purchaseOrder.getTotalAmount());

        existingOrder.setOrderDate(
                purchaseOrder.getOrderDate());

        existingOrder.setStatus(
                purchaseOrder.getStatus());

        return purchaseOrderRepository.save(existingOrder);
    }

    // DELETE
    public boolean deletePurchaseOrder(int id) {

        if (!purchaseOrderRepository.existsById(id)) {
            return false;
        }

        purchaseOrderRepository.deleteById(id);
        return true;
    }
}