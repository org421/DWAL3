document.addEventListener("DOMContentLoaded", () => {

    let app = Vue.createApp({
        template: '<titre titre="Mon panier"/>' +
            '<content></content>'
})

    app.component('titre', {
        props:{titre: String},
        data: () => ({}),
        mounted: function (){

        },
        methods: {
        },
        computed: {
        },
        template: '<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">' +
          '<h1 class="display-4"> {{titre}} </h1>'+
            '</div>',
    })

    app.component('content', {
        props: {},
        data: () => ({
            panier : panier,
            lignePanier : [],
            result : String,
        }),
        mounted: function (){
            if(this.panier == null){
                this.result = "Votre panier est vide"
            } else {
                if (this.panier.lignePanier.length === 0) {
                    this.result = "Votre panier est vide"
                } else {
                    this.result = "Voici les articles dans votre panier :\n "
                    this.lignePanier = this.panier.lignePanier
                }
            }
        },
        watch: {
        },
        methods: {
        },
        computed: {
        },
        template: '<main role="main" class="container">\n' +
            '      <div class="my-3 p-3 bg-white rounded shadow-sm">\n' +
            '        <h6 class="border-bottom border-gray pb-2 mb-0">{{result}}</h6>\n' +
            '            <panier :lignePanier="lignePanier" :panier="panier"></panier>' +
            '      </div>\n' +
            '    </main>'
    })

    app.component('panier', {
        props: {
            panier : panier,
            lignePanier : Array,
        },
        data: () => ({
            showButton: Boolean,
            lines: [],
        }),
        mounted: function () {
            this.showButton = this.lignePanier.length !== 0; //We show the button to pay the items in the cart only if there is at least one item in the cart
        },
        watch : { // check for any change in the prop
            lignePanier(newValue) {
                this.showButton = newValue.length > 0;  // show the "buying" once lignePanier is not empty anymore
                this.loadLines();
            },
        },
        methods: {
            loadLines : function (){
                this.lignePanier.forEach((line, index) => {
                    if(line.abonnement){
                        console.log("abonnement");
                        this.lines[index] = {id: line.id , name: `Abonnement "${line.abonnement.nom}"`, price: line.abonnement.prix};
                    } else {
                        if (line.plat) {
                            console.log("plat")
                        } else {
                            if (line.menu) {
                                this.lines[index] = {id: line.id , name: `Menu "${Vue.toRaw(line.menu.nom)}"`, price: Vue.toRaw(line.menu.prix)*100};
                                console.log(Vue.toRaw(line.menu))
                            } else {
                                console.log("Article dans le panier introuvable !!!")
                            }
                        }
                    }
                })
            },

            },
        computed: {},
        template: '<div class=" row media text-muted pt-3" v-for="(line, index) in lines" :key="index">\n' +
            '          <p class=" col-11 media-body pb-3 mb-0 small lh-125 border-bottom border-gray">\n' +
            '            <strong class="d-block text-gray-dark">{{line.name}} : {{line.price/100}}€</strong>\n' +
            '          </p>\n' +
            '          <p class=" col-1 media-body pb-3 mb-0 small">\n' +
            '               <a class="btn-sm btn btn-danger" :href="`/removeLignePanier/${line.id}`">Enlever</a>\n' +
            '          </p>\n' +
            '        </div>\n' +
            '        <div v-if="showButton">' +
            '          <div class="row">' +
            '            <div class="col-8 d-block text-left mt-3">\n' +
            '              <strong class="d-block text-dark">Total : {{panier.prixTotal/100}}€</strong>\n' +
            '            </div>\n' +
            '            <div class="col-4 d-block text-right mt-3">\n' +
            '              <a class="btn-primary btn" href="/paiement">Passer au paiement</a>\n' +
            '            </div>\n' +
            '          </div>' +
            '        </div>\n'
    })

    let vm = app.mount('#container')
})