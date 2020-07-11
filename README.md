# WebSocket Client API (for Bukkit)

A Simple WebSocket API for Bukkit

Sample Plugin: [websocket-client](https://github.com/patrick-mc/websocket-client/)

---

###### (en-us)

## Introduction

This API provides easy access to websocket client from Bukkit. 

## Features

- Simple WebSocketClient connect / disconnect
- WebSocket Event for Bukkit

## How to Use

### Gradle (Groovy DSL)

```groovy
allprojects {
    repositories {
        mavenCentral() // or maven { url 'https://repo.maven.apache.org/maven2/' }
    }
}
```

```groovy
dependencies {
    implementation 'com.github.patrick-mc:websocket-client-api:1.0.1'
}
```

### Gradle (Kotlin DSL)

```kotlin
allprojects {
    repositories {
        mavenCentral() // or maven("https://repo.maven.apache.org/maven2/")
    }
}
```

```kotlin
dependencies {
    implementation("com.github.patrick-mc:websocket-client-api:1.0.1")
}
```

---

###### (ko-kr)

## 프로젝트 설명

이 API는 WebSocket을 Bukkit에서 쉽게 사용하기 위해 만들어졌습니다.

## 특징

- 간단한 WebSocket 연결 / 해제
- Bukkit을 위한 WebSocket Event

## 사용 방법 

### Gradle (Groovy DSL)

```groovy
allprojects {
    repositories {
        mavenCentral() // 또는 maven { url 'https://repo.maven.apache.org/maven2/' }
    }
}
```

```groovy
dependencies {
    implementation 'com.github.patrick-mc:websocket-client-api:1.0.1'
}
```

### Gradle (Kotlin DSL)

```kotlin
allprojects {
    repositories {
        mavenCentral() // 또는 maven("https://repo.maven.apache.org/maven2/")
    }
}
```

```kotlin
dependencies {
    implementation("com.github.patrick-mc:websocket-client-api:1.0.1")
}
```

---

## Sample Code / 샘플 코드

### Kotlin

```kotlin
val client = WebSocketAPI.createWebSocket("wss://URL_HERE", tls = true, suppress = false)
if (client != null && client.connect()) {
    // Something when successful
} else {
    // Something when failure
}

// implements Listener
fun onWebSocketConnect(event: WebSocketConnectedEvent) {
    if (event.socket == client?.socket) {
        // Something to do
    }
}

client?.disconnect()
```
