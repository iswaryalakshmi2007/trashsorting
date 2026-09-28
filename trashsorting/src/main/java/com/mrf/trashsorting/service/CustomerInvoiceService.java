package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.CustomerInvoiceEntity;
import com.mrf.trashsorting.entity.InventoryEntity;
import com.mrf.trashsorting.repository.CustomerInvoiceRepository;
import com.mrf.trashsorting.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerInvoiceService {

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;

    @Autowired
    private InventoryRepository inventoryRepository;


    // CREATE CUSTOMER INVOICE + REDUCE INVENTORY
    public CustomerInvoiceEntity createCustomerInvoice(
            CustomerInvoiceEntity invoice) {

        // 1. Save Customer Invoice
        CustomerInvoiceEntity savedInvoice =
                customerInvoiceRepository.save(invoice);


        // 2. Find inventory for the same material
        List<InventoryEntity> inventories =
                inventoryRepository.findAll();

        InventoryEntity matchingInventory = null;

        for (InventoryEntity inventory : inventories) {

            if (inventory.getMaterialType()
                    .equalsIgnoreCase(
                            invoice.getMaterialType())) {

                matchingInventory = inventory;
                break;
            }
        }


        // 3. Reduce inventory
        if (matchingInventory != null) {

            double remainingQuantity =
                    matchingInventory.getQuantity()
                            - invoice.getDeliveredWeight();

            // Prevent negative inventory
            if (remainingQuantity >= 0) {

                matchingInventory.setQuantity(
                        remainingQuantity);

                inventoryRepository.save(
                        matchingInventory);

            } else {

                // Delete the invoice if stock is insufficient
                customerInvoiceRepository.delete(
                        savedInvoice);

                throw new RuntimeException(
                        "Insufficient inventory for "
                                + invoice.getMaterialType());
            }
        }

        return savedInvoice;
    }


    // GET ALL CUSTOMER INVOICES
    public List<CustomerInvoiceEntity>
    getAllCustomerInvoices() {

        return customerInvoiceRepository.findAll();
    }


    // GET CUSTOMER INVOICE BY ID
    public CustomerInvoiceEntity
    getCustomerInvoiceById(int id) {

        return customerInvoiceRepository
                .findById(id)
                .orElse(null);
    }


    // UPDATE CUSTOMER INVOICE
    public CustomerInvoiceEntity
    updateCustomerInvoice(
            int id,
            CustomerInvoiceEntity invoice) {

        CustomerInvoiceEntity existingInvoice =
                customerInvoiceRepository
                        .findById(id)
                        .orElse(null);

        if (existingInvoice == null) {
            return null;
        }

        existingInvoice.setSalesOrderId(
                invoice.getSalesOrderId());

        existingInvoice.setBrokerId(
                invoice.getBrokerId());

        existingInvoice.setBrokerName(
                invoice.getBrokerName());

        existingInvoice.setProductName(
                invoice.getProductName());

        existingInvoice.setMaterialType(
                invoice.getMaterialType());

        existingInvoice.setDeliveredWeight(
                invoice.getDeliveredWeight());

        existingInvoice.setUnit(
                invoice.getUnit());

        existingInvoice.setUnitPrice(
                invoice.getUnitPrice());

        existingInvoice.setInvoiceAmount(
                invoice.getInvoiceAmount());

        existingInvoice.setInvoiceDate(
                invoice.getInvoiceDate());

        existingInvoice.setPaymentStatus(
                invoice.getPaymentStatus());

        return customerInvoiceRepository.save(
                existingInvoice);
    }


    // DELETE CUSTOMER INVOICE
    public boolean deleteCustomerInvoice(int id) {

        if (!customerInvoiceRepository.existsById(id)) {
            return false;
        }

        customerInvoiceRepository.deleteById(id);

        return true;
    }
}