# Rick and Morty API Consumer

## Descripción

Este proyecto es una aplicación desarrollada en **Java** utilizando **IntelliJ IDEA**, que consume la API de **Rick and Morty**. Permite seleccionar una temporada y un episodio, mostrando la descripción de los personajes presentes en ese episodio. Al seleccionar un personaje, se muestra su imagen.

## Tecnologías utilizadas

- **Java**
- **Maven**
- **Biblioteca JSON (org.json)**
- **Rick and Morty API**

## Dependencias

Para manejar JSON en este proyecto, se necesita la siguiente dependencia en el archivo `pom.xml`:

```xml
<dependencies>
    <!-- Biblioteca para manejar JSON -->
    <dependency>
        <groupId>org.json</groupId>
        <artifactId>json</artifactId>
        <version>20231013</version>
    </dependency>
</dependencies>
```

## Uso

1. Seleccionar una temporada.
2. Elegir un episodio dentro de la temporada.
3. Visualizar la lista de personajes del episodio.
4. Hacer clic en un personaje para ver su imagen y detalles.

## API Utilizada

Se hace uso de la [Rick and Morty API](https://rickandmortyapi.com/) para obtener los datos de los episodios y personajes.
