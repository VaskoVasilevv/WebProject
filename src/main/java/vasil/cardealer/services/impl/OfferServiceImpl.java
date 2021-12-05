package vasil.cardealer.services.impl;

import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vasil.cardealer.error.OfferNotFoundException;
import vasil.cardealer.error.OfferWithThisVehicleAlreadyExistsException;
import vasil.cardealer.models.entity.Offer;
import vasil.cardealer.models.entity.User;
import vasil.cardealer.models.entity.Vehicle;
import vasil.cardealer.models.service.LogServiceModel;
import vasil.cardealer.models.service.OfferServiceModel;
import vasil.cardealer.repository.OfferRepository;
import vasil.cardealer.services.LogService;
import vasil.cardealer.services.OfferService;
import vasil.cardealer.services.UserService;
import vasil.cardealer.services.VehicleService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private VehicleService vehicleService;
    private OfferRepository offerRepository;
    private LogService logService;
    private UserService userService;
    private ModelMapper modelMapper;

    public OfferServiceImpl(VehicleService vehicleService, OfferRepository offerRepository, LogService logService, UserService userService, ModelMapper modelMapper) {
        this.vehicleService = vehicleService;
        this.offerRepository = offerRepository;
        this.logService = logService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void create(OfferServiceModel offerServiceModel, String username) {
        Offer offer = this.modelMapper.map(offerServiceModel, Offer.class);
        Vehicle vehicle = modelMapper.map(this.vehicleService.findById(offerServiceModel.getVehicle().getId()), Vehicle.class);

        if (offer.getVehicle().getOffer() != null) {
            throw new OfferWithThisVehicleAlreadyExistsException("Offer with this vehicle already exists");
        }
        vehicle.setUser(this.modelMapper.map(this.userService.findUserByUserName(username), User.class));


        offer.setUser(this.modelMapper.map(this.userService.findUserByUserName(username), User.class));
        offer.setVehicle(vehicle);

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(offer.getUser().getUsername());
        logServiceModel.setDescription(vehicle.getMaker() + ", " + vehicle.getModel() +
                ", " + offer.getCreatedOn() + " Add offer");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDb(logServiceModel);

        this.offerRepository.saveAndFlush(offer);
    }

    @Override
    public List<OfferServiceModel> findAll() {
        return this.offerRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferServiceModel> findOfferByUsername(String username) {
        return this.offerRepository.findAllByUserUsername(username)
                .stream()
                .map(event -> modelMapper.map(event, OfferServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public OfferServiceModel findById(String id) {
        return this.offerRepository
                .findById(id)
                .map(o -> this.modelMapper.map(o, OfferServiceModel.class))
                .orElseThrow(() -> new OfferNotFoundException("Offer with this id not found!"));
    }

    @Override
    public void deleteOffer(String id) {
        Offer offer = this.offerRepository
                .findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer with this id not found!"));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(offer.getUser().getUsername());
        logServiceModel.setDescription(offer.getVehicle().getMaker() + ", " + offer.getCreatedOn() + " Delete offer");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDb(logServiceModel);

        this.offerRepository.delete(offer);
    }

    @Override
    public OfferServiceModel editOffer(String id, OfferServiceModel offerServiceModel) {
        Offer offer = this.offerRepository.findById(id)
                .orElseThrow(() -> new OfferNotFoundException("Offer with this id not found!"));
        offer.setCreatedOn(offerServiceModel.getCreatedOn());
        offer.setValidUntil(offerServiceModel.getValidUntil());
        offer.setPrice(offerServiceModel.getPrice());
        offer.setDescription(offerServiceModel.getDescription());


        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(offer.getUser().getUsername());
        logServiceModel.setDescription(offer.getVehicle().getMaker() + ", " + offer.getCreatedOn() + " Edit offer");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.seedLogInDb(logServiceModel);

        return this.modelMapper.map(this.offerRepository.saveAndFlush(offer), OfferServiceModel.class);
    }

    @Scheduled(fixedRate = 5000000)
    private void deleteOfferIfDateIsWrong() {
        List<Offer> offers = new ArrayList<>(this.offerRepository.findAll());
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        DateTime nowDate = DateTime.parse(now);

        for (Offer offer : offers) {
            DateTime createdOn = DateTime.parse(offer.getCreatedOn());
            DateTime validUntil = DateTime.parse(offer.getValidUntil());
            if (createdOn.isAfter(validUntil) || validUntil.isBefore(nowDate)) {
                this.offerRepository.delete(offer);
            }
        }
    }
}
