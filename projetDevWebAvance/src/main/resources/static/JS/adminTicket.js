document.addEventListener("DOMContentLoaded", () => {
    const app = Vue.createApp({
        data() {
            return {
                searchQuery: '',
                tickets: ticketsData
            };
        },
        computed: {
            filteredTickets() {
                return this.tickets.filter(ticket =>
                    ticket.titre.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
                    ticket.client.toLowerCase().includes(this.searchQuery.toLowerCase())   //look at by client & title
                );
            }
        },
        methods: { //bcs date was written bad, now write in french
            formatDate(dateString) {
                const date = new Date(dateString);
                const options = {
                    year: 'numeric',
                    month: '2-digit',
                    day: '2-digit',
                    hour: '2-digit',
                    minute: '2-digit',
                    hour12: false // 24h
                };
                return date.toLocaleString('fr-FR', options);
            },
            lien(id) {
                window.location.href = `/chatTicket/${id}`;
            }
        }
    });

    app.mount('#app');

});
