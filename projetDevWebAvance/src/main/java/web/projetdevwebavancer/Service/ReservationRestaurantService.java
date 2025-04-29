package web.projetdevwebavancer.Service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.projetdevwebavancer.Entity.Horaire;
import web.projetdevwebavancer.Entity.TableReserver;
import web.projetdevwebavancer.Entity.TableRestaurant;
import web.projetdevwebavancer.Entity.User;
import web.projetdevwebavancer.Repository.RestaurantRepository;
import web.projetdevwebavancer.Repository.TableReserverRepository;
import web.projetdevwebavancer.Repository.TableRestaurantRepository;
import web.projetdevwebavancer.Repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class ReservationRestaurantService {
    @Autowired
    TableReserverRepository tableReserverRepository;

    @Autowired
    TableRestaurantRepository tableRestaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    // generates a list of dates for a specified month and year
    public List<LocalDate> genererDate(int mois, int annee) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = LocalDate.of(annee, mois, 1);
        int joursDansLeMois = date.lengthOfMonth();

        for (int i = 0; i < joursDansLeMois; i++) {
            dates.add(date.plusDays(i));
        }

        return dates;
    }

    // counts the number of reserved tables for a specific restaurant, date, number of people, and start time
    public int tableReserver(LocalDate date, Long id_restaurant, int nb_personne, String horaireDebut) {
//        System.out.println("Paramètres d'entrée :");
//        System.out.println("Date : " + date);
//        System.out.println("ID Restaurant : " + id_restaurant);
//        System.out.println("Nombre de personnes : " + nb_personne);
//        System.out.println("Horaire début : " + horaireDebut);

        List<TableReserver> tablesReservees = tableReserverRepository.findAllByDateAndIdRestaurantAndNombreDePersonnes(
            date, id_restaurant, nb_personne);

        if (tablesReservees == null || tablesReservees.isEmpty()) {
           // System.out.println("Aucune table réservée récupérée.");
            return 0;
        }

       // System.out.println("Nombre de tables réservées récupérées : " + tablesReservees.size());

        int count = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        try {
            // Convertir l'heure de début en objet Date
            Date debutHeure = sdf.parse(horaireDebut);
           // System.out.println("Horaire début parsée : " + debutHeure);

            // Calculer l'heure de fin (+90 minutes)
            Calendar cal = Calendar.getInstance();
            cal.setTime(debutHeure);
            cal.add(Calendar.MINUTE, 90);
            Date finHeure = cal.getTime();
            System.out.println("Plage horaire : de " + debutHeure + " à " + finHeure);

            // Parcourir les réservations et vérifier si elles tombent dans la plage horaire
            for (TableReserver tableReserver : tablesReservees) {
//                System.out.println("Détails de la table réservée :");
//                System.out.println("ID Table : " + tableReserver.getId_table());
//                System.out.println("Horaire début de la table : " + tableReserver.getHoraireDebut());

                Date heureDebutTable = sdf.parse(tableReserver.getHoraireDebut());
                Date heureFinTable = sdf.parse(tableReserver.getHoraireFin());
                //System.out.println("Horaire début table parsée : " + heureDebutTable);

                // Vérifier si l'heure de début de la table est dans la plage horaire
                if (heureDebutTable.before(finHeure) && heureFinTable.after(debutHeure)|| heureDebutTable.before(debutHeure) && heureFinTable.after(debutHeure)) {
                    count++;
                }


            }
        } catch (ParseException e) {
           // System.out.println("Erreur lors du parsing de la date : " + e.getMessage());
            e.printStackTrace();
        }

        return count;
    }

    // filters the list of dates, removing those when the restaurant is closed according to its schedule
    public List<LocalDate> dateJourOuverture(List<LocalDate> dates, Horaire horaire) {
        List<LocalDate> datesToRemove = new ArrayList<>();
        for (LocalDate date : dates) {
            String jour = date.getDayOfWeek().toString();
            switch (jour) {
                case "MONDAY":
                    if(horaire.isLundi() == false){
                        datesToRemove.add(date);
                    }
                    break;
                case "TUESDAY":
                    if(horaire.isMardi() == false){
                        datesToRemove.add(date);
                    }
                    break;
                case "WEDNESDAY":
                    if(horaire.isMercredi() == false){
                        datesToRemove.add(date);
                    }
                    break;
                case "THURSDAY":
                    if (horaire.isJeudi() == false){
                        datesToRemove.add(date);
                    }
                    break;
                case "FRIDAY":
                    if (horaire.isVendredi() == false){
                        datesToRemove.add(date);
                    }
                    break;
                case "SATURDAY":
                    if (horaire.isSamedi() == false){
                        datesToRemove.add(date);
                    }
                    break;
                case "SUNDAY":
                    if (horaire.isDimanche() == false){
                        datesToRemove.add(date);
                    }
                    break;
                default:
                    datesToRemove.add(date);
                    break;
            }

        }
        for (LocalDate date : datesToRemove) {
            dates.remove(date);
        }
        return dates;
    }

    // returns the available reservation dates for a restaurant for a specific month, year, number of people, and start time
    public List<LocalDate> obtenirDatesAvecReservations(int mois, int annee, Long idRestaurant, int nbPersonne, String horaireDebut) {
        // Liste pour stocker les dates ayant des réservations valides
        TableRestaurant tableRestaurant = tableRestaurantRepository.findOneByRestaurantIdAndNombrePersonnesMax( Long.valueOf(idRestaurant), nbPersonne);
        Horaire horaire = tableRestaurant.getRestaurant().getHoraire();
        // Générer toutes les dates pour le mois et l'année donnés
        List<LocalDate> dates = genererDate(mois, annee);
        dates = dateJourOuverture(dates, horaire);
        List<LocalDate> datesToRemove = new ArrayList<>();
        // Parcourir chaque date et appeler la méthode tableReserver
        for (LocalDate date : dates) {
            int nombreTables = tableReserver(date, idRestaurant, nbPersonne, horaireDebut);
            if(nombreTables >= tableRestaurant.getNombreTables()) {
                datesToRemove.add(date);
            }
        }

        for (LocalDate date : datesToRemove) {
            dates.remove(date);
        }
        System.out.println(dates);
        return dates;
    }

    // checks if a table is available for a specific restaurant, date, number of people, and start time
    public boolean tableDisponible(LocalDate date, int nbPersonnes, Long idRestaurant, String horaireDebut) {
        TableRestaurant tableRestaurant = tableRestaurantRepository.findOneByRestaurantIdAndNombrePersonnesMax( idRestaurant, nbPersonnes);
        int nbTablePrise = tableReserver(date, idRestaurant, nbPersonnes, horaireDebut);
        if(nbTablePrise < tableRestaurant.getNombreTables()) {
            return true;
        }
        return false;
    }

    // confirms a table reservation for a user, creates a new reservation, and sends confirmation emails to the user and restaurant owner
    public void valideReserverTable(LocalDate date, int nbPersonnes, Long idRestaurant, String horaireDebut, User user ) throws MessagingException {
        System.out.println(idRestaurant+ " " + nbPersonnes);
        TableRestaurant tableRestaurant = tableRestaurantRepository.findOneByRestaurantIdAndNombrePersonnesMax(idRestaurant, nbPersonnes);
        TableReserver tableReserver = new TableReserver();
        tableReserver.setId_table(tableRestaurant);
        tableReserver.setIdRestaurant(idRestaurant);
        tableReserver.setDate(date);
        tableReserver.setHoraireDebut(horaireDebut);

        int heures = Integer.parseInt(horaireDebut.substring(0, 2));
        int minutes = Integer.parseInt(horaireDebut.substring(3, 5));
        minutes += 30;
        heures += 1;
        if (minutes >= 60) {
            minutes -= 60;
            heures += 1;
        }
        if (heures >= 24) {
            heures -= 24;
        }
        String horaireFin = String.format("%02d:%02d", heures, minutes);
        tableReserver.setHoraireFin(horaireFin);
        tableReserver.setNombreDePersonnes(nbPersonnes);
        tableReserver.setUser(user);
        tableReserver.setRestaurantName(tableRestaurant.getRestaurant().getName());
        user.getTableReservers().add(tableReserver);
        tableReserverRepository.save(tableReserver);
        userRepository.save(user);


        Map<String,Object> option = new HashMap<>();
        option.put("reservation", tableReserver);
        mailService.sendMail(user.getEmail(), "Confirmation de Réservation" , "emailReservation", option);
        mailService.sendMail(tableRestaurant.getRestaurant().getRestaurateur().getUser().getEmail(), "Nouvelle réservation" , "emailNouvelleReservation", option);
    }

    // generates a map of reservation statistics for the past 12 months for a specific restaurant
    public Map<String, Integer> statDeReservation(Long idRestaurant){
        Map<String, Integer> map = new LinkedHashMap<>();
        LocalDate end = LocalDate.now();
        LocalDate begining = end.minusMonths(11);
        begining = begining.withDayOfMonth(1);
        LocalDate visit = LocalDate.from(begining);

        while (!visit.isAfter(end)) {
            LocalDate fin = visit.with(TemporalAdjusters.lastDayOfMonth());
            map.put(visit.getMonth().toString(),tableReserverRepository.countAllByDateBetweenAndIdRestaurant(visit, fin, idRestaurant));
            visit = visit.plusMonths(1);

        }
        return map;
    }

}
