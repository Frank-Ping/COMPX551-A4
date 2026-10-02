> 当前执行顺序修订（2026-10-03）：8.4d 已完成部分功能与界面清理，参考图视觉还原未完成。剩余工作独立为 **8.4e（待实施）**，执行方案见 AGENTS.md 5.34，完整中英文 Prompt 见本文件末尾“步骤 8.4e”。本轮仅更新文档；既有功能 Prompt 和测试结果为历史记录，不代表 8.4e 已完成。8.4e 验收后再处理用户指定的 8.5。
> Current execution revision: Step 8.4d has implemented functionality and partial UI cleanup, while visual fidelity remains incomplete. Remaining work is **8.4e (pending)**; see AGENTS.md 5.34 and the bilingual 8.4e prompts at the end of this file. This turn updates documentation only. Earlier functional prompts/test results do not complete 8.4e. Address 8.5 only when requested after 8.4e acceptance.

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

# 步骤 2.3：读取并显示设备电量 / Step 2.3: Read and display device battery level

## 中文

```text
按照根目录 AGENTS.md 第 5.14 节实施步骤 2.3。先检查现有代码，再直接修改文件：

1. 核对 Polar SDK 8.3.0 的电量接口，保留现有 SDK 功能并增加 FEATURE_BATTERY_INFO。
2. 在 PolarBleManager.kt 接收 batteryLevelReceived 回调，仅接受当前有效 SDK 实例和已连接设备的 0—100 电量值，忽略旧回调和无效值。
3. 在 SensorActivity.kt 的连接信息附近显示实际电量百分比。未收到有效值或断开后显示 Battery: --；0% 是有效值。
4. 开始新连接、请求断开、连接丢失、蓝牙或权限不可用、离开前台或释放 SDK 时清空电量。旋转保持状态，重连后等待新值。
5. 电量可用性与连接、数据就绪状态分开处理。不增加轮询、权限、依赖、持久化、低电量报警或数据采集。
6. 使用受控测试验证边界值、无效值、状态清理和旧回调。运行相关测试、debug 构建及 lint，提供真机验证步骤，未执行的检查标记为待完成。
7. 更新两份 AGENTS.md，记录实际改动及验证结果。

使用最小代码、现有 Compose 主题和英文界面文字、注释，只完成步骤 2.3。
中英文对照说明，分别列出文件修改、测试/构建结果和真机验证。
```

## English

```text
Implement step 2.3 according to Section 5.14 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Verify the Polar SDK 8.3.0 battery APIs. Keep existing SDK features and add FEATURE_BATTERY_INFO.
2. Receive batteryLevelReceived callbacks in PolarBleManager.kt. Accept only values from 0 to 100 for the current valid SDK instance and connected device. Ignore stale callbacks and invalid values.
3. Show the actual percentage near the connection information in SensorActivity.kt. Display Battery: -- before receiving a valid value or after disconnection. Treat 0% as valid.
4. Clear battery state when starting a new connection, requesting disconnection, losing the connection, Bluetooth or permissions, leaving the foreground, or releasing the SDK. Preserve state across rotation and wait for a new value after reconnection.
5. Keep battery availability separate from connection and data-readiness states. Do not add polling, permissions, dependencies, persistence, low-battery alerts or data collection.
6. Add controlled tests for boundary values, invalid values, state clearing and stale callbacks. Run relevant tests, a debug build and lint. Provide device verification steps and mark unperformed checks as pending.
7. Update both AGENTS.md files with actual changes and verification results.

Use minimal code, the existing Compose theme, and English UI text and comments. Complete only step 2.3.
Explain in Chinese and English, separating file changes, test/build results and device verification.
```

# 步骤 4.1：接收真实心率 / Step 4.1: Receive real HR data

## 中文

```text
按照根目录 AGENTS.md 第 5.15 节实施步骤 4.1。先检查现有代码，再直接修改文件：

1. 核对 Polar SDK 8.3.0 的心率采集与取消接口，复用现有连接、就绪检查、订阅管理和旧事件防护。
2. 将 startHrStreaming 接入现有 HR 订阅，读取 sample.hr，单位为 bpm。只保留最新心率及手机接收时间 receivedAt，不增加历史缓存。
3. 每批数据记录一次接收时间，按顺序处理样本，最终保留最后一个样本及该批时间；心率数值相同时也更新时间。不虚构传感器时间戳、HR 采样率设置，不根据 RR 间隔推算时间戳。
4. 在 SensorActivity.kt 添加临时 Start HR 和 Stop HR 按钮，显示订阅状态、最新心率、最后接收时间和独立错误。实际收到心率样本后才能显示接收中；防止重复启动，清理完成后允许手动重试。
5. 等待新数据时保留最后值并显示其接收时间，不增加无数据超时。新的有效启动前，以及停止、正常结束、失败或断线时清空心率和时间，显示占位符；拒绝重复启动不能清空正在接收的数据。替换固定的“数据采集尚未开始”提示。
6. 复用蓝牙或权限丢失、离开前台、释放 SDK 时的清理；旋转保持订阅和数值。拒绝旧事件，重连后不自动启动；单独停止 HR 不断开蓝牙连接。
7. 使用受控测试验证批次接收时间、相同心率更新、重复启动、清空、失败、重启及旧事件防护；适用时复用现有订阅测试。运行相关测试、debug 构建和 lint。
8. 更新两份 AGENTS.md，记录实际改动及验证结果。提供真实心率接收、重复点击、停止/重启、旋转、断线及后台返回的真机检查步骤；未执行的项目标记为待完成。

使用最小代码、现有 Compose 主题和英文界面文字、注释。
只完成 4.1，不实现 ACC/ECG 采集、正式会话控制或计时、Pause/Resume、心率过滤或统计、图表、RR 处理或持久化。
中英文说明，分别列出文件修改、测试/构建结果和真机验证。
```

## English

```text
Implement step 4.1 according to Section 5.15 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Verify the Polar SDK 8.3.0 HR streaming and cancellation APIs. Reuse the existing connection, readiness checks, subscription management and stale-event protection.
2. Connect startHrStreaming to the existing HR subscription. Read sample.hr in bpm. Retain only the latest HR value and phone reception timestamp, receivedAt; do not add a history buffer.
3. Record one reception timestamp per received batch. Process samples in order and retain the last sample with that batch's timestamp. Update the timestamp even when the HR value is unchanged. Do not invent sensor timestamps, HR sampling-rate settings or timestamps derived from RR intervals.
4. Add temporary Start HR and Stop HR buttons in SensorActivity.kt. Show the subscription state, latest HR, last reception time and separate errors. Mark receiving only after an actual HR sample arrives. Prevent duplicate starts and allow manual retry after cleanup.
5. While waiting for new data, retain the last value and show its reception time without adding a no-data timeout. Clear both fields before a new accepted start and on stop, normal completion, failure or disconnection; display placeholders. Rejecting a duplicate start must not clear an active reading. Replace the fixed text claiming that data collection has not started.
6. Reuse cleanup for Bluetooth or permission loss, leaving the foreground and SDK release. Preserve the subscription and reading across rotation. Reject stale events and do not restart automatically after reconnection. Stopping HR alone must keep the Bluetooth connection.
7. Add focused controlled tests for batch timestamps, repeated HR values, duplicate starts, clearing, failure, restart and stale events. Reuse existing subscription tests where applicable. Run relevant tests, a debug build and lint.
8. Update both AGENTS.md files with actual changes and verification results. Provide device checks for real HR reception, repeated taps, stop/restart, rotation, disconnection and returning from the background. Mark unperformed checks as pending.

Use minimal code, the existing Compose theme, and English UI text and comments.
Complete only step 4.1. Do not implement ACC/ECG streaming, formal session controls or timing, Pause/Resume, HR filtering or statistics, charts, RR processing or persistence.
Explain in Chinese and English, separating file changes, test/build results and device verification.
```

## 实现说明 / Implementation notes

以下为实施方案，不代表代码已修改或验证已通过。
The following describes the implementation plan, not completed code or verification.

| 文件或环节 / File or area | 最小实现 / Minimal implementation |
|---|---|
| PolarBleManager.kt | 增加 HR 启停入口，复用 startDataSubscription 和 stopDataSubscription；维护可空的最新心率及 receivedAt，不另建订阅框架。 / Add HR start/stop methods using the existing subscription methods; retain a nullable latest HR reading and receivedAt without another subscription framework. |
| 接收与清空 / Reception and clearing | 每批记录一次接收时间，保留最后样本；相同心率仍更新时间。停止、结束、失败或断线时清空，旧数据不得恢复已清空值。 / Timestamp each batch once and retain its last sample; update the time for repeated values. Clear on stop, completion, failure or disconnection and reject stale updates. |
| SensorActivity.kt | 添加临时 Start HR、Stop HR 及数值、时间、状态和错误显示；修正“尚未采集”提示。正式统一控制留在 4.4。 / Add temporary HR controls and value, time, state and error displays; replace the outdated readiness-only message. Formal session controls remain in step 4.4. |
| 生命周期 / Lifecycle | 沿用 ViewModel；旋转保持，离开前台或连接不可用时停止，返回后手动启动。 / Reuse the ViewModel; preserve collection during rotation, stop on foreground exit or connection unavailability, and restart manually. |
| 验证 / Verification | 受控测试验证数据与状态逻辑，真机检查真实 H10 持续上报；构建通过不替代真机验收。 / Use controlled tests for data and state logic and a real H10 for continuous reception checks; a successful build does not replace device verification. |

# 步骤 4.2：接收真实加速度 / Step 4.2: Receive real ACC data

2026-09-29 补录：英文保留本次对话中实际提交的实施提示词，中文为对应翻译；这是历史记录，不是再次执行请求。
Recorded on 2026-09-29: the English text preserves the implementation prompt submitted in this conversation, with a Chinese translation. This is a historical record, not a request to execute it again.

## 中文

```text
按照根目录 AGENTS.md 第 5.16 节实施步骤 4.2。先检查现有代码，再直接修改文件：

1. 核对 Polar SDK 8.3.0 的 ACC 设置、采集、时间戳、单位及取消行为。复用现有连接、就绪检查、订阅管理和旧事件防护。
2. 将 ACC 配置策略更新为选择 100 Hz 和 ±4 g（RANGE = 4）。每次启动或重试前重新查询当前设置；任一目标值不可用时，明确说明原因并阻止 ACC 启动，不选择替代值。其他必要参数只有一个可用值时直接采用，多选项未确定时请用户确认。
3. 将 startAccStreaming 接入现有 ACC 订阅。按顺序处理每个样本，保留传感器 timeStamp（纳秒）及原始 x、y、z（mG，包含重力）；实际收到样本后才显示接收中。
4. 比较连续样本的时间戳，包括跨批次比较。差值严格大于 30 ms 时建立新的连续数据段，保留段边界供后续处理。不插值、不虚构样本，不因缺口断开蓝牙或增加无数据超时。
5. 使用有界内存缓存，保留 (t - 10 秒, t] 内的样本，t 为最新样本时间戳，同时最多保留 1,000 个样本。超过任一限制时移除最旧样本；收到数据立即处理，不等待缓存填满。
6. 在 SensorActivity.kt 添加临时 Start ACC 和 Stop ACC 按钮。显示 ACC 状态、选定设置、最新 x/y/z 及单位、样本时间戳、缓存数量、缺口提示和独立错误。防止重复设置查询和订阅，包括启动或停止期间。
7. 停止、正常结束、失败或断线后保留最后缓存，并标为非活动状态。新的启动或重试被接受后，清空缓存及前一样本/分段状态；拒绝重复启动不能清空正在接收的数据。复用生命周期清理，旋转保持采集，重连后不自动恢复。单独停止或失败 ACC 不停止 HR，也不断开蓝牙。
8. 复用取消与清理，完成后才允许重新启动。核对 SDK 的异步设备停止行为，不将本地 Job 完成视为硬件停止已确认，不增加任意重启延迟。使用受控测试验证配置选择、批次处理、30 ms 边界、跨批次缺口、缓存限制、停止/重启和旧事件；复用现有订阅测试。
9. 运行相关测试、debug 构建和 lint。更新两份 AGENTS.md，记录实际改动及结果。提供真实 ACC 接收、设置、三轴数值、削顶、重复点击、快速停止/重启、HR 与 ACC 同时运行、旋转和断线的真机检查步骤，未执行项目标记为待完成。

使用最小代码、现有 Compose 主题，以及英文 UI 文本和代码注释。不引入通用缓存框架或无关重构。
仅完成步骤 4.2，保留 4.1 的 HR 行为。不实现 ECG 采集、正式会话控制或计时、Pause/Resume、算法单位转换、平滑、步伐检测、统计、图表或持久化。
中英文说明，分别列出文件修改、测试/构建结果和真机验证。
```

## English

```text
Implement step 4.2 according to Section 5.16 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Verify the Polar SDK 8.3.0 ACC settings, streaming, timestamps, units and cancellation behavior. Reuse the existing connection, readiness checks, subscription management and stale-event protection.
2. Update the ACC configuration policy to select 100 Hz and ±4 g (RANGE = 4). Requery current settings before each start or retry. Block ACC with a clear reason if either value is unavailable; do not select alternatives. For other required parameters, accept a single available value and request confirmation for unresolved multiple options.
3. Connect startAccStreaming to the existing ACC subscription. Process every sample in order, retaining its sensor timeStamp in nanoseconds and raw x, y and z in mG, including gravity. Mark receiving only after an actual sample arrives.
4. Compare consecutive sample timestamps, including across batches. A difference strictly greater than 30 ms starts a new continuous segment. Preserve the segment boundary for later processing. Do not interpolate, fabricate samples, disconnect Bluetooth or add a no-data timeout because of a gap.
5. Keep a bounded in-memory buffer containing samples in (t - 10 seconds, t], where t is the latest sample timestamp, with a maximum of 1,000 samples. Remove the oldest samples when either limit is exceeded. Process arrivals immediately without waiting for the buffer to fill.
6. Add temporary Start ACC and Stop ACC buttons in SensorActivity.kt. Show the ACC state, selected settings, latest x/y/z with units, sample timestamp, buffer count and gap indication, with separate errors. Prevent duplicate settings queries and subscriptions, including while starting or stopping.
7. Retain the final buffer on stop, completion, failure or disconnection and label it as inactive. Clear the buffer and previous-sample/segment state before a new accepted start or retry. Rejecting a duplicate start must not clear active data. Reuse lifecycle cleanup, preserve collection across rotation and do not resume automatically after reconnection. Stopping or failing ACC alone must not stop HR or disconnect Bluetooth.
8. Reuse cancellation and cleanup before allowing restart. Verify the SDK's asynchronous device-stop behavior; do not treat local Job completion as confirmed hardware shutdown or add an arbitrary restart delay. Add controlled tests for configuration selection, batch processing, the 30 ms boundary, cross-batch gaps, buffer limits, stop/restart and stale events. Reuse existing subscription tests.
9. Run relevant tests, a debug build and lint. Update both AGENTS.md files with actual changes and results. Provide device checks for real ACC reception, settings, axis values, clipping, repeated taps, rapid stop/restart, simultaneous HR and ACC, rotation and disconnection. Mark unperformed checks as pending.

Use minimal code, the existing Compose theme, and English UI text and comments. Do not introduce a generic buffering framework or unrelated refactoring.
Complete only step 4.2. Preserve step 4.1 HR behavior. Do not implement ECG streaming, formal session controls or timing, Pause/Resume, unit conversion for algorithms, smoothing, step detection, statistics, charts or persistence.
Explain in Chinese and English, separating file changes, test/build results and device verification.
```

## 实施与验证记录 / Implementation and verification record

- 已修改 PolarBleManager.kt、SensorActivity.kt、DataReadiness.kt、DataReadinessTest.kt；新增 AccBuffer.kt、AccBufferTest.kt。实现配置复核、原始样本处理、跨批次缺口标记及有界缓存，详见两份 AGENTS.md 第 5.16 节。 / Modified PolarBleManager.kt, SensorActivity.kt, DataReadiness.kt and DataReadinessTest.kt; added AccBuffer.kt and AccBufferTest.kt. Implemented configuration checks, raw sample processing, cross-batch gap markers and bounded buffering; see Section 5.16 of both AGENTS.md files.
- 4.2 实施时验证：41 项测试通过，debug 构建成功，lint 0 errors、18 warnings。本次仅补录文档，没有重新运行测试或构建。 / At step 4.2, 41 tests passed, the debug build succeeded, and lint reported 0 errors and 18 warnings. No tests or builds were rerun for this documentation update.
- 用户已反馈解除其他程序占用后 ACC 启动检查通过；削顶、快速启停、多流并行及完整生命周期等验收仍待完成。4.2 的独立临时按钮已在 4.4 被统一会话控制替代。 / The user reported that ACC startup passed after another application's access was removed; clipping, rapid restart, simultaneous streams and full lifecycle checks remain pending. Step 4.4 replaced the separate temporary controls with unified session controls.

# 步骤 4.3：接收真实 ECG / Step 4.3: Receive real ECG data

## 中文

```text
按照根目录 AGENTS.md 第 5.17 节实施步骤 4.3。先检查现有代码，再直接修改文件：

1. 核对 Polar SDK 8.3.0 的 ECG 设置、样本类型、单位、时间戳和取消行为。复用现有连接、就绪检查、订阅管理及旧事件防护。

2. 每次接受 ECG 启动或重试后，重新查询当前可用设置。H10 官方 ECG 采样率为 130 Hz，但配置以实际查询结果为准。唯一选项自动采用；多选项展示并等待确认，不自动选择最大值。配置不完整时阻止 ECG 启动，不修改 HR 或 ACC 设置。

3. 将 startEcgStreaming 接入现有 ECG 订阅。按顺序处理每个 H10 EcgSample，保留传感器 timeStamp（纳秒）和带正负号的 voltage（µV）。不用手机接收时间替代采样时间，不把其他 SDK 样本类型解释为 H10 ECG。收到实际 ECG 样本后才显示 Receiving。

4. 新增 EcgBuffer.kt，在内存中保留 (t - 10 秒, t] 内的数据，t 为最新样本时间戳，同时最多保留 1,300 个样本。超过任一限制时移除最旧样本。收到数据立即逐样本处理，保留原始时间间隔，不插值，不将 ACC 的 30 ms 缺口规则套用于 ECG。

5. 在 SensorActivity.kt 增加临时 Start ECG 和 Stop ECG 按钮。展示 ECG 状态、选定设置、最新电压及单位、传感器时间戳、缓存数量和独立错误。启动、接收或停止期间防止重复查询和重复订阅。协调 ECG、ACC 与就绪查询，避免冲突；设备支持时允许 HR、ACC、ECG 同时运行。

6. 停止、正常结束、失败或断线后保留最后的 ECG 缓存，并标为非活动快照。只在新的 ECG 启动或重试被接受后清空；拒绝重复启动不能清空数据。复用生命周期清理，旋转保持采集，重连后不自动恢复。单独停止或失败 ECG 不停止 HR、ACC，也不断开蓝牙。

7. 复用取消和本地清理，清理完成后才允许重启。不得将本地 Job 完成当作硬件停止已确认，不增加任意重启延迟或自动重试。增加针对性受控测试，覆盖样本提取、时间戳、带符号电压、空批次、缓存限制、重复启动、停止与重启、快照保留、失败隔离和旧事件；复用现有订阅测试。

8. 运行相关测试、debug 构建和 lint。更新两份 AGENTS.md，记录实际实现与验证结果。提供真实 ECG 接收、设置、缓存上限、重复点击、快速停止/重启、HR/ACC/ECG 同时运行、旋转和断线的真机验收步骤，未执行项目标为待验证。记录用户反馈：此前 ACC 启动错误在解除其他程序占用后已解决，不将其记为已确认的本 App 缺陷或全部 ACC 验收通过。

使用最小代码、现有 Compose 主题，以及英文 UI 文本和代码注释。不引入通用缓存框架或无关重构。
仅完成步骤 4.3，保留 4.1 和 4.2 的行为。不实现正式会话控制或计时、Pause/Resume、ECG 滤波、波峰检测、医学解释、统计、图表或持久化。
使用中英文说明，分别列出文件变更、测试与构建结果、真机验证。
```

## English

```text
Implement step 4.3 according to Section 5.17 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Verify the Polar SDK 8.3.0 ECG settings, sample types, units, timestamps and cancellation behavior. Reuse the existing connection, readiness checks, subscription management and stale-event protection.

2. Requery current ECG settings after each start or retry is accepted. H10 ECG is specified at 130 Hz, but use the actual available settings. Automatically accept single available options; display multiple options and wait for confirmation without selecting maximum values. Block incomplete ECG configuration without changing HR or ACC settings.

3. Connect startEcgStreaming to the existing ECG subscription. Process every H10 EcgSample in order, retaining its sensor timeStamp in nanoseconds and signed voltage in µV. Do not substitute phone reception time or reinterpret other SDK sample types as H10 ECG. Mark Receiving only after an actual ECG sample arrives.

4. Add EcgBuffer.kt to retain samples in (t - 10 seconds, t], where t is the latest sample timestamp, with a maximum of 1,300 samples. Remove the oldest samples when either limit is exceeded. Process samples immediately, preserve their original timing, do not interpolate, and do not apply ACC's 30 ms gap rule to ECG.

5. Add temporary Start ECG and Stop ECG buttons in SensorActivity.kt. Show ECG state, selected settings, latest voltage with units, sensor timestamp, buffer count and separate errors. Prevent duplicate queries and subscriptions while starting, receiving or stopping. Coordinate ECG, ACC and readiness queries without preventing HR, ACC and ECG from running together when supported.

6. Retain the final ECG buffer on stop, completion, failure or disconnection and label it as an inactive snapshot. Clear it only after a new ECG start or retry is accepted; rejecting a duplicate start must not clear data. Reuse lifecycle cleanup, preserve collection across rotation and do not resume automatically after reconnection. Stopping or failing ECG alone must not stop HR or ACC or disconnect Bluetooth.

7. Reuse cancellation and local cleanup before allowing restart. Do not treat local Job completion as confirmed hardware shutdown, add arbitrary restart delays or retry automatically. Add focused controlled tests for sample extraction, timestamps, signed voltage, empty batches, buffer limits, duplicate starts, stop/restart, retained snapshots, failure isolation and stale events. Reuse existing subscription tests.

8. Run relevant tests, a debug build and lint. Update both AGENTS.md files with actual implementation and verification results. Provide device checks for real ECG reception, settings, buffer limits, repeated taps, rapid stop/restart, simultaneous HR/ACC/ECG, rotation and disconnection. Mark unperformed checks as pending. Record the user's report that the previous ACC startup error was resolved after removing access by another application, without treating it as a confirmed app defect or full ACC acceptance.

Use minimal code, the existing Compose theme, and English UI text and comments. Do not introduce a generic buffering framework or unrelated refactoring.
Complete only step 4.3 and preserve steps 4.1 and 4.2. Do not implement formal session controls or timing, Pause/Resume, ECG filtering, peak detection, medical interpretation, statistics, charts or persistence.
Explain in Chinese and English, separating file changes, test/build results and device verification.
```

## 实施与验证记录 / Implementation and verification record

- 2026-09-29 补录本次实际使用的中英文提示词；代码和验证细节见两份 AGENTS.md 第 5.17 节。 / The bilingual prompt used for this implementation was recorded on 2026-09-29; implementation and verification details are in Section 5.17 of both AGENTS.md files.
- 已修改 PolarBleManager.kt、SensorActivity.kt；新增 EcgBuffer.kt、EcgBufferTest.kt。 / Modified PolarBleManager.kt and SensorActivity.kt; added EcgBuffer.kt and EcgBufferTest.kt.
- 最近一次实施验证：49 项测试通过，debug 构建成功，lint 0 errors、18 warnings；本次文档补录未重新构建。 / The implementation passed 49 tests, a debug build and lint with 0 errors and 18 warnings; no build was rerun for this documentation update.
- ECG 真机接收、三路并行、快速启停及生命周期验收待完成。 / ECG device reception, simultaneous streaming, rapid restart and lifecycle checks remain pending.

# 步骤 4.4：统一会话控制与计时 / Step 4.4: Unified session controls and timing

2026-09-29 补录：英文保留本次对话中实际提交的实施提示词，中文为对应翻译；这是历史记录，不是再次执行请求。
Recorded on 2026-09-29: the English text preserves the implementation prompt submitted in this conversation, with a Chinese translation. This is a historical record, not a request to execute it again.

## 中文

```text
按照根目录 AGENTS.md 第 5.18 节实施步骤 4.4。先检查现有代码，再直接修改文件：

1. 复用 4.1—4.3 的真实 HR、ACC、ECG 数据流、配置检查、订阅管理和旧事件防护。保留 Polar SDK 8.3.0，不增加另一套订阅框架或无关依赖；使用 SDK API 时核对官方实现细节。

2. 添加最小会话状态与计时逻辑，包括 Idle、Starting、Running、Stopping、Stopped 及结束原因。通过现有 SensorViewModel 保留会话；重组或旋转不能重新创建会话、重启数据流或重置计时。

3. 仅在连接、蓝牙和权限有效、至少一路已就绪且配置完整、全部旧任务已完成清理时接受 Start。创建新的会话轮次、重置计时、清空 HR 读数和全部 ACC/ECG 缓存，包括本次会话不可用的数据流。默认尝试启动 HR、ACC、ECG，分别检查前提并复用 ACC/ECG 的最新设置查询；拒绝重复 Start 不能清空当前数据。

4. Starting 不计时；当前会话任意一路收到第一条真实有效样本后才进入 Running，并使用 SystemClock.elapsedRealtime() 计算经过时间。开始时刻只设置一次，不使用系统日期时间、传感器时间戳或计时器触发次数累加时长。单路失败或数据缺口不停止会话计时；不增加启动或无数据超时，允许在 Starting 时 Stop。

5. 分别显示不可用数据流和启动失败的原因，允许其他流继续。会话进行中，对已失败或未启动且清理完成的数据流提供手动 Retry，重新检查前提和当前设置；仅清空重试流的数据，不重置会话时间或其他流。不自动重试。必要时调整已有查询入口，避免正在运行的数据流永久阻止重新检查失败流。

6. Stop 立即拒绝后续会话数据、冻结经过时间、清空当前 HR，并取消全部订阅；保留 ACC/ECG 缓存作为非活动快照。显示 Stopping，全部本地任务清理完成前禁用 Start 和 Retry，之后显示 Stopped。重复 Stop 不能重复结束会话；正常 Stop 保持蓝牙连接。不将本地清理称为硬件停止已确认，不增加任意重启延迟。

7. 初始启动尝试全部处理后，若所有数据任务均已结束且没有活动任务，则结束会话并记录原因；未收到数据时时长保持零。不能因第一路立即失败而在其他流尚未尝试前提前结束。主动或意外断线、蓝牙或权限丢失、锁屏、后台、返回欢迎页及释放 SDK 时，立即结束会话、冻结时间并复用清理；旋转保留会话。返回后必须手动连接并 Start 新会话，不自动恢复。旧会话的数据、错误、结束事件和计时刷新不能影响新会话。

8. 在 SensorActivity.kt 用统一 Start/Stop 控制替换三组临时独立启停按钮；保留必要的 Retry、各流状态、读数、设置和错误。显示会话状态、经过时间及结束原因；采集统一通过会话入口，不能在会话外启动数据流。不添加虚构统计、占位算法或 Saved 提示。

9. 使用受控流和可控制的单调时钟，测试前提检查、重复 Start/Stop、首条样本开始计时、部分启动失败、单路重试、全部任务结束、立即冻结且不计清理耗时、新会话清空全部缓存、清理期间禁止重启、旧事件及生命周期中断。复用已有 HR、ACC、ECG 和订阅测试，运行相关测试、debug 构建和 lint。

10. 更新两份 AGENTS.md，记录实际改动和验证结果。提供统一三路启动、计时起点、停止但不断开、再次启动、部分失败与重试、重复点击、旋转、断线及后台返回的真机检查步骤；未执行项目标记为待完成。

使用最小代码、现有 Compose 主题，以及英文 UI 文本和代码注释。保留现有单位、时间戳、ACC 配置、缺口规则和缓存限制。
仅完成步骤 4.4，不实现 Pause/Resume、算法、统计、图表、会话持久化、历史、后台采集或进程恢复，也不为这些后续功能添加框架。
中英文说明，分别列出文件修改、测试/构建结果和真机验证。
```

## English

```text
Implement step 4.4 according to Section 5.18 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Reuse the real HR, ACC and ECG streams from steps 4.1–4.3, configuration checks, subscription management and stale-event protection. Keep Polar SDK 8.3.0 without adding another subscription framework or unrelated dependencies. Verify official implementation details when using SDK APIs.

2. Add minimal session state and timing logic with Idle, Starting, Running, Stopping and Stopped states and an end reason. Retain the session through the existing SensorViewModel. Recomposition and rotation must not recreate the session, restart streams or reset timing.

3. Accept Start only when connection, Bluetooth and permissions are valid, at least one stream is ready and fully configured, and all previous tasks have completed cleanup. Create a new session generation, reset timing and clear the HR reading and all ACC/ECG buffers, including streams unavailable for this session. Attempt HR, ACC and ECG by default, checking prerequisites separately and reusing fresh ACC/ECG settings queries. Rejecting duplicate Start must not clear current data.

4. Do not count time while Starting. Enter Running only after the first actual valid sample from any stream in the current session, then calculate elapsed time using SystemClock.elapsedRealtime(). Set the start time only once. Do not use wall-clock time, sensor timestamps or timer tick counts to accumulate duration. A single-stream failure or data gap must not stop session timing. Add no startup or no-data timeout; allow Stop while Starting.

5. Show separate reasons for unavailable streams and startup failures while allowing other streams to continue. During an ongoing session, offer manual Retry for failed or unstarted streams after their cleanup completes. Recheck prerequisites and current settings. Clear only the retried stream's data without resetting session time or other streams. Do not retry automatically. Where necessary, adjust existing query entry points so running streams do not permanently block rechecking a failed stream.

6. Stop must immediately reject further session data, freeze elapsed time, clear current HR and cancel all subscriptions. Retain ACC/ECG buffers as inactive snapshots. Show Stopping and disable Start and Retry until all local tasks complete cleanup, then show Stopped. Repeated Stop must not end the session twice. Normal Stop must keep Bluetooth connected. Do not describe local cleanup as confirmed hardware shutdown or add arbitrary restart delays.

7. End the session with a reason once initial startup attempts have all been processed and every data task has ended with no active tasks remaining. Keep elapsed time at zero if no data was received. Do not end prematurely when one stream fails immediately before the other streams have been attempted. Intentional or unexpected disconnection, Bluetooth or permission loss, locking, backgrounding, returning to the welcome screen and SDK release must immediately end the session, freeze time and reuse cleanup. Preserve the session during rotation. Require manual connection and a new Start after returning; do not resume automatically. Old-session data, errors, completion events and timer updates must not affect a new session.

8. In SensorActivity.kt, replace the three temporary independent start/stop button pairs with unified Start/Stop controls. Retain necessary Retry actions, per-stream states, readings, settings and errors. Display session state, elapsed time and the end reason. Route collection through session entry points so streams cannot start outside a session. Do not add fabricated statistics, placeholder algorithms or Saved messages.

9. Use controlled flows and a controllable monotonic clock to test prerequisites, duplicate Start/Stop, timing from the first sample, partial startup failure, per-stream retry, all tasks ending, immediate timing freeze excluding cleanup time, clearing all buffers for a new session, restart blocking during cleanup, stale events and lifecycle interruptions. Reuse existing HR, ACC, ECG and subscription tests. Run relevant tests, a debug build and lint.

10. Update both AGENTS.md files with actual changes and verification results. Provide device checks for unified three-stream startup, timing onset, stopping without disconnecting, starting again, partial failure and retry, repeated taps, rotation, disconnection and returning from the background. Mark unperformed checks as pending.

Use minimal code, the existing Compose theme, and English UI text and comments. Preserve existing units, timestamps, ACC configuration, gap rules and buffer limits.
Complete only step 4.4. Do not implement Pause/Resume, algorithms, statistics, charts, session persistence, history, background collection or process recovery, or add frameworks for those future features.
Explain in Chinese and English, separating file changes, test/build results and device verification.
```

## 实施与验证记录 / Implementation and verification record

- 新增 SessionState.kt、SessionStateTest.kt；修改 PolarBleManager.kt、SensorActivity.kt、AccBuffer.kt、EcgBuffer.kt，并适配四份已有数据/订阅测试。详见两份 AGENTS.md 第 5.18 节。 / Added SessionState.kt and SessionStateTest.kt; modified PolarBleManager.kt, SensorActivity.kt, AccBuffer.kt and EcgBuffer.kt, and adapted four existing data/subscription test files. See Section 5.18 of both AGENTS.md files.
- 已实现统一 Start/Stop、会话状态及轮次、首条数据开始的单调计时、单路手动 Retry、结束原因及生命周期清理。新会话清空全部旧读数；Stop 立即冻结并清除 HR，保留 ACC/ECG 快照，等待本地清理完成后才允许重新开始。 / Implemented unified Start/Stop, session states and generations, monotonic timing from the first sample, independent manual retry, end reasons and lifecycle cleanup. New sessions clear all readings; Stop immediately freezes time and clears HR, retains ACC/ECG snapshots and blocks restart until local cleanup completes.
- 4.4 实施时验证：59 项测试通过（新增 10 项会话测试），debug 构建成功，lint 0 errors、18 warnings。本次仅补录文档，没有重新运行测试或构建。 / At step 4.4, 59 tests passed, including 10 new session tests; the debug build succeeded, and lint reported 0 errors and 18 warnings. No tests or builds were rerun for this documentation update.
- 三路真机启动、计时起点、快速启停、部分失败/Retry、旋转及断线/后台返回验收均待完成。没有实现算法、统计、图表或会话持久化。 / Device checks for three-stream startup, timing onset, rapid restart, partial failure/retry, rotation and disconnection/background return remain pending. Algorithms, statistics, charts and session persistence are not implemented.

# 步骤 5.1：有效心率与会话统计 / Step 5.1: Valid HR samples and session statistics

2026-09-29：以下为已采用的 5.1 实施提示词，规则见两份 AGENTS.md 第 5.19 节。用户要求执行后，代码、单元测试、debug 构建及 lint 已完成；真机验收待完成，实际结果见本节末尾。
2026-09-29: The following step 5.1 prompt has been adopted and executed at the user's request, following Section 5.19 of both AGENTS.md files. Code changes, unit tests, the debug build and lint are complete. Device verification remains pending; results follow below.

## 中文

```text
按照根目录 AGENTS.md 第 5.19 节实施开发步骤 5.1。先检查现有代码，再直接修改文件：

1. 核对 Polar SDK 8.3.0 的 hr、contactStatus、contactStatusSupported 字段，复用当前 HR 接收、会话、订阅及旧事件防护。有效条件为 hr > 0 && (!contactStatusSupported || contactStatus)。不额外设置心率上下限，不做突变过滤、平滑、插值或 RR 处理。
2. 按顺序处理每批全部样本，所有有效样本均参与当前整场 Session 的最小、最大和算术平均统计；相同数值的新样本也分别计入。只保存 count、sum、min、max 等固定数量的累计状态，平均值使用浮点 sum/count，不按时间加权、不补缺失值、不新增 HR 历史缓存。不得只统计批次末值或因重组、计时刷新重复累计。
3. 保留每批一次的手机接收时间 receivedAt。当前显示由批次最后一个样本决定：有效则显示其 HR 和该批时间，无效则两者显示 --；不能回退到之前的有效样本。无接触且支持检测时显示 No sensor contact，否则对非正值显示 Invalid HR sample；与订阅错误分开。有效样本到达时清除该提示。空批次不改变状态；没有新数据时保留当前显示，不增加无数据超时。
4. 区分订阅收到了数据与会话收到了有效数据。非空 HR 批次可显示 Receiving，但仅在包含有效 HR 样本时才可触发 Starting → Running；全部无效 HR 不能启动计时。混合批次中有效样本可以启动计时，即使末样本无效。保留 ACC/ECG 独立触发计时的行为；已运行后无效 HR 不停止或重置会话计时，也不影响其他流。
5. HR 停止、正常结束、失败或接受 Retry 时，清空当前值、接收时间及临时无效样本提示，保留本场累计统计；Retry 后继续累计。Stop、断线及其他整体中断冻结统计并保留结果；接受新 Start 时清空全部 HR 统计，即使本场 HR 不可用。旋转保留，拒绝重复操作不清空或累计，旧事件不能更新新会话。
6. 在 SensorActivity.kt 显示当前 HR、最小、最大、平均值及必要提示。当前/最小/最大显示整数，平均值显示一位小数，单位 bpm；只在显示时四舍五入，并说明 Mean of valid HR samples。没有有效样本时统计显示 --，首个有效样本到达后 min/max/average 均为该值。沿用现有 Compose 主题和英文界面/注释。
7. 添加针对性受控测试：零/负值、支持与不支持接触检测、混合批次及末样本无效、重复数值、空批次、无有效样本、首个样本、均值精度、重复操作、Retry 保留累计、新 Start 清零、停止/断线冻结、旧事件，以及有效样本启动计时与其他流独立性。复用现有 HR、订阅和会话测试。验证输入 80、80、0、100（接触条件允许）得到 min=80、max=100、average≈86.7，随后无效样本只清空当前显示，不改变统计。
8. 运行相关测试、debug 构建和 lint。更新两份 AGENTS.md，记录实际改动及结果；提供真实 HR、统计更新、接触状态（设备支持且能触发时）、Retry、Stop/新 Start、旋转及断线的真机检查步骤，未执行项目标为待验证。

使用最小代码，复用现有数据与会话流程，不添加通用框架、新依赖或无关重构。
仅完成步骤 5.1。保留现有 ACC/ECG 采集、配置、单位、时间戳、缺口及缓存规则；不实现步伐算法、速度、心率区间、图表、会话持久化、History、Pause/Resume 或进程恢复。不在本步决定仅有无效 HR 的会话是否保存。
中英文说明，分别列出文件修改、测试/构建结果和真机验证，不把此前的测试结果当作本步已验证。
```

## English

```text
Implement development step 5.1 according to Section 5.19 of the root AGENTS.md. Inspect the existing code, then modify the files directly:

1. Verify the hr, contactStatus and contactStatusSupported fields in Polar SDK 8.3.0. Reuse the existing HR reception, session, subscription and stale-event protection. Accept a sample only when hr > 0 && (!contactStatusSupported || contactStatus). Do not add arbitrary HR limits, spike filtering, smoothing, interpolation or RR processing.
2. Process every sample in each batch in order. Include all valid samples in the current session's minimum, maximum and arithmetic mean, counting newly received samples even when their values repeat. Retain only fixed-size aggregates such as count, sum, min and max. Calculate the mean using floating-point sum/count without time weighting, filling missing values or adding an HR history buffer. Do not process only the final sample or count readings again during recomposition or timer updates.
3. Keep one phone reception timestamp, receivedAt, per batch. Let the final sample determine the current display: show its HR and batch timestamp if valid, or -- for both if invalid. Do not fall back to an earlier valid sample. Show No sensor contact when contact detection is supported and reports no contact; otherwise show Invalid HR sample for a non-positive value. Keep this message separate from subscription errors and clear it when a valid sample arrives. Empty batches change nothing. Retain the current display when no new data arrives without adding a no-data timeout.
4. Separate subscription reception from valid data starting session timing. A non-empty HR batch can mark the subscription Receiving, but HR may trigger Starting → Running only when the batch contains a valid sample. All-invalid HR batches must not start timing. A valid sample in a mixed batch can start timing even if its final sample is invalid. Preserve independent timing onset from ACC/ECG samples. Once Running, invalid HR must not stop or reset timing or affect other streams.
5. Clear the current HR, reception time and temporary invalid-sample message on HR stop, normal completion, failure or an accepted Retry, while retaining session aggregates. Continue accumulating after Retry. Freeze and retain statistics on Stop, disconnection or other session interruptions. Reset all HR aggregates on an accepted new Start, even if HR is unavailable for that session. Preserve them across rotation. Rejected duplicate actions must not clear or add data, and stale events must not update a new session.
6. In SensorActivity.kt, show current HR, minimum, maximum, mean and necessary messages. Show current/minimum/maximum as integers and the mean to one decimal place, in bpm. Round only for display and explain Mean of valid HR samples. Show -- for statistics when no valid samples exist; the first valid sample sets min, max and mean to its value. Use the existing Compose theme and English UI text and comments.
7. Add focused controlled tests for zero/negative HR, supported and unsupported contact detection, mixed batches and an invalid final sample, repeated values, empty batches, no valid samples, the first sample, mean precision, duplicate actions, aggregates retained across Retry, reset on new Start, freezing on stop/disconnection, stale events, valid-sample timing onset and stream independence. Reuse existing HR, subscription and session tests. Verify that 80, 80, 0, 100 with acceptable contact produces min=80, max=100 and mean approximately 86.7, and that a subsequent invalid sample clears only the current display without changing the statistics.
8. Run relevant tests, a debug build and lint. Update both AGENTS.md files with actual changes and results. Provide device checks for real HR, statistics, contact status when supported and reproducible, Retry, Stop/new Start, rotation and disconnection. Mark unperformed checks as pending.

Use minimal code and reuse the existing data and session flow without generic frameworks, new dependencies or unrelated refactoring.
Complete only step 5.1. Preserve existing ACC/ECG streaming, configuration, units, timestamps, gap rules and buffer limits. Do not implement step detection, speed, HR zones, charts, session persistence, History, Pause/Resume or process recovery. Do not decide persistence eligibility for sessions containing only invalid HR in this step.
Explain in Chinese and English, separating file changes, test/build results and device verification. Do not present earlier test results as verification of this step.
```

## 实现方向与当前状态 / Implementation direction and current status

- 在 HR 实际接收路径逐样本累计；显示最新值与累计统计分开清理，复用会话轮次保护。 / Accumulate per sample in the HR reception path, clear current readings separately from aggregates, and reuse session-generation protection.
- 订阅 Receiving 不再单独证明 HR 样本有效；会话计时使用有效 HR 或真实 ACC/ECG 数据触发。 / Receiving alone does not establish HR validity; valid HR or actual ACC/ECG data triggers session timing.
- 2026-09-29 实施结果：修改 PolarBleManager.kt、SessionState.kt、SensorActivity.kt，扩展 HeartRateTest.kt 与 SessionStateTest.kt；逐样本统计、批末显示、无效提示、有效数据启动计时、Retry 保留累计及新 Start 清零已接入，没有新增依赖或历史缓存。 / Implemented per-sample statistics, final-sample display, invalid-sample messages, valid-data timing onset, aggregates retained across Retry and reset on new Start in the three production files; expanded the two existing test files without dependencies or history buffers.
- 本次重新执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，69 项测试通过（新增 10 项），0 failures/errors/skipped；debug 构建成功，lint 0 errors、18 warnings。首次沙箱下载被阻止后，通过权限流程在主机环境完成构建。 / Reran all three tasks: 69 tests passed, including 10 added tests, with no failures, errors or skips. The debug build passed; lint reported 0 errors and 18 warnings. After a sandbox download restriction, the build succeeded on the host through the permission flow.
- 真机待验证：真实 HR 与统计、支持时的接触变化、故障后 Retry、Stop/新 Start、旋转、断线及后台返回；本次没有安装或操作手机。测试报告与具体步骤见两份 AGENTS.md 第 5.19 节及第 9 节。 / Device checks remain pending for real HR/statistics, supported contact changes, retry after failure, Stop/new Start, rotation, disconnection and returning from the background. No device was installed to or operated in this turn. See AGENTS.md Sections 5.19 and 9 for evidence and steps.

# 步骤 5.3：步长、速度、距离与简单显示 / Step 5.3: Stride length, speed, distance and simple display

2026-09-29：用户已确认方案，规则见两份 AGENTS.md 第 5.20 节。以下提示词尚未执行，实施前须检查 5.2a—5.2d 依赖；本次仅更新文档。
2026-09-29: The user confirmed the plan in Section 5.20 of both AGENTS.md files. This prompt has not been executed. Check steps 5.2a–5.2d before implementation; this update changes documentation only.

## 中文

```text
按照根目录 AGENTS.md 第 5.20 节实施开发步骤 5.3。先检查现有代码及 5.2a—5.2d 的步伐输出；依赖未完成时说明缺口，不自动实现其他步骤。依赖满足后直接修改文件：

1. 沿用 note/551a40924.docx 的步频、Weinberg 步长公式及 AGENTS.md 后续确认规则。按同段相邻接受候选峰之间的平滑最大/最小值计算 Li = 0.5 × (smax − smin)^0.25。连续四步确认前缓存，通过后仅提交一次；每段首峰没有步长，四步补计只有三段距离，不补造起始距离。
2. 复用 5.4 的步频，速度使用最近五秒区间内已确认步长之和除以窗口秒数；不足五秒时用本段预热结束后的实际时长，禁止零分母。每段步长按后一个峰的原始时间归窗，不按补计时间归窗。复用每 250 ms 及新步伐刷新，不重复累计；正常初始预热/等待确认显示 0 和状态，ACC 正常可用且两秒无确认步伐时当前步频/速度归零。
3. 平均速度为本场累计估计距离除以完整 Running 时长，包含静止和休息，不只统计移动时间、不对显示值求算术平均。Starting 或零时长显示 --，Stop 后冻结。仅保留 Start/Stop，不添加 Pause/Resume 或静止自动暂停。
4. 最大步频和最大速度分别取整场完整、连续、无已知缺口的五秒窗口结果的最大值。从本连续段预热结束的传感器时间起算，真实 ACC 样本时间覆盖满五秒才有首个合格窗口，缺口或检测段重建后重新起算；端点使用实际处理到的样本时间，不以手机显示时钟补足窗口。短窗口只用于当前显示，不参与最大值；首次合格窗口前显示 --。最大值使用原始五秒统计（确认步数 × 12、确认步长之和 / 5），两秒无步归零只影响当前显示。使用未舍入值比较，停止或缺口不清除已有最大值。
5. ACC 相邻时间差严格大于 30 ms 时复用分段，清空检测及当前步频/速度窗口，重新预热；不跨段计算步长、不插值或补算，保留本场累计步数、距离和最大值。ACC 不可用、失败或缺口后预热时显示 -- 及原因；接受 Retry 后重新预热和确认连续步伐，其他流与会话计时继续。沿用正常批次等待规则，不新增停流超时或自动重试。
6. 本场发生已识别 ACC 缺口、不可用或失败时，保留 Incomplete ACC data 标记至会话结束；正常初始算法预热不是数据故障。仍可显示已有距离及其除以完整会话时长得到的平均速度，说明可能因遗漏距离而偏低；最大值仅代表记录完整的窗口。完全无 ACC 观测时统计显示 --，不把缺失当作静止。恢复不清除本场缺失标记。
7. 在现有 SensorActivity.kt 用简单英文文本显示当前步频、估计速度、平均速度、最大步频、最大速度及必要距离/状态信息。步频显示整数 steps/min；速度内部以 m/s 计算、乘 3.6 后显示一位小数 km/h，仅显示时舍入。Stop/整体中断后标明 Stopped：本场曾收到正常 ACC 样本时当前值归零，整场没有 ACC 样本时当前值和相关统计保持 --；停止不更新最大值，累计统计冻结。接受新 Start 清空本场统计和缺失标记，重复操作不清空或累加，旋转保留并拒绝旧会话事件。
8. 添加针对性受控测试，覆盖峰间步长、四步/三段补计、原时间归窗、窗口边界、短窗口、两秒归零、完整窗口最大值、静止计入平均、缺口及 Retry、新 Start、Stop 冻结和旧事件。检查真实覆盖 4.99 秒时手机时间不能补足最大值窗口、覆盖五秒后才参与，当前归零不改变原始窗口统计，以及整场没有 ACC 时停止仍显示 --。检查 100 米、运动 80 秒及静止 20 秒得到平均 3.6 km/h。运行相关测试、debug 构建和 lint，更新两份 AGENTS.md 的实际结果；提供已知距离、静止/走路/跑步、休息、缺失恢复和生命周期的真机检查，未执行标记待验证。

使用最小代码、有界窗口、现有 Compose 主题及英文 UI/注释，复用现有会话和数据路径。仅完成步骤 5.3，保留 HR/ACC/ECG 行为；不实现正式布局、图表、心率区间、历史、持久化、自动校准、通用统计框架或无关重构，不增加依赖。
用中英文分别说明文件修改、测试/构建结果及真机验证，不将已确认规划或以前的构建结果当作本步已完成。
```

## English

```text
Implement development step 5.3 according to Section 5.20 of the root AGENTS.md. Inspect the existing code and step outputs from 5.2a–5.2d first. If prerequisites are missing, report them without implementing other steps. Once prerequisites are satisfied, modify the files directly:

1. Reuse the cadence and Weinberg stride-length formulas in note/551a40924.docx and the subsequently confirmed AGENTS.md rules. Calculate Li = 0.5 × (smax − smin)^0.25 from smoothed values between consecutive accepted candidate peaks in the same segment. Cache until four-step confirmation and submit each length once. The first peak has no length; four confirmed steps provide only three distance intervals. Do not fabricate initial distance.
2. Reuse cadence from Section 5.4. Calculate speed as the sum of confirmed lengths in the latest five-second interval divided by its duration. For a shorter segment, use actual time since warm-up ended; never divide by zero. Assign each length to its later peak's original timestamp, not the confirmation time. Reuse updates on new steps and every 250 ms without accumulating distance again. Show 0 with a status during normal initial warm-up/confirmation; with available ACC, set current cadence and speed to zero after two seconds without a confirmed step.
3. Calculate average speed as recorded session distance divided by the full Running duration, including stationary rest. Do not use moving time alone or average UI refresh values. Show -- during Starting or with zero duration, and freeze on Stop. Keep Start/Stop only; add neither Pause/Resume nor automatic pausing while stationary.
4. Track session maximum cadence and speed separately using only complete, continuous five-second windows without known gaps. Measure from the current segment's warm-up completion timestamp; actual ACC sample timestamps must cover five seconds before the first eligible window. Restart this coverage after a gap or segment reset. Use the timestamp of the sample being processed as the endpoint; the phone display clock must not fill missing coverage. Short windows may update current values but not maxima. Show -- before the first eligible window. Use raw five-second statistics (confirmed steps × 12 and confirmed lengths summed / 5) for maxima; two-second zeroing affects current display only. Compare unrounded values and retain existing maxima across stopping or gaps.
5. Reuse segmentation when consecutive ACC timestamps differ by strictly more than 30 ms. Clear detection and current cadence/speed windows and warm up again. Do not calculate lengths across gaps, interpolate or fabricate data; retain session steps, distance and maxima. Show -- and the reason while ACC is unavailable, failed or warming up after a gap. After an accepted Retry, repeat warm-up and consecutive-step confirmation while other streams and session timing continue. Preserve ordinary batch-waiting behavior without adding a no-data timeout or automatic retry.
6. Retain Incomplete ACC data for the rest of a session after a known ACC gap, unavailability or failure. Normal initial algorithm warm-up is not a data fault. Existing distance and its average over full session time may remain visible, with an explanation that missing distance can lower the result; maxima describe only eligible recorded windows. Show -- for statistics when no ACC observations exist, and do not interpret missing data as stationary activity. Recovery must not clear the session's incomplete-data flag.
7. Use simple English text in the existing SensorActivity.kt to show current cadence, estimated speed, average speed, maximum cadence/speed and necessary distance/status information. Display integer steps/min and one decimal place for km/h, converting internal m/s by multiplying by 3.6 and rounding only for display. On Stop or overall interruption, show Stopped: set current values to zero if normal ACC samples were received in this session, but retain -- for current values and related statistics if none were received. Do not update maxima on stopping; freeze aggregates. Clear session statistics and the incomplete-data flag on an accepted new Start. Duplicate actions must not clear or accumulate data; preserve state across rotation and reject old-session events.
8. Add focused controlled tests for peak-interval lengths, four-step/three-interval backfill, original timestamps, window boundaries, short windows, two-second zeroing, complete-window maxima, rest included in averages, gaps, Retry, new Start, Stop freezing and stale events. Verify that phone time cannot complete a maximum window with only 4.99 seconds of actual coverage, five seconds permits evaluation, current-display zeroing does not change raw window statistics, and stopping a session without ACC retains --. Verify that 100 metres over 80 seconds of movement plus 20 seconds of rest gives 3.6 km/h average speed. Run relevant tests, a debug build and lint, and update both AGENTS.md files with actual results. Provide device checks for known distances, stationary/walking/running activity, rest, recovery and lifecycle behavior; mark unperformed checks as pending.

Use minimal code, bounded windows, the existing Compose theme and English UI text/comments. Reuse current session and data paths. Complete only step 5.3 and preserve HR/ACC/ECG behavior. Do not add the final layout, charts, HR zones, history, persistence, automatic calibration, a generic statistics framework, unrelated refactoring or dependencies.
Explain in Chinese and English, separating file changes, test/build results and device verification. Do not present confirmed plans or earlier builds as completion of this step.
```

# 步骤 5.4：固定心率区间与累计时长 / Step 5.4: Fixed HR zones and accumulated duration

用户已确认以下规则，见两份 AGENTS.md 第 5.21 节。本提示词尚未执行；本次只更新文档，未实施代码或运行测试、构建和真机验证。
The user confirmed these rules in Section 5.21 of both AGENTS.md files. This prompt has not been executed. This update changes documentation only; no implementation, tests, builds or device checks were performed.

## 中文

```text
按照根目录 AGENTS.md 第 5.21 节实施开发步骤 5.4。先检查现有代码及 5.1 的有效 HR 处理和会话计时；依赖未满足时说明缺口，不自动实施其他步骤。依赖满足后直接修改文件：

1. 复用有效条件 hr > 0 && (!contactStatusSupported || contactStatus)。固定区间为 <110、[110,125)、[125,140)、[140,155)、≥155 bpm，标签为 Very light、Light、Moderate、High、Very high，颜色依次为蓝、绿、黄、橙、红。无效 HR 不归区，不增加年龄、最大心率、平滑或滞回设置。
2. 当前标签和之后的计时区间由最新非空批次的最后一个样本决定。末样本无效时显示 Heart rate intensity: -- 并保留对应原因；同批更早的有效值仍参与 5.1 统计，但不能替代批末值作为当前区间。空批次不改变状态；相同 HR 再次收到时正常结算，不按值去重。
3. 采用最近有效读数保持法，以每批一次 SystemClock.elapsedRealtime 接收时刻计算区间时长。先结算旧区间到事件时刻，再切换区间和起点。同批样本不分摊时长，不按样本数量计时，不使用系统日期、RR 或伪造的 HR 传感器时间；保持既有 receivedAt 的含义。仅统计 Running，HR 区间起点不得早于会话起点；ACC/ECG 先启动时，首个有效批末 HR 前的时间未归类，不回填。
4. 无效 HR、无接触、HR 失败、正常结束或接受 Retry 时，结算旧区间并清除当前区间/起点，保留已有累计值。新有效批末 HR 到达后再继续，不补算中间空白，不停止其他流或会话时间。拒绝的重复操作不重复结算。静止但 HR 有效时照常计时；无新批次且无无效/结束通知时继续保持最后区间，不增加超时或自动重试，并用 Estimated from received HR 说明静默停流可能使旧区间时间偏多。
5. 仅维护五区间累计毫秒数、当前可空区间和起点，复用会话轮次与现有 250 ms 刷新。UI 刷新展示已累计值加当前未结算时长，不写回累计值、不固定加 250 ms、不新增定时器。Unclassified time 为同一时刻 Running 时长减五区间显示时长之和，单列文字，不增加第六根柱形或强行补齐五区间。
6. 在现有 Compose 页面添加简单心率强度标签及五根时长柱形；英文标题统一为 Heart rate intensity，仅表示 HR 分档，不表示已确认正在运动。横轴 Zone 1—5 并注明 bpm 范围，纵轴为时长，五柱使用相同且随最大累计时长统一调整的比例尺；显示 mm:ss，内部保留毫秒，仅显示时取整秒。未进入区间为 00:00；完全没有有效 HR 时附 No valid HR data。颜色和文字共同说明区间，不只靠颜色，不增加图表库；正式布局留到第 8 阶段。
7. Stop 或整体中断时结算至会话停止时刻，冻结柱形和未归类时间，不包含清理耗时；当前心率强度显示 -- 和停止状态。接受新 Start 才清零全部区间状态，HR Retry 保留累计，旋转保留；拒绝重复操作和旧事件，不自动恢复旧会话。沿用后台、锁屏、返回欢迎页和断线结束会话的规则。
8. 添加受控测试，覆盖 109/110、124/125、139/140、154/155 的边界，无效和混合批次、末样本无效、空批次、相同 HR、先由其他流启动、切区与结束/Retry 结算、静止、无新批次保持、刷新不重复计时、系统日期变化、未归类时间、Stop 冻结、新 Start 和旧事件。验证第 10 秒收到 120、第 13 秒收到 130 时，3 秒归 Zone 2。运行相关测试、debug 构建和 lint，更新两份 AGENTS.md 实际结果；提供真实 HR 标签、柱形、时长、静止、可复现的接触变化、重试、旋转与断线检查，未执行标记待验证。不能自然触发的心率边界使用受控测试。

仅完成步骤 5.4，使用最小代码、现有 Compose 主题及英文 UI/注释。保留既有 HR/ACC/ECG、步伐和速度行为，不增加 HR 历史缓存、个体化训练算法、警报、通用框架、Pause/Resume、曲线、持久化、History、依赖或无关重构。
中英文说明，分别列出文件修改、测试/构建结果和真机验证，不把规划或旧测试结果作为本步完成证据。
```

## English

```text
Implement development step 5.4 according to Section 5.21 of the root AGENTS.md. Inspect the existing code, step 5.1 HR validation and session timing first. If prerequisites are missing, report them without implementing other steps. Once prerequisites are satisfied, modify the files directly:

1. Reuse hr > 0 && (!contactStatusSupported || contactStatus). Use fixed zones <110, [110,125), [125,140), [140,155) and >=155 bpm, labelled Very light, Light, Moderate, High and Very high, with blue, green, yellow, orange and red respectively. Invalid HR belongs to no zone. Do not add age, maximum-HR, smoothing or hysteresis settings.
2. Use the final sample of the latest nonempty batch to determine the current label and subsequent timing zone. An invalid final sample shows Heart rate intensity: -- with the appropriate reason. Earlier valid samples still contribute to step 5.1 statistics but must not replace the final sample for the current zone. Empty batches do not change state. Settle repeated HR values normally without deduplicating by value.
3. Hold the latest valid reading and calculate zone duration using one SystemClock.elapsedRealtime reception timestamp per batch. Settle the old zone to the event time before changing the zone and timing anchor. Do not divide batch time among samples, count samples as time, or use wall-clock dates, RR intervals or invented HR sensor timestamps. Preserve receivedAt semantics. Count only Running time; zone timing must not precede the session start. If ACC/ECG starts the session first, time before the first valid final HR sample is unclassified and must not be backfilled.
4. On invalid HR, lost contact, HR failure, normal completion or an accepted Retry, settle the old zone and clear the current zone/anchor while retaining accumulated durations. Resume only from a new valid final HR sample without filling the interruption or stopping other streams/session timing. Rejected duplicate actions must not settle twice. Continue counting while stationary with valid HR. With no new batch and no invalid/end notification, keep estimating the last zone without adding a timeout or automatic retry. Show Estimated from received HR and explain that silent stream loss can overestimate the last zone's duration.
5. Keep only five accumulated millisecond durations, the nullable current zone and its timing anchor, reusing session-generation protection and the existing 250 ms refresh. Refreshes display accumulated values plus the current unsettled interval without writing back, adding a fixed 250 ms or creating another timer. Calculate Unclassified time as Running duration minus the sum of the five displayed zone durations at the same instant. Show it separately as text, not a sixth bar, and do not force the five zones to cover the session.
6. Add a simple heart rate intensity label and five duration bars to the existing Compose screen. Use the title Heart rate intensity to describe the HR zone, without implying detected movement. Use Zone 1–5 and bpm ranges on the horizontal axis and duration on the vertical axis. All bars share one scale adjusted to the largest accumulated duration. Display mm:ss while retaining milliseconds internally and truncating only display seconds. Unvisited zones show 00:00; show No valid HR data when none exists. Use text as well as colour. Add no chart library; leave final layout integration to stage 8.
7. On Stop or overall interruption, settle to the session stop time and freeze bars and unclassified time, excluding cleanup duration. Show -- with the stopped status for current heart rate intensity. Clear all zone state only on an accepted new Start; retain accumulations across HR Retry and rotation. Reject duplicate actions and stale events, and do not resume old sessions. Preserve existing background, lock, welcome-screen and disconnection behavior.
8. Add controlled tests for boundaries 109/110, 124/125, 139/140 and 154/155; invalid/mixed batches, invalid final samples, empty batches, repeated HR, another stream starting first, zone changes, completion/Retry settlement, stationary time, holding without new batches, refreshes without double counting, wall-clock changes, unclassified time, Stop freezing, new Start and stale events. Verify that HR 120 at second 10 and HR 130 at second 13 assign three seconds to Zone 2. Run relevant tests, a debug build and lint, and update both AGENTS.md files with actual results. Provide device checks for real HR labels, bars, durations, stationary activity, reproducible contact changes, retry, rotation and disconnection; mark unperformed checks as pending. Use controlled tests for HR boundaries that cannot naturally be reproduced.

Complete only step 5.4 with minimal code, the existing Compose theme and English UI text/comments. Preserve HR/ACC/ECG, step and speed behavior. Do not add HR history buffers, personalized training algorithms, alerts, generic frameworks, Pause/Resume, curves, persistence, History, dependencies or unrelated refactoring.
Explain in Chinese and English, separating file changes, test/build results and device verification. Do not present plans or older test results as evidence of this step's completion.
```

# 步骤 5.5：曲线数据与时间窗口 / Step 5.5: Chart data and time windows

规则已确认，详见两份 AGENTS.md 第 5.22 节。以下为尚未执行的中英文实施提示词；本次只更新文档，未实施代码或运行测试/构建、真机验证。
The rules are confirmed in Section 5.22 of both AGENTS.md files. These implementation prompts have not been executed; this update changes documentation only, without code changes, tests, builds or device verification.

## 中文

```text
按照根目录 AGENTS.md 第 5.22 节实施开发步骤 5.5。先检查现有代码、真实 HR/ACC/ECG 采集、5.1 有效性处理、5.2—5.3 步频/速度输出和会话时间；缺少依赖时说明，不自动推进其他步骤。依赖满足后直接修改文件：

1. 为 HR 提供最近 60 秒、最多 61 个显示记录，每个会话整数秒保留最后真实非空批次的末样本及实际接收时间，不取平均。沿用批末有效性，无效值留空并断段，不画成 0；桶内替换不能抹掉断段。无新 HR 不新增点，相同值的新接收仍是数据。该处理只用于曲线，不改变逐样本心率统计或区间计时。
2. 为步频和速度分别保留最近 60 秒、最多 241 点，复用每 250 ms 的已有计算/刷新入口记录一次当前结果，不在绘图、重组或每次候选步事件中重复追加或重算算法。预热/不可用留空，正常 ACC 下静止零值可绘制。所有显示缓存同时按时间及点数移除最旧记录，不补点、不无限累计；断段信息随记录保持有界。
3. ECG 显示最近 5 秒，复用现有 10 秒/最多 1,300 样本原始缓存，不另建长期原始缓存。130 Hz 下窗口约 650 点，绘制窗口内全部样本，不合并、平均、隔点抽取或平滑；保留实际配置，不硬改采样率。HR、步频/速度最多每 250 ms 刷新画面，ECG 可见时最多每 100 ms 刷新；刷新频率不是采样频率，不能每次只取一个 ECG 样本。
4. 横轴统一为 Running 起点后的经过秒数，显示 mm:ss。HR 用批次 elapsedRealtime 接收时刻减会话起点；步频/速度用结果记录时的会话经过时间。保留 receivedAt 日期时间原义，不用系统日期计算横轴；ACC 算法仍使用原始传感器时间。
5. ECG 每个有效订阅仅用首个非空批次建立固定锚点：S0 为批末传感器 ns，P0 为批次手机 elapsedRealtime ms，T0 为会话起点 ms。每个样本 x = (P0 − T0)/1000 + (timeStamp − S0)/1,000,000,000，先做整数时间差再转换浮点秒。保留样本间隔，不把整批画在同一时刻、不逐批移动锚点；负 x 不绘制、不挤到零点。Retry 重建锚点但保留 T0，说明这是含传输延迟的近似对齐，不承诺多流精确同步。
6. HR 遇无效/无接触、失败或结束时断段，合并前相邻真实批次接收时间差严格大于 3 秒也不连线；这只影响绘图，不改变 5.4 区间保持计时。步频/速度复用 ACC 严格大于 30 ms 的缺口、算法段重建和订阅中断，预热后新段开始。ECG 使用原始相邻样本时间差严格大于 3/实际采样率 秒作为绘图断段阈值，130 Hz 时约 23.1 ms；跨批次也检查，不套用 ACC 阈值或触发额外断开/重试。全部曲线不跨缺口连线、不插值或补零，不延长没有新数据的 HR 水平线。
7. 单路失败/正常结束保留并冻结曲线快照；普通缺口只断段。接受 Retry 清空对应曲线和显示锚点/分段，ACC 同时清空步频和速度，其他曲线及本场累计统计保留，会话横轴不归零；拒绝操作不清空。Stop/整体中断冻结曲线及视窗，不添加人为归零点或滚动到空白；接受新 Start 清空全部。旋转、切图保留状态和数据，不重启订阅；未显示的图继续更新有界数据但不持续绘制，拒绝旧会话/旧订阅事件。
8. 使用现有 Compose 主题做简单绘图验收，提供 HR、步频/速度、ECG 切换，步频与速度在同一区域内单独切换，不共用不同单位的纵轴。单位为 bpm、steps/min、km/h、µV，ECG 保留正负值。完整布局留到第 8 阶段，不增加图表依赖、通用缓存框架、历史回放或持久化。
9. 添加针对性受控测试，覆盖末点非平均、无新数据不造点、桶内断段、时间/点数双上限、ECG 五秒子集保留所有样本、固定锚点与单位换算、负 x 排除、系统日期变化、三类断段边界、Retry 范围、Stop 冻结无尾部零点、新 Start、切图/旋转及旧事件。运行相关测试、debug 构建和 lint，更新两份 AGENTS.md 实际结果；提供真实曲线、滚动、ECG 刷新性能、静止、缺口、Retry、切图和旋转检查，未执行标记待验证。

仅完成步骤 5.5，使用最小代码和英文 UI/注释，保留原始数据、算法、区间计时、会话及资源释放规则，不增加滤波、自动重试、Pause/Resume、进程恢复、无关依赖或重构。60 秒曲线缓存不是完整会话存储。
中英文说明，分别列出文件修改、测试/构建结果和真机验证，不把规划或旧构建结果当作本步验收通过。
```

## English

```text
Implement development step 5.5 according to Section 5.22 of the root AGENTS.md. Inspect the existing code, real HR/ACC/ECG reception, step 5.1 validation, cadence/speed outputs from steps 5.2–5.3 and session timing first. Report missing prerequisites without implementing other steps. Once prerequisites are satisfied, modify the files directly:

1. Retain the latest 60 seconds of HR chart data, capped at 61 display records. Within each integer session-second bucket, keep the final sample of the latest real nonempty batch and its actual reception time, without averaging. Preserve final-sample validity: invalid values create a gap rather than zero. Bucket replacement must not erase a segment break. Add no points without new HR; repeated values in new batches remain real data. This selection affects charts only, not per-sample HR statistics or zone timing.
2. Retain the latest 60 seconds of cadence and speed, capped at 241 points each. Reuse the existing 250 ms calculation/refresh entry point to record the current results once. Do not append again or rerun algorithms during drawing, recomposition or every candidate-step event. Leave warm-up/unavailable intervals blank; plot genuine stationary zeros with available ACC. Enforce both time and count limits, remove the oldest records, never pad points, and keep segment markers bounded with their records.
3. Show the latest five seconds of ECG using the existing ten-second/1,300-sample raw buffer, without another long-lived raw copy. At 130 Hz this is approximately 650 points. Draw every sample in the visible window without merging, averaging, skipping or smoothing. Preserve actual confirmed settings instead of forcing a sampling rate. Refresh HR/cadence/speed charts at most every 250 ms and visible ECG at most every 100 ms. Drawing frequency is not sampling frequency; do not select only one ECG sample per refresh.
4. Use elapsed seconds since Running began for the horizontal axis, labelled mm:ss. For HR, subtract the session origin from batch elapsedRealtime reception time. For cadence/speed, use session time when recording the calculated output. Preserve wall-clock receivedAt semantics but do not use dates for chart timing. ACC algorithms continue to use original sensor timestamps.
5. Establish one fixed ECG anchor per valid subscription from its first nonempty batch: S0 is the last sample's sensor timestamp in ns, P0 is batch elapsedRealtime reception time in ms, and T0 is the session origin in ms. Map each sample as x = (P0 − T0)/1000 + (timeStamp − S0)/1,000,000,000, subtracting integer timestamps before converting to floating-point seconds. Preserve sample spacing rather than placing a whole batch at one instant or shifting the anchor each batch. Omit negative x values instead of piling them at zero. Retry establishes a new anchor while retaining T0. Describe this as approximate alignment including transport delay, not exact synchronization between streams.
6. Break HR lines on invalid samples, lost contact, failure or completion, and when consecutive real batch receptions before display reduction are strictly more than three seconds apart. This affects drawing only, not step 5.4 zone-duration holding. Cadence/speed reuse ACC gaps strictly over 30 ms, detection-segment resets and subscription interruptions, starting a new line after warm-up. For ECG, break when original adjacent sample timestamps differ by strictly more than 3/actualSampleRate seconds, approximately 23.1 ms at 130 Hz, including across batches. Do not reuse ACC's threshold or trigger extra disconnection/retry. Never connect across gaps, interpolate or fill missing values with zero, or extend HR horizontally without new data.
7. Retain and freeze chart snapshots on individual stream failure/completion; ordinary gaps only break lines. An accepted Retry clears the corresponding chart and display anchor/segments; ACC clears both cadence and speed. Preserve other charts and session aggregates without resetting the session time axis. Rejected actions must not clear data. Stop or overall interruption freezes chart data and viewport without appending artificial zero points or scrolling to an empty view. An accepted new Start clears all charts. Preserve data/state across rotation and chart selection without restarting subscriptions. Hidden charts maintain bounded data but do not continuously draw. Reject old-session and old-subscription events.
8. Provide simple chart verification using the existing Compose theme, with HR, cadence/speed and ECG selection. Select cadence or speed within their shared area rather than plotting different units on one vertical axis. Use bpm, steps/min, km/h and µV, preserving signed ECG values. Leave final layout integration to stage 8. Add no chart dependency, generic buffering framework, history replay or persistence.
9. Add focused controlled tests for last-point selection rather than averaging, no fabricated points, intra-bucket gaps, time/count limits, the five-second ECG subset retaining every sample, fixed anchors and unit conversion, negative x exclusion, wall-clock changes, gap boundaries for each chart, Retry scope, Stop freezing without trailing zeros, new Start, chart selection/rotation and stale events. Run relevant tests, a debug build and lint. Update both AGENTS.md files with actual results and provide device checks for real curves, scrolling, ECG refresh performance, stationary activity, gaps, Retry, chart switching and rotation. Mark unperformed checks as pending.

Complete only step 5.5 with minimal code and English UI text/comments. Preserve raw data, algorithms, zone timing, sessions and resource cleanup. Do not add filtering, automatic retry, Pause/Resume, process recovery, unrelated dependencies or refactoring. A 60-second chart buffer is not full-session storage.
Explain in Chinese and English, separating file changes, test/build results and device verification. Do not present plans or older builds as this step's acceptance results.
```

# 第 5 阶段 Fix：心率强度命名 / Stage 5 fix: Heart rate intensity naming

## 中文

```text
遵循根目录 AGENTS.md，仅统一第 5 阶段的强度命名。先检查现有代码，再修改适用文件：

1. 将表示固定 HR 分档的“运动强度”统一为“心率强度”，英文界面使用 Heart rate intensity。有效标签示例：Heart rate intensity: Moderate · Zone 3；无有效读数或结束时显示 Heart rate intensity: --，保留原有原因和状态。
2. 保留现有五档 HR 阈值、有效性判断、区间时长和生命周期规则。该名称只表示心率分档，不表示已由 ACC 确认运动，不添加运动/静止分类。
3. 若代码已实现强度展示，只修改相关文案；若尚未实现 5.4，则只更新规划和提示词，不新增占位界面或提前实现 5.4。
4. 同步两份 AGENTS.md 和本提示词，记录实际修改范围。修改代码时运行 debug 构建；仅修改文档时说明未运行构建。真机未执行的检查标为待验证。

使用最小改动，保留英文 UI 与注释，不重构其他功能。说明中英文对照，区分文档修改、代码修改、构建与真机验证。
```

## English

```text
Follow the root AGENTS.md and fix only stage 5 intensity naming. Inspect the existing code before modifying applicable files:

1. Use Heart rate intensity for the fixed HR-zone label, with the Chinese documentation term 心率强度 instead of 运动强度. Example: Heart rate intensity: Moderate · Zone 3. Show Heart rate intensity: -- when no valid reading is available or the session has ended, retaining existing reasons and status.
2. Preserve the five HR thresholds, validity checks, zone durations and lifecycle rules. This label describes an HR zone, not movement confirmed by ACC. Do not add movement or stationary classification.
3. If intensity display exists, change only the relevant wording. If step 5.4 is not implemented, update only the plan and prompt; do not add placeholder UI or implement step 5.4 early.
4. Update both AGENTS.md files and this prompt with the actual scope. Run a debug build if code changes; state that no build was run for documentation-only changes. Mark unperformed device checks as pending.

Use minimal changes, English UI text and comments, and no unrelated refactoring. Explain in Chinese and English, separating documentation, code changes, build results and device verification.
```

## 实施记录 / Implementation record

- 已检查 app/src 下的源码与资源：目前没有强度标签或分档实现，没有可替换的旧强度文案。本次同步两份 AGENTS.md 和根目录 prompt.md，未修改 Kotlin 或资源文件，也未提前实施 5.4。
- Inspected source and resources under app/src: no intensity label or zone implementation exists to rename. Updated both AGENTS.md files and the root prompt.md only; no Kotlin or resource changes, and step 5.4 remains unimplemented.
- 本次未运行测试、构建或真机检查；5.4 实施后再验证实际标签。/ No tests, build or device checks were run for this documentation update. Verify the actual label after step 5.4 is implemented.

# 第 6 阶段：SQLite 与最简单 History 验收 / Stage 6: SQLite and minimal History verification

2026-09-29：用户已采用全部推荐方案。以下提示词已记录但尚未执行；本次只修改两份 AGENTS.md 和两份 prompt.md，没有修改应用代码、运行测试/构建或进行真机验证。规则以 AGENTS.md 第 5.23 节为准。
2026-09-29: The user accepted the complete proposed design. The prompts below are recorded but not executed. This update changes only the two AGENTS.md files and two prompt.md files; no application code, tests, build or device verification. Section 5.23 of AGENTS.md defines the rules.

按 6.1a → 6.1b → 6.1c → 6.1d → 6.2 分步实施，每次只执行一个用户指定编号。先检查 5.3—5.5 依赖，缺少时说明，不自动补做；当前仅准备文档，不因下面出现“修改文件”而执行代码任务。此前两份 prompt.md 历史内容不同；2026-09-30 已核对项目副本没有独有记录，并以包含完整历史的根目录版同步两份最新版。
Execute 6.1a → 6.1b → 6.1c → 6.1d → 6.2, one user-requested step at a time. Inspect prerequisites from 5.3–5.5 and report missing work without implementing it automatically. This turn is documentation only; the future instructions to modify files are not authorization to execute them now. The copies previously differed in historical coverage. On 2026-09-30 the project copy was verified to contain no unique records, and both were synchronized from the root copy containing the complete history.

## 步骤 6.1a：会话身份与摘要 / Step 6.1a: Session identity and summary

### 中文

```text
按照根目录 AGENTS.md 第 5.23 节，仅实施 6.1a。先检查现有代码及 5.3—5.5；缺少依赖时说明并停止实施，不自动补做。依赖满足后直接修改文件：

1. 复用现有会话控制，每次接受 Start 才生成 UUID；重复/拒绝的 Start 不改变 ID。记录 startRequestedAt、startedAt、endedAt 的 Unix 毫秒及单调 durationMs，startedAt 对应首次进入 Running。保存设备名称/ID 快照；曲线时间结构使用 Running 起点后的 elapsedMs，不用系统日期相减。
2. 定义未来对应 sessions、hr_points、motion_points 的最小数据结构，不创建数据库或收集曲线。摘要包含 HR min/max/mean 和有效样本数、五区间及未归类毫秒、总步数、平均/最大/最小步频、累计估计距离、平均/最大估计速度、会话时间/设备/结束原因、各流观测及已知缺失/失败标记。历史点包含会话秒桶、实际 elapsedMs、可空值和断段信息。保存未舍入 ms/m/m·s^-1 数值，未知用 null，真实零用 0。
3. 接入已有摘要来源，不重复计算已有统计。平均/最小步频本步仅定义可空字段，显示未实施/--，不得填零或宣称已测得；计算留到 6.1b。区分收到 HR 和收到有效 HR。至少一个有效 HR 或真实 ACC/ECG 样本才具备保存资格，完全无数据/仅无效 HR 不具备资格，不设最短时长。
4. 在 Stop、整体中断或全部流终止时，复用既有结算顺序，冻结本场元信息和已有摘要，不把清理耗时计入；不因重复结束再次结算。Retry 保留本场 ID/累计摘要，旋转保留，新 Start 创建新记录，旧会话事件不能更新新记录。不提前增加保存失败状态、数据库任务或四小时自动结束。
5. 只加英文测试文本：Session ID、开始/结束/时长、设备、Eligible for saving 及已有摘要。使用现有 Compose 主题，不加历史列表、正式布局或 Saved 状态。
6. 受控测试覆盖新/重复 Start、时间职责和日期变化、有效性资格、设备快照、null/0、Stop/中断冻结、Retry/旋转/旧事件。运行相关单元测试、debug 构建及 lint；同步两份 AGENTS.md 和两份 prompt.md 的本步实际结果，真机未执行标待验证，不引用旧检查作为本步通过证据。

仅完成 6.1a，最小代码、英文 UI/注释；不实施 6.1b—6.1d、6.2，不新增依赖、通用框架或无关重构。中英文区分文件修改、自动验证和真机结果。
```

### English

```text
Implement only 6.1a under Section 5.23 of the root AGENTS.md. Inspect current code and steps 5.3–5.5 first. Report missing prerequisites and stop implementation without completing other steps automatically. Once satisfied, modify files directly:

1. Reuse session control and generate a UUID only on accepted Start. Rejected/duplicate Start must not change it. Record Unix millisecond startRequestedAt, startedAt and endedAt plus monotonic durationMs; startedAt is entry into Running. Snapshot device name/ID. Define chart elapsedMs relative to Running, not wall-clock differences.
2. Define minimal future sessions, hr_points and motion_points data structures without creating a database or collecting curves. Include HR min/max/mean and valid count; five zone and unclassified durations; total steps and mean/max/min cadence; estimated distance and mean/max speed; times/device/end reason and per-stream observation/known missing/failure flags. History records contain a session-second bucket, actual elapsedMs, nullable values and breaks. Preserve unrounded ms/metres/metres-per-second values; distinguish null from genuine zero.
3. Populate existing summary fields from their current owners without recalculating statistics. Only define nullable mean/min cadence fields here; label them unimplemented/-- rather than zero or observed results. Calculation belongs to 6.1b. Distinguish received HR from valid HR. Eligibility requires at least one valid HR or real ACC/ECG sample; exclude no-data and invalid-HR-only attempts without imposing a minimum duration.
4. On Stop, overall interruption or all-stream termination, reuse existing settlement order and freeze metadata/existing summary without cleanup duration or duplicate settlement. Retain identity/accumulations across Retry and rotation; create a fresh record on new Start and reject stale events. Add no save-failure state, database tasks or automatic four-hour ending.
5. Add only simple English text for session ID, times/duration, device, save eligibility and existing summary, using the current Compose theme. Add no History list, final layout or Saved state.
6. Test new/duplicate Start, clock responsibilities/date changes, eligibility, device snapshots, null/zero, ending, Retry/rotation/stale events. Run relevant unit tests, a debug build and lint. Update both AGENTS.md and both prompt.md files with this step's actual results; mark unperformed device checks pending rather than reusing old evidence.

Complete only 6.1a with minimal code and English UI/comments. Do not implement 6.1b–6.1d or 6.2, add dependencies/frameworks or refactor unrelated code. Explain file changes, automated verification and device results in Chinese and English.
```

## 步骤 6.1b：平均与最小步频 / Step 6.1b: Mean and minimum cadence

### 中文

```text
按照根目录 AGENTS.md 第 5.23.2 节，仅实施 6.1b。先检查 5.3—5.5 和 6.1a 的摘要/时间字段；缺少依赖时说明，不自动补做。依赖满足后直接修改文件：

1. 平均步频 = 总确认步数 × 60 / Running 秒数，包含静止和完整 Running 分母，不对显示值或历史点求平均。分母零或无 ACC 观测时为 null；存在 ACC 缺失时保留已记录结果并标不完整，不补步、不扣除缺失时间。
2. 最小步频复用 5.3 最大步频的同一批合格窗口：从检测段预热结束起，由真实 ACC 传感器时间覆盖完整连续五秒且无已知缺口，取原始 N5 × 12 的最小值。保留既有最大值口径，不创建第二套窗口；不使用短窗口、手机显示时钟补足的窗口、两秒强制归零或 Stop 人为零。合格静止可为 0，无合格窗口为 null，不要求平均位于极值之间。
3. 将真实计算结果接入 6.1a 摘要。缺口/Retry 保留累计及已有极值，新段重新满五秒后才更新极值；Stop 冻结、新 Start 清零、旋转保留、旧事件无效。不增加历史点收集或更改步数/步长算法。
4. 只增加最简单英文 Mean cadence、Min cadence，复用 Max cadence，并显示 -- 或不完整原因；内部保留小数，只在展示时舍入，单位 steps/min，不做正式布局。
5. 受控测试平均公式/静止/无 ACC/零分母、4.99/5 秒真实窗口边界、短窗口及强制归零排除、静止零、缺口/Retry/Stop/新 Start/旧事件和摘要更新。运行相关测试、debug 构建、lint；同步两份 AGENTS.md 和两份 prompt.md 的实际结果，真机未执行标待验证。

仅完成 6.1b，最小代码、英文 UI/注释；不自动推进 6.1c、6.1d 或 6.2，不增加依赖、通用框架或无关重构。中英文分别说明修改、自动验证及真机结果。
```

### English

```text
Implement only 6.1b under Section 5.23.2 of the root AGENTS.md. Inspect steps 5.3–5.5 and 6.1a summary/time fields first. Report missing prerequisites without implementing them automatically. Once satisfied, modify files directly:

1. Mean cadence = total confirmed steps × 60 / Running seconds, including stationary time and the full denominator. Do not average display/chart values. Return null with no ACC observations or a zero denominator. Retain recorded results with an incomplete flag after missing ACC, without imputing steps or subtracting missing duration.
2. Reuse exactly the qualifying windows used by step 5.3 maximum cadence: genuine continuous five-second ACC coverage since segment warm-up completion, with no known gap. Minimum cadence is the minimum raw N5 × 12. Preserve existing maximum semantics without another window pipeline. Exclude short/display-extrapolated windows, two-second forced display zeros and Stop zeros. Genuine stationary windows may yield zero; no qualifying window means null. Do not force the mean between extrema.
3. Populate the 6.1a summary with actual results. Retain accumulated values/extrema across gaps and Retry; wait for a fresh full window after segment reset. Freeze on Stop, reset on new Start, retain rotation and reject stale events. Do not collect history or change step/stride detection.
4. Add only simple English Mean cadence and Min cadence text, reusing Max cadence, with -- or incomplete reasons. Preserve internal precision, round only display values and use steps/min. Add no final layout.
5. Test the mean formula, stationary time, no ACC/zero denominator, genuine 4.99/5-second boundaries, short/forced-zero exclusions, stationary zero, gaps/Retry/Stop/new Start/stale events and summary updates. Run relevant tests, a debug build and lint. Update both AGENTS.md and both prompt.md files with actual results and mark unperformed device checks pending.

Complete only 6.1b with minimal code and English UI/comments. Do not advance to 6.1c, 6.1d or 6.2, add dependencies/frameworks or refactor unrelated code. Explain changes, automated verification and device results in Chinese and English.
```

## 步骤 6.1c：整场心率历史 / Step 6.1c: Full-session HR history

### 中文

```text
按照根目录 AGENTS.md 第 5.23.3 节，仅实施 6.1c 的 HR 历史部分。检查 5.3—5.5、6.1a—6.1b 已完成；缺少依赖时说明，不自动补做。依赖满足后直接修改文件：

1. 复用 6.1a 的 HR 点结构，从真实非空 HR 接收事件收集整场记录，不重放最后 60 秒实时缓存。以 Running 起点后的 elapsedMs 整数秒分桶，每秒保留最后真实批次的末样本及实际接收时刻，不取平均；无新 HR 不造点，相同值的新真实批次仍更新。
2. 保留批末有效性，无效值为 null 并断段，不写 0。沿用 5.22 的无效/接触丢失/订阅中断及相邻真实批次严格超过三秒断段，桶内替换不能抹掉已有断段；不补点、不插值，不改变全部有效样本的 HR 统计或区间保持计时。
3. 只收集前四小时，最多 14,401 个秒桶，保留实际 elapsedMs，拒收超出截止的数据；不预分配、不填满、不删早期点伪装整场。超过 60 秒仍保留早期历史。四小时自动结束/保存属于 6.2，本步不接入。
4. 本步完成 HR 历史自身生命周期：接受新 Start 清空，Stop/中断冻结且保留最后部分秒已有记录，不造终点；HR Retry 保留旧段，恢复后沿用原会话横轴，实时缓存清理规则不变。旋转保留，拒绝旧会话/旧订阅数据。此时不收集步频/速度历史，完整组合快照留 6.1d。
5. 仅添加英文 HR history points、首末 elapsedMs 和必要状态文本，使用现有主题；不画正式 History、不写数据库、不显示 Saved。
6. 测试末点非平均、无新数据/重复值、混合批次末值、null/断段、三秒边界、桶内中断、超过 60 秒历史保留、Retry、部分秒、Stop/旋转/旧事件及四小时/数量上限。使用受控时钟，不等待四小时。运行相关测试、debug 构建、lint；同步两份 AGENTS.md 和两份 prompt.md 实际结果，未执行真机检查标待验证。

仅完成 6.1c，最小代码、英文 UI/注释；不推进 6.1d/6.2，不增加框架、依赖、原始 ACC/ECG 历史或无关重构。中英文区分文件修改、自动验证和真机结果。
```

### English

```text
Implement only the HR history part of 6.1c under Section 5.23.3 of the root AGENTS.md. Verify completion of 5.3–5.5 and 6.1a–6.1b first. Report missing prerequisites without implementing them automatically. Once satisfied, modify files directly:

1. Reuse the 6.1a HR point structure and collect full-session records from real nonempty HR events, not by replaying the final 60-second buffer. Bucket by integer elapsed seconds since Running. Keep the final sample of the latest real batch and its actual reception elapsedMs per bucket without averaging. Add no points without new HR; repeated values in new batches still update the bucket.
2. Preserve final-sample validity: invalid values are null with a break, not zero. Follow 5.22 breaks for invalid/contact loss/subscription interruption and real batch gaps strictly over three seconds. Retain intra-bucket breaks during replacement. Do not pad/interpolate or change all-valid-sample HR statistics or zone holding durations.
3. Collect only the first four hours, capped at 14,401 second buckets, retaining actual timestamps and rejecting later records. Do not preallocate, pad or discard early records to pretend completeness. Retain records beyond the 60-second real-time window. Automatic ending/saving at four hours belongs to 6.2.
4. Complete the HR history lifecycle here: clear on accepted new Start; freeze on Stop/interruption with the existing partial-second record and no fabricated endpoint. Retain pre-Retry segments and the original session axis while preserving real-time cache clearing. Retain rotation and reject stale session/subscription events. Do not collect motion history; combined final snapshots belong to 6.1d.
5. Add only simple English HR history point counts, first/last elapsedMs and necessary status text using the current theme. Add no final History view, database writes or Saved state.
6. Test last-point selection, no new data/repeated values, final samples in mixed batches, null/breaks, three-second boundaries, intra-bucket interruptions, history beyond 60 seconds, Retry, partial seconds, Stop/rotation/stale events and four-hour/count limits. Use a controlled clock without waiting four hours. Run relevant tests, a debug build and lint. Update both AGENTS.md and both prompt.md files with actual results; mark unperformed device checks pending.

Complete only 6.1c with minimal code and English UI/comments. Do not advance to 6.1d/6.2, add frameworks/dependencies, raw ACC/ECG history or unrelated refactoring. Explain file changes, automated verification and device results in Chinese and English.
```

## 步骤 6.1d：整场步频/速度历史与完整快照 / Step 6.1d: Motion history and complete snapshot

### 中文

```text
按照根目录 AGENTS.md 第 5.23 节，仅实施 6.1d。检查 5.3—5.5、6.1a—6.1c 已完成，缺少依赖时说明，不自动补做。依赖满足后直接修改文件：

1. 复用 6.1a 的 motion_points 数据结构及已有 250 ms 结果记录入口，每个会话整数秒保留最后一组步频/速度及实际 elapsedMs。从实时计算结果收集整场，不在 Stop 重放最后 60 秒，不在绘图/重组中重复记录，不增加定时器，不重算检测算法；未执行刷新不补点。
2. 预热/不可用留空，正常 ACC 下真实静止可为 0。沿用 ACC 严格超过 30 ms 缺口、检测段重建及订阅中断规则；秒桶替换保留断段，不插值/补零。当前显示的两秒无步规则保持原样，不从这些降频显示记录反算均值或极值。
3. 运动历史自身限定前四小时及最多 14,401 条，不预分配、不填满、不删除早期数据；和 HR 共用同一个 Running 时间基准及截止边界，但不强制点数/时间逐点相同。四小时自动结束及保存仍留 6.2。
4. 完成运动历史的 Start/Stop/Retry/旋转/旧事件接线；Retry 保留此前历史并断段，实时清理规则不变；Stop 保留已有部分秒，不追加人为零或终点。复用 6.1a 摘要冻结和 6.1c HR 接线，在同一次会话结束时组装同 ID 的摘要、HR 和运动历史冻结快照，不重复结算/追加；后续新 Start 或迟到数据不得修改旧快照。
5. 只增加英文 Motion history points、首末 elapsedMs 和快照状态文本，并复用已有 ID、摘要及 HR 点数显示。暂时只是内存快照，不显示 Saved，不创建数据库、保存任务、失败重试或 History 页面；应用级待保存快照管理留 6.2。
6. 测试每秒末组、跳过刷新、null/真实零、缺口及段重建、Retry 保留旧段、部分秒、Stop/新 Start/旋转/旧事件和四小时/数量边界；检查组合快照三部分同一会话、只冻结一次、后续操作不修改旧值。不改已有 HR/区间/步数/距离/极值口径，不持久化原始 ACC/ECG。
7. 运行相关测试、debug 构建及 lint；同步两份 AGENTS.md 和两份 prompt.md 实际结果。提供简单文本真机验收步骤，未执行标待验证，不将内存历史称为重启后保留。

仅完成 6.1d，最小代码、英文 UI/注释；不推进 6.2，不增加依赖、通用框架或无关重构。中英文区分文件修改、自动验证和真机结果。
```

### English

```text
Implement only 6.1d under Section 5.23 of the root AGENTS.md. Verify completion of 5.3–5.5 and 6.1a–6.1c first. Report missing prerequisites without implementing them automatically. Once satisfied, modify files directly:

1. Reuse the 6.1a motion point structure and existing 250 ms result-recording entry point. Keep the last cadence/speed pair and actual elapsedMs per integer session-second bucket. Collect live calculated results across the session rather than replaying the last 60 seconds on Stop. Do not duplicate recording during drawing/recomposition, add timers, rerun detection or backfill skipped refreshes.
2. Keep warm-up/unavailable values null and genuine stationary ACC results zero. Follow ACC gaps strictly over 30 ms, detection-segment resets and subscription interruptions. Preserve breaks during bucket replacement without interpolation/zero filling. Retain current two-second display behavior; never derive summary means/extrema from downsampled display records.
3. Bound motion history to the first four hours and 14,401 records without preallocation, padding or discarding early history. Share the same Running origin/cutoff with HR but do not force matching counts or timestamps. Automatic ending/saving remains in 6.2.
4. Complete motion history Start/Stop/Retry/rotation/stale-event handling. Retain and break pre-Retry history while preserving real-time clearing. Freeze the existing partial-second record on Stop without artificial zeros/endpoints. Reuse 6.1a summary freezing and 6.1c HR lifecycle wiring to assemble one same-session snapshot containing summary, HR and motion history at ending. Do not settle/append twice. New Start and late data must not mutate the previous snapshot.
5. Add only simple English motion point counts, first/last elapsedMs and snapshot status, reusing identity/summary/HR count text. This is an in-memory snapshot: add no Saved state, database, save task, failure retry or History page. Application-level pending-save snapshot management belongs to 6.2.
6. Test last pair per second, skipped refreshes, null/genuine zero, gaps/segment resets, pre-Retry retention, partial seconds, Stop/new Start/rotation/stale events and four-hour/count limits. Verify all snapshot parts belong to the same session, freeze once and cannot be mutated by later operations. Preserve existing HR/zone/step/distance/extrema semantics and persist no raw ACC/ECG.
7. Run relevant tests, a debug build and lint. Update both AGENTS.md and both prompt.md files with actual results. Provide simple-text device verification steps and mark unperformed checks pending; do not describe memory history as surviving restart.

Complete only 6.1d with minimal code and English UI/comments. Do not advance to 6.2, add dependencies/frameworks or refactor unrelated code. Explain file changes, automated verification and device results in Chinese and English.
```

## 步骤 6.2：事务保存与最简单 History / Step 6.2: Transactional saving and minimal History

### 中文

```text
按照根目录 AGENTS.md 第 5.23 节实施步骤 6.2。先检查 6.1a—6.1d 全部完成及 5.3—5.5 依赖，缺失时说明，不自动补做；依赖满足后直接修改文件：

1. 使用 Android SQLiteOpenHelper，在 App 私有目录创建 sessions、hr_points、motion_points 三表，IO 线程读写；会话 UUID 主键，曲线按会话 ID 和秒桶唯一，保留实际 elapsedMs、可空值及断段。保存 5.23 全部摘要，不漏掉平均/最小步频、未归类时长、设备快照和完整性。已有 SharedPreferences 设备记录不迁移，不新增 Room 或通用 Repository 框架。
2. 复用 Start/Stop 和全部中断/流终止路径。有至少一个有效 HR 或真实 ACC/ECG 样本才保存，完全无数据/仅无效 HR 不保存。先结算区间并冻结时长、摘要及整场历史，再用一个 SQLite 事务写入全部三表；成功提交才 Saved，失败回滚，不留部分记录。重复结束不重结算、重复重试不新增会话，也不覆盖累计来掩盖重复事件。
3. 接入四小时 Running 上限，提前显示限制；复用现有刷新/事件入口检查，到达后正常结束、清理并保存，结束原因为 TIME_LIMIT，提示 Session time limit reached。逻辑截止四小时，超出截止不再累计或收集，不将迟到刷新/异步清理时间计入，不补造边界点，也不承诺定时器精确调度。使用受控时钟验证，不要求等待四小时。
4. 显示 Saving… / Saved / Save failed。应用级持有一个冻结待保存快照和任务，不持有 Activity，不因页面离开或 ViewModel 清除主动取消；返回界面可查看及处理。Saving 时禁止重复提交；保存中或失败未处理时禁止新 Start。失败可 Retry save 同一快照/UUID，或明确 Discard session 后丢弃未保存快照并解锁新 Start。不自动重试、不静默丢弃，不添加服务或崩溃恢复；注明进程在成功提交前被杀可能丢失未保存记录。
5. 添加最简单的 SQLite History 测试入口，复用现有 Compose 主题、英文 UI/注释。列表按 Running 开始日期时间倒序，每次 20 条，显示开始时间、时长、总步数、累计估计距离及不完整提示；空列表提示、Load more、按 ID 查看详情和 Back 足够，不加筛选、卡片设计、动画或复杂导航。切换测试视图不销毁活跃会话持有者；真正离开前台/欢迎页继续按既定规则结束。第 7 阶段复用这些查询，第 8 阶段再整合正式布局。
6. 详情从数据库读取整场 HR 和步频曲线、HR min/max/mean、五区间时长和 Running 占比及未归类时间、步频 mean/max/min、Running 时长、总步数、累计估计距离、平均/最大估计速度、设备、结束原因及完整性。基础 Canvas 折线/柱形和文本即可；各曲线使用实际 elapsedMs，仅同段连线，不插值或补零。速度曲线保存但首版无需展示，不做整场 ECG 回放或缩放/拖动。未知显示 --，无有效 HR 提示 No valid HR data；区间注明 Estimated from received HR，未归类不摊入五区间，零分母不算百分比。
7. 历史不自动过期或限条删除。提供单场 Delete 及确认/取消，同一事务删除摘要和两类时序；不清空其他会话。数据只存本机，不上传；根据当前 Manifest/适用 XML 明确排除会话数据库的系统云备份和设备迁移，不能只依赖 allowBackup，不改变已保存设备规则。简单说明卸载/清除数据丢失历史，首版不提供导出。
8. 验证实际 SQLite 事务成功/失败回滚、重复保存/重试、NULL 与真实零、关闭重开读取、会话 ID 对应、20 条分页/排序、删除/取消。验证失败快照跨页面保留、新 Start 限制、重试/丢弃、保存中重复点击和四小时结束；检查备份排除。数据库测试不能只 mock 成功；若实际 SQLite 需模拟器/真机且未运行，要单列未验证，不能宣称全部通过。
9. 运行相关测试、debug 构建和 lint，同步两份 AGENTS.md 和本提示词的实际结果。提供采集→Stop/中断→Saved→重启 History、简单曲线/摘要、缺失、Retry 前后历史、失败恢复及删除的真机步骤；未执行标待验证，性能未测不宣称流畅。6.1a—6.1d/6.2 测试数据只用于测试，不作为真实 H10 会话呈现在正式历史中。

只完成第 6 阶段及上述明确授权的最简单查询/详情/删除测试入口，最小代码实现；不改 SDK、采样配置、检测阈值或正式布局，不增加原始数据持久化、自动校准、云同步、导出、后台采集、Pause/Resume、进程恢复、无关依赖或重构。用中英文区分代码修改、测试/构建、真实 SQLite 结果及真机验收，不能将规划当成完成。
```

### English

```text
Implement step 6.2 according to Section 5.23 of the root AGENTS.md. Inspect completion of all steps 6.1a–6.1d and prerequisites from 5.3–5.5 first. Report missing prerequisites without implementing them automatically. Once they are satisfied, modify the files directly:

1. Use Android SQLiteOpenHelper with three tables in the app-private directory: sessions, hr_points and motion_points. Read/write on IO threads. Use the session UUID as primary key and unique session-ID/second-bucket pairs for curves, retaining actual elapsedMs, nullable values and breaks. Save every summary in 5.23, including mean/min cadence, unclassified time, device snapshot and completeness. Do not migrate existing SharedPreferences device records or add Room/generic Repository frameworks.
2. Reuse Stop and all interruption/stream-ending paths. Save only after at least one valid HR or real ACC/ECG sample; exclude no-data and invalid-HR-only attempts. Settle zones and freeze duration, summary and full-session history before writing all three tables in one SQLite transaction. Show Saved only after successful commit. Roll back failures without partial history. Duplicate endings must not settle twice, retries must not create another session, and overwrites must not hide duplicated accumulation.
3. Wire the four-hour Running limit and show it in advance. Reuse existing refresh/event checks; end, clean up and save normally at the limit with reason TIME_LIMIT and message Session time limit reached. The logical cutoff is four hours: exclude later accumulation/records and delayed refresh/cleanup duration. Do not fabricate boundary points or promise exact timer scheduling. Verify with a controlled clock rather than waiting four hours.
4. Show Saving… / Saved / Save failed. Keep one frozen pending snapshot and save task at application scope without an Activity reference; do not actively cancel them on navigation or ViewModel clearing. Make their status/actions accessible on return. Reject duplicate submissions while saving and block new Start while saving or after an unresolved failure. Retry save uses the same snapshot/UUID; an explicit Discard session removes the unsaved snapshot and unlocks Start. Add no automatic retry, silent discard, service or crash recovery. Explain that process termination before successful commit can lose unsaved data.
5. Add the simplest SQLite History verification entry point using the existing Compose theme and English UI/comments. List by Running start date/time descending, 20 records per load, showing start, duration, total steps, accumulated estimated distance and incomplete status. An empty message, Load more, detail-by-ID and Back are sufficient; add no filtering, designed cards, animation or complex navigation. Switching test views must not destroy the active session holder; actual background/welcome navigation retains existing ending rules. Reuse these queries in stage 7 and leave final layout integration to stage 8.
6. Read detail from SQLite: full-session HR and cadence curves; HR min/max/mean; five zone durations and percentages of Running time plus separate unclassified time; cadence mean/max/min; Running duration; total steps; accumulated estimated distance; mean/max estimated speed; device; end reason; and completeness. Simple Canvas lines/bars and text suffice. Plot actual elapsedMs and connect only within valid segments, without interpolation/zero filling. Save speed history but need not display its curve initially. Add no full-session ECG replay or zoom/pan. Show -- for unknowns, No valid HR data where appropriate and Estimated from received HR for zones. Do not redistribute unclassified time or divide by zero.
7. Retain saved history without automatic expiry or count-based deletion. Offer single-session Delete with confirmation/cancellation, deleting its summary and both series in one transaction without affecting other sessions. Store locally without upload. Inspect the current Manifest/applicable XML and explicitly exclude the session database from cloud backup and device transfer rather than relying only on allowBackup; leave saved-device rules unchanged. Explain that uninstalling/clearing app data loses history. Add no export.
8. Verify actual SQLite commit/rollback, duplicate saves/retries, NULL versus genuine zero, closing/reopening the database, detail identity, 20-record pagination/sorting, deletion/cancellation. Verify pending-state retention across navigation, Start blocking, retry/discard, duplicate save clicks and four-hour ending, and inspect backup exclusions. Do not only mock successful database behavior. If actual SQLite testing requires an emulator/device and was not run, state that separately instead of claiming full verification.
9. Run relevant tests, a debug build and lint, updating both AGENTS.md files and this prompt with actual results. Provide device steps for acquisition → Stop/interruption → Saved → restart History; simple curves/summaries; missing data; pre/post-Retry history; save-failure recovery; and deletion. Mark unperformed checks and unmeasured performance as pending. Test fixtures must remain test-only, not appear as genuine H10 sessions in production History.

Complete only stage 6 and the explicitly authorized minimal list/detail/delete verification entry points using minimal code. Do not change SDK, sampling configuration, detection thresholds or final layout. Add no raw-data persistence, automatic calibration, cloud sync, export, background collection, Pause/Resume, process recovery, unrelated dependencies or refactoring. Explain code changes, test/build results, actual SQLite results and device verification in Chinese and English without presenting plans as completed work.
```

## 本次文档记录 / Documentation record

- 采用：用户明确采用 SQLite 和全部推荐规则，并要求未来实现最小代码、最简单显示；新增 6.1、6.2 中英文待执行提示词及 AGENTS.md 5.23，补齐原待定项。
- Accepted: The user selected SQLite and all proposed rules, with minimal future implementation and simple verification UI. Added bilingual pending prompts for 6.1/6.2 and Section 5.23, replacing relevant undecided entries.
- 修改范围：仅两份 AGENTS.md 和两份 prompt.md；没有实施数据库、统计、History 或任何应用代码。未运行测试、构建或真机检查；文档一致性另行核对，不等于功能验收。
- Scope: Only both AGENTS.md and both prompt.md files. No database, statistics, History or application implementation; no tests, build or device checks. Document consistency checks do not establish functional acceptance.

## 2026-09-30 拆分记录 / Split record

- 用户确认将原 6.1 拆成 6.1a 会话身份与摘要、6.1b 平均与最小步频、6.1c 整场心率历史、6.1d 整场步频/速度历史与完整快照；以上四组中英文提示词替代原整段 6.1，均待执行。先完成 5.3—5.5，再按子步骤顺序推进，每步仅最简单英文测试显示。
- The user approved splitting 6.1 into identity/summary (6.1a), mean/min cadence (6.1b), full-session HR history (6.1c), and motion history/complete snapshot (6.1d). These four bilingual prompts replace the original combined prompt and remain unexecuted. Complete 5.3–5.5 first, then advance one substep at a time with minimal English verification text.
- 6.2 仅更新前置条件引用，功能范围不变：SQLite 保存、失败重试、四小时自动结束及最简单 History 查询。同步两份 AGENTS.md 与两份 prompt.md；没有修改应用代码，也未运行测试、构建或真机验证。
- Only prerequisite references changed for 6.2; SQLite saving, failure retry, automatic four-hour ending and minimal History queries remain its scope. Updated both AGENTS.md and both prompt.md files only, without application changes, tests, builds or device verification.

## 2026-09-30 6.1a 前置检查结果 / Step 6.1a prerequisite check

- 本次用户请求实施 6.1a，并明确要求依赖缺失时停止、不得自动补做。已检查当前源码而非只依据规划：StepState 仍仅有总步数、当前步频与状态；管理器没有距离/速度及合格窗口最大步频、HR 五区间/未归类计时、5.5 曲线输出；页面仍为开发文本显示。
- The user requested 6.1a and explicitly required stopping if prerequisites were missing. Current source was inspected, not only the plan: StepState still contains only total steps, current cadence and status; the manager has no distance/speed or qualifying-window maximum cadence, HR zone/unclassified timing, or step 5.5 chart outputs; the screen remains a development text display.
- 结果：5.3 距离/速度/极值、5.4 心率区间和 5.5 曲线/时间轴尚未实施，因此没有开始 6.1a，也没有自动补做其他步骤。下一依赖顺序为 5.3 → 5.4 → 5.5，之后重新检查 6.1a。
- Result: Steps 5.3 (distance/speed/extrema), 5.4 (HR zones) and 5.5 (curves/time axes) are missing. Step 6.1a was not implemented and no other step was implemented automatically. Complete 5.3 → 5.4 → 5.5 before rechecking 6.1a.
- 文件：仅更新两份 AGENTS.md 与两份 prompt.md 的前置检查记录。未修改应用代码，未执行单元测试、debug 构建、lint、安装或真机验收；未创建 Git commit。建议的文档提交信息为 docs: record missing prerequisites for step 6.1a，不得写成 6.1a 功能已实现。
- Files: Updated only both AGENTS.md and both prompt.md files with this prerequisite check. No application changes, unit tests, debug build, lint, installation or device verification; no Git commit was created. Suggested documentation commit message: docs: record missing prerequisites for step 6.1a. Do not describe this as a completed 6.1a feature.

# 第 5 阶段补充实施入口 / Stage 5 supplemental implementation prompts

2026-09-30：针对 6.1a 前置检查发现的缺失，新增 5.3add、5.4add、5.5add，分别对应原开发步骤 5.3、5.4、5.5。这些是完成既定功能的最新版实施入口，不是要求原步骤先完成的额外阶段；原功能尚未实现时，在对应 add 中按下列完整规则实现。已有部分实现时先检查，仅补缺失或修正不符合规则的部分，不能建立第二套重复路径。
2026-09-30: Added 5.3add, 5.4add and 5.5add to address missing prerequisites identified for 6.1a. They are the latest implementation entry points for original development steps 5.3, 5.4 and 5.5, not extra stages requiring those same steps to be implemented first. Implement the specified original functionality where absent; inspect and complete/correct existing portions without creating duplicate pipelines.

执行顺序：5.3add → 5.4add → 5.5add → 重新检查 6.1a。每次只执行用户指定的一项；前置步骤缺失时报告并停止，不自动连做。初次记录仅保存提示词；2026-09-30 按后续分别授权已完成三项 add 的代码与各自自动检查，真机待验证。6.1a 尚未实施，执行前须重新检查当前前置输出。原 5.3—5.5 提示词保留作为历史记录，后续使用对应 add。
Execution order: 5.3add → 5.4add → 5.5add → recheck 6.1a. Execute only the requested item per turn; report missing preceding prerequisites and stop without automatically implementing them. The initial update recorded prompts only. Separately authorized requests on 2026-09-30 have now completed code and fresh automated checks for all three add steps; device checks remain pending. Step 6.1a is unimplemented and requires a fresh prerequisite check. Keep the original prompts as historical records and use the corresponding add entry going forward.

# 步骤 5.3add：步长、距离、速度及最大值补全 / Complete stride, distance, speed and maxima

2026-09-30：本提示词及用户随后提供的完整 5.3add 指令已执行；完整规则见 AGENTS.md 第 5.20 节及 5.24。代码与本轮自动检查已完成，真机待验证，实际结果见本节末尾。
2026-09-30: This prompt and the user's subsequent full 5.3add instructions have been executed. Full rules are in AGENTS.md Sections 5.20 and 5.24. Code and this run's automated checks are complete; device verification remains pending. Actual results follow below.

## 中文

```text
补充要求（5.3add）：本次用于补齐 6.1a 所需的原 5.3 输出；不要求尚未实现的原 5.3 自身先完成。先检查 5.2a—5.2d 及当前会话路径，已实现部分复用，缺失部分按下列规则实现。相邻接受候选峰的峰间统计必须包含两端并按原峰值时间截取，不能把回落确认后的数据混入；小于 0.25 秒而被拒绝的峰不移动已接受峰的参考起点。保留 0.25—2 秒间隔、100 Hz、A_min 和四步确认规则，使用有界缓存/累计量。以当前统计持有者的最小只读结果提供累计距离、当前/平均/最大速度、最大步频、ACC 观测及不完整状态，供本页和后续摘要复用；不新增 SessionRecord、数据库结构或为未来建立框架。平均/最小步频明确留到 6.1b，不在本步实现。

按照根目录 AGENTS.md 第 5.20 节实施补充步骤 5.3add（对应原开发步骤 5.3）。先检查现有代码及 5.2a—5.2d 的步伐输出；依赖未完成时说明缺口，不自动实现其他步骤。依赖满足后直接修改文件：

1. 沿用 note/551a40924.docx 的步频、Weinberg 步长公式及 AGENTS.md 后续确认规则。按同段相邻接受候选峰之间的平滑最大/最小值计算 Li = 0.5 × (smax − smin)^0.25。连续四步确认前缓存，通过后仅提交一次；每段首峰没有步长，四步补计只有三段距离，不补造起始距离。
2. 复用 5.4 的步频，速度使用最近五秒区间内已确认步长之和除以窗口秒数；不足五秒时用本段预热结束后的实际时长，禁止零分母。每段步长按后一个峰的原始时间归窗，不按补计时间归窗。复用每 250 ms 及新步伐刷新，不重复累计；正常初始预热/等待确认显示 0 和状态，ACC 正常可用且两秒无确认步伐时当前步频/速度归零。
3. 平均速度为本场累计估计距离除以完整 Running 时长，包含静止和休息，不只统计移动时间、不对显示值求算术平均。Starting 或零时长显示 --，Stop 后冻结。仅保留 Start/Stop，不添加 Pause/Resume 或静止自动暂停。
4. 最大步频和最大速度分别取整场完整、连续、无已知缺口的五秒窗口结果的最大值。从本连续段预热结束的传感器时间起算，真实 ACC 样本时间覆盖满五秒才有首个合格窗口，缺口或检测段重建后重新起算；端点使用实际处理到的样本时间，不以手机显示时钟补足窗口。短窗口只用于当前显示，不参与最大值；首次合格窗口前显示 --。最大值使用原始五秒统计（确认步数 × 12、确认步长之和 / 5），两秒无步归零只影响当前显示。使用未舍入值比较，停止或缺口不清除已有最大值。
5. ACC 相邻时间差严格大于 30 ms 时复用分段，清空检测及当前步频/速度窗口，重新预热；不跨段计算步长、不插值或补算，保留本场累计步数、距离和最大值。ACC 不可用、失败或缺口后预热时显示 -- 及原因；接受 Retry 后重新预热和确认连续步伐，其他流与会话计时继续。沿用正常批次等待规则，不新增停流超时或自动重试。
6. 本场发生已识别 ACC 缺口、不可用或失败时，保留 Incomplete ACC data 标记至会话结束；正常初始算法预热不是数据故障。仍可显示已有距离及其除以完整会话时长得到的平均速度，说明可能因遗漏距离而偏低；最大值仅代表记录完整的窗口。完全无 ACC 观测时统计显示 --，不把缺失当作静止。恢复不清除本场缺失标记。
7. 在现有 SensorActivity.kt 用简单英文文本显示当前步频、估计速度、平均速度、最大步频、最大速度及必要距离/状态信息。步频显示整数 steps/min；速度内部以 m/s 计算、乘 3.6 后显示一位小数 km/h，仅显示时舍入。Stop/整体中断后标明 Stopped：本场曾收到正常 ACC 样本时当前值归零，整场没有 ACC 样本时当前值和相关统计保持 --；停止不更新最大值，累计统计冻结。接受新 Start 清空本场统计和缺失标记，重复操作不清空或累加，旋转保留并拒绝旧会话事件。
8. 添加针对性受控测试，覆盖峰间步长、四步/三段补计、原时间归窗、窗口边界、短窗口、两秒归零、完整窗口最大值、静止计入平均、缺口及 Retry、新 Start、Stop 冻结和旧事件。检查真实覆盖 4.99 秒时手机时间不能补足最大值窗口、覆盖五秒后才参与，当前归零不改变原始窗口统计，以及整场没有 ACC 时停止仍显示 --。检查 100 米、运动 80 秒及静止 20 秒得到平均 3.6 km/h。运行相关测试、debug 构建和 lint，更新两份 AGENTS.md 的实际结果；提供已知距离、静止/走路/跑步、休息、缺失恢复和生命周期的真机检查，未执行标记待验证。

使用最小代码、有界窗口、现有 Compose 主题及英文 UI/注释，复用现有会话和数据路径。仅完成 5.3add 对应的原步骤 5.3，保留 HR/ACC/ECG 行为；不实现正式布局、图表、心率区间、历史、持久化、自动校准、通用统计框架或无关重构，不增加依赖。
用中英文分别说明文件修改、测试/构建结果及真机验证，不将已确认规划或以前的构建结果当作本步已完成。
补充交付：本步实现后，按新运行的结果同步两份 AGENTS.md 与两份 prompt.md，保持各自副本一致；分别列出代码修改、测试/debug 构建/lint、真机待验证项，并给出与实际改动匹配的英文 commit message，不自动创建提交或推送。不得因本步完成就宣称其他 add 或 6.1a 已通过；本步未执行则维持待实施。
```

## English

```text
Supplement (5.3add): Complete the original step 5.3 outputs required by 6.1a; do not require original 5.3 itself to be implemented already. Inspect 5.2a–5.2d and current session paths, reuse existing portions and implement missing behavior below. Peak-to-peak extrema must include both accepted peaks and be cut at their original timestamps, excluding data after the later peak during delayed fallback confirmation. A peak rejected for an interval below 0.25 seconds must not move the accepted reference peak. Preserve the 0.25–2-second interval, 100 Hz, A_min and four-step rules, with bounded storage/accumulators. Expose minimal read-only results from the existing statistics owner: distance, current/mean/max speed, maximum cadence, ACC observation and incomplete status for the screen and later summary. Add no SessionRecord, database structure or future framework. Leave mean/min cadence to 6.1b.

Implement supplemental step 5.3add (original development step 5.3) according to Section 5.20 of the root AGENTS.md. Inspect the existing code and step outputs from 5.2a–5.2d first. If prerequisites are missing, report them without implementing other steps. Once prerequisites are satisfied, modify the files directly:

1. Reuse the cadence and Weinberg stride-length formulas in note/551a40924.docx and the subsequently confirmed AGENTS.md rules. Calculate Li = 0.5 × (smax − smin)^0.25 from smoothed values between consecutive accepted candidate peaks in the same segment. Cache until four-step confirmation and submit each length once. The first peak has no length; four confirmed steps provide only three distance intervals. Do not fabricate initial distance.
2. Reuse cadence from Section 5.4. Calculate speed as the sum of confirmed lengths in the latest five-second interval divided by its duration. For a shorter segment, use actual time since warm-up ended; never divide by zero. Assign each length to its later peak's original timestamp, not the confirmation time. Reuse updates on new steps and every 250 ms without accumulating distance again. Show 0 with a status during normal initial warm-up/confirmation; with available ACC, set current cadence and speed to zero after two seconds without a confirmed step.
3. Calculate average speed as recorded session distance divided by the full Running duration, including stationary rest. Do not use moving time alone or average UI refresh values. Show -- during Starting or with zero duration, and freeze on Stop. Keep Start/Stop only; add neither Pause/Resume nor automatic pausing while stationary.
4. Track session maximum cadence and speed separately using only complete, continuous five-second windows without known gaps. Measure from the current segment's warm-up completion timestamp; actual ACC sample timestamps must cover five seconds before the first eligible window. Restart this coverage after a gap or segment reset. Use the timestamp of the sample being processed as the endpoint; the phone display clock must not fill missing coverage. Short windows may update current values but not maxima. Show -- before the first eligible window. Use raw five-second statistics (confirmed steps × 12 and confirmed lengths summed / 5) for maxima; two-second zeroing affects current display only. Compare unrounded values and retain existing maxima across stopping or gaps.
5. Reuse segmentation when consecutive ACC timestamps differ by strictly more than 30 ms. Clear detection and current cadence/speed windows and warm up again. Do not calculate lengths across gaps, interpolate or fabricate data; retain session steps, distance and maxima. Show -- and the reason while ACC is unavailable, failed or warming up after a gap. After an accepted Retry, repeat warm-up and consecutive-step confirmation while other streams and session timing continue. Preserve ordinary batch-waiting behavior without adding a no-data timeout or automatic retry.
6. Retain Incomplete ACC data for the rest of a session after a known ACC gap, unavailability or failure. Normal initial algorithm warm-up is not a data fault. Existing distance and its average over full session time may remain visible, with an explanation that missing distance can lower the result; maxima describe only eligible recorded windows. Show -- for statistics when no ACC observations exist, and do not interpret missing data as stationary activity. Recovery must not clear the session's incomplete-data flag.
7. Use simple English text in the existing SensorActivity.kt to show current cadence, estimated speed, average speed, maximum cadence/speed and necessary distance/status information. Display integer steps/min and one decimal place for km/h, converting internal m/s by multiplying by 3.6 and rounding only for display. On Stop or overall interruption, show Stopped: set current values to zero if normal ACC samples were received in this session, but retain -- for current values and related statistics if none were received. Do not update maxima on stopping; freeze aggregates. Clear session statistics and the incomplete-data flag on an accepted new Start. Duplicate actions must not clear or accumulate data; preserve state across rotation and reject old-session events.
8. Add focused controlled tests for peak-interval lengths, four-step/three-interval backfill, original timestamps, window boundaries, short windows, two-second zeroing, complete-window maxima, rest included in averages, gaps, Retry, new Start, Stop freezing and stale events. Verify that phone time cannot complete a maximum window with only 4.99 seconds of actual coverage, five seconds permits evaluation, current-display zeroing does not change raw window statistics, and stopping a session without ACC retains --. Verify that 100 metres over 80 seconds of movement plus 20 seconds of rest gives 3.6 km/h average speed. Run relevant tests, a debug build and lint, and update both AGENTS.md files with actual results. Provide device checks for known distances, stationary/walking/running activity, rest, recovery and lifecycle behavior; mark unperformed checks as pending.

Use minimal code, bounded windows, the existing Compose theme and English UI text/comments. Reuse current session and data paths. Complete only 5.3add (original step 5.3) and preserve HR/ACC/ECG behavior. Do not add the final layout, charts, HR zones, history, persistence, automatic calibration, a generic statistics framework, unrelated refactoring or dependencies.
Explain in Chinese and English, separating file changes, test/build results and device verification. Do not present confirmed plans or earlier builds as completion of this step.
Additional delivery: After implementation, synchronize both AGENTS.md and both prompt.md files using fresh results and keep each pair identical. Separately report code changes, tests/debug build/lint and pending device checks. Provide an English commit message matching actual changes, without automatically committing or pushing. Completing this item does not establish completion of other add items or 6.1a; keep unexecuted work pending.
```

## 5.3add 实际结果 / Actual results — 2026-09-30

- 前置检查：5.2a—5.2d 的逐样本预处理、候选峰、四步确认、步频窗口以及当前会话/订阅路径均已存在，复用实现。此前 6.1a 检查缺少的 5.3 功能由本步补齐；5.4、5.5 及第 6 阶段没有自动实施。
  Prerequisites: Existing per-sample preprocessing, candidate detection, four-step confirmation, cadence windows and session/subscription control from 5.2a–5.2d were inspected and reused. This step completes the missing 5.3 functionality identified during the earlier 6.1a check. It does not implement 5.4, 5.5 or stage 6.
- 文件与算法：新增 StrideLengthEstimator.kt，以最近两秒/最多 201 个平滑点截取相邻接受峰间区段，包含两端，排除后峰确认回落后的点。StepSequence 只在接受峰后取步长，随原待确认列表缓存；首次四步只提交三段距离。CadenceWindow 复用原峰时间存储可空步长，提供五秒/短窗口速度及不受显示归零影响的原始五秒统计。
  Files and algorithm: Added StrideLengthEstimator.kt with a two-second/201-point smoothed buffer, inclusive accepted-peak endpoints and exclusion of samples after the later peak. StepSequence calculates lengths only for admitted peaks and uses its existing pending list; the first four steps commit exactly three lengths. CadenceWindow carries nullable lengths at original peak times and exposes full/short-window speed plus raw five-second statistics independent of display zeroing.
- 统计与接线：StepDetector/StepState 增加距离、速度、最大值、ACC 观测和不完整状态；平均速度读取完整 Running 毫秒，100 米 / 100 秒 = 3.6 km/h。PolarBleManager 复用原 250 ms 刷新及会话结束结算，Stop、整体中断和全部流结束后冻结，不含清理耗时；缺口/Retry 只清窗口并保留累计。SensorActivity 增加简单英文距离、当前/平均/最大速度、最大步频和缺失提示。内部保留未舍入的米、m/s，速度 UI 为一位小数 km/h。
  Statistics and integration: StepDetector/StepState expose distance, speed, maxima, ACC observations and incomplete status. Average speed uses full Running milliseconds: 100 metres / 100 seconds = 3.6 km/h. PolarBleManager reuses the existing 250 ms refresh and settled session duration; Stop, overall interruption and termination of all streams freeze results before cleanup time. Gaps/Retry reset windows while retaining aggregates. SensorActivity adds simple English verification text; internal metres and m/s stay unrounded, with one decimal place for displayed km/h.
- 本轮验证：新增 StrideLengthEstimatorTest 4 项、MotionStatisticsTest 9 项，StepDetectorTest 新增 1 项并扩展原生命周期测试；执行 `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，BUILD SUCCESSFUL。本轮 XML 共 119 项测试，0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。包含真实 4.99 秒不能由手机外推补满、5 秒可比较最大值、两秒显示零与原始窗口分离、无 ACC 停止仍空、重复操作/旧事件隔离及清理耗时排除。
  Verification in this run: Added four StrideLengthEstimatorTest cases, nine MotionStatisticsTest cases and one StepDetectorTest case, and extended existing lifecycle tests. Ran `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`: BUILD SUCCESSFUL. Current XML reports contain 119 tests, zero failures/errors/skips; lint reports zero errors and 18 warnings. Coverage includes real 4.99 versus 5-second windows, no phone-time completion of maxima, display-zero/raw-window separation, no-ACC placeholders after Stop, duplicate/stale-event isolation and exclusion of cleanup time.
- 真机与局限：未安装 APK、未操作手机。静止/走路/跑步、已知距离误差、运动后静止 20 秒、缺口/Retry、Stop/新 Start、旋转、断线/锁屏/后台检查均待完成，具体步骤见 AGENTS.md 第 9 节。K = 0.5 未校准；每段首峰无距离，缺失不补算，可能低估距离与平均速度；构建与受控测试不证明真实准确率或设备生命周期通过。
  Device checks and limitations: No APK was installed and no phone was operated. Stationary/walking/running activity, known-distance error, 20 seconds of rest, gaps/Retry, Stop/new Start, rotation, disconnection, lock and background behavior remain pending; procedures are in AGENTS.md Section 9. K = 0.5 remains uncalibrated. Each segment's first peak contributes no length and missing distance is not estimated, so distance and average speed may be low. Automated checks do not establish real accuracy or device lifecycle acceptance.
- 记录与范围：两份 AGENTS.md、两份 prompt.md 分别保持相同；仅完成 5.3add，未新增依赖、通用框架、平均/最小步频、心率区间、曲线、History 或持久化。没有自动 commit 或 push。
  Records and scope: Both AGENTS.md copies and both prompt.md copies are synchronized. Only 5.3add is completed; no dependencies, generic framework, mean/min cadence, HR zones, curves, History or persistence were added. No commit or push was performed.

英文提交信息 / English commit message:

```text
feat: implement step 5.3add motion distance and speed statistics

- Estimate stride lengths between accepted peaks and commit confirmed distance
- Add current and average speed, full-window maxima, and ACC missing-data flags
- Preserve aggregates across Retry and freeze statistics when sessions end
- Add English verification text and controlled motion/lifecycle tests
- Synchronize AGENTS and bilingual prompt records with validation results
```

# 步骤 5.4add：心率强度、区间时长及未归类时间补全 / Complete HR intensity, zone durations and unclassified time

2026-09-30：用户要求“给我 5.4 的 prompt 和实现”，已按本 5.4add 入口完成原步骤 5.4。完整规则见 AGENTS.md 第 5.21 节及 5.24；代码与本轮自动检查完成，真机待验证，实际结果见本节末尾。
2026-09-30: The user requested the prompt and implementation for step 5.4. Original step 5.4 has been implemented through this 5.4add entry point. Full rules are in AGENTS.md Sections 5.21 and 5.24. Code and fresh automated checks are complete; device verification remains pending. Actual results follow below.

## 中文

```text
补充要求（5.4add）：本次完成原 5.4，先检查 5.3add 对应的 5.3 已完成，并检查 5.1 有效 HR、会话计时与真实数据路径；不要求原 5.4 自身先完成。复用已有有效性/统计，缺失区间功能按下列规则实现，部分已有则最小补齐。统一英文 Heart rate intensity，提供五区间累计毫秒、同一时刻未归类毫秒、当前可空区间和有效 HR 观测状态的最小只读结果，不让 UI 重算或改变累计。整体结束先按同一结束时刻结算，再清当前读数/订阅，防止最后一段时长丢失；重复结束不得重复提交。后续 6.1a 可读取已冻结结果，但本步不新增会话记录结构或数据库。

按照根目录 AGENTS.md 第 5.21 节实施补充步骤 5.4add（对应原开发步骤 5.4）。先检查现有代码及 5.1 的有效 HR 处理和会话计时；依赖未满足时说明缺口，不自动实施其他步骤。依赖满足后直接修改文件：

1. 复用有效条件 hr > 0 && (!contactStatusSupported || contactStatus)。固定区间为 <110、[110,125)、[125,140)、[140,155)、≥155 bpm，标签为 Very light、Light、Moderate、High、Very high，颜色依次为蓝、绿、黄、橙、红。无效 HR 不归区，不增加年龄、最大心率、平滑或滞回设置。
2. 当前标签和之后的计时区间由最新非空批次的最后一个样本决定。末样本无效时显示 Heart rate intensity: -- 并保留对应原因；同批更早的有效值仍参与 5.1 统计，但不能替代批末值作为当前区间。空批次不改变状态；相同 HR 再次收到时正常结算，不按值去重。
3. 采用最近有效读数保持法，以每批一次 SystemClock.elapsedRealtime 接收时刻计算区间时长。先结算旧区间到事件时刻，再切换区间和起点。同批样本不分摊时长，不按样本数量计时，不使用系统日期、RR 或伪造的 HR 传感器时间；保持既有 receivedAt 的含义。仅统计 Running，HR 区间起点不得早于会话起点；ACC/ECG 先启动时，首个有效批末 HR 前的时间未归类，不回填。
4. 无效 HR、无接触、HR 失败、正常结束或接受 Retry 时，结算旧区间并清除当前区间/起点，保留已有累计值。新有效批末 HR 到达后再继续，不补算中间空白，不停止其他流或会话时间。拒绝的重复操作不重复结算。静止但 HR 有效时照常计时；无新批次且无无效/结束通知时继续保持最后区间，不增加超时或自动重试，并用 Estimated from received HR 说明静默停流可能使旧区间时间偏多。
5. 仅维护五区间累计毫秒数、当前可空区间和起点，复用会话轮次与现有 250 ms 刷新。UI 刷新展示已累计值加当前未结算时长，不写回累计值、不固定加 250 ms、不新增定时器。Unclassified time 为同一时刻 Running 时长减五区间显示时长之和，单列文字，不增加第六根柱形或强行补齐五区间。
6. 在现有 Compose 页面添加简单心率强度标签及五根时长柱形；英文标题统一为 Heart rate intensity，仅表示 HR 分档，不表示已确认正在运动。横轴 Zone 1—5 并注明 bpm 范围，纵轴为时长，五柱使用相同且随最大累计时长统一调整的比例尺；显示 mm:ss，内部保留毫秒，仅显示时取整秒。未进入区间为 00:00；完全没有有效 HR 时附 No valid HR data。颜色和文字共同说明区间，不只靠颜色，不增加图表库；正式布局留到第 8 阶段。
7. Stop 或整体中断时结算至会话停止时刻，冻结柱形和未归类时间，不包含清理耗时；当前心率强度显示 -- 和停止状态。接受新 Start 才清零全部区间状态，HR Retry 保留累计，旋转保留；拒绝重复操作和旧事件，不自动恢复旧会话。沿用后台、锁屏、返回欢迎页和断线结束会话的规则。
8. 添加受控测试，覆盖 109/110、124/125、139/140、154/155 的边界，无效和混合批次、末样本无效、空批次、相同 HR、先由其他流启动、切区与结束/Retry 结算、静止、无新批次保持、刷新不重复计时、系统日期变化、未归类时间、Stop 冻结、新 Start 和旧事件。验证第 10 秒收到 120、第 13 秒收到 130 时，3 秒归 Zone 2。运行相关测试、debug 构建和 lint，更新两份 AGENTS.md 实际结果；提供真实 HR 标签、柱形、时长、静止、可复现的接触变化、重试、旋转与断线检查，未执行标记待验证。不能自然触发的心率边界使用受控测试。

仅完成 5.4add 对应的原步骤 5.4，使用最小代码、现有 Compose 主题及英文 UI/注释。保留既有 HR/ACC/ECG、步伐和速度行为，不增加 HR 历史缓存、个体化训练算法、警报、通用框架、Pause/Resume、曲线、持久化、History、依赖或无关重构。
中英文说明，分别列出文件修改、测试/构建结果和真机验证，不把规划或旧测试结果作为本步完成证据。
补充交付：本步实现后，按新运行的结果同步两份 AGENTS.md 与两份 prompt.md，保持各自副本一致；分别列出代码修改、测试/debug 构建/lint、真机待验证项，并给出与实际改动匹配的英文 commit message，不自动创建提交或推送。不得因本步完成就宣称其他 add 或 6.1a 已通过；本步未执行则维持待实施。
```

## English

```text
Supplement (5.4add): Complete original step 5.4. First verify completion of the step 5.3 functionality addressed by 5.3add, plus step 5.1 valid HR, session timing and real data paths; do not require original 5.4 itself to exist already. Reuse HR validity/statistics and minimally complete the behavior below. Use Heart rate intensity consistently. Expose minimal read-only five-zone millisecond totals, same-instant unclassified time, nullable current zone and valid-HR observation state without UI-driven accumulation. On overall ending, settle to the same stop instant before clearing current readings/subscriptions so the final interval is retained; repeated endings must not settle twice. Later 6.1a may read the frozen result, but do not add session-record structures or a database here.

Implement supplemental step 5.4add (original development step 5.4) according to Section 5.21 of the root AGENTS.md. Inspect the existing code, step 5.1 HR validation and session timing first. If prerequisites are missing, report them without implementing other steps. Once prerequisites are satisfied, modify the files directly:

1. Reuse hr > 0 && (!contactStatusSupported || contactStatus). Use fixed zones <110, [110,125), [125,140), [140,155) and >=155 bpm, labelled Very light, Light, Moderate, High and Very high, with blue, green, yellow, orange and red respectively. Invalid HR belongs to no zone. Do not add age, maximum-HR, smoothing or hysteresis settings.
2. Use the final sample of the latest nonempty batch to determine the current label and subsequent timing zone. An invalid final sample shows Heart rate intensity: -- with the appropriate reason. Earlier valid samples still contribute to step 5.1 statistics but must not replace the final sample for the current zone. Empty batches do not change state. Settle repeated HR values normally without deduplicating by value.
3. Hold the latest valid reading and calculate zone duration using one SystemClock.elapsedRealtime reception timestamp per batch. Settle the old zone to the event time before changing the zone and timing anchor. Do not divide batch time among samples, count samples as time, or use wall-clock dates, RR intervals or invented HR sensor timestamps. Preserve receivedAt semantics. Count only Running time; zone timing must not precede the session start. If ACC/ECG starts the session first, time before the first valid final HR sample is unclassified and must not be backfilled.
4. On invalid HR, lost contact, HR failure, normal completion or an accepted Retry, settle the old zone and clear the current zone/anchor while retaining accumulated durations. Resume only from a new valid final HR sample without filling the interruption or stopping other streams/session timing. Rejected duplicate actions must not settle twice. Continue counting while stationary with valid HR. With no new batch and no invalid/end notification, keep estimating the last zone without adding a timeout or automatic retry. Show Estimated from received HR and explain that silent stream loss can overestimate the last zone's duration.
5. Keep only five accumulated millisecond durations, the nullable current zone and its timing anchor, reusing session-generation protection and the existing 250 ms refresh. Refreshes display accumulated values plus the current unsettled interval without writing back, adding a fixed 250 ms or creating another timer. Calculate Unclassified time as Running duration minus the sum of the five displayed zone durations at the same instant. Show it separately as text, not a sixth bar, and do not force the five zones to cover the session.
6. Add a simple heart rate intensity label and five duration bars to the existing Compose screen. Use the title Heart rate intensity to describe the HR zone, without implying detected movement. Use Zone 1–5 and bpm ranges on the horizontal axis and duration on the vertical axis. All bars share one scale adjusted to the largest accumulated duration. Display mm:ss while retaining milliseconds internally and truncating only display seconds. Unvisited zones show 00:00; show No valid HR data when none exists. Use text as well as colour. Add no chart library; leave final layout integration to stage 8.
7. On Stop or overall interruption, settle to the session stop time and freeze bars and unclassified time, excluding cleanup duration. Show -- with the stopped status for current heart rate intensity. Clear all zone state only on an accepted new Start; retain accumulations across HR Retry and rotation. Reject duplicate actions and stale events, and do not resume old sessions. Preserve existing background, lock, welcome-screen and disconnection behavior.
8. Add controlled tests for boundaries 109/110, 124/125, 139/140 and 154/155; invalid/mixed batches, invalid final samples, empty batches, repeated HR, another stream starting first, zone changes, completion/Retry settlement, stationary time, holding without new batches, refreshes without double counting, wall-clock changes, unclassified time, Stop freezing, new Start and stale events. Verify that HR 120 at second 10 and HR 130 at second 13 assign three seconds to Zone 2. Run relevant tests, a debug build and lint, and update both AGENTS.md files with actual results. Provide device checks for real HR labels, bars, durations, stationary activity, reproducible contact changes, retry, rotation and disconnection; mark unperformed checks as pending. Use controlled tests for HR boundaries that cannot naturally be reproduced.

Complete only 5.4add (original step 5.4) with minimal code, the existing Compose theme and English UI text/comments. Preserve HR/ACC/ECG, step and speed behavior. Do not add HR history buffers, personalized training algorithms, alerts, generic frameworks, Pause/Resume, curves, persistence, History, dependencies or unrelated refactoring.
Explain in Chinese and English, separating file changes, test/build results and device verification. Do not present plans or older test results as evidence of this step's completion.
Additional delivery: After implementation, synchronize both AGENTS.md and both prompt.md files using fresh results and keep each pair identical. Separately report code changes, tests/debug build/lint and pending device checks. Provide an English commit message matching actual changes, without automatically committing or pushing. Completing this item does not establish completion of other add items or 6.1a; keep unexecuted work pending.
```

## 5.4add 实际结果 / Actual results — 2026-09-30

- 前置检查：已检查当前 5.3add、5.1 HR 有效性和整场统计、SessionController 及真实 HR/ACC/ECG 数据接线，代码前置齐全。只执行 5.4，未推进 5.5 或第 6 阶段。
  Prerequisites: Inspected current 5.3add, step 5.1 HR validity/statistics, SessionController and actual HR/ACC/ECG paths. Code prerequisites were present. Only step 5.4 was implemented; step 5.5 and stage 6 remain pending.
- 新增 HeartRateZones.kt：复用 LatestHeartRate 的验证后批末 reading 和本批有效样本标记，固定五区间。只保存五个累计毫秒、当前可空区间、锚点与观测标记，提供只读快照。事件先结算后切区；空批在原订阅入口过滤；250 ms 刷新仅读“累计 + 未结算”，未归类用同一时刻 Running 减去五区间合计，不按样本数/刷新次数计时，不新增定时器或历史缓存。
  Added HeartRateZones.kt: Reuses the validated final reading and valid-sample flag from LatestHeartRate for five fixed zones. It retains only five accumulated millisecond totals, a nullable current zone, its anchor and an observation flag, exposing read-only snapshots. Events settle before changing zones. Existing subscriptions filter empty batches. The 250 ms refresh displays settled plus unsettled time without committing; unclassified time is the same-instant Running duration minus all five zones. No sample/tick counting, new timer or history buffer was added.
- 修改 PolarBleManager.kt、SessionState.kt：每个非空 HR 批次捕获一次计时用 elapsedRealtime，原 receivedAt 仍为日期；把同一捕获时刻用于首次 Running 和刷新，最后流结束也共享事件时刻。无效/HR 中断/Retry 先结算并清当前区间，累计保留；整体结束在清理读数前结算并冻结，清理耗时排除。新 Start 才重置，重复与旧事件沿用会话/订阅保护。ACC、ECG、步伐和速度算法未修改。
  Updated PolarBleManager.kt and SessionState.kt: Captures one timing reception instant per nonempty HR batch using elapsedRealtime while preserving wall-clock receivedAt. The same captured instant sets the initial Running origin and refresh time; final-stream settlement shares its event time with session ending. Invalid HR, stream interruption and Retry settle and clear the current zone while retaining totals. Overall ending settles before clearing readings and freezes without cleanup time. Only accepted new Start resets totals; existing guards reject duplicates and stale events. ACC, ECG, step and speed algorithms are unchanged.
- 新增 HeartRateZonePanel.kt、修改 SensorActivity.kt：显示 Heart rate intensity、五色同尺度时长柱、Zone 1—5/bpm 范围、mm:ss、单列 Unclassified time 和无有效 HR/停止状态。颜色之外保留文字，内部毫秒不舍入；显示 Estimated from received HR，并解释静默停流可能高估最后区间。复用 Compose 主题，无图表依赖，正式布局待第 8 阶段。
  Added HeartRateZonePanel.kt and updated SensorActivity.kt: Displays Heart rate intensity, five coloured duration bars sharing one scale, Zone 1–5 with bpm ranges, mm:ss, separate Unclassified time, and no-valid-HR/stopped states. Text accompanies colour and milliseconds remain unrounded. The screen states Estimated from received HR and explains possible overestimation during silent stream loss. Existing Compose theming is reused without a chart dependency; final layout remains stage 8 work.
- 自动验证：新增 HeartRateZonesTest.kt 13 项。最终执行 `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，BUILD SUCCESSFUL；本轮 XML 为 132 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。覆盖所有档位边界、混合/无效批末/接触/空批/重复值、ACC 先启动、保持估计、刷新不重复、日期变化、未归类、Retry/完成/失败、Stop/整体中断/最后流冻结和清理耗时、新 Start/旧会话与旧订阅事件。10 秒 120、13 秒 130 的示例确认 Zone 2 = 3000 ms。首次测试有两项测试数据接触参数顺序错误，改用命名 copy 字段后全量重跑通过，没有放宽生产有效性规则。
  Automated verification: Added 13 tests in HeartRateZonesTest.kt. Final run of `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline` was BUILD SUCCESSFUL. Current XML reports show 132 tests with zero failures/errors/skips; lint has zero errors and 18 warnings. Tests cover all boundaries, mixed/invalid final samples, contact, empty/repeated batches, ACC-first timing, holding, refreshes, wall-clock changes, unclassified time, Retry/completion/failure, end freezing without cleanup time, new Start and stale session/subscription events. HR 120 at second 10 followed by 130 at second 13 gives Zone 2 exactly 3000 ms. Two initial test fixtures reversed contact parameters; named copy fields corrected the fixtures and all tests then passed, without relaxing production validity rules.
- 真机待验证：本轮未安装 APK、未操作手机。真实 HR 标签与五柱比例/文字、静止累计、设备支持时的接触变化、HR Retry、Stop/新 Start、旋转、断线和锁屏/后台均待检查；步骤见 AGENTS.md 第 9 节。旋转保留只检查了现有 ViewModel/onStop 代码，没有实际设备旋转结果；难以自然产生的边界用受控测试，不要求人为达到高心率。
  Device verification pending: No APK was installed and no phone was operated. Real HR labels, bar scale/text, accumulation while stationary, supported contact changes, HR Retry, Stop/new Start, rotation, disconnection, lock and background behavior remain pending; procedures are in AGENTS.md Section 9. Rotation retention was inspected in existing ViewModel/onStop code but not tested on a device. Controlled tests cover difficult-to-produce HR boundaries without requiring deliberate high heart rates.
- 范围与记录：两份 AGENTS.md 和两份 prompt.md 分别同步；未新增依赖、个体化训练算法、曲线、History 或持久化。心率强度只表示固定 HR 档位，不是运动分类；无通知的静默停流仍会继续旧档位估计。本轮未创建提交或推送。
  Scope and records: Both AGENTS.md copies and both prompt.md copies are synchronized. No dependencies, personalized training algorithm, curves, History or persistence were added. Heart rate intensity describes fixed HR bands, not movement classification; silent stream loss without notification continues the previous estimate. No commit or push was performed.

英文提交信息 / English commit message:

```text
feat: implement step 5.4add heart rate zones and duration bars

- Track fixed HR zones using validated batch-final readings and monotonic time
- Preserve unclassified gaps and freeze zone totals before session cleanup
- Add simple English intensity labels and five duration bars
- Test boundaries, batch validity, timing, Retry, and stale events
- Synchronize AGENTS and bilingual prompt records with current validation
```

# 步骤 5.5add：实时曲线、单调时间轴及有界缓存补全 / Complete real-time curves, monotonic axes and bounded buffers

2026-09-30：用户要求“5.5add 的 prompt 和实现”，已执行下列提示词。完整规则见 AGENTS.md 第 5.22 节及 5.24；代码与本轮自动检查完成，真实曲线、性能和设备生命周期待验证，实际结果见本节末尾。
2026-09-30: The user requested the prompt and implementation for 5.5add. The following prompt has been executed according to AGENTS.md Sections 5.22 and 5.24. Code and fresh automated checks are complete; real curves, performance and device lifecycle checks remain pending. Actual results follow below.

## 中文

```text
补充要求（5.5add）：本次完成原 5.5；先检查 5.3add/5.4add 对应功能已完成、HR/ACC/ECG 实际数据路径可用。不要求原 5.5 自身先完成，按下列规则补齐，复用部分已有代码。HR/步频/速度实时记录应保留实际会话经过时间、可空值及断段信息；用现有管理实例持有，不在 Composable 重组中生成记录或重建订阅。先完成采集事件→取点/断段→有界缓存→简单绘图的真实接线。保持本步实时 60 秒/5 秒窗口；第 6 阶段每秒整场历史、四小时上限、UUID/摘要及 SQLite 不属于本步，不能提前实现。单流 Retry 清实时对应缓存的规则保持不变，未来整场历史的保留由 6.1c/6.1d 另行实现。

按照根目录 AGENTS.md 第 5.22 节实施补充步骤 5.5add（对应原开发步骤 5.5）。先检查现有代码、真实 HR/ACC/ECG 采集、5.1 有效性处理、5.2—5.3 步频/速度输出和会话时间；缺少依赖时说明，不自动推进其他步骤。依赖满足后直接修改文件：

1. 为 HR 提供最近 60 秒、最多 61 个显示记录，每个会话整数秒保留最后真实非空批次的末样本及实际接收时间，不取平均。沿用批末有效性，无效值留空并断段，不画成 0；桶内替换不能抹掉断段。无新 HR 不新增点，相同值的新接收仍是数据。该处理只用于曲线，不改变逐样本心率统计或区间计时。
2. 为步频和速度分别保留最近 60 秒、最多 241 点，复用每 250 ms 的已有计算/刷新入口记录一次当前结果，不在绘图、重组或每次候选步事件中重复追加或重算算法。预热/不可用留空，正常 ACC 下静止零值可绘制。所有显示缓存同时按时间及点数移除最旧记录，不补点、不无限累计；断段信息随记录保持有界。
3. ECG 显示最近 5 秒，复用现有 10 秒/最多 1,300 样本原始缓存，不另建长期原始缓存。130 Hz 下窗口约 650 点，绘制窗口内全部样本，不合并、平均、隔点抽取或平滑；保留实际配置，不硬改采样率。HR、步频/速度最多每 250 ms 刷新画面，ECG 可见时最多每 100 ms 刷新；刷新频率不是采样频率，不能每次只取一个 ECG 样本。
4. 横轴统一为 Running 起点后的经过秒数，显示 mm:ss。HR 用批次 elapsedRealtime 接收时刻减会话起点；步频/速度用结果记录时的会话经过时间。保留 receivedAt 日期时间原义，不用系统日期计算横轴；ACC 算法仍使用原始传感器时间。
5. ECG 每个有效订阅仅用首个非空批次建立固定锚点：S0 为批末传感器 ns，P0 为批次手机 elapsedRealtime ms，T0 为会话起点 ms。每个样本 x = (P0 − T0)/1000 + (timeStamp − S0)/1,000,000,000，先做整数时间差再转换浮点秒。保留样本间隔，不把整批画在同一时刻、不逐批移动锚点；负 x 不绘制、不挤到零点。Retry 重建锚点但保留 T0，说明这是含传输延迟的近似对齐，不承诺多流精确同步。
6. HR 遇无效/无接触、失败或结束时断段，合并前相邻真实批次接收时间差严格大于 3 秒也不连线；这只影响绘图，不改变 5.4 区间保持计时。步频/速度复用 ACC 严格大于 30 ms 的缺口、算法段重建和订阅中断，预热后新段开始。ECG 使用原始相邻样本时间差严格大于 3/实际采样率 秒作为绘图断段阈值，130 Hz 时约 23.1 ms；跨批次也检查，不套用 ACC 阈值或触发额外断开/重试。全部曲线不跨缺口连线、不插值或补零，不延长没有新数据的 HR 水平线。
7. 单路失败/正常结束保留并冻结曲线快照；普通缺口只断段。接受 Retry 清空对应曲线和显示锚点/分段，ACC 同时清空步频和速度，其他曲线及本场累计统计保留，会话横轴不归零；拒绝操作不清空。Stop/整体中断冻结曲线及视窗，不添加人为归零点或滚动到空白；接受新 Start 清空全部。旋转、切图保留状态和数据，不重启订阅；未显示的图继续更新有界数据但不持续绘制，拒绝旧会话/旧订阅事件。
8. 使用现有 Compose 主题做简单绘图验收，提供 HR、步频/速度、ECG 切换，步频与速度在同一区域内单独切换，不共用不同单位的纵轴。单位为 bpm、steps/min、km/h、µV，ECG 保留正负值。完整布局留到第 8 阶段，不增加图表依赖、通用缓存框架、历史回放或持久化。
9. 添加针对性受控测试，覆盖末点非平均、无新数据不造点、桶内断段、时间/点数双上限、ECG 五秒子集保留所有样本、固定锚点与单位换算、负 x 排除、系统日期变化、三类断段边界、Retry 范围、Stop 冻结无尾部零点、新 Start、切图/旋转及旧事件。运行相关测试、debug 构建和 lint，更新两份 AGENTS.md 实际结果；提供真实曲线、滚动、ECG 刷新性能、静止、缺口、Retry、切图和旋转检查，未执行标记待验证。

仅完成 5.5add 对应的原步骤 5.5，使用最小代码和英文 UI/注释，保留原始数据、算法、区间计时、会话及资源释放规则，不增加滤波、自动重试、Pause/Resume、进程恢复、无关依赖或重构。60 秒曲线缓存不是完整会话存储。
中英文说明，分别列出文件修改、测试/构建结果和真机验证，不把规划或旧构建结果当作本步验收通过。
补充交付：本步实现后，按新运行的结果同步两份 AGENTS.md 与两份 prompt.md，保持各自副本一致；分别列出代码修改、测试/debug 构建/lint、真机待验证项，并给出与实际改动匹配的英文 commit message，不自动创建提交或推送。不得因本步完成就宣称其他 add 或 6.1a 已通过；本步未执行则维持待实施。
```

## English

```text
Supplement (5.5add): Complete original step 5.5. Verify completion of the functionality addressed by 5.3add/5.4add and availability of actual HR/ACC/ECG data paths. Do not require original 5.5 itself to exist already; minimally complete the rules below and reuse existing portions. HR/cadence/speed display records retain actual session elapsed time, nullable values and segment breaks, held by the existing manager rather than generated on Compose recomposition or by restarting subscriptions. Connect real acquisition events to point selection/breaks, bounded buffers and simple drawing. Preserve the real-time 60-second/five-second windows; stage 6 per-second full-session history, four-hour limit, UUID/summary and SQLite are outside this step. Keep Retry clearing of the corresponding real-time cache; later 6.1c/6.1d will separately retain full-session history.

Implement supplemental step 5.5add (original development step 5.5) according to Section 5.22 of the root AGENTS.md. Inspect the existing code, real HR/ACC/ECG reception, step 5.1 validation, cadence/speed outputs from steps 5.2–5.3 and session timing first. Report missing prerequisites without implementing other steps. Once prerequisites are satisfied, modify the files directly:

1. Retain the latest 60 seconds of HR chart data, capped at 61 display records. Within each integer session-second bucket, keep the final sample of the latest real nonempty batch and its actual reception time, without averaging. Preserve final-sample validity: invalid values create a gap rather than zero. Bucket replacement must not erase a segment break. Add no points without new HR; repeated values in new batches remain real data. This selection affects charts only, not per-sample HR statistics or zone timing.
2. Retain the latest 60 seconds of cadence and speed, capped at 241 points each. Reuse the existing 250 ms calculation/refresh entry point to record the current results once. Do not append again or rerun algorithms during drawing, recomposition or every candidate-step event. Leave warm-up/unavailable intervals blank; plot genuine stationary zeros with available ACC. Enforce both time and count limits, remove the oldest records, never pad points, and keep segment markers bounded with their records.
3. Show the latest five seconds of ECG using the existing ten-second/1,300-sample raw buffer, without another long-lived raw copy. At 130 Hz this is approximately 650 points. Draw every sample in the visible window without merging, averaging, skipping or smoothing. Preserve actual confirmed settings instead of forcing a sampling rate. Refresh HR/cadence/speed charts at most every 250 ms and visible ECG at most every 100 ms. Drawing frequency is not sampling frequency; do not select only one ECG sample per refresh.
4. Use elapsed seconds since Running began for the horizontal axis, labelled mm:ss. For HR, subtract the session origin from batch elapsedRealtime reception time. For cadence/speed, use session time when recording the calculated output. Preserve wall-clock receivedAt semantics but do not use dates for chart timing. ACC algorithms continue to use original sensor timestamps.
5. Establish one fixed ECG anchor per valid subscription from its first nonempty batch: S0 is the last sample's sensor timestamp in ns, P0 is batch elapsedRealtime reception time in ms, and T0 is the session origin in ms. Map each sample as x = (P0 − T0)/1000 + (timeStamp − S0)/1,000,000,000, subtracting integer timestamps before converting to floating-point seconds. Preserve sample spacing rather than placing a whole batch at one instant or shifting the anchor each batch. Omit negative x values instead of piling them at zero. Retry establishes a new anchor while retaining T0. Describe this as approximate alignment including transport delay, not exact synchronization between streams.
6. Break HR lines on invalid samples, lost contact, failure or completion, and when consecutive real batch receptions before display reduction are strictly more than three seconds apart. This affects drawing only, not step 5.4 zone-duration holding. Cadence/speed reuse ACC gaps strictly over 30 ms, detection-segment resets and subscription interruptions, starting a new line after warm-up. For ECG, break when original adjacent sample timestamps differ by strictly more than 3/actualSampleRate seconds, approximately 23.1 ms at 130 Hz, including across batches. Do not reuse ACC's threshold or trigger extra disconnection/retry. Never connect across gaps, interpolate or fill missing values with zero, or extend HR horizontally without new data.
7. Retain and freeze chart snapshots on individual stream failure/completion; ordinary gaps only break lines. An accepted Retry clears the corresponding chart and display anchor/segments; ACC clears both cadence and speed. Preserve other charts and session aggregates without resetting the session time axis. Rejected actions must not clear data. Stop or overall interruption freezes chart data and viewport without appending artificial zero points or scrolling to an empty view. An accepted new Start clears all charts. Preserve data/state across rotation and chart selection without restarting subscriptions. Hidden charts maintain bounded data but do not continuously draw. Reject old-session and old-subscription events.
8. Provide simple chart verification using the existing Compose theme, with HR, cadence/speed and ECG selection. Select cadence or speed within their shared area rather than plotting different units on one vertical axis. Use bpm, steps/min, km/h and µV, preserving signed ECG values. Leave final layout integration to stage 8. Add no chart dependency, generic buffering framework, history replay or persistence.
9. Add focused controlled tests for last-point selection rather than averaging, no fabricated points, intra-bucket gaps, time/count limits, the five-second ECG subset retaining every sample, fixed anchors and unit conversion, negative x exclusion, wall-clock changes, gap boundaries for each chart, Retry scope, Stop freezing without trailing zeros, new Start, chart selection/rotation and stale events. Run relevant tests, a debug build and lint. Update both AGENTS.md files with actual results and provide device checks for real curves, scrolling, ECG refresh performance, stationary activity, gaps, Retry, chart switching and rotation. Mark unperformed checks as pending.

Complete only 5.5add (original step 5.5) with minimal code and English UI text/comments. Preserve raw data, algorithms, zone timing, sessions and resource cleanup. Do not add filtering, automatic retry, Pause/Resume, process recovery, unrelated dependencies or refactoring. A 60-second chart buffer is not full-session storage.
Explain in Chinese and English, separating file changes, test/build results and device verification. Do not present plans or older builds as this step's acceptance results.
Additional delivery: After implementation, synchronize both AGENTS.md and both prompt.md files using fresh results and keep each pair identical. Separately report code changes, tests/debug build/lint and pending device checks. Provide an English commit message matching actual changes, without automatically committing or pushing. Completing this item does not establish completion of other add items or 6.1a; keep unexecuted work pending.
```

## 5.5add 实际结果 / Actual results — 2026-09-30

- 前置与文件：已检查 5.3add/5.4add、真实 HR/ACC/ECG 路径、原始缓存、步频/速度和会话计时，代码前置齐全。新增 LiveCharts.kt、LiveChartPanel.kt；修改 PolarBleManager.kt、SessionState.kt、StepDetector.kt、SensorActivity.kt；新增 LiveChartsTest.kt 和 LiveChartLifecycleTest.kt。仅完成 5.5add，不自动执行 6.1a。
  Prerequisites and files: Inspected 5.3add/5.4add, actual HR/ACC/ECG paths, raw buffers, cadence/speed and session timing; code prerequisites were present. Added LiveCharts.kt and LiveChartPanel.kt; updated PolarBleManager.kt, SessionState.kt, StepDetector.kt and SensorActivity.kt; added LiveChartsTest.kt and LiveChartLifecycleTest.kt. Only 5.5add was implemented; 6.1a was not started automatically.
- HR/运动缓存：HR 每会话秒保留真实批末最后一点及实际 elapsedMs，不取平均，不重复旧 HR；同桶替换保留断段，真实批次间隔严格超过三秒才新增缺口。步频和速度共用最近 60 秒/最多 241 条记录，在已有 250 ms 计算刷新入口取值，预热/缺失为空，静止零为有效值；StepDetector 只增加随原 clearSegment 递增的只读段编号，捕获刷新之间的段重建，不改算法。两类队列均按时间和点数裁剪，图表不重算或改变原统计。
  HR/motion buffers: HR retains the latest real batch-final point and actual elapsedMs in each session-second bucket, without averaging or repeating stale HR. Bucket replacement preserves breaks, and a real inter-batch gap must exceed three seconds. Cadence and speed share a 60-second/241-record buffer populated by the existing 250 ms calculation refresh; warm-up/missing values are null and measured stationary zeros remain valid. StepDetector only adds a read-only segment counter incremented by existing clearSegment calls, detecting resets between refreshes without changing the algorithm. Both queues enforce time and count bounds, independently of original statistics.
- ECG：复用原 EcgBuffer 十秒/1,300 点，不额外长期保留原始数据。每次有效订阅仅首个非空批次固定传感器末点和接收会话时间锚点，先做纳秒整数差再转换；负 x 不画，窗口使用左开右闭五秒。断段阈值为实际已确认 rate 的三周期，跨批比较；没有降采样、平均或滤波。已核对固定 SDK 8.3.0 官方模型时间单位，未改变采样参数或 SDK API。
  ECG: Reuses the original ten-second/1,300-sample EcgBuffer without another retained raw copy. Each subscription anchors once using its first nonempty batch's final sensor timestamp and session reception time, subtracting integer nanoseconds before conversion. Negative x values are omitted and the five-second window is left-open/right-closed. Gap detection compares adjacent samples across batches against three periods of the actual confirmed rate. No downsampling, averaging or filtering was added. The timestamp unit was checked against the official SDK 8.3.0 model without changing acquisition settings or SDK APIs.
- 接线/生命周期：真实事件送入曲线缓存；accepted STARTING 清对应图/锚点，ACC 同清两个运动图，单路失败或完成冻结对应视窗。整体 Stop/中断在当前指标清零前冻结数据和视窗，不追加零点、不随清理时间滚动；新 Start 清数据，选择随现有管理器保留。SessionController 增加只读 elapsedAt，不改变会话状态。原会话/订阅保护过滤重复与旧事件，累计统计与其他流不受 Retry 清图影响。
  Integration/lifecycle: Real events feed chart buffers. Accepted STARTING clears the corresponding chart/anchor, with ACC clearing both motion series. Individual failure/completion freezes its viewport. Overall Stop/interruption freezes data and viewports before current metrics are zeroed, without appending zeros or scrolling through cleanup. New Start clears chart data; selection stays with the existing manager. SessionController adds read-only elapsedAt without changing session state. Existing session/subscription guards handle duplicates and stale events, while Retry chart clearing preserves aggregates and other streams.
- 简单显示：Compose Canvas 和原主题提供 HR/Motion/ECG 切换，Motion 单独选步频或速度；单位 bpm、steps/min、km/h、µV，横轴 mm:ss，ECG 保留正负值。仅选中图按 HR/运动 250 ms 或 ECG 100 ms 读取快照，隐藏图仍更新有界数据；每个可见 ECG 点都进入绘制循环，无效值/断段不连线。显示实时或冻结状态及近似对齐说明。正式布局未实施。
  Minimal display: Compose Canvas and the existing theme provide HR/Motion/ECG selection, with cadence or speed separately selected inside Motion. Units are bpm, steps/min, km/h and µV; the elapsed axis uses mm:ss and ECG retains signed values. Only the selected chart reads display snapshots at 250 ms for HR/motion or 100 ms for ECG; hidden chart data remains bounded and updated. Every visible ECG point enters the drawing loop, with no lines across nulls or breaks. Live/frozen status and approximate-alignment notes are shown. Final layout remains unimplemented.
- 本轮自动检查：新增 LiveChartsTest 13 项、LiveChartLifecycleTest 3 项；最终运行 `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，BUILD SUCCESSFUL。读取本轮 XML：148 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。覆盖末点/原时间/不造点、桶内断段、统计不变、时间/点数限制、运动刷新/预热/静止/30 ms 与段超时、ECG 负 x/固定锚点/有符号值/五秒完整 650 点/实际 rate 断段、Retry 范围与重锚、冻结/无尾零/清理不滚动、重复和旧事件、新会话及切换/重读不重启采集。首次编译漏写 SettingType 限定名，修正后完成全量验证。
  Automated verification: Added 13 LiveChartsTest cases and three LiveChartLifecycleTest cases. Final run of `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline` was BUILD SUCCESSFUL. Current XML reports contain 148 tests with zero failures/errors/skips; lint has zero errors and 18 warnings. Coverage includes last-point/original-time selection, no fabricated points, intra-bucket breaks, unchanged statistics, time/count bounds, motion refresh/warm-up/zero/30 ms gaps and segment expiry, ECG negative x/fixed anchors/signed values/all 650 five-second samples/actual-rate gaps, Retry scope/reanchoring, freezing without trailing zeros or cleanup scrolling, duplicates/stale events, new sessions and selection/snapshot reads without restarting collection. An initially unqualified SettingType reference was corrected before the successful full checks.
- 真机与限制：未安装 APK、未操作手机，未进行屏幕渲染或性能仪器测试。真实波形、单位/滚动、ECG 100 ms 刷新表现、三流并行、静止零、缺口/Retry、Stop/新 Start、切图/旋转/断线/锁屏/后台均待验证，具体步骤见 AGENTS.md 第 9 节。650 点受控测试证明可见数据集合，绘制全点按代码检查，不当作手机帧率证据。ECG 含传输延迟只是近似对齐；HR 每秒末点可能省略秒内变化，实时缓存不等于整场历史。
  Device checks and limitations: No APK was installed or phone operated, and no screen-rendering or instrumented performance tests ran. Real waveforms, units/scrolling, ECG 100 ms refresh behavior, concurrent streams, stationary zeros, gaps/Retry, Stop/new Start, chart switching/rotation/disconnection/lock/background behavior remain pending; procedures are in AGENTS.md Section 9. The 650-point test verifies the visible data set and full-point drawing was inspected in code; neither establishes phone frame rate. ECG alignment includes transmission delay and is approximate; per-second HR selection may omit changes within a second. Real-time buffers are not full-session history.
- 文档与范围：两份 AGENTS.md、两份 prompt.md 分别同步。未增加依赖、通用框架、整场历史、UUID/摘要、四小时结束、SQLite、History、进程恢复、自动重试或正式布局。没有自动 commit 或 push；6.1a 仍待用户指定并重新检查前置。
  Documentation and scope: Both AGENTS.md copies and both prompt.md copies are synchronized. No dependencies, generic framework, full-session history, UUID/summary, four-hour ending, SQLite, History, process restoration, automatic retry or final layout were added. No commit or push was performed. Step 6.1a awaits a user request and fresh prerequisite inspection.

英文提交信息 / English commit message:

```text
feat: implement step 5.5add bounded live sensor charts

- Add bounded HR and motion records with elapsed timestamps and segment breaks
- Map all visible ECG samples with a fixed per-subscription anchor
- Add simple Compose chart selection and independent display refresh
- Freeze viewports on ending and clear only the retried stream
- Add controlled chart and lifecycle tests and synchronize bilingual records
```

## 2026-09-30 add 提示词与完整同步记录 / Add prompts and full synchronization

- 已新增 5.3add、5.4add、5.5add 的完整中英文待执行提示词；规则复用既定 5.20—5.22，补充缺口检查、最小结果接入、步骤边界和真实验证要求。原 5.3—5.5 提示词及历史实施记录保留，今后使用对应 add 作为执行入口。
- Added complete bilingual pending prompts for 5.3add, 5.4add and 5.5add, preserving Sections 5.20–5.22 and specifying gap inspection, minimal output integration, step boundaries and actual verification. Original prompts and historical records remain; use the corresponding add as the implementation entry point.
- 已确认项目内旧 prompt.md 的前段包含于根目录版且第 6 阶段内容相同，没有独有记录；将完整根目录版同步至项目副本。两份 AGENTS.md、两份 prompt.md 分别完全一致。此次只改四份文档，三个 add 均尚未实施，没有应用代码修改或测试/构建/真机结果，未创建提交。
- Verified that the old project prompt prefix was contained in the root copy and its stage 6 content matched, with no unique records. Synchronized the full root version to the project copy. Each AGENTS.md pair and prompt.md pair is identical. Only four documentation files changed; all three add steps remain unimplemented, with no application changes, tests/build/device results or commit creation.

## 2026-09-30 功能目录迁移 / Functional package organization

- 用户要求：按已确认的功能分类移动对应文件。
- User request: Move the existing files into the agreed functional directories.
- 本轮范围：17 个源码文件移入 ble、sensor、motion、heartrate、chart、session；17 个功能测试同步分包，调整 package/import。两个 Activity、主题、Manifest、资源和依赖保持原位。完整文件映射见 AGENTS.md 5.25。
- Scope: Move 17 production files and 17 matching functional test files into functional packages and update package declarations and imports. Keep activities, theme files, the manifest, resources and dependencies in place. See AGENTS.md Section 5.25 for the file mapping.
- 保持：没有重命名 CadenceWindow、拆分类或改变算法；全部 41 个 Kotlin 文件去除 package/import 和空行后与迁移前 HEAD 内容一致。history/storage 仅为本地空目录，第 6 阶段未实施。
- Preserved behavior: No class extraction, CadenceWindow rename or algorithm change. All 41 Kotlin file bodies match the pre-move HEAD after excluding package/import lines and blank lines. History and storage remain empty local directories; Stage 6 is not implemented.
- 本轮验证：运行 `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，最终 BUILD SUCCESSFUL；148 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。沿用现有测试，本轮已实际执行。
- Verification: Existing unit tests ran after the move: 148 tests, zero failures, errors or skipped tests. Debug assembly and lint passed; lint reported zero errors and 18 warnings.
- 真机：未安装或操作设备，运行时与设备验收仍待完成。两份 AGENTS.md、两份 prompt.md 已同步；未创建 commit 或 push。
- Device checks: No installation or device interaction was performed; runtime and device checks remain pending. Both AGENTS.md copies and both prompt.md copies were synchronized. No commit or push was created.

## 2026-09-30 运动处理最小重构 / Minimal motion refactor

- 中文提示词：按已确认的三项最小方案重构。提取 StepDetector 的步长提交/距离累计与完整五秒窗口极值私有方法，保持执行顺序和统计持有者；封装预处理器并通过只读 isWarmingUp 提供预热状态；将 CadenceWindow 改名为 MotionWindow，同步调用和测试。StepState 保留原位，不修改算法参数、不增加框架或依赖、不实施第 6 阶段。运行测试、debug 构建与 lint，并同步两份 AGENTS.md 和两份 prompt.md 的实际结果。
- English prompt: Apply only the agreed minimal motion refactor. Extract private methods for committing steps and accumulating distance, and for updating complete five-second window maxima, while preserving execution order and the existing statistics owner. Make the preprocessor private and expose read-only isWarmingUp. Rename CadenceWindow to MotionWindow and update callers and tests. Keep StepState in its current file. Do not change algorithm parameters, add frameworks or dependencies, or implement Stage 6. Run unit tests, debug assembly and lint, and synchronize both AGENTS.md copies and both prompt.md copies with actual results.
- 实际结果：StepDetector 新增私有 commitSteps/updateWindowMaxima 和只读 isWarmingUp；PolarBleManager 改用该属性。窗口文件/类和测试分别改名 MotionWindow / MotionWindowTest；既有 StepDetectorTest、LiveChartsTest 和 MotionStatisticsTest 同步调整。StepState 未拆出，统计及生命周期语义保持。
- Actual results: Added private commitSteps/updateWindowMaxima methods and read-only isWarmingUp to StepDetector, and switched PolarBleManager to the new property. Renamed the window and matching test to MotionWindow and MotionWindowTest. Updated existing detector, chart and motion statistics tests. StepState remains in place; statistics and lifecycle behavior are preserved.
- 本轮验证：执行 `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，BUILD SUCCESSFUL；148 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。预热接口覆盖初始及超时重建的第 103/104 个样本边界、缺口/Retry/清理及显示刷新不重置段。原四步补计、距离与极值测试继续通过。
- Verification: Tests ran for this refactor: 148 tests, zero failures, errors or skipped tests. Debug assembly and lint passed; lint reported zero errors and 18 warnings. Existing tests cover warm-up boundaries, interruptions and retries, unchanged display-refresh behavior, four-step confirmation, distance accumulation and full-window maxima.
- 未安装或操作真机；真机验收待完成。第 6 阶段未实施，没有创建 commit 或 push。此前目录迁移记录中的“未重命名 CadenceWindow”是当时状态，本次重构采用新名称。
- No APK installation or device interaction was performed; device verification remains pending. Stage 6 was not implemented, and no commit or push was created. The earlier migration record describes the old name at that time; this refactor now uses MotionWindow.


## 2026-09-30 步骤 6.1a 实施结果 / Step 6.1a implementation results

- 前置：重新检查 5.3add 的 StepState 距离/速度/最大步频、5.4add 的五区间/未归类毫秒以及 5.5add 的实时曲线/单调时轴，代码输出齐全。沿用 Polar SDK 8.3.0 的既有采集入口；未更改 SDK API 调用、依赖或算法参数。
  Prerequisites: Rechecked motion statistics, HR zone/unclassified durations and live charts/monotonic timing from 5.3add–5.5add. Required code outputs were present. Existing Polar SDK 8.3.0 streaming calls, dependencies and algorithm parameters remain unchanged.
- 身份/时间：SessionController 只在接受 Start 后生成 UUID 并快照设备名称/ID；重复/拒绝 Start 不更换记录。startRequestedAt、startedAt、endedAt 为 Unix 毫秒；首次有效 HR 或真实 ACC/ECG 批次的手机接收时间建立 Running 起点，durationMs 和历史 elapsedMs 使用已有单调时钟。日期回拨允许结束日期早于开始日期，但不会改变真实经过时长。
  Identity/time: Accepted Start creates a UUID and device snapshot. Rejected or duplicate starts preserve the record. Unix timestamps describe request/start/end dates; the first valid HR or real ACC/ECG batch establishes Running. Duration and history elapsed time use the existing monotonic clock, independently of date changes.
- 摘要/资格：新增 SessionRecord.kt，直接复制现有 HR min/max/mean/count、五区间/未归类毫秒、总步数、最大步频、距离及平均/最大速度；不从图表重算，不舍入内部数值。平均/最小步频仅为 null，英文显示 -- 和 step 6.1b。收到 HR 与收到有效 HR 分开；至少一个有效 HR 或真实 ACC/ECG 样本才具备保存资格，零时长也可具备资格；无 ACC 的步数/距离为 null，真实观测且无步为 0，无合格窗口的极值仍 null。
  Summary/eligibility: SessionRecord.kt copies existing owner statistics without chart-based reconstruction or internal rounding. Mean/minimum cadence remain null and explicitly unimplemented. HR reception and valid HR are separate. At least one valid HR or real ACC/ECG sample is required, with no minimum duration. Unknown motion remains null; observed zero steps/distance remain zero, and extrema without qualifying windows remain null.
- 完整性：各流保留 received/missing/failed；既有订阅不可用、失败或会话中单流终止保留缺失标记，Retry 不清除。HR 无效样本/批次间超过三秒、ACC 已知缺口、ECG 按已有绘图规则超过三个采样间隔会留下缺失标记。整体中断单独记录；正常 Stop 的清理回调不会伪造缺失。未收到全部三流、无有效 HR、已知缺失/失败或整体中断时 incomplete 为 true；该标记只反映已知状态，不保证检测所有传输丢失。
  Completeness: Per-stream received/missing/failed flags survive Retry. Unavailable/failed/terminated streams, invalid HR or HR batch gaps over three seconds, known ACC gaps and ECG gaps over three sampling intervals retain missing flags. Overall interruption is recorded separately; normal Stop cleanup does not invent missing data. Incomplete reflects absent streams, no valid HR, known gaps/failures or interruption, not a guarantee that every transport loss is detected.
- 生命周期/边界：沿用整体结束结算，固定结束时刻，结算最后区间与平均速度并冻结摘要，再停止订阅；清理耗时不计入，重复结束不重结算。Retry 保留 ID 和累计；旧订阅/旧会话事件沿用原过滤，旋转保留 ViewModel 管理实例；新 Start 创建新记录，不改写已取得的旧快照。仅定义 HrHistoryPoint/MotionHistoryPoint，尚未实例化收集器或数据库，不保留整场曲线，不显示 Saved，不支持进程重启恢复。
  Lifecycle/scope: Existing ending settlement freezes timing and summary before subscription cleanup. Cleanup duration is excluded and repeated endings do not settle twice. Retry retains identity/totals; existing event guards reject stale sources. Rotation retains the ViewModel owner; new Start creates a new record without mutating old snapshots. History point structures are defined only: no full-session history collection, database, Saved state or process restoration.
- 文件/显示：新增 session/SessionRecord.kt、session/SessionSummaryPanel.kt；修改 session/SessionState.kt、ble/PolarBleManager.kt、SensorActivity.kt。沿用 Compose 主题，显示英文身份/时间/设备/资格、摘要及完整性测试文本；新增 session/SessionRecordTest.kt，适配原 session/chart/heartrate/motion 四个生命周期测试文件。
  Files/display: Added SessionRecord.kt and SessionSummaryPanel.kt; updated SessionState.kt, PolarBleManager.kt and SensorActivity.kt. Simple English verification text uses the existing Compose theme. Added SessionRecordTest.kt and adapted four existing lifecycle test files.
- 本轮自动检查：Android Studio JBR 下运行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；161 项测试（新增 13 项）全部通过，0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。新增测试覆盖 UUID/设备、日期回拨/单调时长、无数据/无效 HR/三流资格、混合 HR 批次、未舍入摘要/列表复制、null/0、最后区间/延迟清理、Retry、缺失/失败、ACC 缺口、新场次、全部流完成/中断、旧事件及保留持有者后重订阅。第一轮沙箱构建受网络权限限制，随后获准在主机环境完成检查。模拟数据仅用于测试。
  Automated checks: Ran :app:testDebugUnitTest :app:assembleDebug :app:lintDebug with Android Studio JBR: BUILD SUCCESSFUL. All 161 tests passed, including 13 new tests, with zero failures/errors/skips; lint reported 0 errors and 18 warnings. Coverage includes identity/device snapshots, independent clocks, eligibility, mixed HR validity, unrounded values, null/zero, ending settlement/cleanup, Retry, missing/failure flags, ACC gaps, new sessions, termination/interruption, stale events and collector reattachment to the retained owner. The sandbox network restriction was followed by an approved host build. Synthetic data is test-only.
- 真机验收待执行：连接 H10 → Start，核对 ID/设备/Running 开始时间及资格；保持三流采集并检查摘要与原指标一致；Stop 后等候，核对 Frozen、结束时间/时长/区间/累计不再变化；新 Start 应换 ID、清零新场统计。单流失败/Retry 应保留 ID/累计及缺失标记；旋转应保留 ID/计时，断线或离开前台应冻结本场。通过受控测试核对的日期变化、旧事件和清理边界不代替真实设备结果。
  Pending device checks: Connect H10, Start and inspect identity/device/Running date/eligibility; compare summaries with the existing live metrics. After Stop, verify Frozen and stable end time/duration/zones/totals; new Start must change ID and reset the new session. Failure/Retry must retain ID/totals and missing flags. Rotation must retain identity/timing; disconnect/background must freeze the session. Controlled clock/event tests are not device validation.
- 状态：代码已写入、自动检查已通过；未安装 APK、未操作手机、未验证真实旋转或界面性能。6.1b—6.1d、6.2 均未实施，未自动 commit 或 push。
  Status: Files modified and automated checks passed. No APK installation, phone interaction, actual rotation or UI performance validation was performed. Steps 6.1b–6.1d and 6.2 remain unimplemented. No automatic commit or push.

Suggested commit message (not committed):
```text
feat: add session identity and frozen summaries for step 6.1a
```


## 2026-09-30 步骤 6.1b 实施结果 / Step 6.1b implementation results

用户本轮指令：实施6.1b。按上文已保存的中英文 6.1b 提示词执行。
User instruction: Implement step 6.1b, using the bilingual step 6.1b prompt recorded above.

- 前置与范围：重新检查 5.3add 的真实运动统计/合格窗口、5.4add 心率区间、5.5add 曲线以及 6.1a 的摘要与单调时间接线，前置齐全。仅修改三个生产文件：motion/StepDetector.kt、session/SessionRecord.kt、session/SessionSummaryPanel.kt；未修改 SDK、依赖、采集设置、步伐/步长算法或会话控制路径。
  Prerequisites/scope: Rechecked 5.3add motion/window outputs, 5.4add zones, 5.5add charts and 6.1a summary/monotonic timing. Modified only three production files: StepDetector.kt, SessionRecord.kt and SessionSummaryPanel.kt. SDK calls, dependencies, sampling, step/stride algorithms and session control are unchanged.
- 平均步频：StepState.meanCadence 使用总确认步数 × 60000 / durationMs，包含完整 Running 内的静止和缺失时间；无 ACC 观测或零分母为 null，有观测无步为 0。保留小数，不使用当前显示步频或历史点求平均，不补步或扣除缺失时长。
  Mean cadence: StepState.meanCadence uses total confirmed steps × 60000 / durationMs, including stationary and missing time throughout Running. No ACC observations or zero duration yields null; observed no-step activity yields zero. Internal precision is retained, with no averaging of displayed/history values or imputation.
- 最小步频：在原最大步频/速度的合格窗口入口增加 minimumCadence，以同一个原始 N5 × 12 更新；未增加窗口。真实传感器时间从预热结束覆盖完整五秒后才更新，4.99 秒、预热、手机时间外推、两秒显示归零、Stop 人为零均不参与；真实合格静止可为 0。缺口/失败/Retry 清段但保留极值，重新预热并满五秒才继续更新；新 Start 清空。平均可能低于最小值，因为统计分母和范围不同。
  Minimum cadence: The existing extrema entry point updates minimumCadence using the same raw N5 × 12 as maximum cadence, without another window. Only real five-second coverage since warm-up qualifies. Short/warm-up/display-extrapolated windows and forced display/Stop zeros are excluded; genuine stationary windows may yield zero. Gaps/failure/Retry retain extrema while rebuilding the segment. New Start resets them. The mean may be below the minimum because the measurement scopes differ.
- 摘要/显示：SessionSummary.from 直接接入 meanCadence/minimumCadence。沿用既有刷新、Retry、结束冻结、旋转持有者和旧事件过滤。SessionSummaryPanel 改为英文 Mean cadence / Min cadence / Max cadence（steps/min），仅展示时保留两位小数，未知为 --；显示无 ACC、零时长、无合格窗口或已知 ACC 缺失原因。未新增整场历史、数据库或正式布局。
  Summary/display: SessionSummary.from reads both fields from the current motion owner. Existing refresh, Retry, ending freeze, retained owner and stale-event guards remain in use. The English panel shows Mean/Min/Max cadence in steps/min, rounds only for display and explains unavailable/incomplete values. No full-session history, database or final layout was added.
- 自动验证：扩展 MotionStatisticsTest.kt 和 SessionRecordTest.kt，新增 7 项测试并更新既有边界/摘要断言；覆盖平均公式/小数/静止/缺失分母、无 ACC/零分母、4.99/5 秒、显示零/Stop 零排除、真实静止零、缺口/Retry 重建、停止/中断/全部流结束冻结、新场及旧事件、摘要复制和持有者重订阅。执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；168 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。使用受控数据，不是 H10 真机准确率或旋转验收。
  Automated verification: Added seven tests and updated existing assertions in MotionStatisticsTest.kt and SessionRecordTest.kt. Coverage includes formula/precision/full duration, null/zero, 4.99/5-second boundaries, forced-zero exclusion, stationary windows, gap/Retry recovery, all ending paths, stale/new sessions, summary copying and retained-owner reattachment. :app:testDebugUnitTest :app:assembleDebug :app:lintDebug completed successfully: 168 tests, zero failures/errors/skips; lint 0 errors and 18 warnings. Controlled data does not establish device accuracy or actual rotation behavior.
- 真机待验证：连接 H10 并 Start，检查无 ACC 时 --，收到 ACC 后平均值随总步数/完整时长变化；连续走路后静止，确认平均下降，Min 仅在真实合格窗口更新。Stop 后 Mean/Min/Max 固定；Retry 保留本场极值并显示缺失，新 Start 重置；旋转保留 ID/统计，断线或后台冻结。未安装 APK、未操作手机，实际界面、准确率和生命周期仍待验收。
  Pending device checks: Start with H10; verify -- without ACC and mean cadence against total steps/full duration after reception. Walk then stand still: mean should decrease and minimum should update only from qualifying real windows. Stop freezes all three values; Retry retains extrema and missing flags; new Start resets them. Rotation retains identity/statistics, while disconnect/background freezes them. No APK installation or phone interaction was performed; UI, accuracy and lifecycle remain unverified.
- 状态：6.1b 代码已写入、自动检查通过；两份 AGENTS.md 和两份 prompt.md 同步。6.1c、6.1d、6.2 未实施；未 commit/push。
  Status: Step 6.1b is implemented and automated checks passed. Both AGENTS.md and prompt.md pairs are synchronized. Steps 6.1c, 6.1d and 6.2 remain unimplemented. No commit or push.

Suggested commit message (not committed):
```text
feat: add mean and minimum cadence for step 6.1b
```


## 2026-09-30 步骤 6.1c 实施结果 / Step 6.1c implementation results

用户本轮指令：实施6.1c。按上文已保存的中英文 6.1c 提示词执行。
User instruction: Implement step 6.1c, using the bilingual step 6.1c prompt recorded above.

- 前置与文件：重新检查 5.3—5.5 的统计/曲线、6.1a 的 HrHistoryPoint/UUID/时间及 6.1b 的步频摘要，前置齐全。新增 history/HrHistory.kt；PolarBleManager.kt 接入 HR 事件、Start/Stop/订阅状态；SensorActivity.kt 显示英文计数/首末时间/状态；SessionRecord.kt 仅更新历史点注释。没有修改 SDK API、依赖、统计算法或原始 ACC/ECG 缓存。
  Prerequisites/files: Rechecked statistics/charts, HrHistoryPoint/UUID/timing and mean/minimum cadence. Added history/HrHistory.kt and connected HR events, Start/Stop and subscription state in PolarBleManager.kt. SensorActivity.kt displays English count/times/status; SessionRecord.kt only has a comment update. SDK calls, dependencies, algorithms and raw ACC/ECG buffers are unchanged.
- 收集：复用已筛除空批次、已过滤旧订阅/旧会话的真实 HR 入口；仅 Running 后记录。每个 elapsedMs 整数秒保留最后真实批次末读数及实际时间，不取平均，同值新批次仍替换时间；批末无效/接触丢失保存 null 并断段。无 HR 事件、空批次、250 ms 刷新和绘图重组均不造点。只含无效 HR、尚未 Running 的尝试没有历史点；混合批次存在有效 HR 可启动 Running，末值无效时记录 null。
  Collection: Uses the existing nonempty, current-session/current-subscription HR event path, after Running begins. Each elapsed-second bucket retains the latest real batch's final reading and actual time, without averaging. Equal-valued new batches still update time. Invalid/contact-lost final readings are null with a break. Empty batches, timers and drawing do not create records. Invalid-only attempts before Running have no history; mixed batches can start Running while recording an invalid final value as null.
- 断段/边界：首点、批末无效、订阅中断/Retry、相邻真实批次严格超过三秒时断段；桶内替换保留已发生断段。只接受 0—14,400,000 ms（含截止），最多 14,401 个桶；不预分配、不填补、不淘汰早期点。超过 60 秒仍保留早期记录；超截止读数不进入历史，但原 HR 统计与区间计时继续，本步不自动结束会话。
  Breaks/bounds: Breaks cover the first point, invalid final values, subscription interruption/Retry and gaps strictly over three seconds. Replacements preserve intra-bucket breaks. Collection accepts 0–14,400,000 ms inclusive and at most 14,401 buckets, without preallocation, padding or eviction. Earlier records survive the live 60-second window. Later HR events continue existing statistics/zones but are excluded from history; automatic session ending is not implemented here.
- 生命周期/显示：接受新 Start 绑定本场 UUID 并清空；重复/拒绝 Start 不清空。Retry 标记断段、保留旧记录及原横轴，实时缓存仍按原规则清理。整体 Stop/中断/全部流终止时冻结，保留已有最后部分秒，不制造终点；旧数据/旧刷新沿用既有过滤。管理器仍由 ViewModel 持有，重订阅读同一状态；真实旋转未验证。UI 只订阅计数、首末 elapsedMs、Frozen/Collecting/等待/上限状态，不逐批复制整场列表；snapshot() 按需返回副本。
  Lifecycle/display: Accepted Start binds the UUID and clears history; rejected starts do not. Retry preserves records/session time and marks a break while live charts still clear. Stop/interruption/all-stream termination freezes the existing partial-second record without a fabricated endpoint. Existing guards reject stale events. The ViewModel retains the owner; actual rotation remains unverified. UI subscribes only to metadata rather than copying the growing list every batch; snapshot() provides an on-demand copy.
- 自动验证：新增 HrHistoryTest.kt（6 项）和 HrHistoryLifecycleTest.kt（7 项）；覆盖末点/同值时间、null/混合批次/接触状态、三秒边界及桶内断段、无事件/空批次/日期改变、不从 60 秒缓存重建、隐藏图、Retry/旧订阅、重复 Start/新场次、部分秒/延迟清理/各结束路径、保留持有者重订阅、四小时及 14,401 点满容量/稀疏边界、统计继续而历史停止。运行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；181 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。四小时测试使用受控时间，模拟输入仅用于测试。
  Automated verification: Added six HrHistoryTest tests and seven HrHistoryLifecycleTest tests covering selection/validity, gaps/buckets, empty/no events, wall-clock independence, live-window separation, hidden charts, Retry/stale events, starts/endings/cleanup, retained-owner reattachment and full/sparse four-hour capacity boundaries. :app:testDebugUnitTest :app:assembleDebug :app:lintDebug passed: 181 tests, zero failures/errors/skips; lint 0 errors and 18 warnings. Four-hour checks use controlled time; synthetic inputs are test-only.
- 真机待验收：连接 H10 → Start，观察 HR history points 与首末 elapsedMs；连续超过 60 秒后核对首时间仍保留。切换到 Motion/ECG 时 HR 计数应继续；HR 失败/Retry 后记录不清空、时间轴继续。Stop 后计数/首末时间固定为 Frozen；新 Start 清空并绑定新场。旋转保留、断线/后台冻结及显示性能待真机核对；null/断段和四小时容量已做受控测试，不冒充真机证据。
  Pending device checks: Connect H10 and Start; inspect HR history count and first/last elapsedMs beyond 60 seconds. Counts should continue with other charts selected. HR failure/Retry must retain records/time axis; Stop freezes metadata and new Start clears for the new session. Actual rotation, disconnect/background freezing and UI performance remain pending. Controlled validity/break/capacity tests are not device evidence.
- 状态：6.1c 文件已写入、自动检查已通过；两份 AGENTS.md 和两份 prompt.md 同步。未安装 APK、未操作手机；6.1d 的运动历史/组合快照、6.2 的自动结束/SQLite/History 尚未实施，未 commit/push。
  Status: Step 6.1c is implemented and automated checks passed; both documentation pairs are synchronized. No APK installation or phone interaction. Motion history/combined snapshots (6.1d) and automatic ending/SQLite/History (6.2) remain unimplemented. No commit or push.

Suggested commit message (not committed):
```text
feat: collect bounded session heart rate history for step 6.1c
```

## 2026-09-30 步骤 6.1d 实施结果 / Step 6.1d implementation results

用户本轮指令：实施6.1d。按上文已保存的中英文 6.1d 提示词执行。
User instruction: Implement step 6.1d, using the bilingual step 6.1d prompt recorded above.
- 前置与文件：已复查 5.3—5.5、6.1a—6.1c，前置齐全。新增 history/MotionHistory.kt；SessionRecord.kt 增加 SessionSnapshot；SessionState.kt 在既有摘要结算完成后调用 onSummaryFrozen；PolarBleManager.kt 接入已有 250 ms 入口、ACC 订阅状态及 Start/Stop，SensorActivity.kt 增加英文运动计数、首末 elapsedMs 与快照状态。没有新增定时器或改变 SDK、依赖、算法和原始缓存。
  Prerequisites/files: Verified 5.3–5.5 and 6.1a–6.1c. Added history/MotionHistory.kt and SessionSnapshot in SessionRecord.kt. SessionState.kt invokes onSummaryFrozen after existing summary settlement. PolarBleManager.kt connects the existing 250 ms entry, ACC subscription state and Start/Stop; SensorActivity.kt adds English motion metadata and snapshot status. No new timer or changes to SDK calls, dependencies, algorithms or raw buffers.
- 收集与边界：每个 Running 秒桶保留最后一组已有步频/速度及实际 elapsedMs，保留原始小数和 m/s；不从最后 60 秒重建、不补漏刷秒。预热、不可用、未观察到真实 ACC 时为 null，正常静止可为 0，各值独立可空。首点、空值、订阅中断/Retry、检测段代次变化断段；桶内替换保留断段。ACC 严格超过 30 ms 的识别继续由 AccBuffer/StepDetector 负责。HR 与运动共用 Running 时间轴和 14,400,000 ms 含截止边界，各自最多 14,401 桶；不预分配、填满或淘汰早期历史，不要求点数/时间逐点相同。
  Collection/bounds: Keeps the last existing cadence/speed pair and actual elapsedMs per Running-second bucket, preserving precision and m/s. No live-cache reconstruction or backfilling. Warm-up, unavailable or unobserved ACC yields null; genuine stationary results can be zero, with independently nullable values. First/null points, subscription interruptions/Retry and detector-segment changes break the curve; replacements retain breaks. Existing owners still detect ACC gaps strictly over 30 ms. Both histories share the Running origin and inclusive 14,400,000 ms cutoff, independently bounded to 14,401 buckets without padding/eviction or matching point counts/timestamps.
- 冻结与生命周期：接受新 Start 才清空运动历史；Retry 保留旧记录并断段，实时缓存清理照旧。Stop/中断/全部流结束先冻结已有最后部分秒，不追加人工零/终点；既有流程结算区间、时长及统计后，回调一次复制两类历史并组合相同 UUID 的 SessionSnapshot。管理器只保留最近一次结束快照；新 Start 不清除或修改该快照，下一次结束才替换引用，不建立应用级待保存队列。旧事件及旧刷新沿用现有代次过滤；持有者仍由 ViewModel 保留。摘要继续读取原统计所有者，不由历史反算。UI 只观察历史元数据和最近快照，不在刷新时复制整场数组。
  Freezing/lifecycle: Only accepted Start resets motion collection. Retry retains old records/breaks while live clearing stays unchanged. Stop/interruption/all-stream termination freezes the existing partial second without artificial zeros/endpoints. After existing zone/time/statistics settlement, one callback copies both histories into a same-UUID SessionSnapshot. The manager retains only the latest ended snapshot; new Start leaves it intact and the next ending replaces its reference. This is not an application-level pending-save queue. Existing generation guards and ViewModel retention remain. Summaries still read original statistics owners; UI observes metadata and the latest snapshot without per-refresh whole-history copies.
- 自动检查已通过：新增 MotionHistoryTest.kt（6 项）、SessionSnapshotTest.kt（7 项）。覆盖每秒末组/实际时间、跳过刷新、精度/null/真实零/独立空值、桶内断段、30/31 ms 缺口、段重建/刷新间恢复、两秒显示归零而累计不变、Retry 与实时清理、重复 Start/旧订阅/旧刷新、新场次、保留持有者重订阅、各结束路径/部分秒/延迟清理、同 ID/一次冻结/旧快照不变、未知 ACC、60 秒外早期记录、满容量/稀疏四小时边界及到限不自动结束。运行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；194 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。四小时及传感器输入均为受控测试，不是真机准确率或性能证据。
  Automated checks passed: Added six MotionHistoryTest tests and seven SessionSnapshotTest tests. Coverage includes selection/timing, skipped refreshes, precision/null/zero, breaks, 30/31 ms gaps, segment recovery, two-second display zero, Retry/live clearing, duplicate starts/stale streams/ticks, new sessions, retained-owner reattachment, all endings/partial seconds/cleanup, identity/single freeze/old-snapshot stability, unknown ACC, early-history retention and full/sparse four-hour bounds without automatic ending. :app:testDebugUnitTest :app:assembleDebug :app:lintDebug succeeded: 194 tests, zero failures/errors/skips; lint 0 errors and 18 warnings. Controlled clocks and sensor inputs do not establish device accuracy or performance.
- 真机检查待执行：连接 H10 → Start，观察 Motion history points 每秒最多增加一个，首末 elapsedMs 随真实执行刷新变化；采集超过 60 秒并切换到 HR/ECG，确认运动记录继续且早期首时间仍保留。ACC 失败后 Retry，计数不清零、时间轴不重启；Stop 后计数/首末时间不再变化，Current session snapshot 为 Frozen in memory，Last frozen snapshot ID 与结束会话一致。新 Start 更换当前 ID/清空本场历史，上一个冻结 ID 保留到下一次结束。旋转应保留当前 ID/历史，断线或后台应冻结；这些行为及真实静止/走跑、UI 性能均待真机核对。空值/断段/四小时上限目前只由自动测试验证。
  Pending device checks: Connect H10 and Start. Motion count should grow at most once per second, using actual refresh times. Continue beyond 60 seconds and select HR/ECG; motion collection and early records should remain. ACC Retry must retain count/time axis. Stop must freeze count/times, display Frozen in memory and show the ended session ID. New Start changes the current ID and resets collection while retaining the previous frozen ID until the next ending. Verify rotation retention and disconnect/background freezing, stationary/walking behavior and UI performance on hardware. Null/break/four-hour behavior has only controlled-test evidence so far.
- 状态：仅 6.1d 文件已修改、自动检查通过；两份 AGENTS.md 和两份 prompt.md 同步。未安装 APK、未操作手机；没有 Saved、数据库、保存任务/失败状态、History 页面、应用级待保存管理或自动四小时结束，6.2 仍待实施。内存记录不保证进程重启保留；未 commit/push。
  Status: Only step 6.1d implemented; automated checks passed and both documentation pairs synchronized. No APK installation or phone interaction. No Saved state, database, save task/failure state, History page, application-level pending-save manager or automatic four-hour ending; 6.2 remains pending. Memory records do not survive process restart. No commit or push.
Suggested commit message (not committed):
```text
feat: add bounded motion history and frozen session snapshots
```

## 2026-09-30 步骤 6.2 实施结果 / Step 6.2 implementation results

用户本轮指令：实施6.2。按上文已保存的中英文 6.2 提示词执行。
User instruction: Implement step 6.2, using the bilingual step 6.2 prompt recorded above.
- 前置与文件修改：重新检查 5.3—5.5 及 6.1a—6.1d，前置齐全。新增 storage/SessionDatabase.kt、SessionSaveController.kt、SessionStorage.kt 和 history/HistoryPanel.kt；修改 SessionState.kt、PolarBleManager.kt、SensorActivity.kt、SessionSummaryPanel.kt、SessionRecord.kt，并将既有 ChartPlot 开放给 History 复用。修改两个备份 XML；没有新增依赖、数据库框架、SDK 调用、原始数据持久化、计步参数或第 7/8 阶段正式布局。
  Prerequisites/files modified: Verified 5.3–5.5 and 6.1a–6.1d. Added SessionDatabase.kt, SessionSaveController.kt, SessionStorage.kt and HistoryPanel.kt; updated session/manager/UI wiring and reused the existing ChartPlot. Updated both backup XML resources. No added dependencies/frameworks, SDK calls, raw-data persistence, step-detection parameters or stage 7/8 layout work.
- SQLite：App 私有 sessions.db，SQLiteOpenHelper 版本 1；sessions、hr_points、motion_points 三表，全部读写在 Dispatchers.IO。保存全部摘要、Unix ms、单调 durationMs、设备快照、各路 received/missing/failed、有效 HR 标记及完整性；保留 SQL NULL/真实零、原始小数、m/m/s 和断段。一个事务插入摘要与两类时序，异常回滚；主键 UUID、时序联合主键禁止重复。已提交 UUID 的再次保存只确认已有完整事务，不覆盖已有摘要或累加时序；失败 Retry 复用原快照/ID。无数据/仅无效 HR 不保存，有资格的零时长会话不额外拒绝。
  SQLite: App-private sessions.db uses SQLiteOpenHelper v1 with sessions, hr_points and motion_points; all reads/writes run on Dispatchers.IO. Stores all summary/timing/device/observation/completeness fields with SQL NULL versus genuine zero, full precision, m/m/s and breaks. One transaction inserts all three tables or rolls back. UUID and session/bucket keys enforce uniqueness. An already committed UUID is acknowledged without replacement or additional points; failed retries use the original snapshot/ID. Ineligible attempts are excluded without imposing a minimum duration on eligible sessions.
- 保存生命周期：既有结算后的快照进入应用级 SessionStorage，独立 SupervisorJob/Main scope 调用 IO 保存，不持有 Activity、不由 ViewModel 清除取消。显示 Saving…/Saved/Save failed 及对应 ID；成功提交后才 Saved。保存中/失败未处理时，按钮和 SessionController 同时阻止新 Start；失败仅手动 Retry save 或明确 Discard session，重复提交/重试被拒绝。离开页面后任务继续，返回新管理器仍读取相同应用级状态；没有服务、自动重试、崩溃恢复或静默丢弃。清理采集不会恢复连接；成功提交前杀进程仍可能丢失本场。
  Save lifecycle: The finalized snapshot goes to application-owned SessionStorage with an independent SupervisorJob/Main scope and IO database writes. No Activity reference or ViewModel-driven cancellation. Saving…/Saved/Save failed includes the session ID; Saved follows commit only. Both UI and SessionController block Start while saving or unresolved failure. Manual Retry save or explicit Discard session handles failure; duplicate submissions/retries are rejected. Returning managers observe the same owner. No service, automatic retry, crash recovery or silent discard; process death before commit may lose the pending session.
- 四小时：沿用刷新、订阅状态、三路事件及 Stop/Retry 入口检查；以 Running 单调起点判断达到 14,400,000 ms，在事件进入统计之前结束，原因 TIME_LIMIT、英文 Session time limit reached。重复结束不会再次结算。迟到调度按四小时结算区间、平均值和时长；endedAt 取观察时 Unix 时间减去单调超时时间，以当前日期时钟投影逻辑截止，不由日期差计算时长。图轴最多四小时，不制造边界点、不计入清理时间。原历史收集器仍独立保留含截止边界/14,401 桶防线；实际自动结束先于边界事件，因此不强求存在第 14,400 秒的点。
  Four-hour limit: Existing refresh, subscription, data, Stop and Retry entries enforce the monotonic Running deadline before new data changes statistics. At 14,400,000 ms the normal ending flow uses TIME_LIMIT and Session time limit reached. Duration/zones/means settle at the logical cutoff, excluding lateness and cleanup. endedAt projects that cutoff from the observed Unix clock minus monotonic overshoot; duration never uses wall-clock subtraction. No fabricated boundary point. Collectors retain their inclusive/capacity guards, while automatic ending precedes boundary events and does not require a final bucket.
- History 与隐私：同一 SensorActivity 内用简单 Session/History 按钮切换，现有计时/采集持有者继续存在，真正离开前台仍结束。列表及详情从 SQLite 读取，startedAt/ID 倒序游标分页，每次 20 条，Load more；按 ID 查看摘要、HR/步频整场曲线、五区间柱形及 Running 占比、未归类、缺失/设备/结束原因。复用既有 Canvas，实际 elapsedMs 横轴、null/断段不连线；零时长百分比 --，无有效 HR 明示。删除确认后同一事务删除该场三表行，取消不写库；不导出、不自动清理。Manifest 已引用的 legacy/cloud-backup/device-transfer XML 明确排除 sessions.db 及 journal/wal/shm，仅影响会话数据库，设备 SharedPreferences 不改；界面说明卸载/清数据丢失历史及提交前进程终止风险。
  History/privacy: Simple Session/History switching stays inside SensorActivity and retains active collection/timing; actual foreground departure still ends acquisition. SQLite supplies descending start-time/ID keyset pages of 20 and detail by ID. Detail reuses summary, full-session HR/cadence plots and zone bars, adds Running percentages and separate unclassified time, and preserves actual elapsedMs/null/break behavior. Zero-duration percentages show -- and missing valid HR is explicit. Confirmed deletion atomically removes that session's rows; cancellation writes nothing. No export/automatic expiry. Manifest-linked legacy/cloud/transfer XML excludes sessions.db and journal/wal/shm while leaving saved-device preferences unchanged. UI explains data loss on uninstall/clear and process death before commit.
- 单元测试与构建已通过：新增 SessionSaveControllerTest（6 项）、SessionTimeLimitTest（4 项），更新两项先前“到限不结束”的生命周期预期。覆盖提交/完成/失败/手动重试/丢弃/重复拒绝、无资格/零时长、页面 scope 取消不取消保存、返回新控制器 Start 限制、恰好截止/延迟/日期跳变/清理、不同结束入口与旧代次。运行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest，BUILD SUCCESSFUL；204 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings（另有 1 条 Hint）。不复用 6.1d 的结果作为本步证据。
  Unit/build checks passed: Added six save-controller and four time-limit tests and updated two previous no-auto-ending lifecycle expectations. Coverage includes save states/duplicates/retry/discard, eligibility, page-scope independence, new-controller Start blocking, exact/delayed deadlines, date changes, cleanup/endings and stale generations. :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest succeeded: 204 tests, zero failures/errors/skips; lint 0 errors, 18 warnings and one Hint. These are current-step results.
- 实际 SQLite/Compose 执行：在新建独立 Android 模拟器 emulator-5582（API 37 系统镜像）安装本轮 debug 和 androidTest APK，执行 SessionDatabaseTest 6 项、HistoryPanelTest 2 项，OK (8 tests)。实际 Android SQLite 已验证全部字段/精度/null/零/断段关闭重开、重复点导致三表回滚、同 ID 重试/重复保存不覆盖、无资格/零时长 ACC、45 条及同时间分页/插入新记录不重复、删除中途失败回滚与只删指定 ID；真实触发器故障经 SaveController 重试成功。Compose 已实际点击详情、Cancel、Delete 并核对数据库；已解析三套备份排除规则。测试数据库使用随机独立名称并在测试后删除。旧 AVD 的 ADB offline/安装阻塞未计为测试通过；改用项目 build 目录的全新 AVD 后完成验证，未修改用户原 AVD 配置或手机应用数据。执行日志：PolarH10ActivityViewer/build/step62-validation/instrumentation.txt（忽略的本地验证产物）。
  Actual SQLite/Compose execution: Installed the current debug and androidTest APKs only on a fresh isolated emulator-5582 (API 37 system image). Six SessionDatabaseTest and two HistoryPanelTest cases passed: OK (8 tests). Actual Android SQLite covered full-field/precision/null/zero/break round trips after close/reopen, three-table rollback on duplicate points, same-ID retries/duplicate preservation, eligibility/zero-duration ACC, 45-row/tied-time pagination with a concurrent newer insert, delete rollback and selected-ID isolation. A real failing trigger was recovered through SaveController retry. Compose exercised detail, Cancel and Delete against real rows; XML tests checked all three backup rule sections. Random dedicated test databases were removed afterward. Existing-AVD offline/install failures were not counted as success; validation completed using a fresh AVD in the ignored project build directory, without changing original AVD configuration or phone app data. Log: PolarH10ActivityViewer/build/step62-validation/instrumentation.txt (local ignored artifact).
- 真机待验证：未在 Samsung/H10 上安装或操作本轮版本。待执行：连接 H10 → Start → 走路/静止 → Stop 或断线/后台 → 等待 Saved → 关闭重启 App → History 核对日期、时长、摘要和整场曲线；超过 60 秒并 Retry，核对前后历史/断段。运行中切换 History、旋转，核对不会意外结束；后台/锁屏仍结束。确认取消删除不改变记录、确认删除只删单场。模拟器故障注入不等于真实手机存储满，真实失败恢复、设备迁移/云备份排除行为、四小时现场运行与绘图/保存性能仍待验证，不能宣称 H10 全链路通过。
  Pending hardware checks: This version was not installed or operated on Samsung/H10. Connect → Start → walk/stand → Stop or disconnect/background → wait for Saved → restart app → verify History dates, duration, summaries and full curves. Test beyond 60 seconds and across Retry. Switching History and rotating should retain the active session, while actual background/lock ends it. Cancel/confirm single-session deletion. Emulator fault injection is not a real full-storage phone test; hardware failure recovery, OS backup/transfer behavior, real four-hour running and performance remain pending. No claim of complete H10 end-to-end validation.
- 状态与范围：两份 AGENTS.md、两份 prompt.md 同步中英文实施及证据。仅完成 6.2 及授权的最简单查询/详情/删除入口；无第 7/8 阶段扩展、无算法调参、无新依赖。未 commit/push；测试数据只在测试代码和专用测试数据库，不写入生产 History。
  Status/scope: Both AGENTS.md and prompt.md pairs contain synchronized bilingual implementation/evidence. Only 6.2 and its authorized minimal History entry points; no stage 7/8 expansion, algorithm tuning or dependencies. No commit/push. Synthetic fixtures remain in test code and dedicated test databases, never production History.
Suggested commit message (not committed):
```text
feat: persist sessions with save recovery and minimal history
```

# 第 7 阶段：History 查询交互与验收 / Stage 7: History interactions and verification

- 2026-09-30 用户采用全部推荐规则，依据根目录 AGENTS.md 5.27。20 条为每次加载数量，不是保存上限；手动 Load more，不自动滑底加载、不自动清理。
  On 2026-09-30 the user accepted all recommended rules in root AGENTS.md Section 5.27. Twenty records is a page size, not a storage limit. Use manual Load more, with no automatic loading on scroll or automatic deletion.
- 规则确认时只定义规则与提示词；随后分别按用户指定实施 7.1、7.2，实际结果见本文件末尾及 AGENTS.md 5.27.1—5.27.2。不将 6.2 的测试结果作为第 7 阶段通过证据。
  The rule-confirmation update defined rules and prompts only. Steps 7.1 and 7.2 were subsequently implemented at the user's separate requests; actual results appear at the end of this file and in AGENTS.md Sections 5.27.1–5.27.2. Previous 6.2 results do not prove stage 7 passed.

## 步骤 7.1：历史列表 / Step 7.1: History list

### 中文

```text
仅实施步骤 7.1，遵循根目录 AGENTS.md 第 5.27 节，直接修改文件。

先检查 6.2 当前的 SessionDatabase、HistoryPanel 及保存状态接线。缺少必要前置时说明并停止，不自动补做其他步骤；已有符合规则的实现直接复用，只补实际缺口。

1. 列表必须来自 SQLite，沿用 startedAt DESC、id DESC 游标分页，每次最多 20 条。显示 Running 开始日期时间、时长、步数、累计估计距离及不完整提示；无记录显示 No saved sessions。20 条不是保存上限，不自动清理记录。
2. 向下滚动后用 Load more 加载较早记录，不自动滑底加载、不查询总数。返回不足 20 条时隐藏按钮；恰好满 20 条允许再请求一次确认末页。查询期间禁用重复请求。
3. 首次加载失败显示英文错误及 Retry query；分页失败保留已有列表与游标，重试同一失败页，不回到第一页。取消或旧页面/旧选择的查询结果不得覆盖当前页面。
4. 从详情返回列表、旋转或重新进入 History 时重新加载前 20 条，不要求恢复页数或滚动位置。新的保存成功且正在显示列表时刷新前 20 条；正在显示详情时保留所选会话，返回列表再刷新。切换 History 或旋转不得停止采集，真正离开前台沿用既定结束规则。
5. 列表日期按查看时手机当前时区显示 yyyy-MM-dd HH:mm:ss XXX；重新进入/查询或页面重建时读取时区，不增加时区监听框架。沿用 6.2 单位、舍入及 null/真实零规则，不改数据库时间或摘要算法。
6. 本步重新使用实际 SQLite/Compose 验证空/多记录、同时间排序、超过 20 条和满页边界、Load more、失败原页重试、保存刷新、返回/旋转及旧查询过滤；验证数据库关闭重开后的读取。未执行的采集/设备检查标 pending，不将测试数据当作真机证据。
7. 运行相关测试、debug 构建及 lint；同步两份 AGENTS.md 与两份 prompt.md 的实际结果，中英文区分文件修改、自动检查及设备行为。提供对应英文 commit message，不自动 commit/push。

使用最小代码、当前 Compose 主题和英文 UI/注释。保持现有详情/删除可用，不实施 7.2 的改进或第 8 阶段布局/清理，不增加筛选、导出、批量删除、自动清理、依赖、通用框架或无关重构。
```

### English

```text
Implement only step 7.1, following Section 5.27 of the root AGENTS.md. Modify the files directly.

Inspect the current 6.2 SessionDatabase, HistoryPanel and save-state wiring first. If prerequisites are missing, explain and stop without implementing other steps. Reuse existing compliant behavior and fill only actual gaps.

1. Read the list from SQLite using the existing startedAt DESC, id DESC keyset pagination, with at most 20 records per page. Show the Running start date/time, duration, steps, accumulated estimated distance and incompleteness indication. Show No saved sessions when empty. Twenty is a page size, not a storage limit; do not automatically delete records.
2. Use Load more after scrolling to load older records. Do not load automatically on scroll or query a total count. Hide the button after a page returns fewer than 20 records; a full page may require one more request to discover the end. Disable duplicate requests while querying.
3. Show an English error and Retry query on initial-load failure. On pagination failure, retain the loaded list and cursor and retry the same failed page without returning to page one. Cancelled or stale page/selection queries must not overwrite the current screen.
4. Reload the first 20 records after returning from detail, rotating or re-entering History; restoring loaded pages or scroll position is not required. A new successful save refreshes the first page when the list is visible. While detail is visible, retain the selected session and refresh the list on return. History switching and rotation must retain active acquisition; actual foreground departure keeps the existing ending rules.
5. Format list dates as yyyy-MM-dd HH:mm:ss XXX in the phone's current viewing timezone. Read the timezone when re-entering, querying or rebuilding the screen; do not add a timezone-listener framework. Reuse 6.2 units, rounding and null-versus-zero behavior without changing stored times or summary algorithms.
6. Run fresh actual SQLite/Compose checks for empty/multiple records, tied-time ordering, more than 20 records and full-page boundaries, Load more, failed-page retry, save refresh, return/rotation and stale-query rejection. Verify reads after database close/reopen. Mark unperformed acquisition/device checks pending; fixtures are not hardware evidence.
7. Run relevant tests, a debug build and lint. Synchronize both AGENTS.md files and both prompt.md files with actual results in Chinese and English, separating modified files, automated checks and device behavior. Provide an English commit message matching the changes; do not commit or push automatically.

Use minimal code, the current Compose theme and English UI/comments. Preserve existing detail/delete behavior without implementing step 7.2 improvements or stage 8 layout/cleanup. Add no filtering, export, bulk deletion, automatic cleanup, dependencies, generic frameworks or unrelated refactoring.
```

## 步骤 7.2：详情与确认删除 / Step 7.2: Detail and confirmed deletion

### 中文

```text
仅实施步骤 7.2，遵循根目录 AGENTS.md 第 5.27 节，直接修改文件。

先检查 6.2 与 7.1 的当前输出。缺少必要前置时说明并停止，不自动补做其他步骤。复用既有按 ID 查询、摘要、基础图表和删除事务，只补实际缺口。

1. 按会话 ID 从 SQLite 查询同一场摘要及两类历史。沿用 5.23.5 的 HR/步频整场曲线、HR 与步频统计、五区间时长及 Running 占比、独立未归类时间、Running 时长、步数、估计距离与速度、设备、结束原因及完整性。不要从历史点重新计算摘要；null 显示 --，真实零显示 0；断段不连线、无数据不补点，零时长占比为 --，无有效 HR 明示 No valid HR data。
2. 旋转保留所选 ID 并重新查询详情；新会话保存成功不得切换或重置正在查看的详情，返回列表后再刷新前 20 条。查询失败显示英文错误及 Retry query，按原 ID 重试；查不到记录显示 Session not found 和 Back。取消或旧选择的查询结果不得覆盖当前选择。
3. 列表、详情及删除确认统一按查看时手机当前时区显示 yyyy-MM-dd HH:mm:ss XXX。重新进入/查询或页面重建时读取当前时区，不增加监听框架，不改数据库 Unix 毫秒、单调时长或曲线横轴。
4. 删除确认弹窗显示所选会话的 Running 开始日期时间。Cancel 不写数据库；确认后一个事务只删除该 ID 的摘要和全部 HR/运动历史。删除期间禁止重复操作；失败保留详情与数据库记录并允许再次 Delete，不显示成功；成功返回列表并重新加载前 20 条。
5. 本步重新使用实际 SQLite/Compose 验证不同 ID 的详情一致性、null/零/断段、零时长占比、无有效 HR、旋转选择、新保存不切换详情、查询失败重试及不存在；验证确认日期、取消、仅删除指定 ID、删除失败回滚与重试。检查视图切换/旋转保留采集，未执行的设备项标 pending。
6. 运行相关测试、debug 构建及 lint；同步两份 AGENTS.md 与两份 prompt.md 的实际结果，中英文区分文件修改、自动检查及设备行为。不得复用旧结果宣称本步通过；提供对应英文 commit message，不自动 commit/push。

使用最小代码、当前 Compose 主题和英文 UI/注释。不实施第 8 阶段正式布局或临时显示清理，不增加速度曲线、ECG 历史回放、缩放/拖动、筛选、导出、批量删除、自动清理、依赖、数据库表、通用框架、算法调参或无关重构。
```

### English

```text
Implement only step 7.2, following Section 5.27 of the root AGENTS.md. Modify the files directly.

Inspect the current 6.2 and 7.1 outputs first. If prerequisites are missing, explain and stop without implementing other steps. Reuse existing ID queries, summaries, basic plots and deletion transactions; fill only actual gaps.

1. Query the selected session's summary and both history series from SQLite by session ID. Retain the Section 5.23.5 full-session HR/cadence plots, HR/cadence statistics, five zone durations and Running percentages, separate unclassified time, Running duration, steps, estimated distance/speeds, device, end reason and completeness. Do not recalculate summaries from history points. Show -- for null and 0 for genuine zero; do not connect breaks or fabricate points. Show -- for zero-duration percentages and No valid HR data when appropriate.
2. Retain the selected ID across rotation and requery detail. A newly successful save must not switch or reset the visible detail; refresh the first 20 list records on return. On query failure, show an English error and Retry query for the same ID. Show Session not found and Back when absent. Cancelled or stale-selection queries must not overwrite the current selection.
3. Use yyyy-MM-dd HH:mm:ss XXX consistently for list, detail and deletion confirmation in the phone's current viewing timezone. Read the timezone on re-entry, query or screen rebuild without adding a listener framework. Do not change stored Unix milliseconds, monotonic duration or chart elapsed axes.
4. Include the selected session's Running start date/time in the deletion confirmation. Cancel performs no database writes. Confirm atomically deletes only that ID's summary and all HR/motion points. Block duplicate operations during deletion. On failure, retain detail and database rows and allow Delete again without reporting success. On success, return to the list and reload the first 20 records.
5. Run fresh actual SQLite/Compose checks for detail consistency across IDs, null/zero/breaks, zero-duration percentages, no valid HR, selected-ID rotation, new saves retaining detail, query retry and missing records. Verify confirmation dates, cancellation, selected-ID isolation, deletion rollback and retry. Check that view switching/rotation retains acquisition; mark unperformed device checks pending.
6. Run relevant tests, a debug build and lint. Synchronize both AGENTS.md files and both prompt.md files with actual results in Chinese and English, separating modified files, automated checks and device behavior. Do not reuse previous results as proof this step passed. Provide an English commit message matching the changes; do not commit or push automatically.

Use minimal code, the current Compose theme and English UI/comments. Do not implement stage 8 layout or development-display cleanup. Add no speed plot, historical ECG replay, zoom/pan, filtering, export, bulk deletion, automatic cleanup, dependencies, database tables, generic frameworks, algorithm tuning or unrelated refactoring.
```

## 2026-09-30 步骤 7.1 实施结果 / Step 7.1 implementation results

- 前置与文件修改：检查 6.2 的实际 SQLite、保存状态和 History 接线，前置齐全。只修改 history/HistoryPanel.kt，新增 androidTest/storage/HistoryListTest.kt（8 项）；SessionDatabase、SensorActivity、采集持有者、算法、依赖及删除事务不变。
  Prerequisites/files modified: Verified the current 6.2 SQLite, save-state and History wiring. Updated only history/HistoryPanel.kt and added eight tests in androidTest/storage/HistoryListTest.kt. Reused SessionDatabase, SensorActivity, the acquisition owner, algorithms, dependencies and deletion transaction.
- 列表实现：在原 HistoryPanel 内提取私有 HistoryList，列表保存当前请求游标和已加载记录，Load more 每次最多 20 条；SQL 沿用 startedAt/ID 倒序游标，不查询总数、不自动滑底加载、不限制保存总数或清理旧记录。满页保留按钮，不足 20 条或空末页后隐藏。首次查询失败显示英文提示/Retry query；分页失败保留列表和原游标，Retry 重试该页，期间阻止重复操作。
  List implementation: A private HistoryList inside the existing file owns loaded records and the requested cursor. Manual Load more requests up to 20 rows using the unchanged descending start-time/ID SQL cursor. No total-count query, automatic scroll loading, storage-count cap or cleanup. A full page retains the button; a short or empty final page hides it. Initial failures show an English error/Retry query; pagination failures preserve rows and retry the same cursor, with duplicate operations blocked while loading.
- 刷新与取消：列表返回、重新进入或状态重建后加载前 20 条；成功保存信号重建列表查询，但不再触发所选详情重载。列表查询由自身 LaunchedEffect 管理，离开或刷新取消旧查询；接受结果前检查取消状态，旧查询及旧详情不覆盖当前显示。每次查询重新读取手机当前时区，日期格式 yyyy-MM-dd HH:mm:ss XXX；沿用 null/零、单位与小数显示。未增加时区监听、框架或第 8 阶段布局。
  Refresh/cancellation: Return, re-entry and state reconstruction load the first 20 rows. A committed-save signal refreshes the list without reloading selected detail. List queries belong to their own LaunchedEffect and are cancelled on disposal/refresh; cancellation is checked before accepting results, and obsolete detail is not rendered for another selection. Each query rereads the viewing timezone and uses yyyy-MM-dd HH:mm:ss XXX with existing null/zero, units and rounding. No timezone listener, framework or stage 8 layout.
- 自动检查已通过：本轮重新执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline，最终 BUILD SUCCESSFUL；204 项单元测试、0 failures/errors/skipped；debug 与测试 APK 构建通过；lint 0 errors、18 warnings、1 Hint。首次沙箱下载/缓存权限失败后使用现有缓存执行；修正测试误导入与两个导航测试的滚动操作后重新检查，失败尝试不计为通过。日志 build/step71-validation/gradle.txt。
  Automated checks passed: Fresh executions of :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline ended BUILD SUCCESSFUL. All 204 unit tests passed with zero failures/errors/skips; debug and test APK builds passed; lint reported 0 errors, 18 warnings and 1 Hint. Initial sandbox download/cache-access failures were resolved using the existing cache. A test import and two navigation test scroll actions were corrected before rerunning; failed attempts are not counted as passes. Log: build/step71-validation/gradle.txt.
- 实际模拟器检查已通过：仅在项目专用 emulator-5582（API 37）安装本轮 APK，运行新增 HistoryListTest 8 项及既有 SessionDatabaseTest 6 项、HistoryPanelTest 2 项，最终 OK (16 tests)。真实 SQLite/Compose 覆盖空列表、45 条及同时间倒序、关闭重开、40 条满页/空末页、真实表重命名故障后的首次和分页重试、分页保留/无重复、时区变化、null/零、不完整提示、新保存列表刷新/详情不重载、返回/重新进入/Compose 保存状态重建、SQLite 事务阻塞期间重复拒绝与迟到查询取消；同时重跑字段、事务回滚、去重和删除回归。测试仅使用随机命名的专用数据库，结束后删除；没有写入生产 History。最终日志 build/step71-validation/instrumentation.txt，初轮导航测试失败日志另存 instrumentation-first-attempt.txt（均为忽略的本地产物）。
  Actual emulator checks passed: Installed the current APKs only on dedicated emulator-5582 (API 37). Eight new HistoryListTest, six existing SessionDatabaseTest and two existing HistoryPanelTest cases passed: OK (16 tests). Actual SQLite/Compose covered empty lists, 45 tied-time descending rows, close/reopen, 40-row/full-page/empty-end behavior, real table-rename failures and initial/failed-page retry, preserved rows/no duplicates, timezone changes, null/zero/incompleteness, save-driven list refresh without detail reload, return/re-entry/Compose saved-state reconstruction, and duplicate blocking/late-query cancellation during an actual SQLite transaction lock. Field/transaction/deduplication/deletion regression checks were rerun. Random dedicated databases were removed afterward; fixtures never entered production History. Final log: build/step71-validation/instrumentation.txt; initial navigation-test failures: instrumentation-first-attempt.txt (ignored local artifacts).
- 真机行为 pending：本轮未在 Samsung/H10 上安装或操作版本。待验证运行中 Session/History 切换及真实 Activity 旋转仍保留采集、真正后台/锁屏结束、真实保存后列表刷新、App 重启查询和真实时区切换后的显示。Compose 保存状态重建仅是受控检查，不等于真实手机旋转或 H10 全链路；大量记录的现场滚动性能仍待验证。
  Hardware behavior pending: No installation or operation on Samsung/H10 this turn. Verify active Session/History switching and actual Activity rotation retain acquisition, true background/lock ends it, actual saves refresh the list, app restart reads history, and real timezone changes display correctly. Compose saved-state reconstruction is controlled evidence, not physical-phone rotation or H10 end-to-end validation. Large-list scrolling performance remains pending.
- 范围与交付：仅完成 7.1 列表交互及必需查询隔离；7.2 的详情/删除改进与第 8 阶段仍待用户指定。两份 AGENTS.md 与两份 prompt.md 同步中英文实际结果；没有自动 commit/push。
  Scope/delivery: Completed only 7.1 list interactions and necessary query isolation. Step 7.2 detail/delete improvements and stage 8 await a separate user request. Both AGENTS.md and prompt.md pairs contain synchronized bilingual results. No automatic commit/push.

Suggested commit message (not committed):
```text
feat: improve history list pagination retry and refresh
```

## 2026-10-01 步骤 7.2 实施结果 / Step 7.2 implementation results

- 前置与文件修改：重新检查 6.2、7.1 的 SQLite/History 与当前规则，前置齐全。修改 history/HistoryPanel.kt、session/SessionSummaryPanel.kt，新增 androidTest/storage/HistoryDetailTest.kt（9 项）。复用已有 SessionDatabase 按 ID 的一致性读取和三表删除事务、摘要、心率区间与 Canvas；不修改采集持有者、SDK、算法、数据库结构或依赖。
  Prerequisites/files modified: Verified current 6.2/7.1 SQLite/History behavior and rules. Updated history/HistoryPanel.kt and session/SessionSummaryPanel.kt; added nine tests in androidTest/storage/HistoryDetailTest.kt. Reused SessionDatabase's consistent ID-based reads and three-table deletion transaction, summaries, zones and Canvas. No acquisition-owner, SDK, algorithm, schema or dependency changes.
- 详情与时间：继续按所选 ID 从实际 SQLite 读取摘要及两类历史，不从曲线反算摘要。查询/重试重新读取当前时区；History 通过可选 DateTimeFormatter 参数复用 SessionSummaryPanel，列表、详情与删除确认统一 yyyy-MM-dd HH:mm:ss XXX，Session 开发摘要默认毫秒格式保留。确认删除时再次读取查看时区并显示该场 Running 开始时间。Unix 毫秒、单调时长、单位、null/真实零、区间占比及曲线 elapsedMs/断段不变；未新增图表或速度/ECG 回放。
  Detail/time: Selected IDs still read summaries and both series from actual SQLite without recalculating statistics from curves. Queries/retries reread the current timezone. History passes an optional DateTimeFormatter to the reused SessionSummaryPanel, aligning list/detail/confirmation to yyyy-MM-dd HH:mm:ss XXX while preserving the Session development summary's default millisecond format. Confirmation rereads the viewing timezone and includes that session's Running start. Stored Unix milliseconds, monotonic duration, units, null/genuine zero, zone percentages and elapsedMs/breaks are unchanged. No new plots or speed/ECG replay.
- 查询与删除恢复：详情查询失败提供固定英文错误和原 ID 的 Retry query，点击立即阻止重复请求；记录不存在显示 Session not found/Back。沿用所选 ID 保存状态恢复、新保存不重载详情及查询取消检查。删除错误与查询错误分开，失败保留详情及回滚后的记录，显示英文 Delete failed 并允许再次 Delete session，不误导用户重新查询。确认期间返回不会改变选择；删除进行中 Delete/Cancel/返回均不能重复或取消操作。成功后返回前 20 条；取消不写库。删除完成后的界面更新检查取消状态，不引入后台任务或恢复框架。
  Query/delete recovery: Detail query failures show a fixed English error and Retry query for the original ID, immediately blocking duplicates on click. Missing IDs show Session not found/Back. Selected-ID saved-state restoration, save-independent detail and cancellation checks are reused. Deletion errors are separate from query errors: failed transactions retain detail and rolled-back rows, show English Delete failed and permit another Delete session rather than a query retry. Confirmation retains the selection; Delete/Cancel/back cannot duplicate or dismiss an in-progress delete. Success returns to the first 20 list records; cancellation writes nothing. UI completion checks cancellation without adding background tasks or recovery frameworks.
- 自动检查已通过：本轮重新运行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline --rerun-tasks，BUILD SUCCESSFUL，81 项任务实际执行；204 项单元测试，0 failures/errors/skipped；debug/测试 APK 构建通过；lint 0 errors、18 warnings（0 Hint）。不沿用 7.1 的结果证明本步通过。日志 build/step72-validation/gradle.txt。
  Automated checks passed: Fresh :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline --rerun-tasks completed BUILD SUCCESSFUL with all 81 tasks executed. All 204 unit tests passed with zero failures/errors/skips; debug/test APK builds passed; lint reported 0 errors, 18 warnings and 0 Hint. Step 7.1 results are not reused as proof. Log: build/step72-validation/gradle.txt.
- 实际模拟器检查已通过：仅在项目专用 emulator-5582（API 37）安装本轮 APK，执行新增 HistoryDetailTest 9 项、HistoryListTest 8 项、HistoryPanelTest 2 项和 SessionDatabaseTest 6 项，首次完整执行即 OK (25 tests)。真实 SQLite/Compose 验证不同 ID 的已保存摘要及两类历史关闭重开；曲线坐标最大值与所选历史对应且摘要不从降频点反算；null/零/断段字段、未归类与区间占比、零时长/无有效 HR；列表/详情/确认日期与时区；原 ID 查询失败重试、记录不存在、Compose 保存状态恢复选择、新保存不切换详情；取消删除不写库、真实触发器引发删除中途失败后三表回滚并通过 UI 重试只删指定 ID、事务阻塞时重复 Delete/Cancel/系统返回防护，以及被取消的迟到详情查询。重跑 7.1 分页及既有保存/删除回归。所有测试使用随机独立数据库并清理，不写入生产 History；最终日志 build/step72-validation/instrumentation.txt（忽略的本地产物）。
  Actual emulator checks passed: Installed the current APKs only on dedicated emulator-5582 (API 37). Nine new HistoryDetailTest, eight HistoryListTest, two HistoryPanelTest and six SessionDatabaseTest cases passed on the first complete run: OK (25 tests). Actual SQLite/Compose covered reopened summaries/series across IDs; plot maxima matching selected history while summary statistics remain independent of downsampled points; null/zero/break fields, unclassified time/percentages, zero duration/no valid HR; consistent dates/timezones; same-ID query retry, absent rows, Compose selected-ID state restoration and new saves retaining detail; cancellation without writes, real-trigger three-table delete rollback followed by UI retry deleting only the selected ID, blocked-transaction duplicate Delete/Cancel/system-back rejection, and cancelled late detail queries. Stage 7.1 pagination and save/delete regressions were rerun. Random dedicated databases were removed; fixtures never entered production History. Log: build/step72-validation/instrumentation.txt (ignored local artifact).
- 真机行为 pending：本轮未在 Samsung/H10 上安装或操作版本。仍待实际采集中的 Session/History 切换及 Activity 旋转、后台/锁屏结束、真实保存/重启查询、日期变化后的查看、真机删除/失败恢复与长场曲线/列表性能。Compose 状态恢复不是实际手机旋转；模拟器故障注入不是手机存储满，绘图标签/数据检查不等于真实 H10 信号或像素级断段视觉验收。
  Hardware behavior pending: No installation or operation on Samsung/H10 this turn. Verify live Session/History switching and Activity rotation, background/lock ending, real saves/app-restart queries, viewing after date changes, hardware deletion/failure recovery and long-session chart/list performance. Compose state restoration is not physical-phone rotation; emulator fault injection is not full phone storage, and chart-label/data checks are not real H10 signal or pixel-level gap validation.
- 范围与交付：仅完成 7.2，未推进第 8 阶段布局或临时展示清理，没有筛选/导出/批量删除/自动清理、框架、后台采集或无关重构。两份 AGENTS.md 与两份 prompt.md 同步中英文实际修改和证据；未 commit/push。
  Scope/delivery: Completed only 7.2. No stage 8 layout/development-display cleanup, filtering, export, bulk deletion, automatic cleanup, frameworks, background acquisition or unrelated refactoring. Both AGENTS.md and prompt.md pairs contain synchronized bilingual changes/evidence. No commit/push.

Suggested commit message (not committed):
```text
feat: improve history detail dates and deletion recovery
```

## 步骤 8.0：共用主题与第一版尺寸 / Step 8.0: Shared theme and initial sizing

规则确认日期：2026-10-01。规则确认时仅同步文档；随后按用户指定实施 8.0，实际修改、构建/模拟器及未执行的真机检查见本文件末尾的 8.0 实施结果。
Rules confirmed on 2026-10-01. The initial confirmation updated documentation only. Step 8.0 was subsequently implemented at the user's request; actual changes, build/emulator results and pending hardware checks are recorded at the end of this file.

### 中文

```text
仅实施步骤 8.0，遵循 AGENTS.md 第 5.28 节，直接修改项目文件。

先查看现有 Color.kt、Theme.kt、Type.kt、欢迎页、SensorActivity、心率区间组件及 History 的引用。使用当前 Kotlin、Compose、Material 3，以最小修改定义并接入共用主题、基础字体和必要尺寸，不增加依赖或通用样式框架。

1. 关闭动态配色，跟随系统深浅模式；欢迎页、Session、History 使用同一固定主题。浅色：主色 #2563EB、背景 #F8FAFC、卡片 #FFFFFF、主要文字 #0F172A、次要文字 #475569。深色：主色 #60A5FA、背景 #0F172A、卡片 #1E293B、主要文字 #F1F5F9、次要文字 #CBD5E1。按钮和普通文字使用可读的前景色。
2. Zone 1—5 从低到高统一为绿 #22C55E、蓝 #3B82F6、黄 #EAB308、橙 #F97316、红 #EF4444。当前心率强度标识与 Session/History 五区间柱形复用同一配色定义。保留英文强度、Zone、bpm 范围；普通文字使用主题文字色，区间颜色可用色块或标记，避免黄色小字难读。心率阈值和统计规则不变。
3. 沿用默认字体家族，基础字号为页面标题 24 sp、区域标题 18 sp、正文 16 sp、次要统计 14 sp。正式 HR 主数值 56 sp、步频/速度主要数值 28 sp 留 8.2 接入；本步不提前重排指标。
4. 尺寸基线：页面/卡片内边距 16 dp，区域间距和卡片圆角 16 dp，内部间距 8/12 dp，图标 24 dp，最小触控范围 48 dp，实时曲线绘图区高 220 dp，五区间绘图区高 160 dp。只在本步实际需要的位置定义和复用尺寸；正式卡片、图表高度和控件布局按 8.1—8.5 接入，不提前建立未使用的样式抽象。
5. 字体跟随系统字号，内容允许纵向滚动，横屏沿用相同顺序；实际受影响的文字允许换行，避免截断数值、单位或按钮。保持英文 UI/代码注释，保留 --、真实零、必要状态、错误和恢复入口。
6. 不实施 8.1—8.5 正式布局、导航调整或临时开发显示清理。不修改 SDK、采集、算法、会话生命周期、保存、数据库或 History 查询规则。
7. 检查三页面的固定深浅主题、区间配色、基础字号、按钮/文字对比度及字体放大表现；运行相关检查、debug 构建和 lint。区分源码检查、实际视觉检查和 H10 真机验证，未执行项标 pending，不沿用旧测试结果。
8. 同步两份 AGENTS.md 与两份 prompt.md 的中英文实际结果，提供与修改对应的英文 commit message，不自动 commit/push。
```

### English

```text
Implement only step 8.0, following Section 5.28 of AGENTS.md. Modify the project files directly.

Inspect the existing Color.kt, Theme.kt, Type.kt, welcome screen, SensorActivity, HR zone components and History references first. Use the existing Kotlin, Compose and Material 3 setup with minimal changes to define and apply the shared theme, base typography and necessary dimensions. Add no dependencies or generic styling framework.

1. Disable dynamic colors and follow the system light/dark mode. Apply one fixed theme to Welcome, Session and History. Light: primary #2563EB, background #F8FAFC, card surface #FFFFFF, primary text #0F172A, secondary text #475569. Dark: primary #60A5FA, background #0F172A, card surface #1E293B, primary text #F1F5F9, secondary text #CBD5E1. Use readable foreground colors for buttons and ordinary text.
2. Set Zone 1–5, from lowest to highest, to green #22C55E, blue #3B82F6, yellow #EAB308, orange #F97316 and red #EF4444. Reuse one palette definition for the current intensity marker and Session/History zone bars. Keep English intensity labels, Zone names and bpm ranges. Use theme text colors for ordinary text and colored blocks or markers for zones to avoid unreadable small yellow text. Preserve HR thresholds and statistics.
3. Keep the default font family. Base sizes: page title 24 sp, section title 18 sp, body 16 sp, secondary statistics 14 sp. Reserve 56 sp for the formal HR value and 28 sp for cadence/speed values in step 8.2; do not rearrange metrics in this step.
4. Dimension baseline: page/card padding 16 dp, section spacing and card corners 16 dp, internal spacing 8/12 dp, icons 24 dp, minimum touch targets 48 dp, live plotting area height 220 dp and zone plotting area height 160 dp. Define and reuse only dimensions actually needed now. Apply formal cards, chart heights and control layouts in steps 8.1–8.5 without creating unused styling abstractions.
5. Respect system font scaling, allow vertical scrolling and keep the same content order in landscape. Let affected text wrap without clipping values, units or buttons. Keep English UI/comments, -- for unknown values, genuine zeros, necessary statuses, errors and recovery actions.
6. Do not implement steps 8.1–8.5, navigation changes or development-display cleanup. Preserve SDK calls, acquisition, algorithms, session lifecycle, saving, database and History query rules.
7. Check fixed light/dark themes across all three screens, zone colors, base typography, text/button contrast and enlarged fonts. Run relevant checks, a debug build and lint. Separate source inspection, actual visual checks and H10 hardware validation; mark unperformed checks pending and do not reuse old test results.
8. Synchronize both AGENTS.md files and both prompt.md files with actual results in Chinese and English. Provide a matching English commit message; do not commit or push automatically.
```

## 2026-10-01 步骤 8.0 实施结果 / Step 8.0 implementation results

- 文件与主题：修改 ui/theme/Color.kt、Theme.kt、Type.kt，新增 Dimensions.kt；接入 MainActivity.kt、SensorActivity.kt、history/HistoryPanel.kt、heartrate/HeartRateZonePanel.kt。删除旧紫色变量及动态配色路径，移除无用途的 dynamicColor 参数；按系统深浅模式选择固定蓝/石板色主题，设置 primary/onPrimary、background/onBackground、surface/onSurface、次要文字及实际 Material 容器颜色，避免默认紫色容器残留。三页面复用同一主题。
  Files/theme: Updated Color.kt, Theme.kt and Type.kt; added Dimensions.kt and applied them in MainActivity.kt, SensorActivity.kt, HistoryPanel.kt and HeartRateZonePanel.kt. Removed old purple colors, dynamic-color code and the unused dynamicColor parameter. System light/dark mode selects the fixed blue/slate palette, including readable button/text foregrounds and Material container colors. All three screens reuse the same theme.
- 字号与尺寸：titleLarge/titleMedium/bodyLarge/bodyMedium/bodySmall 分别采用 24/18/16/16/14 sp，沿用默认字体和可增长行高；欢迎页及 Session 标题采用 titleLarge。共用 PagePadding/SectionSpacing/ContentSpacing/ControlSpacing 为 16/16/8/12 dp，仅复用现有页面、卡片与按钮行中实际使用的间距；Material 默认触控范围保留。HR 56 sp、运动 28 sp、正式圆角/图标和 220/160 dp 绘图区仍留对应正式布局步骤，不建立未使用常量。
  Typography/dimensions: titleLarge/titleMedium/bodyLarge/bodyMedium/bodySmall use 24/18/16/16/14 sp with the default font and scalable line heights; Welcome and Session use titleLarge. Only currently used 16/16/8/12 dp padding/spacing values are shared. Material touch targets remain unchanged. Formal HR/motion sizes, corners/icons and 220/160 dp plotting heights remain assigned to their later layout steps without unused constants.
- 区间配色：Color.kt 的 HeartRateZoneColors 唯一列表为绿/蓝/黄/橙/红，当前有效强度旁的 12 dp 色块和 Session/History 的五柱复用它。强度文字使用 onSurface 并允许换行，保留英文强度、Zone、bpm 范围和 mm:ss，不使用黄色小字表示强度；无有效 HR 时无色块，继续显示 --。没有改变阈值、统计、采集、会话、保存或查询逻辑。
  Zones: One HeartRateZoneColors list supplies green/blue/yellow/orange/red to the current intensity marker and Session/History bars. The 12 dp marker appears only for a current valid zone. Intensity text uses onSurface and wraps; English labels, Zone names, bpm ranges, mm:ss and unknown values are preserved. No thresholds, statistics, acquisition, session, saving or query logic changed.
- 本轮自动检查：沙箱首次因 Gradle 缓存锁文件访问被拒绝而未开始构建；获准访问现有缓存后，执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline --console=plain，BUILD SUCCESSFUL（81 tasks：24 executed、57 up-to-date）；204 项单元测试，0 failures/errors/skipped；debug/测试 APK 构建通过；lint 0 errors、17 warnings。日志 build/step80-validation/gradle.txt，失败尝试保留为 gradle-sandbox-attempt.txt，不计为通过。代码文字/按钮颜色的 sRGB 对比度计算为 5.17:1—17.06:1，见 contrast.md；这是数值检查，不是 H10/视觉证据。
  Automated checks: The first sandbox attempt failed before building due to denied access to the existing Gradle lock file. After approved cache access, the four requested Gradle tasks succeeded: 81 tasks, 24 executed/57 up-to-date; 204 unit tests with zero failures/errors/skips; debug/test APK builds passed; lint 0 errors and 17 warnings. Fresh log: build/step80-validation/gradle.txt; the failed attempt is retained separately. Calculated text/button contrast is 5.17:1–17.06:1 in contrast.md, separate from visual or hardware evidence.
- 实际模拟器检查：仅启动项目 build 目录既有独立 Step62 AVD（emulator-5582、API 37），安装本轮 APK，重跑 HistoryDetailTest 9、HistoryListTest 8、HistoryPanelTest 2、SessionDatabaseTest 6，OK (25 tests)。沿用随机专用数据库与原测试清理，不向生产 History 写演示记录。日志 build/step80-validation/instrumentation.txt；这些既有检查验证 History/SQLite 回归，不将其默认测试主题当作实际 App 新主题的验证。
  Emulator regression: Installed the current APKs only on the isolated project Step62 AVD (emulator-5582, API 37). All 25 existing History/SQLite tests passed, using their dedicated random databases and cleanup. No fixture records were added to production History. Log: build/step80-validation/instrumentation.txt. These tests verify regression behavior; their default test theme is not evidence of the App's new theme.
- 实际视觉检查：通过真实 App 导航检查 Welcome、未连接/Idle Session 和空 History；浅/深各按系统 font_scale 1.0、2.0 拍摄并查看共 12 张 1080×2400 截图及对应 UI XML，确认页面背景/文字/按钮配色、标题/正文换行与导航可用。深色欢迎页另等待系统栏动画稳定后复核 dark-welcome-settled.png。截图、脚本及日志在 build/step80-validation；检查后恢复模拟器原字号 1.0 和夜间模式 no。未连接 H10，没有运行采集或向 History 填充演示记录；此项只覆盖截图可见区域，不能声称所有下方开发控件、横屏、真实曲线或已保存详情均已完成视觉验收。
  Actual visual checks: Used real App navigation for Welcome, disconnected/Idle Session and empty History. Inspected 12 screenshots (1080×2400) and UI XML across light/dark mode and system font scales 1.0/2.0. Visible theme colors, wrapping and navigation were checked; the dark welcome status bar was rechecked after settling. Original font scale/night mode were restored. Screenshots/scripts/logs are in build/step80-validation. No H10 connection, acquisition or demonstration records; visual coverage is limited to captured regions, not all lower controls, landscape, live curves or saved details.
- 真机及范围：未在 Samsung 手机安装或操作；H10 有效 HR 下的色块/五柱实际颜色、接收数据、旋转/横屏、长标签/放大字号下的全部区域和设备生命周期仍 pending。8.1—8.5 正式布局与临时展示清理未实施；SDK、依赖、算法、存储及业务行为保持原实现。两对文档同步中英文实际证据；无自动 commit/push。
  Hardware/scope: No Samsung installation or operation. H10-driven markers/bars, data reception, actual rotation/landscape, complete enlarged-font coverage and hardware lifecycle remain pending. Steps 8.1–8.5 and development-display cleanup were not implemented. SDK, dependencies, algorithms, storage and business behavior retain their existing implementation. Both documentation pairs are synchronized; no commit/push.

Suggested commit message (not committed):
```text
feat: apply fixed app theme and shared heart rate zone colors
```

## 步骤 8.1：连接、电量与心率强度 / Step 8.1: Connection, battery and HR intensity

规则确认日期：2026-10-01。确认规则时只写文档；用户随后要求实施，8.1 App 已写入。以下中英文提示词保留，实际结果见本节末尾；H10 真机 pending。
Rules confirmed on 2026-10-01 with documentation-only changes at that time. Step 8.1 was subsequently implemented at the user's request. The bilingual prompts remain below; actual results follow at the end of this section. H10 validation remains pending.

### 中文

```text
仅实施步骤 8.1，遵循 AGENTS.md 第 5.29 节，直接修改项目文件。

先检查 8.0 主题、SensorActivity 的现有连接/权限/电量/扫描/就绪 UI、HeartRateZonePanel 和 History 引用。复用当前 ViewModel 与管理器状态及回调，仅整合区域 1、2，不改变采集与业务逻辑。

1. Session 内容顶部左侧为可点击连接区域，显示蓝牙图标、英文状态和 Battery: 百分比/--；右侧为非交互 Heart rate intensity。沿用 8.0 主题、16 dp 边距/圆角、12 dp 间距、24 dp 图标及最小 48 dp 触控范围。常规宽度均分；文字换行、区域增高，必要时按连接→强度上下排列。随现有内容滚动，不新增固定导航。
2. 点击左侧打开 Devices，即使权限缺失或蓝牙关闭也可查看原因；打开不自动授权/扫描/连接。可用性异常优先显示对应英文状态，否则显示真实 Not connected/Connecting/Connected/Disconnecting。仅有效当前连接回调提供电量，未知/失效为 --，不把 Connected 当成 Ready/Receiving。
3. 弹窗内容可滚动，Close 始终可访问；顺序为当前状态/Current device → 必要蓝牙操作及简短就绪/配置问题/Recheck data readiness → Saved devices → Nearby Polar H10 devices 和扫描操作。显示已有名称、ID、最近连接时间或 RSSI；保存记录不表示在线。复用原去重、30 秒扫描停止、单台连接、10 秒连接超时、防重复、Disconnect/Retry disconnect 与手动恢复。配置不完整时仅展示解决问题所需选项，不保留全部开发参数常驻列表。
4. Close/返回/点击弹窗外关闭时停止活跃扫描并保留结果；不主动断开、不取消已接受连接、不结束运行 Session、不启动数据流。旋转保留弹窗打开状态及现有 ViewModel 数据，不重新发起操作；真正后台/锁屏/返回欢迎页继续既定清理。重新打开不自动扫描。数据流 Retry 留原指标/流区域。
5. 强度仅消费既有 current；显示英文强度与 Zone，并复用绿/蓝/黄/橙/红颜色标记，文字使用 onSurface。无当前有效区间显示 -- 和必要原因；Stop/中断显示 --/Stopped，累计五区间保留。不得用历史最大/平均 HR 推导强度，不改阈值、接触规则、保持计时，不增加平滑、动画或超时。
6. 迁移后删除被替代的 Session 连接/电量/设备/扫描重复 UI，以及被弹窗替代的详细就绪展示；拆出当前强度标签，避免重复。保留 History 累计五柱、所有底层状态/权限/订阅/资源清理及必要恢复。8.2 的正式五柱与主指标、8.3 曲线、8.4 导航/控制、8.5 History 和其他开发区域不在本步调整。
7. 验证状态/电量、弹窗顺序、无自动操作、扫描关闭与结果保留、防重复、权限/连接/配置恢复、旋转和关闭不意外重置会话；核对强度无效/失败/停止/恢复与累计五柱。实际 UI 检查深浅模式、长名称及 1.0/2.0 字号；受控数据、模拟器和 H10 结果分开记录，未执行项标 pending。运行相关检查、debug 构建及 lint，同步两份 AGENTS.md 和两份 prompt.md 的中英文实际结果，提供英文 commit message，不自动 commit/push。

最小代码；不加依赖、框架、SDK API、存储字段或无关重构，不实施 8.2—8.5。
```

### English

```text
Implement only step 8.1, following Section 5.29 of AGENTS.md. Modify project files directly.

Inspect the step 8.0 theme, existing connection/permission/battery/scan/readiness UI in SensorActivity, HeartRateZonePanel and History references first. Reuse current ViewModel/manager states and callbacks. Integrate only regions 1 and 2 without changing acquisition or business logic.

1. Add a clickable connection area at the top left of Session content, showing a Bluetooth icon, English status and Battery: percentage/--. Show noninteractive Heart rate intensity on the right. Use the 8.0 theme, 16 dp padding/corners, 12 dp spacing, 24 dp icons and minimum 48 dp touch targets. Normally share the width equally; allow wrapping/growing and stack connection before intensity when necessary. Scroll with existing content without adding fixed navigation.
2. Open Devices from the left area even when permissions or Bluetooth are unavailable. Opening must not automatically request permissions, scan or connect. Availability problems take display priority; otherwise show the real Not connected/Connecting/Connected/Disconnecting state. Battery comes only from existing callbacks for a valid current connection; unknown/invalid values are --. Connected is not Ready or Receiving.
3. Make dialog content scrollable with Close accessible. Order: current status/Current device → necessary Bluetooth actions and concise readiness/configuration problems/Recheck data readiness → Saved devices → Nearby Polar H10 devices and scan controls. Retain existing names, IDs, last connection time or RSSI; saved does not mean online. Reuse deduplication, 30-second scan stop, single-device connection, 10-second connection timeout, duplicate prevention, Disconnect/Retry disconnect and manual recovery. Show only configuration options needed to resolve an actual blocked configuration, not permanent development parameter lists.
4. Close/back/outside dismissal stops active scanning and retains results. It must not disconnect, cancel an accepted connection, end a running Session or start streams. Retain dialog visibility and existing ViewModel data across rotation without repeating operations. Actual background/lock/welcome departure follows existing cleanup. Reopening does not auto-scan. Keep stream Retry in existing metric/stream areas.
5. Consume only the existing current zone. Show its English intensity/Zone with the shared green/blue/yellow/orange/red marker and onSurface text. Without a current valid zone, show -- and the necessary existing reason. Stop/interruption shows --/Stopped while accumulated zones remain. Do not infer intensity from historical maximum/mean HR or change thresholds, contact rules or held-reading timing. Add no smoothing, animations or freshness timeout.
6. Remove replaced duplicate Session connection/battery/device/scan UI and detailed readiness presentation after migration. Separate current intensity from cumulative bars to avoid duplication. Preserve History bars, underlying state/permissions/subscriptions/cleanup and necessary recovery actions. Do not rearrange formal metrics/bars, charts, navigation/controls, History or unrelated development areas assigned to steps 8.2–8.5.
7. Verify statuses/battery, dialog ordering, no automatic operations, dismissal/retained scan results, duplicate prevention, permission/connection/configuration recovery, rotation and closing without accidental Session reset. Check invalid/failed/stopped/recovered intensity and retained cumulative bars. Inspect actual light/dark UI, long names and 1.0/2.0 fonts. Separate controlled, emulator and H10 evidence; mark unperformed checks pending. Run relevant checks, a debug build and lint. Synchronize both AGENTS.md files and both prompt.md files with actual bilingual results; provide an English commit message without automatically committing or pushing.

Use minimal code. Add no dependencies, frameworks, SDK APIs, storage fields or unrelated refactoring. Do not implement steps 8.2–8.5.
```

### 本轮实际结果 / Actual implementation results（2026-10-01）

- 源码检查与文件：检查了 8.0 主题/字号/尺寸、SensorActivity 的权限/连接/电量/扫描/就绪与采集入口、现有管理器回调及 History 对 HeartRateZonePanel 的引用。新增 session/SessionHeader.kt、ble/DevicesDialog.kt、drawable/ic_bluetooth.xml；修改 SensorActivity.kt、HeartRateZonePanel.kt、ui/theme/Dimensions.kt；新增 androidTest/session/SessionHeaderTest.kt（10 项）。
  Source inspection/files: Inspected the shared theme/typography/dimensions, existing permission/connection/battery/scan/readiness UI and acquisition entry points, manager callbacks and History's zone-panel references. Added SessionHeader.kt, DevicesDialog.kt and ic_bluetooth.xml; updated SensorActivity.kt, HeartRateZonePanel.kt and Dimensions.kt; added ten tests in SessionHeaderTest.kt.
- 顶部与弹窗：连接卡点击打开 Devices，异常可用性优先、有效当前连接才显示电量；正常宽度两栏，大字号/窄宽按连接→强度纵排。复用 16 dp 边距/圆角、12 dp 间距、24 dp 图标和最小 48 dp 触控范围。弹窗采用受限高度滚动内容和独立 Close，保留当前→已保存→附近设备顺序、必要蓝牙操作/错误、简短就绪与未确认选项、原扫描/连接/断开/重试/重新检查回调及启用条件。打开/重开没有自动操作；统一关闭入口仅停止活跃扫描，保留结果；rememberSaveable 保存打开状态，不增加生命周期操作。
  Header/dialog: Availability problems take priority and battery requires a valid current connection. Two columns stack in connection/intensity order when width or font scale requires it. Reuses 16 dp padding/corners, 12 dp spacing, 24 dp icons and a minimum 48 dp connection target. Devices has bounded scrolling and an independent Close button, current/saved/nearby order, necessary Bluetooth errors/actions and concise readiness/unconfirmed options. Existing callbacks and guards remain. Opening/reopening starts no operation; dismissal only stops an active scan and retains results. Dialog visibility uses rememberSaveable without new lifecycle operations.
- 强度及替代清理：仅消费已有 current，显示英文强度与 Zone 和共用五色标记，普通文字使用主题前景色。无效/失败/停止显示 -- 与原因、无色块；累计时长不清零。从 Session 移除被替代的连接/电量/设备/扫描和 DataReadinessPanel；从累计柱形拆出当前强度，保留 History 柱形、英文区间/bpm、累计说明、原尺寸和其他指标/流 Retry。
  Intensity/replaced UI: Uses the existing current zone, English intensity/Zone and shared palette with theme foreground text. Invalid/failed/stopped states show -- and a reason without a marker; totals remain. Removed replaced connection/battery/device/scan UI and DataReadinessPanel, and separated current intensity from cumulative bars. History bars, English ranges/bpm, cumulative explanations, existing heights and other metrics/stream Retry remain.
- 自动检查：最终执行 `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline --console=plain`，BUILD SUCCESSFUL。本轮 XML 为 204 tests、0 failures、0 errors、0 skipped；lint 0 errors、15 warnings。报告为 app/build/reports/tests/testDebugUnitTest/index.html、app/build/reports/lint-results-debug.html；APK 为 app/build/outputs/apk/debug/app-debug.apk。
  Automated checks: Final Gradle run succeeded for unit tests, debug APK, lint and test APK. Fresh XML reports contain 204 tests with zero failures/errors/skips; lint has zero errors and 15 warnings. Report and APK paths are listed above.
- 受控模拟器检查：独立 API 37 emulator-5582 最终 `OK (35 tests)`：新增 10 项顶部/Devices Compose 检查及原 25 项 History/SQLite 回归。覆盖状态/电量失效、打开无自动操作、关闭活跃扫描保留结果、返回键不触发断开/Stop、状态恢复不重复扫描、保存设备 ID 与连接期间禁用、配置问题/忙碌重查防护、五档强度与无效/失败/停止/恢复、累计值保留。运行中的会话、设备与 HR 是明确受控 UI 状态和计数回调；History 回归使用实际 SQLite。测试定位修正了同名提示匹配及 Espresso 返回键误选底层窗口，最终明确选择 Dialog root；未放宽生产行为。另在真实系统字号 2.0 下重跑长设备名视觉用例，`OK (1 test)`。
  Controlled emulator checks: All 35 tests passed: ten new header/Devices Compose tests and 25 existing History/SQLite regressions. Covers statuses/invalid battery, no automatic actions, retained scan results on dismissal, Back without disconnect/Stop, saved-state restoration without repeat scanning, saved ID routing/disabled duplicate actions, blocked configuration/busy recheck, all intensity labels and invalid/failed/stopped/recovered states with retained totals. Session/device/HR states and callbacks are controlled fixtures; History tests use real SQLite. Corrected ambiguous text matching and Espresso targeting the underlying window; the final Back test explicitly targets the Dialog root without relaxing production behavior. The long-name visual test also passed with actual system font scale 2.0.
- 实际视觉检查：已打开实际 App，检查浅/深 × 1.0/2.0 系统字号的 Session 顶部与 Devices（8 张），深色 2.0 横屏顶部/弹窗（2 张），及浅/深 2.0 滚动到底部扫描按钮（2 张）；Close 在所查视口可访问，状态/电量/占位文字换行，扫描按钮在无权限时禁用，打开不弹授权或扫描。实际弹窗旋转后仍打开，Close/系统返回/外部点击可关闭。另检查受控浅/深长名称/ID 与 Zone 3 色块截图；实际系统 2.0 长名称/ID 完整换行。截图只证明所查视口；五档色值及 History 共用配色按源码确认，未完成 H10 驱动的五档色块/柱形视觉验收。
  Actual visual checks: Inspected the real App's Session header/Devices in light/dark modes at system font scales 1.0/2.0 (eight captures), dark 2.0 landscape header/dialog (two), and both themes at 2.0 scrolled to scan controls (two). Close remains accessible, text wraps, scan controls are disabled without permissions and opening triggers no authorization/scan. The actual dialog survives rotation and dismisses via Close, Back and outside taps. Also inspected controlled long-name/ID and Zone 3 captures, including actual system font scale 2.0. Evidence is limited to inspected viewports; five palette values and History reuse were checked in source. H10-driven five-zone marker/bar visual acceptance remains pending.
- 真机与边界：本轮未安装或操作 Samsung/H10。真实权限拒绝/设置/蓝牙开关、30 秒扫描/10 秒连接超时、真实连接/电量/断开恢复、运行采集时弹窗关闭/旋转/后台和真实 HR 接触/失败/五档恢复仍 pending；受控模拟器结果不代替硬件验收。未修改 SDK/API、管理器、采集、算法、阈值/统计、保存/数据库/History 查询、依赖、导航或 8.2—8.5。两份 AGENTS.md 和两份 prompt.md 同步中英文实际结果；未 commit/push。
  Hardware/scope: No Samsung/H10 installation or operation this turn. Real permission/settings/Bluetooth recovery, 30-second scan/10-second connection timeout, connection/battery/disconnection recovery, dismissal/rotation/background during live acquisition and real HR contact/failure/five-zone recovery remain pending. Controlled emulator results do not replace hardware acceptance. No SDK/API, manager, acquisition, algorithm, threshold/statistics, saving/schema/History-query, dependency, navigation or step 8.2–8.5 changes. Both documentation pairs record actual bilingual results; no commit/push.
- 证据：本轮日志、检查脚本与截图在项目 build/step81-validation/；关键日志 gradle.txt、instrumentation.txt、controlled-system-font2.txt、visual-check.txt、scroll-check.txt。该目录为被忽略的本地构建证据，不自动纳入提交。
  Evidence: Local ignored build/step81-validation/ contains logs, scripts and captures; key logs are listed above and are not automatically included in a commit.
- 建议英文提交信息 / Suggested English commit message：`feat: integrate device dialog and session status header`

## 步骤 8.2：主指标、心率区间与运动汇总 / Step 8.2: Metrics, HR zones and activity summary

规则确认日期：2026-10-01。确认规则时只写文档；随后按用户要求实施 8.2。以下中英文提示词保留，实际结果见本节末尾；Samsung/H10 pending。
Rules confirmed on 2026-10-01 with documentation-only changes at that time. Step 8.2 was subsequently implemented at the user's request. Bilingual prompts remain below; actual results follow at the end of this section. Samsung/H10 validation remains pending.

### 中文

```text
仅实施步骤 8.2，遵循 AGENTS.md 第 5.30 节，直接修改项目文件。

先检查 8.0 主题、8.1 顶部/Devices、SensorActivity 的 HR/运动/区间/摘要/Retry 显示，以及 LatestHeartRate、StepState、HeartRateZonePanel、SessionSummaryPanel 和 History 共享引用。复用现有状态、算法结果、统计持有者、会话时间和恢复回调，只整合区域 3—6。

1. 将指标按 Heart rate → Motion → Heart rate zones → Activity summary 排成四张卡片。保留 8.1 当前强度入口，不重复显示。沿用系统深浅主题、默认字体、16 dp 页面/卡片边距/圆角/区域间距及 8/12 dp 内部间距。当前 HR 56 sp，当前步频/速度与汇总主数值 28 sp，区域标题 18 sp、正文/单位 16 sp、次要统计/接收时间/说明 14 sp；数字用 onSurface，次要文字用 onSurfaceVariant。只定义实际使用的样式与尺寸，不创建框架。
2. Heart rate 显示既有有效当前 reading 与 bpm、整场 Min/Max/Mean、Last received (phone)、必要 HR 状态/无接触/错误及原 Retry HR。Min/Max 为整数，Mean 一位小数；receivedAt 沿用英文日期及当前时区，不称采样时间。无效/失败/停止当前值和接收时间为 --，已取得统计继续保留。不要在 UI 从平均/最大值生成当前 HR 或新的统计。
3. Motion 的 Cadence 和 Estimated speed 常规等宽并排，大字体/窄宽按步频→速度纵排。步频显示整数 steps/min，速度仅展示时 m/s × 3.6、一位小数 km/h；步频下显示已有 Mean/Min/Max，速度下显示已有 Mean/Max。读取 StepState 已有结果，不改平均含整个 Running 时长、五秒合格窗口极值、两秒归零、初始预热/缺口/Retry 或步长/距离算法。保留必要 ACC 原因、不完整提示与原 Retry ACC 回调/条件；把该 Retry 移到指标附近并删除原位置重复按钮，ECG Retry 留原区域。
4. Activity summary 显示 Running duration (mm:ss)、Total steps、Estimated distance (m)。首版三项纵向标签—数值行，通常左标签/右数值，空间不足时每行标签上/值下。时长直接使用 session.elapsedMs、截断秒、分钟可超过 59，四小时为 240:00；包含静止/休息和已知缺失。总步数为已有整数，累计距离保留米及一位小数，不自动改 km。用这里的时长替代旧 Elapsed 文本，保持 Start/Stop 位置与启用条件、必要 Session 状态/结束原因，不提前实施 8.4。
5. Heart rate zones 保留五根纵柱，绘图区 160 dp（不含图题/比例尺/横轴/明细），柱宽沿用 24 dp、均分可用空间。共用从零到最大累计毫秒的线性比例尺，比例尺文本放图外；全零柱高为零，避免除零/假最小柱高。柱高按真实毫秒，显示时长截断秒为 mm:ss。横轴固定 Zone 1—5，可换行；图下五行明细完整显示色块、Zone、英文强度、bpm 范围和累计时长，必要时分两行。颜色只读 HeartRateZoneColors，低到高绿/蓝/黄/橙/红；所有标签使用可读主题文字色。保留 Unclassified time、Estimated from received HR、保持法/静默停流高估的简短说明及无数据/停止提示，不增加第六柱、Session 占比、训练建议或交互。
6. 保留 -- 与真实零的区别：正常初始 ACC 预热/等待确认零配状态；缺口/失败/Retry 重新预热按既有 --/原因；Stop 有 ACC 观测才当前归零、无观测仍 --。保留累计/极值/平均值、不完整标记、Stop 冻结与新 Start 清零、旋转保持和旧事件防护。距离/速度明确 Estimated，不用缺失值冒充静止，不新增补值、新鲜度超时、缓存或定时器。
7. 删除被四张卡片替代的 HR/Steps 开发标题和重复指标；SessionSummaryPanel 在 Session 只去掉已替代 HR/区间/运动统计，尚未替代的 UUID/日期/设备/保存资格/完整性信息及历史点/快照开发区域暂留。不得整体删除 History 使用的共享摘要。共享 HeartRateZonePanel 的视觉改进可用于 History，但其存储值、已有 Running 占比、摘要字段/顺序、查询/日期/删除保持，不实施 8.5。ACC/ECG 原始参数/样本开发区域和曲线正式布局留后续步骤，只移除已经迁入 Motion 的重复 Retry ACC。
8. 尊重系统字体缩放，允许纵向滚动，横屏保持相同顺序；数字/单位可分行，卡片增高，不截断、不缩字号或强制单行。检查初始/有效/无接触/失败/恢复、零与 --、停止有/无 ACC、平均/窗口极值/缺失、长数值、四小时、舍入、五档边界/配色、全零/不足一秒/不等时长/最大值变化、未归类、冻结/新场以及恢复按钮条件。实际检查浅/深、系统 1.0/2.0 字号、横屏、长状态/错误及滚动；运行相关 UI/统计及 History 共享组件回归、debug/测试 APK 构建和 lint。源码、受控/模拟器视觉、Samsung/H10 分开报告，未执行项标 pending，不引用 8.1 检查作为本步通过证据。
9. 同步两份 AGENTS.md 和两份 prompt.md 的中英文实际结果，提供英文 commit message，不自动 commit/push。不得改 SDK/API、依赖、采集、算法、阈值、统计、生命周期、保存/数据库/History 查询；不改 8.1 顶部/Devices，不实施 8.3 曲线及 220 dp 高度、8.4 控制/导航或 8.5 History，不加暂停/继续、动画、进度环或无关重构。

最小代码；保留英文 App 文本/代码注释，不增加常驻模拟数据或未使用的抽象。
```

### English

```text
Implement only step 8.2, following Section 5.30 of AGENTS.md. Modify project files directly.

Inspect the shared theme, step 8.1 header/Devices, existing HR/motion/zone/summary/Retry UI in SensorActivity, LatestHeartRate, StepState, HeartRateZonePanel, SessionSummaryPanel and their History references first. Reuse existing states, algorithm outputs, statistics owners, session timing and recovery callbacks. Integrate only regions 3–6.

1. Arrange four cards in this order: Heart rate → Motion → Heart rate zones → Activity summary. Keep current intensity solely in the step 8.1 header. Reuse system light/dark mode, the default font, 16 dp page/card padding/corners/section spacing and 8/12 dp internal spacing. Use 56 sp for current HR, 28 sp for current cadence/speed and summary values, 18 sp for section titles, 16 sp for body/units and 14 sp for secondary statistics/reception time/explanations. Use onSurface for numbers and onSurfaceVariant for secondary text. Define only styles/dimensions actually used; add no styling framework.
2. Heart rate shows the existing valid current reading with bpm, full-session Min/Max/Mean, Last received (phone), necessary HR/contact/error status and the original Retry HR. Min/Max are integers; Mean has one decimal. Retain English/current-timezone formatting for receivedAt and do not call it sampling time. Invalid/failed/stopped current values and reception time are --; existing statistics remain. Do not derive current HR or new statistics from maximum/mean values in UI.
3. Normally show Cadence and Estimated speed in equal-width areas inside Motion; stack cadence before speed at enlarged fonts or narrow widths. Display integer steps/min and speed as m/s × 3.6 with one decimal km/h, only rounding for display. Show existing cadence Mean/Min/Max below cadence and speed Mean/Max below speed. Consume StepState outputs; preserve whole-Running averages, qualifying five-second extrema, two-second zeroing, warmup/gap/Retry and stride/distance rules. Retain necessary ACC reasons/incomplete warnings and the original Retry ACC callback/guard. Move that Retry beside the metrics and remove its old duplicate button; keep ECG Retry in its current area.
4. Activity summary shows Running duration (mm:ss), Total steps and Estimated distance (m) as three vertical label/value rows. Normally place label left/value right; stack each label above its value if necessary. Read session.elapsedMs directly, truncate seconds and allow minutes above 59; four hours is 240:00. Duration includes stationary/rest and known missing periods. Keep integer steps and metres with one decimal, without automatic km conversion. Replace the old Elapsed text with this duration while retaining current Start/Stop positions/guards and necessary Session status/end reason. Do not implement step 8.4.
5. Keep five vertical HR-zone bars with a 160 dp plotting area, excluding title/scale/axis/details, existing 24 dp bar width and equally allocated horizontal space. Share a linear zero-to-maximum cumulative-millisecond scale and place scale text outside the plot. All-zero bars have zero height; avoid division by zero or artificial minimum bars. Heights use actual milliseconds; displayed mm:ss truncates seconds. Fix axis order to Zone 1–5 with wrapping. Below the plot, use five growing detail rows with swatch, Zone, English intensity, bpm range and duration; allow two lines. Read only HeartRateZoneColors in green/blue/yellow/orange/red order and use readable theme text for labels. Keep Unclassified time, Estimated from received HR, concise held-reading/silent-loss limitations and empty/stopped states. Add no sixth bar, Session percentages, training advice or chart interaction.
6. Preserve -- versus genuine zero. Normal initial ACC warmup/confirmation zeros include their state; gap/failure/Retry recovery uses existing --/reasons. Stop zeroes current motion only after ACC observations; without observations it remains --. Preserve totals/extrema/averages, incomplete flags, end freezing/new-Start resets, rotation retention and stale-event guards. Clearly label distance/speed Estimated. Do not treat missing values as stationary measurements or add imputation, freshness timeouts, display caches or timers.
7. Remove only replaced HR/Steps development titles and duplicate metrics. At the Session call site, remove only replaced HR/zone/motion statistics from SessionSummaryPanel; retain unreplaced identity/date/device/saving-eligibility/completeness and history-point/snapshot development information. Do not delete the shared summary used by History. History may reuse improved HeartRateZonePanel visuals, but stored values, existing Running percentages, summary fields/order and queries/dates/deletion remain. Do not implement step 8.5. Keep raw ACC/ECG parameter/sample development areas and formal chart work for later steps; remove only the duplicate Retry ACC already moved into Motion.
8. Respect system font scaling and vertical scrolling with the same landscape order. Allow numbers/units on separate lines and growing cards; use no clipping, ellipsis, font shrinking or forced single lines. Check initial/valid/contact-lost/failed/recovered states, zero/--, Stop with/without ACC, averages/window extrema/missing data, long values, four hours, rounding, all zone boundaries/colors, zero/subsecond/unequal/changing-maximum durations, unclassified time, freezing/new Start and recovery guards. Inspect actual light/dark UI, system font scales 1.0/2.0, landscape, long statuses/errors and scrolling. Run relevant UI/statistics and shared History regressions, debug/test APK builds and lint. Separate source inspection, controlled/emulator visual evidence and Samsung/H10 results; mark unperformed checks pending. Do not reuse step 8.1 results as step 8.2 evidence.
9. Synchronize both AGENTS.md files and both prompt.md files with actual bilingual results. Provide an English commit message without automatically committing or pushing. Preserve SDK/API, dependencies, acquisition, algorithms, thresholds/statistics, lifecycle, saving/schema/History queries. Do not change the step 8.1 header/Devices or implement step 8.3 charts/220 dp height, step 8.4 controls/navigation or step 8.5 History. Add no pause/resume, animations, progress rings or unrelated refactoring.

Use minimal code with English UI/comments. Add no permanent simulated data or unused abstractions.
```

### 规则确认时的文档结果 / Documentation result at rule confirmation

- 已将 8.2 推荐方案写入 AGENTS.md 第 5.30 节及步骤表，并同步两份 AGENTS.md、两份 prompt.md。The recommended step 8.2 plan is recorded in Section 5.30 and the step table, with both documentation pairs synchronized.
- 本轮只修改文档；已有 8.1 源码保持不变。8.2 实施、构建/lint、受控/实际视觉和 H10 验收 pending，未 commit/push。Only documentation changed this turn. Existing step 8.1 source remains unchanged. Step 8.2 implementation, build/lint, controlled/actual visual checks and H10 acceptance are pending; no commit/push.


### 本轮实际结果 / Actual implementation results（2026-10-01）

- 源码与文件：重新检查主题/字号/尺寸、SensorActivity、LatestHeartRate/StepState、区间/会话状态与摘要、History 共享调用。新增 session/SessionMetrics.kt，修改 SensorActivity.kt、HeartRateZonePanel.kt、SessionSummaryPanel.kt、Dimensions.kt；新增 androidTest/session/SessionMetricsTest.kt（13 项）。只读取已有结果，未修改采集/状态持有者。
  Source/files: Reinspected themes, typography/dimensions, SensorActivity, LatestHeartRate/StepState, zones/session/summary and shared History callers. Added SessionMetrics.kt; updated SensorActivity.kt, HeartRateZonePanel.kt, SessionSummaryPanel.kt and Dimensions.kt; added 13 tests in SessionMetricsTest.kt. UI consumes existing results without changing acquisition/state owners.
- 正式卡片：顺序为 Heart rate → Motion → Heart rate zones → Activity summary。HR 56 sp；当前步频/速度及汇总值 28 sp；标题 18 sp、正文/单位 16 sp、统计/说明 14 sp，默认字体与原主题不变。复用 16 dp 卡片边距/圆角、8/12 dp 内部间距。步频/速度通常等宽并排，窄宽/放大字体纵排；汇总标签—值行允许换行，四小时显示 240:00、距离一位小数 m。保留 --、真实零、均值/极值口径、接收时间含义及必要原因/错误。HR/ACC Retry 使用原回调与资格，ACC 仅保留 Motion 中一个按钮，恢复按钮最小 48 dp。
  Cards: Heart rate → Motion → Heart rate zones → Activity summary. HR uses 56 sp; current cadence/speed and summary values use 28 sp; titles/body-units/secondary text use 18/16/14 sp with the existing font/theme. Reuses 16 dp padding/corners and 8/12 dp inner spacing. Cadence/speed share width normally and stack for narrow/large-font layouts; summary label/value rows wrap. Four hours renders 240:00 and distance uses one decimal in metres. Retains --, genuine zero, existing statistical definitions, phone-reception meaning, statuses/errors and original HR/ACC retry callbacks/guards. One ACC Retry remains in Motion; recovery targets are at least 48 dp.
- 区间与共享摘要：五柱仅绘图区为 160 dp、柱宽 24 dp；共用实际累计毫秒的零—最大值比例尺，全零柱高为零，非零不足一秒仍按比例绘制。复用唯一绿/蓝/黄/橙/红配色，主题文字展示 Zone、强度、bpm 范围与累计时长，保留未归类、保持法/静默停流局限和停止说明。Session 的 showMetrics=false 仅隐藏已替代指标/计时；UUID、日期、设备、保存资格、冻结/结束原因、各流观测与完整性仍在。History 默认保留全部摘要字段、占比与共享区间视觉；历史计数/快照、ACC/ECG 原始开发区和 ECG Retry 保留。
  Zones/shared summary: Only the plot is 160 dp high, with 24 dp bars and a shared zero-to-maximum scale based on stored milliseconds. Zero totals have zero height; nonzero subsecond values retain proportional height. One shared green/blue/yellow/orange/red palette is used, with theme text for Zone/intensity/bpm ranges/durations. Unclassified time, held-reading/silent-loss limitations and stopped status remain. Session passes showMetrics=false only for replaced metrics/timing; metadata, stream observations and completeness remain. History retains all summary fields and percentages by default and reuses the improved zones. History counts/snapshots, raw ACC/ECG development panels and ECG Retry remain.
- 自动检查：本轮执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline --console=plain，BUILD SUCCESSFUL。实际单元测试 XML：204 tests、0 failures、0 errors、0 skipped；lint：0 errors、15 warnings。最终模拟器分开运行新增 13 项及既有 35 项，分别 OK (13 tests)、OK (35 tests)，共 48 项不同检查；35 项包括 10 项顶部/Devices、25 项 History/SQLite。检查有效/缺失/预热/停止/新场状态显示、统计舍入、Retry 路由与原条件、全零/不足一秒/动态最大值柱高、五色实际像素、长数值/错误、元数据与 History 存储字段/占比及回归。初次测试的重复文本定位和段落空白宽度断言已修正；曾被测试 APK 替换中断的组合运行不作为通过证据，最终两份独立日志为准。
  Automated checks: Gradle unit tests, debug/test APK builds and lint succeeded. Current unit XML reports 204 tests with zero failures/errors/skips; lint reports 0 errors and 15 warnings. Final emulator runs separately passed 13 new and 35 existing tests, totaling 48 distinct checks: the latter include ten header/Devices and 25 History/SQLite regressions. Coverage includes displayed valid/missing/warmup/stopped/reset states, rounding, retry routing/guards, zero/subsecond/dynamic bar heights, exact rendered palette pixels, long values/errors, metadata and stored History fields/percentages. Initial duplicate-text selectors and an assertion against unused paragraph width were corrected. A combined run interrupted by test-APK replacement is not counted as passing; the two final independent logs are authoritative.
- 对比度源码核对：卡片主/次文字复用 onSurface/onSurfaceVariant，不使用黄色小字。按现有主题标称 sRGB 色计算，浅色卡片主/次文字为 17.85:1 / 7.58:1，深色为 13.35:1 / 9.85:1；普通按钮文字浅/深为 5.17:1 / 7.02:1。实际截图另检查可读性；这些数值不是 H10 验证结果。
  Source contrast check: Card primary/secondary text reuses onSurface/onSurfaceVariant without small yellow text. Contrast calculated from nominal theme sRGB colors is 17.85:1 / 7.58:1 for light cards and 13.35:1 / 9.85:1 for dark cards; ordinary button foreground contrast is 5.17:1 / 7.02:1. Readability was also inspected in rendered screenshots. These values are not H10 evidence.
- 实际视觉：已检查独立 API 37 模拟器中的受控 Session 四卡和 SQLite History 详情截图，涵盖浅/深、默认与 2.0 字号、单位/长错误/Retry、四小时/长步数/距离、柱形和五行明细。实际 App 无 H10 数据时，Welcome、Session 四卡滚动及空 History 在系统浅/深、1.0/2.0 字号检查；四卡顺序保持。另在实际系统 font_scale=2.0 的竖屏和横屏分别运行受控滚动/文字检查，各 OK (1 test)，包含两种主题；最终确认显示 rotation=1、横屏 PNG 为 2400×1080（landscape-display.txt / landscape-final），系统字号用例保留平台 Density。文字/单位可换行与滚动，未发现数值、说明或按钮被自身布局裁剪；截图边缘的部分内容是可滚动视口边界。模拟器字号/夜间模式/旋转设置已恢复。
  Actual visual checks: Inspected controlled Session and SQLite History screenshots on an isolated API 37 emulator in light/dark modes with default/2.0 fonts, units, long errors/Retry, four-hour/long-step/distance values, bars and all zone details. Checked actual Welcome, scrolling Session cards and empty History without H10 data under system light/dark and font scales 1.0/2.0; card order remained. Controlled scrolling/text checks also passed once each in actual system font_scale=2.0 portrait and landscape, including both themes. Final landscape evidence confirms display rotation=1 and 2400×1080 PNGs in landscape-display.txt / landscape-final; system-font fixtures retain platform Density. Text/units wrap and scroll; no self-layout clipping of values/explanations/buttons was found. Partial content at screenshot edges is the scrolling viewport boundary. Emulator font/night/rotation settings were restored.
- 证据与真机边界：build/step82-validation/gradle.txt、metrics-final.txt、regression-final.txt、system-font2-portrait.txt、system-font2-landscape.txt、visual-check.txt 及受控/actual 截图为本轮证据；单元/lint 报告在 app/build/reports/，debug APK 在 app/build/outputs/apk/debug/app-debug.apk。受控大数值及 SQLite 场次均明确标注测试数据，不属于真实 H10。Samsung/H10 未安装/操作：真实无接触/失败/恢复、静止/走跑统计、区间保持/停止/新场、旋转/后台及设备生命周期验收仍 pending。
  Evidence/hardware limits: Evidence is under build/step82-validation/, including final Gradle/metrics/regression/system-font/visual logs and controlled/actual screenshots. Reports are under app/build/reports/ and the debug APK under app/build/outputs/apk/debug/. Controlled large values and SQLite records are explicitly labeled test fixtures, not H10 observations. No Samsung/H10 installation or operation occurred. Real contact/failure/recovery, stationary/walking/running statistics, zone holding/stop/new-session behavior, rotation/background and device lifecycle acceptance remain pending.
- 范围与交付：未修改 SDK/API、依赖、算法/阈值/统计、采集/生命周期、保存/schema/History 查询；未实施 8.3—8.5，未改变 8.1 顶部/Devices 或 Start/Stop/导航规则。两份 AGENTS.md 和两份 prompt.md 同步中英文实际结果，未 commit/push。建议英文 commit message：feat: organize session metrics and heart rate zones。
  Scope/delivery: No SDK/API, dependency, algorithm/threshold/statistics, acquisition/lifecycle, saving/schema/History-query changes or steps 8.3–8.5. Step 8.1 header/Devices and Start/Stop/navigation rules remain. Both documentation pairs record actual bilingual results. No commit/push. Suggested English commit message: feat: organize session metrics and heart rate zones.


## 步骤 8.3：页面底部实时曲线 / Step 8.3: Bottom live charts

### 中文 Prompt

仅实施 AGENTS.md 5.31 的步骤 8.3，直接修改项目文件。先检查曲线、Session、History 共享调用及所有必要状态/恢复。Live charts 卡片位于 Session 滚动内容最下方；采用可换行且明确选中的 HR/Motion/ECG，Motion 内单独切换 Cadence/Speed，保留既有选择。绘图区 220 dp，标签/按钮在外；主题蓝色 HR/运动折线 2 dp、有点标记，ECG 1 dp 全样本折线、仅孤立点标记。横轴 mm:ss 通常起点/中点/终点，短窗口合并重复刻度，按实际字体测量减少或纵排标签。纵轴明确为 Scale 而非统计最值，非 ECG 从零加最大值 10% 留白，ECG 两端加跨度 10% 留白并保留负值，精度与全零/空数据按 5.31。保留 5.22 的真实窗口、断段、刷新、冻结和 Retry；保留必要状态/配置/错误、ECG 原恢复资格，并清理所有不必要 Session 开发显示及对应 UI 收集，不删除保存数据。尊重系统字体、横屏和滚动。History 原绘图/字段/查询不变，不实施 8.4/8.5，不增加依赖或改 SDK/算法/生命周期。运行相关检查、debug/测试 APK 和 lint，分别记录源码、真实模拟器截图检查与 H10 pending，同步两对文档的实际中英文结果；给英文 commit message，不自动 commit/push。

### English Prompt

Implement only step 8.3 under Section 5.31 of AGENTS.md and modify project files directly. Inspect charts, Session, shared History callers and required statuses/recovery first. Put Live charts last in the Session scroll content. Use wrapping selected HR/Motion/ECG chips and separate Cadence/Speed choices, preserving existing selection ownership. Make only the plot 220 dp high. Draw theme-blue HR/motion lines at 2 dp with point markers and ECG at 1 dp using all visible samples, marking isolated points only. Use Running mm:ss start/middle/end labels, merge duplicate short-window labels and measure scaled text to omit the middle or stack endpoints when needed. Label display bounds as Scale rather than statistical extrema: non-ECG starts at zero with 10% maximum headroom, signed ECG uses 10% span margins, with precision and empty/zero rules from Section 5.31. Preserve all Section 5.22 real windows, gaps, refresh, freeze and retry rules. Keep necessary configuration/status/error text and original ECG recovery guards; remove unnecessary Session development displays and their UI collectors without deleting stored data. Respect system fonts, landscape and scrolling. Keep History plots/fields/queries unchanged and do not implement 8.4/8.5, add dependencies or alter SDK, algorithms or lifecycle. Run relevant checks, debug/test APK builds and lint. Record source inspection, actual emulator screenshots and pending H10 hardware separately; synchronize both documentation pairs with factual bilingual results and provide an English commit message without committing or pushing.

## 2026-10-01 步骤 8.3 实施结果 / Step 8.3 implementation results

### 5.31.1 步骤 8.3 实施与实际验证 / Step 8.3 implementation and actual validation（2026-10-01）

- 源码/文件：检查 LiveChartPanel/LiveCharts、SensorActivity、主题/字号/尺寸、配置/订阅状态、Session 指标/记录/SavePanel 和 History 共用绘图。新增 chart/LiveChartCard.kt、ChartScale.kt 与 LivePlotHeight；LiveChartPanel 仅保持选中曲线快照刷新、将正式显示交给卡片。SensorActivity 删除无需显示的样本/历史/冻结快照 UI 收集和参数，最后调用曲线卡片；保存/算法/缓存持有者不变。HistoryPanel.kt 只为 SavePanel 增加默认保留 ID 的显示参数，Session 隐藏该开发 ID；History 默认行为与原 ChartPlot/180 dp 代码保留。
  Source/files: Inspected chart data/UI, SensorActivity, themes/dimensions, readiness/subscription states, Session metrics/records/save UI and shared History plots. Added LiveChartCard.kt, ChartScale.kt and LivePlotHeight. LiveChartPanel retains the selected snapshot refresh and delegates display. Removed unused Session UI collectors/parameters only; saving, algorithms and buffer owners remain. SavePanel receives a default-on ID display parameter, hidden only in Session. History defaults and its existing 180 dp ChartPlot remain.
- 正式显示：曲线卡片为 Session 内容最后一项；FilterChip 选中状态、Motion 子切换、220 dp 绘图区、主题蓝色/断段/孤立点、负值 ECG、零/空数据、Scale 精度/留白和按实际字号排布的时间刻度已接入。没有新增数据生成、抽样、平滑或交互。保留原 ECG Retry 路由/资格及 ACC/ECG 配置错误提示；HR/ACC 状态和 Retry 仍在指标卡片。清理 Session 原始样本/参数/时间戳/缓存、历史点数/快照/开发元数据和保存 ID，保留必要完整性/不可保存与控制/保存恢复。
  Display: Live charts is last in Session content, with selected wrapping chips, Motion subchoices, a 220 dp plot, theme-blue segmented lines/isolated markers, signed ECG, genuine zero versus empty placeholders, scale margins/precision and measured time-label placement. No generated data, decimation, smoothing or extra interactions. Original ECG retry routing/guards and ACC/ECG configuration guidance remain; HR/ACC status/retry stays in metrics. Unnecessary Session raw/debug metadata and save IDs are removed while required integrity/unsavable/control/save recovery remains.
- 自动检查：最终 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest --offline --console=plain 成功；211 项单元测试（新增 7 项比例尺检查），0 failures/errors/skipped；lint 0 errors、15 warnings。最终曲线/指标独立日志 OK (25 tests)：12 项新增曲线 + 13 项指标；最终顶部/Devices/History/SQLite 回归 OK (35 tests)，合计 60 项不同模拟器检查。覆盖选择/单位、220 dp、刻度去重、空/零、状态映射、负值/孤立 ECG、null 与显式断段实际像素、配置/长错误和原恢复条件及存储/查询/删除回归。首轮组合回归有一项旧测试要求已移除的开发标题，按清理规则改为不存在断言后通过；一次 import 清理误删已恢复，最终构建为准。
  Automated checks: Final offline unit/debug/lint/test-APK tasks succeeded. XML reports 211 unit tests including seven scale tests, with zero failures/errors/skips; lint reports 0 errors and 15 warnings. Final chart/metrics log passed 25 tests (12 new chart + 13 metrics), and final header/Devices/History/SQLite log passed 35, totaling 60 distinct emulator checks. Covers choices/units, plot height, duplicate ticks, empty/zero, status mapping, signed/isolated ECG, actual null/explicit-gap pixels, configuration/errors/retry guards and storage/query/delete regressions. A stale development-title assertion failed in the first combined regression, was updated to assert absence and passed. A removed required import was restored; final build evidence is authoritative.
- 实际视觉：检查独立 Pixel 9 / API 37 模拟器受控浅/深 HR 折线与断段、650 点带负值 ECG、长错误/Retry、默认与 2.0 字号截图；测试内容标为 Controlled UI fixture — no H10 data。真实系统 font_scale=2.0 下分别执行竖屏与横屏受控滚动/文字检查，各 OK (1 test)，使用平台 Density，涵盖浅/深、HR/Motion/Speed/ECG 控件与标签/单位/错误/按钮；确认横屏显示与 PNG 为 2424×1080。另检查实际无 H10 的 App Session 在系统浅/深及 1.0/2.0 字号的顶部和底部截图/层级，四组合均可滚动到最下方无有效数据曲线，未见开发标题/样本缓存显示。截图中视口边缘的部分内容可以滚动查看，未发现文本自身布局裁剪。系统设置已恢复；未操作用户已有模拟器。
  Actual visual checks: Inspected controlled light/dark HR gaps, signed 650-point ECG, long errors/retry and default/2.0-font screenshots on an independent API 37 Pixel 9 emulator, explicitly labeled as fixtures without H10 data. Actual system font_scale=2.0 portrait and landscape scroll/text checks each passed one test using platform Density, exercising both themes and all chart choices/labels/units/errors/buttons. Landscape display/PNGs were verified as 2424×1080. Actual no-H10 Session top/bottom captures and hierarchy checks also covered system light/dark and 1.0/2.0 fonts, reaching the last empty-data chart in all four combinations without development/sample-buffer displays. Partial viewport-edge text is scrollable; no self-layout text clipping was found. Settings were restored and the user's existing emulator was untouched.
- 证据/真机边界：build/step83-validation/ 下 gradle-final.txt、charts-metrics-final.txt、regression-final.txt、system-font2-portrait/landscape.txt、display-landscape.txt、actual-visual.txt 和 controlled-final/system-font2-*/actual-* 截图；单元/lint 报告和 APK 保持 app/build/ 原路径。本轮 Samsung/H10 未安装或操作：真实信号/窗口滚动、ECG 100 ms 刷新性能、接触/单流失败与恢复、真实旋转/后台/连接生命周期仍 pending；静态 650 点受控截图和编译不替代这些验收。没有实施 8.4/8.5，没有 SDK、算法、采集/生命周期、保存/query 或依赖变化。两份 AGENTS.md、两份 prompt.md 同步中英文实际结果，未 commit/push。
  Evidence/hardware limits: Final logs and controlled/system-font/actual captures are in build/step83-validation/; reports/APKs retain their app/build/ paths. No Samsung/H10 installation or operation occurred. Real signals/window scrolling, ECG 100 ms refresh performance, contact/stream failure/recovery and live rotation/background/connection lifecycle remain pending. Static controlled 650-point captures and compilation do not replace hardware acceptance. No steps 8.4/8.5, SDK, algorithm, acquisition/lifecycle, storage/query or dependency changes. Both documentation pairs record actual bilingual results; no commit/push.
- 建议英文提交信息 / Suggested English commit message：`feat: finalize session live charts and remove development displays`


## 2026-10-01 步长系数调整 / Stride coefficient adjustment

用户指令 / User request: 那把K值调整为0.45吧 / Change K to 0.45.

- 2026-10-01 K 调整：按用户要求，StrideLengthEstimator.kt 的步长系数由 0.5 改为 0.45；此前记录中的 K=0.5 为当时版本。同一组有效峰间数据下，估计距离和基于步长的速度为原值的 90%，步数/步频与 A_min=0.5 m/s² 不变。同步已有步长及运动统计测试预期；211 项单元测试通过（0 failures/errors/skipped）、debug 构建通过、lint 0 errors/15 warnings。未安装 APK 或进行本轮真机验证，K=0.45 仍为未校准试验值；已保存历史不重算，无 commit/push。
  K adjustment: Changed the stride coefficient from 0.5 to 0.45 at the user's request. Earlier K=0.5 records describe the previous version. Identical valid peak intervals yield 90% of the previous estimated distance and stride-based speeds; steps, cadence and A_min=0.5 m/s² are unchanged. Updated existing stride/motion test expectations. All 211 unit tests passed with no failures/errors/skips, the debug build passed, and lint reported 0 errors/15 warnings. No APK installation or hardware validation was performed; K=0.45 remains an uncalibrated trial value. Saved history is not recalculated. No commit/push.

## 2026-10-03 步骤 8.4a—8.4d 中英文对照提示词 / Bilingual prompts for steps 8.4a–8.4d

用户指令 / User request：生成中英文对照 prompt，按 8.4abcd 排列；随后要求将提示词写入文档。 / Generate bilingual prompts arranged as 8.4a–8.4d, then save them in the documentation.

状态 / Status：三张参考图已实际查看。以下为待执行的提示词，本轮仅同步两份 AGENTS.md 和两份 prompt.md，8.4a—8.4d 均未实施，没有修改 App 或数据层，没有执行新的构建、自动测试、模拟器运行视觉或 H10 验证，无 commit/push。 / The three reference images were visually inspected. These prompts are pending execution. This turn synchronizes both documentation pairs only, without implementing any 8.4 substep, changing App/data-layer code, running new builds/tests/runtime visuals/hardware validation, committing or pushing.

使用方式 / Usage：按 8.4a → 8.4b → 8.4c → 8.4d 顺序，每次复制“通用要求＋对应步骤”的中文或英文版本，只执行一个子步骤。规则见 AGENTS.md 第 5.32 节。 / Follow a → b → c → d. For each run, use one language version of the shared instructions plus the selected substep only. See AGENTS.md Section 5.32.

最新修订（2026-10-03） / Latest revision：用户要求“注意 Motion 中不需要有速度，只需要展示步频，同步这一点到文档和执行计划中”。以下通用要求及 b/c/d 提示词已同步为最终目标：Session Motion 指标卡和图表只展示步频，现有速度 UI 在 8.4d 移除。本轮仅修改两对文档，不实施 App；8.4a—8.4c 已实施，8.4d/8.5 pending。上述原始状态和下方实施结果属于当时记录，不代表本次移除速度 UI 已完成。 / The user requested cadence-only Motion and synchronization of documentation and the execution plan. The shared instructions and b/c/d prompts below now express the final target: both Session Motion metrics and its plot show cadence only; remove existing speed UI during 8.4d. This turn updates both documentation pairs only. Steps 8.4a–8.4c are implemented; 8.4d/8.5 remain pending. The original status above and implementation records below retain their historical meaning and do not establish completion of this speed-UI removal.

最新执行规则：用户随后确认全部按图示，包括暂停/继续和五分钟窗口；下列旧通用要求/子步骤提示词保留为前次提示记录，发生冲突时以本文件末尾“图示功能对齐”提示词和 AGENTS.md 5.33 为准。 / Latest execution rules: The user subsequently confirmed all reference functions, including Pause/Continue and five-minute windows. The older prompts below remain prior prompt records; conflicts are superseded by the final reference-function alignment prompt at the end and AGENTS.md 5.33.

### 通用要求 / Shared instructions

#### 中文 Prompt

```text
工作区：C:\Users\auyhp\OneDrive\Desktop\551-a4
Android 项目：C:\Users\auyhp\OneDrive\Desktop\551-a4\PolarH10ActivityViewer

先阅读两份 AGENTS.md，并实际查看以下参考图：
ui/Session-HR.png
ui/Session-Motion.png
ui/Session-ECG.png

按本次指定的 8.4 子步骤直接修改项目文件。8.4 扩展为 Session 最终界面整合，允许调整已有展示布局；新的强度位置、卡片顺序和 Session 横向区间条覆盖此前相关布局规定，其他功能规则保持不变。

Motion 最新规则覆盖此前保留 Session 速度展示的要求：指标卡只显示当前步频及整场 Mean/Min/Max cadence（steps/min）；图表 Motion 只选择 Cadence，不显示速度或 Cadence/Speed 子切换。现有速度 UI 的移除列入 8.4d；速度计算、状态、缓存、历史存储及 History 速度统计保留，Activity summary 的估计距离保留。此次文档同步不授权提前实施 8.4d。

沿用 Kotlin、Compose、Material 3、固定系统深浅主题和默认字体。
字号：页面标题 24 sp、分区标题 18 sp、正文 16 sp、次要统计 14 sp、当前 HR 56 sp、当前步频 28 sp。
页面/卡片 padding、卡片间距和圆角 16 dp，内部间距 8/12 dp，图标 24 dp，触控至少 48 dp。

UI 和代码注释使用英文。尊重系统字体缩放和安全区域，允许滚动、增高、换行，横屏保留内容顺序，不裁剪数值、单位或按钮。
未知显示 --，保留真实零、负 ECG、必要状态、错误和恢复入口；参考图中的演示数值不得进入正常界面。

不得修改 SDK 调用、采集、缓存、算法、K=0.45、HR 阈值、统计公式、计时、生命周期、会话冻结、SQLite schema、保存事务或 History 查询规则。
不新增 RR/HRV/R 峰分析、依赖、通用样式框架或无关重构，不实施 8.5。

修改前检查相关源码；只完成当前子步骤。先同步规则，再同步实际结果到两份 AGENTS.md 和两份 prompt.md，使用中英文并明确已完成与待完成。
运行相关检查、debug 构建和 lint。分别报告源码、自动检查、实际视觉检查和 Samsung/H10 验证；未执行项标为 pending。
提供英文 commit message，不自动 commit 或 push。
```

#### English Prompt

```text
Workspace: C:\Users\auyhp\OneDrive\Desktop\551-a4
Android project: C:\Users\auyhp\OneDrive\Desktop\551-a4\PolarH10ActivityViewer

Read both AGENTS.md files and visually inspect:
ui/Session-HR.png
ui/Session-Motion.png
ui/Session-ECG.png

Modify project files directly for the requested 8.4 substep only. Step 8.4 now includes final Session UI integration. The new intensity placement, card order and horizontal Session zone bars supersede the corresponding earlier layout rules. Preserve all other functional rules.

The latest Motion rule supersedes earlier requirements to retain Session speed displays: show only current and whole-session Mean/Min/Max cadence in steps/min in the metric card; Motion selects only Cadence, with no speed display or Cadence/Speed sub-selection. Remove existing speed UI during 8.4d. Preserve speed calculations, state, buffers, history storage, History speed statistics and summary estimated distance. This documentation update does not authorize early implementation of 8.4d.

Reuse Kotlin, Compose, Material 3, the fixed system light/dark theme and default font family.
Use 24 sp page titles, 18 sp section titles, 16 sp body text, 14 sp secondary statistics, 56 sp current HR and 28 sp current cadence.
Use 16 dp page/card padding, card spacing and corners; 8/12 dp internal spacing; 24 dp icons; and touch targets of at least 48 dp.

Keep UI and comments in English. Respect system font scaling and system insets. Allow scrolling, growing cards and wrapping, retaining content order in landscape without clipping values, units or buttons.
Keep -- for unknown values, genuine zeros, negative ECG values, necessary statuses, errors and recovery actions. Do not put reference-image demo values into the normal UI.

Do not change SDK calls, acquisition, buffers, algorithms, K=0.45, HR thresholds, statistical formulas, timing, lifecycle, session freezing, SQLite schema, save transactions or History query rules.
Do not add RR/HRV/R-peak analysis, dependencies, generic styling frameworks, unrelated refactoring or step 8.5.

Inspect relevant source before editing and implement only the selected substep. Synchronize its rules first, then actual bilingual results, in both AGENTS.md files and both prompt.md files. Distinguish completed and pending work.
Run relevant checks, a debug build and lint. Report source inspection, automated checks, actual visual checks and Samsung/H10 validation separately; mark unperformed checks pending.
Provide an English commit message without automatically committing or pushing.
```

### 8.4a：页面框架、连接状态、控制与导航 / Page structure, connection status, controls and navigation

实施授权 / Implementation authorization（2026-10-03）：用户要求“实施8.4a”，按通用要求及本节直接修改 UI；结果验证后另记，后续子步骤仍 pending。 / The user requested implementation of 8.4a. Modify the UI according to the shared instructions and this section; record results after verification. Later substeps remain pending.

#### 中文 Prompt

```text
只实施 8.4a，遵循通用要求。

1. 使用 Scaffold 整合页面：顶部固定 Session / History 等宽页签，选中项使用主题蓝色文字和下划线。去掉重复的 Session 页面标题，中间内容可滚动，Session 底部显示 Start / Stop 控制区。保留现有卡片内容，指标重排和曲线改造分别留给 8.4b、8.4c。

2. 将连接和 Data streams 整合为顶部卡片。左侧显示蓝牙图标、真实连接状态、电量及 Devices 齿轮入口；连接区域和齿轮均打开原 Devices 弹窗，不新增设置页。右侧显示 HR、ACC、ECG 及实际订阅状态：Receiving 绿、Starting/Stopping 橙、Failed 红、Idle/Stopped 中性灰。每路提供可见文字状态，不仅依靠颜色，READY 不等于 Receiving。窄屏/大字体时上下排列。原强度显示暂保留，待 8.4b 移入 HR 卡。

3. 图片的暂停/继续/停止改为 Start / Stop 两个控件，图标配英文标签，建议圆形按钮直径 64 dp，不提供 Pause/Resume。保留原回调及启用条件，Starting 可 Stop，Stopping/保存阻塞时不能重复 Start。显示简洁会话状态、必要禁用原因和清楚的英文结束原因。

4. 保存信息靠近会话状态显示，长错误放入可滚动区域。保留 Saving、Save failed、Retry save 和 Discard session；在 Session/History 均可达。不常驻 UUID 或 No session to save，不把上一场 Saved 显示成新会话已保存。Discard 增加确认弹窗后调用原 discard，不修改保存控制器。

5. 在同一个 SensorActivity 内切换 Session/History，不停止、断开、清零、重复订阅或重置曲线选择。保留 Session 滚动位置、History 返回层级、刷新/分页/重试和删除确认。保留原后台、锁屏、返回欢迎页和旋转规则，不新增导航依赖或退出拦截。

6. 检查页签、Devices、按钮状态、保存失败恢复及深浅/大字体/横屏布局。底部按钮不得遮挡内容。不实施 8.4b–8.4d。
```

#### English Prompt

```text
Implement only 8.4a, following the shared instructions.

1. Integrate the screen with Scaffold: fixed equal-width Session/History tabs at the top, theme-blue selected text and an underline, scrollable content, and a Session Start/Stop control area at the bottom. Remove the duplicate Session page title. Retain current card content; metric reorganization and chart changes belong to 8.4b and 8.4c.

2. Combine connection information and Data streams in the header card. Show the Bluetooth icon, actual connection state, battery and Devices gear on the left. Both the connection area and gear open the existing Devices dialog; do not add a settings screen. Show HR/ACC/ECG with actual subscription states: green Receiving, orange Starting/Stopping, red Failed, and neutral gray Idle/Stopped. Include visible state text rather than color alone; READY is not Receiving. Stack content on narrow screens or with enlarged fonts. Keep the existing intensity display until 8.4b moves it into the HR card.

3. Replace the reference pause/resume/stop controls with Start and Stop, using icons and English labels. A 64 dp circular button is recommended. Do not add Pause/Resume. Preserve existing callbacks and enablement guards, allow Stop during Starting, and prevent duplicate Start during Stopping or save blocking. Show concise session states, necessary disabled reasons and readable English end reasons.

4. Display saving information near the session status, placing long errors in scrollable content. Retain Saving, Save failed, Retry save and Discard session on both Session and History. Remove persistent UUID and No session to save displays. Do not present a previous session's Saved status as confirmation that the new session is saved. Confirm Discard before calling the existing discard function; leave the save controller unchanged.

5. Switch Session/History within the same SensorActivity without stopping, disconnecting, clearing data, resubscribing or resetting chart selection. Preserve Session scroll position and existing History back, refresh, pagination, retry and deletion-confirmation rules. Keep existing background, lock-screen, Welcome-return and rotation behavior. Add no navigation dependency or exit interception.

6. Check tabs, Devices, button states, save-failure recovery and light/dark, enlarged-font and landscape layouts. Bottom controls must not obscure content. Do not implement 8.4b–8.4d.
```

### 8.4b：指标卡片、运动汇总与横向区间 / Metric cards, activity summary and horizontal zones

#### 中文 Prompt

```text
只实施 8.4b，遵循通用要求，检查并复用已完成的 8.4a。

1. Session 滚动内容按以下顺序排列：
连接/Data streams → Heart rate → Motion → Live charts → Activity summary → HR zones → 必要会话状态和保存恢复信息。
本步移动现有曲线卡片，但不提前改造其绘制。

2. 将当前强度从顶部移入 Heart rate 卡，只显示一处。使用共用颜色和英文标签，例如 Moderate · Zone 3。宽度足够时左侧为当前 HR/bpm，右侧为整场 Min/Max/Mean HR；底部显示真实 Last received (phone)。保留无效 HR、接触、停止、失败及 Retry HR 规则。停止或当前无效时不显示有效强度，未知为 --。

3. Motion 参考图中步频大值与统计分栏，只展示当前步频及整场 Mean/Min/Max cadence，单位 steps/min。保留缺失、预热、停止、失败说明和 Retry ACC；不显示 Estimated speed、Mean speed、Max speed 或 km/h。窄屏/大字体时纵排。按最新修订，已实施版本的速度区域在 8.4d 移除。

4. Activity summary 使用三个并列小区域：Running duration、Total steps、Estimated distance；空间不足时换行或纵排。复用真实值，保留 mm:ss、m、--、真实零及估计含义。

5. 仅将 Session HR zones 改为五行横向条形。每行包含 Zone 1–5、原英文强度标签、bpm 范围、彩色条和累计 mm:ss。颜色共用绿/蓝/黄/橙/红定义。五条共用时长比例尺，以最大累计区间时长对应满宽，不将其标为 Running 百分比。全部为零显示空条和 00:00；未收到有效 HR 时遵循原占位规则。保留 Unclassified 和不完整提示，文字使用主题前景色。

6. Session 横向区间卡随内容增高，替代此前 160 dp 竖向绘图区规定。History 保留原展示，避免共享组件改动提前影响 8.5。

7. 检查规定的 HR/步频/汇总/区间统计仍存在、未知/零/停止状态正确，以及深浅/大字体/横屏布局。不实施 8.4c、8.4d。
```

#### English Prompt

```text
Implement only 8.4b, following the shared instructions and inspecting/reusing completed 8.4a work.

1. Arrange Session scroll content as:
Connection/Data streams → Heart rate → Motion → Live charts → Activity summary → HR zones → necessary session and save-recovery information.
Move the existing chart card without redesigning its plot in this substep.

2. Move current intensity from the header into the Heart rate card, displaying it once with the shared color and English label, such as Moderate · Zone 3. When space allows, place current HR/bpm on the left and whole-session Min/Max/Mean HR on the right. Show the actual Last received (phone) time below. Preserve invalid HR, contact, stopped, failed and Retry HR behavior. Do not show valid current intensity when stopped or invalid; unknown values remain --.

3. Follow the reference cadence-value/statistics arrangement in Motion, showing only current and whole-session Mean/Min/Max cadence in steps/min. Retain missing, warmup, stopped and failed explanations and Retry ACC. Do not show Estimated speed, Mean speed, Max speed or km/h. Stack content when width or font scaling requires it. Under the latest revision, remove the implemented version's existing speed area during 8.4d.

4. Present Activity summary as three adjacent areas: Running duration, Total steps and Estimated distance. Wrap or stack when necessary. Reuse real values and retain mm:ss, m, --, genuine zeros and estimate wording.

5. Replace only Session HR zones with five horizontal rows. Each row includes Zone 1–5, its existing English intensity label, bpm range, colored bar and cumulative mm:ss. Reuse the green/blue/yellow/orange/red palette. All bars share a duration scale, with the longest cumulative zone duration filling the track; do not label this as a percentage of Running time. All-zero durations show empty bars and 00:00; no valid HR observations retain existing placeholder behavior. Keep Unclassified and incomplete-data information, using readable theme foreground text.

6. Let the Session horizontal-zone card grow with its content, superseding the previous 160 dp vertical-plot requirement. Preserve History's existing presentation and avoid shared-component changes that implement 8.5 prematurely.

7. Check that required HR/cadence/summary/zone statistics remain available, unknown/zero/stopped states are correct, and light/dark, enlarged-font and landscape layouts work. Do not implement 8.4c or 8.4d.
```

### 8.4c：三类实时曲线 / Three live chart views

#### 中文 Prompt

```text
只实施 8.4c，遵循通用要求，复用 8.4a、8.4b 的页面结构。

1. 按三张图片改进 Live charts 卡片。HR/Motion/ECG 使用胶囊选择控件，选中为主题蓝色，未选中为中性色，并提供明确选中语义。Motion 只选择 Cadence（steps/min），不提供 Cadence/Speed 子切换或速度曲线；按最新修订，已实施版本的速度入口在 8.4d 移除。

2. 保留原有选择持有者、刷新频率、缓存、时间锚点、断段、冻结及 Retry 条件。绘图区 220 dp；HR/运动仍显示最近 60 秒，ECG 最近 5 秒，不能照搬图片五分钟 HR/运动窗口。

3. HR 使用红色曲线和浅红色填充；Motion 使用主题蓝色曲线和浅蓝色填充；ECG 使用蓝色细线。曲线及填充均按有效连续段绘制，不跨 null/breakBefore，不补造数据。ECG 保留负值及全部可见样本，不平滑或抽样。

4. 增加清晰的网格和纵轴刻度，沿用 8.3 的范围、留白和精度规则。轴边界不是统计最值。横轴显示真实 Running 单调时间 mm:ss，标签数量随可用空间调整；ECG 宽屏可增加秒刻度，窄屏/大字体减少，禁止重叠或缩字。

5. HR/运动图上方复用已有整场统计，明确标为 Session mean / Session max，Motion 使用步频统计。均值虚线使用同一个整场均值，只有有效且位于当前比例尺内时绘制；不重算统计或强行扩展坐标范围。ECG 显示实际选中采样率和当前窗口实际样本数量，未知为 --，不硬编码 130/650。

6. 保留空数据、等待、失败、冻结、配置错误和原恢复入口；ECG 失败时，即使选中 HR/Motion，仍可找到 ECG Retry。正常状态下精简重复说明，不新增缩放、拖动、动画或图表依赖。

7. 最终检查 HR、Cadence、ECG 三种展示，包含真实零、负值、断段、空窗口、长标签、深浅模式、大字体和横屏。明确受控截图不能证明实时 ECG 性能。不实施 8.4d。
```

#### English Prompt

```text
Implement only 8.4c, following the shared instructions and reusing the 8.4a/8.4b screen structure.

1. Refine Live charts using the three reference images. Use pill-shaped HR/Motion/ECG selectors with theme-blue selection, neutral unselected backgrounds and explicit selection semantics. Motion selects only Cadence in steps/min, with no Cadence/Speed sub-selection or speed plot. Under the latest revision, remove the implemented version's existing speed entry during 8.4d.

2. Preserve selection ownership, refresh intervals, buffers, time anchors, gaps, freezing and Retry guards. Keep a 220 dp plot. HR/motion still display the latest 60 seconds and ECG the latest five seconds; do not copy the reference five-minute HR/motion window.

3. Use a red HR line with a subtle red fill, theme-blue motion lines with subtle blue fills, and a thin blue ECG line. Draw lines and fills separately for valid continuous segments, without crossing null/breakBefore or inventing data. Preserve negative ECG values and every visible ECG sample without smoothing or decimation.

4. Add readable grid lines and Y-axis ticks while retaining 8.3 range, margin and precision rules. Axis bounds are not statistical extrema. Use actual monotonic elapsed Running time in mm:ss on the X-axis, adapting label count to available space. Wider ECG plots may show more second ticks; reduce ticks for narrow screens/enlarged fonts without overlaps or shrinking text.

5. Reuse existing whole-session statistics above HR/motion plots, explicitly labeled Session mean and Session max, using cadence statistics for Motion. Use the same whole-session mean for the dashed reference line, drawing it only when valid and within the current display bounds. Do not recalculate statistics or force an axis expansion. Show ECG's actual selected sampling rate and actual visible-window sample count; use -- when unknown and never hardcode 130/650.

6. Retain empty, waiting, failed, frozen and configuration-error states with existing recovery actions. ECG Retry must remain reachable after an ECG failure even while HR/Motion is selected. Reduce redundant normal-state explanations. Add no zooming, dragging, animation or chart dependency.

7. Check the final HR, Cadence and ECG views, including genuine zeros, negative values, gaps, empty windows, long labels, light/dark mode, enlarged fonts and landscape. Distinguish controlled screenshots from real-time ECG performance validation. Do not implement 8.4d.
```

### 8.4d：最终整合、清理与验收 / Final integration, cleanup and validation

> 历史提示词，勿作为当前完整实施指令：功能规则由 AGENTS.md 5.33 覆盖；已实现功能见 5.33.1，剩余视觉项现拆为 8.4e（5.34 与本文件末尾）。原文中的 Min、60 秒、仅 Start/Stop 等旧要求不再生效。
> Historical prompt, not the current complete execution instruction: AGENTS.md 5.33 supersedes its functional rules; 5.33.1 records implementation and 8.4e now owns the remaining visuals. Old Min, 60-second and Start/Stop-only requirements below are superseded.

#### 中文 Prompt

```text
只实施 8.4d，遵循通用要求。先确认 8.4a–8.4c 已落实；若缺失，说明具体缺口，不将未实施内容写成已完成。

1. 对照三张参考图检查最终 Session 结构、对齐、卡片层次、选择状态及控件位置，并落实用户最新 Motion 只展示步频的要求：指标卡移除 Estimated speed/Mean speed/Max speed、km/h 和仅与速度有关的说明；保留当前步频、整场 Mean/Min/Max cadence、steps/min、缺失/预热/停止/失败说明及 Retry ACC。Live charts 的 Motion 入口直接选择 Cadence，移除 Cadence/Speed 子切换、速度曲线/单位/图上速度统计；沿用原选择持有者，并确保返回、切页和旋转后不会恢复隐藏的 Speed 视图。保留步频蓝线/浅蓝填充、60 秒窗口、整场 Session mean/max 和有效范围内的均值虚线。其余只修复本阶段遗留界面问题，不新增设计或功能；保留 Start/Stop、真实窗口和必要恢复信息。

2. 清理被正式界面替代的开发标题、UUID、缓存计数、重复统计和常驻调试说明。保留 8.4c 明确要求的实际 ECG 采样率/窗口样本数。确认无引用和其他用途后才删除纯 UI 文件，不删除数据持有者、业务代码、测试证据或历史材料。

3. 将相关 UI 检查更新为 HR/Cadence/ECG 三视图，确认 Session Motion 没有速度数值、km/h 或 Speed 入口，步频统计、必要状态和 Retry ACC 仍正确。验证系统深浅模式、字体 1.0/2.0、竖屏/横屏及滚动。检查固定页签和底部按钮不遮挡内容，数字、单位、刻度、错误和恢复按钮均可读可达。此前 b/c 四视图测试与截图属于旧布局证据，不能作为此次只步频布局已通过的证据。

4. 验证无设备、权限/蓝牙不可用、Starting、Running、Stopping、Stopped、单流失败/Retry、保存失败/Retry/Discard 和 Session/History 切换。切换和旋转不清零或重复订阅；History 查询、分页、删除和返回行为保持原规则。

5. 运行相关单元测试、Compose/SQLite 回归、debug 构建及 lint。保存并实际查看可执行范围内的截图。分别记录源码检查、自动检查、实际视觉和 Samsung/H10 真机结果，未执行项标为 pending，不将演示数据或编译成功写成真机通过。

6. 保留 Activity summary 的 Running duration/Total steps/Estimated distance，以及速度计算、状态、缓存、历史字段、SQLite schema/已保存数据和 History 速度统计；只移除无其他用途的速度 UI，不以删除底层速度数据实现隐藏。用 Git diff 核查整个 8.4a–8.4d 范围，确认数据层、算法、存储和 SDK 代码没有修改。若发现差异，说明文件、原因和影响，不静默覆盖用户改动。

7. 同步两份 AGENTS.md 和两份 prompt.md 的中英文实际结果、最终布局规则和未验证项。提供英文 commit message 和实际修改文件清单，不自动提交或推送，不推进 8.5。
```

#### English Prompt

```text
Implement only 8.4d, following the shared instructions. First confirm that 8.4a–8.4c are implemented. Report specific missing prerequisites without describing unimplemented work as complete.

1. Compare the final Session structure, alignment, card hierarchy, selected states and control placement with all three references, implementing the user's latest cadence-only Motion requirement. Remove Estimated speed/Mean speed/Max speed, km/h and speed-only explanations from the metric card. Retain current cadence, whole-session Mean/Min/Max cadence, steps/min, missing/warmup/stopped/failed explanations and Retry ACC. Route Live charts Motion directly to Cadence, removing Cadence/Speed sub-selection, the speed plot/units/plot statistics. Reuse selection ownership and ensure return, tab switching and rotation cannot restore a hidden Speed view. Keep the blue cadence line/subtle fill, 60-second window, whole-session Session mean/max and valid in-range mean line. Otherwise fix only remaining UI issues from this stage without new designs or features, preserving Start/Stop, real windows and recovery information.

2. Remove development headings, UUID displays, buffer counts, duplicate statistics and persistent debugging explanations replaced by the final UI. Retain the actual ECG sampling rate/window sample count explicitly required by 8.4c. Delete UI-only files only after confirming they have no remaining references or other purposes. Preserve data owners, business code, validation evidence and historical materials.

3. Update relevant UI checks to HR/Cadence/ECG and confirm that Session Motion contains no speed values, km/h or Speed entry, with correct cadence statistics, necessary states and Retry ACC. Check system light/dark modes, font scales 1.0/2.0, portrait/landscape and scrolling. Ensure fixed tabs and bottom controls do not obscure content, and values, units, ticks, errors and recovery actions remain readable and reachable. Earlier b/c four-view checks and captures describe the previous layout, not validation of this cadence-only layout.

4. Check no-device and unavailable-permission/Bluetooth states, Starting, Running, Stopping, Stopped, individual stream failure/Retry, save failure/Retry/Discard and Session/History switching. Switching and rotation must not clear data or duplicate subscriptions. Preserve History query, pagination, deletion and back behavior.

5. Run relevant unit tests, Compose/SQLite regression checks, a debug build and lint. Save and actually inspect screenshots within the available execution scope. Record source inspection, automated checks, actual visuals and Samsung/H10 results separately, marking unperformed checks pending. Do not describe demo data or successful compilation as hardware validation.

6. Preserve summary Running duration/Total steps/Estimated distance, speed calculations, state, buffers, history fields, SQLite schema/saved data and History speed statistics. Remove only speed UI with no other purpose; do not remove underlying speed data to hide it. Review the Git diff across the entire 8.4a–8.4d change range and confirm that data-layer, algorithm, storage and SDK code were not modified. If differences exist, report the files, reasons and impact without silently overwriting user changes.

7. Synchronize actual bilingual results, final layout rules and pending validation in both AGENTS.md files and both prompt.md files. Provide an English commit message and the actual changed-file list. Do not automatically commit, push or advance to 8.5.
```

## 2026-10-03 步骤 8.4a 实施结果 / Step 8.4a implementation results

#### 5.32.1.1 8.4a 实施与验证 / Implementation and validation（2026-10-03）

- 源码：SensorActivity.kt 接入固定等宽蓝色选中页签的 SessionScaffold；SessionControls 使用 64 dp 圆形图标按钮与英文标签，仅 Session 显示并占用独立底部安全区域。内容继续滚动，删除重复 Session 标题与常驻计时调试解释，沿用现有指标/强度/区间/曲线顺序；8.4b/c 的重排及绘图未实施。
  Source: SensorActivity uses SessionScaffold with fixed equal tabs and blue selection/underline. SessionControls uses 64 dp circular icon buttons and English labels in a separate bottom safe area on Session only. Content scrolls; the duplicate Session title and persistent timing explanations are removed. Existing metrics/intensity/zones/charts retain their order; no 8.4b/c redesign.
- 连接与状态：SessionHeader.kt 合并连接/电量、两个原 Devices 入口和三路真实订阅指示；窄屏/大字体纵排，宽屏并排。Receiving 绿、Starting/Stopping 橙、Failed 使用主题红色、Idle/Stopped 灰，均有可见状态文字，不以 READY 推断 Receiving。原弹窗、扫描关闭与恢复回调不变。
  Connection/status: SessionHeader combines connection/battery, both original Devices entry points and actual HR/ACC/ECG subscription indicators. Narrow/enlarged layouts stack; wide layouts use columns. Green Receiving, orange Starting/Stopping, theme-red Failed and gray Idle/Stopped all have visible text. READY never implies Receiving. Existing dialog/scan-dismissal/recovery callbacks remain.
- 会话与保存：新 UI startDisabledReason 与原 Start 条件逐组合一致；Starting 可 Stop、Stopping/保存阻塞拒绝 Start。状态、禁用原因、四小时结束原因可读。SavePanel.kt 的提示位于两页滚动状态区域，隐藏常驻保存 UUID/Idle 文案，仅对当前 ID 显示终态；Saving/Failed 始终可达，Retry 调原方法，Discard 须确认后调原控制器。HistoryPanel.kt 只迁出旧保存 UI 并接入状态槽，查询/分页/删除代码不变。
  Session/save: The UI startDisabledReason matches the original Start guard across state combinations. Stop remains available during Starting; Stopping/save blocking prevent Start. State, disabled reasons and the time-limit end reason remain readable. SavePanel renders inside both pages' scrollable status areas, hides save UUID/idle notices and associates terminal states with the current ID. Saving/Failed remain reachable; Retry and confirmed Discard call the existing controller. HistoryPanel only moves the old save UI and adds a status slot; query/pagination/delete code is unchanged.
- 切换与数据边界：同一 Activity 内 SaveableStateHolder 保存两页 UI 状态，Session 滚动位置和 History 选中详情层级可恢复；离屏查询仍按原规则取消/返回时重新查询。曲线选择继续由原 liveCharts 持有，250 ms 会话刷新仍在页面切换之外。Git diff 确认 SDK、PolarBleManager、采集/缓存、motion（K=0.45）、HR 阈值/统计、session 状态/冻结、SQLite/保存控制器、依赖均无修改；未增加导航依赖或退出拦截。
  Switching/data boundary: SaveableStateHolder retains page UI state, Session scroll and History's selected detail. Off-screen queries still cancel and refresh on reentry. The existing liveCharts owner retains selection, while the existing 250 ms session refresh stays outside page switching. Git diff confirms no SDK/manager, acquisition/buffer, motion/K=0.45, HR/statistics, session/timing/freezing, SQLite/save-controller or dependency changes. No navigation dependency or exit interception.
- 自动检查：离线 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug :app:assembleDebugAndroidTest 最终成功。XML 212 tests、0 failures/errors/skipped；lint 0 errors、15 warnings。独立 API 37.1 / Pixel 9 工作区模拟器 emulator-5586 首轮 OK (71 tests)：11 新界面检查 + 60 Devices/指标/曲线/History/SQLite 回归；最后页签 API/弹窗尺寸调整后，新界面重新 OK (11 tests)。实际系统 font_scale=2.0 下竖屏与横屏各 OK (1 test)，不是额外的不同测试数量。
  Automated checks: Final offline unit/debug/lint/test-APK tasks succeeded. XML reports 212 tests with zero failures/errors/skips; lint reports 0 errors and 15 warnings. The independent workspace API 37.1 Pixel 9 emulator-5586 passed 71 distinct checks: 11 new UI checks plus 60 existing Devices/metrics/chart/History/SQLite regressions. After the final tab API/dialog sizing adjustment, all 11 new checks passed again. The actual system font_scale=2.0 check also passed separately in portrait and landscape; these are repeated runs, not extra distinct tests.
- 实际视觉：已查看受控浅/深与 1.0/2.0 字号截图，以及实际系统 2.0 字号竖/横屏的状态、长保存错误、History、底部控件与滚动末项截图；受控页面标为 Controlled UI fixture — no H10 data。实际无 H10 App 的四个深浅/字体竖屏组合已检查顶部、Devices（2.0）、曲线底部及 History；层级确认切页恢复滚动与 ECG 选择。另完成实际无 H10 的 2.0 字号浅/深横屏顶部、曲线底部及 History 截图/层级，确认末项可达与返回滚动位置，共保存 20 张 actual PNG；ECG 选择保留在四个竖屏组合确认。视口边缘的部分内容可滚动查看，不将其当作文字自身布局截断。
  Actual visuals: Inspected controlled light/dark 1.0/2.0-font screenshots and actual-system 2.0-font portrait/landscape status, long save errors, History, bottom controls and last scrollable items. Fixtures explicitly say no H10 data. Actual no-H10 App checks cover four portrait theme/font combinations, header, Devices at 2.0, chart bottom and History; hierarchy checks confirm retained scroll and ECG choice. Actual no-H10 light/dark landscape at 2.0 also covers header, chart bottom and History, confirming last-item reachability and returned scroll position. There are 20 actual PNGs; retained ECG selection is checked in the four portrait combinations. Partial viewport-edge content is scrollable, rather than truncated within its Text layout.
- 证据与未验证：build/step84a-validation/ 的 gradle-final.txt、instrumentation-first.txt、chrome-final.txt、system-font2-portrait/landscape.txt、controlled-final/、system-font2-*/、actual-*.png/xml、actual-visual-final.txt 的四个竖屏成功记录与 actual-landscape-final.txt 的两个横屏成功记录；APK/单元/lint 报告仍在 app/build/。Samsung/H10 未安装或操作；真实连接/电量/数据状态、采集切页/旋转、后台/锁屏、四小时结束与真实保存失败恢复仍 pending。两对文档同步中英文结果，无 commit/push，8.4b—8.4d/8.5 pending。
  Evidence/limits: Logs, controlled/system-font captures and actual PNG/XML evidence, the four successful portrait records in actual-visual-final.txt and both landscape records in actual-landscape-final.txt are in build/step84a-validation/; APKs and unit/lint reports remain in app/build/. No Samsung/H10 installation or operation occurred. Real connection/battery/stream state, acquisition during tab switching/rotation, background/locking, four-hour termination and actual save-failure recovery remain pending. Both documentation pairs record bilingual results; no commit/push and no steps 8.4b–8.4d/8.5.
- 修改文件：SensorActivity.kt、session/SessionHeader.kt、session/SessionChrome.kt、history/HistoryPanel.kt、history/SavePanel.kt；drawable/ic_devices.xml、ic_start.xml、ic_stop.xml；SessionHeaderTest.kt、SessionChromeTest.kt、SessionStartGuardTest.kt；两份 AGENTS.md 和两份 prompt.md。build/ 下脚本/证据仅为本地验证，不进入 APK。
  Changed files: The five UI Kotlin files, three drawable vectors and three test files listed above, plus both AGENTS.md and both prompt.md files. Scripts/evidence under build/ are local validation artifacts, not APK code.
- 建议英文提交信息 / Suggested English commit message：`feat: integrate session navigation controls and save recovery`

## 2026-10-03 步骤 8.4b 实施授权 / Implementation authorization

用户要求现在实施 8.4b，按上方完整中英文提示词和 AGENTS.md 5.32.2 写入并验证，仅本步骤。
The user requests implementing and validating 8.4b under the complete bilingual prompts above and AGENTS.md 5.32.2, without advancing subsequent steps.

## 2026-10-03 步骤 8.4b 实施结果 / Implementation results

#### 5.32.2.1 8.4b 实施与验证（2026-10-03） / Implementation and validation

- 文件已修改：Session 滚动顺序为连接/Data streams → Heart rate → Motion → 原 Live charts → Activity summary → 横向 HR zones → 必要会话/保存恢复。删除顶部重复强度，将原五档标签和配色移入 HR 卡；有效读数与整场 Min/Max/Mean 分栏，空间不足随系统字号纵排，保留实际 Last received (phone)、接触/无效/失败/停止说明和 Retry HR。停止、读数为空或 HR 失败时不显示有效当前强度。
  Files changed: Session now orders connection/streams, HR, Motion, the existing charts, summary, horizontal zones and necessary session/save recovery. Intensity appears once inside HR with the shared labels/palette. Current readings and whole-session Min/Max/Mean use responsive columns, retaining actual phone reception time and contact/invalid/failure/stopped/retry behavior. Stopped, missing-reading and HR-failure states cannot show valid current intensity.
- Motion 的步频和估计速度各有当前值/统计分栏，保留 Mean/Min/Max cadence、Mean/Max speed、单位、缺失/预热/不完整说明和 Retry ACC。Summary 复用真实时长/步数/估计距离，三项独立小区域随宽度和字号换行，不加入参考图演示数据。
  Motion has separate cadence and estimated-speed regions with current/statistical columns, retaining all cadence/speed statistics, units, missing/warmup/incomplete explanations and Retry ACC. Summary reuses actual duration/steps/estimated distance in three areas that wrap with available width and font scale, without reference-image demo data.
- 新增仅 Session 使用的 SessionHeartRateZonePanel：五行横条显示 Zone/原英文标签/bpm/累计 mm:ss，按原累计毫秒与最大时长共用比例尺；时间列预留同宽，保证跨 50:00/100:00 等长度变化时五条仍同尺。全零空条/00:00、未观测说明、Unclassified 和会话不完整提示保留。HistoryPanel 与原 HeartRateZonePanel 本轮未修改，仍为 160 dp 竖向五柱与原百分比。
  The Session-only horizontal panel shows five zone/label/bpm/bar/duration rows, using existing milliseconds relative to the maximum duration. Equal duration-column widths retain a common scale when time labels have different lengths. Empty zero bars, missing observations, Unclassified and session integrity notices remain. History and its original 160 dp vertical zone plot/percentages are unchanged in this turn.
- 自动检查：212 项单元测试通过，0 failures/errors/skipped；debug 与测试 APK 构建通过，lint 0 errors、15 warnings。独立 API 37 Pixel 9 模拟器完整仪器测试 76 项通过，其中 75 项不同 Compose/SQLite 检查、1 项包名基础检查；最终受控状态/History 断言补充后的 17 项指标检查再通过。新增 4 项覆盖卡片顺序、无效/失败/停止强度防护、有效零空条及正常/放大字号分栏；既有比例、亚秒/长时长、五色、真实零/未知、接触/失败/Retry、保存两页恢复、SQLite 查询/删除回归通过。实际系统字号 2.0 竖横屏各通过指标与固定控件/保存恢复两项检查；最终指标截图取景调整后默认/2.0 竖屏及 2.0 横屏各再通过一项，重复运行不重复计入 75。
  Automated checks: All 212 unit tests passed without failures/errors/skips; debug/test APK builds and lint passed (0 errors, 15 warnings). All 76 instrumentation tests passed on an independent API 37 Pixel 9 emulator: 75 distinct Compose/SQLite checks and one package-identity check. The final 17 metric tests passed again after fixture/History assertions were refined. Four new cases cover order, invalid/failure/stopped intensity, observed zero bars and responsive columns. Existing duration/scale/palette/state/retry/save/history checks passed. Real system font 2.0 portrait and landscape each passed metric and fixed-control/save-recovery checks; final metric captures were rerun at default portrait, 2.0 portrait and 2.0 landscape. Repeated checks are not additional distinct tests.
- 实际视觉：已查看受控深浅主题的 HR/步频与速度、汇总换行、五色横条、大字号长数值/长错误/Retry、实际系统 2.0 竖横屏内容截图及 History 竖图截图；受控画面标明 no H10 data。另实际运行无 H10、权限未授予的 App，查看全部 12 张顶部/末尾截图，覆盖深浅×系统字号 1.0/2.0 竖屏和深浅×2.0 横屏，页面末尾状态/禁用原因、固定 Start/Stop、History 往返与滚动保留断言通过。视口边缘的部分文本可滚动，未发现已检查文本自身裁剪；不将受控状态或静态截图作为真实采集/实时性能证据。
  Actual visuals: Inspected controlled light/dark HR/cadence/speed, wrapping summary, colored horizontal bars, enlarged long values/errors/retry, real system 2.0 portrait/landscape content and History's vertical plot. Fixtures explicitly say no H10 data. All 12 actual no-H10, ungranted-permission App top/end captures were inspected across light/dark 1.0/2.0 portrait and light/dark 2.0 landscape; end-state/guidance, fixed Start/Stop, tab return and retained scrolling assertions passed. Partial viewport-edge content remains scrollable; no checked text's own layout clipping was found. Controlled states/static captures do not verify acquisition or live performance.
- 证据位于 build/step84b-validation/：gradle-final.txt、instrumentation-first.txt、metrics-final.txt、system-font2-*/instrumentation.txt、controlled-final/、controlled-system1-final/、system-font2-*-final/metrics/、actual-*.png，以及只用于本地验证的脚本/修改前快照/受保护源码哈希；报告与 APK 位于原 app/build/。记录中横屏两组 UI 断言已通过，外层 PowerShell 日志重定向随后报错，原输出另存 actual-landscape-output.txt；该错误未修改 App，截图和断言结果已复核。
  Evidence is under build/step84b-validation/ in the build/instrumentation logs, final controlled/system-font captures, actual PNGs and local-only scripts/baseline/protected-source hashes; reports/APKs keep their app/build/ locations. Both landscape UI assertion runs passed before an outer PowerShell log-redirection error; the returned output is preserved in actual-landscape-output.txt. This logging error did not affect App code; assertions and captures were checked.
- 范围/真机边界：本轮修改 SensorActivity.kt、session/SessionHeader.kt、session/SessionMetrics.kt，新增 heartrate/SessionHeartRateZonePanel.kt；调整 SessionHeaderTest.kt、SessionMetricsTest.kt；同步两份 AGENTS.md 和两份 prompt.md。与本轮开始时哈希比较，SDK/采集/缓冲/算法/统计/生命周期/冻结/SQLite/保存控制器/History/原曲线绘制与依赖均无变化，K=0.45 与 HR 阈值保持。Samsung/H10 未安装或操作；真实数据、更新、设备/接触/单流恢复、连接/旋转/后台及保存全链路仍 pending。8.4c、8.4d、8.5 未实施，无 commit/push。
  Scope/hardware limits: Changed SensorActivity, SessionHeader, SessionMetrics and the new SessionHeartRateZonePanel, plus SessionHeaderTest/SessionMetricsTest and both documentation pairs. Baseline hashes confirm no SDK/acquisition/buffer/algorithm/statistics/lifecycle/freeze/SQLite/save-controller/History/chart-rendering/dependency changes; K=0.45 and HR thresholds remain. No Samsung/H10 installation or operation occurred. Real data/updates/contact/stream recovery, connection/rotation/background and end-to-end saving remain pending. No 8.4c/8.4d/8.5 implementation, commit or push.
- 建议英文提交信息 / Suggested English commit message：`feat: arrange session metrics and horizontal heart rate zones`

## 2026-10-03 步骤 8.4c 实施授权 / Implementation authorization

用户要求实施 8.4c，按 AGENTS.md 5.32.3 和上方完整中英文提示词写入并验证，仅本步骤。
The user requests implementing and validating only 8.4c under AGENTS.md 5.32.3 and the complete bilingual prompts above.

## 2026-10-03 步骤 8.4c 实施结果 / Implementation results

#### 5.32.3.1 8.4c 实施与验证（2026-10-03） / Implementation and validation

- 文件已修改：LiveChartCard 的 HR/Motion/ECG 与 Cadence/Speed 改为蓝色选中、中性底色未选中的胶囊，沿用选中语义、至少 48 dp 触控区和原选择持有者。新 LiveChartPlot 展示红色 HR/浅红填充、主题蓝色 Motion/浅蓝填充及 1 dp 蓝色 ECG；每个有效连续段单独闭合填充，null/breakBefore 均断开，孤立点保留；全部可见 ECG 原值与时间保持，不平滑/抽样/补数据。
  Files changed: LiveChartCard uses blue selected/neutral unselected pills for HR/Motion/ECG and Cadence/Speed, retaining selection semantics, at least 48 dp targets and the existing selection owner. LiveChartPlot draws red HR/subtle red fill, theme-blue motion/subtle blue fill and a 1 dp blue ECG line. Fills close within each valid continuous segment; null/breakBefore split segments and isolated points remain visible. All visible ECG values/times are retained without smoothing, decimation or invented data.
- 比例尺与统计：220 dp Canvas 沿用原 chartScale 范围、10% 留白与单位精度，添加网格和不重复的纵刻度。Running 单调 mm:ss 按实测文字宽度/字号选择刻度，HR/运动最多三项，ECG 最多六项，容不下两端则 From/To 纵排，不缩字。LiveChartPanel 直接读取原 HR 与 StepState 整场 mean/max，仅将速度换算为 km/h；均值虚线使用未舍入的同一均值，仅在有数据、有效且位于当前比例尺内时绘制，不因整场极值/均值扩轴，ECG 不画统计线。HR mean 保持一位小数，步频整数、速度一位小数，未知 --。
  Scale/statistics: The 220 dp Canvas retains chartScale bounds, 10% margins and unit precision, adding grids and distinct Y ticks. Monotonic Running mm:ss ticks adapt to measured text width/font: up to three for HR/motion and six for ECG, with stacked From/To if endpoints cannot fit. LiveChartPanel reads existing whole-session HR/StepState mean/max, converting speed to km/h for display. The reference uses the same unrounded mean only with valid data and an in-range finite value; statistics never expand the window scale, and ECG has no reference line. HR mean keeps one decimal, cadence whole units and speed one decimal; unknown values remain --.
- ECG 信息和恢复：Selected sample rate 来自当前 DataReadiness.selected 的 SAMPLE_RATE；Window samples 为当前快照的非空点数，已知空窗口为 0，尚未开始/等待且无样本为 --。没有在 App 内硬编码 130/650。原空/等待/失败/冻结/配置错误和 Retry 条件保留，HR/Motion 下仍可恢复失败 ECG。60 秒 HR/运动、5 秒 ECG、100/250 ms 展示刷新、原缓存/锚点/断段/结束冻结均未改变。
  ECG/recovery: Selected sample rate comes from current DataReadiness.selected SAMPLE_RATE; Window samples counts non-null points in the selected snapshot. Known empty windows show 0; not-started/waiting states without samples show --. App code hardcodes neither 130 nor 650. Empty/waiting/failure/frozen/configuration states and original Retry guards remain, including failed ECG recovery from HR/Motion. HR/motion 60-second windows, ECG's five seconds, 100/250 ms display refresh and existing buffers/anchors/gaps/freezing are unchanged.
- 自动检查：最终离线 unit/debug/lint/test-APK 构建通过；218 单元测试，0 failures/errors/skipped，lint 0 errors、15 warnings。独立 API 37 Pixel 9 模拟器共 79 个不同仪器用例取得通过结果：78 项 Compose/SQLite 和 1 项包名基础检查。首轮完整 79 项中两项视觉 fixture 未显式从保留的 Speed 切回 Cadence而失败；修正后及最终配色调整后，15 项曲线用例全部通过，其余 64 项首轮通过。系统 font_scale=2.0 竖/横屏各重复通过四视图视觉测试，两主题均检查；重复运行不另计数量。新增 6 单元与 3 UI 用例覆盖连续段/650 原样本、均值范围/零/无效、刻度去重/宽度/四小时标签、绘线和填充缺口、非固定 ECG 200 Hz/2 点/空/未知，以及实际均值线像素和 ECG 无参考线。
  Automated checks: Final offline unit/debug/lint/test-APK tasks passed: 218 unit tests with zero failures/errors/skips; lint 0 errors/15 warnings. Across runs, all 79 distinct instrumentation cases passed on the independent API 37 Pixel 9 emulator: 78 Compose/SQLite and one package check. Two visual fixtures in the first full run failed because they did not explicitly switch the retained Speed choice back to Cadence. After correction and the final palette change, all 15 chart cases passed; the other 64 passed initially. The four-view visual case passed again with real system font 2.0 in portrait and landscape, including both themes. Repeated runs are not extra distinct cases. Six new unit and three new UI cases cover segments/all 650 original samples, mean bounds/zero/invalid values, adaptive/duplicate/four-hour ticks, line/fill gaps, non-fixed ECG 200 Hz/two samples/empty/unknown metadata, reference-line pixels and ECG without a mean line.
- 范围与证据：本轮修改 chart/LiveChartCard.kt、chart/LiveChartPanel.kt、LiveChartCardTest.kt；新增 chart/LiveChartPlot.kt、chart/LiveChartPresentation.kt、LiveChartPresentationTest.kt；同步两份 AGENTS.md 和两份 prompt.md。与实施前哈希比较，SDK/manager/采集/缓冲/LiveCharts/ChartScale/算法/K=0.45/HR 阈值与统计/会话/生命周期/SQLite/保存控制器/History/依赖无变化；LiveChartPanel 中原 History ChartPlot 文本独立比较确认未变。build/step84c-validation/ 保留 gradle-final.txt、instrumentation-first.txt、charts-final.txt、system-font2-portrait/landscape.txt、controlled-final/、system-font2-*/、baseline-hashes.json 和 history-plot-audit.txt；脚本/截图/日志只在忽略的 build 下，不进入 APK。
  Scope/evidence: Changed LiveChartCard, LiveChartPanel and LiveChartCardTest; added LiveChartPlot, LiveChartPresentation and LiveChartPresentationTest, plus both documentation pairs. Baseline hashes confirm unchanged SDK/manager/acquisition/buffers/LiveCharts/ChartScale/algorithms/K=0.45/HR thresholds/statistics/session/lifecycle/SQLite/save controller/History/dependencies. A separate text comparison confirms the original History ChartPlot inside LiveChartPanel is unchanged. Build, initial/full and final/chart instrumentation logs, real-system-font logs/captures, controlled captures, baseline hashes and the History plot audit remain in ignored build/step84c-validation/. Local scripts/captures/logs do not enter the APK.
- 实际视觉：已查看最终普通/2.0 字号深浅受控 HR、Cadence、Speed、ECG 截图，以及真实系统 font_scale=2.0 竖/横屏的红/蓝分段填充、负 ECG、三项/六项时间刻度、长错误/Retry 和页内滚动；受控页面标明 no H10 data。另运行最终 APK 的无 H10/未授予权限实际 App，查看浅色 1.0 竖屏与深色 2.0 横屏共四张 PNG，未知采样率/样本数、空 ECG、固定控件及 History 返回后的滚动/选择保留断言均通过。横屏视口小于完整图表/说明时需滚动，视口边缘部分内容不视为 Text 自身裁剪。实际 App 范围为上述两组，不声称完成所有主题/字号组合的实际 App 或实时性能验证。
  Actual visuals: Inspected final controlled light/dark normal/enlarged HR, Cadence, Speed and ECG captures, plus real-system-font 2.0 portrait/landscape segmented fills, signed ECG, three/six time ticks, long errors/retry and scrolling. Controlled pages explicitly identify no H10 data. Also ran the final no-H10/ungranted-permission App and inspected four PNGs from light 1.0 portrait and dark 2.0 landscape; unknown rate/count, empty ECG, fixed controls and retained scroll/choice after History passed. Smaller landscape viewports require scrolling through the chart/explanations; partial viewport edges are not Text's own layout clipping. Actual App coverage is limited to those two combinations, not every theme/font combination or real-time performance. Actual evidence is in actual-visual-final.txt and actual-*.png/xml under build/step84c-validation/.
- 未验证：Samsung/H10 未安装或操作；真实 HR/运动/ECG 信号、持续滚动及 ECG 性能、真实单流恢复/停止/旋转/切页及采集保存全链路仍 pending。静态/受控截图不作为真机或实时性能证据。8.4d/8.5 未实施，无 commit/push。
  Limits: No Samsung/H10 installation or operation. Real HR/motion/ECG signals, continuous scrolling/ECG performance, actual stream recovery/stopping/rotation/switching and acquisition-to-storage behavior remain pending. Static/controlled captures do not prove hardware behavior or real-time performance. No 8.4d/8.5 implementation, commit or push.
- 建议英文提交信息 / Suggested English commit message：`feat: refine live chart selectors axes and session references`

## 2026-10-03 Motion 只展示步频：文档与执行计划同步 / Cadence-only Motion documentation and execution plan

- 用户指令 / User request：注意 Motion 中不需要有速度，只需要展示步频，同步这一点到文档和执行计划中。 / Motion needs no speed; show only cadence and synchronize the documentation and execution plan.
- 采用与范围：最终 Session Motion 卡片只保留当前步频及整场 Mean/Min/Max cadence（steps/min）；Motion 图表只显示 Cadence，移除速度展示、速度曲线、km/h 和 Cadence/Speed 子切换。保留步频状态/Retry ACC、Activity summary 估计距离、底层速度计算/缓存/历史及 History 速度统计。AGENTS.md 的界面规则、5.32 最新规则和 8.4d 执行项/步骤表，以及本文件的通用要求与 b/c/d 双语提示词已更新并同步两对文档。
  Adopted scope: Final Session Motion metrics retain only current and whole-session Mean/Min/Max cadence in steps/min. Its plot shows only Cadence, removing speed displays/plots, km/h and Cadence/Speed sub-selection. Preserve cadence states/Retry ACC, summary estimated distance, underlying speed calculations/buffers/history and History speed statistics. Updated and synchronized both documentation pairs: AGENTS.md screen rules, latest 5.32 rules and 8.4d execution items/step table, plus this file's shared instructions and bilingual b/c/d prompts.
- 实施与验证状态：本轮只修改文档，App/测试文件未修改；现有 Motion 速度 UI 尚在，移除工作列入 8.4d pending。此前 b/c 实施与四视图验证记录按当时事实保留。本轮未运行新的构建、单元/Compose/SQLite、模拟器视觉或 Samsung/H10 检查，无 commit/push，不推进 8.5。
  Implementation/validation status: Documentation only; App/tests are unchanged. Existing Motion speed UI remains, with removal pending 8.4d. Prior b/c implementation and four-view validation records retain their historical meaning. No new build, unit/Compose/SQLite, emulator-visual or Samsung/H10 check, commit, push or 8.5 implementation was performed.

## 2026-10-03 图示功能对齐：8.4d 功能实施 Prompt 记录 / Recorded 8.4d functional implementation prompt

本段功能已接入，结果见下文；视觉还原未完成，当前剩余执行计划为文末 8.4e，不重复执行本段功能改造。 / These functions are implemented as recorded below. Visual fidelity remains incomplete; use the final 8.4e plan for remaining work rather than repeating this functional change.

用户要求 / User request：检查图示与规划的功能差异，严格按图示实施；明确选择“全部按图示：加入暂停/继续，HR/步频窗口改为 5 分钟”。 / Check reference/plan differences and implement the shown functions, explicitly including Pause/Continue and five-minute HR/cadence windows.

### 中文 Prompt

```text
只完成当前 Session 参考图功能对齐，先阅读 AGENTS.md 5.33 并查看 ui/Session-HR.png、Session-Motion.png、Session-ECG.png。用户最新要求覆盖此前保留速度、Min、仅 Start/Stop、60 秒窗口的例外。
1. HR 卡只显示当前 HR/bpm、强度、Max HR、Mean HR、Last received；步频卡显示 Cadence、当前值、Mean/Max steps/min。删除 Session 的 Min HR、Min cadence、全部速度指标、km/h、速度曲线和 Cadence/Speed 子切换。
2. 图表只有 HR/Motion/ECG；Motion 直接显示 Cadence。HR 红线/浅红填充，步频蓝线/浅蓝填充；两者最近 300 秒、最多 301/1201 点。图上 HR 使用 Average HR/Max HR，步频 Mean/Max；复用原整场统计和有效范围内均值虚线。ECG 保持五秒、有符号原值、全部可见样本，Sampling Rate/Samples 使用实际配置与实际窗口数量。
3. 汇总仅 Duration、Total steps、Estimated distance。HR Zone 每行仅 Z1–Z5、bpm 范围、横条、mm:ss；保留五色共尺及真实零/未知。删除正常态重复标题、说明和统计；必要错误/缺失/恢复只按真实状态出现。
4. 左 Pause、中 Start/Continue、右 Stop，实际行为按 AGENTS.md 5.33：暂停取消采集、保留连接/同场身份/累计/历史、停止计时且不保存；完成取消后才允许继续。继续重新检查连接/就绪，复用原流入口，首个真实数据恢复计时、ACC 重新预热，历史/HR步频曲线保持前段并断开、ECG 重新锚定；拒绝旧回调与重复请求。暂停时长不计入 duration/均值/区间/四小时上限。暂停中 Stop 或生命周期中断也结束并保存一次。
5. 保留算法、K=0.45、采样设置、HR阈值、SQLite字段/schema/事务和 History 页面。仅为暂停增加会话计时/历史恢复逻辑，为五分钟增加有界显示缓存；明确报告这些数据路径变化，不能再声称数据层完全未变。速度/最小值计算和保存仍保留供 History 使用。
6. 验证暂停恢复身份/累计/时间/历史断段/重新预热/一次保存/四小时边界、三视图、恢复、深浅主题、字号1.0/2.0、竖横屏、滚动和 History/SQLite 回归。运行 unit/debug/lint/测试APK与可用模拟器检查并实际查看截图；区分自动、受控视觉、实际无H10 App和 Samsung/H10，未做项 pending。同步两对文档，不 commit/push，不推进8.5。
```

### English Prompt

```text
Implement only the current Session reference-function alignment. Read AGENTS.md 5.33 and inspect the three ui/Session-*.png references. The latest user request supersedes earlier retained speed/minimum displays, Start/Stop-only controls and 60-second windows.
1. HR shows current HR/bpm, intensity, Max HR, Mean HR and Last received. Cadence shows its current value and Mean/Max steps/min. Remove Session Min HR/Min cadence, every speed metric, km/h, the speed plot and Cadence/Speed sub-selection.
2. Keep only HR/Motion/ECG; Motion directly selects Cadence. Use red HR/subtle red fill and blue cadence/subtle blue fill, both spanning the latest 300 seconds with 301/1201-point bounds. Label existing whole-session statistics Average HR/Max HR or Mean/Max, with valid in-range mean lines. ECG retains five seconds, signed original values and every visible sample; Sampling Rate/Samples use real settings and actual window counts.
3. Summary contains only Duration, Total steps and Estimated distance. HR Zone rows contain Z1–Z5, bpm range, horizontal bar and mm:ss on the existing shared five-color duration scale. Preserve genuine zeros/unknowns. Remove normal-state redundant headings/explanations/statistics; display necessary errors, missing states and recovery according to real state.
4. Place Pause, Start/Continue and Stop from left to right. Implement AGENTS.md 5.33 behavior: pause cancels acquisition, retains connection/identity/totals/history and freezes time without saving; continue is unavailable until cleanup completes. Continue rechecks connectivity/readiness and reuses original stream starts, resuming time on the first real sample and rewarming ACC. Retain prior HR/cadence/history points with explicit breaks and reanchor ECG. Reject stale events and duplicate requests. Exclude paused time from duration/means/zones/the four-hour limit. Stop while paused or lifecycle interruptions must end/freeze/save once.
5. Preserve algorithms, K=0.45, sample settings, HR thresholds, SQLite fields/schema/transactions and History UI. Limit data-path changes to pause timing/history recovery and bounded five-minute display buffers; report those changes accurately. Keep underlying speed/minimum calculations and storage for History.
6. Check pause/resume identity/totals/time/gaps/warmup/one final save/four-hour limits, the three views, recovery, themes, 1.0/2.0 fonts, orientations/scrolling and History/SQLite regression. Run unit/debug/lint/test-APK and available emulator checks, actually inspecting captures. Separate automated, controlled visuals, actual no-H10 App and Samsung/H10 evidence; mark unperformed work pending. Synchronize both documentation pairs without commit/push or 8.5.
```

## 2026-10-03 图示功能对齐实施结果 / Reference-function alignment results

#### 5.33.1 图示功能对齐实施与验证（2026-10-03） / Implementation and validation

- 文件已修改：Session HR 卡删除 Min HR/常驻重复说明，保留当前值、强度、Max/Mean 和接收时间；Cadence 卡删除全部速度/Min cadence，保留当前值及 Mean/Max，当前 HR/步频均 56 sp。Live charts 删除速度子切换、开发标题/额外说明，只提供 HR/Motion/ECG；图上字段采用 Average HR/Max HR、Mean/Max 和 Sampling Rate/Samples，轴单位和实际刻度保留。Activity Summary 仅三项；HR Zone 简化为 Z1–Z5/范围/横条/时长，未归类仅 >0 显示。未知/真实零/无效/等待/失败和恢复保留。
  Files changed: Removed Session Min HR/redundant explanations, retaining current HR, intensity, Max/Mean and reception time. Cadence retains current and Mean/Max only, removing speed/minimum displays; HR/cadence current values use 56 sp. Charts expose only HR/Motion/ECG without speed sub-selection/development headings/extra explanations, using the specified statistics/ECG metadata and actual unit/ticks. Summary has three fields; zones use Z1–Z5/range/bar/duration with positive unclassified time only. Preserve unknown/zero/invalid/waiting/failure/recovery states.
- 暂停/继续已接入真实会话控制器与原三路流入口，新增 Pausing/Paused 和暂停图标；Pause/Start或Continue/Stop 按状态启用。暂停取消订阅但保留连接/同场 UUID/开始日期/累计/历史，不提交保存；继续等待取消完成，重新检查连接/配置，首个数据恢复时钟，更新代次，ACC 重预热；HR/运动曲线及两类历史恢复保留并断段，ECG 沿原启动路径清缓存/重新锚定。暂停期间停止/中断可正常结束一次。Duration、均值、区间、四小时上限只累计实际 Running 段。
  Pause/Continue uses the real session controller and existing stream starts, adding Pausing/Paused and the pause icon. Controls follow actual state. Pause cancels streams while retaining connection/UUID/start date/totals/history without saving. Continue waits for cleanup, rechecks eligibility, resumes timing on first data, changes generation and rewarms ACC. Prior HR/motion charts and histories resume with explicit breaks; ECG clears/reanchors through its original start path. Stop/interruption during pause ends once. Duration/means/zones/four-hour limits count active Running segments only.
- 五分钟缓存已实施：HR/运动窗口 300,000 ms，缓存上限 301/1201；密集输入的十分钟受控检查确实保留 301 个 HR 点和 1200 个 250 ms 运动点，并检查过期移除。ECG 五秒与原始样本缓存未改。保存字段/schema/事务、计步/K=0.45/采样配置/HR阈值/统计公式/History 展示未改；SDK 启动/检查/采集回调和 DataSubscriptions 类文本与基线一致。会话计时、LiveCharts 缓存与两类历史 resume 是本次授权的数据路径变更，不能声称全部数据层未变。
  Five-minute caching uses 300,000 ms windows and 301/1201 bounds; dense ten-minute fixtures retain exactly 301 HR points and 1200 motion points sampled at 250 ms, with expiry verified. ECG/raw buffers remain unchanged. Save fields/schema/transactions, step algorithms/K/sample settings/HR thresholds/statistical formulas/History presentation are unchanged; SDK stream starts/checks/callbacks and DataSubscriptions text match baseline. Session timing, LiveCharts and history-resume changes are explicitly authorized data-path changes, not an unchanged data layer.
- 自动检查：最终离线 unit/debug/lint/测试 APK 构建成功；XML 224 tests，0 failures/errors/skipped；lint 0 errors、15 warnings。独立 API 37 Pixel 9 模拟器全套 OK (80 tests)，其中 79 Compose/SQLite 与 1 基础包名检查；系统 font_scale=2.0 下竖屏与横屏各 OK (3 tests)，两主题的指标/三曲线/固定控件可达性重复检查不另计不同测试。6 新单元用例覆盖暂停计时/身份/清理门控/恢复等待/失败/中断/四小时/历史断段及最终仅冻结一次；新 UI 用例覆盖 Pause/Continue/Stop 启用与路由，现有 UI 检查更新为仅图示指标/三视图，History/SQLite 回归保留。
  Automated checks: Final offline unit/debug/lint/test-APK tasks passed: 224 unit tests, zero failures/errors/skips; lint 0 errors/15 warnings. The independent API 37 Pixel 9 emulator passed all 80 cases (79 Compose/SQLite plus one package check). Real system font 2.0 portrait/landscape each passed three repeated checks of both themes/metrics/three charts/fixed controls; repeats are not additional distinct tests. Six new unit cases cover pause timing/identity/cleanup/first-data waiting/failure/interruption/four-hour limits/history breaks and one final freeze. A new UI case checks control state/routing; existing reference-display/History/SQLite checks remain.
- 首轮与修复：原窗口用例按 60 秒过期预期失败，调整为真实五分钟范围；新历史恢复用例最初误要求保留同秒旧点，按既定同桶末点替换规则修正 fixture。首轮仪器 80 项中两项仍依赖旧常驻 Running 标签/广义 Max 文本而失败，改为状态事实/实际轴约束后，最终 80 项全通过。这些修复未删除生产错误恢复路径。
  Initial failures/repairs: Earlier window tests assumed 60-second expiry and were updated to five minutes. The new history-resume fixture initially expected an old point within the same second; it now respects the existing final-point replacement rule. Two initial instrumentation cases depended on the removed persistent Running label/an overly broad Max-text match. After correcting assertions, all 80 passed. No production recovery path was removed to pass checks.
- 实际视觉：已查看本轮深浅正常/受控大字号 HR/Cadence/ECG、运动/汇总/五行区间与长错误恢复截图，以及实际系统字体 2.0 竖横屏受控指标/区间截图。画面明确标注 no H10 data，样例只来自测试 fixture；截图目录中遗留的 speed PNG 为旧轮文件，不作为本轮证据。另运行最终无 H10/未授予权限 App，查看浅色 1.0 竖屏与深色 2.0 横屏共四张 PNG；未知 ECG 信息、空图、三固定控件和 History 返回后的滚动/选择保留检查通过。大字体/横屏需要滚动，视口边缘部分内容不是 Text 自身裁剪；不宣称所有真实 App 主题/字体组合、暂停 SDK 真机行为或实时性能已验证。
  Actual visuals: Inspected current controlled theme/font HR/Cadence/ECG, metrics/summary/zones/long-recovery captures and actual-system-font 2.0 portrait/landscape metrics/zones. Fixtures explicitly identify no H10 data. Old speed PNGs left in shared capture folders are excluded. Also ran the final no-H10/ungranted-permission App and inspected four captures covering light 1.0 portrait and dark 2.0 landscape; unknown ECG metadata, empty plots, three fixed controls and retained scroll/selection after History passed. Enlarged/landscape layouts require scrolling; partial viewport edges are not Text layout clipping. This does not validate every actual App theme/font combination, hardware pause behavior or real-time performance.
- 范围与证据：修改 SensorActivity.kt；session/SessionMetrics.kt、SessionChrome.kt、SessionState.kt；chart/LiveChartCard.kt、LiveChartPanel.kt、LiveChartPlot.kt、LiveCharts.kt；heartrate/SessionHeartRateZonePanel.kt；history/HrHistory.kt、MotionHistory.kt；新增 drawable/ic_pause.xml；修改四个单元测试文件（LiveChartsTest、HrHistoryLifecycleTest、SessionStateTest、SessionSnapshotTest）和四个 UI 测试文件（SessionMetricsTest、SessionChromeTest、SessionHeaderTest、LiveChartCardTest），同步两对文档。完整日志、基线哈希/SDK与History绘图审计、受控/实际截图在忽略的 build/reference-validation/；验证脚本/图片不进入 APK。独立模拟器已关闭，未操作 Samsung。
  Scope/evidence: Changed SensorActivity; SessionMetrics/Chrome/State; LiveChartCard/Panel/Plot/LiveCharts; SessionHeartRateZonePanel; HrHistory/MotionHistory; added drawable/ic_pause.xml; updated four unit and four UI test files and both documentation pairs. Logs, baseline hashes/SDK and History-plot audits and controlled/actual captures are in ignored build/reference-validation/. Validation scripts/images do not enter the APK. The independent emulator was closed without operating Samsung.
- 未验证：Samsung/H10 的真实暂停取消/继续、旧包拒绝、HR/ACC/ECG 重订阅、计步准确率、五分钟实时滚动/ECG 性能、生命周期和采集→暂停→继续→结束→保存→重启 History 全链路仍 pending。8.4d 的功能整合与部分清理已实施，参考图视觉还原/验收未完成，现划入 8.4e（见 AGENTS.md 5.34，待实施）；8.4 整体未完成，8.5 未实施。本实施记录当轮无 commit/push。
  Limits: Samsung/H10 pause cancellation/resume/stale packets/resubscription, step accuracy, five-minute live scrolling/ECG performance, lifecycle and the full acquisition–pause–resume–stop–save–reopen-History path remain pending. Step 8.4d implements functional integration and partial cleanup. Reference layout/styling/visual acceptance remain incomplete and now belong to pending 8.4e (AGENTS.md 5.34). Overall 8.4 is incomplete; 8.5 is unimplemented. No commit/push occurred in that implementation turn.
- 建议英文提交信息 / Suggested English commit message：`feat: align session metrics and add pause resume with five-minute charts`


## 2026-10-03 步骤 8.4e：参考图布局还原与视觉验收 / Step 8.4e: Reference layout and visual acceptance

- 用户要求 / User request：提取出未实现的计划，作为 8.4e，修改两个文档的提示词与方案。 / Extract unfinished work as 8.4e and update both documents' prompts and plan.
- 状态与采用范围：8.4d 的指标清理、暂停/继续和五分钟曲线已接入；未还原的布局、外观与视觉验收划入 8.4e，详见 AGENTS.md 5.34。当前仅生成并同步两对文档，8.4e/8.5 尚未实施，没有本轮新构建、测试或设备结果，无 commit/push。旧测试结果保留原范围，不能充作 8.4e 视觉验收。
  Status/scope: Step 8.4d implements metric cleanup, Pause/Continue and five-minute charts. Remaining layout, appearance and visual acceptance now belong to 8.4e, specified in AGENTS.md 5.34. This turn only writes and synchronizes both documentation pairs; 8.4e/8.5 remain unimplemented, with no new build/test/device result or commit/push. Historical tests retain their original scope and do not validate 8.4e visuals.

### 中文 Prompt

```text
只实施 8.4e：完成 Session 参考图布局还原与视觉验收。先阅读 AGENTS.md 5.33、5.33.1、5.34，并实际查看工作区根目录 ui/Session-HR.png、ui/Session-Motion.png、ui/Session-ECG.png（Android 项目目录的 ../ui/）。比较现有代码和运行截图，先列出差异；8.4d 已接入的功能不重复实施，不推进 8.5。

1. 整体：保持连接 → HR → Cadence → 曲线 → Activity Summary → HR Zone，固定底部控制。按图示调整 Session/History 等宽导航与下划线、页面底色、细边框、小圆角、统一卡宽/边距和紧凑比例；深色模式保持清晰对比。参考图是长页面，允许滚动，不压缩为单屏。
2. 顶部：常规目标手机竖屏、系统字体 1.0 下，连接信息和 Data Streams 必须左右排列并有竖分隔线。左侧为蓝色圆形蓝牙图标、连接/电量、齿轮 Devices 入口；右侧标题居中，HR/ACC/ECG 三列等宽横排，圆点在名称上方。状态来自真实订阅，保留英文无障碍状态和原 Devices 行为。
3. HR 与 Cadence：HR 左栏上方强度标签使用圆点、淡底色和边框；大心率与 bpm 同行按基线对齐，右栏 Max HR/Mean HR 的值加粗，增加竖分隔线；底部居中紧凑 Last Received 日期/时间，使用真实手机接收时间。Cadence 左栏标题居中，大值与 steps/min 同行；右栏用 Mean/Max 简短标签及原真实统计，加竖分隔线。通过分栏、字号和间距解决常规宽度单位换行；保留必要无效/未知/失败与恢复，无速度或 Min。
4. 曲线：HR/Motion/ECG 三个等宽扁平胶囊居中；统计左右排列并加粗值，HR 用 Average HR/Max HR，Motion 用 Mean/Max；ECG 用 Sampling Rate/Samples。匹配绘图区比例、网格、轴文字、HR 红线/浅红填充、步频蓝线/浅蓝填充及 ECG 蓝线。保留 HR/步频五分钟、ECG 五秒、真实自适应轴、有效均值线、断段、冻结与数据来源，不硬编码图中的波形、坐标、130 Hz 或 650。
5. 汇总与区间：Activity Summary 标题居中，默认手机竖屏字体 1.0 下 Duration、Total Steps、Estimated Distance 三张等宽小卡同一行，标签和值居中，距离保留 m。HR Zone 标题居中，五行的 Z1–Z5、bpm 范围、彩条/淡色轨道和 mm:ss 对齐；保留原阈值、五色顺序、共同时长比例尺和未知/零/未归类规则。
6. 底部：左 Pause、中 Start/Continue、右 Stop，按图示调整圆形按钮、图标、间距、禁用色和底栏背景/高度。移除常驻按钮下文字，保留英文无障碍名称、至少 48 dp 触摸区、真实状态启用和原回调；保留安全区域，不遮挡滚动内容。
7. 自适应与范围：以目标手机正常竖屏字体 1.0 对图，先修正导致默认纵排/两列汇总的尺寸与阈值；仅在较窄空间、大字号或横屏确有需要时换行/纵排并记录。不能禁用字体缩放、裁剪数值/单位或缩小触摸区。保留必要错误、权限、预热、暂停/失败、Retry 和保存恢复。仅改现有 Session UI 与必要局部样式，不改 SDK、订阅、暂停计时、算法/K、采样、统计公式、缓存容量、保存 schema 或 History 正式布局；共用组件改动时验证 Welcome/History，不新增依赖或无关重构。
8. 验收：实际运行完整 Session 组合；无 H10 时用明确标注的测试状态覆盖有数据三视图，不能用孤立组件或图表占位替代整页。保存同设备宽度/字号的前后截图和连续滚动区域，逐项对照 AGENTS.md 5.34.1，列出通过和未解决差异。检查正常浅色三视图、深色、实际系统字体 2.0、横屏、长数字/错误、未知/零、Devices/三按钮/切图/保存恢复/History 导航及滚动可达。运行相关 Compose、既有单元回归、debug/测试 APK 构建和 lint；不能只凭测试通过宣布对图完成。
9. 交付：同步两份 AGENTS.md 和两份 prompt.md，分别记录代码、构建/自动检查、逐项视觉结果及 Samsung/H10 未验项目。只有差异清单和新视觉证据支持时才将 8.4e 标为完成；保留未解决项 pending。真实采集/性能/生命周期留原真机验收，不以模拟数据替代。无 commit/push，不实施 8.5。
```

### English Prompt

```text
Implement only 8.4e: complete Session reference layout and visual acceptance. Read AGENTS.md 5.33, 5.33.1 and 5.34 and actually inspect ui/Session-HR.png, ui/Session-Motion.png and ui/Session-ECG.png under the workspace root (../ui/ from the Android project). Compare current code and runtime captures and list differences first. Do not repeat implemented 8.4d functionality or advance to 8.5.

1. Overall: Keep connection → HR → Cadence → chart → Activity Summary → HR Zone with fixed bottom controls. Match equal-width Session/History navigation and underline, page background, thin borders, smaller corners, consistent card widths/spacing and compact proportions. Adapt contrast for dark mode. Allow scrolling for the long reference rather than compressing it into one viewport.
2. Header: At the target phone's normal portrait width and system font 1.0, place connection and Data Streams side by side with a vertical divider. On the left, use the blue Bluetooth circle, connection/battery and Devices gear. On the right, center the heading above three equal horizontal HR/ACC/ECG columns with dots above labels. Derive states from actual subscriptions and retain English accessibility state descriptions and existing Devices behavior.
3. HR/Cadence: Add a tinted, outlined intensity badge with a circular dot above the HR left column. Align the large HR value/bpm on a baseline, bold right-hand Max HR/Mean HR values, add a divider and center compact Last Received text using genuine phone reception time. Center Cadence over its left column and keep its large value/steps/min together; use concise Mean/Max labels and original statistics on the right with a divider. Adjust column proportions, type and spacing to resolve normal-width unit wrapping. Keep necessary invalid/unknown/failure/recovery states without speed or minimum metrics.
4. Charts: Center three equal-width flat HR/Motion/ECG pills. Align statistics left/right with bold values: Average HR/Max HR, Motion Mean/Max and ECG Sampling Rate/Samples. Match plot proportions, grid, axes, red HR/subtle red fill, blue cadence/subtle blue fill and blue ECG. Preserve five-minute HR/cadence, five-second ECG, real adaptive axes, valid mean lines, gaps, freezing and data sources. Never hardcode reference waveforms, axes, 130 Hz or 650.
5. Summary/zones: Center Activity Summary and keep three equal outlined Duration/Total Steps/Estimated Distance cells on one row at normal phone portrait/font 1.0, with centered labels/values and m. Center HR Zone and align Z1–Z5, bpm ranges, colored bars/tinted tracks and mm:ss across five rows. Retain thresholds, five-color order, shared duration scaling and unknown/zero/unclassified rules.
6. Footer: Place Pause, Start/Continue and Stop left to right. Match circular icons, spacing, disabled colors and footer background/height. Remove persistent captions below icons while retaining English accessibility names, at least 48 dp targets, actual enablement and existing callbacks. Keep safe insets and unobscured scroll content.
7. Responsiveness/scope: Compare normal target-phone portrait/font 1.0 first and fix dimensions/breakpoints that currently force stacked headers or two-column summaries. Wrap/stack only when narrower space, larger fonts or landscape requires it, documenting exceptions. Never disable font scaling, clip values/units or shrink touch targets. Preserve necessary errors, permissions, warmup, pause/failure, retry and save recovery. Change existing Session UI/local styles only, not SDK/subscriptions/pause timing/algorithms/K/sampling/statistical formulas/buffer capacities/storage schema/History layout. Check Welcome/History if shared components change; avoid unrelated refactors and dependencies.
8. Acceptance: Run the complete Session composition. Without H10, use explicitly labeled test states for populated HR/Motion/ECG; isolated components or chart placeholders cannot replace whole-page evidence. Save before/after captures at the same device width/font plus successive scroll regions, compare every AGENTS.md 5.34.1 item and list passed/unresolved differences. Check normal light-mode views, dark mode, actual font 2.0, landscape, long values/errors, unknown/zero states, Devices/controls/chart switching/save recovery/History navigation and scroll reachability. Run relevant Compose checks, existing unit regression, debug/test-APK builds and lint. Passing tests alone cannot establish visual completion.
9. Delivery: Synchronize both AGENTS.md copies and both prompt.md copies. Separately record code changes, build/automated checks, itemized visuals and pending Samsung/H10 validation. Mark 8.4e complete only when the difference checklist and new visual evidence support it; keep unresolved items pending. Hardware acquisition/performance/lifecycle retain their original validation scope and cannot be replaced by fixtures. Do not commit/push or implement 8.5.
```
