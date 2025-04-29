document.addEventListener("DOMContentLoaded", () => {
    const app = Vue.createApp({
        data() {
            return {
                reservations: reservations
            };
        },
        computed: {
            upcomingReservations() {
                const today = new Date();
                return this.reservations.filter(reservation => {
                    const reservationDate = new Date(reservation.date);
                    return reservationDate >= today;
                });
            },
            pastReservations() {
                const today = new Date();
                return this.reservations.filter(reservation => {
                    const reservationDate = new Date(reservation.date);
                    return reservationDate < today;
                });
            }
        }
    });

    app.mount("#app");
});