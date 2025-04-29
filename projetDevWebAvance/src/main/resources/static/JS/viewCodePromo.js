document.addEventListener("DOMContentLoaded", () => {

    let app = Vue.createApp({
        template: '<div class="container mt-5">' +
                '<titre titre="Gestion des Codes promotionnels"/>' +
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
        template: '<h1>{{titre}}</h1>'
    })

    app.component('content', {
        props:{},
        data: () => ({

        }),
        mounted: function (){

        },
        methods: {
        },
        computed: {
        },
        template: `
          <div class="row">
            <div class="col-4"></div>
            <a href="/admin/createCodePromo" class="btn btn-primary col-4 ecart">Crée un code promotionnel</a>
            <div class="col-4"></div>
          </div>
            <tableau></tableau>`
    })

    app.component('tableau', {
        props:{},
        data: () => ({
            codesPromos : codesPromos
        }),
        mounted: function (){
        },
        methods: {
        },
        computed: {
        },

        template: '<table class="table table-striped">'+
        '<thead>' +
        '<tr>' +
            '<th>ID</th>' +
            '<th>Code</th>' +
            '<th>Réduction</th>' +
            '<th>Nb actuel</th>' +
            '<th>Nb MAX</th>' +
            '<th>Statut</th>' +
            '<th>Date de début</th>' +
            '<th>Date de fin</th>' +
            '<th>Action</th>' +
        '</tr>' +
        '</thead>' +
        '<tbody>' +
        `<tr v-for="code in this.codesPromos" :key="code.id">` +
            '<ligne :code="code"></ligne>' +
        '</tr>' +
        '</tbody>' +
    '</table>'
    })

    app.component('ligne', {
        props:{
            code: {
                type: Object,
                required: true
            }
        },
        data: () => ({
            token: token,
            now: new Date(),
        }),
        mounted: function (){
            console.log(this.code)

        },
        methods: {
            isAfterNow(dateString) {
                // To convert code.dateFin LocalDate in Date
                const parsedDate = new Date(dateString + 'T00:00:00'); // Adding hours
                return parsedDate > this.now;
            }
        },
        computed: {
        },
        template: '<td>{{ code.id }}</td>' +
            '<td>{{ code.code }}</td>' +
            '<td>{{ code.reduction * 100}}%</td>' +
            '<td>{{ code.nbActuel}}</td>' +
            '<td>{{ code.nbMax}}</td>' +
            '<td>' +
                '<span v-if="code.active" class="text-success">Activé</span>' +
                '<span v-else class="text-danger">Désactivé</span>' +
            '</td>' +
            '<td>{{ code.dateDebut}}</td>' +
            '<td>{{ code.dateFin}}</td>' +
            '<td v-if="isAfterNow(code.dateFin)">' +
                `<form action="/admin/codePromo" method="post">
                    <input hidden="hidden" name="id" :value="code.id">
                    <input type="hidden" name="_csrf" :value="token" />
                    <button type="submit" class="btn btn-sm" :class="code.active ? 'btn-danger' : 'btn-success'">
                        {{ code.active ? 'Désactiver' : 'Activer' }}
                    </button>
                </form>
            </td>
            <td v-else>
                <p class="btn btn-sm btn-secondary">Expiré</p>
            </td>`
    })

//         <tr v-for="code in filteredCodes" :key="code.id">
//             <td>{{ code.id }}</td>
//             <td>{{ code.code }}</td>
//             <td>{{ code.reduction }}</td>
//             <td>
//                 <span v-if="user.enabled" class="text-success">Actif</span>
//                 <span v-else class="text-danger">Inactif</span>
//             </td>
//             <td>
//                 <form action="/admin/utilisateur" method="post">
//                     <input hidden="hidden" name="id" :value="user.id">
//                     <input type="hidden" name="_csrf" th:value="${_csrf.token}" />
//                     <button
//                             type="submit"
//                             class="btn btn-sm"
//                             :class="user.enabled ? 'btn-danger' : 'btn-success'"
//                             @click="toggleStatus(user)">
//                         {{ user.enabled ? 'Désactiver' : 'Activer' }}
//                     </button>
//                 </form>
//             </td>
//         </tr>
//         </tbody>
//     </table>
// </div>
    let vm = app.mount('#container')
})