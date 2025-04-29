// const app = Vue.createApp({
//      data() {
//          return {
//              message: "Bonjour depuis app.js avec Vue.js !"
//          };
//      }
// });
//
// app.mount("#app");
document.addEventListener("DOMContentLoaded", (event) => {
    let searchBar = document.getElementById("searchBar");
    searchBar.addEventListener("keyup",  () => {
        clearChild();

        if(searchBar.value !== "") {
            searchRestaurants(searchBar.value);
        }
    });
});



function searchRestaurants(name) {
    console.log(name);
    let result = document.getElementById("resResto");
    clearChild();
    fetch(`/searchRestaurant?name=${name}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            data.forEach(restaurant => {
                try {
                    console.log(restaurant);
                    const newP = document.createElement("a");
                    const br = document.createElement("br");
                    newP.href = "/restaurant/"+restaurant.id;
                    newP.textContent = restaurant.name;
                    result.appendChild(newP);
                    result.appendChild(br);
                } catch {
                    console.error(restaurant);
                }

            });
        })
        .catch(error => {
            console.error('Erreur:', data);
        });
}

function clearChild(){
    let result = document.getElementById("resResto");
    while (result.firstChild) {
        result.removeChild(result.firstChild);
    }
}

