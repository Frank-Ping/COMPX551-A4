> 最新评分存储（2026-10-04，9.0b）：SQLite v5新增sessionStrainScore REAL，保存完整精度0–100评分，同时保留原始AU；旧记录按规则补算，History直接读已存评分。262单元、27项不同模拟器检查及深色字号2.0的5项UI重复检查通过；构建/lint通过（0 errors、23 warnings）。见5.48.8—5.48.9，覆盖下方仅展示换算规则；真机pending，无commit/push。
> Latest score persistence (2026-10-04, 9.0b): SQLite v5 adds sessionStrainScore REAL for full-precision 0–100 scores alongside raw AU. Legacy scores are backfilled under the defined rules and History reads persisted scores. All 262 unit tests, 27 distinct emulator checks and five dark/font2.0 UI repeats pass; builds/lint pass (zero errors, 23 warnings). Sections 5.48.8–5.48.9 supersede display-only conversion; hardware pending, no commit/push.


> 最新评分展示（2026-10-04，9.0b）：Session Strain 改为100×原始负荷/(原始负荷+100)，显示一位小数 / 100；SQLite保留原始AU，数据库v4/原始算法2不变。262单元、5项界面检查及深色字号2.0的5项重复检查通过，构建/lint通过（0 errors、23 warnings）。规则与结果见5.48.6—5.48.7；真机pending，无commit/push。
> Latest score display (2026-10-04, 9.0b): Session Strain now shows 100×raw/(raw+100), with one decimal out of 100. SQLite retains raw AU; database v4/raw algorithm v2 are unchanged. All 262 unit tests, five UI checks and five dark/font2.0 repeats pass; builds/lint pass (zero errors, 23 warnings). See 5.48.6–5.48.7; hardware pending, no commit/push.


> 最新修订（2026-10-04，9.0b）：Session Strain 已改为自动计算：70% 心率区间平方加权分钟 + 30% 步频等级平方×活动分钟；删除全部 RPE 输入和评分字段。SQLite v4/算法2 保留历史并重算旧 Strain。260 单元、40 项不同模拟器检查及15项主题/字号重复检查通过；构建/lint通过（0 errors、23 warnings）。规则/Prompt/验证见5.48.3—5.48.5，覆盖下方RPE方案；Samsung/H10 pending，无commit/push。
> Latest revision (2026-10-04, 9.0b): Session Strain now automatically combines 70% zone-square-weighted heart minutes and 30% squared cadence level times active minutes. RPE input/columns are removed. SQLite v4/algorithm v2 preserves history and recalculates legacy Strain. All 260 unit tests, 40 distinct emulator checks and 15 theme/font repeats pass; builds/lint pass (zero errors, 23 warnings). Sections 5.48.3–5.48.5 supersede RPE rules below; Samsung/H10 pending, no commit/push.


> 最新功能（2026-10-04，9.0b）：Activity Summary 已实现 Intensity、Cardio Load、Cadence Stability（替换 HR Recovery）及手动 RPE 的 Session Strain；SQLite v3 保留升级，旧记录不自动回算。255 单元、分轮 42 项不同模拟器检查通过；新增四项 UI 检查覆盖深浅主题及字号 1.0/2.0，构建/lint 通过（0 errors、23 warnings）。规则及验证见 5.48；Samsung/H10 pending，无 commit/push。
> Latest feature (2026-10-04, 9.0b): Activity Summary implements Intensity, Cardio Load, Cadence Stability (replacing HR Recovery) and RPE-based Session Strain. SQLite v3 preserves existing data without backfilling old metrics. All 255 unit tests and 42 distinct emulator checks across runs pass; four new UI checks cover light/dark themes and font scales 1.0/2.0. Builds/lint pass (zero errors, 23 warnings). See 5.48; Samsung/H10 pending, no commit/push.

> 最新 Session 调整（2026-10-04）：HR/Cadence 数值与单位使用 12 dp 间距并垂直居中；移除 Estimated Distance，Duration/Total Steps 放大并列、增加卡片间距。详见 5.41；独立的 Session 整页字号 2.0 与真机验收仍 pending。

> 最新交互（2026-10-04，9.0a）：删除 Browse，ECG/RR 在绘图区左滑下一窗、右滑上一窗；保持五秒/六十条与四图固定布局。3 项检查在默认浅色及字号 2.0 深色均通过，构建/lint 通过（0 errors、23 warnings）。见 5.46.6，覆盖旧弹窗交互；真机 pending，无 commit/push。
> Latest interaction (2026-10-04, 9.0a): Browse is removed. Swipe left/right in ECG/RR for the next/previous window, preserving five seconds/sixty records and fixed layout. Three checks pass in normal-font light and font-2.0 dark modes; builds/lint pass (zero errors, 23 warnings). See 5.46.6; previous dialog interaction is superseded, hardware pending, no commit/push.


> 最新修正（2026-10-04，9.0a）：Summary 四图共用固定布局，切换 ECG/RR 不再重排上方卡片；窗口控制移至 Browse 弹窗。7 项相关检查及深色/字号 2.0 重复检查通过，构建/lint 通过（0 errors、23 warnings）。见 5.46.4，覆盖 5.46.3 的按图表切换布局方案；真机 pending，无 commit/push。
> Latest fix (2026-10-04, 9.0a): Summary charts share one layout; ECG/RR no longer reflow the upper cards. Window controls move to Browse. Seven relevant checks plus dark/font-2.0 repeats and builds/lint pass (zero errors, 23 warnings). Section 5.46.4 supersedes chart-dependent layout in 5.46.3; hardware pending, no commit/push.


> 最新进度（2026-10-04，9.0a）：ECG/RR 持久化、五秒/六十条窗口浏览、SQLite v2 保留升级及异常归档中央提示已实施。244 单元、分轮 39 项不同模拟器检查及字号 2.0 两项重复检查通过；构建/lint 通过（0 errors、23 warnings）。详见 5.46.3；Samsung/H10 pending，无 commit/push。下方“未持久化/未恢复”是历史状态。
> Latest status (2026-10-04, 9.0a): ECG/RR persistence, five-second/sixty-record browsing, preserving SQLite v2 migration and centered interrupted-archive messages are implemented. All 244 unit tests, 39 distinct emulator checks and two font-2.0 repeats pass; builds/lint pass (zero errors, 23 warnings). See 5.46.3. Samsung/H10 remains pending; no commit/push. Earlier no-persistence/no-recovery statements are historical.

> Latest Session update (2026-10-04): HR/cadence values and units use a 12 dp gap and vertical centering. Remove Estimated Distance and enlarge Duration/Total Steps in one row with wider card spacing. See 5.41; separate full-page Session font-2.0 and hardware acceptance remain pending.
> 最新展示（2026-10-04）：Activity Summary 仅保留 Duration / Total Steps 两列占满首行；Estimated Distance 展示及点击日期/时间打开详情信息的功能已删除。日期时间仍显示，详见 5.40.1；此条覆盖旧的三项主指标/信息弹窗要求。
> Latest presentation (2026-10-04): Activity Summary now has a full row shared by Duration / Total Steps. Remove Estimated Distance and the date/time information dialog; retain the displayed date/time. Section 5.40.1 supersedes the older three-metric/info-dialog requirements.
> 最新进度（2026-10-04，8.5d）：History 最终整合与本轮模拟器验收完成；231 单元测试、分轮 53 项不同仪器检查通过，字号 2.0 的 6 项及最终 2 项重复检查通过；debug/测试 APK、lint（0 errors、19 warnings）通过。修复查询重组索引、错误/保存通知挤压、长数值单位和大字号占位裁切。详见 5.40。Samsung/H10、真实性能及独立的 Session 字号 2.0 问题仍 pending；无 commit/push。下方早期状态均为历史记录。
> Latest status (2026-10-04, 8.5d): Final History integration and emulator acceptance are complete. All 231 unit tests and 53 distinct instrumentation checks across separate runs pass, plus six font-2.0 repeats and two final capture repeats. Debug/test builds and lint pass (zero errors, 19 warnings). Fixes cover list recomposition, error/save layout, long-value units and large-font placeholders. See 5.40. Samsung/H10, real performance and the separate Session font-2.0 issue remain pending. No commit/push; earlier statuses below are historical.
> 最新布局（2026-10-03）：Activity Summary 按 History-detail.png 调整为默认字号单屏，主卡/空卡/图表/区间及红色描边删除按钮全部可见；附加信息改由日期旁入口打开弹窗。目标尺寸模拟器默认字号深浅主题已视觉核对，字号 2.0 使用纵向滚动避免裁切。debug/lint 通过（0 errors、19 warnings）；未写/运行测试套件，8.5d 完整验收与真机仍 pending，详见 5.39。此条覆盖下方旧详情布局记录；无 commit/push。
> Latest layout (2026-10-03): Activity Summary follows History-detail.png with all main cards, placeholders, charts, zones and a red outlined Delete button visible on one screen at normal font size. Additional information opens from the date/info entry. Target-size emulator light/dark visuals are checked; font 2.0 uses vertical scrolling. Debug/lint pass (zero errors, 19 warnings); no test suite added/run. Full 8.5d/hardware acceptance remains pending; see 5.39. This supersedes earlier detail-layout records. No commit/push.

> 最新调整（2026-10-03）：History 列表标题为居中的 History Activities，无副标题；列表/详情底部说明小字已移除。加载过渡移除闲置 Session 文本；按最新要求不显示进度指示（5.38.1），详情按 ID 重建查询状态并改用惰性列表，详见 5.38。debug/lint 通过（0 errors、22 warnings）；测试仍留最后，实际流畅度待验收，8.5d 未完成，无 commit/push。
> Latest update (2026-10-03): History has a centered History Activities list heading, no subtitle and no list/detail footer notes. Loading removes idle Session text and shows no progress indicator (5.38.1); detail state resets by ID; detail now uses lazy items. See 5.38. Debug/lint pass (zero errors, 22 warnings). Tests remain deferred; actual smoothness and full 8.5d acceptance remain pending. No commit/push.

> 当前进度（2026-10-03，8.5c）：History 已接入整场 HR/Cadence 切换图、禁用 ECG/RR、保存均值虚线、分段填充及五行横向 HR Zones/Unclassified。debug 构建和 lint 通过（0 errors、22 warnings）。按用户要求不新增/修改测试，最后统一补；自动/视觉及真机验收仍 pending。详见 5.37；8.5d 尚未实施，本轮无 commit/push。下方早期记录保留当时事实。
> Current progress (2026-10-03, 8.5c): History now has whole-session HR/Cadence charts, disabled ECG/RR, saved-mean references, segmented fills and horizontal HR Zones/Unclassified. Debug build and lint pass (zero errors, 22 warnings). Tests are deferred to final integration at the user's request; automated/visual and hardware acceptance remain pending. See 5.37. Step 8.5d is unimplemented; no commit/push this turn. Earlier records retain their historical context.

> 当前进度（2026-10-03，8.5b）：History 详情标题、Overview/Heart rate/Cadence、四项未定义指标空卡片及 Session details 折叠区已写入。debug 构建和 lint 通过（0 errors、22 warnings）。按用户要求，本轮不新增/修改测试，留到最后统一补；未运行自动测试或模拟器视觉检查，真机仍 pending。详见 5.36；8.5c—8.5d 未实施，无 commit/push。下方旧阶段记录按当时事实保留。
> Current progress (2026-10-03, 8.5b): History detail heading, Overview/Heart rate/Cadence, four placeholder cards and the Session details disclosure are implemented. Debug build and lint pass (zero errors, 22 warnings). At the user's request, tests are deferred to final integration and no test files change. No automated tests or emulator visuals run; hardware acceptance remains pending. See 5.36. Steps 8.5c–8.5d are unimplemented; no commit/push. Earlier records retain their historical context.

> 8.5a 实施状态（2026-10-03）：列表卡片、10 条自动加载、右侧滚动条已实施；分轮 41 项不同 SQLite/Compose 检查及额外字号 2.0 视觉复测通过，debug/测试 APK、lint（0 errors、22 warnings）通过。实际视觉/真机边界见文末 5.35。8.5b—8.5d 与 Samsung/H10 验收 pending；本轮无 commit/push。后文提示词设计记录按当时事实保留。
> Step 8.5a implementation (2026-10-03): List cards, automatic batches of 10 and a right-side scrollbar are implemented. Across recorded runs, 41 distinct SQLite/Compose checks plus an extra font-scale 2.0 visual repeat pass; debug/test-APK builds and lint pass (zero errors, 22 warnings). See the final 5.35 record for visual/hardware limits. Steps 8.5b–8.5d and Samsung/H10 acceptance remain pending; no commit/push this turn. Earlier prompt-design records retain their historical context.

> 8.5 提示词更新（2026-10-03）：文末已新增按 History-list.png / History-detail.png 设计的 8.5a—8.5d 中英文分步提示词。每批 10 条、触底自动加载、右侧滚动条、适度字重及未定义指标空卡片为本次设计规则。本轮只同步两份 prompt.md，8.5 App 功能尚未实施；没有运行新的构建、测试或真机验收，也没有 commit/push。下方旧记录保留其当时语境。
> Step 8.5 prompt update (2026-10-03): Bilingual prompts for 8.5a–8.5d have been added at the end, following History-list.png / History-detail.png. The design uses batches of 10, automatic loading near the end, a right-side scrollbar, moderate font weights and placeholder cards for undefined metrics. This turn synchronizes only the two prompt.md files; the 8.5 App features are not implemented. No new build, test, hardware acceptance, commit or push was performed. Earlier records below retain their original context.

> 当前状态修订（2026-10-03）：8.4e 统一管理 Session 最终展示与实施记录；Welcome、Session、History 已请求固定竖屏，目标手机尺寸的模拟旋转检查通过（5.34.3.8）。横屏布局不再作为待完成项；**2.0 字号竖屏裁切与 Samsung/H10 真机验收仍 pending**。既有默认竖屏布局、Stop 重置及三图等高记录保留，不宣称全部 UI/真机验收完成。8.5 未实施，无 commit/push。
> Current status correction (2026-10-03): Step 8.4e owns final Session presentation and its records. Welcome, Session and History request fixed portrait and pass phone-sized simulated rotation checks (5.34.3.8). Landscape layout is no longer an outstanding task; **portrait font 2.0 clipping and Samsung/H10 acceptance remain pending**. Prior default-portrait, Stop-reset and chart-height evidence remains, without claiming complete UI/hardware acceptance. No 8.5, commit or push.

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

本段功能已接入，结果见下文；当时未完成的视觉项已由后续 8.4e 实施，见文末新记录，不重复执行本段功能改造。 / These functions are implemented as recorded below; the visuals left incomplete at that time have subsequently been implemented in 8.4e, documented at the end. Do not repeat this functional change.

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


## 2026-10-03 步骤 8.4e：Session 参考图与单屏最终展示（合并版） / Consolidated Step 8.4e

- 用户要求：将原 8.4e 与 8.4f 的双语提示词和全部实施记录合并为 8.4e。下方只保留一套当前执行提示词；后续记录按时间排列，旧记录不覆盖最新要求。
  User request: Consolidate the former 8.4e/8.4f bilingual prompts and all implementation records as 8.4e. One current prompt pair follows, with chronological records that do not override newer requirements.
- 合并当轮仅整理四份文档，没有再次实施 App 或运行构建/设备测试；后续固定竖屏的修改和检查见 5.34.3.8，未 commit/push。当前默认竖屏已验证，竖屏大字号与 Samsung/H10 仍待验收。
  The merge turn reorganized four documents only; subsequent portrait-lock changes/checks are recorded in 5.34.3.8, without commit/push. Default portrait has prior verification; portrait enlarged fonts and Samsung/H10 remain pending.

### 中文 Prompt

```text
只实施合并后的 8.4e：完成 Session 参考图布局、单屏展示、卡片清理及已确认的后续交互修正。先阅读 AGENTS.md 5.33、5.34 和现有代码/实施记录，查看工作区 ui/Session-HR.png、ui/Session-Motion.png、ui/Session-ECG.png（项目目录的 ../ui/）。以用户最新修订为准，核对已实现内容，只补缺口，不重复改造 8.4d，不推进 8.5。

1. 整体与导航：Welcome、Session、History 在手机上固定竖屏，不随设备旋转切换横屏，不新增旋转监听或横屏分支。默认目标手机竖屏、系统字体 1.0 下，Session/History 等宽导航、连接/电量/三流、HR、Cadence、所选曲线、三项汇总、五行 HR Zone 和底部控制全部同屏，无主页面滚动。沿用参考图顺序、浅色底色、细边框、小圆角和深色适配。按 Galaxy A26 实际尺寸/密度及系统安全区域验收，不合并 Welcome/History。
2. 顶部与指标：连接/电量/Devices 齿轮在左，HR/ACC/ECG 三列状态灯与标签在右，保留竖分隔线和真实状态，不显示 Data Streams 标题。HR 左栏保留圆点/淡底/描边强度标签，大值与 bpm 同行；右栏 Max HR/Mean HR 居中、数值加粗，Last Received 居中且来自真实接收时间。Cadence 左栏标题与当前值/steps/min 同行布局保留，右栏 Mean/Max 居中；Session 不显示速度或 Min。
3. 曲线：HR/Motion/ECG 三个等宽胶囊；统计左右对齐，ECG 使用实际 Sampling Rate/Samples。三种绘图区统一为原基础高度 +12 dp 并保留现有字体比例，切换不改变卡片高度或挤动下方区域。HR 必要状态放在单位行右侧，不增加独立状态行；Motion/ECG 不显示状态文字行，空图不显示 Not started。保留真实自适应轴、网格、HR 红线/浅红填充、步频蓝线/浅蓝填充、ECG 蓝线、均值线、断段、HR/步频五分钟及 ECG 五秒窗口；不硬编码示例波形、采样率或计数。
4. 汇总与区间：不显示 Activity Summary 标题；Duration、Total Steps、Estimated Distance 三个等宽边框单元同排，标签/值居中、距离保留 m。沿用已确认的汇总 24 sp 数值和上下各 5 dp 内边距。HR Zone 保留标题、Z1–Z5、bpm 范围、彩条/淡轨道和 mm:ss，五行对齐；阈值、绿蓝黄橙红顺序、共同时长尺度及未知/零/未归类规则不变。
5. 间距与控制：沿用 Session 顶部 8 dp、卡片间 5 dp、主卡上下内边距各 4 dp、区间行上下各 3 dp；内容底部和控制区顶部不留额外间距，控制区底部保留 4 dp 和系统安全区域。底部为 Pause、Start/Continue、Stop，64 dp 圆形按钮、32 dp 图标，保留英文无障碍名称与真实启用条件。无按钮常驻说明，也无按钮上方 Session/Idle/Details/Save 状态行；不因切图改变下方卡片尺寸。
6. Retry 与错误：删除所有 Session 卡片中的 Retry 和相应占位，不放替代重试按钮。完整流/配置错误、权限、开始受限原因、保存状态与确认后 Discard 从已有 Devices 齿轮弹窗查看；弹窗长内容可滚动，Session 主页面不可滚动。保留底层重试能力和 History 的保存重试，不改设备扫描/连接/配置逻辑。
7. Stop：手动停止时先冻结摘要/历史并按原资格提交保存，等待订阅取消清理完成后清空当前读数、统计、区间、曲线及本场历史，计时归零、Session 回 Idle、图表回 HR；新 generation 拒绝旧数据/刷新。保留设备连接、电量、配置和已保存记录；失败待保存快照可重试，保存中或失败时仍阻止新 Start。覆盖暂停后 Stop、无数据启动及四小时时限边界；非手动结束保持原行为。
8. 范围：复用 Compose/Material 3，只修改必要展示及上述已授权 Stop 重置，不新增依赖或无关重构。保留 8.4d 的 Pause/Continue、同场身份、累计及暂停排除计时；SDK、采样、步伐/步频/距离算法、K、统计公式、缓存容量、保存资格/schema 和 History 功能不作其他改动。
9. 验证：完整运行三个视图，每种保存一张未拼接、未整页缩放的完整截图，逐项对图，检查深浅、空数据、暂停/错误、长值及 Devices/三按钮/切图/保存恢复/History 返回。无 H10 时测试数据只能存在于测试环境并明确标注。断言切图和状态变化时图表高度稳定、下方区域尺寸不变；涉及 Stop 时检查保存快照、失败重试与旧事件拒绝。运行与改动匹配的 Compose/单元检查、debug/测试 APK 构建及 lint。
10. 交付与边界：同步两份 AGENTS.md 和两份 prompt.md，分别记录代码、构建/测试、视觉证据和真机未验范围。保留真实系统竖屏大字号的失败项，左右旋转只验收页面保持竖屏，不关闭字体缩放、裁剪主要内容或擅自恢复页面滚动。默认竖屏已通过不等于所有配置完成；目前 2.0 字号竖屏和 Samsung/H10 验收仍待完成，横屏布局不再列为待办。不 commit/push，不实施 8.5。
```

### English Prompt

```text
Implement consolidated step 8.4e only: Session reference styling, single-screen presentation, card cleanup and the confirmed interaction refinements. Read AGENTS.md 5.33/5.34, current code and implementation records, and inspect the three workspace ui/Session-HR.png, Session-Motion.png and Session-ECG.png references (../ui/ from the project). Apply the latest user revisions, verify existing implementation and address gaps only; do not repeat 8.4d or advance to 8.5.

1. Overall/navigation: Keep Welcome, Session and History portrait on phones, without rotation listeners or landscape branches. At the target phone's default portrait/font scale 1.0, show equal Session/History tabs, connection/battery/streams, HR, cadence, the selected chart, three summary cells, five zones and controls together without main-page scrolling. Retain reference ordering, pale background, thin borders, small corners and dark-theme contrast. Validate Galaxy A26 dimensions/density and system insets; do not merge Welcome/History.
2. Header/metrics: Put connection/battery/Devices gear on the left and three HR/ACC/ECG light/label columns on the right, retaining the divider and real states but omitting the Data Streams title. Keep the tinted/outlined HR intensity badge with a dot, large value/bpm together, centered bold right-column Max HR/Mean HR and centered genuine Last Received time. Keep Cadence's left title/value/unit layout and centered right Mean/Max. Session shows no speed or minimum metrics.
3. Charts: Use equal HR/Motion/ECG pills, aligned statistics and actual ECG Sampling Rate/Samples. Apply the existing base height plus 12 dp and font scaling to every plot so selection does not change card height or displace lower regions. Put necessary HR state text on the right of the unit row; omit separate status rows, Motion/ECG status text and idle Not started. Retain real adaptive axes, grids, red HR/red fill, blue cadence/blue fill, blue ECG, mean lines, gaps, five-minute HR/cadence and five-second ECG. Never hardcode reference signals, sample rates or counts.
4. Summary/zones: Omit Activity Summary. Keep equal outlined Duration/Total Steps/Estimated Distance cells on one row, centered labels/values and m, using the confirmed 24 sp values and 5 dp vertical padding. Retain HR Zone, aligned Z1–Z5/range/bar-track/mm:ss rows, thresholds, green/blue/yellow/orange/red order, shared duration scaling and unknown/zero/unclassified rules.
5. Spacing/controls: Keep 8 dp above Session content, 5 dp card gaps, 4 dp vertical main-card padding and 3 dp vertical zone-row padding. No extra content-bottom or controls-top padding; retain 4 dp below controls and system insets. Use Pause, Start/Continue and Stop as 64 dp circles with 32 dp icons, English accessibility names and real enablement. No persistent captions or Session/Idle/Details/Save row above controls. Chart selection must not resize lower cards.
6. Retry/errors: Remove all Session card Retry buttons and reserved space, without replacement retry controls. Full stream/configuration errors, permissions, blocked-Start reasons, save state and confirmed Discard remain in the existing Devices gear dialog. Long dialog content may scroll; the Session page may not. Retain underlying retry methods and History save retry; preserve device scanning/connection/configuration.
7. Stop: On manual Stop, freeze summary/history and submit under existing eligibility, wait for subscription cancellation/cleanup, then clear current readings/statistics/zones/charts/session histories, zero time, return to Idle and select HR. A new generation rejects stale events/ticks. Keep connection/battery/configuration and saved records. Failed snapshots remain retryable; saving/failed states still block new Start. Cover paused Stop, empty attempts and the four-hour boundary; retain automatic-ending behavior.
8. Scope: Reuse Compose/Material 3 and change only necessary presentation plus the explicitly authorized Stop reset. No new dependencies or unrelated refactors. Preserve 8.4d Pause/Continue, session identity/totals and paused-time exclusion. Do not otherwise change SDK/sampling/step-cadence-distance algorithms/K/statistical formulas/buffer capacities/save eligibility/schema or History behavior.
9. Verification: Run all three complete views, capture each as one unstitched full-screen image without whole-page scaling, and compare the references. Check themes, empty/paused/error states, long values, Devices/controls/selections/save recovery/History return. No-H10 fixtures must be labeled and confined to tests. Assert stable chart height across selections/states and unchanged lower-region sizes; Stop changes require frozen-save/retry/stale-event checks. Run relevant Compose/unit checks, debug/test-APK builds and lint.
10. Delivery/limits: Synchronize both AGENTS.md and both prompt.md copies, separating code, builds/tests, visual evidence and hardware gaps. Retain actual portrait enlarged-font failures and verify that left/right rotation keeps pages portrait rather than disabling font scaling, clipping main content or silently restoring page scrolling. Default-portrait passes do not establish universal completion: portrait font 2.0 and Samsung/H10 acceptance remain pending; landscape layout is no longer required. No commit/push or 8.5.
```

### 8.4e 合并实施与验证记录（2026-10-03） / Consolidated implementation and validation

- 以下八组记录按实施顺序归入同一个 8.4e。旧阶段的滚动、Retry、标题、状态行和尺寸描述只代表当时版本；当前验收规则以 5.34.1—5.34.2 为准。日志及截图目录保留原名，不移动或伪造历史证据。
  Eight chronological records belong to the same 8.4e step. Earlier scrolling, Retry, headings, status rows and sizes describe historical versions only; current acceptance follows 5.34.1–5.34.2. Original evidence-directory names are retained.
- 当前验证概况：Stop 重置实施时 228 项单元测试通过；最新图表尺寸修复的 18 项 Compose 检查、debug/测试 APK 和 lint（0 errors、18 warnings）通过。其他阶段的测试数量按各轮原记录保留，不相加为一次完整回归。默认字号竖屏通过；竖屏大字号适配及 Samsung/H10 实测仍 pending；横屏改为验证固定竖屏。
  Current evidence: 228 unit tests passed during Stop-reset implementation. The latest chart-sizing change passed 18 Compose checks, debug/test-APK builds and lint (zero errors, 18 warnings). Earlier counts retain their per-run scope and are not added into one full regression total. Default-font portrait passes; portrait enlarged-font adaptation and Samsung/H10 validation remain pending; rotation now checks portrait locking.

#### 5.34.3.1 8.4e：初始参考图布局与验证（历史阶段） / Initial reference layout and checks (historical stage)

- 文件已修改：在既有 SessionHeader、SessionMetrics、SessionChrome、LiveChartCard、LiveChartPlot、SessionHeartRateZonePanel 和 SensorActivity 中调整展示，新增 SessionStyle.kt 保存 Session 局部颜色/字号/尺寸。更新三个既有 UI 测试文件，新增 SessionReferenceTest.kt 的两个完整页面检查；同步两对文档。没有新增依赖。
  Files changed: Updated seven existing Session UI files and added SessionStyle.kt for local presentation values. Updated three existing UI test files, added two complete-page cases in SessionReferenceTest.kt and synchronized both document pairs. No new dependency.

**逐项对图结果 / Itemized reference comparison**

| 5.34.1 区域 | 本次实施及实际截图结果 / Implementation and inspected result |
|---|---|
| 导航/整体 | 通过：等宽 Session/History、长下划线；浅蓝白背景、1 dp 细边框、5 dp 卡片圆角及紧凑间距；深色保持对比。 / Passed: equal tabs/long underlines, pale background, thin borders, small corners and compact spacing; dark contrast retained. |
| 连接/Data Streams | 通过：384 dp 手机宽度、字体 1.0 下左右分栏和竖线；蓝牙圆形、齿轮入口，三个流等宽横排、圆点在名称上方，保留真实状态与无障碍描述。 / Passed: side-by-side header at 384 dp/font 1.0, divider, Bluetooth circle/gear and three equal dot-above-label columns with actual state semantics. |
| HR | 通过：圆点/淡色/描边强度标签；当前 HR/bpm 同行基线对齐，右栏 Max/Mean 加粗和分隔线，真实接收时间居中。 / Passed: outlined tinted badge, baseline value/unit, bold right statistics/divider and centered reception time. |
| Cadence | 通过：左栏标题居中，当前值/steps/min 同行，右栏 Mean/Max 与竖线；无速度/Min。 / Passed: centered left title, value/unit on one row, concise right statistics/divider; no speed/minimum display. |
| 三类曲线 | 通过：三个等宽扁平胶囊、左右统计、宽浅绘图区、网格/轴；HR 红线浅红填充、步频蓝线浅蓝填充、ECG 蓝线。原自适应范围、均值线、断段和窗口保留。 / Passed: equal pills, aligned statistics, wide plots, grids/axes and reference line/fill colors; existing adaptive scales, means, gaps and windows retained. |
| Activity Summary | 通过：标题居中，默认手机字体 1.0 三项等宽同排；细边框、居中数值与 m。 / Passed: centered heading, three equal outlined cells on one row at normal phone font, centered values/unit. |
| HR Zone | 通过：居中标题，五行 zone/range/bar/time 对齐、彩条与淡轨道；保留五色顺序和共同时长比例尺。 / Passed: centered heading and aligned five-row columns, tinted tracks and original color order/shared duration scale. |
| 底部控制 | 通过：Pause、Start/Continue、Stop 三个圆形图标，去除下方文字；48 dp 触摸区、英文无障碍名称、状态启用/回调和安全区域保留。 / Passed: three icon-only circles, 48 dp targets, English accessibility names, original enablement/callbacks and safe insets. |

- 视觉证据：已实际查看三张参考图；独立 API 37 模拟器采用 1080×2340、450 dpi（逻辑宽度 384 dp），与只读查询得到的 Samsung 显示配置一致。修改前无 H10 App 截图在 `build/step84e-validation/before/`；修改后完整生产页面组合的深浅三视图、顶部和底部在 `normal-settled/`，正常浅色 HR/Motion/ECG 及深色已逐项查看。完整页面使用真实 SessionScaffold、SessionScreen 和 LiveChartCard，没有曲线占位；测试横幅明确标注 `UI TEST DATA · no H10`，数值/波形仅在 androidTest 中构造，不进入正式数据通路/APK。
  Visual evidence: Inspected all three references. The isolated API 37 emulator used 1080×2340 at 450 dpi (384 dp wide), matching a read-only Samsung display query. Before-change no-H10 App captures are in `build/step84e-validation/before/`; final complete-page theme/chart/top/bottom captures are in `normal-settled/`. Inspected each normal light chart and dark appearance. Tests compose the production scaffold, screen and chart without placeholders. The explicit `UI TEST DATA · no H10` banner and generated values/waveforms exist only in androidTest, not the production data path/APK.
- 适配与差异边界：实际系统 font_scale=2.0 的竖屏/横屏整页截图位于 `font2-portrait/system-*.png`、`font2-landscape/system-*.png`；这两个目录中非 system 文件为此前正常字号截图，不作为大字号证据。已查看大字号布局及 `metrics-regression/` 的长值/长错误恢复图。大字号竖屏允许指标/统计/汇总纵排，横屏可滚动查看大图；视口边缘只展示当前滚动区域，不关闭字体缩放。5.34.1 的布局差异已解决。与示意图不同的实际刻度、波形、数据值、系统安全区域，以及无数据/权限/预热/暂停/错误/Retry 提示由真实状态决定，不硬编码或删除来匹配示例。
  Responsive limits: Actual system font 2.0 portrait/landscape captures are the `system-*.png` files in their respective folders; other files there are earlier normal-font captures, not enlarged-font evidence. Inspected these layouts and long-value/recovery captures in `metrics-regression/`. Enlarged portrait may stack metrics/statistics/summary; landscape may require scrolling through plots. Partial viewport edges reflect scroll position, not disabled font scaling. The listed layout differences are resolved. Actual scales/waveforms/values, system insets and necessary unknown/permission/warmup/pause/error/retry messages remain state-dependent rather than hardcoded to the concepts.
- 真实 App 无 H10 检查：运行最终 debug APK，未授予蓝牙权限；浅色字体 1.0 竖屏、深色字体 2.0 横屏共四张截图均已查看，位于 `actual/`。未知 ECG 采样率/计数、空曲线、三个固定控制和 History 返回后的曲线选择/滚动保留检查通过；欢迎页入口可用。此结果不等于 H10 采集或所有真实 App 显示组合都已验证。
  Actual no-H10 App: Ran the final debug APK without Bluetooth permission. Inspected four captures in `actual/` covering light font 1.0 portrait and dark font 2.0 landscape. Unknown ECG metadata, empty plot, fixed controls, Welcome entry and retained chart/scroll after History passed. This does not validate H10 acquisition or every actual-App display combination.
