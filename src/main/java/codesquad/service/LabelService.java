package codesquad.service;

import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.dto.LabelDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public List<LabelDto> findAll() {
        return labelRepository.findAll().stream().map(Label::_toLabelDto).collect(Collectors.toList());
    }

    public Label findById(Long labelId) {
        return labelRepository.findById(labelId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void add(LabelDto labelDto) {
        labelRepository.save(labelDto._toLabel());
    }
}
