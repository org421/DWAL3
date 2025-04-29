

document.addEventListener("DOMContentLoaded", (event) => {
        const appfacture = Vue.createApp({
            data() {
                return {
                    message: "Voici vos factures",
                    factures: facturesData, // Les factures reçues de Thymeleaf
                };
            },
        });

        appfacture.mount("#appfacture");

})
