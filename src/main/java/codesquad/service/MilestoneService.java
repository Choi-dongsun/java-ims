package codesquad.service;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.dto.MilestoneDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    @Transactional
    public void add(MilestoneDto milestoneDto) {
        milestoneRepository.save(milestoneDto._toMilestone());
    }

    public List<MilestoneDto> findAll() {
        return milestoneRepository.findAll()
                .stream()
                .map(Milestone::_toMilestoneDto)
                .collect(Collectors.toList());
    }

    public Milestone findById(Long milestoneId) {
        return milestoneRepository.findById(milestoneId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
