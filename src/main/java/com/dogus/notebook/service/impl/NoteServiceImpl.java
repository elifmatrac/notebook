package com.dogus.notebook.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dogus.notebook.repository.NoteRepository;
import com.dogus.notebook.repository.UserRepository;
import com.dogus.notebook.repository.entity.Note;
import com.dogus.notebook.repository.entity.User;
import com.dogus.notebook.service.NoteService;
import com.dogus.notebook.service.UserService;
import com.dogus.notebook.service.dto.NoteCreateInputDTO;
import com.dogus.notebook.service.dto.NoteGetOutputDTO;
import com.dogus.notebook.service.dto.NoteUpdateInputDTO;

@Service
public class NoteServiceImpl implements NoteService {

    @Autowired NoteRepository noteRepository;
    @Autowired UserRepository userRepository;
    @Autowired UserService userService;

    @Override
    public NoteGetOutputDTO save(NoteCreateInputDTO input) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User loggedInUser = userRepository.findByEmail(user.getUsername()).get();
        Note noteToCreate = new Note();
        noteToCreate.setDate(LocalDateTime.now().toLocalDate());
        noteToCreate.setTitle(input.getTitle());
        noteToCreate.setUser(loggedInUser);
        noteToCreate.setNoteDescription(input.getNoteDescription());

        Note createdNote = noteRepository.save(noteToCreate);

        NoteGetOutputDTO output = new NoteGetOutputDTO();
        output.setId(createdNote.getId());
        output.setDate(createdNote.getDate());
        output.setTitle(createdNote.getTitle());
        output.setNoteDescription(createdNote.getNoteDescription());
        return output;
    }

    @Override
    public NoteGetOutputDTO get(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Note is not found");
        });
        NoteGetOutputDTO output = new NoteGetOutputDTO();
        output.setId(note.getId());
        output.setDate(note.getDate());
        output.setTitle(note.getTitle());
        output.setNoteDescription(note.getNoteDescription());
        return output;
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public List<NoteGetOutputDTO> list() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        User loggedInUser = userRepository.findByEmail(user.getUsername()).get();
        List<Note> notes = noteRepository.findAll().stream()
                .filter(note -> note.getUser().equals(loggedInUser))
                .collect(Collectors.toList());
        List<NoteGetOutputDTO> outputs = notes.stream().map(note -> {
            NoteGetOutputDTO output = new NoteGetOutputDTO();
            output.setId(note.getId());
            output.setDate(note.getDate());
            output.setTitle(note.getTitle());
            output.setNoteDescription(note.getNoteDescription());
            return output;
        }).collect(Collectors.toList());
        return outputs;
    }

    @Override
    public NoteGetOutputDTO update(Long id, NoteUpdateInputDTO input) {
        Note noteToUpdate = noteRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("Note is not found");
        });

        noteToUpdate.setTitle(input.getTitle());
        noteToUpdate.setNoteDescription(input.getNoteDescription());
        Note updatedNote = noteRepository.save(noteToUpdate);

        NoteGetOutputDTO output = new NoteGetOutputDTO();
        output.setId(updatedNote.getId());
        output.setDate(updatedNote.getDate());
        output.setTitle(updatedNote.getTitle());
        output.setNoteDescription(updatedNote.getNoteDescription());
        return output;
    }
}
