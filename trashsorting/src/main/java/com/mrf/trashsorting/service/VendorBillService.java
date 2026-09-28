package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.VendorBillEntity;
import com.mrf.trashsorting.repository.VendorBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorBillService {

    @Autowired
    private VendorBillRepository vendorBillRepository;

    // CREATE
    public VendorBillEntity createVendorBill(
            VendorBillEntity vendorBill) {

        return vendorBillRepository.save(vendorBill);
    }

    // GET ALL
    public List<VendorBillEntity> getAllVendorBills() {

        return vendorBillRepository.findAll();
    }

    // GET BY ID
    public VendorBillEntity getVendorBillById(int id) {

        return vendorBillRepository
                .findById(id)
                .orElse(null);
    }

    // UPDATE
    public VendorBillEntity updateVendorBill(
            int id,
            VendorBillEntity vendorBill) {

        VendorBillEntity existingBill =
                vendorBillRepository
                        .findById(id)
                        .orElse(null);

        if (existingBill == null) {
            return null;
        }

        existingBill.setPurchaseOrderId(
                vendorBill.getPurchaseOrderId());

        existingBill.setVendorName(
                vendorBill.getVendorName());

        existingBill.setProductName(
                vendorBill.getProductName());

        existingBill.setBillAmount(
                vendorBill.getBillAmount());

        existingBill.setBillDate(
                vendorBill.getBillDate());

        existingBill.setPaymentStatus(
                vendorBill.getPaymentStatus());

        return vendorBillRepository.save(existingBill);
    }

    // DELETE
    public boolean deleteVendorBill(int id) {

        if (!vendorBillRepository.existsById(id)) {
            return false;
        }

        vendorBillRepository.deleteById(id);
        return true;
    }
}