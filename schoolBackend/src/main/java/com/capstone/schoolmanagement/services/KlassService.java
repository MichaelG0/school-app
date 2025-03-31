package com.capstone.schoolmanagement.services;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.KlassDto;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.repos.KlassRepo;
import com.capstone.schoolmanagement.responses.KlassResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KlassService {
  private final KlassRepo klsRepo;

  public Klass create(KlassDto klsDto) {
    Klass klass = new Klass();
    BeanUtils.copyProperties(klsDto, klass);
    return klsRepo.save(klass);
  }

  public Page<KlassResponse> getAll(Optional<Integer> page, Optional<Integer> size) {
    PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, "id");
    Page<Klass> klassesPage = klsRepo.findAll(pgb);
    List<KlassResponse> klasses = klassesPage.stream().map(KlassResponse::buildKlassResponse).toList();
    return new PageImpl<KlassResponse>(klasses, pgb, klassesPage.getTotalElements());
  }

  public Klass getById(Long id) {
    return klsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Class not found"));
  }

  public KlassResponse getByStudentId(Long id) {
    Klass klass = klsRepo.findByStudentsId(id).orElseThrow(() -> new EntityNotFoundException("Class not found"));
    return KlassResponse.buildKlassResponse(klass);
  }

  public List<KlassResponse> getByTeacherId(Long id) {
    List<Klass> klasses = klsRepo.findByTeachersTeacherId(id).orElseThrow(() -> new EntityNotFoundException("Class not found"));
    return klasses.stream().map(KlassResponse::buildKlassResponse).toList();
  }

  public KlassResponse update(Long id, KlassDto klsDto) {
    Klass klass = klsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Class not found"));
    BeanUtils.copyProperties(klsDto, klass);
    klsRepo.save(klass);
    return KlassResponse.buildKlassResponse(klass);
  }

  public void delete(Long id) {
    if (!klsRepo.existsById(id))
      throw new EntityNotFoundException("Class not found");
    klsRepo.deleteById(id);
  }
}
