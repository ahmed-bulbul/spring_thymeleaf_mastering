package com.thikthak.app.service.institute;

import com.thikthak.app.domain.institute.Institute;
import com.thikthak.app.repository.institute.AddressRepository;
import com.thikthak.app.repository.institute.ContactRepository;
import com.thikthak.app.repository.institute.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstituteService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    InstituteRepository instituteRepository;

    public List<Institute> getAll() {

        List<Institute> result = instituteRepository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }

    public Page< Institute > getAllPaginated(int pageNum, int pageSize, String sortField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return instituteRepository.findAll(pageable);

    }

    public Institute findById(Long id) throws Exception {
        Optional<Institute> institute = instituteRepository.findById(id);

        if(institute.isPresent()) {
            return institute.get();
        } else {
            throw new Exception("No record exist for given id");
        }
    }

    public Institute getById(Long id) throws Exception {
        return this.findById(id);
    }


    public Institute createOrUpdate(Institute institute) {
        if(institute.getInstituteId() == null) {
            //address
            addressRepository.save(institute.getContact().getPermanentAddress());
            //contact
            contactRepository.save(institute.getContact());
            //institute
            instituteRepository.save(institute);

        } else {
            Optional<Institute> entityOptional = instituteRepository.findById(institute.getInstituteId());
            if(entityOptional.isPresent()) {

                //address
                addressRepository.save(institute.getContact().getPermanentAddress());
                //contact
                contactRepository.save(institute.getContact());
                //institute
                instituteRepository.save(institute);
            }
        }
        return institute;

    }

    public void deleteById(Long id) throws Exception {
        Optional<Institute> institute = instituteRepository.findById(id);

        if(institute.isPresent()) {
            instituteRepository.deleteById(id);
        } else {
            throw new Exception("No record exist for given id");
        }
    }


}
