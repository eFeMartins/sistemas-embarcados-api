package br.edu.uva.sistemasembarcados.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.uva.sistemasembarcados.model.Classroom;
import br.edu.uva.sistemasembarcados.repositories.ClassroomRepository;
import br.edu.uva.sistemasembarcados.services.execptions.DuplicateResourceException;
import br.edu.uva.sistemasembarcados.services.execptions.ResourceNotFoundException;

@Service
public class ClassroomService {

    private final ClassroomRepository classroomRepository;

    @Autowired
    public ClassroomService(ClassroomRepository classroomRepository) {
        this.classroomRepository = classroomRepository;
    }

    @Transactional(readOnly = true)
    public List<Classroom> findAll() {
        return classroomRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Classroom findById(Long id) {
        return classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public Classroom findByBoardId(String boardId) {
        return classroomRepository.findByBoardId(boardId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with Board ID: " + boardId));
    }

    @Transactional(readOnly = true)
    public Classroom findByRoomNumber(String roomNumber) {
        return classroomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom not found with room number: " + roomNumber));
    }

    @Transactional
    public Classroom save(Classroom classroom) {
        if (classroomRepository.findByRoomNumber(classroom.getRoomNumber()).isPresent()) {
            throw new DuplicateResourceException("Room number already exists: " + classroom.getRoomNumber());
        }

        if (classroomRepository.findByBoardId(classroom.getBoardId()).isPresent()) {
            throw new DuplicateResourceException("Board ID already exists: " + classroom.getBoardId());
        }

        return classroomRepository.save(classroom);
    }

    @Transactional
    public Classroom update(Long id, Classroom classroomDetails) {
        Classroom existingClassroom = findById(id);

        if (classroomDetails.getRoomNumber() != null && !classroomDetails.getRoomNumber().equals(existingClassroom.getRoomNumber())) {
            classroomRepository.findByRoomNumber(classroomDetails.getRoomNumber()).ifPresent(found -> {
                if (!found.getId().equals(id)) {
                    throw new DuplicateResourceException("Room number already belongs to another classroom: " + classroomDetails.getRoomNumber());
                }
            });
            existingClassroom.setRoomNumber(classroomDetails.getRoomNumber());
        }

        if (classroomDetails.getBoardId() != null && !classroomDetails.getBoardId().equals(existingClassroom.getBoardId())) {
            classroomRepository.findByBoardId(classroomDetails.getBoardId()).ifPresent(found -> {
                if (!found.getId().equals(id)) {
                    throw new DuplicateResourceException("Board ID already belongs to another classroom: " + classroomDetails.getBoardId());
                }
            });
            existingClassroom.setBoardId(classroomDetails.getBoardId());
        }

        return classroomRepository.save(existingClassroom);
    }

    @Transactional
    public Classroom updateBoardId(Long id, String newBoardId) {
        Classroom existingClassroom = findById(id);

        if (newBoardId != null && !newBoardId.equals(existingClassroom.getBoardId())) {
            classroomRepository.findByBoardId(newBoardId).ifPresent(found -> {
                if (!found.getId().equals(id)) {
                    throw new DuplicateResourceException("Board ID already belongs to another classroom: " + newBoardId);
                }
            });
            existingClassroom.setBoardId(newBoardId);
        }

        return classroomRepository.save(existingClassroom);
    }

    @Transactional
    public void deleteById(Long id) {
        Classroom classroom = findById(id);
        classroomRepository.delete(classroom);
    }
}