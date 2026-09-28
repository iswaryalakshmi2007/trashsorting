package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.BankPaymentEntity;
import com.mrf.trashsorting.entity.VendorBillEntity;
import com.mrf.trashsorting.repository.BankPaymentRepository;
import com.mrf.trashsorting.repository.VendorBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankPaymentService {

    @Autowired
    private BankPaymentRepository bankPaymentRepository;

    @Autowired
    private VendorBillRepository vendorBillRepository;


    // CREATE PAYMENT + UPDATE VENDOR BILL
    public BankPaymentEntity createBankPayment(
            BankPaymentEntity payment) {

        // 1. Save Bank Payment
        BankPaymentEntity savedPayment =
                bankPaymentRepository.save(payment);


        // 2. Find related Vendor Bill
        VendorBillEntity vendorBill =
                vendorBillRepository
                        .findById(
                                payment.getVendorBillId())
                        .orElse(null);


        // 3. If payment is completed,
        //    change Vendor Bill status to PAID
        if (vendorBill != null
                && "COMPLETED".equalsIgnoreCase(
                payment.getPaymentStatus())) {

            vendorBill.setPaymentStatus("PAID");

            vendorBillRepository.save(vendorBill);
        }


        return savedPayment;
    }


    // GET ALL
    public List<BankPaymentEntity> getAllBankPayments() {

        return bankPaymentRepository.findAll();
    }


    // GET BY ID
    public BankPaymentEntity getBankPaymentById(int id) {

        return bankPaymentRepository
                .findById(id)
                .orElse(null);
    }


    // UPDATE
    public BankPaymentEntity updateBankPayment(
            int id,
            BankPaymentEntity payment) {

        BankPaymentEntity existingPayment =
                bankPaymentRepository
                        .findById(id)
                        .orElse(null);

        if (existingPayment == null) {
            return null;
        }

        existingPayment.setVendorBillId(
                payment.getVendorBillId());

        existingPayment.setVendorName(
                payment.getVendorName());

        existingPayment.setPaymentAmount(
                payment.getPaymentAmount());

        existingPayment.setPaymentDate(
                payment.getPaymentDate());

        existingPayment.setPaymentMethod(
                payment.getPaymentMethod());

        existingPayment.setPaymentStatus(
                payment.getPaymentStatus());

        existingPayment.setReferenceNumber(
                payment.getReferenceNumber());

        return bankPaymentRepository.save(
                existingPayment);
    }


    // DELETE
    public boolean deleteBankPayment(int id) {

        if (!bankPaymentRepository.existsById(id)) {
            return false;
        }

        bankPaymentRepository.deleteById(id);

        return true;
    }
}