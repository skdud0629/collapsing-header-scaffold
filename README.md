# 🧭 CollapsingHeaderScaffold

> Jetpack Compose 기반 **커스텀 Collapsing Header Scaffold**  
> 스크롤에 따라 헤더가 자연스럽게 축소·확장되는 인터랙션을 간단히 구현할 수 있습니다.  
> 기존 라이브러리의 제한을 해결하고, Compose 환경에 최적화된 구조로 재설계했습니다.
<img src=https://github.com/user-attachments/assets/027b4f1e-7852-43a5-abf9-c59b09ba81a4 width="230" height="450">


---

## 🧩 Motivation

Compose로 앱을 개발하며 `CollapsingToolbarLayout`(기존 View 기반)의 한계를 느꼈습니다.  
Material3의 `TopAppBarScrollBehavior`는 단순 스크롤 축소만 제공해 **헤더 전환, 색상 변화, 애니메이션 제어**에 유연하지 않았습니다.  

특히 다음 문제를 해결하기 위해 직접 구현했습니다:

| 기존 라이브러리/기능 | 한계점 |
|----------------|--------|
| `CollapsingToolbarLayout` (Android View) | Compose 환경에서 직접 사용 불가 |
| `TopAppBarScrollBehavior` | 헤더 전환/애니메이션 커스터마이징 불가 |
| `accompanist-collapsingtoolbar` | 업데이트 중단, Material3 미지원 |
| `NestedScrollConnection` 기본 예시 | Offset 제어와 Fling 애니메이션 구현 난이도 높음 |

**Compose 기반에서 완전히 독립적으로 작동하며**,  
**Toolbar와 Header의 상태를 완전 제어할 수 있는 구조**를 목표로 작업을 진행했습니다.

---

## ⚙️ Features
- 헤더의 색상, 높이, 알파 값 커스터마이징  
- `LazyColumn`의 스크롤 이벤트와 자연스럽게 연결
- 스크롤 및 플링 애니메이션 지원  
- `NestedScrollConnection` 기반 물리적 인터랙션  

🧠 Key Implementation Details
🔸 CustomCollapsingToolbarState
- 헤더의 높이, offset, progress를 Compose 상태로 관리
- derivedStateOf로 실시간 progress 계산 (0f ~ 1f)
- 화면 밀도에 따라 달라지는 Dp 단위를 실제 픽셀 단위로 변환해, 스크롤 offset과 정확히 연동되도록 구현

🔸 NestedScrollConnection
- 스크롤 위치에 따라 toolbarOffsetPx 갱신
- fling 시 자동으로 expand / collapse 결정
- tween() 애니메이션으로 부드러운 전환 구현

---

## 🎨 Design Considerations
목표	구현 방식
- 헤더 확장/축소 시 색상 전환	lerp(expandedColor, collapsedColor, alpha)
- 상태바 영역 자연스러운 보정	WindowInsets.statusBarsPadding() 적용
- 다양한 스크롤 컴포넌트 호환	LazyListState 기반 NestedScrollConnection
재구성 시 상태 유지	rememberCollapsingToolbarState()로 상태 관리
