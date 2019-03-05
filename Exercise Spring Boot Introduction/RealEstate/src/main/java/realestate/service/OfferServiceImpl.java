package realestate.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import realestate.domain.dtos.bindings.OfferFindDto;
import realestate.domain.dtos.bindings.OfferRegisterDto;
import realestate.domain.dtos.view.OfferViewDto;
import realestate.domain.entities.Offer;
import realestate.repository.OfferRepository;

import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final Validator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, Validator validator, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveOffer(OfferRegisterDto offerRegister) {
        if (this.validator.validate(offerRegister).size() != 0) {
            throw new IllegalArgumentException("Something is wrong");
        }
        Offer offer = this.modelMapper.map(offerRegister, Offer.class);
        this.offerRepository.saveAndFlush(offer);
    }

    @Override
    public List<OfferViewDto> findAllOffers() {
        return this.offerRepository.findAll().stream()
                .map(offer -> this.modelMapper.map(offer, OfferViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferViewDto> getAllOfferByApartmentTypeAndBudget(OfferFindDto offerFind) {
        if (this.validator.validate(offerFind).size() != 0) {
            throw new IllegalArgumentException("Something is wrong");
        }
        List<Offer> offers = this.offerRepository.findAll();
        List<OfferViewDto> offerViewDtos = offers.stream()
                .filter(offer -> offer.getApartmentType().equalsIgnoreCase(offerFind.getApartmentType()))
                .filter(offer -> {
                    BigDecimal total = offer.getApartmentRent().add(offer.getApartmentRent().multiply(offer.getAgencyCommission().divide(BigDecimal.valueOf(100))));
                    return offerFind.getFamilyBudget().compareTo(total) >= 0;
                })
                .map(offer -> this.modelMapper.map(offer, OfferViewDto.class))
                .collect(Collectors.toList());
        if (offerViewDtos == null) {
            throw new IllegalArgumentException("Something is wrong");
        }
        return offerViewDtos;
    }
}
