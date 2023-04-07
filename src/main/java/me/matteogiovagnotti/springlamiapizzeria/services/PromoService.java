package me.matteogiovagnotti.springlamiapizzeria.services;

import me.matteogiovagnotti.springlamiapizzeria.exceptions.PromoNotFoundException;
import me.matteogiovagnotti.springlamiapizzeria.models.Promo;
import me.matteogiovagnotti.springlamiapizzeria.repositories.PromoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromoService {

    @Autowired
    private PromoRepository promoRepository;

    public Promo create(Promo formPromo) {

        return promoRepository.save(formPromo);

    }

    public Promo getById(Integer id) throws PromoNotFoundException {

        return promoRepository.findById(id).orElseThrow(() -> new PromoNotFoundException(Integer.toString(id)));

    }

    public Promo update(Integer id, Promo formPromo) throws PromoNotFoundException {

        Promo promoToUpdate = getById(id);
        promoToUpdate.setTitle(formPromo.getTitle());
        promoToUpdate.setBeginDate(formPromo.getBeginDate());
        promoToUpdate.setEndDate(formPromo.getEndDate());
        return promoRepository.save(promoToUpdate);

    }

    public void delete(Integer promoId) throws PromoNotFoundException {

        Promo promoToDelete = getById(promoId);
        promoRepository.delete(promoToDelete);

    }

}
