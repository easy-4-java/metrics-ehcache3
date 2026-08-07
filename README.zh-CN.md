# metrics-ehcache3

[English](./README.md) | [简体中文](./README.zh-CN.md)

[![Java](https://img.shields.io/badge/Java-21-orange)](https://github.com/easy-4-java/metrics-ehcache3) [![License](https://img.shields.io/badge/license-Apache%202.0-green)](./LICENSE)

metrics-ehcache3 是规划中的 Dropwizard Metrics 与 Ehcache 3.x 集成组件（项目定位：Metrics + Ehcache 3.x for Ehcache monitoring）。

> **项目状态**：`feature/3.0.x` 版本线（JDK 8）上的脚手架模块。pom、许可证与构建管线已就位，但本分支尚无任何源码与测试。制品尚未发布到 Maven Central。

## 目录

- [1. 项目概述](#1-项目概述)
- [2. 功能与状态](#2-功能与状态)
- [3. 环境要求与兼容性](#3-环境要求与兼容性)
- [4. 架构与模块](#4-架构与模块)
- [5. 安装](#5-安装)
- [6. 快速开始](#6-快速开始)
- [7. 配置](#7-配置)
- [8. 核心用法 / API](#8-核心用法--api)
- [9. 测试与构建](#9-测试与构建)
- [10. 版本与分支](#10-版本与分支)
- [11. 贡献与许可](#11-贡献与许可)

## 1. 项目概述

`metrics-ehcache3` 是规划中的 [Dropwizard Metrics](https://metrics.dropwizard.io/) 与 [Ehcache 3.x](https://www.ehcache.org/) 集成组件（项目定位：`Metrics + Ehcache 3.x for Ehcache monitoring`）。

是什么（规划意图）：

- 将 Ehcache 3 缓存统计（命中、未命中、驱逐、写入、移除等）暴露为 Dropwizard Metrics 的 gauge/metric；
- 支持将缓存级与 CacheManager 级指标注册到 `MetricRegistry`。

不是什么（当前状态）：

- 本分支**尚无任何 Java 源码**——目前仅有 Maven 项目骨架（`pom.xml`、`mvnw`、`LICENSE`、`README`）。依赖基线（ehcache 3.8.1、metrics-core 4.0.2）已在 pom 中声明。

典型场景（实现后的规划）：

| 场景 | 规划 API |
| :--- | :--- |
| 缓存级命中/未命中 gauge | 注册到 `MetricRegistry` 的按 `Cache` 指标包装器 |
| 管理器级统计 | `CacheManager` 作用域的指标集合 |

> **假设**：具体类名尚未定义；模块将沿用其他 `metrics-*` 组件的形态（Dropwizard `MetricSet` / `HealthCheck` 风格）。

## 2. 功能与状态

| 能力 | 状态 | 说明 |
| :--- | :--- | :--- |
| Ehcache 3 缓存指标 | 未实现 | 本分支无源码 |
| 健康检查 | 未实现 | — |
| Maven 骨架 | 已就位 | pom 已声明 ehcache 3.8.1 + metrics-core 4.0.2 + healthchecks |
| 测试 | 暂无 | — |

## 3. 环境要求与兼容性

| 项目 | 要求 |
| :--- | :--- |
| JDK | 21+ |
| Maven | 3.0+（内置 Maven Wrapper `mvnw`） |
| 已声明依赖 | ehcache 3.8.1、metrics-core 4.0.2、metrics-healthchecks、slf4j-api 2.0.18、javax.servlet-api（provided）、lombok（provided）；junit 4.13.2（测试） |

版本线：

| 分支 | JDK | 版本模式 |
| :--- | :---: | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` |
| `feature/2.0.x` | 17 | `2.0.x.*` |
| `feature/3.0.x` | 21 | `3.0.x.*` |

## 4. 架构与模块

```text
(规划中)
Ehcache CacheManager / Cache
        |
        v
指标包装器 (按缓存的 MetricSet / gauge)
        |
        v
MetricRegistry (Dropwizard metrics-core 4.0.x)
```

单模块 jar。目前尚无 Java 包；预期包基址沿用兄弟 `metrics-*` 组件的 `com.codahale.metrics.*` 约定（**假设**）。

## 5. 安装

坐标已预留，待首个版本发布后即可安装：

```xml
<dependency>
    <groupId>io.github.easy4j</groupId>
    <artifactId>metrics-ehcache3</artifactId>
    <version>3.0.x.x.20260630-SNAPSHOT</version>
</dependency>
```

Gradle：

```groovy
implementation 'io.github.easy4j:metrics-ehcache3:3.0.x.x.20260630-SNAPSHOT'
```

快照版本由项目私服提供（见 pom 中 `distributionManagement`）。尚未发布 Maven Central 正式版。

## 6. 快速开始

暂无——本分支没有公开 API。实现后模块将提供 Ehcache 到 Metrics 的注册辅助工具。

## 7. 配置

暂无任何配置项。

## 8. 核心用法 / API

本分支无公开 API。已声明的依赖基线为 `org.ehcache:ehcache:3.8.1` + `io.dropwizard.metrics:metrics-core:4.0.2`。

## 9. 测试与构建

```bash
./mvnw clean verify
```

pom 已预置组织内统一的标准构建门禁：

- JaCoCo 覆盖率报告 + 行覆盖率检查规则，最低目标 90%（`haltOnFailure=false`）；
- package 阶段附加源码包与 Javadoc 包；
- 提供 `central` 发布 profile（GPG 签名 + Central 发布插件），仅用于正式发布。

由于当前无源码无测试，构建可直接通过。

## 10. 版本与分支

三条并行版本线，各自绑定一个 JDK 基线：

| 分支 | JDK | 版本模式 | 维护状态 |
| :--- | :---: | :--- | :--- |
| `feature/1.0.x` | 8 | `1.0.x.*` | 当前开发线 |
| `feature/2.0.x` | 17 | `2.0.x.*` | 并行维护 |
| `feature/3.0.x` | 21 | `3.0.x.*` | 并行维护 |

本分支快照版本为 `3.0.x.x.20260630-SNAPSHOT`。

## 11. 贡献与许可

欢迎通过 GitHub Issue 或 Pull Request 参与贡献。所有源码基于 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt) 许可。
