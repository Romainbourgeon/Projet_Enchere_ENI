document.addEventListener('DOMContentLoaded', function () {
    // 1. Effet de traînée : création de plusieurs petits éléments "trail" invisibles qui vont suivre la souris
    const trailLength = 30;
    const trailElements = [];

    // Création des 30 éléments div qui constitueront la traînée
    for (let i = 0; i < trailLength; i++) {
        const trail = document.createElement('div');
        trail.className = 'trail';
        // Initialisation du style pour rendre chaque élément invisible et réduit
        trail.style.opacity = 0;
        trail.style.transform = 'translate(-50%, -50%) scale(0)';
        // Ajout de l'élément au body du document
        document.body.appendChild(trail);
        // Stockage dans un tableau pour pouvoir manipuler facilement les éléments
        trailElements.push(trail);
    }

    let index = 0; // Index pour parcourir les éléments de la traînée en boucle

    // Écouteur d'événement pour suivre les mouvements de la souris sur la page
    document.addEventListener('mousemove', function (e) {
        // Sélection de l'élément trail courant à déplacer
        const trail = trailElements[index];
        // Positionnement de cet élément au niveau de la souris (pageX et pageY)
        trail.style.left = e.pageX + 'px';
        trail.style.top = e.pageY + 'px';
        // Rendre cet élément visible et agrandi
        trail.style.opacity = '0.6';
        trail.style.transform = 'translate(-50%, -50%) scale(1)';
        // Après 100ms, faire disparaître et rétrécir l'élément pour l'effet de disparition progressive
        setTimeout(() => {
            trail.style.opacity = '0';
            trail.style.transform = 'translate(-50%, -50%) scale(0)';
        }, 100);
        // Passer à l'élément suivant dans le tableau, en bouclant à la fin
        index = (index + 1) % trailLength;
    });

})

    // Deuxième écouteur DOMContentLoaded imbriqué (fonctionnalités pour afficher/masquer des sections)
    document.addEventListener('DOMContentLoaded', function() {
        // Récupération des boutons ou éléments pour filtres "mesAchats" et "mesVentes"
        const mesAchats = document.getElementById('mesAchats');
        const mesVentes = document.getElementById('mesVentes');

        // Si le bouton "mesAchats" existe, on ajoute un écouteur pour afficher la section enchères et cacher la section ventes
        if (mesAchats) {
            mesAchats.addEventListener('click', function() {
                document.getElementById('inputEnchere').style.display = 'block';
                document.getElementById('inputVente').style.display = 'none';
            });
        }

        // Si le bouton "mesVentes" existe, on ajoute un écouteur pour afficher la section ventes et cacher la section enchères
        if (mesVentes) {
            mesVentes.addEventListener('click', function() {
                document.getElementById('inputVente').style.display = 'block';
                document.getElementById('inputEnchere').style.display = 'none';
            });
        }
    });

    // Écouteur sur le changement de valeur du select/input "inputEnchere"
    document.getElementById('inputEnchere').addEventListener("change", function(){
        // Redirige vers la même page avec un paramètre dans l'URL selon la valeur sélectionnée
        if (this.value === 'enCours'){
            window.location.href = window.location.pathname + "?enchere=" + this.value;
        } else if (this.value === 'remportees'){
            window.location.href = window.location.pathname + "?enchere=" + this.value;
        }
    })

    // Écouteur sur le changement de valeur du select/input "inputVente"
    document.getElementById('inputVente').addEventListener("change", function(){
        // Redirige vers la même page avec un paramètre dans l'URL selon la valeur sélectionnée
        if (this.value === 'enCours'){
            window.location.href = window.location.pathname + "?ventes=" + this.value;
        } else if (this.value === 'nonDebutees'){
            window.location.href = window.location.pathname + "?ventes=" + this.value;
        } else if (this.value === 'terminees'){
            window.location.href = window.location.pathname + "?ventes=" + this.value;
        }
    })


document.addEventListener('DOMContentLoaded', function() {
    const passwordInput = document.getElementById('password');
    const passwordStrengthBar = document.createElement('div');
    passwordStrengthBar.className = 'password-strength-bar';

    // Styles pour la barre de progression
    passwordStrengthBar.style.height = '4px';
    passwordStrengthBar.style.marginTop = '8px';
    passwordStrengthBar.style.borderRadius = '2px';
    passwordStrengthBar.style.transition = 'all 0.3s ease';
    passwordStrengthBar.style.width = '0%';
    passwordStrengthBar.style.backgroundColor = 'transparent';

    // Insère la barre après le champ mot de passe
    passwordInput.insertAdjacentElement('afterend', passwordStrengthBar);

    passwordInput.addEventListener('input', function() {
        const password = this.value;
        const strength = calculatePasswordStrength(password);
        updateStrengthBar(strength);
    });

    function calculatePasswordStrength(password) {
        let strength = 0;

        // Longueur minimale
        if (password.length >= 8) strength += 20;
        if (password.length >= 12) strength += 10;

        // Contient des minuscules
        if (/[a-z]/.test(password)) strength += 10;

        // Contient des majuscules
        if (/[A-Z]/.test(password)) strength += 10;

        // Contient des chiffres
        if (/\d/.test(password)) strength += 10;

        // Contient des caractères spéciaux
        if (/[^a-zA-Z0-9]/.test(password)) strength += 20;

        // Mot de passe très fort (conditions multiples)
        if (password.length >= 12 && /[A-Z]/.test(password) &&
            /[a-z]/.test(password) && /\d/.test(password) &&
            /[^a-zA-Z0-9]/.test(password)) {
            strength = 100;
        }

        return Math.min(strength, 100); // Max 100%
    }

    function updateStrengthBar(strength) {
        // Couleur qui évolue du rouge au vert
        const hue = Math.floor(strength * 1.2); // 0-120 (rouge-vert)
        passwordStrengthBar.style.backgroundColor = `hsl(${hue}, 100%, 50%)`;
        passwordStrengthBar.style.width = `${strength}%`;

        // Ajoute une classe pour le feedback visuel
        passwordStrengthBar.className = 'password-strength-bar';
        if (strength < 40) {
            passwordStrengthBar.classList.add('weak');
        } else if (strength < 70) {
            passwordStrengthBar.classList.add('medium');
        } else {
            passwordStrengthBar.classList.add('strong');
        }
    }
});