- 构建/自动检查：离线 unit/debug/lint/测试 APK 构建成功；224 项单元测试，0 failures/errors/skipped；lint 0 errors、15 warnings。完整模拟器回归 `OK (82 tests)`：81 项 Compose/SQLite 与 1 项基础检查。实际 2.0 字号竖屏、横屏各重复 4 项通过，另为最终截图重跑完整页面 2 项通过，不重复计入不同测试总数。覆盖 Devices、三按钮、切图、保存恢复、History/SQLite、未知/零、长数值/错误和滚动。最终测试截图辅助仅增加等待与滚到底部；生产代码在完整回归后未再修改。
  Build/checks: Offline unit/debug/lint/test-APK tasks succeeded. All 224 unit tests passed with zero failures/errors/skips; lint has 0 errors and 15 warnings. Full emulator regression passed 82 cases (81 Compose/SQLite plus one basic check). Four cases were repeated successfully in each actual-font-2.0 orientation, and two whole-page cases were repeated for final captures; repeats are not additional distinct tests. Coverage includes Devices, controls, charts, save recovery, History/SQLite, unknown/zero, long values/errors and scrolling. Final capture-only test edits add settling time/full-bottom scrolling; production code did not change after the full regression.
- 范围核对：本次基线哈希与 Git 差异确认仅 Session 展示文件变化；SDK/订阅、Session 暂停计时、算法/K/采样、统计、缓存/历史/保存 schema、Welcome/History 内容与全局主题均未改。共享页签外观改变，History 导航/查询/删除回归通过。日志、基线、审计与截图保存在忽略目录 `build/step84e-validation/`，不进入 APK。
  Scope audit: Baseline hashes and Git changes restrict production edits to Session presentation. SDK/subscriptions, pause timing, algorithms/K/sample settings, statistics, buffers/history/storage schema, Welcome/History content and global theme are unchanged. Shared navigation tabs changed visually; History navigation/query/delete regression passed. Logs, baseline hashes, audit and captures are in ignored `build/step84e-validation/`, outside the APK.
- 阶段结果：当时的参考图布局与所列检查已完成；后续单屏要求及当前未完成项以本节合并状态为准。Samsung/H10 的真实采集、暂停重订阅、准确率、五分钟滚动/ECG 性能、生命周期与完整保存链路仍 pending。Samsung 仅查询分辨率/密度，未安装或操作其 App；独立模拟器用后关闭。8.5 未实施，无 commit/push。
  Historical result: The initial reference-layout work and listed checks passed at that time; later single-screen requirements and current limitations are governed by the consolidated status above. Samsung/H10 acquisition, pause/resubscription, accuracy, live scrolling/ECG performance, lifecycle and full save flow remain pending. Only Samsung display size/density were queried; no App installation/interaction occurred there. The isolated emulator is closed after use. No 8.5 implementation, commit or push.

#### 5.34.3.2 8.4e：单屏布局与 Retry 清理 / Single-screen layout and Retry removal

- 实施范围：只实施 8.4e。SessionScreen 移除主页面 verticalScroll，压缩卡片留白、标题/指标字号和图高；保留上下系统安全区域与至少 48 dp 的控制触摸区。导航、连接/电量/三流、HR、Cadence、所选曲线、三项汇总、五行区间及三控制在默认字号竖屏同屏。HR 与 Cadence 保留左右分栏、数值/单位同行，Motion 仍只显示步频。
  Scope: 8.4e only. Remove main-page verticalScroll, reduce card spacing/type/plot height, preserve system insets and control targets of at least 48 dp. Default portrait shows navigation, connection/battery/streams, HR, cadence, the selected chart, three summary cells, five zones and all three controls together, retaining reference columns and cadence-only Motion.
- Retry 与错误：删除 Session HR/ACC/ECG 的 Retry UI 和无用 UI 回调，底层 PolarBleManager 重试方法保留。Session 保存区域也不显示 Retry save；History 的 Retry save、底层保存控制器和资格不变。紧凑 Data error/Session/Save 提示打开 Details，保留完整流/配置/保存错误、状态及确认后 Discard。Details 长文本可在弹窗内滚动，Session 主页面没有滚动；没有在卡片内新增替代重试按钮。
  Retry/errors: Remove HR/ACC/ECG Retry UI and unused UI callbacks while retaining manager retry methods. Hide Retry save in Session only; History retry, save controller and eligibility remain unchanged. Compact Data error/Session/Save entries open full errors/state and confirmed Discard in Details. Long dialog content may scroll; the main Session does not. No replacement retry controls occupy the cards.
- 数据边界：相对本轮修改前 SHA-256 基线，app/src 仅 10 个展示文件及 5 个仪器测试文件变化，清单为 build/step84f-validation/source-audit.txt。SDK/订阅实现、算法、计时、统计、缓存、暂停继续、存储 schema/保存控制器与 History 查询/详情绘图未改；共用 SavePanel 只新增默认 true 的 showRetry，由 Session 传 false。保留 HR/步频五分钟、ECG 五秒及断段/冻结逻辑。
  Data boundary: Compared with the pre-turn SHA-256 baseline, only ten presentation files and five instrumentation test files under app/src changed; see source-audit.txt. SDK/subscription implementations, algorithms, timing, statistics, buffers, pause/resume, storage schema/controller and History queries/detail plots are unchanged. Shared SavePanel adds showRetry=true by default, overridden only in Session. Existing chart windows/gaps/freezing remain.
- 基线与正常竖屏证据：只读核实 Samsung Galaxy A26 为 1080×2340、450 dpi、font_scale=1.0；在独立 emulator-5588 使用同尺寸/密度/字号及真实系统安全区域检查。final-captures/light-HR.png、light-Motion.png、light-ECG.png 为未拼接、未整页缩放的完整生产组件测试截图，明确标注 UI TEST DATA · no H10；dark-*、empty.png、paused.png、error.png 也已检查。没有把测试数据放入生产 App。
  Baseline/default portrait evidence: Read-only Galaxy A26 checks report 1080×2340, 450 dpi and font_scale=1.0. Independent emulator-5588 uses those settings and real system insets. final-captures/light-HR.png, light-Motion.png and light-ECG.png are unstitched full-screen production-component test captures with no whole-page scaling and explicit UI TEST DATA · no H10 labeling. Dark, empty, paused and error captures were also checked. No fixtures were added to production.
- 对图检查：逐项核对 ui/Session-HR.png、Session-Motion.png、Session-ECG.png 的区域顺序、左右指标/单位、同排汇总、五行区间、曲线选择和圆形图标控制；默认字号竖屏这些主要结构及无 Retry/无滚动已落实。真实 Android 系统栏、字体渲染、动态曲线刻度及必要 Details 提示与概念图有差异；不声称逐像素一致或用户已经验收。
  Reference comparison: Checked the three ui/Session-*.png references for section order, metric columns/units, summary row, five zones, chart selection and circular icon controls. These main structures plus no Retry/no page scrolling are implemented at default portrait. Android system bars/font rendering, dynamic chart ticks and necessary Details notices differ from the concepts; this is not a pixel-identical or user-approved claim.
- 自动检查：最终 :app:testDebugUnitTest、:app:assembleDebug、:app:lintDebug、:app:assembleDebugAndroidTest 构建通过；224 单元测试，0 failures/errors/skipped；lint 0 errors、15 warnings。仪器完整首轮 83 项中 80 通过，3 个测试断言/截图定位问题修正后定向 5 项通过，合计 83 个不同测试均有通过结果（82 Compose/SQLite + 1 基础检查），并非最终一次完整 83 项重跑。两次额外系统字号/方向诊断各通过 1 项，不重复计数；诊断通过只表示采集/固定控制/无滚动检查成功，不能证明大字号或横屏全部内容可见。日志见 build/step84f-validation/gradle-final.txt、instrumentation-first.txt、instrumentation-recheck.txt、font2-portrait.txt、font1-landscape.txt。
  Automated checks: Final unit/debug/lint/test-APK tasks pass: 224 unit tests with no failures/errors/skips; lint has zero errors and 15 warnings. The initial 83-test instrumentation run passed 80; after correcting three assertion/capture-selector issues, five targeted tests passed. All 83 distinct tests therefore have passing results across runs (82 Compose/SQLite plus one basic check), not one final full-suite rerun. Two additional system-font/orientation diagnostic runs each pass the same one test and are not counted again; diagnostic success does not establish full-page fit. Logs are under build/step84f-validation.
- 实际 App：最终 APK 在独立模拟器、无 H10/未授权蓝牙状态，深浅主题下分别点击 HR/Motion/ECG，确认主要区域及三控制同屏、无可滚动主页面/Retry，Details 完整权限说明可打开，History 返回保留选择。六张 actual-light/dark-HR/Motion/ECG.png 与 actual-light.txt/actual-dark.txt 位于同一验证目录；这些是实际 App 空数据结果，不能作为真实采集结果。
  Actual App: On the independent emulator without H10/Bluetooth permission, the final APK's three chart selections in both themes show the main regions/controls without main-page scrolling or Retry. Details exposes the permission explanation and History return retains selection. Six actual-light/dark-HR/Motion/ECG.png captures and actual-light/dark.txt logs are in the validation directory. These are real empty-App checks, not sensor validation.
- 未完成适配：真实系统 font_scale=2.0 竖屏时，曲线绘图区、汇总及区间不能完整显示；font_scale=1.0 横屏时，步频数值裁切，曲线/汇总/区间不在可见区域。font2-portrait/system-light/dark.png、font1-landscape/system-light/dark.png 明确记录失败边界。没有关闭字体缩放、缩放整页、隐藏主要内容或恢复页面滚动；这些配置仍需后续布局处理，不能称 8.4e/8.4 全面完成。
  Incomplete adaptation: At real system font_scale=2.0 in portrait, the plot/summary/zones do not fit. At font_scale=1.0 in landscape, cadence is clipped and chart/summary/zones are outside the visible area. The font2-portrait and font1-landscape system-light/dark captures record these failures. Font scaling was not disabled, the entire page was not scaled, main content was not deliberately hidden and scrolling was not restored. These configurations still need layout work; 8.4e/8.4 are not universally complete.
- 交付边界：两对 AGENTS.md/prompt.md 同步，git diff --check 通过。未安装或操作 Samsung App、未采集 H10、未验证真实信号/性能/生命周期，Samsung/H10 验收仍 pending。没有实施 8.5，无 commit/push；本轮独立模拟器结束后关闭。
  Delivery limits: Both documentation pairs are synchronized and git diff --check passes. No Samsung App installation/interaction or H10 acquisition, real-signal/performance/lifecycle acceptance was performed. Samsung/H10 remains pending. No 8.5, commit or push; the independent emulator is closed after this run.

#### 5.34.3.3 8.4e：空闲文字与空间分配 / Idle labels and space allocation

- 按用户要求，正常 Idle 不再显示底部 `Session: Idle · Details`，没有为空行保留占位；空图表不再显示 `Not started`。暂停/暂停中也移除底部重复的普通状态入口，暂停状态继续由图表提示；等待数据、停止、错误以及保存状态的必要提示继续保留；错误或保存待处理时仍可打开完整详情。
  Normal Idle no longer renders `Session: Idle · Details` or its reserved row; idle charts omit `Not started`. Pausing/paused states also omit the redundant generic footer entry while the chart retains its pause status. Necessary waiting, stopped, error and save notices remain, including full details when an error/save action needs attention.
- 底部圆形按钮由 48 dp 增至 64 dp，图标为 32 dp，控制区上下内边距保持 4 dp。无 Details 行时，HR Zone 五行分别增加上下各 2 dp 的间距，卡片总计增加 20 dp；有必要详情入口时沿用紧凑区间间距，避免挤压内容。数据处理及控制回调不变。
  Footer circles increase from 48 to 64 dp, with 32 dp icons and the existing 4 dp vertical padding. Without a Details row, each HR Zone row gains 2 dp padding above/below, adding 20 dp to the card. Necessary detail states retain compact zone spacing. Data processing/control callbacks are unchanged.
- 验证：最终 debug/测试 APK 构建与 lint 通过（0 errors、18 warnings）；首轮 40 项相关仪器检查有 1 项暂停提示裁切失败，修正后的最终 25 项 Session/控制/顶部回归全部通过，图表 15 项在首轮通过，不重复计数。默认字号竖屏三图表、深浅主题、空数据/暂停/错误以及完整错误详情通过生产组件检查；实际无 H10 App 的三图表、无 Idle Details/Not started、无页面滚动及 History 返回也通过。证据位于 build/step84f-spacing-validation，最新实际截图 actual-light-HR.png，生产组件截图 verified-captures。本轮未重跑单元测试；之前大字号/横屏适配缺口仍保留，未进行 Samsung/H10 验收，未推进 8.5，未 commit/push。
  Verification: Final debug/test-APK builds and lint pass (zero errors, 18 warnings). The initial 40 relevant instrumentation checks found one clipped paused notice; after correction, all 25 final Session/control/header regression checks pass. The 15 chart checks passed earlier and are not counted again. Production-component checks cover default-font portrait, all three charts, both themes, empty/paused/error states and full errors. Actual no-H10 App checks confirm all chart selections, absent Idle Details/Not started, no page scrolling and History return. Evidence is under build/step84f-spacing-validation, including actual-light-HR.png and verified-captures. Unit tests were not rerun; previous enlarged-font/landscape limitations remain. No Samsung/H10 acceptance, 8.5, commit or push.

#### 5.34.3.4 8.4e：移除状态行并扩大图表/区间 / Remove status rows and expand charts/zones

- 按用户要求，Motion 和 ECG 曲线卡不再渲染数据状态文字行，包括等待、停止、失败及暂停/冻结文字；图表刻度、单位、统计和空数据语义保留。HR 原有必要状态行沿用。Motion/ECG 绘图区在相同宽度/字号下增高 12 dp（沿用字体比例），不改变五分钟/五秒窗口、数据或绘图算法。
  Motion/ECG omit their data-status text rows, including waiting/stopped/failed/paused labels, while preserving axes, units, statistics and empty-data semantics. HR retains its necessary status row. Motion/ECG plots gain 12 dp at the same width/font scale, retaining font scaling, chart windows and data/rendering algorithms.
- Session 按钮上方不再渲染任何 Session/Data error/Save/Details 行及占位。错误、数据不完整、开始受限原因、保存状态及确认后 Discard 统一放入顶部齿轮打开的 Devices 弹窗；History 的保存重试保留。删除原底部 SessionDetails 组件和仅用于该行的 saveSummary 参数，不保留旧入口。Devices 原扫描/连接/配置行为不变。
  Remove every Session/Data error/Save/Details footer row and its reserved space. Errors, incomplete-data notices, blocked-Start explanations, save state and confirmed Discard are available in the existing gear/Devices dialog; History save retry remains. Remove the obsolete footer SessionDetails component and saveSummary parameter. Device scanning/connection/configuration behavior is unchanged.
- 卡片间距由 4 dp 增至 5 dp；五行 HR Zone 上下间距统一各 3 dp，不再因状态入口切换卡片高度。底部 64 dp 圆形按钮及其回调保留。主要区域仍须在默认字号竖屏单屏显示。
  Card gaps increase from 4 to 5 dp. All five zone rows use 3 dp vertical padding on each side, with no status-dependent card sizing. Keep the 64 dp footer controls and their callbacks. Main regions must still fit default-font portrait without scrolling.
- 验证：debug/测试 APK 构建与 lint 通过（0 errors、18 warnings）。本轮 57 项相关 Compose 检查首轮通过 55 项；2 项旧 ECG 状态文字断言按新规则更新后定向复测通过，57 项不同检查均有通过结果，未重复计数。覆盖三种曲线、Motion/ECG 全部订阅状态文字移除、深浅主题、默认字号竖屏完整区域/文字无裁切、暂停/错误及 Devices 中的错误查看、保存阻塞/丢弃与 History 重试。实际无 H10 App 三种空图表及 History 返回检查通过；截图/日志位于 build/step84f-layout2-validation，其中 captures 为明确标注的测试数据截图，actual-light-* 为实际 App 无数据截图。本轮未改算法/订阅/保存控制器，未重跑单元测试、未验证 H10，既有大字号/横屏缺口不因此关闭；两对文档同步，未推进 8.5，无 commit/push。
  Verification: Debug/test-APK builds and lint pass (zero errors, 18 warnings). The initial 57 relevant Compose checks passed 55; after updating two obsolete ECG-status assertions, both targeted rechecks pass. All 57 distinct checks have passing results across runs without double-counting. Coverage includes all three plots, removal of Motion/ECG status text for every subscription state, both themes, default-portrait full-page/text fit, paused/error states, Devices error access, save blocking/discard and History retry. Actual no-H10 App empty-chart and History-return checks pass. Logs/screenshots are under build/step84f-layout2-validation: captures contains labeled fixtures; actual-light-* contains real empty-App captures. No algorithm/subscription/save-controller changes, unit rerun or H10 validation; enlarged-font/landscape gaps remain open. Both documentation pairs are synchronized; no 8.5, commit or push.

#### 5.34.3.5 8.4e：统计居中、汇总放大与 Stop 重置 / Centered statistics, larger summary and Stop reset

- 界面：HR 右侧 Max/Mean 与 Cadence 右侧 Mean/Max 在右栏居中；移除 Activity Summary 标题，三项汇总单元上下内边距由 2 dp 增至 5 dp、数值字号由 22 sp 增至 24 sp；Session 各主卡片上下内边距由 3 dp 增至 4 dp。沿用 5 dp 卡片间距、默认字号竖屏单屏、Motion 仅步频及已移除的状态行。
  UI: Center HR Max/Mean and cadence Mean/Max within their right columns. Remove the Activity Summary title, increase summary-cell vertical padding from 2 to 5 dp and values from 22 to 24 sp, and increase main-card vertical padding from 3 to 4 dp. Retain 5 dp card gaps, default-font single-screen portrait, cadence-only Motion and the previously removed status rows.
- 最新 Stop 规则（覆盖旧的手动停止后保留当前页面数据要求）：用户点击 Stop 后先冻结本场摘要与历史并按既有资格提交保存，再等待所有订阅取消清理完成，清空当前 HR/ACC/ECG、统计、区间、曲线缓存及本场历史，计时归零、Session 回到 Idle、曲线选择回到 HR。使用新的 generation 拒绝旧批次/刷新；暂停中、尚未收到数据及四小时时限边界均支持重置。连接、电量、配置保留；自然结束、后台/断线等非手动停止沿用此前结束处理。
  Latest Stop rule, superseding retained on-page data after manual Stop: Freeze the summary and histories and submit them under existing save eligibility, wait for subscription cancellation/cleanup, then clear current HR/ACC/ECG, statistics, zones, chart buffers and session histories, zero elapsed time, return to Idle and select HR. A new generation rejects stale batches/ticks. Reset also covers paused sessions, empty attempts and the time-limit boundary. Keep connection/battery/configuration; automatic/background/disconnection endings retain their existing behavior.
- 保存边界：清空当前 Session 不删除 History/数据库记录或待保存快照。保存控制器与 SQLite schema 不变；保存进行中/失败时仍阻止新 Start，失败快照可从 History 重试，避免新场覆盖未保存数据。图表快照按 session generation 刷新，避免重置后暂留上一场曲线。
  Save boundary: Reset does not delete History/database records or pending frozen snapshots. Save-controller and SQLite schema behavior is unchanged: saving/failed states block a new Start, and failed snapshots remain retryable through History. Chart snapshots refresh on session generation changes to avoid retaining the previous chart after reset.
- 验证：228 项单元测试全部通过（0 failures/errors/skipped），包含新增的异步清理/保存失败重试、暂停后 Stop、三流及空会话重置、时限边界检查。debug/测试 APK 构建及 lint 通过（0 errors、18 warnings）。57 项相关 Compose 检查全通过；随后修正截图 fixture 的停止后错误标记并定向复测 1 项通过，不重复计数。默认字号竖屏深浅主题及三曲线、汇总无标题/较大单元、右栏居中、停止后空页面已检查截图；实际无 H10 App 三视图和 History 返回也通过。证据为 build/step84f-stop-reset-validation，captures/after-stop.png 是明确标注的生产组件测试截图，不能作为 H10 停止流程实测；actual-light-* 为实际空数据 App 截图。
  Verification: All 228 unit tests pass with no failures/errors/skips, including new cleanup/save-retry, paused Stop, three-stream/empty-session reset and time-limit checks. Debug/test-APK builds and lint pass (zero errors, 18 warnings). All 57 relevant Compose checks pass; after correcting the capture fixture's post-Stop error flag, one targeted recheck also passes without double-counting. Screenshots verify default-font portrait in both themes/all chart modes, centered right-column statistics, larger untitled summary cells and the empty post-Stop page. Actual no-H10 App checks pass for all chart modes and History return. Evidence is under build/step84f-stop-reset-validation; captures/after-stop.png is a labeled production-component fixture, not H10 Stop validation, and actual-light-* shows the real empty App.
- 交付边界：两对文档同步；Samsung/H10 的实际停止/保存/再次 Start 尚未验证；既有大字号/横屏适配缺口仍待处理，不标记 8.4 整体完成。无 8.5、commit/push。
  Delivery limits: Both documentation pairs are synchronized. Samsung/H10 Stop/save/restart remains unverified, and enlarged-font/landscape limitations remain open; overall 8.4 is not declared complete. No 8.5, commit or push.

#### 5.34.3.6 8.4e：顶部/底部间距与流标题 / Outer spacing and stream title

- 按用户要求，Session/History 导航下方至首卡的顶部留白由 4 dp 增至 8 dp；删除 Data Streams 标题，保留 HR/ACC/ECG 指示灯、标签及真实状态。内容区底部 4 dp 留白及按钮区顶部 4 dp 留白移除，收紧区间卡与按钮的距离；按钮区底部 4 dp、系统安全区域及 64 dp 按钮保留。卡片间 5 dp、卡片内部边距、字号和图高均未修改。
  Increase the Session-page gap below the Session/History navigation from 4 to 8 dp. Remove the Data Streams title while retaining HR/ACC/ECG lights, labels and real states. Remove the content's 4 dp bottom padding and controls' 4 dp top padding to tighten the zone-to-controls gap; retain 4 dp below controls, system insets and 64 dp buttons. Card gaps (5 dp), internal padding, typography and plot heights are unchanged.
- 代码边界：本轮只改 SensorActivity.kt、SessionHeader.kt、SessionChrome.kt 三个展示文件及三个既有仪器测试文件的旧标题定位；source-audit.txt 保存本轮改动清单。采集、Stop 重置、统计、保存及 History 功能不变。
  Scope: Only three presentation files (SensorActivity.kt, SessionHeader.kt, SessionChrome.kt) and obsolete title selectors in three existing instrumentation files change; source-audit.txt records the per-turn scope. Acquisition, Stop reset, statistics, saving and History behavior remain unchanged.
- 验证：最终 debug/测试 APK 与 lint 通过（0 errors、18 warnings）。较大的初始顶部留白造成 Z5 裁切，收敛间距后最终 3 项整页检查全通过；其余 13 项相关顶部/导航/顺序检查此前通过，合计 16 个不同检查有通过结果，不表述为最终一次 16 项全量重跑。默认字号竖屏三种曲线、深浅主题、暂停/错误/空数据及无滚动完整页面已检查；本轮不重跑未改的数据层单元测试。证据为 build/step84f-edge-spacing-validation，最终生产组件截图为 final3-captures（明确标注 UI TEST DATA · no H10）。既有大字号/横屏缺口、Samsung/H10 验收仍 pending；两对文档同步，无 8.5、commit/push。
  Verification: Final debug/test-APK builds and lint pass (zero errors, 18 warnings). The initially larger top gap clipped Z5; after spacing corrections, all three final whole-page checks pass. Thirteen other header/navigation/order checks passed earlier, giving 16 distinct passing checks across runs, not one final 16-test rerun. Default-font portrait checks cover all three charts, both themes, paused/error/empty states and full non-scrolling content. Unchanged data-layer unit tests were not rerun. Evidence is under build/step84f-edge-spacing-validation; final3-captures contains labeled production-component fixtures. Existing enlarged-font/landscape gaps and Samsung/H10 acceptance remain pending. Both documentation pairs are synchronized; no 8.5, commit or push.

#### 5.34.3.7 8.4e：三类图表高度一致 / Consistent chart heights

- 修复 HR/其他图表切换时高度变化：三种 LivePlot 统一沿用 Motion/ECG 的高度公式（原基础高度 +12 dp，保留原字体比例），HR 不再少 12 dp。HR 的等待/暂停/停止/失败文字移至单位行右侧，不额外占行；Motion/ECG 仍不显示状态文字。下方 Summary、HR Zone 的尺寸、间距及全部数据处理规则不变。
  Fix chart-switch height changes by using the existing Motion/ECG plot-height formula for all three views (base height plus 12 dp, with existing font scaling). Move HR waiting/paused/stopped/failed notices to the right of the unit row instead of adding a row. Motion/ECG still omit these notices. Summary/HR Zone dimensions, spacing and data-processing rules are unchanged.
- 验证：debug/测试 APK 构建及 lint 通过（0 errors、18 warnings），18 项相关 Compose 检查全部通过。新增到既有用例的尺寸断言覆盖三视图卡片边界/绘图区高度及下方汇总/区间边界不随切换变化；状态用例检查空数据、等待、停止、失败、暂停的高度稳定。实际无 H10 App 三图表与 History 返回通过，Motion 下方区域坐标与上一轮截图 XML 一致。证据位于 build/step84f-chart-size-validation，captures 为带测试数据标记的生产组件截图，actual-light-* 为实际空数据 App。仅改两个图表展示文件及两个已有仪器测试文件；本轮未重跑数据层单元测试，既有大字号/横屏和 H10 验收边界不因此关闭。两对文档同步，无 commit/push、无 8.5。
  Verification: Debug/test-APK builds and lint pass (zero errors, 18 warnings), as do all 18 relevant Compose checks. Existing cases now assert equal card bounds/plot heights across selections and unchanged lower summary/zone bounds, plus stable heights for empty, waiting, stopped, failed and paused states. Actual no-H10 App checks pass for all selections and History return; lower-region coordinates in Motion match the previous run's XML. Evidence is under build/step84f-chart-size-validation: captures contains labeled fixtures and actual-light-* contains real empty-App captures. Only two chart presentation files and two existing instrumentation files changed. Data-layer unit tests were not rerun; enlarged-font/landscape and H10 acceptance limitations remain. Both documentation pairs are synchronized; no commit/push or 8.5.

#### 5.34.3.8 8.4e：固定手机竖屏（2026-10-03） / Fixed phone portrait

- 按用户要求固定手机页面为竖屏：AndroidManifest.xml 为 MainActivity 和 SensorActivity 均设置 screenOrientation="portrait"，覆盖 Welcome、Session、History。当前源码没有独立横屏布局分支或主动旋转代码，因此不添加方向监听或配置拦截；保留供字号/主题等配置变化使用的 ViewModel 和响应式宽度布局，更新相关注释与两个检查名称。
  Both MainActivity and SensorActivity request portrait in AndroidManifest.xml, covering Welcome, Session and History. There is no separate landscape branch or programmatic rotation code to remove; no orientation listener/configuration interception is added. Retain ViewModel ownership and responsive width handling for other configuration changes, and update the comment/two check names.
- 当前规则覆盖早期横屏适配/旋转布局要求；横屏裁切记录保留为历史证据，不再作为手机页面待完成的横屏布局任务。改为验证自动旋转开启、设备左右转动时仍保持竖屏。2.0 字号竖屏裁切与 Samsung/H10 真机采集/生命周期仍待验收；不改指标、卡片、图表尺寸、Stop 重置或数据库。
  This supersedes earlier landscape-layout acceptance. Historical clipping captures remain evidence rather than an outstanding phone-landscape layout task. Verify portrait while auto-rotation is enabled and the device turns left/right. Portrait font 2.0 clipping and Samsung/H10 acquisition/lifecycle remain pending. Metrics, card/chart sizes, Stop reset and database behavior are unchanged.
- 验证：debug/测试 APK 构建及 lint 通过（0 errors、22 warnings；比之前多 4 项方向限制提示：两个 LockedOrientationActivity、两个 DiscouragedApi，未屏蔽）。独立 API 37 手机模拟器（1080×2340、450 dpi）关闭仅模拟器的强制用户方向覆盖后，实际 APK 在 Welcome/Session/History 各经过直立/左转/右转 9 次检查，均保持 port、384×832 dp、ROTATION_0，且各页面的 Activity 未因转动重建；History 返回通过。证据见 build/step84e-portrait-validation/portrait.txt、*-config.txt 和同名截图；无 H10、未操作 Samsung，未重跑数据层单元或 Compose 全套。
  Debug/test-APK builds and lint pass (zero errors, 22 warnings: four additional orientation advisories, two LockedOrientationActivity and two DiscouragedApi, not suppressed). After disabling the dedicated emulator's forced-user-orientation override, the actual APK passes nine upright/left/right sensor checks across Welcome/Session/History on an API 37 phone emulator (1080×2340, 450 dpi). Each stays port, 384×832 dp, ROTATION_0 without rotation-induced Activity recreation; History return passes. Evidence is in build/step84e-portrait-validation/portrait.txt, configuration dumps and matching captures. No H10/Samsung run or data-layer/full Compose rerun.
