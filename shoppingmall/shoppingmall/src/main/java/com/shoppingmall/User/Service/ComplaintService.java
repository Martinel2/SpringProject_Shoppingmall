package com.shoppingmall.User.Service;

import com.shoppingmall.User.Domain.Complaint;
import com.shoppingmall.User.Repository.ComplaintRepository;
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
