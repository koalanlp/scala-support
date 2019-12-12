package kr.bydelta.koala

/**
  * Scala 2.13에서 이름이 변경된 JavaConverters를 CollectionConverters로 import할 수 있도록 하는 trick.
  * @see https://github.com/scala/scala-collection-compat/issues/208#issuecomment-497735669
  */
private object JDKCollectionConvertsCompat {

  object Scope1 {

    object jdk {
      type CollectionConverters = Int
    }

  }

  // import Scope1._ is required
  import Scope1._

  object Scope2 {

    import scala.collection.{JavaConverters => CollectionConverters}

    object Inner {

      // import scala._ is required and should not be concatenated with the next line.
      import scala._
      import jdk.CollectionConverters

      val Converters = CollectionConverters
    }

  }

  val Converters = Scope2.Inner.Converters
}
