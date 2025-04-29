var dataRestaurant = []
var latitude = 48.866667;
var longitude = 2.333333;


document.addEventListener("DOMContentLoaded", (event) => {


    const app = Vue.createApp ({

        data() {
            return {
                restaurants: [],
                currentPage: 1,
                itemsPerPage: 3,
            };
        },

        computed: {
            paginatedRestaurants() {
                const start = (this.currentPage - 1) * this.itemsPerPage;
                const end = start + this.itemsPerPage;
                return this.restaurants.slice(start, end);
            },
            totalPages() {
                return Math.ceil(this.restaurants.length / this.itemsPerPage);
            }
        },

        methods: {
                goToPage(page) {
                    if (page >= 1 && page <= this.totalPages) {
                        this.currentPage = page;
                    }
                },
                updateRestaurants(data) {
                    this.restaurants = data; // Met à jour la liste de restaurants
                },
                initMap() {
                    var location = {lat: latitude, lng: longitude};
                    map = new google.maps.Map(document.getElementById('map'), {
                        zoom: 17,
                        center: location
                    });
                    var marker = new google.maps.Marker({
                        position: location,
                        map: map
                    });
                },
                getLocation() {
                    navigator.geolocation.getCurrentPosition(
                        (position) => {
                            latitude = position.coords.latitude;
                            longitude = position.coords.longitude;
                            initMap();
                        },
                    );
                },
                rechercheRestaurant(){
                fetch(`/locRestaurant?lat=${latitude}&lng=${longitude}`)
                    .then(response => response.json())
                    .then(data => {
                        console.log(data);
                        this.restaurants = data;
                        data.forEach(restaurant => {
                            try {
                                let pos = {lat: restaurant.latitude, lng: restaurant.longitude}
                                const marker = new google.maps.Marker({
                                    position: pos,
                                    map: map
                                });

                                const infoWindow = new google.maps.InfoWindow({
                                    content: `<div>
                                            <h6>${restaurant.name}</h6>
                                            <p>${restaurant.address || "Aucune description address."}</p>
                                            <a class="btn btn-secondary" href="/restaurant/${restaurant.id}">Voir</a>
                                          </div>`,
                                });

                                marker.addListener("click", () => {
                                    infoWindow.open(map, marker);
                                });
                            } catch {
                                console.error(restaurant);
                            }

                        });
                    })
                    .catch(error => {
                        console.error('Erreur:', error);
                    });
                }},

        template: "        <div class=\"col-lg col-md-12 mb-3 mb-md-0 mr-lg-3\" id=\"map\" style=\"height: 500px; background-color: gray\">\n" +
            "        </div>\n" +
            "        <div class=\"col-lg col-md-12\" id=\"listResto\" style=\"height: 500px;\">\n" +
            "            <div class=\"col-12 mb-3 d-flex justify-content-center\" style=\"position: absolute; top: 0px; left: 50%; transform: translateX(-50%); z-index: 1000;\">\n" +
            "                <button @click=\"getLocation()\" class=\"btn btn-primary mr-2\" style=\"height: 50px;\">Obtenir ma position</button>\n" +
            "                <button @click='rechercheRestaurant()' class='btn btn-secondary' style='height: 50px;'>Actualiser</button>"+
            "                <div id=\"app2\">\n" +
            "                </div>\n" +
            "            </div>\n" +
            "            <div class=\"mt-5\">\n" +
            "                <ul>\n" +
            "                    <li  v-for=\"restaurant in paginatedRestaurants\" :key=\"restaurant.id\">\n" +
            "                        <div class=\"col p-2 d-flex flex-column position-static\">\n" +
            "                            <strong class=\"d-inline-block mb-2 text-primary-emphasis\">{{ restaurant.name }}</strong>\n" +
            "                            <p>{{ restaurant.address }}</p>\n" +
            "                            <a :href=\"'/restaurant/' + restaurant.id\">Voir</a>\n" +
            "                        </div>\n" +
            "                    </li>\n" +
            "\n" +
            "                </ul>\n" +
            "                <div>\n" +
            "                    <button\n" +
            "                            class=\"btn m-1\"\n" +
            "                            v-for=\"page in totalPages\"\n" +
            "                            :key=\"page\"\n" +
            "                            @click=\"goToPage(page)\"\n" +
            "                            :class=\"{ 'btn-primary': page !== currentPage, 'btn-secondary': page === currentPage }\">\n" +
            "                        {{ page }}\n" +
            "                    </button>\n" +
            "                </div>\n" +
            "            </div>"


    });
    const vueInstance = app.mount("#app");





    // const app = Vue.createApp({
    //     data() {
    //         return {
    //             restaurants: [],
    //             currentPage: 1,
    //             itemsPerPage: 3
    //         };
    //     },
    //     computed: {
    //         paginatedRestaurants() {
    //             const start = (this.currentPage - 1) * this.itemsPerPage;
    //             const end = start + this.itemsPerPage;
    //             return this.restaurants.slice(start, end);
    //         },
    //         totalPages() {
    //             return Math.ceil(this.restaurants.length / this.itemsPerPage);
    //         }
    //     },
    //     methods: {
    //         goToPage(page) {
    //             if (page >= 1 && page <= this.totalPages) {
    //                 this.currentPage = page;
    //             }
    //         },
    //         updateRestaurants(data) {
    //             this.restaurants = data; // Met à jour la liste de restaurants
    //         }
    //     }
    // });
    // const vueInstance = app.mount("#app"); // Monte une seule fois
    // let map
    // const app2 = Vue.createApp({
    //     methods: {
    //     rechercheRestaurant(){
    //     fetch(`/locRestaurant?lat=${latitude}&lng=${longitude}`)
    //         .then(response => response.json())
    //         .then(data => {
    //             vueInstance.updateRestaurants(data);
    //             console.log(data);
    //             data.forEach(restaurant => {
    //                 try {
    //                     let pos = {lat: restaurant.latitude, lng: restaurant.longitude}
    //                     const marker = new google.maps.Marker({
    //                         position: pos,
    //                         map: map
    //                     });
    //
    //                     const infoWindow = new google.maps.InfoWindow({
    //                         content: `<div>
    //                                 <h6>${restaurant.name}</h6>
    //                                 <p>${restaurant.address || "Aucune description address."}</p>
    //                                 <a class="btn btn-secondary" href="/restaurant/${restaurant.id}">Voir</a>
    //                               </div>`,
    //                     });
    //
    //                     marker.addListener("click", () => {
    //                         infoWindow.open(map, marker);
    //                     });
    //                 } catch {
    //                     console.error(restaurant);
    //                 }
    //
    //             });
    //         })
    //         .catch(error => {
    //             console.error('Erreur:', error);
    //         });
    //     }},
    //     template: "<button @click='rechercheRestaurant()' class='btn btn-secondary' style='height: 50px;'>Actualiser</button>"
    // });
    // const vueInstance2 = app2.mount("#app2"); // Monte une seule fois


    // function rechercheRestaurant(){
    //     fetch(`/locRestaurant?lat=${latitude}&lng=${longitude}`)
    //         .then(response => response.json())
    //         .then(data => {
    //             vueInstance.updateRestaurants(data);
    //             data.forEach(restaurant => {
    //                 try {
    //                     var pos = {lat: restaurant.latitude, lng: restaurant.longitude}
    //                     const marker = new google.maps.Marker({
    //                         position: pos,
    //                         map: map
    //                     });
    //
    //                     const infoWindow = new google.maps.InfoWindow({
    //                         content: `<div>
    //                                 <h6>${restaurant.name}</h6>
    //                                 <p>${restaurant.address || "Aucune description address."}</p>
    //                                 <a class="btn btn-secondary" href="/restaurant/${restaurant.id}">Voir</a>
    //                               </div>`,
    //                     });
    //
    //                     marker.addListener("click", () => {
    //                         infoWindow.open(map, marker);
    //                     });
    //                 } catch {
    //                     console.error(restaurant);
    //                 }
    //
    //             });
    //         })
    //         .catch(error => {
    //             console.error('Erreur:', error);
    //         });
    // }


});

function initMap() {
    var location = {lat: latitude, lng: longitude};
    map = new google.maps.Map(document.getElementById('map'), {
        zoom: 17,
        center: location
    });
    var marker = new google.maps.Marker({
        position: location,
        map: map
    });
}