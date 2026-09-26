# 步骤 0.1：落实项目配置 / Apply the project configuration

中文：

```text
按根目录 AGENTS.md 落实步骤 0.1，直接修改以下配置：

1. 在 app/build.gradle.kts 中，将 minSdk 从 27 改为 33。
2. 在 settings.gradle.kts 的依赖仓库中添加 JitPack：https://jitpack.io。
3. 在 gradle/libs.versions.toml 中添加 Polar BLE SDK 8.3.0，以及 Coroutines Core 和 Coroutines Rx3 1.10.2 的版本与依赖定义。
4. 在 app/build.gradle.kts 中引用这三项依赖。
5. 运行 :app:assembleDebug，检查构建是否通过。

保留现有 Kotlin、Compose、Material 3、compileSdk 37 和 targetSdk 37，不推进后续功能。
```

English:

```text
Apply step 0.1 according to the root AGENTS.md by making these changes:

1. In app/build.gradle.kts, change minSdk from 27 to 33.
2. Add JitPack (https://jitpack.io) to the dependency repositories in settings.gradle.kts.
3. In gradle/libs.versions.toml, add version and dependency definitions for Polar BLE SDK 8.3.0, Coroutines Core 1.10.2, and Coroutines Rx3 1.10.2.
4. Reference these three dependencies in app/build.gradle.kts.
5. Run :app:assembleDebug and check whether the build passes.

Keep the existing Kotlin, Compose, Material 3, compileSdk 37, and targetSdk 37 configuration. Do not implement later features.
```

# 步骤 0.2：欢迎页与 Session 入口 / Step 0.2: Welcome screen and Session entry

## 中文

```text
按根目录 AGENTS.md 执行步骤 0.2，先查看现有代码，再直接修改文件：

1. 将 MainActivity.kt 的模板内容替换为欢迎页，显示 App 标题、功能简介和进入 Session 的按钮。
2. 在同一包下创建 SensorActivity.kt，使用 Compose，暂时显示 Session 标题和未连接状态。
3. 点击欢迎页按钮后打开 SensorActivity。
4. 在 AndroidManifest.xml 中注册 SensorActivity。
5. 沿用现有主题，界面文字和代码注释使用英文。
6. 运行 Debug 构建，并说明如何验证 App 启动和页面跳转。

使用最小代码，本次只做 0.2。说明中英文对照，区分文件已修改、构建已通过和真机已验证。
```

## English

```text
Follow the root AGENTS.md for step 0.2. Inspect the existing code, then modify the files directly:

1. Replace the template content in MainActivity.kt with a welcome screen showing the app title, a short introduction, and a button to enter Session.
2. Create SensorActivity.kt in the same package using Compose. For now, display only the Session title and a disconnected status.
3. Open SensorActivity when the welcome screen button is tapped.
4. Register SensorActivity in AndroidManifest.xml.
5. Use the existing theme and keep UI text and code comments in English.
6. Run a debug build and explain how to verify app launch and navigation.

Use minimal code and complete only step 0.2. Explain in Chinese and English, clearly separating file changes, build success, and device verification.
```

# 步骤 1.1：SDK、权限与可用状态 / Step 1.1: SDK, permissions, and availability

## 中文

```text
按根目录 AGENTS.md 第 5.8 节执行步骤 1.1，先查看现有代码，再直接修改文件：

1. 确认步骤 0.1 的依赖已配置，核对 Polar SDK 8.3.0 官方 API。
2. 在 AndroidManifest.xml 中声明蓝牙扫描、连接权限和可选 BLE 功能；扫描使用 neverForLocation，不申请定位权限。
3. 创建 PolarBleManager.kt，负责 SDK 初始化、蓝牙状态回调、错误处理和资源释放。
4. 在 SensorActivity.kt 添加 Enable Bluetooth 按钮，处理权限申请、拒绝后重试、打开应用设置和系统开启蓝牙。
5. 按第 5.8 节区分所有可用状态；页面恢复时刷新，避免重复初始化，Activity 销毁时释放 SDK。
6. 运行 Debug 构建，给出权限允许/拒绝、蓝牙开关和设置返回的验证步骤。

沿用现有主题，界面和代码注释使用英文。本次只做 1.1，不实现扫描或连接。
使用最小代码，说明中英文对照，区分文件修改、构建结果和真机验证。
```

## English

