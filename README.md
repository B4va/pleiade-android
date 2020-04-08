# Pleiade (android app)

- [Application](#application)
- [Conception](#conception)
    - [Gestion des données](#gestion-des-données)
- [Développement](#développement)
    - [IDE](#IDE)
    - [Environnements](#environnement)
    - [Versions](#versions)

![logo](doc_readme/logo_gold.png)

## Application

## Conception

### Gestion des données

La persistance des données est assurée via Firestore, sous la forme d'une base de données noSQL.

## Développement

### IDE

L'application est développée en Java avec Android Studio. La configuration de l'IDE peut être directement générée sur la base des fichiers de configuration inclus dans le repository.

### Environnements

La gestion du build et des environnements est assurée par Gradle. Deux environnements sont mis en place, intéragissant avec des bases de données isolées :
- L'environnement `dev` est relié à la base de donnée dédiée et nécessite l'ajout du document de configuration dans `/src/dev`.
- L'environnement `prod` est relié à la base de données de production et nécessite l'ajout du document de configuration dans `/src/prod`.

Chacune de ces bases de données peut donner lieu à un export, sous la forme d'un backup pour la base de données de production, et d'un seed pour la base de données de développement. En phase de pré-production, la base de données de production peut importer le seed préconfiguré de la base de données de développement, de façon à ce qu'elle soit initialisée avec un jeu de données préexistant. A l'inverse, la base de donnée de développement peut importer un export de la base de données de production afin de réaliser tests et développements sur une copie du jeu de données réel.

L'accès à chacune des bases de données nécessite de s'être vu accorder l'accès Firestore correspondant.

### Versions

**Versions de déploiement [x.0]** : chaque nouvelle version déployée prend le format `x.0`, le second terme de la référence des versions étant réservé aux versions de développement. Les versions implémentant de nouvelles fonctionnalités et étant prêtes pour la mise en productions sont donc référencées `1.0`, `2.0`, `3.0`, etc. Un troisième terme peut être ajoutée à la référence en cas de mise à jour corrective .

**Versions de développement [*.x]** : pendant la phase de développement, chaque itération conduisant à la prochaine version de déploiement est référencée grâce au second terme du numéro de version. Entre la version 1 et 2, les itérations, correspondant aux différentes étapes du développement, dont donc par exemple référencées `1.1`, `1.2`, `1.3`, etc.

**Versions correctives [*.0.x]** : Ces versions sont développée à partir des versions de déploiement et ont pour objectif la corrections de problèmes fonctionnels ou de sécurité. Ces versions n'ont donc pas pour but le développement de nouvelles fonctionnalités ; elles reprennent par conséquent le numéro des versions de déploiement suivi d'un troieme terme. Après le déploiement de la première version, pourront donc par exemple être déployées des mise à jour référencées `1.0.1`, `1.0.2`, `1.0.3`, etc.