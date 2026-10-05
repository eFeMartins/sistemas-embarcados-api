package br.edu.uva.sistemasembarcados.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.uva.sistemasembarcados.model.Subject;
import br.edu.uva.sistemasembarcados.repositories.SubjectRepository;
import br.edu.uva.sistemasembarcados.services.execptions.DuplicateResourceException;
import br.edu.uva.sistemasembarcados.services.execptions.ResourceNotFoundException;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Transactional(readOnly = true)
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("subject not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Subject findByCode(String code) {
        return subjectRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("subject not found with code: " + code));
    }

    @Transactional
    public Subject save(Subject subject) {
        if (subjectRepository.findByCode(subject.getCode()).isPresent()) {
            throw new DuplicateResourceException("subject code already exists: " + subject.getCode());
        }
        return subjectRepository.save(subject);
    }

    @Transactional
    public Subject update(Long id, Subject subjectDetails) {
        Subject existingSubject = findById(id);

        if (subjectDetails.getCode() != null && !subjectDetails.getCode().equals(existingSubject.getCode())) {
            subjectRepository.findByCode(subjectDetails.getCode()).ifPresent(found -> {
                if (!found.getId().equals(id)) {
                    throw new DuplicateResourceException("subject code already belongs to another subject: " + subjectDetails.getCode());
                }
            });
            existingSubject.setCode(subjectDetails.getCode());
        }

        if (subjectDetails.getName() != null) {
            existingSubject.setName(subjectDetails.getName());
        }

        return subjectRepository.save(existingSubject);
    }

    @Transactional
    public void deleteById(Long id) {
        Subject subject = findById(id);
        subjectRepository.delete(subject);
    }
}
