Una aplicación Android moderna que consume la [PokeAPI](https://pokeapi.co/) para mostrar información detallada de Pokémon. Desarrollada con **Jetpack Compose** y siguiendo los principios de **Clean Architecture**.

## ✨ Características

- 🔍 **Búsqueda en tiempo real** de Pokémon por nombre
- 📋 **Lista paginada** con carga infinita (LazyVerticalGrid)
- 🎨 **Tarjetas con gradientes** basados en el tipo de Pokémon
- 📊 **Detalle completo** con estadísticas, tipos y habilidades
- 🖼️ **Carga eficiente de imágenes** con Coil
- 🌐 **Consumo de API REST** con Retrofit
- 🎯 **Manejo de estado** con Kotlin Flows
- 🧭 **Navegación entre pantallas** con Compose Navigation

## 🛠️ Tecnologías Utilizadas

### Lenguaje y Framework
- **Kotlin** - Lenguaje principal
- **Jetpack Compose** - UI declarativa moderna

### Arquitectura
- **Clean Architecture** - Separación de capas (Data, Domain, Presentation)
- **MVVM** - Model-View-ViewModel
- **Repository Pattern** - Abstracción de fuentes de datos

### Inyección de Dependencias
- **Dagger Hilt** - Inyección de dependencias compile-time

### Networking
- **Retrofit** - Cliente HTTP para APIs REST
- **Kotlinx Serialization** - Serialización JSON nativa de Kotlin
- **OkHttp** - Cliente HTTP subyacente con logging

### Programación Asíncrona
- **Kotlin Coroutines** - Programación asíncrona
- **Kotlin Flows** - Streams de datos reactivos

### UI y Navegación
- **Jetpack Compose** - Toolkit de UI moderno
- **Navigation Compose** - Navegación declarativa
- **Material Design 3** - Sistema de diseño de Google
- **Coil** - Carga de imágenes optimizada para Compose

### Otras Librerías
- **Timber** - Logging profesional
- **Palette** - Extracción de colores dominantes de imágenes

## 📁 Estructura del Proyecto

```
app/src/main/java/com/example/jetpackcomposepokedex/
├── data/                          # Capa de Datos
│   ├── di/                       # Módulos de Hilt
│   │   ├── NetworkModule.kt
│   │   └── RepositoryModule.kt
│   ├── remote/                   # Fuentes de datos remotas
│   │   ├── api/                 # Interfaces Retrofit
│   │   │   └── PokeApi.kt
│   │   ├── dto/                 # Data Transfer Objects
│   │   └── mapper/              # Mapeadores DTO → Domain
│   └── repository/              # Implementaciones de Repository
│       └── PokemonRepositoryImpl.kt
├── domain/                       # Capa de Dominio
│   ├── model/                   # Modelos de dominio
│   │   ├── Pokemon.kt
│   │   ├── PokemonList.kt
│   │   ├── PokemonStat.kt
│   │   └── PokemonType.kt
│   ├── repository/              # Interfaces de Repository
│   │   └── PokemonRepository.kt
│   └── usecase/                 # Casos de uso
│       ├── GetPokemonListUseCase.kt
│       ├── GetPokemonDetailUseCase.kt
│       └── SearchPokemonUseCase.kt
├── presentation/                 # Capa de Presentación
│   ├── model/                   # Modelos de UI
│   │   ├── PokemonListItemUi.kt
│   │   ├── PokemonDetailUi.kt
│   │   └── ...
│   ├── screen/                  # Pantallas
│   │   ├── pokemonlist/
│   │   │   ├── PokemonListScreen.kt
│   │   │   └── PokemonListViewModel.kt
│   │   └── pokemondetail/
│   │       ├── PokemonDetailScreen.kt
│   │       └── PokemonDetailViewModel.kt
│   ├── navigation/              # Configuración de navegación
│   │   ├── NavManager.kt
│   │   └── Screen.kt
│   ├── mapper/                  # Mapeadores Domain → UI
│   └── ui/theme/               # Temas y estilos
├── MainActivity.kt
└── PokedexApplication.kt
```

## 🎯 Arquitectura Clean

La aplicación sigue los principios de **Clean Architecture** con tres capas bien definidas:

### 1. Capa de Datos (Data)
- **Remote**: Comunicación con PokeAPI usando Retrofit
- **DTOs**: Objetos de transferencia de datos (PokemonDto, PokemonListDto)
- **RepositoryImpl**: Implementación concreta de los repositorios
- **Mappers**: Conversión entre DTOs y Modelos de Dominio

### 2. Capa de Dominio (Domain)
- **Modelos**: Entidades de negocio puras (Pokemon, PokemonList, etc.)
- **Repository Interfaces**: Contratos para las fuentes de datos
- **Use Cases**: Lógica de negocio específica
  - `GetPokemonListUseCase`: Obtener lista paginada
  - `GetPokemonDetailUseCase`: Obtener detalle de un Pokémon
  - `SearchPokemonUseCase`: Buscar Pokémon por nombre

### 3. Capa de Presentación (Presentation)
- **ViewModels**: Manejo de estado UI con StateFlow
- **Screens**: Composables de Jetpack Compose
- **UI Models**: Modelos específicos para la capa de UI
- **Mappers**: Conversión entre Domain y UI Models

## 🚀 Funcionalidades

### Pantalla Principal (PokemonListScreen)
- ✅ Lista de Pokémon en grid de 2 columnas
- ✅ Búsqueda en tiempo real por nombre
- ✅ Paginación automática al hacer scroll
- ✅ Tarjetas con color según tipo de Pokémon
- ✅ Indicadores de carga
- ✅ Manejo de errores con opción de reintentar

### Pantalla de Detalle (PokemonDetailScreen)
- ✅ Imagen grande del Pokémon
- ✅ Tipos con colores característicos
- ✅ Estadísticas base con barras animadas
- ✅ Información de peso y altura
- ✅ Habilidades del Pokémon
- ✅ Botón de retroceso

## 📸 Screenshots

*(Agrega aquí screenshots de tu app cuando la tengas ejecutando)*

## 🏃‍♂️ Cómo Ejecutar

### Requisitos Previos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17 o superior
- Android SDK 35
- Dispositivo o emulador con API 24+ (Android 7.0)

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/ReneSoAr/JetpackComposePokedex.git
   cd JetpackComposePokedex
   ```

2. **Abrir en Android Studio**
   - Abre Android Studio
   - Selecciona "Open an existing project"
   - Navega a la carpeta del proyecto y selecciónala

3. **Sincronizar dependencias**
   - Android Studio sincronizará automáticamente las dependencias de Gradle
   - Si no, haz clic en "Sync Now" en la barra de notificaciones

4. **Ejecutar la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Haz clic en el botón "Run" (▶️) o presiona `Shift + F10`

## 🔧 Configuración Técnica

### Dependencias Principales (libs.versions.toml)

```toml
[versions]
agp = "8.7.0"
kotlin = "2.0.21"
compose-bom = "2024.11.00"
hilt = "2.52"
retrofit = "2.11.0"
kotlinx-serialization = "1.7.3"
coroutines = "1.9.0"
coil = "2.7.0"

[libraries]
# Compose
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "compose-bom" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }

