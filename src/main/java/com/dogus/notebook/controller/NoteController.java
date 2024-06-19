package com.dogus.notebook.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dogus.notebook.controller.request.RequestCreateNote;
import com.dogus.notebook.controller.request.RequestUpdateNote;
import com.dogus.notebook.controller.response.ResponseGetNote;
import com.dogus.notebook.repository.entity.Note;
import com.dogus.notebook.service.NoteService;
import com.dogus.notebook.service.dto.NoteCreateInputDTO;
import com.dogus.notebook.service.dto.NoteGetOutputDTO;
import com.dogus.notebook.service.dto.NoteUpdateInputDTO;

@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired NoteService noteService;

    @PostMapping
    public ResponseEntity<ResponseGetNote> create(@RequestBody RequestCreateNote request) {

        NoteCreateInputDTO input = new NoteCreateInputDTO();
        BeanUtils.copyProperties(request, input);

        NoteGetOutputDTO output = noteService.save(input);

        ResponseGetNote response = new ResponseGetNote();
        BeanUtils.copyProperties(output, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGetNote> get(@PathVariable Long id) {
        NoteGetOutputDTO output = noteService.get(id);
        ResponseGetNote response = new ResponseGetNote();
        BeanUtils.copyProperties(output, response);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseGetNote> update(@PathVariable("id") Long id, @RequestBody RequestUpdateNote request) {
        NoteUpdateInputDTO input = new NoteUpdateInputDTO();
        BeanUtils.copyProperties(request, input);
        NoteGetOutputDTO output = noteService.update(id, input);
        ResponseGetNote response = new ResponseGetNote();
        BeanUtils.copyProperties(output, response);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseGetNote>> list() {
        List<NoteGetOutputDTO> notes = noteService.list();
        List<ResponseGetNote> outputs = notes.stream().map (output -> {
                ResponseGetNote response = new ResponseGetNote();
                BeanUtils.copyProperties(output, response);
                return response;
            }).collect(Collectors.toList());
        return new ResponseEntity<>(outputs, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        try {
            noteService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
