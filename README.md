[![License](https://img.shields.io/github/license/mashape/apistatus.svg?maxAge=2592000)]()
[![](https://jitpack.io/v/ZrdJ/javachord.svg)](https://jitpack.io/#ZrdJ/javachord)
![GitHub Workflow Status (branch)](https://github.com/zrdj/javachord/actions/workflows/maven.yml/badge.svg)

# Javachord

This library aims to provide a typed wrapper for creating Application Commands and Message Components using extendable
class structures.

## Maven
Add the Jitpack repository to your build file

```xml

<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Release artifact

```xml

<dependency>
    <groupId>com.github.zrdj</groupId>
    <artifactId>javachord</artifactId>
    <version>3.8.0.7</version>
</dependency>
```

# Versioning

As this library is tied directly to [Javacord](https://github.com/Javacord/Javacord), packages will be released based on
the version of [Javacord](https://github.com/Javacord/Javacord) and extended by an additional build version, which will
only indicate that something in this library has changed.
So for the version `3.8.0` of [Javacord](https://github.com/Javacord/Javacord), this library will be released in
version `3.8.0.X`. This library will always release a matching version
of [Javacord](https://github.com/Javacord/Javacord), even if there are no changes to this libraries code.

# Basic Usage

Make sure you register the current DiscordApi with Javachord by using `Javachord.Instance.Get.register()` method as in
the example below

```java
    ...
private DiscordApi _discord;
private final String _token = System.getProperty("token");
private final long _serverId = Long.parseLong(System.getProperty("serverId"));

public void start() {
    _discord = new DiscordApiBuilder()
            .setToken(_token)
            .setIntents(
                    Intent.GUILDS
                    , Intent.GUILD_MEMBERS
                    , Intent.GUILD_MESSAGES
                    , Intent.GUILD_BANS
                    , Intent.GUILD_INVITES
                    , Intent.GUILD_PRESENCES
                    , Intent.MESSAGE_CONTENT
            )
            .login()
            .join()
    ;
    var server = _discord.getServerById(_serverId).get();
    Javachord.Instance.Get.register(_discord, server);
    System.out.printf("Discord bot successfully initialized with ID %s%n", _discord.getYourself().getIdAsString());
}
    ...
```

Javachord components will register themselves globally as soon as they get created.
