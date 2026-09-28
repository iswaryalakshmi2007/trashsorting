package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.SalesOrderEntity;
import com.mrf.trashsorting.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesOrderService {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    // CREATE
    public SalesOrderEntity createSalesOrder(
            SalesOrderEntity salesOrder) {

        return salesOrderRepository.save(salesOrder);
    }

    // GET ALL
    public List<SalesOrderEntity> getAllSalesOrders() {

        return salesOrderRepository.findAll();
    }

    // GET BY ID
    public SalesOrderEntity getSalesOrderById(int id) {

        return salesOrderRepository
                .findById(id)
                .orElse(null);
    }

    // UPDATE
    public SalesOrderEntity updateSalesOrder(
            int id,
            SalesOrderEntity salesOrder) {

        SalesOrderEntity existingOrder =
                salesOrderRepository
                        .findById(id)
                        .orElse(null);

        if (existingOrder == null) {
            return null;
        }

        existingOrder.setBrokerId(
                salesOrder.getBrokerId());

        existingOrder.setBrokerName(
                salesOrder.getBrokerName());

        existingOrder.setProductName(
                salesOrder.getProductName());

        existingOrder.setMaterialType(
                salesOrder.getMaterialType());

        existingOrder.setQuantity(
                salesOrder.getQuantity());

        existingOrder.setUnit(
                salesOrder.getUnit());

        existingOrder.setUnitPrice(
                salesOrder.getUnitPrice());

        existingOrder.setTotalAmount(
                salesOrder.getTotalAmount());

        existingOrder.setOrderDate(
                salesOrder.getOrderDate());

        existingOrder.setStatus(
                salesOrder.getStatus());

        return salesOrderRepository.save(existingOrder);
    }

    // DELETE
    public boolean deleteSalesOrder(int id) {

        if (!salesOrderRepository.existsById(id)) {
            return false;
        }

        salesOrderRepository.deleteById(id);
        return true;
    }
}