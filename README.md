# Java RPG 전투 시스템 요약

## 개요

객체지향 프로그래밍(OOP)을 활용하여 구현한 간단한 텍스트 기반 RPG 전투 시스템이다.

플레이어와 몬스터가 전투를 진행하며, 몬스터 처치 시 경험치와 골드를 획득하고 레벨업할 수 있다.

---

# 클래스 구조

## Item (추상 클래스)

모든 아이템의 부모 클래스

### 필드

| 변수 | 설명 |
|--------|--------|
| itemName | 아이템 이름 |
| price | 가격 |

### 메서드

- `getItemName()`
  - 아이템 이름 반환

---

## Weapon (Item 상속)

무기 아이템

### 필드

| 변수 | 설명 |
|--------|--------|
| itemDamage | 공격력 |

### 메서드

- `addItemDamagePos(int value)`
  - 공격력 증가

- `getItemDamage()`
  - 공격력 반환

---

## Armour (Item 상속)

방어구 아이템

### 필드

| 변수 | 설명 |
|--------|--------|
| itemProtect | 방어력 |

### 메서드

- `addItemProtectPos(int value)`
  - 방어력 증가

- `getItemProtect()`
  - 방어력 반환

---

## ItemPool

게임 내 아이템 생성 팩토리

### 무기

| 아이템 | 가격 | 공격력 |
|----------|------|----------|
| 나무막대기 | 1G | 15 |
| 나무몽둥이 | 1G | 25 |
| 롱소드 | 5G | 40 |

### 방어구

| 아이템 | 가격 | 방어력 |
|----------|------|----------|
| 갬비슨 | 20G | 20 |
| 호버크 | 50G | 50 |

---

# Character (추상 클래스)

플레이어와 몬스터의 공통 부모 클래스

## 주요 능력치

| 변수 | 설명 |
|--------|--------|
| maxHp | 최대 체력 |
| currentHp | 현재 체력 |
| maxEnergy | 최대 활력 |
| currentEnergy | 현재 활력 |
| damage | 공격력 |
| protect | 방어력 |
| maxItemSlot | 장비 슬롯 수 |

## 장비

| 변수 | 설명 |
|--------|--------|
| equippedWeapon | 장착 무기 |
| equippedArmour | 장착 방어구 |

## 주요 기능

### 생존 여부

```java
isAlive()
```

체력이 0 초과인지 확인

### 체력 감소

```java
addCurrentHpNeg(int damage)
```

### 활력 감소

```java
addCurrentEnergyNeg(int fatigue)
```

### 조사 처리

이름의 마지막 한글 받침 유무를 검사하여

- 을 / 를
- 이 / 가
- 은 / 는

자동 생성

---

# Player

Character 상속

## 추가 필드

| 변수 | 설명 |
|--------|--------|
| level | 레벨 |
| exp | 경험치 |
| gold | 골드 |

## 주요 메서드

### 경험치 증가

```java
addExpPos()
```

### 레벨 증가

```java
addLevelPos()
```

### 골드 증가

```java
addGoldPos()
```

---

## PlayerPool

플레이어 프리셋 생성

### 모험가

| 능력치 | 값 |
|----------|------|
| HP | 100 |
| Energy | 100 |
| Damage | 5 |
| Protect | 0 |
| Level | 0 |
| Gold | 0 |

### 기본 장비

- 롱소드
- 갬비슨

---

# Monster

Character 상속

## 추가 필드

| 변수 | 설명 |
|--------|--------|
| rewardExp | 보상 경험치 |
| rewardGold | 보상 골드 |

---

## MonsterPool

### 고블린

| 능력치 | 값 |
|----------|------|
| HP | 30 |
| Damage | 5 |
| Protect | 5 |
| 경험치 | 1 |
| 골드 | 1 |

장착 무기

- 나무막대기

---

### 오크

| 능력치 | 값 |
|----------|------|
| HP | 200 |
| Damage | 25 |
| Protect | 10 |
| 경험치 | 6 |
| 골드 | 3 |

장착 무기

- 나무몽둥이

---

# LevelManager

플레이어 레벨 관리

## 경험치 테이블

| 레벨 | 필요 경험치 |
|--------|--------|
| 0 → 1 | 1 |
| 1 → 2 | 5 |
| 2 → 3 | 11 |
| 3 → 4 | 19 |
| 4 → 5 | 29 |
| 5 → 6 | 41 |

최대 레벨

```java
6
```

## 기능

### 경험치 획득

```java
gainExpInform()
```

- 경험치 증가
- 레벨업 처리
- 잔여 경험치 출력

---

# GoldManager

골드 획득 처리

## 기능

```java
gainGoldInform()
```

출력 예시

```text
모험가는 3G를 얻었다!
현재 골드: 4G
```

---

# BattleManager

전투 처리 클래스

## 행동 선택

```java
1. 공격
2. 방어
3. 회피
0. 종료
```

---

## 공격

### 피해 공식

```java
realDamage =
(actorDamage * (100 - 0.9 * protect))
/
(100 + protect)
```

방어력이 높을수록 피해 감소

---

## 회피

```java
50% 확률
```

성공 시

```text
공격을 피했다
```

실패 시

```text
피하려 했지만 실패했다
```

---

## 전투 흐름

```text
플레이어 행동 선택
        ↓
공격/방어/회피 실행
        ↓
몬스터 체력 감소
        ↓
몬스터 사망 확인
        ↓
보상 지급
        ↓
다음 전투
```

---

# Main 실행 순서

## 1단계

플레이어 생성

```java
Player adventurer_1
```

---

## 2단계

고블린 전투

```java
Monster goblin_1
```

- 공격
- 방어
- 회피

선택 가능

처치 시

- 경험치 +1
- 골드 +1

---

## 3단계

오크 전투

```java
Monster orc_1
```

처치 시

- 경험치 +6
- 골드 +3

---

## 4단계

게임 종료

```text
-------------------
종료
```

---

# 사용된 OOP 개념

## 추상 클래스

- Item
- Character

---

## 상속

```text
Item
 ├─ Weapon
 └─ Armour

Character
 ├─ Player
 └─ Monster
```

---

## 인터페이스

```java
weaponary
armoury
```

무기와 방어구의 능력치 관리 기능 정의

---

## 다형성

```java
Item item = new Weapon(...)
Item item = new Armour(...)
```

상위 타입(Item)으로 관리 가능

---

# 개선 가능 사항

- 인벤토리 시스템 구현
- 장비 교체 기능
- 스킬 시스템 추가
- 몬스터 AI 추가
- 회복 아이템 추가
- 상점 시스템 구현
- 세이브/로드 기능 추가
- 전투 턴 시스템 개선
- 레벨업 시 능력치 성장 구현
