# 🎮 **Game Management App**

Este proyecto es una aplicación Android que gestiona y muestra información sobre juegos. Utiliza Retrofit para interactuar con una API externa, Room para almacenamiento local, y una arquitectura basada en el patrón MVVM (Model-View-ViewModel) para separar las responsabilidades y mantener el código limpio y organizado.

## 🛠️ **Características**

- **Interacción con una API**: Obtén datos de juegos desde la API externa de RAWG mediante Retrofit. 🌐
- **Favoritos**: Permite marcar juegos como favoritos y gestionarlos en una base de datos local utilizando Room. ⭐
- **Estados del juego**: Los juegos pueden tener varios estados como "Pendiente", "Jugando", "Jugado" o "Sin Estado", los cuales se almacenan en la base de datos. ⏳🎮✅
- **Historial de búsquedas**: Guarda las búsquedas de los usuarios y permite eliminarlas o actualizarlas. 🕵️‍♂️🔍
- **Persistencia**: Los datos de los juegos, favoritos y búsquedas se almacenan localmente en una base de datos SQLite mediante Room. 💾

## 💻 **Tecnologías Utilizadas**

- **Kotlin**: El lenguaje de programación utilizado para el desarrollo de la aplicación. 🧑‍💻
- **Retrofit**: Utilizado para realizar solicitudes HTTP y manejar la API de RAWG. 🔄
- **Room**: Base de datos local para almacenar información de los juegos, favoritos y el historial de búsquedas. 🗃️
- **LiveData & ViewModel**: Implementación del patrón MVVM para una mejor gestión del ciclo de vida de los datos y la UI. 📊
- **Coroutines**: Para la ejecución asincrónica de operaciones en segundo plano. ⏳

## 📂 **Estructura del Proyecto**

### Modelos

- **Game**: Representa un juego con todos sus atributos y su estado actual (favorito, estado de juego).
- **Estado**: Enum para representar los diferentes estados de un juego (Pendiente, Jugando, Jugado, Sin Estado).
- **Busqueda**: Almacena las consultas de búsqueda realizadas por el usuario.
- **GameResponse**: Modelo que envuelve la respuesta de la API que contiene una lista de juegos.
- **Lista**: Representa una lista de juegos.

### Base de Datos (Room)

- **GameDatabase**: La base de datos Room que almacena información sobre juegos y búsquedas.
- **GameDao**: DAO para operaciones sobre la tabla de juegos, como obtener favoritos, agregar juegos, actualizar estados, etc.
- **HistorialDao**: DAO para operaciones sobre el historial de búsquedas.
- **RoomRepository**: Clase encargada de interactuar con la base de datos Room.

### API

- **GameApiService**: Interfaz de Retrofit que define los endpoints de la API para obtener información sobre los juegos.
- **Repository**: Repositorio encargado de obtener los datos de la API.

### ViewModel

- **RoomViewModel**: ViewModel que gestiona la lógica relacionada con los juegos almacenados en la base de datos local y su interacción con la UI.

### Aplicación

- **GameApplication**: Clase `Application` que inicializa la base de datos Room.

## 🕹️ **Funcionalidad**

### 1. **Pantalla Principal**
   - Muestra una lista de juegos obtenidos de la API de RAWG. 🕹️
   - Los juegos se muestran con detalles como el nombre, la imagen de fondo, la calificación y el tiempo de juego. 📊
   - Permite al usuario marcar y desmarcar los juegos como favoritos. ❤️
   - Los juegos tienen la opción de actualizar su estado: **Pendiente**, **Jugando** o **Jugado**. 🔄

### 2. **Favoritos**
   - Los juegos marcados como favoritos se almacenan en una base de datos local utilizando **Room**. 💾
   - Los usuarios pueden ver una lista de sus juegos favoritos en la pantalla principal. ⭐
   - Se permite agregar o eliminar juegos de favoritos de forma sencilla, y la lista de favoritos se actualiza automáticamente en la base de datos local.

### 3. **Historial de Búsquedas**
   - Las búsquedas realizadas por los usuarios se almacenan en una base de datos local. 🗂️
   - El historial de búsquedas se puede consultar desde cualquier parte de la aplicación. 🔍
   - Los usuarios pueden eliminar entradas del historial si lo desean. ❌
   - Las consultas recientes se pueden actualizar con una nueva marca de tiempo. 🕰️

### 4. **Persistencia Local**
   - Utilizando **Room**, todos los datos relevantes (juegos, favoritos y búsquedas) se almacenan localmente en la base de datos. 🗃️
   - Esto permite que los usuarios continúen usando la aplicación incluso sin conexión a Internet. 🌐❌
   - Los datos se actualizan automáticamente cuando hay cambios en el estado de los juegos, sus favoritos o el historial de búsquedas. 🔄

### 5. **Estados del Juego**
   - Los juegos pueden tener diferentes estados que representan el progreso del jugador:
     - **Pendiente**: Juegos que aún no se han jugado. ⏳
     - **Jugando**: Juegos en los que el usuario está actualmente jugando. 🎮
     - **Jugado**: Juegos que el usuario ya ha jugado. ✅
     - **Sin Estado**: Juegos sin un estado definido (por defecto). ❓
   - Los estados se actualizan a través de la aplicación, y la información se almacena en la base de datos local para su persistencia. 💾

### 6. **Interacción con la API RAWG**
   - La aplicación obtiene la lista de juegos desde la API pública de RAWG. 🌍
   - Los detalles de los juegos (como la calificación, tiempo de juego, número de revisiones, etc.) se extraen y se presentan al usuario. 📋
   - Los juegos se pueden buscar y filtrar desde la pantalla principal, y toda la información se muestra de manera organizada. 🧐

### 7. **Actualización en Tiempo Real**
   - Los cambios en los favoritos, estados de los juegos y el historial de búsquedas se reflejan instantáneamente en la interfaz de usuario gracias a la arquitectura basada en **LiveData**. ⚡
   - Los **ViewModels** gestionan la lógica de los datos y aseguran que la interfaz de usuario esté siempre sincronizada con los cambios de estado en la base de datos local o en la API. 🔄
