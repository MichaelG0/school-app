package com.capstone.schoolmanagement.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.capstone.schoolmanagement.dto.KlassDto;
import com.capstone.schoolmanagement.dto.KlassResponse;
import com.capstone.schoolmanagement.model.Klass;
import com.capstone.schoolmanagement.repos.KlassRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class KlassService {
	private final KlassRepo klsRepo;

	public Klass create(KlassDto klsDto) {
		Klass kls = new Klass();
		BeanUtils.copyProperties(klsDto, kls);
		return klsRepo.save(kls);
	}

	public Page<Klass> getAll(Optional<Integer> page, Optional<Integer> size) {
		PageRequest pgb = PageRequest.of(page.orElse(0), size.orElse(5), Sort.Direction.ASC, "id");
		return klsRepo.findAll(pgb);
	}

	public Klass getById(Long id) {
		return klsRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Class not found"));
	}
	
	public KlassResponse getByStudentId(Long id) {
		Klass klass = klsRepo.findByStudentsId(id).orElseThrow(() -> new EntityNotFoundException("Class not found"));
		return KlassResponse.buildKlassResponse(klass);
	}

	public Klass update(Long id, KlassDto klsDto) {
		Klass kls = getById(id);
		BeanUtils.copyProperties(klsDto, kls);
		return klsRepo.save(kls);
	}

	public void delete(Long id) {
		if (!klsRepo.existsById(id))
			throw new EntityNotFoundException("Class not found");
		klsRepo.deleteById(id);
	}
	
}
