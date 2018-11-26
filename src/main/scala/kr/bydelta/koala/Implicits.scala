package kr.bydelta.koala

import java.{lang, util}

import kotlin.jvm.functions
import kr.bydelta.koala.data.{Sentence, Word}
import kr.bydelta.koala.proc._

import scala.collection.JavaConverters._

object Implicits {
  /** Kotlin Triple [A, B, C] --> (A, B, C) */
  implicit def kotlinTripleToScalaTriple[A, B, C](triple: kotlin.Triple[A, B, C]): (A, B, C) =
    (triple.getFirst, triple.getSecond, triple.getThird)

  /** Kotlin Pair [A, B] <-- (A, B) */
  implicit def kotlinPairToScalaTuple[A, B](pair: kotlin.Pair[A, B]): (A, B) =
    (pair.getFirst, pair.getSecond)

  /** Kotlin Triple [A, B, C] <-- (A, B, C) */
  implicit def scalaTripleToKotlinTriple[A, B, C](triple: (A, B, C)): kotlin.Triple[A, B, C] =
    new kotlin.Triple(triple._1, triple._2, triple._3)

  /** Kotlin Pair [A, B] <-- (A, B) */
  implicit def scalaPairToKotlinPair[A, B](pair: (A, B)): kotlin.Pair[A, B] =
    new kotlin.Pair(pair._1, pair._2)

  /** java.util.List[A] --> Seq[A] */
  implicit def kotlinListToScalaSeq[A](list: java.util.List[A]): Seq[A] = list.asScala

  /** java.util.List[A] <-- Seq[A] */
  implicit def scalaListToKotlinList[A](seq: Seq[A]): java.util.List[A] = seq.asJava

  /** java.util.Iterable[A] <-- Iterable[A] */
  implicit def scalaIterableToKotlinIterable[A](it: Iterable[A]): java.lang.Iterable[A] = it.asJava

  /** java.util.Set[A] --> Set[A] */
  implicit def kotlinSetToScalaSet[A](set: java.util.Set[A]): Set[A] = set.asScala.toSet

  /** java.util.Set[A] <-- Set[A] */
  implicit def scalaSetToKotlinSet[A](set: Set[A]): java.util.Set[A] = set.asJava

  /** 초성 문자를 종성 조합형 문자로 변경하는 Map */
  lazy val ChoToJong: Map[Char, Char] = ExtUtil.getChoToJong.asScala.map{
    case (from, to) => (from.asInstanceOf[Char], to.asInstanceOf[Char])
  }.toMap

  /** 초성 조합형 문자열 리스트 (UNICODE 순서) */
  lazy val HanFirstList: Array[Char] = ExtUtil.getHanFirstList.map{ _.asInstanceOf[Char] }

  /** 중성 조합형 문자열 리스트 (UNICODE 순서) */
  lazy val HanSecondList: Array[Char] = ExtUtil.getHanSecondList.map{ _.asInstanceOf[Char] }

  /** 종성 조합형 문자열 리스트 (UNICODE 순서). 가장 첫번째는 None (받침 없음) */
  lazy val HanLastList: Array[Option[Char]] = ExtUtil.getHanLastList.map{
    case null => Option.empty
    case char: Character => Option(char.asInstanceOf[Char])
  }

  /** CanSplitSentence의 Extension */
  implicit class CanSplitInScala(splitter: CanSplitSentence){
    /**
      * 주어진 문단 [text]를 문장단위로 분리합니다.
      *
      * @since 2.0.0
      * @param text 문장단위로 분리할 String.
      * @return 문장단위로 분리된 String의 [List].
      */
    def apply(text: String): Seq[String] = splitter.invoke(text)
  }

  /** SentenceSplitter의 Extension */
  object SentenceSplitter{
    /**
      * 분석결과를 토대로 문장을 분리함.
      *
      * @since 2.0.0
      * @param para 분리할 문단.
      * @return 문장단위로 분리된 결과
      */
    def apply(para: Iterable[Word]): Seq[Sentence] = proc.SentenceSplitter.invoke(para)

    /**
      * 분석결과를 토대로 문장을 분리함.
      *
      * @since 2.0.0
      * @param para 분리할 문단.
      * @return 문장단위로 분리된 결과
      */
    def sentences(para: Iterable[Word]): Seq[Sentence] = proc.SentenceSplitter.invoke(para)
  }

