package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.CustomerInvoiceEntity;
import com.mrf.trashsorting.entity.CustomerPaymentEntity;
import com.mrf.trashsorting.repository.CustomerInvoiceRepository;
import com.mrf.trashsorting.repository.CustomerPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerPaymentService {

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;

    @Autowired
    private CustomerInvoiceRepository customerInvoiceRepository;


    // CREATE CUSTOMER PAYMENT + UPDATE INVOICE
    public CustomerPaymentEntity createCustomerPayment(
            CustomerPaymentEntity payment) {

        // 1. Save Customer Payment
        CustomerPaymentEntity savedPayment =
                customerPaymentRepository.save(payment);

        // 2. Find the related Customer Invoice
        CustomerInvoiceEntity invoice =
                customerInvoiceRepository
                        .findById(payment.getCustomerInvoiceId())
                        .orElse(null);

        // 3. If payment is completed,
        //    change Invoice status to PAID
        if (invoice != null
                && "COMPLETED".equalsIgnoreCase(
                payment.getPaymentStatus())) {

            invoice.setPaymentStatus("PAID");

            customerInvoiceRepository.save(invoice);
        }

        return savedPayment;
    }


    // GET ALL CUSTOMER PAYMENTS
    public List<CustomerPaymentEntity> getAllCustomerPayments() {

        return customerPaymentRepository.findAll();
    }


    // GET CUSTOMER PAYMENT BY ID
    public CustomerPaymentEntity getCustomerPaymentById(int id) {

        return customerPaymentRepository
                .findById(id)
                .orElse(null);
    }


    // UPDATE CUSTOMER PAYMENT
    public CustomerPaymentEntity updateCustomerPayment(
            int id,
            CustomerPaymentEntity payment) {

        CustomerPaymentEntity existingPayment =
                customerPaymentRepository
                        .findById(id)
                        .orElse(null);

        if (existingPayment == null) {
            return null;
        }

        existingPayment.setCustomerInvoiceId(
                payment.getCustomerInvoiceId());

        existingPayment.setBrokerId(
                payment.getBrokerId());

        existingPayment.setBrokerName(
                payment.getBrokerName());

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

        return customerPaymentRepository.save(
                existingPayment);
    }


    // DELETE CUSTOMER PAYMENT
    public boolean deleteCustomerPayment(int id) {

        if (!customerPaymentRepository.existsById(id)) {
            return false;
        }

        customerPaymentRepository.deleteById(id);

        return true;
    }
}