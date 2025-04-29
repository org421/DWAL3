let selectedmois = null;
let selectedannee = null;
let calendar = null;


function getMois(mois){{
    switch (mois) {
        case "January": return 1
        case "February": return 2
        case "March": return 3
        case "April": return 4
        case "May": return 5
        case "June": return 6
        case "July": return 7
        case "August": return 8
        case "September": return 9
        case "October": return 10
        case "November": return 11
        case "December": return 12
    }
}}

function categoryPourResto(action) {
    document.querySelectorAll('.section').forEach(section => section.classList.add('d-none'));
    if (action === 'commander') {
        document.getElementById("commander-section").classList.remove('d-none');
    } else if (action === 'reserver') {
        document.getElementById("reserver-section").classList.remove('d-none');

        if (calendar) {
            calendar.destroy();
        }

        const calendarEl = document.getElementById('calendar');
        calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            selectable: true,
            headerToolbar: {
                left: 'prev,next',
                center: 'title',
                right: ''
            },
            dateClick: function(info) {
                document.getElementById("selectedDate").innerText = info.dateStr;
                document.getElementById("dateFormulaire").value = info.dateStr;
            },
        });
        calendar.render();

    } else if (action === 'post') {
        document.getElementById("post-section").classList.remove('d-none');
    } else if (action === 'offre') {
        document.getElementById("offre-section").classList.remove('d-none');
    }
}

function confirmReservation() {
    const numPeople = document.getElementById("numPeople").value;
    const horraire = document.getElementById("horraireReservation").value;
    const selectedDate = document.getElementById("selectedDate").textContent;

    const url = window.location.href;
    const parts = url.split("/");
    const restaurantId = parts[parts.length - 1];

    // Validation des champs
    if (!numPeople || !horraire || !selectedDate || isNaN(restaurantId)) {
        alert("Veuillez sélectionner toutes les valeurs nécessaires !");
        return;
    }


    fetch(`/reservationTable?id=${restaurantId}&nombrePersonne=${numPeople}&date=${selectedDate}&horaire=${horraire}`, {
        method: "GET"
    }).then(response => {
        if (response.ok) {
            console.log("Réservation réussie !");
        } else {
            console.logalert("Erreur lors de la réservation !");
        }
    }).catch(err => {
        console.error("Erreur :", err);
    });
}

document.addEventListener("DOMContentLoaded", function() {
    // Appel initial pour afficher la section de réservation par défaut
    categoryPourResto('reserver');

    const numPeople = document.getElementById("numPeople");
    const horraire = document.getElementById("horraireReservation");
    const restaurantId = window.location.pathname.split('/').pop(); // ID du restaurant extrait de l'URL
    const nextButton = document.getElementsByClassName("fc-next-button")[0];
    const prevButton = document.getElementsByClassName("fc-prev-button")[0];
    console.log(nextButton);
    console.log(prevButton);

    let selectedPeople = null;
    let selectedHoraire = null;


    function changeDate(){
        const dateString = document.getElementById("fc-dom-1");
        gridCells = document.querySelectorAll('[role="gridcell"]');
        gridCells.forEach(cell => {
            cell.style.background = "lightcoral";
        });
        moisEtAnnee = dateString.childNodes[0].nodeValue;
        listString = moisEtAnnee.split(" ");
        selectedmois = getMois(listString[0]);
        selectedannee = listString[1];
    }
    changeDate();
    // Vérifie si les deux champs sont valides et fait l'Ajax si c'est le cas
    function checkAndFetchReservation() {
        if (selectedPeople && selectedHoraire) {
            if (selectedmois === null || selectedannee === null) {
                alert("Veuillez sélectionner une date dans le calendrier !");
                return;
            }
            gridCells = document.querySelectorAll('[role="gridcell"]');
            gridCells.forEach(cell => {
                cell.style.background = "lightcoral";
            });
            fetch(`/reservationTable?id=${restaurantId}&nombrePersonne=${selectedPeople}&horraireTable=${selectedHoraire}&moiscalendrier=${selectedmois}&anneecalendrier=${selectedannee}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    data.forEach(date => {
                        caseDate = document.querySelector(`[data-date="${date}"]`);
                        caseDate.style.background = "lightgreen";
                    })
                })
                .catch(error => {
                    console.error('Erreur:', error);
                });
        }
    }

    // écouteur pour le nombre de personnes
    numPeople.addEventListener("change", () => {
        selectedPeople = numPeople.value;
        if (!selectedPeople) {
            console.error("Veuillez sélectionner un nombre de personnes !");
        } else {
            console.log(`Nombre de personnes sélectionné : ${selectedPeople}`);
            checkAndFetchReservation();
        }
    });

    // écouteur pour l'horaire
    horraire.addEventListener("change", () => {
        selectedHoraire = horraire.value;
        if (!selectedHoraire) {
            console.error("Veuillez sélectionner un horaire !");
        } else {
            console.log(`Horaire sélectionné : ${selectedHoraire}`);
            checkAndFetchReservation();
        }
    });

    // écouteur pour buttonNext
    nextButton.addEventListener("click", () => {
        changeDate();
        if (selectedPeople && selectedHoraire){
            checkAndFetchReservation();
        }
    })

    prevButton.addEventListener("click", () =>{
        changeDate();
        if (selectedPeople && selectedHoraire){
            checkAndFetchReservation();
        }
    })
    console.log(carte);
    const app = Vue.createApp ({
        data() {
            console.log(carte.categories)
            return {
                categories: carte.categories,
                menus: "",
                plats: "",
                platsCarte: "",
                selectedCategory: "",
            };
        },

        methods: {
            selectCategory(categorie) {
                this.selectedCategory = categorie;
                console.log(Vue.toRaw(categorie.menues));
                this.menus = Vue.toRaw(categorie.menues);
                this.platsCarte = categorie.plats;
            },
            selectMenu(menu) {
                this.plats = menu.plats;
            },
            retirerMenu(idMenu){
                uri = "/remove-panier-menu/" + idMenu;
                fetch(uri)
                    .then(response => response.json())
                    .catch(error => {
                        console.error("Erreur lors du remove au panier");
                    });

            },
            ajouterMenu(idMenu){
                uri = "/add-panier-menu/" + idMenu;
                fetch(uri)
                    .then(response => response.json())
                    .catch(error => {
                        console.error("Erreur lors de l'ajout au panier");
                    });
            }
        },
    });
    const vueInstance = app.mount("#app");

});
