# metrics-ehcache3

[English](./README.md) | [简体中文](./README.zh-CN.md)

> **Status**: scaffold module on the `feature/2.0.x` line (JDK 17). The pom, license and build plumbing are in place; no source code or tests exist in this branch yet. Artifacts are not yet published to Maven Central.

## Table of Contents

- [1. Project Overview](#1-project-overview)
- [2. Features & Status](#2-features--status)
- [3. Requirements & Compatibility](#3-requirements--compatibility)
- [4. Architecture & Modules](#4-architecture--modules)
- [5. Installation](#5-installation)
- [6. Quick Start](#6-quick-start)
- [7. Configuration](#7-configuration)
- [8. Core Usage / API](#8-core-usage--api)
- [9. Testing & Build](#9-testing--build)
- [10. Versioning & Branches](#10-versioning--branches)
- [11. Contributing & License](#11-contributing--license)

## 1. Project Overview

`metrics-ehcache3` is the planned [Dropwizard Metrics](https://metrics.dropwizard.io/) integration for [Ehcache 3.x](https://www.ehcache.org/) (project description: "Metrics + Ehcache 3.x for Ehcache monitoring").

What it is (intent):

- Expose Ehcache 3 cache statistics (hits, misses, evictions, puts, removals, ...) as Dropwizard Metrics gauges/metrics;
- Allow registering cache-level and manager-level metrics in a `MetricRegistry`.

What it is not (current state):

- There is **no Java source code in this branch yet** — the module currently contains only the Maven project skeleton (`pom.xml`, `mvnw`, `LICENSE`, `README`). The dependency baseline (ehcache 3.8.1, metrics-core 4.0.2) is already declared in the pom.

Typical scenarios (planned, once implemented):

| Scenario | Planned API |
| :--- | :--- |
| Cache-level hit/miss gauges | A per-`Cache` metrics wrapper registered in a `MetricRegistry` |
| Manager-level stats | A `CacheManager`-scoped metrics set |

> **Assumption**: concrete class names are not yet defined; the module will follow the same shape as the other `metrics-*` components (Dropwizard `MetricSet` / `HealthCheck` style).

## 2. Features & Status

| Capability | Status | Notes |
| :--- | :--- | :--- |
| Ehcache 3 cache metrics | Not yet implemented | No sources in this branch |
| Health checks | Not yet implemented | — |
| Maven skeleton | In place | pom with ehcache 3.8.1 + metrics-core 4.0.2 + healthchecks declared |
| Tests | Not yet present | — |

## 3. Requirements & Compatibility

| Item | Requirement |
| :--- | :--- |
| JDK | 17+ |
| Maven | 3.0+ (Maven Wrapper `mvnw` included) |
| Declared deps | ehcache 3.8.1, metrics-core 4.0.2, metrics-healthchecks, slf4j-api 2.0.18, javax.servlet-api (provided), lombok (provided); junit 4.13.2 (test) |

Version lines:

| Branch | JDK | Version pattern |
| :--- | :---: | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` |
| `feature/2.0.x` | 17 | `2.0.x.*` |
| `feature/3.0.x` | 21 | `3.0.x.*` |

## 4. Architecture & Modules

```text
(planned)
Ehcache CacheManager / Cache
        |
        v
Metrics wrapper (MetricSet / gauges per cache)
        |
        v
MetricRegistry (Dropwizard metrics-core 4.0.x)
```

Single-module jar. Currently no Java packages exist yet; the intended package base follows the `com.codahale.metrics.*` convention used by the sibling `metrics-*` components (**Assumption**).

## 5. Installation

The coordinate is reserved; install once a first release exists:

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>metrics-ehcache3</artifactId>
    <version>2.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

Gradle:

```groovy
implementation 'io.github.easy4j:metrics-ehcache3:2.0.x.x.20260630-SNAPSHOT'
```

Snapshots are served from the project's private repository (see `distributionManagement` in the pom). No Maven Central release is available yet.

## 6. Quick Start

No Quick Start yet — there is no public API in this branch. Watch this space; the module will ship an Ehcache-to-Metrics registration helper once implemented.

## 7. Configuration

No configuration properties exist yet.

## 8. Core Usage / API

No public API in this branch. The declared dependency baseline is `org.ehcache:ehcache:3.8.1` + `io.dropwizard.metrics:metrics-core:4.0.2`.

## 9. Testing & Build

```bash
./mvnw clean verify
```

The pom is pre-configured with the standard build gate used across the org:

- JaCoCo coverage reporting plus a line-coverage check rule with a 90% minimum target (`haltOnFailure=false`);
- Source and Javadoc jars attached at package time;
- a `central` release profile (GPG signing + Central publishing) reserved for official releases.

The build succeeds today because there are no sources and no tests to run.

## 10. Versioning & Branches

Three parallel version lines, each bound to a JDK baseline:

| Branch | JDK | Version pattern | Maintenance |
| :--- | :---: | :--- | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` | Current development line |
| `feature/2.0.x` | 17 | `2.0.x.*` | Maintained in parallel |
| `feature/3.0.x` | 21 | `3.0.x.*` | Maintained in parallel |

Snapshots on this branch are versioned `2.0.x.x.20260630-SNAPSHOT`.

## 11. Contributing & License

Contributions are welcome — open an issue or pull request on GitHub. All source files are licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).