# Navigation
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version = "2.8.4" }

# Hilt
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version = "1.2.0" }

# Retrofit
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-kotlinx-serialization = { group = "com.squareup.retrofit2", name = "converter-kotlinx-serialization", version.ref = "retrofit" }
okhttp = { group = "com.squareup.okhttp3", name = "okhttp", version = "4.12.0" }
okhttp-logging = { group = "com.squareup.okhttp3", name = "logging-interceptor", version = "4.12.0" }

# Serialization
kotlinx-serialization-json = { group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version.ref = "kotlinx-serialization" }

# Coroutines
kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx", name = "kotlinx-coroutines-android", version.ref = "coroutines" }

# Coil
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }

# Timber
timber = { group = "com.jakewharton.timber", name = "timber", version = "5.0.1" }
```

## 🎨 Diseño UI/UX

### Paleta de Colores
La aplicación utiliza una paleta de colores basada en los tipos de Pokémon:
- 🔥 Fuego: `#F08030`
- 💧 Agua: `#6890F0`
- 🌿 Planta: `#78C850`
- ⚡ Eléctrico: `#F8D030`
- ❄️ Hielo: `#98D8D8`
- 👊 Lucha: `#C03028`
- 🧪 Veneno: `#A040A0`
- 🌍 Tierra: `#E0C068`
- 🪽 Volador: `#A890F0`
- 🧠 Psíquico: `#F85888`
- 🐛 Bicho: `#A8B820`
- 🪨 Roca: `#B8A038`
- 👻 Fantasma: `#705898`
- 🐉 Dragón: `#7038F8`
- ⚙️ Acero: `#B8B8D0`
- 🧚 Hada: `#EE99AC`
- ⭕ Normal: `#A8A878`

### Tipografía
- **Roboto Condensed** - Títulos y nombres de Pokémon
- **Roboto** - Texto general y contenido

## 🧪 Testing

El proyecto incluye configuración básica para testing:
- **Unit Tests**: JUnit 4 para lógica de negocio
- **Instrumented Tests**: Espresso para pruebas de UI
- **Preview Composables**: Previews en Android Studio para UI

```kotlin
// Ejemplo de Preview disponible
@Preview(showBackground = true, name = "Pokemon List - Success")
@Composable
fun PokemonListSuccessPreview() { ... }

@Preview(showBackground = true, name = "Error State")
@Composable
fun ErrorStatePreview() { ... }
```

## 📚 Recursos de Aprendizaje

Esta aplicación fue construida como proyecto de aprendizaje. Conceptos clave aplicados:

- [Clean Architecture - Robert C. Martin](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Kotlin Flows](https://kotlinlang.org/docs/flow.html)
- [Dagger Hilt](https://dagger.dev/hilt/)
- [PokeAPI Documentation](https://pokeapi.co/docs/v2)

## 🤝 Contribuir

Las contribuciones son bienvenidas. Si encuentras algún bug o tienes una mejora:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📝 Licencia

Distribuido bajo la Licencia MIT. Ver `LICENSE` para más información.

## 👨‍💻 Autor

**René Jesús Sosa Arana**

- 📧 Email: renejsosaarana@gmail.com
- 💼 LinkedIn: [René Jesús Sosa Arana](https://www.linkedin.com/in/ren%C3%A9-jes%C3%BAs-sosa-arana-38b8a6225/)
- 🐙 GitHub: [@ReneSoAr](https://github.com/ReneSoAr)

---

⭐ **Si te gustó este proyecto, no olvides darle una estrella!**

<p align="center">
  <img src="https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/items/poke-ball.png" alt="Pokeball" width="50"/>
  <br/>
  <i>Desarrollado con 💙 y mucho ☕</i>
</p>
