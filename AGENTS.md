> 最新清理（2026-10-04）：移除旧区间/图表UI、未消费ACC展示缓存、失效运动提示及速度/距离/步长/最小步频链路；SQLite v7保留其余活动、曲线和评分。App构建及两类测试源码编译通过，未运行测试/lint/模拟器/真机或实际数据库升级。见5.56，无commit/push。
> Latest cleanup (2026-10-04): Remove unused UI/ACC display cache/motion messages and the speed/distance/stride/minimum-cadence pipeline. SQLite v7 retains other activity, chart and score data. App build and both test-source compilations pass; no tests, lint, device runs or actual installed-database upgrade. See 5.56; no commit/push.

> 最新展示（2026-10-04）：撤销5.51的ECG启动隐藏及纵轴更改，恢复原图；保留步频待确认和暂停连接。Intensity/Cardio Load显示整数 / 10，Cadence Stability仅一位小数百分比，/ 10与/ 100用主题蓝色。272单元、25项模拟器及10项深色字号2.0重复检查通过，构建/lint通过（0 errors、23 warnings）。见5.52，无真机安装、commit/push。
> Latest display (2026-10-04): Revert 5.51 ECG startup hiding/scaling; retain cadence confirmation and pause connections. Intensity/Cardio Load show integer scores out of 10; Cadence Stability shows one-decimal percent only; denominators use theme blue. All 272 unit, 25 emulator and ten dark/font-2.0 repeat checks pass; builds/lint pass (zero errors, 23 warnings). See 5.52; no hardware installation, commit or push.
> 最新启动显示（2026-10-04）：ECG原始数据全保留，实时/History仅隐藏检测到的启动漂移并保留原时间；步频首次确认前为空，正常暂停连接保留。277单元、45项不同模拟器及5项深色字号2.0重复检查通过，构建/lint通过（0 errors、23 warnings）。规则与限制见5.51；真机进一步验收pending，无commit/push。
> Latest startup display (2026-10-04): Preserve all raw ECG; hide detected startup drift in live/History charts without shifting time. Cadence stays unknown before initial confirmation; ordinary pause connections remain. All 277 unit, 45 distinct emulator and five dark/font-2.0 repeat checks pass; builds/lint pass (zero errors, 23 warnings). See 5.51 for rules/limits; further hardware validation pending, no commit/push.
> 最新图表规则（2026-10-04）：仅HR/Cadence正常暂停边界直接连接，实时及新保存History均生效；真实缺口继续断段，ECG不变。269单元与21项模拟器检查通过，构建/lint通过（0 errors、23 warnings）。规则及验证见5.50；真机pending，无commit/push。
> Latest chart rule (2026-10-04): Connect ordinary HR/Cadence pause boundaries in live and newly saved History charts. Preserve real gaps and ECG behavior. All 269 unit and 21 emulator checks pass; builds/lint pass (zero errors, 23 warnings). See 5.50; hardware pending, no commit/push.
# AGENTS.md

> 最新调整（2026-10-04）：RR图表及App内RR提取、缓存、会话标记、写入和查询流程已移除。Activity Summary仅保留HR/Cadence/ECG；SQLite v6删除旧RR表及receivedRr字段，保留其他摘要、评分和历史。实施与本轮验证见5.49；下方RR功能记录为历史状态。Samsung/H10 pending，无commit/push。
> Latest revision (2026-10-04): Removed RR charts and app-level RR extraction, buffering, session flags, writes and queries. Activity Summary retains HR/Cadence/ECG. SQLite v6 removes the legacy RR table and receivedRr column while preserving other summaries, scores and history. See 5.49 for implementation and validation; earlier RR entries are historical. Samsung/H10 pending, no commit/push.

> 最新评分存储（2026-10-04，9.0b）：SQLite v5新增sessionStrainScore REAL，保存完整精度0–100评分，同时保留原始AU；旧记录按规则补算，History直接读已存评分。262单元、27项不同模拟器检查及深色字号2.0的5项UI重复检查通过；构建/lint通过（0 errors、23 warnings）。见5.48.8—5.48.9，覆盖下方仅展示换算规则；真机pending，无commit/push。
> Latest score persistence (2026-10-04, 9.0b): SQLite v5 adds sessionStrainScore REAL for full-precision 0–100 scores alongside raw AU. Legacy scores are backfilled under the defined rules and History reads persisted scores. All 262 unit tests, 27 distinct emulator checks and five dark/font2.0 UI repeats pass; builds/lint pass (zero errors, 23 warnings). Sections 5.48.8–5.48.9 supersede display-only conversion; hardware pending, no commit/push.



> 最新评分展示（2026-10-04，9.0b）：Session Strain 改为100×原始负荷/(原始负荷+100)，显示一位小数 / 100；SQLite保留原始AU，数据库v4/原始算法2不变。262单元、5项界面检查及深色字号2.0的5项重复检查通过，构建/lint通过（0 errors、23 warnings）。规则与结果见5.48.6—5.48.7；真机pending，无commit/push。
> Latest score display (2026-10-04, 9.0b): Session Strain now shows 100×raw/(raw+100), with one decimal out of 100. SQLite retains raw AU; database v4/raw algorithm v2 are unchanged. All 262 unit tests, five UI checks and five dark/font2.0 repeats pass; builds/lint pass (zero errors, 23 warnings). See 5.48.6–5.48.7; hardware pending, no commit/push.



> 最新修订（2026-10-04，9.0b）：Session Strain 已改为自动计算：70% 心率区间平方加权分钟 + 30% 步频等级平方×活动分钟；删除全部 RPE 输入和评分字段。SQLite v4/算法2 保留历史并重算旧 Strain。260 单元、40 项不同模拟器检查及15项主题/字号重复检查通过；构建/lint通过（0 errors、23 warnings）。规则/Prompt/验证见5.48.3—5.48.5，覆盖下方RPE方案；Samsung/H10 pending，无commit/push。
> Latest revision (2026-10-04, 9.0b): Session Strain now automatically combines 70% zone-square-weighted heart minutes and 30% squared cadence level times active minutes. RPE input/columns are removed. SQLite v4/algorithm v2 preserves history and recalculates legacy Strain. All 260 unit tests, 40 distinct emulator checks and 15 theme/font repeats pass; builds/lint pass (zero errors, 23 warnings). Sections 5.48.3–5.48.5 supersede RPE rules below; Samsung/H10 pending, no commit/push.



> 最新功能（2026-10-04，9.0b）：Activity Summary 已实现 Intensity、Cardio Load、Cadence Stability（替换 HR Recovery）及手动 RPE 的 Session Strain；SQLite v3 保留升级，旧记录不自动回算。255 单元、分轮 42 项不同模拟器检查通过；新增四项 UI 检查覆盖深浅主题及字号 1.0/2.0，构建/lint 通过（0 errors、23 warnings）。规则及验证见 5.48；Samsung/H10 pending，无 commit/push。
> Latest feature (2026-10-04, 9.0b): Activity Summary implements Intensity, Cardio Load, Cadence Stability (replacing HR Recovery) and RPE-based Session Strain. SQLite v3 preserves existing data without backfilling old metrics. All 255 unit tests and 42 distinct emulator checks across runs pass; four new UI checks cover light/dark themes and font scales 1.0/2.0. Builds/lint pass (zero errors, 23 warnings). See 5.48; Samsung/H10 pending, no commit/push.



> 最新交互（2026-10-04，9.0a）：删除 Browse，ECG/RR 在绘图区左滑下一窗、右滑上一窗；保持五秒/六十条与四图固定布局。3 项检查在默认浅色及字号 2.0 深色均通过，构建/lint 通过（0 errors、23 warnings）。见 5.46.6，覆盖旧弹窗交互；真机 pending，无 commit/push。
> Latest interaction (2026-10-04, 9.0a): Browse is removed. Swipe left/right in ECG/RR for the next/previous window, preserving five seconds/sixty records and fixed layout. Three checks pass in normal-font light and font-2.0 dark modes; builds/lint pass (zero errors, 23 warnings). See 5.46.6; previous dialog interaction is superseded, hardware pending, no commit/push.


> 最新修正（2026-10-04，9.0a）：Summary 四图共用固定布局，切换 ECG/RR 不再重排上方卡片；窗口控制移至 Browse 弹窗。7 项相关检查及深色/字号 2.0 重复检查通过，构建/lint 通过（0 errors、23 warnings）。见 5.46.4，覆盖 5.46.3 的按图表切换布局方案；真机 pending，无 commit/push。
> Latest fix (2026-10-04, 9.0a): Summary charts share one layout; ECG/RR no longer reflow the upper cards. Window controls move to Browse. Seven relevant checks plus dark/font-2.0 repeats and builds/lint pass (zero errors, 23 warnings). Section 5.46.4 supersedes chart-dependent layout in 5.46.3; hardware pending, no commit/push.


> 最新进度（2026-10-04，9.0a）：ECG/RR 持久化、五秒/六十条窗口浏览、SQLite v2 保留升级及异常归档中央提示已实施。244 单元、分轮 39 项不同模拟器检查及字号 2.0 两项重复检查通过；构建/lint 通过（0 errors、23 warnings）。详见 5.46.3；Samsung/H10 pending，无 commit/push。下方“未持久化/未恢复”是历史状态。
> Latest status (2026-10-04, 9.0a): ECG/RR persistence, five-second/sixty-record browsing, preserving SQLite v2 migration and centered interrupted-archive messages are implemented. All 244 unit tests, 39 distinct emulator checks and two font-2.0 repeats pass; builds/lint pass (zero errors, 23 warnings). See 5.46.3. Samsung/H10 remains pending; no commit/push. Earlier no-persistence/no-recovery statements are historical.


> 最新生命周期调整（2026-10-04）：离开前台、锁屏、切换 Activity/History 自动暂停；返回后手动 Continue，同场数据由应用进程持有。暂停期间断线可重连原设备继续，进程死亡恢复未实现。237 单元、7 项受控模拟器检查、debug/测试 APK 与 lint（0 errors、23 warnings）通过，H10 真机 pending。详见 5.47，无 commit/push。
> Latest lifecycle update (2026-10-04): Leaving the foreground, locking or switching Activity/History automatically pauses. Continue is manual and the application process retains the session. Paused disconnections allow reconnection to the original device; process-death recovery is not implemented. All 237 unit tests, seven controlled emulator checks, debug/test builds and lint pass (zero errors, 23 warnings); H10 hardware remains pending. See 5.47; no commit/push.

> 最新调整（2026-10-04，8.6）：设备名称与连接按钮缩小，Saved devices 同行加入 Clear History；仅清除保存设备，不影响活动历史或当前连接。错误与 Recheck 单行，弹窗固定高度。231 单元、分轮 35 项不同仪器检查、最终字号 1.0/2.0 各 7 项 Devices 检查通过；构建/lint 通过（0 errors、16 warnings）。详见 5.43.3；真机仍 pending，无 commit/push。
> Latest update (2026-10-04, 8.6): Compact names/buttons, inline Clear History for saved devices only, inline errors/Recheck and a fixed-height dialog are implemented. Activity History and the current connection are preserved. All 231 unit tests, 35 distinct instrumentation checks across runs and seven final Devices checks at each font scale 1.0/2.0 pass; builds/lint pass (zero errors, 16 warnings). See 5.43.3. Hardware remains pending; no commit/push.

> 最新进度（2026-10-04，8.6）：Devices 弹窗第一版已按参考图实施，删除详细信息，保留必要问题处理；独立 Connect、跨列表去重、Scan/Stop scan、固定头尾与中间滚动已接入。231 单元、33 项不同 UI 检查及字号 2.0 的 5 项重复检查通过；构建/lint 通过（0 errors、16 warnings）。中英文提示词与记录见 5.43/5.43.1 和 prompt.md；真机仍 pending，无 commit/push。
> Latest status (2026-10-04, 8.6): The first Devices redesign follows the reference, removes details and retains necessary recovery. Explicit Connect, cross-list deduplication, Scan/Stop scan and fixed header/footer with a scrolling body are implemented. All 231 unit tests, 33 distinct UI checks and five font-2.0 repeats pass; builds/lint pass (zero errors, 16 warnings). Bilingual prompts/results are in 5.43/5.43.1 and prompt.md. Hardware remains pending; no commit/push.


> 最新 Session 调整（2026-10-04）：HR/Cadence 数值与单位使用 12 dp 间距并垂直居中；移除 Estimated Distance，Duration/Total Steps 放大并列、增加卡片间距。详见 5.41；独立的 Session 整页字号 2.0 与真机验收仍 pending。
> Latest Session update (2026-10-04): HR/cadence values and units use a 12 dp gap and vertical centering. Remove Estimated Distance and enlarge Duration/Total Steps in one row with wider card spacing. See 5.41; separate full-page Session font-2.0 and hardware acceptance remain pending.


> 最新展示（2026-10-04）：Activity Summary 仅保留 Duration / Total Steps 两列占满首行；Estimated Distance 展示及点击日期/时间打开详情信息的功能已删除。日期时间仍显示，详见 5.40.1；此条覆盖旧的三项主指标/信息弹窗要求。
> Latest presentation (2026-10-04): Activity Summary now has a full row shared by Duration / Total Steps. Remove Estimated Distance and the date/time information dialog; retain the displayed date/time. Section 5.40.1 supersedes the older three-metric/info-dialog requirements.


> 最新进度（2026-10-04，8.5d）：History 最终整合与本轮模拟器验收完成；231 单元测试、分轮 53 项不同仪器检查通过，字号 2.0 的 6 项及最终 2 项重复检查通过；debug/测试 APK、lint（0 errors、19 warnings）通过。修复查询重组索引、错误/保存通知挤压、长数值单位和大字号占位裁切。详见 5.40。Samsung/H10、真实性能及独立的 Session 字号 2.0 问题仍 pending；无 commit/push。下方早期状态均为历史记录。
> Latest status (2026-10-04, 8.5d): Final History integration and emulator acceptance are complete. All 231 unit tests and 53 distinct instrumentation checks across separate runs pass, plus six font-2.0 repeats and two final capture repeats. Debug/test builds and lint pass (zero errors, 19 warnings). Fixes cover list recomposition, error/save layout, long-value units and large-font placeholders. See 5.40. Samsung/H10, real performance and the separate Session font-2.0 issue remain pending. No commit/push; earlier statuses below are historical.


> 最新微调（2026-10-03）：详情标题/指标顺序、单位对齐、图表与区间高度及删除按钮已按最新要求优化；Overview、Activity charts、横轴 Duration 和 Unclassified 展示已移除。默认字号深浅主题单屏及字号 2.0 滚动视觉已核对，debug/lint 通过（0 errors、19 warnings），未新增/运行测试套件。详见 5.39.1；该节覆盖 5.39 的对应旧布局要求。
> Latest polish (2026-10-03): Updated heading alignment, metric order, units, chart/zone heights and Delete sizing; removed Overview, Activity charts, the Duration axis caption and Unclassified display. Normal-font light/dark single-screen and font-2.0 scrolling visuals are checked. Debug/lint pass (zero errors, 19 warnings); no test suite added/run. Section 5.39.1 supersedes the corresponding older layout requirements in 5.39.

> 最新布局（2026-10-03）：Activity Summary 按 History-detail.png 调整为默认字号单屏，主卡/空卡/图表/区间及红色描边删除按钮全部可见；附加信息改由日期旁入口打开弹窗。目标尺寸模拟器默认字号深浅主题已视觉核对，字号 2.0 使用纵向滚动避免裁切。debug/lint 通过（0 errors、19 warnings）；未写/运行测试套件，8.5d 完整验收与真机仍 pending，详见 5.39。此条覆盖下方旧详情布局记录；无 commit/push。
> Latest layout (2026-10-03): Activity Summary follows History-detail.png with all main cards, placeholders, charts, zones and a red outlined Delete button visible on one screen at normal font size. Additional information opens from the date/info entry. Target-size emulator light/dark visuals are checked; font 2.0 uses vertical scrolling. Debug/lint pass (zero errors, 19 warnings); no test suite added/run. Full 8.5d/hardware acceptance remains pending; see 5.39. This supersedes earlier detail-layout records. No commit/push.


> 最新调整（2026-10-03）：History 列表标题为居中的 History Activities，无副标题；列表/详情底部说明小字已移除。加载过渡移除闲置 Session 文本；按最新要求不显示进度指示（5.38.1），详情按 ID 重建查询状态并改用惰性列表，详见 5.38。debug/lint 通过（0 errors、22 warnings）；测试仍留最后，实际流畅度待验收，8.5d 未完成，无 commit/push。
> Latest update (2026-10-03): History has a centered History Activities list heading, no subtitle and no list/detail footer notes. Loading removes idle Session text and shows no progress indicator (5.38.1); detail state resets by ID; detail now uses lazy items. See 5.38. Debug/lint pass (zero errors, 22 warnings). Tests remain deferred; actual smoothness and full 8.5d acceptance remain pending. No commit/push.


> 当前进度（2026-10-03，8.5c）：History 已接入整场 HR/Cadence 切换图、禁用 ECG/RR、保存均值虚线、分段填充及五行横向 HR Zones/Unclassified。debug 构建和 lint 通过（0 errors、22 warnings）。按用户要求不新增/修改测试，最后统一补；自动/视觉及真机验收仍 pending。详见 5.37；8.5d 尚未实施，本轮无 commit/push。下方早期记录保留当时事实。
> Current progress (2026-10-03, 8.5c): History now has whole-session HR/Cadence charts, disabled ECG/RR, saved-mean references, segmented fills and horizontal HR Zones/Unclassified. Debug build and lint pass (zero errors, 22 warnings). Tests are deferred to final integration at the user's request; automated/visual and hardware acceptance remain pending. See 5.37. Step 8.5d is unimplemented; no commit/push this turn. Earlier records retain their historical context.


> 当前进度（2026-10-03，8.5b）：History 详情标题、Overview/Heart rate/Cadence、四项未定义指标空卡片及 Session details 折叠区已写入。debug 构建和 lint 通过（0 errors、22 warnings）。按用户要求，本轮不新增/修改测试，留到最后统一补；未运行自动测试或模拟器视觉检查，真机仍 pending。详见 5.36；8.5c—8.5d 未实施，无 commit/push。下方旧阶段记录按当时事实保留。
> Current progress (2026-10-03, 8.5b): History detail heading, Overview/Heart rate/Cadence, four placeholder cards and the Session details disclosure are implemented. Debug build and lint pass (zero errors, 22 warnings). At the user's request, tests are deferred to final integration and no test files change. No automated tests or emulator visuals run; hardware acceptance remains pending. See 5.36. Steps 8.5c–8.5d are unimplemented; no commit/push. Earlier records retain their historical context.


> 当前进度（2026-10-03，8.5a）：History 卡片列表、每批 10 条自动加载、右侧滚动条及适度字重已实施；本轮分轮 41 项不同 SQLite/Compose 检查通过，另重复 1 项真实系统字号 2.0 视觉检查通过；debug/测试 APK 与 lint 通过（0 errors、22 warnings）。详见 5.35。8.5b—8.5d 尚未实施；8.4 Session 字号 2.0 裁切与 Samsung/H10 验收仍 pending。本轮无 commit/push，下方旧阶段记录按当时事实保留。
> Current progress (2026-10-03, 8.5a): History cards, automatic batches of 10, a right-side scrollbar and moderate type are implemented. Across recorded runs, 41 distinct SQLite/Compose checks pass, plus one repeated visual check at actual system font scale 2.0. Debug/test-APK builds and lint pass (zero errors, 22 warnings). See 5.35. Steps 8.5b–8.5d remain unimplemented; Session font-scale 2.0 clipping and Samsung/H10 acceptance remain pending. No commit/push this turn; earlier records retain their historical context.

> 当前状态修订（2026-10-03）：8.4e 统一管理 Session 最终展示与实施记录；Welcome、Session、History 已请求固定竖屏，目标手机尺寸的模拟旋转检查通过（5.34.3.8）。横屏布局不再作为待完成项；**2.0 字号竖屏裁切与 Samsung/H10 真机验收仍 pending**。既有默认竖屏布局、Stop 重置及三图等高记录保留，不宣称全部 UI/真机验收完成。8.5 未实施，无 commit/push。
> Current status correction (2026-10-03): Step 8.4e owns final Session presentation and its records. Welcome, Session and History request fixed portrait and pass phone-sized simulated rotation checks (5.34.3.8). Landscape layout is no longer an outstanding task; **portrait font 2.0 clipping and Samsung/H10 acceptance remain pending**. Prior default-portrait, Stop-reset and chart-height evidence remains, without claiming complete UI/hardware acceptance. No 8.5, commit or push.

## 1. 项目目标

- 开发连接 Polar H10 的 Android 手机 App。
- 使用官方 Polar BLE SDK，完成心率与加速度实时采集。
- 完成数据处理、实时可视化、会话存储与历史查询。
- 作业依据：`req/Assignment_4.pdf`；展示依据：`req/Presentation.pdf`。
- 当前阶段：步骤 0.1—4.4（包括 2.3 设备电量）及 5.1、5.2a—5.2d、5.3add、5.4add、5.5add 的代码已落实。2026-09-30 的 5.5add 检查中，148 项单元测试、debug 构建和 lint 已通过，lint 0 errors、18 warnings。5.3add 提供运动统计，5.4add 提供心率强度和区间时长五柱图，5.5add 当时接入 HR/步频/速度最近 60 秒及 ECG 最近五秒的有界曲线、单调时间轴、断段和简单切换。沿用三路真实 SDK 数据、Start/Stop、Retry 和统计算法；用户此前 ACC 启动反馈不代表其他真机项目已通过。真实信号/滚动、ECG 刷新性能、走跑准确率、心率柱形和设备生命周期仍待验收，见第 9 节。6.1a 会话身份与摘要现已实施：UUID、日期/单调时间、设备快照、已有摘要、保存资格、各流观测/缺失/失败及结束冻结；6.1a 当轮 161 项测试通过。6.1b 平均/最小步频现已接入摘要与英文测试显示；6.1b 当轮 168 项测试通过。6.1c 已接入整场 HR 历史，当轮 181 项测试通过。6.1d 已接入每秒末组运动历史、null/断段、前四小时/14,401 点边界及摘要/两类历史组合冻结快照；6.1d 当轮 194 项测试通过。6.2 已写入 SQLite 三表事务、应用级保存状态、四小时结束及最简单 History 查询/详情/删除；本轮 204 项单元测试、debug 构建、lint（0 errors、18 warnings）及测试 APK 构建通过。独立模拟器中 8 项真实 SQLite/Compose 测试通过，见 5.23.11；H10 真机验收待完成。7.1 现已完善列表分页重试、返回/保存刷新、当前时区与旧查询取消；本轮 204 项单元测试、debug/测试 APK 构建、lint（0 errors、18 warnings、1 Hint）和 16 项实际 SQLite/Compose 检查通过，见 5.27.1；7.2 已完善统一日期、确认删除与失败恢复；2026-10-01 本轮 204 项单元测试、debug/测试 APK 构建、lint（0 errors、18 warnings）与 25 项实际 SQLite/Compose 检查通过，见 5.27.2。Samsung/H10 真机 pending；8.0 共用主题与基础尺寸已实施（见 5.28.1），8.1 顶部连接/电量、Devices 弹窗和强度已实施（见 5.29.1），8.2 四卡指标/区间/汇总已实施（见 5.30.1），8.3 底部曲线与开发显示清理已实施（见 5.31.1），8.4a 页面框架/控制/保存恢复已实施（见 5.32.1.1），8.4b 指标/汇总/横向区间已实施（见 5.32.2.1），8.4c 三类曲线/刻度/填充/整场参考已实施（见 5.32.3.1），8.4d 已实施功能整合与部分清理、暂停继续和五分钟窗口（见 5.33.1）；合并后的 8.4e 已实施参考图/单屏展示、Retry 清理、Stop 重置和图表等高；默认竖屏及手机旋转保持竖屏验证通过，竖屏大字号仍裁切，详见 5.34.3，8.4 整体 UI 未完成，Samsung/H10 真机仍待验收，8.5a—8.5d 软件整合与本轮模拟器验收已完成，最终构建、单元/仪器测试及视觉结果见 5.40；Samsung/H10 与真实性能验收仍 pending。

- 第 8 阶段进度（2026-10-01）：步骤 8.0 固定深浅主题、基础字号、已使用尺寸和共用心率区间配色已实施；204 项单元测试、debug/测试 APK 构建、lint（0 errors、17 warnings）及 25 项实际 SQLite/Compose 回归检查通过；12 张模拟器截图检查三页面的深浅模式和 1.0/2.0 字号，范围及未验证项见 5.28.1。8.1 顶部与 Devices 已实施，本轮 204 项单元测试、35 项 Compose/SQLite 检查、debug/测试 APK 构建及 lint（0 errors、15 warnings）通过，实际视觉检查边界见 5.29.1；8.2 四卡与 160 dp 区间已实施，204 项单元测试、48 项不同模拟器检查、debug/测试 APK 构建及 lint（0 errors、15 warnings）通过，实际视觉/系统字号/横屏及真机边界见 5.30.1；8.3 底部曲线与开发显示清理已实施（见 5.31.1），8.4—8.5 尚未实施，Samsung/H10 pending。

- 步骤 8.1（2026-10-01）：规则见 5.29，用户随后要求实施；顶部状态、Devices 与五档强度入口已写入，构建、自动/受控检查和实际视觉结果见 5.29.1，H10 真机 pending。两对文档同步；未 commit/push。

- 步骤 8.2（2026-10-01）：规则见 5.30；用户随后要求实施，四卡、160 dp 五柱/明细和原状态/恢复入口已接入。本轮检查与视觉结果见 5.30.1；Samsung/H10 pending，8.3—8.5 未实施，两对文档同步，无 commit/push。

- 步骤 8.3（2026-10-01）：按用户指定置底，正式曲线/选择/比例尺/220 dp 与开发显示清理已实施。211 单元测试、60 项不同模拟器检查、debug/测试 APK 与 lint（0 errors、15 warnings）通过；实际系统字号/横屏、受控及无 H10 App 视觉边界见 5.31.1；Samsung/H10 pending，8.4/8.5 未实施，两对文档同步，无 commit/push。
  Step 8.3 implemented with the chart last in Session, 211 unit tests and 60 distinct emulator checks passing, debug/test builds and lint passing (0 errors, 15 warnings). Source, actual system-font/landscape/controlled/no-H10 visuals and hardware limits are in 5.31.1. No 8.4/8.5, commit or push.

- 步骤 8.4 文档（2026-10-03）：按三张 ui/Session-*.png 参考图制定 8.4a—8.4d 中英文提示词，规则见 5.32，完整提示词见 prompt.md 的同日章节。本轮仅修改并同步两对文档；8.4a—8.4d 和 8.5 均未实施，没有修改 App 或数据层，没有运行新的构建、自动测试、模拟器视觉或 H10 验证，无 commit/push。
  Step 8.4 documentation: Bilingual prompts for 8.4a–8.4d follow the three ui/Session-*.png references. Rules are in 5.32 and full prompts are in the dated prompt.md section. This turn changes both documentation pairs only. All 8.4 substeps and 8.5 remain unimplemented; no App/data-layer changes, new builds, automated tests, emulator visuals or H10 validation, commit or push.

- 步骤 8.4a（2026-10-03）：页面框架、连接/Data streams、底部 Start/Stop 与保存恢复已实施；212 项单元测试、71 项不同 Compose/SQLite 检查、debug/测试 APK 构建及 lint（0 errors、15 warnings）通过；实际视觉与真机边界见 5.32.1.1。8.4b—8.4d/8.5 仍 pending，无 commit/push。
  Step 8.4a implements page structure, connection/streams, bottom Start/Stop and save recovery. All 212 unit tests, 71 distinct Compose/SQLite checks, debug/test-APK builds and lint passed (0 errors, 15 warnings). Visual and hardware limits are in 5.32.1.1. Steps 8.4b–8.4d/8.5 remain pending; no commit/push.

- 步骤 8.4b（2026-10-03）：指标分栏、HR 内强度、三项可换行汇总、Session 五行横向区间及新滚动顺序已实施；212 单元测试、75 项不同 Compose/SQLite 检查（另 1 项基础仪器检查）、debug/测试 APK 和 lint（0 errors、15 warnings）通过。实际视觉/真机边界见 5.32.2.1；History 与数据层未变，8.4c/8.4d/8.5 pending，无 commit/push。
  Step 8.4b implements responsive metrics, intensity inside HR, wrapping summary, Session horizontal zones and the specified scrolling order. All 212 unit tests, 75 distinct Compose/SQLite checks (plus one basic instrumentation check), debug/test builds and lint passed (0 errors, 15 warnings). Visual/hardware limits are in 5.32.2.1. History/data layers remain unchanged; 8.4c/8.4d/8.5 pending, no commit/push.

- 步骤 8.4c（2026-10-03）：胶囊选择、网格/自适应刻度、HR/运动分段填充、整场 mean/max 与均值参考、实际 ECG 率/窗口计数已实施；218 单元测试、78 项不同 Compose/SQLite 检查（另 1 项基础检查）、debug/测试 APK 和 lint（0 errors、15 warnings）通过。实际视觉与硬件边界见 5.32.3.1；数据层与 History 绘图未变，8.4d/8.5 pending，无 commit/push。
  Step 8.4c implements pill selection, grids/adaptive ticks, segmented HR/motion fills, whole-session statistics/reference lines and actual ECG rate/window counts. All 218 unit tests, 78 distinct Compose/SQLite checks (plus one basic check), debug/test builds and lint passed (0 errors, 15 warnings) across the recorded runs. Visual/hardware limits are in 5.32.3.1. Data layers and History plots are unchanged; 8.4d/8.5 pending, no commit/push.

- Motion 展示规则修订（2026-10-03）：按用户最新要求，Session 的 Motion 指标卡及 Live charts 的 Motion 视图只展示步频；移除速度数值、速度统计、km/h、速度曲线与 Cadence/Speed 子切换。保留当前及整场 Mean/Min/Max cadence、steps/min、必要状态和 Retry ACC。两对文档与 8.4d 执行计划已同步；本轮只修改文档，现有速度 UI 尚未移除，交由用户指定实施 8.4d 时完成。速度计算/状态/缓存/历史存储及 History 速度统计保留，Activity summary 的估计距离保留。此规则覆盖早期 Session Motion 速度展示要求；此前实施与验证记录保留为历史证据。
  Motion display revision: Session's Motion metric card and Live charts Motion view will show cadence only, removing speed values/statistics, km/h, the speed plot and Cadence/Speed sub-selection. Retain current and whole-session Mean/Min/Max cadence, steps/min, necessary states and Retry ACC. Both documentation pairs and the 8.4d plan are synchronized. This turn changes documentation only; existing speed UI remains until the user requests 8.4d implementation. Preserve speed calculations/state/buffers/history storage, History speed statistics and summary estimated distance. This supersedes earlier Session Motion speed-display requirements; prior implementation/validation records remain historical evidence.

- 参考图功能对齐（2026-10-03）：用户要求严格按三张 Session 图示的展示功能实施，并明确确认加入暂停/继续、HR/步频最近五分钟窗口。最新规则与差异清单见 5.33，覆盖早期“保留 Session 速度/最小值”“仅 Start/Stop”“最近 60 秒”的要求。此前阶段记录与上一轮 Motion 文档同步记录按当时事实保留；当前实施和验证结果以 5.33.1 为准。History 仍留 8.5，Samsung/H10 pending。
  Reference-function alignment: The user explicitly requested the three Session references' displayed functions, including Pause/Continue and five-minute HR/cadence windows. Section 5.33 supersedes earlier Session speed/minimum displays, Start/Stop-only controls and 60-second windows. Earlier stage records and the preceding Motion documentation update remain historical; current implementation/validation is in 5.33.1. History redesign remains in 8.5 and Samsung/H10 validation is pending.

## 2. 协作规则

- 每次只执行用户指定步骤，不自动推进后续步骤。
- 每步使用完成当前功能所需的最小代码，避免无关重构。
- 严禁超前设计。
- 严禁过度工程。
- 严禁防御未出现的错误。
- 严禁向后兼容。
- 严禁使用胶水代码掩盖需要修剪的实现和旧路径。
- 最小代码用于逐步开发；最终仍须满足作业深度与完整性要求。
- 默认提供可复制代码块；明确要求写入文件时，再修改项目代码。
- 修改前查看现有代码；沿用命名、依赖和界面风格。
- 按功能拆分代码块，每块注明文件、位置及新增/替换方式。
- 同一文件有多个修改点时，按实际操作顺序列出。
- 代码不得省略本步骤必需的 import、权限、依赖或调用位置。
- 中文解释，英文代码注释；每段说明尽量简短。
- App 界面统一使用英文，包括页面标题、按钮、状态、提示、错误信息及历史记录字段名称；代码注释使用英文，与用户沟通和开发说明使用中文。
- 必要参数缺失时先询问；不受影响的内容可继续。
- 涉及 SDK API 时核对所用版本及官方文档。
- 分别说明：代码已提供、文件已修改、构建已通过、真机已验证。
- 无数据使用占位符；演示数据必须明确标注。
- 不将规划、模拟数据或编译成功写成真机功能已完成。

## 3. 作业验收

| 要求 | 本项目对应内容 |
|---|---|
| 欢迎界面与开始/停止采集 | MainActivity、Session 控制按钮 |
| 官方 SDK 与实时数据 | Polar BLE SDK、心率、加速度 |
| 有意义的数据处理 | ACC 平滑、步伐确认、步频与距离估算、会话统计 |
| 实时可视化 | 实时指标、心率区间、曲线 |
| 跨会话历史或统计 | 本地持久化、History 列表与详情 |
| 真实设备演示 | Android 手机连接 Polar H10 |
| 设计解释 | 数据类型、处理方法、可视化选择及局限 |
| 开发证据 | 初始化、连接、处理、最终界面的截图或录屏 |
| AI 使用记录 | 自写提示词、用途、采用/修改/拒绝原因与验证 |
| 提交材料 | App ZIP、独立贡献声明、AI 文档、展示幻灯片 |
| 团队评审 | 四人参加；每人能解释整个应用 |

- ECG 为作业可选数据，本项目设计中包含 ECG 曲线。
- 高分需要处理依据、历史分析价值、可靠性及界面完成度。
- 提示词使用自己的描述；理解并验证所有提交代码。

## 4. 已确定的界面

### 4.1 欢迎页

- 使用 MainActivity。
- 显示 App 标题、功能简介、进入 Session 的按钮。

### 4.2 Session 页

- 使用独立的 `SensorActivity.kt`，由欢迎页按钮打开；界面继续使用 Compose。

| 区域 | 内容 |
|---|---|
| 1：左上 | 可点击连接状态；弹窗展示已保存设备、扫描、连接、断开 |
| 2：心率卡 | 当前有效 HR 的五档强度标签置于 HR 卡；规则见 5.21、5.33 |
| 3：主指标 | 当前心率、Max HR、Mean HR、Last received；Session 不展示 Min HR，见 5.33 |
| 4：步伐指标 | Cadence 卡只展示当前步频、Mean/Max cadence（steps/min）；无速度/Min，必要状态与恢复见 5.33 |
| 5：心率区间 | 五个固定区间的本场累计估计时长，以不同颜色柱形展示，规则见 5.21 |
| 6：运动汇总 | 运动时间、总步数、距离 |
| 7：图表 | HR / Motion / ECG；Motion 仅步频，无子切换；HR/步频最近 5 分钟，ECG 最近 5 秒，见 5.33 |
| 8：控制 | Pause / Start或Continue / Stop 三按钮；暂停保留同场数据并排除暂停时长，见 5.33 |
| 9：导航 | Session / History 切换 |

### 4.3 History 页

- 会话列表字段（8.5a，最新展示修订见 5.38）：标题为居中的 History Activities，无副标题和底部常驻小字；日期/时间、Duration 和必要的 Incomplete 标记，以整卡点击进入详情；主列表不再显示步数/距离，详见 5.35。列表日期为当前时区的 dd MMM yyyy、时间为 HH:mm，8.5b 详情标题沿用短日期/时间，精确秒数和 UTC 偏移保留在 Session details。
- 会话详情：整场心率/步频曲线；HR 最小/最大/平均；五区间时长及 Running 占比、未归类时间；步频平均/最大/最小；Running 时长、总步数、累计估计距离、平均/最大估计速度；设备、结束原因及完整性。速度曲线保存但首版详情不必展示；9.0a 已支持 ECG 五秒/RR 六十条窗口回放，见 5.46.3。
- 排序与筛选（8.5a）：开始时间及 ID 稳定倒序，每次 10 条，接近列表末尾自动加载，右侧滚动条，无 Load more；不加筛选。按 ID 查详情，单场删除前确认；详情摘要与空卡片已在 8.5b 实施（5.36），整场切换曲线/横向区间已在 8.5c 实施（5.37），最终整合与本轮模拟器验收已在 8.5d 完成（5.40）；历史方案见 5.23，当前列表见 5.35。

## 5. 设计决定与待填写项

| 项目 | 决定 |
|---|---|
| App 名称、包名 | `Polar H10 ActivityViewer`；`com.example.polarh10activityviewer` |
| Kotlin/Java、Compose/XML | Kotlin + Jetpack Compose + Material 3，沿用现有项目 |
| Android 版本、SDK 版本 | 最低 Android 13 / API 33（`minSdk = 33`）；沿用 `compileSdk = 37`、`targetSdk = 37`；官方 Polar BLE SDK 固定为 `8.3.0` |
| SDK 仓库与配套依赖 | JitPack（`https://jitpack.io`）；`com.github.polarofficial:polar-ble-sdk:8.3.0`；`org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2`；`org.jetbrains.kotlinx:kotlinx-coroutines-rx3:1.10.2` |
| 页面结构、文件职责 | 根包 `com.example.polarh10activityviewer` 保留 `MainActivity.kt` 和 `SensorActivity.kt`；`ble/PolarBleManager.kt` 管理 SDK、连接和订阅；其他源码及测试按功能分包，完整映射见 5.25 |
| 测试设备 | Samsung Galaxy A26，型号 `SM-A266B`；Android 16 / API 36；2026-09-26 已通过 ADB 读取确认，USB 调试已授权 |
| SDK、权限与可用状态 | 步骤 1.1 已实施，权限与异常场景验收待完成，见 5.8 |
| 扫描与去重 | 步骤 1.2 已确认规则见 5.9；30 秒自动停止，停止后保留本轮结果，下次扫描前清空 |
| 颜色、字体、布局尺寸 | 步骤 8.0 已接入固定蓝色主主题、系统深浅模式、基础字号及已使用尺寸；心率区间低到高为绿/蓝/黄/橙/红，完整基线见 5.28，实施/验证见 5.28.1；正式指标字号和图表尺寸留对应步骤 |
| 已保存设备的定义 | App 曾真实连接成功并保存的设备；保存名称、唯一标识和最近连接时间，重启后保留；不表示系统已配对或当前在线，见 5.11 |
| 连接与状态 | 点击 H10 后停止扫描并连接；一次一台，防止重复请求；超时 10 秒；状态由真实回调确认，暂不实现切换设备，见 5.10 |
| 断线与重连行为 | 主动断开由回调确认，保留设备记录；意外断线提示后由用户手动重连；再次连接使用 10 秒超时，见 5.11 |
| 设备电量 | 步骤 2.3 已实施：启用 SDK 电量回调，展示当前有效连接的电量百分比；未收到或断开后显示占位符，测试及构建通过，真机待验证，见 5.14 |
| HR、ACC、ECG 采样配置 | ACC 目标 100 Hz、±4 g，见 5.16；HR 不设置可选采样率；启动前复核当前设备支持配置，其他参数选择规则见 5.12 |
| 数据功能就绪与订阅管理 | 3.1 就绪查询与 3.2 订阅管理已实施，见 5.12—5.13；4.1—4.3 已接入真实 HR、ACC、ECG SDK 流，见 5.15—5.17；ACC 启动通过用户反馈确认，其余完整真机验收待完成 |
| 时间戳、单位、缺失数据规则 | HR 使用手机接收时间，单位 bpm，见 5.15；ACC 使用样本时间戳、间隔大于 30 ms 判定缺口，暂存最近 10 秒且最多 1,000 样本，见 5.16；ECG 保留原始样本时间戳及 µV，暂存最近 10 秒且最多 1,300 样本，见 5.17；算法中断规则见 5.6 |
| 步伐、步频、步长、距离算法 | 已确定，见 5.1—5.7；A_min 初始预设 0.5 m/s²，实测调整方案见 5.6，尚未校准 |
| 速度及速度统计 | 5.3add 已实施：最近 5 秒/预热后短窗口的已确认步长之和除以窗口时长；平均速度为本场估计距离除以完整 Running 时长，包含静止；最大值取真实完整连续 5 秒窗口结果，内部 m/s、UI km/h。自动检查通过，真机待验证，见 5.20 |
| 心率区间与心率强度规则 | 5.4add 已实施：有效 HR <110、[110,125)、[125,140)、[140,155)、≥155 bpm；Very light / Light / Moderate / High / Very high，以最新非空批末有效性决定当前档位，不使用年龄或最大心率参数，见 5.21；真机待验证 |
| 心率区间柱形含义 | 5.4add 已实施：当前 Session 各区间累计估计时长，使用最近有效读数保持法；五根柱形共用时长比例尺，未归类时间单列，见 5.21；内部毫秒，显示截断秒的 mm:ss，真机待验证 |
| 平均值与最大/最小值范围 | HR 见 5.19；5.3add 已实现平均速度包含整场 Running 内静止时间，最大步频/速度取整场有效 5 秒窗口峰值，见 5.20；6.1b 已增加整场平均步频和合格窗口最小步频，见 5.23.2、5.23.8；未增加最小速度 |
| Start、Stop 行为 | Start 新建会话；Pause/离开 Session 暂停；Continue 手动继续原会话，首个真实数据计时；Stop 最终保存并重置。运行中真实断线仍结束，暂停中断线保留，见 5.47；H10 待验收 |
| 页面离开、锁屏、后台行为 | 手机固定竖屏；离开前台、切换 Activity/History、返回欢迎页或锁屏自动暂停并停止订阅，保留同场数据；返回后手动 Continue，断线需先重连原设备。应用进程持有状态，不支持进程死亡恢复，见 5.47 |
| 存储技术、字段与保存时机 | 已保存设备使用私有 SharedPreferences + JSON，在接受有效连接成功回调后保存名称、唯一标识和最近连接时间，见 5.11；会话使用 SQLiteOpenHelper，在 IO 线程将摘要及历史曲线一次事务保存；6.2 已实施事务、保存状态、失败重试与资格，见 5.23.11 的实际验证结果 |
| 原始数据保留范围 | ACC 内存暂存最近 10 秒、最多 1,000 样本，见 5.16；ECG 内存暂存最近 10 秒、最多 1,300 样本，见 5.17；整场保存摘要及每秒最多一点的 HR/步频/速度；9.0a 新增整场原始 ECG 分块与逐条 RR 持久化，不保存原始 ACC，见 5.46.3 |
| 实时曲线窗口 | 5.5add 已实施：HR、步频、速度最近 60 秒，分别最多 61、241、241 点（运动两值共用最多 241 条记录）；ECG 最近 5 秒复用 10 秒/1,300 点原始缓存，130 Hz 受控五秒窗口保留全部 650 个样本；自动检查通过，真实绘图/性能待验证，见 5.22 |
| 数据隐私与保留方式 | App 私有 SQLite；不上传，数据库排除云备份及设备迁移；不自动过期/限条删除，支持确认后单场删除，无导出，卸载/清除数据丢失历史，见 5.23 |

步骤 0.1 的 SDK 依据：[H10 功能说明](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/documentation/products/PolarH10.md)、[8.3.0 发布版](https://github.com/polarofficial/polar-ble-sdk/releases/tag/8.3.0)、[8.3.0 安装说明](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/README.md#installation)。后续 API 按固定版本核对。

最低版本按官方安装步骤选择 API 33；该版本库源码声明的最低版本为 API 26，与安装说明不一致，因此不将 API 33 表述为 SDK 源码唯一声明的最低要求。当前依赖组合已通过 debug 构建与 lint，见第 9 节；构建通过不等于真机功能已全部验证。

### 5.1 加速度准备

- 方法依据：`note/551a40924.docx`；增加本次确认的回落与连续步伐筛选。
- ACC 目标采样率 100 Hz；逐个样本处理，不将一次 SDK 回调视为一个样本。
- 使用样本时间戳计算间隔；仅在连续 100 Hz 数据中，25 个采样间隔等于 0.25 秒。
- 单位转换：`ax = x_mG × 0.0098`；ay、az 同理，单位为 m/s²。
- 合加速度：`a = sqrt(ax² + ay² + az²)`。
- 平滑（2026-09-29 已确认）：`s = 最近 5 个 a 的平均值`。前 4 个原始样本只缓存，不输出 s；从第 5 个样本起，每来一个样本输出一次完整 5 点平均，不使用不足 5 点的平均值填充预热期。s 关联本次最新原始样本的传感器时间戳，不另外补偿平滑延迟。
- 阈值窗口（已确认）：使用当前 s 之前的 100 个 s 计算平均值 μ、总体标准差 σ；当前点不参与它自己的阈值计算。按“先用旧窗口检测当前点，再将当前点加入窗口并移除最旧点”的顺序处理。
- 预热（已确认）：先收满 5 个原始样本产生首个 s，再收满 100 个 s；第 100 个 s 只完成窗口准备，从下一个 s 开始检测。把完成 100 点窗口的样本时间记为本连续段的预热结束时间。窗口未满不输出候选步，接受每个新连续段开头约一秒不能检测步伐的限制；采样率改变时重新确定窗口配置。
- 5.2a 实施（2026-09-29）：新增 AccPreprocessor.kt，将 mG × 0.0098 后计算合加速度、完整 5 点平均、前 100 个平滑值的均值和总体标准差。第 104 个连续原始样本完成预热，第 105 个起输出含前窗口统计的 PreparedAcc；当前点不参与自身统计，保留最新样本传感器时间。窗口分别限制为 5 和 100 点，仅保留最新处理结果。AccBuffer 在原有逐样本循环中将带 gapBeforeNs 的新样本送入预处理器，不重放 UI 缓存。缺口、ACC 停止/完成/失败、接受启动/Retry 及新 Session 清理窗口，旋转沿用管理实例；复用既有订阅有效性检查。仅实施用户本次给出的三条要求，不增加界面、H/L 判断、候选步、步数或步频；后续 5.2b 才消费这些统计进行检测。
- 采样率建议（2026-09-27 用户要求记录）：首版保留 100 Hz，作为走路、跑步及峰值时间分析的合理起点；并非计步的最低必需频率，也不代表比 50 Hz 更准确。先完成真实数据验证，再判断是否需要降低采样率。
- 取舍：100 Hz 的采样间隔为 10 ms，50 Hz 为 20 ms；相同时长下前者样本数为后者两倍，但耗电和性能差异需实测，不能直接按两倍推算。
- 窗口联动：按点数除以采样率估算窗口尺度，100 Hz 下 5 点平滑约 50 ms、100 点阈值窗口约 1 秒；若降至 50 Hz 而点数不变，窗口尺度会翻倍。比较采样率时应按目标时长重新确定点数并验证平滑效果，不能只改采样率；0.25 秒步间隔等时间规则继续使用样本时间戳。
- 依据与边界：[胸部加速度计步伐检测研究](https://www.frontiersin.org/journals/physiology/articles/10.3389/fphys.2022.942954/full)使用 100 Hz 检测走路、慢跑和跑步；[Analog Devices 计步器设计](https://www.analog.com/en/resources/analog-dialogue/articles/pedometer-design-3-axis-digital-acceler.html)采用 50 Hz。设备、佩戴条件和算法不同，这些依据仅支持采样率选择的合理性，不能作为本项目准确率证据。

### 5.2 候选步与回落确认

- 上阈值：`H = μ + max(σ, A_min)`；下阈值：`L = μ`。
- A_min 是上阈值相对 μ 的最低幅度门槛，单位 m/s²；用于避免静止时 σ 很小导致微小抖动也触发候选步。过小可能误计、过大可能漏掉轻步。5.2b 初始采用 `A_min = 0.5 m/s²`，作为待实测校准的试验预设；来源、适用边界与手动调整方案见 5.6，不新增自动标定流程。
- 等待上升（2026-09-29 已确认）：先按前 100 个 s 计算当前 H、L，使用同一个当前 H 比较前后两个平滑值；`previousS <= H && currentS > H` 时进入等待回落状态，不分别使用两个时刻不同的 H 判断上穿。
- 等待回落：固定本周期 H、L，持续记录最高值及其样本时间。滚动阈值窗口仍逐点更新，但不得改变本周期已经固定的 H、L；回落完成或周期丢弃后，下一周期再采用最新窗口。
- 信号降至 L 或以下时，产生一个候选步，返回等待上升状态。
- 一个上升回落周期最多产生一个候选步；候选步时间取峰值时间。
- 候选步与上一接受候选峰的间隔不足 0.25 秒时丢弃，不移动参考峰。
- 未回落的周期不计步；超过 2 秒仍未完成则丢弃，并重新等待上穿。
- 5.2b 实施（2026-09-29）：新增 StepCandidateDetector.kt，消费 PreparedAcc 中的前 100 点均值和总体标准差，按同一当前 H 判断上穿；周期保存固定 H/L、上穿时间及最高峰。回落到固定 L 或以下时输出一次 StepCandidate（峰值与峰值传感器时间）；相同最高值保留首次出现时间。超时从上穿开始，严格大于 2 秒优先丢弃，即使该点已回落也不补记；恰好 2 秒回落可确认。预处理窗口每点继续更新，下一周期使用最新统计。PolarBleManager.kt 已将其接入逐样本 ACC 路径，并在缺口、启动/Retry、停止/失败/完成和新 Session 时清理；沿用旋转保留和旧订阅事件防护。只保留最新候选用于调试，没有候选历史缓存、UI、0.2 秒筛选、连续四步确认、总步数或步频；后几项留给 5.2c—5.2d。

### 5.3 连续步伐确认与总步数

- 最小步间隔调整（2026-09-29）：按用户决定将 StepSequence.kt 的最小接受候选峰间隔从 0.2 秒改为 0.25 秒（250_000_000 ns）；恰好 0.25 秒可接受，更短的候选不移动参考峰。初始四步确认和持续计步共用该规则；两秒上限、A_min、100 Hz、平滑/预热窗口及步频公式保持不变。早期实施/验证记录中的 0.2 秒为当时版本，当前以本条和现行规则为准。StepSequenceTest.kt 已同步 0.25 秒边界和连续计步用例，检查 0.2 秒及 249,999,999 ns 被拒绝、恰好 250,000,000 ns 被接受，且拒绝不移动参考峰。已运行全部 105 项单元测试（0 failures、0 errors、0 skipped）、debug 构建及 lint，BUILD SUCCESSFUL，lint 0 errors、18 warnings。此次调参效果及原地跳误计改善情况待真机对比，未安装 APK，不视为已实现跳跃过滤。

- 初始为寻找状态；连续 4 个候选步、3 个间隔均在 0.25—2 秒内，才确认走跑。
- 确认前缓存候选步时间及可计算步长，不更新正式步数或距离。
- 第 4 个候选步通过时，将缓存的 4 步一次补计，进入持续计步状态。
- 持续状态下，每个通过间隔检查的候选步增加 1 步；每步只提交一次。
- 间隔超过 2 秒时结束旧序列，按 5.6 重建检测段，不连接旧峰。
- 寻找状态的不足 4 步序列超时后丢弃；小于 0.25 秒的杂峰不打断已有序列。
- 这是初始连续性筛选，不代表已验证真实落脚；短于 4 步的活动可能漏计。
- 5.2c 实施（2026-09-29）：新增 StepSequence.kt，按原始候选峰时间筛选；小于 0.2 秒（含重复峰）不更新参考峰，0.2—2 秒含端点。前三个候选只暂存，第四个通过时一次返回四个原始候选并累计 4，后续每个合格候选只返回一次并累计 1；只保存至多三个待确认候选及最近一次提交（至多四个），不建立无界峰值历史。新增 StepDetector.kt 统一持有已有预处理器、候选检测器和序列状态，替换管理器原先直接串接路径。每个新原始样本处理前，以该样本时间与上一接受候选峰时间比较，严格超过 2 秒即清空检测段并从当前样本重新预热；清空后不反复超时，没有 UI 定时器参与。回落确认晚于已触发的段超时不会补回旧段候选。缺口、停止、失败、Retry 清理检测段但保留累计步数，新 Session 才清零；旋转及旧事件防护沿用现有管理器。没有增加 UI、步频、步长、距离或速度；下一步为 5.2d。

### 5.4 当前步频

- 保存已确认步伐的原始峰值时间，包括首次补计的 4 步。
- 统计最近 5 秒区间 `(t − 5, t]` 内的已确认步数 N5。
- 完整窗口：`C = 60 × N5 / 5 = 12 × N5`，单位为步/分钟。
- 当前连续检测段不足 5 秒时：`C = 60 × NT / T`，T 为预热结束后的实际时长，且 T > 0；NT 仅计入本段预热结束后、当前计算时刻及之前的已确认步伐，预热时长不加入分母。
- 首次连续确认前显示 0；确认后按原时间戳归入窗口，不将 4 步记在同一时刻。
- 刷新（2026-09-29 已确认）：收到新的已确认步伐时更新，同时每 250 ms 刷新一次当前显示；刷新不产生步伐、不重复补计。内部保留小数，UI 四舍五入为整数，单位 steps/min。
- 无步归零：距最后已确认峰值达到 2 秒时显示 0，不等待下一次步伐触发；通常在达到两秒后的下一次刷新体现，不宣称定时器具备精确的实时调度保证。该显示规则与 5.6 中“超过 2 秒”的检测段重置分别处理。
- 时间职责（已确认）：样本处理、峰间隔筛选和检测段重置使用传感器时间戳。每批 ACC 到达时记录该批末样本时间及手机单调时钟 SystemClock.elapsedRealtime()；没有新批次时，显示用当前时间估计为“最近批次末样本时间 + 从该批收到至今的手机单调经过时间”，计算前统一单位。下一批到达后更新此对应关系。
- 时间估计仅用于步频显示窗口及两秒无步归零，不能生成样本、改写峰值时间、判断 30 ms 缺口或触发计步算法的检测段重置。显示按估计时刻选择 `(t - 5 秒, t]` 内的峰值；检测用峰值仍保留原始时间，不能以 UI 刷新覆盖或重记步伐。此方法包含传输/调度延迟，不是精确的设备时钟同步；不新增同步框架、停流超时或自动重试。
- 生命周期（已确认）：正常初始预热及首次四步确认前显示 0，配合预热/等待确认提示；ACC 缺口或接受 Retry 时清空当前步频窗口及时间对应关系、重新预热，保留本场总步数。Stop 后立即显示 0，保留累计总步数；新 Start 清空全部步伐状态，旋转保留。5.3 的显示补充见 5.20：ACC 不可用/失败或已识别缺口后的预热期显示 -- 及原因，不将缺失数据当作已测得静止。
- 5.2d 实施（2026-09-29）：新增 CadenceWindow.kt，按原始已确认峰时间计算左开右闭五秒窗口及预热后的短窗口，保留内部小数；真实样本推进时移除五秒外峰值，显示时间不删除检测记录。StepDetector.kt 发布 StepState，正常初始预热/等待四步显示 0；ACC 不可用/失败、缺口及已有 ACC 后 Retry 的重新预热显示 -- 和原因。每批由 PolarBleManager.kt 在处理前读取 SystemClock.elapsedRealtime()，处理后记录该批末样本传感器时间；新确认步及批次完成时更新步频，复用现有 250 ms Session 刷新，并由会话代次过滤旧刷新。显示时钟外推只影响窗口和两秒归零，不生成步、不触发段重置；新批次重新对齐映射。SensorActivity.kt 新增临时 Steps (development check)、状态、总步数及整数 steps/min。整体 Stop/中断立即清理映射和检测段、保留总数，本场收到过 ACC 时显示 0，未收到时仍 --；单流失败仅使该指标不可用。旋转保留，重复启动/Retry 拒绝不清空，新 Session 清零；未实施 5.3 速度/距离等后续功能。

### 5.5 估计步长与总距离

- 使用简易 Weinberg 模型：`Li = 0.45 × (s_max,i − s_min,i)^0.25`，单位为米。
- s_max,i、s_min,i 来自同一连续序列中相邻接受候选峰之间的平滑合加速度，包含两端峰。
- 峰间统计按峰值时间截取，不把确认回落后的数据混入上一峰间区段。
- 每个完整峰间区段仅计算一次步长；`总距离 D = 已提交 Li 的累加值`。
- 寻找状态先缓存步长；4 步确认后统一提交，持续状态逐步提交，禁止重复累加。
- 每段第 1 个峰只建立起点，步长为空；首次确认 4 步时只有 3 个完整区段可累加。
- 不补造起始步长；接受每段起始部分距离少估这一简化限制。
- K = 0.45 为当前试验值（2026-10-01 按用户要求由 0.5 调整），尚未实测校准；界面使用“估计步长”“估计距离”。
- 步频来自步伐时间；距离来自步长累加，两者分别计算。

- 2026-10-01 K 调整：按用户要求，StrideLengthEstimator.kt 的步长系数由 0.5 改为 0.45；此前记录中的 K=0.5 为当时版本。同一组有效峰间数据下，估计距离和基于步长的速度为原值的 90%，步数/步频与 A_min=0.5 m/s² 不变。同步已有步长及运动统计测试预期；211 项单元测试通过（0 failures/errors/skipped）、debug 构建通过、lint 0 errors/15 warnings。未安装 APK 或进行本轮真机验证，K=0.45 仍为未校准试验值；已保存历史不重算，无 commit/push。
  K adjustment: Changed the stride coefficient from 0.5 to 0.45 at the user's request. Earlier K=0.5 records describe the previous version. Identical valid peak intervals yield 90% of the previous estimated distance and stride-based speeds; steps, cadence and A_min=0.5 m/s² are unchanged. Updated existing stride/motion test expectations. All 211 unit tests passed with no failures/errors/skips, the debug build passed, and lint reported 0 errors/15 warnings. No APK installation or hardware validation was performed; K=0.45 remains an uncalibrated trial value. Saved history is not recalculated. No commit/push.

### 5.6 停止、中断与 A_min 预设及调整

- 新 Session 清零总步数、总距离和全部检测状态。
- Stop 或断线结束当前会话，不再计步或累加距离，步频显示 0；数据缺口只中断当前检测段，重新预热后可在同一运行会话中继续处理，不跨缺口累加距离。
- 保留已确认累计值；丢弃未确认候选步，清空平滑、阈值、峰间及步频窗口。
- 已有序列连续超过 2 秒无接受候选步时，执行一次重置；后续重新预热并确认连续 4 步。
- 数据缺口或静止中断后建立新检测段，不跨中断计算步长；断线后再次 Start 创建新会话，不续接旧会话。
- 所有窗口与缓存保持有界；ACC 在 100 Hz 下相邻样本时间差大于 30 ms 判定为数据缺口，见 5.16。

#### A_min 初始预设与依据（2026-09-29）

- 初始采用 `A_min = 0.5 m/s²`，即 `H = μ + max(σ, 0.5)`、`L = μ`。0.5 是相对均值的最低上阈值偏移，不是合加速度绝对阈值，也不是峰谷差；保留重力的合加速度仍按 5.1 处理。本值是项目试验起点，不是 Polar 官方参数或已验证的 H10 最优值。
- 数值参考：[Analog Devices AN-2554 计步实现](https://www.analog.com/en/resources/app-notes/an-2554.html)采用 `sensitivity = 0.1 g`，峰值条件含“动态阈值 + sensitivity/2”，上侧偏移约为 `0.05 × 9.8 = 0.49 m/s²`。据此取整提出本项目的 0.5 预设；这是量级参考与工程推断。该实现使用腕部设备、50 Hz、三轴绝对值之和及不同的阈值/平滑规则，不能直接迁移其参数效果或准确率。
- 其他项目对照：[sensor-zoo 的 Step Counter](https://github.com/tszheichoi/sensor-zoo#step-counter)使用动态阈值配合最小峰谷幅度或峰突出度门槛。这支持设置最低幅度限制的思路，但其门槛定义与本项目的均值偏移不同，不把其 `minAmplitude` 或 `minProminence` 等同于 A_min。

#### 手动调整方案（算法实施后执行，目前未测试）

1. 固定 H10 胸带位置与松紧，保持 100 Hz、±4 g、完整 5 点平滑、前 100 点阈值窗口及连续 4 步确认规则；每轮只修改 A_min，开启新 Session 并完成预热后开始测试。
2. 先用 0.5 测试：自然呼吸静止 60 秒；双脚不移动、轻微转身或摆臂 30 秒；慢走、正常走、快走各 100 步。用视频人工核对真实步数，分别记录误计和漏计，不能只看二者抵消后的总数。走路结束后留出末步回落确认时间再 Stop。
3. 静止或非步行活动出现误计时，先检查对应信号，再尝试增加 `0.1 m/s²`（如 0.5 → 0.6）；轻步漏计且峰值因 A_min 门槛被挡住时，尝试减少 `0.1 m/s²`（如 0.5 → 0.4）。每次调整都复测静止和三种走速；初轮可在 0.3—0.8 m/s² 内比较，这只是试验范围，不是 SDK 限制或准确率保证。
4. 根据实际生效的 `max(σ, A_min)` 判断调整是否有用：当 σ 已大于或等于 A_min 时，继续降低 A_min 不会降低本点 H。缺口、预热、未回落、步间隔或连续 4 步筛选造成的漏计须分别检查，不能统一靠降低门槛处理。
5. 以静止尽量无误计、各走速误计与漏计都较少为选择目标；候选值选定后另做 3 轮相同测试，并补充跑步测试验证项目适用范围。记录 A_min、佩戴条件、各场景人工/App 步数、误计/漏计及走路总数误差 `abs(App − 人工) / 人工 × 100%`；静止仅报告误计数，不计算该百分比。无法兼顾的场景如实记录，不宣称已达到固定准确率。
6. 将最终采用值和实测结果回填本节；每场会话内保持参数固定。本次只确定规划，不增加调参界面、自动标定、日志存储或其他算法框架，也不改变现有中断规则。

### 5.7 实现顺序与验证

- 分块实现：预处理 → 回落确认 → 连续确认 → 步频 → 步长与距离。
- 检查单周期多尖峰只产生一个候选步；不足 0.25 秒的候选步不重复计入。
- 检查前 3 个候选步不提交，第 4 个通过后恰好补计 4 步及 3 段距离。
- 检查超时清空未确认序列、停止后步频归零、中断前后距离不跨段累加。
- 5.2 补充验收（2026-09-29 已确认、尚未执行）：前 4 个原始样本无平滑输出，第 5 个起输出完整平均；前 100 个 s 仅预热，第 101 个 s 才检测；阈值不包含当前点，同一 H 判断上穿，等待回落期间 H/L 固定。使用可控时钟检查 250 ms 刷新、五秒左开右闭窗口、不足五秒分母、四步补计保留原时间、达到两秒显示归零、系统日期变化不影响单调时间，以及显示刷新不增加步数或重置检测段；检查缺口/Retry 重新预热且累计值保留。静止误计与正常走路漏计按 5.6 从 A_min = 0.5 m/s² 开始真机核对并调整。
- 真机记录静止、原地晃动、走路和跑步；用视频核对误计、漏计和重复检测。
- 用已知距离做量级检查；记录实际误差，不预先承诺准确率。
- 采样率验证顺序：先以 100 Hz 完成静止、走路、跑步测试，用视频人工步数核对误计与漏计；之后若需减少数据量，再在相同测试条件下调整窗口并比较 50 Hz 的计步误差、处理开销及实际耗电。比较目前仅为后续建议，尚未执行；本次不改变 100 Hz 配置或增加自动降采样。

### 5.8 步骤 1.1：SDK、权限与可用状态规划

- 目标：区分蓝牙功能可用、权限未允许、蓝牙关闭、不支持 BLE 和 SDK 初始化失败，并提供对应操作。
- 前置条件：按 0.1 落实 Polar BLE SDK 8.3.0、协程相关依赖 1.10.2 和 `minSdk = 33`。
- 权限：声明并动态申请 `BLUETOOTH_SCAN`、`BLUETOOTH_CONNECT`；不通过蓝牙扫描推断位置，扫描权限使用 `neverForLocation`，本步不申请定位权限。
- 请求时机：欢迎页不请求权限；用户在 Session 首次点击蓝牙操作按钮时申请。本步使用“启用蓝牙功能”按钮，1.2 扫描入口复用同一套可用性检查。
- 权限拒绝：显示用途说明及“重新授权”按钮，不自动反复弹窗；结合申请记录及系统返回结果识别无法再次弹窗的情况，提供“打开应用设置”入口。
- 蓝牙关闭：显示“蓝牙已关闭”，提供系统开启入口，由用户确认；不支持 BLE 时显示原因并禁用相关操作。
- 状态刷新：从设置返回或页面恢复时重新检查权限和蓝牙状态；蓝牙状态回调更新界面。
- 可用条件：设备支持 BLE、两项权限均允许、蓝牙已开启、SDK 初始化成功；蓝牙可用性与设备连接状态分别显示，连接后不再提示“尚未连接设备”；不能将蓝牙可用或已连接误报为可采集。
- 初始化与释放：权限满足后，由 `PolarBleManager.kt` 使用 `applicationContext` 初始化 SDK；同一管理实例不重复初始化，不因 Compose 重组反复创建。
- 1.1 初版生命周期：`SensorActivity` 持有管理实例，Activity 销毁时释放 SDK；2.1 起改由 `SensorViewModel` 持有，按 5.10 管理连接，3.2 起的订阅生命周期见 5.13，本阶段不进行后台采集。
- 文件职责：仓库与依赖配置文件落实 0.1 配置；`AndroidManifest.xml` 声明权限及 BLE 功能；`SensorActivity.kt` 负责状态显示、权限请求和系统设置入口；`PolarBleManager.kt` 封装 SDK 初始化、状态回调与资源释放。
- 验收：允许权限后可用、拒绝后可重试、无法再次弹窗时可进入设置、蓝牙关闭及重新开启后状态更新、从设置返回后刷新、SDK 初始化失败时明确报错。
- 实际结果：SDK 初始化、权限申请与重试、设置入口、蓝牙开启及可用性状态代码已实施，并随当前项目通过 debug 构建与 lint；拒绝权限、设置返回、蓝牙切换和初始化失败等完整真机验收待完成。
- 依据：[Android 蓝牙权限](https://developer.android.com/develop/connectivity/bluetooth/bt-permissions)、[运行时权限处理](https://developer.android.com/training/permissions/requesting)、[Polar SDK 8.3.0](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/README.md)。

### 5.9 步骤 1.2：扫描与去重规划

- 目标：扫描并展示真实 Polar H10，能够停止扫描且同一设备不重复列出。
- 前置条件：1.1 蓝牙可用性检查通过。
- 设备筛选：只显示 Polar H10。
- 扫描控制：手动开始、手动停止，并设置自动停止时限；扫描中禁用开始按钮，避免重复扫描。
- 自动停止时限：每轮扫描开始后 30 秒自动停止；允许用户提前手动停止。
- 新一轮扫描：每次开始前清空上一轮结果。
- 列表字段：设备名称、设备标识、信号强度。
- 去重方式：使用 Polar SDK 8.3.0 的 `deviceId` 作为唯一标识；同一设备再次上报时更新原列表项及 `rssi`，不追加重复项。
- 扫描结束无结果：显示“未发现 Polar H10”。
- 停止后的列表：手动停止或 30 秒自动停止后，保留本轮结果供后续选择连接；下次开始扫描前清空。
- 文件职责：`PolarBleManager.kt` 管理扫描、停止、计时和设备结果；`SensorActivity.kt` 展示按钮、扫描状态和设备列表。
- 验收：真实 H10 可显示，其他型号不显示；手动停止及到时停止有效；开始新一轮后旧结果清空；同一设备反复被发现时仅更新原项；无结果时显示提示。
- 实际结果：H10 筛选、扫描启停、30 秒自动停止、按标识更新及停止后保留结果已实施，并通过 debug 构建与 lint。2026-09-27 用户反馈真机信号强度持续刷新；扫描期间的重复上报与列表更新符合实际 SDK 和项目实现，原“RSSI 不会刷新”审查结论已撤回，无需为此修改代码。
- 验证边界：用户反馈未明确刷新是否发生在连接成功后；当前连接入口会停止扫描，尚未实现独立的连接后 RSSI 监测。型号过滤、去重、手动停止、30 秒超时、新扫描清空和空结果提示仍需逐项记录真机结果。

### 5.10 步骤 2.1：选择设备、连接与状态显示规划

- 目标：选择真实 H10 发起连接，并由真实 SDK 回调确认连接结果。
- 前置条件：1.1 蓝牙可用性检查通过；1.2 已发现可选择的 H10。
- 选择方式：点击扫描结果中的 H10 开始连接，同时停止扫描。
- 连接数量：一次只连接一台 H10；暂不实现切换设备。
- 重复操作：连接过程中禁用重复连接操作。
- 连接超时：从发起连接起计时 10 秒；超时后取消未完成的连接，显示超时提示并允许重试。
- 状态显示：未连接、连接中、已连接、断开中；连接失败时显示原因。
- 状态依据：连接成功和断开完成由真实 SDK 回调确认，不因点击按钮或发出请求就显示成功；已连接不等于数据功能已就绪，后者在 3.1 检查。
- 连接生命周期（本次用户确认）：旋转屏幕保持连接；返回欢迎页、锁屏或进入后台时取消连接/断开，返回后由用户手动连接；不自动重连。
- 实现边界：取消尚未确认成功的连接请求后释放该 SDK 实例，再允许重试；这不表示收到了断开回调。已确认的连接等待真实断开回调；旧 SDK 实例的迟到回调不得影响新请求。
- 文件职责：`SensorActivity.kt` 处理设备选择和状态展示；`PolarBleManager.kt` 管理连接请求、超时取消及 SDK 回调。
- 旋转保留：`SensorViewModel.kt` 持有管理实例，避免保留旧 Activity；最终清除 ViewModel 时释放 SDK。
- 验收：点击设备后扫描停止；连接中不重复请求；成功回调后才显示已连接；失败或 10 秒超时后提示原因并可重试。
- 实际结果（步骤 2.1）：已直接修改连接与界面代码；构建结果见第 9 节；真实 H10 连接、超时重试及生命周期行为仍待真机验证。

### 5.11 步骤 2.2：已保存设备、断开与再次连接规划

- 目标：展示 App 曾成功连接的设备，支持主动断开及手动再次连接。
- 列表名称：统一使用“已保存设备”。
- 设备来源：App 曾通过真实 SDK 连接成功回调确认并保存的设备；不等同于系统配对记录，也不表示当前在线或在附近。
- 保存条件：仅在当前有效请求的真实连接成功回调被接受后保存；扫描发现、连接失败、超时、重复成功回调及取消中的迟到回调不新增记录或更新时间。
- 保存字段：设备名称、唯一标识、最近连接时间；同一设备按唯一标识更新原记录，不重复新增。
- 持久化：使用私有 SharedPreferences（saved_devices）中的 JSON（devices），字段为 name、deviceId、lastConnectedAt（成功回调时的 Unix 毫秒时间）；按唯一标识更新，按最近连接时间倒序展示，App 重启后仍保留。
- 存储职责：新增 `SavedDeviceStore.kt`；使用 applicationContext 和应用级实例，在 IO 线程串行读取、写入并检查 commit 返回值。保存失败单独提示，不改变连接状态；读取失败不覆盖原始存储；离开 Session 不取消已接受成功回调触发的保存。
- 再次连接：点击已保存设备手动连接，停止正在进行的扫描，复用 2.1 的可用性检查、连接状态和 10 秒超时规则；不要求设备先出现在本轮扫描列表中；离线失败后保留记录。
- 主动断开：点击断开后显示“断开中”；真实 SDK 断开回调确认后显示“未连接”，保留设备记录。
- 断开请求报错重试：已加入 `disconnectError` 与 `Retry disconnect` 按钮；请求抛出异常后保持 Disconnecting，蓝牙可用且权限满足时允许手动复用当前设备和 SDK 重试，发送前清除重试标记以防重复点击；真实断开回调后清除错误与重试状态，保留设备记录及旧回调防护。不增加断开超时或自动重试；请求未抛错但始终无断开回调的情况不在本次修复范围内。
- 意外断线：显示断线提示，由用户手动重连。
- 连接范围：一次只连接一台 H10，暂不实现切换设备。
- 连接生命周期沿用 5.10：旋转保持连接；返回欢迎页、锁屏或进入后台时断开，返回后手动连接；3.2 起的订阅清理与恢复规则见 5.13。
- 文件职责：`SensorActivity.kt` 展示已保存设备及断开、再次连接入口；`PolarBleManager.kt` 管理连接和断开回调，成功连接后触发设备记录保存。
- 验收：首次成功连接后有记录；重复成功连接更新原记录；连接失败不新增记录；App 重启后记录仍存在；主动断开后状态与回调一致且记录保留；再次连接成功后状态恢复；意外断线后不自动重连。
- 实际结果（步骤 2.2）：已写入设备保存、Saved devices 列表、主动断开与再次连接代码，并修复断开请求抛错后无法手动重试的问题；构建结果见第 9 节。首次保存、时间更新与去重、失败不保存、重启保留、断开重连、离线重试、意外断线及故障注入验证均待完成。
- 已知待处理问题：`SavedDeviceStore.save()` 会忽略早于已存时间戳的记录，手机系统时间回拨后，新的成功连接可能无法更新最近连接时间；仅记录代码审查发现，本次未修改，运行时复现待完成。

### 5.12 步骤 3.1：数据功能就绪与可用采样设置要求

- 目标：分别展示 HR、ACC、ECG 的就绪情况与可用设置，明确“蓝牙可用”“设备已连接”“数据功能已就绪”“正在收到数据”的区别。
- 前置条件：复用 1.1 的权限与蓝牙检查、2.1—2.2 的有效连接及旧回调防护；真机连接验收未完成时须说明，不将代码存在当作设备已验证。
- SDK 配置：固定 Polar BLE SDK 8.3.0，保留 `FEATURE_HR`，增加 ACC、ECG 所需的 `FEATURE_POLAR_ONLINE_STREAMING`；不启用与本步无关的功能。
- 就绪依据：使用真实 SDK 就绪回调与能力查询，按当前 SDK 实例、设备标识和连接轮次接受结果；收到连接成功回调不能直接将各数据类型标为可用。
- 回调解释：`bleSdkFeatureReady` 表示单项 SDK 功能已就绪；`bleSdkFeaturesReadiness` 的 ready 和 unavailable 分别表示已就绪与不支持，未出现在两者中的项仍可能稍后就绪，不能按不支持处理。
- 数据类型区分：在线采集功能就绪后查询当前设备可用的数据类型，分别判断 ACC、ECG；HR 按 HR 功能就绪情况处理。SDK 功能就绪不等于所有数据类型均支持。
- 最小状态：未连接、等待就绪、检查中、已就绪、不支持、检查失败；ACC、ECG 还需显示配置是否已确定。检查失败或尚未就绪时不能宣称可以开始对应数据流。
- 设置查询：对支持的 ACC、ECG 使用 `requestStreamSettings` 获取当前运行状态下可用的设置，展示 SDK 实际返回的采样率及其他必要参数、单位；区分“可用值”和“选定值”。HR 开始接口不接收此类设置，不虚构可选 HR 采样率。
- 配置选择（用户已确认）：ACC 固定目标 100 Hz，不支持时禁用 ACC 并显示原因，不擅自换采样率；ECG 及量程、分辨率等其他必需参数先读取设备支持值，唯一选项直接采用，多选项在获得实际值后请用户确认，不自动选择最大值。参数未定仅阻止对应流启动，不阻止其他已就绪且配置完整的流。
- 设置有效期：设置属于当前连接和设备运行状态；连接变化后清空旧结果。后续真正启动 ACC、ECG 前需重新核对当前可用设置，不能永久复用 3.1 的查询结果。
- 已确认参数补充（2026-09-28）：ACC 多选量程已确定为 ±4 g（RANGE = 4），见 5.16；其他未确认的多选参数仍等待用户确认。
- 查询与重试：查询中防止重复请求；错误按数据类型单独显示，提供手动重新检查入口；正常取消不作为错误，不自动循环重试。沿用 SDK 就绪结果，不新增未经确认的应用层等待超时秒数。
- Stop 后再次启动修复（2026-09-29）：同一有效连接已由真实回调或成功查询确认的功能就绪状态保持到断开/SDK 清理。启动与 Recheck 共用 confirmReadiness；仅未确认功能调用 isFeatureReady，避免 HR 订阅取消后通知关闭被误判为 HR/在线采集功能未就绪。ACC/ECG 仍在 Recheck 和每次启动前查询当前配置；清理连接时仍清空 readyFeatures，不跨连接复用。
- 生命周期：断开、蓝牙不可用或释放 SDK 时取消查询、清空就绪与配置；重新连接后重新检查。旋转沿用 ViewModel 中的状态，不因 Compose 重组重复查询；迟到结果不得恢复旧状态。
- 文件职责：`PolarBleManager.kt` 管理就绪回调、能力查询及结果；`DataReadiness.kt` 定义每项状态和配置选择规则；`SensorActivity.kt` 以现有 Compose 主题展示英文状态、设置和重新检查入口；`SensorViewModel.kt` 继续持有管理实例。`DataReadiness.kt` 属于底层逻辑，不是第 8 阶段待删除的临时展示文件；临时展示集中在 `DataReadinessPanel` 中。
- 本步边界：只查询与展示，不启动 HR、ACC、ECG 持续采集；不实现算法、图表、会话控制或历史存储。
- 临时展示：本步的详细就绪状态、采样设置列表和重新检查入口用于开发验收，第 8 阶段按第 6 节的清理规则删除；3.1 就绪检查不能作为已收到真实数据的证据。
- 验收：连接成功后仍能看到未就绪状态；就绪后展示实际支持类型和设置；不支持与查询失败明确区分；重复点击不重复查询；断开清空；重连重新检查；旋转不重复请求；旧连接的迟到结果无效。真机无法自然触发的失败场景可用受控测试验证，记录测试方式。
- 实际结果（2026-09-27）：3.1 代码已直接写入；启用 HR 与在线采集功能，接入两种真实就绪回调、`isFeatureReady` 手动复查、支持类型及 ACC/ECG 当前设置查询。单个查询任务防重复，各类型独立记录结果；SDK 实例、设备、连接状态与查询代次共同隔离旧结果；断开、权限丢失、蓝牙不可用及 SDK 释放时取消并清空。3.1 未启动数据流；后续 3.2 内部管理结果见 5.13。
- 配置结果边界：ACC 缺少 100 Hz 时功能可以显示 Ready，但配置被明确阻止，不能表示可采集；多选参数显示待确认及未选定值；HR 不提供采样率配置。ECG 采样率和其他参数的实际可选值尚未从真机读取，不预填文档示例值。未来开始采集前仍需复核当前设置。
- 已执行验证：`:app:assembleDebug :app:lintDebug :app:testDebugUnitTest` 成功；lint 0 errors、17 warnings；新增 `DataReadinessTest.kt` 的 4 项配置策略测试通过，另有原模板 1 项测试通过。单元测试使用受控设置值，不作为真实 H10 能力证据。
- 待执行验收：连接真实 H10，确认 Connected 与各项就绪/配置状态分开；检查可用值、单位及选定值，记录多组选项后再由用户确认；查询中重复点击不产生并发查询；旋转不重复查询；查询时断开或关闭蓝牙后状态清空，手动重连重新检查；等待旧查询结果返回时不得覆盖新连接。使用调试断点或受控故障注入检查失败后手动复查、正常取消无错误及未确定功能不被判为不支持；这些运行时检查本次均未执行。
- SDK 依据：[8.3.0 就绪回调](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarBleApiCallbackProvider.kt)、[8.3.0 在线采集与设置接口](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarOnlineStreamingApi.kt)。

### 5.13 步骤 3.2：数据订阅、错误与资源释放要求

- 目标：建立后续 HR、ACC、ECG 共用的最小订阅管理机制，使启动、取消、失败与断开清理可验证。
- 前置条件：3.1 的数据类型就绪与配置检查已接入；对应流未就绪、配置未确定、蓝牙不可用或设备正在断开时不允许启动。
- 与后续步骤分工：3.2 只提供内部启动、停止、全部清理和状态管理；4.1—4.3 分别接入真实 HR、ACC、ECG 数据接收及字段转换；4.4 再接入正式 Start/Stop 会话控制。不在 3.2 提前增加最终采集按钮或业务统计。
- 实现规模：优先在 `PolarBleManager.kt` 中管理每种数据类型的协程 Job 和状态，不引入通用框架、多层接口或占位数据模型；现有扫描 Job 与数据订阅分别管理。
- 订阅唯一性：同一连接、同一数据类型最多一个订阅；启动中、运行中或停止中均拒绝重复启动。每种数据流分别记录状态，不能使用一个全局布尔值替代各流状态。
- 状态与依据：区分未启动、启动中、接收中、停止中、已停止、失败；发出启动请求或创建 Job 不表示已经收到数据，接收状态需由当前有效订阅的数据确认。
- 停止与再启动：取消当前 Job 并完成相应清理后才允许替换订阅；清理可重复调用，旧 Job 的结束处理不能清除新 Job 或覆盖其状态。正常取消不显示为采集错误。
- 错误报告与重试（用户已确认）：标明失败的数据类型和原因，区分数据流错误与蓝牙连接错误；单路失败时其他流继续，失败流清理后由用户手动重试，不自动重试，不直接将设备连接状态改为未连接。重试前再次检查连接、功能就绪和配置；蓝牙连接整体不可用时仍清理全部流。
- 配置衔接：真实 ACC、ECG 流在 4.2、4.3 接入时，启动前复核当前支持设置并使用已确认配置；多流并行时不假设启动前后可用配置始终不变。
- 统一清理：主动断开、意外断线、蓝牙关闭、权限丢失、离开前台触发断开及最终释放 SDK 时，停止所有数据任务并禁止接受旧数据；不能只停止扫描。断开完成仍按现有连接回调规则确认。
- 旧数据隔离：每个订阅关联当前 SDK、设备和连接/订阅轮次；停止、重新连接或重新启动后，旧数据、错误和完成事件不得改变新订阅状态或未来会话统计。
- 订阅生命周期（用户已确认）：旋转保持连接和现有订阅，不重复启动；返回欢迎页、锁屏或进入后台时清理订阅并沿用既有断开规则。返回并手动重连后重新检查功能就绪，不自动恢复采集，由用户重新开始；不引入后台采集服务。正式采集入口在 4.1—4.4 逐步接入。
- 数据处理边界：本步不累计心率统计、步数或距离，不保存会话或原始数据；后续 Stop 和数据缺口处理仍按 5.6 与 4.4 的职责落实。
- 文件职责：`PolarBleManager.kt` 管理任务、错误和统一清理；`SensorViewModel.kt` 保持旋转时的实例；`SensorActivity.kt` 如需展示，仅增加英文订阅状态与错误，不提前实现完整会话控制。
- 临时展示：详细订阅调试状态仅用于开发验收，第 8 阶段删除对应展示代码；最终保留用户需要的采集/停止状态和失败提示，底层订阅管理继续使用。
- 验收方式：3.2 使用受控测试数据流验证管理逻辑，测试输入不得冒充真实 H10 数据；4.1—4.3 接入后补做对应真实数据流验收。
- 验收场景：连续两次启动只有一个任务；启动中、停止中不重复启动；停止后可重新启动；正常取消无错误提示；单路失败释放该任务、其他流继续且失败流可手动重试；断开清理全部任务；旧事件不影响新订阅；旋转不重复订阅；离开前台停止采集，返回重连后不自动恢复。
- 实际结果（2026-09-27）：3.2 已直接写入 `PolarBleManager.kt`。同文件中的内部 `DataSubscriptions` 保存 HR、ACC、ECG 各自的 Job、状态及错误；管理器提供 `startDataSubscription`、`stopDataSubscription`、`cleanupDataSubscriptions` 和只读状态。启动复查有效 SDK/设备连接、两项蓝牙权限、蓝牙开启及 3.1 的 READY/配置完整条件；实际开始执行前再次复查。未接入真实 SDK 采集调用，也未增加 UI 或测试数据入口。
- 清理实现：复用 `clearDataReadiness` 将主动/意外断线、蓝牙不可用、权限丢失、离开前台和 SDK 释放接入全部任务取消；沿用现有 ViewModel 与旋转分支，无自动恢复。停止立即拒绝旧事件；任务完成回调在主线程收尾，包含其子任务及本地清理完成后才移除占用，期间拒绝同类型重启。SDK/设备身份及任务身份共同隔离旧事件；正常取消和结束为 STOPPED，单流失败单独记录 FAILED 并允许清理后手动重试。
- 已执行验证：新增 `DataSubscriptionsTest.kt`，受控流全部位于 `src/test`；11 项测试通过，覆盖未满足前提与延迟启动复查、未收数据不算接收中、重复启动、等待取消清理、执行前取消、单路失败隔离与重试、子任务清理、重复全部清理、重连不自动恢复、旧连接数据/错误与旧订阅迟到数据隔离。连同 3.1 的 4 项及模板 1 项，共 16 项通过，0 failures、0 errors、0 skipped；debug 构建及 lint 成功，lint 0 errors、18 warnings。仅增加测试依赖 `kotlinx-coroutines-test:1.10.2`。
- 验证边界：本步证明本地 Flow 收集任务的生命周期，不证明真实 H10 已开始或停止测量。SDK 8.3.0 的 ACC/ECG `startStreaming` 在 finally 中调用 `stopPmdStreaming`，后者在独立 `apiScope` 中异步发送停止命令；本地 Job 完成不等于设备停止已确认，4.2/4.3 接入时仍须验证快速停止/重启及多流配置兼容性，不增加猜测性的等待时间。
- 待执行真机验收：4.1—4.3 接入真实流后检查收到数据才进入 RECEIVING、各类型停止/重启、单路故障不影响其他流、断开/关闭蓝牙/权限撤销后的全部清理、旋转不重复订阅、返回欢迎页/锁屏/后台后停止、手动重连不自动恢复。当前生命周期调用路径已做代码检查，受控流测试不替代上述真机结果；ACC 100 Hz、±4 g 配置与真实流已在 4.2 落实，规则和验证边界见 5.16。
- SDK 依据：已核对 8.3.0 的 `startHrStreaming`、`startAccStreaming`、`startEcgStreaming` 返回 Kotlin Flow；官方说明连接关闭、流错误或取消收集会停止相应流。HR 另有挂起的 `stopHrStreaming`；ACC/ECG 底层停止命令异步执行，不能将取消收集等同于真机已停止。[官方接口](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarOnlineStreamingApi.kt)、[8.3.0 实现](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/impl/BDBleApiImpl.kt)。

### 5.14 步骤 2.3：读取并显示设备电量规划

- 目标：连接 Polar H10 后显示设备报告的剩余电量百分比；这是设备信息，不需要启动 HR、ACC 或 ECG 数据采集。
- 前置条件：复用 1.1 的权限与蓝牙检查、2.1—2.2 的有效连接及旧回调防护；不增加权限或依赖。
- SDK：固定 Polar BLE SDK 8.3.0，初始化时保留现有功能并增加 `FEATURE_BATTERY_INFO`，通过 `batteryLevelReceived(identifier, level)` 接收电量；有效范围为 0—100%，0 是有效电量，不能作为“未知”的默认值。
- 状态与显示：`PolarBleManager.kt` 保存当前设备电量，`SensorActivity.kt` 在连接信息附近以现有 Compose 风格显示 `Battery: 80%` 等实际值；未收到有效值时显示 `Battery: --`，不使用示例电量作为实际数据。
- 更新与隔离：按 SDK 真实回调更新，校验 SDK 实例、设备标识和当前有效连接；忽略旧连接的迟到回调及范围外数值，不承诺固定刷新频率，不增加定时轮询。
- 生命周期：开始新连接、请求断开、意外断线、蓝牙不可用、权限丢失或 SDK 释放时清空电量；旋转沿用 ViewModel 中的状态。重连后等待新的有效电量，不把上一连接的值当作当前值。
- 错误边界：电量尚未收到、不可用或失败时不显示虚构百分比，不将设备连接或其他数据功能判为失败；必要提示使用英文。
- 本步范围：最小改动集中于 `PolarBleManager.kt` 和 `SensorActivity.kt`；不修改已保存设备字段，不存储历史电量，不增加低电量阈值、报警、剩余使用时长估算或正式采集控制。
- 验收：连接真实 H10 后显示回调电量；未收到时使用占位符；断开后清空；重连重新获取；旋转保持当前值；旧回调不得覆盖新连接。受控测试验证 0、100、范围外数值和迟到回调，运行 debug 构建及 lint；测试值不进入正式 UI。
- 实际结果（2026-09-27）：2.3 已直接写入 `PolarBleManager.kt` 和 `SensorActivity.kt`。保留 HR、在线采集功能并增加 `FEATURE_BATTERY_INFO`；电量回调投递到主线程后，使用同文件内的 `DeviceBattery` 校验 SDK 对象身份、当前 CONNECTED 状态、设备标识及 0—100 范围。电量独立保存为可空 StateFlow，0% 有效，无效或旧回调不覆盖当前值；UI 在连接信息后显示 `Battery: n%` 或 `Battery: --`。
- 生命周期实现：新连接前清空；复用 `clearDataReadiness` 中的清理调用覆盖主动/意外断线、蓝牙不可用、权限丢失、离开前台和 SDK 释放。沿用 ViewModel 与现有旋转分支，不因重组或旋转清空；重连后等待新有效回调。电量值与连接、就绪状态独立，没有新增轮询、权限、依赖、持久化、报警或真实数据流采集。
- 已执行验证：新增 `DeviceBatteryTest.kt` 的 7 项受控单元测试，覆盖初始未知、0/100 边界及正常更新、越界值、重复清空、重连等待新值、非连接状态、错误设备、已释放 SDK、同值不同 SDK 对象、排队回调执行时的状态变化。全部 23 项单元测试通过（电量 7、订阅 11、配置 4、模板 1），0 failures、0 errors、0 skipped；`:app:testDebugUnitTest :app:assembleDebug :app:lintDebug` 成功；lint 0 errors、18 warnings，与上次最终数量一致。首次 lint 发现新增参数顺序建议，已修正并通过最终复验。
- 验证边界：受控测试验证电量状态类，生命周期接线已做代码检查；未运行手机 UI 自动化或真实 H10 电量验收。测试数值仅在 `src/test`，不进入正式界面；构建通过不代表设备已经上报电量。
- 待执行真机步骤：① 打开 Session，连接前显示 `Battery: --`；② 连接 H10，等待真实回调显示百分比，不预设数值或刷新间隔；③ 旋转后保留当前值；④ 主动断开、意外断线、关闭蓝牙后清空；⑤ 返回欢迎页、锁屏或后台后重新进入，保持未知直到手动重连并收到新值；⑥ 撤销 Nearby devices 权限后确认电量清空（系统可能终止进程），重新授权连接后获取新值。上述项目均待完成。
- SDK 依据：[8.3.0 电量功能声明](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarBleApi.kt)、[8.3.0 电量回调](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarBleApiCallbackProvider.kt)。

### 5.15 步骤 4.1：心率接收时间、暂存与显示规则

- 目标：接收真实 HR 数据，保留心率数值、单位 `bpm` 和手机接收时间 `receivedAt`。
- 时间来源（2026-09-28 用户确认）：Polar SDK 8.3.0 的 `PolarHrSample` 没有传感器采样时间戳；4.1 使用手机接收时间，不将其表述为传感器采样时间。
- 记录时机：仅接受当前有效连接及订阅的数据；每次收到一批 HR 数据时记录一次手机接收时间，该批样本共用此接收时间，不人为构造样本间隔。
- 时间边界：接收时间包含传输和调度延迟，不能作为精确采样时间；不根据 RR 间隔反推设备未提供的心率采样时间戳。本规则仅针对 HR。
- 暂存方式（2026-09-28 用户确认方案 A）：仅保留最新心率值及其手机接收时间 receivedAt；不建立 HR 历史列表或 10 秒缓存，后续曲线所需缓存另行实现。同批包含多个样本时按顺序读取，最终保留最后一个样本及该批接收时间；相同心率再次收到时也更新接收时间。
- 等待新数据：暂时无新数据时保留最后值，同时显示最后接收时间，明确这是最新收到的值，不保证此刻仍有新数据；不增加无数据超时或自动重试。
- 清空与占位：首次收到数据前，以及停止、正常结束、失败或断线后，清空最新心率和接收时间，显示 HR: --，接收时间也使用占位符；新的有效启动前清空旧值，拒绝重复启动时不清空正在接收的数据。蓝牙或权限丢失、离开前台及 SDK 释放沿用已有清理规则，旋转保留当前值和订阅。
- 实际实现（2026-09-28）：在 PolarBleManager.kt 增加 startHr/stopHr，复用 startDataSubscription、既有 SDK/设备/任务有效性检查和 Job 取消清理。startHrStreaming 的空批次不传入订阅收集逻辑，实际收到非空 HR 批次后才进入 RECEIVING；每批调用一次 System.currentTimeMillis，按样本顺序更新 LatestHeartRate，只保留最后的 HeartRateReading(bpm, receivedAt)，相同心率的新批次也更新时间。本步不筛选心率数值、不处理 RR、不设置 HR 采样率。
- 清理实现：现有 DataSubscriptions 增加一个状态通知回调，LatestHeartRate 仅在 HR 状态变为非 RECEIVING 时清空读数；STARTING 在任务执行前清空，STOPPING 立即清空，STOPPED/FAILED 清空。重复启动在状态通知前被拒绝，不影响当前读数；结束事件先检查任务身份，旧数据也沿用原有效性检查。stopAll 复用于断开、权限或蓝牙丢失、离开前台和 SDK 释放。取消 HR 任务不请求蓝牙断开；没有独立计时器、自动重试或历史缓存。
- 界面实现：SensorActivity.kt 接入 StateFlow，新增临时 HeartRatePanel，显示 HR 状态、最新 bpm、手机最后接收时间、独立错误及 Start HR/Stop HR；开始前刷新蓝牙与权限并检查连接、HR 就绪和配置，启动/接收/停止期间禁用重复开始。移除固定的“尚未采集”说明，改为就绪与实际接收相互独立的提示。旋转继续由 SensorViewModel 持有同一管理实例；未增加正式会话控制或计时。
- SDK 核对：8.3.0 startHrStreaming 返回 Flow<PolarHrData>；官方接口声明关闭连接、流错误或取消收集会停止该流；另有 suspend stopHrStreaming。实现核对显示 HR 数据来自 observeHrNotifications。本步复用 Job.cancel 取消收集，不额外叠加停止请求；本地订阅取消不等于验证了 H10 硬件停止测量。[官方接口](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarOnlineStreamingApi.kt)、[8.3.0 实现](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/impl/BDBleApiImpl.kt)。
- 已执行验证：新增 HeartRateTest.kt 的 8 项受控测试，覆盖批次末样本与单次接收时间、相同值更新时间、空批次、重复开始保留读数、停止立即清空及清理后重启、正常结束与失败清空/手动重试、全部清理后不自动恢复、旧连接与旧任务事件隔离、其他流状态不清空 HR；复用原有 11 项订阅测试。全部 31 项测试通过，0 failures、0 errors、0 skipped；:app:testDebugUnitTest :app:assembleDebug :app:lintDebug 成功，lint 0 errors、18 warnings。首次沙箱执行被 Gradle 下载网络限制阻止；获准使用主机环境后完成上述检查。
- 待执行真机验收：① 连接佩戴好的 H10，HR Ready 后点击 Start HR，收到真实样本才显示 Receiving，数值和手机接收时间持续更新（相同数值也更新时间）；② 连续点击 Start HR 不重复订阅、不清空已有值；③ Stop HR 后心率及时间立即显示 --，设备保持连接，清理完成后手动重新开始；④ 旋转保持连接、采集及最新值，无重复启动；⑤ 主动/意外断开、关闭蓝牙或撤销权限后读数清空，重连不自动采集；⑥ 锁屏、后台、返回欢迎页后停止并断开，返回后手动连接和启动。上述真机检查本次均未执行；失败重试与迟到事件仅通过受控测试验证，真实设备故障场景仍待验收。
- 验证边界：已修改代码、通过测试和构建，不表示真实 H10 已持续上报或手机生命周期行为已真机通过。4.1 实施时未推进后续步骤；后续 4.2 结果见 5.16。后续 4.3、4.4 实施结果见 5.17—5.18；算法、图表和持久化仍未实施；测试数据仅在 src/test。
- SDK 依据：[8.3.0 PolarHrData 字段](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarHrData.kt)。

### 5.16 步骤 4.2：ACC 量程、数据缺口与暂存规则

- 目标：接收真实三轴 ACC 数据，逐样本保留传感器时间戳及原始 x、y、z（mG，包含重力），不把一次 SDK 回调当作一个样本。
- 采样配置（2026-09-28 用户确认）：保留 100 Hz，首版选择 ±4 g（SDK RANGE = 4）。启动前复核当前设备设置；确认值不可用时提示并阻止 ACC 启动，不自动改用其他频率或量程。其他必要参数继续按 5.12 的唯一值自动采用、多值待确认规则处理。
- 量程验证：±4 g 是走路、跑步实测的初始选择，不是已验证的最佳量程。验收时检查各轴是否反复接近上下限或出现峰顶截平；必要时再由用户确认调整为 ±8 g，不自动切换。
- 时间依据：使用 SDK 每个 ACC 样本的 timeStamp（纳秒，起点为 2000-01-01），逐样本并跨批次比较相邻时间；不使用手机回调接收间隔判断采样缺口。
- 缺口阈值：100 Hz 正常间隔约 10 ms；相邻样本时间差严格大于 30 ms 时，判定连续数据段中断。20—30 ms 暂不触发整段重置，不代表没有缺样；30 ms 为首版容差，需按真实时间间隔验证。
- 缺口处理：不插值、不补造样本、不因缺口自动断开蓝牙；缺口后的当前样本作为新数据段起点。4.2 仅识别分段；后续算法按 5.6 清空平滑、阈值和未确认步伐等检测状态，重新预热，保留已确认累计值，不跨缺口计算步长。
- 缺口边界：该规则在新样本到达时比较时间，不能立即识别完全停流；本次不增加无数据计时器。2 秒无步伐规则与数据缺口阈值相互独立。
- 暂存范围：运行时仅保留最新样本时间戳之前最近 10 秒的数据，窗口为 (t − 10 秒, t]，并限制最多 1,000 个三轴样本；新样本进入后移除超时或超量的最旧样本，不无限累积。窗口以传感器样本时间推进，不是等待 10 秒才开始处理。
- 暂存用途：供短段数据验收及后续处理、曲线使用；后续算法逐样本处理，不反复计算整个缓存。移除旧原始数据不扣减已经单独累计的步数或距离；保留数据的时间间隔，不把缺口两侧当作连续样本。
- 存储边界：10 秒为内存暂存，不是完整会话历史或持久化；长期运动对照、完整记录与导出不在 4.2 实现。停止后的缓存展示及保留按 5.18：保留停止快照，再次 Start 时清空。
- 实际实现（2026-09-28）：DataReadiness.kt 固定选择 ACC 100 Hz 与 RANGE = 4，任一不可用时阻止启动，不回退；其他参数仅采用唯一值。PolarBleManager.startAcc 在现有 ACC 订阅任务内部重新 requestStreamSettings，检查取消、有效 SDK/设备及连接后更新配置，再调用 startAccStreaming；每次接受的开始或重试均重新查询。配置不完整时显示原因及实际选项，等待确认；查询失败可以手动重试，配置被阻止时先用 Recheck data readiness 复查。查询/启动/接收/停止期间拒绝重复启动；ACC 任务存在时暂时禁用统一 Recheck，避免并发查询覆盖正在使用的设置。
- 缓存实现：新增专用 AccBuffer.kt，逐样本按接收顺序保存原始 timeStamp、x/y/z；跨批次比较相邻时间，在差值严格大于 30,000,000 ns 时将 gapBeforeNs 写入缺口后的样本，保留连续段边界。每个样本进入时移除时间窗口左端点及更旧样本，并限制最多 1,000 个；每批只发布一次只读列表。空批次不触发 RECEIVING；未做单位转换、重力去除、插值或算法处理。
- 启停与生命周期：复用 DataSubscriptions 的任务身份、当前连接检查与取消清理；STARTING 仅在新启动被接受后清空缓存及前一个时间戳。停止、正常结束、失败和断开保留最后缓存；重复开始不清空。停止后等本地任务清理完成再允许手动启动，旧事件不得更新新缓存。单独停止或失败 ACC 不停止 HR、不请求蓝牙断开；旋转沿用 ViewModel，其他生命周期中断复用全部清理，重连不自动恢复。
- 临时界面：SensorActivity.kt 增加 Start ACC/Stop ACC，展示订阅状态、选定频率/量程/分辨率、最新 x/y/z（mG）、传感器时间戳（ns，2000-01-01 起点）、缓存数量及缓存内最近缺口；非 RECEIVING 状态显示 Inactive snapshot，配置错误和采集错误分别显示。第 8 阶段替换临时展示；4.1 HR 行为保留。
- SDK 取消边界：已核对 8.3.0 在线采集接口及 BDBleApiImpl；取消收集进入 finally 后，stopPmdStreaming 在独立 apiScope 异步发送设备停止命令。本地 Job 完成仅表示本地任务清理完成，不表示硬件停止已确认；没有加入任意重启延迟或额外停止请求。快速停止/重启、设备是否真正停止及多流兼容性仍需真机验证。[官方接口](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarOnlineStreamingApi.kt)、[8.3.0 实现](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/impl/BDBleApiImpl.kt)。
- 已执行验证：新增 AccBufferTest.kt 的 8 项受控测试，覆盖逐样本原始字段、30 ms 边界与跨批次缺口、时间窗口左端点/长缺口、1,000 样本上限、空批次、重复开始、停止等待清理及重启清空、结束/失败/断开保留、HR 独立性、查询期间防重复和取消、旧连接及旧任务事件；DataReadinessTest.kt 调整已确认量程测试并新增 2 项，覆盖缺少 ±4 g 阻止及其他多选参数等待确认。复用原有订阅及 HR 测试；全部 41 项测试通过，0 failures、0 errors、0 skipped；:app:testDebugUnitTest :app:assembleDebug :app:lintDebug 成功，lint 0 errors、18 warnings。测试输入仅在 src/test，不作为真实 H10 数据或硬件取消证据。
- 待执行真机验收：① 连接佩戴好的 H10，确认选定 100 Hz、±4 g，Start ACC 后仅真实样本到达才显示 Receiving；核对实际可用参数、x/y/z 单位及时间戳持续推进。② 静止及改变朝向时核对含重力的三轴值；走路/跑步时用调试器查看缓存各轴是否反复接近 ±4,000 mG 或峰顶截平，当前 UI 的批次末值不能证明没有削顶，不自动换量程。③ 持续采集超过 10 秒，确认样本数不超过 1,000，调试器核对缓存首尾时间满足窗口规则；自然缺口出现时核对跨批次 gapBeforeNs，无缺口时不宣称已真机验证缺口分支。④ 连续点击、查询中 Stop、快速 Stop/Start：仅一个本地任务，停止保留并标为 Inactive snapshot，新开始清空后接收；记录 SDK 是否报告正在停止/已启动等失败，失败后仅手动重试。⑤ 同时运行 HR 与 ACC，单独 Stop ACC 后 HR 继续且蓝牙保持连接；单路失败隔离的真实故障场景待验证。⑥ 旋转不中断或重复订阅；主动/意外断线、关闭蓝牙、撤销权限、锁屏/后台后缓存停止更新，返回手动重连且不自动采集；返回欢迎页或进程结束后不承诺内存快照保留。上述真机检查本次均未执行。
- SDK 依据：[8.3.0 H10 采样率和量程](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/documentation/products/PolarH10.md)、[8.3.0 ACC 单位与时间戳](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarAccelerometerData.kt)。

- 用户真机反馈（本次补录）：用户确认此前 Start ACC 的 ERROR_ALREADY_IN_STATE 是另一个程序占用采集功能导致，解除占用后检查通过。记录为 ACC 启动检查通过；没有确认本 App 的启停缺陷，不据此新增修复，也不扩大为快速启停、削顶、缓存上限、多流并行或完整生命周期验收通过。

### 5.17 步骤 4.3：ECG 数据保留规则

- 目标：接收真实 ECG 数据，逐样本保留传感器时间戳和电压值（µV）；使用 SDK 的样本 timeStamp，不用手机接收时间替代采样时间。
- 采样依据：H10 官方 ECG 采样率为 130 Hz；启动前仍按 5.12 复核当前可用设置，唯一选项直接采用，多选项等待确认，不自动选择最大值。
- 暂存范围（2026-09-28 用户确认）：运行时保留最近 10 秒 ECG，窗口为 (t − 10 秒, t]，t 为最新样本时间戳；同时最多保留 1,300 个样本。新样本进入后移除超时或超量的最旧样本，不无限累积。
- 处理与用途：收到数据即可逐样本处理，不等待缓存填满；用于短段连续数据验收及后续实时曲线。10 秒为工程缓存选择，不是医学分析标准；后续曲线显示窗口可以小于缓存窗口，本步不实现图表。
- 存储边界：仅保存在内存，不保存整段 ECG 历史，不实现导出或完整会话回放；如后续需要持久化，另行确定。停止、失败或断线后的缓存保留与显示按 5.18：保留已停止或失败的快照，重试对应流或再次 Start 时清空相关缓存。
- 实际实现（2026-09-28）：PolarBleManager.kt 增加 startEcg/stopEcg，将 startEcgStreaming 接入现有 ECG DataSubscriptions 任务。复用连接、权限、就绪、配置完整条件及任务身份检查；每次接受的启动或重试都在任务内重新 requestStreamSettings，再按 checkedSettings 的唯一选项策略生成配置，不硬编码 130 Hz、不自动选择多选项最大值。配置不完整展示实际选项并阻止该流，等待用户确认；没有更改 HR、ACC 的选值规则。
- 查询协调：ACC 与 ECG 的启动前查询共用 currentStreamSettings 和一个 Mutex，仅串行查询、校验及发布选定设置，不锁住持续采集；等待查询也属于本流 STARTING 任务，重复启动被拒绝，Stop 可取消等待或查询。取得锁后及查询返回后检查取消和当前连接。已有统一 readiness 查询进行时拒绝新 ACC/ECG 启动；ACC 或 ECG 任务存在时禁止统一 Recheck，防止其覆盖使用中的设置。两路已就绪的流可以分别启动并同时接收，HR 不受查询锁影响。
- 样本与缓存：新增 EcgBuffer.kt，通过 h10EcgSamples 提取 SDK EcgSample，跳过其他样本类型及空批次；只有实际 H10 ECG 样本到达才触发 RECEIVING。按输入顺序保存 SDK 的 timeStamp（ns，2000-01-01 起点）及带符号 voltage（µV），无接收时间替代、转换或滤波。逐样本移除窗口左端点及更旧数据，同时限制 1,300 个样本；每批发布一次只读列表，不等窗口填满，不插值，也不套用 ACC 的 30 ms 阈值。
- 启停与生命周期：新 ECG 启动被接受时清空本流缓存，重复请求不清空；停止、正常结束、失败或连接中断保留快照。旧事件沿用原订阅身份和连接有效性防护；停止后等待本地任务清理再允许重启。单独停止/失败 ECG 不停止 HR、ACC 或请求蓝牙断开。旋转保留 ViewModel、订阅及缓存；主动/意外断线、蓝牙或权限丢失、离开前台与 SDK 释放复用全部清理；手动重连不自动恢复，ViewModel 清除或进程结束后不承诺内存快照仍存在。
- 临时界面：SensorActivity.kt 增加 Start ECG/Stop ECG，展示状态、选定频率/分辨率、最新电压 µV、样本时间戳、缓存数量及独立配置/采集错误；完整设置继续展示于 Data readiness。非 RECEIVING 显示 Inactive snapshot；启动、接收、停止期间禁止本流重复开始，查询中允许停止。不增加正式会话控制或曲线。
- SDK 核对与停止边界：8.3.0 startEcgStreaming 返回 Flow<PolarEcgData>；H10 普通 ECG 样本为 EcgSample，FecgSample 不解释为 H10 电压数据。取消收集后 SDK 在 finally 中调用 stopPmdStreaming，后者独立异步发送设备停止命令；本地 Job 完成不表示硬件停止已确认。本步未增加任意等待时间、自动重试或额外设备停止请求。[官方采集接口](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarOnlineStreamingApi.kt)、[SDK 实现](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/impl/BDBleApiImpl.kt)。
- 已执行验证：新增 EcgBufferTest.kt 的 8 项受控测试，覆盖样本子类型提取、正负及零电压、原始时间戳与顺序、空批次、不插值、10 秒窗口左端点、1,300 样本上限、重复请求不清空、停止等待清理与重启清空、正常结束/失败/停止保留、HR/ACC 失败隔离、重复全部清理与重连不自动恢复、旧任务和旧连接事件。复用现有配置策略、订阅、HR、ACC 测试；共 49 项通过，0 failures、0 errors、0 skipped。:app:testDebugUnitTest :app:assembleDebug :app:lintDebug 成功，lint 0 errors、18 warnings，与上一步数量相同。SDK 实际查询和多流设备行为仅做源码核对，受控测试不作为真机或硬件停止证据。
- 待执行真机验收：① 关闭其他占用 H10 采集的程序，连接佩戴好的 H10，核对实际 ECG 设置；唯一选项自动选择，多选项保持未确认。Start ECG 后实际样本到达才显示 Receiving，检查电压单位 µV、带符号数值和纳秒时间戳持续更新。② 连续接收超过 10 秒，确认缓存不超过 1,300；用调试器检查首尾时间戳符合 (t−10 秒, t]，不因批次拆分丢样或补样。③ 重复点击、查询中 Stop、快速 Stop/Start：不重复订阅，停止保留并标为 Inactive snapshot，新接受的启动立即清空；记录真实 SDK 停止/重启结果，失败只手动重试。④ 同时运行 HR、ACC、ECG，核对当前设置兼容性；停止 ECG 后 HR、ACC 继续且蓝牙保持连接，真实单流故障隔离仍需验证。⑤ 旋转保持采集和缓存；主动/意外断线、蓝牙关闭、权限撤销、锁屏/后台后停止更新并保留存活 ViewModel 内的快照，重连不自动恢复。以上 ECG 真机项目本次全部未执行；未实现 4.4、滤波、峰值检测、医学解释、统计、图表或存储。
- SDK 依据：[8.3.0 H10 ECG 规格](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/documentation/products/PolarH10.md)、[8.3.0 ECG 样本时间戳](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarEcgData.kt)。

### 5.18 步骤 4.4：Start/Stop 会话规则

- 控制范围（2026-09-28 用户确认）：只提供 Start 和 Stop，不实现 Pause/Resume 或恢复已结束会话。作业要求开始和停止采集；暂停不属于本项目当前范围。
- Start：有效连接、权限和蓝牙可用，且至少一路数据满足就绪及配置条件时，允许创建新会话；清零会话计时、累计指标、检测状态及实时缓存，默认尝试启动 HR、ACC、ECG。每路仍分别检查前提，不能因默认开启而跳过检查；仅连接成功或切换图表不启动采集。
- 部分流不可用：显示该流原因，其他满足条件的流可以开始；不把未启动的流显示为正在接收。失败或未能启动的流，在同一运行会话中由用户手动重试，重试前复核设置，不自动重试。
- 状态与计时起点：点击 Start 后为 Starting；当前有效订阅中任意一路首次收到真实数据后，进入 Running 并开始计时。尚未收到数据时不计时；全部启动失败或全部任务结束且无活动订阅时结束本次尝试，显示原因并允许新的 Start。不增加启动等待或无数据超时，Starting 时允许 Stop。
- 计时方式：使用手机单调时钟累计会话运行时长；它不是身体实际运动时长，也不表示三路数据每秒均完整。单路失败或短暂数据缺口不停止整场计时；停止或整体中断时立即冻结，不将异步清理耗时计入。
- Stop：立即停止接受本次会话的新数据及统计更新，冻结计时，取消全部订阅；清理期间显示 Stopping 并禁止新 Start，清理完成后显示 Stopped。保留正常蓝牙连接供下一次会话使用；单纯 Stop 不主动断开。重复 Stop 不重复结束或保存。
- 结果显示：结束后保留本次已累计结果和最后的 ACC/ECG 短时缓存，明确标为已停止；当前心率显示 --、步频显示 0，未知指标不冒充实测零值。单路失败时保留该流缓存并标明 Failed，失败的 HR 当前值显示 --；手动重试该流前清空其缓存与检测窗口，不清空其他流或整场累计值。
- 再次 Start：清理完成后新建会话，清空上一会话的实时缓存及累计状态，不恢复或续接上一会话。已有有效连接可以复用；已经断开时先由用户手动连接。
- 单路错误：其他流继续采集和计时，沿用 5.13 的失败隔离和手动重试；对应 ACC 检测窗口按 5.6 清理，保留本场已确认累计值。若全部流已失败或结束且无活动订阅，则结束会话并显示原因，不进入暂停状态。
- 整体中断：主动或意外断线、蓝牙关闭、权限丢失、锁屏、进入后台或返回欢迎页均结束当前会话、冻结计时并清理全部订阅；按既有连接生命周期断开或取消连接。返回后手动连接并 Start 新会话，不自动恢复，不跨中断计步或计算步长。
- 旋转与内存边界：旋转保留会话、连接、订阅和计时，不重复启动。结果仅在仍存活的会话持有者中保留；返回欢迎页导致 ViewModel 清除或进程结束时，不承诺内存结果仍存在。4.4 不实现进程恢复或后台采集。
- 保存时机规划：第 6 阶段实现会话结束时保存一次，包括 Stop、连接/前台中断及全部流终止；至少一个有效 HR 或真实 ACC/ECG 样本才保存；完全无数据、仅无效 HR 的尝试不保存。部分流缺失可保存，缺失字段标为未知；重复结束不重复写入。SQLite、具体字段、历史范围及四小时结束规则已在 5.23 确认，尚未实施。
- 实施边界：4.4 实现会话控制、计时及与已有数据流的衔接；统计和算法在第 5 阶段、持久化在第 6 阶段实现。尚未实现持久化时不得显示 Saved 或宣称历史已保存；10 秒缓存不等于整场记录。
- 验收规划：检查重复 Start/Stop、首个真实数据才计时、部分流启动与单路失败隔离、全部失败结束、停止后旧数据不更新结果、快速停止后新建会话、中断结束及旋转保持。保存去重和重启历史留在第 6 阶段验证。
- 实际实现（2026-09-29）：新增 SessionState.kt，SessionController 复用现有 DataSubscriptions 管理会话状态 Idle/Starting/Running/Stopping/Stopped、轮次、计时起点、经过时长及结束原因。由已有 SensorViewModel 持有的 PolarBleManager 保存，不持有 Activity、不增加另一套订阅任务或后台服务。
- Start 与轮次：管理器先检查有效连接、蓝牙及权限，并要求至少一路 READY 且配置完整；SessionController 拒绝进行中/停止中或仍有旧任务的 Start。接受后增加 generation，清空 HR 及全部 ACC/ECG 缓存（包括本轮不可用的流），重置时长及旧订阅状态，然后依次尝试三路；不满足条件的流单独记录未启动原因。initial startup 期间暂不判断全部结束，避免第一路立即失败时跳过其余流；全部尝试处理后才允许因无活动任务结束。拒绝重复 Start 不改变数据。
- 真实数据与计时：当前有效订阅的非空 HR/ACC 或 H10 ECG 样本触发 RECEIVING 后，首次从 Starting 转为 Running，且仅设置一次 SystemClock.elapsedRealtime 起点。Compose 的 LaunchedEffect 以会话轮次/状态为键每 250 ms 刷新显示，经过时长由单调时钟差值计算，不按刷新次数累加；旧轮次刷新被拒绝。无数据时为零，无超时；单流失败、缺口及新流加入不重置或冻结整场时间。HR 的手机接收时间及 ACC/ECG 的传感器时间戳保持原含义。[Android 单调时钟](https://developer.android.com/reference/android/os/SystemClock)。
- Retry 与配置：删除对外独立 startHr/startAcc/startEcg 和单流 Stop 入口，SDK 流启动方法改为私有，仅从 Start/Retry 调用。会话进行中、连接有效且对应任务已清理后允许 Retry；占用中、停止中或已结束时拒绝。重试任务执行时重新检查 SDK feature readiness；ACC/ECG 在原 Mutex 内重新查询支持类型与当前设置，单值/100 Hz/±4 g 等选择规则不变，配置不完整仍阻止真实流启动。初次就绪查询进行时提示稍后 Retry；其他流运行时可直接通过对应 Retry 重查，避免必须先停止其他流才能恢复。只有接受的本流重试清空该流缓存，其他流、会话时长和轮次不变。
- 订阅衔接：DataSubscriptions 的启动前提改为调用方提供的 canStart 判断，继续在入队和执行前两次检查；会话入口管理初始配置条件，重试可在任务内重新检查此前失败/未确认的设置，实际 SDK 流仍须通过检查后才能启动。沿用同一个任务表和旧事件防护；isCurrent 同时检查有效连接和会话 generation/可接收状态。订阅状态先更新后通知会话，最后任务清理完成时判断会话结束；现有订阅/数据测试只调整前提参数适配，不取消原有断言。
- Stop 与整体中断：Stop 先变为 Stopping、拒绝数据及 Retry、按当前单调时钟立即冻结时长并清空 HR，再取消全部任务；ACC/ECG 保留 Inactive snapshot。全部本地任务清理后才变为 Stopped，重复 Stop 不覆盖原因或时长，正常 Stop 不断开蓝牙。主动/意外断线、蓝牙或权限丢失、离开前台及 SDK release 先结束会话，再复用原清理路径；意外断线保留 SDK 原因，其他中断展示对应提示。返回手动连接并新 Start，不自动恢复；旋转沿用原 ViewModel/Activity 分支，当前实例存活期间保留状态，未实现进程恢复。
- 界面：SensorActivity.kt 用统一 Start/Stop 替换三组临时 Start HR/ACC/ECG 与 Stop 按钮，显示会话状态、分钟:秒经过时长、结束原因；保留每路状态、读数、设置及错误，增加 Retry HR/ACC/ECG。Starting/Running 可 Stop；Stopping 禁止新 Start/Retry；无可用配置时不能新建会话。未加入步数、距离、统计占位算法或 Saved 提示。
- 结束与 SDK 边界：初始尝试结束且无活动任务时，未收数据的尝试以零时长结束并提示 No data received；已收数据后全部任务结束则冻结并提示 All streams ended，各流错误单独保留。已核对 Polar SDK 8.3.0 的在线流取消行为，ACC/ECG 设备停止命令仍由 SDK 异步执行；Stopped 仅表示本地清理完成，不是硬件停止确认，未添加任意延迟或自动重试。[官方接口](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/PolarOnlineStreamingApi.kt)、[官方实现](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/impl/BDBleApiImpl.kt)。
- 已执行验证：新增 SessionStateTest.kt 的 10 项受控流/可控单调时钟测试：前提拒绝且不清空、会话外不能采集、首条样本才计时及非 tick 累加、重复 Start/Stop、停止立即冻结且不计清理耗时、清理完成前禁止重启/重试、新会话清空不可用流旧缓存、第一路立即失败不提前结束、全部启动失败零时长结束、单流 Retry 保留其他读数与计时、最后任务结束冻结、旧数据/旧计时轮次无效及中断清理后不自动恢复。复用 49 项原测试，共 59 项通过，0 failures、0 errors、0 skipped；:app:testDebugUnitTest :app:assembleDebug :app:lintDebug 最终 BUILD SUCCESSFUL，lint 0 errors、18 warnings，与上次相同。测试中中断通过会话结束入口模拟，未执行真实 Activity 旋转或设备断线测试。
- 待执行真机验收：① 连接 H10 后仅显示 Idle，点击 Start 后 Starting，首个真实样本到达才 Running 并计时；三路分别显示实际接收状态，不能以 Running 推断全部成功。② 重复 Start 不清空/重复订阅；Stop 立即冻结时间、HR 显示 --、ACC/ECG 保留非活动快照，设备保持 Connected；清理后再次 Start 全部缓存清空、时间归零，新数据才计时。③ 在 Starting 时 Stop、快速重复 Stop/Start，确认清理期间不能重启，旧事件不更新读数；记录实际 SDK 停止/重启结果。④ 部分流不可用/失败时原因独立、其他流继续；连接和设置允许后点 Retry，仅该流重查及清空，其他数据和计时不变；全部失败或结束时会话停止，无数据则时长零。⑤ 旋转保持连接、采集及经过时长，无重复启动；主动/意外断线、蓝牙关闭、权限丢失、锁屏、后台或返回欢迎页结束会话并冻结，返回后手动连接和 Start，新会话不续接旧数据。上述 4.4 真机项目全部待执行。
- 实施范围：本步仅会话控制、计时与三路既有采集衔接。没有实现 Pause/Resume、算法、统计、图表、会话持久化、历史查询、后台采集或进程恢复。原始数据单位、时间戳、ACC 缺口和缓存限制保持不变；下一开发步骤为 5.1，不能将当前本地测试结果当作真机完整验收。

### 5.19 步骤 5.1：有效心率与会话统计规则（代码及构建完成，真机待验证）

- 确认及实施日期：2026-09-29。用户采用“基础有效性检查 + 整场会话统计 + 有效样本算术平均”方案，并要求按已保存 prompt 实施。本节是开发步骤 5.1，与文档小节 5.1 的加速度准备不同；代码及自动验证已完成，真机验收待完成。
- 数据来源：继续使用 Polar SDK 8.3.0 的 sample.hr（bpm）、contactStatus、contactStatusSupported。有效条件为 `hr > 0 && (!contactStatusSupported || contactStatus)`；只有设备声明支持接触检测时才检查接触状态。有效性是本项目统计筛选规则，不代表医学准确性认证。
- 最小过滤：不另外规定 40—200 bpm 等上下限，不做突变过滤、平滑、插值或无数据超时；不使用 correctedHr、ppgQuality 或 RR 替代当前 HR 来源。
- 统计范围与公式：仅统计当前有效会话、当前有效订阅接收的全部有效样本。每个有效样本增加一次 count 和 sum，更新 min/max；`average = sum / count`，使用浮点除法。相同数值的新样本仍计入，不按值去重；不按持续时间加权，不用会话总时长作为分母，不给缺失数据补值。
- 批次与时间：逐样本按顺序处理，不能只统计每批末样本，也不能重复遍历旧读数或在重组/计时刷新时重复累加。每批记录一次手机 receivedAt，沿用 4.1；不虚构 HR 传感器时间。空批次不改变读数、提示、统计或启动计时。
- 当前显示：最后一个样本有效时，显示该值及该批 receivedAt，并清除先前的无效样本提示；最后一个样本无效时，当前 HR 与对应接收时间均为 --，已有统计保持不变，不回退到该批更早的有效值。支持接触检测且无接触时显示 No sensor contact，否则对非正值显示 Invalid HR sample；提示与订阅错误分开。暂时没有新数据时保留当前显示和时间，不假设读数仍在刷新。
- 会话计时衔接：收到非空 HR 批次仍可表示订阅 Receiving，但只有该批包含实际有效 HR 样本时才可由 HR 触发 Starting → Running；全部无效的 HR 批次不能启动会话计时。混合批次中的有效样本可以启动计时，即使该批末样本无效导致当前 HR 显示 --。ACC/ECG 的真实样本仍可独立启动计时。已经 Running 后无效 HR 不暂停或重置计时，不影响其他流；不新增超时。
- 生命周期与重试：HR 停止、正常结束、失败或 Retry 时清空当前 HR、接收时间及临时无效样本提示，保留本场 HR 累计统计；仅接受的 Retry 可清空当前显示，拒绝重复请求不得清空或累计。Retry 后的有效样本继续本场累计；Stop、断线及其他整体中断冻结统计并保留结果，旧事件不得更新。接受新 Start 时清零 count/sum/min/max（即使该场 HR 不可用），旋转保留；不承诺进程结束或 ViewModel 清除后保留内存结果。
- 显示精度：当前、最小、最大心率显示整数 bpm，平均值以一位小数显示，四舍五入仅在展示时进行。界面说明平均值为有效样本算术平均（Mean of valid HR samples）。count 为 0 时统计显示 --，不显示虚构的 0 bpm；首个有效样本到达后 min/max/average 均等于该值。
- 内存与范围：仅保留最新可空读数、无效样本提示及 count、sum、min、max 等固定数量的累计状态，不增加 HR 历史缓存、通用统计框架或新依赖。保留第 4 阶段的 ACC/ECG 配置、单位、缺口和缓存规则；不推进步伐算法、速度、心率区间、曲线、持久化或 History。
- 验收要求：受控测试覆盖零/负值、支持及不支持接触检测、有效与无效混合批次、批次末无效、相同值重复计入、空批次、count 为零与首个样本、均值精度、重复请求、Retry 保留累计、新 Start 清零、Stop/断线冻结、旧事件及旋转保留接线。验证 HR 全无效不启动计时、有效 HR 或 ACC/ECG 可启动计时、已运行后无效 HR 不停止计时。例：有效接触下输入 80、80、0、100，count=3、min=80、max=100、average≈86.7，当前 HR=100；再收到 0 后当前 HR/时间为 --，统计不变。执行相关单元测试、debug 构建及 lint，真机项目未执行时标记待验证，不把此前 59 项测试作为 5.1 已通过的证据。
- 文件与实现方向：在现有 HR 接收路径中处理每个样本并维护最小累计状态；将订阅 Receiving 与有效样本触发会话计时区分，复用现有会话轮次和任务隔离。SensorActivity.kt 增加必要统计及无效样本提示，沿用 Compose 主题与英文 UI/注释；不因 Compose 重组触发统计处理。具体修改前检查当前代码。
- 第 6 阶段边界：会话保存仍未实施；2026-09-29 已确认仅无效 HR 且没有 ACC/ECG 的尝试不保存，保存资格与 Running 触发条件一致，见 5.23。
- SDK 字段依据：[PolarHrData 8.3.0](https://github.com/polarofficial/polar-ble-sdk/blob/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarHrData.kt)。
- 实际实现（2026-09-29）：修改 PolarBleManager.kt 中现有 LatestHeartRate，新增 HeartRateStatistics（Long count/sum、可空 min/max、浮点 average）和单独的无效提示流；receive 按顺序处理全部样本，返回本批是否包含有效 HR，批末决定当前读数，未建立历史缓存。clear 只清当前读数和提示，reset 在接受新 Start 时清全部统计；Retry/完成/失败清当前值但保留累计，原订阅/会话身份检查阻止旧数据更新。没有增加依赖、通用框架或参数上限。
- 会话与界面接线：SessionState.kt 将 onValidData 与订阅状态通知分开；只有有效 HR 批次或实际非空 ACC/ECG 数据触发计时，订阅状态仍负责全部任务结束判定。SensorActivity.kt 的现有 HeartRatePanel 显示当前/最小/最大/平均 bpm、接收时间和单独无效提示；均值一位小数，无有效统计显示 --，继续复用 SensorViewModel，旋转无需重建统计。
- 本步自动验证：扩展 HeartRateTest.kt 和 SessionStateTest.kt，分别新增 5 项测试，并加强原测试中的统计断言。执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug 成功；共 69 项测试（HR 13、会话 15、ACC 8、ECG 8、订阅 11、电量 7、配置 6、模板 1），0 failures、0 errors、0 skipped。lint 0 errors、18 warnings。首次沙箱构建因 Gradle 下载网络权限受阻，随后通过权限流程在主机环境构建成功；以上结果来自本次重新执行。
- 真机待验证：① 连接佩戴 H10、Start 后首次有效 HR 建立 min/max/mean，后续均值在最小与最大之间，界面英文和单位正确；② 能复现接触变化时检查无效提示、当前值 --、已有统计保留，恢复后继续累计，不强求设备必然上报无接触状态；③ HR 故障且其他流仍活动时 Retry，统计保留并继续；④ Stop 清当前值且冻结统计，新的 Start 立即清零，包括 HR 不可用的会话；⑤ 旋转保持读数、统计和计时，断线/锁屏/后台结束会话并冻结，返回后手动新建；⑥ HR 全无效不启动计时、混合批次和迟到事件已用受控测试覆盖，真机未自然触发时不得标为设备通过。本次未安装或操作真机，全部运行时项目仍待完成。

### 5.20 步骤 5.3：步长、速度、距离与简单显示（5.3add 已实施，真机待验证）

- 最新实施结果（2026-09-30）：已按两份 prompt.md 中的 5.3add 及本次用户完整指令实施。先检查 5.2a—5.2d 和会话/订阅路径，代码前置条件满足；自动验证通过，真机待验证，证据见第 9 节。

- 确认日期：2026-09-29。沿用 `note/551a40924.docx` 中的步频和 Weinberg 步长公式，以及 5.1—5.7 后续确认的检测规则；笔记未明确速度统计口径，本节补充用户已采用的方案。本节是开发步骤 5.3，不是文档小节 5.3 的连续步伐确认。依赖 5.2a—5.2d 的有效步伐输出，实施前检查依赖，不自动推进未完成步骤。
- 最小界面：先在现有 Compose 页面用英文文本显示当前步频、估计移动速度、平均速度、最大步频、最大速度及必要的距离/状态信息；不在本步设计正式布局或曲线。步频单位 steps/min，速度单位 km/h；速度内部用 m/s 计算，显示时乘 3.6。沿用步频整数显示，速度显示一位小数，仅展示时舍入，标签明确 Estimated speed / Estimated distance。
- 步长与距离：复用 5.5 的 `Li = 0.45 × (s_max,i − s_min,i)^0.25`，仅使用同一连续段相邻接受候选峰之间的平滑数据、包含两端；连续四步确认前缓存，通过后补计四步但仅有三段可计算步长。每段第一步不补造步长；未确认步长不累加，已确认步长只提交一次。每个 Li 按后一个峰的原始传感器时间归入速度窗口，不能按补计发生的手机时间归入。
- 当前值：步频复用 5.4。速度为 `(t − W, t]` 内已确认 Li 之和除以 W，W 为 5 秒或当前连续检测段预热结束后的实际时长（不足 5 秒时），且 W > 0；无可计算时长时不做除法。复用新确认步伐和每 250 ms 的显示刷新及 5.4 时间映射，不增加另一套定时器；计时刷新不能重复累计距离。正常预热/首次四步确认前显示 0 并附状态；ACC 持续可用且距最后确认峰达到 2 秒时，当前步频和速度显示 0。保留两秒无步归零规则，即使五秒窗口中仍有更早步长。
- 平均速度：`averageSpeed = 本场累计估计距离 / 本场 Running 经过秒数`，包含静止、休息时间；Starting 不计时，Stop 后冻结。复用现有 SystemClock.elapsedRealtime 会话时长，不按速度 UI 刷新值求算术平均，也不只除以移动时间。尚未 Running 或分母为零时显示 --；ACC 正常可用且已 Running、尚无距离时可显示 0。当前项目只提供 Start/Stop，不增加手动暂停或静止自动暂停。例如记录 100 米、运动 80 秒后静止 20 秒，平均为 1 m/s，即 3.6 km/h。
- 最大值：分别保存本场最大步频和最大估计速度；仅用完整、连续、无已知 ACC 缺口的 5 秒窗口结果更新。从本连续段预热结束的传感器时间起算，真实 ACC 样本时间覆盖满 5 秒才有首个合格窗口；缺口或检测段重建后重新起算。窗口端点使用实际处理到的 ACC 样本时间，不由手机显示时钟外推生成或补足窗口。窗口不足 5 秒时允许显示当前值，但不更新最大值；首次合格窗口前显示 --，合格静止窗口可产生 0。最大值比较原始五秒统计结果（已确认步数 × 12、已确认步长之和 / 5），不使用两秒无步规则强制归零后的显示值；两秒归零只控制当前显示。使用未舍入结果比较，已有最大值在停止或缺口后保留。这里表示整场五秒窗口结果的最大值，不是单步瞬时峰值；不同时增加 1/3/10 秒等其他窗口。
- 缺口与恢复：样本时间差严格大于 30 ms 时复用 5.16 分段；清空当前平滑、阈值、未确认步伐、峰间、步频和速度窗口及显示时间映射，保留本场已确认步数、距离和最大值。不插值、不补步、不跨缺口计算步长。当前值显示 -- 和重新预热原因；新段完成预热后恢复 5.4 的等待确认/正常显示，满连续 5 秒后才再次参与最大值计算。缺口本身不停止其他流或整场计时。
- ACC 不可用或订阅失败：当前步频/速度显示 -- 及原因；沿用手动 Retry，接受后重新预热和确认连续步伐，保留本场累计统计。没有实际 ACC 观测时不以 0 宣称静止；普通批次间等待沿用现有时间规则，不另加停流超时或自动重试。完全无 ACC 观测的会话中，速度统计显示 --。
- 不完整统计：会话内已识别 ACC 缺口、不可用或失败后，标记 `Incomplete ACC data`，恢复后仍保留到本场结束；正常初始算法预热本身不作为数据故障。已有累计距离和平均速度仍可显示，平均继续使用已记录距离除以完整 Running 时长；不减掉缺失时段、不推算丢失距离。明确遗漏距离可能使平均速度偏低，不把缺失解释为静止；最大值仅代表已记录的合格窗口。缺失不清除之前已取得的统计，无有效 ACC 观测时不生成数值统计。
- 生命周期：Stop/整体中断后显示 Stopped；本场曾收到正常 ACC 样本时，当前步频和速度显示 0；本场从未收到 ACC 样本时，当前值及相关统计继续显示 --，不能因停止生成 0。这是对 5.4、5.6 停止归零规则的无观测例外。累计统计和平均速度冻结，保留缺失标记；不把停止状态下的零作为新的最大值。接受新 Start 才清空全部本场统计、窗口和缺失标记；拒绝重复操作不得清空或累加，旋转保留状态，旧会话事件不得影响新会话。继续沿用返回欢迎页、锁屏、后台和断线结束会话的规则，不恢复旧会话。
- 实施和验收：使用最小累计状态及有界步伐/步长窗口，复用现有会话、订阅和步伐处理路径，不增加通用统计框架。受控测试覆盖步长区间、四步补计但三段距离、按原时间归窗、五秒左开右闭边界、短窗口、两秒归零、完整窗口最大值、静止计入平均、30 ms 边界、缺口不跨段、不完整标记、Retry 保留累计、新 Start 清零、Stop 冻结及旧事件。运行相关测试、debug 构建和 lint；真机检查静止/走路/跑步、已知距离、休息后平均降低、恢复后的重新预热、旋转和停止。准确率与设备验收须实测记录。
- 边界补充验收（2026-09-30 受控测试通过，真机待执行）：预热结束后真实 ACC 只覆盖 4.99 秒时，即使手机显示时间已超过 5 秒也不更新最大值；真实覆盖达到 5 秒才允许更新。两秒无步时当前显示为 0，但最大值仍按合格窗口内的原始统计比较。有 ACC 观测后停止显示 0，整场无 ACC 后停止仍为 --，两者都冻结累计结果。
- 实施：新增 StrideLengthEstimator.kt，仅暂存最近两秒且最多 201 个平滑点；沿用每个真实样本上“超过两秒重置检测段”的顺序，保留合格峰间区段两端。StepSequence 在接受候选峰后才计算可空步长，拒绝峰不移动起点；原有待确认列表随峰缓存步长，第四步一次提交四步和三段距离。确认回落后的点留给下一区段，不混入已结束区段；新段第一峰步长为空，真实等值峰间步长可为零。
- 实施：CadenceWindow 复用已确认峰列表携带步长，不新增定时器或完整历史；StepDetector 是步数、距离、当前/最大速度、最大步频及 ACC 观测/不完整状态的唯一统计持有者。StepState 提供只读结果及按完整 Running 毫秒计算的平均速度；PolarBleManager 从现有会话计时取得分母，整体结束先读取已结算时长再清理步伐，清理耗时不进入平均值。缺口/Retry 保留累计和最大值，已识别缺失标记保持至新 Start；正常初始预热不标记故障。SensorActivity 仅增加英文验证文本，速度显示一位小数 km/h。
- 当前状态与边界：仅完成 5.3add，未新增依赖、SessionRecord、数据库、平均/最小步频、心率区间或曲线。100 Hz、A_min、0.25—2 秒、四步确认、现有 HR/ACC/ECG API 和 ViewModel 旋转保留路径不变。步长 K = 0.5 仍为未校准估计；段首没有距离、缺失数据不补算，不能以单元测试/构建证明实际距离准确率或手机生命周期已验收。

### 5.21 步骤 5.4：固定心率区间、心率强度标签与时长柱形（5.4add 已实施，真机待验证）

- 最新实施结果（2026-09-30）：用户要求“5.4 的 prompt 和实现”，按当前 5.4add 入口完成本节，前置 5.3add、5.1 有效 HR 和会话/订阅代码已检查齐全；代码及本轮自动验证完成，真机待验证，见第 9 节。

- 命名（用户已确认）：统一称为“心率强度”，英文界面使用 `Heart rate intensity`。仅表示当前有效 HR 所属的固定档位，不表示 ACC 已确认正在运动；五档阈值与区间时长统计规则不变。
- 命名 Fix 核对（2026-09-29）：已检查 app/src 下源码与资源，当前只有 HR 读数和统计，没有强度标签或区间功能，因而无可替换的代码文案；未新增占位 UI，5.4 仍未实施。已在根目录 prompt.md 的 5.5 后追加“第 5 阶段 Fix：心率强度命名”的中英文提示词及实施记录，并同步两份 AGENTS.md。本次仅修改文档，未运行测试、构建或真机验证；实际英文标签待 5.4 实施后验收。

- 用户已采用以下方案。本节是开发步骤 5.4，与文档小节 5.4 的当前步频不同；先检查 5.1 的有效 HR 筛选和现有会话生命周期，不自动补做其他步骤。方案初次确认仅改文档，当前实现结果见本节末尾。
- 固定区间：复用 5.19 的有效条件 `hr > 0 && (!contactStatusSupported || contactStatus)`，无效值不进入任何区间。下限包含、上限不包含，110、125、140、155 分别归入 Zone 2、3、4、5。阈值是用户指定的项目分档，不是个体化训练区间；不增加年龄、最大心率、静息心率或个体校准。一般训练区间依赖个体最大心率等因素，来源说明见 [AHA 心率区间说明](https://www.heart.org/en/healthy-living/exercise-and-physical-activity/fitness-basics/target-heart-rates)。

| 区间 | 有效 HR 范围（bpm） | 英文心率强度标签 | 柱形与标签颜色 |
|---|---|---|---|
| Zone 1 | HR < 110 | Very light | 绿色 |
| Zone 2 | 110 ≤ HR < 125 | Light | 蓝色 |
| Zone 3 | 125 ≤ HR < 140 | Moderate | 黄色 |
| Zone 4 | 140 ≤ HR < 155 | High | 橙色 |
| Zone 5 | HR ≥ 155 | Very high | 红色 |

- 配色更新（2026-10-01）：用户确认低到高为绿、蓝、黄、橙、红，具体颜色见 5.28。此前实施记录中的蓝/绿顺序是历史版本；8.0 已将源码改为现行配色，当前标识及 Session/History 五柱共用同一定义，实际检查边界见 5.28.1。HR 阈值、标签、计时和统计规则不变。

- 当前标签：仅用最新批次的最后一个样本及其有效性确定后续区间，例如 `Heart rate intensity: Moderate · Zone 3`。不增加平滑、延迟切换或滞回；在边界附近允许随新 HR 切换。无有效 HR、HR 不可用或结束时显示 `Heart rate intensity: --`，同时保留已有接触不良/错误/停止提示。颜色与柱形一致，同时保留标签和区间文字，不只靠颜色传意。
- 时间口径：采用最近有效读数保持法，统计当前 Session 的 Running 时段。每个非空 HR 批次额外记录一次 `SystemClock.elapsedRealtime()` 作为本批接收的单调时间，用于时长差；4.1 的 receivedAt 仍是手机日期时间，不改变其含义，不以它或 RR 推导区间时长，不伪造 HR 传感器时间。有效批末样本从该批接收时刻开始占用其区间，下一状态事件先结算旧区间再更新状态。例如单调时间第 10 秒收到 120、第 13 秒收到 130，则这 3 秒计入 Zone 2，之后计入 Zone 3。
- 批次边界：同一批各样本没有独立接收时刻，不平均分配批次时长或按样本数计时。末样本无效时，从本批接收时刻开始不属于任何区间，即使同批更早样本有效；这些有效样本仍按 5.19 参与心率统计，且可按既定规则触发会话 Running。空批次不改变区间或计时状态。相同 HR 的新批次照常结算并继续同一区间，不按数值去重、不重复累计。
- 会话起点：若由 ACC/ECG 先触发 Running，首次有效批末 HR 到达前的时间为未归类；不得把该段追溯分配给后来出现的区间。Starting 不计入任何区间；HR 触发 Running 时区间起点不得早于本场 Running 起点。
- 结算与显示：仅维护五个累计毫秒数、当前可空区间及其计时起点，复用会话轮次和现有 250 ms 刷新。数据、无效值、结束及停止事件均先结算旧区间至事件时刻，再更新区间/计时起点；每个时间段只提交一次。UI 刷新只用“已累计值 + 当前未结算时长”生成显示快照，不写回累计值、不固定加 250 ms、不另建统计定时器。区间总时间不得超出同一时刻的 Running 时长。
- 无效与恢复：收到无效 HR、无接触、HR 订阅失败、正常结束或接受 Retry 时，先结算旧区间到该事件时刻，然后清除当前区间/计时起点；本场五区间累计值保留。恢复后由新的有效批末样本重新开始，不补算中间空白；拒绝的重复 Retry 不清空、不结算第二次。单路中断不停止其他数据流或整场计时。
- 静止与无新批次：用户静止但 HR 有效时照常累计，不依赖 ACC 或步伐决定心率强度/计时。没有新 HR 批次且未收到无效或结束通知时，继续按最后有效区间估算，不增加无数据超时、自动重试或新的新鲜度限制。已知限制是静默停流可能使旧区间时长偏多；界面说明 `Estimated from received HR`，不能将这些时长声称为逐秒真实观测。
- 未归类时间：`Unclassified time = 本场 Running 时长 − 同一时刻五区间显示时长之和`。包括首个有效 HR 前及已知无效/中断的时间；恢复后不回填。单独用文字显示，不增加第六根柱形，也不强行让五根柱形总时长等于会话时长。完全没有有效 HR 时五区间均为 00:00，显示 `No valid HR data`，Running 时间全部未归类；未 Running 时均为零。
- 柱形展示：5.4 先实现简单 Compose 五柱图和标签，第 8 阶段再整合正式布局，不增加图表库。横轴固定 Zone 1—5 并附 bpm 范围；纵轴为时长，五柱使用同一比例尺，随最大累计时长统一调整，不能每柱各自归一化。各柱显示 mm:ss，内部保留毫秒精度，仅显示时取整秒；未进入过的区间为 00:00、柱高为零。标签及文字需可读，颜色沿用表中分档。
- 生命周期：Stop、断线、蓝牙/权限丢失、后台、锁屏、返回欢迎页或 SDK 释放导致会话结束时，结算至会话停止时刻并冻结柱形和未归类时间，不计本地清理耗时；当前心率强度为 -- 并附停止状态。新 Start 被接受时清空全部区间统计，重复请求不重置或重复累计；旋转保留，旧会话或旧订阅事件无效，返回后按现有规则手动连接和创建新会话。HR Retry 保留累计，绝不续计重试空白时段。
- 验收：受控测试覆盖各阈值边界、无效 HR 不归区、相同值重复批次、混合批次末样本、空批次、先由 ACC/ECG 启动、切区结算、无效/正常结束/失败/Retry 后恢复、静止累计、无新批次保持、刷新不重复计时、系统日期变化不影响时长、区间合计与未归类时间、Stop 不含清理耗时、新 Start 清零及旧事件。运行相关单元测试、debug 构建和 lint；真机核对标签、柱形和时长、静止、可复现的接触变化、停止/重试、新会话、旋转和断线。不能自然触发的边界用受控测试记录，不要求用户为测试达到指定高心率。
- 实施（2026-09-30）：新增 HeartRateZones.kt，保存五个累计毫秒数、可空当前区间、计时起点及有效 HR 观测标记，通过只读 HeartRateZoneState 暴露五区间显示时长与同一时刻未归类时间。直接消费 LatestHeartRate 的已验证批末 reading 和 receive 返回的“本批含有效样本”标记，不重复实现有效性或 HR 最小/最大/平均统计。事件先结算再更换锚点；250 ms 刷新仅生成含未结算段的快照，不写回累计、不按刷新次数加时长、不新增定时器。静止与无新批次时继续保持最近有效档位；无效/失败/正常完成/接受 Retry 清当前档位，保留累计。
- 接线（2026-09-30）：PolarBleManager 为非空 HR 批次捕获一次计时用 elapsedRealtime，并保持 receivedAt 日期含义；SessionController 的 onValidData、refresh 和 onSubscriptionState 接受同一事件时刻，避免 HR 起点早于 Running 或最后流完成时的两次取时差。整体结束回调先按已冻结会话毫秒结算区间，再清 HR/步伐；重复结束/清理回调不重复增加，清理时间不进入统计。区间状态随现有 ViewModel 管理器保留，旧会话/旧订阅保护沿用原入口。
- 显示（2026-09-30）：新增 HeartRateZonePanel.kt 并在 SensorActivity.kt 收集只读状态。当前标签 Heart rate intensity、五根蓝/绿/黄/橙/红柱、Zone 1—5 和 bpm 范围、统一随最大值调整的时长纵轴、mm:ss 数值及 Unclassified time 均为简单英文验证显示；无有效样本提示 No valid HR data，停止显示 --/Stopped。显示 Estimated from received HR 及静默停流可能高估的说明。UI 不修改统计，正式布局仍留第 8 阶段。
- 5.4add 的范围与局限：本步仅负责区间计时和柱形；后续 5.5add 实施结果见 5.22，第 6 阶段仍未实施。5.4add 未增加依赖、HR 历史缓存、通用框架、个体化训练算法、警报、Pause/Resume、曲线、持久化或 History，未改 ACC/ECG/步伐与速度算法。固定档位不表示识别到运动或个人训练建议；时长是接收数据保持估计，静默停流可能高估旧档位。5.4add 当轮未安装 APK 或操作手机，自动验证不代表真实 UI、接触变化或旋转已验收。

### 5.22 步骤 5.5：曲线数据、横轴与有界缓存（5.5add 已实施，真机待验证）

- 最新实施结果（2026-09-30）：已按两份 prompt.md 的 5.5add 实施本节。5.3add/5.4add、真实三流路径及现有会话代码前置齐全，已直接修改文件并完成本轮自动检查；真实绘图、性能及设备生命周期待验证，见第 9 节。

- 用户已采用以下方案。本节是开发步骤 5.5，与文档小节 5.5 的估计步长不同；先检查真实采集、5.1 有效 HR、5.2—5.3 步频/速度输出及会话计时，不自动补做其他步骤。初次方案记录只改文档，当前实施结果见本节末尾。
- 数据层次：原始采集、算法处理、显示点选择、界面刷新分别处理。点数限制是内存上限，不是把所有数据压缩到固定点数；不足时不补点。仅 HR 合并同一秒的显示点，保留末点而非取平均。步频/速度定期记录已计算结果，ECG 不合并、不平均、不隔点抽取；原始样本仍完整进入既有处理路径，图表不反向改变 HR 统计、区间时长、步数、距离或最大值。

| 曲线 | 显示窗口 | 保留点数与数据来源 | 界面刷新上限 |
|---|---|---|---|
| HR | 最近 60 秒 | 最多 61 点；每个会话秒保留最后一个真实批次末样本对应的显示记录 | 每 250 ms |
| 步频 | 最近 60 秒 | 最多 241 点；每 250 ms 记录一次当前计算结果，不额外合并 | 每 250 ms |
| 速度 | 最近 60 秒 | 最多 241 点；与步频同时记录当前计算结果，不额外合并 | 每 250 ms |
| ECG | 最近 5 秒 | 复用最近 10 秒、最多 1,300 点的原始缓存；130 Hz 下五秒约 650 点，显示窗口内全部样本 | 每 100 ms |

- 窗口与上限：运行时以本场经过时间作为右端，保留/绘制 `(t − 窗口时长, t]`，会话不足窗口长度时只显示已有数据。HR 的 61 点容纳跨整数秒边界的桶，步频/速度 241 点容纳边界点；超时或超量均删除最旧显示记录，断段信息随点保存，不无限保存独立标记。ECG 原始缓存仍按 5.17 的最新传感器样本时间和 1,300 点上限裁剪，绘图仅取其中映射后落入五秒视窗的样本，不额外复制一份长期原始缓存。ECG 约 650 点来自当前 130 Hz，不硬改设备设置；实际显示数随已确认采样率及缺口变化，仍受现有原始缓存上限约束。
- HR 取点：以会话经过时间的整数秒分桶，同一桶用最后一个真实批次末样本替换，保留该批实际接收时刻，不将 x 改成整秒。沿用 5.19 批末有效性；无效批末样本产生断段/无值记录而非 0。没有新 HR 批次时不生成新点、不用旧 HR 反复填满曲线，相同数值的新批次仍可形成真实新点。已知无效、结束等断段不得被后来的桶内替换抹掉；下一有效显示点带断段标记。每秒末点可能省略秒内短暂变化，但不影响独立累计统计。
- 步频/速度取点：复用现有 250 ms 计算/刷新入口，以当前会话经过时间记录一次结果，不因每次候选步、Compose 重组或切图额外追加，不在绘图时重算算法或历史数据。它表示当时输出的估计值，不是原始 ACC 波形；峰间隔、步长、30 ms 缺口等算法仍使用原始 ACC 时间戳。预热、不可用、缺口恢复阶段不绘制有效值，留空并保留状态；正常 ACC 下确认静止的零值可正常绘制。
- 横轴与手机时间：三类曲线统一显示本次 Running 开始后的经过时间，刻度 mm:ss。HR 的 x 为 `(批次 elapsedRealtime 接收时刻 − 会话 Running 起点) / 1000`；步频/速度的 x 为本次结果记录时的会话经过秒数。沿用单调时钟，不以系统日期时间计算横轴；既有 receivedAt 日期时间字段保持原含义。手机系统日期改变不能移动曲线。[Android 单调时钟说明](https://developer.android.com/reference/android/os/SystemClock)。
- ECG 时间映射：每次有效订阅首个非空批次建立一次锚点，S0 为首批最后样本的传感器时间（ns），P0 为该批手机 elapsedRealtime 接收时间（ms），T0 为会话 Running 起点（ms）；`x秒 = (P0 − T0) / 1000 + (sample.timeStamp − S0) / 1_000_000_000`，用浮点秒数、先做整数时间差。保留每个样本原有相对间隔，不把整批画在同一时刻；单次订阅内不逐批重新移动锚点或已绘制点。Retry 后重新建锚点但 T0 不变；映射到会话零点之前的样本不绘制，不挤到零点。这是含传输延迟的近似显示对齐，不声称 HR、ACC 和 ECG 精确同步，不增加时钟同步框架。SDK 的带符号 µV 与纳秒时间保留原义：[PolarEcgData 8.3.0](https://raw.githubusercontent.com/polarofficial/polar-ble-sdk/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarEcgData.kt)。
- HR 断段：无效 HR、无接触、订阅正常结束或失败时断段；连续两次真实非空批次接收时间差严格大于 3 秒时，下一有效点不与前段相连。该判断使用取点合并前的真实接收记录，不使用压缩后点间距。3 秒仅是绘图连线规则，不停止订阅、不清零心率或修改 5.21 最后区间继续估算时长的规则；没有新数据时曲线只到最后真实点，不延长水平线。
- 步频/速度断段：ACC 已识别的严格大于 30 ms 的缺口、失败、正常结束、Retry 或算法检测段重建时切断曲线；重新预热时留空，恢复有效计算结果后开启新段。只改变显示分段，不额外触发算法重置、不跨缺口连线、不将缺失填成零。
- ECG 断段：跨批次比较原始相邻样本时间差，严格大于已确认采样周期的 3 倍时断段，即 `Δt > 3 / sampleRate` 秒；130 Hz 下约 23.1 ms。这是绘图工程阈值，不是医学标准，不套用 ACC 的 30 ms 算法规则，不修改原始样本或触发断开/重试。订阅结束、失败或 Retry 同样结束旧段。所有曲线只在同一有效段内连线，不跨段插值；ECG 首版不做显示降采样或平滑。
- 刷新与切换：HR、步频/速度最多每 250 ms 刷新画面；ECG 可见时最多每 100 ms 刷新，刷新只是重画已有样本，不把 ECG 采样率变成 10 Hz，不每个样本触发整页重绘。切图不重启订阅；未显示的图继续更新有界数据但不持续绘制，返回时显示最新窗口。三类图切换，步频/速度区域内部用按钮选择其中一项，不共用不同单位的纵轴；纵轴分别为 bpm、steps/min、km/h、µV，ECG 保留正负值。
- 停止与 Retry：单路失败或正常结束时保留并冻结该曲线视窗，标记非活动/失败快照；其他流继续。接受 Retry 时清空对应曲线及显示分段/锚点，ACC Retry 同时清空步频与速度曲线，HR Retry 清空 HR 曲线，ECG Retry 沿用原始缓存清空；本场累计统计按原规则保留，横轴仍是原会话经过时间，新结果不连接旧段。被拒绝的 Retry 不清空。普通缺口只断段，窗口内旧段仍可见。
- 整体生命周期：Stop 或整体中断冻结视窗与数据，不追加人为归零点，不继续滚动到空白；当前指标的 Stop 归零规则不生成图表样本。新 Start 被接受后清空全部曲线；旋转保留数据/锚点/选择状态，不因重组追加点或重建订阅，旧会话/旧订阅事件不能更新新曲线。只保证现有 ViewModel 存活期间的内存状态，不增加进程恢复、持久化或历史回放。
- 实施范围与验收：5.5 完成曲线数据、时间映射、有界缓存和简单绘图验收，第 8 阶段整合正式布局；不增加图表库或通用流/缓存框架。受控测试覆盖每秒末点非平均、无新 HR 不造点、桶内断段保留、原始统计不变、60 秒/点数双上限、ECG 五秒子集及全部样本绘制、固定锚点/纳秒换算/负 x 排除、系统日期变化、三类断段边界、Retry 仅清对应曲线且会话横轴不归零、Stop 冻结无尾部零点、新 Start 清零、切图/旋转无重复采集与旧事件。运行相关测试、debug 构建和 lint；真机检查曲线单位和滚动、ECG 100 ms 刷新表现、同时采集、静止零值、缺口、Retry、切图和旋转；未执行项目标记待验证。上述参数是首版工程选择，不将未执行的性能或真实信号验收记为通过。
- 数据实施（2026-09-30）：新增 LiveCharts.kt，由现有 PolarBleManager/ViewModel 持有。HR 按原会话整数秒保留真实批末最新点，时间仍为实际接收 elapsedMs；桶内替换保留已有断段，使用合并前的真实批次间隔判断严格大于三秒缺口。运动曲线在原 250 ms 刷新入口记录一次步频/m/s，两个值共用最多 241 条记录；正常预热留空，真实静止零可绘制，绘图输出才将速度乘 3.6。StepDetector 仅新增只读检测段编号，在已有 clearSegment 中递增，使两次刷新之间完成的重置/预热也能被曲线识别；不修改算法或额外触发重置。
- ECG 实施（2026-09-30）：复用 EcgBuffer 的原始十秒/1,300 点列表，只持有首批末样本传感器锚点、对应会话经过毫秒和实际确认采样率。每次可见快照将原始纳秒差映射到 Running 时间轴，排除负 x 和五秒窗外数据，按实际 rate 的三周期阈值跨批比较相邻样本。不建立另一份长期原始缓存，不滤波、平均或抽点；已核对固定 SDK 8.3.0 官方 EcgSample 模型的纳秒时间字段（来源见本节 ECG 时间映射链接），未改 SDK API 或采样设置。
- 生命周期实施（2026-09-30）：接入现有有效订阅状态回调，单路失败/完成冻结对应数据与事件时刻视窗；STARTING 仅在接受启动/Retry 后清对应缓存/锚点，ACC 同清步频与速度。整体结束在当前指标清零前冻结曲线，不追加零点；后续清理不移动视窗。新 Start 重置数据，当前图和运动子图选择随管理器保留；旧事件沿用现有会话/订阅代次过滤。SessionController 仅增加只读 elapsedAt，用于快照时间，不推进会话状态或重算统计。
- 显示实施（2026-09-30）：新增 LiveChartPanel.kt，并由 SensorActivity.kt 接入；使用 Compose Canvas、主题颜色和英文文本，HR/Motion/ECG 三类切换，Motion 内单选 Cadence/Speed，各自标注 bpm、steps/min、km/h、µV。横轴 mm:ss，纵轴标注单位及当前上下界，ECG 保留负值。仅选中的图按 250 ms（HR/运动）或 100 ms（ECG）读取绘图快照；隐藏图仍通过数据事件和原会话刷新维护有界缓存。Canvas 遍历快照全部可见点，遇空值或断段不连接；代码检查与受控快照测试已完成，实际帧率、屏幕效果和设备旋转未验证。
- 当前范围：5.5add 代码与自动检查完成，未安装或操作手机，未新增依赖或通用缓存框架。60 秒显示缓存不是整场历史；本步未实施 UUID/SessionRecord、整场 1 Hz 历史、四小时自动结束、SQLite、History、进程恢复或正式布局。ECG 映射含传输延迟，只是近似对齐；HR 每秒末点可能省略秒内变化，已知显示限制不反向改变累计统计。

### 5.23 第 6 阶段：SQLite 会话存储与最简单 History 验收（6.1a—6.1d、6.2 代码已实施，验收边界见 5.23.11）

- 确认日期：2026-09-29。用户采用全部推荐方案，要求实施使用最小代码及最简单显示，方便测试；当时仅修改文档，没有修改应用代码或运行构建/真机测试。以下为设计规则；当前 6.1a—6.1d、6.2 的实际结果见 5.23.7—5.23.11；第 7/8 阶段未自动推进。
- 实施顺序：6.1a 会话身份与摘要 → 6.1b 平均与最小步频 → 6.1c 整场心率历史 → 6.1d 整场步频/速度历史及完整快照 → 6.2 保存及最简单测试入口；先检查 5.3—5.5 依赖，缺少时说明并停在缺失依赖，不自动补做其他步骤。6.2 可包含验收所需的最小列表/详情查询、简单曲线和删除入口，复用到第 7 阶段；正式布局仍留第 8 阶段。不新增 Room、图表库、通用 Repository/存储框架、兼容旧版本路径或无关重构。

- 拆分确认（2026-09-30）：原 6.1 拆为下列四步，每次只实施用户指定的一个编号。先完成 5.3—5.5，再依次执行 6.1a → 6.1b → 6.1c → 6.1d；缺失依赖时先说明，不自动补做。当时只更新文档，四步均未实施；现 6.1a—6.1d、6.2 代码已实施，验证范围见本节及第 9 节最新记录。

| 子步骤 | 本步范围 | 最简单显示与验收 |
|---|---|---|
| 6.1a | UUID、日期/单调时间、设备快照、保存资格；定义摘要和两类历史点结构，接入已有摘要；平均/最小步频仅定义字段，计算留 6.1b；不收集历史、不建库 | 英文 ID、时间、资格及已有摘要文本；重复 Start 不换 ID、新 Start 换 ID，未知与零区分，Stop/中断冻结摘要 |
| 6.1b | 平均及最小步频计算并写入摘要；沿用已有最大步频和同一批合格窗口，不改变统计口径 | 英文 Mean/Min/Max cadence；静止计入平均，无 ACC 为 --，短窗口及 Stop 人为零不参与极值 |
| 6.1c | 每秒最后真实 HR 点、elapsedMs、断段、四小时/14,401 点边界；独立接好 HR 的 Start/Stop/Retry/旋转及旧事件过滤 | 英文 HR history points 及起止时间；超过 60 秒保留早期点，无新 HR 不造点，Retry 保留旧段，Stop 冻结 |
| 6.1d | 每秒最后一组步频/速度结果、断段及容量；独立接好运动历史生命周期；将摘要与两类历史组装为同一场冻结快照，复用此前接线 | 英文 Motion history points、时间范围及快照状态；缺失不填零、Retry 不删历史、两类缓存有界，新 Start 不修改旧快照 |

- 分步边界：6.1a 的平均/最小步频字段暂未计算时不得冒充有效值；6.1b 接入真实结果后才显示。6.1c、6.1d 各自完成相应生命周期，不将全部接线推迟到最后。两者只限制历史收集窗口；四小时自动结束、SQLite 事务、保存失败重试、应用级待保存快照及最简单 History 查询仍由 6.2 实施。6.1a—6.1d 使用最小代码和英文测试文本，不做正式页面，不显示 Saved，不承诺进程重启后保留。
#### 5.23.1 会话 ID、时间与数据表（6.1a 定义与摘要，6.2 建库）

- 使用 Android 自带 SQLiteOpenHelper，数据库位于 App 私有目录，读写在 IO 线程。已有 saved_devices SharedPreferences 不迁移、不改用途。
- 接受 Start 时生成 UUID，保存/失败重试始终复用该 ID；每个新 Start 使用新 ID。保存 startRequestedAt、startedAt、endedAt 的 Unix 毫秒时间，以及单调时钟累计的 durationMs。startedAt 为首次有效 HR 或真实 ACC/ECG 触发 Running 的手机日期时间，History 以它作为开始时间；未 Running 的尝试不保存。
- 曲线横轴使用从 Running 起点起算的 elapsedMs，保留真实接收/结果记录时刻；系统日期不参与时长或横轴计算。设备名称、设备 ID 随会话保存快照，之后设备列表变化不改变历史。
- 仅三张表：sessions（UUID 主键、时间、设备、全部摘要、结束原因及完整性）、hr_points（会话 ID、会话秒桶、实际 elapsedMs、可空 bpm、断段信息）、motion_points（会话 ID、会话秒桶、实际 elapsedMs、可空步频与速度、断段信息）。时序记录按会话 ID 和秒桶唯一，不把完整曲线写成 sessions 中的大 JSON。
- sessions 摘要：HR min/max/mean、有效样本数；五区间累计毫秒、未归类毫秒；总步数、平均/最大/最小步频；累计估计距离、平均/最大估计速度；各路是否有数据及已知缺失/失败标记。HR 另外区分是否有有效 HR，避免收到无效批次就生成心率统计。保存未舍入值，时间 ms、距离 m、速度 m/s；UI 显示 bpm、steps/min、m、km/h，并仅在显示时舍入。

#### 5.23.2 平均及最小步频补充（6.1b）

- 新增平均步频 = 总确认步数 × 60 / Running 秒数，包含静止，沿用平均速度的完整 Running 分母；不是显示值或历史点的算术平均。分母为零或整场没有 ACC 观测时为 NULL；存在 ACC 缺失时保留已记录结果并标为不完整，可能偏低，不补步、不扣除缺失时段。
- 最大步频沿用 5.20；最小步频从同一批完整、连续、无已知 ACC 缺口的五秒真实样本窗口结果中取最小值。窗口从检测段预热结束起算，真实传感器时间覆盖满五秒才合格；不用手机显示时钟补足窗口。
- 使用窗口原始结果 N5 × 12 比较，不用两秒无步归零后的显示值；真实静止合格窗口可为 0。预热、短窗口、未知值和 Stop 人为归零不参与极值；没有合格窗口时极值为 NULL。平均与极值口径不同，不强行要求平均位于两者之间。
- 缺口/Retry 保留本场已累计摘要，极值等待新段重新具备合格窗口；新 Start 清零、Stop 冻结、旋转保留。该补充属于 6.1b，不能把旧 5.3 记录当作平均/最小步频已实现。

#### 5.23.3 整场历史曲线与容量（6.1c 心率，6.1d 步频/速度）

- 实时规则仍为 5.22 的 60 秒/250 ms；历史使用独立的有界整场收集，在事件到来时选择记录，不在 Stop 时从最后 60 秒缓存反建整场数据。
- HR：每个会话整数秒最多一条，保留该秒最后一个真实非空批次末样本及其实际接收 elapsedMs；不取平均，无新 HR 不造点。无效批末样本保存无值/断段，不写 0。保留 5.22 的相邻批次超过三秒绘图断段规则，不影响区间保持计时。
- 步频/速度：复用已有 250 ms 计算/记录入口，在每个会话秒桶只保留最后一组结果及实际 elapsedMs；各值可空。预热/不可用不生成实测零，正常 ACC 下静止可为 0。跳过没有执行的刷新，不补过去的整秒点。
- 断段沿用既有 HR 无效/中断和 ACC 缺口/检测段重建规则；桶内替换必须保留已发生的断段。失败/缺失持续区间不连线、不插值；断段标记随记录保存，不另建无界事件历史。
- 单流 Retry 清实时曲线及对应锚点，但不得删除本场历史记录；历史断段后继续原会话横轴。普通缺口及未显示的图也继续保留已记录历史。Stop 不追加人为归零点，保存已有最后不足一秒的记录，不制造终点样本；旧会话/旧订阅事件不得写入当前历史。
- 单场 Running 上限为四小时（14,400 秒），提前在简单界面说明；复用现有刷新/事件入口检查，达到上限按正常结束流程冻结、清理并保存，原因 TIME_LIMIT，提示 Session time limit reached。逻辑截止为四小时，超出截止的记录不再收集；异步调度及清理不保证恰好在边界执行，也不计入超出的会话时长，不补造边界数据。
- hr_points、motion_points 及对应内存记录各最多 14,401 条/场，容纳秒桶边界；不预分配、不填满、不丢掉旧历史来伪装完整记录。四小时及每秒一点是首版工程选择，性能与真机验收尚未执行。
- HR 统计仍用全部有效样本；步频/速度极值仍从合格五秒窗口计算；区间时长沿用 5.21。不从降频历史点反算这些摘要。
- 不持久化原始 ACC、ECG 或每步检测过程，仅保留既定 10 秒原始内存缓存。不能从历史重新运行算法或回放整场 ECG。速度曲线保存但首版 History 只需显示 HR 和步频曲线。

#### 5.23.4 保存资格、事务与失败

- 有至少一个有效 HR 样本或真实 ACC/ECG 样本才保存；完全无数据、仅无效 HR 的尝试不保存，不增加最短有效时长。缺失流可以保存；无观测指标为 SQL NULL/UI --，真实零保存为 0。有观测且无步时可保存零步/零距离，但无合格窗口的极值仍未知。
- Stop、连接/前台中断、全部流终止或四小时上限触发一次结束。先按既定规则结算区间并冻结时长、摘要和整场曲线，再异步保存，清理耗时不计入。保存结束原因及各流观测/完整性标记，已累计有效值不因失败清零；重复结束不重结算或重复插入。
- 一个 SQLite 事务写入 sessions 和全部时序行，成功提交后才显示 Saved；失败回滚，不留半条历史。UUID 主键及唯一时序键防重复；重试同一个冻结快照和 ID，不生成第二场，不用覆盖写掩盖重复累计。
- 简单状态为 Saving… / Saved / Save failed。保存失败保留一个待保存内存快照及 Retry save；保存中禁止重复提交，不允许并发保存同一场。保存中或失败未处理时禁止新 Start，失败后可重试，或明确选择 Discard session 后丢弃未保存快照并允许新场次；不得暗中覆盖失败结果。正常无资格的尝试无需等待保存即可结束。
- 保存任务和待保存快照由应用级持有者管理，不持有 Activity，不随离开页面或 ViewModel 清除主动取消；返回测试界面可看到状态并重试。资源清理与保存职责分开，保存完成不恢复连接或采集。不新增后台采集、服务、自动重试、进程恢复或崩溃日志；进程在提交成功前被强制结束可能丢失本场未保存记录，不能宣称崩溃恢复已支持。

#### 5.23.5 最简单显示、查询、删除与隐私

- 使用现有 Compose 主题、英文 UI/注释，简单文本、按钮、可滚动列表和基础 Canvas 折线/柱形即可；不做正式卡片布局、动画、复杂导航或额外图表依赖。第 6 阶段只加验收所需的最小入口，后续第 7 阶段复用查询，第 8 阶段再整合正式界面。
- 列表显示开始日期时间、Running 时长、总步数、累计估计距离及不完整提示；startedAt 倒序，每次 20 条，Load more，无筛选。按 ID 查询详情，简单 Back 返回列表。测试入口切换不应因销毁正在使用的会话持有者而意外停止采集；仍遵守真正离开前台/返回欢迎页的既定结束规则。
- 详情显示整场 HR 曲线、HR min/max/mean；五区间时长和各自占 Running 时长的百分比，未归类单列且不摊入五区间；步频曲线及 mean/max/min；Running 时长、总步数、累计估计距离、平均/最大估计速度、设备、结束原因和不完整提示。HR 无有效样本时明确 No valid HR data；各值按可空状态显示 --。区间时长是 Estimated from received HR，五区间占比可小于 100%；分母零时不计算百分比。
- 整场图按实际 elapsedMs 排序和缩放，只在同一有效段内连线；不画示例点、不自动补零。首版不要求缩放/拖动/ECG 回放，速度曲线虽保存但可不展示。所有简单查询结果必须来自实际 SQLite，不能拿内存结果冒充重启后的 History。
- 已保存记录一直保留，不自动按日期或数量清除。删除单场前确认，同一个事务删除该 ID 的摘要和全部曲线；取消删除无影响，不提供清空全部或导出。
- 数据只存 App 私有目录，不上传；将会话数据库明确排除系统云备份及设备迁移，检查现有 Manifest 与适用备份 XML，不能只写“本地存储”或只依赖 allowBackup。已有设备记录不顺带改变。提示卸载 App 或清除应用数据会丢失历史，无自动恢复承诺。
- 技术依据：[Android SQLite](https://developer.android.com/training/data-storage/sqlite)、[事务与批量写入](https://developer.android.com/topic/performance/sqlite-performance-best-practices)、[备份排除规则](https://developer.android.com/identity/data/autobackup)。实现时检查当前项目配置和相关官方 API。

#### 5.23.6 验收与当前状态

- 6.1a 受控检查：UUID/重复操作、日期与单调时间分工、保存资格、设备快照、摘要 null/0、Stop/中断冻结及旋转/旧事件；不要求尚未实施的平均/最小步频或历史收集通过。
- 6.1b 受控检查：平均分母含静止、无 ACC/零分母、完整五秒窗口极值、缺失及段重建、Retry 保留累计、Stop 人为零排除、新 Start；摘要应反映本步计算结果。
- 6.1c 受控检查：HR 秒桶真实末点、无新数据不造点、批末无效、超过三秒断段、桶内断段保留、超过 60 秒保留旧点、Retry/部分秒/Stop/旋转/旧事件、四小时与 14,401 点边界。
- 6.1d 受控检查：250 ms 结果按秒保留末组、缺失与真实静止、ACC 缺口及段重建、Retry/部分秒/Stop/旋转/旧事件、四小时与 14,401 点边界；摘要和两类历史同 ID、结束时整体冻结，新 Start 不修改旧快照。两类容量边界用受控时钟验证，不等待四小时或把模拟结果当真机验证。
- 6.2 检查：事务成功/失败回滚、重复结束/重试去重、失败快照保留和新 Start 限制、手动重试/丢弃、页面离开后任务不主动取消；SQLite 实际关闭再打开后的读取、按 ID 详情、20 条分页及倒序、删除/取消、NULL 与 0、备份排除配置。数据库行为应使用真实 SQLite 验证，不仅 mock 成功；如依赖模拟器/设备的测试未执行，明确标待验证。
- 实施代码后运行相关测试、debug 构建和 lint；真机验证采集→Stop/中断→Saved→重启查询、简单图与摘要一致、失败恢复和删除，并记录性能及局限。不能用旧的 105 项测试或构建结果证明第 6 阶段通过。
- 当前 6.1a—6.1d、6.2 已写入代码；6.2 单元测试、debug、lint 和仪器测试 APK 构建通过。保存成功提交才显示 Saved；进程在提交前终止可能丢失未保存快照。实际 SQLite/界面执行及 H10 真机待验收项分别见 5.23.11。

#### 5.23.7 步骤 6.1a 实施与验证 / Step 6.1a implementation and verification（2026-09-30）

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

#### 5.23.8 步骤 6.1b 实施与验证 / Step 6.1b implementation and verification（2026-09-30）

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

#### 5.23.9 步骤 6.1c 实施与验证 / Step 6.1c implementation and verification（2026-09-30）

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

#### 5.23.10 步骤 6.1d 实施与验证 / Step 6.1d implementation and verification（2026-09-30）

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

#### 5.23.11 步骤 6.2 实施与验证 / Step 6.2 implementation and verification（2026-09-30）

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

### 5.24 5.3add、5.4add、5.5add 补充实施入口（2026-09-30，三项代码已实施）

- 初次记录要求：提供三个补充 prompt，命名为 5.3add、5.4add、5.5add，并将两份 AGENTS.md 和两份 prompt.md 分别同步为完全一致的最新版；初次记录只改文档。随后分别按用户授权实施三项，实际结果见 5.20—5.22 和第 9 节；三项均仍有真机待验收项。
- 含义：三个 add 分别完成原开发步骤 5.3、5.4、5.5 中尚未落实的功能，不是要求同编号原步骤先实现的额外阶段，也不改变既定算法/统计口径。原提示词保留作为历史记录，后续使用对应 add；已有部分实现时检查后最小补齐，不重复建立另一套算法、计时或曲线路径。
- 顺序：5.3add → 5.4add → 5.5add → 重新检查 6.1a。每次只执行用户指定的一项；缺失前置条件时报告并停止，不自动连做或推进第 6 阶段。依赖编号 5.3—5.5 仍指原功能，完成对应 add 并实际检查通过后才算满足代码前置条件；真机未验收仍需单列。

| 提示词 | 对应规则及本步范围 | 前置条件与检查 |
|---|---|---|
| 5.3add | 原 5.3 / 5.20：峰间步长、累计距离、当前/平均/最大速度、最大步频、ACC 观测和不完整状态；英文简单文本 | 检查 5.2a—5.2d 和会话已实现；四步仅三段距离、原峰时间截取、真实五秒极值、平均包含静止、null/0、Retry/Stop/旧事件 |
| 5.4add | 原 5.4 / 5.21：Heart rate intensity、五区间毫秒、未归类时间、简单五柱图及标签 | 检查 5.3add 对应功能及有效 HR/会话路径；同批末值、单调计时、无效/Retry 结算、整体停止先结算后清理、无重复累计 |
| 5.5add | 原 5.5 / 5.22：HR/步频/速度最近 60 秒、ECG 最近五秒、会话时间轴、断段、有界缓存和简单绘图 | 检查 5.3add/5.4add 对应功能及真实三流接线；点数/窗口双上限、ECG 固定锚点和全部可见样本、Retry/Stop/切图/旋转 |

- 5.3add 补充：同段相邻接受候选峰间统计包含两端，以原峰时间截取，不把回落确认后的数据混入；小于 0.25 秒被拒绝的峰不移动已接受参考峰。保留当前 0.25—2 秒间隔、100 Hz、A_min、四步确认及中断规则。最大步频/速度复用同一批真实完整五秒窗口，不受显示两秒归零影响。由当前统计持有者提供最小只读结果供 UI 和后续摘要读取；不新增 SessionRecord 或数据库框架。平均/最小步频仍留 6.1b。
- 5.4add 补充：由当前区间统计持有者提供最小只读五区间时长、同一时刻未归类时间、当前可空区间及有效 HR 观测状态，UI 不重复结算。整体结束以同一停止时刻先结算后清读数/订阅，保留末段时长；重复结束不重复累计。使用既定固定档位、最近有效读数保持法及无新批次的已知限制，不增加超时、个体化算法或历史缓存。
- 5.5add 补充：真实事件→取点/断段→有界缓存→简单绘图必须接通。HR/步频/速度记录保留实际会话经过时间、可空值和断段，管理实例持有，不在重组中造点或重建订阅。实时 60 秒/五秒窗口不变；不提前实现第 6 阶段整场 1 Hz 历史、四小时结束、UUID/摘要或 SQLite。Retry 清对应实时缓存，未来整场历史保留由 6.1c/6.1d 负责。
- 交付：每项代码实际实施后运行对应必要测试、debug 构建和 lint，并同步两份 AGENTS.md 与两份 prompt.md 的实际结果、保持副本一致。分别说明代码已写入、自动检查、真机未验证项；提供匹配真实改动的英文 commit message，不自动创建 Git commit 或推送。不使用原 105 项测试或之前构建作为新步骤证据，不把单项通过当作三项或 6.1a 均完成。
- 状态：2026-09-30 5.3add、5.4add、5.5add 代码与各自自动检查已完成，真机待验证。此前 6.1a 前置检查作为历史保留，所列 5.3—5.5 代码缺口现已补齐；随后已重新检查当前输出并实施 6.1a，见 5.23 和第 9 节；5.5add 的 148 项测试仍仅作为当时证据，不替代 6.1a 本轮检查。正式布局仍留第 8 阶段；每次只实施用户指定一步。

### 5.25 功能目录整理（2026-09-30）

- 源码基准：`PolarH10ActivityViewer/app/src/main/java/com/example/polarh10activityviewer/`；下表为当前实际位置。旧记录中的文件名仍对应同一文件，后续按此目录查找，不在根包重复创建旧路径。

| 目录 | 文件 | 对应功能步骤 |
|---|---|---|
| 根包 | MainActivity.kt、SensorActivity.kt、ActivityViewerApplication.kt | 页面入口、权限、Session UI 与应用级会话持有者（5.47） |
| ble/ | PolarBleManager.kt、DataReadiness.kt、SavedDeviceStore.kt、DevicesDialog.kt | 1—4：蓝牙、设备记录、就绪与订阅；8.1 Devices 正式操作弹窗 |
| sensor/ | AccBuffer.kt、EcgBuffer.kt | 4.2—4.3：真实样本及短缓存 |
| motion/ | AccPreprocessor.kt、StepCandidateDetector.kt、StepSequence.kt、StepDetector.kt、MotionWindow.kt、StrideLengthEstimator.kt | 5.2a—5.2d、5.3add；6.1b 在现有统计上补充 |
| heartrate/ | HeartRateZones.kt、HeartRateZonePanel.kt | 5.4add 区间计时；8.2 共用累计五柱/明细显示 |
| chart/ | LiveCharts.kt、LiveChartPanel.kt | 5.5add：实时曲线 |
| session/ | SessionState.kt、SessionRecord.kt、SessionSummaryPanel.kt、SessionHeader.kt、SessionMetrics.kt | 4.4 会话与持有者；6.1a 身份/摘要/历史结构及测试文本；6.1d 组合冻结快照；8.1 连接/电量与当前强度顶部；8.2 主指标/运动汇总卡片 |
| history/ | HrHistory.kt、MotionHistory.kt、HistoryPanel.kt | 6.1c—6.1d 整场 HR/运动收集；6.2 SQLite 列表/详情/确认删除与保存状态文本 |
| storage/ | SessionDatabase.kt、SessionSaveController.kt、SessionStorage.kt | 6.2 SQLite 三表事务与查询、保存状态机、应用级持有者 |
| ui/theme/ | Color.kt、Theme.kt、Type.kt、Dimensions.kt | 共用主题、区间颜色、基础字号与已使用尺寸；8.0 实施结果见 5.28.1 |

- `app/src/test/java/com/example/polarh10activityviewer/` 下 17 个功能测试文件同步分包；HeartRateTest 位于 heartrate/，其被测心率类仍在 ble/PolarBleManager.kt 内，通过 import 引用。ExampleUnitTest 和 androidTest 的 ExampleInstrumentedTest 保持原位。
- 本轮仅移动 17 个源码和 17 个测试文件并调整 package/import；未重命名 CadenceWindow、拆分类或改算法。检查全部 41 个 Kotlin 文件，忽略 package/import 和空行后与迁移前 HEAD 内容一致。Activity、Manifest、资源、依赖和主题保持原位。空目录不被 Git 跟踪，不添加占位文件。
- 本轮验证：运行 `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，最终 BUILD SUCCESSFUL；148 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。未新增测试用例，现有测试已在迁移后执行。
- 未安装 APK 或进行真机验证；两份 AGENTS.md 和两份 prompt.md 同步目录及本轮结果。第 6 阶段未实施；未创建 Git commit 或推送。

### 5.26 运动处理最小重构（2026-09-30）

- 按用户确认的三项方案实施：StepDetector.receive 保持原处理顺序，将距离累计和窗口提交提取为私有 commitSteps，将完整五秒真实窗口的最大步频/速度更新提取为私有 updateWindowMaxima。StepDetector 仍是原有唯一协调及统计持有者，不增加管理类、框架或依赖。
- preprocessor 改为 private，新增只读 isWarmingUp（预热结束时间为空）；PolarBleManager 的曲线记录入口使用该属性，不直接访问预处理器。该属性表示预热尚未完成，不代表流正在采集；停止/重置后的判定仍与原表达式相同。
- motion/CadenceWindow.kt 和类名改为 MotionWindow.kt / MotionWindow，私有字段改为 motionWindow；对应测试改为 MotionWindowTest.kt / MotionWindowTest，MotionStatisticsTest 同步引用。窗口公式、时间单位、淘汰、显示归零及原始极值口径不变；不保留旧名别名。早期记录中的 CadenceWindow 是同一实现的旧名称。
- StepState 继续位于 StepDetector.kt；未实施 6.1a—6.1d、SQLite、History 或平均/最小步频，未改变算法参数及生命周期顺序。
- 调整既有 StepDetectorTest 和 LiveChartsTest，通过公开只读预热状态及 segment 检查代替内部预处理器访问；验证初始及超时重建的第 103/104 个样本预热边界、显示刷新不重置段、缺口/Retry/清理状态。平滑和阈值窗口的内部边界仍由 AccPreprocessorTest 验证。
- 本轮执行 `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，BUILD SUCCESSFUL；148 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。为本轮实际执行结果，不沿用之前迁移的检查记录。两份 AGENTS.md 和两份 prompt.md 同步。
- 未安装 APK 或操作真机；设备与运行时验收仍待完成。未创建 Git commit 或 push。

### 5.27 第 7 阶段：History 查询交互与验收规则（2026-09-30 确认，7.1/7.2 代码已实施）

- 用户采用全部推荐规则。规则确认时仅定义规则并补充中英文提示词；随后分别按用户指定实施 7.1、7.2，实际结果见 5.27.1—5.27.2。6.2 的既有实现与证据保留，第 7 阶段不得使用旧测试结果代替本步验证。
- 范围：7.1 完善历史列表，7.2 完善按 ID 的详情与确认删除；检查并复用 6.2 的 SessionDatabase、HistoryPanel、摘要和基础曲线，只补实际缺口，不重复实现符合规则的功能。每次只实施用户指定的一个编号；正式布局、卡片和临时开发显示清理留第 8 阶段。
- 返回与旋转：从详情返回列表、旋转或重新进入 History 时，重新查询前 20 条，不要求恢复已加载页数或滚动位置。旋转保留所选会话 ID，按 ID 重新读取详情。测试视图切换及旋转不停止运行中的采集；真正离开前台仍遵守 5.18。
- 保存后刷新：当前显示列表时，新的保存成功后重新加载前 20 条；当前显示详情时，继续显示所选会话，返回列表后再刷新。保存失败不得作为新记录出现在列表中。
- 排序与分页：沿用 startedAt DESC、id DESC 的游标分页，每次最多 20 条；同一开始时间由 ID 确保稳定顺序。20 条是每次加载数量，不是保存上限。向下滚动后点击 Load more 加载较早记录，不自动滑底加载、不额外查询总数。返回不足 20 条时隐藏 Load more；恰好满 20 条允许再请求一次才能确认末页。沿用 5.23 的不自动过期或按数量删除规则。
- 查询失败：首次加载失败显示英文错误和 Retry query；Load more 失败保留已有列表及原游标，Retry query 重试失败页，不回到第一页。查询期间禁用重复请求；取消或旧页面/旧选择的查询结果不得覆盖当前页面。详情查询失败按原 ID 重试。
- 删除与不存在：确认弹窗显示所选会话的 Running 开始日期时间。Cancel 不写数据库；确认后沿用一个事务删除该 ID 的摘要与两类历史。删除期间禁止重复操作；失败保留详情和数据库记录并允许再次 Delete，不显示成功。成功返回列表并重新加载前 20 条。按 ID 查不到记录时显示 Session not found 和 Back。
- 日期、单位与未知值：列表、详情和删除确认统一按查看时手机的当前时区显示 yyyy-MM-dd HH:mm:ss XXX（包括 UTC 偏移）；重新进入/查询或页面重建时读取当前时区，不新增系统时区监听框架。数据库 Unix 毫秒不改，时长和曲线横轴继续使用单调时间结果。数值单位与舍入沿用 6.2，未知显示 --，真实零显示 0，不从历史点重新计算摘要。
- 保留范围：继续显示 5.23.5 已规定的摘要、HR/步频整场曲线、五区间时长与 Running 占比及未归类时间，保留 null/断段规则。无筛选、导出、批量删除、自动清理、曲线缩放/拖动；不增加速度曲线或 ECG 历史回放，不新增依赖、数据库表、通用框架、算法调参或后台采集。
- 7.1 验收：本步重新验证空列表、超过 20 条、同时间排序、满页边界、Load more 及分页失败原页重试、成功保存后的列表刷新、返回/旋转/重新进入后的前 20 条、旧查询过滤；使用实际 SQLite 验证关闭重开后的读取及分页。检查运行中切换 History/旋转的采集行为，未执行的设备项标 pending。
- 7.2 验收：本步重新验证不同 ID 的摘要与两类历史、null/零/断段、零时长占比、无有效 HR、所选 ID 的旋转恢复、新保存不切换详情、记录不存在及查询重试；验证删除日期确认、取消、成功只删所选 ID、事务失败回滚及重试。实际 SQLite/Compose 检查与 H10 真机检查分开记录，不用测试数据宣称真机采集全链路通过。
- 交付：各步修改后运行相关测试、debug 构建和 lint；中英文记录实际修改、自动检查及设备行为，未执行项为 pending。同步两份 AGENTS.md 和两份 prompt.md，提供对应英文 commit message，不自动 commit/push。

#### 5.27.1 步骤 7.1 实施与验证 / Step 7.1 implementation and verification（2026-09-30）

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

#### 5.27.2 步骤 7.2 实施与验证 / Step 7.2 implementation and verification（2026-10-01）

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

### 5.28 步骤 8.0：共用主题与第一版尺寸 / Step 8.0: Shared theme and initial sizing

- 确认日期：2026-10-01。用户采用推荐主题与尺寸，并明确心率区间从低到高为绿、蓝、黄、橙、红。规则确认时只更新文档；随后按用户指定实施 8.0，实际结果见 5.28.1。8.1 规则随后独立确认，见 5.29；8.2—8.5 的其他交互与布局建议未因主题选择自动成为已确认规则。
- 顺序与范围：8.0 在 8.1 前完成共用样式定义及基础接入。沿用 Kotlin、Compose、Material 3 和现有主题文件，不增加依赖或通用样式框架。只接入固定深浅配色、基础字体、必要的共用尺寸及现有心率区间配色；正式区域、卡片、导航和临时展示清理按 8.1—8.5 各自实施。无需为尚未使用的每个尺寸提前建立抽象。
- 主题：关闭动态配色，按系统深浅模式选择固定色板，欢迎页、Session、History 使用同一主题。沿用现有默认字体家族；文字使用 sp，布局使用 dp，字体随系统字号缩放。主色按钮文字及表面文字须有可读对比度。

| 主题元素 | 浅色模式 | 深色模式 |
|---|---|---|
| 主色 / Primary | `#2563EB` | `#60A5FA` |
| 页面背景 / Background | `#F8FAFC` | `#0F172A` |
| 卡片背景 / Surface | `#FFFFFF` | `#1E293B` |
| 主要文字 / Primary text | `#0F172A` | `#F1F5F9` |
| 次要文字 / Secondary text | `#475569` | `#CBD5E1` |

| 心率区间 | 标签 | 固定颜色 |
|---|---|---|
| Zone 1 | Very light | 绿 `#22C55E` |
| Zone 2 | Light | 蓝 `#3B82F6` |
| Zone 3 | Moderate | 黄 `#EAB308` |
| Zone 4 | High | 橙 `#F97316` |
| Zone 5 | Very high | 红 `#EF4444` |

- 心率配色用于当前强度标识和 Session/History 的五区间柱形，颜色顺序共用同一处定义。保留英文强度、Zone 和 bpm 范围，不只靠颜色传意；普通文字使用主题文字色，区间颜色可作为色块或标记，避免黄色小字在浅色背景上难以阅读。不能因改配色修改阈值、统计、单位或采集行为。

| 尺寸项 | 第一版值 | 接入位置 |
|---|---|---|
| 页面边距、卡片内边距 | `16 dp` | 正式页面逐区接入 |
| 区域间距、卡片圆角 | `16 dp` | 正式布局逐区接入 |
| 卡片内部间距 | 紧凑 `8 dp`，常规 `12 dp` | 正式布局按内容使用 |
| 当前 HR 数值 | `56 sp` | 8.2 主指标 |
| 步频、速度等主要数值 | `28 sp` | 8.2 指标 |
| 页面标题、区域标题 | `24 sp`、`18 sp` | 8.0 基础字体，后续正式区域复用 |
| 正文、次要统计 | `16 sp`、`14 sp` | 8.0 基础字体，后续正式区域复用 |
| 普通图标 | `24 dp` | 后续正式布局 |
| 按钮和可点击区域 | 最小高度/触控范围 `48 dp` | 保留 Material 默认触控范围，后续正式控件落实 |
| 实时曲线绘图区 | 高度 `220 dp` | 8.3，不包含轴标签与切换按钮 |
| 心率区间柱形绘图区 | 高度 `160 dp` | 8.2，不包含图题、标签与说明 |

- 排版原则：内容允许纵向滚动，横屏沿用相同内容顺序，不增加另一套复杂布局；字号增大时允许文字换行与区域增高，避免截断数值、单位或按钮。上述尺寸是第一版基线，若实际显示需调整，记录原因和最终值，不改算法。设备弹窗排列见已确认的 5.29；控制区是否固定、返回键顺序及 History 正式分组仍待对应步骤确认。
- 清理边界：8.0 不提前删除开发验收显示；8.1—8.5 在正式区域替代后按第 6 节清理。无数据占位符 `--`、真实零、必要状态、错误、Retry/Recheck 与保存恢复入口继续按已有规则保留。
- 8.0 实施验收：检查欢迎页、Session、History 的固定深浅主题及现有五区间颜色顺序、普通文字/按钮对比度、基础字号与字体放大后的可读性；运行相关检查、debug 构建及 lint。正式布局及绘图区高度的完整显示验收留对应步骤；未执行的设备/视觉项目标 pending。同步两份 AGENTS.md 和两份 prompt.md 的实际结果，不沿用旧构建或 H10 证据证明本步通过。
- 当前交付 / Current delivery：步骤 8.0 源码及本轮自动/模拟器检查已完成，两份 AGENTS.md 和两份 prompt.md 同步中英文实际结果；8.1—8.5 formal layouts and Samsung/H10 validation remain pending. 未 commit/push，具体检查边界见 5.28.1。

#### 5.28.1 步骤 8.0 实施与验证 / Step 8.0 implementation and verification（2026-10-01）

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

### 5.29 步骤 8.1：连接、电量与心率强度正式入口 / Step 8.1: Connection, battery and HR intensity header

- 确认日期：2026-10-01。规则确认时仅写文档；用户随后要求实施，实际结果见 5.29.1。前置为 8.0 共用主题及现有连接、电量、心率区间状态；8.1 仅整合区域 1、2 和相应设备操作界面。
- 顶部布局：在现有 Session 内容顶部放置同一行的两块区域，左为连接入口，右为 Heart rate intensity；它们随现有 Session 内容滚动，不新增固定导航或控制区。采用 8.0 的页面边距 16 dp、区域圆角 16 dp、常规间距 12 dp、图标 24 dp、可点击区域至少 48 dp。常规宽度两区域均分可用宽度；文字可换行、区域可增高，空间不足时按连接→心率强度顺序上下排列，不缩小系统字号或省略状态文字。深浅模式复用 8.0 主题。
- 左侧连接入口：显示蓝牙图标、简短英文状态，第二行显示 Battery: 百分比或 --；整个左侧区域可点击打开 Devices 弹窗，即使权限缺失或蓝牙关闭也能查看原因。Connected 不等于数据 Ready 或正在接收，不用 HR/ACC 是否收到数据反推连接。有效电量只取当前连接的既有回调，未知或连接无效时显示 Battery: --，不显示上一连接电量。

| 当前情况 | 顶部英文状态 | 弹窗操作规则 |
|---|---|---|
| 蓝牙可用，未连接 | Not connected | 使用既有扫描和已保存设备连接入口 |
| 正在连接 | Connecting | 保留当前目标设备；禁用重复连接、扫描及切换设备 |
| 回调确认已连接 | Connected | 显示当前设备与电量，提供 Disconnect；不提供切换设备 |
| 正在断开 | Disconnecting | 禁用重复操作；既有断开请求失败时提供 Retry disconnect |
| 权限缺失/拒绝/需设置 | Permissions needed | 展示现有用途说明和对应授权/设置入口 |
| 蓝牙关闭 | Bluetooth off | 保留现有 Turn on Bluetooth 入口 |
| 不支持 BLE / SDK 失败 | BLE unavailable / SDK error | 展示现有原因；SDK 失败复用现有初始化 Retry |

- 状态来源与优先级：权限、蓝牙、SDK 不可用时顶部显示相应不可用状态；否则按现有 ConnectionStatus 显示四种连接状态。此处仅映射展示文本，不修改真实连接状态、回调确认、10 秒连接超时或取消逻辑。连接与操作错误在弹窗内显示，顶部仅保留简短必要状态。
- 设备弹窗：标题 Devices；内容使用受限高度内的可滚动 Column，底部 Close 始终可访问。顺序固定为① 当前连接状态及 Current device，包含当前/目标设备名称、ID、有效电量和必要连接错误，未选择时显示 No device connected；② 蓝牙可用性问题与必要操作、简短数据就绪/配置问题及 Recheck data readiness；③ Saved devices；④ Nearby Polar H10 devices、Start scan/Stop scan、扫描状态及结果。三类设备信息的相对顺序始终为当前→已保存→扫描结果。
- 列表与操作：已保存设备显示名称、ID 和最近连接时间，保留“曾由本 App 连接，不代表当前在线”的简短英文说明；扫描结果显示名称、ID 和已有 RSSI（dBm），不增加连接后信号监测。复用现有去重、30 秒停止、新扫描清空、选设备后停止扫描并连接、单台连接和保存设备规则，不增加自动扫描/自动连接/自动重连/设备切换。已保存设备加载失败、扫描错误和空结果保留必要提示及现有恢复方式。
- 权限、关闭与旋转：打开 Devices 不自动申请权限、不开始扫描或连接；用户点击对应蓝牙操作时复用原权限流程。Close/系统返回/点击弹窗外关闭时，停止仍在进行的本轮扫描并保留结果；关闭不主动断开、不取消已接受的连接、不停止运行中的 Session、不启动数据流。旋转保留弹窗是否打开，沿用 ViewModel 的设备/扫描/连接状态，不重新发起操作；真正离开前台、锁屏或返回欢迎页仍执行既定结束清理。重新打开弹窗不自动扫描。
- 配置与恢复：弹窗只保留用户需要的简短 HR/ACC/ECG 就绪或配置问题、对应原因及既有 Recheck data readiness；配置被阻止/尚未确认时显示解决问题所需的实际选项，不将所有已确认采样参数作为常驻列表。按钮启用条件、查询取消和旧结果防护沿用当前实现。对应数据流的 Retry 继续留在现有指标/流区域，本步不改变单流恢复逻辑。
- 右侧强度：显示 Heart rate intensity 标题、颜色标记、英文强度和 Zone，例如 Moderate · Zone 3；它是非交互信息区域。普通文字使用主题 onSurface，颜色标记复用唯一 HeartRateZoneColors，低到高绿/蓝/黄/橙/红，不增加另一套颜色或使用难读的黄色小字。仅消费最新批末有效性决定的 current；不使用历史最大/平均 HR 推导当前强度，不表示已确认正在运动。
- 强度缺失与停止：current 为空时显示 --，无有效 HR/无接触/订阅失败/停止的必要原因沿用现有状态或消息；没有当前区间时不显示有色标记。Stop/中断后显示 --/Stopped，累计五区间时长继续保留。保持原阈值、接触有效性、静止计时及无新批次保持规则，不新增平滑、数据新鲜度超时、动画或自动恢复。
- 本步清理：将原 Session 页面下方的连接/电量/已保存设备/扫描操作迁入正式入口及弹窗，替代后删除重复展示；将当前强度标签从累计五柱展示中拆出，避免 Session 显示两次当前强度。History 的累计五柱和必要说明继续可用，正式五柱布局留 8.2。已被弹窗替代的详细就绪参数常驻展示按第 6 节删除；未被替代的流指标、Retry、错误和其他开发区域保持。仅用于被替代 UI 的文件确认无引用后才删除；底层 DataReadiness、连接/权限/订阅/电量/统计逻辑必须保留。
- 实施边界：只做 8.1，不重排主指标、不调整正式五柱/曲线高度、不改 Start/Stop 和 Session/History 导航、不清理无关开发区域、不增加 SDK API、依赖、框架或存储字段；不实施 8.2—8.5。沿用现有 Compose 组件，App 文本与注释为英文。
- 后续验收：检查全部顶部状态和电量未知/已知/失效、弹窗顺序、打开不自动操作、扫描停止/结果保留、重复操作禁用、权限/蓝牙/连接/配置错误与恢复、关闭不结束 Session、旋转不重复请求；检查五档强度及无效/失败/停止/恢复、累计五柱仍可用。使用真实 UI 检查浅/深、长设备名和 1.0/2.0 字号，区分受控状态与真实 H10；运行相关检查、debug 构建和 lint，未执行项目标 pending，不沿用 8.0 结果宣称 8.1 通过。
- 当前交付 / Current delivery：8.1 App 已实施，构建、单元/Compose/SQLite 与实际视觉检查结果见 5.29.1；两对文档同步实际中英文结果。Step 8.1 is implemented; fresh automated, controlled and actual visual evidence is recorded in 5.29.1. Samsung/H10 validation remains pending. 未 commit/push。

### 5.29.1 步骤 8.1 实施与实际验证 / Step 8.1 implementation and verification（2026-10-01）

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

### 5.30 步骤 8.2：主指标、心率区间与运动汇总 / Step 8.2: Metrics, HR zones and activity summary

- 确认日期：2026-10-01。用户采用推荐方案；确认规则时仅同步文档，随后按用户要求实施，结果见 5.30.1。前置为 8.0 共用主题、8.1 顶部/Devices，以及已实现的 5.19—5.23 HR/运动/区间/摘要规则。已检查 SensorActivity、StepState、LatestHeartRate、HeartRateZonePanel、SessionSummaryPanel 及 History 引用；实施时只整合区域 3—6，沿用现有状态、统计持有者和回调。
- 内容顺序：在 8.1 顶部之后，将区域 3—6 按 Heart rate → Motion → Heart rate zones → Activity summary 排列，形成四张正式卡片；当前强度继续只在 8.1 顶部显示。既有 Session 状态、必要结束原因、Start/Stop 的位置与启用条件保留，控制/导航正式整合留 8.4；本步不将按钮移到底部或固定。曲线正式布局留 8.3，History 列表/详情分组留 8.5。原 Elapsed 计时文本由 Activity summary 中的 Running duration 替代，避免重复计时。
- 主题和尺寸：沿用固定深浅主题与默认字体；页面/卡片内边距、卡片圆角、区域间距均为 16 dp，卡片内使用 8/12 dp。区域标题 18 sp、正文与单位 16 sp、次要统计/接收时间/说明 14 sp；当前 HR 56 sp，当前步频/速度和汇总主数值 28 sp。主数值使用 onSurface，次要文字使用 onSurfaceVariant；不以当前 Zone 颜色染色大数值。数值与单位分别排版，窄宽/大字号可将单位放到下一行，不使用省略号、自动缩字号或固定文本高度。只定义实际使用的尺寸/字号，不引入通用样式框架。

| 卡片 | 正式显示 | 数值来源与口径 |
|---|---|---|
| Heart rate | 当前 HR 与 bpm；Min / Max / Mean；Last received (phone)；必要 HR 状态/错误与 Retry HR | 当前值只读既有有效 reading；Min/Max 为整数，Mean 一位小数，复用本场有效 HR 样本统计；最后接收时间读 reading.receivedAt，按既有英文日期/当前时区格式显示，不称为传感器采样时间 |
| Motion | Cadence 与 steps/min；Estimated speed 与 km/h；步频 Mean / Min / Max；速度 Mean / Max；必要 ACC 状态/不完整提示与 Retry ACC | 使用 StepState 已有 cadence、speed、meanCadence、minimumCadence、maximumCadence、averageSpeed、maximumSpeed；步频仅展示时四舍五入为整数，速度仅展示时 m/s × 3.6 并保留一位小数；步频极值/最大速度仍取合格完整连续五秒窗口，平均值仍含整个 Running 的静止及缺失时段 |
| Heart rate zones | 五根累计时长柱、统一比例尺、Zone 1—5、五行完整明细、未归类时长及必要说明 | 仅使用 HeartRateZoneState 的累计毫秒与未归类结果，不按样本数计时、不在 UI 累计；保留 5.21 的有效性、阈值、保持法、停止结算和新场重置 |
| Activity summary | Running duration (mm:ss)、Total steps、Estimated distance (m) | 时长直接来自 SessionState.elapsedMs，显示截断秒的 mm:ss，分钟可超过 59，四小时为 240:00；总步数直接显示已有整数，距离为已有累计米数、一位小数，不自动换成 km 或重新估算 |

- 指标排列：Heart rate 的大数值优先，Min/Max/Mean 使用可换行的紧凑标签—值排列；保留简短的有效样本均值说明与手机接收时间含义。Motion 内常规宽度将 Cadence 与 Estimated speed 两个指标区域等宽并排，各自下面放自己的次要统计；空间不足或字体放大时按步频→速度纵排，单位和标签完整保留。Activity summary 首版使用三个纵向标签—数值行，通常标签左/值右；不足时每行改为标签在上/值在下，不强挤三个横向数值格。Summary 中 Running duration 指本场 Running 经过时间，包含静止/休息和已知缺失，不代表仅走跑时间；Starting 未开始计时，Stop 后冻结。
- 未知与真实零：保留 --，不得用 0 填补 null。初始正常 ACC 预热/等待四步的当前零配原状态；已知缺口、失败及 Retry 重新预热的当前值沿用 --/原因。HR 无效/无接触/停止后当前值和接收时间为 --，已有 Min/Max/Mean 不清除。Stop 后有 ACC 观测时当前步频/速度为 0、无观测仍 --；已取得累计/极值/平均值及不完整标记保留。新 Start、旋转、Retry、旧事件及静默停流行为仍由既有持有者决定，不加显示缓存、数据新鲜度超时、补值、定时器或自动恢复。
- 五柱布局：保留纵向五柱比较累计时长，绘图区高 160 dp，只包含柱体/参考线，不包含图题、比例尺文字、横轴或明细。五柱共用从零到当前最大累计毫秒的线性比例尺，柱宽沿用 24 dp、均分可用横向空间；全零时柱高为零，不造最小柱高、不除零，也不把非零的不足一秒时长强制画成零。比例尺起点与上限用 mm:ss 标在绘图区外，最大值为零时注明所有区间 00:00。轴顺序固定 Zone 1—5，标签可换行；不要把完整 bpm 范围和五个时长挤进五列或柱顶。
- 区间明细：柱图下方按 Zone 1—5 放五行可增长明细，显示色块、Zone、Very light / Light / Moderate / High / Very high、对应 <110 / 110–124 / 125–139 / 140–154 / ≥155 bpm 和累计 mm:ss；标签和时长使用主题文字色，绿/蓝/黄/橙/红只用于柱和标记，唯一来源为 HeartRateZoneColors。明细可分两行以保证大字体可读，不缩小黄色标签、不只靠颜色传意。单列 Unclassified time，不增加第六根柱、不强迫五柱时长等于 Running；保留 Estimated from received HR 及“末次有效区间保持、静默停流可能高估”的简短英文说明。没有有效 HR 时显示 No valid HR data 和五个 00:00；停止保留已有累计值和必要停止说明。Session 本步不新增占比、区间目标、训练建议或图表交互。
- 恢复入口：将现有 HR 状态、接触/无效原因、订阅错误及 Retry HR 留在 Heart rate 卡片；将 ACC 必要预热/等待确认/缺口/错误、不完整提示及 Retry ACC 放在 Motion 卡片附近，复用原回调和启用条件，移除原位置重复的 Retry ACC。配置重查仍在 8.1 Devices，ECG Retry 保持原区域。恢复按钮保留至少 48 dp 触控范围，长错误允许换行；不改变真实 SDK/订阅、恢复或采集资格判断。距离和速度明确为估计；不完整 ACC 提示说明缺失可能降低已记录距离、平均速度/步频，极值仅代表已记录合格窗口，不宣称准确率。
- 替代清理与共享组件：正式卡片替代 Heart rate/Steps 的开发标题和重复指标文本；SessionSummaryPanel 在 Session 调用处仅去掉被新卡片替代的 HR/区间/运动统计。其 UUID、日期/设备/保存资格/完整性等尚未替代信息暂留原开发区域；HR/运动历史计数、冻结快照、ACC/ECG 原始参数/样本开发区域也留对应后续步骤。按实际引用作最小拆分或调用调整，不整体删除共享 SessionSummaryPanel，不改 History 的现有摘要字段。允许 History 复用新 HeartRateZonePanel 的五柱/明细视觉改进；保留其已存储时长、Running 占比、未归类、日期/删除/查询及内容顺序，不借此实施 8.5。仅被正式区域替代的重复 UI 删除，所有底层状态、必要错误和恢复继续保留。
- 实施边界：使用现有 Kotlin/Compose/Material 3，不新增图表库、依赖、SDK/API、数据库字段、算法参数、统计公式、计时或持有者；不改 8.1 顶部/Devices、8.3 曲线及其 220 dp 高度、8.4 控制/导航、8.5 History 正式布局，不加暂停/继续、动画、进度环、模拟数据常驻或无关重构。横屏保持相同内容顺序并允许纵向滚动；系统字体放大时卡片增高/指标纵排/明细换行，不裁剪数值、单位或 Retry 按钮。
- 后续验收：核对 HR 有效/无接触/失败/恢复、统计保留；核对 ACC 预热零与缺口/无观测 --、停止零的有观测例外、平均/合格窗口极值、缺失提示及 Retry 原条件；用受控状态检查零、长数值、四小时 240:00、距离/速度舍入、五区间边界和配色、全零柱、不足一秒/不等时长/最大值变化、未归类、Stop 冻结与新场清零。实际检查浅/深、1.0/2.0 系统字号、横屏、长状态/错误及卡片滚动；对共享区间组件运行 History 回归。实施时运行相关检查、debug/测试 APK 构建及 lint，源码、受控/模拟器视觉和 Samsung/H10 分开记录，未执行项标 pending；不复用 8.1 结果声称 8.2 通过。
- 当前交付 / Current delivery：8.2 App 及两对文档已修改；本轮构建、自动/模拟器视觉检查通过，实际证据与 Samsung/H10 pending 见 5.30.1。Step 8.2 is implemented and both documentation pairs are synchronized. Build, automated and emulator visual checks passed; evidence and pending hardware validation are in 5.30.1. 未 commit/push。

### 5.30.1 步骤 8.2 实施与实际验证 / Step 8.2 implementation and actual validation（2026-10-01）

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

### 5.31 步骤 8.3：页面底部实时曲线 / Step 8.3: Bottom live chart card

- 确认日期：2026-10-01。用户指定曲线卡片放在 Session 页面最下方，其他规则采用推荐方案，并授权先实施。沿用 5.22 的数据、窗口、断段、刷新、冻结、Retry 和选择持有者规则，以及 8.0 的主题/基础字号；不重新制定采集算法。
- 位置与控件：标题 Live charts，位于 Activity summary 和必要会话完整性/不可保存提示之后，为 Session 滚动内容最后一张卡片。HR / Motion / ECG 使用 Material 3 FilterChip，选中外观和语义明确；Motion 内 Cadence / Speed 独立切换、一次只绘制一种单位。首次选择 HR，保持既有选择持有者，新会话/旋转不强制切回 HR。FlowRow 允许窄屏/放大字体换行，触控至少 48 dp。Start/Stop、保存恢复和导航仍用现有位置/条件，8.4 未实施。
- 图形尺寸/样式：绘图区自身高 220 dp，不包含标题、控件、状态和轴标签；16 dp 卡片边距/圆角、8/12 dp 间距，18 sp 标题、16 sp 正文、14 sp 次要说明，尊重系统字体缩放。主主题蓝色绘制 HR/步频/速度折线（2 dp）和有效点（半径 2 dp）；ECG 1 dp 折线不逐点画圆，孤立有效点仍用半径 2 dp 标记。有效数据按原顺序连接，null/breakBefore 断开，绘制限制在 Canvas 内；真实零可绘制，缺失不补零。所有可见 ECG 样本仍参与绘制，不抽样、平滑或平均。
- 横轴：沿用 Running 单调经过时间和 mm:ss，正常显示起点/中点/终点三刻度；短窗口从 00:00 起，重复的整数秒标签合并。用实际字号测量标签，宽度不足先省略中点，再将起止时间按 From/To 纵排；不缩字、不重叠。窗口说明为 HR/步频/速度 60 s、ECG 5 s，实际初始范围仍为从 Running 起到当前时刻。
- 纵轴：轴范围明确命名 Scale (unit)，不是本场或窗口 Min/Max 统计。HR/步频/速度从零到可见有效最大值加 10% 留白；HR/步频上界向上取整数，速度向上取一位小数。ECG 基于 min(0, 可见最小值) 和 max(0, 可见最大值)，两侧加原跨度 10% 留白并向外取整数 µV，显示负值与零参考线。全零非 ECG 使用 0—1；全零 ECG 使用 -1—1；没有有效数据时仅显示 -- to -- 和 No valid chart data in this window，内部有限绘图范围不宣称实测数据。范围随可见窗口变化，不改变原值或统计。
- 状态/恢复：区分 Not started、Waiting for data、Waiting for valid data、Live、Stopping/Stopped/Failed · chart frozen。HR/ACC 订阅错误和 Retry 保留在指标卡片；ACC/ECG 配置错误与未确认提示在曲线卡片保留，并引导 Devices。ECG 显示原订阅错误，ECG 选中或失败/有错误时提供 Retry ECG，因此在 HR/Motion 中也能发现 ECG 失败；按钮出现状态仍为 IDLE/FAILED/STOPPED，启用仍要求有效连接、ongoing 且无 CHECKING，调用原回调，不增加自动重试、停流超时或状态持有者。
- 清理范围：删除 Session 的原始 ACC/ECG 样本/参数/时间戳/缓存、HR/运动历史点数与时间、冻结快照、SessionSummaryPanel 开发元数据和重复显示；停止为 UI 收集这些无用流，保留管理器内数据和保存快照。保留必要 Session 状态/结束原因、四小时限制、完整性提示、无有效观测不可保存说明、Save/Retry save/Discard、Devices、指标状态与恢复；仅 Session 隐藏 Save session ID，History 的默认 SavePanel、摘要/曲线/数据库字段保留。
- 交互与边界：只切换曲线，不增加缩放、拖动、点选、浮层、动画、图表库或通用框架。History 继续原 ChartPlot/180 dp 布局，不通过共享组件提前实施 8.5；8.3 新卡片只用于 Session。SDK/API、采集、算法/阈值/统计、生命周期、历史缓存和 SQLite 查询/保存不变。横屏维持内容顺序、纵向滚动，长文本/单位/按钮允许增高和换行。
- 验收：运行相关单元/Compose 回归、debug/测试 APK 和 lint；实际检查深浅、1.0/2.0 字号、横屏、控件选中/切换、空数据/单点/真实零、断段、短窗口、带负值且 650 个样本的受控 ECG、状态/长错误及原恢复条件。源码、自动/受控截图与 Samsung/H10 独立记录；模拟器数据明确标为测试，真实信号滚动、ECG 刷新性能、旋转/后台/设备恢复仍需 H10 验证，未执行标 pending。
  Confirmed scope: Place Live charts last in the Session scroll content, with wrapping selected HR/Motion/ECG chips and Cadence/Speed subchoices. Preserve selection ownership and all Section 5.22 acquisition, buffering, gaps, timing, freeze and retry behavior. Only the plot is 220 dp high. Use theme-blue 2 dp lines/2 dp-radius markers for HR/motion, 1 dp ECG lines with isolated-point markers and every visible sample. Show up to three measured mm:ss labels, remove duplicate/middle labels as needed and stack endpoints when necessary. Scale labels describe display bounds: zero-based non-ECG with 10% headroom, signed ECG with 10% margins, integer bpm/steps-min/µV and one-decimal km/h. Empty data retains placeholders; genuine zero and negative ECG remain. Keep necessary statuses, configuration errors, original recovery guards and save actions; remove unnecessary Session development displays and UI collectors only. History keeps its existing plot, summary and storage behavior. No steps 8.4/8.5, dependencies, algorithms, SDK or lifecycle changes. Validation must separate source, controlled/emulator visuals and pending H10 hardware.

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

### 5.32 步骤 8.4a—8.4d：参考图 Session 最终界面整合 / Reference-based final Session UI integration

- 上一轮 Motion 文档修订记录（2026-10-03）：8.4a—8.4c 已实施，8.4d/8.5 pending。按用户“Motion 中不需要有速度，只需要展示步频”的最新要求，下列 b/c 展示规则与双语提示词已修订为最终目标；现有 Motion 速度 UI 在 8.4d 移除，具体计划见 5.32.4。本轮仅同步文档，不执行 8.4d，不修改 App/测试或进行新的构建、测试、运行视觉、真机验证。以下原始文档状态和 a—c 实施记录按当时情况保留。
  Previous Motion documentation revision: Steps 8.4a–8.4c are implemented; 8.4d/8.5 remain pending. The user's latest cadence-only Motion requirement updates the b/c display rules and bilingual prompts below as the final target. Remove the existing Motion speed UI during 8.4d under 5.32.4. This turn synchronizes documentation only, without implementing 8.4d, editing App/tests or running new builds/tests/runtime visuals/hardware validation. The original documentation status and a–c implementation records below retain their historical meaning.

- 确认与状态（2026-10-03）：用户要求将此前提供的中英文提示词写入文档。参考为工作区 ui/Session-HR.png、Session-Motion.png、Session-ECG.png，三图已实际查看，均为标注 DEMO DATA 的界面概念图。本轮仅保存规则与提示词，不实施 App；各子步骤均 pending。每次使用 prompt.md 中“通用要求＋对应子步骤”的一个语言版本，按 a → b → c → d 顺序执行一个编号。
  Confirmation/status: The user requested saving the previously supplied bilingual prompts. The three workspace ui/Session-*.png files were visually inspected and are explicitly demo-data concepts. This turn saves documentation only; every substep remains pending. Use one language version of the shared instructions plus the selected prompt, implementing one substep at a time in a → b → c → d order.
- 范围与替代关系：8.4 扩展为 Session 最终视觉整合，允许调整 8.1—8.3 的 UI 展示。实施对应子步骤时，新规定覆盖此前“强度只在顶部”“HR zones/summary 在曲线之前”“曲线为滚动最后卡片”“Session 160 dp 竖向五柱”和主题蓝 HR 曲线等相关布局/绘制规则；旧章节保留为当时实施与验证记录。未替代的数据、功能、主题及行为规则继续有效。8.4 不实施 8.5；History 正式列表/详情不重排。
  Supersession: Step 8.4 includes final Session visual integration and may adjust the 8.1–8.3 presentation. When the relevant substep is implemented, it supersedes header-only intensity, zones/summary before charts, charts last, Session's 160 dp vertical zone plot and theme-blue HR plot styling. Earlier sections remain historical implementation/validation records. All unaffected data, functional, theme and behavior rules remain. History's formal list/detail redesign stays in 8.5.
- 共用基线：Kotlin/Compose/Material 3、固定系统深浅主题、默认字体；字号 24/18/16/14 sp，当前 HR/步频 56 sp；padding/卡片间距/圆角 16 dp、内部 8/12 dp、图标 24 dp、触控至少 48 dp。英文 UI/注释，未知 --、真实零与负 ECG 不变。尊重系统字体和安全区域，横屏同序、纵向滚动、增高/换行，不裁剪数字/单位/按钮、不强压参考长图为一屏、不使用图中演示值。
  Shared baseline: Reuse Kotlin/Compose/Material 3, fixed system light/dark mode and default fonts; 24/18/16/14 sp base sizes, 56 sp current HR/cadence; 16 dp padding/card spacing/corners, 8/12 dp internal spacing, 24 dp icons and at least 48 dp touch targets. Keep English UI/comments, --, genuine zeros and signed ECG. Respect system fonts/insets with same-order landscape scrolling and growing/wrapping layouts. Do not compress the reference long image into one viewport or use its demo values.

#### 5.32.1 8.4a：页面框架、连接状态、控制与导航 / Page structure, connection status, controls and navigation

- 2026-10-03 用户已授权直接实施 8.4a；本轮沿用下列规则，仅修改此子步骤所需 UI，实际结果随后记录，8.4b—8.4d/8.5 仍 pending。
  The user authorized direct implementation of 8.4a on 2026-10-03. Apply the following rules to this substep's UI only; record actual results after validation. Steps 8.4b–8.4d/8.5 remain pending.

- Scaffold 顶部固定等宽 Session/History 页签，蓝色选中项和下划线，删除重复 Session 页面标题；中间滚动、Session 底部 Start/Stop；a 暂保留现有指标/强度，重排和绘图留 b/c。连接/Data streams 合为顶卡：左侧蓝牙、状态、电量及 Devices 齿轮，连接区/齿轮均开原弹窗；右侧 HR/ACC/ECG 指示点及可见状态文本，Receiving 绿、Starting/Stopping 橙、Failed 红、Idle/Stopped 灰，READY 不等于 Receiving；窄屏/大字体纵排。
  Use fixed equal Session/History tabs with blue selection/underline, no duplicate Session title, scrolling content and bottom Session Start/Stop controls. Keep existing metrics/intensity until b/c. Combine connection/Bluetooth/battery/Devices gear and actual HR/ACC/ECG states in one header; both device entry points open the existing dialog. Receiving is green, Starting/Stopping orange, Failed red and Idle/Stopped gray, with visible text. READY is not Receiving; stack when necessary.
- 两个图标＋英文标签控件，推荐圆形 64 dp，不新增 Pause/Resume，原启用条件/回调不变，Starting 可 Stop，Stopping/保存阻塞不重复 Start；简洁状态、必要禁用原因、可读英文结束原因。保存信息靠近会话状态，长错误可滚动；两页均可达 Saving/Failed/Retry save/Discard，不常驻 UUID/No session to save，不将上一场 Saved 归给新场，Discard 确认后调用原方法，不改控制器。
  Use two icon-and-label controls, preferably 64 dp circles, preserving Start/Stop callbacks/guards and Stop during Starting without Pause/Resume. Show concise state, required disabled reasons and readable end reasons. Keep save status and recovery accessible on both pages, with long errors scrollable. Avoid persistent UUID/no-session notices and attributing the previous save to a new session. Confirm Discard before the existing call; leave the controller unchanged.
- 同一 SensorActivity 切换两页，不结束、断开、清零、重订阅或重置曲线选择；保留 Session 滚动位置、History 返回层级/查询刷新/分页/重试/删除确认、原后台/锁屏/返回欢迎/旋转规则；无导航依赖/退出拦截。检查页签、弹窗、控制、保存恢复、深浅/大字号/横屏，底控不遮内容。
  Keep both pages in the same SensorActivity without stopping/disconnecting/resetting/resubscribing. Preserve Session scroll position, chart selection, existing History back/query/refresh/pagination/retry/delete and lifecycle rules. Add no navigation dependency or exit interception. Check tabs, Devices, controls, save recovery, themes, enlarged fonts and landscape without bottom-control occlusion.

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

#### 5.32.2 8.4b：指标卡片、运动汇总与横向区间 / Metric cards, summary and horizontal zones

- 2026-10-03：用户要求现在实施 8.4b，授权按本节和 prompt.md 写入代码并验证；仅执行本步骤。
  The user requested implementation of 8.4b, authorizing code edits and validation under this section and prompt.md; this step only.

- 滚动顺序：连接/Data streams → Heart rate → Motion → Live charts → Activity summary → HR zones → 必要会话/保存恢复信息。仅移动现有曲线，绘制留 c。强度移入 HR 卡且只显示一处；左当前 HR/bpm、右整场 Max/Mean、底真实 Last received (phone)；保留无效/接触/停止/失败/Retry，不以无效或停止状态显示有效强度。
  Order scrolling content as connection/data streams, HR, Motion, charts, summary, zones and necessary session/save recovery. Move the existing plot without c's redesign. Show intensity once inside HR, with current HR/bpm, whole-session Max/Mean and actual phone reception time, preserving invalid/contact/stopped/failed/retry behavior.
- Motion 最终只展示当前步频及整场 Mean/Max cadence，单位 steps/min，保留缺失/预热/停止/失败说明及 Retry ACC；不显示 Estimated speed/Mean speed/Max speed 或 km/h，速度和最小步频展示移除，按 5.33 执行。Summary 为 Running duration/Total steps/Estimated distance 三区域，不足宽度纵排/换行，沿用真实值、mm:ss/m/估计意义。
  Motion's final display contains only current and whole-session Mean/Max cadence in steps/min, with missing/warmup/stopped/failed explanations and Retry ACC. Do not show Estimated speed/Mean speed/Max speed or km/h; remove speed/minimum cadence displays under 5.33. Summary has duration/steps/estimated distance areas that wrap/stack, retaining actual values and units/estimate wording.
- 仅 Session 改五行横条，每行 Zone/原英文强度/bpm/条形/mm:ss；共用既有绿蓝黄橙红，最大累计区间时长为满宽，同尺不标 Running 百分比；全零空条/00:00、未观测沿旧占位，保留 Unclassified/不完整。文字用主题前景。自适应卡高替代 Session 160 dp 竖图，不改 History 共用五柱布局。检查统计完整性、未知/零/停止及字体/方向/主题。
  Only Session switches to five horizontal zone rows with zone/intensity/bpm/bar/duration, the shared palette and a common scale relative to the longest duration rather than Running percentages. Preserve zero versus missing, Unclassified and integrity information with readable foreground text. Let card height grow instead of using the Session 160 dp vertical plot; retain History's plot. Check statistics, states, fonts, orientation and themes.

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

#### 5.32.3 8.4c：三类实时曲线 / Three live chart views

- HR/Motion/ECG 胶囊选择（蓝选中/中性未选中/语义明确），Motion 最终只选择 Cadence（steps/min），不提供 Cadence/Speed 子切换或速度曲线；沿用原选择持有者，按 5.33 移除速度入口。220 dp 绘图区、5 分钟 HR/步频和 5 秒 ECG；采用图示五分钟窗口。刷新/缓冲/时间锚点/断段/冻结/Retry 不变。
  Use pill selectors with blue selection and explicit semantics. Motion will select only Cadence in steps/min, with no Cadence/Speed sub-selection or speed plot. Reuse existing selection ownership and remove speed entries under 5.33. Keep the 220 dp plot, five-minute HR/cadence and five-second ECG, plus existing refresh/buffers/anchors/gaps/freeze/retry; use the reference five-minute window.
- HR 红线浅红填充，Motion 蓝线浅蓝填充，ECG 蓝细线；每个有效连续段单独绘线/填充，不跨 null/breakBefore，不补数据。保留负 ECG/全部可见样本，不平滑抽样。增加网格/纵刻度，沿 8.3 范围/留白/精度；轴界不当极值，横轴真实 Running mm:ss，按宽度/字号增减，ECG 宽屏可加秒刻度，不重叠缩字。
  Use a red HR line/subtle fill, blue Motion line/subtle fill and thin blue ECG. Draw each continuous segment independently, with no interpolation across null/breakBefore. Preserve negative/all visible ECG samples without smoothing/decimation. Add grids/ticks with 8.3 bounds/margins/precision, using real Running mm:ss and adaptive non-overlapping labels rather than statistical-extrema axis labels.
- 上方复用整场 Session mean/max，Motion 使用步频统计；均值虚线同值，仅有效且在比例尺内绘制，不重算/强扩轴。ECG 实际选中率/实际窗口样本数，未知 --、不硬编码 130/650。保留等待/空/失败/冻结/配置错误，非 ECG 选中时 ECG 失败仍可 Retry；无缩放/拖动/动画/图表依赖。最终检查 HR/Cadence/ECG 三视图、零/负/断段/空/长标签/主题/大字号/横屏；静态图不作为实时性能证据。
  Reuse whole-session mean/max, using cadence statistics for Motion; draw the same mean as a dashed line only when valid and in range, without recalculation or forced expansion. Show actual selected ECG rate/window count, never fixed 130/650. Retain all necessary states/configuration/recovery, including ECG retry from other selections after failure. No zoom/drag/animation/dependency. Check the final HR/Cadence/ECG views and edge cases; static captures do not validate live performance.

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

#### 5.32.4 8.4d：最终整合、清理与验收 / Final integration, cleanup and validation

- 范围拆分（2026-10-03）：以下为原 8.4d 功能整合要求，已实施部分见 5.33.1；尚未完成的参考图布局、样式及视觉验收现划入 8.4e（5.34），不再以本节的功能/测试结果宣布整个 8.4 完成。
  Scope split: These are the original 8.4d integration requirements; implemented work is recorded in 5.33.1. Remaining reference layout, styling and visual acceptance now belong to 8.4e (5.34). Functional/test results here do not complete overall 8.4.
- 用户最新要求优先于此前 8.4 提示词中的功能差异保留项：按 5.33 的差异清单实施 Session 最终展示，删除速度与 Min 指标，加入真实暂停/继续及五分钟窗口；不是只隐藏按钮或更改文字。保留有效真实数据、未知/零/负 ECG、必要错误与恢复。整阶段 diff 中会话计时、HR/运动缓存和历史恢复的必要修改属于本次明确授权，必须列明，不再声称数据层完全未变。
  The latest user instruction supersedes earlier 8.4 functional exceptions: implement Section 5.33's final Session display, removing speed/minimum metrics and adding real Pause/Continue and five-minute windows. Implement behavior as well as presentation. Preserve real data, unknown/zero/signed ECG and necessary errors/recovery. The required session-timing, HR/motion-cache and history-resume changes are explicitly authorized and must be reported rather than described as an unchanged data layer.
- 验证三视图、系统深浅、字号 1.0/2.0、竖横屏/滚动/安全区域、暂停前后身份/累计/计时/断段/预热/旧事件/结束保存、失败恢复/分页/删除/切页/旋转。运行相关 unit/Compose/SQLite/debug/lint，实际查看可执行截图，区分源码、自动、实际视觉和 Samsung/H10。不得使用图中演示值、固定采样率或固定轴值。同步两对文档，无 commit/push，不推进 8.5。
  Validate the three views, themes, fonts, orientations/insets, pause/resume identity/totals/timing/gaps/warmup/stale events/final saving, recovery and History regression. Run relevant unit/Compose/SQLite/debug/lint checks and inspect available captures. Separate source, automated, actual visuals and Samsung/H10 evidence. Never use reference demo values, fixed sample metadata or fixed axes. Synchronize both documentation pairs without commit/push or 8.5.

### 5.33 图示功能清单与执行规则（2026-10-03） / Reference functions and execution rules

- 授权：用户要求“检查现在图示中的设计与文档规划不同的地方，比如删除了预估速度等指标，严格按图示中展示的功能实施”，并回答“全部按图示：加入暂停/继续，HR/步频窗口改为 5 分钟”。此新要求覆盖此前布局与功能例外；旧实施记录保留，不将旧测试结果用作新功能已通过的证据。
  Authorization: The user requested checking design/plan differences and implementing the displayed reference functions, then explicitly confirmed Pause/Continue and five-minute HR/cadence windows. This supersedes previous presentation/functional exceptions; historical validation is not evidence for the new behavior.

| 项目 | 旧规划/代码与图示差异 | 当前目标与执行项 |
|---|---|---|
| HR | 多出 Min HR、常驻说明与流状态重复文本 | 当前 HR/bpm、强度、Max HR、Mean HR、Last received；无 Min |
| Cadence | 多出 Estimated/Mean/Max speed、km/h 和 Min cadence | 当前步频、Mean/Max（steps/min）；无速度、无 Min |
| 曲线入口 | 多出 Cadence/Speed 子切换及开发标题 | 仅 HR/Motion/ECG，Motion 直接选择 Cadence，无速度入口 |
| HR/步频窗口 | 原为最近 60 秒 | 最近 300 秒；HR 最多 301 点，运动最多 1201 条，保持有界 |
| 图上统计 | 文档要求 Session mean/max 标签与额外说明 | HR: Average HR/Max HR；Motion: Mean/Max；复用整场统计，虚线仅有效且在轴内 |
| ECG | 选中率/窗口数量标签和常驻对齐说明 | Sampling Rate/Samples；实际配置与实际五秒窗口数量，不硬编码 130/650；保留负值及全部可见样本 |
| Activity Summary | 多出平均速度估计说明与 Running 标签 | Duration、Total steps、Estimated distance；未知 --，真实零与估计距离保留 |
| HR Zone | 多出每行强度文字、比例尺/估计说明、零值提示 | Z1–Z5、原 bpm 范围、五色横条、mm:ss；共用最大时长比例尺，零条为空；未归类 >0 或异常时才显示必要提示 |
| 控制 | 仅 Start/Stop | 左 Pause、中 Start/Continue、右 Stop；每项按真实状态启用 |

- 暂停/继续：仅 Running 可 Pause；进入 Pausing 后立即拒绝数据/刷新并取消三路订阅，保留 BLE 连接、会话 UUID/原开始日期、累计步数/距离/HR统计/区间/历史，不冻结提交保存。完成取消后才 Paused；清理未完成不可 Continue。暂停不计入 Duration、均值分母、区间时长或四小时上限。Continue 在当前连接/就绪满足时重新启动原三路流；首个真实数据后恢复计时，保留同场 UUID，更新代次拒绝旧事件，ACC 重新预热，不跨暂停计步/估计步长。Stop（含暂停中）及断线/锁屏/后台按原规则结束并冻结/保存一次，不自动继续。
  Pause/Continue: Only Running can pause. Pausing immediately rejects data/refresh and cancels the three subscriptions, retaining BLE connection, UUID/original start date, totals/statistics/zones/history without submitting a saved snapshot. Continue is unavailable until cleanup finishes and status is Paused. Paused time is excluded from duration, mean denominators, zone durations and the four-hour limit. Continue rechecks current connectivity/readiness and restarts the original streams; timing resumes with the first real sample, UUID remains, generation changes reject stale events, and ACC warms up without connecting steps/strides across pause. Stop, including during pause, or disconnect/lock/background ends and freezes/saves once under existing rules. Never resume automatically.
- 历史与缓存：暂停冻结显示和历史写入；继续保留 HR/运动先前数据并显式断段，ECG 清旧原始窗口并重新锚定。已有每秒末点和同桶替换规则不变，故暂停边界同一秒仍只留末点；暂停间隔不伪造数据。保存字段/schema/事务不变；速度及最小值仍计算/保存供 History 使用，仅从 Session 展示删除。步伐算法/K=0.45/采样配置/HR阈值不变。History 页面不按 Session 图重新设计。
  History/buffers: Pause freezes display and history writes. Continue retains prior HR/motion points with explicit breaks; ECG clears/reanchors its raw window. Existing last-point-per-second and same-bucket replacement remain, so a pause boundary within one second still retains only the final point. Do not invent paused data. Save fields/schema/transactions remain unchanged; speed/minimum values continue to be calculated/stored for History but are removed from Session. Preserve step algorithms/K=0.45/sample settings/HR thresholds and defer History redesign.
- 正常态按图示精简；错误、权限/蓝牙、无数据、等待/预热、暂停、停止/失败、Retry 和保存恢复是按真实状态出现的必要界面，不能从一张 Running 概念图推断删除。图中 DEMO DATA、固定例值、130 Hz/650、坐标数值不是功能或运行证据。保留英文 UI/注释、自适应字号、滚动、深浅主题和安全区域，不能将长图强压为一屏。
  Keep normal-state content concise as shown. Necessary errors, permissions/Bluetooth, unknown/waiting/warmup, pause/stopped/failed, retry and save recovery appear according to real state; a Running concept cannot imply their deletion. Demo markings/values, 130 Hz/650 and axis values are not runtime functions or evidence. Preserve English UI/comments, adaptive fonts, scrolling, themes and safe areas rather than compressing the long concept into one screen.

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

### 5.34 步骤 8.4e：Session 参考图与单屏最终展示 / Step 8.4e: Session reference styling and final single-screen presentation

- 按用户要求，将原 8.4e 与 8.4f 的方案、双语提示词和所有后续实施记录合并为一个 8.4e，不再设置独立的 8.4f 步骤。当前方案覆盖历史记录中的滚动、卡片 Retry、旧标题及旧状态行要求；证据目录保留原名称。
  At the user's request, the former 8.4e and 8.4f plans, bilingual prompts and all follow-up records are consolidated as 8.4e, with no separate 8.4f step. Current requirements supersede historical scrolling, card Retry, headings and status rows; evidence directories retain their original names.
- 当前状态：默认字号竖屏的参考图布局、单屏、卡片清理、Stop 重置与图表等高已实施并有验证记录；现固定手机页面为竖屏（5.34.3.8）；2.0 系统字号竖屏仍有裁切，Samsung/H10 真机验收 pending，不宣称整个 8.4 UI 全面完成。完整中英文执行提示词见 prompt.md 的统一 8.4e 章节。
  Status: Default-font portrait reference styling, single-screen presentation, cleanup, Stop reset and equal chart heights have implementation and validation records. Phone pages now request fixed portrait (5.34.3.8); portrait font 2.0 still clips content and Samsung/H10 acceptance is pending; overall 8.4 UI is not universally complete. The single bilingual execution prompt is in prompt.md under 8.4e.

#### 5.34.1 合并后的当前目标 / Consolidated current targets

| 区域 / Region | 当前要求 / Current requirement |
|---|---|
| 方向 / Orientation | Welcome/Session/History 手机页面固定竖屏；旋转设备不切横屏，见 5.34.3.8。 / Phone pages request fixed portrait; device rotation must not switch to landscape. |
| 导航与全页 / Navigation and page | 等宽 Session/History、参考图背景/边框/圆角与深浅主题；导航下 8 dp，主卡间 5 dp，默认竖屏单屏无滚动。 / Equal tabs, reference colors/borders/corners and both themes; 8 dp top gap, 5 dp card gaps, one non-scrolling default portrait screen. |
| 连接与三流 / Connection and streams | 左连接/电量/蓝牙/Devices，右 HR/ACC/ECG 指示灯及标签；竖线分栏，无 Data Streams 标题，保留真实状态。 / Connection/battery/Bluetooth/Devices left, stream dots/labels right, divider, real states and no Data Streams heading. |
| HR 与 Cadence | 左大值与单位同行、HR 强度和真实 Last Received；右 Mean/Max 居中且值加粗；无 Session 速度/Min。 / Large left values/units, HR intensity and reception time; centered bold right statistics, no Session speed/minima. |
| 三图表 / Charts | 等宽胶囊、左右统计、真实 ECG 率/计数；HR/步频五分钟、ECG 五秒；统一原基础图高 +12 dp，切换及 HR 状态不撑高卡片；HR 状态在单位行右侧，Motion/ECG 无状态行，空图无 Not started。 / Equal pills, real statistics/ECG metadata/windows, common plot height; HR state shares the unit row, no Motion/ECG status row or idle Not started. |
| 三项汇总 / Summary | 无 Activity Summary 标题；Duration/Total Steps/Estimated Distance 等宽同排，24 sp 值、上下各 5 dp 内边距，距离 m。 / Untitled equal summary cells on one row, 24 sp values, 5 dp vertical padding and m. |
| HR Zone | 五行 zone/range/bar/time 对齐，原五色/阈值/共同时长尺度，行上下各 3 dp；切图不改变尺寸。 / Five aligned rows, original colors/thresholds/shared scale, 3 dp vertical row padding, unchanged dimensions across chart selection. |
| 底部控制 / Controls | Pause、Start/Continue、Stop；64 dp 圆按钮/32 dp 图标；无常驻文字及上方状态行；内容底部/控制顶部无额外留白，控制底部 4 dp 与安全区保留。 / Three 64 dp circles/32 dp icons, no captions or footer status row; no extra content-bottom/control-top space, retain 4 dp below controls and insets. |
| 错误与 Retry / Errors and Retry | 全部 Session 卡片无 Retry/占位；错误、权限、保存状态与确认 Discard 在 Devices；保留底层重试与 History 保存重试。 / No card Retry/placeholder; full error/permission/save/Discard details in Devices; underlying and History retry retained. |
| 手动 Stop / Manual Stop | 先冻结提交保存，订阅清理后清空当前 Session 并回 Idle/HR；保留连接/配置/历史及失败快照重试，旧事件无效。 / Freeze/submit before cleanup and reset to Idle/HR; preserve connection/configuration/history/retryable snapshots and reject stale events. |

#### 5.34.2 实施边界与验收 / Scope and acceptance

- 只处理统一 8.4e；复用现有 Compose/Material 3 和 Session 局部样式，无新依赖或无关重构。保留 8.4d 已实现的 Pause/Continue、同场身份与暂停排除计时。除用户授权的 Stop 重置外，不改 SDK、采样、算法/K、统计公式、缓存容量、保存资格/schema 或 History 功能；History 正式布局仍为 8.5。
  Implement consolidated 8.4e only using existing Compose/Material 3 and local styles. Preserve 8.4d pause/resume/identity/timing; except for authorized Stop reset, do not change SDK/sampling/algorithms/K/statistics/buffer capacities/save eligibility/schema or History. History layout remains 8.5.
- 默认目标手机竖屏字号 1.0 对照三张 ui/Session-*.png；完整页面各留一张未拼接、未整页缩放截图，主要内容不得滚动、折叠、分页或隐藏。图示值/DEMO DATA 不进入生产；无 H10 的有数据截图明确标注测试数据。检查深浅、无数据/暂停/错误、长数字/错误、控件与导航、三图表等高和下方尺寸稳定、Stop 快照保存/失败重试/旧事件拒绝。
  Compare all three references at target-phone portrait/font 1.0 using complete unstitched captures without whole-page scaling. No scrolling/collapsing/paging/hiding main content or production demo data. Label no-H10 fixtures. Check themes, data states, long values/errors, controls/navigation, chart/lower-region stability and Stop snapshot/retry/stale-event handling.
- 按实际改动运行必要的 Compose/单元回归、debug/测试 APK 与 lint，分别记录新证据，不重算历史测试为本轮结果。保留竖屏大字号裁切待办，手机固定竖屏、不再适配横屏布局，不关闭字体缩放或私自恢复滚动；模拟器不替代 H10 实测。两对文档同步，不自动 commit/push，不推进 8.5。
  Run change-appropriate Compose/unit regression, debug/test-APK builds and lint; separate current evidence from historical runs. Retain portrait enlarged-font gaps; phone pages request fixed portrait without landscape layout without disabling font scaling or restoring scrolling. Emulator results do not establish H10 validation. Synchronize both document pairs; no automatic commit/push or 8.5.

#### 5.34.3 8.4e 合并实施与验证记录（2026-10-03） / Consolidated implementation and validation

- 以下八组记录按实施顺序归入同一个 8.4e。旧阶段的滚动、Retry、标题、状态行和尺寸描述只代表当时版本；当前验收规则以 5.34.1—5.34.2 为准。日志及截图目录保留原名，不移动或伪造历史证据。
  Eight chronological records belong to the same 8.4e step. Earlier scrolling, Retry, headings, status rows and sizes describe historical versions only; current acceptance follows 5.34.1–5.34.2. Original evidence-directory names are retained.
- 当前验证概况：Stop 重置实施时 228 项单元测试通过；最新图表尺寸修复的 18 项 Compose 检查、debug/测试 APK 和 lint（0 errors、18 warnings）通过。其他阶段的测试数量按各轮原记录保留，不相加为一次完整回归。默认字号竖屏通过；竖屏大字号适配及 Samsung/H10 实测仍 pending；横屏改为验证固定竖屏。
  Current evidence: 228 unit tests passed during Stop-reset implementation. The latest chart-sizing change passed 18 Compose checks, debug/test-APK builds and lint (zero errors, 18 warnings). Earlier counts retain their per-run scope and are not added into one full regression total. Default-font portrait passes; portrait enlarged-font adaptation and Samsung/H10 validation remain pending; rotation now checks portrait locking.

##### 5.34.3.1 8.4e：初始参考图布局与验证（历史阶段） / Initial reference layout and checks (historical stage)

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

##### 5.34.3.2 8.4e：单屏布局与 Retry 清理 / Single-screen layout and Retry removal

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

##### 5.34.3.3 8.4e：空闲文字与空间分配 / Idle labels and space allocation

- 按用户要求，正常 Idle 不再显示底部 `Session: Idle · Details`，没有为空行保留占位；空图表不再显示 `Not started`。暂停/暂停中也移除底部重复的普通状态入口，暂停状态继续由图表提示；等待数据、停止、错误以及保存状态的必要提示继续保留；错误或保存待处理时仍可打开完整详情。
  Normal Idle no longer renders `Session: Idle · Details` or its reserved row; idle charts omit `Not started`. Pausing/paused states also omit the redundant generic footer entry while the chart retains its pause status. Necessary waiting, stopped, error and save notices remain, including full details when an error/save action needs attention.
- 底部圆形按钮由 48 dp 增至 64 dp，图标为 32 dp，控制区上下内边距保持 4 dp。无 Details 行时，HR Zone 五行分别增加上下各 2 dp 的间距，卡片总计增加 20 dp；有必要详情入口时沿用紧凑区间间距，避免挤压内容。数据处理及控制回调不变。
  Footer circles increase from 48 to 64 dp, with 32 dp icons and the existing 4 dp vertical padding. Without a Details row, each HR Zone row gains 2 dp padding above/below, adding 20 dp to the card. Necessary detail states retain compact zone spacing. Data processing/control callbacks are unchanged.
- 验证：最终 debug/测试 APK 构建与 lint 通过（0 errors、18 warnings）；首轮 40 项相关仪器检查有 1 项暂停提示裁切失败，修正后的最终 25 项 Session/控制/顶部回归全部通过，图表 15 项在首轮通过，不重复计数。默认字号竖屏三图表、深浅主题、空数据/暂停/错误以及完整错误详情通过生产组件检查；实际无 H10 App 的三图表、无 Idle Details/Not started、无页面滚动及 History 返回也通过。证据位于 build/step84f-spacing-validation，最新实际截图 actual-light-HR.png，生产组件截图 verified-captures。本轮未重跑单元测试；之前大字号/横屏适配缺口仍保留，未进行 Samsung/H10 验收，未推进 8.5，未 commit/push。
  Verification: Final debug/test-APK builds and lint pass (zero errors, 18 warnings). The initial 40 relevant instrumentation checks found one clipped paused notice; after correction, all 25 final Session/control/header regression checks pass. The 15 chart checks passed earlier and are not counted again. Production-component checks cover default-font portrait, all three charts, both themes, empty/paused/error states and full errors. Actual no-H10 App checks confirm all chart selections, absent Idle Details/Not started, no page scrolling and History return. Evidence is under build/step84f-spacing-validation, including actual-light-HR.png and verified-captures. Unit tests were not rerun; previous enlarged-font/landscape limitations remain. No Samsung/H10 acceptance, 8.5, commit or push.

##### 5.34.3.4 8.4e：移除状态行并扩大图表/区间 / Remove status rows and expand charts/zones

- 按用户要求，Motion 和 ECG 曲线卡不再渲染数据状态文字行，包括等待、停止、失败及暂停/冻结文字；图表刻度、单位、统计和空数据语义保留。HR 原有必要状态行沿用。Motion/ECG 绘图区在相同宽度/字号下增高 12 dp（沿用字体比例），不改变五分钟/五秒窗口、数据或绘图算法。
  Motion/ECG omit their data-status text rows, including waiting/stopped/failed/paused labels, while preserving axes, units, statistics and empty-data semantics. HR retains its necessary status row. Motion/ECG plots gain 12 dp at the same width/font scale, retaining font scaling, chart windows and data/rendering algorithms.
- Session 按钮上方不再渲染任何 Session/Data error/Save/Details 行及占位。错误、数据不完整、开始受限原因、保存状态及确认后 Discard 统一放入顶部齿轮打开的 Devices 弹窗；History 的保存重试保留。删除原底部 SessionDetails 组件和仅用于该行的 saveSummary 参数，不保留旧入口。Devices 原扫描/连接/配置行为不变。
  Remove every Session/Data error/Save/Details footer row and its reserved space. Errors, incomplete-data notices, blocked-Start explanations, save state and confirmed Discard are available in the existing gear/Devices dialog; History save retry remains. Remove the obsolete footer SessionDetails component and saveSummary parameter. Device scanning/connection/configuration behavior is unchanged.
- 卡片间距由 4 dp 增至 5 dp；五行 HR Zone 上下间距统一各 3 dp，不再因状态入口切换卡片高度。底部 64 dp 圆形按钮及其回调保留。主要区域仍须在默认字号竖屏单屏显示。
  Card gaps increase from 4 to 5 dp. All five zone rows use 3 dp vertical padding on each side, with no status-dependent card sizing. Keep the 64 dp footer controls and their callbacks. Main regions must still fit default-font portrait without scrolling.
- 验证：debug/测试 APK 构建与 lint 通过（0 errors、18 warnings）。本轮 57 项相关 Compose 检查首轮通过 55 项；2 项旧 ECG 状态文字断言按新规则更新后定向复测通过，57 项不同检查均有通过结果，未重复计数。覆盖三种曲线、Motion/ECG 全部订阅状态文字移除、深浅主题、默认字号竖屏完整区域/文字无裁切、暂停/错误及 Devices 中的错误查看、保存阻塞/丢弃与 History 重试。实际无 H10 App 三种空图表及 History 返回检查通过；截图/日志位于 build/step84f-layout2-validation，其中 captures 为明确标注的测试数据截图，actual-light-* 为实际 App 无数据截图。本轮未改算法/订阅/保存控制器，未重跑单元测试、未验证 H10，既有大字号/横屏缺口不因此关闭；两对文档同步，未推进 8.5，无 commit/push。
  Verification: Debug/test-APK builds and lint pass (zero errors, 18 warnings). The initial 57 relevant Compose checks passed 55; after updating two obsolete ECG-status assertions, both targeted rechecks pass. All 57 distinct checks have passing results across runs without double-counting. Coverage includes all three plots, removal of Motion/ECG status text for every subscription state, both themes, default-portrait full-page/text fit, paused/error states, Devices error access, save blocking/discard and History retry. Actual no-H10 App empty-chart and History-return checks pass. Logs/screenshots are under build/step84f-layout2-validation: captures contains labeled fixtures; actual-light-* contains real empty-App captures. No algorithm/subscription/save-controller changes, unit rerun or H10 validation; enlarged-font/landscape gaps remain open. Both documentation pairs are synchronized; no 8.5, commit or push.

##### 5.34.3.5 8.4e：统计居中、汇总放大与 Stop 重置 / Centered statistics, larger summary and Stop reset

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

##### 5.34.3.6 8.4e：顶部/底部间距与流标题 / Outer spacing and stream title

- 按用户要求，Session/History 导航下方至首卡的顶部留白由 4 dp 增至 8 dp；删除 Data Streams 标题，保留 HR/ACC/ECG 指示灯、标签及真实状态。内容区底部 4 dp 留白及按钮区顶部 4 dp 留白移除，收紧区间卡与按钮的距离；按钮区底部 4 dp、系统安全区域及 64 dp 按钮保留。卡片间 5 dp、卡片内部边距、字号和图高均未修改。
  Increase the Session-page gap below the Session/History navigation from 4 to 8 dp. Remove the Data Streams title while retaining HR/ACC/ECG lights, labels and real states. Remove the content's 4 dp bottom padding and controls' 4 dp top padding to tighten the zone-to-controls gap; retain 4 dp below controls, system insets and 64 dp buttons. Card gaps (5 dp), internal padding, typography and plot heights are unchanged.
- 代码边界：本轮只改 SensorActivity.kt、SessionHeader.kt、SessionChrome.kt 三个展示文件及三个既有仪器测试文件的旧标题定位；source-audit.txt 保存本轮改动清单。采集、Stop 重置、统计、保存及 History 功能不变。
  Scope: Only three presentation files (SensorActivity.kt, SessionHeader.kt, SessionChrome.kt) and obsolete title selectors in three existing instrumentation files change; source-audit.txt records the per-turn scope. Acquisition, Stop reset, statistics, saving and History behavior remain unchanged.
- 验证：最终 debug/测试 APK 与 lint 通过（0 errors、18 warnings）。较大的初始顶部留白造成 Z5 裁切，收敛间距后最终 3 项整页检查全通过；其余 13 项相关顶部/导航/顺序检查此前通过，合计 16 个不同检查有通过结果，不表述为最终一次 16 项全量重跑。默认字号竖屏三种曲线、深浅主题、暂停/错误/空数据及无滚动完整页面已检查；本轮不重跑未改的数据层单元测试。证据为 build/step84f-edge-spacing-validation，最终生产组件截图为 final3-captures（明确标注 UI TEST DATA · no H10）。既有大字号/横屏缺口、Samsung/H10 验收仍 pending；两对文档同步，无 8.5、commit/push。
  Verification: Final debug/test-APK builds and lint pass (zero errors, 18 warnings). The initially larger top gap clipped Z5; after spacing corrections, all three final whole-page checks pass. Thirteen other header/navigation/order checks passed earlier, giving 16 distinct passing checks across runs, not one final 16-test rerun. Default-font portrait checks cover all three charts, both themes, paused/error/empty states and full non-scrolling content. Unchanged data-layer unit tests were not rerun. Evidence is under build/step84f-edge-spacing-validation; final3-captures contains labeled production-component fixtures. Existing enlarged-font/landscape gaps and Samsung/H10 acceptance remain pending. Both documentation pairs are synchronized; no 8.5, commit or push.

##### 5.34.3.7 8.4e：三类图表高度一致 / Consistent chart heights

- 修复 HR/其他图表切换时高度变化：三种 LivePlot 统一沿用 Motion/ECG 的高度公式（原基础高度 +12 dp，保留原字体比例），HR 不再少 12 dp。HR 的等待/暂停/停止/失败文字移至单位行右侧，不额外占行；Motion/ECG 仍不显示状态文字。下方 Summary、HR Zone 的尺寸、间距及全部数据处理规则不变。
  Fix chart-switch height changes by using the existing Motion/ECG plot-height formula for all three views (base height plus 12 dp, with existing font scaling). Move HR waiting/paused/stopped/failed notices to the right of the unit row instead of adding a row. Motion/ECG still omit these notices. Summary/HR Zone dimensions, spacing and data-processing rules are unchanged.
- 验证：debug/测试 APK 构建及 lint 通过（0 errors、18 warnings），18 项相关 Compose 检查全部通过。新增到既有用例的尺寸断言覆盖三视图卡片边界/绘图区高度及下方汇总/区间边界不随切换变化；状态用例检查空数据、等待、停止、失败、暂停的高度稳定。实际无 H10 App 三图表与 History 返回通过，Motion 下方区域坐标与上一轮截图 XML 一致。证据位于 build/step84f-chart-size-validation，captures 为带测试数据标记的生产组件截图，actual-light-* 为实际空数据 App。仅改两个图表展示文件及两个已有仪器测试文件；本轮未重跑数据层单元测试，既有大字号/横屏和 H10 验收边界不因此关闭。两对文档同步，无 commit/push、无 8.5。
  Verification: Debug/test-APK builds and lint pass (zero errors, 18 warnings), as do all 18 relevant Compose checks. Existing cases now assert equal card bounds/plot heights across selections and unchanged lower summary/zone bounds, plus stable heights for empty, waiting, stopped, failed and paused states. Actual no-H10 App checks pass for all selections and History return; lower-region coordinates in Motion match the previous run's XML. Evidence is under build/step84f-chart-size-validation: captures contains labeled fixtures and actual-light-* contains real empty-App captures. Only two chart presentation files and two existing instrumentation files changed. Data-layer unit tests were not rerun; enlarged-font/landscape and H10 acceptance limitations remain. Both documentation pairs are synchronized; no commit/push or 8.5.

##### 5.34.3.8 8.4e：固定手机竖屏（2026-10-03） / Fixed phone portrait

- 按用户要求固定手机页面为竖屏：AndroidManifest.xml 为 MainActivity 和 SensorActivity 均设置 screenOrientation="portrait"，覆盖 Welcome、Session、History。当前源码没有独立横屏布局分支或主动旋转代码，因此不添加方向监听或配置拦截；保留供字号/主题等配置变化使用的 ViewModel 和响应式宽度布局，更新相关注释与两个检查名称。
  Both MainActivity and SensorActivity request portrait in AndroidManifest.xml, covering Welcome, Session and History. There is no separate landscape branch or programmatic rotation code to remove; no orientation listener/configuration interception is added. Retain ViewModel ownership and responsive width handling for other configuration changes, and update the comment/two check names.
- 当前规则覆盖早期横屏适配/旋转布局要求；横屏裁切记录保留为历史证据，不再作为手机页面待完成的横屏布局任务。改为验证自动旋转开启、设备左右转动时仍保持竖屏。2.0 字号竖屏裁切与 Samsung/H10 真机采集/生命周期仍待验收；不改指标、卡片、图表尺寸、Stop 重置或数据库。
  This supersedes earlier landscape-layout acceptance. Historical clipping captures remain evidence rather than an outstanding phone-landscape layout task. Verify portrait while auto-rotation is enabled and the device turns left/right. Portrait font 2.0 clipping and Samsung/H10 acquisition/lifecycle remain pending. Metrics, card/chart sizes, Stop reset and database behavior are unchanged.
- 验证：debug/测试 APK 构建及 lint 通过（0 errors、22 warnings；比之前多 4 项方向限制提示：两个 LockedOrientationActivity、两个 DiscouragedApi，未屏蔽）。独立 API 37 手机模拟器（1080×2340、450 dpi）关闭仅模拟器的强制用户方向覆盖后，实际 APK 在 Welcome/Session/History 各经过直立/左转/右转 9 次检查，均保持 port、384×832 dp、ROTATION_0，且各页面的 Activity 未因转动重建；History 返回通过。证据见 build/step84e-portrait-validation/portrait.txt、*-config.txt 和同名截图；无 H10、未操作 Samsung，未重跑数据层单元或 Compose 全套。
  Debug/test-APK builds and lint pass (zero errors, 22 warnings: four additional orientation advisories, two LockedOrientationActivity and two DiscouragedApi, not suppressed). After disabling the dedicated emulator's forced-user-orientation override, the actual APK passes nine upright/left/right sensor checks across Welcome/Session/History on an API 37 phone emulator (1080×2340, 450 dpi). Each stays port, 384×832 dp, ROTATION_0 without rotation-induced Activity recreation; History return passes. Evidence is in build/step84e-portrait-validation/portrait.txt, configuration dumps and matching captures. No H10/Samsung run or data-layer/full Compose rerun.
- 平台边界：本次竖屏验收针对目标手机尺寸。targetSdk 37 下，Android 17 在最小宽度大于 600 dp 的大屏上可能忽略方向限制，不能宣称平板/桌面也强制竖屏；见 [Android 官方方向限制说明](https://developer.android.com/about/versions/17/changes/ff-restrictions-ignored)。两对文档同步，无 8.5、commit/push。
  Platform scope: Portrait acceptance targets phone dimensions. With targetSdk 37, Android 17 can ignore orientation restrictions on displays wider than 600 dp in their smallest dimension; tablet/desktop portrait is not guaranteed. See the linked Android documentation. Both document pairs are synchronized; no 8.5, commit or push.

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

### 5.43 步骤 8.6：Devices 弹窗第一版（2026-10-04） / Step 8.6: Devices dialog first version

- 用户已确认：按 ui/Devices-dialog.png 重做设备连接弹窗；删除详细信息，其余采用本轮建议，实施第一版并记录中英文提示词及结果。本节覆盖 5.29、5.34 中旧弹窗顺序、详细信息、整卡连接及双扫描按钮的相应要求；不修改 BLE、存储、算法或 Session 整页布局。
  Confirmed request: Restyle the device connection dialog using ui/Devices-dialog.png, remove detailed information, adopt the remaining proposed rules, implement the first version and record bilingual prompts/results. This section supersedes the corresponding older dialog order, details, whole-card connection and two-scan-button requirements in 5.29/5.34. Do not alter BLE, storage, algorithms or the full Session layout.
- 外观与内容：圆角描边弹窗、蓝牙圆形图标、Devices 标题与右上角关闭，状态胶囊；当前/目标设备按需出现，其后 Saved devices、Nearby devices。底部 Scan/Stop scan 和 Close 固定，中间区域限高滚动。适度字重、蓝色按钮、深浅主题；长名称换行，大字号/窄宽度时连接按钮移到下一行，不缩小系统字号。标题图标用与参考图对应的蓝牙矢量图；Session 入口保持现有设置齿轮。
  Presentation: Rounded outlined dialog, circular Bluetooth icon, Devices heading/top-right close, and a status pill. Show the current/target device only when applicable, then Saved devices and Nearby devices. Fix Scan/Stop scan and Close at the bottom and scroll the bounded middle region. Use moderate weights, blue buttons and light/dark themes. Wrap long names; move device actions below their names at narrow widths/large fonts without reducing system font scale. Use a Bluetooth vector matching the reference; retain Session's settings gear.
- 删除详细信息：不提供折叠详情、最近连接时间、RSSI、采样参数/正常就绪列表、常驻 Session 状态/统计说明或已保存终态。设备名未包含 ID 时补一行 ID。当前设备保留有效电量、Disconnect 和原 Retry disconnect。保留权限/蓝牙/连接问题、失败数据流、阻止启动/配置问题和保存中/失败的必要提示及原恢复入口，避免无解释地阻止操作；不新增单流 Retry 或 Session Retry save。
  Remove details: No disclosure, last-connected time, RSSI, sampling parameters/healthy-readiness list, persistent Session status/statistical notes or terminal saved notice. Add an ID line only when the name does not already include it. Retain valid current-device battery, Disconnect and existing Retry disconnect. Preserve necessary permission/Bluetooth/connection, failed-stream, blocked-start/configuration and saving/failed-save notices with existing recovery actions. Add no stream Retry or Session Retry save.
- 设备规则：独立 Connect 按钮连接，设备卡片本身不触发连接；连接成功后弹窗保持打开。按 deviceId 去重：当前设备不重复出现在列表，Nearby 只显示本轮发现且不在 Saved 的设备。Saved 仍按最近连接倒序且可直接连接，不据此声明在线；读入未完成及错误保留提示，不新增存储规则。
  Device rules: Connect through explicit buttons; cards themselves do not connect. Keep the dialog open after success. Deduplicate by deviceId: exclude the current device from lists and saved devices from Nearby. Preserve recency order and direct connection for saved devices without claiming they are online. Preserve loading/error messages; no storage changes.
- 交互状态：Scan 与 Stop scan 合并，保留 30 秒扫描、每轮清空、停止后保留结果、连接前停止扫描、10 秒连接超时和单设备限制。连接/断开中禁用重复操作，真实回调确认状态；不自动扫描/重连/切设备。右上角关闭、Close、返回和点击外部统一关闭，只停止扫描，不主动断开或结束 Session；真实后台/离开行为不变。
  Interaction: Combine Scan and Stop scan while preserving the 30-second scan, new-scan reset, retained results, stop-before-connect, 10-second connection timeout and single-device limit. Disable duplicate actions while connecting/disconnecting and derive status from real callbacks. No automatic scans/reconnects/device switching. Top close, Close, Back and outside dismissal share the close path, stopping scans without disconnecting or ending Session. Actual background/leaving behavior is unchanged.
- 验证范围：参考布局深浅主题，空/已保存/附近/重复设备，扫描与关闭，连接中/已连接/断开失败，权限/蓝牙/SDK/配置问题，长名称、多设备、系统字号 2.0、固定底部按钮与保存恢复。仅测试环境使用明确标注的模拟数据；运行相关 Compose 检查、debug/测试 APK 构建及 lint。真机结果独立记录。最终验证结果见 5.43.1；无 commit/push。
  Validation scope: Reference light/dark layout, empty/saved/nearby/duplicate devices, scanning/dismissal, connecting/connected/disconnect failures, permission/Bluetooth/SDK/configuration issues, long names/many devices, system font 2.0, fixed footer and save recovery. Use labeled synthetic data only in tests; run relevant Compose checks, debug/test APK builds and lint. Record hardware results separately. Final validation is recorded in 5.43.1. No commit/push.

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

## 6. 功能开发步骤

按下表顺序推进；一次只处理一个编号。依赖未满足时先说明缺口。
早期使用最小按钮、文本或日志验收；完整布局在第 8 阶段整合。

- 临时状态展示清理（用户已确认）：当前及后续为开发验证添加的详细状态文本、参数列表、测试按钮和占位提示，在第 8 阶段由正式界面替代后删除，不作为最终页面的常驻内容。
- 文件清理范围：若有仅用于上述临时展示的独立文件，确认无其他用途和引用后删除；若展示代码位于 `SensorActivity.kt` 等业务文件中，只删除对应代码，不删除整个业务文件。当前未指定需要整文件删除的名称，实施清理时按实际引用确认。
- 必须保留：底层就绪判断、连接与订阅状态、权限检查、防重复操作、旧回调防护、错误处理和资源释放；最终界面仍保留连接状态、采集/停止状态、必要错误及恢复入口，按第 4 节布局整合。
- 清理验收：删除临时展示后重新构建并验证正式界面的连接、采集状态和失败恢复操作；此前开发截图、日志及 AI 使用记录作为证据保留。

| 步骤 | 功能与最小实现 | 完成检查 |
|---|---|---|
| 0.1 | 确定技术栈、项目结构与依赖版本 | 关键配置已填写 |
| 0.2 | 创建手机项目、欢迎页与页面入口 | App 可启动并进入 Session |
| 1.1 | 配置 SDK、蓝牙权限与可用状态提示 | 区分允许、拒绝、蓝牙关闭 |
| 1.2 | 开始/停止扫描，设备列表去重 | 真实 H10 可显示，扫描可停止 |
| 2.1 | 选择设备、连接、显示连接状态 | 连接状态由真实回调更新 |
| 2.2 | 展示已保存设备，断开与再次连接 | 设备来源清楚，断开后状态一致 |
| 2.3 | 读取并显示设备电量，见 5.14（代码及测试完成，真机待验证） | 真实回调更新百分比；未知或断开显示占位符；旧回调无效 |
| 3.1 | 检查数据功能就绪与可用采样设置，见 5.12 | 区分连接、就绪、配置与失败；断开清空并防止旧查询覆盖 |
| 3.2 | 管理数据订阅、错误与资源释放，见 5.13 | 每类最多一个订阅；取消、失败与断开后正确清理；旧事件无效 |
| 4.1 | 接收心率，保留数值、bpm 单位和手机接收时间，见 5.15 | 真机产生连续心率数据；不将接收时间标为采样时间 |
| 4.2 | 接收三轴加速度，使用 100 Hz、±4 g，按样本时间判断缺口并保留有限缓存，见 5.16 | 真机产生 ACC 数据；验证量程、30 ms 缺口及 10 秒/1,000 样本上限 |
| 4.3 | 接收 ECG，核对单位与样本时间轴，暂存最近 10 秒且最多 1,300 样本，见 5.17 | 真机产生 ECG 数据；验证时间轴及缓存上限 |
| 4.4 | 实现 Start/Stop、首个数据开始计时及中断结束；默认采集 HR、ACC、ECG，见 5.18 | 防重复启动、停止后不再累计、中断后新建会话、旋转保持；保存留在第 6 阶段 |
| 5.1 | 按 5.19 处理无效心率并计算整场有效样本最小/最大/平均值（代码、测试及构建完成，真机待验证） | 批次逐样本统计、Retry 保留累计、新 Start 清零、无效 HR 不启动计时 |
| 5.2a | ACC 单位转换、合加速度与完整 5 点平滑，见 5.1 | 不足 5 点不输出，100 个平滑值预热，处理顺序正确 |
| 5.2b | 前 100 点动态阈值与回落确认，见 5.2；A_min 初始 0.5 m/s²，按 5.6 实测调整 | 当前点不参与阈值，同一 H 判断上穿，周期 H/L 固定，一个周期只产生一个候选步 |
| 5.2c | 间隔筛选与连续 4 步确认 | 缓存、补计及超时清空正确 |
| 5.2d | 5 秒窗口步频、250 ms 显示刷新与两秒无步归零，见 5.4 | 原始峰值时间及不足 5 秒处理正确，手机单调时间仅辅助显示，不改变检测结果 |
| 5.3（5.3add） | 已实现 5.20 步长、五秒/短窗口速度、距离和统计，英文文本展示；119 项测试、构建及 lint 通过 | 受控算法/生命周期检查通过；参考距离、静止/走跑和设备生命周期仍待真机比较 |
| 5.4（5.4add） | 已实现 5.21 固定五档心率强度、累计时长五柱图与未归类时间；本轮 132 项测试、构建及 lint 通过 | 边界/批末有效性/保持计时/重试/结束冻结受控测试通过；真实标签、柱形、接触变化、旋转和断线待真机验证 |
| 5.5（5.5add） | 已实现 5.22 有界实时曲线、单调时间轴、断段与简单切换；本轮 148 项测试、构建及 lint 通过 | 点数/窗口、固定锚点、断段及 Retry/Stop 受控测试通过；真实绘图、ECG 刷新性能、切图和旋转待真机验证 |
| 6.1a | 会话身份与摘要：UUID、时间、设备、资格及数据结构，见 5.23（代码及自动检查完成，真机待验证） | 重复 Start 不换 ID，新场换 ID；未知/零区分，Stop 冻结摘要 |
| 6.1b | 平均与最小步频，沿用已有最大步频，见 5.23.2、5.23.8（代码及自动检查完成，真机待验证） | 平均包含静止，极值仅用真实合格五秒窗口，无观测为 -- |
| 6.1c | 整场心率历史及自身生命周期，见 5.23.3、5.23.9（代码及自动检查完成，真机待验证） | 每秒真实末点，断段保留，Retry 不删旧段，四小时/14,401 点有界 |
| 6.1d | 整场步频/速度历史及完整会话快照，见 5.23.3、5.23.10（代码及自动检查完成，真机待验证） | 每秒已有末结果，缺失不填零，Stop 冻结两类历史与摘要，新场不修改旧快照 |
| 6.2 | SQLite 事务保存、四小时上限、失败重试及最简单 History 入口（代码已实施，实际验收见 5.23.11） | 重启保留、去重、失败回滚、简单查询/曲线/删除可验收，无正式布局 |
| 7.1 | 完善历史列表，复用 6.2；倒序游标分页每次 20 条、手动 Load more、原页失败重试及刷新，见 5.27.1（代码及自动检查完成，真机 pending） | 204 项单元测试、构建/lint 及 16 项实际 SQLite/Compose 检查通过；真实采集切换/旋转待验证 |
| 7.2 | 完善按 ID 详情及日期确认删除，复用 6.2，见 5.27.2（代码及自动检查完成，真机 pending） | 204 项单元测试、构建/lint 及 25 项实际 SQLite/Compose 检查通过；真实手机行为待验证 |
| 8.0 | 共用主题、基础字号、已使用尺寸及绿/蓝/黄/橙/红配色已接入，见 5.28.1 | 204 项单元测试、构建/lint（0 errors、17 warnings）、25 项 SQLite/Compose 回归及三页面深浅/放大字号截图检查；完整视觉及 Samsung/H10 pending |
| 8.1 | 已整合区域 1、2：连接/电量入口、Devices 弹窗与心率强度，见 5.29.1 | 204 项单元测试、35 项 Compose/SQLite 检查、debug/测试 APK 构建与 lint（0 errors、15 warnings）通过；实际深浅/字体/横屏检查完成所述范围，H10 pending |
| 8.2 | 四卡指标、160 dp 五柱/明细及运动汇总已实施；规则/结果见 5.30—5.30.1 | 204 单元测试、13 新 UI + 35 回归、debug/测试构建及 lint 通过；深浅/大字体/横屏视觉已检查，Samsung/H10 pending |
| 8.3 | 整合区域 7：三类曲线切换 | 时间轴清楚，切换后数据连续 |
| 8.4 | 参考图 Session 最终整合，拆为 a—f；既有功能/视觉记录见 5.33—5.34，最新单屏要求见 5.34 | a—e 有实施记录；f 默认字号竖屏单屏已实现，大字号/横屏适配未完成，整体 UI 未全面完成；Samsung/H10 pending |
| 8.4a | 页面框架、连接/Data streams、Start/Stop、保存恢复与顶部导航，见 5.32.1.1 | 212 单元测试、71 项不同 Compose/SQLite 检查、debug/测试构建及 lint（0 errors、15 warnings）通过；Samsung/H10 pending |
| 8.4b | 指标分栏、HR 内强度、三项汇总与 Session 横向区间已实施，见 5.32.2.1 | 212 单元测试、75 项不同 Compose/SQLite 检查、debug/测试构建及 lint（0 errors、15 warnings）通过；实际视觉范围见实施记录，Samsung/H10 pending |
| 8.4c | 三类实时曲线、网格/刻度/填充/整场均值参考线已实施，见 5.32.3.1 | 218 单元测试、78 项不同 Compose/SQLite 检查（另 1 项基础检查）、debug/测试构建及 lint（0 errors、15 warnings）通过；真实窗口/断段/冻结/恢复不变，Samsung/H10 pending |
| 8.4d | 按 5.33 删除 Session 速度/Min、加入 Pause/Continue 与五分钟窗口，实施功能整合与部分清理 | 已实施部分及既有验证见 5.33.1；未完成的视觉布局/样式/对图验收移至 8.4e，不能视为整个 8.4 完成 |
| 8.4e | 合并参考图布局、单屏无滚动、卡片/标题/状态清理、统计/空间调整、Stop 重置及三图等高，统一方案见 5.34 | 默认字号竖屏已实施并验证；历史及后续记录合并见 5.34.3；手机固定竖屏；竖屏大字号适配与 H10 真机 pending，不标记整体 UI 全面完成 |
| 8.5 | History 列表与详情，8.5a—8.5d | 软件与本轮模拟器验收完成（5.40）；231 单元、分轮 53 项不同仪器检查通过，深浅/字号 1.0/2.0 已核对；Samsung/H10 pending |
| 8.5a | 卡片列表、10 条自动分页、右侧滚动条、适度字重 | 分轮 41 项不同 SQLite/Compose 检查通过，另字号 2.0 视觉重复检查通过；构建/lint 0 errors、22 warnings；Samsung/H10 pending |
| 8.6 | Devices 参考图弹窗、移除详情与设备操作整理 | 第一版已实施；231 单元、33 UI 及 5 次字号 2.0 重复检查通过，构建/lint 0 errors、16 warnings；见 5.43.1，Samsung/H10 pending |
| 9.1 | 验证拒绝权限、断线、页面重建和后台行为 | 行为符合设计，无重复采集 |
| 9.2 | 真机完整演示并记录性能与算法局限 | 完成采集→处理→保存→重启查询 |
| 9.3 | 整理证据、提交文件与展示材料 | 所有作业交付项齐全 |

## 7. 每步填写卡

每次只补充当前步骤；避免重复整份规划。

```text
步骤编号：【待填写】
目标：【一句话】
前置条件：【必要依赖】
涉及文件：【路径与职责】
输入 → 处理 → 输出：【一句话】
参数与依据：【必要时填写】
本次代码：【按功能分块】
验证方法：【操作与预期结果】
实际结果：【未验证 / 实际证据】
```

## 8. 提示词与回复格式

### 用户提示词模板

```text
执行步骤【编号】：【功能】。
本次要求：【补充规则】。
使用最小代码，按功能分步骤提供代码块。
注明文件路径、插入/替换位置和验证方法。
输出方式：【仅提供代码 / 直接修改文件】。
```

### 助手回复顺序

1. 本步目标：一句话。
2. 文件与位置：列出本次改动点。
3. 功能代码块：每块只处理一个清楚的职责。
4. 验证：操作、预期结果、已执行结果。
5. 状态：本步完成程度及剩余问题。

## 9. 进度与证据

- 2026-10-03 8.4c 实施：源文件、218 单元/78 不同 Compose/SQLite 检查、构建/lint、受控与实际 App 视觉及真机边界见 5.32.3.1。8.4d/8.5 pending，两对文档同步，无 commit/push。
  Step 8.4c source, 218 unit/78 distinct Compose/SQLite checks, builds/lint, controlled/actual App visuals and hardware limits are in 5.32.3.1. Steps 8.4d/8.5 remain pending; both documentation pairs synchronized, no commit/push.

- 2026-10-03 8.4b 实施：指标分栏/HR 内强度/汇总换行/横向区间/页面顺序、自动检查、实际视觉和真机边界见 5.32.2.1。8.4c/8.4d/8.5 pending，两对文档同步，无 commit/push。
  Step 8.4b implementation, automated checks, actual visuals and hardware limits are in 5.32.2.1. Steps 8.4c/8.4d/8.5 remain pending; both documentation pairs synchronized, no commit/push.

- 2026-10-03 8.4a 实施：代码、自动检查、视觉范围、文件及真机未验证项见 5.32.1.1。8.4b—8.4d/8.5 pending；两对文档同步，无 commit/push。
  Step 8.4a implementation, checks, visual scope, files and hardware limits are in 5.32.1.1. Steps 8.4b–8.4d/8.5 remain pending; both documentation pairs synchronized, no commit/push.

- 2026-10-01 8.2 实施：四卡、160 dp 共用区间图与汇总已接入；204 单元测试、48 项不同模拟器检查、debug/测试构建及 lint（0 errors、15 warnings）通过，实际字号/深浅/横屏视觉及真机边界见 5.30.1。Samsung/H10 pending；8.3—8.5 未实施，两对文档同步，无 commit/push。
  Step 8.2 implemented with 204 unit tests, 48 distinct emulator checks, debug/test builds and lint passing (0 errors, 15 warnings). Actual theme/font/landscape checks and hardware limits are recorded in 5.30.1. Samsung/H10 pending; no steps 8.3–8.5, commit or push.

- 2026-10-01 8.2 规则确认：已检查现有 HR/运动/区间/摘要及 History 共享引用，采用 Heart rate → Motion → Heart rate zones → Activity summary 四卡布局、56/28 sp 主数值、160 dp 五柱与可换行明细；完整规则见 5.30。仅同步两份 AGENTS.md 和两份 prompt.md，已有 8.1 源码未改；8.2 实施、测试/构建/lint、实际视觉和 Samsung/H10 pending，未 commit/push。
  Step 8.2 rules: Inspected current metrics/zones/summary and History reuse; defined four cards, 56/28 sp main values, a 160 dp zone plot and wrapping details in 5.30. Only both documentation pairs changed; existing step 8.1 source is unchanged. Step 8.2 implementation and fresh automated/visual/hardware checks remain pending; no commit/push.

- 2026-10-01 8.1 实施：SessionHeader、DevicesDialog 及蓝牙图标已接入，迁移被替代的设备/就绪 UI 并拆出当前强度。204 项单元测试、debug/测试 APK 构建、lint（0 errors、15 warnings）及 35 项 Compose/SQLite 检查通过；实际系统 2.0 长设备名受控用例另通过，深浅/字号/横屏/滚动和关闭视觉检查范围见 5.29.1。Samsung/H10 pending；8.2—8.5 未实施，两对文档同步，无 commit/push。
  Step 8.1: Header/Devices and Bluetooth icon implemented with replaced UI removed and current intensity separated. All 204 unit tests, debug/test builds, lint (0 errors, 15 warnings) and 35 Compose/SQLite tests passed. Long-name controlled test also passed at real system font scale 2.0; actual visual evidence and limits are in 5.29.1. Samsung/H10 pending; no steps 8.2–8.5, commit or push.

- 2026-10-01 8.1 规则确认：按用户采用的推荐方案定义顶部连接/电量入口、Devices 弹窗、五档强度标签、对应临时展示清理与验收边界，见 5.29；仅同步两对文档及中英文 prompt，App 代码、构建、UI 与 H10 验证待实施。8.0 原有证据不作为 8.1 通过结果，无 commit/push。

- 2026-10-01 8.0 实施：固定深浅主题、24/18/16/14 sp 基础字号、现有页面间距及共用绿/蓝/黄/橙/红区间配色；204 项单元测试、debug/测试 APK 构建、lint（0 errors、17 warnings）及 25 项实际 SQLite/Compose 回归通过。已检查三页面深浅模式及 1.0/2.0 字号的 12 张模拟器截图；截图覆盖范围、颜色源码检查与 H10 pending 分开记录，见 5.28.1。两对文档同步；8.1—8.5 未实施，无 commit/push。

- 2026-10-01 7.2 实施：统一 History 列表/详情/确认日期，保留原 ID 查询重试，分离删除错误并支持回滚后再次 Delete，阻止删除期间重复操作；修改 HistoryPanel.kt/SessionSummaryPanel.kt，新增 9 项 HistoryDetailTest。本轮 204 项单元测试、debug/测试 APK 构建、lint（0 errors、18 warnings）通过；专用模拟器 25 项实际 SQLite/Compose 检查通过，见 5.27.2。Samsung/H10 真机 pending，第 8 阶段未实施；两对文档同步，无 commit/push。

- 2026-09-30 7.1 实施：复用 SQLite，完善列表分页原页重试、返回/保存刷新、时区显示及查询取消；只修改 HistoryPanel.kt，新增 8 项 HistoryListTest。本轮 204 项单元测试、debug/测试 APK 构建、lint（0 errors、18 warnings、1 Hint）通过；专用模拟器 16 项实际 SQLite/Compose 检查通过，见 5.27.1。Samsung/H10 行为 pending；7.2/第 8 阶段未实施，两对文档同步，无 commit/push。

- 2026-09-30 第 7 阶段规则确认：用户采用 5.27 的全部推荐方案；同步两份 AGENTS.md，并在两份 prompt.md 补充 7.1/7.2 中英文提示词。仅修改文档，未实施 7.1/7.2、未运行测试/构建、未操作设备、未 commit/push；20 条为分页数量，保存无数量上限且不自动清理。

- 2026-09-30 6.2 实施：SQLite 三表事务/应用级保存状态、四小时截止及最简单 History 查询/详情/确认删除已写入；204 项单元测试、debug 构建、lint（0 errors、18 warnings）和仪器测试 APK 构建通过。独立模拟器 8 项真实 SQLite/Compose 测试通过，详见 5.23.11；H10 真机待验证；两对文档同步，无 commit/push，不推进第 7/8 阶段。
  Step 6.2: Implemented transactional SQLite, application-owned saving, four-hour ending and minimal History queries/detail/confirmed deletion. All 204 unit tests, debug build, lint (0 errors, 18 warnings) and instrumentation APK build passed. Eight actual SQLite/Compose emulator tests passed; see 5.23.11. H10 checks pending. Documentation pairs synchronized; no commit/push or stage 7/8 work.
- 2026-09-30 6.1d 实施：新增 MotionHistory.kt，复用 250 ms 结果每秒保留末组、null/断段、前四小时及 14,401 桶边界；既有结算后一次组合摘要、HR/运动冻结快照，增加英文元数据显示。新增 13 项测试，本轮 194 项全部通过，debug 构建及 lint 通过（0 errors、18 warnings）。两对文档同步；真机待验证，未安装/commit/push，6.2 未实施。见 5.23.10。
  Step 6.1d: Added bounded motion history at the existing refresh entry and one combined summary/HR/motion snapshot after settlement, with English metadata. Thirteen new tests; all 194 tests, debug build and lint passed (0 errors, 18 warnings). Both document pairs synchronized. Device checks pending; no installation/commit/push; 6.2 remains unimplemented. See 5.23.10.
- 2026-09-30 6.1c 实施：新增 HrHistory.kt，接入真实 HR 末点、每秒分桶、null/断段、四小时/14,401 点上限和自身生命周期；SensorActivity 显示英文计数/首末时间。新增 13 项测试，本轮 181 项全部通过，debug 构建和 lint 通过（0 errors、18 warnings）。两份 AGENTS.md、两份 prompt.md 同步；未安装/真机验证/commit/push，6.1d、6.2 未实施。详见 5.23.9。
  Step 6.1c: Added bounded HR history and event/lifecycle wiring with English metadata display. Thirteen new tests; all 181 tests, debug build and lint passed (0 errors, 18 warnings). Both documentation pairs synchronized. No installation/device validation/commit/push; 6.1d and 6.2 remain pending. See 5.23.9.
- 2026-09-30 6.1b 实施：StepDetector.kt 增加完整 Running 平均步频和同一合格窗口最小步频，SessionRecord.kt 接入摘要，SessionSummaryPanel.kt 显示英文 Mean/Min/Max 与不可用/缺失原因。新增 7 项测试，本轮 168 项测试全部通过，debug 构建与 lint 通过（0 errors、18 warnings）。两份 AGENTS.md、两份 prompt.md 已同步；未安装 APK、未真机验证、未提交/推送。后续步骤未实施；详见 5.23.8。
  Step 6.1b: Mean/minimum cadence added to the existing motion owner, session summary and English panel. Seven new tests; all 168 tests, debug build and lint passed (0 errors, 18 warnings). Both documentation pairs synchronized. No APK installation, device validation, commit or push; later steps remain pending. See 5.23.8.
- 2026-09-30 6.1a 实施：当前 5.3add—5.5add 前置代码已重新检查，新增 SessionRecord.kt、SessionSummaryPanel.kt 和 SessionRecordTest.kt；接入 UUID、日期/单调时间、设备、已有摘要、保存资格、各流完整性及结束冻结。仅定义两类历史点结构；平均/最小步频、整场历史和数据库仍未实施。详情及真机步骤见 5.23.7。
  Step 6.1a: Prerequisites rechecked; identity, time, device snapshot, existing summaries, eligibility, completeness and ending freeze implemented. History point structures only; mean/minimum cadence, history collection and persistence remain pending. See 5.23.7.
- 6.1a 本轮检查：161 项单元测试全部通过，debug 构建及 lint 为 BUILD SUCCESSFUL，lint 0 errors、18 warnings；报告 app/build/reports/tests/testDebugUnitTest/index.html、app/build/reports/lint-results-debug.html，APK app/build/outputs/apk/debug/app-debug.apk。未安装或执行真机验收；已同步两份 AGENTS.md 与两份 prompt.md，未 commit/push。
  Verification: All 161 unit tests passed; debug build/lint succeeded, with 0 lint errors and 18 warnings. Reports and APK are at the paths above. Device verification is pending; both documentation pairs synchronized. No commit/push.
- 2026-09-30 5.5add 实施：前置 5.3add/5.4add、真实 HR/ACC/ECG 订阅、已有 raw 缓存及会话时钟均已检查。新增 LiveCharts.kt、LiveChartPanel.kt；修改 PolarBleManager.kt、SessionState.kt、StepDetector.kt、SensorActivity.kt；新增 LiveChartsTest.kt（13 项）、LiveChartLifecycleTest.kt（3 项）。原采集设置、HR/区间/步伐/距离统计、资源释放路径保留。两份 AGENTS.md 与两份 prompt.md 同步本轮中英文记录；未创建 commit 或 push，未实施 6.1a。
- 2026-09-30 5.5add 自动检查：使用现有 Android Studio JBR 和 Gradle 缓存运行 `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，最终 BUILD SUCCESSFUL。读取本轮 XML：148 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。新增测试覆盖每秒末点/真实时间/不平均、无新 HR 不造点、桶内断段、原统计不变、60 秒/数量上限与过期删除、250 ms 去重/预热空值/静止零、30 ms ACC 边界与段超时、ECG 固定首锚点/纳秒差/负 x/有符号值、五秒全部 650 点、实际采样率三周期跨批断段、单流失败/Retry 清理范围/重新锚定、Stop/自然结束/中断冻结且无尾部零点、重复操作/旧会话数据与刷新、新 Start 和选择/重复快照不重启采集。首次编译缺少 SettingType 限定名，修正为 PolarSensorSetting.SettingType 后全量验证通过。
- 2026-09-30 5.5add 证据：测试报告 `PolarH10ActivityViewer/app/build/reports/tests/testDebugUnitTest/index.html`；lint `PolarH10ActivityViewer/app/build/reports/lint-results-debug.html`；APK `PolarH10ActivityViewer/app/build/outputs/apk/debug/app-debug.apk`。650 点测试验证可见数据集合，Canvas 全点遍历和断段按源码检查；没有执行屏幕渲染或性能仪器测试。选择随 ViewModel 持有者保留的路径已检查，重绑/重复快照测试不等于手机实际旋转验证。
- 2026-09-30 5.5add 真机待验证：本轮未安装或操作手机。① 三路采集后依次查看 HR/Motion/ECG，核对单位、mm:ss 和滚动窗口，Motion 单独切 Cadence/Speed；② 静止时预热留空、完成预热后运动零值可见，HR 没有新批次时不延长水平线；③ ECG 核对有正负电压、波形连续性和 100 ms 显示刷新表现，记录卡顿/延迟，不以构建通过宣称性能达标；④ 可复现缺口/单流失败后检查断段、冻结视窗和其他流继续，Retry 仅清对应图且横轴不归零、累计统计保留；⑤ Stop/重复 Stop、断线、后台/锁屏、旋转、切图和新 Start，检查不补尾零、不继续滚空、选择保留、不重复订阅以及新会话清空。不同流含传输延迟的近似对齐与 HR 每秒末点省略秒内变化为已知限制；没有完成整场历史存储。

- 2026-09-30 5.4add 实施：先核对当前 5.3add 运动统计、5.1 有效 HR、SessionController 和真实三流订阅路径，前置代码齐全。新增 HeartRateZones.kt、HeartRateZonePanel.kt；修改 PolarBleManager.kt、SessionState.kt、SensorActivity.kt；新增 HeartRateZonesTest.kt（13 项）。当前档位只使用已验证批末样本，区间与未归类毫秒由唯一持有者计算，Compose 仅读取。两份 AGENTS.md、两份 prompt.md 同步本轮结果及中英文提示词；本轮未创建 Git commit 或推送，未实施 5.5add/6.1a。
- 2026-09-30 5.4add 自动检查：使用现有 JBR 与 Gradle 缓存执行 `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，最终 BUILD SUCCESSFUL。本轮 XML：132 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。新增覆盖 109/110、124/125、139/140、154/155，接触支持/丢失、无效/混合批末、空批不改变、相同 HR 重复结算、ACC 先启动、无 HR 全未归类、静止/无新批保持、刷新不重计、日期跳变、接收时刻作为 Running 起点、10 秒 120→13 秒 130 恰好给 Zone 2 三秒、失败/完成/Retry 保留与空白不回填、Stop/整体中断/最后流结束冻结且排除清理、新 Start/重复请求/旧源/旧刷新隔离及 mm:ss 截断。首次新增测试中两项接触用例因测试构造参数顺序写反而失败，改为命名 copy 字段后全量重跑通过；生产有效性规则未改。
- 2026-09-30 5.4add 证据路径：单元测试 `PolarH10ActivityViewer/app/build/reports/tests/testDebugUnitTest/index.html`；lint `PolarH10ActivityViewer/app/build/reports/lint-results-debug.html`；debug APK `PolarH10ActivityViewer/app/build/outputs/apk/debug/app-debug.apk`。沿用 ViewModel 旋转保留与 onStop 的 isChangingConfigurations 分支已做代码检查，未做设备旋转实验，不将代码存在/自动检查写成真机通过。
- 2026-09-30 5.4add 真机待执行：① 连接 H10，Start 后核对最新有效 HR 对应档位、五柱统一比例尺和 mm:ss；② 静止保持有效 HR，区间时长应继续增长，五区间原始毫秒加未归类等于 Running，注意界面各项截断秒会有显示取整差；③ 在设备支持时复现接触变化，检查 --/原因、区间暂停、未归类增加，恢复后不回填；④ HR 失败/Retry 时累计保留、其他流继续，随后新有效批末恢复计时；⑤ Stop/重复 Stop/新 Start、旋转、断线、锁屏/后台/返回欢迎页，检查冻结、清零和保留边界及小屏文字可读性。本轮未安装或操作手机；自然难以达到的 HR 边界已由受控测试覆盖，不要求为测试达到指定高心率。静默停流无通知时继续估计旧区间，是既定局限。

- 2026-09-30 5.3add 实施：已直接修改 7 个生产 Kotlin 文件（新增 StrideLengthEstimator.kt，修改 StepCandidateDetector.kt、StepSequence.kt、CadenceWindow.kt、StepDetector.kt、PolarBleManager.kt、SensorActivity.kt）；新增 StrideLengthEstimatorTest.kt 4 项、MotionStatisticsTest.kt 9 项，StepDetectorTest.kt 新增 1 项并扩展原测试。四份文档同步本轮结果，中英文 prompt 保留。未创建 Git commit 或推送；未推进 5.4add、5.5add 或 6.1a。
- 2026-09-30 5.3add 自动检查：使用现有 Android Studio JBR 与本机 Gradle 缓存，执行 `./gradlew.bat :app:testDebugUnitTest :app:assembleDebug :app:lintDebug --offline`，BUILD SUCCESSFUL。读取本轮 XML：119 tests、0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。新增测试覆盖原峰两端/延后回落排除、拒绝峰不移起点、四步三段且不重计、两秒缓存、窗口边界/短窗/两秒归零、真实 4.99/5 秒最大值及静止零、缺口和 Retry、平均速度 100 m / 100 s = 3.6 km/h、Stop/整体中断/全部流结束冻结并排除清理耗时、新 Start/重复请求/旧源及旧会话刷新隔离。报告：`PolarH10ActivityViewer/app/build/reports/tests/testDebugUnitTest/index.html`、`PolarH10ActivityViewer/app/build/reports/lint-results-debug.html`；APK：`PolarH10ActivityViewer/app/build/outputs/apk/debug/app-debug.apk`。运行时的测试波形仅在单元测试中，不是 App 演示数据或真机证据。
- 2026-09-30 5.3add 真机验收：本轮未安装 APK、未操作手机，以下全部待执行。① 静止预热后检查当前零、约预热结束五秒后最大值可为零，无缺失提示；② 走路/跑步用人工视频核对四步后开始累计和当前速度，走已知距离并记录估计误差；③ 运动后静止至少 20 秒，检查约两秒后当前值归零、距离不变、平均继续降低；④ 缺口/ACC 失败/Retry 检查 --、重新预热、累计/最大值保留及 Incomplete ACC data 持续；⑤ Stop/重复 Stop/新 Start、旋转、断线/后台/锁屏，检查冻结、清零与保留边界；无 ACC 会话结束相关值仍为 --。K = 0.5 未校准、段首距离少估及缺失导致低估均保留为局限，不预先承诺准确率。

- 2026-09-30 add 与完整同步：已新增 5.24 及 5.3add/5.4add/5.5add 中英文待执行提示词，保留原规则、旧提示词和此前检查记录。已验证项目旧 prompt.md 无独有记录，以根目录完整历史同步；两份 AGENTS.md、两份 prompt.md 分别一致。本次仅更新四份文档，未修改应用代码，未运行测试/构建/真机验收，未创建提交。

- 2026-09-30 6.1a 前置检查：已重新检查当前 app/src/main 源码，5.3—5.5 依赖未满足，按用户本次明确指令停止 6.1a 实施，不自动补做。StepDetector.kt 的 StepState 只有 totalSteps、cadence、message，尚无步长/距离、速度及合格窗口最大步频统计；PolarBleManager.kt 仅接入已有 HR 统计、计步和 ACC/ECG 短缓存，未见五区间/未归类计时或 5.5 曲线输出；SensorActivity.kt 仍是既有开发文本界面。缺失项分别为 5.3 距离/速度/极值、5.4 心率区间、5.5 曲线与时间轴。此次只同步两份 AGENTS.md、两份 prompt.md 的检查记录，没有新增 6.1a 数据结构或修改应用代码；未运行测试、debug 构建、lint、安装或真机验证，未创建 Git commit。先完成 5.3 → 5.4 → 5.5，再重新检查 6.1a 前置条件。

- 2026-09-30 分步规划：用户确认将原 6.1 拆为 6.1a—6.1d；已更新两份 AGENTS.md 的范围、步骤表、验收及两份 prompt.md 的四组中英文提示词。6.2 功能范围保持不变；本次仅修改文档，未实施代码或运行测试/构建/真机验证。

- 2026-09-29 第 6 阶段规划：已同步两份 AGENTS.md 的 5.23 及两份 prompt.md 的中英文待执行提示词。用户仅授权本次文档更新；SQLite、新增平均/最小步频及简单 History 尚未实施，未执行测试/构建/真机验证。

- 5.2d 验证（2026-09-29）：新增 CadenceWindowTest.kt（5 项），扩展 StepDetectorTest.kt（5 项）；复用 5.2a—5.2c 全部算法及订阅/会话测试。新增覆盖短窗口小数、五秒左开右闭及未来峰排除、四步按原时间归窗、两秒精确归零、显示外推不破坏峰值、可控单调时钟 250 ms 刷新且不重置检测、缺口/失败/Retry 缺失提示与恢复、无 ACC Stop 占位、有 ACC Stop 归零、新会话清空、停止后清理回调及旧会话刷新隔离。执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；105 项测试通过，0 failures、0 errors、0 skipped，lint 0 errors、18 warnings。测试波形仅在单元测试中使用；本次未安装 APK 或执行真机计步/界面验证。
- 5.2 真机验收待执行：用 Android Studio Run 安装本次构建，连接 H10 后 Start；确认初始 Warming up ACC./等待四步时为 0，四步确认后补计且整数步频刷新。静止 60 秒、双脚不动轻微转身/摆臂 30 秒，慢走/正常走/快走各 100 步，随后补充跑步，用视频分别核对误计及漏计。停止走动后约两秒步频归零；Stop 保留步数，再 Start 清零并预热，连接保持。通过真实缺口或仅调试故障注入验证 -- 提示、Retry 保留总数并重预热；旋转不清零，断线/锁屏/后台结束会话且返回需手动重连/Start。完全无 ACC 的会话停止后仍显示 --。按 5.6 从 A_min=0.5 开始，仅在信号证据支持时每次调整 0.1 并复测，不增加自动标定，也不宣称已验证准确率。

- 5.2c 验证（2026-09-29）：新增 StepSequenceTest.kt（6 项）和 StepDetectorTest.kt（4 项），覆盖前三步不提交/第四步补计/后续逐步、原始峰值时间、重复及不足 0.2 秒不移动参考峰、0.2/2 秒边界、待确认序列超时丢弃、累计值保留及新会话清零、长序列无重复提交、真实处理路径跨批输入、仅传感器样本触发一次超时重预热、缺口、Stop/Retry 与旧源事件。执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；95 项测试通过，0 failures、0 errors；lint 0 errors、18 warnings。上述波形均为测试数据，未进入生产 UI。未安装 APK 或执行真实 H10 计步验收；目前可通过调试器观察四步提交、总数、原始峰时间以及停止/重启/Retry/旋转行为，正式计步误差和 A_min 校准仍待完整算法及显示完成后按 5.6 实测。

- 5.2b 验证（2026-09-29）：新增 StepCandidateDetectorTest.kt 的 7 项测试，覆盖预热与首个检测点、同一 H 上穿及等号、标准差大于 A_min、阈值固定、多峰一次输出及峰值时间、两秒边界/迟到回落/重新上穿、清理与原始样本预处理联动。首次原始波形测试回到与基线相同的浮点值，未严格达到求平均后的 L；将回落测试样本设为明确低于基线后通过，生产阈值未增加容差。最终 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug 为 BUILD SUCCESSFUL；85 项测试通过，0 failures、0 errors，lint 0 errors、18 warnings。未安装 APK 或执行真机候选步验收；可在调试器观察静止/走路波形中的固定阈值、峰值时间与每周期一次输出，验证 Stop/Start、Retry、缺口清理及旋转保留。候选步不等于已确认步数，A_min = 0.5 仍待后续实测校准。

- 5.2a 自动验证（2026-09-29）：新增 AccPreprocessorTest.kt 的 6 项测试，覆盖三轴单位与重力、完整滑动平均、104/105 原始样本预热边界、总体标准差与前 100 点、跨批逐样本且不重放缓存、30 ms 缺口边界、停止/重新启动和旧源事件。执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；78 项测试，0 failures、0 errors，lint 0 errors、18 warnings。本次未安装 APK 或进行真机预处理验收。真机待检查：用调试器观察静止时合加速度量级、前 4 点无平滑值、第 104/105 点边界、Stop/Start 与 Retry 重新预热、旋转保留窗口；本步没有新增 UI 或计步结果。

- Stop/Recheck 后无法再次 Start 修复（2026-09-29）：真机读取到 Connected、Stopped、HR/ACC/ECG 均 Waiting；用户确认 Stop 前后点过 Recheck。核对 SDK 8.3.0 源码：HR Flow 完成时移除通知，isFeatureReady 对 HR 及在线采集均检查 HR 通知；原应用将该结果作为再次启动前提，形成阻塞。修改 PolarBleManager.kt 的启动与 Recheck 判断，在 DataReadiness.kt 复用当前连接确认结果；没有放宽连接、权限、配置或任务清理要求。
- 本次修复验证：DataReadinessTest.kt 新增 2 项、SessionStateTest.kt 新增 1 项回归测试，覆盖通知关闭后的 Recheck/再次启动、未确认功能仍阻止就绪、连接清理后重新检查。执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；72 项测试通过，0 failures、0 errors；lint 0 errors、18 warnings。受控测试不等于修复后真机验收；尚未安装本次 APK，真机需重新运行后验证 Start → 数据到达 → Stop → Recheck → Start，以及不点 Recheck 的直接重启，确认仍 Connected 且恢复真实采集。

- 更新日期：2026-09-29（步骤 5.1 已实施并重新测试/构建；5.2—5.5 规则已确认，尚未实施）。
- 当前步骤：0.1—4.4（含 2.3）及 5.1 已有实现；本次加入有效 HR 筛选、整场 min/max/mean、有效样本触发计时及 Retry/新会话统计规则，见 5.19。下一开发步骤为 5.2a；步伐算法、图表及会话持久化尚未实施。
- 本次 5.1 文件修改：PolarBleManager.kt、SessionState.kt、SensorActivity.kt；扩展 HeartRateTest.kt 和 SessionStateTest.kt；同步两份 AGENTS.md 及 prompt.md 的实施记录。未提交 Git，原有第 5 阶段规划改动保留。
- 本次 5.1 验证：Android Studio JBR 下运行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；69 项测试全部通过，lint 0 errors、18 warnings。测试报告 app/build/reports/tests/testDebugUnitTest/index.html、lint 报告 app/build/reports/lint-results-debug.html、APK app/build/outputs/apk/debug/app-debug.apk。5.1 真机 HR/接触状态/Retry/停止/旋转/中断检查待完成，步骤见 5.19。
- 本次已写入文件（4.4）：新增 SessionState.kt、SessionStateTest.kt；修改 PolarBleManager.kt、SensorActivity.kt、AccBuffer.kt、EcgBuffer.kt；适配 DataSubscriptionsTest.kt、HeartRateTest.kt、AccBufferTest.kt、EcgBufferTest.kt 的启动前提参数；同步两份 AGENTS.md。未修改 SDK/依赖、Manifest、SensorViewModel、设备存储或 prompt.md；未自动提交。
- 本次构建验证（4.4）：2026-09-29 使用 Android Studio JBR 执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，最终 BUILD SUCCESSFUL；59 项测试通过（会话 10、ECG 8、ACC 8、HR 8、订阅 11、电量 7、配置 6、模板 1），0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。APK 位于 app/build/outputs/apk/debug/app-debug.apk；统一启动、计时、部分失败/Retry、旋转及断线/后台真机验收均待完成。
- 先前已写入文件（4.3）：修改 PolarBleManager.kt、SensorActivity.kt；新增 EcgBuffer.kt、EcgBufferTest.kt；同步两份 AGENTS.md 并补录 ACC 占用问题的用户反馈。保留 HR/ACC 行为；未修改依赖、Manifest、ViewModel、设备存储或 prompt.md。
- 先前构建验证（4.3）：2026-09-28 使用 Android Studio JBR，:app:testDebugUnitTest :app:assembleDebug :app:lintDebug 最终 BUILD SUCCESSFUL；49 项测试通过（ECG 8、ACC 8、HR 8、订阅 11、电量 7、配置 6、模板 1），0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。真实 ECG 接收、设置、快速启停、三路并行及完整生命周期验收待执行。
- 先前已写入文件（4.2）：修改 DataReadiness.kt、PolarBleManager.kt、SensorActivity.kt、DataReadinessTest.kt；新增 AccBuffer.kt、AccBufferTest.kt；同步两份 AGENTS.md。保留原 4.1 HR 实现；未修改依赖、Manifest、ViewModel、存储或提示词文件。
- 先前构建验证（4.2）：2026-09-28 使用 Android Studio JBR 执行 :app:testDebugUnitTest :app:assembleDebug :app:lintDebug，BUILD SUCCESSFUL；41 项测试通过（ACC 8、HR 8、订阅 11、电量 7、配置 6、模板 1），0 failures、0 errors、0 skipped；lint 0 errors、18 warnings，与上次数量一致。真实 ACC、削顶、快速停止/重启、HR 并行、旋转及断线/后台验收均待执行，见 5.16。
- 先前已写入文件（4.1）：修改 PolarBleManager.kt、SensorActivity.kt，新增 HeartRateTest.kt，同步两份 AGENTS.md；未修改依赖、Manifest、存储、ViewModel 或 prompt.md。
- 先前构建验证（4.1）：2026-09-28 使用 Android Studio JBR，:app:testDebugUnitTest :app:assembleDebug :app:lintDebug 最终 BUILD SUCCESSFUL；31 项测试通过（HR 8、订阅 11、电量 7、配置 4、模板 1），0 failures、0 errors、0 skipped；lint 0 errors、18 warnings，与此前数量一致。真实 H10 连续接收、快速启停、旋转、权限和断线/后台验收均待执行。
- 先前已写入文件（2.3）：修改 `PolarBleManager.kt`、`SensorActivity.kt`，新增 `DeviceBatteryTest.kt`，同步两份 AGENTS.md；未修改依赖、Manifest、存储、ViewModel 或提示词文档。
- 先前构建验证（2.3）：2026-09-27 使用 Android Studio JBR，在获准的主机环境执行 `:app:testDebugUnitTest :app:assembleDebug :app:lintDebug`，最终 BUILD SUCCESSFUL；23 项测试通过，0 failures、0 errors、0 skipped；lint 0 errors、18 warnings。真机电量、旋转、权限及断线/后台清理仍待逐项验收，见 5.14。
- 先前规划记录：新增 5.12、5.13，明确 3.1、3.2 的状态、职责、错误处理、资源清理、步骤边界和验收方法。用户已确认 ACC 100 Hz 不可用时禁用该流、其他参数唯一值直接采用而多值需确认；单路失败其他流继续且手动重试；旋转保留订阅，离开前台清理，重连后手动开始采集。该规划阶段仅更新两份 AGENTS.md；后续 3.1、3.2 实施与验证结果分别见 5.12、5.13。
- 临时界面决定：已记录第 8 阶段删除开发用详细状态展示及仅服务于它的文件，保留底层逻辑与正式用户状态；当前仅记录要求，未删除展示代码或文件。
- 已提供代码：步骤 0.1—4.4（含 2.3）已直接写入项目；已接入电量、真实 HR/ACC/ECG API 及统一会话控制和计时。实现、自动检查与真机验收分别记录；第 5 阶段算法及后续持久化仍未实施。
- 先前已写入文件（3.2）：修改 `PolarBleManager.kt`，新增 `DataSubscriptionsTest.kt`；在 `gradle/libs.versions.toml`、`app/build.gradle.kts` 增加仅测试使用的 coroutines-test 1.10.2；同步更新两份 `AGENTS.md`。3.2 沿用已有界面、ViewModel 和就绪逻辑；本次 2.3 的改动见上文。
- Git 记录：`ac252e4` 为步骤 2.1，`baad553` 为步骤 2.2，`5efc426` 为断开报错后手动重试修复；本次文档更新未自动提交。
- 项目配置落实状态：`minSdk = 33`、Polar SDK 8.3.0、协程运行时依赖 1.10.2 保持原配置；3.2 增加同版本 coroutines-test 测试依赖，未修改 Manifest。
- 已构建验证：2026-09-27 步骤 3.2 执行 `:app:assembleDebug :app:lintDebug :app:testDebugUnitTest`，BUILD SUCCESSFUL；16 项测试通过（11 项订阅管理、4 项配置策略、1 项原模板），0 failures、0 errors、0 skipped。lint 0 errors、18 warnings；比 3.1 的 17 项多出 coroutines-test 版本更新建议，固定 1.10.2 不变。首次沙箱构建因 Gradle 下载权限失败，随后获准在主机环境构建成功。报告位于项目 `app/build/reports/`，测试 XML 位于 `app/build/test-results/testDebugUnitTest/`，APK 位于 `app/build/outputs/apk/debug/app-debug.apk`。
- 已真机验证：此前通过 ADB 确认测试手机型号、Android 16 / API 36 和调试连接；2026-09-27 用户反馈实际测试中 RSSI 持续刷新。该反馈作为用户实测记录，不扩大为全部扫描、连接或存储验收通过；截图、日志及是否处于连接后状态尚未补充。
- 用户真机反馈（本次补录）：用户确认此前 Start ACC 的 ERROR_ALREADY_IN_STATE 是另一个程序占用采集功能导致，解除占用后检查通过。记录为 ACC 启动检查通过；没有确认本 App 的启停缺陷，不据此新增修复，也不扩大为快速启停、削顶、缓存上限、多流并行或完整生命周期验收通过。
- 待验证：权限拒绝与设置返回；扫描筛选、去重及停止规则；真实连接、10 秒超时、重试与生命周期；设备保存、时间更新及重启保留；主动与意外断线；断开异常重试、防重复点击及回调清理。读写错误和迟到回调仍待故障注入验证。
- 3.1 待验证与待确认：真实设备功能就绪、实际采样设置、重复请求与旋转、失败重试、断开清理和迟到结果隔离均待运行时验证，步骤见 5.12；实际 ECG 与其他多选参数尚未读取，因此没有新增参数决定。构建和配置单元测试不等于上述真机验证已通过。
- 审查跟进：断开请求抛错后无法重试已修复；“扫描 RSSI 不会刷新”的结论已撤回；系统时间回拨影响最近连接时间更新的问题尚未修复，见 5.11。
- 当前待决策：设备实际返回多组选项时尚未确认的 ECG 及其他采样参数，以及第 5 节其余待填写项。会话持久化与 History 最小方案已在 6.2 实施，6.2 验证及真机边界见 5.23.11；7.1 列表与 7.2 详情/删除已完善，见 5.27.1—5.27.2；8.0 共用主题与基础尺寸已实施（见 5.28.1），8.1 顶部连接/电量、Devices 弹窗和强度已实施（见 5.29.1），8.2 指标/区间/汇总已实施（见 5.30.1），8.3 底部曲线与开发显示清理已实施（见 5.31.1），8.4a/8.4b/8.4c 已实施（见 5.32.1.1、5.32.2.1、5.32.3.1），8.4d 已实施功能整合与部分清理、暂停继续和五分钟窗口（见 5.33.1）；合并后的 8.4e 已实施参考图/单屏展示、Retry 清理、Stop 重置和图表等高；默认竖屏及手机旋转保持竖屏验证通过，竖屏大字号仍裁切，详见 5.34.3，8.4 整体 UI 未完成，Samsung/H10 真机仍待验收，8.5a—8.5d 软件整合与本轮模拟器验收已完成，最终构建、单元/仪器测试及视觉结果见 5.40；Samsung/H10 与真实性能验收仍 pending。HR 已确认仅保留最新心率和接收时间的方案 A，见 5.15；4.4 原 Start/Stop、首个数据计时及中断结束已在 5.33 扩展暂停/继续，见 5.18；6.2 已接入结束时保存，H10 真机全链路仍待验证。1.2 扫描规则、2.1 连接生命周期和本次 3.1—3.2 的配置选择、错误与采集恢复规则均已确认，无需重复决策；本阶段不进行后台采集。
- 截图/录屏位置：【待填写】。
- 5.1 实施跟进：用户要求执行已保存的中英文提示词；5.19 所列代码及自动验证已完成，真机待验证。其他第 5 阶段核心规则见 5.1—5.7、5.20—5.22，均已明确但尚未实施，不再以旧规划记录称统计口径未定。
- 5.2 规划确认：用户采用此前技术建议，明确完整 5 点平滑后再积累 100 个 s 预热、前 100 点阈值、同一 H 上穿判断、周期 H/L 固定，以及 250 ms 步频刷新、两秒无步归零和显示用单调时间估计；规则直接更新于 5.1—5.4、5.7 及开发步骤表。2026-09-29 按用户要求检索其他计步方案，在 5.6 补充 A_min = 0.5 m/s² 的试验预设、来源差异和每次 0.1 m/s² 的手动调整方案；实测校准尚未执行。本次仅同步两份 AGENTS.md，未修改 Kotlin 或 prompt.md，未运行测试/构建或真机验收；5.2 尚未实施。
- AI 提示词与使用记录位置：工作区根目录 `prompt.md` 已记录 0.1、0.2、1.1、1.2、2.1、2.2 断开重试修复、3.1、3.2、2.3 及完整 4.1—4.4 的中英文提示词；2026-09-29 已补录 4.2、4.3、4.4 实际使用的英文提示词、中文对照及实施/验证摘要。4.2、4.4 补录仅修改文档，未重新测试或构建，未改变已有真机验证边界。完整 2.2 实施提示词及各步采用/修改/拒绝原因、验证证据仍待补齐。

### 9.3 实际测试问题记录

- 用途：集中保存实际测试中发现的所有问题，包括用户真机反馈、现场观察、处理方案、修改范围、实施状态和复测结果。后续新问题继续追加；已解决问题保留记录，不删除。代码审查推测与实际复现须区分；记录方案不表示授权立即修改代码。
- 每项记录：编号、发现日期/步骤、操作与现象、证据来源、原因判断、拟采用方案、修改范围、当前状态、复测结果。未知项明确写待补充，不把自动测试通过视为真机已解决。

| 编号 | 问题与证据 | 当前状态与跟进 |
|---|---|---|
| T01 | 原地跳被计为有效步数；2026-09-29 用户在 5.2d 后实测反馈，具体跳跃次数、误计数量及波形待补充。 | 待评估方案 A，详见下文；未修改过滤逻辑，未复测。 |
| T02 | Stop 后无法再次 Start；用户反馈并确认点过 Recheck，现场读取到 Connected、Stopped、三路 Waiting。 | 已修复同一连接的就绪判断，自动测试通过；修复后的真机复测结果尚未记录，见本节此前的 Stop/Recheck 修复记录。 |
| T03 | Start ACC 报 ERROR_ALREADY_IN_STATE；用户确认另一个程序占用采集功能。 | 用户反馈解除占用后启动检查通过；不作为已确认的本 App 缺陷，也不扩大为完整 ACC 验收。 |

#### T01：方案 A——过滤明显跳跃，先评估

- 现象解释：当前算法依据合加速度的上穿、回落及连续候选峰确认步伐，没有活动分类；连续原地跳可能满足这些条件。此解释基于当前代码，具体误计波形尚未采集核对。
- 评估方案：保留三轴合加速度，结合短时低合加速度、随后落地冲击的幅度和持续时间，识别疑似明显跳跃，在提交步数前排除对应候选；必要时清除受影响的未确认序列。先固定佩戴方式，对比正常走路、跑步和原地跳的真实数据，再确定是否实施及参数。
- 边界：跑步也可能出现类似低加速度和冲击，轻跳也可能不明显；仅目标为减少明显跳跃误计，不保证完全排除。不得直接把 Z 轴当作竖直方向，不直接删除 Z 轴，不凭空设置过滤阈值，也不通过单纯提高 A_min 或连续确认次数宣称已解决。
- 修改范围估计：中小，约 3—4 个生产文件及配套测试；AccPreprocessor.kt 提供必要特征，StepCandidateDetector.kt 关联疑似跳跃周期，StepDetector.kt 在提交前过滤，必要时调整 StepSequence.kt 的提交时机。可能需短暂延后候选提交以等待落地证据；不改 SDK、蓝牙连接、数据库，不引入分类框架。
- 验证要求：对比修改前后静止、非步行晃动、正常走路、跑步及不同幅度原地跳，记录人工动作数、App 步数、误计和漏计；评估跳跃误计下降是否伴随正常走跑漏计增加。若特征明显重叠，保留算法局限，不勉强采用单阈值过滤。
- 状态：仅记录方案 A，等待真实数据评估及用户决定；参数未定，代码未实施，效果未验证。

## 10. 最终提交检查

- [ ] 完整 Android 项目 ZIP。
- [ ] 独立的四人贡献声明。
- [ ] AI 使用文档（PDF/DOCX）：关键阶段截图、提示词、用途与设计依据。
- [ ] 真机评审演示：连接、采集、处理、可视化、历史查询。
- [ ] 全员能解释 SDK、数据流、算法、生命周期与存储。
- [ ] 展示幻灯片（PDF/PPTX）：10 分钟展示，最多 5 分钟问答。
- [ ] 展示涵盖用途、架构、处理依据、实时/历史视图、演示与反思。
- [ ] 展示讨论与本项目相关的文化和伦理问题。
- [ ] 准备不超过 2 分钟的演示视频作为可选备份。
- [ ] 每位成员承担实质展示内容。
- [ ] 按文档于 10 月 4 日 23:59 前提交；变更以课程通知为准。

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

- 用户要求：将 ECG、RR 图表持久化保存的中英文实施 prompt 写入文档，登记为增加功能。完整草案见同目录 prompt.md 的 5.46。
  Request: Document the bilingual implementation prompt for ECG/RR chart persistence as an additional feature. The full draft is in section 5.46 of prompt.md in the same directory.
- 状态：仅文档规划，待确认、未实施。本轮未修改应用、数据库或测试代码，未运行构建、测试、模拟器或真机检查，无 commit/push。后续实施需用户明确指定。
  Status: Documentation-only planning; pending confirmation and not implemented. No App/database/test code changes, builds, tests, emulator/hardware checks, commit or push this turn. Implementation requires a subsequent explicit request.
- 建议目标：整场原始 ECG 与逐条 RR 本地保存；复用 HR 订阅提取 rrsMs；ECG 保留原始时间戳/有符号电压，按五秒窗口查询展示；RR 默认建议以记录序号为横轴。分批 IO 写入、有界内存、结束排空后最终提交、失败报告/重试去重，以及关联数据事务删除。
  Proposed scope: Persist full-session raw ECG and individual RR intervals locally; obtain rrsMs from the existing HR subscription. Preserve ECG timestamps/signed voltage and query five-second display windows. Propose recorded interval number for RR. Use batched IO, bounded memory, draining before final commit, explicit failures/idempotent retries and transactional deletion of related data.
- 待确认：① 整场还是片段；② RR 序号还是估计时间横轴；③ 保留旧历史的一次性升级还是明确获准的新数据库方案；④ 进程异常结束后的未完成记录处理。不得把建议作为已批准规则，不擅自迁移兼容或清空历史。
  Open decisions: full session versus excerpt; RR interval index versus estimated time; a preserving one-time migration versus an explicitly approved fresh database; handling unfinished records after process termination. Proposals are not approved rules; no implicit compatibility migration or history deletion.
- 修改范围：采集管理、最少必要的数据模型/写入组件、会话冻结与保存协调、SQLite 表/索引/窗口查询/删除、History 图表/状态及对应测试。保留实时 ECG 短缓存，持久化直接接收采集批次；不将整场 ECG 塞入 SessionSnapshot。不增加 Trend、Session 实时 RR、HRV、导出、云同步或后台采集功能。
  Affected areas: acquisition integration, minimal models/writer components, freeze/save coordination, SQLite tables/indexes/window queries/deletion, History charts/states and relevant tests. Keep the live ECG buffer and persist incoming batches directly; do not place whole-session ECG in SessionSnapshot. Exclude Trend, live Session RR, HRV, export, cloud sync and new background acquisition.
- 现有规则关系：此前“不持久化原始 ECG”“RR 未接入/History ECG 与 RR 禁用”仍描述当前实现；本节只登记拟增加功能，确认实施后再更新对应规则。实施前复核最新生命周期与工作区改动，不用旧提示词覆盖已有调整；保留 5.44/5.44.1 移除 Incomplete 标签的要求，新写入错误和未完成记录可见性另按确认规则处理。
  Relationship to existing rules: no raw ECG persistence, no RR processing and disabled History ECG/RR still describe the existing implementation; this section only registers a proposed addition. Update those rules after approved implementation. Recheck current lifecycle and workspace changes before coding. Preserve removed Incomplete labels under 5.44/5.44.1; new write-error and unfinished-record visibility follows the confirmed policy.
- 验收计划：RR 多间期/相同值/有效性、ECG 符号/时间/断段、暂停继续/迟到回调、写入失败/重试/最终提交/删除、四小时生成数据规模、窗口查询和深浅主题/字号 1.0/2.0；自动测试与 Samsung/H10 真机证据分别记录，不把规划写成通过。
  Planned validation: RR multiplicity/equal values/validity, ECG sign/timing/segments, pause/resume/stale callbacks, write failures/retry/final commit/deletion, four-hour generated-data scale, window queries and light/dark at font scales 1.0/2.0. Record automated and Samsung/H10 evidence separately; planned checks are not passes.

### 5.47 离开前台自动暂停 / Automatic pause on leaving Session（2026-10-04）

- 用户先要求实施方案、不改代码，随后明确要求“实现”。本节覆盖旧的离开前台/锁屏/返回欢迎页结束会话、仅 Running 可暂停、切换 History 继续采集及 Activity ViewModel 持有者规则；此前记录保留为历史证据。
  The user first requested a plan without code changes, then explicitly requested implementation. This section supersedes ending sessions on background/lock/welcome navigation, Running-only pause, continued acquisition in History and Activity ViewModel ownership. Earlier records remain historical evidence.
- SensorActivity.onPause 自动暂停；onStop 仅解除页面回调。切换 History 显式暂停；返回 Session/onResume 只复核可用性，不自动继续。页面内 Devices 弹窗不触发暂停；引起 Activity.onPause 的系统界面会暂停。
  SensorActivity.onPause pauses automatically; onStop detaches the page callback. Selecting History explicitly pauses. Returning to Session/onResume rechecks availability without resuming acquisition. The in-page Devices dialog does not pause; system UI that invokes Activity.onPause does.
- 复用同一 SessionController 暂停路径，支持 Running 和 Starting；立即冻结有效活动时间、拒绝迟到数据/刷新、停止 HR/ACC/ECG 订阅，清理完成后 Paused。保留 UUID、原开始时间、统计与历史，暂停不结束、不提交保存。Continue 需原设备已连接、流清理完成及数据就绪；首个真实数据恢复计时，ACC 重新预热、曲线断段，暂停不计时。
  Reuse SessionController.pause for Running and Starting: freeze active time, reject late events/refreshes, stop HR/ACC/ECG subscriptions and reach Paused after cleanup. Keep UUID, original start, statistics and histories; pausing neither ends nor submits a save. Continue requires the original connected device, completed cleanup and readiness. Timing resumes on real data, ACC warms up again and charts retain segment breaks; paused time is excluded.
- 新增 ActivityViewerApplication 并在 Manifest 登记，由应用进程持有 PolarBleManager；删除 SensorViewModel，页面销毁不再释放共享会话。仅进程存活时可恢复，未实现强制停止/进程死亡后的草稿恢复，也未增加后台采集或服务。
  Register ActivityViewerApplication in the manifest and let it own PolarBleManager for the process lifetime. Remove SensorViewModel; page destruction no longer releases the shared session. Recovery requires the process to remain alive. No draft restoration after process death/force-stop, background acquisition or service is added.
- 自动暂停停止扫描、取消尚未完成的连接请求，已有连接不主动断开。拆开连接清理与结束逻辑：暂停中的断线、蓝牙关闭、权限/SDK释放和重连准备保留会话；SDK释放后清除本地连接归属，由新 SDK 回调确认重连。运行中真实断线仍按原规则结束。暂停中只允许重连原设备，Devices 提供恢复提示；Stop 仍最终保存并清空当前会话。
  Automatic pause stops scanning and cancels unconfirmed connection attempts, while retaining established connections. Separate connection cleanup from finalization: disconnect, Bluetooth loss, permission/SDK release and reconnection preparation preserve a paused session. SDK release clears local connection ownership; a fresh SDK callback must confirm reconnection. Connection loss while running still ends the session. Only the original device is accepted while a session is open; Devices shows recovery guidance. Stop still saves and resets the session.
- 验证：237 单元测试通过（新增 6 项，0 failures/errors/skipped）；debug/测试 APK 构建及 lint 通过（0 errors、23 warnings，当前依赖版本建议/已有资源与样式提醒）。专用 emulator-5592 上 AutoPauseLifecycleTest 最终 7 项通过，覆盖真实生命周期回调、Home/灭屏与返回、跨 Activity/欢迎页、History、Starting 重建、暂停中蓝牙清理/拒绝其他设备、Stop 与真实 SQLite 原会话保存。仪器测试使用受控订阅、不代表 H10 数据验证；测试产生记录已清理。
  Validation: 237 unit tests pass (six new tests; zero failures/errors/skips), debug/test APK builds and lint pass (zero errors, 23 warnings for current dependency suggestions and existing resources/style). All seven final AutoPauseLifecycleTest checks pass on dedicated emulator-5592, covering real lifecycle callbacks, Home/screen-off/return, Activity/welcome navigation, History, Starting recreation, paused Bluetooth cleanup/wrong-device rejection, and Stop saving the original session to real SQLite. Instrumentation uses controlled subscriptions, not H10 signals; fixture records are removed.
- 首轮 Home 返回及追加跨 Activity 用例分别遇到测试导航 API/启动 flags 问题，调整测试为实际任务前台导航及从 SensorActivity 启动 MainActivity 后最终全通过；未作为 App 故障记录。证据：build/auto-pause-validation/build-final.txt、build-tests-final.txt、lifecycle-tests-final.txt 及单元/lint 报告。SDK 仍固定 8.3.0，已核对官方该版本 PolarBleApi.kt 的 shutDown/手动重连接口，未改变 SDK 依赖或采样配置。
  Initial Home-return and added Activity-navigation checks encountered test navigation API/launch-flag issues. Use actual task foreground navigation and launch MainActivity from SensorActivity; the final suite passes. These were test-harness issues. Evidence: build/auto-pause-validation/build-final.txt, build-tests-final.txt, lifecycle-tests-final.txt and unit/lint reports. SDK remains 8.3.0; its official PolarBleApi.kt shutdown/manual-reconnection contracts were checked. Dependencies and sampling configuration are unchanged.
- Samsung/H10 未安装或操作本轮版本。真实三路流停止/恢复、暂停期间断线后同设备重连、长时间锁屏与系统省电仍 pending。两对文档同步，无 commit/push。
  No installation or interaction with Samsung/H10 this turn. Real three-stream stop/resume, same-device reconnection after a paused disconnect, prolonged lock and power management remain pending. Both documentation pairs synchronized; no commit/push.

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

### 5.48.10 Activity Summary 点击与字号调整 / Interaction and typography (2026-10-04)

- 按用户最新要求，Intensity、Cardio Load、Cadence Stability、Session Strain 只显示结果，删除四项卡片的点击入口、说明状态和详情弹窗。本节覆盖此前点击说明要求；指标计算与 SQLite v5 保存规则不变。
  The four metric cards now display results only. Remove click actions, explanation state and help dialogs, superseding earlier help-interaction requirements. Metric calculations and SQLite v5 persistence remain unchanged.
- Heart Rate 与 Cadence 数值统一为21sp、26sp行高，使用相同卡片高度与内容排布，不再分别自动缩字。下面 Intensity/Cardio Load/Cadence Stability 三项数值沿用同一个20sp样式；Session Strain也保持20sp。大字号继续纵向排布和滚动。
  Heart Rate and Cadence values share 21sp text and 26sp line height, equal card heights and matching layout, without independent autosizing. The three metrics below retain their shared 20sp value style; Session Strain remains 20sp. Enlarged system fonts retain stacked, scrollable content.
- 本轮验证：debug/测试APK构建通过；lint为0 errors、23 warnings。独立emulator-5590默认字号下9项相关检查通过（ActivityMetricsUiTest 5项、HistoryPresentationTest 4项），实际系统字号2.0复跑6项通过（指标5项及深浅主题布局1项）。已视觉核对默认字号整页、实际指标数值及深色大字号滚动截图，均为合成测试夹具。未新增测试类，未重跑全量单元测试，未安装或操作Samsung/H10，无commit/push。
  Validation: debug/test APK builds pass; lint reports zero errors and 23 warnings. Nine relevant checks pass on emulator-5590 at normal font, with six repeats passing at actual system font 2.0. Inspected normal-font full-page/metric-value captures and dark enlarged-font scrolling captures using synthetic fixtures. No new test classes, full unit-suite rerun, Samsung/H10 installation or operation, commit or push.
- 证据 / Evidence: build/summary-type-polish/build.txt、light1-tests.txt、dark2-tests.txt、metrics-light1.png、light1-captures/、font2-captures/。两对AGENTS.md/prompt.md已同步。

## 5.49 删除RR功能 / Remove RR functionality（2026-10-04）

- 按用户要求完整删除RR图表与应用内采集、存储相关流程，覆盖5.46及之后所有RR窗口、六十条浏览、四图切换和RR持久化要求。Activity Summary保留HR、Cadence、ECG三种等宽选项，ECG仍为五秒窗口、左右滑动与固定布局；沿用5.48.10的无点击高层指标和统一字号。
  Remove the RR feature as requested, superseding earlier RR windows, sixty-record browsing, four-chart selection and persistence requirements. Keep HR/Cadence/ECG with equal-width choices, five-second ECG swipe windows and fixed layout. Retain 5.48.10 metric interactions and typography.
- Polar BLE SDK仍为8.3.0。RR是PolarHrSample内随HR回调附带的字段；App不再读取rrsMs/rrAvailable，不再由RR启动会话或获得保存资格，但保留startHrStreaming及有效HR处理。SDK/设备仍可能在HR数据包中附带RR，不宣称关闭了设备端字段发送。官方定义：[PolarHrData 8.3.0](https://raw.githubusercontent.com/polarofficial/polar-ble-sdk/8.3.0/sources/Android/android-communications/library/src/sdk/java/com/polar/sdk/api/model/PolarHrData.kt)。
  Retain SDK 8.3.0 HR streaming and valid-HR processing, without reading RR fields or using them to start/qualify an activity. The SDK/device may still include RR in HR packets; this does not claim disabling transmission at the sensor.
- 删除RrPoint、SignalBatch.rr、RR分段/批次/计数/缓存、endRr/receiveRr/markRrReceived、128条RR分批与checkpoint触发、rrCount/rrWindow和RR专用图轴参数。SignalBuffer/SignalWriter继续服务ECG及已有摘要/HR/运动checkpoint；保留队列容量、暂停/继续、停止保存和异常归档。
  Remove RR models, buffering/segmentation, counters, flags, batching/checkpoint triggers, queries and chart-axis overrides. Keep ECG/history checkpoints, queue limits, pause/continue, final save and interrupted recovery.
- SQLite升级v6：新库没有rr_points或receivedRr；已有v2—v5在升级事务中仅复制保留的摘要列，删掉RR标记并DROP旧RR表；不重建/复制ECG及HR/步频子表。v1直接创建现行无RR信号结构；既有v1—v4评分迁移规则继续执行，v5原始AU及已存百分制评分原样保留。旧已保存活动本身保留；未提交且删除RR后已无HR/ACC/ECG数据的空记录由原恢复规则删除。
  SQLite v6 creates no RR schema. During migration preserve summary columns and all ECG/HR/motion child tables, remove the RR flag/table and retain earlier score migrations. V5 raw AU and stored scores are copied exactly. Keep finalized activities; recovery removes unfinished records with no remaining HR/ACC/ECG data.
- 当前minSdk=33，其SQLite为3.32，因此沿用重建摘要表方法，不使用较新DROP COLUMN。onConfigure在需要重建时先关闭外键，升级事务内核对foreign_key_check，onOpen恢复外键；失败时schema、数据与user_version一起回滚。依据：[Android SQLite版本](https://developer.android.com/reference/android/database/sqlite/package-summary)、[SQLite ALTER TABLE](https://www.sqlite.org/lang_altertable.html)。
  Use the summary-table rebuild supported by minSdk 33 SQLite, with foreign keys configured before migration, integrity checked inside the transaction and enforcement restored on open. Failed upgrades roll back schema, rows and version.
### 5.49.1 本轮实施与验证 / Implementation and validation

- 生产代码已移除全部RR处理，源码中只保留升级时DROP旧rr_points的清理语句；测试中的RR仅用于构造旧数据库与断言功能已移除。修改PolarBleManager、SignalHistory、SessionRecord、SessionState、SessionDatabase、HistoryCharts和LiveChartPlot，并更新相关既有测试；新增RrRemovalDatabaseTest三项。
  Production code retains only the legacy RR table-drop statement. Test RR references construct historical schemas or assert absence. Updated the acquisition, buffer, session, database and chart owners and relevant tests; added three migration/removal checks.
- 261项单元测试通过（移除原1项RR缓存测试，0 failures/errors/skips）；debug、测试APK和lint通过（0 errors、23 warnings）。首轮测试夹具误用了派生属性作为构造参数，修正为validHrCount后构建和全部单元测试通过。
  All 261 unit tests pass after removing one obsolete RR-buffer test, with zero failures/errors/skips. Debug/test builds and lint pass (zero errors, 23 warnings). Corrected an initial test-fixture constructor argument before the successful full build/test run.
- 独立emulator-5590共53项不同检查通过：RR移除/迁移3、指标数据库8、信号数据库6、SessionDatabase6、图表3、HistoryDetail9、HistoryPresentation4、指标UI5、AutoPauseLifecycle7、进程恢复2。实际系统字号2.0复跑9项通过（图表3、指标UI5、深浅主题布局1），不重复计入53项。
  Fifty-three distinct emulator checks pass across database, charts, History, metrics, lifecycle and process-recovery runs. Nine repeat at actual system font 2.0 and are not counted twice.
- 数据库覆盖新库无RR、v1—v5保留升级、v5已存12.5评分不重算、原始AU/HR/步频/ECG保留、旧RR表/标记删除、重开、级联删除、升级失败回滚及重试；四小时合成ECG共1,872,000点的分块存储与有界窗口检查通过。独立进程seed→force-stop→冷启动→verify通过，未完成20秒记录恢复为唯一异常归档且ECG窗口保留。以上不是H10四小时性能或系统自然杀进程验证。
  Verify fresh/legacy schemas, unchanged persisted scores/raw AU/history/ECG, RR removal, reopen/cascade and rollback/retry. Four-hour synthetic ECG storage/window coverage and a controlled seed/force-stop/cold-start/recovery sequence pass. These do not establish physical-device performance or natural process-kill behavior.
- 28项UI/生命周期首轮中，旧blockedDelete检查在Espresso.pressBack遇到窗口焦点异常；其余27项通过，该项随后单独复跑通过，没有为此修改生产代码。默认字号整页、三图选择及深色大字号滚动/ECG选择截图已视觉核对；保留全部初始日志，不能把首轮混合日志称作全通过。
  The initial 28-check UI/lifecycle run had one Espresso window-focus failure in the existing blocked-delete test; the other 27 passed and the failing check passed separately on retry without a production-code change. Inspected normal-font three-chart/full-page and dark enlarged-font scrolling/ECG captures. Retain initial logs rather than labeling the mixed run all-pass.
- 证据：build/rr-removal-validation/的build-final.txt、recovery-build.txt、database-tests.txt、ui-lifecycle-tests.txt、delete-focus-retry.txt、dark2-tests.txt、process-seed.txt、process-recovery.txt、passed-tests.txt、light1-captures/、font2-captures/、ecg-dark2.png；截图均为合成测试数据。两对文档同步，真机未安装或操作，无commit/push。
  Evidence is under build/rr-removal-validation; screenshots use synthetic fixtures. Both documentation pairs are synchronized. No Samsung/H10 installation or operation, commit or push.

## 5.50 暂停边界连接 / Connect pause boundaries（2026-10-04）

- 用户确认仅修改HR和Cadence。Session实时图及新保存活动的History图按活动时间直接连接正常暂停前后的有效点；暂停时间仍不计入Duration。覆盖旧的暂停必断段规则，ECG保持现有断段和窗口行为。
  User scope is HR and Cadence only. Connect valid readings across ordinary pause/resume in live charts and newly saved History charts using active time; paused time remains excluded. This supersedes mandatory pause breaks. ECG segmentation/windows are unchanged.
- 暂停时仅为正在RECEIVING的HR/ACC保留连接资格；暂停期间设备断线、保存阻塞或订阅失败不连接。HR继续保留无效读数及超过三秒活动时间的缺口；ACC恢复启动记录真实检测分段，后续真实数据缺口继续断段。
  Capture continuation eligibility only for receiving HR/ACC streams. Disconnection while paused, blocked persistence and stream failures retain breaks. Invalid HR and gaps exceeding three seconds of active time still break. Capture the ACC detector segment at restart so later real gaps still break.
- ACC正常恢复预热期间不写入空点、不补造步频或增加步数；下一次有效步频连接暂停前有效点。活动计时、统计算法、采集控制和SQLite v6结构不变；既有breakBefore字段保存新的连接结果。旧记录无法区分暂停与真实缺口，因此不改写旧断段标记。
  Skip empty points during ordinary ACC resume warmup and connect the next valid cadence without synthesizing readings or steps. Timing/statistical algorithms, collection controls and SQLite v6 are unchanged; existing breakBefore fields persist the result. Existing records retain their flags because pause and real-gap breaks cannot be distinguished retrospectively.
- 实现：PolarBleManager传递暂停资格及恢复ACC分段；LiveCharts、HrHistory和MotionHistory处理连接；更新旧暂停断段测试，新增8项PauseChartContinuityTest及1项SQLite保存重开测试。覆盖正常连接、预热、真实缺口、订阅失败和禁止连接情况。
  Implementation updates pause eligibility/ACC segment wiring and live/history collectors. Update previous pause expectations; add eight continuity unit checks and one SQLite round-trip check covering connections, warmup, real gaps, failures and ineligible continuation.
- 验证：269项单元测试全部通过（0 failures/errors/skips）；debug及测试APK构建通过；lint为0 errors、23 warnings。独立emulator-5590分轮21项检查通过：SQLite 7项（含暂停连接保存重读）、AutoPauseLifecycle 7项、HistoryPresentation 4项及SignalChart 3项。首轮启动测试返回Process crashed，随后分开运行7+14项均通过；保留初始日志，不将首轮称作通过。首轮单元测试的旧预热断段断言按新需求修正后，全套通过。
  All 269 unit tests pass with zero failures/errors/skips; debug/test APK builds and lint pass (zero errors, 23 warnings). Twenty-one distinct emulator checks pass: seven SQLite, seven lifecycle, four History presentation and three signal-chart checks. The initial instrumentation launch returned Process crashed; separate runs subsequently passed all 7+14 checks. Preserve the initial log. Update the obsolete warmup-break unit expectation before the successful full rerun.
- 证据：build/pause-chart-continuity/build-final.txt、database-tests.txt、ui-lifecycle-tests.txt及原始失败日志；单元XML位于app/build/test-results/testDebugUnitTest。模拟器使用受控/合成数据；本轮未新增截图视觉验收、未复跑字号2.0，Samsung/H10真机未验证，未commit/push。两对AGENTS.md/prompt.md已同步。
  Evidence is under build/pause-chart-continuity and the unit XML report directory. Emulator checks use controlled/synthetic data. No new screenshot visual acceptance, font-2.0 rerun, Samsung/H10 validation, commit or push. Both document pairs are synchronized.

## 5.51 启动阶段图表显示 / Startup chart presentation（2026-10-04）

- 用户要求实施启动显示优化。ECG保留所有原始电压和时间戳，仅在实时及History绘图时隐藏检测到的启动不稳定段，显示Signal stabilizing；不固定删除前五秒、不移动时间轴、不补造数据。本轮采用原幅值显示门控及坐标缩放，不叠加基线滤波，不标注Baseline corrected。
  Preserve all raw ECG voltages/timestamps. Hide detected unsettled startup only in live/History rendering and show Signal stabilizing, retaining original elapsed positions. No fixed five-second deletion, synthetic values or baseline filtering; do not label the waveform Baseline corrected.
- EcgStartupGate是显示启发式：每个连续采集段按原始时间组成250 ms完整块，保留最近四块的电压中位数；四个中位数最大减最小不超过250 µV时，从这四块的第一块起点开放显示。取得约一秒证据后，本来稳定的开头可原样显示；不足一秒或始终漂移的段保持空白提示。一次开放后不重新隐藏后续伪迹；本规则不是临床信号质量判定，也不能证明显示出来的信号没有噪声。阈值须继续由不同佩戴/运动的真机记录验证。
  The display heuristic uses four complete 250 ms blocks whose voltage medians span at most 250 µV. Once confirmed, show samples from the first of these blocks at their original times. Already stable starts are retained after about one second of evidence; short/unsettled segments stay blank with status. The gate latches per continuous segment and does not remove later artefacts. This is not a clinical quality assessment; broader hardware validation remains required.
- ECG暂停重开及真实缺口重新判定，不跨段连接。History按原始采集段前缀计算起点，不随五秒浏览窗口重置；原始ecgWindow查询保持可用，SQLite v6和原始BLOB不变。既有ECG记录也可应用显示规则；没有ECG样本仍显示No ECG data in this interval，存在但暂不显示的样本不误报无数据。实时Samples计数保留窗口原始样本数量。
  Restart startup detection after ECG pause/restart or real gaps without bridging segments. History replays segment prefixes, not viewport starts. Retain the raw query, SQLite v6 and original BLOBs; existing ECG recordings receive the display rule. Distinguish absent data from withheld data and retain raw visible-window sample counts.
- ECG纵轴只根据显示中的原始最小/最大值加10%留白，不再强制包含0；常量值使用原值上下1 µV范围。HR/Cadence仍以0为纵轴起点。没有裁幅、平移电压或修改心率统计。
  ECG bounds use displayed raw extrema plus ten percent padding without forcing zero into range; constant ECG values use ±1 µV around their actual value. HR/Cadence retain zero-based axes. No clipping, voltage shifting or HR-statistic changes.
- Cadence启动/重连预热及首次四步确认前显示空值，确认阶段标记Detecting steps；没有候选步伐且已收到完整五秒预热后ACC样本时可确认0，单靠UI计时不能产生此0。确认步伐后保留原步频算法与无步伐两秒归零；普通序列超时保留已取得的确认资格，真实缺口/订阅重新启动重新确认。计步、距离与摘要公式不改。
  Cadence is unknown during startup/retry warmup and initial four-step confirmation, with Detecting steps while pending. A quiet zero needs five full seconds of arriving post-warmup samples and no pending sequence; UI time alone cannot establish it. Preserve established cadence calculations and two-second zeroing. Ordinary sequence expiry retains prior readiness; real gaps/restarts require new confirmation. Step/distance/summary formulas remain unchanged.
- 5.50的正常暂停连接继续生效：恢复预热与步伐确认都不插入空点，等待下一次有效步频再连线；实际缺口仍断段。新活动持久化新的Cadence空值；旧Cadence的0无法区分静止和旧启动状态，因此不改写旧记录。
  Preserve 5.50 pause connections through both resume warmup and pending confirmation, without inserting empty points. Real gaps still break. Newly recorded cadence stores unknowns as null; existing zeros are not reinterpreted.
- 真实记录回放：使用上一轮从手机只读取得的本地数据库快照，Python重放相同中位数规则（非新安装/真机实时测试）。三场记录的首次显示点约3.681 s、7.212 s、3.813 s；第二场首次ECG本身就在7.212 s。最近一场前五秒原值范围[-348,12640] µV，保留可见样本范围[-348,601] µV。回放只验证这些记录上的显示效果，不表示所有设备条件已通过。
  Replayed the same median rule in Python against the previously read-only device snapshot, not a newly installed/live hardware run. First displayed times are about 3.681, 7.212 and 3.813 seconds; the second recording's first ECG sample itself occurs at 7.212 seconds. The latest first-five-second range changes from [-348,12640] to [-348,601] µV by withholding startup samples, without changing retained values. Results apply only to these recordings.
- 验证：277项单元测试通过（0 failures/errors/skips）；debug/测试APK与lint通过（0 errors、23 warnings）。独立emulator-5590分轮45项不同检查通过：StartupDisplay 3、SignalDatabase 6、LiveChartCard 15、SignalChart 3、AutoPauseLifecycle 7、SessionDatabase 7、HistoryPresentation 4；另实际系统字号2.0/深色复查5项通过，不重复计数。SQLite检查确认原始ECG保存重开不变、启动隐藏与恢复段重新判定、重叠窗口一致、全不稳定数据不伪造平线；四小时原始数据分块存储回归通过，但不是四小时显示门控性能/真机验收。
  All 277 unit tests pass (zero failures/errors/skips), with debug/test APKs and lint passing (zero errors, 23 warnings). Forty-five distinct emulator checks pass across startup display, signal storage, live charts, signal charts, lifecycle, session storage and History presentation. Five repeat in actual font-2.0/dark mode. Verify unchanged raw ECG after reopen, segment restart gating, overlapping-window consistency and no invented flat trace. Four-hour raw-storage regression passes; this does not establish four-hour display-gate performance or hardware acceptance.
- 首轮5项单元失败均为旧启动即0/即时绘图预期，逐项改为新规则后全量通过；首次34项模拟器中1项旧Devices测试失败，原因是未连接夹具及旧提示预期。仅更新夹具为已连接、当前错误文字和Recheck，后续15项回归全部通过，没有修改Devices生产代码。初始截图被模拟器预装Pixel Watch崩溃弹窗遮挡，不作验收依据；仅在只读模拟器禁用该预装包并重新捕获Compose截图。
  Update five obsolete unit expectations, then pass the full suite. One of the first 34 emulator checks used an obsolete disconnected Devices fixture and old text. Correct only the fixture/current error and Recheck assertions; all 15 follow-up checks pass without Devices production changes. Initial captures were obscured by the emulator's bundled Pixel Watch crash dialog; disable it only in the disposable emulator and recapture Compose content.
- 证据：build/startup-display-validation/build-second.txt、build-final.txt、test-build-final.txt、emulator-tests.txt、final-regression.txt、dark2-tests.txt、recorded-replay.json、startup-*-final-1.0.png及startup-*-2.0.png。已视觉核对默认字号及深色2.0字号的启动留白/提示/曲线，截图为合成数据。本轮没有安装/操作真机，没有commit/push；两对文档同步，阈值的更多真机条件验收pending。
  Evidence is under build/startup-display-validation. Visually checked normal-font and dark/font-2.0 startup gaps/status/waveforms using synthetic captures. No hardware installation/operation, commit or push this turn. Both document pairs are synchronized; broader real-device validation of the heuristic is pending.

## 5.52 ECG显示撤销与评分格式 / Restore ECG display and revise score presentation（2026-10-04）

- 按用户要求撤销5.51中全部ECG显示更改：移除EcgStartupGate、启动样本隐藏、Signal stabilizing提示及显示前缀查询；实时图和History恢复直接绘制原始ECG，恢复包含0的旧纵轴范围及旧样本计数。原始存储未曾删除/改写，无需数据迁移；原五秒浏览、真实缺口和暂停断段保持原样。删除仅服务被撤销功能的测试，恢复原ECG断言。
  Revert all 5.51 ECG presentation changes: remove the startup gate, hidden samples, stabilization label and prefix replay query. Restore raw live/History rendering, the previous zero-inclusive ECG scale and sample count. Raw storage was never deleted/rewritten, so no migration is needed. Keep five-second browsing and existing ECG gap/pause segmentation; remove reverted-feature tests and restore previous assertions.
- 保留5.50的HR/Cadence暂停连接，以及5.51的Cadence预热/首次确认前为空与真实静止归零。此次撤销仅针对ECG显示。
  Retain HR/Cadence pause connections and pending-cadence/quiet-zero handling. The rollback is limited to ECG presentation.
- Intensity展示为round(原始1—5评级×2) / 10，仅显示整数；Cardio Load按用户确认的round(10×AU/(AU+100)) / 10，仅显示整数。85 AU显示5 / 10，900 AU显示9 / 10，1900 AU四舍五入显示10 / 10。显示10分可能来自四舍五入，不代表无界AU本身有固定满分阈值。
  Display Intensity as round(original 1–5 rating × 2) out of 10. Display Cardio Load using the user-confirmed round(10 × AU / (AU + 100)), out of 10. Examples: 85 AU → 5, 900 AU → 9, 1900 AU → 10. An integer ten may result from rounding; raw AU remains unbounded.
- Cadence Stability只去掉CV前缀，显示一位小数百分数（例如7.4%、0.0%），仍表示原变异系数，没有改成100−CV。Session Strain的已存百分制评分和一位小数保持。所有缺失数据仍显示--，不附满分分母。
  Cadence Stability removes only the CV prefix and retains one decimal plus percent (e.g. 7.4%, 0.0%). It remains the original variation percentage, not 100 minus CV. Session Strain retains its persisted 0–100 score/one decimal. Missing metrics stay -- without a denominator.
- 用户确认对比色用于满分上限文字：/ 10与/ 100使用主题primary（浅色#2563EB、深色#60A5FA），得分保留原正文颜色。采用同一Text中的不同颜色区段，字号/居中/无点击交互不变；未增加满分后的整卡变色。
  The user confirmed contrasting color for denominator text. Use theme primary for / 10 and / 100, keeping the numerator's existing text color, font size, centering and non-clickable layout. Do not recolor whole cards at maximum scores.
- 以上仅变更展示，SQLite v6、指标算法版本2、原始Intensity/AU/CV和已存Session Strain评分均不变；已有记录读出后按新格式显示，不重算或写回数据库。
  These are presentation changes only: preserve SQLite v6, algorithm version 2, original intensity/AU/CV and stored Session Strain scores. Existing records use the new formatting without recalculation or database writes.
- 本轮验证：272项单元测试通过（0 failures/errors/skips，移除已撤销ECG门控/缩放的6项测试，新增1项十分制展示检查）；debug/测试APK构建及lint通过（0 errors、23 warnings）。ChartScale、HistoryCharts、SessionDatabase源码核对与ECG优化前版本一致，未残留启动隐藏/前缀查询调用。
  All 272 unit tests pass after removing six reverted ECG gate/scale checks and adding one ten-point presentation check. Debug/test APK builds and lint pass (zero errors, 23 warnings). ChartScale, HistoryCharts and SessionDatabase match their pre-optimization source, with no startup-gate/prefix-query calls remaining.
- 独立emulator-5590默认字号25项检查全部通过：指标UI 6、指标数据库8、SignalChart 3、HistoryPresentation 4、信号保存/恢复2、ECG计数/负值显示2。深色实际系统字号2.0复跑10项全部通过（指标UI 6、SignalChart 3、History布局1）。核对整数舍入、满分、空值、一位小数百分比、分母文字颜色范围、已存原值不变、无点击详情、原ECG窗口/数据保留。
  All 25 normal-font emulator checks pass; ten repeat successfully in dark mode at actual font scale 2.0. Coverage includes integer rounding/full scores, missing values, one-decimal percentages, denominator color spans, unchanged stored raw metrics, no detail-click actions and original ECG windows/data.
- 默认字号及深色2.0字号截图已视觉核对，均为合成测试夹具。证据：build/metric-rating-validation/build.txt、light1-tests.txt、dark2-tests.txt、summary-light1.png、full-light1.png、summary-dark2.png和full-dark2.png。两对AGENTS.md/prompt.md同步；未安装/操作真机，无commit/push。
  Inspected normal-font and dark/font-2.0 captures using synthetic fixtures. Evidence is under build/metric-rating-validation. Both documentation pairs are synchronized; no hardware installation/operation, commit or push.

## 5.53 Session Cadence光滑曲线 / Smooth Session cadence（2026-10-04）

- 用户要求：现在需要使用光滑曲线。仅Session的Cadence图启用三次贝塞尔连接，控制点位于相邻点横坐标中点、纵坐标分别等于两端读数；接点水平切线连续，曲线经过原点且不超出相邻读数范围，填充复用同一路径。
  User request: use a smooth curve. Enable cubic Bezier connections only for Session cadence. Controls share the midpoint time coordinate and use the respective endpoint values, giving matching horizontal tangents without overshoot. The fill uses the same path.
- 原始读数、步频算法、统计、保存、五分钟窗口及首次确认空值不变。沿用chartSegments，正常暂停可连接，真实缺口仍断段；HR、ECG和History保持原绘制。
  Preserve readings, cadence calculations, statistics, storage, the five-minute window and pending-confirmation unknowns. Existing segments retain ordinary pause connections and real gaps; HR, ECG and History rendering remain unchanged.
- 本轮修改LiveChartCard.kt与LiveChartPlot.kt。debug构建、272项既有单元测试（0 failures/errors/skips）及lint通过（0 errors、23 warnings）；未新增测试。证据：build/cadence-smooth-validation.txt及app/build对应报告。未运行模拟器视觉或仪器检查，未安装/操作Samsung/H10；实际观感待验收，无commit/push。
  Changed LiveChartCard.kt and LiveChartPlot.kt. Debug build, all 272 existing unit tests (zero failures/errors/skips) and lint pass (zero errors, 23 warnings). No new tests. Evidence: build/cadence-smooth-validation.txt and app/build reports. No emulator visual/instrumentation checks or Samsung/H10 installation/operation this turn; appearance validation remains pending. No commit/push.
## 5.54 Session Cadence显示平滑与改名 / Cadence display filtering and label（2026-10-04）

- 用户反馈Motion曲线仍不够平滑，并要求改名Cadence。5.53仅改变相邻点连接，密集250ms读数仍可能呈现锯齿。本轮将Session选择标签改为Cadence，并在贝塞尔绘制前增加时间常数1秒的指数低通：alpha=1-exp(-dt/1000)，display=previousDisplay+alpha*(raw-previousDisplay)。仅用于绘图及填充，不更改原始数据、当前步频、Mean/Max、计步、评分或SQLite。
  The user reported insufficient smoothing and requested the Cadence label. Section 5.53 only rounded connections between dense 250 ms samples. Rename the Session tab to Cadence and apply a display-only exponential low-pass filter with a one-second time constant before Bezier drawing. Alpha is 1-exp(-dt/1000); the display is previousDisplay+alpha*(raw-previousDisplay). Readings, current cadence, Mean/Max, steps, scores and SQLite remain unchanged.
- 图线变化约有1秒响应滞后；保持原时间戳，每个真实断段/空值后从首个有效值重新开始，不跨缺口平均。普通暂停连接规则不变。HR、ECG与History不启用此显示滤波。五分钟窗口首个点从其原值初始化；窗口左端可能有短暂滤波过渡。此规则覆盖5.53中图线经过每个原始点的描述。
  The visual response has roughly one second of lag. Preserve timestamps and restart from the first valid value after nulls or real segment breaks; never average across gaps. Ordinary pause connections remain. HR, ECG and History do not use the filter. Each five-minute window initializes at its first raw point, so its left edge can show a brief settling transition. This supersedes 5.53's statement that the curve passes through every raw reading.
- 新增3项单元检查覆盖密集抖动衰减、不修改输入/时间/断段标记、空值/缺口重置及不规则采样时间响应；新增1项受控UI检查覆盖1201个合成点、改名、Mean/Max保留与两主题/字号1.0和2.0。已有Session选择测试按可点击节点区分Cadence标签与同名指标标题。
  Add three unit checks for dense jitter attenuation, unchanged input/times/break flags, gap/null resets and elapsed-time response; add one controlled UI check with 1,201 synthetic points, the renamed tab, unchanged statistics and both themes at font scales 1.0/2.0. Existing Session selection tests distinguish the clickable Cadence tab from the metric heading.
- Debug/测试APK构建、275项单元测试和lint通过（0 errors、23 warnings）。证据：build/cadence-display-validation.txt、build/cadence-ui-build.txt及app/build报告。未安装/操作Samsung/H10，无commit/push。
  Debug/test APK builds, all 275 unit tests and lint pass (zero errors, 23 warnings). Evidence: build/cadence-display-validation.txt, build/cadence-ui-build.txt and app/build reports. No Samsung/H10 installation/operation, commit or push.
- 手机模拟器验收：独立Pixel_9（1080×2340、450dpi）19项相关图表/Session检查全部通过，含密集合成数据及Compose字号1.0/2.0、深浅主题。已视觉核对默认整页Cadence、密集数据浅色1.0与深色2.0截图。证据：build/cadence-phone-tests-retry.txt、cadence-phone-captures/、cadence-session-captures/。属于受控模拟数据，不代表H10实测。
  All 19 related chart/Session checks pass on an isolated Pixel_9 phone emulator at 1080×2340/450dpi, including dense synthetic data and Compose font scales 1.0/2.0 in both themes. Inspected the full Session Cadence view and dense light/1.0 and dark/2.0 captures. Evidence: build/cadence-phone-tests-retry.txt, cadence-phone-captures/ and cadence-session-captures/. These are controlled fixtures, not H10 results.
- 环境记录：最初For_a26实际为Wear OS配置，17/19通过而手机整页2项失败，不将其计入手机验收；Pixel_8被现有实例占用后改用Pixel_9。Pixel_9第一次仪器启动报告Process crashed且无crash buffer记录，重试19项全部通过；原因未确认，不将初次启动记为通过。本轮独立实例验收后关闭。
  Environment notes: For_a26 was actually a Wear OS configuration, passing 17/19 checks but failing two phone-layout checks; excluded from phone acceptance. Pixel_8 was already occupied, so Pixel_9 was used. Its first instrumentation launch reported Process crashed with no crash-buffer entry; a retry passed all 19 checks. The initial launch cause is unconfirmed and is not counted as a pass. The isolated instance was closed after validation.
## 5.55 步频显示更新频率 / Cadence display cadence（2026-10-04）

- 用户确认按推荐方案更新：Session当前Cadence及两处Mean/Max每1000ms读取最新值；Cadence图表每500ms取点与获取显示快照。HR仍250ms、ECG仍100ms获取快照，原Session的250ms定时入口、ACC 100Hz、步伐检测、五秒步频计算、History每秒桶与保存均不变。
  Apply the approved rates: sample the current Session cadence and both sets of Mean/Max readings every 1,000 ms; record live cadence points and obtain its display snapshots every 500 ms. HR snapshots remain 250 ms and ECG 100 ms. Preserve the Session 250 ms tick, ACC 100 Hz acquisition, step detection, five-second cadence calculation, per-second History buckets and persistence.
- 新增共用rememberCadenceDisplay，仅由MotionCard和LiveChartPanel的Cadence显示消费，不回写StepState。首次显示、暂停/恢复、有效/空值切换、无ACC及时长重置立即更新，不需等待一秒；普通读数只显示下一次采样时的最新值。原始统计没有改为一秒计算，Total Steps/Duration继续原频率。
  The shared rememberCadenceDisplay is used only by MotionCard and LiveChartPanel cadence displays and never writes back to StepState. First display, pause/resume, validity changes, no-ACC state and duration reset update immediately. Ordinary readings use the newest value on the next one-second tick. Statistical computation and Total Steps/Duration retain their original rates.
- LiveCharts仍由250ms入口调用，但每500ms才添加运动点；五分钟典型保留600点、容量上限601。被跳过的读数若发生空值/真实分段则保留待断段标志，下一点不跨缺口连接；缺口后立即暂停也不得恢复正常连接资格。正常暂停预热/首次确认连接和原一秒绘图低通/贝塞尔保留。
  LiveCharts still receives 250 ms calls but adds motion points only every 500 ms, typically retaining 600 points over five minutes with a 601-point cap. Missing values or real segment changes between samples latch a break for the next point, including when followed immediately by pause. Preserve ordinary pause/warmup/confirmation connections and the existing one-second visual low-pass/Bezier drawing.
- 本轮新增2项单元检查验证500ms边界、跳过空值的断段保留及缺口后暂停；更新既有250ms/点数假设。新增2项模拟器计时检查验证Current/Mean/Max保持及一秒后取最新值、缺失/暂停/恢复/重置即时生效。277项单元测试、debug/测试APK构建和lint通过（0 errors、23 warnings）。证据：build/cadence-rate-validation.txt、cadence-rate-final-check.txt、cadence-rate-ui-build.txt。
  Add two unit checks for the 500 ms boundary, skipped missing readings and pause after a gap, and update prior cadence point-count/timing assumptions. Two emulator timing checks cover current/mean/max sampling and immediate missing/pause/resume/reset transitions. All 277 unit tests, debug/test APK builds and lint pass (zero errors, 23 warnings). Evidence: build/cadence-rate-validation.txt, cadence-rate-final-check.txt and cadence-rate-ui-build.txt.
- 独立Pixel_9手机模拟器（1080×2340、450dpi）分轮38项不同检查全部通过：CadenceRefresh 2、SessionMetrics 17、SessionReference 3、LiveChartCard 16。首轮36/38通过；两项仍断言已移除Estimated Distance的旧测试按现行Duration/Total Steps更新后重跑2/2通过。保留全部原始统计断言。本轮包括计时检查和既有主题/字号检查，未另作截图视觉验收或真机性能测量。
  Across runs, all 38 distinct checks pass on the isolated Pixel_9 phone emulator (1080×2340, 450dpi): CadenceRefresh 2, SessionMetrics 17, SessionReference 3 and LiveChartCard 16. The first run passed 36/38; two obsolete Estimated Distance assertions were updated to the current Duration/Total Steps presentation and both passed on retry. Original statistical assertions remain. Includes timing and existing theme/font checks; no separate screenshot visual acceptance or hardware performance measurement this turn.
- 模拟器证据：build/cadence-rate-ui.txt、cadence-rate-ui-retry.txt。两对AGENTS.md/prompt.md同步；本轮专用模拟器完成后关闭，未安装/操作Samsung/H10，无commit/push。
  Emulator evidence: build/cadence-rate-ui.txt and cadence-rate-ui-retry.txt. Both AGENTS.md/prompt.md pairs are synchronized. Close the dedicated emulator after validation; no Samsung/H10 installation/operation, commit or push.

## 5.56 当前未使用路径清理 / Retired-path cleanup（2026-10-04）

- 用户批准上一轮建议的两批代码清理；第7项临时产物按保留作业证据处理，不批量删除已有build/output/tmp、截图、日志或实际设备数据。此节覆盖早期保留速度、距离、步长估计、最小步频和ACC展示副本的要求。
  The user approved both proposed code-cleanup batches. Preserve existing build/output/tmp evidence, captures, logs and device data. This section supersedes earlier retention of speed, distance, stride estimation, minimum cadence and the ACC display copy.
- 删除未调用HeartRateZonePanel及ChartPlot；formatZoneDuration移入ZoneDuration.kt继续共用。移除旧尺寸常量、模板颜色资源及两个Example测试。
  Remove the uncalled HeartRateZonePanel and ChartPlot, retain the shared formatter in ZoneDuration.kt, and remove unused dimensions, template colors and the two Example tests.
- AccBuffer仅逐样本转发并检测跨批30ms缺口，不再保留十秒/1000点列表或StateFlow；移除未消费accSamples/ecgSamples对外接口，保留ECG实际绘图缓存。StepState.message及其生成路径、仅供测试的latestCommitted副本删除；测试改观察回调/返回值，保留采集、错误和恢复检查。
  AccBuffer forwards every sample and detects cross-batch gaps above 30 ms without a ten-second/1,000-point list or StateFlow. Remove unused accSamples/ecgSamples exports, retaining the real ECG chart buffer. Remove unused StepState.message generation and the test-only latestCommitted copy; tests observe callbacks/returns while preserving acquisition/error/recovery coverage.
- 删除StrideLengthEstimator、StepCandidate.length、速度/距离/最小步频计算、字段和SQLite读写、ChartKind.SPEED及旧子选择。LiveCharts/MotionHistory仅按步频有效性、真实缺口和既有暂停规则判断连线。保留原步伐确认、Total Steps、Mean/Max cadence、评分、HR/ECG及暂停继续逻辑。
  Remove stride length, speed, distance, minimum cadence, their persistence fields and SPEED chart branches/sub-selection. Live/history cadence continuity uses cadence validity, real gaps and existing pause rules. Preserve step confirmation, totals, mean/max cadence, scores, HR/ECG and pause/resume.
- SQLite从v6升至v7，在升级事务中重建sessions和motion_points，仅省略退休字段；其余摘要、HR/步频/ECG、评分及缺口标记保留。沿用v1—v5既有升级语义，v6评分直接保留；升级失败由SQLiteOpenHelper事务回滚。没有打开或迁移用户手机数据库。
  SQLite v7 rebuilds sessions and motion_points transactionally, omitting only retired fields and retaining other summaries, HR/cadence/ECG, scores and gap markers. Preserve existing v1–v5 migration semantics and v6 stored scores; SQLiteOpenHelper rolls failed upgrades back. No user-device database was opened or migrated.
- 删除退休功能专属测试，保留混合测试内仍有效的断言；区间UI检查更新为当前活动时长比例、整数四舍五入及着色范围。新增两项v6→v7迁移测试代码覆盖保留/重开/级联删除和失败回滚重试；RR/RPE旧版本迁移检查保留。所有测试均未执行，不能沿用旧测试通过数量作为本轮结果。
  Remove retired-feature-only checks and preserve useful assertions in mixed tests. Update zone UI checks for active-time proportions, integer rounding and colored fills. Add two v6→v7 migration checks for preservation/reopen/cascade and rollback/retry; retain RR/RPE legacy migration checks. No tests were run; prior passing counts do not describe this revision.
- 验证：离线assembleDebug、compileDebugUnitTestKotlin、compileDebugAndroidTestKotlin通过，日志build/cleanup-compile.txt。初轮编译暴露的Compose隐式委托import及旧ACC缓存测试引用已修正。未运行测试、lint、模拟器或Samsung/H10；数据库迁移运行验收pending。两对文档同步，无commit/push。
  Offline assembleDebug and both test Kotlin compilation tasks pass; see build/cleanup-compile.txt. Corrected implicit Compose delegate imports and obsolete ACC-cache references found by compilation. No tests, lint, emulator or Samsung/H10 runs; runtime database migration acceptance is pending. Both document pairs synchronized; no commit/push.

## 5.57 Session移除最后接收时间 / Remove Session reception-time display（2026-10-04）

- 按用户要求删除心率卡片整行Last Received及时间值，移除专用日期格式化和import。删除HeartRateReading.receivedAt、LatestHeartRate.receive的日期参数及其传递，不保留闲置字段或兼容入口。
  Remove the entire Last Received row and its time value, formatting and imports. Remove HeartRateReading.receivedAt and the date parameter passed to LatestHeartRate.receive, leaving no unused field or compatibility overload.
- 保留当前HR、Max/Mean、强度、接触/失败状态与统计。会话起止日期、单调计时、缺口检测、图表时间轴和SQLite v7不变；相同HR再次到达仍按每批数据更新统计与历史。测试夹具移除旧日期参数，旧接收时间断言删除或改为检查现行行为。
  Preserve current HR, max/mean, intensity, contact/failure states and statistics. Keep session dates, monotonic timing, gap detection, chart timestamps and SQLite v7. Repeated equal HR samples still update statistics and history per batch. Update fixtures and retired timestamp assertions for the new model.
- 本轮离线assembleDebug、testDebugUnitTest及compileDebugAndroidTestKotlin通过；267项单元测试，0 failures/errors/skips。日志：build/last-received-validation.txt。未新增测试用例，未运行仪器测试、lint、模拟器或Samsung/H10；不宣称实际界面或数据库升级验收通过。两对文档同步，无commit/push。
  Offline debug build, unit tests and instrumentation-test Kotlin compilation pass: 267 unit tests, zero failures/errors/skips. See build/last-received-validation.txt. No new test cases, instrumentation execution, lint, emulator or Samsung/H10 checks; no runtime UI or database-migration acceptance claim. Both document pairs synchronized; no commit/push.

## 5.58 Cadence曲线连接适配 / Cadence curve joins（2026-10-04）

- 用户反馈调整步频显示频率后曲线不平滑，要求检查并修复。源码确认：数值每1000ms显示，曲线每500ms取点/快照，原一秒低通已使用实际dt，因此没有硬编码250ms滤波失配。原贝塞尔却在每个采样点强制水平切线，连续上升/下降会反复变平；点距增加可能使这种观感更明显。未对用户当前真机画面复现，不能认定这是全部成因。
  Source inspection confirms 1,000 ms metric updates, 500 ms chart recording/snapshots and a one-second low-pass already based on actual elapsed time. The previous Bezier joins forced horizontal tangents at every sample, flattening continuous rises/falls; wider point spacing can make this more noticeable. The reported hardware appearance was not reproduced, so this is not a claim of its sole cause.
- 仅Session Cadence改为相邻点共享斜率的三次贝塞尔：斜率按实际时间计算，同向取两侧较小斜率幅值，峰谷/平台取零；控制点横坐标为区间三分之一/三分之二，纵坐标限制在相邻值范围内。连续线性趋势保持直线，不再每点形成水平平台；填充复用同一路径。每个真实断段单独计算。
  Use shared time-based slopes for Session cadence cubic Bezier joins. Same-direction neighbors use the smaller slope magnitude; peaks, troughs and plateaus use zero. Controls at one-third/two-thirds of the time interval stay within endpoint values. Linear trends remain linear instead of flattening at each sample. Fill reuses the path; each real segment is processed independently.
- 保留一秒显示低通、500ms取点/快照、1000ms指标显示、原始步频/统计/评分/SQLite及暂停连接规则；HR、ECG、History绘图不变。新增3项单元检查覆盖500ms滤波时间响应、250/500ms与不规则时间的线性连接、峰谷不越界和真实断段隔离。
  Preserve the one-second display filter, 500 ms chart and 1,000 ms metric rates, raw cadence/statistics/scores/SQLite and pause connections. HR, ECG and History plots are unchanged. Add three unit checks for timed filter response, linear joins at 250/500 ms and irregular intervals, bounded extrema and gap isolation.
- 验证：270项单元测试全部通过（0 failures/errors/skips）；assembleDebug、compileDebugAndroidTestKotlin、lintDebug通过，lint 0 errors、20 warnings。日志build/cadence-curve-tangents-validation.txt。未运行仪器测试、模拟器视觉或Samsung/H10；实际观感仍待设备复核。两对文档同步，无commit/push。
  All 270 unit tests pass with zero failures/errors/skips; debug build, instrumentation-test Kotlin compilation and lint pass (zero errors, 20 warnings). Evidence: build/cadence-curve-tangents-validation.txt. No instrumentation execution, emulator visual check or Samsung/H10 run; device appearance remains unverified. Both documentation pairs synchronized; no commit/push.

## 5.59 HR Zones高度分配 / HR Zones height allocation（2026-10-04）

- 用户反馈Activity Summary的HR Zones挤压导致Z5不可见，要求压缩Intensity和Session Strain两排。默认字号紧凑布局的两排评分高度从72/72dp改为64/48dp，保留原字号与评分内容；HR Zones由剩余高度权重改为140dp，无有效HR需额外提示时为156dp。大字号继续原可滚动布局。
  The user reported clipped Z5 and requested shorter Intensity/Session Strain rows. Compact normal-font rating rows change from 72/72 dp to 64/48 dp without changing text sizes or scores. Reserve 140 dp for HR Zones, or 156 dp when the no-HR message is present, instead of a weighted remainder. Enlarged text retains the scrolling layout.
- 既有HistoryPresentation检查增加Z1—Z5完整边界断言。debug/测试APK构建及lint通过；独立Pixel_9模拟器（1080×2340、450dpi、字号1.0）HistoryPresentation 4项及ActivityMetricsUi 6项全部通过，包含深浅主题布局检查。本轮未运行单元测试、字号2.0重复检查或独立截图视觉核对。
  Extend the existing HistoryPresentation check with unclipped bounds for Z1–Z5. Debug/test APK builds and lint pass. All four HistoryPresentation and six ActivityMetricsUi checks pass on the isolated Pixel_9 at 1080×2340/450dpi and font scale 1.0, including light/dark layout assertions. No unit tests, font-2.0 repeats or independent screenshot inspection this turn.
- 首次仪器启动报告Process crashed；重试10项全部通过。crash日志记录模拟器nexuslauncher异常，未确认与首次仪器失败的因果关系。证据：build/summary-zones-height-build.txt、summary-zones-test-build.txt、summary-zones-light-tests.txt、summary-zones-light-retry.txt、summary-zones-crash.txt。
  The first instrumentation launch reported Process crashed; all ten checks pass on retry. The crash buffer records a nexuslauncher exception without an established causal link to the instrumentation failure. Evidence is in the listed build logs.
- 用户随后明确反馈“我手动测试通过了”，据此记录本次HR Zones显示调整的用户手动验收通过；设备、主题及字号范围未说明，不扩展到其他功能的验收。停止追加测试，关闭本轮独立模拟器。两对文档同步，无commit/push。
  The user subsequently confirmed that manual testing passed. Record user acceptance for this HR Zones display change; device, theme and font scope were not specified, so do not extend acceptance to other features. Stop further testing and close the dedicated emulator. Both document pairs synchronized; no commit/push.

## 5.60 恢复Cadence曲线250ms / Restore 250 ms cadence charts（2026-10-04）

- 按用户确认，Session Cadence曲线恢复每250ms取点和获取显示快照；删除500ms专用刷新分支，与HR共用250ms分支，ECG仍100ms。五分钟缓存上限由601改回1201点，正常等间隔窗口保留1200点。保留被跳过样本的缺口标记、正常暂停连接和五分钟窗口。
  Restore Session cadence recording and display snapshots to 250 ms. Remove the dedicated 500 ms refresh branch and use the shared 250 ms branch with HR; ECG remains 100 ms. Restore the five-minute cap from 601 to 1,201 points, normally 1,200 evenly spaced visible points. Preserve skipped-gap flags, ordinary pause connections and the five-minute window.
- 当前Cadence及两处Mean/Max仍每1000ms取最新值；保留已有一秒显示低通和共享切线贝塞尔。ACC原始采集、步伐检测、步频计算、统计、评分和History保存频率不变。此条覆盖5.55/5.58中的500ms曲线频率。
  Current cadence and both Mean/Max displays still sample the newest values every 1,000 ms. Preserve the one-second visual low-pass and shared-tangent Bezier joins. Raw ACC acquisition, step detection, cadence calculation, statistics, scores and History recording rates remain unchanged. This supersedes the 500 ms chart rate in sections 5.55/5.58.
- 更新既有250ms边界、跳过缺口/暂停、五分钟点数检查，未新增测试用例。270项单元测试全部通过（0 failures/errors/skips）；debug构建、仪器测试源码编译、lint通过（0 errors、20 warnings）。证据build/cadence-250ms-validation.txt。本轮未运行模拟器/仪器测试或真机；不宣称所有陡坡消除或真机观感已验证。两对文档同步，无commit/push。
  Update existing interval-boundary, skipped-gap/pause and five-minute capacity checks without adding test cases. All 270 unit tests pass with zero failures/errors/skips. Debug build, instrumentation-test source compilation and lint pass (zero errors, 20 warnings); see build/cadence-250ms-validation.txt. No emulator/instrumentation execution or hardware checks this turn; no claim that all steep slopes disappear or hardware appearance is verified. Both document pairs synchronized; no commit/push.

## 5.61 Cadence两秒显示滤波试调 / Trial two-second cadence display filter（2026-10-04）

- 根据用户提供的曲线截图及确认，将Session Cadence绘图指数低通时间常数从1000ms改为2000ms，alpha=1-exp(-dt/2000)。保持250ms曲线取点/刷新、1000ms数值显示、共享切线贝塞尔及真实缺口重置。仅改变图线和填充，不修改步频原值、计步、统计、评分、History或SQLite。
  Following the supplied screenshot and user approval, change the Session cadence display low-pass time constant from 1,000 to 2,000 ms, using alpha=1-exp(-dt/2000). Preserve 250 ms chart updates, 1,000 ms numeric updates, shared-tangent Bezier joins and gap resets. Only the plotted line/fill changes; raw cadence, steps, statistics, scores, History and SQLite are unchanged.
- 两秒时间常数会加强细小波动衰减并使曲线响应更慢，不等于固定延迟两秒。阶跃变化一秒后约完成39.3%，两秒后约完成63.2%；截图毛边改善程度需用户实际复核，不宣称已消除。
  The longer time constant attenuates small fluctuations more strongly and slows the visual response; it is not a fixed two-second delay. A step reaches about 39.3% after one second and 63.2% after two. Actual improvement in the reported rough edge remains for user verification.
- 更新已有时间响应断言，将250ms交替100/140合成抖动的稳定范围上限从6收紧到3 steps/min。debug构建和270项单元测试通过（0 failures/errors/skips），日志build/cadence-2s-filter-validation.txt。本轮未运行lint、仪器测试、模拟器或真机；两对文档同步，无commit/push。
  Update existing response assertions and tighten the settled range limit for synthetic alternating 100/140 cadence at 250 ms from six to three steps/min. Debug build and all 270 unit tests pass with zero failures/errors/skips; see build/cadence-2s-filter-validation.txt. No lint, instrumentation, emulator or hardware runs this turn. Both document pairs synchronized; no commit/push.

## 5.62 Summary Cadence平滑 / Smooth Summary cadence（2026-10-04）

- 用户明确截图来自Summary并要求实施修复。HistoryCharts对Cadence启用LivePlot已有smoothLine入口，复用两秒指数显示低通与共享切线贝塞尔；HR和ECG仍走原绘制路径。此前5.61只影响Session，不能作为Summary毛边已修复的证据。
  After the user identified the screenshot as Summary and requested implementation, enable LivePlot's existing smoothLine option for Cadence in HistoryCharts. Reuse the two-second display low-pass and shared-tangent Bezier joins; HR and ECG keep their previous rendering. Section 5.61 affected only Session and did not fix Summary.
- 滤波仅作用于绘图及填充副本，按保存点实际时间间隔计算；真实空值/断段重置，原始每秒保存点、均值虚线、统计、评分及SQLite不变。已有记录重新打开也使用新绘制，无需迁移或重存。两页共用平滑规则，但Summary每秒点与Session每250ms点不同，不保证逐像素相同。
  Filter only copies used for the line/fill, using actual saved-point intervals and resetting at nulls/segment breaks. Preserve raw per-second records, saved-mean references, statistics, scores and SQLite. Existing records use the new rendering without migration or resaving. Both pages share smoothing rules, but one-second Summary and 250 ms Session samples need not produce pixel-identical curves.
- debug构建、270项既有单元测试（0 failures/errors/skips）、仪器测试源码编译和lint通过（0 errors、20 warnings）。日志build/summary-cadence-smoothing-validation.txt。未新增测试，本轮未运行模拟器/仪器测试或真机，Summary实际观感待用户复核。两对文档同步，无commit/push。
  Debug build, all 270 existing unit tests (zero failures/errors/skips), instrumentation-test source compilation and lint pass (zero errors, 20 warnings). Evidence: build/summary-cadence-smoothing-validation.txt. No new tests or emulator/instrumentation/hardware runs this turn; actual Summary appearance remains for user verification. Both document pairs synchronized; no commit/push.
