# VaultX 🔐

**AI-Driven Password Vault - Your Digital Security Companion**

[![Android](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://android.com)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

VaultX is a comprehensive, secure Android application designed to help users safeguard their sensitive information through advanced encryption and AI-driven features. Built with modern Android development practices, VaultX offers an intuitive user experience via Jetpack Compose while maintaining industry-standard security protocols to protect your passwords, personal notes, and credentials.

## 📸 App Screenshots

### 🔐 Start Page
![Start](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Start.jpg?raw=true)

### 🔑 Set Master Key
![Set Key](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Set%20Key.jpg?raw=true)

### ⚠️ Weak Key Warning
![Weak Key](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Weak%20Key.jpg?raw=true)

### ✅ Strong Key Confirmation
![Strong Key](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Strong%20Key.jpg?raw=true)

### 🔒 AES-256 Encrypted Secure Key
![Secure Key](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Secure%20Key.jpg?raw=true)

### 🏠 Home / Initial Page
![Initial Page](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Initial%20Page.jpg?raw=true)

### 📂 Password Storage Page
![Password Page](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Password%20Page.jpg?raw=true)

### ✨ Features Highlight
![Features](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Features.jpg?raw=true)

### ⚙️ Settings Page
![Settings Page](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/blob/main/Setting%20Page.jpg?raw=true)


## 🌟 Key Features

### 🔐 Enterprise-Grade Security
- **AES-256 Encryption**: Data secured using Android Keystore System
- **Zero-Knowledge Architecture**: Only you can access your data
- **Local Storage**: All data saved locally on your device, never on remote servers
- **Biometric Authentication**: Fingerprint and face recognition support
- **Auto-Lock**: Configurable timeout for enhanced security

### 🧠 AI-Driven Intelligence
- **Password Strength Analysis**: AI-powered assessment of password security
- **Smart Password Generator**: Intelligent password creation with customizable parameters
- **Duplicate Detection**: Automatically identifies and alerts about duplicate passwords
- **Security Recommendations**: AI-driven suggestions for improving your security posture

### 📱 Modern User Experience
- **Jetpack Compose UI**: Fluid animations and responsive design
- **Material Design 3**: Modern aesthetic with dynamic theming
- **Dark/Light Theme**: Automatic theme switching based on system preferences
- **Accessibility**: Full support for accessibility features
- **Intuitive Navigation**: Clean, organized interface for effortless use

### 🛡️ Advanced Password Management
- **Secure Password Generator**: Customizable parameters for strong passwords
- **Password Health Dashboard**: Monitor and improve your password security
- **Expiration Reminders**: Get notified when passwords need updating
- **Categorized Storage**: Organize entries by type or custom categories
- **Powerful Search**: Find entries quickly with intelligent search

### 📊 Data Management
- **Import/Export**: Secure backup and restore functionality
- **Custom Fields**: Add personalized fields to entries
- **Rich Text Notes**: Secure notes with formatting support
- **Tagging System**: Organize entries with custom tags

## 🔧 Technical Stack

| Component | Technology |
|-----------|------------|
| **Architecture** | MVVM with Clean Architecture |
| **UI Framework** | Jetpack Compose + Material Design 3 |
| **Language** | Kotlin |
| **Database** | Room with encryption layer |
| **Security** | Android Keystore, EncryptedSharedPreferences |
| **Dependency Injection** | Hilt |
| **State Management** | StateFlow, SharedFlow |
| **Authentication** | Biometric APIs |
| **Testing** | JUnit, Espresso, Compose UI Testing |

## 🚀 Getting Started

### Prerequisites

Before you begin, ensure you have the following installed:

- **Android Studio**: Latest version from [developer.android.com](https://developer.android.com)
- **Java 17**: JDK 17 or higher
- **Gradle**: 8.9.X version
- **Android SDK**: API Level 33 or higher
- **Git**: For version control

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault.git
   cd VaultX-AI-Driven-Password-Vault
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Go to `File > Open`
   - Navigate to the project directory and open it
   - Wait for Gradle sync to complete

3. **Build and Run**
   - Select a device (emulator or physical device)
   - Click `Run > Run 'android-app'` or press `Shift + F10`
   - For first run, ensure your emulator has Google Play Services

### Configuration

1. **Build Variants**
   - Debug: For development with debugging enabled
   - Release: Optimized build for production

## 📂 Project Structure

```
VaultX/
├── android-app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/vaultx/
│   │   │   │   ├── data/          # Repositories, data sources
│   │   │   │   ├── di/            # Hilt modules
│   │   │   │   ├── domain/        # Use cases, models
│   │   │   │   ├── presentation/  # Screens, viewmodels
│   │   │   │   │   ├── common/    # Shared UI components
│   │   │   │   │   ├── dashboard/ # Dashboard screen
│   │   │   │   │   ├── settings/  # Settings screen
│   │   │   │   │   ├── vault/     # Vault entries
│   │   │   │   │   └── auth/      # Auth screens
│   │   │   │   ├── util/          # Utility classes
│   │   │   │   └── VaultXApp.kt   # Application class
│   │   │   ├── res/               # Resources (layouts, drawables)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                  # Unit tests
│   │   └── androidTest/           # UI/instrumentation tests
│   ├── build.gradle.kts
│   └── proguard-rules.pro
├── buildSrc/
│   └── src/main/kotlin/
│       ├── Dependencies.kt
│       └── Plugins.kt
├── fastlane/
│   ├── Appfile
│   ├── Fastfile
│   └── Pluginfile
├── gradle/wrapper/
├── icons/
├── screenshots/
├── .github/workflows/
│   ├── android_build.yml
│   └── android_test.yml
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── .gitignore
├── LICENSE
└── README.md
```

## 🧪 Testing

### Running Tests

```bash
# Unit tests
./gradlew test

# Integration tests
./gradlew connectedAndroidTest

# UI tests
./gradlew connectedDebugAndroidTest
```

### Test Coverage

- **Unit Tests**: Business logic and data layer
- **Integration Tests**: Database operations and repositories  
- **UI Tests**: User interaction flows and screen navigation

## 🔒 Security Features

### Encryption
- **AES-256**: Industry-standard encryption for all stored data
- **Android Keystore**: Hardware-backed key storage when available
- **Key Derivation**: PBKDF2 for password-based encryption

### Authentication
- **Biometric**: Fingerprint and face recognition
- **PIN/Pattern**: Fallback authentication methods
- **Auto-Lock**: Configurable timeout periods

### Privacy
- **Local Storage**: No data sent to external servers
- **Memory Protection**: Sensitive data cleared from memory
- **Screen Protection**: Content hidden in recent apps

## 🤝 Contributing

We welcome contributions to VaultX! Here's how you can help:

### Code Contributions

1. **Fork** the repository
2. **Create** a feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** your changes (`git commit -m 'Add amazing feature'`)
4. **Push** to the branch (`git push origin feature/amazing-feature`)
5. **Open** a Pull Request

### Guidelines

- Follow the existing code style and architecture
- Add unit tests for new functionality
- Update documentation when appropriate
- Keep PRs focused on a single feature or bug fix
- Ensure all tests pass before submitting

### Bug Reports

Use GitHub Issues to report bugs. Please include:
- Device information and Android version
- Steps to reproduce the issue
- Expected vs actual behavior
- Screenshots or logs if applicable


## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

- **Issues**: [GitHub Issues](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/issues)
- **Discussions**: [GitHub Discussions](https://github.com/ishashwatthakur/VaultX-AI-Driven-Password-Vault/discussions)
- **Email**: [Contact Developer](mailto:your-email@domain.com)

## 🙏 Acknowledgments

- Android development community for excellent resources
- Material Design team for design guidelines
- Security researchers for encryption best practices
- Open source libraries that made this project possible

## ⭐ Show Your Support

If you find VaultX helpful, please consider:
- ⭐ Starring the repository
- 🐛 Reporting bugs
- 💡 Suggesting new features
- 🤝 Contributing to the codebase

---

**Made with ❤️ for people who value privacy and security**

*VaultX - Your Digital Security Companion*
