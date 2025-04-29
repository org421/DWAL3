document.addEventListener("DOMContentLoaded", () => {
    const appfacture = Vue.createApp({
        data() {
            return {
                message: "Mes demandes",
                tickets: allticket,
                filteredTickets: [], // ticket filtred after the filtre
                currentPage: 1,
                ticketsPerPage: 5
            };
        },
        computed: {
            paginatedTickets() {
                const start = (this.currentPage - 1) * this.ticketsPerPage;
                const end = start + this.ticketsPerPage;
                return this.filteredTickets.slice(start, end);
            },
            maxPage() {
                return Math.ceil(this.filteredTickets.length / this.ticketsPerPage);
            }
        },
        methods: {
            getStatus(status) {
                return status === 1 ? "Ouvert" : "Fermé";
            },
            getPrioriter(prioriter) {
                switch (prioriter) {
                    case 0:
                        return "Faible";
                    case 1:
                        return "Moyenne";
                    case 2:
                        return "Élevée";
                    default:
                        return "Inconnue";
                }
            },
            formatDate(date) {
                if (!date) return "Non spécifié";
                const options = { year: "numeric", month: "long", day: "numeric", hour: "2-digit", minute: "2-digit" };
                return new Date(date).toLocaleDateString("fr-FR", options);
            },
            filterOpenTickets() {
                this.filteredTickets = this.tickets.filter(ticket => ticket.status === 1);
                this.currentPage = 1; // go to page one to look good
            },
            resetFilter() {
                this.filteredTickets = [...this.tickets];
                this.currentPage = 1; // go to page one to look good
            },
            prevPage() {
                if (this.currentPage > 1) {
                    this.currentPage--;
                }
            },
            nextPage() {
                if (this.currentPage < this.maxPage) {
                    this.currentPage++;
                }
            },
            lien(id) {
                window.location.href = `chatTicket/${id}`;
            }

        },
        mounted() {
            this.resetFilter(); // load all ticket at start
        }
    });

    appfacture.mount("#appfacture");
});






document.addEventListener("DOMContentLoaded", () => {
    const appMessage = Vue.createApp({
        data() {
            return {
                msg: "Votre discussion",
                messages: messageData // Variable provenant du backend
            };
        },
        methods: {
            formatDate(date) {
                if (!date) return "Non spécifiée";
                const options = { year: "numeric", month: "long", day: "numeric", hour: "2-digit", minute: "2-digit" };
                return new Date(date).toLocaleDateString("fr-FR", options);
            }
        }
    });

    appMessage.mount("#appMessage");
});



