# Pleiade (android app)

- [Application](#application)
- [Conception](#conception)
- [Développement](#développement)

![logo](doc_readme/logo_gold.png)

## Application

## Conception

La persistance des données est assurée via Firestore, sous la forme d'une base de données noSQL.

## Développement

L'application est développée en Java avec Android Studio. La configuration de l'IDE peut être directement générée sur la base des fichiers de configuration inclus dans le repository.

La gestion du build et des environnements est assurée par Gradle. Deux environnements sont mis en place, intéragissant avec des bases de données isolées :
- L'environnement `dev` est relié à la base de donnée dédiée et nécessite l'ajout du document de configuration dans `/src/dev`.
- L'environnement `prod` est relié à la base de données de production et nécessite l'ajout du document de configuration dans `/src/prod`.

Chacune de ces bases de données peut donner lieu à un export, sous la forme d'un backup pour la base de données de production, et d'un seed pour la base de données de développement. En phase de pré-production, la base de données de production peut importer le seed préconfiguré de la base de données de développement, de façon à ce qu'elle soit initialisée avec un jeu de données préexistant. A l'inverse, la base de donnée de développement peut importer un export de la base de données de production afin de réaliser tests et développements sur une copie du jeu de données réel.

L'accès à chacune des bases de données nécessite de s'être vu accorder l'accès Firestore correspondant.