```text
Implement step 1.1 according to Section 5.8 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Confirm the step 0.1 dependencies are configured and verify the official Polar SDK 8.3.0 APIs.
2. Declare Bluetooth scan and connect permissions and an optional BLE feature in AndroidManifest.xml. Use neverForLocation and do not request location permissions.
3. Create PolarBleManager.kt for SDK initialization, Bluetooth state callbacks, error handling, and cleanup.
4. Add an Enable Bluetooth button to SensorActivity.kt. Handle permission requests, retries after denial, opening app settings, and the system Bluetooth enable dialog.
5. Distinguish all availability states defined in Section 5.8. Refresh on resume, prevent repeated initialization, and release the SDK when the activity is destroyed.
6. Run a debug build and provide verification steps for permission approval/denial, Bluetooth switching, and returning from settings.

Use the existing theme and English UI text and code comments. Complete only step 1.1; do not implement scanning or connection.
Use minimal code and explain in Chinese and English, separating file changes, build results, and device verification.
```

# 步骤 1.2：扫描与去重 / Step 1.2: Scanning and deduplication

已确认参数版：30 秒自动停止，停止后保留本轮结果，下次扫描前清空。
Confirmed parameters: stop automatically after 30 seconds, retain results after stopping, and clear them before the next scan.

## 中文

```text
按根目录 AGENTS.md 第 5.9 节执行步骤 1.2，先查看现有代码，再直接修改文件：

1. 复用步骤 1.1 的蓝牙可用性检查，核对 Polar SDK 8.3.0 的扫描 API、设备型号识别方式及唯一标识字段。
2. 在 PolarBleManager.kt 中实现开始、停止和 30 秒自动超时扫描，只保留真实 Polar H10。
3. 每轮开始前清空旧结果；按唯一标识去重，同一设备再次出现时更新原项。手动或自动停止后保留本轮结果，下次扫描前清空。
4. 在 SensorActivity.kt 添加 Start scan 和 Stop scan，显示扫描状态、设备名称、标识和信号强度；扫描中禁止重复开始。
5. 扫描结束无结果时显示 No Polar H10 found。扫描错误单独显示；蓝牙不可用或 Activity 销毁时停止扫描并释放相关资源。
6. 超时时间和结果保留规则已经确认，按上述规则实施，无需再次询问。
7. 运行 Debug 构建，给出真实 H10 扫描、型号筛选、去重、手动停止、超时停止及无结果的验证步骤。

沿用现有主题，界面和代码注释使用英文。本次只做 1.2，不实现连接。
使用最小代码，说明中英文对照，区分文件修改、构建结果和真机验证。
```

## English

```text
Implement step 1.2 according to Section 5.9 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Reuse the Bluetooth availability checks from step 1.1. Verify the scanning APIs, model identification method and unique identifier fields in Polar SDK 8.3.0.
2. Implement starting, stopping and a 30-second automatic scan timeout in PolarBleManager.kt. Include only real Polar H10 devices.
3. Clear previous results before each scan. Deduplicate by unique identifier and update existing entries when a device appears again. Retain results after manual or automatic stopping and clear them before the next scan.
4. Add Start scan and Stop scan to SensorActivity.kt. Show scan status, device name, identifier and signal strength. Prevent duplicate scan starts.
5. Show No Polar H10 found when scanning finishes without results. Display scan errors separately. Stop scanning and release related resources when Bluetooth becomes unavailable or the activity is destroyed.
6. The timeout and result retention rules are confirmed. Apply these rules without asking again.
7. Run a debug build and provide verification steps for real H10 discovery, model filtering, deduplication, manual stop, timeout and empty results.

Use the existing theme and English UI text and code comments. Complete only step 1.2; do not implement connection.
Use minimal code and explain in Chinese and English, separating file changes, build results and device verification.
```

# 步骤 2.1：选择设备、连接与状态显示 / Step 2.1: Device selection, connection and status

已确认生命周期版：旋转保持连接；返回欢迎页、锁屏或进入后台时取消连接/断开，返回后手动连接。
Confirmed lifecycle: retain the connection during rotation; cancel or disconnect when returning to the welcome screen, locking the screen or entering the background, and reconnect manually after returning.

## 中文

