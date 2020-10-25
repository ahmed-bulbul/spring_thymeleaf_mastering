package com.thikthak.app.service.auth;

import com.thikthak.app.domain.auth.User;
import com.thikthak.app.repository.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

//    @Autowired
//    UserRepository repository;
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<User> getAll() {
        List<User> result = repository.findAll();

        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<>();
        }
    }


    public Page< User > getAllPaginated(int pageNum, int pageSize, String sortField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);
        return repository.findAll(pageable);

    }


    public User findById(Long id) throws Exception {
        Optional<User> entity = repository.findById(id);

        if(entity.isPresent()) {
            return entity.get();
        } else {
            throw new Exception("No record exist for given id");
        }
    }

    public User getById(Long id) throws Exception {
        return this.findById(id);
    }


    public void setAttributeForCreateUpdate(){
        System.out.println("Abc");
    }

    public User createOrUpdate(User entity) {

        this.setAttributeForCreateUpdate();

        if(entity.getId() == null) {
            entity.setPassword(new BCryptPasswordEncoder().encode( entity.getPassword()));
            entity = repository.save(entity);

        } else {
            Optional<User> entityOptional = repository.findById(entity.getId());
            if(entityOptional.isPresent()) {
                User editEntity = entityOptional.get();
                String oldPassword = editEntity.getPassword();
                String tnxPassword = entity.getPassword();
                if(!oldPassword.equals(tnxPassword)) entity.setPassword(new BCryptPasswordEncoder().encode( entity.getPassword()));
//                editEntity.setDisplayName(entity.getDisplayName());
//                editEntity.setPhoneNumber(entity.getPhoneNumber());
//                editEntity = repository.save(editEntity);
//                return editEntity;
                entity = repository.save(entity);
            }
        }
        return entity;

    }


    public void deleteById(Long id) throws Exception {
        Optional<User> entity = repository.findById(id);

        if(entity.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new Exception("No record exist for given id");
        }
    }



}
