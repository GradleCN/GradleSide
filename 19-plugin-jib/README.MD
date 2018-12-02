[![experimental](http://badges.github.io/stability-badges/dist/experimental.svg)](http://github.com/badges/stability-badges)
[![Gradle Plugin Portal](https://img.shields.io/badge/gradle%20plugin-v0.9.2-blue.svg)](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib)
[![Gitter version](https://img.shields.io/gitter/room/gitterHQ/gitter.svg)](https://gitter.im/google/jib)

# Jib - 容器化你的Gradle Java项目,jib快速入门

- 原文： https://github.com/GoogleContainerTools/jib/tree/master/jib-gradle-plugin#quickstart
- 翻译： flyingghost
- 校对： PKAQ

Jib 是一个用来为你的Java应用构建Docker和OCI镜像的 [Gradle](https://gradle.org/) 插件。

Jib的项目信息，可以戳 [Jib project README](../README.md).
jib的Maven插件，可以戳 [jib-maven-plugin project](../jib-maven-plugin).

## 即将实现的功能

以下功能目前尚未支持，但在未来的版本中会加入进来。

- Support for WAR 格式的支持
- 运行和调试构建好的容器

## 快速入门

### Setup

*首先确保你使用的Gradle版本为4.6以上。*

在你的 Gradle Java 项目里，添加jib插件到 `build.gradle`:

```groovy
plugins {
  id 'com.google.cloud.tools.jib' version '0.9.2'
}
```

*戳 [Gradle Plugin Portal](https://plugins.gradle.org/plugin/com.google.cloud.tools.jib) 可以了解更多细节。*

然后你就可以用一条命令容器化你的应用：

```shell
gradle jib --image=<MY IMAGE>
```

这条命令可以把你的应用构建成镜像并且推送到容器镜像服务。 *如果你遇到鉴权相关的问题，可以看看 [Authentication Methods](#authentication-methods).*

使用创建一个Docker守护进程：

```shell
gradle jibDockerBuild
```

如果你想把Jib作为你Gradle构建的一部分，请往下看。

## 配置

配置镜像推送：

#### 使用 [Google Container Registry (GCR)](https://cloud.google.com/container-registry/)...

*确保已经安装了 [`docker-credential-gcr`命令行工具](https://cloud.google.com/container-registry/docs/advanced-authentication#docker_credential_helper). Jib 将自动调用 `docker-credential-gcr` 命令来获取凭证。 戳 [Authentication Methods](#authentication-methods) 可以看到其他认证方式。*

举个栗子，想构建 `gcr.io/my-gcp-project/my-app` 的镜像，配置文件这么写：

```groovy
jib.to.image = 'gcr.io/my-gcp-project/my-app'
```

#### 使用 [Amazon Elastic Container Registry (ECR)](https://aws.amazon.com/ecr/)...

*确保已经安装了 [`docker-credential-ecr-login`命令行工具](https://github.com/awslabs/amazon-ecr-credential-helper). Jib 将自动调用 `docker-credential-ecr-login` 命令来获取凭证。 戳 [Authentication Methods](#authentication-methods) 可以看到其他认证方式。*

举个栗子， 想构建 `aws_account_id.dkr.ecr.region.amazonaws.com/my-app` 的镜像，配置文件这么写：

```groovy
jib.to.image = 'aws_account_id.dkr.ecr.region.amazonaws.com/my-app'
```

#### 使用 [Docker Hub Registry](https://hub.docker.com/)...

*确保已经安装了 [docker-credential-helper](https://github.com/docker/docker-credential-helpers#available-programs) 。 例如，在MacOS系统，凭据助手(credential helper)命令是 `docker-credential-osxkeychain`. 戳 [Authentication Methods](#authentication-methods) 可以看到其他认证方式。*

举个栗子， 想构建 `my-docker-id/my-app` 镜像，配置文件这么写：

```groovy
jib.to.image = 'my-docker-id/my-app'
```

##### TODO: 为常见的镜像服务添加更多范例。

### 构建你的镜像

使用如下命令构建你的容器镜像：

```shell
gradle jib
```

在第一次构建之后，后续的构建过程会快很多。

如果你遇到了任何困难，欢迎提交issue或者在[Gitter](https://gitter.im/google/jib)， [Jib users forum](https://groups.google.com/forum/#!forum/jib-users)上来寻求帮助。

#### 构建为Docker守护进程

Jib 还可以直接把你的镜像构建成为Docker守护进程。 这会调用 `docker` 命令，所以需要你的 `docker` 命已经在 `PATH` 环境变量里设置好了。

```shell
gradle jibDockerBuild
```

如果你正在使用 [`minikube`](https://github.com/kubernetes/minikube) 的远程Docker镜像，确保你已经 [正确设置了环境变量](https://github.com/kubernetes/minikube/blob/master/docs/reusing_the_docker_daemon.md) 指向远程守护进程。：

```shell
eval $(minikube docker-env)
gradle jibDockerBuild
```

### 对每一个构建都运行 `jib`

你也可以通过设置`build`任务的依赖关系，使得每一次构建都会自动运行 `jib` 。

```groovy
tasks.build.dependsOn tasks.jib
```

然后， ```gradle build``` 命令将会构建并且容器化你的应用。

### 导出dockerfile

Jib 可以导出一份dockerfile，需要的时候你可以用Docker来完成构建。

```shell
gradle jibExportDockerContext
```

默认dockerfile将会创建并保存在 `build/jib-docker-context` 。你可以通过 `targetDir` 配置项或者命令行的  `---jib.dockerDir` 参数来修改这个保存路径：

```shell
gradle jibExportDockerContext --jib.dockerDir=my/docker/context/
```

然后可以使用Docker来构建你的镜像。

```shell
docker build -t myimage my/docker/context/
```

## 扩展用法

插件为配置文件增加了 `jib` 扩展项，它拥有以下配置参数，可用来定制化镜像构建过程：

| 字段                      | 类型                              | 默认值                                 | 描述                                                         |
| ------------------------- | --------------------------------- | -------------------------------------- | ------------------------------------------------------------ |
| `from`                    | [`from`](#from-closure)           | 参考 [`from`](#from-closure)           | 设置将在什么基础镜像之上构建应用                             |
| `to`                      | [`to`](#to-closure)               | *Required*                             | 设置目标镜像                                                 |
| `container`               | [`container`](#container-closure) | 参考 [`container`](#container-closure) | 设置将要运行镜像的容器                                       |
| `useProjectOnlyCache`     | `boolean`                         | `false`                                | 如果设为true，Jib将不会在不同的Maven项目之间共享缓存         |
| `allowInsecureRegistries` | boolean                           | `false`                                | 如果设为true，Jib在镜像仓储不支持HTTPS的时候使用HTTP作为备用方案。强烈建议把这个参数设为false，否则，和没开HTTPS的镜像仓储之间的通讯流量不会被加密，容易被偷窥。 |

<a name="from-closure"></a>`from` 是一个包含以下属性的闭包：

| 属性         | 类型                    | 默认值                   | 描述                                                         |
| ------------ | ----------------------- | ------------------------ | ------------------------------------------------------------ |
| `image`      | `String`                | `gcr.io/distroless/java` | 指向基础镜像                                                 |
| `credHelper` | `String`                | *None*                   | 拉取基础镜像的时候，用于完成鉴权环节的凭据助手(credential helper)命令行工具的**后缀** ( `docker-credential-`后面部分). |
| `auth`       | [`auth`](#auth-closure) | *None*                   | 直接指定鉴权信息 (替代 `credHelper` 方式)。                  |

<a name="to-closure"></a>`to` 是一个包含以下属性的闭包：

| 属性         | 类型                    | 默认值     | 描述                                                         |
| ------------ | ----------------------- | ---------- | ------------------------------------------------------------ |
| `image`      | `String`                | *Required* | 指向目标镜像。也可以通过命令行的 `--image` 参数来指定。      |
| `credHelper` | `String`                | *None*     | 拉取基础镜像的时候，用于完成鉴权环节的凭据助手(credential helper)命令行工具的**后缀** ( `docker-credential-`后面部分)。<br />注：怀疑是原文档写错了，猜测应该是构建好的镜像要推(push)到的地方才对。原文如下：<br />Suffix for the credential helper that can authenticate pulling the base image (following `docker-credential-`). |
|              |                         |            |                                                              |
| `auth`       | [`auth`](#auth-closure) | *None*     | 直接指定鉴权信息 (替代 `credHelper` 方式)。                  |

<a name="auth-closure"></a>`auth` 是一个包含以下属性的闭包 (参考 [Using Specific Credentials](#using-specific-credentials))：

| 属性       | 类型     |
| ---------- | -------- |
| `username` | `String` |
| `password` | `String` |

<a name="container-closure"></a>`container` 是一个包含以下属性的闭包：

| 属性        | 类型           | 默认值       | 描述                                                         |
| ----------- | -------------- | ------------ | ------------------------------------------------------------ |
| `jvmFlags`  | `List<String>` | *None*       | 运行应用时需要传递给JVM的额外附加参数                        |
| `mainClass` | `String`       | *Inferred\** | 启动应用的入口main class                                     |
| `args`      | `List<String>` | *None*       | 运行应用时传入默认main方法的参数                             |
| `ports`     | `List<String>` | *None*       | 容器在运行时对外暴露的端口号（等价于Docker的[EXPOSE](https://docs.docker.com/engine/reference/builder/#expose)指令）。 |
| `format`    | `String`       | `Docker`     | 使用 `OCI` 构建一个 [OCI 容器镜像](https://www.opencontainers.org/). |

*\* 使用在 `jar` 任务中定义的main class，或者尝试搜索到一个可用的main class。*

### 范例

在以下示例配置中，镜像文件将：

- 基于基础镜像 `openjdk:alpine` 构建(从 Docker Hub拉取)
- 推到 `localhost:5000/my-image:built-with-jib`
- 使用命令行 `java -Xms512m -Xdebug -Xmy:flag=jib-rules -cp app/libs/*:app/resources:app/classes mypackage.MyApp some args` 运行应用
- 暴露TCP端口 1000 （默认），以及UDP端口 2000, 2001, 2002, 2003 
- 构建为 OCI 标准格式

```groovy
jib {
  from {
    image = 'openjdk:alpine'
  }
  to {
    image = 'localhost:5000/my-image/built-with-jib'
    credHelper = 'osxkeychain'
  }
  container {
    jvmFlags = ['-Xms512m', '-Xdebug', '-Xmy:flag=jib-rules']
    mainClass = 'mypackage.MyApp'
    args = ['some', 'args']
    ports = ['1000', '2000-2003/udp']
    format = 'OCI'
  }
}
```

### 鉴权方法

从私有镜像仓储推/拉镜像需要认证授权。 这项工作可以使用 [Docker 凭据助手(credential helpers) 来协助完成](#using-docker-credential-helpers)<!-- or in the `jib` extension-->。如果你没有明确指定凭据， Jib 会尝试 [使用在Docker配置中定义的凭据](/../../issues/101) ，或者尝试在常见的凭据助手(credential helpers)中瞎猜一个。

#### 使用 Docker 凭据助手(Credential Helpers)

Docker 凭据助手(Credential Helpers) 是一组针对各种镜像仓储用来处理鉴权问题的命令行工具。

常见凭据助手包括：

- Google Container Registry: [`docker-credential-gcr`](https://cloud.google.com/container-registry/docs/advanced-authentication#docker_credential_helper)
- AWS Elastic Container Registry: [`docker-credential-ecr-login`](https://github.com/awslabs/amazon-ecr-credential-helper)
- Docker Hub Registry: [`docker-credential-*`](https://github.com/docker/docker-credential-helpers)
  <!--* Azure Container Registry: [`docker-credential-acr-*`](https://github.com/Azure/acr-docker-credential-helper)
  -->

在 `jib` 扩展中，可以通过 `credHelper` 属性指定镜像仓储要使用的凭据助手。

*示例配置：* 

```groovy
jib {
  from {
    image = 'aws_account_id.dkr.ecr.region.amazonaws.com/my-base-image'
    credHelper = 'ecr-login'
  }
  to {
    image = 'gcr.io/my-gcp-project/my-app'
    credHelper = 'gcr'
  }
}
```

#### 使用明确的鉴权信息

你也可以在 `from` 或者 `to` 里直接指定镜像使用的凭据。

```groovy
jib {
  from {
    image = 'aws_account_id.dkr.ecr.region.amazonaws.com/my-base-image'
    auth {
      username = USERNAME // Defined in 'gradle.properties'.
      password = PASSWORD
    }
  }
  to {
    image = 'gcr.io/my-gcp-project/my-app'
    auth {
      username = 'oauth2accesstoken'
      password = 'gcloud auth print-access-token'.execute().text.trim()
    }
  }
}
```

这些凭据信息可以存储在 `gradle.properties` 配置文件中，从命令行中接收 (例如 `gcloud auth print-access-token`)，或者从文件中读取。

举个栗子，你可以读取一个key文件来完成认证。 (例如 GCR ，参考 [使用 JSON 密钥文件](https://cloud.google.com/container-registry/docs/advanced-authentication#using_a_json_key_file)):

```groovy
jib {
  to {
    image = 'gcr.io/my-gcp-project/my-app'
    auth {
      username = '_json_key'
      password = file('keyfile.json').text
    }
  }
}
```

## Jib 是如何工作的

戳 [Jib project README](/../../#how-jib-works).

## 常见问题 (FAQ)

戳 [Jib project README](/../../#frequently-asked-questions-faq).

## 社区

戳 [Jib project README](/../../#community).