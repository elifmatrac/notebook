package com.dogus.notebook.service;

import java.util.List;

import com.dogus.notebook.service.dto.NoteCreateInputDTO;
import com.dogus.notebook.service.dto.NoteGetOutputDTO;
import com.dogus.notebook.service.dto.NoteUpdateInputDTO;

public interface NoteService {

    NoteGetOutputDTO save(NoteCreateInputDTO input);

    NoteGetOutputDTO get(Long id);

    void delete(Long id);

    List<NoteGetOutputDTO> list();

    NoteGetOutputDTO update(Long id, NoteUpdateInputDTO input);
}
