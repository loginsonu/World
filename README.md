# WorldApp

## Overview
This Android app serves as a sample project for demonstrating my development skills, 
specifically focusing on Clean Architecture, MVVM (Model-View-ViewModel) pattern, and Jetpack Compose. 
It features a simple, modular structure aimed at clean code, testability, scalability, maintainability,
performance, readability and simplicity.

## Features
This app shows some countries list and their details.

## Architecture
This app follows the MVVM with Clean Architecture principles, organizing code into features
and further features are divided into layers:
### 1.Data Layer: Manages data sources (e.g., local database, remote API).
### 2.Domain Layer: Contains business logic, use cases, and repositories.
### 3.Presentation Layer: Handles UI elements, with Jetpack Compose and MVVM.

Dividing the code into features layer helps in scalability of project and further data, domain
and presentation layer has dedicated responsibilities, making the code easy to maintain and extend.

## Tech Stack
### Language: Kotlin
### UI: Jetpack Compose
### Architecture: MVVM, Clean Architecture
### Networking: Retrofit
### Dependency Injection: Hilt
### Coroutines & Flow: For background tasks and asynchronous programming

## SetUp
Latest android studio version required like Android Studio Ladybug