  /** CanTag의 Extension */
  implicit class CanTagInScala(tagger: CanTag){
    /**
      * 주어진 문단 [text]을 분석하여 품사를 부착하고, 결과로 [List]<[Sentence]> 객체를 돌려줍니다.
      *
      * @since 2.0.0
      * @param text 분석할 문장입니다.
      * @return 분석된 결과로 [Sentence] 객체들의 목록입니다.
      */
    def apply(text: String): Seq[Sentence] = tagger.tag(text)
  }

  /** CanAnalyzeInScala의 Extension */
  implicit class CanAnalyzeInScala[X](parser: CanAnalyzeProperty[X]){
    /**
      * [sentence]를 분석함. 결과는 각 [Sentence]의 property로 저장됨.
      *
      * @since 2.0.0
      * @param sentence 텍스트에서 변환할 문장입니다.
      * @return 결과가 부착된 문장입니다.
      */
    def apply(sentence: String): Seq[Sentence] = parser.invoke(sentence)

    /**
      * [sentence]를 분석함. 결과는 각 [Sentence]의 property로 저장됨.
      *
      * @since 2.0.0
      * @param sentence 분석 결과를 부착할 문장입니다.
      * @return 결과가 부착된 문장입니다.
      */
    def apply(sentence: Sentence): Sentence = parser.invoke(sentence)

    /**
      * [sentences]를 분석함. 결과는 각 [Sentence]의 property로 저장됨.
      *
      * @since 2.0.0
      * @param sentences 분석 결과를 부착할 문장들의 목록입니다.
      * @return 결과가 부착된 문장들의 목록입니다.
      */
    def apply(sentences: Seq[Sentence]): Seq[Sentence] = parser.invoke(sentences)
  }

