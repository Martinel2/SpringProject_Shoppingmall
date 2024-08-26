package com.shoppingmall.service;

import com.shoppingmall.domain.Complaint;
import com.shoppingmall.repository.ComplaintRepository;
import jakarta.transaction.Transactional;

@Transactional
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public Complaint addComplaint(Complaint complaint){
        return complaintRepository.addComplain(complaint);
    }

}
