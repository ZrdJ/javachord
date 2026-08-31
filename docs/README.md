---
type: index
title: javachord — Knowledge Layer
lang: en
updated: 2026-08-31
---

# javachord — Knowledge Layer

Typed wrapper around Javacord for building Discord Application Commands and Message
Components through extendable class hierarchies instead of Javacord's raw builder API;
versioned in lockstep with the underlying Javacord release.

Repo-specific knowledge. What concerns more than this repo lives in the knowledge layer
of the WS root (`~/workspaces/personal/docs/`).

## Folders

- `project/decisions/` — why things are the way they are (ADRs)
- `project/worklog/` — work logs, one file per day
- `project/research/` — self-collected material
- `project/sources/` — material delivered by others
- `wayfinding/` — undertakings whose path is not yet settled
- `changes/` — ongoing undertakings whose path is settled
- `archive/` — completed changes
- `specs/` — current state per capability

## Entry points

- `pom.xml` — coordinates (`com.github.zrdj:javachord`), Java 11, tracks the Javacord version it wraps
- `src/main/java/com/github/zrdj/javachord/Javachord.java` — registration entry point (`Javachord.Instance.Get.register()`)
- `src/main/java/com/github/zrdj/javachord/command/ApplicationCommand.java` — application command base type
- `src/main/java/com/github/zrdj/javachord/component/MessageComponent.java` — message component base type
- `README.md` (repo root) — versioning scheme and basic registration example

This repo does not (yet) have its own `CLAUDE.md` — working rules apply from
`zrdj/CLAUDE.md` and the provider levels above it.
