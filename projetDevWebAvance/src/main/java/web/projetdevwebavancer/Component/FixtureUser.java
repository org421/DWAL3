//package web.projetdevwebavancer.Component;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import web.projetdevwebavancer.Entity.Restaurant;
//import web.projetdevwebavancer.Entity.Restaurateur;
//import web.projetdevwebavancer.Entity.User;
//import web.projetdevwebavancer.Repository.RestaurantRepository;
//import web.projetdevwebavancer.Repository.RestaurateurRepository;
//import web.projetdevwebavancer.Repository.UserRepository;
//import web.projetdevwebavancer.Service.RestaurantService;
//import web.projetdevwebavancer.Service.RestaurateurService;
//
//import java.util.Random;
//
//@Component
//public class FixtureUser implements CommandLineRunner {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RestaurateurService restaurateurService;
//
//    @Autowired
//    private RestaurateurRepository restaurateurRepository;
//
//    @Autowired
//    private RestaurantRepository restaurantRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (userRepository.count() == 0) {
//            userRepository.deleteAll();
//            User user = new User();
//            user.setEnabled(true);
//            user.setNom("cyp");
//            user.setPrenom("cyp");
//            user.setEmail("cyp.muyard@gmail.com");
//            user.setTel("0123456789");
//            user.setMdp("$2a$10$62/TT31oMgtXuUi1/jBwZ.0755VNlfpgE/pTbhxKOYjIBhJsguPiK");
//            user.setRole("ROLE_USER");
//            userRepository.save(user);
//
//            User user2 = new User();
//            user2.setEnabled(true);
//            user2.setNom("Evrard");
//            user2.setPrenom("Valentin");
//            user2.setEmail("ecrard2715@gmail.com");
//            user2.setTel("0123456789");
//            user2.setMdp("$2a$10$0d90vw0HcTBcSL8YFw5GIOeZcKXQ70NRho0/IULQ.lsf6L2DF3GL2");
//            user2.setRole("ROLE_ADMIN");
//            userRepository.save(user2);
//
//
//            User user3 = new User();
//            user3.setEnabled(true);
//            user3.setNom("YILDIZ");
//            user3.setPrenom("Tolga");
//            user3.setEmail("y1903tolga@gmail.com");
//            user3.setTel("0652099131");
//            user3.setMdp("$2a$10$/4hJ0PUlk6phSK0e2vBPnue5/cGnYVvzpC8k3vnTZQrmLlCzGy68G");
//            user3.setRole("ROLE_ADMIN");
//            userRepository.save(user3);
//
//
//            for (int i = 1; i <= 200; i++) {
//                user = new User();
//                user.setEnabled(true);
//                user.setNom("TestUser" + i);
//                user.setPrenom("Test" + i);
//                user.setEmail("testuser" + i + "@example.com");
//                user.setTel("01020304" + (10 + i));
//                user.setMdp("$2a$10$62/TT31oMgtXuUi1/jBwZ.0755VNlfpgE/pTbhxKOYjIBhJsguPiK"); // Même hash bcrypt
//                user.setRole("ROLE_USER");
//                userRepository.save(user);
//                Restaurateur restaurateur = new Restaurateur();
//                restaurateur.setSiret("123");
//                restaurateurRepository.save(restaurateur);
//                restaurateurService.createRestaurateur(restaurateur, user);
//                modifRestaurant(user);
//            }
//        }
//    }
//
//
//    private void modifRestaurant(User user) {
//        Restaurant restaurant = user.getRestaurateur().getRestaurant();
//        String randomName = generateRandomString(10);
//        restaurant.setName(randomName);
//        restaurant.setAddress("test");
//        float randomLatitude = 44 + new Random().nextFloat() * (46 - 44);
//        float randomLongitude = 3 + new Random().nextFloat() * (5 - 3);
//        restaurant.setLatitude(randomLatitude);
//        restaurant.setLongitude(randomLongitude);
//        restaurantRepository.save(restaurant);
//    }
//
//    private String generateRandomString(int length) {
//        String characters = "abcdefghijklmnopqrstuvwxyz";
//        StringBuilder randomString = new StringBuilder(length);
//        Random random = new Random();
//        for (int i = 0; i < length; i++) {
//            randomString.append(characters.charAt(random.nextInt(characters.length())));
//        }
//        return randomString.toString();
//    }
//}
