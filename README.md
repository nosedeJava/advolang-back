# Advolang-back

Este repositorio correpsonde al codigo de back-end del proyecto Advolang

Actualmente esta desplegado en heroku

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://advolang-back.herokuapp.com/)

## Problema a solucionar

Los métodos tradicionales hacen engorroso el aprendizaje de un nuevo idioma, acarreando consigo algunos problemas entre ellos, que tiene como prioridad como cualquier otro negocio de maximizar la utilidad, por lo que se tienden a alargar los programas al punto de por ejemplo dedicar todo un semestre a aprender dos o tres tiempos gramaticales en un programa de inglés, cuando no necesariamente el proceso debe ser tan tardío. Por otra parte estos cursos suelen ser costosos, pues debido al alargue de sus programas te encontraras pagando varias veces una suscripción que no te provee el conocimiento esperado, esto conlleva en muchos casos a que las personas terminen aburriéndose y abandonando la consistencia en su aprendizaje.

## Descripción

Nuestra plataforma permite el aprendizaje colaborativo de diferentes comunidades, a través de la recomendación de contenido, que permita el desarrollo de la habilidad en un idioma. Este contenido puede variar, pues pueden ser videos de YouTube, libros, guías, películas, entre otros. Fundamentalmente puedes recomendar cualquier cosa que pueda servirle a la comunidad para mejorar en su camino a la fluidez de un idioma. Este contenido está etiquetado para que sea fácil de buscar y así se adecue a los intereses del usuario, lo que buscamos es que las personas tengan un acercamiento del idioma que quieren aprender a su vida cotidiana.

## Arquitectura

En este caso nuestra aplicación esta diseñada considerando 4 componentes para su funcionamiento

- Spring-boot para el back-end
- React para el front-end
- MongoDB para el almacenamiento de la información
- Azure Storage(blob) para el alamcenamiento de multimedia

A continuación, mostramos algunos de los modelos usados dentro de esta aplicación.

### Diagrama de componentes

![Diagrama de componentes](https://raw.githubusercontent.com/nosedeJava/advolang-front/master/public/img/component%20diagram.png)

## Autores

- Michael Preciado (urobs17)
- Jeymar Vega (Stilink)
- Miguel Rivera (migue1994)
- Natalia Duran (nduran06)
