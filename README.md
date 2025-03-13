# 🎮 **Game Management App**

Aplicación Android para gestionar y mostrar información sobre juegos, utilizando Retrofit para interactuar con una API externa, Room para almacenamiento local y el patrón MVVM para estructurar el código.

## 🛠️ **Características**

- **Interacción con API**: Obtención de datos de juegos desde la API de RAWG mediante Retrofit. 🌐
- **Favoritos**: Marca juegos como favoritos y almacénalos en una base de datos local. ⭐
- **Estados del juego**: Gestiona los estados de los juegos (Pendiente, Jugando, Jugado). ⏳🎮✅
- **Historial de búsquedas**: Guarda y actualiza las búsquedas realizadas por el usuario. 🕵️‍♂️🔍
- **Persistencia**: Almacenamiento local de datos con Room. 💾

## 💻 **Tecnologías**

- **Kotlin**: Lenguaje utilizado para el desarrollo. 🧑‍💻
- **Retrofit**: Para interactuar con la API de RAWG. 🔄
- **Room**: Base de datos local para juegos, favoritos y búsquedas. 🗃️
- **LiveData & ViewModel**: Para gestionar el ciclo de vida de los datos y la UI. 📊

## 📂 **Estructura del Proyecto**

- **Modelos**: Representan juegos, estados, búsquedas y respuestas de la API.
- **Base de Datos**: Utiliza Room para almacenar datos relacionados con juegos y búsquedas.
- **API**: GameApiService para interactuar con la API de RAWG.
- **ViewModel**: Utilizamos ViewModels para gestiona la interacción con la base de datos y la UI.

## 🕹️ **Funcionalidad**

- **Pantalla Principal**: Muestra juegos de la API RAWG, permite marcar favoritos y actualizar estados. 🕹️
- **Favoritos**: Los juegos favoritos se almacenan localmente y pueden ser gestionados fácilmente. 💾
- **Historial de Búsquedas**: Guarda, consulta y elimina búsquedas previas. 🗂️
- **Persistencia Local**: Los datos de juegos y búsquedas se mantienen incluso sin conexión. 🌐❌
- **Estados del Juego**: Los juegos tienen estados (Pendiente, Jugando, Jugado) que se actualizan en la base de datos. 💾
- **Interacción con la API RAWG**: Obtención y visualización de juegos con sus detalles. 🌍
- **Actualización en Tiempo Real**: Cambios en favoritos, estados y búsquedas se reflejan inmediatamente en la UI. ⚡
