# 📱 TaskMaster - CRUD con Jetpack Compose y Room

Aplicación Android desarrollada como ejemplo práctico de un CRUD completo utilizando **Jetpack Compose**, **Room** y una arquitectura limpia basada en **MVVM + Repository**.

Este proyecto sirve como base para aprender cómo construir aplicaciones modernas en Android con persistencia local y buenas prácticas.

---

## 🚀 Características

- Crear tareas
- Listar tareas
- Editar tareas
- Eliminar tareas
- Marcar tareas como completadas
- Persistencia de datos con Room
- UI moderna con Jetpack Compose
- Arquitectura limpia (Presentation, Domain, Data)

---

## 🧠 Arquitectura

El proyecto está estructurado siguiendo una arquitectura limpia:
presentation → UI (Compose) + ViewModel
domain → modelos y contratos (repository)
data → implementación (Room, DAO, Entity)

### Flujo de datos:

UI → ViewModel → Repository → Room (DAO)

- La UI observa el estado
- El ViewModel maneja la lógica
- El Repository desacopla la fuente de datos
- Room gestiona la persistencia local

---

## 🛠️ Tecnologías utilizadas

- Kotlin
- Jetpack Compose
- Material 3
- Room Database
- StateFlow
- Coroutines
- MVVM
- Repository Pattern

---

## 📂 Estructura del proyecto
com.dgb.taskmasterapp
│
├── data
│   ├── local
│   │   ├── dao
│   │   ├── database
│   │   └── entity
│   └── repository
│
├── domain
│   ├── model
│   └── repository
│
├── presentation
│   ├── tasklist
│   └── components
│
├── ui
│   └── theme
│
└── MainActivity.kt

---

## ▶️ Video del tutorial

Este proyecto forma parte de un tutorial completo en YouTube donde se explica paso a paso:

---

## 📦 Cómo ejecutar el proyecto

1. Clonar el repositorio:
```bash
git clone https://github.com/tu-usuario/taskmaster.git

2.	Abrir en Android Studio
3.	Ejecutar la app en un emulador o dispositivo físico


## 💡 Próximas mejoras

•	Modo oscuro 🌙
•	Filtros de tareas (completadas / pendientes)
•	Búsqueda de tareas
•	Navegación entre pantallas
•	Inyección de dependencias con Hilt
•	Sincronización con API

🤝 Contribuciones

Las contribuciones son bienvenidas. Puedes abrir un issue o enviar un pull request.

⸻

## 👨‍💻 Autor

Desarrollado por David González
📺 Canal de YouTube: https://www.youtube.com/channel/UC29eg7ri7dhUe-bxULgVQ8w

⭐ Apoya el proyecto

Si este proyecto te ayudó, dale una estrella ⭐ en GitHub y suscríbete al canal.
