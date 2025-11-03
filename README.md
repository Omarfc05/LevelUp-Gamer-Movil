# 🎮 LevelUpGamer

![Kotlin](https://img.shields.io/badge/Kotlin-1.9-blueviolet?logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-blue?logo=android)
![Room](https://img.shields.io/badge/Room-Database-green?logo=sqlite)
![Android Studio](https://img.shields.io/badge/Android%20Studio-Koala-brightgreen?logo=android)


---

## 👥 Autores

- **Omar Filun**
- **Rodrigo Garrido**



---

## 🚀 Descripción del Proyecto

**LevelUpGamer** es una aplicación de comercio electrónico orientada al público gamer.  
Permite a los usuarios:
- Registrarse con nombre, correo, contraseña y una **foto de perfil** seleccionada desde la galería.  
- Iniciar sesión y mantener la sesión activa gracias al almacenamiento local.  
- Navegar por un **catálogo de productos tecnológicos** con diseño gamer y animaciones.  
- Acceder a un **perfil personal**, cambiar su foto y cerrar sesión.

---

## 🧱 Arquitectura

El proyecto utiliza el patrón **MVVM (Model - View - ViewModel)** para mantener una estructura limpia y escalable.


- **Model / Data:** Definición de entidades `User` y `CurrentUser`, acceso a datos mediante DAOs y persistencia local con `Room`.  
- **Repository:** Intermediario entre la base de datos y los ViewModels (`UserRepository`, `CurrentUserRepository`).  
- **ViewModel:** Lógica de negocio y gestión de estado (`AuthViewModel`, `ProductViewModel`).  
- **View (UI):** Pantallas diseñadas con **Jetpack Compose** (Material 3).  

---

## 🧩 Funcionalidades Implementadas

✔️ **Diseño visual con Material 3**  
✔️ **Formularios validados (registro e inicio de sesión)**  
✔️ **Navegación funcional entre pantallas (NavHostController)**  
✔️ **Gestión de estado reactiva con StateFlow y remember**  
✔️ **Persistencia local con Room (SQLite)**  
✔️ **Uso de componente nativo:** selección de imagen desde galería (`ActivityResultContracts.GetContent()`)  
✔️ **Animaciones en catálogo (efecto hover con animateFloatAsState)**  

---

## 🖼️ Pantallas Principales

- **SplashScreen:** Pantalla de inicio con logo animado.  
- **LoginScreen:** Inicio de sesión validado.  
- **RegisterScreen:** Registro con carga opcional de foto de perfil.  
- **CatalogScreen:** Visualización de productos en cuadrícula con animaciones.  
- **ProfileScreen:** Vista del usuario logueado, cambio de foto y cierre de sesión.

---

## 🧠 Tecnologías y librerías utilizadas

- **Kotlin**
- **Jetpack Compose (Material 3)**
- **Room (SQLite)**
- **Coroutines / StateFlow**
- **Coil (carga de imágenes)**
- **Navigation Compose**

---

