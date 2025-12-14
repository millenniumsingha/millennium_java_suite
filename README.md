# Java Playground

A showcase of Java mini-applications that runs directly in the browser via [CheerpJ](https://cheerpj.com/).

![Java](https://img.shields.io/badge/Java-11-orange)
![Swing](https://img.shields.io/badge/GUI-Swing-blue)
![CheerpJ](https://img.shields.io/badge/Runtime-CheerpJ%203.0-green)

## 🎮 Live Demo

[View Live Demo](https://YOUR_FIREBASE_URL.web.app)

## 📦 Applications Included

| App | Description |
|-----|-------------|
| **BattleShips** | Classic naval battle game vs computer AI |
| **Cipher Tool** | Caesar cipher encryption/decryption with live preview |
| **Trip Planner** | Budget calculator, timezone converter & distance calculator using Haversine formula |
| **Odd/Even** | Quick odds and evens finger game against computer |

## 🛠️ Technology

This project demonstrates:

- **Java Swing** - Desktop GUI framework
- **CheerpJ** - Java-to-WebAssembly runtime enabling browser execution
- **Firebase Hosting** - Static file hosting
- **Maven** - Build automation

## 🚀 Running Locally

### Prerequisites
- Java JDK 11+
- Maven

### Build & Run
```bash
# Build JAR
mvn clean package

# Run desktop version
java -jar target/JavaShowcase-1.0.jar

# Or test web version
cp target/JavaShowcase-1.0.jar web/app.jar
cd web
python3 -m http.server 8080
# Open http://localhost:8080
```

## 📁 Project Structure

```
java-showcase/
├── src/main/java/showcase/
│   ├── App.java                 # Main entry, sidebar navigation
│   └── panels/
│       ├── BattleShipsPanel.java
│       ├── CipherPanel.java
│       ├── TripPlannerPanel.java
│       └── OddEvenPanel.java
├── web/
│   ├── index.html               # CheerpJ loader
│   └── app.jar                  # Built JAR (after compilation)
├── pom.xml
└── README.md
```

## 🌐 Deployment

```bash
# Build
mvn clean package
cp target/JavaShowcase-1.0.jar web/app.jar

# Deploy to Firebase
firebase deploy --only hosting
```

## 📝 Origin

These applications started as Java coursework exercises demonstrating:
- Control flow and game logic (BattleShips, Odd/Even)
- String manipulation and algorithms (Cipher)
- Mathematical computations and Haversine formula (Trip Planner)

Reimplemented as a unified Swing application to demonstrate GUI development and browser deployment capabilities.

## 📄 License

MIT License - feel free to use and modify.
