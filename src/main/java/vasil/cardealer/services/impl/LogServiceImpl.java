package vasil.cardealer.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vasil.cardealer.models.entity.Log;
import vasil.cardealer.models.service.LogServiceModel;
import vasil.cardealer.repository.LogRepository;
import vasil.cardealer.services.LogService;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public LogServiceModel seedLogInDb(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        return this.modelMapper.map(this.logRepository.saveAndFlush(log),LogServiceModel.class);
    }
}