- 平台边界：本次竖屏验收针对目标手机尺寸。targetSdk 37 下，Android 17 在最小宽度大于 600 dp 的大屏上可能忽略方向限制，不能宣称平板/桌面也强制竖屏；见 [Android 官方方向限制说明](https://developer.android.com/about/versions/17/changes/ff-restrictions-ignored)。两对文档同步，无 8.5、commit/push。
  Platform scope: Portrait acceptance targets phone dimensions. With targetSdk 37, Android 17 can ignore orientation restrictions on displays wider than 600 dp in their smallest dimension; tablet/desktop portrait is not guaranteed. See the linked Android documentation. Both document pairs are synchronized; no 8.5, commit or push.

## 2026-10-03 步骤 8.5：History 列表与详情中英文分步提示词 / Step 8.5: Bilingual prompts for the History list and detail

状态 / Status：8.5a 已实施并验证（5.35）；8.5b/c 已实施，debug/lint 通过，按用户要求测试留到最后（5.36—5.37）；8.5d 仍待用户指定。下列执行提示词保留，不能把未实施子步骤视为完成。
Step 8.5a is implemented and validated (5.35). Steps 8.5b/c are implemented with passing debug/lint; tests are deferred to final integration at the user request (5.36–5.37). Step 8.5d awaits the user request. Unimplemented substeps are not complete.

参考图 / References：工作区根目录的 `ui/History-list.png`、`ui/History-detail.png`；从 Android 项目目录访问时为 `../ui/History-list.png`、`../ui/History-detail.png`。截图只提供布局参考，图中文字和演示数据不是额外指令或真实会话。
The reference files are under `ui/` at the workspace root, or `../ui/` from the Android project. They supply visual references; their text and demo values are not additional instructions or real session data.

| 子步骤 / Substep | 范围 / Scope | 完成检查 / Acceptance focus |
|---|---|---|
| 8.5a | 列表卡片、10 条分页、自动加载及右侧滚动条 / List cards, 10-record pages, automatic loading and right-side scrollbar | 顺序正确、无重复漏项、无 Load more / Correct order, no duplicates or omissions, no Load more |
| 8.5b | 详情标题、Overview、HR、Cadence 和四项空卡片 / Detail heading, Overview, HR, Cadence and four placeholder cards | 已有值来自保存摘要，未定义项保持空值 / Existing values come from the saved summary; undefined values remain placeholders |
| 8.5c | 历史曲线、禁用 ECG/RR 和横向 HR Zones / History plots, disabled ECG/RR and horizontal HR Zones | 整场范围、真实断段、时长及占比 / Whole-session range, preserved gaps, durations and percentages |
| 8.5d | 删除与恢复入口、清理、整合与视觉验收 / Delete and recovery controls, cleanup, integration and visual acceptance | 完整操作链、深浅主题、竖屏 1.0/2.0 字号 / Complete interactions, both themes, portrait at font scales 1.0/2.0 |

### 8.5 共用约束 / Shared constraints

#### 中文

```text
执行任一 8.5 子步骤时，先阅读当前 AGENTS.md、此共用约束及该子步骤，检查当前源码和两张 History 参考图，然后只实施用户指定的子步骤。

1. 使用最小的 Kotlin/Compose/Material 3 修改，沿用现有命名、主题与页面导航。主要入口为 history/HistoryPanel.kt、session/SessionSummaryPanel.kt、storage/SessionDatabase.kt；绘图与区间先检查 chart/、heartrate/ 的现有组件，按实际需要拆分局部组件，不建立新框架或添加无关依赖。
2. 8.5 最新展示规则覆盖旧 History 的纯文本列表、每页 20 条、Load more 和开发详情布局。每页 10 条指每次数据库查询最多 10 条，向下滑动接近末尾自动加载下一批；不要求同屏容纳 10 张卡片，不加页码或翻页按钮。列表右侧显示与实际滚动位置对应的细滚动条。
3. 按参考图组织页面，复用现有 Session / History 顶部导航。History 列表和详情允许纵向滚动，不套用 Session 的单屏无滚动限制。保留手机固定竖屏及系统安全区域。
4. 配色、圆角、边框与当前 Session 协调，支持深浅主题。标题和关键数值使用 Medium/SemiBold（500–600），说明与单位使用 Regular（400），不照搬参考图的超粗字重。根据手机宽度确定字号，支持系统字号 1.0/2.0；大字号可换行或改变分栏，不关闭字体缩放、压缩整页或裁掉必要内容。
5. 所有界面文字及代码注释使用英文。日期按当前手机时区显示，列表日期为 dd MMM yyyy、时间为 HH:mm；详情标题沿用同一日期/时间。保留查看精确秒数和时区的会话信息入口。时长复用已有有效运动时长及格式；距离从已保存的米值除以 1000，以两位小数 km 展示，不重新估算。
6. Overview、Heart rate、Cadence、HR Zones 及图表只读已保存的摘要与历史。有效零值显示 0，缺失值显示 --。Intensity、Cardio Load、HR Recovery、Session Strain 仅显示标题和 --；不定义公式、不新增计算或数据库字段，不用当前 HR 强度替代整场 Intensity。ECG/RR 历史目前未存储，保留灰色禁用选择项，不伪造曲线或新增采集/存储。
7. 除 8.5a 必需的页大小与加载触发变更外，保留现有保存资格、SQLite schema/事务、ID 查询、稳定倒序、取消旧查询、失败恢复及确认删除。保留必要的不完整提示、未归类时间和 History 保存重试。现有设备/结束原因/完整性、最小步频及速度统计可放入简洁的 Session details 折叠区，不挤占参考图的主摘要布局，不删除已保存字段。
8. 不改变 SDK、采样、计步/距离公式、K、暂停计时、Stop 重置、Session 展示或数据库历史数据。参考图的 DEMO DATA 横幅和示例数值不进入生产；测试截图明确标注 UI TEST DATA · no H10。
9. 每步只运行与改动相关的必要检查和 debug 构建；涉及 UI 时构建测试 APK 并做相关 Compose/视觉检查，运行 lint。实际完成结果同步到两对 AGENTS.md / prompt.md，历史结果保留；分别报告文件修改、构建、自动检查、模拟器视觉与 Samsung/H10 验收。未运行的项目写明未验证，不把规划写成已完成，不自动推进下一步或 commit/push。
```

#### English

```text
For any 8.5 substep, first read the current AGENTS.md, these shared constraints and the requested substep. Inspect the current source and both History references, then implement only the substep requested by the user.

1. Make minimal Kotlin/Compose/Material 3 changes and reuse existing names, themes and navigation. Main entry points are history/HistoryPanel.kt, session/SessionSummaryPanel.kt and storage/SessionDatabase.kt. Inspect existing chart/ and heartrate/ components before changing plots or zones. Extract local components only when needed; add no new framework or unrelated dependency.
2. These latest 8.5 presentation rules supersede the old plain-text History list, 20-record pages, Load more and development detail layout. A page of 10 means a database request returns at most 10 records; scrolling near the end loads the next batch automatically. Do not squeeze 10 cards onto one screen or add page numbers/navigation buttons. Show a thin right-side scrollbar that reflects the actual scroll position.
3. Follow the references and reuse the existing Session / History navigation. Both History screens may scroll vertically; Session's single-screen, non-scrolling rule does not apply. Retain fixed phone portrait and system insets.
4. Coordinate colors, corners and borders with the current Session and support both themes. Use Medium/SemiBold (500–600) for headings and key values, and Regular (400) for supporting text and units. Avoid the references' extra-heavy type. Choose sizes for phone widths and support system font scales 1.0/2.0 through wrapping or column changes; do not disable font scaling, shrink the entire page or clip necessary content.
5. Use English UI text and code comments. Display dates in the phone's current timezone: dd MMM yyyy and HH:mm in the list, with the same date/time in the detail heading. Retain access to seconds and timezone in session information. Reuse the saved active duration and existing duration formatting. Display saved metres divided by 1000 as km with two decimals; do not re-estimate distance.
6. Read Overview, Heart rate, Cadence, HR Zones and plots only from saved summaries/history. Show valid zero as 0 and missing values as --. Intensity, Cardio Load, HR Recovery and Session Strain contain only their titles and --. Define no formulas, computations or database fields, and do not substitute current-HR intensity for session Intensity. Historical ECG/RR is not stored: retain grey disabled choices without fabricated plots or new acquisition/storage.
7. Apart from the page-size/loading-trigger changes required by 8.5a, preserve save eligibility, SQLite schema/transactions, ID-based lookup, stable descending order, cancellation of old queries, recovery and confirmed deletion. Retain necessary incomplete notices, unclassified time and History save retry. Existing device/end-reason/completeness information, minimum cadence and speed statistics may use a compact Session details disclosure without crowding the main reference layout or deleting saved fields.
8. Do not change the SDK, sampling, step/distance formulas, K, pause timing, Stop reset, Session presentation or historical database records. Do not place reference DEMO DATA banners or example values in production. Label fixture captures UI TEST DATA · no H10.
9. Run necessary checks relevant to each change and a debug build. For UI changes, build the test APK, run relevant Compose/visual checks and lint. Synchronize actual results in both AGENTS.md / prompt.md pairs while retaining historical records. Distinguish file changes, builds, automated checks, emulator visuals and Samsung/H10 acceptance. Mark unperformed checks unverified; do not present plans as completed work, advance automatically or commit/push.
```

> 2026-10-03 最新展示要求：列表标题改为居中的 History Activities，删除副标题与列表/详情最下方常驻说明小字。History 仅显示仍在保存或失败的恢复入口，不显示当前 Session 的闲置状态/终态文字；加载不显示进度指示或 Loading 文本，详情采用按 ID 独立查询状态和惰性布局（最新覆盖见 5.38.1）。覆盖下列旧标题、副标题、底部说明和状态展示要求；不删除必要错误、保存恢复或删除确认。详见 5.38，测试最后统一补写。
> Latest display requirements (2026-10-03): Center History Activities, remove the subtitle and permanent list/detail footer notes. Show History save status/recovery only while saving or failed; omit idle/current-session/terminal status text. Show no loading indicators or Loading text; retain ID-scoped detail query state and lazy detail layout (latest override: 5.38.1). This supersedes the earlier headings/footer/status requirements, while preserving errors, save recovery and deletion confirmation. See 5.38; tests remain deferred.
### 8.5a：历史列表与每批 10 条自动加载 / History list and automatic loading in batches of 10

#### 中文

```text
按本文件“8.5 共用约束”实施 8.5a，直接修改所需文件，只完成 History 列表：

1. 查看 ui/History-list.png 及 HistoryPanel.kt、SessionDatabase.kt 和既有列表测试。保留顶部 Session / History 导航；列表内容为 Saved activities、Most recent first 和可点击卡片。
2. 卡片左侧显示日期和时间，右侧显示 Duration、时长及进入详情箭头；整卡点击仍按真实 session ID 打开现有详情。主卡不再堆放步数/距离等旧列表文本，必要时保留简短 Incomplete 标记。使用适度字重、细边框、圆角与合理留白。
3. 将数据库页大小及 UI 是否还有下一批的判断统一改为 10，沿用 startedAt DESC、id DESC 和现有游标。删除 Load more 按钮与旧点击加载路径。滑到接近末尾时自动加载下一批并追加；同一时刻只允许一次请求，重组不得重复加载，空批/不足 10 条后停止继续请求。恰好 10 条允许下一次空查询确认结束，不添加总数查询或新分页框架。
4. 使用适合列表的单一纵向滚动容器，右侧添加细滚动条，位置和滑块随当前列表滚动更新；内容不足一屏时不显示误导性滑块。不用固定装饰线冒充滚动条，不要求额外拖拽跳转。大字号时卡片可重排，保持整卡可点击和至少 48 dp 触摸目标。
5. 保留空列表、首次加载、追加加载、查询失败及 Retry query。追加失败时保留已加载卡片并暂停自动请求，用户重试同一游标；不无限自动重试。保留新保存、重新进入列表及删除成功后的既有刷新规则和旧查询取消行为。
6. 调整现有 SQLite/HistoryList/HistoryPanel 检查，覆盖 0/1/10/11/超过 20 条、相同开始时间的稳定顺序、连续加载无重复漏项、触底并发保护、末页停止、失败后重试和卡片打开正确 ID。检查默认/大字号和深浅主题列表截图；测试数据只放测试环境。
7. 运行相关检查、debug/测试 APK 构建和 lint，记录真实结果与证据。同步两对文档，只记录 8.5a 已实际完成部分；详情正式卡片、曲线及最终整合分别留给 8.5b—8.5d，不自动推进。
```

#### English

```text
Implement 8.5a under this file's shared 8.5 constraints. Modify the necessary files directly and complete only the History list:

1. Inspect ui/History-list.png, HistoryPanel.kt, SessionDatabase.kt and existing list tests. Retain Session / History navigation and show Saved activities, Most recent first and clickable cards.
2. Put date/time on the left and Duration, its value and a detail arrow on the right. Tapping the card must open the existing detail by the actual session ID. Remove the old steps/distance text from the main list card, retaining a concise Incomplete marker when needed. Use moderate weight, thin borders, rounded corners and suitable spacing.
3. Change both database page size and the UI's next-page decision to 10 while retaining startedAt DESC, id DESC and the existing cursor. Remove Load more and its old click-loading path. Scrolling near the end loads and appends the next batch; allow one request at a time and no duplicate requests from recomposition. Stop after an empty or short batch. A full batch of exactly 10 may require a subsequent empty request to detect the end; add no count query or pagination framework.
4. Use one vertical scrolling container appropriate for the list and add a thin right-side scrollbar whose thumb and position follow the current list. Do not show a misleading thumb for content shorter than the viewport. Do not use a static decorative line as a scrollbar or add drag-to-seek requirements. Cards may reflow at large font sizes; retain whole-card interaction and at least 48 dp touch targets.
5. Preserve empty, initial-loading, append-loading, query-failure and Retry query states. On append failure, retain existing cards and suspend automatic requests until the user retries the same cursor; do not retry indefinitely. Preserve refresh after a new save, list re-entry or successful deletion, and cancellation of outdated queries.
6. Adapt existing SQLite/HistoryList/HistoryPanel checks for 0/1/10/11/more than 20 records, stable ordering for identical start times, loading without duplicates/omissions, concurrent end-of-list triggers, end detection, failure/retry and opening the correct ID. Inspect both themes at normal/large font sizes. Keep fixtures in tests only.
7. Run relevant checks, debug/test-APK builds and lint; record actual results/evidence. Synchronize both documentation pairs and report only completed 8.5a work. Leave formal detail cards, plots and final integration to 8.5b–8.5d; do not advance automatically.
```

> 2026-10-03 单屏覆盖：Activity Summary 在目标手机默认字号下一屏展示参考图的主卡片、曲线、区间与红色描边删除按钮，按可用高度分配区域；附加 Session details 放入日期/信息入口弹窗。主指标整数、距离两位 km；大字号或较小视口保留自然高度滚动，不关闭字体缩放。无进度指示，真实数据/空值/ECG-RR 禁用规则保留。此要求覆盖下列默认滚动详情、页内折叠信息和旧尺寸要求，实施/视觉证据见 5.39；完整测试仍留最后。
> Single-screen override (2026-10-03): At normal font size on the target phone, fit reference summary cards, plots, zones and the red outlined Delete button using available-height allocation. Put extra Session details in the date/info dialog. Main metrics use integers and distance two-decimal km. Use natural-height scrolling for large text/small viewports without disabling font scaling. Keep no loading indicator, real data/null semantics and disabled ECG/RR. This supersedes earlier default scrolling, inline disclosure and sizing rules; see 5.39. Full tests remain deferred.
### 8.5b：详情摘要与未定义指标空卡片 / Detail summary and placeholder cards for undefined metrics
> 2026-10-03 用户最新覆盖：实施 8.5b，本轮不写/修改测试，最后统一补写。此要求覆盖本节第 6/7 项和共用约束中的本轮测试安排。本轮仅运行 debug 构建和 lint，未运行自动/视觉测试；最终整合时补写与调整测试并完成实际深浅主题/字号检查。
> Latest user override (2026-10-03): Implement 8.5b without writing/modifying tests; write them together at final integration. This supersedes this section's items 6/7 and the shared per-step test schedule. This turn runs only debug build and lint, without automated/visual tests. Add/adapt tests and perform actual theme/font checks at final integration.

#### 中文

```text
在 8.5a 基础上，按“8.5 共用约束”只实施 8.5b，直接修改 History 详情展示：

1. 查看 ui/History-detail.png、HistoryPanel.kt、SessionSummaryPanel.kt、SessionRecord.kt。使用已有按 ID 读取的 SessionSnapshot，不从当前 Session 或曲线反算摘要。保留顶部导航；详情标题为返回箭头、Activity Summary 和会话日期时间，大字号允许日期换行。
2. 从上到下实现 Overview、Heart rate、Cadence。Overview 为 Duration、Total Steps、Estimated Distance 三栏；HR 左侧为 Mean HR 与 bpm，右侧 Range 使用保存的 minimumHr—maximumHr；Cadence 左侧 Max、右侧 Mean，单位 steps/min。单位与数字清楚分层，字重按共用约束，不使用参考图示例值。
3. 添加 Intensity、Cardio Load、HR Recovery 三张并排卡片，随后添加整宽 Session Strain。默认字号按图布局，大字号可换行。四项始终只显示标题和 --；不从心率区间、距离、平均 HR 或其他数据推算，也不增加字段、算法、后台任务或依赖。
4. 有效 0 与缺失 -- 明确区分；某一 HR 范围端点缺失时该端点显示 --，不伪造范围。距离仅将已保存米数转换为两位小数 km。用当前时区统一列表和详情日期，保留精确时间/时区的详情信息。
5. 替换旧 Session record (development check) 文本堆叠。将已有设备、结束原因、完整性说明，以及截图主卡未展示的最小步频和估计速度统计整理到简洁的 Session details 折叠区；不丢失必要的缺流/无有效窗口解释。加载/失败/找不到记录/返回和已有删除确认保持可用。曲线与区间仍保留现有实现，正式重排留 8.5c。
6. 用已有/必要的 Compose 检查覆盖摘要取值、0/缺失、四项空卡片、长日期/数值、部分会话、返回和详情错误重试。查看深浅主题及实际系统字号 1.0/2.0 的竖屏截图，所有内容可滚动到达，卡片内部文字不裁切。
7. 运行相关检查、debug/测试 APK 构建和 lint，同步两对文档中的实际结果，明确 8.5b 的完成与未验证范围。不执行 8.5c/8.5d，不定义四项新指标的计算规则。
```

#### English

```text
After 8.5a, implement only 8.5b under the shared 8.5 constraints. Modify the History detail presentation directly:

1. Inspect ui/History-detail.png, HistoryPanel.kt, SessionSummaryPanel.kt and SessionRecord.kt. Use the SessionSnapshot loaded by ID; do not derive summaries from the current Session or plots. Retain navigation and show a back arrow, Activity Summary and session date/time; the date may wrap at large font sizes.
2. Implement Overview, Heart rate and Cadence in that order. Overview has Duration, Total Steps and Estimated Distance. HR shows Mean HR with bpm on the left and Range from saved minimumHr—maximumHr on the right. Cadence shows Max on the left and Mean on the right, in steps/min. Separate units visually from values, use the shared font-weight rules and never copy example values.
3. Add three side-by-side cards for Intensity, Cardio Load and HR Recovery, followed by a full-width Session Strain card. Follow the reference at normal font size and allow wrapping at large sizes. All four always contain only the title and --. Do not infer them from zones, distance, mean HR or other data, or add fields, algorithms, background work or dependencies.
4. Distinguish valid 0 from missing --. If an HR range endpoint is missing, show -- for that endpoint without inventing a range. Convert saved metres to km with two decimals only. Use consistent current-timezone dates in list/detail and retain access to exact time/timezone information.
5. Replace the old Session record (development check) text dump. Organize existing device/end-reason/completeness information, minimum cadence and estimated-speed statistics outside the main reference cards in a compact Session details disclosure. Retain necessary missing-stream/no-valid-window explanations. Keep loading, failure, missing-record, back and existing confirmed deletion functional. Retain current charts/zones until their formal arrangement in 8.5c.
6. Use existing/necessary Compose checks for saved summary values, zero/missing data, four placeholder cards, long dates/values, partial sessions, back and detail-query retry. Inspect portrait screenshots in both themes and actual system font scales 1.0/2.0. All content must be reachable by scrolling without internal text clipping.
7. Run relevant checks, debug/test-APK builds and lint. Synchronize actual results in both documentation pairs and state completed/unverified 8.5b scope. Do not execute 8.5c/8.5d or define computations for the four new metrics.
```

### 8.5c：整场曲线与横向心率区间 / Whole-session charts and horizontal heart-rate zones
> 2026-10-03 用户最新覆盖：实施 8.5c，不写/修改测试，最后统一补写。本轮执行 debug 构建及 lint；自动测试、测试 APK 和实际视觉验收留到最终整合。此安排覆盖第 6/7 项及共用约束的本轮测试要求，未运行的项目不得记为通过。
> Latest user override (2026-10-03): Implement 8.5c without writing/modifying tests; add them at final integration. Run debug build and lint this turn, deferring automated tests, test-APK build and actual visuals. This supersedes items 6/7 and the shared per-step testing schedule; unperformed checks are not passing results.

#### 中文

```text
在 8.5b 基础上，按“8.5 共用约束”只实施 8.5c，直接修改 History 的图表与区间：

1. 按 ui/History-detail.png，在摘要卡片后放置 Activity charts，之后为 HR Zones。曲线选择使用 HR / Cadence / ECG / RR 胶囊；默认 HR，HR/Cadence 可切换。ECG/RR 灰色禁用，提供 Not recorded 的可访问性说明，不允许选出虚构或实时曲线。
2. HR 读取已保存的 hrPoints，Cadence 读取 motionPoints.cadence，横轴从 0 到该记录的完整 durationMs，标为 Duration，刻度用 mm:ss；这不是 Session 的五分钟窗口。纵轴分别为 bpm、steps/min，使用能覆盖实际数据的合理刻度，不硬编码截图的范围或波形。
3. 复用适合历史范围的现有绘图能力，实现细网格、曲线、浅色分段填充和已保存均值的虚线参考。缺失值和 breakBefore 必须断开线与填充，不跨缺口连接；均值使用保存摘要，不从降采样曲线重新计算。无有效数据时显示明确空状态，不绘制假零值曲线；单点、平值和零时长不得产生无效坐标。
4. HR Zones 使用五行横向条形，顺序和绿/蓝/黄/橙/红配色与 Session 一致。每行显示 Z1—Z5、<110 / 110–124 / 125–139 / 140–154 / ≥155 bpm、保存时长和占有效运动时长的百分比。条长使用同一比例尺度，按该区间时长 / record.durationMs 展示；沿用内部毫秒和既有截断秒显示，不改变区间累计算法。
5. 单独显示 Unclassified 时长及同分母占比，不强行让五区间达到 100%。durationMs 为 0 时占比显示 --；无有效 HR 时用空状态/--，不暗示已测得全零分布。以现有摘要语义区分“该区间确实为 0”和“没有心率数据”。
6. 测试完整/缺失/断段 HR 与步频历史、均值来源、切换、禁用 ECG/RR、单点/平值/零时长，以及区间边界标签、比例和未归类时间。检查完整会话横轴、深浅主题、大字号时标签/单位/选择项可见，并确认共享绘图修改没有改变 Session 的三图等高和五分钟/五秒行为。
7. 运行必要绘图/Compose 回归、debug/测试 APK 构建和 lint，保存明确标注测试数据的截图和实际结果，同步两对文档。不新增 ECG/RR 存储，不执行 8.5d，不改变已有摘要统计公式。
```

#### English

```text
After 8.5b, implement only 8.5c under the shared 8.5 constraints. Modify History plots and zones directly:

1. Following ui/History-detail.png, place Activity charts after summary cards and HR Zones after the chart. Use HR / Cadence / ECG / RR pills, with HR selected initially and HR/Cadence switchable. Keep ECG/RR grey and disabled with accessible Not recorded information; never show invented or live curves for them.
2. Read HR from saved hrPoints and cadence from motionPoints.cadence. The x-axis spans 0 to the record's full durationMs, labeled Duration with mm:ss ticks; it is not Session's five-minute window. Use bpm and steps/min y-axes with suitable scales covering actual data, without hardcoding reference ranges or waveforms.
3. Reuse existing plotting capabilities suitable for historical ranges. Show fine grids, curves, light segmented fills and a dashed reference from the saved mean. Null values and breakBefore must break both line and fill; never bridge gaps. Use saved summary means rather than recomputing them from downsampled history. Show an explicit empty state when no valid data exists, without a false zero curve. Single-point, constant and zero-duration cases must not produce invalid coordinates.
4. HR Zones contains five horizontal rows with Session's green/blue/yellow/orange/red order. Each row shows Z1–Z5, <110 / 110–124 / 125–139 / 140–154 / ≥155 bpm, saved duration and percentage of active duration. Use one shared bar scale based on zone duration / record.durationMs. Retain internal milliseconds and existing truncated-second display without changing accumulation rules.
5. Show Unclassified duration and percentage using the same denominator separately; do not force the five zones to total 100%. Show -- percentages for zero duration. With no valid HR, use an empty state/-- rather than implying a measured all-zero distribution. Preserve the distinction between a genuinely zero-duration zone and absent HR data.
6. Check complete/missing/segmented HR and cadence histories, saved mean sources, selection, disabled ECG/RR, single-point/constant/zero-duration data, zone labels, proportions and unclassified time. Inspect the full-session axis, both themes and large-font labels/units/choices. Ensure shared plotting changes do not alter Session's equal chart heights or five-minute/five-second behavior.
7. Run necessary plotting/Compose regression checks, debug/test-APK builds and lint. Save labeled fixture captures and actual results, and synchronize both documentation pairs. Do not add ECG/RR storage, implement 8.5d or change existing summary formulas.
```

### 8.5d：删除、恢复、清理与完整验收 / Deletion, recovery, cleanup and complete acceptance

#### 中文

```text
在 8.5a—8.5c 基础上，按“8.5 共用约束”只实施 8.5d，完成 History 整合与验收：

1. 核对两张参考图和当前实现，保留列表与详情既定顺序、10 条自动分页、滚动条、适度字重、四项空卡片及 ECG/RR 禁用状态。详情以 5.38—5.39.4 的最新展示为准：标题与日期/信息入口、三项主指标、Heart Rate、Cadence、三张空卡片、Session Strain、图表、HR Zones、Delete session；Session details 使用信息弹窗，已移除标题/说明不恢复。
2. 将底部 Delete session 做成红色文字/描边按钮，沿用现有确认弹窗和真实事务删除。确认显示所选会话的日期时间；Cancel 不写入；删除中禁止重复提交和冲突返回；失败保留详情并可重试，成功返回刷新列表。不得删除其他记录或绕过确认。
3. 保留 History 的保存状态、Retry save 和确认后 Discard，以及保存中/失败时阻止新 Start 的现有逻辑；它们按真实状态出现，不为匹配截图而移除。保留空列表、加载/追加失败、找不到记录、不完整会话和必要隐私说明；用简洁正式文案替换 development check、原始字段堆叠和已失效入口。
4. 清理此次被替代的 UI 代码、参数和旧测试断言，保留仍被调用的共享组件。不添加新的指标算法、数据库迁移、导出或筛选功能；不借此重构 BLE、Session 或存储控制器。
5. 运行现有且相关的 SQLite、History 列表/详情、保存恢复、删除和导航检查，必要时更新针对实际行为的测试。覆盖连续多批加载、失败重试、新保存刷新、选中 ID、删除取消/成功/失败、Session 与 History 往返，以及所有未定义值保持 --。
6. 使用独立的目标手机尺寸模拟器检查深浅主题和真实系统字号 1.0/2.0 的竖屏布局。列表检查滚动条/首批与后续批次；详情从顶部滚到底部检查每张卡片、曲线、区间和删除按钮。确认长日期/数值/错误不裁切，空/缺失/不完整状态可理解；检查实际无 H10 App 导航与空状态。有数据的测试截图必须标注 UI TEST DATA · no H10，保留截图与日志，不操作用户真机或既有模拟器设置来制造通过结果。
7. 完成 debug/测试 APK 构建、lint 和与实际变更相符的回归；准确记录测试数量和各轮结果，不将分轮通过合并描述为一次全量通过。核对两份 AGENTS.md、两份 prompt.md 的状态与证据并同步。
8. 最终分别说明：已实现的 8.5 功能、构建/自动检查结果、实际视觉覆盖和未验证项。只在所需检查确实通过后标记相应范围完成；8.4 的 2.0 字号缺口和 Samsung/H10 验收不得因 8.5 模拟器结果被关闭。四项新指标的规则与 ECG/RR 历史继续待定义，不自动开始新功能、不 commit/push。
```

#### English

```text
After 8.5a–8.5c, implement only 8.5d under the shared 8.5 constraints to finish History integration and acceptance:

1. Compare both references with the implementation. Retain the agreed list/detail order, automatic 10-record pages, scrollbar, moderate type, four placeholder cards and disabled ECG/RR. Follow the latest presentation in 5.38–5.39.4: heading/date/info, three summary metrics, Heart Rate, Cadence, three placeholders, Session Strain, plots, HR Zones and Delete session. Session details opens as a dialog; do not restore removed captions/notes.
2. Style Delete session with red text/border while retaining the existing confirmation dialog and actual transactional deletion. Identify the selected session by date/time. Cancel must not write; block duplicate submission and conflicting back actions while deleting. Failure retains detail and retry; success returns to a refreshed list. Never delete other records or bypass confirmation.
3. Preserve History save state, Retry save and confirmed Discard, plus existing Start blocking during saving/failure. Show these when relevant rather than removing them to match a screenshot. Retain empty, initial/append-error, missing-record and incomplete-session states and necessary privacy information. Replace development check labels, raw field dumps and obsolete controls with concise production text.
4. Remove UI code, parameters and obsolete test assertions replaced by this work, while retaining shared components that are still used. Add no metric algorithms, database migrations, export or filters, and do not refactor BLE, Session or storage controllers as part of this step.
5. Run existing relevant SQLite, History list/detail, save-recovery, deletion and navigation checks, updating behavioral tests where necessary. Cover multiple automatic batches, failure/retry, refresh after saving, selected IDs, deletion cancellation/success/failure, Session/History navigation and undefined metrics remaining --.
6. Use an independent emulator at target phone dimensions to inspect portrait in both themes with actual system font scales 1.0/2.0. Check the list scrollbar and initial/later batches; scroll detail from top to bottom to inspect every card, plot, zone and deletion control. Verify long dates/values/errors fit and empty/missing/incomplete states are understandable. Check actual no-H10 App navigation and empty states. Label data fixtures UI TEST DATA · no H10 and retain captures/logs. Do not manipulate the user's phone or existing emulator settings to manufacture passing results.
7. Complete debug/test-APK builds, lint and regression checks appropriate to the actual changes. Report counts and separate runs accurately; do not describe combined passing reruns as one full successful run. Verify and synchronize status/evidence in both AGENTS.md and both prompt.md files.
8. Report implemented 8.5 behavior, builds/automated results, actual visual coverage and remaining unverified items separately. Mark only genuinely validated scope complete. Do not close 8.4's font-scale 2.0 gap or Samsung/H10 acceptance based on 8.5 emulator results. The four new metric rules and historical ECG/RR remain undefined; do not start further features or commit/push automatically.
```

## 2026-10-03 步骤 8.5a 实施结果 / Step 8.5a implementation results

### 5.35 步骤 8.5a：History 列表实施与验证（2026-10-03） / History list implementation and validation

- 实施范围：新增 history/HistoryList.kt，HistoryPanel.kt 将列表交给独立 LazyColumn，详情仍使用原有展示；SessionDatabase.kt 只将分页 LIMIT 与 UI 共用的 HISTORY_PAGE_SIZE 改为 10，schema/事务/保存资格不变。仅 3 个生产文件及 6 个既有仪器测试文件改变，清单见 build/step85a-validation/source-audit.txt；其余 Session、BLE、算法、原始缓存和历史数据不变。
  Scope: Add history/HistoryList.kt and route the list from HistoryPanel.kt into one LazyColumn, retaining existing detail presentation. SessionDatabase.kt changes only the page limit to the shared HISTORY_PAGE_SIZE of 10; schema, transactions and save eligibility are unchanged. Three production files and six existing instrumentation files change; see build/step85a-validation/source-audit.txt. Session, BLE, algorithms, raw buffers and stored history remain unchanged.
- 展示：按 ui/History-list.png 显示 Saved activities、Most recent first、日期/时间、Duration 与箭头的整张可点击卡片；按实际完整性显示 Incomplete。标题与关键数值用 600 字重，说明/单位沿用正常字重；使用与 Session 协调的深浅颜色。默认字号左右分栏，系统字号 2.0 改为上下排。保留共用顶部导航，移除旧列表开发标题、步数/距离文本和 Load more；列表页的返回由系统返回与顶部 Session 导航完成。
  Presentation: Follow ui/History-list.png with Saved activities, Most recent first and whole-card links containing date/time, Duration and an arrow; show Incomplete when appropriate. Headings/key values use weight 600; supporting text/units retain regular weights. Colors coordinate with Session in both themes. Normal font uses columns; system font scale 2.0 stacks content. Retain shared navigation and remove the old list development heading, steps/distance text and Load more. System back and the Session tab provide list navigation.
- 分页与滚动：沿用 startedAt DESC、id DESC 游标，每次最多 10 条；接近末两条时自动请求，先锁定 loading 再更新游标，重组/连续滑动不重复提交。空批或不足 10 条结束；恰好 10 条允许下一次空查询确认。失败保留已加载行、暂停自动请求，Retry query 重试同一游标；新保存、详情返回、重新进入及状态恢复重新加载首批并回到顶部。右侧细滚动条使用 Compose scrollIndicatorState 的位置/视口/内容估计随当前已加载列表更新，短列表不显示，不提供拖拽跳转；不是完整数据库总数的进度条。API 核对：[Android ScrollIndicatorState](https://developer.android.com/reference/kotlin/androidx/compose/foundation/ScrollIndicatorState)，复用当前依赖。
  Paging/scrolling: Preserve startedAt DESC, id DESC keyset pagination with at most 10 rows per query. Near the last two rows, set loading before updating the cursor to prevent duplicate requests from recomposition/flings. Empty/short pages end loading; exactly 10 may require an empty request. Failure retains rows and suspends automatic requests; Retry query retries the same cursor. Saves, detail returns, re-entry and state restoration reload the first batch at the top. The thin right scrollbar follows Compose scrollIndicatorState estimates for the currently loaded list, is hidden for short content, and does not support dragging or represent total database progress. Reuse the current dependency and the linked official API.
- 原行为保留：按真实 ID 打开详情；详情字段、图表、精确秒数/时区及删除确认未改版。列表末尾保留现有 sessionStatus/SavePanel 与隐私说明，真实保存失败可取消 Discard 后 Retry save，成功提交会刷新列表，新 Start 阻塞规则保留。四项新摘要卡片、ECG/RR、详情新曲线/区间及最终清理未实施。
  Retained behavior: Open detail by the actual ID; existing fields/plots, precise seconds/timezone and confirmed deletion remain. The list footer retains sessionStatus/SavePanel and privacy text. A real failed save can cancel Discard then Retry save; successful commit refreshes cards and existing Start blocking is preserved. New summary placeholders, ECG/RR, redesigned detail charts/zones and final cleanup are not implemented.
- 自动检查：最终 debug/测试 APK 与 lint 通过，0 errors、22 warnings。SQLite/History 首轮 27 项通过（HistoryList 10、HistoryDetail 9、HistoryPanel 2、SessionDatabase 6）；导航/共享回归另 13 项通过；新增真实列表保存失败恢复 1 项通过，合计 41 个不同测试分轮有通过结果。真实系统字号 2.0 重复执行同一视觉用例 1 项通过，不重复计入 41。覆盖 0/1/10/11/25/45 条、同时间排序、追加/重试、阻塞期间退出/恢复、末页停止、新保存刷新、正确 ID、日期和删除。测试编译曾有一处多余 import，移除后最终构建通过。本轮未重跑未改算法/数据处理的 JVM 单元测试，旧 228 项不能记作本轮结果。
  Automated checks: Final debug/test-APK builds and lint pass with zero errors and 22 warnings. The initial SQLite/History run passes 27 checks (10 HistoryList, nine HistoryDetail, two HistoryPanel and six SessionDatabase). Navigation/shared regression passes another 13; the added real-list failed-save recovery check passes one. These are 41 distinct passing checks across runs. One repeat of the visual case at actual system font scale 2.0 also passes and is not counted again. Coverage includes 0/1/10/11/25/45 rows, tied times, append/retry, blocked-query exit/restoration, exhaustion, save refresh, correct IDs, dates and deletion. An unnecessary test import initially prevented test compilation; removal restores the final passing build. Unchanged JVM algorithm/processing tests were not rerun; the earlier 228 are not this turn's results.
- 视觉证据：独立 API 37 手机模拟器 emulator-5590，1080×2340、450 dpi；font1-captures/ 与 font2-captures/ 各保存深浅主题的顶部/跨批/末尾 6 张生产组件截图，全部标注 UI TEST DATA · no H10，文字溢出检查通过。人工查看代表性默认字号浅色顶部/深色跨批、大字号浅色顶部/深色末尾，布局、换行和滚动条位置正常。实际无 H10、未授权蓝牙的 App 在深浅模式×1.0/2.0 字号四组合中，欢迎入口、History 空状态、无 Load more 和 Session 往返通过；actual-*.png/XML 保存真实 App 结果，已查看浅色 1.0 和深色 2.0 代表图。测试数据使用独立测试数据库，不写入正式 sessions.db。
  Visual evidence: Use independent API 37 phone emulator emulator-5590 at 1080×2340/450 dpi. font1-captures/ and font2-captures/ each contain six production-component screenshots covering both themes at top, later pages and end, labeled UI TEST DATA · no H10; text-overflow checks pass. Representative normal-light top/normal-dark later-page and large-light top/large-dark end captures were inspected for layout, wrapping and scrollbar position. The actual App without H10/Bluetooth permission passes Welcome entry, empty History, absent Load more and Session return in all four theme/font combinations. actual-*.png/XML records actual results; light 1.0 and dark 2.0 were visually inspected. Fixtures use separate test databases, never production sessions.db.
- 证据与交付：所有本轮日志/截图在 build/step85a-validation/，重点为 build-final.txt、instrumentation-first.txt、navigation-regression.txt、save-recovery.txt、font2-visual.txt、font2-setting.txt、actual.txt 和 source-audit.txt。两对文档同步；模拟器设置恢复后关闭本轮独立实例。未安装/操作 Samsung 或采集 H10，真机验收仍 pending。只完成 8.5a，8.5b—8.5d 尚未实施；8.4 Session 的 2.0 字号问题不因此关闭。无 commit/push。
  Evidence/delivery: Logs/captures are under build/step85a-validation/, particularly build-final.txt, instrumentation-first.txt, navigation-regression.txt, save-recovery.txt, font2-visual.txt, font2-setting.txt, actual.txt and source-audit.txt. Both documentation pairs are synchronized; restore settings and close this independent emulator. No Samsung installation/interaction or H10 acquisition; hardware acceptance remains pending. Only 8.5a is implemented; 8.5b–8.5d and the separate 8.4 Session font-scale 2.0 issue remain open. No commit/push.

### 5.36 步骤 8.5b：History 详情摘要实施（2026-10-03） / History detail summary implementation

- 本轮范围：仅修改生产文件 history/HistoryPanel.kt、session/SessionSummaryPanel.kt。按所选 ID 的已保存 SessionSnapshot 展示，未改变查询/取消/重试/删除事务、保存、数据库结构、Session、SDK 或算法；保留既有图表和区间，8.5c—8.5d 未实施。
  Scope: Change only history/HistoryPanel.kt and session/SessionSummaryPanel.kt in production. Display the saved SessionSnapshot selected by ID. Queries/cancellation/retries/deletion transactions, saving, schema, Session, SDK and algorithms remain unchanged. Retain existing plots/zones; 8.5c–8.5d are not implemented.
- 标题与主卡：返回箭头、Activity Summary、当前时区的 dd MMM yyyy · HH:mm 和必要的 Incomplete；随后为 Overview（有效时长、总步数、估计距离）、Heart rate（Mean HR/Range）和 Cadence（Max/Mean）。距离仅将保存米值除以 1000，显示两位小数 km；HR/步频平均等小数沿用两位显示，范围端点各自处理缺失，真实零不替换成 --。未从曲线或当前 Session 重算摘要。
  Header/cards: Back arrow, Activity Summary, current-timezone dd MMM yyyy · HH:mm and an Incomplete marker when needed. Follow with Overview (active duration, steps, estimated distance), Heart rate (Mean HR/Range) and Cadence (Max/Mean). Convert saved metres to km with two decimals; retain two-decimal presentation for decimal HR/cadence values. Handle each missing range endpoint independently and preserve genuine zero. No summaries are reconstructed from plots or the current Session.
- 空卡与布局：Intensity、Cardio Load、HR Recovery 默认三列，Session Strain 整宽；四项仅标题和 --，没有计算或字段。复用深浅主题颜色，卡片边框/圆角与列表协调，标题/数值使用 500–600 字重。按可用宽度及字体缩放切换纵向卡片/指标，允许标题日期和内容换行，详情仍可纵向滚动；这些为源码实现，尚无本轮实际字号截图验收。
  Placeholders/layout: Intensity, Cardio Load and HR Recovery use three columns at normal font size, followed by full-width Session Strain. Each contains only its title and --, without computations or fields. Reuse light/dark colors and list-style card borders/corners with weights 500–600. Available width and font scale select stacked cards/metrics; text may wrap and the detail scrolls. These are source-level changes; actual system-font screenshots have not been verified this turn.
- 附加信息：删除旧开发标题和摘要文本堆叠，将设备、精确开始/结束时间及 UTC 偏移、结束原因、完整性/缺流说明、最小步频与平均/最大估计速度放入默认折叠的 Session details。保留无有效 HR、无 ACC、零时长、无合格五秒窗口、ACC 缺失解释；保存状态、隐私说明、查询错误和删除确认仍有入口。
  Additional information: Replace the development headings/text dump with a collapsed Session details disclosure for device, precise start/end times and UTC offset, end reason, completeness/missing streams, minimum cadence and mean/maximum estimated speed. Retain explanations for no valid HR, no ACC, zero duration, no qualifying five-second window and incomplete ACC. Saving status, privacy text, query errors and deletion confirmation remain accessible.
- 用户覆盖与验证：按本轮“无需写测试，最后一起写”的要求，没有新增或修改 app/src/test、app/src/androidTest，也未运行单元/Compose/SQLite 测试或构建测试 APK。最后统一补写/调整摘要取值、0/缺失、四空卡、折叠区、旧文本选择器、日期、返回、错误恢复和删除回归测试。实际深浅主题/字号 1.0/2.0 视觉检查及 Samsung/H10 验收仍 pending，不沿用 8.5a 的通过数作为本轮结果。
  User override/validation: Per the request to write tests together at the end, neither app/src/test nor app/src/androidTest is added to or modified. No unit/Compose/SQLite tests or test-APK build run this turn. Add/adapt saved-value, zero/missing, placeholder, disclosure, old-text-selector, date, back, recovery and deletion regression checks during final integration. Actual light/dark/font 1.0/2.0 visuals and Samsung/H10 acceptance remain pending; do not reuse 8.5a passing counts as current results.
- 构建与证据：最终离线 :app:assembleDebug 与 :app:lintDebug 通过，lint 0 errors、22 warnings；首次 lint 新增的 ModifierParameter 提示已修正后重跑。git diff --check 通过。日志为 build/step85b-validation/build-final.txt，文件范围核对为 source-audit.txt；本轮仅两个生产文件变化，测试文件保持本轮开始时的内容。两对中英文 AGENTS.md/prompt.md 同步；未安装 APK、未操作模拟器/Samsung、无 commit/push。
  Build/evidence: Final offline :app:assembleDebug and :app:lintDebug pass with zero errors and 22 warnings after fixing the initially introduced ModifierParameter warning. git diff --check passes. See build/step85b-validation/build-final.txt and source-audit.txt. Only two production files change this turn; test files retain their starting contents. Both bilingual AGENTS.md/prompt.md pairs are synchronized. No APK installation, emulator/Samsung interaction, commit or push.

### 5.37 步骤 8.5c：History 整场曲线与横向区间实施（2026-10-03） / Whole-session History charts and horizontal zones

- 范围与顺序：新增 history/HistoryCharts.kt、history/HistoryZones.kt；HistoryPanel.kt 在摘要与四空卡之后显示 Activity charts，再显示 HR Zones、Session details 和既有删除入口。SessionSummaryPanel.kt 仅将 SummaryCard 开放为内部共用，保持卡片样式。未实施 8.5d 删除样式/最终整合，未改变数据库、采集、SDK、算法、已保存值或四项空卡规则。
  Scope/order: Add history/HistoryCharts.kt and history/HistoryZones.kt. HistoryPanel.kt places Activity charts after the summaries/placeholders, followed by HR Zones, Session details and existing deletion. SessionSummaryPanel.kt only exposes SummaryCard internally to reuse its style. Step 8.5d deletion styling/final integration is not implemented. Database, acquisition, SDK, algorithms, stored values and placeholder rules remain unchanged.
- 选择与数据：默认 HR，可切换 Cadence；按所选记录 ID 保存页面选择，ECG/RR 灰色禁用，提供 Not recorded 可访问性状态及可见说明。HR 来自 hrPoints，步频来自 motionPoints.cadence；保留原 elapsedMs、null、breakBefore，横轴为 0 至完整 durationMs，标为 Duration，刻度沿用 mm:ss。未增加历史数据、降采样、实时引用或 ECG/RR 存储。
  Selection/data: HR is the default and Cadence is selectable, with selection keyed to the record ID. ECG/RR are disabled and grey with accessible Not recorded state and visible explanation. Read HR from hrPoints and cadence from motionPoints.cadence, preserving elapsedMs/null/breakBefore. The Duration axis spans zero to full durationMs with mm:ss labels. No new history, downsampling, live-data references or ECG/RR storage.
- 绘图复用：复用 LivePlot 的网格、曲线、分段填充、单点圆点、零跨度保护和自适应时间标签；无有效历史明确显示空状态，不生成零曲线。均值虚线使用保存摘要的 meanHr/meanCadence，图下说明数值与虚线含义。ChartScale 新增可选参考值，在有历史点时将保存均值纳入显示范围，避免每秒末值的极值范围排除整场均值；不把均值加成样本，不从曲线重算统计。
  Plot reuse: Reuse LivePlot grids, lines, segmented fills, single-point markers, zero-span handling and adaptive time labels. Explicit empty states contain no fabricated zero curve. The dashed reference uses saved meanHr/meanCadence, with a numeric legend. ChartScale accepts an optional reference to include the saved mean in the display range when history exists, because per-second extrema may exclude the whole-session mean. The reference is not a sample and statistics are not recomputed from plots.
- Session 边界：LivePlot 仅增加可选 scale/height 参数，History 使用 180 dp × 字体缩放的绘图区；原 Session 调用、默认比例尺和高度表达式不变，未改其五分钟/五秒窗口或等高设置。选择项随宽度/字号变为四列、两列或单列；区间行根据实际文字测量宽度换行。源码保留断段逻辑；运行与大字号视觉效果仍待最终验收。
  Session boundary: LivePlot adds optional scale/height parameters. History uses a 180 dp × font-scale plot; Session calls, default scale and height expression remain unchanged, as do five-minute/five-second windows and equal-height settings. Choices adapt to four/two/one columns; zone rows wrap based on measured text widths. Segmentation is retained in source; runtime and large-font visuals await final validation.
- HR Zones：Z1—Z5 沿用 <110、110–124、125–139、140–154、≥155 bpm 与绿/蓝/黄/橙/红。同宽轨道的条长按保存区间毫秒 / record.durationMs，百分比保留两位，时间沿用截断秒格式；不按最大区间归一化。另列保存的 Unclassified 时间与同分母占比，不强行补足五区间 100%。零时长百分比为 --；无有效 HR 时五区间时间/占比为 --、无填充并有说明，已知 Unclassified 时长仍正常展示。
  HR Zones: Retain Z1–Z5 ranges <110, 110–124, 125–139, 140–154 and ≥155 bpm with green/blue/yellow/orange/red. Equal-width tracks use saved zone milliseconds / record.durationMs, two-decimal percentages and existing truncated-second times, without normalization to the largest zone. Show saved Unclassified time and its percentage separately. Do not force five zones to sum to 100%. Zero duration yields -- percentages; absent valid HR yields -- zone times/percentages and no fill with an explanation, while known Unclassified duration remains visible.
- 验证与用户覆盖：按“无需写测试，最后一起写”，本轮没有新增/修改测试，没有运行单元/Compose/SQLite、测试 APK 构建或模拟器视觉检查。最后统一补写/调整图表选择、禁用状态、均值范围、单点/平值/零时长、null/断段、整场时间轴、区间比例/缺失/未归类，以及 Session 共享绘图回归。8.5b/c 的深浅主题、实际字号 1.0/2.0 与 Samsung/H10 仍待验证。
  Validation/user override: Per the request to write tests at the end, no tests are added/modified; no unit/Compose/SQLite execution, test-APK build or emulator visual checks run. Final integration must add/adapt checks for selection/disabled choices, saved-mean bounds, single/constant/zero-duration data, nulls/breaks, full-session axes, zone proportions/missing/unclassified data and shared Session plot regressions. Light/dark, actual font 1.0/2.0 and Samsung/H10 validation for 8.5b/c remain pending.
- 实际结果：离线 :app:assembleDebug 与 :app:lintDebug 通过，lint 0 errors、22 warnings；git diff --check 通过。build/step85c-validation/build.txt、lint-final.txt、source-audit.txt 保存构建、lint 及源码范围证据。本轮新增两个/修改四个生产文件，测试文件变化为 0。两对中英文文档同步；未安装 APK、未操作模拟器或 Samsung、未 commit/push，未推进 8.5d。
  Actual results: Offline :app:assembleDebug and :app:lintDebug pass with zero errors and 22 warnings; git diff --check passes. Evidence is in build/step85c-validation/build.txt, lint-final.txt and source-audit.txt. Two production files are added and four modified; zero test files change. Both bilingual document pairs are synchronized. No APK installation, emulator/Samsung interaction, commit/push or progression to 8.5d.

### 5.38 History 标题清理与加载过渡修正（2026-10-03） / History heading and loading transition update

- 用户展示覆盖：列表标题改为居中的 History Activities，删除 Most recent first；列表与详情底部两段本地存储/未保存说明小字移除。此规则覆盖先前提示词中的旧标题/副标题和底部常驻说明要求；详情 Activity Summary、会话日期和必要错误/删除确认保留。
  Presentation override: Center the list title History Activities and remove Most recent first. Remove the two permanent local-storage/unsaved-session footnotes from list and detail. This supersedes earlier title/subtitle/footer requirements; retain Activity Summary, session date and necessary error/deletion confirmation text.
- 原因与修改：源码显示查询完成前已绘制 SessionStatusPanel、保存终态和页尾文字，查询完成后列表才插入，导致文本先出现再被推下；详情原本为整页 Column 一次布局全部内容。SensorActivity 的 History 回调现在仅在 Saving/Failed 时展示 SavePanel，保留 Retry save 与确认后 Discard；当前 Session 状态仍在 Session 的 Devices 中可查。列表/详情以带可访问性说明的小型进度指示替代 Loading 文本，不添加假数据、固定等待或数据库缓存。
  Cause/change: Source showed SessionStatusPanel, terminal save status and footer text rendering before the asynchronous query inserted cards. Detail also measured the entire page in one Column. History now shows SavePanel only for Saving/Failed, preserving Retry save and confirmed Discard; current-session status remains in Session's Devices. Compact accessible progress indicators replace Loading text, without fake data, fixed delays or a database cache.
- 详情过渡：按 selectedId 同步重建 loading/detail/error/delete/date 状态，避免等待 LaunchedEffect 才清除上一记录的状态。详情使用带稳定 item key 的 LazyColumn，摘要、图表、区间、折叠详情和删除分项；首屏不再要求一次布局所有下方组件。重新进入/换 ID 从顶部开始；实际 SQLite 查询仍在 IO、离开取消、重试/删除事务及保存刷新规则不变。主要为减少过渡文本闪现和首屏布局工作，不宣称数据库已零延迟或已实测消除全部掉帧。
  Detail transition: Reset loading/detail/error/delete/date state synchronously by selectedId, before LaunchedEffect runs. Use a LazyColumn with stable keys for summary, charts, zones, disclosure and deletion so the first screen need not lay out every lower component. Re-entry/new IDs start at the top. SQLite remains on IO with cancellation, retries, deletion transactions and save refresh preserved. This removes transient text sources and reduces initial layout work; it is not evidence of zero query latency or measured elimination of every dropped frame.
- 范围与验证：本轮只修改 HistoryList.kt、HistoryPanel.kt、SensorActivity.kt；之前未提交的 8.5c 改动保留。debug 构建、lint（0 errors、22 warnings）及 git diff --check 通过；证据在 build/history-transition-validation/。测试文件未新增/修改，按前述要求最终统一补写，本轮未运行自动测试或模拟器/真机流畅度检查。两对中英文文档同步，无 commit/push，8.5d 完整验收仍 pending。
  Scope/validation: This turn changes only HistoryList.kt, HistoryPanel.kt and SensorActivity.kt, preserving the existing uncommitted 8.5c work. Debug build, lint (zero errors, 22 warnings) and git diff --check pass; evidence is under build/history-transition-validation/. No test files are added/modified; tests remain deferred to final integration. No automated tests or emulator/hardware smoothness checks run. Both bilingual document pairs are synchronized; no commit/push, and full 8.5d acceptance remains pending.

### 5.38.1 移除 History 进度指示（2026-10-03） / Remove History loading indicators

- 按用户最新要求，移除列表首次/追加加载及详情查询时的进度指示，不替换成 Loading 文本。查询期间保留标题/背景及已加载内容；内部 loading 状态继续用于防止重复分页、控制重试和删除，查询完成前不显示空列表/找不到记录。此要求覆盖 5.38 的进度指示展示方案。
  Per the latest request, remove list initial/append and detail loading indicators without replacing them with Loading text. Keep the heading/background and existing content while querying. Internal loading still guards pagination, retry and deletion, and prevents premature empty/missing states. This supersedes the indicator presentation in 5.38.
- 本轮只修改 HistoryList.kt、HistoryPanel.kt 及两对文档；debug/lint 通过（0 errors、22 warnings），日志为 build/history-transition-validation/no-indicator-build.txt。没有新增/修改或运行测试，没有安装 APK/视觉/真机检查，无 commit/push。
  This turn changes only HistoryList.kt, HistoryPanel.kt and both document pairs. Debug/lint pass (zero errors, 22 warnings); see build/history-transition-validation/no-indicator-build.txt. No tests are added/modified/run, no APK installation/visual/hardware checks, and no commit/push.

### 5.39 Activity Summary 单屏参考图布局（2026-10-03） / Single-screen reference layout

- 用户最新要求：优化 Activity Summary，在一页展示 ui/History-detail.png 的主要内容。此规则覆盖 8.5 的默认滚动详情、5.38 惰性分项布局及页内 Session details 折叠卡；列表不变，无进度指示。默认手机字号采用按可用高度加权分配的卡片，保留参考图顺序与适度字重；字体缩放大于 1.3、宽度不足 350 dp 或内容高度不足 680 dp 时使用可滚动自然高度布局，避免关闭字体缩放或裁掉内容。
  Latest request: Fit the main History-detail.png content onto one Activity Summary screen. This supersedes the default scrolling detail, 5.38 lazy items and inline Session details disclosure. The list and no-loading-indicator rule remain. Normal phone text uses height-weighted cards in reference order with moderate type; font scales above 1.3, widths below 350 dp or available heights below 680 dp use natural-height scrolling to preserve accessibility and reachability.
- 布局：新增 HistoryDetailLayout.kt 管理标题/日期与单屏区域；Overview 三栏、HR 左均值/下方 Mean HR 与右 Range、Cadence 左 Max/右 Mean，数字和单位同行，三空卡和整宽 Session Strain 保留。卡片内边距/字号/边框压缩为手机尺度；图表胶囊、细网格、分段填充和保存均值保留，历史横轴最多五个标签并按实际宽度减少；Session 原刻度默认与曲线窗口不变。删除按钮改为整宽红色文字/描边，沿用原确认及事务逻辑。
  Layout: Add HistoryDetailLayout.kt for the heading/date and screen regions. Use three Overview columns, HR mean with Mean HR below and Range to its right, and Max/Mean cadence with inline units. Keep three placeholders and full-width Session Strain. Compact card padding/type/borders for phone dimensions. Preserve chart pills, grids, segmented fills and saved means; History requests up to five time labels, reduced when necessary. Session default ticks/windows remain unchanged. Use a full-width red outlined Delete session button with existing confirmation/transactions.
- 信息保留：点击标题右侧日期/信息入口打开可滚动 Session details 弹窗，保留设备、精确日期/时区、结束原因、完整性、最小步频/速度及缺流解释；图下注释移入弹窗和可访问性说明。主卡 HR/步频按整数显示，精确到两位的小数仍在弹窗；距离两位 km，区间百分比按整数显示、条长仍按原毫秒比例，无统计重算。Unclassified 保留在区间底部；ECG/RR 禁用、四项新指标为 --，生产不加入参考图的演示数值或横幅。
  Information: Tap the date/info entry to open scrollable Session details retaining device, precise timestamps/timezone, end reason, completeness, minimum cadence/speeds and missing-stream explanations. Move plot notes to the dialog/accessibility descriptions. Main HR/cadence values use whole numbers, with two-decimal values retained in the dialog; distance uses two-decimal km and zone percentages whole numbers while bars retain millisecond ratios. No statistics are recomputed. Keep Unclassified, disabled ECG/RR and four -- placeholders; no reference demo values/banner enter production.
- 构建与视觉：最终 debug 与 lint 通过，0 errors、19 warnings；git diff --check 通过。独立 API 37 emulator-5590（1080×2340、450 dpi，约 384×832 dp）安装本轮 APK，以临时 SQLite 演示数据检查；默认字号深浅主题的整个详情和删除按钮均在单屏，UI 层级 scrollable=False，HR/Cadence 切换及信息弹窗可用。初版 Mean HR 裁切已增加心率卡高度修复并复查。实际字号 2.0 初版拥挤裁切后改为纵向指标/空卡，已查看顶部、中部/底部截图，底部删除可达；不宣称大字号也是单屏。
  Build/visuals: Final debug/lint pass with zero errors and 19 warnings; git diff --check passes. Install this APK on dedicated API 37 emulator-5590 (1080×2340, 450 dpi, approximately 384×832 dp) with temporary SQLite visual data. At normal font scale, both themes show all main detail content and Delete session without scrolling (UI hierarchy scrollable=False); HR/Cadence selection and the details dialog work. Initial Mean HR clipping was corrected by enlarging its card. Actual font 2.0 initially clipped, then received vertical metrics/placeholders; inspect top/middle/bottom captures and confirm bottom deletion is reachable. Large text is not claimed to be single-screen.
- 证据与边界：build/history-single-screen/ 中 build-final.txt、lint-final.txt、visual-record.txt、source-audit.txt 和 UI-TEST-DATA-no-H10-*.png；图片为 UI TEST DATA · no H10，仅用于布局。最终浅色截图 final-default、深色 final-dark；大字号 final-font2-*。原模拟器数据库已按字节恢复、字号恢复 1.0、浅色恢复并关闭独立实例；未操作 Samsung。没有新增/修改测试文件，也没有运行单元/Compose/SQLite 回归或构建测试 APK。两对中英文文档同步；本轮含用户授权的删除按钮样式，但 8.5d 的完整功能验收仍 pending，8.4 大字号和 H10 验收不因本轮结果关闭，无 commit/push。
  Evidence/limits: build/history-single-screen/ contains build-final.txt, lint-final.txt, visual-record.txt, source-audit.txt and UI-TEST-DATA-no-H10-*.png. Captures use UI TEST DATA · no H10 for layout only; final-default/final-dark show the final normal-size screen and final-font2-* large text. Restore the original emulator database byte-for-byte, font 1.0 and light mode, then close the dedicated instance. No Samsung interaction. No test files are added/modified; no unit/Compose/SQLite regression suite or test-APK build runs. Both bilingual documentation pairs are synchronized. The requested deletion styling is included, but full 8.5d acceptance, the separate 8.4 large-font issue and H10 acceptance remain pending. No commit/push.

### 5.39.1 Activity Summary 最终细节优化（2026-10-03） / Final layout polish

- 用户最新要求覆盖 5.39 对应展示：删除 Overview 标题，保留三项指标并在卡片内上下等距居中；Heart Rate、Cadence、HR Zones 标题居中；Mean HR 放在数值上方，Cadence 左 Mean / 右 Max，增加底部内边距，单位与数值垂直居中。
  The latest request supersedes the corresponding presentation in 5.39: remove the Overview heading while retaining its three metrics with equal top/bottom spacing; center Heart Rate, Cadence and HR Zones headings; place Mean HR above its value; show Mean/Max cadence left/right with more bottom padding and vertically centered units.
- 删除 Activity charts 标题、图下 Duration 标题及 Unclassified 展示行；保留时间刻度、已存统计与百分比分母。增加图表及 HR Zones 可用高度，区间普通字重文字由 10 sp 增至 12 sp / 16 sp 行高。Delete session 改为 14 sp、较小按钮内边距和 36 dp 最小视觉高度，保留 Material 最小交互区域与删除确认流程；未定义指标仍为 --。
  Remove the Activity charts heading, Duration axis caption and Unclassified display row. Preserve time ticks, stored statistics and percentage denominators. Enlarge the chart and zone areas; increase regular-weight zone text from 10 sp to 12 sp with 16 sp line height. Reduce Delete session to 14 sp with smaller padding and a 36 dp minimum visual height, retaining Material's minimum interaction area and deletion confirmation. Undefined metrics remain --.
- 本轮仅修改 SessionSummaryPanel.kt、HistoryDetailLayout.kt、HistoryCharts.kt、HistoryZones.kt 及两对文档。debug/lint 通过（0 errors、19 warnings），diff 空白检查通过。独立 API 37 模拟器 1080×2340、450 dpi，临时 SQLite 演示记录下深浅主题默认字号均单屏完整显示（scrollable=false），Cadence 可切换；实际系统字号 2.0 的顶部/中部/底部已目视检查，使用滚动布局且底部删除可达。截图仅为 UI TEST DATA / no H10，证据见 build/history-final-polish/。
  This turn changes only SessionSummaryPanel.kt, HistoryDetailLayout.kt, HistoryCharts.kt, HistoryZones.kt and both documentation pairs. Debug/lint pass (zero errors, 19 warnings), as does the whitespace diff check. On the dedicated API 37 emulator at 1080×2340 and 450 dpi, temporary SQLite demo records fit one screen in both themes at normal font size (scrollable=false), and Cadence selection works. Actual font-scale 2.0 top/middle/bottom captures are visually checked; content scrolls and deletion remains reachable. Images are UI TEST DATA / no H10 only; evidence is under build/history-final-polish/.
- 原模拟器数据库按字节恢复，系统字号及深浅设置恢复原值并关闭独立实例，未操作 Samsung。没有新增/修改测试文件或运行测试套件；测试仍留最终统一补写。没有真机/H10 验证或 commit/push，8.5d 完整验收仍 pending。
  Restore the original emulator database byte-for-byte and original font/theme settings, then close the dedicated instance; no Samsung interaction. No test files are added/modified and no test suite runs; tests remain deferred to final integration. No hardware/H10 validation or commit/push; full 8.5d acceptance remains pending.

### 5.39.2 顶部与指标字号/间距（2026-10-03） / Header and metric typography

- 按用户最新要求：Activity Summary 从 21 sp 改为 18 sp，在返回与日期之间的可用区域居中；返回控件 36→28 dp，箭头 22→18 dp。右侧日期 12 sp、时间 14 sp Medium，日期/时间整体垂直居中，保留信息入口。时长/步数/距离标签增为 11 sp，数值 26 sp，首卡增加高度；数值和单位水平间距统一 8 dp。四个未定义指标仍为 --，统一标题/占位符组合居中，占位符 26 sp / 30 sp 行高、3 sp 字距。
  Per the latest request, reduce Activity Summary from 21 to 18 sp and center it in the space between Back and the date. Reduce the Back control from 36 to 28 dp and its arrow from 22 to 18 dp. Use 12 sp dates and 14 sp Medium time, vertically centered as a group, retaining the info entry. Increase duration/steps/distance labels to 11 sp and values to 26 sp with more card height. Use an 8 dp value/unit gap. Keep all four undefined metrics as --, with centered label/placeholder groups and consistent 26 sp type, 30 sp line height and 3 sp letter spacing.
- 只修改 HistoryDetailLayout.kt、SessionSummaryPanel.kt 及两对中英文文档。初次视觉检查发现距离标签换行挤压数值，已调整标签字距/字号并复查。最终 debug/lint 通过（0 errors、19 warnings）；独立模拟器 1080×2340 / 450 dpi 的默认字号深浅主题均完整单屏（scrollable=false）。证据为 build/history-type-polish/build-final.txt、visual-record.txt 和 UI-TEST-DATA-no-H10-*-final.png。截图使用临时演示数据，原数据库按字节恢复，原系统设置恢复并关闭实例。未操作 Samsung；本轮未新增/修改或运行测试，没有 H10 验证或 commit/push。最终版本未重新完成整页大字号验收。
  Only HistoryDetailLayout.kt, SessionSummaryPanel.kt and both bilingual document pairs change. The first visual check found a wrapped distance label squeezing its value; adjust label size/spacing and recheck. Final debug/lint pass (zero errors, 19 warnings). Normal-font light/dark layouts fit one screen on the dedicated 1080×2340 / 450 dpi emulator (scrollable=false). Evidence: build/history-type-polish/build-final.txt, visual-record.txt and UI-TEST-DATA-no-H10-*-final.png. Use temporary demo data, restore the original database byte-for-byte and original settings, then close the instance. No Samsung interaction, test additions/changes/runs, H10 validation or commit/push. Full-page large-font acceptance was not repeated for the final version.

### 5.39.3 Cadence 数值裁切修复（2026-10-03） / Cadence clipping fix

- Cadence 的加权高度不足以容纳标题、标签、数值及底部留白。紧凑布局改按 18/13/27 sp 三行实际缩放高度加 20 dp 留白预留卡片高度，不缩小数值或单位、不修改数据。字号大于 1.0 时改用已有自然高度滚动布局，避免继续挤压其他卡片；此规则覆盖之前 1.3 的单屏字号阈值，默认字号仍单屏。
  The weighted Cadence height could not accommodate its heading, label, value and bottom padding. In compact mode, reserve the scaled 18/13/27 sp line heights plus 20 dp spacing without reducing values/units or changing data. Font scales above 1.0 use the existing natural-height scrolling layout to avoid squeezing other cards; this supersedes the earlier 1.3 compact threshold. Normal text remains single-screen.
- 本轮仅修改 SessionSummaryPanel.kt、HistoryDetailLayout.kt 及两对文档；debug/lint 通过（0 errors、19 warnings）。独立模拟器 1080×2340 / 450 dpi，最终 APK 的字号 1.0 与 1.15 临时演示数据截图中，Mean 96、Max 144 和 steps/min 均完整显示；默认字号单屏，1.15 可滚动。证据在 build/history-cadence-fix/。原数据库按字节恢复、原设置恢复并关闭模拟器；无 Samsung/H10 操作。未新增/修改或运行测试，未 commit/push；不将模拟器演示数据视为真机验收。
  This turn changes only SessionSummaryPanel.kt, HistoryDetailLayout.kt and both document pairs. Debug/lint pass (zero errors, 19 warnings). On the dedicated 1080×2340 / 450 dpi emulator, final APK demo captures at font scales 1.0 and 1.15 show Mean 96, Max 144 and steps/min completely. Default text fits one screen; 1.15 scrolls. Evidence is under build/history-cadence-fix/. Restore the original database byte-for-byte and original settings, then close the emulator; no Samsung/H10 interaction. No test additions/changes/runs or commit/push. Emulator demo data is not hardware acceptance.

### 5.39.4 Mean HR 与占位数值对齐（2026-10-03） / Mean HR and placeholder alignment

- 按用户要求，Mean HR 数值从 30 sp 降为与 Range 相同的 23 sp，统一行高并删除不再需要的 largeValue 分支。Intensity、Cardio Load、HR Recovery、Session Strain 保留标题，下方独立数值区域水平/垂直居中显示 --。四卡按标题 16 sp、数值 30 sp 行高及 12 dp 留白预留高度，随系统字号调整，防止占位符贴底或在滚动布局中消失；尚未定义计算规则。
  Per the request, reduce Mean HR from 30 to 23 sp to match Range, use matching line heights and remove the unused largeValue branch. Keep the four placeholder titles and center -- horizontally/vertically within dedicated value areas. Reserve each card's height from its 16 sp title, 30 sp value line and 12 dp spacing, scaled with system text, so values neither hug the bottom nor disappear in scrolling layouts. Calculation rules remain undefined.
- 本轮仅修改 SessionSummaryPanel.kt 与两对文档；debug/lint 通过（0 errors、19 warnings）。独立模拟器最终演示截图核对字号 1.0（单屏）和 1.15（滚动），Mean HR/Range 字号一致、四个占位符均显示并居中。证据 build/history-placeholder-align/，原数据库按字节恢复、原设置恢复并关闭模拟器，无 Samsung/H10 操作。没有新增/修改或运行测试，没有 commit/push。
  Only SessionSummaryPanel.kt and both document pairs change. Debug/lint pass (zero errors, 19 warnings). Final dedicated-emulator demo captures at font 1.0 (single-screen) and 1.15 (scrolling) show matching Mean HR/Range sizes and all four centered placeholders. Evidence: build/history-placeholder-align/. Restore the original database byte-for-byte and original settings, then close the emulator; no Samsung/H10 interaction. No test additions/changes/runs or commit/push.

## 2026-10-04 步骤 8.5d 实施结果 / Step 8.5d implementation results

### 5.40 步骤 8.5d：History 最终整合与验收（2026-10-04） / Final History integration and acceptance

- 范围：本轮完成 8.5d，并统一补写此前按用户要求延期的 8.5b/c 测试。保留 5.38—5.39.4 的最终展示：History Activities 居中、10 条自动分页/右侧滚动条、无加载进度；详情保留三项指标、Heart Rate、Cadence、四空卡、HR/Cadence 图、HR Zones 与红色描边删除。未恢复 Overview、Activity charts、横轴 Duration、Unclassified 或常驻底部说明；ECG/RR 仍禁用，四项未定义指标仍为 --。
  Scope: Complete 8.5d and add the previously deferred 8.5b/c tests. Retain the final presentation in 5.38–5.39.4: centered History Activities, automatic batches of ten, scrollbar and no progress indicator; retain summary metrics, Heart Rate, Cadence, four placeholders, HR/Cadence plots, HR Zones and outlined deletion. Removed headings/footnotes stay removed. ECG/RR remain disabled and undefined metrics remain --.
- 实际修复：HistoryList 在组合时捕获本次记录列表，避免异步查询重组期间 LazyColumn 的索引/数量不一致；删除失败或存在保存阻塞通知时，详情改用自然高度滚动，防止通知挤压指标。默认字号数值仅在宽度不足时自适应缩小，先预留单位空间，修复 123.45 km 的单位拆行；大字号占位卡改为最小高度和自然文字高度，修复字号 2.0 的 -- 裁切，默认单屏与居中样式保留。Session details 增加可访问名称，并在弹窗内恢复本地保存/清除数据的隐私说明。
  Fixes: Capture the record list for each composition so asynchronous query updates cannot mismatch lazy-item indices/counts. Use natural-height scrolling for deletion errors or blocking save notices. Reserve unit space and shrink normal-font values only when necessary, fixing the split unit in 123.45 km. Large-font placeholders use minimum/natural height to avoid clipping --, preserving normal-font single-screen alignment. Add an accessible Session details name and show local-storage/clear-data information inside that dialog.
- 删除/恢复验证：沿用现有事务及确认弹窗，真实 SQLite 检查取消不写入、仅删所选 ID、阻止重复删除/冲突返回、事务失败保留详情且可重试、成功刷新列表；核对 45 条同时间记录的稳定顺序、多批加载、追加/查询失败与取消旧查询、缺失/不完整记录、当前时区日期、重建/页面往返。新增详情内保存失败→Cancel discard→Retry save→返回列表刷新的真实数据库路径，保留保存阻塞 Start。
  Deletion/recovery: Retain existing transactions and confirmation. Real SQLite checks cover cancellation without writes, deleting only the selected ID, duplicate/back blocking, rollback/retry and list refresh. Verify 45 tied-time records, multiple pages, query/append failures and cancellation, missing/incomplete records, current timezone, restoration/navigation. Add the real-database failed-save path inside detail through discard cancellation, save retry and refreshed list; retain Start blocking.
- 摘要/图表验证：新增 HistoryPresentationTest，检查已存值、零与缺失、四空卡、禁用入口、整场时轴、单点/平值/零时长、区间使用 Running 分母且不强补 100%、默认字号单屏、单位不换行、长数值/错误和文字边界。补充 ChartScale/时间刻度单元测试；更新旧 Session ID、Loading、已删标题和折叠区断言。共享 LiveChartCard 15 项、Session 保存/导航 3 项、默认三图等高/单屏 1 项、History 共享区间 1 项通过，未改变采集、SDK、数据库结构或统计算法。
  Summary/charts: Add stored-value, zero/missing, placeholder, disabled-choice, full-session-axis, isolated/constant/zero-duration, Running-denominator, single-screen, unit and text-boundary checks. Add scale/time-tick unit tests and replace obsolete UI selectors. Fifteen shared chart tests, three save/navigation tests, one normal Session chart-layout test and one shared History-zone test pass. Acquisition, SDK, schema and calculations are unchanged.
- 最终构建与自动结果：231 项单元测试通过（0 failures/errors/skipped）；debug/测试 APK 构建及 lint 通过（0 errors、19 warnings）。最终 storage-final-all.txt 单轮 33/33 通过；shared-regression.txt 单轮 21/21 通过（其中 1 项与前者重复），共 53 项不同仪器检查分轮通过。字号 2.0 六项重复验收 6/6 通过，随后两项截图复查 2/2 通过；不能描述为一次 53 项全量运行。默认字号详情四项专项复查也通过。
  Final builds/results: All 231 unit tests pass with no failures/errors/skips; debug/test APK builds and lint pass with zero errors and 19 warnings. The final storage run passes 33/33; the shared run passes 21/21 with one overlapping test: 53 distinct instrumentation checks pass across separate runs. Six font-2.0 repeats and two final capture repeats pass. The four normal-font presentation checks also pass; there was no single 53-test run.
- 失败记录：首次 32 项有 8 项失败；后续 4/13/32/33 项诊断轮分别有 2/2/1/1 项失败，包含旧选择器、对齐文字坐标误判、预加载节点排序、真实列表索引错误及删除错误布局裁切。目标尺寸自适应字号轮 33 项有 2 项行高失败，修正后详情 4/4 通过；字号 2.0 初轮 6 项有 2 项占位裁切失败，单项诊断复现后修复。全部轮次数量保存在 instrumentation-runs.json，最终通过日志与早期失败日志分开保留。
  Failure history: Initial 32-test run had eight failures; subsequent 4/13/32/33-test diagnostic runs had 2/2/1/1 failures from obsolete selectors, aligned-text coordinates, prefetched-node ordering, a real list-index error and error-state clipping. At target size, two of 33 tests found auto-size line-height problems; the repaired presentation run passed 4/4. Two of six font-2.0 checks found placeholder clipping, reproduced by one diagnostic test and repaired. Preserve every run separately in instrumentation-runs.json and the original logs.
- 模拟器/视觉：新建独立 Step85d / emulator-5592，未复用旧 AVD 数据；初轮皮肤实际为 1080×2424，最终验收已明确覆盖为 1080×2340、450 dpi，API 37.1，实际系统字号 1.0/2.0。深浅主题检查列表首批/后续/末尾、详情顶部/指标/图表/区间/底部、长时长/数值/不完整及删除失败。默认字号正常详情全部单屏，大字号/错误/保存通知使用滚动。测试场景使用独立命名 SQLite 库和 UI TEST DATA · no H10 标记；另在未注入历史的正式 App 中完成四种主题/字号组合的空列表及 Session/History 往返。
  Emulator/visuals: Create fresh Step85d / emulator-5592 without reusing existing AVD data. Its skin initially used 1080×2424; final acceptance explicitly uses 1080×2340, 450 dpi, API 37.1 and actual font scales 1.0/2.0. Check both themes, list pages/end, detail regions, long/incomplete values and deletion errors. Normal details fit one screen; large text/errors/save notices scroll. Fixtures use separate named databases and UI TEST DATA · no H10 labels. Four theme/font combinations also pass in the actual app's unseeded empty History and tab navigation.
- 证据与边界：build/step85d-validation/ 保存 build-final.txt、storage-final-all.txt、shared-regression.txt、font2-final.txt、font2-capture-final.txt、actual.txt、instrumentation-runs.json、passed-methods.txt 及 final-font1/2-detail/list 截图；overflow.png 是失败诊断，不是最终通过图。两对中英文文档同步，无 commit/push。8.5 软件及本轮模拟器验收完成；Samsung/H10 全链路、真实性能、计步/距离校准和独立的 8.4 Session 字号 2.0 问题仍 pending，四项指标规则和 ECG/RR 历史仍未定义，不自动推进。
  Evidence/limits: Keep builds, separate test logs/counts, passed methods, actual-app logs and final-font1/2 detail/list captures under build/step85d-validation/. overflow.png is diagnostic, not final acceptance. Synchronize both bilingual document pairs without commit/push. Step 8.5 software/emulator acceptance is complete; Samsung/H10, real performance, motion calibration and the separate Session font-2.0 issue remain pending. Undefined metrics and historical ECG/RR are not implemented.

- 清理完成：本轮新建 emulator-5592 已恢复字号 1.0/浅色并关闭；未操作既有 AVD 或 Samsung。
  Cleanup complete: Restore font 1.0/light mode and close the newly created emulator-5592; existing AVDs and Samsung remain untouched.

### 5.40.1 Activity Summary 首卡与日期入口精简（2026-10-04） / Summary card and date simplification

- 按用户最新要求，Activity Summary 移除 Estimated Distance；Duration 和 Total Steps 各占一半宽度，在默认及大字号下保持同一行。沿用既有自适应数值字号和居中对齐。
  Per the latest request, remove Estimated Distance from Activity Summary. Duration and Total Steps share the full row equally at normal and enlarged system fonts, retaining adaptive value sizing and centered alignment.
- 日期/时间改为纯展示，删除点击行为、信息图标、Session details 弹窗、相关状态及已无调用的 SessionDetails 组件。此规则覆盖 5.39—5.40 的日期信息入口和三项主指标要求；历史记录本身、距离计算/保存、Session 页和删除确认中的精确日期不变。
  Make the date/time display-only. Remove its click action, information icon, Session details dialog, state and unused SessionDetails component. This supersedes the date-info entry and three-metric requirements in 5.39–5.40. Stored records, distance calculation/persistence, the Session screen and precise deletion-confirmation dates remain unchanged.
- 修改两个生产文件 HistoryDetailLayout.kt、SessionSummaryPanel.kt，并同步调整两个现有 History 详情测试文件中已失效的距离/弹窗断言。debug/测试 APK 构建及 lint 通过（0 errors、19 warnings）；本轮证据保存于 build/history-summary-simplify/，不复用 5.40 的全量测试数作为本次结果。无 commit/push。
  Change HistoryDetailLayout.kt and SessionSummaryPanel.kt, updating obsolete distance/dialog assertions in two existing History test files. Debug/test APK builds and lint pass (zero errors, 19 warnings). Current evidence is under build/history-summary-simplify/; the full counts from 5.40 are not claimed as new results. No commit/push.
- 本轮验收：13 项相关 History 详情/展示检查全部通过，字号 2.0 另重复 4 项全部通过；核对深浅主题截图，Duration/Total Steps 保持同排各半，默认字号详情单屏，日期时间无点击入口。未运行本轮全量单元测试或 H10 真机验收。专用 emulator-5592 恢复字号 1.0/浅色后关闭；未操作 Samsung。
  Current validation: All 13 relevant History detail/presentation checks pass, plus four repeats at font scale 2.0. Inspect both themes: Duration/Total Steps share one row equally, normal-font detail fits one screen and date/time has no click entry. No full unit suite or H10 validation runs this turn. Restore font 1.0/light mode and close dedicated emulator-5592; no Samsung interaction.

### 5.41 Session 单位对齐与双列汇总（2026-10-04） / Session unit alignment and two-column summary

- 按用户要求，Session 当前心率/步频主数值和单位的间距从 8 dp 增至 12 dp，改为同一 Row 内垂直居中，单位保持单行；主数值继续使用 38 sp，bpm/steps/min 分别保留 18/14 sp。
  Per the request, increase the gap between current HR/cadence values and units from 8 to 12 dp. Center them vertically in one row and keep units on one line. Retain 38 sp main values and 18/14 sp bpm/steps/min units.
- Session 移除 Estimated Distance 展示，Duration 和 Total Steps 各占半宽并列一行，卡片间距从 6 增至 14 dp，内部横向/纵向留白改为 8/4 dp，标签数值间距为 4 dp。标签增至 13 sp、数值上限由 24 增至 30 sp；长数值使用 22–30 sp 单行自适应显示。清理仅服务距离展示的格式化函数和旧三列/上下排列路径。
  Remove Estimated Distance from Session. Duration and Total Steps share one row equally, with a 14 dp card gap (previously 6), 8/4 dp horizontal/vertical padding and 4 dp label/value spacing. Increase labels to 13 sp and values from 24 to up to 30 sp; long values adapt within 22–30 sp on one line. Remove the unused distance formatter and old three-column/stacked summary branches.
- 仅生产文件 SessionMetrics.kt 改变；同步两个现有 Session 仪器测试的距离/三列断言。距离计算、保存、算法、实时曲线与 History 不变。此要求覆盖此前 Session 三项运动汇总展示；独立的 Session 整页字号 2.0 问题继续保留，不能用本轮局部布局改动关闭。
  Only SessionMetrics.kt changes in production; update obsolete distance/three-column assertions in two existing Session instrumentation files. Distance calculation/storage, algorithms, live plots and History remain unchanged. This supersedes the former three-metric Session summary. The separate full-page Session font-2.0 issue remains open.
- debug/测试 APK 构建和 lint 通过（0 errors、19 warnings），本轮证据在 build/session-metric-polish/。两对中英文文档同步，不 commit/push；真机验收仍 pending。
  Debug/test APK builds and lint pass (zero errors, 19 warnings). Current evidence is under build/session-metric-polish/. Synchronize both bilingual documentation pairs without commit/push; hardware acceptance remains pending.
- 验证与修正：首轮 3 项检查中 2 项发现放大汇总后底部 Z5 被挤压；移除汇总重复外层卡片并将纵向内边距调整为 4 dp 后，最终 3/3 通过，包含默认三类图等高/整页、暂停/空/失败状态及 123456 步/长时长。已目视核对默认字号深浅主题，数值/单位居中，汇总两卡放大并列、Z5 完整。最终 debug/测试 APK/lint 通过；未运行全量单元测试或完成整页字号 2.0/H10 验收。证据为 tests.txt、tests-final.txt、build-final.txt 和 final-captures/。专用模拟器恢复字号 1.0/浅色并关闭，未操作 Samsung。
  Validation/fix: Two of the initial three checks found Z5 clipped after enlarging the summary. Remove its duplicate outer card and use 4 dp vertical padding; all three final checks pass, covering equal-height charts/full page, paused/empty/failed states and 123456 steps/long duration. Inspect normal-font light/dark captures: centered units, enlarged side-by-side summary cards and complete Z5. Final builds/lint pass; no full unit suite, complete font-2.0 or H10 acceptance runs. Evidence: tests.txt, tests-final.txt, build-final.txt and final-captures/. Restore font 1.0/light mode and close the dedicated emulator; no Samsung interaction.

### 5.42 Session 图标与强度间距（2026-10-04） / Session icons and intensity spacing

- 按用户要求，将左侧蓝牙图标替换为 ui/icon/polar-logo-icon.png，右侧设置图标替换为 bluetooth-icon.png，均为 32 dp 并保留原色与 Devices 点击行为。六张原始 PNG 原样复制至 drawable-nodpi，以合法下划线资源名引用；删除无引用的旧 ic_bluetooth/ic_devices 矢量资源。
  Replace the left Bluetooth symbol with ui/icon/polar-logo-icon.png and the right settings symbol with bluetooth-icon.png. Both use 32 dp, original colors and existing Devices actions. Copy all six original PNGs unchanged into drawable-nodpi with underscore resource names; remove the unused ic_bluetooth/ic_devices vectors.
- HR/ACC/ECG 使用 20 dp 的 hr-status 图标：RECEIVING 为 green；STARTING/STOPPING 为 yellow；FAILED 为 red；IDLE/STOPPED 为 gray。保留标签、独立订阅状态映射和无障碍状态说明。心率强度标签外侧上边距增加 2 dp。
  HR/ACC/ECG use 20 dp hr-status icons: green for RECEIVING, yellow for STARTING/STOPPING, red for FAILED and gray for IDLE/STOPPED. Retain labels, independent subscription mapping and accessibility state descriptions. Add 2 dp above the heart-rate intensity badge.
- debug/测试 APK 构建与 lint 通过（0 errors、21 warnings，其中两项 IconXmlAndPng 指向既有 launcher 资源）。未新增/修改测试，本轮复用 3 项现有检查全部通过，覆盖默认字号三类图单屏/深浅主题、暂停/空/失败状态与左右 Devices 入口。已目视核对浅色 HR/深色 Motion，Z5 与底部按钮完整显示。证据：build/session-icon-polish/。未运行全量测试，未操作 Samsung/H10；独立 Session 字号 2.0 与真机验收仍 pending。同步两对中英文文档，无 commit/push。
  Debug/test APK builds and lint pass (zero errors, 21 warnings, including two IconXmlAndPng warnings on existing launcher assets). No tests are added or edited this turn; three existing checks pass for normal-font single-screen charts/light and dark themes, paused/empty/failed states and both Devices entries. Visually inspect light HR/dark Motion: Z5 and bottom controls remain complete. Evidence: build/session-icon-polish/. No full suite or Samsung/H10 interaction; separate Session font-2.0 and hardware acceptance remain pending. Synchronize both bilingual documentation pairs without commit/push.

### 5.42.1 恢复设置图标（2026-10-04） / Restore settings icon

- 按用户最新要求，右侧 Devices 按钮恢复原 ic_devices 设置齿轮、主题蓝色及浅色圆形背景，点击行为不变。移除 App 内不再使用的 bluetooth_icon.png；ui/icon 原始素材保留。Polar Logo、HR/ACC/ECG 四种状态图标及强度上边距不变。此项覆盖 5.42 的右侧 Bluetooth 图标要求。
  Restore the right Devices button to its original ic_devices settings gear, theme-blue tint and pale circular background, preserving its action. Remove the unused App bluetooth_icon.png while retaining the original ui/icon asset. Keep the Polar logo, four HR/ACC/ECG status icons and intensity top spacing. This supersedes the right Bluetooth icon requirement in 5.42.
- debug 构建及 lint 通过；证据：build/session-icon-polish/restore-settings-build.txt。本轮未新增/运行测试或模拟器/真机检查；两对中英文文档同步，无 commit/push。
  Debug build and lint pass; evidence: build/session-icon-polish/restore-settings-build.txt. No tests or emulator/hardware checks run this turn. Synchronize both bilingual documentation pairs; no commit/push.

## 8.6 Devices 弹窗重设计 / Devices dialog redesign（2026-10-04）

### 中文提示词

参照 ui/Devices-dialog.png 实施设备连接弹窗第一版。删除详细信息区，其余按已确认建议处理：
1. 圆角描边弹窗、蓝牙圆形图标、Devices 标题、右上角关闭和连接状态胶囊；Saved devices 与 Nearby devices 使用设备图标、名称和独立 Connect 按钮，蓝色主按钮、适度字重。沿用深浅主题，Session 设置入口不变。
2. 未连接时不显示空的 Current device；连接中/已连接/断开中显示实际目标或当前设备与真实状态，已连接保留有效电量、Disconnect 和既有 Retry disconnect。连接成功不自动关闭。
3. 不显示折叠详情、最近连接时间、RSSI、采样参数、正常就绪列表或常驻 Session 详情；名称没有 ID 时补一行 ID。权限、蓝牙、连接/流失败、配置/开始受限与保存中/失败仍提供必要提示和已有恢复操作，不新增流 Retry 或 Session Retry save。
4. 只通过 Connect 按钮发起连接。Saved 沿用最近连接倒序和直接连接；Nearby 排除已保存设备，两类列表也排除当前设备，按 deviceId 判断，不修改底层数据或把 Saved 当作在线。
5. 合并为 Scan / Stop scan 单按钮。标题/状态与底部 Scan、Close 固定，中间内容滚动；长名称允许换行，大字号/窄宽度将设备按钮移至下一行，不能裁切内容或缩小系统字号。
6. 沿用扫描 30 秒、新扫描清空、停止保留、连接前停止扫描、连接 10 秒超时和单设备规则。连接/断开中防重复；打开不自动扫描、成功不自动采集、不增加重连/切换。×、Close、系统返回和外部点击采用同一关闭路径，关闭只停止扫描，不结束会话或断开设备。
7. 最小修改现有 Compose UI；SDK、数据状态、计步/统计、持久化与 History 不变。验证深浅、空/重复/多设备、所有连接及异常操作、关闭、长名称、真实字号 2.0、保存恢复；合成数据仅用于明确标注的测试。运行相关检查、debug/测试 APK 构建和 lint，区分实际通过与真机 pending；同步两份 AGENTS.md 和 prompt.md 实施记录，不 commit/push。

### English prompt

Implement the first Devices dialog redesign using ui/Devices-dialog.png. Remove detailed information and apply the agreed recommendations:
1. Use a rounded outlined dialog, circular Bluetooth icon, Devices heading, top-right close action and connection-status pill. Present Saved devices and Nearby devices as icon/name cards with explicit Connect buttons, blue primary actions and moderate font weights. Support both themes and retain the Session settings entry.
2. Hide the empty Current device section when disconnected. Show the actual target/current device while connecting, connected or disconnecting; retain valid battery, Disconnect and existing Retry disconnect. Keep the dialog open after connection succeeds.
3. Omit disclosures, last-connected time, RSSI, sampling parameters, healthy-readiness lists and persistent Session details. Add an ID line only if absent from the name. Preserve necessary permission, Bluetooth, connection/stream failure, blocked configuration/start and saving/failed-save notices with existing recovery actions. Add no stream Retry or Session Retry save.
4. Connect only through Connect buttons. Preserve saved-device recency order and direct connection. Exclude saved devices from Nearby and the current device from both lists, using deviceId. Do not change stored data or imply that saved devices are online.
5. Combine scanning into one Scan / Stop scan button. Keep heading/status and Scan/Close footer fixed, with a scrolling middle region. Wrap long names and stack device actions at narrow widths/large fonts; do not clip content or reduce system font scale.
6. Preserve 30-second scans, fresh-scan clearing, retained stopped results, stop-before-connect, 10-second connection timeout and single-device behavior. Prevent duplicate busy operations. Opening never starts a scan; successful connection never starts acquisition. Add no reconnection or device switching. Top close, Close, Back and outside clicks use the same dismissal path, stopping scans without ending Session or disconnecting.
7. Make minimal changes to existing Compose UI; preserve SDK, data state, processing/statistics, persistence and History. Validate themes, empty/duplicate/many devices, connection/error actions, dismissal, long names, actual font scale 2.0 and save recovery. Use explicitly labeled synthetic data only in tests. Run relevant checks, debug/test APK builds and lint; distinguish passing checks from pending hardware verification. Synchronize both AGENTS.md and prompt.md copies with implementation results; no commit/push.

实施规则与最终验证记录 / Implementation rules and final verification: AGENTS.md §5.43.

### 5.43.1 8.6 第一版实施与验证 / First implementation and validation

- 已修改 DevicesDialog.kt 和 SensorActivity.kt；新增蓝牙/搜索/关闭矢量资源。弹窗使用 94% 可用宽度、最大 560 dp 与 90% 可用高度上限；顶部标题/状态和底部 Scan/Close 固定，中间滚动。名称/ID、独立按钮、真实状态卡、跨列表去重、异常提示和关闭行为按 5.43 实施。未增加折叠详情；删除原参数/时间/RSSI/常驻 Session 说明，仅保留必要问题及保存恢复内容。
  Implemented DevicesDialog.kt/SensorActivity.kt changes and Bluetooth/search/close vector resources. The dialog uses 94% of available width with a 560 dp cap and 90% height cap, fixed heading/status and Scan/Close footer, and a scrolling body. Names/IDs, explicit buttons, real-state current cards, cross-list deduplication, actionable notices and dismissal follow 5.43. No disclosure is introduced; remove former parameters/timestamps/RSSI/persistent Session notes, retaining necessary issues and save recovery.
- 新增 DevicesDialogTest 的 5 项测试；更新 SessionHeaderTest、SessionChromeTest 和 SessionMetricsTest 中与新入口、信息移除相关的断言，并增加顶部关闭扫描检查。231 项单元测试全部通过（0 failures/errors）；默认字号分轮 28 + 5 = 33 项不同 UI 检查通过，另实际系统字号 2.0 重复 5 项 DevicesDialogTest 全部通过。覆盖独立 Connect/不自动关闭、Saved/Nearby 去重、Scan 切换与结果、忙碌禁用、断开失败重试、权限/蓝牙/配置问题、顶部关闭/Close/系统返回、保存恢复、长名称/12 个设备、深浅与滚动。外部点击共用 onDismissRequest，经源码检查，本轮没有单独注入外部点击事件。
  Add five DevicesDialogTest cases; update SessionHeaderTest/SessionChromeTest/SessionMetricsTest assertions for the new actions and removed details, plus a top-close scan check. All 231 unit tests pass (zero failures/errors). Default-font runs pass 28 + 5 = 33 distinct UI checks; five DevicesDialogTest cases also pass at actual system font 2.0. Coverage includes explicit Connect/no auto-close, Saved/Nearby deduplication, scan toggling/results, busy guards, disconnect retry, permission/Bluetooth/configuration issues, top close/Close/Back, save recovery, long names/12 devices, themes and scrolling. Outside clicks share onDismissRequest by source inspection; no separate outside-click event was injected this turn.
- debug/测试 APK 构建与最终 lint 通过，0 errors、16 warnings。已目视核对默认字号深浅参考场景、字号 2.0 深色列表及浅色长名滚动，按钮固定可见。全部截图为明确标注的测试状态，不是真实 H10。证据：build/step86-devices/ 下 build.txt、build-final.txt、tests.txt、tests-final.txt、font2.0.txt、font1.0/ 和 font2.0/。未运行全部仪器测试；独立 Session 整页字号 2.0 问题、真实连接/扫描/权限系统交互及 Samsung/H10 验收仍 pending。
  Debug/test APK builds and final lint pass with zero errors and 16 warnings. Visually check default-font light/dark reference fixtures, font-2.0 dark lists and light long-name scrolling; footer buttons remain visible. Captures are labeled synthetic states, not real H10 evidence. Evidence under build/step86-devices/: build.txt, build-final.txt, tests.txt, tests-final.txt, font2.0.txt, font1.0/ and font2.0/. The complete instrumentation suite was not run. Separate full-page Session font-2.0 clipping and real connection/scanning/system-permission/Samsung-H10 acceptance remain pending.
- 两对中英文文档同步。专用模拟器验证后恢复默认字号/浅色并关闭，未操作 Samsung；不 commit/push。
  Synchronize both bilingual documentation pairs. Restore the dedicated emulator to default font/light mode and close it after validation; no Samsung interaction or commit/push.

### 5.43.2 删除连接后的数据流状态文字（2026-10-04） / Remove connected stream status text

- 按用户最新要求，Devices 弹窗不再逐项显示 HR/ACC/ECG 的 Waiting for readiness、Checking、Unsupported、Configuration unavailable 等状态文字。仅保留实际 error 文本与原 Recheck 操作及其启用条件；连接状态胶囊和 Session 顶部状态图标不变。此项覆盖 8.6 第一版对应的就绪状态展示。
  Per the latest request, remove per-stream HR/ACC/ECG Waiting for readiness, Checking, Unsupported and Configuration unavailable text from Devices. Retain actual errors and the existing Recheck action/enablement. Keep the connection pill and Session header status icons. This supersedes the corresponding readiness-status display in the first 8.6 version.
- 本轮仅修改 DevicesDialog.kt 的文字渲染，并同步两对文档；debug/lint 通过，证据为 build/step86-devices/remove-stream-status-build.txt。未新增/运行测试或模拟器/真机验证，无 commit/push。
  This turn changes only text rendering in DevicesDialog.kt and synchronizes both documentation pairs. Debug/lint pass; evidence: build/step86-devices/remove-stream-status-build.txt. No new tests, test execution or emulator/hardware validation; no commit/push.

### 5.43.3 紧凑设备卡、Clear History 与固定弹窗（2026-10-04） / Compact device cards, Clear History and a fixed dialog

- 用户要求：缩小设备名称及 Connect/Disconnect 按钮，使名称单行显示；Saved devices 标题右侧同一行加入 Clear History 清理已保存设备记录；连接错误单行，Recheck 缩小并放到同一行，连接后弹窗高度保持固定。
  Request: Reduce device-name type and Connect/Disconnect controls, keep names on one line, place Clear History to the right of Saved devices, combine a single-line connection error with a smaller Recheck action, and keep dialog height stable after connection.
- 设备名称改为 13 sp/18 sp、Medium、单行，超长名称末尾省略，完整名称仍保留在文本语义中；普通设备图标缩至 28 dp。操作文字为 11 sp/15 sp，减小按钮内边距，沿用 Material 触控尺寸；大字号/窄宽度仍将操作放到下一行。此规则覆盖此前设备名换行的要求。
  Device names use 13 sp/18 sp Medium on one line, with end ellipsis for long names and complete text retained in semantics. Device icons shrink to 28 dp. Action labels use 11 sp/15 sp and smaller padding while retaining Material touch-target sizing; narrow/large-font layouts still stack actions below identities. This supersedes the former multiline-name rule.
- Clear History 直接清除本 App 已保存设备记录，不清除 Activity History，不断开当前连接。删除 SharedPreferences 的 devices 数据项并检查提交结果，沿用 IO/Mutex 串行写入；成功后更新列表，失败保留错误提示。空列表且无读取错误时禁用；清除后原已保存扫描结果自动归入 Nearby，当前设备仍只出现一次。未来真实成功重连可再次保存设备。没有执行用户设备上的实际清除。
  Clear History directly removes this App's saved-device records, preserving Activity History and the active connection. Remove only the SharedPreferences devices entry, check commit success and serialize on the existing IO/mutex path. Update the list after success and report failures. Disable for an empty error-free list. Previously saved scan results become Nearby entries, with the current device still unique; a later genuine successful connection may save a device again. No saved devices on the user's hardware were cleared.
- 连接/配置/流错误去重并合并为一行 11 sp 文本，超长省略，Recheck 同行且保持原启用限制；不恢复逐流状态或详情区。弹窗固定为可用窗口高度的 90%，内容区占剩余高度并滚动，连接、断开、错误或列表变化不会改变外框高度。保留权限与保存恢复操作。
  Deduplicate and combine connection/configuration/stream errors into one 11 sp line with ellipsis and an adjacent Recheck action retaining its original guards. Do not restore stream statuses/details. Fix the dialog to 90% of the available window height and scroll the remaining body area, so connection, errors or list changes do not resize its frame. Preserve permission and save recovery actions.
- Saved devices 标题使用 11—18 sp 自动适配，在系统字号 2.0 下仍与 Clear History 同行完整显示。标准设备名在 1.0/2.0 字号的受控检查中均完整单行，超长名称按规则省略。
  The Saved devices heading fits within 11–18 sp so it remains complete beside Clear History at system font scale 2.0. Standard device names remain complete on one line at both tested scales; unusually long names use the specified ellipsis.
- 本轮 231 项单元测试通过；分轮 35 项不同仪器检查全部通过，包括 2 项隔离 SharedPreferences/SQLite 持久化检查、7 项 Devices 检查及 Header/Chrome/错误布局回归。初次名称检查误把段落空白宽度判为文字溢出，改为检查实际行边界、垂直高度和省略号后通过；最终 Devices 7 项在 1.0 与 2.0 字号分别复跑通过。debug/测试 APK 构建和 lint 通过（0 errors、16 warnings）。证据在 build/step86-compact；受控浅/深主题、错误同行、清除后连接保留及大字号滚动截图已检查。两对中英文文档同步，无 commit/push；Samsung/H10 真实蓝牙与清除后的重连仍待真机验证。
  All 231 unit tests pass. Across the recorded runs, 35 distinct instrumentation checks pass, including two isolated SharedPreferences/SQLite persistence checks, seven Devices checks and Header/Chrome/error-layout regressions. An initial name assertion counted paragraph whitespace as overflow; actual line bounds, vertical height and ellipsis checks pass. All seven final Devices checks pass again at font scales 1.0 and 2.0. Debug/test APK builds and lint pass (zero errors, 16 warnings). Evidence is in build/step86-compact. Controlled light/dark, inline-error, retained-connection-after-clear and large-font scrolling captures were reviewed. Both bilingual documentation pairs are synchronized; no commit/push. Samsung/H10 BLE behavior and reconnecting after clear still require hardware validation.

### 5.43.4 缩小弹窗高度（2026-10-04） / Reduce dialog height

- 用户提示词：将当前弹窗高度减少到原来的 60%，暂不考虑大量 Nearby 设备。
  User prompt: Reduce the current dialog height to 60% of its previous height; do not handle large Nearby lists in this change.
- DevicesDialog.kt 的固定高度由可用窗口的 90% 改为 54%（90% × 60%），覆盖 5.43.3 的高度规则；沿用现有布局和中间滚动，不增加列表处理逻辑。
  Change the fixed height in DevicesDialog.kt from 90% to 54% of the available window (90% × 60%), superseding the height rule in 5.43.3. Retain the existing layout and body scrolling without new list-handling logic.
- debug 构建通过，日志为 build/step86-compact/build-height60.txt；本轮未新增或运行测试、lint、模拟器视觉或真机验收。同步两对中英文文档，无 commit/push。
  The debug build passes; evidence is in build/step86-compact/build-height60.txt. No tests, lint, emulator visual checks or hardware validation were added/run this turn. Both bilingual documentation pairs are synchronized; no commit/push.

### 5.43.5 精简连接提示（2026-10-04） / Shorten connection messages

- 用户提示词：删除连接错误提示中 Tap 及其后面的文本。
  User prompt: Remove Tap and the following text from connection error messages.
- PolarBleManager.kt 移除连接结束、10 秒超时、连接异常及主动断开提示中的 Tap 重试/重连句，保留原因、GATT 状态或异常类型；连接操作逻辑不变。
  Remove the Tap retry/reconnect sentence from connection-ended, ten-second timeout, connection-exception and intentional-disconnection messages in PolarBleManager.kt. Retain reasons, GATT status or exception type; connection behavior is unchanged.
- debug 构建通过（build/step86-compact/build-error-copy.txt）；本轮无新增测试，未运行测试套件、lint 或设备验收。同步两对中英文文档，无 commit/push。
  The debug build passes (build/step86-compact/build-error-copy.txt). No tests were added; test suites, lint and device validation were not run this turn. Both bilingual documentation pairs are synchronized; no commit/push.

### 5.43.6 Dialog height 80% / 弹窗高度 80%（2026-10-04）

- 按用户要求，固定高度改为可用窗口的 80%，覆盖此前 54%；其余布局沿用。
  Set the fixed height to 80% of the available window as requested, superseding 54%; retain the remaining layout.
- Debug 构建通过（build/step86-compact/build-height80.txt）；本轮未运行测试、lint 或视觉/真机验收。两对文档同步，无 commit/push。
  Debug build passed (build/step86-compact/build-height80.txt); no tests, lint, visual or hardware checks this turn. Both documentation pairs synchronized; no commit/push.


### 5.43.7 Timeout message / 超时提示（2026-10-04）

- 按用户要求，删除超时后的 No connection was confirmed; the request was cancelled.，仅保留 Connection timed out after 10 seconds.。取消未确认连接时保留原始 message，清理与连接状态逻辑不变。
  Remove the extra No connection was confirmed; the request was cancelled. after timeout. Retain the original cancellation message; cleanup and connection state logic remain unchanged.
- Debug 构建通过（build/step86-compact/build-timeout-copy.txt）；未运行测试、lint 或真机验收。两对文档同步，无 commit/push。
  Debug build passed (build/step86-compact/build-timeout-copy.txt); no tests, lint or hardware checks this turn. Both documentation pairs synchronized; no commit/push.


### 5.44 History list without Incomplete / 移除列表完整性标记（2026-10-04）

- 用户提示词：删除 History 列表中 Incomplete 的显示，不需要检测数据未完成状态。列表卡片已移除 record.incomplete 判断及 Incomplete 文本；列表展示不再区分完整性。仅调整列表，保留已有数据记录和详情页行为；覆盖 4.3/5.35 中列表标记要求。
  User prompt: Remove Incomplete from the History list; the list does not need to check incomplete-data status. Remove the record.incomplete condition and label from list cards. Preserve stored records and detail-page behavior. This supersedes the list-marker requirement in 4.3/5.35.
- 更新既有 HistoryListTest 断言为标记不存在；debug 与测试 APK 构建通过（build/step86-compact/build-history-label.txt）。未运行测试套件、lint 或真机验收；两对文档同步，无 commit/push。
  Update the existing HistoryListTest assertion to expect no marker. Debug and test APK builds pass (build/step86-compact/build-history-label.txt). Test suites, lint and hardware checks were not run; both documentation pairs synchronized, no commit/push.

### 5.44.1 Activity Summary without Incomplete / 移除详情完整性提示（2026-10-04）

- 用户提示词：Activity Summary 中的 Incomplete 提示也删除。HistoryDetailLayout.kt 已移除日期/时间下方的 Incomplete 文本及 record.incomplete 显示判断，沿用日期时间与其余布局，保留存储字段。此规则覆盖早期详情标记要求。
  User prompt: Also remove Incomplete from Activity Summary. Remove the label below the date/time and its record.incomplete display condition in HistoryDetailLayout.kt. Retain date/time, other layout and stored fields. This supersedes the earlier detail-marker requirement.
- Debug 构建通过（build/step86-compact/build-summary-label.txt）；本轮未新增或运行测试、lint、视觉或真机验收。两对文档同步，无 commit/push。
  Debug build passed (build/step86-compact/build-summary-label.txt); no tests, lint, visual or hardware checks were added/run this turn. Both documentation pairs synchronized; no commit/push.

### 5.45 Session HR Zone display / Session 区间展示（2026-10-04）

- 用户提示词：删除 Session 页面 HR Zone 最下方的 Unclassified。SessionHeartRateZonePanel.kt 已删除 Unclassified time 行及其显示判断；保留五个区间、时长计算及存储字段，覆盖此前 Session 未归类时长展示要求。
  User prompt: Remove Unclassified below HR Zone on Session. Remove the Unclassified time row and its display condition from SessionHeartRateZonePanel.kt. Retain the five zones, duration calculations and stored fields; this supersedes the previous Session unclassified-duration display requirement.
- 同步既有 SessionMetricsTest 的标记断言和截图定位；debug/测试 APK 构建通过（build/step86-compact/build-session-unclassified.txt）。未运行测试套件、lint 或视觉/真机验收；两对文档同步，无 commit/push。
  Update the existing SessionMetricsTest label assertion and capture target. Debug/test APK builds pass (build/step86-compact/build-session-unclassified.txt). Test suites, lint and visual/hardware checks were not run; both documentation pairs synchronized, no commit/push.

### 5.46 增加功能：ECG、RR 图表持久化保存 / Additional feature: ECG and RR chart persistence（2026-10-04）

- 实施步骤编号：**9.0a**（用户于 2026-10-04 指定）。5.46 为设计记录编号，5.46.1 与 5.46.2 为现行规则，编号分配不表示已实施。
  Implementation step: **9.0a**, assigned by the user on 2026-10-04. Section 5.46 is the design record; 5.46.1 and 5.46.2 define current rules. Number assignment does not imply implementation.

> 当前有效规则见 5.46.1 及修订 5.46.2；以下 5.46 草案保留为历史记录，待定项已被替代。 / See 5.46.1 and revision 5.46.2 for current rules; the following draft is historical and its open decisions are superseded.

- 用户请求：把实现 ECG、RR 图表持久化保存的 prompt 写入文档，记录为增加功能。
  User request: Save the implementation prompt for ECG/RR chart persistence in the documentation as an additional feature.
- 状态：仅记录中英文实施草案，未授权本轮实施；待定规则仍需确认，不表示功能已完成。本轮不修改 App、数据库或测试代码，不运行构建、测试或真机验证，不 commit/push。
  Status: Bilingual implementation draft only; implementation is not authorized in this turn. Open decisions remain unconfirmed. No App, database or test code changes, builds, tests, hardware validation, commit or push are part of this documentation update.
- 当前基线：此前检查中 ECG 仅有实时短缓存，RR 未接入，History 的 ECG/RR 入口禁用。正式实施前重新核对现行源码，尤其会话生命周期和未提交改动，不用旧提示词覆盖后续调整。
  Baseline: The preceding inspection found only a short live ECG buffer, no RR processing and disabled History ECG/RR choices. Recheck current source before implementation, especially lifecycle behavior and uncommitted changes; do not overwrite subsequent changes with this older draft.

#### 中文实施 prompt（草案，待确认）

```text
请为当前 PolarH10ActivityViewer 增加 ECG、RR 本地持久化及 History 图表。先读取当前 AGENTS.md 和现行源码，只修改本功能必需的代码。沿用 Kotlin、Compose、SQLiteOpenHelper 和 Polar BLE SDK 8.3.0，不升级依赖，不进行无关重构。App 文案和代码注释使用英文。

开始实施前先确认以下四项；以下建议不代表用户已经确认：
1. 是否保存整场原始 ECG 和逐条 RR，而非只保存末尾片段。
2. RR 横轴采用 Recorded interval number，还是另行定义近似会话时间。
3. 已有数据库采用一次性保留数据升级，还是使用明确获准的新数据库方案。当前项目禁止擅自增加兼容路径，不得自动删除已有历史；保留数据升级需明确确认。
4. 进程异常结束后，未完成记录的保留、展示或清理规则。

1. 数据来源
ECG 直接使用现有 startEcgStreaming 的原始样本，保留 SDK 纳秒时间戳和有符号 µV 电压。保留现有实时短缓存，持久化直接接收采集批次，不能在 Stop 时从短缓存补取整场数据。
RR 从现有 HR 订阅的 rrsMs 提取，不增加独立 BLE 订阅。要求 rrAvailable、非空列表、正值，以及支持接触状态时接触有效。按顺序保留批内全部间期，不用 HR 推算 RR，不按相同数值去重，不新增 ECG R 峰检测或 HRV 算法。

2. 数据模型与时间
保存 sessionId、流内稳定序号、分段编号、必要时间锚点及实际 ECG 采样率。RR 另存批次接收时间和批内顺序。
ECG 使用传感器时间保留样本间隔，会话时间映射注明近似对齐。明确首批样本、暂停边界和结束边界的归属，不能通过强行截成同一时间破坏样本顺序。
RR 若使用序号横轴，不宣称精确时间对齐；若采用时间横轴，先定义和验证估计方法。SDK 的 HR 样本没有逐搏时间戳，接收时间不能作为精确搏动时间。

3. 生命周期
接入现行 Start/Pause/Continue/Stop、四小时 Running 上限和已确认的中断处理，不重写生命周期策略。实施前核对后台、锁屏与返回页面的最新行为，不依据旧记录恢复已被修改的行为。
暂停不计入 Running 时长，Continue 保留同场数据，新 Start 使用新会话。流重启、缺口和时间戳异常形成分段，不跨段连线、不插值、不补零。结束后拒绝旧回调，UI 清空不能删除尚未提交的持久化数据。

4. 存储
使用 IO 线程和有界缓冲分批写入，避免主线程写库、整场 ECG 对象列表及逐样本刷新 UI。不能复用 HR 每秒一个点的保存方式，否则会丢失 ECG 波形细节。
定义未完成记录和最终提交状态。结束后排空已接受的待写批次，再提交摘要与完成状态；全部成功后才显示 Saved。不得把 ECG/RR 大列表直接加入 SessionSnapshot 后继续一次性保存。
写入失败必须明确报告，定义缓冲满时的行为，不允许静默丢点。重试不得重复写入，丢弃须清理对应未完成数据。保留现有 HR/Cadence 摘要与历史一致性。
按已确认方案处理数据库版本、表结构、索引和事务。数据库升级或重新建库不能擅自删除已有数据。

5. History 图表
启用 ECG/RR 入口。建议 ECG 默认显示 5 秒窗口，提供前后切换或滑块浏览整场数据，纵轴为 µV。RR 纵轴为 ms，横轴采用已确认方案。
详情不一次性读取整场 ECG；窗口查询使用索引，切换会话或窗口时取消旧查询并拒绝旧结果。绘图抽稀只用于显示，不覆盖保存的原始数据，不机械套用 HR/Cadence 的均值虚线。
区分无数据和查询失败，记录缺口按图表断段处理。沿用 5.44/5.44.1 已删除 Incomplete 标记的展示要求，不自动恢复列表或详情完整性标签；新增持久化失败提示和未完成记录可见性按确认规则处理。
删除活动时事务删除全部关联 ECG/RR 数据，并更新删除提示。

6. 修改范围
重点检查 ble/PolarBleManager.kt、sensor/EcgBuffer.kt、session/SessionRecord.kt、session/SessionState.kt、storage/SessionDatabase.kt、SessionSaveController.kt、SessionStorage.kt、history/HistoryPanel.kt、HistoryCharts.kt，以及必要的图表类型、刻度、绘图和详情布局代码。按职责增加最少的数据模型或写入组件，不引入通用框架。
保留 HR/Cadence 历史、统计、计步、连接和分页行为。不增加 Trend 页面、Session 实时 RR 图、HRV、导出、云同步或后台采集功能；不撤销现有生命周期改动。

7. 验证与记录
验证多 RR 批次、相同 RR 值、无效接触、ECG 正负值、时间分段、暂停继续、迟到回调、写入失败、重试去重、最终提交、窗口查询及完整删除。
使用生成数据验证四小时规模的存储和查询，记录时间与内存表现，明确不属于 H10 真机验证。检查深浅主题和字号 1.0/2.0。
运行适当的单元测试、SQLite/Compose 检查、debug 构建和 lint。同步两份 AGENTS.md 和两份 prompt.md，准确区分代码实现、自动检查、模拟器结果及 Samsung/H10 待验收项。不要 commit 或 push。
```

#### English implementation prompt (draft; decisions pending)

```text
Add local ECG/RR persistence and History charts to the current PolarH10ActivityViewer. Read the current AGENTS.md and source first. Make only changes required for this feature. Keep Kotlin, Compose, SQLiteOpenHelper and Polar BLE SDK 8.3.0. Do not upgrade dependencies or perform unrelated refactoring. Use English UI text and code comments.

Confirm these four decisions before implementation; the proposals below are not yet approved:
1. Retain all raw ECG samples and individual RR intervals throughout the session, or only a final excerpt.
2. Use Recorded interval number for the RR x-axis, or define an estimated session timeline separately.
3. Use a one-time migration preserving existing history, or an explicitly approved fresh-database approach. Do not add compatibility paths or delete history without authorization; a preserving migration requires explicit confirmation under the current project rules.
4. Define retention, visibility or cleanup of unfinished records after unexpected process termination.

1. Sources
Use original startEcgStreaming samples, preserving SDK nanosecond timestamps and signed microvolt values. Keep the live buffer and feed persistence directly from incoming batches; the short buffer cannot supply a whole session at Stop.
Extract every rrsMs interval from the existing HR subscription without a separate BLE subscription. Require RR availability, a nonempty list, positive values and valid contact when supported. Preserve within-batch order. Do not derive RR from HR, deduplicate equal values, or add ECG R-peak detection or HRV algorithms.

2. Models and timing
Store the session ID, stable per-stream sequence, segment ID, required timing anchors and actual ECG sample rate. Also retain RR batch receipt time and within-batch order.
Preserve ECG sample spacing using sensor timestamps and describe session alignment as approximate. Define initial-sample and pause/end boundary handling without collapsing samples onto identical timestamps.
An interval-number RR axis must not imply precise time alignment. Define and validate estimation before using a time axis. SDK HR samples have no per-beat timestamp; receipt time is not an exact heartbeat timestamp.

3. Lifecycle
Integrate with the current Start/Pause/Continue/Stop behavior, four-hour Running limit and confirmed interruption handling without redesigning lifecycle policy. Recheck current background, lock-screen and return behavior; do not restore superseded behavior from older documentation.
Exclude pauses from Running duration, retain earlier data on Continue, and use a new session for a new Start. Stream restarts, gaps and timestamp anomalies create segment boundaries. Do not connect across gaps, interpolate or insert zeros. Reject stale callbacks after finalization. UI reset must not delete pending persistence data.

4. Storage
Use bounded buffering and batched IO writes. Avoid main-thread database writes, whole-session ECG object lists and per-sample UI updates. Do not reuse HR's one-point-per-second storage rule for ECG.
Define unfinished and finalized records. Drain accepted batches before committing the final summary and completion state. Show Saved only after all required writes succeed. Do not simply add large ECG/RR lists to SessionSnapshot and retain end-only saving.
Report write failures and define buffer-full behavior without silent loss. Retries must not duplicate records; discarding must clean up associated unfinished data. Preserve consistency with existing HR/Cadence summaries and histories.
Handle database versions, tables, indexes and transactions according to the confirmed approach. Never delete existing data implicitly during upgrade or recreation.

5. History charts
Enable ECG and RR. Propose a five-second ECG window with previous/next controls or a slider for browsing the session and a µV axis. Use ms for RR and the confirmed x-axis.
Do not load all ECG when opening details. Use indexed window queries and cancel/reject stale queries and results when changing sessions or windows. Display reduction must not overwrite stored samples. Do not automatically reuse HR/Cadence mean reference lines.
Distinguish absent data from query failure and display recording gaps as breaks. Preserve sections 5.44/5.44.1, which removed Incomplete labels; do not restore list/detail completeness labels automatically. Follow the confirmed policy for new persistence failures and unfinished-record visibility.
Delete all associated ECG/RR data transactionally and update the deletion message.

6. Scope
Inspect ble/PolarBleManager.kt, sensor/EcgBuffer.kt, session/SessionRecord.kt, session/SessionState.kt, storage/SessionDatabase.kt, SessionSaveController.kt, SessionStorage.kt, history/HistoryPanel.kt, HistoryCharts.kt and necessary chart types, scales, rendering and detail layout. Add only the necessary models or writer components; avoid a generic framework.
Preserve HR/Cadence history, statistics, step processing, connections and pagination. Do not add Trend, a live Session RR chart, HRV, export, cloud synchronization or background acquisition. Do not undo existing lifecycle changes.

7. Validation and documentation
Cover multiple RR intervals per batch, equal values, invalid contact, signed ECG values, segmentation, pause/resume, stale callbacks, write failure, retry deduplication, finalization, window queries and complete deletion.
Use generated data to assess four-hour storage/query behavior and record timing and memory observations without presenting this as hardware validation. Check light/dark themes and font scales 1.0/2.0.
Run appropriate unit tests, SQLite/Compose checks, debug build and lint. Synchronize both AGENTS.md files and both prompt.md files. Separate implementation, automated checks, emulator results and pending Samsung/H10 validation. Do not commit or push.
```

#### SDK 8.3.0 依据 / SDK 8.3.0 references

- [PolarHrData: rrsMs, rrAvailable and contact status](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarHrData.kt)
- [PolarEcgData: sample timestamps and voltage](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarEcgData.kt)
- [Polar H10: ECG 130 Hz and supported data](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/documentation/products/PolarH10.md)

### 5.47 离开前台自动暂停 / Automatic pause on leaving Session（2026-10-04）

- 用户原始请求：离开前台、切换 Activity、锁屏等行为要暂停活动，先给实施方案、不修改代码；随后确认“实现”。以下为采用方案的中英文整理，不是逐字原始提示词。
  Original request: Pause the activity session when leaving the foreground, switching Activity or locking the screen; first provide a plan without editing code. The user then requested implementation. The following is an edited bilingual description of the accepted plan, not a verbatim prompt.
- 中文实施描述：复用现有 Pause/Continue，在 onPause 和切换 History 时自动暂停，覆盖 Running/Starting，返回保持 Paused。停止三路订阅、排除暂停时间，保留同场 ID/统计/曲线，不提交最终保存。用应用级持有者替换 Activity ViewModel，支持返回欢迎页再进入。已有连接不主动断开；暂停期间断线或权限释放保留会话，重连原 H10 后手动 Continue，阻止切换设备混入同场。保留 Stop/四小时/运行中真实断线的结束规则，不新增后台采集或进程死亡恢复。检查迟到数据、重复暂停、生命周期、重连边界和保存，同步文档，不 commit/push。
  English implementation description: Reuse Pause/Continue and automatically pause on Activity.onPause and History selection, including Running/Starting, with manual Continue on return. Stop the three subscriptions, exclude paused time and retain the session ID/statistics/charts without final saving. Replace Activity ViewModel ownership with application ownership so welcome navigation preserves the session. Keep established connections; preserve paused sessions through disconnect/permission release and require reconnection to the original H10 before Continue. Prevent another device from joining the same session. Retain Stop, four-hour and running-disconnect finalization. Add no background acquisition or process-death recovery. Validate stale events, repeat pauses, lifecycle, reconnect boundaries and saving; synchronize docs without commit/push.
- 采用：上述方案已实施；最小改动使用既有状态机/订阅/存储，不改数据库结构或 SDK 配置。删除旧 SensorViewModel/leaveSession 路径；SDK资源释放与会话结束分开。验证与边界见 AGENTS.md 5.47：237 单元、7 项受控模拟器检查及构建/lint 通过；H10 未验证。
  Adoption: Implemented using existing state/subscription/storage logic without schema or SDK-setting changes. Remove obsolete SensorViewModel/leaveSession paths and separate SDK cleanup from session finalization. See AGENTS.md 5.47: 237 unit tests, seven controlled emulator checks and builds/lint pass; H10 remains unverified.
### 5.46.1 已确认规则：ECG/RR 持久化与窗口浏览 / Confirmed ECG/RR persistence and browsing rules（2026-10-04）

- 用户确认：“好的，按你说的明确规则”。本节替代 5.46 中的待定方案和对应实施 prompt 条款；仅明确规则，代码未实施。其余验证与范围约束沿用 5.46。先前“不持久化 ECG/未接入 RR”仍是当前代码事实，不再是本功能的目标限制。
  User confirmation: “Okay, clarify the rules as you recommended.” This section supersedes the open proposals and corresponding prompt clauses in 5.46. Rules only; code is not implemented. Other scope and validation requirements in 5.46 remain. Earlier no-ECG-persistence/no-RR statements describe current code, not the target scope.

#### 1. 保存内容与组织 / Content and organization

- 保存整场已接受的原始 ECG 和每条有效 RR，不取平均、不固定抽点、不进行有损压缩。保留 SDK 8.3.0、实际设置查询及现有实时 ECG 短缓存。历史数据直接来自采集批次，不从屏幕缓存或图形反推。
  Save all accepted raw ECG samples and every valid RR interval for the session without averaging, fixed-step point removal or lossy compression. Keep SDK 8.3.0, actual settings queries and the existing live ECG buffer. Feed history from acquisition batches, not from the viewport or rendered chart.
- ECG 使用约一秒一个二进制数据块（SQLite BLOB），正常 130 Hz 时约 130 点；按原始传感器时间划分半开一秒桶，桶内逐点保留纳秒时间戳和有符号 µV 值，另存 sessionId、segmentId、chunkIndex、格式版本、实际采样率及会话时间锚点。不按一个 SDK 回调等于一秒处理。暂停、缺口、重启、结束立即封闭不足一秒的块，不跨分段合并；块序号在会话内唯一。可采用固定字节序的 Long/Int 编码，不需要额外压缩库。
  Store ECG in approximately one-second binary SQLite BLOB chunks, about 130 points at 130 Hz. Use half-open one-second buckets based on original sensor time; preserve each nanosecond timestamp and signed µV value. Store sessionId, segmentId, chunkIndex, format version, actual sample rate and session-time anchor. An SDK callback is not a one-second bucket. Close partial chunks at pause, gap, restart and end without crossing segments; chunk indexes are unique within the session. Fixed-endian Long/Int encoding is sufficient; no compression library is required.
- RR 复用现有 HR 订阅的 rrsMs；rrAvailable 为 true、列表非空、值 > 0，支持接触状态时要求有效接触。不额外要求 HR 数值有效才接收本身有效的 RR。按批次/样本/间期原顺序逐条保存 sessionId、recordIndex、segmentId、rrMs、批次接收时间、接收时会话时间、批内顺序。recordIndex 从 1 开始，只对接受的 RR 递增，同值不去重。RR 不是独立 BLE 流，不增加 HRV 或 ECG R 峰检测。
  Extract rrsMs from the existing HR subscription with rrAvailable, a nonempty list, positive values and valid contact when supported. Do not require a valid HR number as an additional gate for otherwise valid RR. Preserve batch/sample/interval order and store sessionId, recordIndex, segmentId, rrMs, batch receipt time, session elapsed time at receipt and within-batch order. Start recordIndex at 1 and increment for accepted RR; equal values remain distinct. RR is not a separate BLE stream; add no HRV or ECG R-peak detection.
- RR 可独立满足收到有效数据/保存资格，但不得伪造有效 HR 或改变 HR 均值、区间。空 RR 批次或无效间期不插入零值，下一有效间期从新段开始。
  Valid RR may independently establish valid reception/save eligibility without inventing valid HR or changing HR means/zones. Empty RR batches and invalid intervals insert no zero; the next valid interval starts a new segment.

#### 2. 时间与缺口 / Timing and gaps

- ECG 每次启动/继续/重试建立新段，以首批最后样本的传感器时间和该批手机接收时的 Running elapsed 为锚点，映射 t = anchorElapsed + (sensorTime - anchorSensorTime) / 1,000,000；原始纳秒时间戳完整保留。这是含传输延迟的近似对齐，不是精确手机/传感器同步。
  Start a new ECG segment on start/resume/retry. Anchor the final sensor timestamp of the first batch to Running elapsed at its phone receipt; map t = anchorElapsed + (sensorTime - anchorSensorTime) / 1,000,000. Preserve original timestamps. Alignment includes transport delay and is approximate.
- 相邻 ECG 时间差 > 3 个实际采样周期时断段；重复或倒退时间戳也断段并重新建立锚点，保留原始值和接收顺序，不按时间戳键覆盖。映射到 0 之前、所属 Running 段之外或最终 Duration 之后的点保留原始记录，但不画到活动时间范围内、不强行夹到边界。查询/绘图同时按段处理，不能跨暂停或缺口连线。
  Break ECG segments for a gap greater than three actual sample periods or a repeated/backward timestamp; re-anchor and retain original values/reception order without timestamp-key replacement. Keep raw points mapped before zero, outside their Running segment or beyond final Duration, but do not draw them inside the activity interval or clamp them onto a boundary. Query/render by segment and never bridge pauses or gaps.
- RR 横轴确定为 Recorded interval number，不反推逐搏时间。HR 批次接收间隔 > 3 秒、无效/空 RR、暂停继续、订阅重启或失败形成断段。多个 RR 共用批次接收时间是允许的，序号负责稳定排序。该图不能与 ECG 宣称逐搏精确对齐。
  The RR x-axis is Recorded interval number; do not reconstruct beat timestamps. Break after HR batch gaps above three seconds, empty/invalid RR, pause/resume, subscription restart or failure. Multiple intervals may share a batch receipt time; indexes define stable order. Do not claim beat-exact ECG alignment.

#### 3. 图表交互 / Chart interaction

- ECG 默认显示起始五秒 [0, 5s)，不足五秒显示实际活动范围；原始点连线，不逐点画圆点、不填充、不加均值虚线。图下时间滑块选择窗口起点，拖动时只更新时间标签，松手后查询；Previous/Next 每次前后五秒。起点限制在 0 至 max(0, Duration - 5s)，最后窗口按实际时长对齐。无数据的窗口显示 No ECG data in this interval。
  ECG starts with [0, 5s), or the actual duration when shorter. Connect raw samples without point markers, fill or mean reference. A time slider updates its label while dragging and queries on release; Previous/Next moves five seconds. Clamp the start to 0..max(0, Duration - 5s) and align the final window to the actual duration. Empty windows show No ECG data in this interval.
- RR 默认显示第 1—60 条有效记录，不足 60 条全部显示；纵轴 ms、横轴记录序号，用简单折线，断段不连线。滑块按整数序号定位，松手后查询；Previous/Next 每次移动 60 条，末窗口最多 60 条且首尾按钮禁用。不采用 60 秒窗口。无记录显示 No recorded RR data。
  RR initially shows records 1–60, or all records when fewer exist. Use ms vertically and interval index horizontally with a simple segmented line. The slider selects an integer index and queries on release; Previous/Next moves 60 records, with at most 60 in the final window and disabled boundary buttons. Do not use a 60-second window. Empty sessions show No recorded RR data.
- 不直接拖动波形，不加缩放或整场概览。按选中会话/图种/窗口查询，取消并拒绝过期结果，不一次性加载整场 ECG/RR。ECG 窗口边界可读取同段相邻点用于裁剪连线，但不跨段连线。查询失败提供 Retry；保留现有深浅主题及大字号滚动。
  Add no direct waveform dragging, zoom or whole-session overview. Query by session/chart/window, cancel/reject stale results and never load whole-session ECG/RR. ECG may read adjacent same-segment points to clip lines at viewport boundaries, never across segments. Query failures offer Retry; retain themes and large-font scrolling.

#### 4. 写入、失败与最终提交 / Writes, failures and final commit

- 使用一个应用级 IO 写入者和有界队列。封闭的 ECG 块及时入队；RR 按最多一秒或 128 条（先到者）形成写入批次。每次事务最多处理 5 个已就绪批次，不为凑满批次额外等待。活动尾部、暂停和正常结束刷新不足批次。队列中编码负载总量最多 1 MiB，包含当前写入/失败待重试项；当前正在收集的部分块另行保持一秒/采样率边界。该上限不等于整个进程内存上限。
  Use one application-owned IO writer and a bounded queue. Enqueue closed ECG chunks promptly; batch RR for up to one second or 128 intervals, whichever comes first. Each transaction processes at most five ready batches without waiting to fill the batch. Flush partial batches on pause and normal finalization. Bound queued encoded payload, including in-flight/failed retry items, to 1 MiB; separately bound the open partial chunk by one second/sample rate. This is not a bound on total process memory.
- 写入失败或队列满时停止继续接受持久化数据并自动暂停当前会话，报告 Recording storage failed。失败事务回滚，保留已经接受但未提交的有界批次及已提交的暂存块，禁止静默丢弃最旧数据。无法入队的输入不计为已接受，记录发生了截断/缺口；不会声称可以找回该输入。提供 Retry save / Discard；重试先处理待写数据，不自动继续采集。成功后保持 Paused，由用户 Continue 或 Stop。若原本已在结束，则成功重试后继续最终提交。
  On write failure or queue saturation, stop accepting persistence data, automatically pause the session and report Recording storage failed. Roll back failed transactions; retain bounded accepted pending batches and committed staging chunks without dropping oldest data silently. Unqueueable input is not accepted; record a truncation/gap and never claim it is recoverable. Offer Retry save / Discard. Retry drains pending data without automatically resuming acquisition; success leaves Paused for manual Continue or Stop, or completes finalization if already ending.
- 用 sessionId + chunkIndex / recordIndex 唯一键实现事务重试去重，不按电压、RR 值或时间戳去重。Discard 结束当前会话并清理其暂存内容，不影响其他已保存活动；清理失败保留错误并允许重试。
  Deduplicate transactional retries by sessionId plus chunkIndex/recordIndex, not voltage, RR value or timestamp. Discard ends the current session and removes its staging data without touching other saved activities; report cleanup failure and allow retry.
- 暂存会话不进入 History 列表；正常 Stop/四小时/Running 中真实断线等既有结束触发后，拒绝迟到数据、封闭尾块、排空队列，再将摘要、既有 HR/Motion 历史和提交标记在最终事务中完成。最终成功前不得显示 Saved；此前已写入块不代表活动完成。存储错误使用局部错误提示，不恢复已删除的 Incomplete 标签。
  Hide staging sessions from History. For existing finalization triggers (Stop, four-hour limit, real disconnect while Running), reject late data, close tail chunks, drain the queue and atomically finalize the summary, existing HR/Motion histories and commit marker. Do not show Saved earlier; written chunks alone do not mean a completed activity. Use local storage errors without restoring removed Incomplete labels.

#### 5. 生命周期、升级和清理 / Lifecycle, upgrade and cleanup

- 严格沿用 5.47：离开前台/锁屏/切换 History 自动暂停、返回不自动继续；暂停保留同场数据，可以刷新暂存块但不能最终提交。暂停时断线仍保留会话，重连原设备后手动 Continue。不得恢复旧的 onStop 结束/断开规则，不增加后台采集。
  Follow 5.47: background/lock/History navigation pauses automatically with no automatic resume. Preserve the session and optionally flush staging chunks without finalizing. Paused disconnect preserves the session for manual Continue after reconnection to the original device. Do not restore old onStop end/disconnect behavior or add background acquisition.
- 允许本功能所需的一次性 SQLite v1→v2 保留数据升级，作为“严禁向后兼容”的本次明确例外；只做该必要升级，不维护双写/双格式旧路径。旧活动视为已提交，保留摘要/HR/Motion，ECG/RR 显示无记录；升级事务失败回滚并报告，不清库。实施时若实际版本已变，先核对，不盲目重复 v1→v2。
  Authorize the required one-time preserving SQLite v1→v2 migration as an explicit exception to the no-backward-compatibility rule. Add only this migration, not dual-write or dual-format legacy paths. Treat existing activities as finalized and retain summaries/HR/Motion; ECG/RR have no records. Roll back/report migration failures without clearing the database. Recheck the actual version at implementation time.
- 进程异常结束处理已由 5.46.2 覆盖：保留有已提交数据的未完成会话，启动时归档并提供 History/Activity Summary 查询；图表中央显示 Data collection incomplete，不自动删除这些部分记录。只清理没有已提交有效采集数据的暂存空会话，不恢复旧会话继续采集。
  Superseded by 5.46.2: retain unfinished sessions with committed data, archive them at startup for History/Activity Summary, and show Data collection incomplete centered in their charts. Do not automatically delete these partial records. Clean up only empty staging sessions without committed valid acquisition data; do not resume old acquisition.

#### 6. 验收与本轮结果 / Acceptance and this update

- 增加验证：BLOB 编解码逐点一致、跨秒/尾块、130 Hz 长记录、有界队列及溢出/磁盘失败、失败时暂停、重试唯一性、后台自动暂停后 Continue、旧库升级不丢数据、重启保留并归档部分记录及中央提示（5.46.2）、5 秒 ECG/60 条 RR 浏览、快速滑块松手查询过期隔离及删除事务。四小时生成数据性能与真实 H10 多流测试分开记录。
  Add checks for lossless BLOB round trips, second boundaries/tail chunks, long 130 Hz records, queue bounds/overflow/disk failure, failure-induced pause, retry uniqueness, automatic pause/Continue, preserving migration, startup retention/archiving and centered messages for partial records (5.46.2), five-second ECG/60-record RR browsing, stale query isolation and deletion transactions. Separate four-hour generated-data performance from real H10 multi-stream validation.
- 本轮仅更新并核对两对文档；未修改应用/数据库/测试源码，未运行构建、测试、模拟器或真机验证，无 commit/push。上述容量/批量参数为已选工程起点，未声称通过性能验收；实施验收发现不满足时需记录证据及必要调整。
  This update only edits/verifies both documentation pairs. No App/database/test source changes, builds, tests, emulator/hardware checks, commit or push. Capacity/batch parameters are chosen engineering starting points, not verified performance results; document evidence and necessary changes if acceptance fails.

### 5.46.2 修订：异常结束保留记录与图表提示 / Revision: interrupted-record retention and chart message（2026-10-04）

- 用户要求：进程异常结束后，在 Activity Summary 图表中间显示“数据采集未完成”，样式类似当前心率图的 No recorded heart rate data。本节覆盖 5.46.1 的启动删除未完成记录规则；仅更新规划和 prompt，未实施代码。
  User request: After unexpected process termination, show a centered incomplete-collection message in the Activity Summary chart, styled like the current No recorded heart rate data message. This supersedes startup deletion of unfinished records in 5.46.1. Documentation/prompt update only; code is not implemented.
- 下次进程启动时，在 IO 事务中将有已提交采集数据但没有最终提交标记的会话归档为采集中断，并保留已落盘数据，使其可从 History 列表打开 Activity Summary。不恢复旧会话继续采集；新 Start 创建新会话。重复启动不得重复归档或产生重复活动。完全没有已提交有效采集数据的暂存空会话可清理；正在本进程内正常暂停的会话不归档。
  On the next process start, use an IO transaction to archive sessions with committed acquisition data but no final commit marker as interrupted, retaining durable data and making them accessible from History. Do not resume acquisition for the old session; new Start creates a new session. Repeated startup must not duplicate records or rearchive them. Empty staging sessions without committed valid acquisition data may be cleaned up; ordinary pauses in the live process are not archived.
- 持久化会话身份、开始时间、设备与可恢复的最近检查点。分批写入时同时提交对应进度/必要摘要检查点，保证恢复出来的摘要不超前于其已提交数据；不能依赖仅在内存中的最终 SessionSnapshot。HR/Motion 没有落盘的数据不伪造、不从 ECG/RR 反算。不知道的指标显示 --，Duration 最多采用最后持久化的 Running 时长，不能拿下次启动时间当运动结束时间，也不把暂停时间算入。检查点是部分结果，不宣称最终完整摘要。
  Persist session identity, start time, device and a recoverable checkpoint. Commit corresponding progress/necessary summary checkpoints with batched data so recovered summaries do not run ahead of their committed data; do not depend solely on an in-memory final SessionSnapshot. Do not invent unpersisted HR/Motion data or reconstruct it from ECG/RR. Unknown metrics show --; Duration uses at most the last persisted Running duration, never the next launch time or paused duration. A checkpoint is partial, not a complete final summary.
- Activity Summary 的 HR/Cadence/ECG/RR 当前选中图表均显示 Data collection incomplete，位置为图表绘图区中央，沿用 HistoryCharts.kt 现有 Modifier.align(Alignment.Center) 和 MaterialTheme.typography.bodySmall 及主题文字颜色；不用红色警告条、不增加弹窗，不恢复日期下方或列表中的 Incomplete 标签。
  For an interrupted session, each selected HR/Cadence/ECG/RR chart in Activity Summary shows Data collection incomplete centered in the plot area, reusing HistoryCharts.kt's Modifier.align(Alignment.Center), MaterialTheme.typography.bodySmall and theme text color. Add no red warning banner or dialog and do not restore date-area/list Incomplete labels.
- 保留并显示可用的部分曲线和窗口浏览，中间文字作为覆盖提示；不存在的点不补零、不跨缺口连线。该提示优先于 No recorded .../No ECG data in this interval，同一绘图区不叠加两条空态文字；查询失败时仍优先展示实际查询错误及 Retry。只有异常归档记录使用该提示，正常完成但某流无数据仍使用原无数据提示。不要直接用旧 record.incomplete 字段触发，需区分进程中断归档、普通数据缺失和存储失败。
  Keep available partial curves and browsing, with the centered message as an overlay. Do not insert zeros or bridge gaps. The message takes precedence over No recorded .../No ECG data in this interval, avoiding duplicate empty-state text; actual query errors and Retry retain priority. Apply it only to interrupted archives, not every completed activity missing a stream. Do not drive it directly from the old record.incomplete flag; distinguish interrupted archives, ordinary missing data and storage failure.
- 仅能恢复已成功提交的数据；进程结束时的内存队列和未提交尾块可能丢失，不能承诺补回。正常活动最终提交和 Saved 规则不变；异常归档不伪装为正常完成。用户删除该活动时事务删除所有关联数据。启动归档失败应报告并支持重试，不通过清库恢复。
  Only committed data is recoverable; in-memory queues and uncommitted tail chunks may be lost and must not be promised back. Preserve normal finalization/Saved rules without treating an interrupted archive as normal completion. Delete all associated data transactionally on user deletion. Report startup archiving failures with retry; never recover by clearing history.
- 后续验收增加：真实杀进程/重启恢复、尾块丢失边界、无空记录、重复启动幂等、部分摘要与曲线一致性、四种图表中央提示/空态优先级、深浅主题与字号 1.0/2.0，以及普通暂停/正常完成不误显示提示。本轮未运行这些检查，未修改应用代码、数据库或测试，无 commit/push。
  Add future checks for process kill/relaunch, tail loss, absence of empty records, idempotent startup, partial-summary/data consistency, centered messages/empty-state priority across four charts, light/dark and font scales 1.0/2.0, and no false message for normal pause/completion. None ran in this documentation turn; no App/database/test changes, commit or push.

### 9.0a ECG、RR 图表持久化保存 / ECG and RR chart persistence（2026-10-04）

- 状态：9.0a 已实施，验证及待真机项目见 5.46.3。设计记录见 5.46；执行 5.46.1 的已确认规则，并以 5.46.2 覆盖异常结束处理。5.46 中早期草案保留为历史，不再按其待定项或清理部分记录的旧方案实施。
  Status: Step 9.0a is implemented; validation and hardware limits are in 5.46.3. See design record 5.46. Apply confirmed rules in 5.46.1 with interrupted-record handling superseded by 5.46.2. The original draft is historical; do not implement its superseded open decisions or partial-record deletion policy.

#### 中文执行 prompt

请只实施步骤 9.0a：ECG、RR 图表持久化保存。严格执行本文 5.46.1 和 5.46.2：ECG 整场原始数据按约一秒分块保存、五秒窗口浏览；RR 逐条保存，以记录序号为横轴，每次显示六十条；使用滑块和前后按钮。沿用 5.47 自动暂停与手动 Continue，落实有界写入、失败重试、最终提交及保留已有历史的数据库升级。进程异常结束后保留已提交的部分记录，Activity Summary 图表中央按现有空态样式显示 Data collection incomplete，不恢复旧会话采集，不恢复列表 Incomplete 标签。遵循对应规则的时间、分段、检查点、查询、删除及验收要求；同步两对中英文文档，分别报告实现与验证结果，不自动推进其他步骤，不 commit/push。

#### English execution prompt

Implement only step 9.0a: ECG and RR chart persistence. Follow sections 5.46.1 and 5.46.2: store full-session raw ECG in approximately one-second chunks and browse five-second windows; preserve individual RR intervals with a recorded-interval-number axis and sixty records per window, using a slider and previous/next buttons. Preserve automatic pause and manual Continue under 5.47. Implement bounded writes, failure retry, final commit and a database migration preserving existing history. After unexpected process termination, retain committed partial records and center Data collection incomplete in Activity Summary charts using the existing empty-state style. Do not resume old acquisition or restore list Incomplete labels. Follow the specified timing, segmentation, checkpoints, queries, deletion and acceptance rules. Synchronize both bilingual documentation pairs and distinguish implementation from validation. Do not proceed to other steps, commit or push.

- 编号分配轮仅更新文档；用户随后要求“实现9.0a”，实施及验证记录如下。
  The number-assignment turn changed documentation only. The user subsequently requested implementation; results follow.

### 5.46.3 步骤 9.0a 实施与验证 / Implementation and validation（2026-10-04）

- 用户明确要求“实现9.0a”，本节更新当前实现状态；5.46—5.46.2 的“未实施”保留为当轮历史记录，当前以本节为准。仅实施 ECG/RR 持久化及必要的恢复、查询、展示与检查；保留 5.47 自动暂停和手动 Continue，不新增 Trend、HRV 或四项摘要指标算法。
  The user explicitly requested implementation of 9.0a. Earlier unimplemented statuses describe their documentation turns; this section records the current result. Scope covers ECG/RR persistence and required recovery, querying, display and validation. Preserve 5.47 automatic pause/manual Continue; add no Trend, HRV or algorithms for the four summary placeholders.
- 新增 SignalHistory.kt、SignalWriter.kt、RecordingPanel.kt；接入 PolarBleManager、SessionController、SessionStorage/SaveController、SQLite 和 History 图表。ECG 保留原始纳秒时间及有符号 µV，以版本 1、小端序、每点 12 字节的 BLOB 按传感器秒及段边界分块，不均值合并或抽点。RR 独立按 rrAvailable、接触有效性、非空和正值筛选；相同值保留，保存序号、接收时间、Running elapsed、批次/批内顺序和断段。
  Added SignalHistory.kt, SignalWriter.kt and RecordingPanel.kt, integrated with acquisition/session ownership, storage, SQLite and History. ECG retains nanosecond timestamps and signed microvolts in version-1 little-endian BLOBs (12 bytes per point), chunked at sensor-second/segment boundaries without averaging or decimation. RR validity is independent of HR validity; equal intervals and sequence/arrival/elapsed/batch/segment metadata are retained.
- 应用级队列按编码数据及元数据估算限制为 1 MiB，包含正在写入的批次；约一秒检查点，RR 每批最多 128 条，IO 事务每组最多五批。超限/写入失败暂停采集，保留待重试批次；界面提供 Retry save 和确认后 Discard。成功重试后仍需手动 Continue。停止时提交尾块，排空队列后最终事务才显示 Saved。1 MiB 是队列计费上限，不是整个进程堆内存承诺。
  The application queue uses a 1 MiB encoded-data/metadata budget including in-flight writes. Checkpoints occur about once per second, RR batches contain at most 128 intervals and IO transactions group at most five batches. Capacity/write failures pause recording and retain pending batches for explicit retry or confirmed discard. Retry does not resume acquisition. Stop flushes tails and drains writes before finalization/Saved. The queue budget is not a total process-heap limit.
- SQLite 升级为 v2：一次保留数据的 v1→v2 升级保留已有摘要/HR/Motion；新增 ECG 段/块、RR、暂存/完成/异常归档状态及独立 collectionIncomplete。增量 HR/Motion 与摘要检查点同事务；欢迎页或直接进入 Session 时由唯一存储持有者初始化归档。正常暂停不归档；异常重启只恢复已提交内容，以检查点时长为准，不恢复旧会话采集。删除活动连同全部曲线级联删除。
  SQLite v2 preserves existing summaries/HR/Motion through the authorized v1→v2 migration. It adds ECG segments/chunks, RR records, staging/final/archive states and collectionIncomplete. Incremental HR/Motion and summary checkpoints share transactions. The single storage owner initializes archiving from welcome or direct Session entry. Normal pauses remain open; interrupted sessions retain only committed checkpoints and never resume acquisition. Activity deletion cascades to all chart data.
- Activity Summary 已启用 ECG/RR：ECG 五秒，RR 六十条且横轴为记录序号、纵轴 ms；滑块松手查询，Previous/Next 切窗，旧查询取消后不能覆盖新窗口。仅加载所需原始窗口。HR/Cadence 保留整场曲线。异常归档四种图表中央使用 bodySmall 显示 Data collection incomplete，保留部分曲线，覆盖无数据文案；查询错误及 Retry 优先，无列表/日期 Incomplete 标签。
  Activity Summary enables five-second ECG and sixty-record RR windows (record-number axis, milliseconds), querying on slider release or Previous/Next and rejecting obsolete results. Raw queries load only the requested window. HR/Cadence retain whole-session charts. Interrupted archives show the centered bodySmall message over available curves on all four tabs, replacing empty text; query errors/retry take priority. List/date Incomplete labels remain removed.
- 视觉检查发现原紧凑布局加入窗口控制后会压缩绘图区，已修正：选择 ECG/RR 时详情纵向滚动，图表卡使用 400 dp × fontScale；HR/Cadence 保留原紧凑规则。浅/深主题、实际系统字号 1.0/2.0 的受控波形、中央提示及可滚动到达的按钮均已检查。截图中数据均为专用模拟器合成测试数据，不是 H10 实测。
  Visual inspection found the old compact layout squeezed plots after adding window controls. ECG/RR now use scrolling detail with a 400 dp × fontScale chart card; HR/Cadence retain compact rules. Controlled plots, centered text and reachable controls were checked in light/dark themes at actual system font scales 1.0/2.0. All captured data is synthetic emulator test data, not H10 measurements.
- 验证：244 项单元测试通过（新增 7 项，0 failures/errors/skips）；分轮 39 项不同 SQLite/Compose/生命周期/进程恢复检查全部通过，另有字号 2.0 的两项重复检查。覆盖无损编码、分段/尾块、相同 RR、队列失败/容量/重试、事务回滚、升级保留、删除、正常/异常记录区别、窗口翻页、空态优先级、图表高度及既有自动暂停/History 回归。初次旧删除文案断言失败，更新为包含全部曲线后的最终复跑通过。
  All 244 unit tests pass (seven added; zero failures/errors/skips). Across runs, 39 distinct SQLite/Compose/lifecycle/process-recovery checks pass, with two additional font-2.0 repeats. Coverage includes lossless encoding, segments/tails, equal RR values, queue failure/capacity/retry, transaction rollback, preserving migration, deletion, normal versus interrupted records, paging, message priority, plot height and existing automatic-pause/History behavior. One stale deletion-copy assertion failed initially and passed after updating it to cover all charts.
- 独立 emulator-5590 上先写入 20 秒未最终提交的合成记录，再 adb force-stop 并冷启动欢迎页；应用自行归档，验证 Duration 仍为 20 秒、RR 180 条、五秒 ECG 窗口约 650 点且记录唯一。SignalRecoveryTest 仅通过显式 recoveryPhase=seed/verify 启用两阶段检查，默认测试不向应用主库注入该夹具。
  On isolated emulator-5590, seeded a synthetic 20-second unfinished record, force-stopped the process and cold-launched welcome. The app archived it automatically; verification retained the 20-second duration, 180 RR records, approximately 650 ECG points per five-second window and one activity. SignalRecoveryTest requires explicit recoveryPhase=seed/verify arguments; ordinary test runs do not inject this fixture into the main database.
- 四小时合成规模检查：1,872,000 个 ECG 点、14,400 块、28,800 条 RR；批量构建/写入约 23.861 秒，末尾窗口查询约 9 ms，数据库 31,657,984 字节，采样到的进程 Java 堆峰值 37,753,536 字节。该结果是单次受控模拟器测量，写入时间包含测试数据构造，堆值是整个测试进程；不是持续四小时真机功耗/蓝牙吞吐/帧率承诺。
  The four-hour synthetic scale check stores 1,872,000 ECG samples in 14,400 chunks plus 28,800 RR intervals. Construction/batch writing took about 23.861 seconds; an end-window query took about 9 ms. Database size was 31,657,984 bytes and sampled process Java heap peaked at 37,753,536 bytes. This single controlled emulator measurement includes fixture construction and whole-process heap, not a four-hour hardware power/throughput/frame-rate guarantee.
- debug/测试 APK 构建通过；lint 为 0 errors、23 warnings。证据：build/step90a-validation/final-build2.txt、recovery-test-build.txt、signals-instrumentation2.txt、regression.txt（保留初次失败）、final-regression.txt、layout-regression.txt、font2-regression.txt、process-seed-final.txt、process-recovered-final.txt、performance.txt 及标注为合成数据的截图；单元 XML 和 lint 报告在 app/build。ecg-light-1.png 是修正前截图，以 ecg-light-1-fixed.png 为准。
  Debug/test APK builds pass; lint reports zero errors and 23 warnings. Evidence is under build/step90a-validation in the named build, instrumentation, recovery, performance and synthetic-image artifacts; unit XML/lint reports remain under app/build. The initial ECG capture is superseded by ecg-light-1-fixed.png.
- Samsung/H10 未安装或操作：真实三流并发、接触失效/重连、长期存储与滚动性能、实际磁盘空间不足以及采集途中系统杀进程仍待真机验收。受控异常注入及 force-stop 测试不等于这些硬件场景已通过。两对中英文文档同步，无 commit/push。
  No Samsung/H10 installation or interaction occurred. Real simultaneous streams, contact loss/reconnection, long-run storage/scrolling, actual disk exhaustion and system termination during hardware acquisition remain pending. Fault injection and force-stop do not establish those hardware outcomes. Both bilingual documentation pairs are synchronized; no commit/push.

### 5.46.4 Summary 四图固定布局修正 / Fixed Summary chart layout（2026-10-04）

- 用户反馈切换 ECG/RR 后上方卡片重新排列，要求四图大小和位置一致。本节覆盖 5.46.3 按图表类型切换整页布局的决定；仅修正展示，不改变持久化、查询窗口和采集规则。
  The user reported summary-card reflow when selecting ECG/RR and requested equal chart size/position. This supersedes the chart-dependent whole-page layout in 5.46.3; persistence, query windows and acquisition rules stay unchanged.
- 移除 HistoryDetailLayout 的 windowedChart 状态及回调；四种图共用原 HR/Cadence 的 compact 条件、相同图表权重和位置。大字号/小屏仍使用同一纵向滚动规则，选择图表不再改变卡片布局。Summary 固定纵轴留白为 48 dp × fontScale，使 Canvas 横向位置及宽度也不因单位/刻度宽度改变；Session 实时图沿用原默认逻辑。
  Removed chart-dependent page state/callbacks. All four charts share the existing HR/Cadence compact conditions, chart weight and position. Large text/small screens keep the same scrolling rules regardless of selection. A shared 48 dp × fontScale axis gutter also stabilizes the Summary plot's horizontal position/width; live Session defaults are preserved.
- ECG/RR 在图表单位行右侧显示 Browse；点击打开窗口选择弹窗，保留滑块松手查询、Previous/Next 和五秒/六十条窗口，Done 返回图表。窗口控制不再占用图表底部空间，也不挤压绘图区。异常归档中央提示保留。
  ECG/RR provide Browse on the existing unit row. Its dialog retains slider-release querying, Previous/Next and five-second/sixty-record windows, with Done returning to the plot. Controls consume no plot/footer space and no longer change layout. Centered interrupted-record messages remain.
- debug/测试 APK 与 lint 通过（0 errors、23 warnings）；本轮 7 项 SignalChartTest/HistoryPresentationTest 通过，固定布局检查在深色默认字号及实际系统字号 2.0 分别复跑通过。断言逐图比较六个摘要标签、图表卡及 Canvas 的完整坐标/尺寸，覆盖 Browse 打开关闭、翻页及既有 History 展示。修正了大字号测试只滚动到第一排标签的定位问题，截图改为系统截图以避免 Compose 截图线程冲突；相关初次失败日志保留。
  Debug/test APK builds and lint pass (zero errors, 23 warnings). Seven SignalChartTest/HistoryPresentationTest checks pass, with fixed-layout repeats in dark mode at normal and actual 2.0 font scale. Assertions compare six summary labels, chart-card and Canvas bounds across tabs, plus Browse open/close, paging and existing History presentation. Fixed test scrolling to expose the full card at large font and used system screenshots to avoid a Compose capture-thread conflict; initial failure logs remain.
- 证据在 build/step90a-validation/fixed-layout-build.txt、fixed-layout-test-build.txt、fixed-layout-tests.txt、fixed-layout-dark.txt、fixed-layout-font2-verified.txt 与 fixed-layout-*-captures。受控截图为合成数据；本轮未重跑全部单元测试、未操作 Samsung/H10。两对中英文文档同步，无 commit/push。
  Evidence is under build/step90a-validation in fixed-layout build/test/theme/font logs and capture folders. Controlled images use synthetic data. The full unit suite was not rerun and Samsung/H10 was not operated. Both bilingual documentation pairs are synchronized; no commit/push.

### 5.46.5 ECG 零参考线与纵轴标注 / ECG zero reference and axis label（2026-10-04）

- 用户要求：无数据时隐藏，有数据时用浅色细线显示，在左侧纵轴加 0。已修改共用 LiveChartPlot，适用于 Session 与 Summary 的 ECG：无有效点时不绘制零参考线，也跳过重合的零网格线；有有效点时用主题 outline 的 25% 透明度、0.5 dp 线宽绘制，并在纵轴对应位置显示 0。保留无数据 -- 和原空态文案。
  The user requested hiding the zero reference without data, showing a light thin line with data and adding a left-axis zero label. Shared LiveChartPlot now applies this to Session and Summary ECG: no zero reference or coincident zero grid line without valid points; otherwise use theme outline at 25% opacity and 0.5 dp width with a zero axis label. Existing empty placeholders/messages remain.
- 零刻度只显示一次，移除与 0 文字垂直距离不足的相邻刻度文字，避免重叠；原始数据、纵轴范围、窗口浏览与四图固定布局规则不变。HR/Cadence/RR 零线沿用原逻辑。
  Show the zero label once and omit neighboring tick labels that would overlap it. Raw data, scale bounds, browsing and fixed chart layout remain unchanged. HR/Cadence/RR zero-line behavior is preserved.
- debug 构建与 lint 通过（0 errors、23 warnings），日志 build/step90a-validation/ecg-zero-build.txt。本轮未新增/运行测试套件或模拟器/真机视觉检查；不将构建成功当作真机验证。两对中英文文档同步，无 commit/push。
  Debug build and lint pass (zero errors, 23 warnings), logged in build/step90a-validation/ecg-zero-build.txt. No test suites or emulator/hardware visual checks were added/run this turn. Both bilingual documentation pairs are synchronized; no commit/push.

### 5.46.6 ECG/RR 图内左右滑动 / In-chart ECG/RR swiping（2026-10-04）

- 用户要求删除 Browse，只用左右滑动浏览全程，单画面保持目前设置。本节覆盖 5.46.4 的 Browse 弹窗及 5.46.1 的滑块/按钮交互。已删除按钮、弹窗、滑块、Previous/Next/Done 和无用的弹窗状态/绘图标题动作入口。
  The user requested removing Browse and using horizontal swipes while preserving the current viewport. This supersedes Browse in 5.46.4 and the slider/button interaction in 5.46.1. Removed the button, dialog, slider, Previous/Next/Done and unused dialog/header-action state.
- ECG/RR 绘图区左滑后移一个窗口，右滑前移一个窗口；ECG 步进五秒、单屏最多五秒，RR 步进六十条、单屏最多六十条。首尾夹紧；末尾不足完整步进时停在最后一个窗口，允许与前一窗口重叠。超过绘图区宽度 10% 的水平拖动在松手后切窗，取消或短拖动不切窗；不增加惯性或缩放。HR/Cadence 整场展示不变。
  Swipe left for the next window and right for the previous: five seconds for ECG and sixty records for RR. Clamp at both ends; the last window may overlap the previous one when less than a full step remains. A horizontal drag exceeding 10% of plot width changes the window on release; cancelled/short drags do not. No fling or zoom; HR/Cadence retain whole-session display.
- 手势仅放在绘图区，垂直手势交给页面滚动；窗口查询继续在 IO 执行并取消过期结果。保持四图固定大小/位置、五秒/六十条窗口、ECG 浅色零线与 0 标注、异常归档中央提示。无障碍提供前后窗口动作，无额外可见控件。
  Gestures apply only to the plot and vertical gestures remain available to page scrolling. IO window queries and stale-result cancellation remain. Preserve fixed layout, viewport sizes, ECG zero styling/label and interrupted-record text. Accessibility exposes previous/next-window actions without visible controls.
- debug/测试 APK 与 lint 通过（0 errors、23 warnings）。既有 SignalChartTest 的 3 项检查更新后在默认字号浅色通过，并在系统字号 2.0 深色全部复跑通过：真实注入左右滑动、RR/ECG 首尾与窗口步进、无 Browse/按钮、固定布局、异常提示；大字号额外确认图内上滑使整页滚动但不改变窗口。未新增测试类，未重跑全量单元/其他仪器套件，未操作 Samsung/H10。
  Debug/test APK builds and lint pass (zero errors, 23 warnings). The three updated SignalChartTest checks pass at normal font/light theme and all repeat at actual font 2.0/dark theme, covering injected swipes, boundaries/steps, removed controls, fixed layout and interrupted messages. The enlarged-font check also verifies vertical plot swipes scroll the page without changing the window. No new test class, full-suite rerun or Samsung/H10 interaction.
- 证据：build/step90a-validation/swipe-build-final.txt、swipe-test-build.txt、swipe-tests.txt、swipe-font2-tests.txt、swipe-captures（合成夹具）。两对中英文文档同步；真机手感与实际信号浏览仍待验证，无 commit/push。
  Evidence: build/step90a-validation/swipe-build-final.txt, swipe-test-build.txt, swipe-tests.txt, swipe-font2-tests.txt and swipe-captures (synthetic fixtures). Both bilingual documentation pairs are synchronized. Hardware gesture feel and real-signal browsing remain pending; no commit/push.

## 5.48 步骤 9.0b：Activity Summary 高层指标 / Activity Summary metrics（2026-10-04）

### 5.48.1 已确定规则 / Agreed rules

- 本节覆盖旧的“四项指标仅占位、不计算、不存储”规则。保留 Intensity、Cardio Load、Session Strain；HR Recovery 改为 Cadence Stability。沿用 9.0a 四图固定布局、ECG 五秒/RR 六十条及左右滑动，以及 5.47 自动暂停/手动 Continue；不增加 Trend 页面、原始 ACC 存储或额外实时传感器处理。
  This supersedes the four-placeholder/no-computation/no-storage rule. Retain Intensity, Cardio Load and Session Strain; replace HR Recovery with Cadence Stability. Preserve 9.0a fixed chart layout, five-second ECG/sixty-record RR swiping and 5.47 automatic pause/manual Continue. No Trend page, raw ACC persistence or additional live sensor processing.

| 指标 / Metric | 算法与展示 / Algorithm and display |
|---|---|
| Intensity | 五区间有效时长 d1…d5（毫秒），D=Σdi，W=Σ(i×di)。D>0 时 W/D，否则 NULL；显示一位小数，例如 2.8 / 5。Time-weighted zone score W/D, or NULL without classified time; one decimal. |
| Cardio Load | D>0 时 W/60000，单位 AU，否则 NULL。Zone-weighted minutes in arbitrary units; no rounding before persistence. |
| Cadence Stability | 有效步频 c 的总体标准差 σ=sqrt(Σ(c−mean(c))²/n)，CV=100×σ/mean(c)。至少 30 个有效秒记录才输出，显示 CV 7.4%；不足为 NULL。Population CV over at least 30 selected per-second observations. |
| Session Strain | 用户主动选择整数 RPE 0…10 × durationMs/60000，单位 AU。未评分或有效时长≤0 为 NULL；正时长且 RPE=0 为有效 0。User-selected RPE multiplied by active minutes; no default rating. |

- 心率沿用固定区间 <110、110–124、125–139、140–154、≥155 bpm，权重依次 1…5。只使用既有区间累计值；暂停、Unclassified 与缺失时长不补值，不按整场时长扩大负荷，也不重新分类 HR。Intensity 是本 App 的区间均值，Cardio Load 是简化区间负荷，不宣称 Polar 官方指标、个体最大心率百分比或 TRIMP。
  Reuse the existing fixed HR zones and accumulated durations. Exclude pauses/unclassified/missing time without imputation or extrapolation. These are app-specific scores, not Polar metrics, individualized maximum-HR percentages or TRIMP.
- 步频使用结束冻结的每秒末条 motion_points 对应数据；限同一 sessionId 且 elapsedMs 在 0…durationMs。先按 secondBucket 保留最后一条，再排除 null、非有限、≤0 的 cadence；末条无效时不能回退到同秒较早的有效值。breakBefore 只表示断段，不排除其有效新点。暂停没有采集点；缺口、停止的零值不插补。均值与方差均使用同一有效集合，不复用旧 meanCadence；用 Welford 总体方差，分母 n，不用 n−1。
  Use the frozen final-per-second motion observations from the same session and final active-time bounds. Deduplicate before filtering invalid/nonpositive cadence; do not revive an earlier value when a bucket ends missing. A valid breakBefore observation remains valid. Use the selected population for both mean and variance, with no padding or interpolation.
- CV 越低表示本场有效步频越稳定；恒定步频显示 CV 0.0%，不得用 100−CV、不得夹紧到 100%。走跑切换和间歇会增大 CV。30 点是本 App 的显示门槛，不是医学阈值；该指标是每秒步频变化，不是逐步步态变异或健康评分。
  Lower CV means steadier recorded cadence; constant cadence is valid zero. Never invert or cap CV at 100%. Intervals/transitions affect it. Thirty observations are a product threshold, not a medical cutoff or stride-level gait/health assessment.
- 正常结束并保存时计算前三项；等信号写入 drain，再在 Dispatchers.Default 计算，和最终摘要一起在数据库 IO 事务保存。History 直接读已存结果，不在重组、打开详情或切换图表时扫描重算。单独缺失 HR/ACC 只影响相应指标；collectionIncomplete 的进程异常归档全部为 NULL 且不能评分。
  Calculate the first three metrics during final saving, after signal writes drain, on Dispatchers.Default; persist with the final summary through database IO. History reads stored results. A missing stream affects only its related metric; process-interrupted collectionIncomplete archives have no metrics or rating.
- 点击 Session Strain 打开英文 Rate session effort 弹窗；新记录没有默认选项，选择前不能保存。允许稍后评分/编辑、取消不写入；保存 sessionRpe、ratedAt（手机 UTC epoch 毫秒）、sessionStrain 和算法版本。使用数据库已存 active duration，保留毫秒精度再换算分钟，排除暂停。保存失败保留旧结果与选中值，显示 Retry save；成功提交后再刷新详情。
  Open an English rating dialog from Session Strain. Unrated records start unselected; permit later edits, and cancel without writing. Store RPE, UTC epoch-millisecond rating time, strain and algorithm version using the persisted active duration. Retain the old result/selection on failure and refresh only after commit.
- Session Strain 是单场主观负荷，不是 Polar 七日 Strain；不得从 HR、Intensity、Cardio Load 或步频猜测 RPE。帮助说明提供各指标定义与局限；数值一位小数，单位 AU/CV% 明确，缺失显示 --，零值显示 0.0。默认三卡加整宽 Strain，大字号纵向排布并滚动。
  Session Strain is perceived single-session load, not Polar seven-day Strain. Never infer RPE from sensors. Explain the definitions/limitations; display one decimal and explicit units, missing values as -- and measured zeros as 0.0. Preserve compact cards with stacked scrolling at large font.
- SQLite 升级到 v3，在 sessions 添加 8 个可空字段：intensity、cardioLoad、cadenceCvPercent、cadencePointCount、metricsVersion、sessionRpe、ratedAt、sessionStrain；算法版本 1。v2→v3 仅添加列，v1→v3 沿用已有信号表升级后加列，不删库。旧记录新字段 NULL，不自动回算前三项；完整旧记录可主动补 RPE 并仅计算 Strain。暂存记录和异常归档不能评分；重复保存已提交 UUID 不覆盖后来评分；删除沿用原事务与级联。
  SQLite v3 adds eight nullable session columns and algorithm version 1. Preserve v2 data and the existing v1 signal-table upgrade; never reset the database. Do not backfill old computed metrics. Complete older records may receive a rating/strain only. Reject staging/archive ratings, preserve later ratings on duplicate final saves and retain transactional cascade deletion.

### 5.48.2 实施与验证 / Implementation and validation

- 新增 ActivityMetrics.kt（纯计算/结果）、ActivityMetricCards.kt（指标及说明）、SessionRatingDialog.kt（评分）；接入 SessionRecord、SessionStorage、SessionDatabase、SessionSummaryPanel、HistoryPanel 与 HistoryDetailLayout。没有更改 HR/ACC/ECG/RR SDK 订阅、步伐检测或实时计时逻辑。
  Added the metrics calculator/result, metric cards/help and rating dialog; integrated existing summary, storage and History owners without changing SDK subscriptions, step detection or live timing.
- 数学示例：Z2=10 分钟、Z3=15 分钟、Z4=5 分钟，则 W=85 加权分钟，Intensity=85/30≈2.8333，Cardio Load=85 AU。30 个 100/110/120 循环点的 CV≈7.422696%；30 分钟且 RPE=6 时 Strain=180 AU。持久化完整精度，仅 UI 四舍五入。
  Examples: ten Z2 minutes, fifteen Z3 and five Z4 yield 85/30 intensity and 85 AU load. Thirty repeating 100/110/120 cadence records yield CV about 7.422696%; RPE 6 over thirty active minutes yields 180 AU. Round only for display.
- 全部 255 项单元测试通过（新增 11 项）。debug/测试 APK 构建通过，lint 0 errors、23 warnings。模拟器 emulator-5590 分轮共 42 项不同检查通过：新增数据库 5、指标 UI 4，原 SessionDatabase 6、SignalDatabase 6、HistoryDetail 9、HistoryPresentation 4、固定图表布局 1、AutoPauseLifecycle 7。
  All 255 unit tests pass, including eleven new tests. Debug/test APK builds and lint pass (zero errors, 23 warnings). Across runs, 42 distinct emulator checks pass: five new database and four UI checks, six SessionDatabase, six SignalDatabase, nine HistoryDetail, four HistoryPresentation, one fixed-chart-layout and seven AutoPauseLifecycle checks.
- 新增 UI 四项分别在浅/深主题及实际系统字号 1.0/2.0 复查；验证说明、无默认 RPE、取消、保存、改为 0、失败重试及异常归档禁用。固定四图布局在浅色默认及深色 2.0 通过；数据库验证升级保留、重开读取、旧记录 NULL、评分事务回滚、重复保存与删除。既有四小时合成信号写入测试通过，不等于真实 H10 性能。
  Repeat the four UI checks in all four theme/font combinations. Verify help, unselected ratings, cancellation, saving/editing zero, retry and archive restrictions. Fixed charts pass normal/light and 2.0/dark checks. Database coverage includes preserving migration, reopen, old nulls, rollback, idempotency and deletion. Existing four-hour synthetic persistence coverage passes; real H10 performance remains unverified.
- 首轮 UI 检查发现 Strain 卡片高度被撑大和 0 节点选择歧义，分别修正紧凑卡片高度与测试定位后复跑通过。自动暂停旧断言因新增算法元数据失败，改为检查 version=1/count=0/缺失值，并比较其余完整快照，7 项复跑通过。截图测试补充主题 Surface、安全边距和系统弹窗动画等待；早期失败/过渡截图保留为过程证据。
  Initial UI failures exposed expanded compact Strain height and an ambiguous zero test selector; both were corrected and rerun. Updated the lifecycle assertion to check new metadata while comparing the unchanged snapshot; all seven pass. Screenshot tests now include the theme surface, safe insets and settled system-dialog transitions; retain initial failures/transient captures as development evidence.
- 证据：build/step90b-validation/ 中的 build-layout-fix.txt、test-final-build.txt、capture-test-build.txt、metrics-light1-final.txt、metrics-dark2.txt、metrics-light2.txt、regression-dark1.txt、lifecycle-final.txt、visual-*-final.txt、passed-tests.txt 和 captures-final/files/metrics-*.png。42 为去重后的通过项，主题/字号复跑不重复计数；旧混合失败日志不能单独称为整轮通过。
  Evidence is in build/step90b-validation. The distinct-test list deduplicates theme/font repetitions; mixed initial logs are not all-pass runs. Captures use controlled synthetic fixtures and verify the new metric cards/dialog, not real signals.
- 两对 AGENTS.md/prompt.md 同步；代码与上述软件检查已完成。没有操作连接的 Samsung 或 H10；真实运动准确性、设备端评分体验和实际采集端到端仍 pending。没有 commit/push，也未将 Trend 或后续步骤提前实施。
  Both documentation pairs are synchronized. Code and the listed software checks are complete; Samsung/H10 runtime, real-activity accuracy and physical-device end-to-end behavior remain pending. No commit/push or later-step implementation.

## 9.0b 中英文实施 Prompt / Bilingual implementation prompt（2026-10-04）

### 中文 Prompt

请只实施 9.0b：将 Activity Summary 的四张占位卡接入真实计算和 SQLite 持久化，保留 Intensity、Cardio Load、Session Strain，将 HR Recovery 改为 Cadence Stability。实施前读取当前 AGENTS.md 和相关源码，遵循本文件 5.48 的完整算法、缺失值、归档、升级和验证规则；使用当前 Kotlin/Compose 架构和最小必要代码，不新增依赖，不修改 SDK 采集、步伐检测、原始 ACC 保存方式、9.0a 图表浏览及 5.47 生命周期。

1. 增加可测试的纯算法。用五个现有 HR 区间的有效时长计算 Intensity=Σ(i×di)/Σdi、Cardio Load=Σ(i×di)/60000；无有效区间时长为 null，不补缺失时间。
2. 从同场结束快照的每秒最终 cadence 取有限正值，先按秒去重后过滤，不排除有效 breakBefore 点，不插值；n≥30 才计算总体 CV=100×sqrt(Σ(c−mean)²/n)/mean，保存有效点数。不得用摘要 meanCadence、100−CV 或封顶；输出含义为步频稳定性而非健康等级。
3. 最终信号 drain 后在后台计算，与摘要一起事务保存；History 只读取持久化结果。SQLite v3 保留原记录并增加八个可空列和版本1，旧记录不自动回算，进程异常归档保持 null。
4. 点击 Session Strain 可选填整数 RPE 0–10，无默认值；Strain=RPE×已存活动分钟数，保存评分时间，允许后改、取消及失败重试。零评分有效；无评分/非正时长不伪造0。事务失败不刷新旧值，未提交/异常归档禁止评分，重复最终保存不覆盖评分。
5. 全英文 UI，前三卡及整宽 Strain 沿用布局、深浅主题和大字号滚动；各数值一位小数，缺失为 --。提供定义说明，明确 Cardio Load 是 App 简化估算，Session Strain 不是 Polar 七日指标；保留四图固定尺寸及 ECG/RR 图内左右滑动。
6. 补充数学边界、29/30点、过滤与重复秒、缺失/有效零、RPE0/10、SQLite保留升级与重开、事务失败重试和历史/生命周期回归。构建 debug/测试 APK、运行单元测试/lint以及可用模拟器检查；区分合成数据、真实数据库、模拟器视觉和 Samsung/H10。同步两对中英文文档，记录失败与修复，不 commit/push。

### English Prompt

Implement only step 9.0b: replace the four Activity Summary placeholders with computed, persisted metrics. Keep Intensity, Cardio Load and Session Strain; rename HR Recovery to Cadence Stability. Read current AGENTS.md and source first and follow all formulas, missing-data, archive, migration and verification rules in section 5.48. Use the existing Kotlin/Compose architecture and minimal necessary changes, without new dependencies or changes to SDK acquisition, step detection, raw ACC storage, 9.0a browsing or 5.47 lifecycle behavior.

1. Add a pure testable calculator. From existing five-zone durations in milliseconds, compute Intensity=Σ(i×di)/Σdi and Cardio Load=Σ(i×di)/60000. Return null without classified duration; do not estimate missing time.
2. Use the frozen same-session final-per-second cadence observations. Deduplicate seconds before filtering finite positive values; retain valid breakBefore points and do not interpolate. Require at least thirty observations for population CV=100×sqrt(Σ(c−mean)²/n)/mean and persist the count. Do not use summary meanCadence, invert CV or cap it; describe cadence variability without health grades.
3. After signal writes drain, compute off the main thread and persist with the final summary transaction. History reads saved results. Upgrade to SQLite v3 preserving existing data and adding eight nullable columns with algorithm version 1. Do not backfill old metrics; interrupted archives remain null.
4. Open an optional 0–10 integer RPE dialog from Session Strain with no default. Multiply RPE by persisted active minutes and save the rating timestamp. Support later edits, cancellation and retry. Zero is valid; an unset rating or nonpositive duration has null strain. Failed writes retain the old summary; reject staging/archive ratings and preserve ratings on duplicate final saves.
5. Use English UI, one decimal, explicit units and -- for missing values. Preserve compact cards, light/dark themes, large-font scrolling, fixed chart geometry and in-plot ECG/RR swiping. Explain that Cardio Load is an app estimate and Session Strain is not Polar seven-day Strain.
6. Test numerical boundaries, 29/30 observations, filtering/deduplication, null/zero, RPE limits, preserving migration/reopen, transactional failures/retry and History/lifecycle regression. Build debug/test APKs, run unit tests/lint and available emulator checks. Distinguish synthetic fixtures, actual SQLite, emulator visuals and Samsung/H10 validation. Synchronize both bilingual documentation pairs, record failures/fixes and do not commit or push.

执行结果 / Outcome: 见本文件 5.48.2；软件实施与列明检查已完成，真机 pending。See section 5.48.2 for completed software work and checks; hardware remains pending.


### 5.48.3 9.0b 修订：自动综合 Session Strain / Automatic composite Session Strain（2026-10-04）

本节按用户“参考步频、运动总时长且不获取用户输入”的确认实施，覆盖 5.48.1/5.48.2 及原 9.0b prompt 中所有 RPE 算法、评分界面、评分字段与旧 Strain 不回算要求。原记录保留为历史证据；Intensity、Cardio Load 和 Cadence Stability 的算法不变。
This user-authorized revision supersedes the RPE formula, rating UI/fields and no-Strain-backfill rules in 5.48.1/5.48.2 and the original 9.0b prompt. Earlier entries remain historical. Intensity, Cardio Load and Cadence Stability formulas are unchanged.

- 设 ti=zoneDurationsMs[i−1]/60000，T=durationMs/60000，c=meanCadence：
  H=Σ(ti×i²)，i=1…5；C=5×clamp(c/180,0,1)；M=T×C²；Session Strain=0.7×H+0.3×M，单位 AU。使用 Double 保留精度，显示一位小数。70%/30% 和 180 是固定 App 参数，不是医学阈值或推荐步频；指标不等同于 Polar Strain，也不宣称经验证的疲劳测量。
  With zone minutes ti, active minutes T and mean cadence c, compute H=sum(ti×i²), C=5×clamp(c/180,0,1), M=T×C² and Strain=0.7H+0.3M in AU. Persist full precision and display one decimal. The weights and cadence reference are app parameters, not medical thresholds, recommended cadence, Polar Strain or validated fatigue.
- 时长使用已冻结的累计 Running 时间，排除 Pause，不用 endedAt−startedAt。平均步频沿用 Summary 的总步数/活动分钟数，包含静止时间；不再次加入总步数权重，不加入 Cadence Stability 或其他用户参数，不新增 ACC/ECG/RR 实时处理。
  Use persisted active Running duration, excluding pauses, rather than wall-clock end minus start. Existing mean cadence includes stationary time through total steps divided by active minutes. Do not add a second step-count weight, cadence CV, user parameters or additional live processing.
- durationMs≤0、没有已分类 HR 时长、meanCadence 缺失/非有限/负值、ACC 未收到/标记 missing 或 failed、collectionIncomplete 任一成立时，Strain=NULL，UI 显示 --。ACC 有效且平均步频为 0 时 M=0，心率部分仍有效。不因为 ECG/RR 缺失而禁用。HR 缺口只是不贡献 H，不外推或将缺失时长补到完整时长，因此可能低估负荷。
  Return null for nonpositive duration, no classified HR time, invalid/missing mean cadence, absent/missing/failed ACC or collectionIncomplete archives. Valid zero cadence contributes zero motion but retains the heart component. Missing ECG/RR alone does not disqualify the score. Do not extrapolate missing HR time; partial HR can underestimate load.
- 计算仍在最终信号 drain 后的 Dispatchers.Default 执行，摘要与历史一起事务保存；History 只读已保存结果。删除 SessionRatingDialog.kt、评分状态/回调、rateSession、sessionRpe/ratedAt 模型和数据库字段；Strain 卡片点击仅打开可滚动英文说明，没有输入、默认值、Edit 或 Tap to rate。
  Continue calculating after signal drain on Dispatchers.Default and saving with the final snapshot transaction. History reads saved values. Remove the rating dialog/state/callbacks/database update API and RPE/time fields. The card opens scrollable English help only.
- SQLite 升级为 v4，算法版本为 2。v3 摘要表在 SQLiteOpenHelper 升级事务中重建，去除两个评分字段；只复制摘要，不复制/删除大体积信号表。迁移前在 onConfigure 关闭外键级联，校验 foreign_key_check 后完成事务，再于 onOpen 恢复外键；因此不会因替换父表而删除 HR/步频/ECG/RR 子记录。迁移失败回滚 schema、数据和版本。实现依据 [SQLite ALTER TABLE](https://www.sqlite.org/lang_altertable.html) 与 [Android SQLiteOpenHelper](https://developer.android.com/reference/android/database/sqlite/SQLiteOpenHelper) 的事务与配置顺序。
  SQLite v4/algorithm v2 rebuilds only the v3 session table within the helper's upgrade transaction, removing rating columns while preserving signal/history tables. Configure foreign keys before migration, check integrity and restore enforcement after opening. Roll back schema, data and version on failure; follow the linked SQLite/Android migration guidance.
- v1/v2 保留原有升级路径并添加当前六个指标字段；v1/v2/v3 已提交且非异常归档的旧记录，按当前 Summary 和上述有效性规则重算 Strain，覆盖旧 RPE 分数并记录算法版本2。其他三项及有效步频点数原样保留，不补算；旧记录其他指标可仍为 NULL。未提交/异常归档 Strain 为 NULL，不能混用旧分数。相同 UUID 的重复最终保存仍幂等。
  Preserve existing v1/v2 upgrade paths. Recompute only Strain for committed non-archive legacy records, replacing RPE scores using saved summaries and recording version 2. Preserve the other three metrics/count, including existing nulls. Staging/archive Strain is null; duplicate final saves remain idempotent.
- 示例：全程 Z3，30分钟/90步每分钟→245.25 AU，30分钟/120步每分钟→289 AU，60分钟/120步每分钟→578 AU。30分钟且 Z2/Z3/Z4 为10/15/5分钟、平均120步每分钟→278.5 AU。相同全程Z3例子的180与240步每分钟均为414 AU（步频部分封顶），0步每分钟为189 AU。
  Examples: all-Z3 sessions yield 245.25 AU at 30 minutes/90 cadence, 289 at 30/120 and 578 at 60/120. Ten/fifteen/five Z2/Z3/Z4 minutes and mean cadence 120 yield 278.5 AU. Cadence 180 and 240 both yield 414 AU for the thirty-minute Z3 example; zero cadence yields 189 AU.

### 5.48.4 修订实施 Prompt / Revision implementation prompt

中文：只修订9.0b的Session Strain。按5.48.3，以已存心率区间平方加权分钟、整场平均步频及排除暂停的活动时长，计算70%心率+30%步频负荷。删除所有RPE输入及保存路径，卡片点击仅显示可滚动算法说明。无有效HR、ACC不完整或异常归档等情况按规则显示--。升级SQLite至v4、算法至2，安全删除评分字段、保留全部历史与信号，在迁移时仅重算旧Strain，不改变其他指标。补充公式、边界、缺失、迁移失败回滚、旧分数替换、信号保留与级联删除测试，回归History、自动暂停及ECG/RR固定窗口与滑动，核对深浅主题/字号1.0和2.0。同步两对文档，区分模拟数据、模拟器和真机结果，不commit/push，不实施后续步骤。

English: Revise only Session Strain in 9.0b. Follow 5.48.3 to combine 70% zone-square-weighted heart minutes and 30% cadence load from the saved mean cadence and pause-excluded active duration. Remove all RPE input/persistence paths; tapping the card opens scrollable help only. Apply the specified null rules for absent HR, incomplete ACC and archives. Upgrade to SQLite v4/algorithm v2, remove rating columns safely, preserve all historical/signal data and recalculate only legacy Strain. Test formulas, boundaries, missing data, migration rollback, replacement of old scores, signal preservation and cascades; regress History, automatic pause and fixed ECG/RR swiping. Check light/dark and font 1.0/2.0, synchronize both documentation pairs, distinguish fixtures/emulator/hardware and do not commit, push or advance later steps.



### 5.48.5 修订验证结果 / Revision validation results（2026-10-04）

- 本轮修改并移除旧 RPE 实现；ActivityMetricsTest 由11项调整为16项，覆盖245.25/289/578示例、180归一化封顶、有效静止、非有限与缺失输入、ACC质量、异常归档、非ACC流缺失、HR不外推、毫秒精度、排除暂停的活动时长及不重复累计步数。全量260项单元测试通过，debug/测试APK构建成功；lint为0 errors、23 warnings。
  Replaced the RPE implementation; the metrics unit class now has sixteen tests, previously eleven, covering the agreed examples, cadence cap, stationary periods, invalid/missing data, ACC quality, archives, unrelated streams, no HR extrapolation, fractional minutes, active-time boundaries and no duplicate step weighting. All 260 unit tests pass, with successful debug/test APK builds and lint (zero errors, 23 warnings).
- 14项第一轮检查通过：修订后的ActivityMetricsDatabaseTest 6项、ActivityMetricsUiTest 4项、v1迁移1项、SignalChartTest 3项。数据库验证实际SQLite新建/重开、v1/v2/v3升级、仅Strain回算、旧RPE字段删除、原摘要与HR/步频/ECG/RR保留、外键恢复及级联删除、升级失败整体回滚后重试、暂存和异常归档NULL、最终保存失败回滚和重试幂等。
  All fourteen initial emulator checks pass: six metrics database, four metrics UI, one v1 migration and three signal-chart checks. Actual SQLite tests cover create/reopen, preserving v1/v2/v3 upgrades, Strain-only recalculation, removal of rating columns, all history/signal tables, restored foreign keys/cascades, migration rollback/retry, staging/archive nulls and final-save failure/retry/idempotency.
- 26项回归通过：SessionDatabase 6、HistoryPresentation 4、HistoryDetail 9、AutoPauseLifecycle 7。与首轮合计40项不同检查，不重复计数；本轮没有重跑四小时信号压力测试或全部仪器套件。
  All twenty-six regression checks pass: six SessionDatabase, four HistoryPresentation, nine HistoryDetail and seven AutoPauseLifecycle. Combined with the initial run, this is forty distinct checks. The four-hour signal stress test and complete instrumentation suite were not rerun.
- 深色/字号2.0重复7项（4指标UI+3图表），深色/字号1.0重复4项UI，浅色/字号2.0重复4项UI，全部通过。新增指标UI共覆盖深浅主题及真实系统字号1.0/2.0；无RPE入口、自动值/缺失值、只读说明、固定四图与左右滑动保持通过。默认浅色整页及深色大字号说明已视觉核对；大字号说明正文可滚动，Close固定可见。截图为受控合成数据。
  All fifteen repeats pass: seven at dark/font2.0, four UI checks at dark/font1.0 and four at light/font2.0. Metric UI covers all four combinations, including no rating input, automatic/null values and read-only help; fixed charts/swiping remain verified. Normal/light full-page and enlarged/dark help captures were visually checked. Large-font help scrolls with a visible Close action. Captures use synthetic fixtures.
- 证据在build/step90b-strain-validation/：build.txt、metrics-light1.txt、regression.txt、metrics-dark2.txt、metrics-dark1.txt、metrics-light2.txt、passed-tests.txt、captures-final/strain-*.png。260单元结果在app/build/test-results/testDebugUnitTest，lint在app/build/reports/lint-results-debug.xml。本轮软件测试首轮即通过。
  Evidence is under build/step90b-strain-validation, with unit XML and lint reports in the standard app/build locations. This revision's software test runs passed on their first attempts.
- 两对AGENTS.md/prompt.md已同步。仅操作为本轮启动的emulator-5590；未安装或操作连接的Samsung/H10。真实活动准确性和设备端端到端体验仍待验证；本轮不commit/push、不推进后续步骤。9.0a/5.47采集与生命周期业务代码未修改。
  Both documentation pairs are synchronized. Only the task-started emulator-5590 was used; the connected Samsung/H10 was not operated or installed to. Real-activity accuracy and physical-device end-to-end behavior remain pending. No commit/push or later steps; 9.0a/5.47 acquisition/lifecycle business code is unchanged.

### 5.48.6 9.0b 百分制展示 / Session Strain score out of 100（2026-10-04）

- 用户确认将Session Strain改为0–100活动负荷评分。本节只覆盖5.48.3—5.48.5的卡片AU展示；底层70%心率+30%步频/时长的原始负荷算法与有效性规则保持不变。
  The user approved a 0–100 activity load score. This supersedes only the card's AU display in 5.48.3–5.48.5; the underlying heart/cadence/duration load and validity rules remain unchanged.
- score = 100 × rawStrain / (rawStrain + 100)。采用100 AU作为固定参考值，对应50分；参数是App展示规则，不是医学阈值。原始负荷越高，分数越高并逐渐趋近100；不使用硬截断将所有高负荷直接归为100。高分表示累计负荷更大，不代表运动表现更好；短活动低分是正常的。
  Score = 100 × rawStrain / (rawStrain + 100). The fixed 100 AU reference maps to fifty points; it is an app parameter, not a medical threshold. The monotonic mapping approaches 100 without early hard saturation. Higher means greater accumulated load, not better performance; short activities usually score lower.
- Session Strain标题保持，显示一位小数和“/ 100”，不显示AU或百分号，不新增好坏等级。缺失仍显示--，有效原始零值显示0.0 / 100。示例：1.8 AU→1.8 / 100（未四舍五入约1.76817）；25→20.0；100→50.0；300→75.0；900→90.0；受控夹具278.5 AU→73.6 / 100。
  Keep the title, display one decimal followed by / 100, and do not label it AU, a percentage or a performance grade. Missing values remain -- and valid zero becomes 0.0 / 100. The listed examples define expected display rounding.
- ActivityMetrics新增只读派生属性sessionStrainScore；ActivityMetricCards使用该属性并更新英文说明，将原始负荷定义和评分换算区分开。SQLite继续只保存sessionStrain原始AU；没有数据迁移、批量重算或评分写回，数据库v4和原始算法版本2不变。历史记录读取时直接换算，点击说明前后数据库快照相同。
  Add the read-only derived sessionStrainScore property and use it in ActivityMetricCards with revised English help distinguishing raw load and display score. SQLite still stores raw AU only. No migration, backfill or score writes; database v4/raw algorithm v2 remain unchanged. Existing saved loads use the mapping when displayed.
- 中文Prompt：只更新9.0b的Session Strain展示，按100×raw/(raw+100)换算为0–100负荷评分，显示一位小数 / 100，保留缺失占位和原始AU存储，明确高分不等于表现好。更新英文说明、数值及只读测试，验证缺失/零值/单调性/参考示例和默认/大字号布局，同步两对文档，不改采集、底层负荷或数据库版本，不commit/push。
  English prompt: Update only the 9.0b Session Strain display to 100×raw/(raw+100), showing one decimal out of 100. Preserve missing placeholders and stored raw AU; explain that higher load is not better performance. Update help and numeric/read-only checks, verify null/zero/monotonicity/reference examples and normal/large-font layout, synchronize both documentation pairs and do not change acquisition, raw-load calculation or database version, commit or push.

### 5.48.7 百分制实施验证 / Score display validation（2026-10-04）

- 新增2项单元测试，覆盖参考换算、保留原始AU、缺失/有效零及递增且不提前封顶。全量262项单元测试通过；debug/测试APK构建通过，lint 0 errors、23 warnings。
  Added two unit tests covering reference mappings, unchanged raw AU, null/valid zero and monotonic scores without early saturation. All 262 unit tests and debug/test builds pass; lint reports zero errors and 23 warnings.
- ActivityMetricsUiTest的4项及SignalChartTest固定布局1项在默认浅色通过，随后在实际字号2.0深色全部复跑通过，合计5项不同检查+5项重复。验证73.6 / 100显示、原始278.5 AU数据库值未改变、缺失占位、无RPE输入、只读说明以及四图切换布局。两种配置的截图已视觉核对；正常字号整页布局保持，大字号说明可滚动且Close可见。使用合成夹具，不是真实活动数据。
  Four metrics UI checks and one fixed-chart-layout check pass in normal/light and repeat in actual font2.0/dark, totaling five distinct checks plus five repeats. Verify score display, unchanged stored raw value, missing placeholders, no RPE input, read-only help and fixed charts. Screenshots were visually inspected; fixtures are synthetic.
- 本轮未改数据库、采集与生命周期代码，未重复上轮迁移/完整History/自动暂停仪器套件。证据：build/step90b-score-validation/build.txt、score-light1.txt、score-dark2.txt、captures-final/strain-score-*.png；单元与lint结果沿用标准app/build报告路径。
  Database/acquisition/lifecycle code is unchanged. Previous migration/full History/automatic-pause instrumentation suites were not rerun. Evidence is in build/step90b-score-validation, with unit/lint reports under standard app/build paths.
- 两对AGENTS.md/prompt.md同步；只操作本轮启动的emulator-5590，Samsung/H10未安装或验证。无commit/push，未推进后续功能。
  Both documentation pairs are synchronized. Only the task-started emulator-5590 was used; Samsung/H10 installation/validation remains pending. No commit/push or later features.

### 5.48.8 9.0b 评分持久化 / Persisting the Session Strain score（2026-10-04）

- 用户要求百分制评分也保存到SQLite。本节覆盖5.48.6—5.48.7“只保存原始AU、读取时即时换算、不迁移”的规则；展示与评分公式不变，Session Strain仍显示一位小数 / 100，缺失显示--。
  The user requested storing the score in SQLite as well. This supersedes the raw-only storage/display-time conversion/no-migration rules in 5.48.6–5.48.7. The formula and one-decimal / 100 display remain unchanged, with -- for missing data.
- ActivityMetrics.sessionStrainScore改为可空的已保存字段。结束冻结后，ActivityMetricsCalculator先计算原始sessionStrain，再计算score=100×raw/(raw+100)；两个值与其他摘要/历史在同一最终保存事务中写入。评分按0–100范围保存（不是0–1），不提前四舍五入；原始AU保留。History直接读取数据库字段，不在重组或打开详情时从原始AU重新计算。
  Make sessionStrainScore a nullable stored field. During finalization calculate raw Strain and its score, then persist both with the summary/history in the same transaction. Store the full-precision 0–100 value, not a 0–1 fraction or rounded display value. Preserve raw AU. History reads the stored score without display-time recalculation.
- SQLite升级到v5，sessions新增sessionStrainScore REAL（可空）。v4→v5仅加列并用已存sessionStrain补算评分，不改原始AU、其他指标、算法版本或信号历史。只有commitState=1且非collectionIncomplete的记录可补算；raw=NULL则score=NULL，raw=0则score=0。未提交/异常归档不补评分。
  SQLite v5 adds nullable sessionStrainScore REAL. Upgrade v4 by adding the column and backfilling from saved raw Strain, without modifying raw load, other metrics, algorithm version or signal history. Only committed non-archive records are eligible. Preserve null versus zero; leave staging/archive scores null.
- v1/v2/v3先沿用既有保留升级和原始Strain修订规则，再生成评分，避免把旧RPE分数当作新负荷。升级事务失败时列、数据和user_version一起回滚，可重试。新建、最终保存、分页/详情查询、重开与重复UUID幂等路径均接入评分字段；未增加第二套评分写入入口。
  Retain the existing preserving migrations/raw-load revision for v1/v2/v3 before generating scores, avoiding conversion of obsolete RPE values. Roll back column/data/version together on upgrade failure. Integrate score serialization and reading into existing creation, final save, list/detail, reopen and idempotent UUID paths.
- 数据库schema版本为5；原始负荷/评分公式没有变化，metricsVersion仍为2。例：原始278.5 AU与评分约73.57992073976222同时保存，UI显示73.6 / 100。无新的传感器、用户输入或采集/生命周期修改。
  Database schema version is 5; unchanged formulas retain metricsVersion 2. For example, raw 278.5 AU and score about 73.57992073976222 are both stored, while the UI shows 73.6 / 100. No additional sensors, user input or acquisition/lifecycle changes.

中文Prompt：只为9.0b补充Session Strain百分制评分持久化。将sessionStrainScore从显示派生属性改为可空存储字段，在会话结束时按既定公式与原始AU一起计算并事务保存。SQLite升级v5，保留数据，从v4已存原始负荷补算旧记录评分；旧v1/v2/v3继续先完成已定义的负荷升级。保留缺失、零值、异常归档与重复保存语义，History只读已存评分。测试实际SQL列与精度、升级回滚、重启恢复、旧版本迁移、界面不重算及最终保存回归；同步两对中英文文档，不commit/push。
English prompt: Add persisted Session Strain scores to 9.0b only. Replace the display-derived property with a nullable stored field, calculate it with raw AU at finalization and save both atomically. Upgrade SQLite to v5 preserving data and backfill v4 scores from saved raw loads; retain the defined prior raw-load migrations for v1/v2/v3. Preserve null/zero/archive/idempotency semantics and make History read saved scores. Verify actual SQL precision, upgrade rollback, reopen, earlier migrations, no UI recalculation and final-save regression. Synchronize both bilingual documentation pairs without commit/push.

### 5.48.9 评分存储验证 / Stored-score validation（2026-10-04）

- 全量262项单元测试、debug/测试APK构建通过，lint 0 errors、23 warnings。调整现有评分单元测试适配显式计算/存储字段，最终冻结测试同时确认原始289 AU和评分约74.293059125964。
  All 262 unit tests and debug/test APK builds pass; lint has zero errors and 23 warnings. Existing score tests now exercise explicit calculation/storage, and finalization asserts both raw AU and its score.
- 默认浅色27项不同仪器检查通过：ActivityMetricsDatabaseTest 8、ActivityMetricsUiTest 5、SignalDatabase v1迁移1、SessionDatabase 6、AutoPauseLifecycle 7。评分数据库测试新增v4→v5补算及失败回滚两项，保留v1/v2/v3升级、信号保留、级联删除、最终保存重试及幂等验证。
  Twenty-seven distinct checks pass in normal/light: eight metrics database, five metrics UI, one v1 signal migration, six SessionDatabase and seven AutoPauseLifecycle. Added v4 score-backfill and rollback/retry tests alongside earlier migration, signal retention, cascade, final-save retry and idempotency coverage.
- 实际SQL列验证原始278.5 AU和评分约73.57992073976222同时保留；重开后精度不变。旧raw为NULL/0/1.8/100/900分别正确补算，原始值与其他摘要/HR/步频保持不变，异常归档不补评分。故障注入确认新增列与user_version回滚，去除故障后可升级成功。
  Actual SQL verifies stored raw AU and full-precision score together, surviving reopen. Legacy null/zero/reference loads map correctly without changing raw/other history values; archives remain unscored. Fault injection verifies column/version rollback and successful retry.
- 新增UI检查通过显式注入12.5评分且保留原始278.5 AU，确认History显示12.5 / 100而不是重新换算成73.6。该测试仅用于证明读取路径，测试库不进入生产数据。5项UI在深色、实际字号2.0复跑均通过；默认浅色及深色大字号指标截图已核对，使用合成数据。
  A new UI test injects score 12.5 while retaining raw 278.5 AU and confirms History displays the stored 12.5 rather than recomputing 73.6. This controlled test database is not production data. All five UI checks repeat successfully in dark/font2.0; normal/light and large/dark captures were inspected.
- 证据：build/step90b-score-storage-validation/build.txt、storage-light1.txt、storage-dark2.txt、passed-tests.txt、captures/stored-score-*.png；单元/lint报告在标准app/build路径。本轮未重跑全量仪器、四小时压力或独立四图布局套件；界面布局、传感器采集和生命周期业务代码未改。
  Evidence is under build/step90b-score-storage-validation with standard unit/lint reports. The complete instrumentation, four-hour stress and separate chart-layout suites were not rerun; layout/acquisition/lifecycle business code is unchanged.
- 两对AGENTS.md/prompt.md已同步。只操作本轮启动的emulator-5590，Samsung/H10真机未安装或验证。无commit/push，不推进后续步骤。
  Both documentation pairs are synchronized. Only task-started emulator-5590 was used; Samsung/H10 installation/validation remains pending. No commit/push or later steps.
