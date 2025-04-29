package web.projetdevwebavancer.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Horaire;
import web.projetdevwebavancer.Entity.Restaurant;
import web.projetdevwebavancer.Entity.TableRestaurant;
import web.projetdevwebavancer.Repository.HoraireRepository;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.TableRestaurantRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    TableRestaurantRepository tableRestaurantRepository;

    @Autowired
    HoraireRepository horaireRepository;

    // updates the restaurant's details (name, address, city, latitude, and longitude) and saves the changes
    public void saveRestaurantEdit(Restaurant restaurant, String name, String address, String city, float latitude, float longitude) {
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setCity(city);
        restaurant.setLatitude(latitude);
        restaurant.setLongitude(longitude);
        restaurantRepository.save(restaurant);
    }

    // updates and saves the restaurant's schedule for each day of the week, setting opening and closing times for up to two service periods per day
    public void saveHoraireRestaurant(Horaire horaire,
                                      String lundiOpen,
                                      String lundiService1StartHour,
                                      String lundiService1StartMinute,
                                      String lundiService1EndHour,
                                      String lundiService1EndMinute,
                                      String lundiService2StartHour,
                                      String lundiService2StartMinute,
                                      String lundiService2EndHour,
                                      String lundiService2EndMinute,

                                      String mardiOpen,
                                      String mardiService1StartHour,
                                      String mardiService1StartMinute,
                                      String mardiService1EndHour,
                                      String mardiService1EndMinute,
                                      String mardiService2StartHour,
                                      String mardiService2StartMinute,
                                      String mardiService2EndHour,
                                      String mardiService2EndMinute,

                                      String mercrediOpen,
                                      String mercrediService1StartHour,
                                      String mercrediService1StartMinute,
                                      String mercrediService1EndHour,
                                      String mercrediService1EndMinute,
                                      String mercrediService2StartHour,
                                      String mercrediService2StartMinute,
                                      String mercrediService2EndHour,
                                      String mercrediService2EndMinute,

                                      String jeudiOpen,
                                      String jeudiService1StartHour,
                                      String jeudiService1StartMinute,
                                      String jeudiService1EndHour,
                                      String jeudiService1EndMinute,
                                      String jeudiService2StartHour,
                                      String jeudiService2StartMinute,
                                      String jeudiService2EndHour,
                                      String jeudiService2EndMinute,

                                      String vendrediOpen,
                                      String vendrediService1StartHour,
                                      String vendrediService1StartMinute,
                                      String vendrediService1EndHour,
                                      String vendrediService1EndMinute,
                                      String vendrediService2StartHour,
                                      String vendrediService2StartMinute,
                                      String vendrediService2EndHour,
                                      String vendrediService2EndMinute,

                                      String samediOpen,
                                      String samediService1StartHour,
                                      String samediService1StartMinute,
                                      String samediService1EndHour,
                                      String samediService1EndMinute,
                                      String samediService2StartHour,
                                      String samediService2StartMinute,
                                      String samediService2EndHour,
                                      String samediService2EndMinute,

                                      String dimancheOpen,
                                      String dimancheService1StartHour,
                                      String dimancheService1StartMinute,
                                      String dimancheService1EndHour,
                                      String dimancheService1EndMinute,
                                      String dimancheService2StartHour,
                                      String dimancheService2StartMinute,
                                      String dimancheService2EndHour,
                                      String dimancheService2EndMinute
                                      ) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        if (null != lundiOpen && "off" != lundiOpen){
            horaire.setLundi(true);
            if(!"".equals(lundiService1StartHour) && !"".equals(lundiService1StartMinute) && !"".equals(lundiService1EndHour) && !"".equals(lundiService1EndMinute)){
                String time = lundiService1StartHour + ":" + lundiService1StartMinute;
                LocalTime localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireOuvertureLundi1(localTime);
                time = lundiService1EndHour + ":" + lundiService1EndMinute;
                localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireFermetureLundi1(localTime);
                if(!"".equals(lundiService2StartHour) && !"".equals(lundiService2StartMinute) && !"".equals(lundiService2EndHour) && !"".equals(lundiService2EndMinute)){
                    time = lundiService2StartHour + ":" + lundiService2StartMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireOuvertureLundi2(localTime);
                    time = lundiService2EndHour + ":" + lundiService2EndMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireFermetureLundi2(localTime);
                } else {
                    horaire.setHoraireOuvertureLundi2(null);
                    horaire.setHoraireFermetureLundi2(null);
                }

            }
        } else { horaire.setLundi(false); }

        if (null != mardiOpen && !"off".equals(mardiOpen)){
            horaire.setMardi(true);
            if(!"".equals(mardiService1StartHour) && !"".equals(mardiService1StartMinute) && !"".equals(mardiService1EndHour) && !"".equals(mardiService1EndMinute)){
                String time = mardiService1StartHour + ":" + mardiService1StartMinute;
                LocalTime localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireOuvertureMardi1(localTime);

                time = mardiService1EndHour + ":" + mardiService1EndMinute;
                localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireFermetureMardi1(localTime);

                if(!"".equals(mardiService2StartHour) && !"".equals(mardiService2StartMinute) && !"".equals(mardiService2EndHour) && !"".equals(mardiService2EndMinute)){
                    time = mardiService2StartHour + ":" + mardiService2StartMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireOuvertureMardi2(localTime);

                    time = mardiService2EndHour + ":" + mardiService2EndMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireFermetureMardi2(localTime);
                }
            }
        } else { horaire.setMardi(false); }

        if (null != mercrediOpen && !"off".equals(mercrediOpen)){
            horaire.setMercredi(true);
            if(!"".equals(mercrediService1StartHour) && !"".equals(mercrediService1StartMinute) && !"".equals(mercrediService1EndHour) && !"".equals(mercrediService1EndMinute)){
                String time = mercrediService1StartHour + ":" + mercrediService1StartMinute;
                LocalTime localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireOuvertureMercredi1(localTime);

                time = mercrediService1EndHour + ":" + mercrediService1EndMinute;
                localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireFermetureMercredi1(localTime);

                if(!"".equals(mercrediService2StartHour) && !"".equals(mercrediService2StartMinute) && !"".equals(mercrediService2EndHour) && !"".equals(mercrediService2EndMinute)){
                    time = mercrediService2StartHour + ":" + mercrediService2StartMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireOuvertureMercredi2(localTime);

                    time = mercrediService2EndHour + ":" + mercrediService2EndMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireFermetureMercredi2(localTime);
                }
            }
        } else { horaire.setMercredi(false); }

        if (null != jeudiOpen && !"off".equals(jeudiOpen)){
            horaire.setJeudi(true);
            if(!"".equals(jeudiService1StartHour) && !"".equals(jeudiService1StartMinute) && !"".equals(jeudiService1EndHour) && !"".equals(jeudiService1EndMinute)){
                String time = jeudiService1StartHour + ":" + jeudiService1StartMinute;
                LocalTime localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireOuvertureJeudi1(localTime);

                time = jeudiService1EndHour + ":" + jeudiService1EndMinute;
                localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireFermetureJeudi1(localTime);

                if("" != jeudiService2StartHour && !"".equals(jeudiService2StartMinute) && !"".equals(jeudiService2EndHour) && !"".equals(jeudiService2EndMinute)){
                    time = jeudiService2StartHour + ":" + jeudiService2StartMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireOuvertureJeudi2(localTime);

                    time = jeudiService2EndHour + ":" + jeudiService2EndMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireFermetureJeudi2(localTime);
                }
            }
        } else { horaire.setJeudi(false); }

        if (null != vendrediOpen && !"off".equals(vendrediOpen)){
            horaire.setVendredi(true);
            if("" != vendrediService1StartHour && !"".equals(vendrediService1StartMinute) && !"".equals(vendrediService1EndHour) && !"".equals(vendrediService1EndMinute)){
                String time = vendrediService1StartHour + ":" + vendrediService1StartMinute;
                LocalTime localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireOuvertureVendredi1(localTime);

                time = vendrediService1EndHour + ":" + vendrediService1EndMinute;
                localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireFermetureVendredi1(localTime);

                if(!"".equals(vendrediService2StartHour) && !"".equals(vendrediService2StartMinute) && !"".equals(vendrediService2EndHour) && !"".equals(vendrediService2EndMinute)){
                    time = vendrediService2StartHour + ":" + vendrediService2StartMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireOuvertureVendredi2(localTime);

                    time = vendrediService2EndHour + ":" + vendrediService2EndMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireFermetureVendredi2(localTime);
                }
            }
        } else { horaire.setVendredi(false); }

        if (null != samediOpen && !"off".equals(samediOpen)){
            horaire.setSamedi(true);
            if(!"".equals(samediService1StartHour) && !"".equals(samediService1StartMinute) && !"".equals(samediService1EndHour) && !"".equals(samediService1EndMinute)){
                String time = samediService1StartHour + ":" + samediService1StartMinute;
                LocalTime localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireOuvertureSamedi1(localTime);

                time = samediService1EndHour + ":" + samediService1EndMinute;
                localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireFermetureSamedi1(localTime);

                if(!"".equals(samediService2StartHour) && !"".equals(samediService2StartMinute) && !"".equals(samediService2EndHour) && !"".equals(samediService2EndMinute)){
                    time = samediService2StartHour + ":" + samediService2StartMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireOuvertureSamedi2(localTime);

                    time = samediService2EndHour + ":" + samediService2EndMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireFermetureSamedi2(localTime);
                }
            }
        } else { horaire.setSamedi(false); }

        if (null != dimancheOpen && !"off".equals(dimancheOpen)){
            horaire.setDimanche(true);
            if(!"".equals(dimancheService1StartHour) && !"".equals(dimancheService1StartMinute) && !"".equals(dimancheService1EndHour) && !"".equals(dimancheService1EndMinute)){
                String time = dimancheService1StartHour + ":" + dimancheService1StartMinute;
                LocalTime localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireOuvertureDimanche1(localTime);

                time = dimancheService1EndHour + ":" + dimancheService1EndMinute;
                localTime = LocalTime.parse(time, formatter);
                horaire.setHoraireFermetureDimanche1(localTime);

                if(!"".equals(dimancheService2StartHour) && !"".equals(dimancheService2StartMinute) && !"".equals(dimancheService2EndHour) && !"".equals(dimancheService2EndMinute)){
                    time = dimancheService2StartHour + ":" + dimancheService2StartMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireOuvertureDimanche2(localTime);

                    time = dimancheService2EndHour + ":" + dimancheService2EndMinute;
                    localTime = LocalTime.parse(time, formatter);
                    horaire.setHoraireFermetureDimanche2(localTime);
                }
            }
        } else { horaire.setDimanche(false); }

        horaireRepository.save(horaire);

    }
}
