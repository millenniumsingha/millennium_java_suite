# Java Showcase App - Build & Deploy Instructions

## Overview
This project creates a Java Swing application showcasing 4 mini-apps (BattleShips, Cipher Tool, Trip Planner, Odd/Even Game) that runs in the browser via CheerpJ on Firebase Hosting.

## Prerequisites
Ensure these are installed:
- Java JDK 11+ (`java --version`)
- Maven (`mvn --version`)
- Firebase CLI (`firebase --version`)

If missing, install:
```bash
# Java (Ubuntu/Debian)
sudo apt install openjdk-11-jdk

# Maven
sudo apt install maven

# Firebase CLI
npm install -g firebase-tools
```

---

## Step 1: Build the JAR

```bash
cd java-showcase
mvn clean package
```

This creates: `target/JavaShowcase-1.0.jar`

---

## Step 2: Test Locally (Optional)

```bash
java -jar target/JavaShowcase-1.0.jar
```

The Swing app should open. Close it after verifying.

---

## Step 3: Prepare Web Deployment

Copy the JAR to the web folder:
```bash
cp target/JavaShowcase-1.0.jar web/app.jar
```

---

## Step 4: Test CheerpJ Locally (Optional)

Serve the web folder locally:
```bash
cd web
python3 -m http.server 8080
```

Open http://localhost:8080 in Chrome. The Java app should load in browser.

---

## Step 5: Deploy to Firebase

### First-time setup:
```bash
firebase login
firebase init hosting
# Select: Use existing project OR create new
# Public directory: web
# Single-page app: No
# Overwrite index.html: No
```

### Deploy:
```bash
firebase deploy --only hosting
```

---

## Step 6: Verify Deployment

Visit the Firebase URL (shown after deploy). The app should:
1. Show "Loading Java..." message briefly
2. Display the Java Swing app in browser
3. All 4 modules should be functional

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| JAR won't build | Check Java/Maven versions, run `mvn clean` first |
| App doesn't load in browser | Check browser console, ensure CheerpJ CDN is accessible |
| Slow loading | Normal - CheerpJ loads ~10MB runtime, first load is slower |
| UI looks different | CheerpJ renders Swing, minor visual differences expected |

---

## Project Structure

```
java-showcase/
├── pom.xml                     # Maven build config
├── src/main/java/showcase/
│   ├── App.java                # Main entry point
│   └── panels/
│       ├── BattleShipsPanel.java
│       ├── CipherPanel.java
│       ├── TripPlannerPanel.java
│       └── OddEvenPanel.java
├── web/
│   ├── index.html              # CheerpJ loader
│   └── app.jar                 # (created after build)
└── INSTRUCTIONS.md             # This file
```