```text
按根目录 AGENTS.md 第 5.10 节执行步骤 2.1，先查看现有代码，再直接修改文件：

1. 复用步骤 1.1 的蓝牙可用性检查和步骤 1.2 的扫描结果；核对 Polar SDK 8.3.0 的连接、取消及状态回调 API。
2. 在 SensorActivity.kt 中使已发现的 H10 条目可点击；点击后停止扫描并发起连接。
3. 在 PolarBleManager.kt 中管理连接请求和回调；一次只连接一台设备，禁止重复请求，暂不实现设备切换。
4. 从发起连接请求起设置 10 秒超时；超时后取消未完成的连接，显示原因并允许重试。处理取消过程中及重试后的迟到回调，避免旧请求覆盖新状态。SDK 回调没有请求编号时，重试使用新的 SDK 实例并忽略旧实例回调。
5. 显示 Not connected、Connecting、Connected 和 Disconnecting。连接成功及已建立连接的断开完成必须由真实 SDK 回调确认。取消从未确认连接成功的请求时，释放该 SDK 后允许重试，并明确说明请求已取消，不冒称收到断开回调。Connected 不代表数据功能已就绪。
6. 分别显示蓝牙可用性和设备连接状态，避免连接成功后仍显示“没有设备连接”；单独显示失败原因。
7. 按已确认生命周期实施，无需再次询问：新增 SensorViewModel.kt 持有管理实例，旋转屏幕时保持连接，避免保留旧 Activity；返回欢迎页、锁屏或进入后台时取消连接/断开，返回后由用户手动连接。关闭 SDK 自动重连；最终清除 ViewModel 时释放 SDK。
8. 运行 Debug 构建和 lint，给出连接成功、重复点击、10 秒超时、失败后重试、关闭蓝牙、旋转，以及返回欢迎页/锁屏/后台的验证步骤。同步两份 AGENTS.md 中本步的生命周期决定和实际实施状态。

沿用现有主题，界面和代码注释使用英文。本次只做 2.1，不实现已保存设备、手动断开按钮或数据采集。
使用最小代码，说明中英文对照，区分文件修改、构建结果和真机验证；未执行的真机检查必须标明待验证。
```

## English

```text
Implement step 2.1 according to Section 5.10 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Reuse the Bluetooth availability checks from step 1.1 and scan results from step 1.2. Verify the connection, cancellation and state callback APIs in Polar SDK 8.3.0.
2. Make discovered H10 entries selectable in SensorActivity.kt. Stop scanning and initiate connection when an entry is tapped.
3. Manage connection requests and callbacks in PolarBleManager.kt. Allow one device at a time, prevent duplicate requests and do not implement device switching.
4. Apply a 10-second timeout from the connection request. Cancel unfinished connections on timeout, display the reason and allow retrying. Handle late callbacks during cancellation and after retrying without letting old requests overwrite the new state. When SDK callbacks have no request identifier, use a fresh SDK instance for retries and ignore callbacks from old instances.
5. Display Not connected, Connecting, Connected and Disconnecting. Confirm connection success and completed disconnection of an established connection through real SDK callbacks. When cancelling an attempt whose connection was never confirmed, release its SDK before allowing retry and explicitly report request cancellation without claiming a disconnection callback. Connected does not mean data features are ready.
6. Display Bluetooth availability separately from device connection status, so no device connected is not shown after connection. Display failure reasons separately.
7. Apply the confirmed lifecycle without asking again: add SensorViewModel.kt to retain the manager and connection during rotation without retaining the old Activity. Cancel or disconnect when returning to the welcome screen, locking the screen or entering the background. Reconnect manually after returning. Disable SDK automatic reconnection and release the SDK when the ViewModel is finally cleared.
8. Run a debug build and lint. Provide verification steps for successful connection, repeated taps, the 10-second timeout, retry after failure, disabling Bluetooth, rotation, and returning to the welcome screen, locking or backgrounding. Update both AGENTS.md files with this step's confirmed lifecycle rules and actual implementation status.

Use the existing theme and English UI text and code comments. Complete only step 2.1; do not implement saved devices, a manual disconnect button or data collection.
Use minimal code and explain in Chinese and English, separating file changes, build results and device verification. Mark any device checks that have not been performed as pending.
```

# 步骤 2.2 修复：断开请求报错后重试 / Step 2.2 fix: Retry after a disconnect request error

## 中文

```text
按根目录 AGENTS.md，只修复步骤 2.2 中“断开请求报错后无法重试”，直接修改文件：

1. 在 PolarBleManager.kt 中允许断开异常后手动重试，复用当前设备和 SDK，防止重复请求。
2. 在 SensorActivity.kt 添加 Retry disconnect 按钮，复用蓝牙和权限检查。
3. 保持 Disconnecting，直到真实断开回调确认 Not connected；随后清除错误和重试标记。保留设备记录及旧回调保护。
4. 运行 Debug 构建和 lint，给出异常后重试的验证方法。

使用最小代码、现有主题及英文界面和注释。不增加断开超时或自动重试，不修改其他功能。说明中英文对照，区分构建结果与真机验证。
```

