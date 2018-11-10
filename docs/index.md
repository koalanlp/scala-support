KoalaNLP
==============
[![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp.svg?style=flat-square&label=release)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp%22)
[![API Doc](https://img.shields.io/badge/doc-Java,Kotlin,Scala-blue.svg?style=flat-square)](http://koalanlp.github.io/KoalaNLP/api/koalanlp/index)
[![분석기별 품사비교표](https://img.shields.io/badge/%ED%92%88%EC%82%AC-%EB%B9%84%EA%B5%90%ED%91%9C-blue.svg?style=flat-square)](https://docs.google.com/spreadsheets/d/1OGM4JDdLk6URuegFKXg1huuKWynhg_EQnZYgTmG4h0s/edit?usp=sharing)

[![MIT License](https://img.shields.io/badge/license-MIT-green.svg?style=flat-square)](https://tldrlegal.com/license/mit-license)
[![nodejs-koalanlp](https://img.shields.io/badge/Nodejs-KoalaNLP-blue.svg?style=flat-square)](https://koalanlp.github.io/nodejs-support)
[![py-koalanlp](https://img.shields.io/badge/Python-KoalaNLP-blue.svg?style=flat-square)](https://koalanlp.github.io/python-support)

# 소개

[KoalaNLP](https://koalanlp.github.io/koalanlp)는 한국어 처리의 통합 인터페이스를 지향하는 Java/Kotlin/Scala Library입니다.

이 프로젝트는 KoalaNLP를 Scala에서 편하게 사용할 수 있도록 하는 여러 Implicit 기능을 담고 있습니다.

개선이나 추가 등을 하고 싶으시다면,
* 개발이 직접 가능하시다면 pull request를 보내주세요. 테스트 후 반영할 수 있도록 하겠습니다.
* 개발이 어렵다면 issue tracker에 등록해주세요. 검토 후 답변해드리겠습니다.

## 특징

[KoalaNLP](https://koalanlp.github.io/koalanlp)는 다음과 같은 특징을 가지고 있습니다.

1. 복잡한 설정이 필요없는 텍스트 분석:

   모델은 자동으로 Maven으로 배포되기 때문에, 각 모델을 별도로 설치할 필요가 없습니다.

2. 코드 2~3 줄로 수행하는 텍스트 처리:

   모델마다 다른 복잡한 설정 과정, 초기화 과정은 필요하지 않습니다. Dependency에 추가하고, 객체를 생성하고, 분석 메소드를 호출하는 3줄이면 끝납니다.

3. 모델에 상관 없는 동일한 코드, 동일한 결과:

   모델마다 실행 방법, 실행 결과를 표현하는 형태가 다릅니다. KoalaNLP는 이를 정부 및 관계기관의 표준안에 따라 표준화합니다. 따라서 모델에 독립적으로 응용 프로그램 개발이 가능합니다.

4. [Java, Kotlin](https://koalanlp.github.io/koalanlp), [Scala](https://koalanlp.github.io/scala-support), [Python 3](https://koalanlp.github.io/python-support), [NodeJS](https://koalanlp.github.io/nodejs-support)에서 크게 다르지 않은 코드:

   KoalaNLP는 여러 프로그래밍 언어에서 사용할 수 있습니다. 어디서 개발을 하더라도 크게 코드가 다르지 않습니다. 


# License 조항

이 프로젝트 자체(KoalaNLP-core, koalanlp-scala)와 인터페이스 통합을 위한 코드는
소스코드에 저작권 귀속에 대한 별도 지시사항이 없는 한 v1.8.0부터 [*MIT License*](https://tldrlegal.com/license/mit-license)을 따르며,
원본 분석기의 License와 저작권은 각 저작권자가 지정한 바를 따릅니다.

단, GPL의 저작권 조항에 따라, GPL 하에서 이용이 허가되는 패키지들의 저작권은 해당 저작권 규정을 따릅니다.

* Hannanum 및 NLP_HUB: [GPL v3](https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3))
* KKMA: [GPL v2](https://tldrlegal.com/license/gnu-general-public-license-v2) (GPL v2를 따르지 않더라도, 상업적 이용시 별도 협의 가능)
* KOMORAN 3.x: [Apache License 2.0](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))
* Open Korean Text: [Apache License 2.0](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))
* SEunjeon: [Apache License 2.0](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))
* 아리랑: [Apache License 2.0](https://tldrlegal.com/license/apache-license-2.0-(apache-2.0))
* RHINO: [GPL v3](https://tldrlegal.com/license/gnu-general-public-license-v3-(gpl-3)) (참고: 다운로드 위치별로 조항 상이함)
* Daon: 지정된 조항 없음
* ETRI: 별도 API 키 발급 동의 필요

# Dependency 추가

## Java 패키지 목록
| 패키지명            | 설명                                                                 |  버전    | License (원본)     |
| ------------------ | ------------------------------------------------------------------ | ------- | ------------ |
| `koalanlp-scala`   | Scala를 위한 편의기능 (Implicit conversion 등)                         | [Scala 2.11 ![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-scala_2.11.svg?style=flat-square&label=r) 2.12 ![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-scala_2.12.svg?style=flat-square&label=r) 2.13 ![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-scala_2.13.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-scala%22) | MIT |
| `koalanlp-kmr`     | 코모란 Wrapper / 분석범위: 형태소                                       | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-kmr.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-kmr%22)         | Apache 2.0 |
| `koalanlp-eunjeon` | 은전한닢 Wrapper / 분석범위: 형태소 <sup>2-3</sup>                      | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-eunjeon.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-eunjeon%22) | Apache 2.0 |
| `koalanlp-arirang` | 아리랑 Wrapper / 분석범위: 형태소 <sup>2-1</sup>                        | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-arirang.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-arirang%22) | Apache 2.0 |
| `koalanlp-rhino`   | RHINO Wrapper / 분석범위: 형태소 <sup>2-1</sup>                        | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-rhino.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-rhino%22)     | GPL v3 |
| `koalanlp-daon`    | Daon Wrapper / 분석범위: 형태소 <sup>2-1</sup>                         | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-daon.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-daon%22)       | MIT(별도 지정 없음) |
| `koalanlp-okt`     | Open Korean Text Wrapper / 분석범위: 문장분리, 형태소 <sup>2-3</sup>     | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-okt.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-okt%22)        | Apache 2.0  |
| `koalanlp-kkma`    | 꼬꼬마 Wrapper / 분석범위: 형태소, 의존구문 <sup>2-1</sup>                | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-kkma.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-kkma%22)       | GPL v2    |
| `koalanlp-hnn`     | 한나눔 Wrapper / 분석범위: 문장분리, 형태소, 구문분석, 의존구문 <sup>2-1</sup>| Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-hnn.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-hnn%22)        | GPL v3    |
| `koalanlp-etri`    | ETRI Open API Wrapper / 분석범위: 형태소, 구문분석, 의존구문, 개체명, 의미역 | Java [![Version](https://img.shields.io/maven-central/v/kr.bydelta/koalanlp-etri.svg?style=flat-square&label=r)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22koalanlp-etri%22)      | MIT<sup>2-2</sup> |

> <sup>주2-1</sup> 꼬꼬마, 한나눔, 아리랑, RHINO 분석기는 타 분석기와 달리 Maven repository에 등재되어 있지 않아, 원래는 수동으로 직접 추가하셔야 합니다.
> 이 점이 불편하다는 것을 알기에, KoalaNLP는 assembly 형태로 해당 패키지를 포함하여 배포하고 있습니다. 포함된 패키지를 사용하려면, `assembly` classifier를 사용하십시오.
> "assembly" classifier가 지정되지 않으면, 각 분석기 라이브러리가 빠진 채로 dependency가 참조됩니다.
>
> <sup>주2-2</sup> ETRI의 경우 Open API를 접근하기 위한 코드 부분은 KoalaNLP의 License 정책에 귀속되지만, Open API 접근 이후의 사용권에 관한 조항은 ETRI에서 별도로 정한 바를 따릅니다.
> 따라서, ETRI의 사용권 조항에 동의하시고 키를 발급하셔야 하며, 다음 위치에서 발급을 신청할 수 있습니다: [키 발급 신청](http://aiopen.etri.re.kr/key_main.php)
>
> <sup>주2-3</sup> **은전한닢과 Open Korean Text는 원본 분석기가 Scala 2.12에서 개발되어 있어, Scala 2.12만 지원합니다.**  

## 실행환경
* KoalaNLP의 모든 코드는 Java 8을 기준으로 컴파일되고 테스트 되었습니다.

## SBT
(버전은 Latest Release 기준입니다. SNAPSHOT을 사용하시려면, `latest.integration`을 사용하세요.)
```sbtshell
val koalaVer = "2.0.0"

// Scala support 추가
libraryDependencies += "kr.bydelta" %% "koalanlp-scala" % koalaVer

// 코모란 분석기의 경우
resolvers += "jitpack" at "https://jitpack.io/"
libraryDependencies += "kr.bydelta" % "koalanlp-kmr" % koalaVer

// 은전한닢 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-eunjeon" % koalaVer

// 아리랑 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-arirang" % koalaVer classifier "assembly"

// RHINO 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-rhino" % koalaVer classifier "assembly"

// Daon 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-daon" % koalaVer

// Open Korean Text 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-okt" % koalaVer

// 꼬꼬마 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-kkma" % koalaVer classifier "assembly"

// 한나눔 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-hannanum" % koalaVer classifier "assembly"

// ETRI 분석기의 경우
libraryDependencies += "kr.bydelta" % "koalanlp-etri" % koalaVer
```

# 사용방법
아래에는 대표적인 특징만 기술되어 있습니다.

상세한 사항은 [Usage](https://koalanlp.github.io/koalanlp/usage/) 또는 [Kotlin API Doc](http://koalanlp.github.io/koalanlp/api/koalanlp/index.html), 
[Scala Support API Doc](https://koalanlp.github.io/scala-support/api/)을 참고하십시오.

## 여러 패키지의 사용
통합 인터페이스는 여러 패키지간의 호환이 가능하게 설계되어 있습니다. 이론적으로는 타 패키지의 품사 분석 결과를 토대로 구문 분석이 가능합니다.
> __Note:__
> * 본 분석의 결과는 검증되지 않았습니다.
> * 신조어 등으로 인해 한나눔이나 꼬꼬마에서 품사 분석이 제대로 수행되지 않을 경우를 위한 기능입니다.
> * 사용자 정의 사전은 `Tagger`와 `Parser`의 대상이 되는 패키지에 모두에 추가하여야 합니다.
> * 타 패키지의 분석 결과는 ETRI 분석기의 입력으로 쓸 수 없습니다.

### Kotlin
```kotlin
/* 패키지 명: 한나눔(hnn), 코모란(kmr), 꼬꼬마(kkma), 은전한닢(eunjeon), 트위터(twt), 아리랑(arirang) */
// 예시에서는 트위터 문장분석기, 은전한닢 품사 분석, 꼬꼬마 구문 분석을 진행함.
import kr.bydelta.koala.twt.SentenceSplitter
import kr.bydelta.koala.eunjeon.Tagger
import kr.bydelta.koala.kkma.Parser

val splitter = SentenceSplitter()
val tagger = Tagger()
val parser = Parser()

val paragraph = "누군가가 말했다. Python에는 KoNLPy가 있다. Kotlin은 KoalaNLP가 있다."
val sentences = splitter(paragraph)
val tagged = sentences.map{ tagger.tagSentence(it) }
val parsed = tagged.map{ parser.parse(it) }
```

### Scala
```scala
import kr.bydelta.koala.twt.SentenceSplitter
import kr.bydelta.koala.eunjeon.Tagger
import kr.bydelta.koala.kkma.Parser

val splitter = new SentenceSplitter
val tagger = new Tagger
val parser = new Parser

val paragraph = "누군가가 말했다. Python에는 KoNLPy가 있다. Scala는 KoalaNLP가 있었다."
val sentences = splitter.invoke(paragraph)
val tagged = sentences.map(tagger.tagSentence)
val parsed = tagged.map(parser.parse)
```

### Java
```java
import kr.bydelta.koala.twt.SentenceSplitter;
import kr.bydelta.koala.eunjeon.Tagger;
import kr.bydelta.koala.kkma.Parser;
import kr.bydelta.koala.Sentence;

SentenceSplitter splitter = new SentenceSplitter();
Tagger tagger = new Tagger();
Tagger parser = new Parser();

String paragraph = "누군가가 말했다. Python에는 KoNLPy가 있다. Java는 KoalaNLP가 있었다.";
List<String> sentences = splitter.invoke(paragraph);
for(String line : sentences){
  Sentence tagged = tagger.tagSentence(line);
  Sentence parsed = parser.parse(tagged);
}
```