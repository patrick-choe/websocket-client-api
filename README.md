# WebSocket Client API (for Bukkit)

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
    implementation 'com.github.patrick-mc:websocket-client-api:1.0'
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
    implementation("com.github.patrick-mc:websocket-client-api:1.0")
}
```

## Note (Important)

Do not forget to include this API in the plugin. (as a FatJar)

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
    implementation 'com.github.patrick-mc:websocket-client-api:1.0'
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
    implementation("com.github.patrick-mc:websocket-client-api:1.0")
}
```

## 주의 사항

위 라이브러리는 플러그인이 아니므로, 사용하실 플러그인에 포함시켜 사용해주세요. (FatJar로 사용)