## English

```text
Follow the root AGENTS.md and fix only retrying after a disconnect request error in step 2.2. Modify the files directly:

1. In PolarBleManager.kt, allow manual retry after a disconnect exception, reusing the current device and SDK and preventing duplicate requests.
2. Add a Retry disconnect button in SensorActivity.kt, reusing Bluetooth and permission checks.
3. Keep Disconnecting until a real disconnection callback confirms Not connected, then clear errors and retry flags. Preserve saved records and stale-callback protection.
4. Run a debug build and lint, and provide verification steps for retry after an exception.

Use minimal code, the existing theme, and English UI text and comments. Do not add a disconnection timeout or automatic retries, or change other features. Explain in Chinese and English, separating build results from device verification.
```

# 步骤 3.1：数据功能就绪与可用采样设置 / Step 3.1: Data readiness and available sampling settings

记录说明：以下英文为本次实施时用户提交的提示词，中文为对应翻译。
Record note: The English prompt below is the prompt submitted for this implementation; the Chinese version is its translation.

## 中文

```text
按照根目录 AGENTS.md 第 5.12 节实施步骤 3.1。先检查现有代码，再直接修改文件：

1. 核对 Polar SDK 8.3.0 官方接口。保留 FEATURE_HR，增加 FEATURE_POLAR_ONLINE_STREAMING，复用现有连接流程和旧回调防护。
2. 在 PolarBleManager.kt 中处理真实功能就绪回调，分别跟踪 HR、ACC 和 ECG。区分未连接、等待就绪、检查中、已就绪、不支持和检查失败。不能将尚未确定的功能判为不支持。
3. 在线采集功能就绪后，查询支持的数据类型和 ACC、ECG 当前可用设置。HR 不提供采样率设置。区分功能就绪、配置完整和正在采集数据。
4. ACC 使用 100 Hz；不支持时禁用 ACC 并解释原因，不自动选择其他采样率。ECG 及其他必需参数仅在只有一个可用选项时自动采用。存在多个选项时展示实际值并等待我确认，不自动选择最大值。待确认配置不能阻止其他已就绪项。
5. 在 SensorActivity.kt 展示各项状态、带单位的可用设置、选定值和单独的错误信息。增加重新检查入口。防止重复查询，正常处理取消，不增加自动重试循环或新的就绪等待超时。
6. 断开、蓝牙不可用或释放 SDK 时，取消查询并清空就绪状态和设置；重连后重新检查。旋转时保留状态，避免重组触发查询，并拒绝旧结果。
7. 运行 debug 构建和 lint。提供就绪检查、设置展示、重复点击、失败重试、断开重连、旋转和迟到结果的验证步骤。未执行的检查标为待验证。
8. 同步更新两份 AGENTS.md，记录实际变动、构建结果以及仍待确认的参数。

使用最小代码、现有 Compose 主题、英文界面文字和代码注释。
只完成步骤 3.1，不实施步骤 3.2、数据采集、会话控制或图表。
中英文说明，分别列出文件变动、构建结果和真机验证。
```

## English

```text
Implement step 3.1 according to Section 5.12 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Verify the official Polar SDK 8.3.0 APIs. Keep FEATURE_HR and add FEATURE_POLAR_ONLINE_STREAMING, reusing the existing connection flow and stale-callback protection.
2. Handle real feature-readiness callbacks in PolarBleManager.kt and track HR, ACC and ECG separately. Distinguish disconnected, waiting, checking, ready, unsupported and failed states. Do not classify unresolved features as unsupported.
3. Once online streaming is ready, query supported data types and the currently available ACC and ECG settings. Do not offer a sampling-rate setting for HR. Distinguish feature readiness, complete configuration and active data collection.
4. Use 100 Hz for ACC. If unavailable, disable ACC and explain why without selecting another rate. For ECG and other required parameters, automatically accept only a single available option. Display multiple options and wait for my confirmation; do not automatically select maximum values. Pending configuration must not block other ready items.
5. In SensorActivity.kt, display individual states, available settings with units, selected values and separate errors. Add a recheck action. Prevent duplicate queries, treat cancellation normally, and add neither automatic retry loops nor a new readiness timeout.
6. Cancel queries and clear readiness and settings on disconnection, Bluetooth unavailability or SDK release. Recheck after reconnection. Preserve state across rotation, avoid queries triggered by recomposition, and reject stale results.
7. Run a debug build and lint. Provide verification steps for readiness, settings, repeated taps, retry after failure, disconnection/reconnection, rotation and late results. Mark unperformed checks as pending.
8. Update both AGENTS.md files with actual changes, build results and parameters still awaiting confirmation.

Use minimal code, the existing Compose theme, and English UI text and code comments.
Complete only step 3.1; do not implement step 3.2, data collection, session controls or charts.
Explain in Chinese and English, separating file changes, build results and device verification.
```