  /** Char의 Extension */
  implicit class CharExtension(ch: Char){
    /** 현재 문자를 초성, 중성, 종성 자음문자로 분리해 [Triple]을 구성합니다. 종성이 없으면 [Triple._3] 값은 None.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.dissembleHangul // ㄱ, ㅏ, None
      * ```
      *
      * @since 2.0.0
      * @return [Char.isCompleteHangul]이면 문자를 (초성, 중성, Option[종성])으로 나누고, 아니라면 null.
      * */
    def dissembleHangul: (Char, Char, Option[Char]) = {
      val triple = ExtUtil.dissembleHangul(ch)
      triple.getThird match {
        case null => (triple.getFirst, triple.getSecond, Option.empty[Char])
        case char: Character => (triple.getFirst, triple.getSecond, Option(char.asInstanceOf[Char]))
      }
    }

    /** 현재 문자에서 초성 자음문자를 분리합니다. 초성이 없으면 None.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.getChosung
      * ```
      *
      * @since 2.0.0
      * @return [Char.isChosungJamo]가 참이면 문자를 그대로, [Char.isCompleteHangul]이 참이면 초성 문자를 분리해서 (0x1100-0x1112 대역), 아니라면 None.
      * */
    def getChosung: Option[Char] = ExtUtil.getChosung(ch) match {
      case null => Option.empty
      case char: Character => Option(char.asInstanceOf[Char])
    }

    /** 현재 문자에서 종성 자음문자를 분리합니다. 종성이 없으면 None.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.getJongsung
      * ```
      *
      * @since 2.0.0
      * @return [Char.isJongsungJamo]가 참이면 문자를 그대로, [Char.isJongsungEnding]이 참이면 종성 문자를 분리해서 (0x11A7-0x11C2 대역), 아니라면 None.
      * */
    def getJongsung: Option[Char] = ExtUtil.getJongsung(ch) match {
      case null => Option.empty
      case char: Character => Option(char.asInstanceOf[Char])
    }

    /** 현재 문자에서 중성 모음문자를 분리합니다. 중성이 없으면 None.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.getJungsung
      * ```
      *
      * @since 2.0.0
      * @return [Char.isJungsungJamo]가 참이면 문자를 그대로, [Char.isCompleteHangul]이 참이면 중성 문자를 분리해서 (0x1161-0x1175 대역), 아니라면 None.
      * */
    def getJungsung: Option[Char] = ExtUtil.getJungsung(ch) match {
      case null => Option.empty
      case char: Character => Option(char.asInstanceOf[Char])
    }

    /** 현재 문자가 한중일 통합한자, 통합한자 확장 - A, 호환용 한자 범위인지 확인합니다.
      * (국사편찬위원회 한자음가사전은 해당 범위에서만 정의되어 있어, 별도 확인합니다.)
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '樂'.isCJKHanja
      * ```
      *
      * @since 2.0.0
      * @return 해당 범위의 한자라면 true
      * */
    def isCJKHanja: Boolean = ExtUtil.isCJKHanja(ch)

    /** 현재 문자가 현대 한글 초성 자음 문자인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.isChosungJamo
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isChosungJamo: Boolean = ExtUtil.isChosungJamo(ch)

    /**
      * 현재 문자가 초성, 중성, 종성(선택적)을 다 갖춘 문자인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.isCompleteHangul
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isCompleteHangul: Boolean = ExtUtil.isCompleteHangul(ch)

    /** 현재 문자가 한글 완성형 또는 조합용 문자인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.isHangul
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isHangul: Boolean = ExtUtil.isHangul(ch)

    /** 현재 문자가 한자 범위인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '樂'.isHanja
      * ```
      *
      * @since 2.0.0
      * @return 한자범위라면 true
      * */
    def isHanja: Boolean = ExtUtil.isHanja(ch)

    /** 현재 문자가 불완전한 한글 문자인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.isIncompleteHangul
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isIncompleteHangul: Boolean = ExtUtil.isIncompleteHangul(ch)

    /** 현재 문자가 종성으로 끝인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.isJongsungEnding
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isJongsungEnding: Boolean = ExtUtil.isJongsungEnding(ch)

    /** 현재 문자가 한글 종성 자음 문자인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.isJongsungJamo
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isJongsungJamo: Boolean = ExtUtil.isJongsungJamo(ch)

    /** 현재 문자가 현대 한글 중성 모음 문자인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * '가'.isJungsungJamo
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isJungsungJamo: Boolean = ExtUtil.isJungsungJamo(ch)
  }

  /** CharSequence의 Extension */
  implicit class CharSeqExtension(str: CharSequence){
    /**
      * 주어진 문자열에서 알파벳이 발음되는 대로 국문 문자열로 표기하여 값으로 돌려줍니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * "ABC".alphaToHangul
      * ```
      *
      * @since 2.0.0
      * @return 국문 발음 표기된 문자열
      */
    def alphaToHangul: CharSequence = ExtUtil.alphaToHangul(str)

    /**
      * 주어진 문자열에서 초성, 중성, 종성이 연달아 나오는 경우 이를 조합하여 한글 문자를 재구성합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * // 왼쪽 문자열은 조합형 문자열임.
      * "까?ABC".assembleHangul // "까?ABC"
      * ```
      *
      * @since 2.0.0
      * @return 조합형 문자들이 조합된 문자열. 조합이 불가능한 문자는 그대로 남습니다.
      */
    def assembleHangul: CharSequence = ExtUtil.assembleHangul(str)

    /**
      * (Extension) 이 String 값에 주어진 [tag]가 포함되는지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * POS.NN in "N"
      * \\ 또는
      * "N".contains(POS.NN)
      * ```
      *
      * @since 2.0.0
      * @param tag 하위 분류인지 확인할 형태소 품사표기 값
      * @return 하위 분류에 해당한다면 true
      */
    def contains(tag: POS): Boolean = Util.contains(str, tag)

    /**
      * 현재 문자열 [this]를 초성, 중성, 종성 자음문자로 분리하여 새 문자열을 만듭니다. 종성이 없으면 종성은 쓰지 않습니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * "가나다".dissembleHangul // "ㄱㅏㄴㅏㄷㅏ"
      * ```
      *
      * @since 2.0.0
      * @return [Char.isCompleteHangul]이 참인 문자는 초성, 중성, 종성 순서로 붙인 새 문자열로 바꾸고, 나머지는 그대로 둔 문자열.
      * */
    def dissembleHangul: CharSequence = ExtUtil.dissembleHangul(str)

    /**
      * 주어진 문자열에 적힌 알파벳 발음을 알파벳으로 변환하여 문자열로 반환합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * "에이비씨".hangulToAlpha
      * ```
      *
      * @since 2.0.0
      * @return 영문 변환된 문자열
      */
    def hangulToAlpha: CharSequence = ExtUtil.hangulToAlpha(str)

    /**
      * 국사편찬위원회 한자음가사전에 따라 한자 표기된 내용을 국문 표기로 전환합니다.
      *
      * ## 참고
      * * [headCorrection] 값이 true인 경우, whitespace에 따라오는 문자에 두음법칙을 자동 적용함. (기본값 true)
      * * 단, 다음 의존명사는 예외: 냥(兩), 년(年), 리(里), 리(理), 량(輛)
      *
      * 다음 두음법칙은 사전을 조회하지 않기 때문에 적용되지 않음에 유의:
      * * 한자 파생어나 합성어에서 원 단어의 두음법칙: 예) "신여성"이 옳은 표기이나 "신녀성"으로 표기됨
      * * 외자가 아닌 이름: 예) "허난설헌"이 옳은 표기이나 "허란설헌"으로 표기됨
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * "可口可樂".hanjaToHangul()
      * ```
      *
      * @since 2.0.0
      * @return 해당 범위의 한자라면 true
      */
    def hanjaToHangul(headCorrection: Boolean = true): CharSequence =
      ExtUtil.hanjaToHangul(str, headCorrection)

    /**
      * 주어진 문자열이 알파벳이 발음되는 대로 표기된 문자열인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * "에이비씨".isAlphaPronounced
      * ```
      *
      * @since 2.0.0
      * @return 영문 발음으로만 구성되었다면 true
      */
    def isAlphaPronounced: Boolean = ExtUtil.isAlphaPronounced(str)

    /** 현재 문자열가 한글 (완성/조합)로 끝나는지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * "가나다".isHangulEnding
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isHangulEnding: Boolean = ExtUtil.isHangulEnding(str)

    /** 현재 문자열이 종성으로 끝인지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * "가나다".isJongsungEnding
      * ```
      *
      * @since 2.0.0
      * @return 조건에 맞으면 true
      * */
    def isJongsungEnding: Boolean = ExtUtil.isJongsungEnding(str)
  }

  /** Iterable[String]의 Extension */
  implicit class IterStringExtenstion(list: Iterable[String]){

    /**
      * (Extension) 주어진 목록에 주어진 구문구조 표지 [tag]가 포함되는지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * PhraseTag.NP in Seq("S", "NP")
      * \\ 또는
      * Seq("S", "NP").contains(PhraseTag.NP)
      * ```
      *
      * @since 2.0.0
      * @param tag  속하는지 확인할 구문구조 표지 값
      * @return 목록 중 하나라도 일치한다면 true
      */
    def contains(tag: PhraseTag): Boolean = Util.contains(list, tag)

    /**
      * (Extension) 주어진 목록에 주어진 의존구문 표지 [tag]가 포함되는지 확인.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * DependencyTag.SBJ in Seq("SBJ", "MOD")
      * \\ 또는
      * Seq("SBJ", "MOD").contains(DependencyTag.SBJ)
      * ```
      *
      * @since 2.0.0
      * @param tag 속하는지 확인할 의존구조 표지 값
      * @return 목록 중 하나라도 일치한다면 true
      */
    def contains(tag: DependencyTag): Boolean = Util.contains(list, tag)

    /**
      * (Extension) 주어진 목록에 주어진 의미역 표지 [tag]가 포함되는지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * RoleType.ARG0 in Seq("ARG0", "ARGM_LOC")
      * \\ 또는
      * Seq("ARG0", "ARGM_LOC").contains(RoleType.ARG0)
      * ```
      *
      * @since 2.0.0
      * @param tag 속하는지 확인할 의미역 표지 값
      * @return 목록 중 하나라도 일치한다면 true
      */
    def contains(tag: RoleType): Boolean = Util.contains(list, tag)

    /**
      * (Extension) 주어진 목록에 주어진 개체명 유형 [tag]가 포함되는지 확인합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * CoarseEntityType.PL in Seq("PS", "PL")
      * \\ 또는
      * Seq("PS", "PL").contains(CoarseEntityType.PL)
      * ```
      *
      * @since 2.0.0
      * @param tag 속하는지 확인할 개체명 표지 값
      * @return 목록 중 하나라도 일치한다면 true
      */
    def contains(tag: CoarseEntityType): Boolean = Util.contains(list, tag)
  }

  /** (Char, Char, Option[Char])의 Extension */
  implicit class CharTripleExtension(triple: (Char, Char, Option[Char])){

    /**
      * 초성을 [Triple.first] 문자로, 중성을 [Triple.second] 문자로, 종성을 [Triple.third] 문자로 갖는 한글 문자를 재구성합니다.
      *
      * ## 사용법
      * ```scala
      * import kr.bydelta.koala.Implicits._
      * ('ᄁ', 'ᅡ', None).assembleHangul // "까"
      * ```
      *
      * @since 2.0.0
      * @throws IllegalArgumentException 초성, 중성, 종성이 지정된 범위가 아닌 경우 발생합니다.
      * @return 초성, 중성, 종성을 조합하여 문자를 만듭니다.
      */
    @throws[IllegalArgumentException]
    def assembleHangul: Char =
      triple._3 match {
        case Some(jong) =>
          val t: (Character, Character, Character) = (triple._1, triple._2, jong)
          ExtUtil.assembleHangul(scalaTripleToKotlinTriple(t))
        case _ =>
          val t: (Character, Character, Character) = (triple._1, triple._2, null)
          ExtUtil.assembleHangul(scalaTripleToKotlinTriple(t))
      }
  }

  /** 품사 필터 */
  type POSFilter = kotlin.jvm.functions.Function1[_ >: POS, java.lang.Boolean]

  /**
    * 사용자 사전의 Scala interface
    */
  object Dictionary extends CanCompileDict{
    private var dict: CanCompileDict = _

    /**
      * 사용할 사전을 지정합니다.
      * @param dict 사용할 사전 (Dictionary.INSTANCE)
      */
    def use(dict: CanCompileDict): Unit ={
      this.dict = dict
    }

    /**
      * 사용자 사전에, (표면형,품사)의 여러 순서쌍을 추가합니다.
      *
      * @param pairs 추가할 (표면형, 품사)의 순서쌍들 (가변인자). 즉, [Pair]<[String], [POS]>들
      */
    override def addUserDictionary(pairs: kotlin.Pair[String, _ <: POS]*): Unit =
      dict.addUserDictionary(pairs:_*)

    /**
      * 사용자 사전에, 표면형과 그 품사를 추가합니다.
      *
      * @param morph 표면형 [String]
      * @param tag   품사: [POS] Enum 값.
      */
    override def addUserDictionary(morph: String, tag: POS): Unit =
      dict.addUserDictionary(morph, tag)

    /**
      * 사용자 사전에, (표면형,품사)의 여러 순서쌍을 추가.
      *
      * @param morphs 추가할 단어의 표면형의 목록.
      * @param tags   추가할 단어의 품사의 목록.
      */
    override def addUserDictionary(morphs: util.List[String], tags: util.List[_ <: POS]): Unit =
      dict.addUserDictionary(morphs, tags)

    /**
      * 사용자 사전에, (표면형,품사)의 여러 순서쌍을 추가.
      *
      * @param morphs 추가할 단어의 표면형의 목록.
      * @param tags   추가할 단어의 품사의 목록.
      */
    def addUserDictionary(morphs: Seq[String], tags: Seq[POS]): Unit =
      addUserDictionary(scalaListToKotlinList(morphs), scalaListToKotlinList(tags))

    /**
      * 사전에 등재되어 있는지 확인합니다. 품사 후보 [posTag] 중의 하나라도 참이면 참이라고 판정합니다.
      *
      * @param word   확인할 형태소
      * @param posTag 품사들 후보
      */
    override def contains(word: String, posTag: util.Set[_ <: POS]): Boolean =
      dict.contains(word, posTag)

    /**
      * 사전에 등재되어 있는지 확인합니다. 품사 후보 [posTag] 중의 하나라도 참이면 참이라고 판정합니다.
      *
      * @param word   확인할 형태소
      * @param posTag 품사들 후보(기본값: [POS.NNP] 고유명사, [POS.NNG] 일반명사)
      */
    def contains(word: String, posTag: Set[POS] = Set(POS.NNP, POS.NNG)): Boolean =
      contains(word, scalaSetToKotlinSet(posTag))

    /**
      * 사전에 등재되어 있는지 확인합니다.
      *
      * @param entry 확인할 형태소, 품사의 순서쌍
      */
    override def contains(entry: kotlin.Pair[String, _ <: POS]): Boolean =
      dict.contains(entry)

    /**
      * 사전에 등재되어 있는지 확인합니다.
      *
      * @param entry 확인할 형태소, 품사의 순서쌍
      */
    def contains(entry: (String, POS)): Boolean = contains(scalaPairToKotlinPair(entry))

    /**
      * 원본 사전에 등재된 항목 중에서, 지정된 형태소의 항목만을 가져옵니다. (복합 품사 결합 형태는 제외)
      *
      * @param filter 가져올 품사인지 판단하는 함수.
      * @return (형태소, 품사)의 Iterator.
      */
    override def getBaseEntries(filter: POSFilter):
    util.Iterator[kotlin.Pair[String, POS]] =
      dict.getBaseEntries(filter)

    /**
      * 원본 사전에 등재된 항목 중에서, 지정된 형태소의 항목만을 가져옵니다. (복합 품사 결합 형태는 제외)
      *
      * @param filter 가져올 품사인지 판단하는 함수.
      * @return (형태소, 품사)의 Iterator.
      */
    def getBaseEntries(filter: POS => Boolean): Iterator[(String, POS)] =
      dict.getBaseEntries(new functions.Function1[POS, java.lang.Boolean] {
        override def invoke(p1: POS): java.lang.Boolean = Boolean.box(filter(p1))
      }).asScala.map(kotlinPairToScalaTuple)

    /**
      * 사용자 사전에 등재된 모든 Item을 불러옵니다.
      *
      * @return (형태소, 통합품사)의 Sequence.
      */
    override def getItems: util.Set[kotlin.Pair[String, POS]] = dict.getItems

    /**
      * 사전에 등재되어 있는지 확인하고, 사전에 없는단어만 반환합니다.
      *
      * @param onlySystemDic 시스템 사전에서만 검색할지 결정합니다.
      * @param word          확인할 (형태소, 품사)들.
      * @return 사전에 없는 단어들, 즉, [Pair]<[String], [POS]>들.
      */
    override def getNotExists(onlySystemDic: Boolean, word: kotlin.Pair[String, _ <: POS]*):
    Array[kotlin.Pair[String, POS]] =
      dict.getNotExists(onlySystemDic, word:_*)

    /**
      * 사전에 등재되어 있는지 확인하고, 사전에 없는단어만 반환합니다.
      *
      * @param onlySystemDic 시스템 사전에서만 검색할지 결정합니다.
      * @param word          확인할 (형태소, 품사)들.
      * @return 사전에 없는 단어들, 즉, [Pair]<[String], [POS]>들.
      */
    def getNotExists(onlySystemDic: Boolean, word: (String, POS)*): Array[(String, POS)] =
      dict.getNotExists(onlySystemDic, word.map(scalaPairToKotlinPair):_*).map(kotlinPairToScalaTuple)

    /**
      * 다른 사전을 참조하여, 선택된 사전에 없는 단어를 사용자사전으로 추가합니다.
      *
      * @param dict       참조할 사전
      * @param fastAppend 선택된 사전에 존재하는지를 검사하지 않고, 빠르게 추가하고자 할 때 (기본값 false)
      * @param filter     추가할 품사를 지정하는 함수. (기본값 [POS.isNoun])
      */
    override def importFrom(dict: CanCompileDict, fastAppend: Boolean, filter: POSFilter): Unit =
      this.dict.importFrom(dict, fastAppend, filter)

    /**
      * 다른 사전을 참조하여, 선택된 사전에 없는 단어를 사용자사전으로 추가합니다.
      *
      * @param dict       참조할 사전
      * @param fastAppend 선택된 사전에 존재하는지를 검사하지 않고, 빠르게 추가하고자 할 때 (기본값 false)
      * @param filter     추가할 품사를 지정하는 함수. (기본값 [POS.isNoun])
      */
    def importFrom(dict: CanCompileDict, fastAppend: Boolean, filter: POS => Boolean): Unit =
      this.dict.importFrom(dict, fastAppend, new functions.Function1[POS, java.lang.Boolean] {
        override def invoke(p1: POS): java.lang.Boolean = Boolean.box(filter(p1))
      })

    /**
      * 다른 사전을 참조하여, 선택된 사전에 없는 체언([POS.isNoun]이 true인 값)을 사용자사전으로 추가합니다.
      *
      * - 추가시에 선택된 사전에 존재하는지를 검사하여, 없는 값만 삽입합니다.
      *
      * @param dict       참조할 사전
      */
    override def importFrom(dict: CanCompileDict): Unit = this.dict.importFrom(dict)

    /**
      * 사용자 사전에, (표면형,품사)의 여러 순서쌍을 추가.
      *
      * @param entry 추가할 (표면형,품사)의 순서쌍. 즉, [Pair]<[String], [POS]>.
      */
    override def plusAssign(entry: kotlin.Pair[String, _ <: POS]): Unit = dict.plusAssign(entry)

    /**
      * 사용자 사전에, (표면형,품사)의 여러 순서쌍을 추가.
      *
      * @param entry 추가할 (표면형,품사)의 순서쌍. 즉, [Pair]<[String], [POS]>.
      */
    def +=(entry: (String, POS)): this.type = {
      dict.plusAssign(entry)

      this
    }
  }
}
