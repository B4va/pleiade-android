# Pleiade

*Application Android. Pleiade offre la possibilité de partager des recommandations de films, séries, musiques, livres, etc.*

**Version en développement** : 1.0

![logo](doc_readme/logo_gold.png)

- [Application](#application)
    - [Général](#général)
    - [Cas d'utilisation](#cas-dutilisation)
    - [Charte graphique](#charte-graphique)
- [Conception](#conception)
    - [Modélisation](#modélisation)
    - [Ressources](#ressources)
    - [Gestion des données](#gestion-des-données)
    - [APIs](#apis)
- [Développement](#développement)
    - [IDE](#IDE)
    - [Environnements](#environnements)
    - [Tests](#tests)
    - [Versions](#versions)

## Application

### Général

Pleiade est une plateforme participative ayant pour but de partager des recommandations concernant des oeuvres et des produits culturels. A terme, il sera donc possible de recommander des livres, des films, des séries, des jeux, etc. L'objectif est de pouvoir personnaliser au mieux sa recommandation, en indiquant *à qui* cette dernière est destinée ; ainsi, il ne s'agira pas simplement de partager une découverte de façon anonyme, mais également de pouvoir adresser ces recommandation de façon personnalisée. Des fonctionnalités plus "classiques" devraient néanmoins également être disponible, à savoir de pouvoir partager une oeuvre avec l'ensemble de ses contacts et de pouvoir consulter les oeuvres les plus recommandées par la communauté dans son ensemble.

![capture1](doc_readme/capture1.png)
![capture2](doc_readme/capture2.png)
![capture3](doc_readme/capture3.png)

### Cas d'utilisation

![cas d'utilisation](doc_readme/cas_dutilisation.png)

### Charte graphique

![logo square](doc_readme/logo_square.png)
![logo round](doc_readme/logo_round.png)

La **charte graphique** Pleiade est [disponible ici](doc_readme/charte_graphique.pdf).

## Conception

### Modélisation

La modélisation et la structuration de l'application reposent sur les standards de développement Android, et inclut donc les packages suivants :

- `activities` : Classes de traitement ayant le rôle de controleurs.
- `adapters` : Classes permettant la structuration d'ensembles de données à afficher.
- `app` : Classe de chargement de l'application.
- `fragments` : Sous-activités modulables, composant certaines activités complexes.
- `models` : Classes de modélisation impliquées dans les processus de l'application, et liées à la base de donnée.
- `utils` : Classes utilitaires.
- `viewModels` : Classes de gestion des données lorsque celles-ci sont mobilisées dans une activité. Permettent une persistance des données indépendamment des changements d'états de l'activitié (ex : rotation de l'écran).

![diagramme de classe](doc_readme/diagramme_classes.png)

### Ressources

La gestion des ressources de l'application repose sur les standards de développement Android, et inclut donc les dossiers suivants :

- `drawable` : Images inclues dans les différentes activités.
- `layout` : Différentes vues de l'application au format `xml`
- `values` : Propriétés générales, notamment de style, et éléments textuels.

### Gestion des données

La persistance des données est assurée via Firestore, sous la forme d'une base de données noSQL.

La gestion des données propres aux comptes utilisateurs est gérée indépendamment via le service Firebase Authentification.

Des règles sont configurées pour chaque modèle, permettant de paramétrer le type de données attendues et les conditions d'accès à certaines données.

![schéma bdd](doc_readme/schema_bdd.png)

### APIs

L'application est développée via l'API Android versions 19 et supérieures, et s'appuie notamment sur une base de données et des services Firebase.

Concernant le référencement des divers oeuvres et produits culturels, l'application fait appel à des API dédiées :
- exemple 1
- exemple 2
- exemple 3

## Développement

### IDE

L'application est développée en Java avec Android Studio. La configuration de l'IDE peut être directement générée sur la base des fichiers de configuration inclus dans le repository.

### Environnements

La gestion du build et des environnements est assurée par Gradle. Deux environnements sont mis en place, intéragissant avec des bases de données isolées :
- L'environnement `dev` est relié à la base de donnée dédiée et nécessite l'ajout du document de configuration dans `/src/dev`.
- L'environnement `prod` est relié à la base de données de production et nécessite l'ajout du document de configuration dans `/src/prod`.

Chacune de ces bases de données peut donner lieu à un export, sous la forme d'un backup pour la base de données de production, et d'un seed pour la base de données de développement. En phase de pré-production, la base de données de production peut importer le seed préconfiguré de la base de données de développement, de façon à ce qu'elle soit initialisée avec un jeu de données cohérent. A l'inverse, la base de donnée de développement peut importer un export de la base de données de production afin de réaliser tests et développements sur une copie du jeu de données réel.

L'accès à chacune des bases de données nécessite de s'être vu accorder l'accès Firestore correspondant.

### Tests

Le processus de développement de l'application repose sur le *Test Driven Développement* (TDD). Les tests unitaires sont donc réalisés en amont du développement proprement dit, pour chaque nouvelle version de développement, de manière à garantir la possibilité de tester l'application à n'importe quelle étape du développement. Les tests d'intégration sont quant à eux réalisés après chaque mise à jour importante des fonctionnalités, avec pour objectif le test de scénarii plus généraux.

### Versions / Processus

Chaque version donne lieu à un processus de développement standardisé, qu'il s'agit chaque fois de respecter dans la mesure du possible :

- *Analyse des besoins* : Etudes des objectifs de la version de développement, en termes de fonctionnalités. A cet effet, production ou mise à jour d'un diagramme de cas d'utilisation et un diagramme des parcours utilisateur.
- *Maquette* : Réalisation de la maquette des vues à intégrer ou modifier au cours du développement.
- *Paramétrage des dépendances* : Paramétrage du build de l'application, notamment en ce qui concerne ses dépendance, de manière à disposer des ressources nécessaires lors du développement.
- *Modélisation* : Conception des modèles, des structures de données, et des traitements développer. Cette étape donne lieu à la production ou la mise à jour d'un diagramme de classe et d'un diagramme d'entités relationnelles.
- *Paramétrage de la base de données* : Implémentation des structures de données et des règles de contrôle de la base de données côté serveur, suivie de l'initialisation d'un jeu de données.
- *Développement* : Intégration front-end des vues de l'application, production de tests en amont de la programmation des classes métiers, puis mise en oeuvre des fonctionnalités.
- *Mise en production* : Déploiement de l'application en version de test et/ou de production.
- *Documentation* : Retour sur les documents produits en amont du développement, validation et mise à jour, et production éventuelle d'une documentation complémentaire, axée sur les processus mis en oeuvre (ex : diagrammes d'activité ou de séquence).
- *Mise à jour du Readme* : Les avancées et modifications générales sont rendues visibles publiquement de manière à faciliter le travail collectif.

Toutes ces étapes sont coordonnées via la plateforme Trello.

Tout au long du développement, la documentation est uploadée sur la plateforme. Des ressources de documentation externes sont également mises en commun, suite au travail de recherche étant mis en oeuvre de manière continue.

**Références des versions :**

*Versions de déploiement `[x.0]`* : chaque nouvelle version déployée prend le format `x.0`, le second terme de la référence des versions étant réservé aux versions de développement. Les versions implémentant de nouvelles fonctionnalités et étant prêtes pour la mise en productions sont donc référencées `1.0`, `2.0`, `3.0`, etc. Un troisième terme peut être ajoutée à la référence en cas de mise à jour corrective .

*Versions de développement `[*.x]`* : pendant la phase de développement, chaque itération conduisant à la prochaine version de déploiement est référencée grâce au second terme du numéro de version. Entre la version 1 et 2, les itérations, correspondant aux différentes étapes du développement, dont donc par exemple référencées `1.1`, `1.2`, `1.3`, etc.

*Versions correctives `[*.0.x]`* : Ces versions sont développée à partir des versions de déploiement et ont pour objectif la corrections de problèmes fonctionnels ou de sécurité. Ces versions n'ont donc pas pour but le développement de nouvelles fonctionnalités ; elles reprennent par conséquent le numéro des versions de déploiement suivi d'un troieme terme. Après le déploiement de la première version, pourront donc par exemple être déployées des mise à jour référencées `1.0.1`, `1.0.2`, `1.0.3`, etc.