# 步骤 3.2：数据订阅、错误与资源释放 / Step 3.2: Subscription management, errors and cleanup

## 中文

```text
按照根目录 AGENTS.md 第 5.13 节实施步骤 3.2。先检查现有代码，再直接修改文件：

1. 核对 Polar SDK 8.3.0 的数据流与取消行为，复用 3.1 的就绪状态和配置检查，以及现有连接和旧回调防护。
2. 在 PolarBleManager.kt 中增加最小的内部订阅管理。HR、ACC、ECG 分别管理任务、状态和错误；每种类型最多一个订阅，启动中、运行中和停止中禁止重复启动。
3. 区分未启动、启动中、接收中、停止中、已停止和失败。只有当前有效订阅收到数据后才能显示接收中；连接未建立、功能未就绪或配置不完整时拒绝启动。
4. 提供内部启动、停止和全部清理方法。取消及清理完成前不得替换订阅；正常取消不报错，重复清理安全；旧任务的数据、错误和结束处理不得影响新任务。
5. 单路失败时其他流继续，失败流清理后允许手动重试，不自动重试或改变成功的蓝牙连接状态。重试前重新检查前置条件。
6. 主动断开、意外断线、蓝牙不可用、权限丢失、离开前台和释放 SDK 时清理全部订阅。旋转保持现有订阅；返回并手动重连后不自动恢复采集。
7. 用仅存在于测试中的受控数据流验证启动条件、重复启动、停止后重启、正常取消、单路失败隔离、全部清理和旧事件防护。运行相关测试、debug 构建和 lint，标明真机验证待完成。
8. 同步更新两份 AGENTS.md，记录实际实现、测试与构建结果及未验证项。

使用最小代码，避免通用框架或无关重构。保持现有 Compose 主题，界面和注释使用英文。
只完成 3.2，不接入真实 HR、ACC、ECG 数据采集，不增加正式会话按钮、算法、图表或存储。测试数据不得进入正式界面或冒充 H10 数据。
中英文说明，分别列出文件变动、测试与构建结果、真机验证。
```

## English

```text
Implement step 3.2 according to Section 5.13 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Verify Polar SDK 8.3.0 streaming and cancellation behavior. Reuse step 3.1 readiness and configuration checks, the existing connection flow and stale-callback protection.
2. Add minimal internal subscription management in PolarBleManager.kt. Track tasks, states and errors separately for HR, ACC and ECG. Allow at most one subscription per type and reject duplicate starts while starting, running or stopping.
3. Distinguish idle, starting, receiving, stopping, stopped and failed states. Mark receiving only after data arrives from the current valid subscription. Reject starts without a connection, feature readiness or complete configuration.
4. Provide internal start, stop and cleanup-all methods. Wait for cancellation and cleanup before replacing a subscription. Treat cancellation normally and make repeated cleanup safe. Old data, errors and completion handlers must not affect newer tasks.
5. Keep other streams running when one fails. Allow manual retry after the failed task is cleaned up, rechecking prerequisites first. Do not retry automatically or turn a stream failure into a Bluetooth connection failure.
6. Clean up all subscriptions on intentional or unexpected disconnection, Bluetooth unavailability, permission loss, leaving the foreground or SDK release. Preserve subscriptions across rotation. Do not automatically resume collection after manual reconnection.
7. Use controlled flows confined to tests to verify prerequisites, duplicate starts, restart after stopping, normal cancellation, failure isolation, cleanup and stale-event protection. Run relevant tests, a debug build and lint. Mark device verification as pending.
8. Update both AGENTS.md files with actual implementation, test and build results, and unverified items.

Use minimal code without a generic framework or unrelated refactoring. Preserve the existing Compose theme and use English UI text and comments.
Complete only step 3.2. Do not connect real HR, ACC or ECG streams or add session controls, algorithms, charts or storage. Test data must not enter the production UI or be presented as H10 data.
Explain in Chinese and English, separating file changes, test/build results and device verification.
```
