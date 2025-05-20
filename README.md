# 📚 Bookpedia

Bookpedia is a modern book discovery app built with Kotlin Multiplatform Mobile (KMM). It leverages the Google Books API to provide a rich, interactive experience for exploring trending books across different categories and time periods.

## ✨ Features

### 📱 User Interface
- Modern, Material Design-based UI
- Smooth animations and transitions
- Pull-to-refresh functionality
- Responsive grid layout
- Beautiful book details view
- Category-based browsing
- Time-based trending sections (Week/Month/Year)

### 📖 Book Discovery
- Trending books by category
- Detailed book information
- High-quality book covers
- Author information
- Publication details
- Book descriptions
- Multiple categories (Fiction, Non-Fiction, Business, Technology, etc.)

## 🏗 Architecture

The app follows Clean Architecture principles with a clear separation of concerns:

### 📐 Layers

```
app/
├── core/
│   ├── theme/           # App theming and styling
│   └── domain/          # Core domain models and utilities
├── features/
│   └── booklist/
│       ├── data/        # Data layer
│       │   ├── mapper/  # Data mapping
│       │   ├── model/   # Data models
│       │   ├── remote/  # Remote data source
│       │   └── repository/ # Repository implementations
│       ├── domain/      # Domain layer
│       │   ├── model/   # Domain models
│       │   ├── repository/ # Repository interfaces
│       │   └── usecase/ # Business logic
│       └── presentation/ # UI layer
│           ├── composables/ # UI components
│           └── viewmodel/   # ViewModels
```

### 🏛 Architecture Components

1. **Presentation Layer**
   - Jetpack Compose UI
   - MVVM pattern
   - State management with StateFlow
   - Composable UI components
   - Material Design components

2. **Domain Layer**
   - Use cases for business logic
   - Repository interfaces
   - Domain models
   - Error handling

3. **Data Layer**
   - Repository implementations
   - Remote data source
   - Data mapping
   - Error handling
   - API integration

## 🛠 Technologies Used

### 📱 UI & Presentation
- **Jetpack Compose**: Modern UI toolkit
- **Material Design**: UI components and styling
- **Compose Multiplatform**: Cross-platform UI
- **Coil**: Image loading and caching

### 🔄 Networking & Data
- **Ktor**: HTTP client
- **Google Books API**: Book data source
- **Kotlinx Serialization**: JSON parsing
- **Coroutines**: Asynchronous programming
- **Flow**: Reactive streams

### 🏗 Architecture & DI
- **Clean Architecture**: Project structure
- **MVVM**: Presentation pattern
- **Repository Pattern**: Data management
- **Use Cases**: Business logic encapsulation

### 🧪 Error Handling
- Custom error types
- User-friendly error messages
- Proper error state management
- Network error handling

## 🎨 UI/UX Features

### 💅 Styling
- Custom typography system
- Dynamic color scheme
- Elevation and shadows
- Proper spacing and padding
- Responsive layouts

### 🎬 Animations
- Screen transitions
- Content loading animations
- Pull-to-refresh indicators
- Card animations
- State change animations

## 🚀 Getting Started

1. Clone the repository
2. Add your Google Books API key in `GoogleBooksHttpClient.kt`
3. Build and run the project

## 🤝 Credits

This project was developed with the assistance of Claude, an AI assistant by Anthropic. Claude helped with:
- Architecture design
- Implementation guidance
- UI/UX improvements
- Code optimization
- Best practices implementation

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Google Books API for providing the book data
- Kotlin Multiplatform team for the amazing framework
- Jetpack Compose team for the modern UI toolkit
- Claude (Anthropic) for development assistance
- All the open-source libraries used in this project

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that's common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple's CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you're sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.

# Bookpedia

A cross-platform book discovery application built with Kotlin Multiplatform and Compose Multiplatform.

## Features

- Browse trending books in various categories
- Search for books by title, author, or keywords
- View detailed book information
- Material Design UI with smooth animations
- Cross-platform (Android, iOS, Desktop, Web)

## Architecture

The application follows Clean Architecture principles with the following layers:

- **Presentation**: UI components built with Compose Multiplatform
- **Domain**: Business logic and use cases
- **Data**: Repository implementation and data sources

## Tech Stack

- **Kotlin Multiplatform**: For sharing code across platforms
- **Compose Multiplatform**: For UI development
- **Koin**: For dependency injection
- **Ktor**: For networking
- **Kotlinx Serialization**: For JSON parsing
- **Coil**: For image loading
- **Google Books API**: For book data

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Kotlin 1.8.0 or later
- Google Books API key

### Setup

1. Clone the repository
2. Open the project in Android Studio
3. Add your Google Books API key in `GoogleBooksHttpClient.kt`
4. Build and run the application

### Running on Different Platforms

#### Android

```
./gradlew :composeApp:assembleDebug
```

#### Desktop

```
./gradlew :composeApp:run
```

#### iOS

Open the Xcode project in the `iosApp` directory and run it.

#### Web

```
./gradlew :composeApp:wasmJsBrowserDevelopmentRun
```

## Project Structure

- `composeApp/`: Main module containing shared code
  - `src/commonMain/`: Code shared across all platforms
    - `kotlin/`: Kotlin source files
      - `core/`: Core functionality
      - `features/`: Feature modules
      - `di/`: Dependency injection setup
  - `src/androidMain/`: Android-specific code
  - `src/iosMain/`: iOS-specific code
  - `src/desktopMain/`: Desktop-specific code
  - `src/wasmJsMain/`: Web-specific code

## License

This project is licensed under the MIT License - see the LICENSE file for details.