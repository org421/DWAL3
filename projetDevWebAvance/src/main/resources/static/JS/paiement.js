document.addEventListener("DOMContentLoaded", () => {

    let app = Vue.createApp({
        template: '<div class="container">' +
            '<titre titre="Paiement du panier"/>' +
            '<content></content>'+
            '</div>'

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
        template: '<div class="py-5 text-center">'+
            '<h2>{{titre}}</h2>'+
            '</div>'
    })

    app.component('content', {
        props: {},
        data: () => ({
            user : user,
            token : token,
            codePromo: codePromo

        }),
        mounted: function (){
        },
        watch: {
        },
        methods: {
        },
        computed: {
        },
        template: '<div class="row">' +
            '<cart :user=user ></cart>' +
            // '<div id="cart"></div>' +
            '<div class="col-md-8 order-md-1">'+
            '  <form class="needs-validation" novalidate="" action="/paiementAbonnement" method="post">'+
            `   <div v-if="codePromo">` +
            `     <input :value="codePromo.code" name="codePromo" type="hidden" id="codePromo">` +
            `    </div>` +
            `<selectAbo></selectAbo>` +
            '  <h4 class="mb-3">Adresse de facturation</h4>'+
            '    <div class="row">'+
            '      <div class="col-md-6 mb-3">'+
            '        <label for="nom">Nom</label>'+
            `        <input :value="user.nom" name="nom" type="text" class="form-control" id="nom" placeholder="" required="">`+
            '        <div class="invalid-feedback">'+
            '          Nom valide requis.'+
            '        </div>'+
            '      </div>'+
            '      <div class="col-md-6 mb-3">'+
            '        <label for="prenom">Prénom</label>'+
            `        <input :value="user.prenom" name="prenom" type="text" class="form-control" id="prenom" placeholder="" required="">`+
            '        <div class="invalid-feedback">'+
            '          Prénom valide requis.'+
            '        </div>'+
            '      </div>'+
            '    </div>'+
            '    <div class="mb-3">'+
            '      <label for="email">Email <span class="text-muted"></span></label>'+
            `      <input :value="user.email" name="email" type="email" class="form-control" id="email" placeholder="">`+
            '      <div class="invalid-feedback">'+
            '        Email valide requis.'+
            '      </div>'+
            '    </div>'+
            '    <div class="mb-3">'+
            '      <label for="adresse">Adresse</label>'+
            '      <input value="adresse" name="adresse" type="text" class="form-control" id="adresse" placeholder="" required="">'+
            '      <div class="invalid-feedback">'+
            '        Adresse valide requise.'+
            '      </div>'+
            '    </div>'+
            '    <hr class="mb-4">'+
            '    <h4 class="mb-3">Mode de paiement</h4>'+
            '    <div class="d-block my-3">'+
            '      <div class="custom-control custom-radio">'+
            '        <input value="1" id="banque" name="modePaiement" type="radio" class="custom-control-input" checked="" required="">'+
            '        <label class="custom-control-label" for="banque">Carte bancaire</label>'+
            '      </div>'+
            '    </div>'+
            '    <div class="row">'+
            '      <div class="col-md-6 mb-3">'+
            '        <label for="cc-nom">Nom sur la carte</label>'+
            `        <input :value="user.nom" name="cc-nom" type="text" class="form-control" id="cc-nom" placeholder="" required="">`+
            '        <small class="text-muted">Nom entier sur la carte</small>'+
            '        <div class="invalid-feedback">'+
            '          Nom de la carte requis.'+
            '        </div>'+
            '      </div>'+
            '      <div class="col-md-6 mb-3">'+
            '        <label for="cc-numero">Numéro de la carte</label>'+
            '        <input value="0123456789" name="cc-numero" type="text" class="form-control" id="cc-numero" placeholder="" required="">'+
            '        <div class="invalid-feedback">'+
            '          Numéro de la carte requis.'+
            '        </div>'+
            '      </div>'+
            '    </div>'+
            '    <div class="row">'+
            '      <div class="col-md-3 mb-3">'+
            `       <label for="cc-expiration">Date d'expiration</label>`+
            '        <input value="1234" name="cc-expiration" type="text" class="form-control" id="cc-expiration" placeholder="" required="">'+
            '        <div class="invalid-feedback">'+
            `         Date d'expiration requise.`+
            '        </div>'+
            '      </div>'+
            '      <div class="col-md-3 mb-3">'+
            '        <label for="cc-cvv">CVV</label>'+
            '        <input value="345" name="cc-cvv" type="text" class="form-control" id="cc-cvv" placeholder="" required="">'+
            '        <div class="invalid-feedback">'+
            '          Code de sécurité requis.'+
            '        </div>'+
            '      </div>'+
            '    </div>'+
            '    <div class="custom-control custom-checkbox">'+
            '      <input name="paiementDev" type="checkbox" class="custom-control-input" id="paiementDev">'+
            '      <label class="custom-control-label" for="paiementDev">Paiement développeur</label>'+
            '    </div>'+
            '    <hr class="mb-4">'+
            `    <input type="hidden" name="_csrf" :value="token"/>`+
            '    <button class="btn btn-primary btn-lg btn-block" type="submit">Payer</button>'+
            '  </form>'+
            '</div>'+
            '</div>'
    })

    app.component('cart', {
        props: {
            user: user
        },
        data: () => ({
            showAbo: Boolean,
            showProd: Boolean,
        }),
        mounted: function () {
            if(user.panier.abonnement){
                this.showAbo = true
                this.showProd = false
            } else {
                this.showAbo = false
                this.showProd = true
            }

        },
        watch : {
            user(newValue){
                if(newValue.panier.abonnement){
                    this.showAbo = true
                    this.showProd = false
                } else {
                    this.showAbo = false
                    this.showProd = true
                }
            }
        },
        methods: {

        },
        computed: {

        },
        template: '<aboCart :user=user v-if="showAbo"></aboCart>'+
            '<prodCart :user=user v-if="showProd"></prodCart>'
    })

    app.component('prodCart', {
        props: {
            user: Object
        },
        data: () => ({
            lines : [],
            panier: ""
        }),
        mounted: function () {
            this.panier = Vue.toRaw(user.panier);
            this.panier.lignePanier.forEach((line, index) => {
                if(line.abonnement){
                    console.log("abonnement");
                    this.lines[index] = {id: line.id , name: `Abonnement "${line.abonnement.nom}"`, price: line.abonnement.prix};
                } else {
                    if (line.plat) {
                        console.log("plat")
                        this.lines[index] = {id: line.id , name: `"${line.plat.nom}"`, price: line.plat.prix * 100}; //multiply by 100 because it will be divided by 100 later
                    } else {
                        if (line.menu) {
                            console.log("menu")
                            this.lines[index] = {id: line.id , name: `"${line.menu.nom}"`, price: line.menu.prix * 100};
                        } else {
                            console.log("Article dans le panier introuvable !!!")
                        }
                    }
                }
            })

            console.log(this.lines);
        },
        watch : {

        },
        methods: {

        },
        computed: {

        },
        template: '<div class="col-md-4 order-md-2 mb-4">'+
            '  <h4 class="d-flex justify-content-between align-items-center mb-3">'+
            '    <span class="text-muted">Vos produits</span>'+
            '    <span class="badge badge-secondary badge-pill"></span>'+
            '  </h4>'+
            '  <ul class="list-group mb-3">'+
            '    <li v-for="(line, index) in lines" :key="index" class="list-group-item d-flex justify-content-between lh-condensed">'+
            '      <div>'+
            `        <h6 class="my-0">{{line.name}}</h6>`+
            '        <small class="text-muted"></small>'+
            '      </div>'+
            `      <span class="text-muted">{{line.price / 100}}€</span>`+
            '    </li>'+
            '    <li class="list-group-item d-flex justify-content-between">'+
            '      <span>Total</span>'+
            `      <strong> ${user.panier.prixTotal / 100}€</strong>`+
            '    </li>'+
            '  </ul>'+
            '</div>'
    })

    app.component('aboCart', {
        props: {
            user: user // Déclarez user comme une prop
        },
        data: () => ({
            panier: {},         // Initialisation
            lignePanier: [],    // Initialisation à un tableau vide
            length: 0,
            prixTotal: 0,
            token : token,
            codePromo : codePromo,
            reduction : 0
        }),
        mounted: function () {
            // Vérifiez que user.panier existe avant de l'utiliser
            this.panier = Vue.toRaw(this.user.panier || {});
            this.lignePanier = Array.isArray(this.panier.lignePanier) ? this.panier.lignePanier : [];
            this.length = this.lignePanier.length;
            this.prixTotal = this.panier.prixTotal || 0; // Par défaut à 0 si non défini

            console.log("prix à payer : " + Number(user.panier.prixTotal - user.panier.prixTotal * this.reduction))

            if(this.codePromo != null) {
                console.log("code promo : " + this.codePromo.code)
                // console.log("reduction : " + this.codePromo.reduction)
                this.reduction = this.codePromo.reduction
                console.log("total : " + user.panier.prixTotal)
                console.log("reduction : " + this.reduction)
                console.log("= " + user.panier.prixTotal + "-" + user.panier.prixTotal + "*" + this.reduction)
                console.log("= " + user.panier.prixTotal + "-" + Number(user.panier.prixTotal * this.reduction))
                console.log("= " + Number (user.panier.prixTotal - user.panier.prixTotal * this.reduction))

            } else {
                console.log("pas de code promo")
            }
        },

        watch : {
            reduction(newValue){
                console.log("prix à payer : " + Number(user.panier.prixTotal - user.panier.prixTotal * newValue))
            }

        },

        methods: {
            formatPriceFloor(price){ //to make a number into a price format (for reductions)
                return (Math.floor(price * 100) / 100).toFixed(2)
                //first *100 to make the price into the number of cent
                //then /100 to make the price into the number of euro again
            },

            formatPriceCeil(price){ //to make a number into a price format (for actual prices)
                return (Math.ceil(price * 100) / 100).toFixed(2)
                //first *100 to make the price into the number of cent
                //then /100 to make the price into the number of euro again
            }

        },
        template: `
    <div class="col-md-4 order-md-2 mb-4">
        <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-muted">Vos produits</span>
            <span class="badge badge-secondary badge-pill"></span>
        </h4>
        <ul class="list-group mb-3">
            <li class="list-group-item d-flex justify-content-between lh-condensed">
                <div>
                    <h6 class="my-0" v-if="lignePanier.length > 0 && lignePanier[0].abonnement">
                        Abonnement "{{ lignePanier[0].abonnement.nom }}"
                    </h6>
                    <small class="text-muted"></small>
                </div>
                <span class="text-muted" v-if="lignePanier.length > 0 && lignePanier[0].abonnement">
                    {{ lignePanier[0].abonnement.prix / 100 }} €
                </span>
            </li>
            <li class="list-group-item d-flex justify-content-between bg-light">
                <div class="text-success">
                  <h6 class="my-0" v-if="codePromo == null "> Pas de code promotionnel</h6>
                  <h6 class="my-0" v-if="codePromo != null ">Code promotionnel : {{ this.codePromo.code }}</h6>
                  <small></small>
                </div>
              <span class="text-success" v-if="codePromo == null ">-0 €</span>
              <span class="text-success" v-if="codePromo != null ">-
                {{ formatPriceFloor((this.reduction * ${user.panier.prixTotal}) / 100) }} €</span>
            </li>
            <li class="list-group-item d-flex justify-content-between">
                <span>Total</span>
                <strong>{{ formatPriceCeil( (${(user.panier.prixTotal)} - this.reduction * ${(user.panier.prixTotal)}) / 100 ) }}€</strong>
            </li>
        </ul>
        <form class="card p-2" action="/reclamation" method="post">
            <div class="input-group">
                <input type="text" name="code" class="form-control" placeholder="Promo code">
                <div class="input-group-append">
                    <input type="hidden" name="_csrf" :value="token"/>
                    <button type="submit" class="btn btn-secondary">Réclamer</button>
                </div>
            </div>
        </form>
    </div>
    `
    });

    app.component('selectAbo', {
        props: {
        },
        data: () => ({
            showContent: Boolean,
        }),
        mounted: function () {
            this.showContent = !!user.panier.abonnement; //true if user.panier.abonnement is true

        },
        watch : {

        },
        methods: {

        },
        computed: {

        },
        template: `   <h4 class="mb-3" v-if="showContent">Sélection du type d'abonnement</h4>`+
            '    <div class="row" v-if="showContent">'+
            '      <div class="d-block my-3">'+
            '      <div class="custom-control custom-checkbox">'+
            '        <input name="renouvellement" type="checkbox" class="custom-control-input" id="renouvellement">'+
            '        <label class="custom-control-label" for="renouvellement">Activer le renouvellement automatique ?</label>'+
            '      </div>'+
            '      </div>'+
            '    </div>'+
            '    <hr class="mb-4">'
    })

    let vm = app.mount('#container')
})