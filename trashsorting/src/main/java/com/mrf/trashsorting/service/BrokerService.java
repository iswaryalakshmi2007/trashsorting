package com.mrf.trashsorting.service;

import com.mrf.trashsorting.entity.BrokerEntity;
import com.mrf.trashsorting.repository.BrokerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrokerService {

    @Autowired
    private BrokerRepository brokerRepository;

    // Create Broker
    public BrokerEntity createBroker(BrokerEntity broker) {
        return brokerRepository.save(broker);
    }

    // Get All Brokers
    public List<BrokerEntity> getAllBrokers() {
        return brokerRepository.findAll();
    }

    // Get Broker By ID
    public BrokerEntity getBrokerById(int id) {
        return brokerRepository.findById(id).orElse(null);
    }

    // Update Broker
    public BrokerEntity updateBroker(int id, BrokerEntity broker) {

        BrokerEntity existingBroker =
                brokerRepository.findById(id).orElse(null);

        if (existingBroker == null) {
            return null;
        }

        existingBroker.setBrokerName(broker.getBrokerName());
        existingBroker.setCompanyName(broker.getCompanyName());
        existingBroker.setEmail(broker.getEmail());
        existingBroker.setPhone(broker.getPhone());
        existingBroker.setMaterialType(broker.getMaterialType());

        return brokerRepository.save(existingBroker);
    }

    // Delete Broker
    public boolean deleteBroker(int id) {

        if (!brokerRepository.existsById(id)) {
            return false;
        }

        brokerRepository.deleteById(id);
        return true;
    }
}