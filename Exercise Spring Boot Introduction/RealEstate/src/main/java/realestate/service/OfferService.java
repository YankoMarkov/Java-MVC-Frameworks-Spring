package realestate.service;

import realestate.domain.dtos.bindings.OfferFindDto;
import realestate.domain.dtos.bindings.OfferRegisterDto;
import realestate.domain.dtos.view.OfferViewDto;

import java.util.List;

public interface OfferService {
	
	void saveOffer(OfferRegisterDto offerRegister);
	
	List<OfferViewDto> findAllOffers();
	
	List<OfferViewDto> getAllOfferByApartmentTypeAndBudget(OfferFindDto offerFind);
}
