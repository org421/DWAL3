// Fonction afficher la section cliquée
function showSection(sectionId) {
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = section.id === sectionId ? 'block' : 'none';
    });
}


// Section afficher par défaut lors du chargement de la page Mon Profil
document.addEventListener("DOMContentLoaded", () => {
    const urlParams = new URLSearchParams(window.location.search);
    const section = urlParams.get('section') || 'persoInfo';
    showSection(section);
});

